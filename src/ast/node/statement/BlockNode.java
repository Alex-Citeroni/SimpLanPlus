package ast.node.statement;

import ast.Node;
import ast.node.declaration.DecFunNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Function/Inline block node.
 * <p>
 * block : '{' declaration* statement* '}';
 */
public class BlockNode implements Node {
    private final ArrayList<Node> declarations, statements;
    private boolean isBlockFunction = false, isBlockIte = false;

    public BlockNode(ArrayList<Node> dec, ArrayList<Node> stat) {
        this.declarations = dec;
        this.statements = stat;
    }

    /*
     * Usata da SimplanPlusVisitorImpl, in particolare imposta la variabile isBlockFunction
     * quando il blocco che si sta analizzando è una funzione.
     * Questo per distinguere il body di una funzione (quando si ha una dichiarazione di una function)
     * da quello di un blocco.
     */
    public void setBlockFunction() {
        this.isBlockFunction = true;
    }

    /*
     * Usata da SimplanPlusVisitorImpl, in particolare imposta la variabile isBlockIte
     * quando il blocco che si sta analizzando si trova all'interno di un IfElse.
     */
    public void setBlockIte() {
        this.isBlockIte = true;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        /*
         *  Quando si ha un blocco diverso dal corpo di una funzione creo un nuovo scope, incrementando anche il nesting
         *  level e infine aggiorno il valore di offset.
         *  Quando ho una funzione, vado ad analizzare il contenuto del body
         *  (gestito da DecFunNode) senza creare un nuovo ambiente.
         */
        if (!isBlockFunction) {
            env.symTable.add(new HashMap<>());
            env.nestingLevel++;
            env.offset = -1;
        }
        ArrayList<SemanticError> res = new ArrayList<>();
        if (this.declarations != null && this.declarations.size() > 0)
            res = declarations.stream().flatMap(dec ->
                    dec.checkSemantics(env).stream()).collect(Collectors.toCollection(ArrayList::new));
        if (this.statements != null && this.statements.size() > 0) {
            for (Node stm : statements) {
                boolean flagRet = false;
                if(stm instanceof RetNode){
                    /*
                        * prendo l'indice del return ed eseguo il confronto,
                        * se tale valore coincide con l''indice dell'ultimo statement -->
                        * il return si trova nella posizione giusta.
                     */
                    int indexRet = statements.indexOf(stm);
                    if(!(indexRet == statements.size()-1)) flagRet = true;
                }
                ArrayList<SemanticError> semanticErrors = new ArrayList<>();
                /*
                    * se il return rappresenta l'ultimo statement del blocco allora esegue checkSemantics
                    * altrimenti stampa un messaggio di errore semantico
                 */
                if(!flagRet)semanticErrors = stm.checkSemantics(env); else semanticErrors.add( new SemanticError("Errore in un blocco: dopo un return, non ci devono essere altri statement"));
                res.addAll(semanticErrors);

            }
        }
        if (!isBlockFunction) env.symTable.remove(env.nestingLevel--);
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        declarations.forEach(dec -> dec.typeCheck(typeErr));
        /*
         * nodeList -> contiene tutti gli statement di ritorno di ciascun blocco. Più precisamente:
         *  - se il blocco di uno dei rami contiene un return node
         *  - se tutti e due i rami (then ed else) contengono un return node
         *
         * NV. IteNode --> è possibile mettere come statement un node return
         * (restituendo un int o un bool), in questo caso il programma non ritorna un errore solo se:
         *  1. If-then contiene un return node senza else.
         *  2. If-then-else, entrambi i rami hanno un return dello stesso tipo.
         * Se non vi è un node Return allora si presume che all'interno di ciascun branch ci sia un VoidTypeNode.
         */
        ArrayList<Node> nodeList = statements.stream().filter(stm ->
                stm instanceof RetNode || stm.typeCheck(typeErr) instanceof IteNode &&
                        !(stm.typeCheck(typeErr) instanceof NullTypeNode) &&
                        !((IteNode) stm).getIsElNull()).map(stm ->
                stm.typeCheck(typeErr)).collect(Collectors.toCollection(ArrayList::new));
        if (nodeList.isEmpty()) return new VoidTypeNode(Status.DECLARED);
        /*
         * Controllo che tutti i return node presenti in tutti e due i rami o dentro then abbiano lo stesso tipo
         * (IntTypeNode, BoolTypeNode o VoidTypeNode).
         */
        Node check = nodeList.get(0);
        nodeList.stream().filter(ret -> !SimpLanPlusLib.isSubtype(ret, check)).map(ret ->
                new SemanticError("Tipi restituiti non corrispondenti")).forEach(typeErr::add);
        return SimpLanPlusLib.isSubtype(check, new BoolTypeNode(0, Status.DECLARED)) ?
                new BoolTypeNode(0, Status.DECLARED) :
                SimpLanPlusLib.isSubtype(check, new IntTypeNode(0, Status.DECLARED)) ?
                        new IntTypeNode(0, Status.DECLARED) :
                        SimpLanPlusLib.isSubtype(check, new VoidTypeNode(Status.DECLARED)) ?
                                new VoidTypeNode(Status.DECLARED) : new NullTypeNode(Status.DECLARED);
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        Arrays.asList(declarations, statements).forEach(nodes -> nodes.stream().map(dec ->
                dec.toPrint(indent + "  ")).forEach(builder::append));
        return indent + "Block\n" + builder;
    }

    @Override
    public int getPointLevel() {
        return 0;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        if (!isBlockFunction) {
            env.symTable.add(new HashMap<>());
            env.nestingLevel++;
        }
        ArrayList<SemanticError> res = declarations.stream().flatMap(dec ->
                dec.checkEffects(env).stream()).collect(Collectors.toCollection(ArrayList::new));
        statements.stream().map(stm -> stm.checkEffects(env)).forEach(res::addAll);
        if (!isBlockFunction) {
            env.symTable.remove(env.nestingLevel);
            env.nestingLevel--;
        }
        return res;
    }

    @Override
    public void setStatus(Status status) {

    }

    // getDecList ritorna un ArrayList<Node>, ossia una lista di nodi declarations (variabili)
    public ArrayList<Node> getDecList() {
        return this.declarations;
    }

    // getDecList ritorna un ArrayList<Node>, ossia una lista di nodi declarations (variabili)
    public ArrayList<Node> getStateList() {
        return this.statements;
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        // blockLabel serve per gestire correttamente i return nodes
        Label blockLabel = new Label();
        if (isBlockFunction) env.setIsBlockFuncPre();
        StringBuilder builder = new StringBuilder();
        if (!isBlockFunction) {
            env.incrementNestingLevel();
            env.setLabel(blockLabel.getLabel());
            builder.append("cal\n").append("push $al\n").append("cfp\n");
        }
        Arrays.asList(declarations, statements).forEach(nodes -> nodes.stream().map(dec ->
                dec.codeGeneration(env)).forEach(builder::append));
        if (!isBlockFunction) {
            env.decrementNestingLevel();
            builder.append(blockLabel.getLabel()).append(":\n");
            StringBuilder popLocal = new StringBuilder();
            this.declarations.stream().filter(declaration ->
                    !(declaration instanceof DecFunNode)).map(declaration -> "pop\n").forEach(popLocal::append);
            builder.append(popLocal).append("lw $fp $fp\n").append("pop\n");
            env.removeLabel();
            if (isBlockIte && env.getIsPreBlockFunc() && env.getReturn())
                builder.append("b").append(env.getLabel()).append("\n");
            if (env.getNestingLevel() == -1) builder.append("halt\n");
        }
        env.setReturn(false);
        return builder.toString();
    }

    @Override
    public String getId() {
        return null;
    }
}

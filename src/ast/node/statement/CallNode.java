package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.ArgNode;
import ast.node.exp.BaseExpNode;
import ast.node.exp.DerExpNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Classe che si occupa delle chiamate di funzioni
public class CallNode implements Node {
    private final String ID;
    private STentry entry;
    private final ArrayList<Node> args;
    private boolean isCallExp = false;

    public CallNode(String ID, ArrayList<Node> args) {
        this.ID = ID;
        this.args = args;
    }

    public void setCallExp() {
        this.isCallExp = true;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        int i = env.nestingLevel;
        STentry tmpEntry = null;
        /*
         * Identifica l'id di una specifica funzione all'interno di symbol table e verifica se essa è stata dichiarata.
         * Se al termine della ricerca tmpEntry è vuota allora nel Symbol Table function id risulta assente.
         * Invece se viene rilevata una corrispondenza allora si procede con: assegnare la struttura dati a entry data
         * e la checkSemantics di exp
         */
        while (i >= 0 && tmpEntry == null) tmpEntry = env.symTable.get(i--).get(ID);
        ArrayList<SemanticError> res = new ArrayList<>();
        if (tmpEntry == null) res.add(new SemanticError("Funzione '" + ID + "' non dichiarata"));
        else {
            this.entry = tmpEntry;
            for (Node arg : args) res.addAll(arg.checkSemantics(env));
        }
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        ArrowTypeNode functionType = null;
        if (entry.getType() instanceof ArrowTypeNode) functionType = (ArrowTypeNode) entry.getType();
        else typeErr.add(new SemanticError("Invocazione di una non funzione: " + ID));
        /*
         * Estrae i parametri d'input inseriti dall'utente durante una invocazione di una funzione.
         * FunctionType è una variabile ArrowTypeNode che contiene elementi tra cui args.
         */
        if (functionType != null) {
            ArrayList<ArgNode> argList = functionType.getArgList();
            /*
             * Controlla se il numero di parametri inseriti dall'utente sia corretto al numero di args richiesti.
             * Se i due numeri non corrispondono allora manda un messaggio di errore;
             * Altrimenti si prosegue con la verifica del tipo di ciascun args: accerta (in ordine) che tutti
             * i tipi di parametri inseriti corrispondano a quelli presenti all'interno di functionType.
             */
            if (!(argList.size() == args.size()))
                typeErr.add(new SemanticError("Numero errato di parametri nell'invocazione di: " + ID));
            else {
                IntStream.range(0, args.size()).forEach(i -> {
                    Node arg_i = args.get(i).typeCheck(typeErr);
                    if (!SimpLanPlusLib.isSubtype(arg_i, argList.get(i).getType()) ||
                            (arg_i.getPointLevel() != argList.get(i).getType().getPointLevel()))
                        typeErr.add(new SemanticError("Tipo sbagliato per il parametro numero " + (i + 1) +
                                " nell'invocazione di: " + ID));
                });
                /*
                 * Caso in cui come exp dell'invocazione si ha un'altra invocazione di un'altra funzione:
                 * [es f(g(3)) con f() e g() due funzioni distinte con args int]
                 * Bisogna verificare che il tipo di ritorno non sia void [es g(3) sia una funzione di tipo int]
                 */
                if (isCallExp) {
                    if (functionType.getRet() instanceof VoidTypeNode)
                        typeErr.add(new SemanticError("non è possibile utilizzare la funzione void come exp"));
                } else return new NullTypeNode(Status.DECLARED);
            }
        }
        return functionType != null ? functionType.getRet() : null;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent).append("Call: '").append(ID).append("' -> at nesting level ")
                .append(this.entry.getNestingLevel()).append(" with offset ")
                .append(this.entry.getOffset()).append("\n");
        args.stream().map(exp -> exp.toPrint(indent + "\t")).forEach(builder::append);
        return builder.toString();
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
        int i = env.nestingLevel;
        STentry tmpEntry = null;
        while (i >= 0 && tmpEntry == null) tmpEntry = env.symTable.get(i--).get(ID);
        ArrayList<SemanticError> res = args.stream().flatMap(arg ->
                arg.checkEffects(env).stream()).collect(Collectors.toCollection(ArrayList::new));
        // Ci preoccupiamo solo della variazione degli effetti dei puntatori durante una chiamata di funzione.
        ArrayList<DerExpNode> pointerList = new ArrayList<>();
        for (Node arg : args) {
            Node tmp = arg;
            /*
             * Come gestire dei parametri particolari
             *  BaseExpNode -> estrae exp ed esegui il controllo dell'elemento al suo interno
             *  DerExpNode -> quando exp corrisponde a un ID, il sistema consulta il symbol table
             *   e controlla se tale id è stato già dichiarato e inizializzato.
             */
            if (arg instanceof BaseExpNode) {
                while (((BaseExpNode) tmp).getExp() instanceof BaseExpNode) tmp = ((BaseExpNode) tmp).getExp();
                tmp = ((BaseExpNode) tmp).getExp();
            }
            /*
             * Successivamente aggiorneremo la tabella dei simboli con gli effetti delle funzioni,
             * modificando lo stato di un parametro effettivo con il corrispondente stato di parametro formale.
             * Aggiorna, dentro il symbolTable, le informazioni riguardanti gli effetti della funzione
             */
            args.set(args.indexOf(arg), tmp);
            if (tmp instanceof DerExpNode) {
                DerExpNode tmpDerNode = (DerExpNode) tmp;
                int tmpNest = env.nestingLevel;
                while (env.symTable.get(tmpNest).get(tmpDerNode.getIdNode().getId()) == null) tmpNest--;
                /*
                 * Una volta ottenuti tutti i DerExpNode, dobbiamo controllare il livello del punto con cui
                 * sono deferenziate e confrontarlo con il valore nella tabella dei simboli
                 */
                if (env.symTable.get(tmpNest).get(tmpDerNode.getIdNode().getId()).getType().getPointLevel() -
                        tmpDerNode.getIdNode().getPointLevel() > 0)
                    pointerList.add(tmpDerNode);
            }
        }
        // Se si ha la lista pointer non vuota allora controlla se ogni pointer viene passato con lo status READWRITE
        for (DerExpNode arg : pointerList) {
            int tmpNest = env.nestingLevel;
            while (env.symTable.get(tmpNest).get(arg.getIdNode().getId()) == null) tmpNest--;
            if (env.symTable.get(tmpNest).get(arg.getIdNode().getId()).getType().getStatus() == Status.DELETED)
                res.add(new SemanticError("Impossibile passare un puntatore eliminato come parametro effettivo"));
            else if (env.symTable.get(tmpNest).get(arg.getIdNode().getId()).getType().getStatus() == Status.ERROR)
                res.add(new SemanticError("Errore nell'accesso all'id '" + arg.getIdNode().getId() + "'"));
        }
        // Consulta all'interno del symbolTable gli effetti associati alla funzione
        int tmpArrow = env.nestingLevel;
        while (env.symTable.get(tmpArrow).get(this.ID) == null) tmpArrow--;
        /*
         * Per applicare gli effetti della funzione ai puntatori raccolti in pointerList,
         * estraiamo dal nodo di tipo freccia scritto nella tabella dei simboli i parametri formali perché
         * memorizzano gli effetti associati alla funzione
         */
        ArrayList<ArgNode> argsArrow = ((ArrowTypeNode) env.symTable.get(tmpArrow).get(this.ID).getType()).getArgList();
        HashMap<String, ArrayList<Status>> aliasHsm = new HashMap<>();
        /*
         * Mentre aggiorniamo i parametri effettivi (solo i puntatori) con gli effetti dei parametri formali,
         * costruiamo anche una mappa hash per gli alias. Questo dizionario ha:
         *  - come chiave, l'identificatore del IdNode che compone il parametro effettivo
         *  - come valori, un elenco contenente tutti i parametri formali che sono alias per quel particolare parametro effettivo
         */
        for (int idx = 0; idx < args.size(); idx++)
            if (pointerList.contains(args.get(idx))) {
                String id = ((DerExpNode) args.get(idx)).getIdNode().getId();
                int tmpNest = env.nestingLevel;
                while (env.symTable.get(tmpNest).get(id) == null) tmpNest--;
                // Tutti i parametri effettivi iniziano da READWRITE
                Status argStatus = env.symTable.get(tmpNest).get(id).getType().getStatus();
                /*
                 * Lo stato finale si ottiene come operazione sequenziale tra READWRITE e gli effetti dichiarati dalla
                 * funzione (gli effetti sono calcolati nella dichiarazione della funzione)
                 */
                Status seqFinal = SimpLanPlusLib.seqStatus(Status.READWRITE, argsArrow.get(idx).getType().getStatus());
                // Crea la mappa del alias
                if (aliasHsm.containsKey(id)) aliasHsm.get(id).add(seqFinal);
                else {
                    ArrayList<Status> tmp = new ArrayList<>();
                    tmp.add(seqFinal);
                    aliasHsm.put(id, tmp);
                }
                // Aggiorna la tabella dei simboli con il nuovo stato per i puntatori
                env.symTable.get(tmpNest).get(id).getType().setStatus(SimpLanPlusLib.maxStatus(seqFinal, argStatus));
            }
        // Gestione e controllo del Alias
        for (String key : aliasHsm.keySet()) {
            // Ottieni un elenco di alias per un particolare parametro effettivo
            ArrayList<Status> values = aliasHsm.get(key);
            int size = values.size();
            if (size > 1) {
                // Se sono presenti alias, l'elenco avrà più di un elemento
                Status maxLocal = Status.DECLARED;
                // Eseguire il par tra ogni stato di alias
                Status finalStatus = Status.DECLARED;
                for (int idx = 0; idx < size; idx++) {
                    for (int jdx = idx; jdx < size; jdx++)
                        maxLocal = idx != jdx ? SimpLanPlusLib.maxStatus(SimpLanPlusLib.parStatus(values.get(idx),
                                values.get(jdx)), maxLocal) : values.get(idx);
                    finalStatus = SimpLanPlusLib.maxStatus(maxLocal, finalStatus);
                }
                values.clear();
                // Svuota l'elenco e memorizza solo il risultato di pars
                values.add(finalStatus);
            } else aliasHsm.get(key).clear();
        }
        // Se è presente un alias, l'elenco nella mappa degli alias avrà solo un valore
        for (String key : aliasHsm.keySet())
            if (aliasHsm.get(key).size() != 0) {
                int level = env.nestingLevel;
                STentry entry = null;
                while (level >= 0 && entry == null) entry = env.symTable.get(level--).get(key);
                // Imposta il risultato di pars nella tabella dei simboli
                if (entry != null) entry.getType().setStatus(aliasHsm.get(key).get(0));
            }
        // Segnala potenziali errori dovuti al aliasing
        aliasHsm.keySet().stream().filter(key ->
                aliasHsm.get(key).size() != 0 && aliasHsm.get(key).get(0) == Status.ERROR).map(key ->
                new SemanticError("Errore di aliasing nella chiamata di funzione '" + this.ID +
                        "' sul puntatore: " + key)).forEach(res::add);
        return res;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return IntStream.iterate(this.args.size() - 1, i -> i >= 0, i -> i - 1)
                .mapToObj(i -> args.get(i).codeGeneration(env) + "push $a0\n")
                .collect(Collectors.joining("", "", "jal __" + this.ID +
                        ((ArrowTypeNode) this.entry.getType()).getFunUniqueLabel() + "_\n"));
    }

    @Override
    public String getId() {
        return this.ID;
    }
}
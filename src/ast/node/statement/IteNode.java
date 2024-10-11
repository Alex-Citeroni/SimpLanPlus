package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.type.BoolTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.SimpLanPlusLib.cloneEnvironment;
import static util.SimpLanPlusLib.iteStatus;

/**
 * If-then-else statement node.
 * <p>
 * ite : 'if' '(' exp ')' statement ('else' statement)?;
 */
public class IteNode implements Node {
    private final Node cond, th, el;
    private boolean isElNull = false;

    public IteNode(Node cond, Node th, Node el) {
        this.cond = cond;
        this.th = th;
        this.el = el;
    }

    public boolean getIsElNull() {
        return this.isElNull;
    }

    public Node getTh() {
        return this.th;
    }
    public Node getEl() {
        return this.el;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // Il controllo del numero di return viene eseguito dentro SimplanPlusVisitorImpl: visitIteNode
        ArrayList<SemanticError> res = Stream.of(cond, th).flatMap(node ->
                node.checkSemantics(env).stream()).collect(Collectors.toCollection(ArrayList::new));
        if (this.el != null) res.addAll(this.el.checkSemantics(env));
        else this.isElNull = true;
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node cond_type = cond.typeCheck(typeErr);
        // L'exp all'interno della condizione If deve essere di tipo bool.
        if (cond_type.getPointLevel() != 0 || !SimpLanPlusLib.isSubtype(cond_type,
                new BoolTypeNode(0, Status.DECLARED)))
            typeErr.add(new SemanticError("condizione non booleana in if"));
        Node t = th.typeCheck(typeErr);
        if (el != null) {
            Node e = el.typeCheck(typeErr);
            // Garantisce statement ITENODE annidati
            if (el instanceof IteNode) this.isElNull = ((IteNode) el).getIsElNull() || this.isElNull;
            // Controlliamo che i rami then-else abbiano lo stesso tipo di Return (tutti e due boolType, intType o voidType).
            if (SimpLanPlusLib.isSubtype(t, e)) return e;
            typeErr.add(new SemanticError("Tipi incompatibili in altri rami"));
        }
        return t;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent).append("ITE\n").append(cond.toPrint(indent + "\t"))
                .append(th.toPrint(indent + "\t"));
        if (el != null) builder.append(el.toPrint(indent + "\t"));
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
        ArrayList<SemanticError> res = new ArrayList<>(cond.checkEffects(env));
        if (el != null) {
            /*
             * Nell'analisi dell'effetto if-then-else dobbiamo creare due diversi ambienti in cui continuare l'analisi
             * dell'effetto dei due diversi rami. Questo è il motivo per cui vengono realizzate due diverse copie di env.
             */
            Environment envThen = cloneEnvironment(env), envElse = cloneEnvironment(env);
            res.addAll(th.checkEffects(envThen));
            res.addAll(el.checkEffects(envElse));
            // Ora eseguiamo l'operazione massima tra i due ambienti. Carica la tabella dei simboli.
            env.symTable.stream().flatMap(map -> map.entrySet().stream()).forEach(entry -> {
                String key = entry.getKey();
                int nestLevel = entry.getValue().getNestingLevel();
                Status th = envThen.symTable.get(nestLevel).get(key).getType().getStatus();
                Status max = iteStatus(th, envElse.symTable.get(nestLevel).get(key).getType().getStatus());
                STentry newEntry = max == th ? envThen.symTable.get(nestLevel).get(key) :
                        envElse.symTable.get(nestLevel).get(key);
                env.symTable.get(nestLevel).replace(key, newEntry);
            });
        } else res.addAll(th.checkEffects(env));
        return res;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        Label elseLabel = new Label(), endLabel = new Label();
        // Salva in $t0 il valore false (0).  Il valore booleano della condizione invece viene salvato in $a0.
        String ite = this.cond.codeGeneration(env) + "li $t0 0\nbeq" + elseLabel.getLabel() + " $a0 $t0\n" +
                this.th.codeGeneration(env) + "b " + endLabel.getLabel() + "\n" + elseLabel.getLabel() + ":\n";
        if (this.el != null) ite += this.el.codeGeneration(env);
        ite += endLabel.getLabel() + ":\n";
        return ite;
    }

    @Override
    public String getId() {
        return null;
    }
}
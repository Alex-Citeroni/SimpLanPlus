package ast.node.declaration;

import ast.STentry;
import ast.Node;
import ast.node.type.NullTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Nodo di dichiarazione delle variabili.
 * Consiste in un identificatore e un tipo (a volte anche un'espressione come inizializzazione).
 * <p>
 * decVar : type ID ('=' exp)? ';' ;
 */
public class DecVarNode implements Node {
    private final String id;
    private final Node exp, type;
    public static ArrayList<String> declarations = new ArrayList<>();

    public DecVarNode(String id, Node type, Node exp) {
        this.id = id;
        this.type = type;
        this.exp = exp;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent).append("DecVar: '").append(id).append("' -> ").append(type.toPrint(""));
        if (exp != null) builder.append(exp.toPrint(indent + "\t"));
        return builder.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // Ottieni lo scope nella parte superiore della tabella dei simboli
        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        // Crea una nuova voce nella tabella dei simboli per la variabile
        STentry entry = new STentry(env.nestingLevel, type, env.offset--);
        // Prima controlla exp, perché vogliamo valutarlo nel environment prima di aggiungervi la dichiarazione
        ArrayList<SemanticError> res = new ArrayList<>();
        if (exp != null) res.addAll(exp.checkSemantics(env));
        if (hm.put(id, entry) != null) res.add(new SemanticError("DecVar '" + id + "' già dichiarato"));
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (exp != null) if (!SimpLanPlusLib.isSubtype(this.type, this.exp.typeCheck(typeErr)))
            typeErr.add(new SemanticError("Valore incompatibile per la variabile: " + this.id));
            // È necessario un ultimo controllo perché abbiamo anche tipi di puntatore
        else if (this.type.getPointLevel() != this.exp.typeCheck(typeErr).getPointLevel())
            typeErr.add(new SemanticError("Impossibile assegnare variabili o puntatori di tipo diverso!"));
        return new NullTypeNode(Status.DECLARED);
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
        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        ArrayList<SemanticError> res = new ArrayList<>();
        /*
         * Se la variabile viene inizializzata anche durante la dichiarazione, il suo stato deve essere READWRITE
         * (id predefinito DECLARED, quindi non è necessario impostarlo nel ramo else)
         */
        if (exp != null) {
            res.addAll(exp.checkEffects(env));
            Node tmpID = type;
            tmpID.setStatus(Status.READWRITE);
            hm.put(id, new STentry(env.nestingLevel, tmpID, env.offset--));
        } else hm.put(id, new STentry(env.nestingLevel, type, env.offset--));
        if (type.getStatus().name().equals("DECLARED")) declarations.add(id);
        return res;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();
        if (exp != null) builder.append(exp.codeGeneration(env)).append("push $a0\n");
        else builder.append("addi $sp -1\n");
        return builder.toString();
    }
}
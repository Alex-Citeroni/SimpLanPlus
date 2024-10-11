package ast.node.exp;

import ast.Node;
import ast.node.type.IntTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Nodo di espressione d'interi negativi.
 * <p>
 * exp    :    '-'exp    #negExp
 */
public class NegExpNode implements Node {
    private final Node exp;

    public NegExpNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "NegExpNode\n" + exp.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node typeCheck = this.exp.typeCheck(typeErr);
        if (!SimpLanPlusLib.isSubtype(typeCheck, new IntTypeNode(0, Status.DECLARED)) ||
                typeCheck.getPointLevel() != 0)
            typeErr.add(new SemanticError("tipi incompatibili per operatore -"));
        return typeCheck;
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
        return this.exp.checkEffects(env);
    }

    @Override
    public void setStatus(Status status) {

    }

    // valNegativo = 0 - (valPositivo) --> l'operazione di sottrazione permette di ottenere un valore negativo
    @Override
    public String codeGeneration(CGenEnv env) {
        return this.exp.codeGeneration(env) + "li $t0 0\n" + "sub $t0 $a0 $a0\n";
    }

    @Override
    public String getId() {
        return null;
    }
}
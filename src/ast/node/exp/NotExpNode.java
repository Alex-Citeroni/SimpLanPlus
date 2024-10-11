package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Nodo dell'espressione di negazione (operazione per i booleani).
 * <p>
 * exp    :    '!' exp    #notExp
 */
public class NotExpNode implements Node {
    private final Node exp;

    public NotExpNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "NotExpNode\n" + exp.toPrint(indent + "\t");
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node typeCheck = this.exp.typeCheck(typeErr);
        if (!SimpLanPlusLib.isSubtype(typeCheck, new BoolTypeNode(0, Status.DECLARED)) || typeCheck.getPointLevel() != 0)
            typeErr.add(new SemanticError("tipi incompatibili per l'operatore !"));
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
        return exp.checkEffects(env);
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.exp.codeGeneration(env) + "li $t0 1\n" + "sub $t0 $a0 $a0\n";
    }

    @Override
    public String getId() {
        return null;
    }
}
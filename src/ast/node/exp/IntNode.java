package ast.node.exp;

import ast.Node;
import ast.node.type.IntTypeNode;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * Nodo di espressione intera.
 * <p>
 * exp    :    INT    #valExp
 */
public class IntNode implements Node {
    private final int val;

    public IntNode(int val) {
        this.val = val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return new IntTypeNode(0, Status.DECLARED);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "ValExpNode: " + this.val + "\n";
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
        return new ArrayList<>();
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return "li $a0 " + this.val + "\n";
    }

    @Override
    public String getId() {
        return null;
    }
}
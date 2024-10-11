package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import util.*;

import java.util.ArrayList;

/**
 * Nodo di espressione booleana.
 * <p>
 * exp    :    BOOL    #boolExp
 */
public class BoolNode implements Node {
    private final boolean val;

    public BoolNode(boolean val) {
        this.val = val;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public String toPrint(String indent) {
        return indent + "BoolExpNode: " + this.val + "\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return new BoolTypeNode(0, Status.DECLARED);
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
        return "li $a0 " + (val ? 1 : 0) + "\n";
    }

    @Override
    public String getId() {
        return null;
    }
}
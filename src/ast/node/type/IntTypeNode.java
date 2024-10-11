package ast.node.type;

import ast.Node;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

// tipo Int del nodo, ha come variabile pointLevel e status.
public class IntTypeNode implements Node {
    private final int pointLevel;
    private Status status;

    public IntTypeNode(int pointLevel, Status status) {
        this.pointLevel = pointLevel;
        this.status = status;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "IntType\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return null;
    }

    @Override
    public int getPointLevel() {
        return this.pointLevel;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return null;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
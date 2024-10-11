package ast.node.exp;

import ast.Node;
import util.*;

import java.util.ArrayList;

/**
 * Nodo delle espressioni di base.
 * <p>
 * exp	    : '(' exp ')'		 #baseExp
 */
public class BaseExpNode implements Node {
    private final Node exp;

    public BaseExpNode(Node exp) {
        this.exp = exp;
    }

    public Node getExp() {
        return this.exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return exp.checkSemantics(env);
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.exp.typeCheck(typeErr);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "BaseExpNode\n" + this.exp.toPrint(indent + "\t");
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

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.exp.codeGeneration(env);
    }

    @Override
    public String getId() {
        return null;
    }
}
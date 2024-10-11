package ast.node.exp;

import ast.Node;
import ast.node.statement.CallNode;
import util.*;

import java.util.ArrayList;

/**
 * Chiamata di funzione da un nodo di espressione.
 * <p>
 * exp    :    call    #callExp
 */
public class CallExpNode implements Node {
    private final CallNode callNode;

    public CallExpNode(CallNode callNode) {
        this.callNode = callNode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return this.callNode.checkSemantics(env);
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.callNode.typeCheck(typeErr);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "CallExpNode\n" + this.callNode.toPrint(indent + "\t");
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
        return this.callNode.checkEffects(env);
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.callNode.codeGeneration(env);
    }

    @Override
    public String getId() {
        return null;
    }
}
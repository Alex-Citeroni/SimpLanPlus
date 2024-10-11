package ast.node.exp;

import ast.Node;
import ast.node.IdNode;
import util.*;

import java.util.ArrayList;

/**
 * Nodo di espressione sul lato destro.
 * <p>
 * exp    :    id    #derExp
 */
public class DerExpNode implements Node {
    private final IdNode id;

    public DerExpNode(IdNode id) {
        this.id = id;
    }

    public IdNode getIdNode() {
        return this.id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "DerExpNode: '" + this.id.toPrint("");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return this.id.checkSemantics(env);
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.id.typeCheck(typeErr);
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
        return this.id.checkEffects(env);
    }

    @Override
    public void setStatus(Status status) {
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.id.codeGeneration(env);
    }

    @Override
    public String getId() {
        return this.id.toString();
    }
}
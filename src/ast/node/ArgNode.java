package ast.node;

import ast.Node;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * Nodo argomento della funzione.
 * <p>
 * arg    :    type ID;
 */
public class ArgNode implements Node {
    private final String ID;
    private final Node type;

    public ArgNode(String ID, Node type) {
        this.ID = ID;
        this.type = type;
    }

    public String getId() {
        return ID;
    }

    public Node getType() {
        return type;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return this.type.typeCheck(typeErr);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Arg: '" + ID+"' -> "+ type.toPrint("");
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
        return null;
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return null;
    }
}
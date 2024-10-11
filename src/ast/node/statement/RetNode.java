package ast.node.statement;

import ast.Node;
import ast.node.type.NullTypeNode;
import ast.node.type.VoidTypeNode;
import util.CGenEnv;
import util.Environment;
import util.SemanticError;
import util.Status;

import java.util.ArrayList;

/**
 * Return statement node.
 * <p>
 * ret    :    'return' (exp)?;
 */
public class RetNode implements Node {
    private final Node val;

    public RetNode(Node val) {
        this.val = val;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    @Override
    public String toPrint(String indent) {
        return val != null ? indent + "Return\n" + val.toPrint(indent + "\t") : indent + "Return\n";
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (val == null) return new VoidTypeNode(Status.DECLARED);
        if (val.typeCheck(typeErr).getPointLevel() != 0)
            typeErr.add(new SemanticError("non può restituire i puntatori"));
        else return val.typeCheck(typeErr);
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return val != null ? val.checkSemantics(env) : new ArrayList<>();
    }

    @Override
    public int getPointLevel() {
        return 0;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return val != null ? val.checkEffects(env) : new ArrayList<>();
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        env.setReturn(true);
        // Memorizza $a0 in $rv. Fa un Jump verso la fine del corpo di un function/block Node
        return (this.val != null ? this.val.codeGeneration(env) + "srv\n" : "") + "b " + env.getLabel() + "\n";
    }

    @Override
    public String getId() {
        return null;
    }
}
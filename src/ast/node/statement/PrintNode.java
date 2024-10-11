package ast.node.statement;

import java.util.ArrayList;

import ast.Node;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.NullTypeNode;
import util.*;

/**
 * Print statement node.
 * <p>
 * print    :    'print' exp;
 */
public class PrintNode implements Node {
    private final Node val;

    public PrintNode(Node val) {
        this.val = val;
    }

    @Override
    public String toPrint(String s) {
        return s + "PrintNode\n" + this.val.toPrint(s + "\t");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return this.val.checkSemantics(env);
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node t = this.val.typeCheck(typeErr);
        if (!SimpLanPlusLib.isSubtype(t, new IntTypeNode(0, Status.DECLARED)) &&
                !SimpLanPlusLib.isSubtype(t, new BoolTypeNode(0, Status.DECLARED)))
            typeErr.add(new SemanticError("tipo incompatibile per il print"));
        return new NullTypeNode(Status.DECLARED);
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
    public void setStatus(Status status) {

    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return this.val.checkEffects(env);
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.val.codeGeneration(env) + "print $a0\n";
    }

    @Override
    public String getId() {
        return null;
    }
}
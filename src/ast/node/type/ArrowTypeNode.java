package ast.node.type;

import ast.Node;
import ast.node.ArgNode;
import util.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Function arrow type class.
 * <p>
 * (T1 a1 x T2 a2 x ... x Tn an) -> T
 */
public class ArrowTypeNode implements Node {
    private final ArrayList<ArgNode> args;
    private final Node ret;
    private final int funUniqueLabel;

    public ArrowTypeNode(ArrayList<ArgNode> args, Node ret) {
        this.args = args;
        this.ret = ret;
        this.funUniqueLabel = SimpLanPlusLib.getUniqueLabel();
    }

    public ArrayList<ArgNode> getArgList() {
        return this.args;
    }

    public Node getRet() {
        return this.ret;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        return null;
    }

    @Override
    public String toPrint(String indent) {
        String builder = args.stream().map(arg -> arg.toPrint(indent + "\t")).collect(Collectors.joining());
        return indent + "ArrowType\n" + builder + ret.toPrint(indent + " -> ");
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

    public int getFunUniqueLabel() {
        return this.funUniqueLabel;
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
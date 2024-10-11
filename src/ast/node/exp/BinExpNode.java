package ast.node.exp;

import ast.Node;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.NullTypeNode;
import org.antlr.v4.runtime.Token;
import util.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Nodo delle espressioni binarie.
 * <p>
 * exp    :    left=exp    op    right=exp    #binExp
 */
public class BinExpNode implements Node {
    private final Token op;
    private final Node left, right;

    public BinExpNode(Node left, Token op, Node right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public String toPrint(String s) {
        return s + "BinExp[" + this.op.getText() + "]\n" + this.left.toPrint(s + "\t") +
                this.right.toPrint(s + "\t");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return Stream.of(left, right).flatMap(node ->
                node.checkSemantics(env).stream()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node l = left.typeCheck(typeErr), r = right.typeCheck(typeErr);
        String errore = "tipi incompatibili per l'operatore binario ";
        /*
         * Per prima cosa distinguiamo tra variabili normali e variabili puntatore.
         * Per le variabili normali distinguiamo tra operazioni su interi e booleani
         */
        if (l.getPointLevel() == 0 && r.getPointLevel() == 0)
            // Verifica del tipo di operazioni intere (ad es. 1 + 1 deve essere digitato come intero exp)
            if (op.getText().equals("*") || op.getText().equals("/") || op.getText().equals("+")
                    || op.getText().equals("-"))
                if (!(SimpLanPlusLib.isSubtype(l, new IntTypeNode(0, Status.DECLARED))
                        && SimpLanPlusLib.isSubtype(r, new IntTypeNode(0, Status.DECLARED))))
                    typeErr.add(new SemanticError(errore + op.getText()));
                else return new IntTypeNode(0, Status.DECLARED);
            else // Esempio: 2 < 3 viene tipato come espressione booleana
                if (op.getText().equals("<") || op.getText().equals("<=") || op.getText().equals(">")
                        || op.getText().equals(">="))
                    if (!(SimpLanPlusLib.isSubtype(l, new IntTypeNode(0, Status.DECLARED))
                            && SimpLanPlusLib.isSubtype(r, new IntTypeNode(0, Status.DECLARED))))
                        typeErr.add(new SemanticError(errore + op.getText()));
                    else return new BoolTypeNode(0, Status.DECLARED);
                else // Controllo del tipo di operazioni booleane (ad es. true || false)
                    if (op.getText().equals("&&") || op.getText().equals("||"))
                        if (!(SimpLanPlusLib.isSubtype(l, new BoolTypeNode(0, Status.DECLARED))
                                && SimpLanPlusLib.isSubtype(r, new BoolTypeNode(0, Status.DECLARED))))
                            typeErr.add(new SemanticError(errore + op.getText()));
                        else return new BoolTypeNode(0, Status.DECLARED);
                    else if (!SimpLanPlusLib.isSubtype(l, r))
                        typeErr.add(new SemanticError(errore + op.getText()));
                    else return new BoolTypeNode(0, Status.DECLARED);
        else if (l.getPointLevel() != 0 && r.getPointLevel() != 0 && l.getPointLevel() == r.getPointLevel()) {
            // Per i puntatori, consideriamo solo gli operatori == e !=
            if (op.getText().equals("==") || op.getText().equals("!=")) if (!SimpLanPlusLib.isSubtype(l, r))
                typeErr.add(new SemanticError(errore + op.getText()));
            else return new BoolTypeNode(0, Status.DECLARED);
        } else // Il lato sinistro e il lato destro dell'operatore binario sono due puntatori con point level diverso
            typeErr.add(new SemanticError("impossibile applicare l'operatore '" + op.getText()
                    + "' tra puntatori e variabili"));
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
    public ArrayList<SemanticError> checkEffects(Environment env) {
        return Stream.of(left, right).flatMap(node ->
                node.checkEffects(env).stream()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        StringBuilder builder = new StringBuilder();
        builder.append(left.codeGeneration(env)).append("push $a0\n").append(right.codeGeneration(env)).append("lw $t0 $sp\n");
        switch (op.getText()) {
            case "+":
                builder.append("add $t0 $a0 $a0\n");
                break;
            case "-":
                builder.append("sub $t0 $a0 $a0\n");
                break;
            case "/":
                builder.append("div $t0 $a0 $a0\n");
                break;
            case "*":
                builder.append("mult $t0 $a0 $a0\n");
                break;
            case "<": // $a0 < $t0
                builder.append("less $t0 $a0 $a0\n");
                break;
            case "<=": // $a0 <= $t0
                builder.append("leq $t0 $a0 $a0\n");
                break;
            case ">": // $t0 < $a0
                builder.append("less $a0 $t0 $a0\n");
                break;
            case ">=": // $t0 <= $a0
                builder.append("leq $a0 $t0 $a0\n");
                break;
            case "==": // $a0 == $t0
                builder.append("eq $t0 $a0 $a0\n");
                break;
            case "!=": // $a0 == $t0
                builder.append("neq $t0 $a0 $a0\n");
                break;
            case "&&": // $a0 && $t0
                builder.append("and $t0 $a0 $a0\n");
                break;
            case "||": // $a0 || $t0
                builder.append("or $t0 $a0 $a0\n");
                break;
            default:
        }
        builder.append("pop\n");
        return builder.toString();
    }

    @Override
    public String getId() {
        return null;
    }
}
package ast.node.statement;

import ast.Node;
import ast.STentry;
import ast.node.IdNode;
import ast.node.declaration.DecVarNode;
import ast.node.exp.BaseExpNode;
import ast.node.type.NullTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Nodo di assignment statement.
 * <p>
 * assignment : ID '=' exp;
 */
public class AsgNode implements Node {
    private final IdNode id;
    private final Node exp;

    public AsgNode(IdNode id, Node exp) {
        this.id = id;
        this.exp = exp;
    }

    /*
     * Prima di controllare l'id, controlliamo l'espressione perché può essere una funzione
     * che modifica variabili globali tramite puntatori
     */
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        res.addAll(exp.checkSemantics(env));
        res.addAll(id.checkSemantics(env));
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (!SimpLanPlusLib.isSubtype(this.id.typeCheck(typeErr), this.exp.typeCheck(typeErr)))
            typeErr.add(new SemanticError("Valore incompatibile per la variabile: " + this.id.getId()));
        else if (!Objects.equals(this.id.typeCheck(typeErr).getPointLevel(), this.exp.typeCheck(typeErr).getPointLevel()))
            typeErr.add(new SemanticError("Impossibile assegnare variabili o puntatori di tipo diverso!"));
        return new NullTypeNode(Status.DECLARED);
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Assignment: '" + this.id.toPrint("") + this.exp.toPrint(indent + "\t");
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
        int idLevel = env.nestingLevel;
        STentry tmpEntry = null;
        while (idLevel >= 0 && tmpEntry == null) tmpEntry = env.symTable.get(idLevel--).get(id.getId());
        // Dalle espressioni di base ci interessa ottenere l'espressione nidificata tra parentesi
        Node tmp = this.exp;
        if (exp instanceof BaseExpNode)
            while (((BaseExpNode) tmp).getExp() instanceof BaseExpNode) tmp = ((BaseExpNode) tmp).getExp();
        if (tmpEntry != null) {
            tmpEntry.getType().setStatus(SimpLanPlusLib.seqStatus(tmpEntry.getType().getStatus(), Status.READWRITE));
            // Aggiorna la tabella dei simboli
            env.symTable.get(tmpEntry.getNestingLevel()).replace(id.getId(), tmpEntry);
            if (tmpEntry.getType().getStatus().equals(Status.READWRITE))
                DecVarNode.declarations.remove(id.getId());
        }
        return Stream.of(exp.checkEffects(env),
                id.checkEffects(env)).flatMap(Collection::stream).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void setStatus(Status status) {

    }

    @Override
    public String codeGeneration(CGenEnv env) {
        return this.exp.codeGeneration(env) + "push $a0\n" + this.id.codeGeneration(env) +
                "lw $t0 $sp\npop\nsw $t0 $a0\n";
    }

    @Override
    public String getId() {
        return this.id.toString();
    }
}
package ast.node;

import java.util.ArrayList;

import ast.Node;
import ast.STentry;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import util.*;

public class IdNode implements Node {
    private final String id;
    private STentry entry;
    private final int pointLevel;
    private boolean isRightHandSide = false;

    public IdNode(String id, int pointLevel) {
        this.id = id;
        this.pointLevel = pointLevel;
    }

    @Override
    public String toPrint(String indent) {
        return this.id + indent + "' -> at nesting level " +
                this.entry.getNestingLevel() + " with offset " + this.entry.getOffset() + "\n";
    }

    public String getId() {
        return this.id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        int nestingLevel = env.nestingLevel;
        STentry sTentry = null;
        while (nestingLevel >= 0 && sTentry == null) sTentry = env.symTable.get(nestingLevel--).get(this.id);
        ArrayList<SemanticError> res = new ArrayList<>();
        if (sTentry == null) res.add(new SemanticError("\t\tId '" + this.id + "' non dichiarato"));
        else this.entry = sTentry;
        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        if (entry.getType() instanceof ArrowTypeNode)
            typeErr.add(new SemanticError("uso errato dell'identificatore di funzione"));
        int difference = entry.getType().getPointLevel() - this.pointLevel;
        if (difference < 0) typeErr.add(new SemanticError("troppe operazioni di dereferenziazione"));
        return SimpLanPlusLib.isSubtype(entry.getType(), new IntTypeNode(0, Status.DECLARED)) ?
                new IntTypeNode(difference, Status.DECLARED) :
                new BoolTypeNode(difference, Status.DECLARED);
    }

    @Override
    public int getPointLevel() {
        return this.pointLevel;
    }

    @Override
    public Status getStatus() {
        return Status.DECLARED;
    }

    public void setRightHandSide() {
        this.isRightHandSide = true;
    }

    @Override
    public ArrayList<SemanticError> checkEffects(Environment env) {
        int idLevel = env.nestingLevel;
        STentry tmpEntry = null;
        // Voce della tabella dei simboli Estrae per ID
        while (idLevel >= 0 && tmpEntry == null) tmpEntry = env.symTable.get(idLevel--).get(id);
        ArrayList<SemanticError> res = new ArrayList<>();
        // Consideriamo ora i nodi di sinistra
        if (tmpEntry != null && isRightHandSide) if (entry.getType().getPointLevel() - this.pointLevel == 0) {
            // Qui abbiamo a che fare con variabili normali, int semplici o bool
            if (tmpEntry.getType().getStatus() != Status.READWRITE) {
                switch (tmpEntry.getType().getStatus()) {
                    case DECLARED:
                        res.add(new SemanticError("L'Id '" + id + "' non è stato inizializzato!"));
                        break;
                    case DELETED:
                        res.add(new SemanticError("L'Id '" + id + "' è stato cancellato"));
                        break;
                    case ERROR:
                        res.add(new SemanticError("L'Id '" + id + "' ha restituito uno stato di errore"));
                        break;
                }
                tmpEntry.getType().setStatus(Status.ERROR);
                env.symTable.get(tmpEntry.getNestingLevel()).replace(this.id, tmpEntry);
            }
            /*
             * Qui abbiamo a che fare con i puntatori. Assumiamo che un puntatore abbia lo stato READWRITE solo
             * dopo l'inizializzazione/assegnazione con una nuova espressione.
             */
        } else if (tmpEntry.getType().getStatus().ordinal() != Status.READWRITE.ordinal()) {
            switch (tmpEntry.getType().getStatus()) {
                case DECLARED:
                    res.add(new SemanticError("Il Puntatore '" + id + "' non è stato inizializzato!"));
                    break;
                case DELETED:
                    res.add(new SemanticError("Il Puntatore '" + id + "' è stato cancellato"));
                    break;
                case ERROR:
                    res.add(new SemanticError("Il Puntatore '" + id + "' ha restituito uno stato di errore"));
                    break;
            }
            tmpEntry.getType().setStatus(Status.ERROR);
            env.symTable.get(tmpEntry.getNestingLevel()).replace(this.id, tmpEntry);
            // Consideriamo ora i nodi di sinistra
        } else if (tmpEntry.getType().getStatus() != Status.READWRITE && this.pointLevel > 0 &&
                tmpEntry.getType().getPointLevel() > 0) {
            res.add(tmpEntry.getType().getStatus() == Status.DECLARED ?
                    new SemanticError("Il Puntatore '" + id +
                            "' non può essere de-referenziato in quanto non è stato ancora assegnato") :
                    new SemanticError("Il Puntatore '" + id +
                            "' non può essere de-referenziato poiché è stato cancellato"));
            tmpEntry.getType().setStatus(Status.ERROR);
            env.symTable.get(tmpEntry.getNestingLevel()).replace(this.id, tmpEntry);
        }
        return res;
    }

    @Override
    public void setStatus(Status status) {
    }

    /*
     * Grazie a CGenEnv conosciamo l'attuale livello di annidamento e possiamo ottenere il numero di salti tramite il
     * link di accesso per ottenere il valore dell'id.
     * Il puntatore del frame viene copiato per accedere al registro del collegamento poiché $fp punta a una posizione
     * nella memoria che contiene il collegamento di accesso. Quindi lw $al $al permette di salire lungo la catena statica.
     * Una volta trovato l'indirizzo di memoria, se abbiamo un puntatore, dobbiamo anche recuperare il valore dal heap.
     * Ora $al ha l'indirizzo del valore che ci interessa.
     */
    @Override
    public String codeGeneration(CGenEnv env) {
        return "cal\n" + "lw $al $al\n".repeat(Math.max(0, env.getNestingLevel() - this.entry.getNestingLevel())) +
                "addi $al " + this.entry.getOffset() + "\n" + "lw $al $al\n".repeat(Math.max(0, this.pointLevel)) +
                (isRightHandSide ? "lw $a0 $al\n" : "lal\n");
    }
}
package util;

import ast.Node;
import ast.STentry;
import ast.node.ArgNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.VoidTypeNode;
import org.antlr.v4.runtime.Token;
import parser.SVM.SVMParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Contenitore per funzioni ausiliarie utilizzate in tutto il codice nei pacchetti.
public class SimpLanPlusLib {
    /*
     * Questo contatore è necessario per creare un'etichetta univoca,
     * perché è possibile avere ridefinizioni della stessa funzione in blocchi o funzioni interne.
     */
    private static int funLabelCount = 0;

    public static int getUniqueLabel() {
        return funLabelCount++;
    }

    /**
     * Verifica se "a" <= "b", dove "a" e "b" sono tipi di base: int o bool
     *
     * @param a -> base type
     * @param b -> base type
     * @return boolean
     */
    public static boolean isSubtype(Node a, Node b) {
        return a.getClass().equals(b.getClass());
    }

    /**
     * Operazione per l'analisi degli effetti: restituisce il massimo tra due istanze dell'enumerazione Status.
     *
     * @param s1 -> Status
     * @param s2 -> Status
     * @return max(s1, s2)
     */
    public static Status maxStatus(Status s1, Status s2) {
        return s1.ordinal() <= s2.ordinal() ? s2 : s1;
    }

    /**
     * Operazione per l'analisi degli effetti: restituisce il massimo tra due istanze dell'enumerazione Status,
     * a eccezione di Readwrite/Declared o Declared/Readwrite.
     *
     * @param s1 -> Status
     * @param s2 -> Status
     */
    public static Status iteStatus(Status s1, Status s2) {
        return s1 == Status.DECLARED && s2 == Status.READWRITE ?
                Status.DECLARED : s1 == Status.READWRITE && s2 == Status.DECLARED ?
                Status.DECLARED : maxStatus(s1, s2);
    }

    /**
     * Operazione per l'analisi degli effetti: esegue l'operazione di par tra due stati.
     * Questo è usato solo per aliasing.
     *
     * @param s1 -> Status
     * @param s2 -> Status
     * @return s1 par s2
     */
    public static Status parStatus(Status s1, Status s2) {
        return maxStatus(seqStatus(s1, s2), seqStatus(s2, s1));
    }

    /**
     * Operazione per l'analisi degli effetti:
     * Esegue la sequenza tra due stati. Si prega di notare la condizione della clausola else-if:
     * (s1 == Status.DELETED && s2 == Status.READWRITE)
     * s2 è READWRITE perché assumiamo che i parametri formali della funzione siano READWRITE.
     *
     * @param s1 -> initial Status
     * @param s2 -> final Status
     * @return s1 seq s2
     */
    public static Status seqStatus(Status s1, Status s2) {
        return maxStatus(s1, s2).ordinal() <= Status.READWRITE.ordinal() ? maxStatus(s1, s2) :
                s1.ordinal() <= Status.READWRITE.ordinal() && s2 == Status.DELETED ||
                        s1 == Status.DELETED && s2 == Status.READWRITE
                        ? Status.DELETED : Status.ERROR;
    }

    /**
     * Restituire una copia di environment. Il nuovo environment ha posizioni di memoria diverse da quello d'ingresso.
     *
     * @param env to copy
     * @return new environment
     */
    public static Environment cloneEnvironment(Environment env) {
        Environment newEnv = new Environment(env.nestingLevel, env.offset);
        for (HashMap<String, STentry> map : env.symTable) {
            newEnv.symTable.add(new HashMap<>());
            for (Map.Entry<String, STentry> entry : map.entrySet()) {
                HashMap<String, STentry> newMap = newEnv.symTable.get(newEnv.symTable.size() - 1);
                int newNestingLevel = entry.getValue().getNestingLevel();
                Node newType = entry.getValue().getType();
                int newPointLevel = newType.getPointLevel();
                Status newStatus = newType.getStatus();
                int newOffset = entry.getValue().getOffset();
                if (newType instanceof ArgNode) {
                    System.out.println("ERRORE FATALE");
                    System.exit(0);
                }
                Node newEnvType;
                if (newType instanceof BoolTypeNode) newEnvType = new BoolTypeNode(newPointLevel, newStatus);
                else if (newType instanceof IntTypeNode) newEnvType = new IntTypeNode(newPointLevel, newStatus);
                else {
                    ArrayList<ArgNode> newArgList = new ArrayList<>();
                    ((ArrowTypeNode) newType).getArgList().forEach(arg -> {
                        Node newArgType = arg.getType();
                        int newArgPoint = newArgType.getPointLevel();
                        newArgList.add(new ArgNode(arg.getId(), newArgType instanceof BoolTypeNode ?
                                new BoolTypeNode(newArgPoint, newArgType.getStatus()) :
                                new IntTypeNode(newArgPoint, newArgType.getStatus())));
                    });
                    Node newEnvArrowRetType;
                    if (((ArrowTypeNode) newType).getRet() instanceof BoolTypeNode)
                        newEnvArrowRetType = new BoolTypeNode(0, Status.DECLARED);
                    else if (((ArrowTypeNode) newType).getRet() instanceof IntTypeNode)
                        newEnvArrowRetType = new IntTypeNode(0, Status.DECLARED);
                    else newEnvArrowRetType = new VoidTypeNode(Status.DECLARED);
                    newEnvType = new ArrowTypeNode(newArgList, newEnvArrowRetType);
                }
                newMap.put(entry.getKey(), new STentry(newNestingLevel, newEnvType, newOffset));
            }
        }
        return newEnv;
    }

    // Converte il token REGISTER di base in uno dei token specifici in base al testo.
    public static int getRegister(Token register) {
        switch (register.getText()) {
            case "$a0":
                return SVMParser.A0;
            case "$t0":
                return SVMParser.T0;
            case "$sp":
                return SVMParser.SP;
            case "$ra":
                return SVMParser.RA;
            case "$fp":
                return SVMParser.FP;
            case "$al":
                return SVMParser.AL;
            case "$hp":
                return SVMParser.HP;
            case "$rv":
                return SVMParser.RV;
            default:
                return -1;
        }
    }
}
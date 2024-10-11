package ast;

import java.util.ArrayList;

import util.*;

// Questa è l'interfaccia implementata da ciascun nodo nell'albero della sintassi astratta (AST).
public interface Node {
    String toPrint(String indent); // Stampa AST quando l'analisi semantica è terminata.

    /**
     * Visita AST per verificare se gli ambiti sono formati correttamente, con riferimento alla regola degli ambiti,
     * e costruisce progressivamente la tabella dei simboli; riempie anche alcune strutture di dati durante la visita
     * di AST che verranno utilizzate nelle visite successive.
     *
     * @param env -> elenco delle mappe hash
     * @return un elenco con tutti gli errori di ambito
     */
    ArrayList<SemanticError> checkSemantics(Environment env);

    /**
     * Esegue la seconda visita di AST alla ricerca di tipi non corrispondenti nel codice.
     *
     * @param typeErr -> elenchi di errori di tipo che verranno compilati durante la seconda visita AST
     * @return il tipo dell'intero programma
     */
    Node typeCheck(ArrayList<SemanticError> typeErr);

    /**
     * Recupera il numero di operazioni di dereferenziazione per ottenere l'archivio dati dalla variabile.
     * Le variabili normali hanno un point level 0, mentre i puntatori hanno un point level > 0.
     * Questo è inteso per essere utilizzato solo su IntTypeNode e BoolTypeNode.
     *
     * @return point level del nodo
     */
    int getPointLevel();

    /**
     * Recupera lo stato associato al nodo per eseguire l'analisi degli effetti.
     * Questo deve essere utilizzato solo su IntTypeNode e BoolTypeNode.
     *
     * @return status dei nodi
     */
    Status getStatus();

    /**
     * Questa funzione fa eseguire la terza visita del AST all'analisi degli effetti,
     * occupandosi principalmente di puntatori, identificatori non inizializzati e aliasing.
     *
     * @param env -> elenchi di mappe hash
     * @return liste con errori
     */
    ArrayList<SemanticError> checkEffects(Environment env);

    /**
     * Imposta lo stato associato al nodo come risultato dell'analisi degli effetti.
     * Questo deve essere utilizzato solo su IntTypeNode e BoolTypeNode.
     *
     * @param status del nodo
     */
    void setStatus(Status status);

    /**
     * Una volta eseguita l'analisi semantica,
     * questa funzione visita AST per l'ultima volta in modo che venga generato il bytecode per l'interprete.
     * In questa parte del compilatore, le informazioni come gli offset e le voci della tabella dei simboli lasciate
     * nel AST dalle visite precedenti vengono utilizzate con una versione minima della struttura dei dati dell'ambiente
     * per implementare correttamente la semantica.
     *
     * @param env -> lista di Label
     * @return la traduzione del codice SimpLanPlus in bytecode, visto come una sequenza di stringhe collegate tra loro
     */
    String codeGeneration(CGenEnv env);

    String getId();
}  
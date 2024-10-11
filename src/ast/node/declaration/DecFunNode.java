package ast.node.declaration;

import ast.Node;
import ast.STentry;
import ast.node.ArgNode;
import ast.node.statement.BlockNode;
import ast.node.statement.IteNode;
import ast.node.statement.RetNode;
import ast.node.type.ArrowTypeNode;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.VoidTypeNode;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Nodo di dichiarazione di funzione.
 * <p>
 * decFun : (type | 'void') ID '(' (arg (',' arg)*)? ')' block ;
 */
public class DecFunNode implements Node {
    private final String ID;
    private final Node type;
    private final BlockNode body;
    private final ArrayList<ArgNode> args;
    private int funUniqueLabel;

    public DecFunNode(String ID, Node type, BlockNode body) {
        this.ID = ID;
        this.type = type;
        this.body = body;
        this.args = new ArrayList<>();
    }

    @Override
    public String getId() {
        return this.ID;
    }

    /*
     * checkFuncNested()
     * tipo di ritorno booleano
     * controlla se all'intero del body di una specifica funzione è stata dichiarata
     * (/o sono state dichiarate altre) un'altra.
     *
     */
    public boolean checkFuncNested() {
        ArrayList<Node> listaDec = body.getDecList();
        /*
         * Controllo se esistono delle dichiarazioni di nodo DecFun.
         * Se identifica una funzione decFunNode allora setta la variabile booleana flag a true.
         */
        boolean flag = false;
        if (listaDec.size() > 0) flag = listaDec.stream().anyMatch(dec -> dec instanceof DecFunNode);
        /*
         * Verifica che all'interno di un "blockNode" presente dentro la decFunNode non ci sia un'altra dichiarazione di
         * una nuova funzione.
         * [STATEMENT]CASI IN CUI SI PUò AVERE UN NODO DI TIPO BLOCCO: ITENODE E BLOCKNODE STESSO
         */

        if (body.getStateList().size() > 0) {
            for (Node statement : body.getStateList()) {
                // analizza cosa può contenere un nodo di tipo ITE o BLOCK
                if (statement instanceof IteNode) {
                    //verifica che nel blocco th d'IteNode non ci sia una dichiarazione di una funzione
                    if (((IteNode) statement).getTh() instanceof BlockNode)
                        flag = ((BlockNode) ((IteNode) statement).getTh()).getDecList().stream().anyMatch(dec ->
                                dec instanceof DecFunNode);
                    // se c'è anche un else (più precisamente un blocco Else), assicura che non ci siano dichiarazioni di funzioni
                    if (((IteNode) statement).getIsElNull() && ((IteNode) statement).getEl() instanceof BlockNode)
                        flag = ((BlockNode) ((IteNode) statement).getEl()).getDecList().stream().anyMatch(dec ->
                                dec instanceof DecFunNode);
                } else if (statement instanceof BlockNode)
                    flag = ((BlockNode) statement).getDecList().stream().anyMatch(dec -> dec instanceof DecFunNode);
            }
        }
        return flag;
    }

    public void addArg(ArgNode arg) {
        this.args.add(arg);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // Cerca l'identificatore di funzione nell'ambito corrente
        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, 0);
        ArrayList<SemanticError> res = new ArrayList<>();
        if (hm.put(ID, entry) != null) res.add(new SemanticError("ID parametro '" + ID + "' già dichiarato"));
        else {
            // Crea un nuovo ambiente da zero
            Environment funEnv = new Environment();
            funEnv.nestingLevel = env.nestingLevel;
            /*
             * Il record di attivazione delle funzioni ha parametri superiori sull'indirizzo di memoria del
             * puntatore del frame (a indirizzi di memoria superiori),
             * quindi l'offset per i parametri effettivi deve iniziare da 1
             */
            funEnv.offset++;
            /*
             * L'Environment della funzione è ora vuoto.
             * Per costruire correttamente un nuovo ambiente grezzo aggiungiamo mappe hash vuote considerando
             * il livello di nidificazione a cui viene dichiarata la funzione
             */
            for (int i = env.nestingLevel; i > 0; i--) funEnv.symTable.add(new HashMap<>());
            /*
             * hmFun è la mappa hash SIGMA FUN,
             * quindi verrà riempita con tutte le definizioni di funzione visibili dall'ambito della funzione ID.
             * L'aggiunta d'ID a hmFun consente la ricorsione
             */
            HashMap<String, STentry> hmFun = new HashMap<>();
            if (hmFun.put(ID, entry) != null) res.add(new SemanticError("ID parametro '" + ID + "' già dichiarato"));
            int getFunEnv = env.nestingLevel;
            while (getFunEnv >= 0) {
                /*
                 * Poiché possiamo avere diverse funzioni con lo stesso identificatore dichiarato in ambiti diversi,
                 * consideriamo valida la prima definizione che abbiamo trovato dall'ambito corrente (shadowing)
                 */
                for (Map.Entry<String, STentry> funEntry : env.symTable.get(getFunEnv).entrySet())
                    if (funEntry.getValue().getType() instanceof ArrowTypeNode && !hmFun.containsKey(funEntry.getKey()))
                        hmFun.put(funEntry.getKey(), funEntry.getValue());
                getFunEnv--;
            }
            entry.addType(new ArrowTypeNode(args, this.type));
            hmFun.put(ID, entry);
            funEnv.symTable.add(hmFun);
            // Incrementa il livello di annidamento del nuovo ambiente e inserisce in una nuova hash map i parametri
            funEnv.nestingLevel++;
            HashMap<String, STentry> hmArg = new HashMap<>();
            funEnv.symTable.add(hmArg);
            for (ArgNode arg : args)
                if (hmArg.put(arg.getId(), new STentry(funEnv.nestingLevel, arg.getType(), funEnv.offset++)) != null)
                    res.add(new SemanticError("ID parametro '" + arg.getId() + "' già dichiarato"));
            getFunEnv = env.nestingLevel;
            // per ogni ambiente vai da symTable vai a estrarre e analizzare ogni scope
            while (getFunEnv >= 0) {
                env.symTable.get(getFunEnv).forEach((key, value) -> {
                    // Se la funzione ha delle variabili locali devo fare il medesimo controllo:
                    if (body.getDecList().size() > 0) {
                        /*
                         * Prima di aggiungere una variabile globale dentro hmArg devo assicurarmi
                         * che non ci sia alcuna concorrenza con quelle locali, ad esempio:
                         * {
                         *   int x = 1;
                         *   void f(int n) {
                         *      bool y = true;
                         *      int x = 2;
                         *      if (n == 0) {
                         *          print(x);
                         *      } else {
                         *          x = x * n;
                         *      }
                         *   }
                         *   f(10);
                         *  }
                         *  x è ripetuto due volte, ciò che viene analizzata:  x globale -->
                         *  Se id x globale risulta assente in: hmFun, listaDec(quindi non è definita come variabile
                         *  locale), hmArg quindi non è un parametro d'ingresso della funzione. Aggiungo hmArgs
                         */
                        for (Node node : body.getDecList())
                            if (!hmFun.containsKey(key) && !node.getId().equals(key) && !hmArg.containsKey(key))
                                hmArg.put(key, value);
                        /*
                         * Se la funzione non ha alcuna variabile locale -->
                         * posso aggiungere dentro hmArgs le variabili globali
                         * assicurandomi però che queste non siano dentro hmFun e hmArgs.
                         * Aggiungo tutte le variabili globali del primo blocco
                         */
                    } else if (!hmFun.containsKey(key) && !hmArg.containsKey(key))
                        hmArg.put(key, value);
                });
                getFunEnv--;
            }
            // Questa Label verrà utilizzata nella generazione del codice
            this.funUniqueLabel = ((ArrowTypeNode) entry.getType()).getFunUniqueLabel();
            /*
             * Nel record di attivazione della funzione, le variabili locali si trovano sotto l'indirizzo di ritorno
             * (a indirizzi di memoria inferiori rispetto all'indirizzo di memoria del frame pointer),
             * quindi il loro offset inizierà da -2
             */
            boolean flag = false;
            int dimState = body.getStateList().size();
            if(!(type instanceof VoidTypeNode)){
                Node ultimoEl = body.getStateList().get(dimState-1);
                if(!(ultimoEl instanceof RetNode)){
                    flag = true;
                }

            }
            if((!(type instanceof VoidTypeNode) && !flag) || type instanceof VoidTypeNode ) {
                funEnv.offset = -2;
                res.addAll(body.checkSemantics(funEnv));
                funEnv.symTable.remove(funEnv.nestingLevel);
                funEnv.nestingLevel--;
            } else {
                res.add(new SemanticError("Errore in " + this.ID +" :Dopo un return, non ci devono essere altri statement"));
            }
            /*
             * Controllo: la presenza di funzioni annidate;
             * NB senza flagNested (quindi usando solo annidata) non funziona bene
             * perché ogni volta che controlla una funzione il valore di annidata cambia
             * quindi se ho tre dichiarazioni di funzioni:
             * la prima: funzione annidata e le ultime due invece sono funzioni normali
             * il valore di annidata è relativo all'ultimo controllo d(ovvero della terza func)
             */
            if(checkFuncNested()){
                res.add(new SemanticError("Errore in "  + this.ID + ": Funzioni annidate"));

            }
        }

        return res;
    }

    @Override
    public Node typeCheck(ArrayList<SemanticError> typeErr) {
        Node bodyTmp = body.typeCheck(typeErr);
        if (this.type.getPointLevel() == 0) {
            if (!SimpLanPlusLib.isSubtype(this.type, bodyTmp))
                typeErr.add(new SemanticError("Tipi restituiti non corrispondenti <function = " +
                        this.type.getClass().getSimpleName() + ", body = " + bodyTmp.getClass().getSimpleName() + ">"));
        } else typeErr.add(new SemanticError("La funzione non può avere tipo puntatore"));
        return bodyTmp;
    }

    @Override
    public String toPrint(String indent) {
        return args.stream().map(arg -> arg.toPrint(indent + "\t")).collect(Collectors.joining("",
                indent + "DecFun: '" + ID + "' -> " + type.toPrint(""),
                body.toPrint(indent + "\t")));
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
        Environment effectEnv = new Environment();
        HashMap<String, STentry> hm = env.symTable.get(env.nestingLevel);
        STentry entry = new STentry(env.nestingLevel, 0);
        hm.put(ID, entry);
        effectEnv.nestingLevel++;
        HashMap<String, STentry> hmFun = new HashMap<>();
        effectEnv.symTable.add(effectEnv.nestingLevel, hmFun);

        int getFunEnv = env.nestingLevel;
        while (getFunEnv >= 0) {
            env.symTable.get(getFunEnv).entrySet().stream().filter(funEntry ->
                    funEntry.getValue().getType() instanceof ArrowTypeNode &&
                            !hmFun.containsKey(funEntry.getKey())).forEach(funEntry ->
                    hmFun.put(funEntry.getKey(), funEntry.getValue()));
            getFunEnv--;
        }
        entry.addType(new ArrowTypeNode(args, this.type));
        hmFun.put(ID, entry);
        effectEnv.nestingLevel++;
        HashMap<String, STentry> hmn = new HashMap<>();
        effectEnv.symTable.add(hmn);
        int argOffset = 1;
        for (ArgNode arg : args)
            hmn.put(arg.getId(), arg.getType() instanceof IntTypeNode ?
                    new STentry(effectEnv.nestingLevel, new IntTypeNode(arg.getType().getPointLevel(),
                            arg.getType().getStatus()), argOffset++) :
                    new STentry(effectEnv.nestingLevel, new BoolTypeNode(arg.getType().getPointLevel(),
                            arg.getType().getStatus()), argOffset++));
        // Inizia fixPoint
        ArrayList<ArgNode> sigma1 = args;
        /*
         * Poiché fixPoint termina con un ciclo aggiuntivo, utilizziamo un elenco ausiliario di errori che verrà
         * aggiunto alla raccolta completa di res di errori semantici solo dopo che fixPoint è terminato.
         * In questo modo evitiamo di segnalare errori due volte
         */
        args.forEach(arg ->
                effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().setStatus(Status.READWRITE));

        // READWRITE è lo stato iniziale per tutti i parametri formali
        boolean checkFixPoint;
        ArrayList<SemanticError> fixPointErrors;
        do {
            checkFixPoint = false;
            ArrayList<ArgNode> sigma0 = new ArrayList<>();
            // Salva sigma1 in sigma0 per fare un confronto dopo il ciclo fixPoint
            for (ArgNode argNode : sigma1)
                if (argNode.getType() instanceof IntTypeNode)
                    sigma0.add(new ArgNode(argNode.getId(), new IntTypeNode(argNode.getPointLevel(),
                            argNode.getType().getStatus())));
                else if (argNode.getType() instanceof BoolTypeNode)
                    sigma0.add(new ArgNode(argNode.getId(), new BoolTypeNode(argNode.getPointLevel(),
                            argNode.getType().getStatus())));
            // Con body checkEffects, lo stato dei parametri formali nella tabella dei simboli cambia
            fixPointErrors = new ArrayList<>(body.checkEffects(effectEnv));
            // Scambia lo stato delle variabili locali con args se le variabili locali sono parametri formali
            args.forEach(arg -> {
                Status localVar = effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().getStatus();
                effectEnv.symTable.get(effectEnv.nestingLevel).get(arg.getId()).getType().setStatus(Status.READWRITE);
                ((ArrowTypeNode) effectEnv.symTable.get(effectEnv.nestingLevel - 1).get(this.ID).getType())
                        .getArgList().stream().filter(arrowArg ->
                                effectEnv.symTable.get(effectEnv.nestingLevel).containsKey(arrowArg.getId()) &&
                                        arrowArg.getId().equals(arg.getId())).forEach(arrowArg ->
                                arrowArg.getType().setStatus(localVar));
            });
            // Ora salviamo gli effetti applicati dalla funzione body in sigma1
            sigma1 = new ArrayList<>();
            for (ArgNode a : ((ArrowTypeNode) effectEnv.symTable.get(effectEnv.nestingLevel - 1)
                    .get(this.ID).getType()).getArgList())
                sigma1.add(new ArgNode(a.getId(), a.getType()));
            // Confronta sigma0 e sigma1 per determinare la condizione di arresto dell'algoritmo fixPoint
            for (int idx = 0; idx < sigma0.size(); idx++)
                if (sigma0.get(idx).getType().getStatus() != sigma1.get(idx).getType().getStatus()) {
                    checkFixPoint = true;
                    break;
                }
        } while (checkFixPoint);
        // Fine fixPoint
        entry.addType(new ArrowTypeNode(sigma1, this.type));
        effectEnv.symTable.remove(effectEnv.nestingLevel);
        effectEnv.nestingLevel--;
        effectEnv.symTable.add(effectEnv.nestingLevel, hm);
        return new ArrayList<>(fixPointErrors);
    }

    @Override
    public void setStatus(Status status) {
    }

    @Override
    public String codeGeneration(CGenEnv env) {
        /*
         * La prima Label viene utilizzata per saltare l'esecuzione del codice quando l'interprete legge il bytecode
         * La seconda Label viene utilizzata all'interno del nodo di ritorno.
         * Una volta trovato un nodo di ritorno durante l'esecuzione, saltiamo alla fine della generazione del codice
         * del corpo della funzione per gestire correttamente stack e registri
         */
        Label label = new Label(), labelReturn = new Label();
        env.incrementNestingLevel();
        env.setLabel(labelReturn.getLabel());
        // Conta i pop dallo stack per le variabili locali della funzione
        String popLocal = body.getDecList().stream().filter(node ->
                !(node instanceof DecFunNode)).map(node -> "pop\n").collect(Collectors.joining());
        String builder = "b " + label.getLabel() + "\n__" + this.ID + funUniqueLabel +
                "_:\ncal\npush $al\ncfp\npush $ra\n" + this.body.codeGeneration(env) +
                /*
                 * Quando troviamo un nodo di ritorno nella generazione del codice del corpo, saltiamo qui.
                 * Restituisce l'esecuzione all'istruzione dopo la chiamata della funzione,
                 * ripristina il vecchio puntatore del frame e aggiungi il valore di ritorno a $a0
                 */
                labelReturn.getLabel() + ":\n" + popLocal + "lw $a0 $sp\nsra\npop\nlw $a0 $sp\nsfp\npop\n" +
                // Conta i pop dallo stack per gli argomenti delle funzioni
                "pop\n".repeat(args.size()) + "lrv\njr\n" + label.getLabel() + ":\n";
        env.decrementNestingLevel();
        env.removeLabel();
        return builder;
    }
}

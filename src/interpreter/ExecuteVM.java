package interpreter;

import parser.SVM.SVMParser;

import java.util.ArrayList;

/**
 * Classe interprete per il bytecode prodotto con la codeGeneration.
 * Qui vengono aggiunti i registri $a0, $t0, $rv e $al, poi vengono eseguite le operazioni
 * che si occupano di caricare/scaricare il registro $a0
 */
public class ExecuteVM {
    public static final int CODESIZE = 10000, MEMSIZE = 10000;
    private final int[] code, memory = new int[MEMSIZE];
    private int ip = 0, sp = MEMSIZE, hp = -1, fp = MEMSIZE - 1, a0, t0, ra, rv, al;
    public static ArrayList<String> error = new ArrayList<>();

    public ExecuteVM(int[] code) {
        this.code = code;
    }

    public void cpu() {
        while (true) if (hp + 1 >= sp) {
            error.add("\n\tErrore: memoria insufficiente");
            System.out.println("\n\tErrore: memoria insufficiente");
            return;
        } else {
            int bytecode = code[ip++], v1, v2, v3, address;
            switch (bytecode) {
                case SVMParser.PUSH:
                    push(getRegister(code[ip++]));
                    break;
                case SVMParser.POP:
                    pop();
                    break;
                case SVMParser.ADD:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    setRegister(code[ip++], v1 + v2);
                    break;
                case SVMParser.ADDI:
                    setRegister(code[ip], getRegister(code[ip++]) + code[ip++]);
                    break;
                case SVMParser.SUB:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    setRegister(code[ip++], v1 - v2);
                    break;
                case SVMParser.MULT:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    setRegister(code[ip++], v1 * v2);
                    break;
                case SVMParser.DIV:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    setRegister(code[ip++], v1 / v2);
                    break;
                case SVMParser.STOREW:
                    v1 = getRegister(code[ip++]);
                    memory[getRegister(code[ip++])] = v1;
                    break;
                case SVMParser.STOREI:
                    v1 = code[ip++];
                    memory[getRegister(code[ip++])] = v1;
                    break;
                case SVMParser.LOADW:
                    // Controlla se l'indirizzo dell'oggetto in cui prendiamo la label del metodo è un valore nullo
                    if (sp < 10000 && memory[sp] == -10000) {
                        error.add("\n\tErrore: Null Pointer Exception");
                        System.out.println("\n\tErrore: Null Pointer Exception");
                        return;
                    }
                    setRegister(code[ip++], memory[getRegister(code[ip++])]);
                    break;
                case SVMParser.LOADI:
                    setRegister(code[ip++], code[ip++]);
                    break;
                case SVMParser.LABEL:
                    break;
                case SVMParser.BRANCH:
                    address = code[ip];
                    ip = address;
                    break;
                case SVMParser.BRANCHEQ:
                    address = code[ip++];
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    if (v2 == v1) ip = address;
                    break;
                case SVMParser.BRANCHLESSEQ:
                    address = code[ip++];
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    if (v2 > v1) ip = address;
                    break;
                case SVMParser.LESS:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    v3 = v1 < v2 ? 1 : 0;
                    setRegister(code[ip++], v3);
                    break;
                case SVMParser.LESSEQ:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    v3 = v1 <= v2 ? 1 : 0;
                    setRegister(code[ip++], v3);
                    break;
                case SVMParser.EQ:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    v3 = v1 == v2 ? 1 : 0;
                    setRegister(code[ip++], v3);
                    break;
                case SVMParser.NEQ:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    v3 = v1 != v2 ? 1 : 0;
                    setRegister(code[ip++], v3);
                    break;
                case SVMParser.AND:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    v3 = v1 + v2 == 2 ? 1 : 0;
                    setRegister(code[ip++], v3);
                    break;
                case SVMParser.OR:
                    v1 = getRegister(code[ip++]);
                    v2 = getRegister(code[ip++]);
                    v3 = v1 + v2 != 0 ? 1 : 0;
                    setRegister(code[ip++], v3);
                    break;
                case SVMParser.JR:
                    ip = ra;
                    break;
                case SVMParser.JAL:
                    address = code[ip++];
                    ra = ip;
                    ip = address;
                    break;
                case SVMParser.LOADRA:
                    a0 = ra;
                    break;
                case SVMParser.STORERA:
                    ra = a0;
                    break;
                case SVMParser.LOADRV:
                    a0 = rv;
                    break;
                case SVMParser.STORERV:
                    rv = a0;
                    break;
                case SVMParser.LOADFP:
                    a0 = fp;
                    break;
                case SVMParser.STOREFP:
                    fp = a0;
                    break;
                case SVMParser.COPYFP:
                    fp = sp;
                    break;
                case SVMParser.LOADAL:
                    a0 = al;
                    break;
                case SVMParser.STOREAL:
                    al = a0;
                    break;
                case SVMParser.COPYAL:
                    al = fp;
                    break;
                case SVMParser.LOADHP:
                    a0 = hp;
                    break;
                case SVMParser.STOREHP:
                    hp = a0;
                    break;
                case SVMParser.PRINT:
                    int print = getRegister(code[ip++]);
                    error.add("\t" + print);
                    System.out.println("\t" + print);
                    break;
                case SVMParser.HALT: // stampa il risultato
                    error.add("\nUscita: nessun errore!\nAnalisi completata!");
                    System.out.println("\nUscita: nessun errore!\nAnalisi completata!");
                    return;
                default:
                    error.add("\nUscita: nessun errore");
                    System.out.println("\nUscita: nessun errore\n");
            }
        }
    }

    private void setRegister(int token, int val) {
        switch (token) {
            case SVMParser.A0:
                this.a0 = val;
                break;
            case SVMParser.T0:
                this.t0 = val;
                break;
            case SVMParser.RA:
                this.ra = val;
                break;
            case SVMParser.SP:
                this.sp = val;
                break;
            case SVMParser.FP:
                this.fp = val;
                break;
            case SVMParser.AL:
                this.al = val;
                break;
            case SVMParser.HP:
                this.hp = val;
                break;
            case SVMParser.RV:
                this.rv = val;
                break;
            default:
                break;
        }
    }

    private int getRegister(int token) {
        switch (token) {
            case SVMParser.A0:
                return this.a0;
            case SVMParser.T0:
                return this.t0;
            case SVMParser.RA:
                return this.ra;
            case SVMParser.SP:
                return this.sp;
            case SVMParser.FP:
                return this.fp;
            case SVMParser.AL:
                return this.al;
            case SVMParser.HP:
                return this.hp;
            case SVMParser.RV:
                return this.rv;
            default:
                return -1;
        }
    }

    private void pop() {
        sp++;
    }

    private void push(int v) {
        memory[--sp] = v;
    }
}
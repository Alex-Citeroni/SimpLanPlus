package ast;

// Questa è una voce della tabella dei simboli. Verrà inserito nelle Hash Maps durante la generazione degli environment.
public class STentry {
    private final int nl, offset;
    private Node type;

    public STentry(int n, int os) {
        this.nl = n;
        this.offset = os;
    }

    public STentry(int n, Node t, int os) {
        this.nl = n;
        this.type = t;
        this.offset = os;
    }

    public void addType(Node t) {
        this.type = t;
    }

    public Node getType() {
        return this.type;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getNestingLevel() {
        return this.nl;
    }

    public String toPrint(String s) {
        return s + "STentry: nestingLevel " + this.nl + "\n" + s +
                "STentry: type\n" + this.type.toPrint(s + "  ") + s +
                "STentry: offset " + this.offset + "\n";
    }
}  
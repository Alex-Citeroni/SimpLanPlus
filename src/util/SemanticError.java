package util;

// Classe che gestisce i messaggi di errore semantici
public class SemanticError {
    public final String msg;

    public SemanticError(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
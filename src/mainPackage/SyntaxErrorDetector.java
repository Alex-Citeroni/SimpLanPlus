package mainPackage;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.stream.Collectors;

// Classe utilizzate per gestire gli errori di sintassi
public class SyntaxErrorDetector extends BaseErrorListener {
    ArrayList<String> errors;

    public SyntaxErrorDetector() {
        this.errors = new ArrayList<>();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rec, Object offSymbol, int riga, int posChar, String mess, RecognitionException e) {
        this.errors.add("Errore in linea " + riga + ", carattere " + posChar + ": " + mess);
    }

    @Override
    public String toString() {
        return this.errors.stream().map(event -> event + "\n").collect(Collectors.joining());
    }
}
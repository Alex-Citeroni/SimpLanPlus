package mainPackage;

import ast.node.declaration.DecVarNode;
import interpreter.ExecuteVM;
import ast.Node;
import ast.SVMVisitorImpl;
import ast.SimpLanPlusVisitorImpl;
import org.antlr.v4.runtime.CharStream;
import parser.SVM.SVMLexer;
import parser.SVM.SVMParser;
import parser.SimpLanPlus.SimpLanPlusLexer;
import parser.SimpLanPlus.SimpLanPlusParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

// Qui viene dato inizio al programma
public class Main {
    public static void main(String[] args) throws Exception {
        String fileName = "test.simplanplus";
        if (args.length > 0) fileName = args[0];
        CharStream stream;
        try {
            stream = CharStreams.fromFileName(fileName);
        } catch (Exception e) {
            System.out.println("Qualcosa è andato storto durante l'accesso al file. " +
                    "Si prega di controllare il nome del file.");
            return;
        }
        SimpLanPlusLexer lexer = new SimpLanPlusLexer(stream);
        SyntaxErrorDetector handler = new SyntaxErrorDetector();
        lexer.removeErrorListeners();
        lexer.addErrorListener(handler);
        SimpLanPlusParser parser = new SimpLanPlusParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(handler);
        System.out.println("\nAnalisi ...");
        Node ast = new SimpLanPlusVisitorImpl().visit(parser.block());
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".log"));
        if (handler.errors.size() == 0 && !SimpLanPlusVisitorImpl.ritorno) {
            System.out.println("Controllo dell'ambito ok!\nControllo errori semantici ...");
            ArrayList<SemanticError> err = ast.checkSemantics(new Environment());
            if (err != null && err.size() > 0) {
                System.out.println("\tHai " + err.size() + " errori semantici:");
                writer.write("Hai " + err.size() + " errori semantici:" + "\n");
                for (SemanticError semanticError : err) {
                    System.out.println(semanticError);
                    writer.write("\t" + semanticError + "\n");
                }
                writer.close();
                return;
            }
            System.out.println("Nessun errore semantico.\nControllo errori di tipo ...");
            ArrayList<SemanticError> typeErr = new ArrayList<>();
            ast.typeCheck(typeErr); // controllo del tipo dal basso verso l'alto
            if (typeErr.size() > 0) {
                System.out.println("Hai " + typeErr.size() + " errori di tipo:");
                writer.write("Hai " + typeErr.size() + " errori di tipo:" + "\n");
                for (SemanticError e : typeErr) {
                    System.out.println("\t" + e);
                    writer.write("\t" + e + "\n");
                }
                writer.close();
                return;
            } else {
                System.out.println("Controllo del tipo ok!\nControllo degli effetti ...");
                ArrayList<SemanticError> effectErr = ast.checkEffects(new Environment());
                if (effectErr.size() > 0) {
                    System.out.println("Hai " + effectErr.size() + " errori di effetto:");
                    writer.write("Hai " + effectErr.size() + " errori di effetto:" + "\n");
                    for (SemanticError e : effectErr) {
                        System.out.println("\t" + e);
                        writer.write("\t" + e + "\n");
                    }
                } else if (DecVarNode.declarations.size() > 0) {
                    System.out.println("Hai " + DecVarNode.declarations.size() + " errori di utilizzo:");
                    writer.write("Hai " + DecVarNode.declarations.size() + " errori di utilizzo:" + "\n");
                    for (String s : DecVarNode.declarations) {
                        System.out.println("\tErrore: la variabile '" + s + "' è stata dichiarata, ma non viene utilizzata");
                        writer.write("\tErrore: la variabile '" + s + "' è stata dichiarata, ma non viene utilizzata\n");
                    }
                } else {
                    System.out.println("Controllo degli effetti ok!\n------------------");
                    // Visualizzazione AST
                    System.out.println("Albero sintattico:\n" + ast.toPrint("") + "------------------");
                    writer.write("Nessun errore!\nAlbero sintattico:\n\n" + ast.toPrint(""));
                    // CODE GENERATION
                    BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".asm"));
                    out.write(ast.codeGeneration(new CGenEnv()));
                    out.close();
                    System.out.println("Codice generato! Assemblaggio ed esecuzione del codice generato.");
                    SVMLexer lexerASM = new SVMLexer(CharStreams.fromFileName(fileName + ".asm"));
                    SVMParser parserASM = new SVMParser(new CommonTokenStream(lexerASM));
                    SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
                    visitorSVM.visit(parserASM.assembly());
                    System.out.println("Hai " + lexerASM.lexicalErrors + " errori lessicali e " +
                            parserASM.getNumberOfSyntaxErrors() + " errori di sintassi.");
                    writer.write("\nNella Code generation hai " + lexerASM.lexicalErrors +
                            " errori lessicali e " + parserASM.getNumberOfSyntaxErrors() + " errori di sintassi.\n");
                    if (lexerASM.lexicalErrors > 0 || parserASM.getNumberOfSyntaxErrors() > 0) System.exit(1);
                    System.out.println("\nAvvio della macchina virtuale...\nRisultato esecuzione:\n");
                    writer.write("\nAvvio della macchina virtuale...\nRisultato esecuzione:\n\n");
                    new ExecuteVM(visitorSVM.code).cpu();
                    for (int i = 0; i < ExecuteVM.error.size(); i++) writer.write(ExecuteVM.error.get(i) + "\n");
                    writer.close();
                }
            }
        } else {
            System.out.println("Sono stati trovati degli errori:" + handler);
            // Messaggio di errore presente quando ci sono return indesiderati all'interno di una funzione o di un blocco ITE
            if (SimpLanPlusVisitorImpl.ritorno) {
                System.out.println("\tIn una funzione sono presenti return multipli!");
                writer.write("\tIn una funzione sono presenti return multipli!");
            }
            writer.write(handler.toString());
        }
        writer.close();
    }
}

// Generated from C:/Users/velas/OneDrive/Desktop/MAGISTRALE/SecondoSemestre/Cosimo Laneve/SimpLanPlusCIProject-master/src\SVM.g4 by ANTLR 4.10.1
package parser.SVM;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link SVMVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public class SVMBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements SVMVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitAssembly(SVMParser.AssemblyContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitInstruction(SVMParser.InstructionContext ctx) {
        return visitChildren(ctx);
    }
}
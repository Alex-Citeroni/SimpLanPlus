package ast;

import java.util.ArrayList;
import java.util.stream.Collectors;

import ast.node.ArgNode;
import ast.node.IdNode;
import ast.node.statement.*;
import ast.node.declaration.*;
import ast.node.exp.*;
import ast.node.type.BoolTypeNode;
import ast.node.type.IntTypeNode;
import ast.node.type.VoidTypeNode;
import parser.SimpLanPlus.SimpLanPlusBaseVisitor;
import parser.SimpLanPlus.SimpLanPlusParser.*;
import util.Status;

// In questa classe viene modificato lo standard visit del AST in modo che sia possibile creare nodi del pacchetto ast.
public class SimpLanPlusVisitorImpl extends SimpLanPlusBaseVisitor<Node> {
    // annidata controlla se ci sono funzioni annidate; ritorno controlla se ci sono più return all'interno di un funzione
    public static boolean ritorno = false;
    // ret viene incrementato quando si ha più di un return
    private int ret = 0;

    @Override
    public BlockNode visitBlock(BlockContext ctx) {
        ArrayList<Node> declarations = new ArrayList<>(), statements = new ArrayList<>();
        for (DeclarationContext dc : ctx.declaration()) declarations.add(visit(dc));
        for (StatementContext st : ctx.statement()) statements.add(visit(st));
        ret = 0;
        return new BlockNode(declarations, statements);
    }

    @Override
    public Node visitStatement(StatementContext ctx) {
        return ctx.assignment() != null ?
                visit(ctx.assignment()) : ctx.print() != null ?
                visit(ctx.print()) : ctx.ret() != null ?
                visit(ctx.ret()) : ctx.ite() != null ?
                visit(ctx.ite()) : ctx.call() != null ?
                visit(ctx.call()) : ctx.block() != null ?
                visit(ctx.block()) : null;
    }

    @Override
    public DecVarNode visitDecVar(DecVarContext ctx) {
        return new DecVarNode(ctx.ID().getText(), visit(ctx.type()), ctx.exp() != null ? visit(ctx.exp()) : null);
    }

    @Override
    public DecFunNode visitDecFun(DecFunContext ctx) {
        BlockNode block = visitBlock(ctx.block());
        // Imposta flag per distinguere tra blocco inline e blocco funzione
        block.setBlockFunction();
        DecFunNode res = new DecFunNode(ctx.ID().getText(), ctx.type() != null ? visit(ctx.type()) :
                new VoidTypeNode(Status.DECLARED), block);
        // Carica parametri formali nel nuovo DecFunNode
        for (ArgContext vc : ctx.arg()) res.addArg(new ArgNode(vc.ID().getText(), visit(vc.type())));
        
        return res;
    }

    /*
     * Possiamo avere sia puntatori interi che booleani se il nodo contiene il testo 'int' dobbiamo eliminare tre
     * caratteri in modo che la lunghezza della stringa risultante sia il livello del punto.
     */
    @Override
    public Node visitType(TypeContext ctx) {
        return ctx.getText().contains("int") ?
                new IntTypeNode(ctx.getText().substring(0, ctx.getText().length() - 3).length(), Status.DECLARED) :
                ctx.getText().contains("bool") ?
                        new BoolTypeNode(ctx.getText().substring(0, ctx.getText().length() - 4).length(), Status.DECLARED)
                        : null;
    }

    @Override
    public AsgNode visitAssignment(AssignmentContext ctx) {
        return new AsgNode(new IdNode(ctx.ID().getText(), 0), visit(ctx.exp()));
    }

    @Override
    public PrintNode visitPrint(PrintContext ctx) {
        return new PrintNode(visit(ctx.exp()));
    }

    // Le funzioni vuote devono essere restituite; alla fine quindi exp potrebbe essere nullo.
    @Override
    public RetNode visitRet(RetContext ctx) {
        if (ret > 0) ritorno = true;
        ret++;
        return new RetNode(ctx.exp() != null ? visit(ctx.exp()) : null);
    }

    @Override
    public BaseExpNode visitBaseExp(BaseExpContext ctx) {
        return new BaseExpNode(visit(ctx.exp()));
    }

    @Override
    public IteNode visitIte(IteContext ctx) {
        Node thenExp = visit(ctx.statement(0));
        /*
         * Imposta il flag in modo che durante la generazione del codice sia possibile restituire
         * correttamente le istruzioni if-then-else annidate in funzioni e blocchi inline.
         */
        if (thenExp instanceof BlockNode) ((BlockNode) thenExp).setBlockIte();
        Node elseExp = null;
        if (ctx.statement().size() > 1) {
            elseExp = visit(ctx.statement(1));
            if (elseExp instanceof BlockNode) ((BlockNode) elseExp).setBlockIte();
        }
        ret = 0;
        return new IteNode(visit(ctx.exp()), thenExp, elseExp);
    }

    // Carica i parametri effettivi nel nuovo CallNode
    @Override
    public CallNode visitCall(CallContext ctx) {
        return new CallNode(ctx.ID().getText(),
                ctx.exp().stream().map(this::visit).collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public NegExpNode visitNegExp(NegExpContext ctx) {
        return new NegExpNode(visit(ctx.exp()));
    }

    @Override
    public NotExpNode visitNotExp(NotExpContext ctx) {
        return new NotExpNode(visit(ctx.exp()));
    }

    @Override
    public DerExpNode visitDerExp(DerExpContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText(), 0);
        id.setRightHandSide();
        return new DerExpNode(id);
    }

    @Override
    public BinExpNode visitBinExp(BinExpContext ctx) {
        return new BinExpNode(visit(ctx.left), ctx.op, visit(ctx.right));
    }

    @Override
    public CallExpNode visitCallExp(CallExpContext ctx) {
        CallNode call = visitCall(ctx.call());
        call.setCallExp();
        return new CallExpNode(call);
    }

    @Override
    public BoolNode visitBoolExp(BoolExpContext ctx) {
        return new BoolNode(Boolean.parseBoolean(ctx.getText()));
    }

    @Override
    public IntNode visitValExp(ValExpContext ctx) {
        return new IntNode(Integer.parseInt(ctx.NUMBER().getText()));
    }
}

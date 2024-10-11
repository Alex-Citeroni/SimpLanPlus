// Generated from C:/Users/velas/OneDrive/Desktop/MAGISTRALE/SecondoSemestre/Cosimo Laneve/SimpLanPlusCIProject-master/src\SVM.g4 by ANTLR 4.10.1
package parser.SVM;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            PUSH = 1, POP = 2, ADD = 3, ADDI = 4, SUB = 5, MULT = 6, DIV = 7, STOREW = 8, STOREI = 9,
            LOADW = 10, LOADI = 11, BRANCH = 12, BRANCHEQ = 13, BRANCHLESSEQ = 14, LESS = 15,
            LESSEQ = 16, EQ = 17, NEQ = 18, AND = 19, OR = 20, JR = 21, JAL = 22, LOADRA = 23, STORERA = 24,
            LOADRV = 25, STORERV = 26, LOADFP = 27, STOREFP = 28, COPYFP = 29, LOADAL = 30, STOREAL = 31,
            COPYAL = 32, LOADHP = 33, STOREHP = 34, PRINT = 35, HALT = 36, COL = 37, NUMBER = 38,
            LABEL = 39, REGISTER = 40, A0 = 41, T0 = 42, SP = 43, RA = 44, FP = 45, AL = 46, HP = 47,
            RV = 48, WHITESP = 49, ERR = 50;
    public static final int
            RULE_assembly = 0, RULE_instruction = 1;

    private static String[] makeRuleNames() {
        return new String[]{
                "assembly", "instruction"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
                null, "'push'", "'pop'", "'add'", "'addi'", "'sub'", "'mult'", "'div'",
                "'sw'", "'si'", "'lw'", "'li'", "'b'", "'beq'", "'bleq'", "'less'", "'leq'",
                "'eq'", "'neq'", "'and'", "'or'", "'jr'", "'jal'", "'lra'", "'sra'",
                "'lrv'", "'srv'", "'lfp'", "'sfp'", "'cfp'", "'lal'", "'sal'", "'cal'",
                "'lhp'", "'shp'", "'print'", "'halt'", "':'", null, null, null, "'$a0'",
                "'$t0'", "'$sp'", "'$ra'", "'$fp'", "'$al'", "'$hp'", "'$rv'"
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "PUSH", "POP", "ADD", "ADDI", "SUB", "MULT", "DIV", "STOREW", "STOREI",
                "LOADW", "LOADI", "BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "LESS", "LESSEQ",
                "EQ", "NEQ", "AND", "OR", "JR", "JAL", "LOADRA", "STORERA", "LOADRV",
                "STORERV", "LOADFP", "STOREFP", "COPYFP", "LOADAL", "STOREAL", "COPYAL",
                "LOADHP", "STOREHP", "PRINT", "HALT", "COL", "NUMBER", "LABEL", "REGISTER",
                "A0", "T0", "SP", "RA", "FP", "AL", "HP", "RV", "WHITESP", "ERR"
        };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "SVM.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public SVMParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class AssemblyContext extends ParserRuleContext {
        public List<InstructionContext> instruction() {
            return getRuleContexts(InstructionContext.class);
        }

        public InstructionContext instruction(int i) {
            return getRuleContext(InstructionContext.class, i);
        }

        public AssemblyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assembly;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SVMListener) ((SVMListener) listener).enterAssembly(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SVMListener) ((SVMListener) listener).exitAssembly(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SVMVisitor) return ((SVMVisitor<? extends T>) visitor).visitAssembly(this);
            else return visitor.visitChildren(this);
        }
    }

    public final AssemblyContext assembly() throws RecognitionException {
        AssemblyContext _localctx = new AssemblyContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_assembly);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(7);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << ADDI) | (1L << SUB) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << STOREI) | (1L << LOADW) | (1L << LOADI) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESSEQ) | (1L << LESS) | (1L << LESSEQ) | (1L << EQ) | (1L << NEQ) | (1L << AND) | (1L << OR) | (1L << JR) | (1L << JAL) | (1L << LOADRA) | (1L << STORERA) | (1L << LOADRV) | (1L << STORERV) | (1L << LOADFP) | (1L << STOREFP) | (1L << COPYFP) | (1L << LOADAL) | (1L << STOREAL) | (1L << COPYAL) | (1L << LOADHP) | (1L << STOREHP) | (1L << PRINT) | (1L << HALT) | (1L << LABEL))) != 0)) {
                    {
                        {
                            setState(4);
                            instruction();
                        }
                    }
                    setState(9);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class InstructionContext extends ParserRuleContext {
        public Token r;
        public Token r1;
        public Token r2;
        public Token res;
        public Token val;
        public Token dest;
        public Token source;
        public Token l;
        public Token e1;
        public Token e2;

        public TerminalNode PUSH() {
            return getToken(SVMParser.PUSH, 0);
        }

        public TerminalNode POP() {
            return getToken(SVMParser.POP, 0);
        }

        public TerminalNode ADD() {
            return getToken(SVMParser.ADD, 0);
        }

        public TerminalNode ADDI() {
            return getToken(SVMParser.ADDI, 0);
        }

        public TerminalNode SUB() {
            return getToken(SVMParser.SUB, 0);
        }

        public TerminalNode MULT() {
            return getToken(SVMParser.MULT, 0);
        }

        public TerminalNode DIV() {
            return getToken(SVMParser.DIV, 0);
        }

        public TerminalNode STOREW() {
            return getToken(SVMParser.STOREW, 0);
        }

        public TerminalNode STOREI() {
            return getToken(SVMParser.STOREI, 0);
        }

        public TerminalNode LOADW() {
            return getToken(SVMParser.LOADW, 0);
        }

        public TerminalNode LOADI() {
            return getToken(SVMParser.LOADI, 0);
        }

        public TerminalNode COL() {
            return getToken(SVMParser.COL, 0);
        }

        public TerminalNode BRANCH() {
            return getToken(SVMParser.BRANCH, 0);
        }

        public TerminalNode BRANCHEQ() {
            return getToken(SVMParser.BRANCHEQ, 0);
        }

        public TerminalNode BRANCHLESSEQ() {
            return getToken(SVMParser.BRANCHLESSEQ, 0);
        }

        public TerminalNode LESS() {
            return getToken(SVMParser.LESS, 0);
        }

        public TerminalNode LESSEQ() {
            return getToken(SVMParser.LESSEQ, 0);
        }

        public TerminalNode EQ() {
            return getToken(SVMParser.EQ, 0);
        }

        public TerminalNode NEQ() {
            return getToken(SVMParser.NEQ, 0);
        }

        public TerminalNode AND() {
            return getToken(SVMParser.AND, 0);
        }

        public TerminalNode OR() {
            return getToken(SVMParser.OR, 0);
        }

        public TerminalNode JR() {
            return getToken(SVMParser.JR, 0);
        }

        public TerminalNode JAL() {
            return getToken(SVMParser.JAL, 0);
        }

        public TerminalNode LOADRA() {
            return getToken(SVMParser.LOADRA, 0);
        }

        public TerminalNode STORERA() {
            return getToken(SVMParser.STORERA, 0);
        }

        public TerminalNode LOADRV() {
            return getToken(SVMParser.LOADRV, 0);
        }

        public TerminalNode STORERV() {
            return getToken(SVMParser.STORERV, 0);
        }

        public TerminalNode LOADFP() {
            return getToken(SVMParser.LOADFP, 0);
        }

        public TerminalNode STOREFP() {
            return getToken(SVMParser.STOREFP, 0);
        }

        public TerminalNode COPYFP() {
            return getToken(SVMParser.COPYFP, 0);
        }

        public TerminalNode STOREAL() {
            return getToken(SVMParser.STOREAL, 0);
        }

        public TerminalNode LOADAL() {
            return getToken(SVMParser.LOADAL, 0);
        }

        public TerminalNode COPYAL() {
            return getToken(SVMParser.COPYAL, 0);
        }

        public TerminalNode LOADHP() {
            return getToken(SVMParser.LOADHP, 0);
        }

        public TerminalNode STOREHP() {
            return getToken(SVMParser.STOREHP, 0);
        }

        public TerminalNode PRINT() {
            return getToken(SVMParser.PRINT, 0);
        }

        public TerminalNode HALT() {
            return getToken(SVMParser.HALT, 0);
        }

        public List<TerminalNode> REGISTER() {
            return getTokens(SVMParser.REGISTER);
        }

        public TerminalNode REGISTER(int i) {
            return getToken(SVMParser.REGISTER, i);
        }

        public TerminalNode NUMBER() {
            return getToken(SVMParser.NUMBER, 0);
        }

        public TerminalNode LABEL() {
            return getToken(SVMParser.LABEL, 0);
        }

        public InstructionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_instruction;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof SVMListener) ((SVMListener) listener).enterInstruction(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof SVMListener) ((SVMListener) listener).exitInstruction(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SVMVisitor) return ((SVMVisitor<? extends T>) visitor).visitInstruction(this);
            else return visitor.visitChildren(this);
        }
    }

    public final InstructionContext instruction() throws RecognitionException {
        InstructionContext _localctx = new InstructionContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_instruction);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(98);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case PUSH: {
                        setState(10);
                        match(PUSH);
                        setState(11);
                        ((InstructionContext) _localctx).r = match(REGISTER);
                    }
                    break;
                    case POP: {
                        setState(12);
                        match(POP);
                    }
                    break;
                    case ADD: {
                        setState(13);
                        match(ADD);
                        setState(14);
                        ((InstructionContext) _localctx).r1 = match(REGISTER);
                        setState(15);
                        ((InstructionContext) _localctx).r2 = match(REGISTER);
                        setState(16);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case ADDI: {
                        setState(17);
                        match(ADDI);
                        setState(18);
                        ((InstructionContext) _localctx).r1 = match(REGISTER);
                        setState(19);
                        ((InstructionContext) _localctx).val = match(NUMBER);
                    }
                    break;
                    case SUB: {
                        setState(20);
                        match(SUB);
                        setState(21);
                        ((InstructionContext) _localctx).r1 = match(REGISTER);
                        setState(22);
                        ((InstructionContext) _localctx).r2 = match(REGISTER);
                        setState(23);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case MULT: {
                        setState(24);
                        match(MULT);
                        setState(25);
                        ((InstructionContext) _localctx).r1 = match(REGISTER);
                        setState(26);
                        ((InstructionContext) _localctx).r2 = match(REGISTER);
                        setState(27);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case DIV: {
                        setState(28);
                        match(DIV);
                        setState(29);
                        ((InstructionContext) _localctx).r1 = match(REGISTER);
                        setState(30);
                        ((InstructionContext) _localctx).r2 = match(REGISTER);
                        setState(31);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case STOREW: {
                        setState(32);
                        match(STOREW);
                        setState(33);
                        ((InstructionContext) _localctx).val = match(REGISTER);
                        setState(34);
                        ((InstructionContext) _localctx).dest = match(REGISTER);
                    }
                    break;
                    case STOREI: {
                        setState(35);
                        match(STOREI);
                        setState(36);
                        ((InstructionContext) _localctx).val = match(NUMBER);
                        setState(37);
                        ((InstructionContext) _localctx).dest = match(REGISTER);
                    }
                    break;
                    case LOADW: {
                        setState(38);
                        match(LOADW);
                        setState(39);
                        ((InstructionContext) _localctx).val = match(REGISTER);
                        setState(40);
                        ((InstructionContext) _localctx).source = match(REGISTER);
                    }
                    break;
                    case LOADI: {
                        setState(41);
                        match(LOADI);
                        setState(42);
                        ((InstructionContext) _localctx).val = match(REGISTER);
                        setState(43);
                        ((InstructionContext) _localctx).source = match(NUMBER);
                    }
                    break;
                    case LABEL: {
                        setState(44);
                        ((InstructionContext) _localctx).l = match(LABEL);
                        setState(45);
                        match(COL);
                    }
                    break;
                    case BRANCH: {
                        setState(46);
                        match(BRANCH);
                        setState(47);
                        ((InstructionContext) _localctx).l = match(LABEL);
                    }
                    break;
                    case BRANCHEQ: {
                        setState(48);
                        match(BRANCHEQ);
                        setState(49);
                        ((InstructionContext) _localctx).l = match(LABEL);
                        setState(50);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(51);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                    }
                    break;
                    case BRANCHLESSEQ: {
                        setState(52);
                        match(BRANCHLESSEQ);
                        setState(53);
                        ((InstructionContext) _localctx).l = match(LABEL);
                        setState(54);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(55);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                    }
                    break;
                    case LESS: {
                        setState(56);
                        match(LESS);
                        setState(57);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(58);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                        setState(59);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case LESSEQ: {
                        setState(60);
                        match(LESSEQ);
                        setState(61);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(62);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                        setState(63);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case EQ: {
                        setState(64);
                        match(EQ);
                        setState(65);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(66);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                        setState(67);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case NEQ: {
                        setState(68);
                        match(NEQ);
                        setState(69);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(70);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                        setState(71);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case AND: {
                        setState(72);
                        match(AND);
                        setState(73);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(74);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                        setState(75);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case OR: {
                        setState(76);
                        match(OR);
                        setState(77);
                        ((InstructionContext) _localctx).e1 = match(REGISTER);
                        setState(78);
                        ((InstructionContext) _localctx).e2 = match(REGISTER);
                        setState(79);
                        ((InstructionContext) _localctx).res = match(REGISTER);
                    }
                    break;
                    case JR: {
                        setState(80);
                        match(JR);
                    }
                    break;
                    case JAL: {
                        setState(81);
                        match(JAL);
                        setState(82);
                        ((InstructionContext) _localctx).l = match(LABEL);
                    }
                    break;
                    case LOADRA: {
                        setState(83);
                        match(LOADRA);
                    }
                    break;
                    case STORERA: {
                        setState(84);
                        match(STORERA);
                    }
                    break;
                    case LOADRV: {
                        setState(85);
                        match(LOADRV);
                    }
                    break;
                    case STORERV: {
                        setState(86);
                        match(STORERV);
                    }
                    break;
                    case LOADFP: {
                        setState(87);
                        match(LOADFP);
                    }
                    break;
                    case STOREFP: {
                        setState(88);
                        match(STOREFP);
                    }
                    break;
                    case COPYFP: {
                        setState(89);
                        match(COPYFP);
                    }
                    break;
                    case STOREAL: {
                        setState(90);
                        match(STOREAL);
                    }
                    break;
                    case LOADAL: {
                        setState(91);
                        match(LOADAL);
                    }
                    break;
                    case COPYAL: {
                        setState(92);
                        match(COPYAL);
                    }
                    break;
                    case LOADHP: {
                        setState(93);
                        match(LOADHP);
                    }
                    break;
                    case STOREHP: {
                        setState(94);
                        match(STOREHP);
                    }
                    break;
                    case PRINT: {
                        setState(95);
                        match(PRINT);
                        setState(96);
                        ((InstructionContext) _localctx).val = match(REGISTER);
                    }
                    break;
                    case HALT: {
                        setState(97);
                        match(HALT);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\u0004\u00012e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0001\u0000" +
                    "\u0005\u0000\u0006\b\u0000\n\u0000\f\u0000\t\t\u0000\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001" +
                    "\u0001\u0001\u0001\u0001\u0001\u0003\u0001c\b\u0001\u0001\u0001\u0000" +
                    "\u0000\u0002\u0000\u0002\u0000\u0000\u0087\u0000\u0007\u0001\u0000\u0000" +
                    "\u0000\u0002b\u0001\u0000\u0000\u0000\u0004\u0006\u0003\u0002\u0001\u0000" +
                    "\u0005\u0004\u0001\u0000\u0000\u0000\u0006\t\u0001\u0000\u0000\u0000\u0007" +
                    "\u0005\u0001\u0000\u0000\u0000\u0007\b\u0001\u0000\u0000\u0000\b\u0001" +
                    "\u0001\u0000\u0000\u0000\t\u0007\u0001\u0000\u0000\u0000\n\u000b\u0005" +
                    "\u0001\u0000\u0000\u000bc\u0005(\u0000\u0000\fc\u0005\u0002\u0000\u0000" +
                    "\r\u000e\u0005\u0003\u0000\u0000\u000e\u000f\u0005(\u0000\u0000\u000f" +
                    "\u0010\u0005(\u0000\u0000\u0010c\u0005(\u0000\u0000\u0011\u0012\u0005" +
                    "\u0004\u0000\u0000\u0012\u0013\u0005(\u0000\u0000\u0013c\u0005&\u0000" +
                    "\u0000\u0014\u0015\u0005\u0005\u0000\u0000\u0015\u0016\u0005(\u0000\u0000" +
                    "\u0016\u0017\u0005(\u0000\u0000\u0017c\u0005(\u0000\u0000\u0018\u0019" +
                    "\u0005\u0006\u0000\u0000\u0019\u001a\u0005(\u0000\u0000\u001a\u001b\u0005" +
                    "(\u0000\u0000\u001bc\u0005(\u0000\u0000\u001c\u001d\u0005\u0007\u0000" +
                    "\u0000\u001d\u001e\u0005(\u0000\u0000\u001e\u001f\u0005(\u0000\u0000\u001f" +
                    "c\u0005(\u0000\u0000 !\u0005\b\u0000\u0000!\"\u0005(\u0000\u0000\"c\u0005" +
                    "(\u0000\u0000#$\u0005\t\u0000\u0000$%\u0005&\u0000\u0000%c\u0005(\u0000" +
                    "\u0000&\'\u0005\n\u0000\u0000\'(\u0005(\u0000\u0000(c\u0005(\u0000\u0000" +
                    ")*\u0005\u000b\u0000\u0000*+\u0005(\u0000\u0000+c\u0005&\u0000\u0000," +
                    "-\u0005\'\u0000\u0000-c\u0005%\u0000\u0000./\u0005\f\u0000\u0000/c\u0005" +
                    "\'\u0000\u000001\u0005\r\u0000\u000012\u0005\'\u0000\u000023\u0005(\u0000" +
                    "\u00003c\u0005(\u0000\u000045\u0005\u000e\u0000\u000056\u0005\'\u0000" +
                    "\u000067\u0005(\u0000\u00007c\u0005(\u0000\u000089\u0005\u000f\u0000\u0000" +
                    "9:\u0005(\u0000\u0000:;\u0005(\u0000\u0000;c\u0005(\u0000\u0000<=\u0005" +
                    "\u0010\u0000\u0000=>\u0005(\u0000\u0000>?\u0005(\u0000\u0000?c\u0005(" +
                    "\u0000\u0000@A\u0005\u0011\u0000\u0000AB\u0005(\u0000\u0000BC\u0005(\u0000" +
                    "\u0000Cc\u0005(\u0000\u0000DE\u0005\u0012\u0000\u0000EF\u0005(\u0000\u0000" +
                    "FG\u0005(\u0000\u0000Gc\u0005(\u0000\u0000HI\u0005\u0013\u0000\u0000I" +
                    "J\u0005(\u0000\u0000JK\u0005(\u0000\u0000Kc\u0005(\u0000\u0000LM\u0005" +
                    "\u0014\u0000\u0000MN\u0005(\u0000\u0000NO\u0005(\u0000\u0000Oc\u0005(" +
                    "\u0000\u0000Pc\u0005\u0015\u0000\u0000QR\u0005\u0016\u0000\u0000Rc\u0005" +
                    "\'\u0000\u0000Sc\u0005\u0017\u0000\u0000Tc\u0005\u0018\u0000\u0000Uc\u0005" +
                    "\u0019\u0000\u0000Vc\u0005\u001a\u0000\u0000Wc\u0005\u001b\u0000\u0000" +
                    "Xc\u0005\u001c\u0000\u0000Yc\u0005\u001d\u0000\u0000Zc\u0005\u001f\u0000" +
                    "\u0000[c\u0005\u001e\u0000\u0000\\c\u0005 \u0000\u0000]c\u0005!\u0000" +
                    "\u0000^c\u0005\"\u0000\u0000_`\u0005#\u0000\u0000`c\u0005(\u0000\u0000" +
                    "ac\u0005$\u0000\u0000b\n\u0001\u0000\u0000\u0000b\f\u0001\u0000\u0000" +
                    "\u0000b\r\u0001\u0000\u0000\u0000b\u0011\u0001\u0000\u0000\u0000b\u0014" +
                    "\u0001\u0000\u0000\u0000b\u0018\u0001\u0000\u0000\u0000b\u001c\u0001\u0000" +
                    "\u0000\u0000b \u0001\u0000\u0000\u0000b#\u0001\u0000\u0000\u0000b&\u0001" +
                    "\u0000\u0000\u0000b)\u0001\u0000\u0000\u0000b,\u0001\u0000\u0000\u0000" +
                    "b.\u0001\u0000\u0000\u0000b0\u0001\u0000\u0000\u0000b4\u0001\u0000\u0000" +
                    "\u0000b8\u0001\u0000\u0000\u0000b<\u0001\u0000\u0000\u0000b@\u0001\u0000" +
                    "\u0000\u0000bD\u0001\u0000\u0000\u0000bH\u0001\u0000\u0000\u0000bL\u0001" +
                    "\u0000\u0000\u0000bP\u0001\u0000\u0000\u0000bQ\u0001\u0000\u0000\u0000" +
                    "bS\u0001\u0000\u0000\u0000bT\u0001\u0000\u0000\u0000bU\u0001\u0000\u0000" +
                    "\u0000bV\u0001\u0000\u0000\u0000bW\u0001\u0000\u0000\u0000bX\u0001\u0000" +
                    "\u0000\u0000bY\u0001\u0000\u0000\u0000bZ\u0001\u0000\u0000\u0000b[\u0001" +
                    "\u0000\u0000\u0000b\\\u0001\u0000\u0000\u0000b]\u0001\u0000\u0000\u0000" +
                    "b^\u0001\u0000\u0000\u0000b_\u0001\u0000\u0000\u0000ba\u0001\u0000\u0000" +
                    "\u0000c\u0003\u0001\u0000\u0000\u0000\u0002\u0007b";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
// Generated from C:/Users/velas/OneDrive/Desktop/MAGISTRALE/SecondoSemestre/Cosimo Laneve/SimpLanPlusCIProject-master/src\SVM.g4 by ANTLR 4.10.1
package parser.SVM;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMLexer extends Lexer {
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
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    private static String[] makeRuleNames() {
        return new String[]{
                "PUSH", "POP", "ADD", "ADDI", "SUB", "MULT", "DIV", "STOREW", "STOREI",
                "LOADW", "LOADI", "BRANCH", "BRANCHEQ", "BRANCHLESSEQ", "LESS", "LESSEQ",
                "EQ", "NEQ", "AND", "OR", "JR", "JAL", "LOADRA", "STORERA", "LOADRV",
                "STORERV", "LOADFP", "STOREFP", "COPYFP", "LOADAL", "STOREAL", "COPYAL",
                "LOADHP", "STOREHP", "PRINT", "HALT", "COL", "NUMBER", "LABEL", "REGISTER",
                "A0", "T0", "SP", "RA", "FP", "AL", "HP", "RV", "WHITESP", "ERR"
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


    public int lexicalErrors = 0;


    public SVMLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    @Override
    public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
        switch (ruleIndex) {
            case 49:
                ERR_action((RuleContext) _localctx, actionIndex);
                break;
        }
    }

    private void ERR_action(RuleContext _localctx, int actionIndex) {
        switch (actionIndex) {
            case 0:
                System.err.println("Invalid char: " + getText());
                lexicalErrors++;
                break;
        }
    }

    public static final String _serializedATN =
            "\u0004\u00002\u0144\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001" +
                    "\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004" +
                    "\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007" +
                    "\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b" +
                    "\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002" +
                    "\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002" +
                    "\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002" +
                    "\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002" +
                    "\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002" +
                    "\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002" +
                    "\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007" +
                    "!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007" +
                    "&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007" +
                    "+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007" +
                    "0\u00021\u00071\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001" +
                    "\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001" +
                    "\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001" +
                    "\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001" +
                    "\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001" +
                    "\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001" +
                    "\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001" +
                    "\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001" +
                    "\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001" +
                    "\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001" +
                    "\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001" +
                    "\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001" +
                    "\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001" +
                    "\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001" +
                    "\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001" +
                    "\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001" +
                    "\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001" +
                    "\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001" +
                    "\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001" +
                    "\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001" +
                    " \u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0001\"\u0001\"\u0001" +
                    "\"\u0001\"\u0001\"\u0001\"\u0001#\u0001#\u0001#\u0001#\u0001#\u0001$\u0001" +
                    "$\u0001%\u0001%\u0003%\u00f9\b%\u0001%\u0001%\u0005%\u00fd\b%\n%\f%\u0100" +
                    "\t%\u0003%\u0102\b%\u0001&\u0003&\u0105\b&\u0001&\u0001&\u0004&\u0109" +
                    "\b&\u000b&\f&\u010a\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0001\'\u0001" +
                    "\'\u0001\'\u0001\'\u0001\'\u0003\'\u0117\b\'\u0001(\u0001(\u0001(\u0001" +
                    "(\u0001)\u0001)\u0001)\u0001)\u0001*\u0001*\u0001*\u0001*\u0001+\u0001" +
                    "+\u0001+\u0001+\u0001,\u0001,\u0001,\u0001,\u0001-\u0001-\u0001-\u0001" +
                    "-\u0001.\u0001.\u0001.\u0001.\u0001/\u0001/\u0001/\u0001/\u00010\u0004" +
                    "0\u013a\b0\u000b0\f0\u013b\u00010\u00010\u00011\u00011\u00011\u00011\u0001" +
                    "1\u0000\u00002\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005" +
                    "\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019" +
                    "\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015" +
                    "+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f" +
                    "? A!C\"E#G$I%K&M\'O(Q)S*U+W,Y-[.]/_0a1c2\u0001\u0000\u0002\u0003\u0000" +
                    "09AZaz\u0003\u0000\t\n\r\r  \u0150\u0000\u0001\u0001\u0000\u0000\u0000" +
                    "\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000" +
                    "\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000" +
                    "\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f" +
                    "\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013" +
                    "\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017" +
                    "\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b" +
                    "\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f" +
                    "\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000" +
                    "\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000" +
                    "\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000" +
                    "-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001" +
                    "\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000" +
                    "\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000" +
                    ";\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001" +
                    "\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000" +
                    "\u0000\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000" +
                    "I\u0001\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001" +
                    "\u0000\u0000\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000\u0000" +
                    "\u0000\u0000S\u0001\u0000\u0000\u0000\u0000U\u0001\u0000\u0000\u0000\u0000" +
                    "W\u0001\u0000\u0000\u0000\u0000Y\u0001\u0000\u0000\u0000\u0000[\u0001" +
                    "\u0000\u0000\u0000\u0000]\u0001\u0000\u0000\u0000\u0000_\u0001\u0000\u0000" +
                    "\u0000\u0000a\u0001\u0000\u0000\u0000\u0000c\u0001\u0000\u0000\u0000\u0001" +
                    "e\u0001\u0000\u0000\u0000\u0003j\u0001\u0000\u0000\u0000\u0005n\u0001" +
                    "\u0000\u0000\u0000\u0007r\u0001\u0000\u0000\u0000\tw\u0001\u0000\u0000" +
                    "\u0000\u000b{\u0001\u0000\u0000\u0000\r\u0080\u0001\u0000\u0000\u0000" +
                    "\u000f\u0084\u0001\u0000\u0000\u0000\u0011\u0087\u0001\u0000\u0000\u0000" +
                    "\u0013\u008a\u0001\u0000\u0000\u0000\u0015\u008d\u0001\u0000\u0000\u0000" +
                    "\u0017\u0090\u0001\u0000\u0000\u0000\u0019\u0092\u0001\u0000\u0000\u0000" +
                    "\u001b\u0096\u0001\u0000\u0000\u0000\u001d\u009b\u0001\u0000\u0000\u0000" +
                    "\u001f\u00a0\u0001\u0000\u0000\u0000!\u00a4\u0001\u0000\u0000\u0000#\u00a7" +
                    "\u0001\u0000\u0000\u0000%\u00ab\u0001\u0000\u0000\u0000\'\u00af\u0001" +
                    "\u0000\u0000\u0000)\u00b2\u0001\u0000\u0000\u0000+\u00b5\u0001\u0000\u0000" +
                    "\u0000-\u00b9\u0001\u0000\u0000\u0000/\u00bd\u0001\u0000\u0000\u00001" +
                    "\u00c1\u0001\u0000\u0000\u00003\u00c5\u0001\u0000\u0000\u00005\u00c9\u0001" +
                    "\u0000\u0000\u00007\u00cd\u0001\u0000\u0000\u00009\u00d1\u0001\u0000\u0000" +
                    "\u0000;\u00d5\u0001\u0000\u0000\u0000=\u00d9\u0001\u0000\u0000\u0000?" +
                    "\u00dd\u0001\u0000\u0000\u0000A\u00e1\u0001\u0000\u0000\u0000C\u00e5\u0001" +
                    "\u0000\u0000\u0000E\u00e9\u0001\u0000\u0000\u0000G\u00ef\u0001\u0000\u0000" +
                    "\u0000I\u00f4\u0001\u0000\u0000\u0000K\u0101\u0001\u0000\u0000\u0000M" +
                    "\u0104\u0001\u0000\u0000\u0000O\u0116\u0001\u0000\u0000\u0000Q\u0118\u0001" +
                    "\u0000\u0000\u0000S\u011c\u0001\u0000\u0000\u0000U\u0120\u0001\u0000\u0000" +
                    "\u0000W\u0124\u0001\u0000\u0000\u0000Y\u0128\u0001\u0000\u0000\u0000[" +
                    "\u012c\u0001\u0000\u0000\u0000]\u0130\u0001\u0000\u0000\u0000_\u0134\u0001" +
                    "\u0000\u0000\u0000a\u0139\u0001\u0000\u0000\u0000c\u013f\u0001\u0000\u0000" +
                    "\u0000ef\u0005p\u0000\u0000fg\u0005u\u0000\u0000gh\u0005s\u0000\u0000" +
                    "hi\u0005h\u0000\u0000i\u0002\u0001\u0000\u0000\u0000jk\u0005p\u0000\u0000" +
                    "kl\u0005o\u0000\u0000lm\u0005p\u0000\u0000m\u0004\u0001\u0000\u0000\u0000" +
                    "no\u0005a\u0000\u0000op\u0005d\u0000\u0000pq\u0005d\u0000\u0000q\u0006" +
                    "\u0001\u0000\u0000\u0000rs\u0005a\u0000\u0000st\u0005d\u0000\u0000tu\u0005" +
                    "d\u0000\u0000uv\u0005i\u0000\u0000v\b\u0001\u0000\u0000\u0000wx\u0005" +
                    "s\u0000\u0000xy\u0005u\u0000\u0000yz\u0005b\u0000\u0000z\n\u0001\u0000" +
                    "\u0000\u0000{|\u0005m\u0000\u0000|}\u0005u\u0000\u0000}~\u0005l\u0000" +
                    "\u0000~\u007f\u0005t\u0000\u0000\u007f\f\u0001\u0000\u0000\u0000\u0080" +
                    "\u0081\u0005d\u0000\u0000\u0081\u0082\u0005i\u0000\u0000\u0082\u0083\u0005" +
                    "v\u0000\u0000\u0083\u000e\u0001\u0000\u0000\u0000\u0084\u0085\u0005s\u0000" +
                    "\u0000\u0085\u0086\u0005w\u0000\u0000\u0086\u0010\u0001\u0000\u0000\u0000" +
                    "\u0087\u0088\u0005s\u0000\u0000\u0088\u0089\u0005i\u0000\u0000\u0089\u0012" +
                    "\u0001\u0000\u0000\u0000\u008a\u008b\u0005l\u0000\u0000\u008b\u008c\u0005" +
                    "w\u0000\u0000\u008c\u0014\u0001\u0000\u0000\u0000\u008d\u008e\u0005l\u0000" +
                    "\u0000\u008e\u008f\u0005i\u0000\u0000\u008f\u0016\u0001\u0000\u0000\u0000" +
                    "\u0090\u0091\u0005b\u0000\u0000\u0091\u0018\u0001\u0000\u0000\u0000\u0092" +
                    "\u0093\u0005b\u0000\u0000\u0093\u0094\u0005e\u0000\u0000\u0094\u0095\u0005" +
                    "q\u0000\u0000\u0095\u001a\u0001\u0000\u0000\u0000\u0096\u0097\u0005b\u0000" +
                    "\u0000\u0097\u0098\u0005l\u0000\u0000\u0098\u0099\u0005e\u0000\u0000\u0099" +
                    "\u009a\u0005q\u0000\u0000\u009a\u001c\u0001\u0000\u0000\u0000\u009b\u009c" +
                    "\u0005l\u0000\u0000\u009c\u009d\u0005e\u0000\u0000\u009d\u009e\u0005s" +
                    "\u0000\u0000\u009e\u009f\u0005s\u0000\u0000\u009f\u001e\u0001\u0000\u0000" +
                    "\u0000\u00a0\u00a1\u0005l\u0000\u0000\u00a1\u00a2\u0005e\u0000\u0000\u00a2" +
                    "\u00a3\u0005q\u0000\u0000\u00a3 \u0001\u0000\u0000\u0000\u00a4\u00a5\u0005" +
                    "e\u0000\u0000\u00a5\u00a6\u0005q\u0000\u0000\u00a6\"\u0001\u0000\u0000" +
                    "\u0000\u00a7\u00a8\u0005n\u0000\u0000\u00a8\u00a9\u0005e\u0000\u0000\u00a9" +
                    "\u00aa\u0005q\u0000\u0000\u00aa$\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005" +
                    "a\u0000\u0000\u00ac\u00ad\u0005n\u0000\u0000\u00ad\u00ae\u0005d\u0000" +
                    "\u0000\u00ae&\u0001\u0000\u0000\u0000\u00af\u00b0\u0005o\u0000\u0000\u00b0" +
                    "\u00b1\u0005r\u0000\u0000\u00b1(\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005" +
                    "j\u0000\u0000\u00b3\u00b4\u0005r\u0000\u0000\u00b4*\u0001\u0000\u0000" +
                    "\u0000\u00b5\u00b6\u0005j\u0000\u0000\u00b6\u00b7\u0005a\u0000\u0000\u00b7" +
                    "\u00b8\u0005l\u0000\u0000\u00b8,\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005" +
                    "l\u0000\u0000\u00ba\u00bb\u0005r\u0000\u0000\u00bb\u00bc\u0005a\u0000" +
                    "\u0000\u00bc.\u0001\u0000\u0000\u0000\u00bd\u00be\u0005s\u0000\u0000\u00be" +
                    "\u00bf\u0005r\u0000\u0000\u00bf\u00c0\u0005a\u0000\u0000\u00c00\u0001" +
                    "\u0000\u0000\u0000\u00c1\u00c2\u0005l\u0000\u0000\u00c2\u00c3\u0005r\u0000" +
                    "\u0000\u00c3\u00c4\u0005v\u0000\u0000\u00c42\u0001\u0000\u0000\u0000\u00c5" +
                    "\u00c6\u0005s\u0000\u0000\u00c6\u00c7\u0005r\u0000\u0000\u00c7\u00c8\u0005" +
                    "v\u0000\u0000\u00c84\u0001\u0000\u0000\u0000\u00c9\u00ca\u0005l\u0000" +
                    "\u0000\u00ca\u00cb\u0005f\u0000\u0000\u00cb\u00cc\u0005p\u0000\u0000\u00cc" +
                    "6\u0001\u0000\u0000\u0000\u00cd\u00ce\u0005s\u0000\u0000\u00ce\u00cf\u0005" +
                    "f\u0000\u0000\u00cf\u00d0\u0005p\u0000\u0000\u00d08\u0001\u0000\u0000" +
                    "\u0000\u00d1\u00d2\u0005c\u0000\u0000\u00d2\u00d3\u0005f\u0000\u0000\u00d3" +
                    "\u00d4\u0005p\u0000\u0000\u00d4:\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005" +
                    "l\u0000\u0000\u00d6\u00d7\u0005a\u0000\u0000\u00d7\u00d8\u0005l\u0000" +
                    "\u0000\u00d8<\u0001\u0000\u0000\u0000\u00d9\u00da\u0005s\u0000\u0000\u00da" +
                    "\u00db\u0005a\u0000\u0000\u00db\u00dc\u0005l\u0000\u0000\u00dc>\u0001" +
                    "\u0000\u0000\u0000\u00dd\u00de\u0005c\u0000\u0000\u00de\u00df\u0005a\u0000" +
                    "\u0000\u00df\u00e0\u0005l\u0000\u0000\u00e0@\u0001\u0000\u0000\u0000\u00e1" +
                    "\u00e2\u0005l\u0000\u0000\u00e2\u00e3\u0005h\u0000\u0000\u00e3\u00e4\u0005" +
                    "p\u0000\u0000\u00e4B\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005s\u0000" +
                    "\u0000\u00e6\u00e7\u0005h\u0000\u0000\u00e7\u00e8\u0005p\u0000\u0000\u00e8" +
                    "D\u0001\u0000\u0000\u0000\u00e9\u00ea\u0005p\u0000\u0000\u00ea\u00eb\u0005" +
                    "r\u0000\u0000\u00eb\u00ec\u0005i\u0000\u0000\u00ec\u00ed\u0005n\u0000" +
                    "\u0000\u00ed\u00ee\u0005t\u0000\u0000\u00eeF\u0001\u0000\u0000\u0000\u00ef" +
                    "\u00f0\u0005h\u0000\u0000\u00f0\u00f1\u0005a\u0000\u0000\u00f1\u00f2\u0005" +
                    "l\u0000\u0000\u00f2\u00f3\u0005t\u0000\u0000\u00f3H\u0001\u0000\u0000" +
                    "\u0000\u00f4\u00f5\u0005:\u0000\u0000\u00f5J\u0001\u0000\u0000\u0000\u00f6" +
                    "\u0102\u00050\u0000\u0000\u00f7\u00f9\u0005-\u0000\u0000\u00f8\u00f7\u0001" +
                    "\u0000\u0000\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fa\u0001" +
                    "\u0000\u0000\u0000\u00fa\u00fe\u000219\u0000\u00fb\u00fd\u000209\u0000" +
                    "\u00fc\u00fb\u0001\u0000\u0000\u0000\u00fd\u0100\u0001\u0000\u0000\u0000" +
                    "\u00fe\u00fc\u0001\u0000\u0000\u0000\u00fe\u00ff\u0001\u0000\u0000\u0000" +
                    "\u00ff\u0102\u0001\u0000\u0000\u0000\u0100\u00fe\u0001\u0000\u0000\u0000" +
                    "\u0101\u00f6\u0001\u0000\u0000\u0000\u0101\u00f8\u0001\u0000\u0000\u0000" +
                    "\u0102L\u0001\u0000\u0000\u0000\u0103\u0105\u0005_\u0000\u0000\u0104\u0103" +
                    "\u0001\u0000\u0000\u0000\u0104\u0105\u0001\u0000\u0000\u0000\u0105\u0106" +
                    "\u0001\u0000\u0000\u0000\u0106\u0108\u0005_\u0000\u0000\u0107\u0109\u0007" +
                    "\u0000\u0000\u0000\u0108\u0107\u0001\u0000\u0000\u0000\u0109\u010a\u0001" +
                    "\u0000\u0000\u0000\u010a\u0108\u0001\u0000\u0000\u0000\u010a\u010b\u0001" +
                    "\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000\u010c\u010d\u0005" +
                    "_\u0000\u0000\u010dN\u0001\u0000\u0000\u0000\u010e\u0117\u0003Q(\u0000" +
                    "\u010f\u0117\u0003S)\u0000\u0110\u0117\u0003U*\u0000\u0111\u0117\u0003" +
                    "W+\u0000\u0112\u0117\u0003Y,\u0000\u0113\u0117\u0003[-\u0000\u0114\u0117" +
                    "\u0003].\u0000\u0115\u0117\u0003_/\u0000\u0116\u010e\u0001\u0000\u0000" +
                    "\u0000\u0116\u010f\u0001\u0000\u0000\u0000\u0116\u0110\u0001\u0000\u0000" +
                    "\u0000\u0116\u0111\u0001\u0000\u0000\u0000\u0116\u0112\u0001\u0000\u0000" +
                    "\u0000\u0116\u0113\u0001\u0000\u0000\u0000\u0116\u0114\u0001\u0000\u0000" +
                    "\u0000\u0116\u0115\u0001\u0000\u0000\u0000\u0117P\u0001\u0000\u0000\u0000" +
                    "\u0118\u0119\u0005$\u0000\u0000\u0119\u011a\u0005a\u0000\u0000\u011a\u011b" +
                    "\u00050\u0000\u0000\u011bR\u0001\u0000\u0000\u0000\u011c\u011d\u0005$" +
                    "\u0000\u0000\u011d\u011e\u0005t\u0000\u0000\u011e\u011f\u00050\u0000\u0000" +
                    "\u011fT\u0001\u0000\u0000\u0000\u0120\u0121\u0005$\u0000\u0000\u0121\u0122" +
                    "\u0005s\u0000\u0000\u0122\u0123\u0005p\u0000\u0000\u0123V\u0001\u0000" +
                    "\u0000\u0000\u0124\u0125\u0005$\u0000\u0000\u0125\u0126\u0005r\u0000\u0000" +
                    "\u0126\u0127\u0005a\u0000\u0000\u0127X\u0001\u0000\u0000\u0000\u0128\u0129" +
                    "\u0005$\u0000\u0000\u0129\u012a\u0005f\u0000\u0000\u012a\u012b\u0005p" +
                    "\u0000\u0000\u012bZ\u0001\u0000\u0000\u0000\u012c\u012d\u0005$\u0000\u0000" +
                    "\u012d\u012e\u0005a\u0000\u0000\u012e\u012f\u0005l\u0000\u0000\u012f\\" +
                    "\u0001\u0000\u0000\u0000\u0130\u0131\u0005$\u0000\u0000\u0131\u0132\u0005" +
                    "h\u0000\u0000\u0132\u0133\u0005p\u0000\u0000\u0133^\u0001\u0000\u0000" +
                    "\u0000\u0134\u0135\u0005$\u0000\u0000\u0135\u0136\u0005r\u0000\u0000\u0136" +
                    "\u0137\u0005v\u0000\u0000\u0137`\u0001\u0000\u0000\u0000\u0138\u013a\u0007" +
                    "\u0001\u0000\u0000\u0139\u0138\u0001\u0000\u0000\u0000\u013a\u013b\u0001" +
                    "\u0000\u0000\u0000\u013b\u0139\u0001\u0000\u0000\u0000\u013b\u013c\u0001" +
                    "\u0000\u0000\u0000\u013c\u013d\u0001\u0000\u0000\u0000\u013d\u013e\u0006" +
                    "0\u0000\u0000\u013eb\u0001\u0000\u0000\u0000\u013f\u0140\t\u0000\u0000" +
                    "\u0000\u0140\u0141\u00061\u0001\u0000\u0141\u0142\u0001\u0000\u0000\u0000" +
                    "\u0142\u0143\u00061\u0000\u0000\u0143d\u0001\u0000\u0000\u0000\b\u0000" +
                    "\u00f8\u00fe\u0101\u0104\u010a\u0116\u013b\u0002\u0000\u0001\u0000\u0001" +
                    "1\u0000";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
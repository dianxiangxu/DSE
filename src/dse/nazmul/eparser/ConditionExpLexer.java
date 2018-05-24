// Generated from ConditionExp.g4 by ANTLR 4.5.3
package dse.nazmul.eparser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ConditionExpLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, ADD=3, SUB=4, MUL=5, DIV=6, MOD=7, AND=8, OR=9, XOR=10, 
		LSH=11, RSH=12, RSH0F=13, COMPL=14, ID=15, INT=16, NEWLINE=17, WS=18;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "ADD", "SUB", "MUL", "DIV", "MOD", "AND", "OR", "XOR", 
		"LSH", "RSH", "RSH0F", "COMPL", "ID", "INT", "NEWLINE", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", "'%'", "'&'", "'|'", "'^'", 
		"'<<'", "'>>'", "'>>>'", "'~'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "ADD", "SUB", "MUL", "DIV", "MOD", "AND", "OR", "XOR", 
		"LSH", "RSH", "RSH0F", "COMPL", "ID", "INT", "NEWLINE", "WS"
	};
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


	public ConditionExpLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ConditionExp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\24^\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t"+
		"\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\6\20J\n\20\r\20\16\20K\3\21\6\21O\n\21\r\21\16\21P\3\22"+
		"\5\22T\n\22\3\22\3\22\3\23\6\23Y\n\23\r\23\16\23Z\3\23\3\23\2\2\24\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\3\2\4\3\2\62;\4\2\13\13\"\"a\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\3\'\3\2\2\2"+
		"\5)\3\2\2\2\7+\3\2\2\2\t-\3\2\2\2\13/\3\2\2\2\r\61\3\2\2\2\17\63\3\2\2"+
		"\2\21\65\3\2\2\2\23\67\3\2\2\2\259\3\2\2\2\27;\3\2\2\2\31>\3\2\2\2\33"+
		"A\3\2\2\2\35E\3\2\2\2\37G\3\2\2\2!N\3\2\2\2#S\3\2\2\2%X\3\2\2\2\'(\7*"+
		"\2\2(\4\3\2\2\2)*\7+\2\2*\6\3\2\2\2+,\7-\2\2,\b\3\2\2\2-.\7/\2\2.\n\3"+
		"\2\2\2/\60\7,\2\2\60\f\3\2\2\2\61\62\7\61\2\2\62\16\3\2\2\2\63\64\7\'"+
		"\2\2\64\20\3\2\2\2\65\66\7(\2\2\66\22\3\2\2\2\678\7~\2\28\24\3\2\2\29"+
		":\7`\2\2:\26\3\2\2\2;<\7>\2\2<=\7>\2\2=\30\3\2\2\2>?\7@\2\2?@\7@\2\2@"+
		"\32\3\2\2\2AB\7@\2\2BC\7@\2\2CD\7@\2\2D\34\3\2\2\2EF\7\u0080\2\2F\36\3"+
		"\2\2\2GI\7r\2\2HJ\t\2\2\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2L \3"+
		"\2\2\2MO\t\2\2\2NM\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\"\3\2\2\2RT"+
		"\7\17\2\2SR\3\2\2\2ST\3\2\2\2TU\3\2\2\2UV\7\f\2\2V$\3\2\2\2WY\t\3\2\2"+
		"XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\]\b\23\2\2]&\3\2"+
		"\2\2\7\2KPSZ\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
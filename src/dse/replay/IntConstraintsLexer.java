// Generated from IntConstraints.g4 by ANTLR 4.1

package dse.replay;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class IntConstraintsLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, UNARYBITWISE=4, GR=5, LS=6, GE=7, LE=8, EQ=9, 
		NEQ=10, ADD=11, SUB=12, MULT=13, MOD=14, DIV=15, AND=16, OREXL=17, ORINCL=18, 
		SHL=19, SHR=20, SHRU=21, SYMB=22, INT=23, WS=24;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"')'", "'('", "'!'", "'~'", "'>'", "'<'", "'>='", "'<='", "'=='", "'!='", 
		"'+'", "'-'", "'*'", "'%'", "'/'", "'&'", "'^'", "'|'", "'<<'", "'>>'", 
		"'>>>'", "SYMB", "INT", "WS"
	};
	public static final String[] ruleNames = {
		"T__2", "T__1", "T__0", "UNARYBITWISE", "GR", "LS", "GE", "LE", "EQ", 
		"NEQ", "ADD", "SUB", "MULT", "MOD", "DIV", "AND", "OREXL", "ORINCL", "SHL", 
		"SHR", "SHRU", "SYMB", "INT", "WS"
	};


	public IntConstraintsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "IntConstraints.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 23: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\32w\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\6\27h\n\27\r\27\16\27i\3\30\6\30m\n\30\r\30"+
		"\16\30n\3\31\6\31r\n\31\r\31\16\31s\3\31\3\31\2\32\3\3\1\5\4\1\7\5\1\t"+
		"\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1"+
		"\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32"+
		"\2\3\2\4\3\2\62;\5\2\13\f\17\17\"\"y\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\3\63\3\2\2\2\5\65\3"+
		"\2\2\2\7\67\3\2\2\2\t9\3\2\2\2\13;\3\2\2\2\r=\3\2\2\2\17?\3\2\2\2\21B"+
		"\3\2\2\2\23E\3\2\2\2\25H\3\2\2\2\27K\3\2\2\2\31M\3\2\2\2\33O\3\2\2\2\35"+
		"Q\3\2\2\2\37S\3\2\2\2!U\3\2\2\2#W\3\2\2\2%Y\3\2\2\2\'[\3\2\2\2)^\3\2\2"+
		"\2+a\3\2\2\2-e\3\2\2\2/l\3\2\2\2\61q\3\2\2\2\63\64\7+\2\2\64\4\3\2\2\2"+
		"\65\66\7*\2\2\66\6\3\2\2\2\678\7#\2\28\b\3\2\2\29:\7\u0080\2\2:\n\3\2"+
		"\2\2;<\7@\2\2<\f\3\2\2\2=>\7>\2\2>\16\3\2\2\2?@\7@\2\2@A\7?\2\2A\20\3"+
		"\2\2\2BC\7>\2\2CD\7?\2\2D\22\3\2\2\2EF\7?\2\2FG\7?\2\2G\24\3\2\2\2HI\7"+
		"#\2\2IJ\7?\2\2J\26\3\2\2\2KL\7-\2\2L\30\3\2\2\2MN\7/\2\2N\32\3\2\2\2O"+
		"P\7,\2\2P\34\3\2\2\2QR\7\'\2\2R\36\3\2\2\2ST\7\61\2\2T \3\2\2\2UV\7(\2"+
		"\2V\"\3\2\2\2WX\7`\2\2X$\3\2\2\2YZ\7~\2\2Z&\3\2\2\2[\\\7>\2\2\\]\7>\2"+
		"\2](\3\2\2\2^_\7@\2\2_`\7@\2\2`*\3\2\2\2ab\7@\2\2bc\7@\2\2cd\7@\2\2d,"+
		"\3\2\2\2eg\7r\2\2fh\t\2\2\2gf\3\2\2\2hi\3\2\2\2ig\3\2\2\2ij\3\2\2\2j."+
		"\3\2\2\2km\t\2\2\2lk\3\2\2\2mn\3\2\2\2nl\3\2\2\2no\3\2\2\2o\60\3\2\2\2"+
		"pr\t\3\2\2qp\3\2\2\2rs\3\2\2\2sq\3\2\2\2st\3\2\2\2tu\3\2\2\2uv\b\31\2"+
		"\2v\62\3\2\2\2\6\2ins";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
// Generated from IntConstraints.g4 by ANTLR 4.1

package dse.replay;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class IntConstraintsParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, UNARYBITWISE=4, GR=5, LS=6, GE=7, LE=8, EQ=9, 
		NEQ=10, ADD=11, SUB=12, MULT=13, MOD=14, DIV=15, AND=16, OREXL=17, ORINCL=18, 
		SHL=19, SHR=20, SHRU=21, SYMB=22, INT=23, WS=24;
	public static final String[] tokenNames = {
		"<INVALID>", "')'", "'('", "'!'", "'~'", "'>'", "'<'", "'>='", "'<='", 
		"'=='", "'!='", "'+'", "'-'", "'*'", "'%'", "'/'", "'&'", "'^'", "'|'", 
		"'<<'", "'>>'", "'>>>'", "SYMB", "INT", "WS"
	};
	public static final int
		RULE_constraints = 0, RULE_constraint = 1, RULE_negConstraint = 2, RULE_posConstraint = 3, 
		RULE_expr = 4;
	public static final String[] ruleNames = {
		"constraints", "constraint", "negConstraint", "posConstraint", "expr"
	};

	@Override
	public String getGrammarFileName() { return "IntConstraints.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public IntConstraintsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ConstraintsContext extends ParserRuleContext {
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraints; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitConstraints(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_constraints);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 3) | (1L << UNARYBITWISE) | (1L << SUB) | (1L << SYMB) | (1L << INT))) != 0)) {
				{
				{
				setState(10); constraint();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public PosConstraintContext posConstraint() {
			return getRuleContext(PosConstraintContext.class,0);
		}
		public NegConstraintContext negConstraint() {
			return getRuleContext(NegConstraintContext.class,0);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_constraint);
		try {
			setState(18);
			switch (_input.LA(1)) {
			case 2:
			case UNARYBITWISE:
			case SUB:
			case SYMB:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(16); posConstraint();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 2);
				{
				setState(17); negConstraint();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NegConstraintContext extends ParserRuleContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public NegConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negConstraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitNegConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegConstraintContext negConstraint() throws RecognitionException {
		NegConstraintContext _localctx = new NegConstraintContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_negConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20); match(3);
			setState(21); match(2);
			setState(22); ((NegConstraintContext)_localctx).lhs = expr(0);
			setState(23);
			((NegConstraintContext)_localctx).op = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GR) | (1L << LS) | (1L << GE) | (1L << LE) | (1L << EQ) | (1L << NEQ))) != 0)) ) {
				((NegConstraintContext)_localctx).op = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(24); ((NegConstraintContext)_localctx).rhs = expr(0);
			setState(25); match(1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PosConstraintContext extends ParserRuleContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public PosConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_posConstraint; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitPosConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PosConstraintContext posConstraint() throws RecognitionException {
		PosConstraintContext _localctx = new PosConstraintContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_posConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27); ((PosConstraintContext)_localctx).lhs = expr(0);
			setState(28);
			((PosConstraintContext)_localctx).op = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GR) | (1L << LS) | (1L << GE) | (1L << LE) | (1L << EQ) | (1L << NEQ))) != 0)) ) {
				((PosConstraintContext)_localctx).op = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(29); ((PosConstraintContext)_localctx).rhs = expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public int _p;
		public ExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExprContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
			this._p = ctx._p;
		}
	}
	public static class BinaryArithmContext extends ExprContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BinaryArithmContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitBinaryArithm(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryBitwiseContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode UNARYBITWISE() { return getToken(IntConstraintsParser.UNARYBITWISE, 0); }
		public UnaryBitwiseContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitUnaryBitwise(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SymbolicContext extends ExprContext {
		public TerminalNode SYMB() { return getToken(IntConstraintsParser.SYMB, 0); }
		public SymbolicContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitSymbolic(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParensContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParensContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryBitwiseContext extends ExprContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BinaryBitwiseContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitBinaryBitwise(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConcreteContext extends ExprContext {
		public TerminalNode INT() { return getToken(IntConstraintsParser.INT, 0); }
		public ConcreteContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitConcrete(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryArithmContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SUB() { return getToken(IntConstraintsParser.SUB, 0); }
		public UnaryArithmContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IntConstraintsVisitor ) return ((IntConstraintsVisitor<? extends T>)visitor).visitUnaryArithm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState, _p);
		ExprContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, RULE_expr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			switch (_input.LA(1)) {
			case SUB:
				{
				_localctx = new UnaryArithmContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(32); match(SUB);
				setState(33); expr(7);
				}
				break;
			case UNARYBITWISE:
				{
				_localctx = new UnaryBitwiseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(34); match(UNARYBITWISE);
				setState(35); expr(6);
				}
				break;
			case 2:
				{
				_localctx = new ParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(36); match(2);
				setState(37); expr(0);
				setState(38); match(1);
				}
				break;
			case SYMB:
				{
				_localctx = new SymbolicContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(40); match(SYMB);
				}
				break;
			case INT:
				{
				_localctx = new ConcreteContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(41); match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(52);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(50);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryArithmContext(new ExprContext(_parentctx, _parentState, _p));
						((BinaryArithmContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(44);
						if (!(4 >= _localctx._p)) throw new FailedPredicateException(this, "4 >= $_p");
						setState(45);
						((BinaryArithmContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUB) | (1L << MULT) | (1L << MOD) | (1L << DIV))) != 0)) ) {
							((BinaryArithmContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(46); ((BinaryArithmContext)_localctx).rhs = expr(5);
						}
						break;

					case 2:
						{
						_localctx = new BinaryBitwiseContext(new ExprContext(_parentctx, _parentState, _p));
						((BinaryBitwiseContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(47);
						if (!(3 >= _localctx._p)) throw new FailedPredicateException(this, "3 >= $_p");
						setState(48);
						((BinaryBitwiseContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AND) | (1L << OREXL) | (1L << ORINCL) | (1L << SHL) | (1L << SHR) | (1L << SHRU))) != 0)) ) {
							((BinaryBitwiseContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(49); ((BinaryBitwiseContext)_localctx).rhs = expr(4);
						}
						break;
					}
					} 
				}
				setState(54);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return 4 >= _localctx._p;

		case 1: return 3 >= _localctx._p;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\32:\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\7\2\16\n\2\f\2\16\2\21\13\2\3\3\3\3\5\3"+
		"\25\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6-\n\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\65\n\6"+
		"\f\6\16\68\13\6\3\6\2\7\2\4\6\b\n\2\5\3\2\7\f\3\2\r\21\3\2\22\27<\2\17"+
		"\3\2\2\2\4\24\3\2\2\2\6\26\3\2\2\2\b\35\3\2\2\2\n,\3\2\2\2\f\16\5\4\3"+
		"\2\r\f\3\2\2\2\16\21\3\2\2\2\17\r\3\2\2\2\17\20\3\2\2\2\20\3\3\2\2\2\21"+
		"\17\3\2\2\2\22\25\5\b\5\2\23\25\5\6\4\2\24\22\3\2\2\2\24\23\3\2\2\2\25"+
		"\5\3\2\2\2\26\27\7\5\2\2\27\30\7\4\2\2\30\31\5\n\6\2\31\32\t\2\2\2\32"+
		"\33\5\n\6\2\33\34\7\3\2\2\34\7\3\2\2\2\35\36\5\n\6\2\36\37\t\2\2\2\37"+
		" \5\n\6\2 \t\3\2\2\2!\"\b\6\1\2\"#\7\16\2\2#-\5\n\6\2$%\7\6\2\2%-\5\n"+
		"\6\2&\'\7\4\2\2\'(\5\n\6\2()\7\3\2\2)-\3\2\2\2*-\7\30\2\2+-\7\31\2\2,"+
		"!\3\2\2\2,$\3\2\2\2,&\3\2\2\2,*\3\2\2\2,+\3\2\2\2-\66\3\2\2\2./\6\6\2"+
		"\3/\60\t\3\2\2\60\65\5\n\6\2\61\62\6\6\3\3\62\63\t\4\2\2\63\65\5\n\6\2"+
		"\64.\3\2\2\2\64\61\3\2\2\2\658\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\67"+
		"\13\3\2\2\28\66\3\2\2\2\7\17\24,\64\66";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
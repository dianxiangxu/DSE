// Generated from CONDEXP.g4 by ANTLR 4.5.3

package dse.nazmul.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CONDEXPParser}.
 */
public interface CONDEXPListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CONDEXPParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CONDEXPParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CONDEXPParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CONDEXPParser.ExprContext ctx);
}
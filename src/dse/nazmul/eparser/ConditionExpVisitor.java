// Generated from ConditionExp.g4 by ANTLR 4.5.3
package dse.nazmul.eparser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ConditionExpParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ConditionExpVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ConditionExpParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ConditionExpParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link ConditionExpParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpr(ConditionExpParser.PrintExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link ConditionExpParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(ConditionExpParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArithOperation}
	 * labeled alternative in {@link ConditionExpParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithOperation(ConditionExpParser.ArithOperationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link ConditionExpParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(ConditionExpParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link ConditionExpParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(ConditionExpParser.IntContext ctx);
}
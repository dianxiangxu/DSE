// Generated from IntConstraints.g4 by ANTLR 4.1

package dse.replay;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link IntConstraintsParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface IntConstraintsVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#binaryArithm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryArithm(@NotNull IntConstraintsParser.BinaryArithmContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#unaryBitwise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryBitwise(@NotNull IntConstraintsParser.UnaryBitwiseContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#constraints}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraints(@NotNull IntConstraintsParser.ConstraintsContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#symbolic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbolic(@NotNull IntConstraintsParser.SymbolicContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint(@NotNull IntConstraintsParser.ConstraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#parens}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(@NotNull IntConstraintsParser.ParensContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#posConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPosConstraint(@NotNull IntConstraintsParser.PosConstraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#binaryBitwise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryBitwise(@NotNull IntConstraintsParser.BinaryBitwiseContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#concrete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcrete(@NotNull IntConstraintsParser.ConcreteContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#negConstraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegConstraint(@NotNull IntConstraintsParser.NegConstraintContext ctx);

	/**
	 * Visit a parse tree produced by {@link IntConstraintsParser#unaryArithm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryArithm(@NotNull IntConstraintsParser.UnaryArithmContext ctx);
}
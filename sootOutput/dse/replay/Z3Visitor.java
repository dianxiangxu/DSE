package dse.replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.misc.NotNull;

import com.microsoft.z3.BitVecExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

import dse.replay.IntConstraintsParser.BinaryArithmContext;
import dse.replay.IntConstraintsParser.BinaryBitwiseContext;
import dse.replay.IntConstraintsParser.ConcreteContext;
import dse.replay.IntConstraintsParser.ConstraintContext;
import dse.replay.IntConstraintsParser.NegConstraintContext;
import dse.replay.IntConstraintsParser.ParensContext;
import dse.replay.IntConstraintsParser.PosConstraintContext;
import dse.replay.IntConstraintsParser.SymbolicContext;
import dse.replay.IntConstraintsParser.UnaryArithmContext;


public class Z3Visitor extends IntConstraintsBaseVisitor<Expr> {
	public static int maxRatio = 2;
	Map<String, Expr> memory = new HashMap<String, Expr>();
	Context z3;
	Solver solver;
	Status status;
	List<String> knownTaken = new ArrayList<String>();
	List<String> knownNotTaken = new ArrayList<String>();
	List<String> timeR = new ArrayList<String>();//time for the taken branch
	List<String> timeN = new ArrayList<String>();//time for the negated branch
	float runningAverage;
	long totalTime;
	int constraintCount;

	public Z3Visitor(){
		super();
		try {
			z3 = new Context();
			solver = z3.MkSolver();
			status = solver.Check();
			runningAverage = 0;
			totalTime = 0;
			constraintCount = 0;
		} catch (Z3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Expr visitConstraint(@NotNull ConstraintContext ctx){
		//System.out.println("In constraint " + ctx.getText() + " " + ctx.getChildCount());
		String name = ctx.getText();
		Expr result = retrive(name);
		if(result == null){
			result = visit(ctx.getChild(0));
			//add to the map
			memory.put(name, result);
		}
		try {
			solver.Push();
			//negate the result
			BoolExpr negatedResult = z3.MkNot((BoolExpr)result);
			long startTime = System.nanoTime();
			solver.Assert(negatedResult);
			status = solver.Check();
			//System.out.println("!" + ctx.getText() + " is " + status + " " + solver.Assertions().length + " " + solver.Assertions()[solver.Assertions().length-1]);
			long endTime = System.nanoTime();
			knownNotTaken.add(status.toString());
			long duration = endTime - startTime;
			timeN.add(String.valueOf(duration));
			//System.out.println(solver.toString());
			solver.Pop();
//			if(solver.Assertions().length-1 >= 0){
//				System.out.println("Cleared " + solver.Assertions().length + " last left "+ solver.Assertions()[solver.Assertions().length-1]);
//			}
			solver.Push();
			startTime = System.nanoTime();
			solver.Assert((BoolExpr)result);
			status = solver.Check();
			endTime = System.nanoTime();
			duration = endTime-startTime;
			timeR.add(String.valueOf(duration));
			//System.out.println(ctx.getText() + " is " + status + " " + solver.Assertions().length + " " + solver.Assertions()[solver.Assertions().length-1]);
			//for DSE status cannot be unsat
			if(status.equals(Status.UNSATISFIABLE)){
				System.out.println("Status cannot be UNSAT");
				System.exit(2);
			} else{ 
				//check the ratio of the running average and the duration
				int ratio = 0;
				if(runningAverage == 0){
					ratio = 1;//the fist value
				} else {
					ratio = Math.round(duration/runningAverage);
				}
				//System.out.println(totalTime + " " + constraintCount + " " + runningAverage + " " + duration + " " + ratio);
				if(ratio > maxRatio){
					//System.out.println("Ratio " + ratio + " length " + solver.Assertions().length);
					knownTaken.add("UNKNOWN");
					//pop the constraint
						solver.Pop();//the constraint is not in PC anymore
				} else {
					knownTaken.add(status.toString());
				}
			}
			//recalculate the running average
			constraintCount++;
			totalTime +=duration;
			runningAverage = totalTime/constraintCount;
		} catch (Z3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Expr visitPosConstraint(@NotNull PosConstraintContext ctx){
		//System.out.println("Visiting positive");
		//first check the memory
		String name = ctx.getText();
		Expr result = retrive(name);
		if(result == null){
			ArithExpr lhs = (ArithExpr) visit(ctx.lhs);
			ArithExpr rhs = (ArithExpr) visit(ctx.rhs);
			result = relExpr(lhs,rhs,ctx.op.getType());
			//add to the map
			memory.put(name, result);
		}
		return result;
	}

	@Override
	public Expr visitNegConstraint(@NotNull NegConstraintContext ctx){
		//negate what it gets
		String name = ctx.getText();
		//System.out.println("Visiting negative");
		Expr result = retrive(name);
		if(result == null){
		ArithExpr lhs = (ArithExpr) visit(ctx.lhs);
		ArithExpr rhs = (ArithExpr) visit(ctx.rhs);
		result = relExpr(lhs,rhs,ctx.op.getType());
		//negate
		try {
			result = z3.MkNot((BoolExpr)result);
		} catch (Z3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		memory.put(name, result);
		}
		return result;
	}

	private BoolExpr relExpr(ArithExpr rhs, ArithExpr lhs, int type ){
		BoolExpr result = null;
		try{
			switch(type){
			case IntConstraintsParser.EQ : {
				result = z3.MkEq(rhs, lhs);
			};
			break;
			case IntConstraintsParser.NEQ : {
				result = z3.MkNot(z3.MkEq(rhs, lhs));
			};
			break;
			case IntConstraintsParser.LS : {
				result = z3.MkLt(rhs, lhs);
			};
			break;
			case IntConstraintsParser.LE : {
				result = z3.MkLe(rhs, lhs);
			};
			break;
			case IntConstraintsParser.GR : {
				result = z3.MkGt(rhs, lhs);
			};
			break;
			case IntConstraintsParser.GE : {
				result = z3.MkGe(rhs, lhs);
			};
			break;
			}

		} catch (Z3Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Expr visitParens(@NotNull ParensContext ctx){
		//System.out.println("Text " + ctx.getText());
		String name = ctx.getText();
		Expr result = retrive(name);
		if(result == null){
			result = visit(ctx.expr());
			//add to the map
			memory.put(name, result);
		}
		return result;
	}

	@Override
	public Expr visitBinaryArithm(@NotNull BinaryArithmContext ctx){
		String name = ctx.getText();
		Expr result = retrive(name);
		if(result == null){
		Expr lhs = visit(ctx.lhs);
		Expr rhs = visit (ctx.rhs);
		//System.out.println(lhs + " " + rhs);
		try {
			//switch stmt for different types
			switch(ctx.op.getType()){
			case IntConstraintsParser.ADD: {
				result = z3.MkAdd(new ArithExpr[]{(ArithExpr)lhs, (ArithExpr)rhs});};
				break;
			case IntConstraintsParser.SUB: {
				result = z3.MkSub(new ArithExpr[]{(ArithExpr)lhs, (ArithExpr)rhs});
			};
			break;
			case IntConstraintsParser.DIV: {
				result =  z3.MkDiv((ArithExpr)lhs, (ArithExpr)rhs);
			};
			break;
			case IntConstraintsParser.MULT: {
				result = z3.MkMul(new ArithExpr[]{(ArithExpr)lhs, (ArithExpr)rhs});
			};
			break;
			case IntConstraintsParser.MOD: {
				result = z3.MkMod((IntExpr)lhs, (IntExpr)rhs);
			};
			break;
			}
			//add to the map
			memory.put(name, result);
		} catch (Z3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		//System.out.println(result);
		return result;
	}

	@Override
	public Expr visitBinaryBitwise(@NotNull BinaryBitwiseContext ctx){
		String name = ctx.getText();
		//System.out.println(ctx.getText());
		Expr result = retrive(name);
		if(result == null){
		try {
			BitVecExpr resultBV = null;
			//32 - unsinged size
			BitVecExpr lhs = z3.MkInt2BV(32, (IntExpr)visit(ctx.lhs));

			BitVecExpr rhs = z3.MkInt2BV(32, (IntExpr) visit (ctx.rhs));
			//System.out.println("Done converting to BV");
			//first covert lhs and rhs to bitvector
			//switch stmt for different types
			switch(ctx.op.getType()){
			case IntConstraintsParser.AND: {
				resultBV = z3.MkBVAND(lhs, rhs);
			};
			break;
			case IntConstraintsParser.OREXL: {
				resultBV = z3.MkBVXOR(lhs, rhs);
			};
			break;
			case IntConstraintsParser.ORINCL: {
				resultBV = z3.MkBVNOR(lhs, rhs);
			};
			break;
			case IntConstraintsParser.SHL: {
				resultBV = z3.MkBVSHL(lhs, rhs);
			};
			break;
			case IntConstraintsParser.SHR: {
				resultBV = z3.MkBVASHR(lhs, rhs);//arithmetic shift -- keeps the sing bit
			};
			break;
			case IntConstraintsParser.SHRU: {
				resultBV = z3.MkBVLSHR(lhs, rhs);//logical shift -- puts zero to the left
			};
			break;
			}
			//convert back to integer before returning
			result = z3.MkBV2Int(resultBV, false); //last arg - isSigned? -- with true gets an error
			//add to the map
			memory.put(name, result);
		} catch (Z3Exception e) {
			e.printStackTrace();
		}
		}
		return result;
	}

	@Override
	public Expr visitUnaryArithm(@NotNull UnaryArithmContext ctx){
		String name = ctx.getText();
		Expr result = retrive(name);
		if(result == null){
			try {
				result = z3.MkUnaryMinus((ArithExpr) visit(ctx.expr()));
				//put in the map
				memory.put(name, result);
			} catch (Z3Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("Result is " + result);
		return result;
	}
	@Override
	public Expr visitConcrete(@NotNull ConcreteContext ctx){
		//make sure we can create and int
		String name = ctx.getText();
		Long val = Long.valueOf(name);
		//check if exists with such value
		Expr constant = retrive(name);
		if(constant==null){
			try {
				constant = z3.MkInt(val);
				//put in the map
				memory.put(name, constant);
			} catch (Z3Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("Constant " + constant);
		return constant;
	}

	@Override
	public Expr visitSymbolic(@NotNull SymbolicContext ctx){
		//make sure we can create and int
		String name = ctx.getText();
		//check if exits with such name
		Expr constant = retrive(name);
		//check if tis in the map
		if(constant == null){
			try {
				constant = z3.MkIntConst(name);
				//put to the map
				memory.put(name, constant);
				//System.out.println("new symb");

			} catch (Z3Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("Symbolic " + constant);
		return constant;
	}

	private Expr retrive(String name){
		Expr result = null;
		if(memory.containsKey(name)){
			result = memory.get(name);
		}
		return result;
	}

}

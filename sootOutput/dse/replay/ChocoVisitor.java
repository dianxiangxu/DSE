package dse.replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.v4.runtime.tree.ParseTree;

import choco.Choco;
import choco.cp.solver.CPSolver;
import choco.cp.solver.search.VarSelectorFactory;
import choco.cp.solver.variables.integer.IntVarEvent;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.ContradictionException;
import choco.kernel.solver.variables.integer.IntDomainVar;
import choco.cp.model.CPModel;

import dse.replay.IntConstraintsParser.BinaryArithmContext;
import dse.replay.IntConstraintsParser.BinaryBitwiseContext;
import dse.replay.IntConstraintsParser.ConcreteContext;
import dse.replay.IntConstraintsParser.ConstraintContext;
import dse.replay.IntConstraintsParser.PosConstraintContext;
import dse.replay.IntConstraintsParser.SymbolicContext;

public class ChocoVisitor extends IntConstraintsBaseVisitor<Object> {
	CPSolver solver; 
	CPModel model; 
	public static Map<String,IntegerVariable> vars = new HashMap<String, IntegerVariable>();
	
	public ChocoVisitor(){
		//Solver iterates through the model and can solve based on parameters 
		solver = new CPSolver();
		//Model records the variables and constraints from the program 
		model = new CPModel();	
	}
	
	@Override
	public Object visitConstraint(ConstraintContext ctx){
		//TODO: Make Constraint, and Add Constraint ? make IntegerVariable then make IntegerExpressionVariable 
		System.out.println("     VISITCONSTRAINT    ");
		String name = ctx.getText();
		System.out.println("Name:" + name);
		System.out.println("Class " + ctx.getChild(0).getClass());
		
		Constraint result = (Constraint) visit(ctx.getChild(0));
		
		//Read the model
		solver.read(model);
		//If the solver can solve things in the model 
		if(solver.solve()){
			//Iterate through the vars in map
			for(Entry<String, IntegerVariable> entry : vars.entrySet()){
				System.out.println("Solution: " + "Key:" + entry.getKey() +" " + "Solution:" + solver.getVar(entry.getValue()).getVal());
			}
			//entry.getValue()
		}
		return result;
	}
	
	@Override
	public Object visitSymbolic(SymbolicContext ctx){
		//TODO: Make variable, add variable to the model, and map key value pair to vars map 
		System.out.println("     VISITSYMBOLIC     ");
		String name = ctx.getText();
		System.out.println(name);
		System.out.println("Name and child0 " +name + " " + ctx.getChild(0));
		
		IntegerVariable symbolic;
		
		//If the symbolic value is already in vars map Return value
		if(vars.containsKey(ctx.getText())){ 
			 symbolic = vars.get(ctx.getText());
		}
		//Creating new IntegerVariable for symbolic value w/ domain of 1000 to -1000
		else{
			symbolic = Choco.makeIntVar(name, -1000, 1000);
			vars.put(name, symbolic);
		}
		System.out.println("Vars " + vars);
		return symbolic;
	}
	
	@Override
	public Object visitConcrete(ConcreteContext ctx){
		System.out.println("     VISITCONCRETE     ");
		String name = ctx.getText();
		System.out.println(name);
		System.out.println("Name and child0 " +name + " " + ctx.getChild(0));
		
		//Converting string to Integer Value 
		Integer val = Integer.valueOf(name);
		//Store value as constant 
		IntegerConstantVariable constant = Choco.constant(val);
		return constant; 
	}
	
	@Override
	public Object visitPosConstraint(PosConstraintContext ctx){
		System.out.println("     VISTINGPOSITIVE     ");
		String name = ctx.getText();
		System.out.println("Name and child0 " +name + " " + ctx.getChild(0));
		System.out.println("lhs " +ctx.lhs);
		System.out.println("rhs " +ctx.rhs);
		 
		
		IntegerExpressionVariable lhs = (IntegerExpressionVariable) visit(ctx.lhs);
		IntegerExpressionVariable rhs = (IntegerExpressionVariable) visit(ctx.rhs);
				
		Constraint c =  null;
		System.out.println("here " + ctx.op.getType());
		
		//Switch case for possible positive constraints
		switch(ctx.op.getType()){
	
		case IntConstraintsParser.GR:{
			System.out.println("Greater Than");
			c = Choco.gt(lhs, rhs);
			model.addConstraint(c);
			break;
		}
		
		case IntConstraintsParser.LS:{
			System.out.println("Less Than");
			c = Choco.lt(lhs, rhs);
			model.addConstraint(c);
			break;
		}
		
		case IntConstraintsParser.LE:{
			System.out.println("Less Than Equal To");
			c = Choco.leq(lhs, rhs);
			model.addConstraint(c);
			break;
		}
		
		case IntConstraintsParser.GE:{
			System.out.println("Greater Than Equal To");
			c = Choco.geq(lhs, rhs);
			model.addConstraint(c);
			break;
		}
		
		case IntConstraintsParser.EQ:{
			System.out.println("Equal To");
			c = Choco.eq(lhs, rhs);
			model.addConstraint(c);
			break;
		}
		
		case IntConstraintsParser.NEQ:{
			System.out.println("Not Equal To");
			c = Choco.neq(lhs, rhs);
			model.addConstraint(c);
			break;
		}			
}

		System.out.println("lhs " + ctx.lhs.getText() + " rhs " + ctx.rhs.getText() + " op " + ctx.op.getText());
			
		return c;
	}
	
	public Object visitBinaryArithm(BinaryArithmContext ctx){
		System.out.println("     VISITBINARY    ");
		System.out.println(ctx.getText());
		String name = ctx.getText();
		System.out.println("Name " + name);
		System.out.println("lhs " +ctx.lhs.getText());
		System.out.println("op "+ctx.op.getText());
		System.out.println(ctx.op.getType());
		System.out.println("rhs "+ctx.rhs.getText());
	
		System.out.println("Name and child0 " +name + " " + ctx.getChild(0));
		IntegerExpressionVariable lhs = (IntegerExpressionVariable) visit(ctx.lhs);
		IntegerExpressionVariable rhs = (IntegerExpressionVariable) visit(ctx.rhs);

		IntegerExpressionVariable res = null;
		
		//Switch case for binary arithm context code
		switch(ctx.op.getType()){
		
		case IntConstraintsParser.ADD:{
			System.out.println("Add");
			res = Choco.plus(lhs, rhs);
			break;
			}
		case IntConstraintsParser.SUB:{
			System.out.println("Sub");
			res = Choco.minus(lhs, rhs);
			break;
			}
		
		case IntConstraintsParser.DIV:{
			System.out.println("Div");
			res = Choco.div(lhs, rhs);
			break;
			}
		
		case IntConstraintsParser.MULT:{
			System.out.println("Mult");
			res = Choco.mult(lhs, rhs);
			break;
			}
		
		case IntConstraintsParser.MOD:{
			System.out.println("Mod");
			res = Choco.mod(lhs, rhs);
			break;
		}
		
		}
		
		return res;
}
	
	public Object visitBinaryBitwise(BinaryBitwiseContext ctx){
		//TODO: Find methods in Choco for inclusive or, shift left, shift right, shift zero to leftmost
		//Description of bitwise operators 
		//https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html
		Object res = null;
		IntegerVariable lhs = (IntegerVariable) visit(ctx.lhs);
		IntegerVariable rhs = (IntegerVariable) visit(ctx.rhs);

		//Switch case for binary bitwise context, this all are bite wise operators 
		switch(ctx.op.getType()){
		case IntConstraintsParser.AND:{
			//'&'
			//TODO: Needs to be passed 1 Constraint or IntegerVariable
			System.out.println("And");
			//res = Choco.and();
			break;
		}
		
		case IntConstraintsParser.OREXL:{
			//'^'
			//TODO: Pass and IntegerVariable 
			System.out.println("Exclusive Or");
			//res = Choco.xor(arg0, arg1)
			break;
		}
		
		case IntConstraintsParser.ORINCL:{
			//'|'
			//TODO: Find inclusive or function in Choco 
			System.out.println("Mod");
			//res = Choco.
			break;
		}
		
		case IntConstraintsParser.SHL:{
			//TODO: Shift left 
			System.out.println("Mod");
			//res = Choco.(lhs, rhs);
			break;
		}
		
		case IntConstraintsParser.SHR:{
			//TODO: Shift Right
			System.out.println("Mod");
			//res = Choco.mod(lhs, rhs);
			break;
		}
		
		case IntConstraintsParser.SHRU:{
			//TODO: Shift zero to the leftmost position 
			System.out.println("Mod");
			//res = Choco.mod(lhs, rhs);
			break;
		}
		
		}
		return res; 
	}
	
}


//m.addConstraint(Choco.eq(Choco.plus(lhs2, 0), 2));
//model.addConstraint(Choco.gt(Choco.plus((IntegerExpressionVariable) x, 1), 2));
//IntegerVariable constant = VarSelectorFactory.fixed(val, solver);
//Unused code or old code from choco 4.1.1
//Constraint result = IntConstraintFactory.arithm(lhs, "-", rhs, ctx.op.getText(), 0);
//Object child = visit(ctx.getChild(0));
//Object lhs = visit(ctx.lhs.getChild(0));
//Object rhs = visit(ctx.rhs.getChild(0)); 
//IntVar x = VarSelectorFactory.bounded(name, -1000, 1000, solver);
//solver.read(model);
//solver.solve();
//Integer solvedValue = solver.getVar(x).getVal();
//System.out.println(solvedValue);
//vars.put(name, solvedValue);
//Constraint exp = Choco.gt(lhs , rhs);
//System.out.println("VBA Result " +result);
/*if (ctx.op.getType() == IntConstraintsParser.ADD){
	IntVar x = VariableFactory.bounded(name, -1000, 1000, solver);
	solver.post(ICF.arithm(x,">",Integer.parseInt(ctx.rhs.getText())));
	solver.findAllSolutions();
}*/
//vars.put(name, );
//Object lhs = visit(ctx.lhs.getChild(0));
//Object rhs = visit(ctx.rhs.getChild(0));
//Object ret = visit(ctx.getChild(0));
//IntegerVariable constant = VarSelectorFactory.fixed(val, solver);
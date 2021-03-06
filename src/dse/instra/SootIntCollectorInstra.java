package dse.instra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SootIntCollectorInstra {
       
	public static String currentMethod = "";
	public static String argsToMethod = "";
	
	// set of maps that hold the symbolic values of the variables
	// Map 1 for locals:  MethodContext -> localVar -> SymbolicVal
	public static Map<String,String> localMap = new HashMap<String, String>();
	// Map 2 for static fields: FieldSignature -> Symbolic Val
	public static Map<String, String> staticFieldMap = new HashMap<String, String>();
	// Map 3 for instance fields: Object -> Field -> SymbolicVal
	public static Map<Object, Map<String, String>> instanceFieldMap = new IdentityHashMap<Object, Map<String, String>>();
	
	//public static String ret="";
	public static String retVal="";

	//count of symbolic value
	public static int symbCount = 0;
	//Condition count for line number
	
	
	//for constraint handling
	public static String previousCondition="";
	public static int previousOutcome = 0;
	public static List<String> conditions = new ArrayList<String>();
	public static List<Integer> condLinNumber = new ArrayList<Integer>();
	
	/**
	 * This method is called after an update to a memory location 
	 * @param lhs - memory location which is updated
	 * @param op1 - a mem location or a value 
	 * @param op2 - a mem location or a value if any
	 * @param op - an operator if any
	 */
	public static void updateLocal(String lhs, String op1, String op2, String op){		
		System.out.println(currentMethod + " uL," + lhs + "," + op1 + "," + op2 + "," + op);
                //logger.info(currentMethod + " uL," + lhs + "," + op1 + "," + op2 + "," + op);
		//System.out.println("UL:"+currentMethod + " uL," + lhs + "," + op1 + "," + op2 + "," + op); //nazmul
		System.out.flush();
		
		String lhsVar = currentMethod+"_"+lhs;
		String op1Var = currentMethod+"_"+op1;
		//check if it is a constant 
		boolean op1Const = isInt(op1);
		String op1Val;
		//Op1 is not a int
		if(op1Const){
			op1Val = op1;
			//System.out.println("UM:1"+op1Val);//nazmul
		} else {
			op1Val = localMap.get(op1Var);
			//System.out.println("UM:2"+op1Val);//nazmul
			if(op1Val == null){
				System.out.println("WARNING: cannot find local, assigning symbolic. Make sure it's ok");
				System.out.println("Map " + localMap);
//				op1Val ="p"+String.valueOf(symbCount);
//				symbCount++;
			}
		}
		//System.out.println("op1Val " + op1Val);
		if(op=="" && op2==""){
			// a simple assignment
			localMap.put(lhsVar, op1Val);
			//System.out.println("UM:3");//nazmul
		} else if (op2==""){
			// the negation
			localMap.put(lhsVar, "("+op+op1Val+")");
			//System.out.println("UM:4");//nazmul
		} else {
			// full binary expression
			String op2Var = currentMethod+"_"+op2;
			boolean op2Const = isInt(op2);
			String op2Val;
			//System.out.println("UM:5");//nazmul
			if(op2Const){
				op2Val = op2;
				//System.out.println("UM:6");//nazmul
			} else {
				op2Val = localMap.get(op2Var);
				//System.out.println("UM:7");//nazmul
			}
			localMap.put(lhsVar, "("+op1Val+op+op2Val+")");
		}
		
	}

	public static void assignLocal(String lhs, Object o, String field){
		System.out.println("aL," + lhs + ", " + ((o==null)? o : o.getClass()) + ", " + field);
		//System.out.println("ASSIGNLOCAL:"+"aL," + lhs + ", " + ((o==null)? o : o.getClass()) + ", " + field);//nazmul
		if(o == null){
			// it is static field
			localMap.put(currentMethod+"_" + lhs, staticFieldMap.get(field));
			//System.out.println("ASSIGNLOCAL:1");//nazmul
		} else {
			System.out.println(instanceFieldMap.get(o));
			String val = instanceFieldMap.get(o).get(field);
			System.out.println(currentMethod+"_" + lhs + " putting " + val);
			// it is an instance field
			localMap.put(currentMethod+"_" + lhs, instanceFieldMap.get(o).get(field));
			//System.out.println("ASSIGNLOCAL:2");//nazmul

		}
		//System.out.println(localMap);
	}

	/**
	 * This method is called after the branch result calculation
	 * @param loc - the location of the conditional statement
	 * @param rhs - the right hand side of the conditional statement
	 * @param lhs - the left hand side of the conditional statement
	 * @param op - the relational operator
	 * @param result - the result of the conditional statement. If true leave the op as it is, if false then negate op.
	 */
	public static void condition(String rhs, String lhs, String op, int result, int lineNum){
		System.out.println("Line num: "+lineNum+" CONDITION:"+"c," + lhs + "," + rhs + "," + op + "," + result );//nazmul
		System.out.println("c," + lhs + "," + rhs + "," + op + "," + result );
		System.out.println("Map " + localMap);
		System.out.println("CurrMethod "+ currentMethod);
		System.out.flush();
		String currentCondition = rhs+op+lhs;
		
		/*If the previous condition is the same as current condition and 
		 * previous outcome is 1 or has been executed already and
		 * result was passed as 0 
		 */
		if(SootIntCollectorInstra.previousCondition.equals(currentCondition) && 
				SootIntCollectorInstra.previousOutcome == 1 && result==0){
			//the false outcome has been taken therefore remove the previously added
			SootIntCollectorInstra.conditions.remove(conditions.size()-1);
                        SootIntCollectorInstra.condLinNumber.remove(condLinNumber.size()-1);
			//System.out.println("CONDITION:1");//nazmul
		}
		
		//get the symbolic values
		if(rhs.equals(null)){
			System.out.println("RHS is null before evaluation!");
			//System.out.println("CONDITION:2");//nazmul
		}
		String rhsVal; 
		
		if (isInt(rhs)){
			rhsVal = rhs;
			//System.out.println("CONDITION:3");//nazmul
		}  else {
			//Returns the value from the map based on key 
			rhsVal=localMap.get(currentMethod+"_"+rhs);
			//System.out.println("CONDITION:4");//nazmul
		}
		
		if(lhs.equals(null)){
			System.out.println("LHS is null before evaluation!");
			//System.out.println("CONDITION:5");//nazmul
		}
		String lhsVal;
		
		if (isInt(lhs) ){
			lhsVal = lhs;
			//System.out.println("CONDITION:6");//nazmul
		} 
		else {
			//Returns the value from the map based on key 
			lhsVal=localMap.get(currentMethod+"_"+lhs);
			//System.out.println("CONDITION:7");//nazmul
		}
		
		//Null is being printed because lhsVal has never been "put" in the localMap
		System.out.println(currentMethod+"_ " + lhs + " and " + lhsVal);
		System.out.println(currentMethod+"_ " + rhs + " and " + rhsVal);
		//add to the constraint list
		String condition = lhsVal + op + rhsVal;
		if(result == 0){
			System.out.println("Result was 0");
			condition = "!("+condition+")";
			//System.out.println("CONDITION:8");//nazmul
		}
		//Add condition and linNumber to respective List
		SootIntCollectorInstra.conditions.add(condition);
		SootIntCollectorInstra.condLinNumber.add(lineNum);
		//update the previous conditions
		previousCondition = currentCondition;
		previousOutcome = result;
		System.out.println();
		//System.out.println("************************");
		for(String c : conditions){
			System.out.println("Line Num: "+lineNum+": Conditions " + c);
		}
		//System.out.println("************************");
	}
	
	private static boolean isInt(String s){
		try{
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException nfe)
		{
			return false;
		}
	}
	
	public static void idenStmt(String currMethod, String var){
		currentMethod = currMethod;
		//create a new symbolic variable
		String symbVal = "p"+ symbCount;
		symbCount++;
		localMap.put(currentMethod+"_"+var, symbVal);
		//System.out.println(currentMethod+"_"+var+"|"+symbVal); //nazmul
	}

	public static void beforeInvoke(List<String> args, String toMethod){
		//append with the current method signature
		for(int i=0; i < args.size(); i++){
			if(!isInt(args.get(i))){
				args.set(i, currentMethod+"_" + args.get(i));
			}
		}
		
		SootIntCollectorInstra.conditions = args;
		System.out.println(currentMethod + " Args passed " + args + " to " + toMethod);
		//this method only called when args are not empty
		argsToMethod = toMethod;
		//System.out.println("Locals Map " + localMap);
	}
	
	public static void returnMethod(String retVar){
		for( int i=0 ; i< conditions.size(); i++)
		System.out.println("Condition:"+ conditions.get(i) + ", Unit:" +condLinNumber.get(i));
		
	}
}


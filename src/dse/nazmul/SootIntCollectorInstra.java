package dse.nazmul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import soot.toolkits.graph.pdg.ConditionalPDGNode;


public class SootIntCollectorInstra {
       
	public static String currentMethod = "";
	public static String argsToMethod = "";
        
        public static boolean DEBUG_ON = true;
        public static boolean HANDLE_ARRAY = true;
	
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
        
        public static int sequenceNo = 0;
	
	
	//for constraint handling
	public static String previousCondition="";
	public static int previousOutcome = 0;
	public static List<String> conditions = new ArrayList<String>();
	public static List<Integer> condLinNumber = new ArrayList<Integer>();
        public static List<ConditionStatement> newConditions = new ArrayList<ConditionStatement>();
	public static Vector<ConditionMetrics> conditionHolder = new Vector<ConditionMetrics>();
        public static HashMap<Integer, ConditionStatement> conditionSaver = new HashMap<Integer, ConditionStatement>();
        public static HashMap<Integer, ConditionStatement> lineCounter = new HashMap<Integer, ConditionStatement>();
	//public static HashMap<int,ConditionStatement> conditionSaver = new HashMap<int,ConditionStatement>();
	
        public static void  resetAll()
        {
            currentMethod = "";
            argsToMethod = "";
            localMap.clear();
            staticFieldMap.clear();
            instanceFieldMap.clear();
            retVal = "";
            symbCount = 0;
            previousCondition="";
            previousOutcome = 0;
            conditions.clear();
            condLinNumber.clear();
            newConditions.clear();
            conditionHolder.clear();
            conditionSaver.clear();
            lineCounter.clear();
            sequenceNo = 0;
        }
        
	/**
	 * This method is called after an update to a memory location 
	 * @param lhs - memory location which is updated
	 * @param op1 - a mem location or a value 
	 * @param op2 - a mem location or a value if any
	 * @param op - an operator if any
	 */
	public static void updateLocal(String lhs, String op1, String op2, String op,String method,int lineNo){
            sequenceNo++;
            currentMethod = method;
            if(DEBUG_ON)
            {
                System.out.println("FOUND UPDATE_LOCAL:seq"+sequenceNo+"Line:"+lineNo);
                System.out.println("Collector :"+currentMethod+"-> updateLocal() Start*******");
		System.out.println(currentMethod + " uL," + lhs + "," + op1 + "," + op2 + "," + op);  //6FEB
    	//	System.out.println("UL:"+currentMethod + " uL," + lhs + "," + op1 + "," + op2 + "," + op); //nazmul
            }
                System.out.flush();
		
		String lhsVar = currentMethod+"_"+lhs;
		String op1Var = currentMethod+"_"+op1;
                
                boolean op1HasArrayNotation = false;
                String op1Prefix = "";
                String op1Infix = "";
                
                if(HANDLE_ARRAY && op1.contains("["))
                {
                    System.out.println("Has array notation");
                    op1Prefix = op1.substring(0,op1.indexOf("["));
                    System.out.println(op1Prefix);
                    op1Prefix = currentMethod+"_"+op1Prefix;
                    op1Infix = op1.substring(op1.indexOf("[")+1,op1.indexOf("]"));
                    System.out.println(op1Infix);
                    op1Infix = currentMethod+"_"+op1Infix;
                    op1HasArrayNotation = true;
                }
		//check if it is a constant 
		boolean op1Const = isInt(op1);
		String op1Val;
		//Op1 is not a int
		if(op1Const)
                {
                    op1Val = op1;
                    if(DEBUG_ON){ System.out.println("UL:1"+op1Val); }
		} 
                else 
                {
                    if(HANDLE_ARRAY && op1HasArrayNotation)
                    {
                        op1Val = localMap.get(op1Prefix)+"["+localMap.get(op1Infix)+"]";
                    }
                    else
                    {
                        op1Val = localMap.get(op1Var); 
                    }
                    if(DEBUG_ON){ System.out.println("UL:2"+op1Val); }
			if(op1Val == null)
                        {
				//System.out.println("WARNING: cannot find local, assigning symbolic. Make sure it's ok");  6FEB
				if(DEBUG_ON){ System.out.println("Map " + localMap); } //          6FEB
//				op1Val ="p"+String.valueOf(symbCount);
//				symbCount++;
			}
		}
                if(DEBUG_ON){ System.out.println("op1Val " + op1Val); }
		if(op=="" && op2==""){
			// a simple assignment
			localMap.put(lhsVar, op1Val);
                        if(DEBUG_ON){ System.out.println("UM:3");} 
		} else if (op2==""){
			// the negation
			localMap.put(lhsVar, "("+op+op1Val+")");
                        if(DEBUG_ON){ System.out.println("UM:4");}
		} else {
			// full binary expression
			String op2Var = currentMethod+"_"+op2;
			boolean op2Const = isInt(op2);
			String op2Val;
			if(DEBUG_ON){ System.out.println("UM:5");}
			if(op2Const){
				op2Val = op2;
				if(DEBUG_ON){ System.out.println("UM:6");}
			} else {
				op2Val = localMap.get(op2Var);
				if(DEBUG_ON){ System.out.println("UM:7");}
			}
                        if(DEBUG_ON){ System.out.println("LOCAL:"+lhsVar+":("+op1Val+op+op2Val+")");}
			localMap.put(lhsVar, "("+op1Val+op+op2Val+")");
		}
                if(DEBUG_ON){ 
                    System.out.println("Map " + localMap);
                    System.out.println("staticFieldMap:"+staticFieldMap);
                    System.out.println("instanceFieldMap:"+instanceFieldMap);
                    System.out.println("localMap:"+localMap);
                } //
                if(DEBUG_ON){ System.out.println("Collector :"+currentMethod+"-> updateLocal() End*******");}
	}

	public static void assignLocal(String lhs, Object o, String field){
		//System.out.println("aL," + lhs + ", " + ((o==null)? o : o.getClass()) + ", " + field);  6FEB
                sequenceNo++;
		if(DEBUG_ON){
                    System.out.println("FOUND ASSIGN_LOCAL:seq"+sequenceNo);
                    System.out.println("ASSIGNLOCAL:"+"aL," + lhs + ", " + ((o==null)? o : o.getClass()) + ", " + field);}
		if(o == null){
			// it is static field
			localMap.put(currentMethod+"_" + lhs, staticFieldMap.get(field));
			if(DEBUG_ON){ System.out.println("ASSIGNLOCAL:1");}
		} else {
			if(DEBUG_ON){ System.out.println(instanceFieldMap.get(o)); }//  6FEB
			String val = instanceFieldMap.get(o).get(field);
			if(DEBUG_ON){ System.out.println(currentMethod+"_" + lhs + " putting " + val);}//  6FEB
			// it is an instance field
			localMap.put(currentMethod+"_" + lhs, instanceFieldMap.get(o).get(field));
			if(DEBUG_ON){ System.out.println("ASSIGNLOCAL:2");}//nazmul

		}
		if(DEBUG_ON){ System.out.println(localMap);}
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
            sequenceNo++;
            if(DEBUG_ON){ 
            System.out.println("FOUND CONDITION:seq"+sequenceNo+"Line:"+lineNum);
            System.out.println("Line num: "+lineNum+" CONDITION:"+"c," + lhs + "," + rhs + "," + op + "," + result );//nazmul
		System.out.println("c," + lhs + "," + rhs + "," + op + "," + result );
		System.out.println("Map " + localMap);
		System.out.println("CurrMethod "+ currentMethod);    
        }
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
			SootIntCollectorInstra.newConditions.remove(newConditions.size()-1);
                        SootIntCollectorInstra.condLinNumber.remove(condLinNumber.size()-1);
			if(DEBUG_ON){ System.out.println("CONDITION:1");}//nazmul
		}
		
		//get the symbolic values
		if(rhs.equals(null)){
			if(DEBUG_ON){ System.out.println("RHS is null before evaluation!"); 
			System.out.println("CONDITION:2");//nazmul
                                }
		}
		String rhsVal; 
		
		if (isInt(rhs)){
			rhsVal = rhs;
			if(DEBUG_ON){ System.out.println("CONDITION:3");}//nazmul
		}  else {
			//Returns the value from the map based on key 
			rhsVal=localMap.get(currentMethod+"_"+rhs);
			if(DEBUG_ON){ System.out.println("CONDITION:4");}//nazmul
		}
		
		if(lhs.equals(null)){
			if(DEBUG_ON){ System.out.println("LHS is null before evaluation!"); 
			System.out.println("CONDITION:5");//nazmul
                        }
		}
		String lhsVal;
		
		if (isInt(lhs) ){
			lhsVal = lhs;
			if(DEBUG_ON){ System.out.println("CONDITION:6");}//nazmul
		} 
		else {
			//Returns the value from the map based on key 
			lhsVal=localMap.get(currentMethod+"_"+lhs);
			if(DEBUG_ON){ System.out.println("CONDITION:7");}//nazmul
		}
		
		//Null is being printed because lhsVal has never been "put" in the localMap
		if(DEBUG_ON){ System.out.println(currentMethod+"_ " + lhs + " and " + lhsVal);  }// 6FEB
		//System.out.println(currentMethod+"_ " + rhs + " and " + rhsVal);   6FEB
		//add to the constraint list
		String condition = lhsVal + op + rhsVal;
		ConditionStatement conditionStatement = new ConditionStatement(lineNum, lhsVal , op , rhsVal);
		if(result == 0){
			if(DEBUG_ON){ System.out.println("Result was 0"); } //6FEB
			condition = "!("+condition+")";
                        conditionStatement.negateCondition();
			if(DEBUG_ON){ System.out.println("CONDITION:8");}//nazmul
		}
		//Add condition and linNumber to respective List
		SootIntCollectorInstra.conditions.add(condition);
		SootIntCollectorInstra.newConditions.add(conditionStatement);
		SootIntCollectorInstra.condLinNumber.add(lineNum);
		
		//update the previous conditions
		previousCondition = currentCondition;
		previousOutcome = result;
		if(DEBUG_ON){ System.out.println();   //6FEB
		System.out.println("************************");
                }
		for(String c : conditions){
			if(DEBUG_ON){ System.out.println("Line Num: "+lineNum+": Conditions " + c);  } // 6FEB
		}
                
		if(DEBUG_ON){ System.out.println("************************");}
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
	
	public static void idenStmt(String currMethod, String var,int lineNo){
            sequenceNo++;
            if(DEBUG_ON){ 
            System.out.println("FOUND IDEN_STMT:seq"+sequenceNo+"Line:"+lineNo);
            System.out.println("Collector :"+currentMethod+"-> idenStmt() Start*******");}
		currentMethod = currMethod;
		//create a new symbolic variable
		String symbVal = "p"+ symbCount;
		symbCount++;
		localMap.put(currentMethod+"_"+var, symbVal);
        if(DEBUG_ON){ System.out.println("Method Name:"+currentMethod+"|Variable Name:"+var+"|Symbol :"+symbVal); //nazmul
               System.out.println("Collector :"+currentMethod+"-> idenStmt() End*******");
        }
	}

	public static void beforeInvoke(List<String> args, String toMethod){
		//append with the current method signature
		for(int i=0; i < args.size(); i++){
			if(!isInt(args.get(i))){
				args.set(i, currentMethod+"_" + args.get(i));
			}
		}
		
		SootIntCollectorInstra.conditions = args;
		if(DEBUG_ON){ System.out.println(currentMethod + " Args passed " + args + " to " + toMethod); }//6FEB
		//this method only called when args are not empty
		argsToMethod = toMethod;
		if(DEBUG_ON){ System.out.println("Locals Map " + localMap);}
	}
	
	public static void returnMethod(String curMethod,String retVar){
                sequenceNo++;
                if(DEBUG_ON){
                    System.out.println("FOUND RETURN_METHOD:seq:"+sequenceNo+retVar+"|"+curMethod);
                }
                    String[] tokens = retVar.split("[ ]");
                    if(!tokens[1].isEmpty())
                    {
                        retVal = localMap.get(curMethod+"_"+tokens[1]);
                   if(DEBUG_ON){      
                        System.out.println(retVal); 
                        System.out.println(localMap);
                        System.out.println("Collector :"+currentMethod+"-> returnMethod() Start*******");
                   }   
                   }
                    
		for( int i=0 ; i< conditions.size(); i++)
		{
	if(DEBUG_ON){ 	System.out.println("Condition:"+ conditions.get(i) + ", Unit:" +condLinNumber.get(i)); } //6FEB
			SootIntCollectorInstra.conditionHolder.add(new ConditionMetrics(conditions.get(i),condLinNumber.get(i)));
			SootIntCollectorInstra.conditionSaver.put(condLinNumber.get(i),newConditions.get(i));
                      if(lineCounter.get(condLinNumber.get(i)) == null)
                      {
                         // String condDesc = newConditions.get(i).raw()+"~1";
                          ConditionStatement cs = newConditions.get(i);
                          cs.setBase(new ConditionStatement(cs.getLineNo(),cs.leftHand,cs.operand,cs.rightHand));
                          //System.out.println("D:"+condDesc);
                          lineCounter.put(condLinNumber.get(i), cs);
                      }
                      else
                      {
                         //String condtionDescriptor = lineCounter.get(condLinNumber.get(i));
                         ConditionStatement cs = lineCounter.get(condLinNumber.get(i));
//                          String[] tokens = condtionDescriptor.split("[~]");
//                          int num = Integer.parseInt(tokens[1]);
//                          num++;
//                          condtionDescriptor = tokens[0]+"~"+num;
//                          System.out.println("D:"+condtionDescriptor);
                           cs.hasLoop = true;
                          lineCounter.put(condLinNumber.get(i),cs);
                      }
		}
                if(DEBUG_ON)
                {
                    System.out.println(lineCounter.size());
                    System.out.println(lineCounter);
                }
                new ShutdownInstra().doPreShutdownJob();
        if(DEBUG_ON){ System.out.println("Collector :"+currentMethod+"-> returnMethod() End*******");  }
	}
}

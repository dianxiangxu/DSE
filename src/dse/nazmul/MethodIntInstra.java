package dse.nazmul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import soot.Body;
import soot.BodyTransformer;
import soot.ByteType;
import soot.G;
import soot.IntType;
import soot.Local;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.ShortType;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Trap;
import soot.Type;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Unit;
import soot.UnitBox;
import soot.Value;
import soot.jimple.BinopExpr;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.internal.AbstractNegExpr;
import soot.jimple.internal.JAddExpr;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.internal.JDivExpr;
import soot.jimple.internal.JGeExpr;
import soot.jimple.internal.JIdentityStmt;
import soot.jimple.internal.JIfStmt;
import soot.jimple.internal.JInterfaceInvokeExpr;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.internal.JLeExpr;
import soot.jimple.internal.JMulExpr;
import soot.jimple.internal.JNewExpr;
import soot.jimple.internal.JReturnStmt;
import soot.jimple.internal.JReturnVoidStmt;
import soot.jimple.internal.JSpecialInvokeExpr;
import soot.jimple.internal.JStaticInvokeExpr;
import soot.jimple.internal.JSubExpr;
import soot.jimple.internal.JVirtualInvokeExpr;
import soot.jimple.internal.JimpleLocal;
import soot.util.Chain;
import soot.util.HashChain;


/*An abstract class which acts on a Body. 
 * This class provides a harness and acts as an interface for classes that wish to transform a Body. 
 * Subclasses provide the actual Body transformation implementation.
*/
public class MethodIntInstra extends BodyTransformer {
	//Classes that we want to use 
	static SootClass collectorClass;
	static SootClass listClass;
	static SootClass listVector;
	static SootClass arrayListClass;
	
	//Methods that will be used from above classes
	static SootMethod beforeInvoke;
	static SootMethod afterInvoke;
	static SootMethod handleMethod;
	static SootMethod isInt;
	static SootMethod condition;
	static SootMethod updateLocal;
	static SootMethod idenStmt;
	static SootMethod arrayListInit;
	static SootMethod addList;
	static SootMethod returnMethod;
	
	
	
	static{
		//Loading basic classes to the scene
		Scene.v().loadBasicClasses();
		//Adding classes to the scene
		collectorClass = Scene.v().getSootClass("dse.nazmul.SootIntCollectorInstra");
		arrayListClass = Scene.v().getSootClass("java.util.ArrayList");
		listClass = Scene.v().getSootClass("java.util.List");
		listVector = Scene.v().getSootClass("java.util.Vector");
		
		//Getting methods from the above classes
		isInt = collectorClass.getMethod
				("boolean isInt(java.lang.String)");
		condition = collectorClass.getMethod
				("void condition(java.lang.String,java.lang.String,java.lang.String,int,int)");
		updateLocal = collectorClass.getMethod
				("void updateLocal(java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String)");  //22 May 2018 added 1 more String for current method
		idenStmt = collectorClass.getMethod("void idenStmt(java.lang.String,java.lang.String)");
		returnMethod = collectorClass.getMethod("void returnMethod(java.lang.String)");
		arrayListInit = arrayListClass.getMethod("void <init>()");
		addList = listClass.getMethod("boolean add(java.lang.Object)");
		
	
	}
	//This Hash Map will store the three parts of a if statement condition
	//Left hand side, operator, and right hand side i0 <= 2 
	
	HashMap<String,String> ifStuff = new HashMap<String,String>();
	//These are the three strings that hold that data 
	String lhs = null, op = null, rhs = null;
	//Decide how to know what gets added before or to after
	
	private int localVarCount = 0;
	String currentMethod = "";
	
	@Override
	protected void internalTransform(Body b, String phaseName, Map options) {
		//Two maps that while contain units we iterate through 
		//Decide if we instrument before of after
		Map<Unit, Chain<Unit>> insertMapBefore = new HashMap<Unit, Chain<Unit>>();
		Map<Unit, Chain<Unit>> insertMapAfter = new HashMap<Unit, Chain<Unit>>();
		
		SootMethod method = b.getMethod();
		currentMethod = method.method().getName().toString();
		System.out.println("Method Name "+ method);
		SootClass className = method.getDeclaringClass();
		System.out.println("Class Name "+ className.toString());
		System.out.println(" ");
		
		//Skip if it is the main method 
		if( //method.getName().contains("main") ||
				className.getName().contains("SootIntCollectorInstra")   ||
				className.getName().contains("ShutdownInstra") ||
				className.getName().contains("ConditionMetrics")||
				className.getName().contains("ConditionStatement")
                        ){
			return;
		}
		
		
		if(className.getName().contains("instraa. ")){
			System.out.println(className.toString());
			}
		
		//List of parameters 
		List<Value> paramsVars= new ArrayList<Value>();
		Iterator<Unit> iter= b.getUnits().iterator();
		Iterator<Unit> iterForPrinting= b.getUnits().iterator();
		List<Local> newLocalVars = new ArrayList<Local>();
		
		Unit lastIdentity = null;
		Unit lastReturn = null;
		while(iter.hasNext()){
			Unit u = iter.next();
                        if(u instanceof JIdentityStmt ){    // //it is just tracking the last identity statement
				lastIdentity = u;
			}
			//if(u instanceof JAssignStmt ){    //JIdentityStmt //it is just tracking the last identity statement
				lastReturn = u;
			//}
		}
		
		iterForPrinting = b.getUnits().iterator();
                
		System.out.println("MARK0:");   //nazmul
                //
                int count =0;
                
		while(iterForPrinting.hasNext()){
			Unit u = iterForPrinting.next();
                        count++;
			System.out.println("Unit : "+count+" : "+u.toString());
                        //Tags tags = null;
//                       List<UnitBox> ubx = u.getUnitBoxes();
//                       if(u.fallsThrough())
//                       {   
//                           System.out.println("Falls through");
//                           for (int i = 0; i < ubx.size(); i++) {
//                            System.out.print("Target status: "+ubx.get(i).isBranchTarget());
//                            }
//                       }
//                       else
//                       {    
//                           System.out.println("Does not Fall through");
//                            for (int i = 0; i < ubx.size(); i++) {
//                            System.out.print("Target status: "+ubx.get(i).isBranchTarget());
//                            }
//                           
//                       }
                       
		}
		System.out.println("MARK0: ENDS");//nazmul
		
		
		iter = b.getUnits().iterator();
		int stmtCount = 1;
		//Iterating through the units in the current method. Units are statements or a line of code
		while(iter.hasNext()){
			Unit u = iter.next();
			//If this is the last unit instrument unit before it's executed 
			if(!iter.hasNext()){
                                if(!method.getName().contains("main"))
                                {
                                    addToMap(insertMapBefore, u, returnMethod(u.toString()));
                                    System.out.println("Last Item :Count:("+stmtCount+ "):"+u.toString());
                                }
                        }
			System.out.println("Current Boxes Of U: "+  u.getUseBoxes());
			System.out.println("This Unit is a " +u.getClass().getSimpleName());
			
			//Case of Jimple Assign Statement 
			if(u instanceof JAssignStmt){
				JAssignStmt jAssignStmt= (JAssignStmt) u;
				System.out.println("Jassignment:Count: ("+stmtCount+ "):"+ u.toString());
				Value leftOperand = jAssignStmt.getLeftOp();
				Value rightOperand = jAssignStmt.getRightOp();
				System.out.println("This Left Op is a " +jAssignStmt.getLeftOp().getClass().getSimpleName());
				System.out.println("This Right Op is a " +jAssignStmt.getRightOp().getClass().getSimpleName());
				System. out.println("CURR METHOD:"+currentMethod);   //added 22 May 2018 for current method
				if(leftOperand instanceof JimpleLocal){
					addToMap(insertMapBefore,u,updateLocal(currentMethod,leftOperand.toString(),rightOperand));  ////added 22 May 2018 for current method
					System.out.println("ATP JAS for JL");
				}
			}
			//Case of Jimple Identity Statement 
			if(u instanceof JIdentityStmt){
				JIdentityStmt jIdentityStmt= (JIdentityStmt) u;
				System.out.println( "Identity:Count:("+stmtCount+ "):"+u.toString());
				Value leftOp = jIdentityStmt.getLeftOp();
				paramsVars.add(leftOp);
                                System. out.println("CURR METHOD:"+currentMethod);     //added 22 May 2018 for current method
				
				System.out.println("This Left Op is a " +jIdentityStmt.getLeftOp().getClass().getSimpleName());
				System.out.println("This Right Op is a " +jIdentityStmt.getRightOp().getClass().getSimpleName());
				
				addToMap(insertMapAfter,lastIdentity,identityStmt(currentMethod, leftOp.toString()));  //added 22 May 2018 for current method
				
				
			}
			//Case of Jimple If Statement 
			if(u instanceof JIfStmt){
				JIfStmt jIfStmt= (JIfStmt) u;		
				System.out.println("Ifstmt:Count:("+stmtCount+ "):"+jIfStmt);			
				System.out.println("The conditions is a " +jIfStmt.getCondition().getClass().getSimpleName());
				System.out.println("The target is a " +jIfStmt.getTarget().getClass().getSimpleName());
				System. out.println("CURR METHOD:"+currentMethod);    //added 22 May 2018 for current method
				String ifStmt = jIfStmt.getConditionBox().getValue().toString();
				System.out.println("If Condition: " + ifStmt);
							
				HashMap<String,String> ifConds = stringConditionToString(jIfStmt);
				
				Value ifCond = jIfStmt.getCondition();
				Stmt ifTarget = jIfStmt.getTarget();
				//ifTarget.
				
				//addToMap(insertMapBefore, u, updateLocal(ifCond.toString(),ifCond));
				addToMap(insertMapBefore, u, condition(ifConds.get(rhs), ifConds.get(lhs), ifConds.get(op), 1, stmtCount));
				addToMap(insertMapAfter, u, condition(ifConds.get(rhs), ifConds.get(lhs), ifConds.get(op), 0, stmtCount));				
			}
//			else if (u instanceof JIfStmt){
//				// track the condition that use integer values
//				JIfStmt ifStmt = (JIfStmt) u;
//				Value condition = ifStmt.getCondition();
//				//System.out.println(((AbstractJimpleIntBinopExpr)condition).getOp1() + " with " + ((((AbstractJimpleIntBinopExpr)condition).getOp1()).getType() instanceof IntType));
//				BinopExpr be = (BinopExpr) condition;
//				Value op1 = be.getOp1();
//				//System.out.println(op1 + " " + op1.getType());
//				
//				if(isAnyIntType(op1.getType())){
//				//generate both true and false outputs
//					String lhs = op1.toString();
//					String rhs = be.getOp2().toString();
//					String op = be.getSymbol().trim();
//					Chain<Unit> trueOutcome = conditional(rhs, lhs, op, 1);
//					//insertMapBefore.put(u, trueOutcome);
//					addToTheMap(insertMapBefore,u,trueOutcome);
//					Chain<Unit> falseOutcome = conditional(rhs, lhs, op, 0);
//					//insertMapAfter.put(u, falseOutcome);
//					addToTheMap(insertMapAfter,u,falseOutcome);
//				}
			
			//Case of Jimple Invoke Statement 
			if(u instanceof JInvokeStmt){		
				//Not added to localMap
				JInvokeStmt jInvokeStmt= (JInvokeStmt) u;
				System.out.println("Invooke:Count:("+stmtCount+ "):"+jInvokeStmt);
				InvokeExpr invokeExpr = jInvokeStmt.getInvokeExpr();
                                System. out.println("CURR METHOD:"+currentMethod);        //added 22 May 2018 for current method
				addToMap(insertMapBefore, u, processInvokeExpr(jInvokeStmt.getInvokeExpr(), newLocalVars));
				System.out.println("ATP JIS ");
			}
			//Case of Jimple Return Statement 
			if(u instanceof JReturnStmt){
				JReturnStmt jReturnStmt= (JReturnStmt) u;
				System.out.println("Return:Count: ("+stmtCount+ "):"+u.toString());
				Value op = ((JReturnStmt) u).getOp();
                                System. out.println("CURR METHOD:"+currentMethod);         //added 22 May 2018 for current method
				addToMap(insertMapBefore, u, updateLocal(currentMethod,jReturnStmt.toString(),op));
				System.out.println("ATP JRS ");
			}
			//Case of Jimple Local Variable  
			if(u instanceof JimpleLocal){
				//Not added to localMap
				localVarCount ++;
				JimpleLocal jimpleLocal= (JimpleLocal) u;
				System.out.println("jimple local:Count:("+stmtCount+ "):"+u.toString());
                                System. out.println("CURR METHOD:"+currentMethod);       //added 22 May 2018 for current method
				String name  =((JimpleLocal) u).getName();
				Integer num =((JimpleLocal) u).getNumber();
				Type type = ((JimpleLocal) u).getType();
			}
			System.out.println(" ");
			stmtCount++;
		} 
		//Done Iterating
//		if(method.getSignature().contains("void main(java.lang.String[])")){
//			System.out.println("main found:"+method.getSignature());
//			Chain<Unit> val = insertMapBefore.get(lastReturn);
//                        Chain<Unit> sdu = shutdown(newLocalVars);
//			val.addAll(sdu);
//			insertMapBefore.put(lastReturn, val);
//			
//			//addToMap(insertMapAfter,lastIdentity,sdu);   //nazmul 12 January 2018
//		}
		//Print out control flow graph for the current body(b) of code 
//		CFG myCFG = new CFG(b);
//		System.out.println("CFG");
//		System.out.println(myCFG.toString());
				
		//add new local variables
		b.getLocals().addAll(newLocalVars);
		//add new statements
		
		PatchingChain<Unit> stmt = b.getUnits();
		//Iterate through statements in map insert before 
		for(Entry<Unit, Chain<Unit>> ul : insertMapBefore.entrySet()){
			stmt.insertBefore(ul.getValue(), ul.getKey());
		}

		//iterate through statements in map insert after
		for(Entry<Unit, Chain<Unit>> ul : insertMapAfter.entrySet()){
			if(ul.getKey() == null){
				stmt.insertBefore(ul.getValue(), stmt.getFirst());
			} else {
				stmt.insertAfter(ul.getValue(), ul.getKey());
			}
		}
	}
	
	//Get args from parameters then add to array list.
	/*
	 * 1. Get arguments from parameters 
	 * 2. Add String or Int Constant to array list 
	 * 3. Make Jimple Static Invoke Expression
	 * 4. Make Jimple Invoke Statement
	 * 5. Reference it to it's method in SootIntCollectorInstra so if will take the arguments in the array list and execute
	 *    the method
	 * 6. Return the Chain 
	 */
	private Chain<Unit> beforeInvoke(){
		Chain<Unit> beforeChain = new PatchingChain<Unit>(new HashChain<Unit>());
		return beforeChain;
	}

	private Chain<Unit> afterInvoke(Value val){
		Chain<Unit> afterChain = new PatchingChain<Unit>(new HashChain<Unit>());
		List<Value> args = new ArrayList();
		args.add(StringConstant.v(val.toString()));
		
		JStaticInvokeExpr newExpr = new JStaticInvokeExpr(afterInvoke.makeRef(),args);
		afterChain.add(new JInvokeStmt(newExpr));
		return afterChain;
		}

	private Chain<Unit> handleMethod(SootMethod sm){
		Chain<Unit> handleChain = new PatchingChain<Unit>(new HashChain<Unit>());
		List<Value> args = new ArrayList();
		args.add(StringConstant.v(sm.getSignature()));
		
		JStaticInvokeExpr newExpr = new JStaticInvokeExpr(handleMethod.makeRef(),args);
		handleChain.add(new JInvokeStmt(newExpr));
		return handleChain;
	}
	
	private Chain<Unit> condition(String rhs, String lhs, String op, int result, int linNum){
		System.out.println("condition called : "+rhs+"|"+lhs+"|"+op+"|"+result +"|"+linNum);
		Chain<Unit> condChain = new PatchingChain<Unit>(new HashChain<Unit>());
		List<Value> args = new ArrayList<Value>();
		args.add(StringConstant.v(rhs));
		args.add(StringConstant.v(lhs));
		args.add(StringConstant.v(op));
		args.add(IntConstant.v(result));
		args.add(IntConstant.v(linNum));
		JStaticInvokeExpr newExpr = new JStaticInvokeExpr(condition.makeRef(), args);
		condChain.add(new JInvokeStmt(newExpr));
		return condChain; 
	}
	
	private Chain<Unit> conditional(String rhs, String lhs, String op, int result){
		Chain<Unit> conditionalChain = new PatchingChain<Unit>(new HashChain<Unit>());
		List<Value> args = new ArrayList<Value>();
		args.add(StringConstant.v(rhs));
		args.add(StringConstant.v(lhs));
		args.add(StringConstant.v(op));
		args.add(IntConstant.v(result));
		JStaticInvokeExpr newExpr = new JStaticInvokeExpr(condition.makeRef(), args);
		JInvokeStmt newStmt= new JInvokeStmt(newExpr);
		conditionalChain.add(newStmt);
		return conditionalChain;
	}
	
	

	private Chain<Unit> updateLocal(String currMethod, String lhs, Value rhs){      ////added 22 May 2018 for current method
		System.out.println("update local called : "+lhs+"|"+rhs);
		Chain<Unit> updateLocalChain = new PatchingChain<Unit>(new HashChain<Unit>());
		String op1="", op2="", op="";
		
		if(rhs instanceof AbstractNegExpr){
			AbstractNegExpr ane = (AbstractNegExpr) rhs;
			op1 = ane.getOp().toString();
			op = "-";
		} else if (rhs instanceof BinopExpr){
			// a binary expression
			BinopExpr be = (BinopExpr) rhs;
			op1 = be.getOp1().toString();
			op2 = be.getOp2().toString();
			op = be.getSymbol().trim();
		} else {
			// an assignment x := y, or x := 5
			op1 = rhs.toString();
		}
		List<Value> args = new ArrayList<Value>();
		args.add(StringConstant.v(lhs));
		args.add(StringConstant.v(op1));
		args.add(StringConstant.v(op2));
		args.add(StringConstant.v(op));
		args.add(StringConstant.v(currMethod));       //added 22 May 2018 for current method
		
		if(op1 != ""){
			System.out.println("Op1 "+op1);
		}
		
		if(op != ""){
			System.out.println("Op "+op);
		}
		
		if(op2 != ""){
			System.out.println("Op2 "+op2);
		}
				
		JStaticInvokeExpr newExpr = new JStaticInvokeExpr(updateLocal.makeRef(), args);
		updateLocalChain.add(new JInvokeStmt(newExpr));
		return updateLocalChain;
		
	}
	
	private Chain<Unit> identityStmt(String currMethod, String var) {
		System.out.println("Identity Statement Called : "+currMethod + "|"+var);
		Chain<Unit> identityStmtChain = new PatchingChain<Unit>(new HashChain<Unit>());
		List<Value> args = new ArrayList<Value>();
		args.add(StringConstant.v(currMethod));
		args.add(StringConstant.v(var));
		JStaticInvokeExpr newExpr = new JStaticInvokeExpr(idenStmt.makeRef(),args);
		JInvokeStmt newStmt = new JInvokeStmt(newExpr);
		identityStmtChain.add(newStmt);
		return identityStmtChain;
	}
	
	private Chain<Unit> returnMethod(String retVar){
		System.out.println("Return Statement called : "+retVar);
		Chain<Unit> returnChain = new PatchingChain<Unit>(new HashChain<Unit>());
		List<Value> args = new ArrayList<Value>();
		args.add(StringConstant.v(retVar));
		JStaticInvokeExpr newExpr = new JStaticInvokeExpr(returnMethod.makeRef(), args);
		Unit newStmt = new JInvokeStmt(newExpr);
		returnChain.add(newStmt);
		
		return returnChain;
	}

	private void addToMap(Map<Unit, Chain<Unit>> map,       Unit key,     Chain<Unit> value){
		
		if(map.containsKey(key)){
			//get the previously added units
			System.out.println("key : "+key.toString() );
			Chain<Unit> previousUnits = map.get(key);
			System.out.println("previous " + previousUnits);
			System.out.println("new " + value);
			//add them to the new chain
			previousUnits.addAll(value);
		} else {
			System.out.println("new key : "+key.toString() );
			System.out.println("new value : "+value );
			map.put(key, value);
		}
		
	}
	
	private HashMap<String,String> stringConditionToString(JIfStmt currIfStmt){
		//Get the if condition from the statement
		String ifStmt = currIfStmt.getConditionBox().getValue().toString();
		
		//Array that will store each character from the above String 
		ArrayList<Character> dst = new ArrayList<Character>();
		
		//Iterate through if statement and add to Array List
		for(int i=0; i < ifStmt.length() ; i++){
			dst.add(ifStmt.charAt(i));
		}
		
		System.out.println("Character Array List " + dst);
		
		//Iterate through the character 
		Iterator<Character> iter2= dst.iterator();
		
		//Build a string from the characters
		for(int x =0; x < 3; x++){
			StringBuilder sb = new StringBuilder();
			while(iter2.hasNext()){
				Object element = iter2.next();
				//As long as the character is not a white space add to the string
				if(!element.toString().equals(" ")){
					sb.append(element.toString());
				}
				//When it does encounter a white space break out the while loop and restart i0" ">=" "2
				else{
					break;
				}
			}
			//Lhs of the if condition 
			if(x==0){
			lhs=sb.toString();
			}
			//Op of the if condition 
			if(x==1){
				op=sb.toString();
			}
			//Rhs of the if condition 
			if(x==2){
				rhs=sb.toString();
			}
			//Add the three variables to the HashMap and make them accessible by their respective name 
			for(int i=0; i < 1 ; i++){
				ifStuff.put(lhs, lhs);
				ifStuff.put(op, op);
				ifStuff.put(rhs, rhs);
				
		}
		
		
	}

		return ifStuff;
	}

	private Chain<Unit> createListOfStrings(List<Value> args, Local listLocal){
		Chain<Unit> units = new PatchingChain<Unit>(new HashChain<Unit>());
		// create a new object of type ArrayList
		JNewExpr newList = new JNewExpr(RefType.v("java.util.ArrayList"));
		// do the assignment
		Unit u = new JAssignStmt(listLocal, newList);
		// add to the chain
		units.add(u);
		// special invoke of List <init>
		JSpecialInvokeExpr invoke = new JSpecialInvokeExpr(listLocal,arrayListInit.makeRef(),new ArrayList()); 
		u = new JInvokeStmt(invoke);
		// add to the chain
		units.add(u);
		// add arguments to the list
		for(Value arg : args){
			List<Value> addVal = new ArrayList<Value>();
			addVal.add(StringConstant.v(arg.toString()));
			JInterfaceInvokeExpr add = new JInterfaceInvokeExpr(listLocal,addList.makeRef(), addVal);
			u = new JInvokeStmt(add);
			units.add(u);
		}
		return units;
	}

	private boolean isAnyIntType(Type t){
		return t instanceof IntType || t instanceof ByteType || t instanceof ShortType;
	}

	private Chain<Unit> shutdown(List<Local> newLocalVars){
		Chain<Unit> shutdownChain = new PatchingChain<Unit>(new HashChain<Unit>());

			SootClass shutdownClass = Scene.v().getSootClass("dse.nazmul.ShutdownInstra");
			Local runtime =  new JimpleLocal("runtime", RefType.v("java.lang.Runtime"));
			Local shutdown =  new JimpleLocal("shutdown", RefType.v("dse.nazmul.ShutdownInstra"));
			newLocalVars.add(runtime);
			newLocalVars.add(shutdown);
			//do assignments of these locals
			SootMethod runtimeMethod = Scene.v().getMethod("<java.lang.Runtime: java.lang.Runtime getRuntime()>");
			JStaticInvokeExpr runtimeInvokeMethod = new JStaticInvokeExpr(runtimeMethod.makeRef(), new ArrayList<Value>());
			Unit runtimeAssign = new JAssignStmt(runtime, runtimeInvokeMethod);

			JNewExpr shutdownNew = new JNewExpr(RefType.v("dse.nazmul.ShutdownInstra"));
			Unit shutdownAssign = new JAssignStmt(shutdown, shutdownNew);

			SootMethod shutdownInit = shutdownClass.getMethod("void <init>()");
                        //SootMethod shutdownInit = shutdownClass.getMethod("void <doJob>()");
			//Scene.v().getMethod("<inst.Shutdown: void <init>()>");
			JSpecialInvokeExpr classShutdown = new JSpecialInvokeExpr(shutdown, shutdownInit.makeRef(), new ArrayList<Value>());
			Unit specInvokeShutdown = new JInvokeStmt(classShutdown);

			SootMethod runtimeAddHook = Scene.v().getMethod("<java.lang.Runtime: void addShutdownHook(java.lang.Thread)>");
			List<Value> args = new ArrayList<Value>();
			args.add(shutdown);
			JVirtualInvokeExpr invokeRuntimeAddHook = new JVirtualInvokeExpr(runtime, runtimeAddHook.makeRef(), args);
			Unit stmtInvokeRuntimeAddHook = new JInvokeStmt(invokeRuntimeAddHook);


			shutdownChain.addFirst(stmtInvokeRuntimeAddHook);
			shutdownChain.addFirst(specInvokeShutdown);
			shutdownChain.addFirst(shutdownAssign);
			shutdownChain.addFirst(runtimeAssign);

		return shutdownChain;
	}

	private Chain<Unit> processInvokeExpr(InvokeExpr expr, List<Local> localVars){
		Chain<Unit> invokeUnits = new PatchingChain<Unit>(new HashChain<Unit>());
		//check if this is method in the library class or not
		SootMethod sm = expr.getMethod();
		System.out.println("Invoking \t\t " + sm + " " + expr.getArgs() + " " + sm.getParameterTypes() + " " + sm.getDeclaringClass().isApplicationClass());
		
		// skip instrumentation of any kind if this is not an application class
		if(sm.getDeclaringClass().isApplicationClass()){
			List<Value> args = expr.getArgs();
			List<Value> addArgs = new ArrayList<Value>();
			int argIndex = 0;
			
			for(Value arg : args){
				System.out.println(arg + " " + arg.getType().getClass());

				if(isAnyIntType(sm.getParameterType(argIndex))){
					addArgs.add(arg);
				}
				argIndex++;
			}
			
			if(!addArgs.isEmpty()){				
				// create a variable for list
				Local listLocal = new JimpleLocal("list"+localVarCount,RefType.v("java.util.ArrayList"));
				localVarCount++;
				localVars.add(listLocal);
				invokeUnits.addAll(createListOfStrings(addArgs, listLocal));
				// now invoke the static method
				List<Value> args1 = new ArrayList<Value>();
				args1.add(listLocal);
				args1.add(StringConstant.v(sm.getName()));
				JStaticInvokeExpr newExpr = new JStaticInvokeExpr(beforeInvoke.makeRef(), args1);
				Unit u = new JInvokeStmt(newExpr);
				invokeUnits.add(u);
				
			}
		}
		return invokeUnits;
	}

}






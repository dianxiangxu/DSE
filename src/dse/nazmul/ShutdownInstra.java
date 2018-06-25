package dse.nazmul;

import dse.nazmul.replay.Utility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/*A thread is a thread of execution in a program. 
 * The Java Virtual Machine allows an application to have multiple threads of execution running concurrently.
 */
public class ShutdownInstra extends Thread {
	
	public void run(){
		System.out.println("Shutdown hook called");
		File tempFileAllLog = new File(Utility.tempFile);
		File outputFile = new File(Utility.outputFile);
		try{
			FileWriter fwTemp = new FileWriter(tempFileAllLog,true);
			FileWriter fwOutput = new FileWriter(outputFile,true);
			
			//Iterator it = SootIntCollectorInstra.conditionHolder.iterator();         //Vector
			Iterator it = SootIntCollectorInstra.conditionSaver.entrySet().iterator();  //HashMap
                        
			//ConditionMetrics cm;
			ConditionStatement cs;
			while(it.hasNext())
			{
				//cm = (ConditionMetrics)it.next();
				Map.Entry pair = (Map.Entry)it.next();
                                System.out.println(pair.getKey() + " = " );
                                cs = (ConditionStatement) pair.getValue();
                               //fw.write("Line Num:"+cm.getLineNumber()+" :"+cm.getConditionStatement()+"\n");
                                fwTemp.write("Line Num:"+pair.getKey()+" :"+cs+"\n");
                                fwOutput.write(cs.getLineNo()+"~"+cs.leftHand+"~"+cs.operand+"~"+cs.rightHand+"\n");
				fwTemp.flush();
				fwOutput.flush();
			}

			fwTemp.flush();
			fwTemp.close();
                        fwOutput.flush();
			fwOutput.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
        public void doPreShutdownJob(){
		System.out.println("Shutdown hook doJob called");
		File tempFileAllLog = new File(Utility.tempFile);
		File outputFile = new File(Utility.outputFile);
		try{
			FileWriter fwTempLog = new FileWriter(tempFileAllLog,true);
			FileWriter fwOutput = new FileWriter(outputFile,true);
			
			//Iterator it = SootIntCollectorInstra.conditionHolder.iterator();         //Vector
                        
                        Map<Integer, ConditionStatement> map = new TreeMap<Integer, ConditionStatement>(SootIntCollectorInstra.conditionSaver);
                        
			//Iterator it = SootIntCollectorInstra.conditionSaver.entrySet().iterator();  //HashMap
                        
                        Set set2 = map.entrySet();
                        Iterator it = set2.iterator();
                        
			//ConditionMetrics cm;
			ConditionStatement cs;
                        ConditionStatement conditionMeta = null;
			while(it.hasNext())
			{
				//cm = (ConditionMetrics)it.next();
				Map.Entry pair = (Map.Entry)it.next();
                               // System.out.println(pair.getKey() + " = " );
                                cs = (ConditionStatement) pair.getValue();
                                conditionMeta = (ConditionStatement)SootIntCollectorInstra.lineCounter.get(pair.getKey());
                                if(conditionMeta.hasLoop)
                                {
                                    fwTempLog.write("Line Num:"+pair.getKey()+"~"+conditionMeta+"~"+conditionMeta.hasLoop+"\n");
                                    fwOutput.write(conditionMeta.getLineNo()+"~"+conditionMeta.baseCondition.leftHand+"~"+conditionMeta.baseCondition.operand+"~"+conditionMeta.baseCondition.rightHand+"~"+conditionMeta.hasLoop+"\n");
                                }
                                else
                                {
                                    fwTempLog.write("Line Num:"+pair.getKey()+"~"+cs+"~"+conditionMeta+"~"+conditionMeta.hasLoop+"\n");
                                    fwOutput.write(cs.getLineNo()+"~"+cs.leftHand+"~"+cs.operand+"~"+cs.rightHand+"~"+conditionMeta.hasLoop+"\n");
                                }
                                fwTempLog.flush();
				fwOutput.flush();
			}
                        fwTempLog.write("RETURN:"+SootIntCollectorInstra.retVal+"\n");
                        fwTempLog.write("*****************\n");
                        clearSootCollector();
                       
        		fwTempLog.flush();
			fwTempLog.close();
                        fwOutput.flush();
			fwOutput.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e)
                {
                    e.printStackTrace();
                }
                
	}
        
        public void clearSootCollector()
        {
             SootIntCollectorInstra.resetAll();
        }
}
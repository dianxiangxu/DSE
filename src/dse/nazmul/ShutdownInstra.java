package dse.nazmul;

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
	public static String tempFile = "temp.txt";
        public static String outputFile = "output.txt";
	public void run(){
		System.out.println("Shutdown hook called");
		File out = new File(tempFile);
		File out2 = new File(outputFile);
		try{
			FileWriter fw = new FileWriter(out,true);
			FileWriter fw2 = new FileWriter(out2,true);
			
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
                                fw.write("Line Num:"+pair.getKey()+" :"+cs+"\n");
                                fw2.write(cs.getLineNo()+"~"+cs.leftHand+"~"+cs.operand+"~"+cs.rightHand+"\n");
				fw.flush();
				fw2.flush();
			}
//			for(String c: SootIntCollectorInstra.conditions){
//				fw.write(c+"\n");
//				fw.flush();
//			}
			fw.flush();
			fw.close();
                        fw2.flush();
			fw2.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
        public void doJob(){
		System.out.println("Shutdown hook doJob called");
		File out = new File(tempFile);
		File out2 = new File(outputFile);
		try{
			FileWriter fw = new FileWriter(out,true);
			FileWriter fw2 = new FileWriter(out2,true);
			
			//Iterator it = SootIntCollectorInstra.conditionHolder.iterator();         //Vector
                        
                        Map<Integer, ConditionStatement> map = new TreeMap<Integer, ConditionStatement>(SootIntCollectorInstra.conditionSaver);
                        
			//Iterator it = SootIntCollectorInstra.conditionSaver.entrySet().iterator();  //HashMap
                        
                        Set set2 = map.entrySet();
                        Iterator it = set2.iterator();
                        
			//ConditionMetrics cm;
			ConditionStatement cs;
			while(it.hasNext())
			{
				//cm = (ConditionMetrics)it.next();
				Map.Entry pair = (Map.Entry)it.next();
                               // System.out.println(pair.getKey() + " = " );
                                cs = (ConditionStatement) pair.getValue();
                               //fw.write("Line Num:"+cm.getLineNumber()+" :"+cm.getConditionStatement()+"\n");
                                //fw.write("Line Num:"+pair.getKey()+" :"+cs+"\n");
                                //fw2.write(cs.leftHand+"~"+cs.operand+"~"+cs.rightHand+"\n");
				fw.write("Line Num:"+pair.getKey()+" :"+cs+"\n");
                                fw2.write(cs.getLineNo()+"~"+cs.leftHand+"~"+cs.operand+"~"+cs.rightHand+"\n");
				
                                fw.flush();
				fw2.flush();
			}
                        clearSootCollector();
                       
//			for(String c: SootIntCollectorInstra.conditions){
//				fw.write(c+"\n");
//				fw.flush();
//			}
			fw.flush();
			fw.close();
                        fw2.flush();
			fw2.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        public void clearSootCollector()
        {
             SootIntCollectorInstra.resetAll();
        }
}
package dse.instra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


/*A thread is a thread of execution in a program. 
 * The Java Virtual Machine allows an application to have multiple threads of execution running concurrently.
 */
public class ShutdownInstra extends Thread {
	
	public void run(){
		System.out.println("Shutdown hook called");
		//File out = new File("temp");
		File out = new File("C:/applications/temp.txt");
		try{
			FileWriter fw = new FileWriter(out,true);
			for(String c: SootIntCollectorInstra.conditions){
				fw.write(c+"\n");
				fw.flush();
			}
			fw.flush();
			fw.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
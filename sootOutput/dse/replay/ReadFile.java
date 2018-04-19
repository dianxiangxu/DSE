package dse.replay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadFile {
	Map<String, String> tc = new HashMap<String,String>();

	public void read(String fileName) throws FileNotFoundException{
		File file = new File(fileName);
		Scanner scanner = new Scanner(new FileReader(file));
		String key = "";
		String value ="";
		
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			//if line matches ---number--- then this is the key
			if(line.startsWith("---")){
				//add the previous key/value pair
				if(!key.equals("")){
					tc.put(key, value);
					value = "";
				}
				//get the number
				key = line.split("---")[1];
				//System.out.println("key is " + key);
			} else if(!line.contains("null")){
				//add to the value
				value += line + "\n";
				//System.out.println("Value " + value);
			}
		}
		//the map is 
		//System.out.println(tc);
	}

}

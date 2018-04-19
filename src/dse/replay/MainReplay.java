package dse.replay;

import java.io.IOException;
import java.util.Map.Entry;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class MainReplay {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//read file and parse into map of TC->constraint
		
		ReadFile rf = new ReadFile();
		System.out.println("File " + args[0]);
		rf.read(args[0]);
		//Get the map
		System.out.println("Map " + rf.tc);
		
		
		for(Entry<String, String> entry : rf.tc.entrySet()){
			// create a CharStream that reads from standard input
			//System.out.println("TC " + entry.getKey());
			ANTLRInputStream input = new ANTLRInputStream(entry.getValue());
			// create a lexer that feeds off of input CharStream
			IntConstraintsLexer lexer = new IntConstraintsLexer(input);
			// create a buffer of tokens pulled from the lexer
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			// create a parser that feeds off the tokens buffer
			IntConstraintsParser parser = new IntConstraintsParser(tokens);
			ParseTree tree = parser.constraints(); // begin parsing at init rule
	
			//Z3Visitor testVis = new Z3Visitor();
			ChocoVisitor testVis = new ChocoVisitor();
			testVis.visit(tree);
			//System.out.println(testVis.known);
			//System.out.println(testVis.timeR);
			//System.out.println(testVis.timeN);
			//print out or write to the file
			/*if(!testVis.knownTaken.isEmpty()){
				//all lists are of the same length
				System.out.println("TC_" + entry.getKey());
				System.out.println(testVis.runningAverage);
				int size = testVis.knownTaken.size();
				for(int i=0; i < size; i++){
					System.out.println(i+1 + "\t" + testVis.knownTaken.get(i) + "\t" + testVis.knownNotTaken.get(i));
					//System.out.println(testVis.timeR.get(i)); 
				}
			}*/
		}
		//System.out.println(tree.toStringTree(parser)); // print LISP-style tree
		

	}
	

}

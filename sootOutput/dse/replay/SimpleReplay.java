package dse.replay;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class SimpleReplay {

	public static void main(String[] args) {
		String constraint = "p0 + 2 > p0 ";
		ANTLRInputStream input = new ANTLRInputStream(constraint);
		System.out.println("Input " + input);
		
		// create a lexer that feeds off of input CharStream
		IntConstraintsLexer lexer = new IntConstraintsLexer(input);
		
		// create a buffer of tokens pulled from the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		
		// create a parser that feeds off the tokens buffer
		IntConstraintsParser parser = new IntConstraintsParser(tokens);
		
		// begin parsing at init rule
		ParseTree tree = parser.constraints(); 
		System.out.println("parseTree " + tree.toStringTree());
		System.out.println(tree.toStringTree(parser));
		
	
		
		//Z3Visitor testVis = new Z3Visitor();
		ChocoVisitor testVis = new ChocoVisitor();
		testVis.visit(tree);
		
	}

}

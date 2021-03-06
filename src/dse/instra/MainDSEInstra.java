package dse.instra;

//import dse.instra.MethodIntInstra;
import soot.PackManager;// manages packs that contains various phases
import soot.Scene;// Manages all the Classes in a Scene
import soot.SootClass;// Class in Soot 
import soot.Transform; // (phaseName, singleton) needed for transformation

public class MainDSEInstra{
	
	public static void main(String[] args){
		
		String[] sootArgs=  {"-f", "c","--app","artifacts.Example1"};

		//adding runtime to the path
		Scene.v().setSootClassPath(Scene.v().getSootClassPath()+
		System.getProperty("path.separator")+
		System.getProperty("sun.boot.class.path")+
		System.getProperty("path.separator")+
		System.getProperty("user.dir")+"/src");
				
		//loading collector classes
		Scene.v().loadClassAndSupport("dse.instra.SootIntCollectorInstra");
		
		//loading List-related classes 
		Scene.v().loadClassAndSupport("java.util.ArrayList");
		Scene.v().loadClassAndSupport("java.util.List");
		
		//for shutdown hook
		Scene.v().loadClassAndSupport("dse.instra.ShutdownInstra");
		Scene.v().addBasicClass("java.lang.Runtime", SootClass.SIGNATURES);
		
		PackManager.v().getPack("jtp").add(new Transform("jtp.MethodInstrumenter", new MethodIntInstra()));
		System.out.println("Path" + Scene.v().getSootClassPath());
		soot.Main.main(sootArgs);
		
	}
	
}
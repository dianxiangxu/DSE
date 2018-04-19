package dse.nazmul;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.tagkit.StringTag;
import soot.tagkit.Tag;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

public class CFG {
	
	private List<Node> items;
	private List<Integer> itemIDs;
	private List<Node> branches;
	private UnitGraph ug;
	private Body body;
	
	public CFG(Body methodBody){
		ug= new ExceptionalUnitGraph(methodBody);
		body = methodBody;
		items = new ArrayList<Node>();
		itemIDs = new ArrayList<Integer>();
		branches = new ArrayList<Node>();
		setItems();
	}
	
	private void setItems(){
		Chain<Unit> units = body.getUnits();
		Iterator<Unit> iterator = units.iterator();
		while(iterator.hasNext()){
			Unit u = iterator.next();			
			makeNode(u);
		}
		for(Node n:items){
			setTransitions(n);
		}
		setEndBranches();
		setTrueAndFalse();
		setSuperPreds();
		//findOrs();
		//findAnds();
	}
	
	private Node makeNode(Unit u){
		Node newNode = new Node(u);
		items.add(newNode);
		itemIDs.add(u.hashCode());
		return newNode;
	}
	
	private void setTransitions(Node n){
		Unit u = n.getUnit();
		List<Unit> preds = ug.getPredsOf(u);
		List<Unit> succs = ug.getSuccsOf(u);
		List<Node> myPreds = new ArrayList<Node>();
		List<Node> mySuccs = new ArrayList<Node>();
		for(Unit pred: preds){
				myPreds.add(items.get(itemIDs.indexOf(pred.hashCode())));
		}
		for(Unit succ: succs){
				mySuccs.add(items.get(itemIDs.indexOf(succ.hashCode())));
		}
		n.setPredsAndSuccs(myPreds, mySuccs);
	}
	private void setEndBranches(){
		for(Node n: items){
			if(n.branches()){
				branches.add(n);
				if(n.getEndBranch() == null){
					n.setEndBranch(findEndBranch(n));
				}
			}
		}
	}
	
	private void setTrueAndFalse(){
		for(Node n: items){
			if(n.branches()){
				n.setTrue(n.getSuccs().get(1));
				n.setFalse(n.getSuccs().get(0).getSuccs().get(0));
			}
		}
	}
	
	private void setSuperPreds(){
		for(Node n: items){
			if(n.multPreds()){
				n.setSuperPreds(findSuperPreds(n));
			}
		}
	}
	
	private List<Node> findSuperPreds(Node n){
		List<Node> superPreds = new ArrayList<Node>();
		for(Node b: branches){
			if(b.getFalse().equals(n) || b.getTrue().equals(n)){
				superPreds.add(b);
			}
		}
		if(superPreds.size()<1){
			Node sNode = findBranch(n);
			superPreds.add(sNode);
		}
		return superPreds;
		
	}
	
	private Node findEndBranch(Node n){
		int lastPred = getNodeNum(n.getPreds().get(n.getPreds().size()-1));
		if(lastPred>getNodeNum(n)){
			n.getUnit().addTag(new StringTag("Loop"));
			return items.get(lastPred+1);
		}
		if(n.branches()){
			if(getNodeNum(n.getSuccs().get(0))>getNodeNum(n.getSuccs().get(1))){
				n.getUnit().addTag(new StringTag("Loop"));
				return n.getSuccs().get(0).getSuccs().get(0);				
			}
		}
		Node succ = n.getSuccs().get(0);
		if(succ.getPreds().size()>1){
			return succ;
		}
		if(succ.branches()){
			if(succ.getEndBranch()==null){
				succ.setEndBranch(findEndBranch(succ));
			}
			return succ.getEndBranch();
		}
		return findEndBranch(succ);
	}		
	
	public Node findMutant(){
		for(Node n: items){
			List<Tag> tags = n.getUnit().getTags();
			for(Tag t: tags){
				if(t.toString().equals("Mutant")){
					return n;
				}
			}
		}
		System.out.println("No mutants found.");
		return null;
	}
	
	public List<Node> findMutants(){
		List<Node> mutants = new ArrayList<Node>();
		for(Node n: items){
			List<Tag> tags = n.getUnit().getTags();
			for(Tag t: tags){
				if(t.toString().equals("Mutant")){
					mutants.add(n);
				}
			}
		}
		return mutants;
	}
	
	private Node findBranch(Node find){
		for(int i = 0; i<branches.size(); i++){
			if(branches.get(i).getEndBranch().equals(find)){
				return branches.get(i);
			}
		}
		return null;
	}
	
	private Node getHead(){
		return items.get(0);
	}
	
	private Node getTail(){
		return items.get(items.size()-1);
	}
	
	public Node findNode(Unit u){
		for(Node n: items){
			if(n.getUnit().equals(u)) {
				return n;
			}
		}
		System.out.println("Node not found.");
		return null;
	}
	
	public List<List<Node>> pathToNode(Node n){
		List<List<Node>> possiblePaths = new ArrayList<List<Node>>();
		List<Node> curPath = new ArrayList<Node>();
		possiblePaths.add(curPath);
		pathToPred(n,curPath,possiblePaths);
		return possiblePaths;
		
	}
	
	private void pathToPred(Node n,List<Node> curPath,List<List<Node>> allPaths){
		Node cur = n;
		curPath.add(0,cur);
		int pathLength = curPath.size();
		if(cur!=getHead()){
			List<Node> preds = cur.getPreds();
			if(preds.size()>1){
				List<Node> superPreds = cur.getSuperPreds();
				for(int i = 0;i<superPreds.size();i++){
					if(i==0){
						cur = superPreds.get(i);
						pathToPred(cur,curPath,allPaths);
					}
					else{
						List<Node> newPath = new ArrayList<Node>();
						List<Node> past = curPath.subList(curPath.size() - pathLength,curPath.size());
						for(Node p :past){
							newPath.add(p);
						}	
						allPaths.add(newPath);
						cur = superPreds.get(i);
						pathToPred(cur,newPath,allPaths);
					}
				}
			}
			else{
				cur = preds.get(0);
				pathToPred(cur,curPath,allPaths);
			}
		}
			
		return;
	}

	public List<Node> getItems(){
		return items;
	}
	
	public String toText(List<List<Node>> list){
		String out = "";
		List<List<Node>> paths = list;
		int length = paths.get(0).size();
		for(int i = 1; i<paths.size();i++){
			if(paths.get(i).size()>length){
				length = paths.get(i).size();
			}
		}
		for(int i = 0; i<length; i++){
			for(List<Node> path: paths){
				String toAdd = "";
				if(!(i>=path.size())){
					toAdd = getNodeNum(path.get(i)) + " " + path.get(i).getUnit() + "";
				}
				out += String.format("%-35s", toAdd);
			}
			out += "\n";
		}
		return out;
	}
	
	public int getNodeNum(Node find){
		return items.indexOf(find);
	}
	
	public String toString(){
		String out = "";
		for(Node n: items){
			out += "Unit:("+items.indexOf(n) +") " + n.getUnit() + "\n";
			List<Node> preds = n.getPreds();
			for(int i = 0;i<preds.size();i++){
				if(i == 0){
					out += "\tPreds: ";
				}
				out += items.indexOf(preds.get(i));
				if(i<preds.size()-1 ) {
					out += ",";
				}
				else {
					out += "\n";
				}
			}
			List<Node> succs = n.getSuccs();
			for(int i = 0;i<succs.size();i++){
				if(i ==0){
					out += "\tSuccs: ";
				}
				out += items.indexOf(succs.get(i));
				if(i<succs.size()-1 ) {
					out += ",";
				}
			}
			if(n.branches()){
			out += "\nTrue Node: " + items.indexOf(n.getTrue()) +"\n";
			out += "False Node: " + items.indexOf(n.getFalse()) +"\n";
			out += "End Branch: " + items.indexOf(n.getEndBranch()) +"\n";
			}
			if(n.multPreds()){
				for(Node s: n.getSuperPreds()){
					out += "\nSuper Pred: " + items.indexOf(s) + "\n";
				}
			}
			else{
					out +="\n";
			}
		}
		return out;
	}
	
	public List<List<Node>> randomPath(){
		Node cur = getHead();
		List<List<Node>> output = new ArrayList<List<Node>>();
		List<Node> path = new ArrayList<Node>();
		path.add(cur);
		while(cur != getTail()){
			int rand = (int) (Math.random()*cur.getSuccs().size());
			cur = cur.getSuccs().get(rand);
			path.add(cur);
		}
		output.add(path);
		return output;
	}
	
	public List<List<Node>> fixPath(List<List<Node>> path, BranchNode fix){
		Node badNode = fix.getNode();
		List<Node> newPath = new ArrayList<Node>();
		List<Node> oldPath = path.get(0);
		for(Node n: oldPath){
			newPath.add(n);
			if(n.equals(badNode)){
				break;
			}
		}
		Node next;
		if(fix.getPath() == true){
			next = badNode.getSuccs().get(1);
		}
		else{
			next = badNode.getSuccs().get(0);
		}
		List<Node> randPath = randomRest(next);
		for(Node n: randPath){
			newPath.add(n);
		}
		List<List<Node>> myPath = new ArrayList<List<Node>>();
		myPath.add(newPath);
		return myPath;
		
	}
	
	public List<Node> randomRest(Node n){
		Node cur = n;
		List<Node> path = new ArrayList<Node>();
		path.add(cur);
		while(cur != getTail()){
			int rand = (int) (Math.random()*cur.getSuccs().size());
			cur = cur.getSuccs().get(rand);
			path.add(cur);
		}
		return path;
	}
	
	public String printPath(List<List<BranchNode>> branches){
		if(branches.get(0).isEmpty()){
			return "No branches to take\n";
		}
		else{
			return format(branches);
		}
	}
	
	public List<List<BranchNode>> convertToBranches(List<List<Node>> path){
		List<List<BranchNode>> branches = new ArrayList<List<BranchNode>>();
		for(int i = 0; i<path.size(); i++){
			List<Node> curPath = path.get(i);
			List<BranchNode> branch = new ArrayList<BranchNode>();
			for(int j = 0; j<curPath.size(); j++){
				if(curPath.get(j).branches() && j<curPath.size()-1){
					Node cur = curPath.get(j);
					Node next = curPath.get(j+1);
					if(cur.getFalse().equals(next) || getNodeNum(next)-getNodeNum(cur)==1){
						BranchNode newBranch = new BranchNode(cur,false);
						branch.add(newBranch);
					}
					else if(cur.getTrue().equals(next)){
						BranchNode newBranch = new BranchNode(cur,true);
						branch.add(newBranch);
					}
				}
			}
			branches.add(branch);
		}
		return branches;
	}
	
	public String format(List<List<BranchNode>> branches){
		String out = "";
		List<List<BranchNode>> paths = branches;
		int length = paths.get(0).size();
		for(int i = 1; i<paths.size();i++){
			if(paths.get(i).size()>length){
				length = paths.get(i).size();
			}
		}
		for(int i = 0; i<length; i++){
			for(List<BranchNode> path: paths){
				String toAdd = "";
				if(!(i>=path.size())){
					String loop = "";
					if(path.get(i).getNode().loops()){
						loop = "(loop)";
					}
					toAdd = getNodeNum(path.get(i).getNode()) + "," + path.get(i).getPath() + " " + loop;
				}
				out += String.format("%-20s", toAdd);
			}
			out += "\n";
		}
		return out;
	}
	
	public BranchNode findProblem(List<List<BranchNode>> possiblePaths, List<List<BranchNode>> path){
		List<BranchNode> pathTaken = path.get(0);
		int wrong = possiblePaths.get(0).size() +1;
		int index = -1;
		for(List<BranchNode> possible: possiblePaths){
			int curWrong = compareBranchNodes(possible,pathTaken);
			if(curWrong<wrong){
				wrong = curWrong;
				index = possiblePaths.indexOf(possible);
			}
			if(wrong == 0){
				break;
			}
		}
		return findDifference(possiblePaths.get(index), pathTaken);
	}
	
	private int compareBranchNodes(List<BranchNode> correct, List<BranchNode> pathTaken){
		int wrong = 0;
		boolean failed = false;
		for(BranchNode c: correct){
			if(!failed){
				for(BranchNode b: pathTaken){
					if(getNodeNum(c.getNode()) == getNodeNum(b.getNode())){
						if(c.getPath() == b.getPath()){
							break;
						}
						else{
							if(b.getPath() == true && c.getNode().loops()){
								break;
							}
							wrong++;
							failed = true;
						}
					}
				}
			}
			else{
				wrong++;
			}
		}
		return wrong;
		
	}
	
	private BranchNode findDifference(List<BranchNode> correct, List<BranchNode> pathTaken){
		for(BranchNode c: correct){
			for(BranchNode b: pathTaken){
				if(getNodeNum(c.getNode()) == getNodeNum(b.getNode())){
					if(c.getPath() == b.getPath()){
						break;
					}
					else{
						if(b.getPath() == true && c.getNode().loops()){
							break;
						}
						return c;
					}
				}
			}
		}
		//System.out.println("No Difference");
		return null;
	}
	
	public List<List<BranchNode>> sortPaths(List<List<BranchNode>> paths){
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		int[] sorted = new int[paths.size()];
		List<List<BranchNode>> sortedPaths = new ArrayList<List<BranchNode>>();
		for(int i = 0; i<paths.size(); i++){
			sizes.add(paths.get(i).size());
			sorted[i] = paths.get(i).size();
		}
		Arrays.sort(sorted);
		for(int val: sorted){
			sortedPaths.add(paths.get(sizes.indexOf(val)));
		}
		return sortedPaths;
	}
	
	public List<List<BranchNode>> joinPaths(List<List<BranchNode>> path1, List<List<BranchNode>> path2){
		List<List<BranchNode>> paths = new ArrayList<List<BranchNode>>();
		if(path1.get(0).size()<1){
			for(List<BranchNode> path: path2){
				paths.add(path);
			}
			return paths;
		}
		if(path2.get(0).size()<1){
			for(List<BranchNode> path: path1){
				paths.add(path);
			}
			return paths;
		}
		for(List<BranchNode> possible1: path1){
			for(List<BranchNode> possible2: path2){
				List<BranchNode> combined = combinePath(possible1,possible2);
				if(combined.size()>0){
					paths.add(combined);
				}
			}
		}
		return paths;
	}
	
	private List<BranchNode> combinePath(List<BranchNode> path1, List<BranchNode> path2){
		List<BranchNode> newPath = new ArrayList<BranchNode>();
		for(BranchNode p1: path1){
			for(BranchNode p2: path2){
				if(p1.getNode().equals(p2.getNode())){
					if(p1.getPath() != p2.getPath()){
						if(p1.getNode().loops()){
							System.out.println("Loop");
							newPath.add(new BranchNode(p1.getNode(),true));
							newPath.add(new BranchNode(p1.getNode(),false));
						}
						else{
							return new ArrayList<BranchNode>();
						}
					}
					break;
				}
			}
			newPath.add(p1);
		}
		for(BranchNode p2: path2){
			boolean contained = false;
			for(BranchNode p1: path1){
				if(p2.getNode().equals(p1.getNode())){
					contained = true;
				}
			}
			if(!contained){
				newPath.add(p2);
			}
		}
		return sortPath(newPath);
	}
	
	private List<BranchNode> sortPath(List<BranchNode> path){
		ArrayList<Integer> nodeNum = new ArrayList<Integer>();
		int[] sorted = new int[path.size()];
		List<BranchNode> sortedPath = new ArrayList<BranchNode>();
		for(int i = 0; i<path.size(); i++){
			nodeNum.add(getNodeNum(path.get(i).getNode()));
			sorted[i] = getNodeNum(path.get(i).getNode());
		}
		Arrays.sort(sorted);
		for(int val: sorted){
			sortedPath.add(path.get(nodeNum.indexOf(val)));
		}
		return sortedPath;
	}
		
	/**
	public String branchers(){
		String out = "Ands: \n";
		for(Node n: branches){
			if(n.hasAnd()){
				out += "Unit:" + n.getUnit();
				for(Node a: n.getAnd()){
					out += "\t" + a.getUnit();
				}
				out +="\n";
			}
		}
		out += "Ors: \n";
		for(Node n: branches){
			if(n.hasOr()){
				out += "Unit:" + n.getUnit();
				for(Node o: n.getOr()){
					out += "\t" + o.getUnit();
				}
				out +="\n";
			}
		}
		return out;
	}
	
	public List<List<Node>> traverse(){
		List<List<Node>> possiblePaths = new ArrayList<List<Node>>();
		List<Node> curPath = new ArrayList<Node>();
		Node start = getHead();
		possiblePaths.add(curPath);
		pathToSucc(start,curPath,possiblePaths);
		return possiblePaths;
	}
	
	private List<Node> findBranches(Node find){
		List<Node> branchers = new ArrayList<Node>();
		for(int i = branches.size()-1; i>=0; i--){
			if(branches.get(i).getEndBranch().equals(find)){
				branchers.add(branches.get(i));
			}
		}
		return branchers;
	}
	private void pathToSucc(Node n,List<Node> curPath,List<List<Node>> allPaths){
		Node cur = n;
		curPath.add(cur);
		int pathLength = curPath.size();
		if(cur.getSuccs().size()>0){
			List<Node> succs = cur.getSuccs();
			if(succs.size()>1){
				for(int i = 0;i<succs.size();i++){
					if(i==0){
						cur = succs.get(i);
						pathToSucc(cur,curPath,allPaths);
					}
					else{
						List<Node> newPath = new ArrayList<Node>();
						List<Node> past = curPath.subList(0,pathLength);
						for(Node p :past){
							newPath.add(p);
						}	
						allPaths.add(newPath);
						cur = succs.get(i);
						pathToSucc(cur,newPath,allPaths);
					}
				}
			}
			else{
				cur = succs.get(0);
				pathToSucc(cur,curPath,allPaths);
			}
		}
			
		return;
	}
	
	public String printPath(List<Node> path){
		List<BranchNode> branches = new ArrayList<BranchNode>();
		for(int i = 0;i<path.size()-1;i++){
			if(path.get(i).branches() && i<path.size()-1){
				Node cur = path.get(i);
				Node next = path.get(i+1);
				boolean skip = false;
				for(int j = i; j<path.size()-1;j++){
					if(path.get(j).hasOr()){
						for(Node n: path.get(j).getOr()){
							if(n.equals(cur)){
								skip = true;
							}
						}
					}
					if(path.get(j).hasAnd()){
						for(Node n: path.get(j).getAnd()){
							if(n.equals(cur)){
								skip = true;
							}
						}
					}
				}
				if((cur.getFalse().equals(next) || getNodeNum(next)-getNodeNum(cur)==1) && !skip){
					BranchNode newBranch = new BranchNode(path.get(i),false);
					branches.add(newBranch);
				}
				else if(cur.getTrue().equals(next) && !skip){
					BranchNode newBranch = new BranchNode(path.get(i),true);
					branches.add(newBranch);
				}
			}
		}
		if(branches.size()<1){
			return "No branches to take";
		}
		else{
			String out = "";
			for(BranchNode b: branches){
				if(b.getNode().hasOr()){
					for(Node n: b.getNode().getOr()){
						if(getNodeNum(n)<getNodeNum(b.getNode())){
							if(n.hasAnd()){
								out += "(";
								for(Node i: n.getAnd()){
									if(b.getPath()){
										out += getNodeNum(i) + "," + b.getPath() + " && ";
									}
									else{
										out += getNodeNum(i) + "," + b.getPath() + " || ";
									}
								}
							}
							String hasAnd = "";
							if(n.hasAnd()){
								hasAnd = ")";
							}
							if(b.getPath()){
								out += getNodeNum(n) + "," + b.getPath() + hasAnd + " || ";
							}
							else{
								out += getNodeNum(n) + "," + b.getPath() + hasAnd + " && ";
							}
						}
					}
				}
				if(b.getNode().hasAnd()){
					for(Node n: b.getNode().getAnd()){
						if(n.hasOr()){
							out += "(";
							for(Node i: n.getOr()){
								if(b.getPath()){
									out += getNodeNum(i) + "," + b.getPath() + " || ";
								}
								else{
									out += getNodeNum(i) + "," + b.getPath() + " && ";
								}
							}
						}
						String hasOr = "";
						if(n.hasOr()){
							hasOr = ")";
						}
						if(b.getPath()){
							out+= getNodeNum(n) + "," + b.getPath() + hasOr + " && ";
						}
						else{
							out+= getNodeNum(n) + "," + b.getPath() + hasOr + " || ";
						}
					}
				}
				String loop = "";
				if(b.getNode().loops()){
					loop = " (loop)";
				}
				out += getNodeNum(b.getNode()) + "," + b.getPath() + loop+ "\n";
			}
			return out;
		}
	}
	
	public List<Node> quickFind(Node n){
		List<Node> path = new ArrayList<Node>();
		quickPred(n,path);
		return path;
	}
	
	private void quickPred(Node n, List<Node> path){
		path.add(0,n);
		if(n.equals(getHead())){
			return;
		}
		List<Node> preds = n.getPreds();
		if(preds.size() > 1){
			Node next = findBranch(n);
			if(next == null){
				if(getNodeNum(preds.get(1))>getNodeNum(n)){
					quickPred(preds.get(0),path);
				}
				else{
					for(int i = 1; i<preds.size(); i++){
						path.add(0,preds.get(i));
					}
					quickPred(preds.get(0),path);
				}
			}
			else{
				quickPred(next,path);
			}
		}
		else{
			quickPred(preds.get(0),path);
		}
		return;
	}
	private void findOrs(){
		for(Node n: branches){
			for(Node i: branches){
				if(getNodeNum(n) > getNodeNum(i) && n.getTrue().equals(i.getTrue())){
					n.addOr(i);
				}
			}
		}
	}
	
	private void findAnds(){
		for(Node n: branches){
			for(Node i: branches){
				if(getNodeNum(n) > getNodeNum(i) && n.getFalse().equals(i.getFalse())){
					n.addAnd(i);
				}
			}
		}
	}

	*/
	
}

class Node {

	private Unit curUnit;
	private List<Node> preds;
	private List<Node> succs;
	private boolean branch;
	private Node trueBranch;
	private Node falseBranch;
	private Node endBranch;
	private boolean or;
	private List<Node> orNodes;
	private boolean and;
	private List<Node> andNodes;
	private List<Node> superPreds;
	private boolean multPreds;
	
	public Node(Unit myUnit){
		curUnit = myUnit;
		preds = new ArrayList<Node>();
		succs = new ArrayList<Node>();
		superPreds = new ArrayList<Node>();
		branch = false;
		multPreds = false;
		trueBranch = null;
		falseBranch = null;
		or = false;
		and = false;
	}
	
	public Node(Unit myUnit,List<Node> myPreds,List<Node> mySuccs) {
		curUnit = myUnit;
		preds = myPreds;
		succs = mySuccs;
		superPreds = new ArrayList<Node>();
		or = false;
		and = false;
		if(succs.size()>1){
			branch = true;
		}
		else{
			branch = false;
		}
		if(preds.size()>1){
			multPreds = true;
		}
		else{
			multPreds = false;
		}
		endBranch = null;
	}
	
	public void setPredsAndSuccs(List<Node> myPreds,List<Node> mySuccs){
		preds = myPreds;
		succs = mySuccs;
		if(succs.size()>1){
			branch = true;
		} 
		if(preds.size()>1){
			multPreds = true;
		}
	}
	public Unit getUnit(){
		return curUnit;
	}
	
	public List<Node> getPreds(){
		return preds;
	}
	
	public List<Node> getSuccs(){
		return succs;
	}
	
	public List<Node> getSuperPreds(){
		return superPreds;
	}
	public boolean branches(){
		return branch;
	}
	
	public boolean multPreds(){
		return multPreds;
	}
	
	public boolean hasOr(){
		return or;
	}
	
	public boolean hasAnd(){
		return and;
	}
	
	public List<Node> getOr(){
		return orNodes;
	}
	
	public List<Node> getAnd(){
		return andNodes;
	}
	
	public void setSuperPreds(List<Node> newSuperPreds){
		for(Node sPred: newSuperPreds){
			superPreds.add(sPred);
		}
	}
	public void addPreds(List<Node> newPreds){
		for(Node pred: newPreds) {
			addPred(pred);
		}
		if(preds.size()>1){
			multPreds = true;
		}
	}
	
	public void addSuccs(List<Node> newSuccs){
		for(Node succ: newSuccs) {
			addPred(succ);
		}
		if(succs.size()>1){
			branch = true;
		}
	}
	
	public boolean addPred(Node newPred){
		if(preds.contains(newPred)){
			return false;
		}
		preds.add(newPred);
		return true;
	}
	
	public boolean addSucc(Node newSucc){
		if(succs.contains(newSucc)){
			return false;
		}
		succs.add(newSucc);
		return true;
	}
	
	public void setEndBranch(Node n){
		endBranch = n;
	}
	
	public Node getEndBranch(){
		return endBranch;
	}
	
	public void addOr(Node n){
		if(or == false){
			orNodes = new ArrayList<Node>();
			or = true;
		}
		orNodes.add(n);
	}
	
	public void addAnd(Node n){
		if(and == false){
			andNodes = new ArrayList<Node>();
			and = true;
		}
		andNodes.add(n);
	}
	
	public boolean loops(){
		for(Tag t: getUnit().getTags()){
			if(t.toString().equals("Loop")){
				return true;
			}
		}
		return false;
	}
	
	public Node getTrue(){
		return trueBranch;
	}
	
	public Node getFalse(){
		return falseBranch;
	}
	
	public void setTrue(Node trueNode){
		trueBranch = trueNode;
	}
	
	public void setFalse(Node falseNode){
		falseBranch = falseNode;
	}
}

class Tracker {
	private Unit curUnit;
	private ValueBox mutant;
	
	Tracker(Unit u, ValueBox v){
		curUnit = u;
		mutant = v;
	}
	
	public Unit getUnit(){
		return curUnit;
	}
	
	public ValueBox getValueBox(){
		return mutant;
	}
}

class BranchNode {
	private Node branchNode; 
	private boolean path;
	
	public BranchNode(Node n, boolean p){
		branchNode = n;
		path = p;
	}
	
	public Node getNode(){
		return branchNode;
	}
	
	public boolean getPath(){
		return path;
	}
	
	public String toString(){
		return "" + branchNode.getUnit() + "," + path;
	}
}

class ReadyMutant{
	
	ValueBox mutant;
	Value newValue;
	Unit mUnit;
	
	public ReadyMutant(ValueBox vb, Value v, Unit u){
		mutant = vb;
		newValue = v;
		mUnit = u;
	}
	
	public ValueBox getMutant(){
		return mutant;
	}
	
	public Value getValue(){
		return newValue;
	}
	
	public Unit getUnit(){
		return mUnit;
	}
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import com.microsoft.z3.Model;
import dse.nazmul.ConditionStatement;
import dse.nazmul.MainDSEInstra;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

class TreeGenerator
{
    InvokeManager invokeManager = null;
    TreeNode tree = null;
    HashMap<String,TreeNode> conditionTracer = null;
    TreeNode root =null;
    TreeNode currentTreeNode =null;    
    ListStack nodeStack = null;
    
    String textAreaOutput = "";
    int noOfPaths = 0;
    
    public TreeGenerator()
    {
        invokeManager = new InvokeManager();
        conditionTracer = new HashMap<>();
        root = new TreeNode();
        nodeStack = new ListStack();
        textAreaOutput = "";
    }
       
    public void invokeFirstTime()
    {
        Utility.clear();
        noOfPaths = 0;
        invokeManager.invokeFromModel(null);
        UniquePath uniquePath = invokeManager.getUniquePath();

        addToTree(root,uniquePath);
        System.out.println("####################");
        preOrderRecursivePrint(root);
        System.out.println("####################");
        
        stepAndExplore();        
        preOrderRecursivePrint(root);
        
        showTree();
    }
    
    int width = 500;
    private void showTree()
    {
        JFrame frame = new JFrame("Condition Tree");
        //String str = ((ConditionStatement)root.getCondition()).printValue();
        //String str = "root";
        String str = Utility.className;
        DefaultMutableTreeNode mroot = new DefaultMutableTreeNode(str);
        traverseTree(root,mroot);
        
        JTree tree = new JTree(mroot);
       
        JScrollPane scrollPane = new JScrollPane(tree);
        expandAllNodes(tree);
        
        scrollPane.setPreferredSize(new Dimension(width,390));
        
        JPanel motherPanel =  new JPanel();
        motherPanel.setPreferredSize(new Dimension(width,700));
        motherPanel.setLayout(new BorderLayout(5, 10));
        motherPanel.add(scrollPane, BorderLayout.NORTH);
        motherPanel.add(getOutputPanel(),BorderLayout.SOUTH);
        frame.getContentPane().add(motherPanel);
        frame.setSize(width,700 );
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    private JPanel getOutputPanel()
    {
        JPanel outputPanel = new JPanel();
        outputPanel.setPreferredSize(new Dimension(width, 360));
        outputPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0,90));
        outputPanel.add(new JTextArea(textAreaOutput,27, 45));        
        return outputPanel;
    }
    
    private void expandAllNodes(JTree tree) {
        int j = tree.getRowCount();
        int i = 0;
        while (i < j) {
            tree.expandRow(i);
            i += 1;
            j = tree.getRowCount();
        }
    }
    
    public void traverseTree(TreeNode tree,DefaultMutableTreeNode node) {
            
           String str = ((ConditionStatement)tree.getCondition()).printValue();
//	    if(node.getParent() ==null)
//            {
//                node = new DefaultMutableTreeNode(str);
//             
//            }  
            if(tree.getTrueChildren() != null)
            {
                TreeNode trueChild = tree.getTrueChildren();
//                String tag = "";
//                if(trueChild.causeOfBirth)
//                    tag = "T";
//                else
//                    tag = "F";
                if(trueChild.isLeaf)
                    System.out.println("IS_A_LEAF");
                else
                    System.out.println("NOT_A_LEAF");
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(((ConditionStatement)trueChild.getCondition()).printValue());
                traverseTree(trueChild,childNode);
                node.add(childNode);
            }
            if(tree.getFalseChildren() != null)
            {
                TreeNode falseChild = tree.getFalseChildren();
//                String tag = "";
//                if(falseChild.causeOfBirth)
//                    tag = "T";
//                else
//                    tag = "F";
                if(falseChild.isLeaf)
                    System.out.println("IS_A_LEAF");
                else
                    System.out.println("NOT_A_LEAF");
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(((ConditionStatement)falseChild.getCondition()).printValue());
                traverseTree(falseChild,childNode);
                node.add(childNode);
            }
	}
    
    private TreeNode addToTree(TreeNode nextNode ,UniquePath uniquePath)
    {
        Iterator it = uniquePath.path.iterator();
        System.out.println(uniquePath.path.size());
        if(nextNode.data == null)//if(conditionTracer.size()==0)   //very first time
        {
            nextNode = createRootNode(nextNode);
            System.out.println("Start Tree generation");
            while(it.hasNext())
            {
               ConditionStatement uniquePathCondition = (ConditionStatement) it.next();
               System.out.println(uniquePathCondition);
               nextNode = addToTrueBranch(nextNode,uniquePathCondition);
               
               nextNode.isLeaf = false;
               nextNode.invoked = true;
               nextNode.conditionSatisfiable = true;
               nextNode.trueChecked = true;
            }
            
            nextNode.isLeaf = true;
            nextNode.invoked = true;
            nextNode.conditionSatisfiable = true;
            nextNode.model = uniquePath.model;
        }
        else
        {
            System.out.println("Start Tree comparing");
            System.out.println("addToTree:"+nextNode.data);
            
            boolean eligibleToAdd = false;
            int conditionEquivalencyValue = 2;
            System.out.println((ConditionStatement)nextNode.getCondition());
            while(it.hasNext())
            {
               
               ConditionStatement uniquePathCondition = (ConditionStatement) it.next();
               System.out.println(uniquePathCondition);
               System.out.println("Equivalency Value:1:"+conditionEquivalencyValue);
               conditionEquivalencyValue = ((ConditionStatement)nextNode.getCondition()).compareEquivalency(uniquePathCondition); 
               System.out.println("Equivalency Value:2:"+conditionEquivalencyValue);
                   
               if(conditionEquivalencyValue==0)     // 2 means completely different condition
               {   
                   System.out.println("Equivalency Value:3:"+conditionEquivalencyValue);
                   eligibleToAdd = true;
                   continue;
                   
               }
               System.out.println("Equivalency Value:5:"+conditionEquivalencyValue);
               if(eligibleToAdd)
               {
                   System.out.println("Call to add...");
                   nextNode = addToTrueBranch(nextNode,uniquePathCondition);
               }
            }
        }
        noOfPaths++;
        System.out.println("ADDING To OUTPUT:"+uniquePath.descriptionString + "::"+ uniquePath.inputAsString + "\n");
        textAreaOutput += noOfPaths+" :"+uniquePath.descriptionString + "::"+ uniquePath.inputAsString + "\n";
        
        nextNode.isLeaf = true;
        nextNode.invoked = true;
        nextNode.conditionSatisfiable = true;
        nextNode.model = uniquePath.model;
        
        return nextNode;

    }
    
    private TreeNode createRootNode(TreeNode root)
    {
        ConditionStatement rootCondition = new ConditionStatement(true);  //empty condition with true value
        root.setCondition(rootCondition);
        return root;
    }
    
    private TreeNode addComplementaryNodeToTree(TreeNode node, ConditionStatement cs,boolean hasSolution,Model model)
    {
        TreeNode child = new TreeNode();
        child.setCondition(cs);       
        if(hasSolution)
        {    
            child.conditionSatisfiable = true;
            child.model = model;
        }
        else
        {
            child.isLeaf = true;  // means non feasible leaf  arguable
        }
        child.complementaryNodeChecked = true;
        node.addFalseChild(child);
        return child;
    }
    
    private TreeNode addToTrueBranch(TreeNode node, ConditionStatement cs)
    {
        TreeNode temp = null;
        if(node.data == null)
        {
            System.out.println("This should not be executed...");
            System.out.println("Node: "+cs);
            node.setCondition(cs);
            conditionTracer.put(cs.toString(), node);
            System.out.println("Added to tracer :"+cs.toString());
            conditionTracer.put(cs.toAlternativeString(), node);
            System.out.println("Added to tracer :"+cs.toAlternativeString());
            node.trueChecked = true;
            nodeStack.push(node);
            System.out.println("PORP: PUSH:"+node.data);
            temp = node;
        }
        else
        {
           TreeNode child = new TreeNode();
           child.setCondition(cs);
           child.complementaryNodeChecked = false;
           node.addTrueBranch(child);
           
           conditionTracer.put(cs.toString(), child);
           System.out.println("Added to tracer :"+cs.toString());
           conditionTracer.put(cs.toAlternativeString(), child);
           System.out.println("Added to tracer :"+cs.toAlternativeString());
           nodeStack.push(child);
           System.out.println("PORP: PUSH:"+child.data);
           temp = child;
        }
        return temp;
    }
    
    private TreeNode addToFalseBranch(TreeNode node, ConditionStatement cs)
    {
        TreeNode temp = null;
        if(node.data == null)
        {
            System.out.println("This should not happen");
        }
        else
        {
            
           TreeNode child = new TreeNode();
           child.setCondition(cs);
           child.trueChecked = true;
           node.falseChecked = true;
           node.addFalseChild(child);
           
           conditionTracer.put(cs.toString(), child);
           System.out.println("Added to tracer :"+cs.toString());
           conditionTracer.put(cs.toAlternativeString(), child);
           System.out.println("Added to tracer :"+cs.toAlternativeString());
           nodeStack.push(child);
           System.out.println("PORP: PUSH:"+child.data);
           temp = child;
        }
        
        return temp;
    }
    
    
    public void stepAndExplore()
    {
        int loopCounter = 0;
        
        while(!nodeStack.isEmpty() )
        {
            TreeNode node = (TreeNode) nodeStack.top();
            System.out.println("WORKING with node: "+node.getCondition().toString());
//            System.out.println("#########0###########");
//            preOrderRecursivePrint(root);
//            System.out.println("##########0##########");
            if(node.complementaryNodeChecked)
            {
                System.out.println("PORP: POP:"+node.data);
                nodeStack.pop();
            }
            else
            {
                System.out.println("Complementary not checked");
                negateAndCall(node);                
                node.complementaryNodeChecked = true;
                
            }
//            System.out.println("#########1###########");
//            preOrderRecursivePrint(root);
//            System.out.println("#########1###########");
            loopCounter++; 
        }

    }
    
    public void negateAndCall(TreeNode node)
    {
        createModel(node);
    }
    
    public void invokeFromModel(Model model,TreeNode currentNode)
    {
        System.out.println("invokeFromModel : Current Node:"+currentNode.data);
        invokeManager.invokeFromModel(model);
        UniquePath uniquePath = invokeManager.getUniquePath();
        
//        LoopDetector loopDetector = new LoopDetector();
//        loopDetector.setUniquePath(uniquePath);
//        loopDetector.processLoopDetection();
//        
//        uniquePath = loopDetector.getProcessedUniquePath();
        
        
        System.out.println("After Invoke:"+uniquePath.getDescriptionString());
        System.out.println("IS NODE NULL:"+currentNode.getCondition());
        addToTree(currentNode,uniquePath);  //not first time
    }
    
    public void createModel(TreeNode node)
    {
        UniquePath up = new UniquePath();
        TreeNode currentNodeHolder = node;
        System.out.println("createModel:"+node.data);
        ConditionStatement negationCondition = ((ConditionStatement)(node.getCondition())).returnNegateCondition();
        up.addACondition(negationCondition);
        
        
        
        //boolean causeOfBirth = node.causeOfBirth;
        node = node.getParent();
        while(node.parent != null)
        {
            ConditionStatement tempCond = (ConditionStatement)(node.getCondition());
           // if(!causeOfBirth)
           //     up.addACondition(tempCond.returnNegateCondition());
           // else
            up.addACondition(tempCond);
          //  causeOfBirth = node.causeOfBirth;
            node = node.getParent();
        }
        up.reversePath();
        System.out.println("UP for model:"+up.getDescriptionString());
        System.out.println("Node:"+node.data);
        new ExpressionBuilder().parseAndBuildExpression(up);
        
        
        
        //ystem.out.println(node.data);
        if(up.hasSolution)
        {   
           currentNodeHolder = addComplementaryNodeToTree(currentNodeHolder.getParent(),negationCondition,true,up.model); 
           invokeFromModel(up.model, currentNodeHolder);
        }
        else
        {
            addComplementaryNodeToTree(currentNodeHolder.getParent(),negationCondition,false,null); 
            noOfPaths++;
            System.out.println("ADDING To OUTPUT:"+up.descriptionString + "::"+ "Not feasible" + "\n");
            textAreaOutput += noOfPaths+" :"+up.descriptionString + "::"+ "Not feasible" + "\n";
        
        }
//        System.out.println("#######2#############");
//        preOrderRecursivePrint(root);
//        System.out.println("########2############");
    }
    
    
    
    public void preOrderRecursivePrint(TreeNode root) {
        
        if (root != null) {
            if(root.getCondition()!=null)
            {
                if(root.getParent()!= null)
                    System.out.println(((ConditionStatement)root.getCondition()).getDescriptionString() + " D:"+root.getParent().data);
                else
                {   
                    System.out.println("Printing the tree...");
                    System.out.println(((ConditionStatement)root.getCondition()).getDescriptionString() + " ");
                }
            }
            
                
            if(root.getTrueChildren()!=null)
                preOrderRecursivePrint(root.getTrueChildren());
            if(root.getFalseChildren()!=null)
                preOrderRecursivePrint(root.getFalseChildren());
            
        }
    }
    
    
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import dse.nazmul.ConditionStatement;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Md Nazmul Karim
 */
public class LoopDetector {
    
//    UniquePath  uniquePath = null;
//    UniquePath  processedUniquePath = null;
//    
//    int noOfLoopPatternFound = 0;
//    InvokeManager invokeManager = null;
//       
//    public LoopDetector()
//    {
//        invokeManager = new InvokeManager();
//        processedUniquePath = new UniquePath();
//    }
//    
//    public UniquePath getProcessedUniquePath()
//    {
//        return processedUniquePath;
//    }
//    public void processLoopDetection()
//    {
//        boolean firstLoopPatternEccountered = false;
//        int firstLine = -1;
//        int secondLine = -1;
//        UniquePath up = new UniquePath();
//        ConditionStatement saveStmt1st = null;
//        ConditionStatement saveStmt2nd = null;
//        
//        Iterator it = uniquePath.path.iterator();
//        while (it.hasNext()) 
//        {
//            ConditionStatement stmt = (ConditionStatement)it.next();
//            System.out.println(stmt.toString());
//            
//            if(TreeGenerator.loopStatus.get(stmt.getLineNo()) == null)  //no loop history
//            {
//                if(stmt.checkForLoopAndUpdate(false))   // has loop pattern
//                {
//                    noOfLoopPatternFound++;
//                    if(firstLoopPatternEccountered == false)
//                    {
//                        firstLoopPatternEccountered = true;
//                        firstLine = stmt.getLineNo();
//                        stmt.checkForLoopAndUpdate(true);
//                        saveStmt1st = stmt;
//                        up.addACondition(stmt.returnNegateCondition());
//                    }
//                    else
//                    {
//                        secondLine = stmt.getLineNo();
//                        saveStmt2nd = stmt;
//                        //up.addACondition(stmt);
//                    }
//                    
//                    stmt.loopStatusCode = 10;
//                    TreeGenerator.loopStatus.put(stmt.getLineNo(),stmt );
//                }
//                else    // has no loop history and no loop pattern
//                {
//                    stmt.loopStatusCode = 0;
//                    TreeGenerator.loopStatus.put(stmt.getLineNo(),stmt );
//                    up.addACondition(stmt);
//                }
//            }
//            else
//            {
//                noOfLoopPatternFound++;
//                if(firstLoopPatternEccountered == false)
//                {
//                    firstLoopPatternEccountered = true;
//                    firstLine = stmt.getLineNo();
//                    stmt.checkForLoopAndUpdate(true);
//                    up.addACondition(stmt);
//                }
//                else
//                {
//                   // up.addACondition(stmt);
//                }
//                System.out.println("Has loop history");
//            }
//            
//        }
//        
//        System.out.println("First Line :"+firstLine+" Second Line :"+secondLine);
//        printLoopStatus();
//        
//        
//        UniquePath invokedUniquePath = null;
//        
//        if(noOfLoopPatternFound>1)
//        {
//            System.out.println(noOfLoopPatternFound);
//            System.out.println("Before Extra Invoke:"+uniquePath.indentityString);
//            System.out.println("Before Extra Invoke:"+up.indentityString);
//            invokedUniquePath = extraInvoke(up);
//            System.out.println("After Extra Invoke:"+invokedUniquePath.indentityString);
//        }
//        
//        
//        Iterator itAgain = invokedUniquePath.path.iterator();
//        while (itAgain.hasNext()) 
//        {
//            ConditionStatement stmt = (ConditionStatement)itAgain.next();
//            System.out.println(stmt.toString());
//            
//            if(stmt.getLineNo() == firstLine)
//            {
//                if(!stmt.checkForLoopAndUpdate(false))
//                {
//                    ConditionStatement temp = TreeGenerator.loopStatus.get(stmt.getLineNo());
//                    temp.loopStatusCode = 111;
//                    TreeGenerator.loopStatus.put(stmt.getLineNo(),temp );
//                }
//                else
//                {
//                    ConditionStatement temp = TreeGenerator.loopStatus.get(stmt.getLineNo());
//                    temp.loopStatusCode = 110;
//                    TreeGenerator.loopStatus.put(stmt.getLineNo(),temp );
//                }
//            }
//            if(stmt.getLineNo() == secondLine)
//            {
//                if(!stmt.checkForLoopAndUpdate(false))
//                {
//                    ConditionStatement temp = TreeGenerator.loopStatus.get(stmt.getLineNo());
//                    temp.loopStatusCode = 110;
//                    TreeGenerator.loopStatus.put(stmt.getLineNo(),temp );
//                }
//                else
//                {
//                    ConditionStatement temp = TreeGenerator.loopStatus.get(stmt.getLineNo());
//                    temp.loopStatusCode = 111;
//                    TreeGenerator.loopStatus.put(stmt.getLineNo(),temp );
//                }
//            }
//            
//       }
//        printLoopStatus();
//        System.out.println("After Compare:"+uniquePath.indentityString);
//        
//        Iterator it2 = uniquePath.path.iterator();
//        while (it2.hasNext()) 
//        {
//            ConditionStatement stmt = (ConditionStatement)it2.next();
//            System.out.println(stmt.toString());
//            if(TreeGenerator.loopStatus.get(stmt.getLineNo()) != null)
//            {
//                 ConditionStatement temp = TreeGenerator.loopStatus.get(stmt.getLineNo());
//                 if(temp.loopStatusCode == 111)
//                 {
//                     stmt.checkForLoopAndUpdate(true);
//                 }
//            }
//            processedUniquePath.addACondition(stmt);
//        }
//         System.out.println("Processed:"+processedUniquePath.indentityString);
//    }
//    
//    public UniquePath extraInvoke(UniquePath up)
//    {
//        
//        new ExpressionBuilder().parseAndBuildExpression(up);
//        invokeManager.invokeFromModel(up.model);
//        UniquePath uniquePath = invokeManager.getUniquePath();
//        return uniquePath;
//    }
//    
//    public void printLoopStatus()
//    {
//        //System.out.println(TreeGenerator.loopStatus);
//        Iterator it = TreeGenerator.loopStatus.entrySet().iterator();  //HashMap
//        ConditionStatement cs;
//        while(it.hasNext())
//        {
//            Map.Entry pair = (Map.Entry)it.next();
//            cs = (ConditionStatement) pair.getValue();
//            System.out.println(pair.getKey() + " = "+cs+"|" +cs.loopStatusCode);
//        }
//    }
//    
//    public UniquePath getUniquePath() {
//        return uniquePath;
//    }
//
//    public void setUniquePath(UniquePath uniquePath) {
//        this.uniquePath = uniquePath;
//    }
//    
//    public void compareUniquePath()
//    {}
    
}

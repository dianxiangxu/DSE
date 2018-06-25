/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.test;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Md Nazmul Karim
 */
public class Test {
    public static void main(String[] args) {

//		String test = "p1+30+30+30";
//
//		int step = 2;
//
//		Set<String> nonReaptingSeq = new TreeSet<>();
//
//		Set<String> reaptingSeq = new TreeSet<>();
//
//		for (int i = 0; (i < test.length() && (i + step) <= test.length()); i++) {
//
//			String sub = test.substring(i, i + step);
//			System.out.println(sub);
//
//			if (!nonReaptingSeq.add(sub)) {
//				reaptingSeq.add(sub);
//			}
//
//		}
//
//		System.out.println(reaptingSeq);

            String testString = "r0[0]";
            if(testString.contains("["))
            {    
                System.out.println("Has array notation");
                String op1Prefix = testString.substring(0,testString.indexOf("["));
                System.out.println(op1Prefix);
                String op1Infix = testString.substring(testString.indexOf("[")+1,testString.indexOf("]"));
                System.out.println(op1Infix);
            }
	}
}

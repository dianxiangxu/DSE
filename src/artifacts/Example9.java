/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artifacts;

/**
 *
 * @author Md Nazmul Karim
 */
public class Example9 {
     static int reportTriangleType(int angleA, int angleB, int angleC) {
		
                int returnValue = -1;
		if (angleA <= 0 || angleB <= 0 || angleC <=0){
			returnValue= 0;
		}
		
		if (angleA+angleB+angleC != 180) {
			returnValue= 180;
		}

		if (angleA== 90 || angleB == 90 || angleC == 90) {
			returnValue= 90;
		}
		
		if (angleA > 90 || angleB > 90 || angleC > 90) 
			returnValue= 91;
		else 
			returnValue= 110;
                
                return returnValue;
	}
     
     public static int noOfArguments = 3;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        int z = Integer.valueOf(args[2]);
        System.out.println(reportTriangleType(x,y,z));
    }
}

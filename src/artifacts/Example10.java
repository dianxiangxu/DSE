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
public class Example10 {
    static int reportTriangleType2(int a, int b, int c) {
		int returnValue = -1;
		if (a < b + c && b < a + c && c < b + a){
			if (a==b && b==c)
				returnValue = 100;
			else
			if (a==b || b==c || c==a)
				returnValue = 50;
			else
				returnValue = 200;
		}
		else
			returnValue = 300;
                return returnValue;
	}
    
    public static int noOfArguments = 3;
    
    
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        int z = Integer.valueOf(args[2]);
        System.out.println(reportTriangleType2(x,y,z));
    }
}

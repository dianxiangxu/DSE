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
public class Example31 {
    static int example(int x1, int y1, int z1) {
         
        int x = x1;
        int y = y1;
        int z = z1;
        
        int k = 10;
        // the inner condition has no dependency on loop condition : loop condition is symbolic : inner cond is concrete
        while( x< (y+20))
        {
            
            if(k > 30)
            {
                x = x-2;     
            }
            else
            {
                x = x+20;
            } 
           // k = k+15;
        }    
        if(x > 5)
            z = x + y +z ;
        return z;
    }
    public static int noOfArguments = 3;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        int z = Integer.valueOf(args[2]);
        if(x>y)
            x= y+10;
        System.out.println(example(x,y,z));
    }
}

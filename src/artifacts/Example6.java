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
public class Example6 {
    static int example(int x1, int y1, int z1) {
         
        int x = x1;
        int y = y1;
        int z = z1;
        
        if (x1 > 2 ) 
        {
            x = x + 3;
            
//            for(int k=0; k<3 ; k++)
//            {
                if(y1 > 5)
                {
                    x = x+y;

                    if(z1>15)
                    {
                        y = y+2;
                    }
                    else
                    {
                        y = y -1;
                    }
                }
                else
                {
                    if(z1>20)
                    {
                        y = y+2;
                    }
                    else
                    {
                        y = y -1;
                    }
                }
           // }
        }
        else
        {
            x = x + 3;
            
            if(y1 > 6)
            {
                x = x+y;
                
                if(z1>25)
                {
                    y = y+2;
                }
                else
                {
                    y = y -1;
                }
            }
            else
            {
                if(z1>9)
                {
                    y = y+2;
                }
                else
                {
                    y = y -1;
                }
            }
        }
        z = x + y +z ;
        return z;
    }
    
    public static int noOfArguments = 3;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        int z = Integer.valueOf(args[2]);
        System.out.println(example(x,y,z));
    }
}

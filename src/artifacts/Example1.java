package artifacts;

import java.util.ArrayList;

public class Example1 {

	/*
	 * New example to really see how making new expressions work
	 * @author Hannah Johnson
	 */


    static int example_1(int x1, int y1) {
        x1 = x1 * 2;
        while (x1 != 2) {
            y1 = y1 + 2;
            while (y1 > 0) {
                y1--;
            }
            if (x1 == 0) {
                System.out.println("assert");
                System.exit(2);
            }
            x1++;
        }
        return y1;

    }

    /*
	 * In general if SA analyzes a particular part of CFG
	 * and finds that assertion violates, it will not be
	 * able to tell if violating path is feasible or not.
	 * Need to do SE to dismiss false positives.
	 * However, if SA tells that a particular part of CFG
	 * assertion does not violates then feasibility should 
	 * not be an issue. 
	 * Non proven fix right now is to find the 
	 * state change and make sure that different values are opposites, i.e., 0 and !0
     */
    static int example_2(int x1, int x2) {
        int x = x1 + x2;
        boolean cond_1;
        if (x == 0) {
            cond_1 = false;
        } else {
            cond_1 = true;
        }
        //cond_1 = x1==x2 && x != 5 && cond_1;
        boolean cond_2 = false;
        if (x1 == x2) {
            if (x != 5) {
                if (cond_1) {
                    cond_2 = true;
                }
            }
        }

        //if(x1==x2 && x != 5 && cond_1){
        if (cond_2) {
            System.out.println("assert");
            System.exit(2);
        }
        return x;
    }

    static int example_3(int x, int y) {
        if (x == 0) {
            y = 0;
        } else {
            y = 0;
        }

        if (y > 0) {
            x = y;
        } else {
            x = y + 1;
        }

        if (x >= 0) {
            System.out.println("assert");
        }
        return x;
    }

    static int example_4(int x, int y) {

        if (x != 0) {
            x = x + 0;
            while (y > 0) {
                x = x + 2;
                y = y - 1;
            }
        } else {
            x = x - 2;
        }

        if (x != 0) {
            System.out.println("assert");
        }
        return x;
    }

    /*
	 * This example shows that SA will determine that the 
	 * 3rd branch false outcome is infeasible, i.e., assertion violates.
	 * Yet, the path itself is infeasible. 
     */
    public int example_5(int x, int y) {
        x = x * 0;
        if (x > y) {
            if (x == y) {
                if (x == 0) {
                    System.out.println("assert");
                }
            }
        }
        return x + y;
    }

    public double example_6(double x, double ret) {
        double y = 0.0 * x;
        double ter = ret / 5.0;
        int z = (int) y;
        if (x > ret && y != z) {
            ret = 2 * ter + z;
        }
        return ret;
    }

    public double example_7(double x, double y) {
        double ret = -1.00;
        if (x == 0) {
            ret = x * 2;
        } else if (y >= ret) {
            ret = y;
        }

        if (ret == 0) {
            System.out.println("assertion violated");
        }
        return ret;
    }

    public int example_8(double x, byte y) {
        byte ret = (byte) 0;
        if (x <= 0) {
            ret = (byte) (y + ((byte) x));
        } else {
            x = ret;
        }

        if (x == 0) {
            System.out.println("assertion violated");
        }
        return (int) x;
    }
    
        static int example_9(int x, int y) {
        if (x > 0 || y > 0) {
            y = x + y;
        } else {
            y = y + 2;
        }

        if (y > 0) {
            x = y;
        } else {
            x = y + 1;
        }

        if (x >= 0) {
            System.out.println("assert");
        }
        return x;
    }
           
    static int example_10(int x1, int y1) {

        if (x1 > y1) {
            y1 = y1 + 2;
        } else {
            x1 = x1 + 3;
        }
        int z = x1 + y1;
        return z;
    }



    static int example_11(int x1, int y1) {
        int a = x1 * 5;

        if (a != 11) {
            x1 = y1 * 2;
        }

        if (x1 == 6) {
            while (x1 != 2) {
                y1 = y1 + 2;
                while (y1 > 0) {
                    y1--;
                }
                if (x1 >= 0) {
                    System.out.println("assert");
                }
                x1 = 0;
            }
        } else {
            y1 = 5;
        }
        x1++;
        y1 = -y1;
        return y1;

    }
    static int example_12(int x1, int y1) {
        int a = x1 + 5;
        int b = a - y1;

        if (b > a || b == a) {
            a = a * 5;
        } else {
            b = b * 5;
        }
        x1 = a * b;
        y1 = a / b;

        return x1;
    }

    static int example_13(int x1, int y1) {

        int z = 0;
        if (x1 > 5 ) 
        {
            if( x1>10 && y1<21)
            { 
                z = y1 + 2;
                if(y1<15)
                {
                    //y1 = 18;
                    if(y1>11)
                    {
                        z=9;
                    }
                    else
                    {
                        z=0;
                    }
                }
                else
                {
                    y1 = y1+10;
                }
            }
            else
            {
               if(y1>8)
                {
                    z=0;
                }
                else
                {
                    z =1;
                }
            }
        } 
        else {
            z=0;
            if(y1>7)
            {
                z=1;
            }
            else
            {
                z=3;
            }
        }
        z = x1 + y1;
        return z;
    }
    
     static int example_14(int x1, int y1) {

        if (x1 > 0  && y1>1 ) 
        {
            x1 = x1 + 3;
        }
        int z = x1 + y1;
        return z;
    }
     
     static int example_15(int x1, int y1) {

        if (x1 > 0 || y1>1 ) 
        {
            x1 = x1 + 3;
        }
        int z = x1 + y1;
        return z;
    }
     
     static int example_16(int x1, int y1) {
         int z =0;
        if (x1 > 1  ) 
        {
            if(y1>5)
            { 
                z=2;
            }
            else
            {
                z =1;
            }
         }
        else
        {
            if(y1>7)
            {
                z = 7;
            }
            else
            {
                z =9;
            }
        }
        
        z = x1 + y1;
        return z;
    }
     static int example_17(int x1, int y1, int z1) {
         
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
     static int example_18(int x1, int y1, int z1) {
         
        int x = x1;
        int y = y1;
        int z = z1;
        
        int k = 0;
        
        while(k<3)
        {
//                if(y1 > 5)
//                {
                    x = x+y;     
                    k++;
//                }
//                else
//                {
//                    x = y + z;
//                }
                
            }    
        if(x > 5)
            z = x + y +z ;
        return z;
    }
     
     static int example_19(int x1, int y1, int z1) {
         
        int x = x1;
        int y = y1;
        int z = z1;
        
        int k = 0;
        x= 2*x;
        y= y+1;
        y = 2*y;
        
        if(y > 5)
        {
            x = x+y;     
            k++;
        }
        else
        {
            x = y + z;
        }
                
             
        if(x > 5)
            z = x + y +z ;
        return z;
    }
     
     public static int noOfArguments = 3;
	public static void main(String[] args){
//                if(noOfArguments == 2)
//                {
//                    int x = Integer.valueOf(args[0]);
//		    int y = Integer.valueOf(args[1]);
//		    System.out.println(example_13(x,y));
//                }
//                else if(noOfArguments == 3)
//                {
                    int x = Integer.valueOf(args[0]);
		    int y = Integer.valueOf(args[1]);
		    int z = Integer.valueOf(args[2]);
		    System.out.println(example_18(x,y,z));
		   // System.out.println(example_19(x,y,z));
//                }
                
                
		//dse.nazmul.SootIntCollectorInstra.conditionSaver.clear();
	}

}


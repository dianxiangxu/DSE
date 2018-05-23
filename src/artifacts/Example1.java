package artifacts;


public class Example1 
{
    static int example(int x1, int y1) 
    {
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
     
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }

}


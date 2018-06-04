/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.test;

/**
 *
 * @author Md Nazmul Karim
 */
public class StringUtil {
    String longestRepeatedSubstring(String str)
{
    int n = str.length();
    int LCSRe[][] = new int[n+1][n+1];
 
    // Setting all to 0
   // memset(LCSRe, 0, sizeof(LCSRe));
 
    String res=""; // To store result
    int res_length  = 0; // To store length of result
 
    // building table in bottom-up manner
    int i, index = 0;
    for (i=1; i<=n; i++)
    {
        for (int j=i+1; j<=n; j++)
        {
            // (j-i) > LCSRe[i-1][j-1] to remove
            // overlapping
            if (str.charAt(i-1) == str.charAt(j-1) &&
                LCSRe[i-1][j-1] < (j - i))
            {
                LCSRe[i][j] = LCSRe[i-1][j-1] + 1;
 
                // updating maximum length of the
                // substring and updating the finishing
                // index of the suffix
                if (LCSRe[i][j] > res_length)
                {
                    res_length = LCSRe[i][j];
                    index = Math.max(i, index);
                }
            }
            else
                LCSRe[i][j] = 0;
        }
    }
 
    // If we have non-empty result, then insert all
    // characters from first character to last
    // character of string
    if (res_length > 0)
    {    for (i = index - res_length + 1; i <= index; i++)
        {   
            //res.push_back(str[i-1]); 
            res +=str.charAt(i-1);
        }
     }
    return res;
}
    
    public static void main(String[] args)
    {
        String input = "(2*p1)";
        input = "((((2*p1)+30)+30)+30)";
        //input = "(30+(30+(30+p1)))";
        String repeatedString = new StringUtil().longestRepeatedSubstring(input);
       // System.out.println(repeatedString);
        input = input.replace(")", "");
        input = input.replace("(", "");
        repeatedString = repeatedString.replace(")", "");
        repeatedString = repeatedString.replace("(", "");
    //    System.out.println(input);
        System.out.println("Repeat:"+repeatedString);
        System.out.println("Base:"+input.replace(repeatedString,""));
    }
    
    public PatternDefinition hasLoopPattern(String input)
    {
        //System.out.println("Input:"+input);
        String repeatedString = new StringUtil().longestRepeatedSubstring(input);
       // System.out.println(repeatedString);
        input = input.replace(")", "");
        input = input.replace("(", "");
        repeatedString = repeatedString.replace(")", "");
        repeatedString = repeatedString.replace("(", "");
        System.out.println(input);
       // System.out.println("Repeat:"+repeatedString);
        String base = input.replace(repeatedString,"");
       // System.out.println("Base:"+base);
        
        
        PatternDefinition patternDefinition = new PatternDefinition();
        
        patternDefinition.setBase(base);
        patternDefinition.setRepeat(repeatedString);
        
        if(repeatedString.isEmpty())
            patternDefinition.setHasLoop(false);
        else
            patternDefinition.setHasLoop(true);
        
        return patternDefinition;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.test;
//https://www.geeksforgeeks.org/longest-repeating-and-non-overlapping-substring/
//https://www.programcreek.com/2013/02/leetcode-longest-substring-without-repeating-characters-java/

import java.util.ArrayList;

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
      //  input = "((((2*p1)+30)+30)+30)";
        input = "((((p1+10)+10)+10)+10)";
        //input = "(30+(30+(30+p1)))";
       
       // System.out.println(repeatedString);
        input = input.replace(")", "");
        input = input.replace("(", "");
        String repeatedString = new StringUtil().longestRepeatedSubstring(input);
        repeatedString = repeatedString.replace(")", "");
        repeatedString = repeatedString.replace("(", "");
    //    System.out.println(input);
        System.out.println("Repeat:"+repeatedString);
        System.out.println("Base:"+input.replace(repeatedString,"").replace(new StringUtil().runit(repeatedString), ""));
       // new StringUtil().runit();
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
       // System.out.println(input);
       // System.out.println("Repeat:"+repeatedString);
        
        String base = input;
        if(!repeatedString.isEmpty())
        {
            base = input.replace(repeatedString,"");
           // System.out.println("Base:"+base);
            String smallestRepeat = runit(repeatedString);

           // System.out.println("Smallest Repeat:"+smallestRepeat);
            base = base.replace(smallestRepeat, "");
        }
        PatternDefinition patternDefinition = new PatternDefinition();
        
        patternDefinition.setBase(base);
        patternDefinition.setRepeat(repeatedString);
        
        if(repeatedString.isEmpty())
            patternDefinition.setHasLoop(false);
        else
            patternDefinition.setHasLoop(true);
        
        return patternDefinition;
    }
    
    private String runit(String t){
        ArrayList<String> subs = new ArrayList<>();
        //String t = "+10+10";
        String out = null;
        for (int i = 0; i < t.length(); i++) {
            if (t.substring(0, t.length() - (i + 1)).equals(t.substring(i + 1, t.length()))) {
                subs.add(t.substring(0, t.length() - (i + 1)));
            }
        }
        subs.add(0, t);
        for (int j = subs.size() - 2; j >= 0; j--) {
            String match = subs.get(j);
            int mLength = match.length();
            if (j != 0 && mLength <= t.length() / 2) {
                if (t.substring(mLength, mLength * 2).equals(match)) {
                    out = match;
                    break;
                }
            } else {
                out = match;
            }
        }
       //System.out.println(out);
        return out;
    }
    
    private void runit2()
    {}

}

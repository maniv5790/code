package c1x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class fill_blanks {
public static List<String> l1 = new ArrayList<String>();
static ScriptEngineManager mgr = new ScriptEngineManager();
    static ScriptEngine engine = mgr.getEngineByName("JavaScript");
    static List<Object> nearestvalue = new ArrayList<Object>();

static void printAllKLength(char[] set, int k)
{
   int n = set.length;  
   printAllKLengthRec(set, "", n, k);
}
 
// The main recursive method
// to print all possible  
// strings of length k
static List<String> printAllKLengthRec(char[] set,  
                              String prefix,  
                              int n, int k)
{
         

   // Base case: k is 0,
   // print prefix
   if (k == 0)  
   {
       //System.out.println(prefix);
       l1.add(prefix);
       return l1;
   }
 
   // One by one add all characters  
   // from set and recursively  
   // call for k equals to k-1
   for (int i = 0; i < n; ++i)
   {
 
       // Next character of input added
       String newPrefix = prefix + set[i];  
         
       // k is decreased, because  
       // we have added a new character
       printAllKLengthRec(set, newPrefix,  
                               n, k - 1);  
   }
return l1;
   
}
         
       // Driver Code
       public static void main(String[] args) throws IOException, ScriptException
       {
        //System.out.println("Started");
    BufferedReader reader =  
           new BufferedReader(new InputStreamReader(System.in));
           System.out.println("Enter the Text for letters pairing : ");
           //this is a good thing
           // Reading data using readLine
           String name = reader.readLine();
           System.out.println("Input : "+name);  
           String[] lines_split = name.split(",");
           //System.out.println("---Length---"+lines_split.length);
           //System.out.println("Combination Starts");
           char[] set1 = {'+', '-'};
           int k = lines_split.length - 1;
            printAllKLength(set1, k);
           //System.out.println("--size--1--"+l1.size());
           
           for(int a = 0; a <l1.size(); a++)
           {
        	//getting the symbols +++ ++- +-- etc through l1 list
            //System.out.println(l1.get(a));
            String abc = l1.get(a);
            //System.out.println(abc);
            String abcd = "" ;
            for (int i=0; i< abc.length(); i++) {
           
            if (i == abc.length()-1)
            {
        //System.out.println(lines_split[i]+" "+abc.charAt(i)+" "+lines_split[i+1]);
        abcd = abcd + lines_split[i]+""+abc.charAt(i)+""+lines_split[i+1];
        //value = Integer.parseInt(abcd) + Integer.parseInt(lines_split[i])+abc.charAt(i)+Integer.parseInt(lines_split[i+1]);
            }
            else
            {
        //System.out.println(lines_split[i]+abc.charAt(i));
        abcd = abcd + lines_split[i]+abc.charAt(i);
        //value = Integer.parseInt(lines_split[i])+ abc.charAt(i);
            }
           
       }
            //System.out.println(abcd);
            //System.out.println("---value---"+engine.eval(abcd));
            // JavaScript code from String
        Object ob = engine.eval(abcd);
        //System.out.println("Result of evaluating mathematical expressions in String = "+ob);
           
       
        nearestvalue.add(ob);
       
       
           }
           
           //System.out.println("---final_--size---"+nearestvalue.size());
           String ad = "";
           for(int g = 0; g < nearestvalue.size() ; g++ )
           {
            if(g == 0)
            {
            ad = nearestvalue.get(g)+",";
            }
            else if(g == nearestvalue.size()-1)
            {
            ad = ad + nearestvalue.get(g);
            }
            else
            {
            ad = ad + nearestvalue.get(g)+",";
            }
           
           }
           //System.out.println(ad);
           String[] data = ad.split(",");
           int curr = 0;
           int near = Integer.parseInt(data[0]);
           Arrays.sort(data);      //  add this
           //System.out.println(Arrays.toString(data));        
           // find the element nearest to zero
           for ( int i=0; i < data.length; i++ ){
            //System.out.println("---IN lOop---");
              // System.out.println("dist from " + Integer.parseInt(data[i]) + " = " + Math.abs(0 - Integer.parseInt(data[i])));
               curr = Integer.parseInt(data[i]) * Integer.parseInt(data[i]);
               if ( curr <= (near * near) )  {
                   near = Integer.parseInt(data[i]);
               }
           }
           System.out.println( "Value Close to Zero : "+near );
           
       }
       

}
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;
public class EnglishParse {
	//input string //in the future may change to input from a file
	//break into sentences  -may also need to break into words
	//send each sentence to bitpar
	//get bitpar output
	//modify bitpar output 
	 // most challenging:
     //--modify brackets
	 //--tag words in sentences where necessary to distinguish types
	//NOTE NOTE NOTE: this assumes output from bitpar is a string, also idk how to
	//call bitpar from code
	
	//*handle strings quotes, handle integers Character.isDigit(character)
	//*and-et or-et,
	//use map for strings and integers
	public static Map<String,String> strings= new HashMap<String,String>(100);
	public static int currentnumStrings=0;
	public static Map<String,Integer> integers= new HashMap<String,Integer>(200);
	public static int currentnumIntegers=0;
	private static void storeStrings(ArrayList<String> a){
		for (int i=0;i<a.size();i++){
			//replace any string with s1/2/etc
			for (int c=0;c<a.get(i).length();c++){
			    if (a.get(i).charAt(c)=='"'){
			    	int start=c;
			    	int end=c;
			    	String s="";
			    	for(int z=c+1;z<a.get(i).length();z++){
			    		if(a.get(i).charAt(z)!='"')
			    			s+=a.get(i).charAt(z);
			    		else {end=z;break;}
			    	}
			    	currentnumStrings++;
			    	String tag="S";
			        tag+=currentnumStrings;
			        //puts the string and its tag into the map
			    	strings.put(tag,s);
			    	//replaces the string with the tag in the original string
			    	String switchesTo=a.get(i).substring(0,start)+tag+a.get(i).substring(end+1,a.get(i).length());
			    	a.set(i,switchesTo);
			    	c=start+tag.length();
			    }
			}
		}
	}
	
	
	private static void storeIntegers(ArrayList<String> a){
		for (int i=0;i<a.size();i++){
			//replace any integer with N1/2/etc
			for (int c=0;c<a.get(i).length();c++){
			    if (Character.isDigit(a.get(i).charAt(c))){
			    	int start=c;
			    	String s="";
			    	s+=a.get(i).charAt(c);
			    	int end=c;
			    	for(int z=c+1;z<a.get(i).length();z++){
			    		if(Character.isDigit(a.get(i).charAt(z)))
			    			s+=a.get(i).charAt(z);
			    		else {end=z;break;}
			    	}
			    	//to not confuse integers with variables V1-V200
			    	if(a.get(i).charAt(c-1)!='V'){
			    	currentnumIntegers++;
			    	String tag="N";
					tag+=currentnumIntegers;
			        //puts the integer and its tag into the map
			    	integers.put(tag,Integer.parseInt(s));
			    	//replaces the integer with the tag in the original string
			    	String switchesTo=a.get(i).substring(0,start)+tag+a.get(i).substring(end,a.get(i).length());
			    	a.set(i,switchesTo);
			    	c=start+tag.length();
			    	}
			    	else{
			    		c=end;
			    	}
			    }
			}
		}
	}
	
	//changes all ( to [ and ) to ]
	private static void parensToBrackets(ArrayList<String> a){
		for(int i=0; i<a.size();i++){
			String thisOne=a.get(i);
			//thisOne.replace('(',']');
			//thisOne.replace(')',']');
			String switchesTo="";
			for (int c=0;c<thisOne.length();c++){
				if(thisOne.charAt(c)=='('){
					switchesTo=switchesTo.concat("[");
				}
				else if(thisOne.charAt(c)==')'){
					switchesTo=switchesTo.concat("]");
				}
				else{
					switchesTo+=thisOne.charAt(c);
				}
			a.set(i,switchesTo);
			}
		}
		
	}
	
	//checks if the character is first preceded by quote or bracket
	private static boolean bracketFirst(int c, String s){
		boolean b=false;
		for(int i=(c-1);i<s.length() && i >=0;i--){
			if (s.charAt(i)=='"'){
				i=-1;
				return false;
			}
			else if(s.charAt(i)=='['){
				i=-1;
				b=true;
				return true;
			}
		}
		//if not include this gives a warning
		return b;
	}
	//delete characters that are between brackets but not between quotes
	//note that this also deletes any periods or commas
	private static void deleteLing(ArrayList<String> a){
		//char not a bracket and not a quote
		//char has bracket following it first before a quote
		for(int i=0; i<a.size();i++){
			String thisOne=a.get(i);
			String switchesTo="";
			for (int c=0;c<thisOne.length();c++){
				if (thisOne.charAt(c)!='[' && thisOne.charAt(c)!=']' && thisOne.charAt(c)!='\"'){
					if(bracketFirst(c,thisOne)){
						//skips
					}
					else
						switchesTo+=thisOne.charAt(c);
				}
				else
				  switchesTo+=thisOne.charAt(c);
			}
			a.set(i,switchesTo);
		}
	}
	private static void deleteQuotes(ArrayList<String> a){
		for(int i=0; i<a.size();i++){
			String thisOne=a.get(i);
			String switchesTo="";
			for (int c=0;c<thisOne.length();c++){
				if(thisOne.charAt(c)=='"'){//not sure if " matches quotes from bitpar tho
					//skips
				}
				else{
					switchesTo+=thisOne.charAt(c);
				}
			}
			a.set(i,switchesTo);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 
		Scanner input = new Scanner(System.in);
		try{
		System.out.println("Enter English sentences to be converted to Java. " +
				"Remember that each sentence must end with a period and that the valid names for variables " +
				"are V1 through V200.");
		String txt=input.nextLine();
		ArrayList<String> sentences= new ArrayList<String>();
		//int start=0;
		//break input into array of sentences (separation by comma also counts
		//as different sentence rn, may change later)
		for (int start=0; start< txt.length();){
		for(int i=start;i < txt.length();i++){
			if(txt.charAt(i)=='.' || txt.charAt(i)==','){
				//gets the period or comma too
				//sentences.add(txt.substring(start, i+1));
				//start=i+1;
				
				//does not get the period, easier for bitpar tree
				//to preserve punctuation consider creating a separate array just for it
				sentences.add(txt.substring(start, i));
				start=i+1;
			}
		}
		}
		if(sentences.size()==0){
			System.out.println("No sentences were entered");
		}
		else{
			//just checks input/output correct
		   System.out.println(txt);
		   sentences.add("Set x to \"i set this x\" meow and i to \"hi\" meow and set V1 to 145 hi");
		   System.out.println(sentences.get(0));
		   System.out.println(sentences.get(1));
		   //VERY IMPORTANT:integers should always be stored first
		   storeIntegers(sentences);
		   storeStrings(sentences);
		   System.out.println(sentences.get(1));
		   //send sentences to be proccessed by bitpar and store the output in following arraylist
		   ArrayList<String> bitparOut=new ArrayList<String>();
		   bitparOut.add("(ROOT (NP.nvd.-.-.-.-.- (NP.nvd.base.-.-.p.- (NNP.-.- \"set\")(NNP.p.- \"b\"))(PP.nvd.to.np (TO \"to\")(NP.nvd.base.-.-.-.- (NNP.-.- \"twice\")(NNP.-.- \"x\")))))");
		   //
		   //*important remember to handle and preserve commas and periods
		   //*for now, may later decide that we're not gonna allow commmas
		   //*UPDATE:for now im just using periods to get the sentences but otherwise disregarding punctuation
		   
		   //here loop thru array of sentences, sending each to bitpar somehow
		   //and somehow getting the bitpar output(can this be done?--mayb it 
		   //should be its own program in separate file) 
		   //and storing it in bitparOut arraylist
		   //now loop thru bitparOut and: 1.change parens to brackets
		   //2.remove linguistic classifications(aka letters not within quotes) 
		   //3.remove quotes 
		   //4.the difficult part patrick said he will do=rebracketing
		   //5.the other difficult part: add type tags
		   // to words : this may be more useful to be done even before step 4
		   //--but how? cuz rn im thinking to use the structures from step 4 to c 
		   //what the word takes in to annotate it with type
		   
		   //1
		   parensToBrackets(bitparOut);
		   //2
		   deleteLing(bitparOut);
		   //3
		   deleteQuotes(bitparOut);
		   //4
		   //5
		   //tagWords()
		   System.out.println(bitparOut.get(0));
		   }
		
		}//try
		finally{ input.close();}

	}//main

}

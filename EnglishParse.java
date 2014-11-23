import java.util.ArrayList;
import java.util.Scanner;


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
				"are b through n and t through z.");
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
		   System.out.println(sentences.get(0));
		   //System.out.println(sentences.get(1));
		   ArrayList<String> bitparOut=new ArrayList<String>();
		   bitparOut.add("(ROOT (NP.nvd.-.-.-.-.- (NP.nvd.base.-.-.p.- (NNP.-.- \"set\")(NNP.p.- \"b\"))(PP.nvd.to.np (TO \"to\")(NP.nvd.base.-.-.-.- (NNP.-.- \"twice\")(NNP.-.- \"x\")))))");
		   //
		   //*important remember to handle and preserve commas and periods
		   //*for now, may later decide that we're not gonna allow commmas
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
		   System.out.println(bitparOut.get(0));
		   }
		
		}//try
		finally{ input.close();}

	}//main

}

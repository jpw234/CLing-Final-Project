import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TreeParser {
	public static Map<String,String> strings= new HashMap<String,String>(100);
	public static int currentnumStrings=0;
	public static Map<String,Integer> integers= new HashMap<String,Integer>(200);
	public static int currentnumIntegers=0;
	public static Map<String,Double> doubles= new HashMap<String,Double>(200);
	public static int currentnumDoubles=0;
	
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		ArrayList<String> totaloutput = new ArrayList<String>();
		ArrayList<String> res = new ArrayList<String>();
		String next = input.nextLine(); 
		while(!next.equals("done")){
			if(next.equals("out")){
				System.out.println(totaloutput);
			} else if (next.equals("clear")){
				totaloutput = new ArrayList<String>();
				System.out.println(totaloutput);
			} else if (next.equals("undo")){
				totaloutput.remove(totaloutput.size()-1);
				System.out.println(totaloutput);
			} else {
				res = partition(auto_parens(next));
				for(String exp : res){
					if(!(exp.equals("(") || exp.equals(")"))){
						totaloutput.add(recbracket(exp));
					} else {
						totaloutput.add(exp);
					}
				}
				//stores both doubles and integers
				storeNumbers(totaloutput);
				//store strings
				storeStrings(totaloutput);
				//tag words with types
				addTags(totaloutput);
				System.out.println(totaloutput);
			}
			next = input.nextLine();
		}
		input.close();
		//+integer, double as well as declare ex: "declare integer x"
		//also put in the method that breaks string into sentences
		//now left to tag words w types and send all sentences (excluding brackets) from array/or is it string
		//to lambda calculator
	}
	private static String before(String s){
		//System.out.println("befoe input"+s);
		int closed=0;
		int countdownIndex=0;
		int i=s.length()-1;
		//gets an xtra char but thts alright i hope
		for (;i>=0&&countdownIndex==0;i--){
			if(s.charAt(i)==']'){
				closed++;
				countdownIndex=i;
			}
		}
		int x=countdownIndex-1;
		for(;x>=0&&closed>0;x--){
			if(s.charAt(x)==']'){
				closed++;
			}
			if(s.charAt(x)=='['){
				closed--;
			}
		}
		
		//System.out.println("before["+s.substring(x+1,s.length())+"]");
		String answer=s.substring(x+1,s.length());
		System.out.println(answer);
		if(answer.indexOf("not")==-1 && answer.indexOf("is")==-1)
		return answer;
		else
			return before(s.substring(0,x+1));
	}
	private static String after(String s, boolean twotimes){
		//System.out.println("after input"+s);
		int countdownIndex=0;
		int open=0;
		int i=0;
		for (;i<s.length()&&countdownIndex==0;i++){
			if(s.charAt(i)=='['){
				open++;
				countdownIndex=i;
			}
		}
		int x=countdownIndex+1;
		for(;x<s.length()&&open>0;x++){
			if(s.charAt(x)=='['){
				open++;
			}
			if (s.charAt(x)==']'){
				open--;
			}
			//System.out.println("hey1 "+x+ "open is"+open);
		}
		//System.out.println("hey"+ x);
		//System.out.println("after output "+s.substring(0,x));
		if(!twotimes)
		return s.substring(0,x);
		else
			return after(s.substring(x,s.length()),false);
	}
	private static String typeOfString(String s){
		if(s.indexOf("multiply")!=-1 || 
				s.indexOf("times")!=-1 ||
				s.indexOf("plus")!=-1 ||
				s.indexOf("minus")!=-1 || 
				s.indexOf("divide")!=-1 ||
				s.indexOf("divided")!=-1 ||
				s.indexOf("add")!=-1 ||
				s.indexOf("subtract")!=-1 ||
				s.indexOf("modulo")!=-1 ||
				//s.indexOf("negative")!=-1 ||
				s.indexOf("twice")!=-1){
			return "t";
		}
		return "e";
	}
	private static boolean wordTypeAfter(String s){
		boolean b=false;
		switch(s){
		//noooo, set is 2 after that is the after after the first after case "set": b=true; break;
		//later test and add case "twice"
		//also test and add later case "negative"
		case "by":  b=true; break;
		case "to": b=true; break;
		case "from": b=true; break;
		case "than": b=true; break;
		case "of": b=true; break;
		case "print": b=true; break;
		case "return": b=true; break;
		default:b=false;break;
		}
		return b;
	}
	private static boolean wordTypeAfterAfter(String s){
		boolean b=false;
		switch(s){
		case "multiply": b=true; break;
		//later test and add case "twice"
		//also test and add later case "negative"
		case "divide":  b=true; break;
		case "add": b=true; break;
		case "subtract": b=true; break;
		default:b=false;break;
		}
		return b;
	}
	private static boolean wordTypeBeforeAfter(String s){
		boolean b=false;
		switch(s){
		case "times": b=true; break;
		//later test and add case "twice"
		//also test and add later case "negative"
		case "plus":  b=true; break;
		case "minus": b=true; break;
		case "greater": b=true; break;
		case "less": b=true; break;
		case "equal": b=true; break;
		case "divided": b=true; break;
		default:b=false;break;
		}
		return b;
	}
	//to get string after word to be tagged, use .sublist(from,to) for the input from addTags
	private static String arrListToString(List<String> list){
		String str="";
		for(int i=0;i<list.size();i++){
			//System.out.println("adding"+list.get(i));
			str=str.concat(list.get(i));
		}
		//System.out.println("the string from array is "+str);
		return str;
	}
	private static void addTags(ArrayList<String> a){
		for(int i=0;i<a.size();i++){
			String thisOne=a.get(i);
			//for each taggable word, check what is in before/after to determine type
			//make array of  and booleans, if bracket or whitespace, put it in
			//if encounter characters, put the whole sequence in and true
			//then in the array, all the words to be tagged are the ones that are characters
			ArrayList<String> untagged=new ArrayList<String>();
			for (int c=0;c<thisOne.length();c++){
				if(thisOne.charAt(c)=='['||thisOne.charAt(c)==']' ||thisOne.charAt(c)==' '){
					untagged.add(""+thisOne.charAt(c));
				}
				else{
					int j=c;
					while(thisOne.charAt(j)!='['&& thisOne.charAt(j)!=']' && thisOne.charAt(j)!=' '){
						j++;
					}
					untagged.add(thisOne.substring(c,j));
					c=j-1;
					}
				}
			//now that we have populated untagged...
			//System.out.println("hi"+untagged.get(4));
			for(int w=0;w<untagged.size();w++){
				String toTag=untagged.get(w);
				//System.out.println("totag  "+toTag);
				if(!toTag.equals("[")&&!toTag.equals("]")&&!toTag.equals(" ")){
					//which of the 3 sets is it from
					if(toTag.equals("set")){
						String typeafter2=typeOfString(after(arrListToString(untagged. subList(w+1,untagged.size())),true));
						if(!typeafter2.equals("e")){
							untagged.set(w,toTag.concat("-").concat(typeafter2));
						}
					}
					else
					if(wordTypeAfter(untagged.get(w))){
						String type=typeOfString(after(arrListToString(untagged. subList(w+1,untagged.size())),false));
						if(!type.equals("e")){
							untagged.set(w,toTag.concat("-").concat(type));
						}
					}
					else if(wordTypeAfterAfter(untagged.get(w))){
						String typeafter1=typeOfString(after(arrListToString(untagged. subList(w+1,untagged.size())),false));
						String typeafter2=typeOfString(after(arrListToString(untagged. subList(w+1,untagged.size())),true));
						String totaltype=typeafter1+typeafter2;
						if(!totaltype.equals("ee")){
							untagged.set(w,toTag.concat("-").concat(totaltype));
						}
						//System.out.println("Not yet");
					}
					else if(wordTypeBeforeAfter(untagged.get(w))){
						String typeafter=typeOfString(after(arrListToString(untagged. subList(w+1,untagged.size())),false));
						String typebefore=typeOfString(before(arrListToString(untagged. subList(0,w))));
						//reversed because i thought it would take left first, but the parser now it takes right first(that is the after comes first)
						String totaltype;
						if(!toTag.equals("divided")){
						totaltype=typeafter+typebefore;
						}
						else{
						 totaltype=typebefore+typeafter;
						}
						if(!totaltype.equals("ee")){
							untagged.set(w,toTag.concat("-").concat(totaltype));
						}
						//System.out.println("not yet");
						
					}
					//based on the set, call before/after to figure type
					//System.out.println(untagged.get(w));
				}
			}
			//now that untagged has been tagged
			a.set(i,arrListToString(untagged));
			}
			
		}
	
	
	private static void storeStrings(ArrayList<String> a){
		for (int i=0;i<a.size();i++){
			//String thisOne=a.get(i);//original string
			//replace any string with s1/2/etc
			for (int c=0;c<a.get(i).length();c++){
				//System.out.println(c);
			    if (a.get(i).charAt(c)=='"'){
			    	int start=c;
			    	//System.out.println(start);
			    	int end=c;
			    	String s="";
			    	for(int z=c+1;z<a.get(i).length();z++){
			    		//System.out.println("aqui");
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
			    	//System.out.println("start"+start);
			    	String switchesTo=a.get(i).substring(0,start)+tag+a.get(i).substring(end+1,a.get(i).length());
			    	//System.out.println(switchesTo);
			    	//System.out.println("eeeee: "+a.get(i));
			    	a.set(i,switchesTo);
			    	//System.out.println("mmmm: "+a.get(i));
			    	c=start+tag.length();
			    	//System.out.println("new c "+c);
			    }
			}
		}
	}
	
	
	private static void storeNumbers(ArrayList<String> a){
		for (int i=0;i<a.size();i++){
			//String thisOne=a.get(i);//original string
			//replace any string with s1/2/etc
			for (int c=0;c<a.get(i).length();c++){
				//System.out.println(c);
			    if (Character.isDigit(a.get(i).charAt(c))){
			    	boolean doublenumber=false;
			    	int start=c;
			    	//System.out.println(start);
			    	String s="";
			    	if(c-1>=0)
			    	if(a.get(i).charAt(c-1)=='-'){
			    		//System.out.println("negative");
			    		//System.out.println(Integer.parseInt("-2"));
			    		s+=a.get(i).charAt(c-1);
			    		start=c-1;
			    	}
			    	s+=a.get(i).charAt(c);
			    	int end=c;
			    	for(int z=c+1;z<a.get(i).length();z++){
			    		//System.out.println("aqui");
			    		if(Character.isDigit(a.get(i).charAt(z)))
			    			s+=a.get(i).charAt(z);
			    		else if(a.get(i).charAt(z)=='.'){
			    			doublenumber=true;
			    			s+=a.get(i).charAt(z);
			    		}
			    		else {end=z;break;}
			    	}
			    	//to not confuse integers with variables V1-V200 or other integers already replaced or strings already replaced
			    	//or double already replaced
			    	char ch=a.get(i).charAt(c-1);
			    	if(ch!='V' && ch!='N'&& ch!='S' && ch!='D' ){
			    	String tag="";
			    	if(!doublenumber){
			    		currentnumIntegers++;
			    	   tag="N";
					   tag+=currentnumIntegers;
			           //puts the string and its tag into the map
			    	   integers.put(tag,Integer.parseInt(s));
			    	  }
			    	else if(doublenumber){
			    		currentnumDoubles++;
			    		tag="D";
						tag+=currentnumDoubles;
						Scanner sc= new Scanner(s);
				        //puts the string and its tag into the map
				    	doubles.put(tag,sc.nextDouble());
				    	sc.close();
			    	  }
			    	//replaces the string with the tag in the original string
			    	//System.out.println("start"+start);
			    	String switchesTo=a.get(i).substring(0,start)+tag+a.get(i).substring(end,a.get(i).length());
			    	//System.out.println(switchesTo);
			    	//System.out.println("eeeee: "+a.get(i));
			    	a.set(i,switchesTo);
			    	//System.out.println("mmmm: "+a.get(i));
			    	c=start+tag.length();
			    	//System.out.println("new c "+c);
			    	}
			    	else{
			    		c=end;
			    	}
			    }
			}
		}
	}
	
	//will always create a valid group of parentheses if omitted, may not give a desired grouping though
	//will not fix incorrectly inserted parenthesis though (make sure to parenthesize correctly)
	//the 'then' keyword is no longer required, but still can be used
	public static String auto_parens(String str){
		String[] tmp = str.split("\\s+");
		String[] cmds = new String[]{"(if", "(while", "return", "print", "increment", "decrement", "set", "multiply", "add", "subtract", "divide", "declare", "remainder"};
		String result = "";
		String tmpcombined = "";
		int parenscnt = 0;
		int ifcnt = 0;
		for(int i=1; i<tmp.length; i++){
			if(tmp[i-1].equals("if") || tmp[i-1].equals("while")){
				ifcnt++;
				result = result + " " + tmp[i-1];
			} else if(tmp[i].equals("else") || tmp[i].equals("otherwise")){
				String tmpelse = " else";
				tmpcombined = tmp[i-1] + tmp[i];
				if(!tmpcombined.contains(")")){
					tmpelse = " )" + tmpelse;
					parenscnt--;
				}
				if(i < tmp.length-1){
					tmpcombined = tmp[i] + tmp[i+1];
					if(!tmpcombined.contains("(")){
						tmpelse = tmpelse + " (";
						parenscnt++;
					}
				}
				result = result + " " + tmp[i-1] + tmpelse;
				i++;
			} else {
				tmpcombined = tmp[i-1] + tmp[i];
				if(!tmpcombined.contains("(")){
					if(arrayfind(cmds, tmp[i]) > -1 && ifcnt > 0){
						result = result + " " + tmp[i-1] + " (";
						parenscnt++;
						ifcnt--;
					} else if (tmp[i].equals("if") || tmp[i].equals("while")){
						result = result + " " + tmp[i-1] + " ( " + tmp[i];
						parenscnt++;
						i++;
					} else {
						result = result + " " + tmp[i-1];
					}
				} else {
					result = result + " " + tmp[i-1];
					ifcnt--;
				}
			}
		}
		result = result + " " + tmp[tmp.length-1];
		while(parenscnt > 0){
			result = result + " )";
			parenscnt--;
		}
		return result;
	}
	
	//also swaps otherwise to else
	public static String trimthen(String str){
		int c = str.length()-1;
		if(c == -1){
			return "";
		} else if (str.equals("otherwise")) {
			return "else";
		}
		while(str.charAt(c) == ' '){
			c--;
		}
		c++;
		int b = 0;
		while(str.charAt(b) == ' '){
			b++;
		}
		if(c >= 4 && str.substring(c-4,c).equals("then")){
			return str.substring(b,c-4);
		}
		return str.substring(b,c);
	}
	
	public static ArrayList<String> partition (String str){
		ArrayList<String> res = new ArrayList<String>();
		String tmp = ""; String tmpres = "";
		for(int i=0; i<=str.length(); i++){
			if(i == str.length()){
				if(tmp.trim().length() > 0){
					tmpres = trimthen(tmp);
					if(!tmpres.equals("")){
						res.add(tmpres);					
					}
				}
			} else if(str.charAt(i) != ')' && str.charAt(i) != '(' && str.charAt(i) != ','){
				tmp = tmp + str.substring(i,i+1);
			} else {
				if(tmp.trim().length() > 0){
					tmpres = trimthen(tmp);
					if(!tmpres.equals("")){
						res.add(tmpres);					
					}
				}
				if(str.charAt(i) != ','){
					res.add(str.substring(i,i+1));
				}
				tmp = "";
			}
		}
		return res;
	}
	
	public static String recombine(String[] words, String delim, int a, int b){
		String result = "";
		for(int i=a; i<b; i++){
			result = result + delim + words[i];
		}
		return result.substring(delim.length());
	}
	
	public static int arrayfind(String[] words, String key){
		for(int i=0; i<words.length; i++){
			if(words[i].equals(key)){
				return i;
			}
		}
		return -1;
	}
	
	public static int lists_search(String[] items, String[] lst){
		int result = -1;
		for(String item : lst){
			result = arrayfind(items, item);
			if(result != -1){
				return result;
			}
		}
		return result;
	}
	
	public static String recbracket(String str){
		String[] tmp = str.split("\\s+");
		String[] comparator = new String[]{"greater", "equal", "less"};
		String[] unary_ops = new String[]{"twice", "not", "negative", "print", "return", "increment", "decrement","integer","boolean","double","string","declare"};
		String[] short_ops = new String[]{"times", "plus", "minus", "modulo", "mod"};
		String[] long_ops = new String[]{"remainder","multiply", "add", "subtract", "divide"};
		String[] passive_ops = new String[]{"multiplied", "divided", "added", "subtracted"};
		int comparator_i = lists_search(tmp, comparator);
		int ops_long_i = lists_search(tmp, long_ops);
		int ops_short_i = lists_search(tmp, short_ops);
		int unary_ops_i = lists_search(tmp, unary_ops);
		int passive_ops_i = lists_search(tmp, passive_ops);
		if(tmp[0].equals("if")){
			return if_only(str);
		} else if (tmp[0].equals("while")){
			return while_block(str);
		} else if (tmp[0].equals("set")){
			return setvar(str);
		} else if (comparator_i > -1){
			return compare_than(str);
		} else if (arrayfind(tmp, "and") > -1 || arrayfind(tmp, "or") > -1){ 
			return and_or(str);
		} else if (unary_ops_i > -1){
			return unary_ops(str);
		} else if (tmp[0].equals("a") || tmp[0].equals("an")){
			 return a_an(str);
		} else {
			if(((ops_short_i < ops_long_i) || ops_long_i == -1) && ops_short_i > -1){
				return ops_short(str);
			} else if(ops_long_i > -1) {
				return ops_long(str);
			} else if (passive_ops_i > -1){
				return passive_ops(str);
			}
		}
		if(tmp.length == 2 && tmp[0].equals("than")){
			return "[[than] [" + tmp[1] + "]]";
		}
		return "[" + str + "]";
	}
	
	public static String a_an(String str){
		String[] tmp = str.split("\\s+");
		if(tmp[0].equals("a") || tmp[0].equals("an")){
			return "[[" + tmp[0] + "] [" + recbracket(recombine(tmp," ", 1, tmp.length)) + "]]";
		}
		System.out.println("You broke an invariant, needs start with a or an");
		return "";	
	}
	
	public static String if_only(String str){
		String[] tmp = str.split("\\s+");
		if(tmp[0].equals("if")){
			return "[[if] " + recbracket(recombine(tmp," ", 1, tmp.length)) + "]";
		}
		System.out.println("You broke an invariant, needs to be if block only");
		return "";	
	}
	
	public static String while_block(String str){
		String[] tmp = str.split("\\s+");
		if(tmp[0].equals("while")){
			return "[[while] " + recbracket(recombine(tmp," ", 1, tmp.length)) + "]";
		}
		System.out.println("You broke an invariant, needs to be while block only");
		return "";
	}

	public static String setvar(String str){
		String[] tmp = str.split("\\s+");
		if(tmp.length > 3 && tmp[0].equals("set") && tmp[2].equals("to")){
			return "[[[set] [" + tmp[1] + "]] [[to] " + recbracket(recombine(tmp," ", 3, tmp.length)) + "]]";
		}
		System.out.println("needs correct syntax for set");
		return "";
	}
	
	public static String and_or(String str){
		String[] tmp = str.split("\\s+");
		int and_i = arrayfind(tmp, "and");
		int or_i = arrayfind(tmp, "or");
		if(and_i != -1){
			return "[[" + recbracket(recombine(tmp," ",0,and_i)) + "] [[and] [" + recbracket(recombine(tmp, " ", and_i+1, tmp.length)) + "]]]";
		} else if (or_i != -1){
			return "[[" + recbracket(recombine(tmp," ",0,or_i)) + "] [[or] [" + recbracket(recombine(tmp, " ", or_i+1, tmp.length)) + "]]]";
		}
		System.out.println("not an and/or expression");
		return "";
	}
	
	public static String point(String str){
		String[] tmp = str.split("\\s+");
		int point_i = arrayfind(tmp, "point");
		if(tmp.length > point_i + 1 && point_i > -1){
			return "[["+ recbracket(recombine(tmp," ",0,point_i)) + " [point]] [" + recbracket(recombine(tmp," ", point_i+1, tmp.length)) + "]]";
		}
		System.out.println("wrong syntax for point");
		return "";
	}
	
	public static String compare_than(String str){
		String[] tmp = str.split("\\s+");
		int is_i = arrayfind(tmp, "is");
		if(tmp.length > is_i + 2 && is_i > -1){
			if((tmp[is_i+1].equals("greater") || tmp[is_i+1].equals("less") || tmp[is_i+1].equals("equal")) && (tmp[is_i+2].equals("than") || tmp[is_i+2].equals("to"))){
				return "[[" + recbracket(recombine(tmp," ",0,is_i)) + "] [[is] [[" + tmp[is_i+1] + "] [[" + tmp[is_i+2] + "] " + recbracket(recombine(tmp," ",is_i+3,tmp.length)) + "]]]]";
			} else if (tmp[is_i+1].equals("not") && tmp.length > is_i + 3){
				return "[[" + recbracket(recombine(tmp," ",0,is_i)) + "] [[is] [[" + tmp[is_i+1] + "] [[" + tmp[is_i+2] + "] " + recbracket(recombine(tmp," ",is_i+3,tmp.length)) + "]]]]";
			}
		} 
		System.out.println("syntax error in comparative functions");
		return "";
	}
	
	public static String ops_short(String str){
		String[] tmp = str.split("\\s+");
		String[] ops = new String[]{"times", "plus", "minus", "modulo", "mod"};
		int op_i = lists_search(tmp, ops);
		if(op_i > -1 && tmp.length > op_i + 1){
			return "[" + recbracket(recombine(tmp," ",0,op_i)) + " [["+ tmp[op_i] + "] " + recbracket(recombine(tmp," ", op_i+1, tmp.length)) + "]]";
		} else if (op_i == 0){
			return "[" + tmp[0] + "]";
		}
		System.out.println("syntax error for 'shorter' operations");
		return "";
	}
	
	public static String ops_long(String str){
		String[] tmp = str.split("\\s+");
		String[] ops = new String[]{"remainder","multiply", "add", "subtract", "divide"};
		String[] cmpwords = new String[]{"of","by", "to", "from"};
		int op_i = lists_search(tmp, ops);
		int word_i = lists_search(tmp, cmpwords);
		if(tmp[op_i].equals("remainder") && tmp[word_i].equals("of") && op_i == 0 && word_i == 1){
			return "[[remainder] [[of] " + recbracket(recombine(tmp," ",2,tmp.length)) + "]]";
		} else if(op_i == 0 && word_i > -1 & tmp.length > word_i + 1){
			return "[[[" + tmp[0] + "] " + recbracket(recombine(tmp," ",1,word_i)) + "] [[" + tmp[word_i] + "] " + recbracket(recombine(tmp," ",word_i+1, tmp.length)) + "]]";
		} else if (op_i == 0){
			return "[" + tmp[0] + "]";
		}
		System.out.println("syntax error for 'longer' operations");
		return "";
	}
	
	public static String passive_ops(String str){
		String[] tmp = str.split("\\s+");
		String[] ops = new String[]{"multiplied", "divided", "added", "subtracted"};
		String[] cmpwords = new String[]{"by", "to", "from"};
		int op_i = lists_search(tmp, ops);
		int word_i = lists_search(tmp, cmpwords);
		if(op_i > -1 && word_i > -1 && op_i + 1 == word_i){
			return "[[" + recbracket(recombine(tmp," ",0,op_i)) + " [" + tmp[op_i] + "]] [[" + tmp[word_i] + "] " + recbracket(recombine(tmp," ",word_i+1,tmp.length)) + "]]";
		}
		System.out.println("syntax error for 'passive' operations");
		return "";
	}
	
	public static String unary_ops(String str){
		String[] tmp = str.split("\\s+");
		String[] ops = new String[]{"twice", "not", "negative", "print", "return", "increment", "decrement","declare","integer","boolean","double","string"};
		int op_i = lists_search(tmp, ops);
		if(op_i == 0 && tmp.length > op_i + 1){
			return "[[" + tmp[0] + "] " + recbracket(recombine(tmp, " ", 1, tmp.length)) + "]";
		} else if (op_i == 0){
			return "[" + tmp[0] + "]";
		}
		System.out.println("syntax error for 'unary' operations");
		return "";
	}
}


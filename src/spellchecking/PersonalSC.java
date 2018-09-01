package spellchecking;

import java.io.*;
import java.util.*;

public class PersonalSC extends InteractiveSC {

	protected int personalDictSize = 0;
	protected int lastPos = 0;
	BufferedWriter output = null;
	protected List<String> mainDict;
	protected List<String> personalDict;
	 

	public PersonalSC(String str1, boolean ignoreCase, Scanner stdin,
			PrintWriter stdout, String str2) {
		super(str1, ignoreCase, stdin, stdout);
		
//		mainDict = new ArrayList<>();
//		personalDict = new ArrayList<>();
	}

	public boolean isCorrect(String string) {
//		print(mainDict.size());

		if (ignoreCase) {
			for (int i = 0; i < mainDict.size(); i++) {
				if (string.equalsIgnoreCase(mainDict.get(i)))
					return true;
			}
			return false;
		} else {
			if (mainDict.contains(string))
				return true;
			else
				return false;
		}
	}

	public String correctWord(String word) {
		String correct_word = null;
		Scanner scan = new Scanner(System.in);
		String temp = null;
		String yes_no = null;

		int score = 50000;
		if (ignoreCase) {
			for (int i = 0; i < mainDict.size(); i++) {
				temp = mainDict.get(i);
				if (score > StringComparison.editDistance(temp.toLowerCase(),
						word.toLowerCase())) {
					score = StringComparison.editDistance(temp.toLowerCase(),
							word.toLowerCase());
					correct_word = temp;
				}
			}
		} else {
			for (int i = 0; i < mainDict.size(); i++) {
				temp = mainDict.get(i);
				if (score > StringComparison.editDistance(temp, word)) {
					score = StringComparison.editDistance(temp, word);
					correct_word = temp;
				}
			}
		}

		print("@- **" + word + "** not in dictionary add it? (yes / no)");
		// line_catch = scan.nextLine();
		yes_no = scan.nextLine();
		if (yes_no.equals("yes")) {
//			 print(dictSize);
			 mainDict.add(word);
			 dictSize = mainDict.size();
			 personalDict.add(word);
			 personalDictSize = personalDict.size();
			 correct_word=word;
			 //savePersonalDict();
			 //try {
				//output.write(word);
				//} catch (IOException e) {
					//e.printStackTrace();
				//}
//			 print(dictSize+" "+personalDictSize);

		} else {
			print("@- Correction for **" + word + "** : " + correct_word);
			print("@ Corrected to : " + correct_word);
		}
		return correct_word;
	}
	
	public Document correctDocument(Document doc) {
//		mainDict = Arrays.asList(dictWord);
		mainDict = new ArrayList<>();
		personalDict = new ArrayList<>();
		lastPos = 0;
		
		for(String s:dictWord)
		{
			mainDict.add(s);
		}
//		print(mainDict.size());
//		mainDict.add("hello");
		String next = null;
		while (doc.hasNextWord()) {
			next = doc.nextWord();
			
			if (!isCorrect(next)) {
				doc.replaceLastWord("**" + next + "**");
				print("@ MISSPELLING in: "+doc.toString());
				doc.replaceLastWord(correctWord(next));
//		print(isCorrect(next));

			}
		}
		return doc;
	}


	public void getAllPersonalDictWords() {
//		print(personalDictSize);
		for (int i = 0; i < personalDict.size(); i++) {
			print(personalDict.get(i));
		}
	}

	public void savePersonalDict() {
		try {
			output = new BufferedWriter(
					new FileWriter("personal-dict.txt",true));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		print(lastPos+" "+personalDict.size());
		for (int i = lastPos; i < personalDict.size(); i++) {
			try {
				output.write(personalDict.get(i)+"\n");
				print(personalDict.get(i));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		lastPos = personalDictSize;
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		PersonalSC sc = new PersonalSC("english-dict.txt", true, in, out,
				"personal-dict.txt");
		sc.getAllPersonalDictWords();
		sc.savePersonalDict();
		print(sc.isCorrect("potatoe"));
	}

}

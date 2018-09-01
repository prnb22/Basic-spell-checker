package spellchecking;

import java.io.*;
import java.util.*;

public class InteractiveSC extends SpellChecker{
	Scanner stdin=null;
	PrintWriter stdout=null;
	public InteractiveSC(String string, boolean ignoreCase,Scanner stdin,PrintWriter stdout)
	{
		super(string,ignoreCase);
		this.stdin = stdin;
		this.stdout = stdout;
		String s = stdin.nextLine();
		Document doc = new Document(s);
		stdout.append(correctDocument(doc).toString());
		stdout.flush();
	}

	public String correctWord(String word) {
		if (isCorrect(word))
			return word;
		String correct_word = null;
		int score = 50000;
		StringComparison check = new StringComparison();
		if (ignoreCase) {
			for (String i : dictWord) {
				if (score > check.editDistance(i.toLowerCase(), word.toLowerCase())) {
					score = check.editDistance(i.toLowerCase(), word.toLowerCase());
					correct_word = i;
				}
			}
		} else {
			for (String i : dictWord) {
				if (score > check.editDistance(i, word)) {
					score = check.editDistance(i, word);
					correct_word = i;
				}
			}
		}
		print("@- Correction for **"+word+"** : "+correct_word);
		print("@ Corrected to : "+correct_word);

		return correct_word;
	}
	
	public Document correctDocument(Document doc) {
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
	
	public static void main(String[] args) {
//		Scanner in = new Scanner(System.in);
//		PrintWriter out = new PrintWriter(System.out,true);
//		InteractiveSC sc = new InteractiveSC("english-dict.txt",true,in,out);
//        print(sc.dictSize());
//        print(sc.correctWord("potatoe"));
//        print(sc.isCorrect("potato"));

	}

}

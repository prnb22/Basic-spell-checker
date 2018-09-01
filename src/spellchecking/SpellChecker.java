package spellchecking;

import java.io.*;
import java.util.*;

public class SpellChecker {

	protected boolean ignoreCase;
	protected String dictWord[]=null;
	protected int dictSize = 0;
//	protected List<String> personalDict = new ArrayList<>();


	// constructor
	public SpellChecker(String string, boolean ignoreCase) {

		this.ignoreCase = ignoreCase;
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(string)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		dictWord = readAllLines(string);
		dictSize = dictWord.length;
//		print(dictSize);
	}

	// print method
	public static void print(Object s) {
		System.out.println(s);
	}

	// readAllLine method
	public static String[] readAllLines(String string) {
		BufferedReader inputFile = null;
		try {
			inputFile = new BufferedReader(new InputStreamReader(
					new FileInputStream(string)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String correctWord = "";
		List<String> array = new ArrayList<>();

		try {
			while (inputFile != null
					&& (correctWord = inputFile.readLine()) != null) {

				array.add(correctWord);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] words = new String[array.size()];
		words = array.toArray(words);
		return words;
	}

	// method for dictionary size
	public int dictSize() {
		return dictSize;
	}

	// method for correctness of a word
	public boolean isCorrect(String string) {
		for (String i:dictWord) {
			if (ignoreCase)
			{
				if(string.equalsIgnoreCase(i))return true;
			}
			else 
			{
				if(string.equals(i))return true;
			}
		}
		return false;
	}

	// method for word correction
	public String correctWord(String word) {
		if (isCorrect(word))
			return word;
		String correct_word = null;
		int score = 50000;
		StringComparison check = new StringComparison();
		if (ignoreCase) {
			word = word.toLowerCase();
			for (String i : dictWord) {
				if (score > check.editDistance(i.toLowerCase(), word)) {
					score = check.editDistance(i.toLowerCase(), word);
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
		return correct_word;
	}

	public Document correctDocument(Document doc) {
		String next = null;

		while (doc.hasNextWord()) {
			next = doc.nextWord();
			if (!isCorrect(next)) {
				doc.replaceLastWord("**" + next + "**");
			}
		}
		return doc;
	}

	public static void main(String[] args) throws IOException {
//		 SpellChecker sc = new SpellChecker("english-dict.txt", true);
		// String[] line = sc.readAllLines("english-dict.txt");
		// System.out.println(sc.dictSize());
//		 System.out.println(sc.isCorrect("aAA"));
		// StringComparison p = new StringComparison();
		// System.out.println(p.editDistance("aAA","AAA"));
		// System.out.println(p.editDistance("AAA","Zzz"));

		// System.out.println(sc.correctWord("potatoe"));
		// // System.out.println(sc.correctWord("AAA"));
		//
//		 Document doc = new Document(
//		 "One potatoe, two tumatoes, three potatoes, four. I misunderestimated how many potatoes.");
//		 doc = sc.correctDocument(doc);
//		 print(doc.toString());

	}

}
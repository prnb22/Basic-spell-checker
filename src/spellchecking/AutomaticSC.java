package spellchecking;

public class AutomaticSC extends SpellChecker {

	public AutomaticSC(String string, boolean ignoreCase) {
		super(string, ignoreCase);
	}

	
	public String correctWord (String word) {
		String correct_word;
		correct_word = super.correctWord(word);
		if(ignoreCase)return matchCase(word,correct_word);
		else return correct_word;

	}

	public static String matchCase(String word, String correct_word) {
		int word_len = word.length(), correct_len = correct_word.length(),cnt = 0;
		char ch;
		for (int i = 0; i < word_len; i++) {
			ch = word.charAt(i);
			if (Character.isUpperCase(ch)) {
				cnt++;
			}
		}
		if (cnt == word_len)correct_word=correct_word.toUpperCase();
		else if (cnt == 0)correct_word=correct_word.toLowerCase();
		else {
			if (cnt == 1 && Character.isUpperCase(word.charAt(0))) {
				correct_word= correct_word.substring(0, 1).toUpperCase() + correct_word.substring(1);
			} else correct_word=correct_word.toLowerCase();
		}
		return correct_word;
	}

	public static void main(String[] args) {
//		AutomaticSC sc = new AutomaticSC("english-dict.txt", true);
//		Document doc = new Document(
//				"One Potatoe, two Tumatoes, three potatoes, four. I misunderestimated how many potatoes.");
//		print(sc.correctWord("INHERITANCC"));
//		print(sc.correctWord("Inheritence"));
//		print(sc.correctWord("INheritence"));

	}

}

package spellchecking;

import java.util.*;
import java.io.*;

// Simple, editable document. Contents are initialized with a
// string. Allows scanning through the document by word with calls to
// nextWord() and hasNextWord() with rewind() resetting back to the
// beginning of the document.  Words can be replaced via calls to
// replaceLastWord(str). Display contents with toString() and
// debugString().
public class Document{
  // Contents of the document. StringBuilder allows modification of
  // the document contents.
  private StringBuilder contents;

  // Position of the first and last character+1 of the marked word;
  // both are -1 on initialization and calls to reset()
  private int beginWordPos, endWordPos;

  // Construct a document with the given contents
  public Document(String contents){
    this.contents = new StringBuilder(contents);
    this.beginWordPos = -1;
    this.endWordPos = -1;
  }

  // Read entire contents of a file and return as a string
  private static String slurp(File file) throws Exception{
    return new Scanner(file, "UTF-8").useDelimiter("\\Z").next();
  }

  // Construct a document contents initialized from the given file
  public Document(File file) throws Exception{
    this(slurp(file)); // calls the other constructor.
  }

  // Returns a string representation of the entire document
  public String toString(){
    return contents.toString();
  }

  // Returns a string representation of the document with the contents
  // modified to mark the word selected by nextWord()
  public String debugString(){
    if(beginWordPos==-1){
      return this.contents.toString(); // No word marked
    }
    StringBuilder debugStr = new StringBuilder(this.toString());
    debugStr.insert(endWordPos,"<<");
    debugStr.insert(beginWordPos,">>");
    return debugStr.toString();
  }

  // Locate the position of the next word beginning at position start
  // or later. Return -1 if no words exist after the given start
  // position.
  private int startOfNextWord(int start){
    for(int pos=start; pos<this.contents.length(); pos++){
      char c = this.contents.charAt(pos);
      if(Character.isLetter(c)){
        return pos;
      }
    }
    return -1;
  }

  // Locate the end of the word starting after the indicated position;
  // the returned integer will be position character after the last
  // character belonging to the word to facilitate
  // StringBuilder.delete() calls.
  private int endOfWord(int startAfter){
    int pos;
    for(pos=startAfter+1; pos<this.contents.length(); pos++){
      char c = this.contents.charAt(pos);
      if(!Character.isLetter(c) && c!='\''){ // include contractions with apostrophe
        return pos;
      }
    }
    return pos;                 // Off the end of the doc, return ending pos
  }
    
  // Return the next word in the document starting with the first
  // word. Ignores punctuation and numbers. Throws an exception if
  // there are no words remaining in the document.
  public String nextWord(){
    if(this.endWordPos == -1){
      this.endWordPos = 0;
    }
    int begin = startOfNextWord(this.endWordPos);
    if(begin==-1){
      throw new RuntimeException("No words remain in the document");
    }
    this.beginWordPos = begin;
    this.endWordPos = endOfWord(begin);
    return this.contents.substring(this.beginWordPos, this.endWordPos);
  }

  // Return true if the document contains any more words so that a
  // call to nextWord() would succeed. Returns false if no words
  // remain in the document.  Punctuation and numbers do not count as
  // words.
  public boolean hasNextWord(){
    if(this.endWordPos == -1){
      this.endWordPos = 0;
    }
    int begin = startOfNextWord(this.endWordPos);
    return begin != -1;
  }
  

  // Reset the internal position of the document so that a subsequent
  // call to nextWord() will return the first word in the document
  public void rewind(){
    this.endWordPos = -1;
    this.beginWordPos = -1;
  }

  // Replace the last word returned by nextWord() with the given
  // correction.  Internal positioning will be adjusted so that
  // subsequent calls to nextWord() will move beyond the supplied
  // correction. Throws an exception if nextWord() has not been called
  // appropriately (ex: immediately after construction or after a call
  // to rewind())
  public void replaceLastWord(String correction){
    this.contents.delete(this.beginWordPos, this.endWordPos);
    this.contents.insert(beginWordPos,correction);
    this.endWordPos = this.beginWordPos + correction.length();
  }

  // Return a string showing the line of the document which contains
  // the last word returned by nextWord().  Returns the first line of
  // the document if called after construction or a call to rewind().
  public String currentLine(){
    // Search for backwards from beginWordPos, then search forward
    // from it for newlines
    int beginLine = this.beginWordPos;
    if(beginLine==-1){
      beginLine = 0;
    }
    for(; beginLine>0; beginLine--){
      if(this.contents.charAt(beginLine)=='\n'){
        break;
      }
    }
    int endLine = beginLine+1;
    for(; endLine < this.contents.length(); endLine++){
      if(this.contents.charAt(endLine) == '\n'){
        break;
      }
    }
    String line = this.contents.substring(beginLine, endLine);
    return line;
  }


  public static final String gettysburg =
    "Four score and seven years ago our fathers brought forth on this continent, a\n"+
    "new nation, conceived in Liberty, and dedicated to the proposition that all men\n"+
    "are created equal.\n"+
    "\n"+
    "Now we are engaged in a great civil war, testing whether that nation, or any\n"+
    "nation so conceived and so dedicated, can long endure. We are met on a great\n"+
    "battle-field of that war. We have come to dedicate a portion of that field, as a\n"+
    "final resting place for those who here gave their lives that that nation might\n"+
    "live. It is altogether fitting and proper that we should do this.\n"+
    "\n"+
    "But, in a larger sense, we can not dedicate -- we can not consecrate -- we can\n"+
    "not hallow -- this ground. The brave men, living and dead, who struggled here,\n"+
    "have consecrated it, far above our poor power to add or detract. The world will\n"+
    "little note, nor long remember what we say here, but it can never forget what\n"+
    "they did here. It is for us the living, rather, to be dedicated here to the\n"+
    "unfinished work which they who fought here have thus far so nobly advanced. It\n"+
    "is rather for us to be here dedicated to the great task remaining before us --\n"+
    "that from these honored dead we take increased devotion to that cause for which\n"+
    "they gave the last full measure of devotion -- that we here highly resolve that\n"+
    "these dead shall not have died in vain -- that this nation, under God, shall\n"+
    "have a new birth of freedom -- and that government of the people, by the people,\n"+
    "for the people, shall not perish from the earth.\n"+
    "\n"+
    "Abraham Lincoln\n"+
    "November 19, 1863\n"+
    "";
  

}

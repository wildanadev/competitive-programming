public class GoatLatin {
  public String toGoatLatin(String sentence) {
    String[] sentenceArray = sentence.split(" ");
    StringBuilder sb = new StringBuilder();
    StringBuilder sbA = new StringBuilder();
    for (int i = 0; i < sentenceArray.length; i++) {
      sbA.append('a');
      sb.append(' ');
      if (isFirstWordAVowel(sentenceArray[i])) sb.append(sentenceArray[i]);
      else {
        sb.append(sentenceArray[i].substring(1, sentenceArray[i].length()));
        sb.append(sentenceArray[i].charAt(0));
      }
      sb.append("ma");
      sb.append(sbA);
    }
    return sb.toString().substring(1);
  }

  private boolean isFirstWordAVowel(String word) {
    char firstLetter = word.charAt(0);
    return firstLetter == 'a'
        || firstLetter == 'e'
        || firstLetter == 'i'
        || firstLetter == 'o'
        || firstLetter == 'u'
        || firstLetter == 'A'
        || firstLetter == 'E'
        || firstLetter == 'I'
        || firstLetter == 'O'
        || firstLetter == 'U';
  }
}

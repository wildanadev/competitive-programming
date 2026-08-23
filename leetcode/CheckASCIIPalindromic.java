public class CheckASCIIPalindromic {
  public boolean isPalindromic(String s) {
    StringBuilder binary = new StringBuilder();
    for (char c : s.toCharArray()) {
      String binaryString = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
      binary.append(binaryString);
    }
    return binary.toString().equals(new String(binary.reverse().toString()));
  }
}

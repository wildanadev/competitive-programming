public class _1BitAnd2BitCharacters {
  public boolean isOneBitCharacter(int[] bits) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bits.length; i++) {
      if (bits[i] == 0) {
        sb.append(0);
        if (i == bits.length - 1 && !sb.toString().equals("0")) return false;
        sb.setLength(0);
      } else sb.append(1);
      if (sb.length() == 2) sb.setLength(0);
    }
    return true;
  }
}

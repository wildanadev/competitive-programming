public class BaseballGame {
  public int calPoints(String[] operations) {
    int[] records = new int[operations.length];
    int ans = 0;
    int size = 0;
    for (String i : operations) {
      if (i.equals("+")) {
        records[size] = records[size - 1] + records[size - 2];
        size++;
      } else if (i.equals("D")) {
        records[size] = records[size - 1] * 2;
        size++;
      } else if (i.equals("C")) size--;
      else records[size++] = Integer.parseInt(i);
    }
    for (int i = 0; i < size; i++) ans += records[i];
    return ans;
  }
}

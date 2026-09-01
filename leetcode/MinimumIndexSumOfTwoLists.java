import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinimumIndexSumOfTwoLists {
  public String[] findRestaurant(String[] list1, String[] list2) {
    HashMap<String, int[]> mapList1 = new HashMap<String, int[]>();
    ArrayList<String> commonStrings = new ArrayList<String>();
    int leastIndexSum = Integer.MAX_VALUE;
    for (int i = 0; i < list1.length; i++) mapList1.put(list1[i], new int[] {0, i});
    for (int i = 0; i < list2.length; i++)
      if (mapList1.containsKey(list2[i])) {
        mapList1.put(list2[i], new int[] {1, mapList1.get(list2[i])[1] + i});
        leastIndexSum = Math.min(leastIndexSum, mapList1.get(list2[i])[1]);
      }
    for (Map.Entry<String, int[]> entry : mapList1.entrySet()) {
      int[] i = entry.getValue();
      int j = i[0];
      int k = i[1];
      if (j == 0) continue;
      if (k == leastIndexSum) commonStrings.add(entry.getKey());
    }
    String[] ans = new String[commonStrings.size()];
    for (int i = 0; i < commonStrings.size(); i++) {
      ans[i] = commonStrings.get(i);
    }
    return ans;
  }
}

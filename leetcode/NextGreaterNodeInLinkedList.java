import java.util.ArrayDeque;
import java.util.ArrayList;

public class NextGreaterNodeInLinkedList {
  public class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  public int[] nextLargerNodes(ListNode head) {
    ArrayList<Integer> al = new ArrayList<>();
    for (ListNode node = head; node != null; node = node.next) al.add(node.val);
    ArrayDeque<Integer> ad = new ArrayDeque<>();
    int[] ans = new int[al.size()];
    for (int i = 0; i < al.size(); i++) {
      while (!ad.isEmpty() && al.get(ad.peek()) < al.get(i)) ans[ad.pop()] = al.get(i);
      ad.push(i);
    }
    return ans;
  }
}

public class PalindromeLinkedList {

  class ListNode {
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

  public boolean isPalindrome(ListNode head) {
    ListNode s = head, f = head, prev = null, cur = null, l = null, r = null;
    while (f != null && f.next != null) {
      s = s.next;
      f = f.next.next;
    }

    cur = s;
    while (cur != null) {
      ListNode next = cur.next;
      cur.next = prev;
      prev = cur;
      cur = next;
    }

    l = head;
    r = prev;
    while (r != null) {
      if (l.val != r.val) return false;
      l = l.next;
      r = r.next;
    }

    return true;
  }
}

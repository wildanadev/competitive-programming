import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class DesignEventManager {
  Map<Integer, Integer> events;
  TreeSet<int[]> ts;

  public EventManager(int[][] events) {
    this.events = new HashMap<>();
    ts = new TreeSet<>((a, b) -> a[1] != b[1] ? b[1] - a[1] : a[0] - b[0]);
    for (int[] event : events) {
      int eventId = event[0];
      int priority = event[1];
      this.events.put(eventId, priority);
      ts.add(new int[] {eventId, priority});
    }
  }

  public void updatePriority(int eventId, int newPriority) {
    int oldPriority = events.get(eventId);
    events.put(eventId, newPriority);
    ts.remove(new int[] {eventId, oldPriority});
    ts.add(new int[] {eventId, newPriority});
  }

  public int pollHighest() {
    if (events.isEmpty()) return -1;
    int[] first = ts.getFirst();
    ts.remove(first);
    events.remove(first[0]);
    return first[0];
  }
}

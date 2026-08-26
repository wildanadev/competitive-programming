import java.util.Arrays;

public class DesignHashMap {
  int[] map;

  public DesignHashMap() {
    map = new int[(int) 1e6 + 1];
    Arrays.fill(map, -1);
  }

  public void put(int key, int value) {
    map[key] = value;
  }

  public int get(int key) {
    return map[key];
  }

  public void remove(int key) {
    map[key] = -1;
  }
}

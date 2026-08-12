class MyHashSet {
  private String[] arr;

  public MyHashSet() {
    arr = new String[(int) 1e6 + 1];
  }

  public void add(int key) {
    arr[key] = String.valueOf(key);
  }

  public void remove(int key) {
    arr[key] = null;
  }

  public boolean contains(int key) {
    if (arr[key] == null) return false;
    return true;
  }
}

/**
 * Your MyHashSet object will be instantiated and called as such: MyHashSet obj = new MyHashSet();
 * obj.add(key); obj.remove(key); boolean param_3 = obj.contains(key);
 */

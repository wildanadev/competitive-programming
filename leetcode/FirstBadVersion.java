public class FirstBadVersion extends VersionControl {
  public int firstBadVersion(int n) {
    int l = 1, r = n;
    while (l <= r) {
      int m = l + (r - l) / 2;
      if (isBadVersion(m)) {
        r = m - 1;
      } else l = m + 1;
    }
    return l;
  }
}

// example extended api
class VersionControl {
  boolean[] version;

  public VersionControl(int n) {
    version = new boolean[n + 1];
  }

  public VersionControl() {}

  public boolean isBadVersion(int n) {
    return version[n];
  }
}

public class StudentAttedanceRecordI {
  public boolean checkRecord(String s) {
    int absentCount = 0;
    int late = 0;
    for (char c : s.toCharArray()) {
      if (c == 'A') absentCount++;
      if (c == 'L') late++;
      if (c != 'L') late = 0;
      if (late > 2 || absentCount > 1) return false;
    }
    return true;
  }
}

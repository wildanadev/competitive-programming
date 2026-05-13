public class NumberOfStudentsUnableToEatLaunch {
  public int countStudents(int[] students, int[] sandwiches) {
    int circle = 0, square = 0;
    for (int student : students) {
      if (student == 0) circle++;
      else square++;
    }
    for (int sandwich : sandwiches) {
      if (sandwich == 0 && circle == 0) return square;
      if (sandwich == 1 && square == 0) return circle;
      if (sandwich == 0) circle--;
      else square--;
    }
    return 0;
  }
}

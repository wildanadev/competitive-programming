public class RobotReturnToOrigin {
  public boolean judgeCircle(String moves) {
    int x = 0, y = 0;
    for (char i : moves.toCharArray()) {
      if (i == 'U') y++;
      if (i == 'D') y--;
      if (i == 'R') x++;
      if (i == 'L') x--;
    }
    return x == 0 && y == 0;
  }
}

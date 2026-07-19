public class EvenNumberOfKnightMoves {
  public boolean canReach(int[] start, int[] target) {
    int isEvenOrOdd1 = (start[0] + start[1]) & 1;
    int isEvenOrOdd2 = (target[0] + target[1]) & 1;
    return isEvenOrOdd1 == isEvenOrOdd2;
  }
}

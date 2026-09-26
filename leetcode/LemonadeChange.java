public class LemonadeChange {
  public boolean lemonadeChange(int[] bills) {
    int fiveCnt = 0;
    int tenCnt = 0;
    for (int i : bills) {
      if (i == 5) fiveCnt++;
      else if (i == 10) {
        if (fiveCnt == 0) return false;
        fiveCnt--;
        tenCnt++;
      } else {
        if (fiveCnt > 0 && tenCnt > 0) {
          fiveCnt--;
          tenCnt--;
        } else if (fiveCnt > 2) fiveCnt -= 3;
        else return false;
      }
    }
    return true;
  }
}

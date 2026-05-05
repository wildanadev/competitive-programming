import java.util.ArrayDeque;

public class EvaluateReversePolishNotation {
  public int evalRPN(String[] tokens) {
    ArrayDeque<Integer> ad = new ArrayDeque<>();
    int op1 = 0, op2 = 0;
    for (String i : tokens) {
      switch (i) {
        case "+":
          ad.push(ad.pop() + ad.pop());
          break;

        case "-":
          op2 = ad.pop();
          op1 = ad.pop();
          ad.push(op1 - op2);
          break;

        case "*":
          ad.push(ad.pop() * ad.pop());
          break;

        case "/":
          op2 = ad.pop();
          op1 = ad.pop();
          ad.push(op1 / op2);
          break;

        default:
          ad.push(Integer.parseInt(i));
          break;
      }
    }
    return ad.pop();
  }
}

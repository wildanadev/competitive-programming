public class DesignCircularQueue {
  private class MyCircularQueue {
    private int[] queue;
    private int capacity;
    private int front;
    private int rear;
    private int count;

    public MyCircularQueue(int k) {
      capacity = k;
      queue = new int[capacity];
      front = 0;
      rear = -1;
      count = 0;
    }

    public boolean enQueue(int value) {
      if (isFull()) return false;
      rear = (rear + 1) % capacity;
      queue[rear] = value;
      count++;
      return true;
    }

    public boolean deQueue() {
      if (isEmpty()) return false;
      front = (front + 1) % capacity;
      count--;
      return true;
    }

    public int Front() {
      if (isEmpty()) return -1;
      return queue[front];
    }

    public int Rear() {
      if (isEmpty()) return -1;
      return queue[rear];
    }

    public boolean isEmpty() {
      return count == 0;
    }

    public boolean isFull() {
      return count == capacity;
    }
  }
}

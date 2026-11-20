/**
 * PrimeNumberOfSetBitsInBinaryRepresentation
 */
public class PrimeNumberOfSetBitsInBinaryRepresentation {
    private static boolean isPrime(int value) {
        if(value <= 1) return false;
        for(int i = 2; i <= Math.sqrt(value);i++) {
            if(value % i == 0) return false;
        }
        return true;
    }

    public int countPrimeSetBits(int left, int right) {
        int ans = 0;
        while (left <= right) {
            int count = Integer.bitCount(left);
            if(isPrime(count)) ans++;
            left++;
        }
        return ans;
    }
}
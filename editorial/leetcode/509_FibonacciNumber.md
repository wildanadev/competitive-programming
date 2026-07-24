# 509. Fibonacci Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Dynamic Programming, Recursion, Memoization
- **Link**: [Problem](https://leetcode.com/problems/fibonacci-number/)
- **Solution**: [Code](../../leetcode/FibonacciNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Hitung nilai Fibonacci ke-`n` di mana:

- `F(0) = 0`, `F(1) = 1`
- `F(n) = F(n-1) + F(n-2)` untuk `n >= 2`

Contoh:

- `n = 2` → `1` (0+1)
- `n = 3` → `2` (1+1)
- `n = 4` → `3` (1+2)

______________________________________________________________________

## 💡 Intuition

Gunakan **dua variabel rolling** — `a = F(n-2)` dan `b = F(n-1)`. Setiap iterasi hitung `c = a + b`, lalu geser: `a = b`, `b = c`. Setelah loop, `b = F(n)`.

Ini adalah DP bottom-up yang dioptimasi dari O(n) space menjadi O(1) karena Fibonacci hanya bergantung pada **dua nilai sebelumnya**.

______________________________________________________________________

## 🔍 Approach

### Iterative — Rolling Variables (Bottom-Up DP)

1. Base case: `n == 0` → return `0`, `n == 1` → return `1`.
1. Inisialisasi `a = 0` (F(0)), `b = 1` (F(1)).
1. Loop `i` dari `2` sampai `n`:
   - `c = a + b`
   - `a = b`
   - `b = c`
1. Return `b`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------- |
| **Time** | O(n) — satu loop |
| **Space** | O(1) — hanya tiga variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 6`

`a=0, b=1`

| i | c = a+b | a = b | b = c |
| --- | ------- | ----- | ----- |
| 2 | 0+1=1 | 1 | 1 |
| 3 | 1+1=2 | 1 | 2 |
| 4 | 1+2=3 | 2 | 3 |
| 5 | 2+3=5 | 3 | 5 |
| 6 | 3+5=8 | 5 | 8 |

Return `b = 8`

**Output: `8` ✅** (F(6) = 0,1,1,2,3,5,8)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 0` → return `0` langsung
- [ ] `n = 1` → return `1` langsung
- [ ] `n = 2` → satu iterasi, `c = 0+1 = 1`, return `1` ✅

______________________________________________________________________

## 🔧 Empat Cara Implementasi Fibonacci

**1. Rekursi Naif — O(2^n) ❌**

```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n-1) + fib(n-2);  // submasalah dihitung berulang!
}
```

**2. Top-Down DP (Memoization) — O(n) time, O(n) space**

```java
int[] memo = new int[n+1];
Arrays.fill(memo, -1);
int fib(int n) {
    if (n <= 1) return n;
    if (memo[n] != -1) return memo[n];
    return memo[n] = fib(n-1) + fib(n-2);
}
```

**3. Bottom-Up DP Array — O(n) time, O(n) space**

```java
int[] dp = new int[n+1];
dp[0]=0; dp[1]=1;
for (int i=2; i<=n; i++) dp[i] = dp[i-1]+dp[i-2];
return dp[n];
```

**4. Rolling Variables (kode ini) — O(n) time, O(1) space ✅**

```java
int a=0, b=1;
for (int i=2; i<=n; i++) { int c=a+b; a=b; b=c; }
return b;
```

| Approach | Time | Space | Catatan |
| ------------------ | ------ | ---------- | ----------------- |
| Rekursi naif | O(2^n) | O(n) stack | TLE untuk n besar |
| Memoization | O(n) | O(n) | Top-down |
| DP array | O(n) | O(n) | Bottom-up |
| Rolling vars (ini) | O(n) | O(1) | Optimal! |

______________________________________________________________________

## 🔧 Hubungan dengan Climbing Stairs (#70)

Seperti yang sudah kita bahas sebelumnya, Climbing Stairs adalah Fibonacci yang di-shift:

```
Fibonacci: F(0)=0, F(1)=1, F(2)=1, F(3)=2, F(4)=3, F(5)=5
ClimbStairs: f(1)=1, f(2)=2, f(3)=3, f(4)=5, f(5)=8

climbStairs(n) = fib(n+1)
```

Keduanya menggunakan relasi `f(n) = f(n-1) + f(n-2)` — pola rolling variables yang sama persis.

______________________________________________________________________

## 📌 Key Takeaway

Fibonacci adalah soal paling dasar untuk memahami optimasi DP: dari rekursi naif O(2^n) → memoization O(n) space → rolling variables O(1) space. Insight kuncinya: kita hanya butuh **dua nilai sebelumnya** untuk menghitung nilai berikutnya — tidak perlu menyimpan seluruh array. Pola rolling variables (`a`, `b`, `c = a+b`, `a=b`, `b=c`) adalah template yang berguna di banyak soal DP linear. 🎯

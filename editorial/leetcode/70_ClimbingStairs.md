# 70. Climbing Stairs

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Dynamic Programming, Memoization
- **Link**: [Problem](https://leetcode.com/problems/climbing-stairs/)
- **Solution**: [Code](../../leetcode/ClimbingStairs.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n` (jumlah anak tangga), setiap langkah kamu bisa naik **1 atau 2 anak tangga**. Hitung berapa banyak cara berbeda untuk mencapai puncak.

Contoh:

- `n = 2` → `2` (1+1 atau 2)
- `n = 3` → `3` (1+1+1, 1+2, 2+1)

______________________________________________________________________

## 💡 Intuition

Untuk mencapai anak tangga ke-`n`, langkah terakhir kamu pasti dari anak tangga `n-1` (naik 1) atau `n-2` (naik 2). Jadi:

```
ways(n) = ways(n-1) + ways(n-2)
```

Ini adalah **deret Fibonacci**! `ways(1)=1`, `ways(2)=2`, dan seterusnya mengikuti pola Fibonacci yang sama.

```
n:     1  2  3  4  5  6
ways:  1  2  3  5  8  13
fib:   1  1  2  3  5  8  13 (fib biasa)
       ↑ ways(n) = fib(n+1)
```

______________________________________________________________________

## 🔍 Approach

### Reduksi ke Fibonacci + Iterative DP

1. `climbStairs(n)` = `fibo(n+1)` — climbing stairs adalah Fibonacci yang di-shift.
1. `fibo(n)` dihitung iteratif dengan dua variabel (`prev1`, `prev2`) tanpa rekursi:
   - Base case: `n <= 1` → return `n`.
   - Loop dari `2` sampai `n`: `curr = prev1 + prev2`, geser `prev2 = prev1`, `prev1 = curr`.
1. Return `curr`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------- |
| **Time** | O(n) — satu loop linear |
| **Space** | O(1) — hanya tiga variabel, tidak ada array DP |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 4` → panggil `fibo(5)`

`prev1=1, prev2=0`

| i | curr = prev1+prev2 | prev2 = prev1 | prev1 = curr |
| --- | ------------------ | ------------- | ------------ |
| 2 | 1+0=1 | 1 | 1 |
| 3 | 1+1=2 | 1 | 2 |
| 4 | 2+1=3 | 2 | 3 |
| 5 | 3+2=5 | 3 | 5 |

`fibo(5) = 5`

**Output: `5` ✅**

Verifikasi manual `n=4`: 1+1+1+1, 1+1+2, 1+2+1, 2+1+1, 2+2 → 5 cara ✅

______________________________________________________________________

**Input:** `n = 2` → panggil `fibo(3)`

`prev1=1, prev2=0`

| i | curr | prev2 | prev1 |
| --- | ---- | ----- | ----- |
| 2 | 1 | 1 | 1 |
| 3 | 2 | 1 | 2 |

`fibo(3) = 2`

**Output: `2` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → `fibo(2)`: loop `i=2`, `curr=1+0=1` → return `1` (hanya 1 cara: langkah tunggal)
- [ ] `n = 0` (di luar constraint biasanya `n>=1`) → tidak relevan untuk soal ini

______________________________________________________________________

## 🔧 Kenapa `climbStairs(n) = fibo(n+1)`?

Mari bandingkan urutan:

```
fibo:      fibo(0)=0, fibo(1)=1, fibo(2)=1, fibo(3)=2, fibo(4)=3, fibo(5)=5
climbStairs: n=1→1,    n=2→2,    n=3→3,     n=4→5

climbStairs(1) = 1 = fibo(2)
climbStairs(2) = 2 = fibo(3)
climbStairs(3) = 3 = fibo(4)
climbStairs(4) = 5 = fibo(5)

→ climbStairs(n) = fibo(n+1) ✅
```

Ini karena base case climbing stairs (`ways(1)=1, ways(2)=2`) sedikit "bergeser" dari Fibonacci standar (`fib(0)=0, fib(1)=1`).

______________________________________________________________________

## 🔧 Kenapa Pakai Dua Variabel, Bukan Array DP?

```java
// Array DP — O(n) space
int[] dp = new int[n+1];
dp[0]=0; dp[1]=1;
for (int i=2; i<=n; i++) dp[i] = dp[i-1]+dp[i-2];
return dp[n];

// Dua variabel — O(1) space (kode ini)
int prev1=1, prev2=0;
for (int i=2; i<=n; i++) {
    curr = prev1+prev2;
    prev2=prev1; prev1=curr;
}
```

Karena `fibo(i)` hanya membutuhkan **dua nilai sebelumnya**, kita tidak perlu menyimpan seluruh array — cukup dua variabel yang terus diperbarui (**rolling variables**). Ini mengurangi space dari O(n) menjadi O(1).

______________________________________________________________________

## 📌 Key Takeaway

Climbing Stairs adalah pengenalan klasik konsep **Dynamic Programming via reduksi ke masalah yang dikenal** (Fibonacci). Insight `ways(n) = ways(n-1) + ways(n-2)` muncul natural dari memikirkan "langkah terakhir apa yang mungkin". Teknik **rolling variables** (`prev1`, `prev2`) adalah optimasi space standar untuk DP yang hanya bergantung pada beberapa state sebelumnya — pola ini terus berulang di soal DP linear seperti _House Robber_ dan _Min Cost Climbing Stairs_. 🎯

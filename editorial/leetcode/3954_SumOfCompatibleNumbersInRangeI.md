# 3954. Sum of Compatible Numbers in Range I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/sum-of-compatible-numbers-in-range-i/)
- **Solution**: [Code](../../leetcode/SumOfCompatibleNumbersInRangeI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua integer `n` dan `k`. Integer positif `x` disebut **compatible** jika memenuhi dua kondisi:

1. `|n - x| <= k` → `x` berada dalam rentang `[n-k, n+k]`
1. `(n & x) == 0` → tidak ada bit yang sama antara `n` dan `x`

Kembalikan jumlah semua integer compatible.

Contoh:

- `n=2, k=3` → `10` (x=1,4,5 → 1+4+5=10)
- `n=5, k=1` → `0` (tidak ada x yang compatible dalam range [4,6])

______________________________________________________________________

## 💡 Intuition

Karena rentang `[n-k, n+k]` terbatas (maksimal `n+k <= 200` dari constraints), cukup **bruteforce** semua kandidat `x` dari `1` sampai `n+k` dan cek kedua kondisi.

Mengapa mulai dari `1` bukan dari `n-k`? Karena `n-k` bisa negatif atau nol (x harus positif), sedangkan kondisi `|n-x| <= k` sudah menjamin `x` berada di rentang yang tepat.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Linear Scan

1. Loop `x` dari `1` sampai `n+k`.
1. Cek `|n - x| <= k` → `x` dalam rentang.
1. Cek `(n & x) == 0` → tidak ada bit yang sama.
1. Jika keduanya terpenuhi → `ans += x`.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------- |
| **Time** | O(n+k) — loop linear sampai `n+k` |
| **Space** | O(1) — hanya variabel `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n=2, k=3`

`n = 2 = 010 (binary)`, rentang valid = `[n-k, n+k] = [-1, 5]` → kandidat positif: `1..5`

| x | binary | |2-x| \<= 3? | 2 & x == 0? | ans |
| --- | ------ | ------------- | ------------------ | --- |
| 1 | 001 | |2-1|=1 ✅ | 010 & 001 = 000 ✅ | 1 |
| 2 | 010 | |2-2|=0 ✅ | 010 & 010 = 010 ❌ | 1 |
| 3 | 011 | |2-3|=1 ✅ | 010 & 011 = 010 ❌ | 1 |
| 4 | 100 | |2-4|=2 ✅ | 010 & 100 = 000 ✅ | 5 |
| 5 | 101 | |2-5|=3 ✅ | 010 & 101 = 000 ✅ | 10 |

**Output: `10` ✅**

______________________________________________________________________

**Input:** `n=5, k=1`

`n = 5 = 101`, rentang `[4, 6]`

| x | binary | |5-x| \<= 1? | 5 & x == 0? | ans |
| --- | ------ | ------------- | ------------------ | --- |
| 1 | 001 | |5-1|=4 ❌ | — | 0 |
| 2 | 010 | |5-2|=3 ❌ | — | 0 |
| 3 | 011 | |5-3|=2 ❌ | — | 0 |
| 4 | 100 | |5-4|=1 ✅ | 101 & 100 = 100 ❌ | 0 |
| 5 | 101 | |5-5|=0 ✅ | 101 & 101 = 101 ❌ | 0 |
| 6 | 110 | |5-6|=1 ✅ | 101 & 110 = 100 ❌ | 0 |

**Output: `0` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n=1, k=1` → rentang `[0,2]`, positif `x=1,2`; `1&1=1≠0`, `1&2=0` → ans=2
- [ ] `(n & x) == 0` sulit dipenuhi jika `n` punya banyak bit aktif → bisa return 0
- [ ] `n=k=100` → loop sampai 200, constraint aman

______________________________________________________________________

## 🔧 Kenapa `(n & x) == 0`?

Kondisi ini berarti tidak ada bit yang **sama-sama menyala** antara `n` dan `x`.

```
n = 2 = 010
x = 5 = 101

010
101
---
000  ← AND = 0, tidak ada bit yang sama ✅

n = 2 = 010
x = 3 = 011

010
011
---
010  ← AND ≠ 0, bit ke-1 sama-sama menyala ❌
```

Dalam konteks bit, ini berarti `n` dan `x` adalah **komplemen parsial** — bit yang menyala di `n` pasti mati di `x` dan sebaliknya (untuk bit-bit yang relevan).

______________________________________________________________________

## 🔧 Kenapa Loop Sampai `n+k`, Bukan `2*n` atau Lebih?

Kondisi `|n - x| <= k` setara dengan `n-k <= x <= n+k`. Karena kita loop dari `1`, batas atas yang diperlukan adalah `n+k`. Nilai `x > n+k` pasti gagal kondisi pertama → tidak perlu dicek.

```
n=2, k=3 → loop sampai 2+3=5
x=6: |2-6|=4 > 3 → pasti gagal → tidak perlu loop lebih jauh
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah kombinasi **range check** dan **bit check** yang sederhana. Karena constraints kecil (`n,k <= 100`), brute force O(n+k) sudah lebih dari cukup. Kondisi `(n & x) == 0` adalah operasi bitwise yang mengecek apakah dua angka tidak berbagi bit yang menyala — penting dipahami sebagai "tidak ada bit yang sama" bukan "nilainya berbeda". 🎯

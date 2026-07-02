# 231. Power of Two

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Bit Manipulation, Recursion
- **Link**: [Problem](https://leetcode.com/problems/power-of-two/)
- **Solution**: [Code](../../leetcode/PowerOfTwo.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, return `true` jika `n` adalah **pangkat dari 2** (yaitu `n = 2^x` untuk suatu integer `x >= 0`).

Contoh:

- `n = 1` → `true` (2⁰ = 1)
- `n = 16` → `true` (2⁴ = 16)
- `n = 3` → `false`

______________________________________________________________________

## 💡 Intuition

Sama dengan pola di _Power of Three_ dan _Power of Four_ — bagi habis `n` dengan `2` selama bisa dibagi, lalu cek apakah hasilnya `1`.

```
n = 16
16 / 2 = 8
8 / 2 = 4
4 / 2 = 2
2 / 2 = 1
→ n == 1 → true ✅

n = 12 = 2² × 3
12 / 2 = 6
6 / 2 = 3
3 % 2 ≠ 0 → stop
→ n == 3 ≠ 1 → false ✅ (ada faktor 3 tersisa)
```

______________________________________________________________________

## 🔍 Approach

### Repeated Division by 2

1. Jika `n < 1` → return `false`.
1. Bagi `n` dengan `2` selama `n % 2 == 0`.
1. Return `n == 1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------ |
| **Time** | O(log₂ n) — setiap pembagian mengurangi `n` menjadi setengah |
| **Space** | O(1) — hanya variabel `n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 16`

```
n=16 >= 1 → lanjut
16 % 2 == 0 → n = 8
8 % 2 == 0  → n = 4
4 % 2 == 0  → n = 2
2 % 2 == 0  → n = 1
1 % 2 != 0  → stop

n == 1? ✅ → true
```

**Output: `true` ✅**

______________________________________________________________________

**Input:** `n = 3`

```
n=3 >= 1 → lanjut
3 % 2 != 0 → loop tidak jalan

n == 1? ❌ (n=3) → false
```

**Output: `false` ✅**

______________________________________________________________________

**Input:** `n = 1`

```
n=1 >= 1 → lanjut
1 % 2 != 0 → loop tidak jalan

n == 1? ✅ → true
```

**Output: `true` ✅** (2⁰ = 1)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → pangkat 2 valid (2⁰)
- [ ] `n <= 0` → selalu `false`
- [ ] `n` pangkat 2 besar (misal `n=1073741824=2³⁰`) → tetap `true`

______________________________________________________________________

## 🚀 Alternatif: Bit Manipulation O(1)

Pangkat 2 memiliki properti biner yang unik: **hanya 1 bit yang menyala**.

```
1  = 0001
2  = 0010
4  = 0100
8  = 1000
16 = 10000
```

Trik klasik: `n & (n-1) == 0` menghapus bit paling kanan yang menyala. Jika hasilnya 0, berarti `n` hanya punya 1 bit menyala → pangkat 2.

```
n = 16 = 10000
n-1 = 15 = 01111
n & (n-1) = 00000 = 0 → true ✅

n = 12 = 1100
n-1 = 11 = 1011
n & (n-1) = 1000 ≠ 0 → false ✅
```

```java
class Solution {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
```

| Approach | Time | Space | Catatan |
| ------------------------ | -------- | ----- | ------------------------------------- |
| Repeated Division (kode) | O(log n) | O(1) | Konsisten dengan pola Power of X |
| Bit Manipulation | O(1) | O(1) | Lebih efisien, manfaatkan sifat biner |

______________________________________________________________________

## 🔧 Pola "Power of X" — Ringkasan

Ketiga soal Power of menggunakan pola yang sama:

| Soal | Approach | Alternatif O(1) |
| ------------------- | ------------- | -------------------------------------------- |
| 231. Power of Two | bagi 2 → n==1 | `n > 0 && (n & (n-1)) == 0` |
| 326. Power of Three | bagi 3 → n==1 | `n > 0 && 1162261467 % n == 0` |
| 342. Power of Four | bagi 4 → n==1 | `n > 0 && (n&(n-1))==0 && (n&0x55555555)!=0` |

Power of Two punya alternatif bit manipulation yang paling elegan karena sifat biner dari pangkat 2 sangat jelas (hanya 1 bit menyala).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah anggota "keluarga" Power of X — pola repeated division berlaku universal. Namun Power of Two punya solusi bit manipulation yang jauh lebih elegan: `n & (n-1) == 0` memanfaatkan fakta bahwa pangkat 2 dalam biner selalu punya **tepat 1 bit yang menyala**, dan trik `n & (n-1)` menghapus bit tersebut menjadi `0` dalam satu operasi. 🎯

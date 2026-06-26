# 342. Power of Four

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Bit Manipulation, Recursion
- **Link**: [Problem](https://leetcode.com/problems/power-of-four/)
- **Solution**: [Code](../../leetcode/PowerOfFour.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, return `true` jika `n` adalah **pangkat dari 4** (yaitu `n = 4^x` untuk suatu integer `x >= 0`).

Contoh:

- `n = 16` → `true` (4² = 16)
- `n = 5` → `false`
- `n = 1` → `true` (4⁰ = 1)

______________________________________________________________________

## 💡 Intuition

Jika `n` adalah pangkat dari 4, maka kita bisa **terus membagi** `n` dengan `4` selama habis dibagi. Jika hasil akhirnya `1`, berarti `n` murni terdiri dari faktor `4` saja → pangkat dari 4.

```
n = 16
16 / 4 = 4
4 / 4 = 1
→ n == 1 → true ✅

n = 8
8 / 4 = 2
2 % 4 ≠ 0 → stop
→ n == 2 ≠ 1 → false ✅ (8 = 2³, bukan pangkat 4)
```

______________________________________________________________________

## 🔍 Approach

### Repeated Division by 4

1. Jika `n < 1` → bukan pangkat dari 4 (harus positif) → return `false`.
1. Bagi `n` dengan `4` selama habis dibagi (`n % 4 == 0`).
1. Return `n == 1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------- |
| **Time** | O(log₄ n) — setiap pembagian mengurangi `n` menjadi seperempatnya |
| **Space** | O(1) — hanya variabel `n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 16`

```
n=16 >= 1 → lanjut
16 % 4 == 0 → n = 4
4 % 4 == 0  → n = 1
1 % 4 != 0  → stop

n == 1? ✅ → true
```

**Output: `true` ✅**

______________________________________________________________________

**Input:** `n = 5`

```
n=5 >= 1 → lanjut
5 % 4 != 0 → loop tidak jalan

n == 1? ❌ (n=5) → false
```

**Output: `false` ✅**

______________________________________________________________________

**Input:** `n = 8`

```
n=8 >= 1 → lanjut
8 % 4 == 0 → n = 2
2 % 4 != 0 → stop

n == 1? ❌ (n=2) → false
```

**Output: `false` ✅** — 8 = 2³ adalah pangkat 2, bukan pangkat 4

______________________________________________________________________

**Input:** `n = 1`

```
n=1 >= 1 → lanjut
1 % 4 != 0 → loop tidak jalan

n == 1? ✅ → true
```

**Output: `true` ✅** (4⁰ = 1)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → pangkat 4 valid (4⁰)
- [ ] `n <= 0` → selalu `false`
- [ ] `n` pangkat dari 2 tapi bukan pangkat 4 (misal `8, 32`) → terdeteksi `false` karena sisa bukan 1
- [ ] `n` sangat besar pangkat 4 valid (misal `4¹⁵`) → loop tetap cepat karena pembagian `/4` berulang

______________________________________________________________________

## 🔧 Kenapa Tidak Semua Pangkat 2 adalah Pangkat 4?

```
Pangkat 2: 1, 2, 4, 8, 16, 32, 64, ...
Pangkat 4: 1, 4, 16, 64, 256, ...
            ↑  ↑  ↑   ↑
         hanya separuh dari pangkat 2 (pangkat genap dari 2)
```

`4^x = 2^(2x)` — pangkat 4 hanya mencakup pangkat 2 yang **genap** (`2⁰, 2², 2⁴, ...`). Itulah mengapa `8 = 2³` (pangkat ganjil) bukan pangkat 4, sedangkan `16 = 2⁴` (pangkat genap) adalah pangkat 4.

______________________________________________________________________

## 🔧 Kenapa `n < 1`, Bukan `n <= 0`?

```java
if (n < 1) return false;  // n=0 dan n negatif keduanya false
```

`n < 1` mencakup `n <= 0` (karena `n` adalah integer) — keduanya ekuivalen. `0` bukan pangkat dari 4 berapapun (`4^x` selalu `>= 1` untuk `x >= 0`).

______________________________________________________________________

## 🚀 Alternatif: Bit Manipulation

Pendekatan repeated division sudah optimal untuk soal ini, tapi ada solusi bit-level yang lebih cepat secara konstanta:

```java
class Solution {
    public boolean isPowerOfFour(int n) {
        if (n <= 0) return false;
        // harus pangkat 2 DULU (hanya 1 bit nyala)
        if ((n & (n - 1)) != 0) return false;
        // bit yang nyala harus di posisi GENAP (0,2,4,...)
        return (n & 0x55555555) != 0;
    }
}
```

**Penjelasan:**

- `n & (n-1) == 0` → cek apakah `n` adalah pangkat 2 (hanya 1 bit nyala).
- `0x55555555` = `...01010101` (bit nyala di posisi genap: 0,2,4,...). Jika bit nyala `n` berada di posisi ini, berarti pangkatnya genap → pangkat 4.

| Approach | Time | Space |
| ----------------- | --------- | ----- |
| Repeated Division | O(log₄ n) | O(1) |
| Bit Manipulation | O(1) | O(1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mirip dengan _Ugly Number_ — bagi habis dengan faktor yang dicari, cek apakah hasilnya `1`. Insight matematika penting: pangkat 4 adalah **subset** dari pangkat 2 (hanya pangkat genap), sehingga solusi bit manipulation perlu dua pengecekan: pangkat 2 dulu, lalu posisi bit yang genap. 🎯

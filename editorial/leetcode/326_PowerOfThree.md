# 326. Power of Three

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Recursion
- **Link**: [Problem](https://leetcode.com/problems/power-of-three/)
- **Solution**: [Code](../../leetcode/PowerOfThree.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, return `true` jika `n` adalah **pangkat dari 3** (yaitu `n = 3^x` untuk suatu integer `x >= 0`).

Contoh:

- `n = 27` → `true` (3³ = 27)
- `n = 0` → `false`
- `n = 9` → `true` (3² = 9)
- `n = 45` → `false`

______________________________________________________________________

## 💡 Intuition

Sama persis dengan pendekatan di _Power of Four_ dan _Ugly Number_ — bagi habis `n` dengan `3` selama bisa dibagi, lalu cek apakah hasilnya `1`. Jika ya, berarti `n` hanya tersusun dari faktor `3`.

```
n = 27
27 / 3 = 9
9 / 3 = 3
3 / 3 = 1
→ n == 1 → true ✅

n = 45 = 3² × 5
45 / 3 = 15
15 / 3 = 5
5 % 3 ≠ 0 → stop
→ n == 5 ≠ 1 → false ✅ (ada faktor 5 yang tersisa)
```

______________________________________________________________________

## 🔍 Approach

### Repeated Division by 3

1. Jika `n < 1` → bukan pangkat dari 3 → return `false`.
1. Bagi `n` dengan `3` selama `n % 3 == 0`.
1. Return `n == 1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------- |
| **Time** | O(log₃ n) — setiap pembagian mengurangi `n` menjadi sepertiga |
| **Space** | O(1) — hanya variabel `n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 27`

```
n=27 >= 1 → lanjut
27 % 3 == 0 → n = 9
9 % 3 == 0  → n = 3
3 % 3 == 0  → n = 1
1 % 3 != 0  → stop

n == 1? ✅ → true
```

**Output: `true` ✅**

______________________________________________________________________

**Input:** `n = 45`

```
n=45 >= 1 → lanjut
45 % 3 == 0 → n = 15
15 % 3 == 0 → n = 5
5 % 3 != 0  → stop

n == 1? ❌ (n=5) → false
```

**Output: `false` ✅** (45 = 3² × 5, ada faktor 5)

______________________________________________________________________

**Input:** `n = 1`

```
n=1 >= 1 → lanjut
1 % 3 != 0 → loop tidak jalan

n == 1? ✅ → true
```

**Output: `true` ✅** (3⁰ = 1)

______________________________________________________________________

**Input:** `n = 0`

```
n=0 < 1 → return false langsung
```

**Output: `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → pangkat 3 valid (3⁰ = 1)
- [ ] `n <= 0` → selalu `false`
- [ ] `n` pangkat dari 9 (misal `9,81`) → juga pangkat dari 3, terdeteksi `true`
- [ ] `n` prima besar selain 3 → tidak terbagi sama sekali → return `false`

______________________________________________________________________

## 🔧 Pola yang Sama di Soal Lain

Pendekatan "repeated division + check n==1" ini muncul berulang di beberapa soal:

| Soal | Faktor | Return true jika |
| ------------------- | ------- | ------------------------ |
| 263. Ugly Number | 2, 3, 5 | setelah bagi semua, n==1 |
| 326. Power of Three | 3 | setelah bagi 3, n==1 |
| 342. Power of Four | 4 | setelah bagi 4, n==1 |

Pola yang sama, hanya faktor pembaginya yang berbeda.

______________________________________________________________________

## 🚀 Alternatif: Divisibility by Largest Power of 3

Karena `int` di Java maksimum `2³¹-1 ≈ 2.1×10⁹`, nilai pangkat 3 terbesar yang muat dalam `int` adalah `3¹⁹ = 1162261467`.

```java
// O(1) tanpa loop!
class Solution {
    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }
}
```

Jika `n` adalah pangkat 3, maka `n` pasti membagi habis `3¹⁹` (karena `3¹⁹` adalah kelipatan dari semua pangkat 3 yang lebih kecil). Jika `n` bukan pangkat 3, ia memiliki faktor prima lain yang tidak membagi `3¹⁹`.

| Approach | Time | Space | Catatan |
| ------------------------ | --------- | ----- | ------------------------------------ |
| Repeated Division (kode) | O(log₃ n) | O(1) | Intuitif, general |
| Max Power Divisibility | O(1) | O(1) | Cerdas, tapi butuh nilai magic `3¹⁹` |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi langsung dari pola **"repeated division + check n==1"** — jika setelah membagi habis dengan faktor yang diizinkan hasilnya `1`, berarti tidak ada faktor prima lain. Alternatif O(1) dengan `3¹⁹ % n == 0` bergantung pada fakta bahwa pangkat tertinggi dari 3 dalam rentang `int` adalah divisible oleh semua pangkat 3 yang lebih kecil. 🎯

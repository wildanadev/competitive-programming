# 69. Sqrt(x)

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/sqrtx/)
- **Solution**: [Code](../../leetcode/MySqrt.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer non-negatif `x`, kembalikan **akar kuadrat dari `x`** yang dibulatkan ke bawah (floor). Tidak boleh menggunakan fungsi built-in seperti `Math.sqrt`.

Contoh:

- `x = 4` → `2`
- `x = 8` → `2` (√8 ≈ 2.828, floor = 2)

______________________________________________________________________

## 💡 Intuition

Cari `m` terbesar sehingga `m² <= x` menggunakan **Binary Search** pada rentang `[0, x]`. Karena fungsi `m²` monoton naik terhadap `m`, kita bisa mempersempit rentang pencarian secara efisien.

```
x = 8
m=0: 0
m=1: 1
m=2: 4   ← m² <= 8
m=3: 9   ← m² > 8, terlalu besar

Jawaban: 2 (m terbesar dengan m² <= 8)
```

______________________________________________________________________

## 🔍 Approach

### Binary Search pada Rentang [0, x]

1. Jika `x == 0` atau `x == 1` → return `x` langsung (kasus trivial).
1. `l = 0`, `r = x`.
1. Selama `l <= r`:
   - `m = l + (r-l)/2`
   - Jika `m*m == x` → return `m` (akar eksak ditemukan).
   - Jika `m*m > x` → `m` terlalu besar → `r = m-1`.
   - Jika `m*m < x` → `m` mungkin terlalu kecil → `l = m+1`.
1. Return `r` — saat loop berakhir, `r` adalah jawaban (floor sqrt).

> Gunakan `(long) m * m` untuk mencegah overflow saat `m` besar.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------ |
| **Time** | O(log x) — binary search |
| **Space** | O(1) — hanya pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `x = 8`

`l=0, r=8`

| l | r | m | m\*m | Perbandingan | Aksi |
| --- | --- | --- | ---- | ------------ | ---- |
| 0 | 8 | 4 | 16 | 16 > 8 | r=3 |
| 0 | 3 | 1 | 1 | 1 < 8 | l=2 |
| 2 | 3 | 2 | 4 | 4 < 8 | l=3 |
| 3 | 3 | 3 | 9 | 9 > 8 | r=2 |

`l=3 > r=2` → loop berhenti → return `r=2`

**Output: `2` ✅** (√8 ≈ 2.83, floor = 2)

______________________________________________________________________

**Input:** `x = 4`

`l=0, r=4`

| l | r | m | m\*m | Perbandingan | Aksi |
| --- | --- | --- | ---- | ------------ | -------- |
| 0 | 4 | 2 | 4 | 4 == 4 ✅ | return 2 |

**Output: `2` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `x = 0` → return `0` langsung
- [ ] `x = 1` → return `1` langsung
- [ ] `x` perfect square → ditemukan via kondisi `==`
- [ ] `x` bukan perfect square → return via `r` setelah loop (floor value)

______________________________________________________________________

## 🔧 Kenapa Return `r`, Bukan `l`, Setelah Loop?

Saat loop berakhir (`l > r`):

- `r` adalah nilai **terbesar** yang sudah dicoba dan `r*r <= x` (kandidat valid terakhir).
- `l` sudah melebihi (kandidat yang gagal, `l*l > x`).

```
Invariant yang dijaga:
- Setelah setiap iterasi, r selalu <= jawaban yang benar
- l selalu > jawaban yang benar (jika belum ketemu exact)

Saat l > r:
  r = jawaban floor sqrt yang benar
```

______________________________________________________________________

## 🔧 Kenapa `(long) m * m`, Bukan `m * m`?

```java
if ((long) m * m == x)  // ✅ aman
if (m * m == x)          // ❌ bisa overflow!
```

`x` bisa sampai `2³¹-1 ≈ 2.1 × 10⁹`. Jika `m` mendekati `√x ≈ 46340`, maka `m*m` bisa mendekati `x` — tapi proses binary search bisa mencoba `m` yang jauh lebih besar di awal (misalnya `m = x/2` saat `x` besar), dan `m*m` di situ bisa **overflow** `int` jika dihitung sebagai `int * int`.

Casting ke `long` sebelum perkalian mencegah overflow.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi **Binary Search pada fungsi monoton** — `m²` naik secara monoton terhadap `m`, sehingga kita bisa mempersempit rentang pencarian. Detail krusial: return `r` (bukan `l`) setelah loop karena `r` selalu menyimpan kandidat valid terakhir, dan casting ke `long` untuk operasi kuadrat mencegah overflow integer. Pola "binary search untuk floor/ceiling value" ini berguna untuk banyak soal matematika seperti _Valid Perfect Square_ dan _Find K-th Smallest Pair Distance_. 🎯

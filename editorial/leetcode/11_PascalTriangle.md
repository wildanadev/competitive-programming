# 118. Pascal's Triangle

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Dynamic Programming, Math
- **Link**: [Problem](https://leetcode.com/problems/pascals-triangle/)
- **Solution**: [Code](../../leetcode/PascalTriangle.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `numRows`, kembalikan `numRows` baris pertama dari **Pascal's Triangle**.

```
numRows = 5:
[1]
[1,1]
[1,2,1]
[1,3,3,1]
[1,4,6,4,1]
```

Setiap elemen di baris `i` dan kolom `j` = `C(i, j)` (Binomial Coefficient).

______________________________________________________________________

## 💡 Intuition

Setiap elemen Pascal's Triangle adalah **kombinasi C(i, j)** dimana `i` adalah nomor baris (0-based) dan `j` adalah posisi kolom. Gunakan rumus rekursif binomial coefficient untuk hitung setiap elemen.

```
C(n, r) = C(n, r-1) * (n - r + 1) / r
C(n, 0) = 1  ← base case
```

______________________________________________________________________

## 🔍 Approach

### Helper `rC(n, r)` — Rekursif Binomial Coefficient

```
rC(n, 0) = 1
rC(n, r) = rC(n, r-1) * (n - r + 1) / r
```

### Generate Pascal's Triangle

1. Loop baris `i` dari `0` sampai `numRows-1`
1. Untuk setiap baris, loop kolom `j` dari `0` sampai `i`
1. Tambahkan `rC(i, j)` ke row
1. Tambahkan row ke `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------- |
| **Time** | O(n³) — n baris × n kolom × rekursif depth n |
| **Space** | O(n) — rekursif call stack |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `numRows = 4`

**Simulasi `rC()`:**

```
rC(3, 0) = 1
rC(3, 1) = rC(3,0) * (3-1+1)/1 = 1 * 3/1 = 3
rC(3, 2) = rC(3,1) * (3-2+1)/2 = 3 * 2/2 = 3
rC(3, 3) = rC(3,2) * (3-3+1)/3 = 3 * 1/3 = 1
```

**Generate tiap baris:**

| i | j | rC(i,j) | row |
| --- | --- | --------- | --------- |
| 0 | 0 | rC(0,0)=1 | [1] |
| 1 | 0 | rC(1,0)=1 | [1] |
| 1 | 1 | rC(1,1)=1 | [1,1] |
| 2 | 0 | rC(2,0)=1 | [1] |
| 2 | 1 | rC(2,1)=2 | [1,2] |
| 2 | 2 | rC(2,2)=1 | [1,2,1] |
| 3 | 0 | rC(3,0)=1 | [1] |
| 3 | 1 | rC(3,1)=3 | [1,3] |
| 3 | 2 | rC(3,2)=3 | [1,3,3] |
| 3 | 3 | rC(3,3)=1 | [1,3,3,1] |

**Output:**

```
[
  [1],
  [1,1],
  [1,2,1],
  [1,3,3,1]
]  ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `numRows = 1` → `[[1]]`
- [ ] `numRows = 2` → `[[1],[1,1]]`

______________________________________________________________________

## 📌 Key Takeaway

Setiap elemen Pascal's Triangle adalah **C(i, j)** — Binomial Coefficient. Rumus rekursif `C(n,r) = C(n,r-1) * (n-r+1) / r` lebih efisien dari rumus faktorial karena menghitung secara incremental tanpa mengulang dari awal. Perkalian dilakukan **sebelum** pembagian untuk menghindari integer division yang salah. 🎯

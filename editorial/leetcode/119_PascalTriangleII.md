# 119. Pascal's Triangle II

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Dynamic Programming, Math
- **Link**: [Problem](https://leetcode.com/problems/pascals-triangle-ii/)
- **Solution**: [Code](../../leetcode/PascalTriangleII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `rowIndex`, kembalikan baris ke-`rowIndex` (0-based) dari Pascal's Triangle.

Contoh:

- `rowIndex = 3` → `[1,3,3,1]`
- `rowIndex = 0` → `[1]`
- `rowIndex = 1` → `[1,1]`

______________________________________________________________________

## 💡 Intuition

Berbeda dengan Pascal's Triangle (#118) yang generate semua baris, soal ini hanya butuh **satu baris saja**. Setiap elemen di baris `rowIndex` dan kolom `j` = `C(rowIndex, j)`. Gunakan rumus rekursif binomial coefficient untuk hitung langsung tanpa generate baris sebelumnya.

```
C(n, 0) = 1
C(n, j) = C(n, j-1) * (n - j + 1) / j
```

______________________________________________________________________

## 🔍 Approach

### Helper `rC(n, r)` — Rekursif Binomial Coefficient

```
rC(n, 0) = 1  ← base case
rC(n, r) = rC(n, r-1) * (n - r + 1) / r
```

### Generate Baris

1. Loop `i` dari `0` sampai `rowIndex`
1. Tambahkan `rC(rowIndex, i)` ke `ans`
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------- |
| **Time** | O(n²) — loop n kolom × rekursif depth n |
| **Space** | O(n) — rekursif call stack + output list |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `rowIndex = 3`

**Simulasi `rC(3, j)`:**

| j | Rekursif | Hasil |
| --- | --------------------------------- | ----- |
| 0 | rC(3,0) = 1 | **1** |
| 1 | rC(3,0) _ (3-1+1)/1 = 1 _ 3 = 3 | **3** |
| 2 | rC(3,1) _ (3-2+1)/2 = 3 _ 2/2 = 3 | **3** |
| 3 | rC(3,2) _ (3-3+1)/3 = 3 _ 1/3 = 1 | **1** |

**Output: `[1, 3, 3, 1]` ✅**

______________________________________________________________________

**Input:** `rowIndex = 4`

| j | Hasil rC(4,j) |
| --- | ------------- |
| 0 | 1 |
| 1 | 4 |
| 2 | 6 |
| 3 | 4 |
| 4 | 1 |

**Output: `[1, 4, 6, 4, 1]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `rowIndex = 0` → `[1]`
- [ ] `rowIndex = 1` → `[1,1]`

______________________________________________________________________

## 📌 Key Takeaway

Bedanya dengan Pascal's Triangle (#118) — tidak perlu generate semua baris, cukup langsung hitung baris yang diminta pakai `C(rowIndex, j)`. Ini lebih efisien dari sisi memori karena tidak menyimpan semua baris sebelumnya. Simetri Pascal's Triangle juga bisa dimanfaatkan lebih lanjut: `C(n,j) == C(n, n-j)` sehingga sebenarnya hanya perlu hitung setengah baris. 🎯

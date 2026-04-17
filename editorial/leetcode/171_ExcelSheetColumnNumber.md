# 171. Excel Sheet Column Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String
- **Link**: [Problem](https://leetcode.com/problems/excel-sheet-column-number/)
- **Solution**: [Code](../../leetcode/ExcelSheetColumnNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `columnTitle` yang merepresentasikan judul kolom pada spreadsheet Excel, kembalikan nomor kolom yang bersesuaian.

Contoh:

- `"A"` → `1`
- `"AB"` → `28`
- `"ZY"` → `701`

______________________________________________________________________

## 💡 Intuition

Sistem penomoran kolom Excel adalah **Base-26**, mirip seperti sistem bilangan desimal (Base-10), tetapi menggunakan huruf `A–Z` sebagai digit dengan nilai `1–26` (bukan `0–25`).

Analoginya:

- Desimal: `"123"` = `1×10² + 2×10¹ + 3×10⁰`
- Excel: `"ABC"` = `1×26² + 2×26¹ + 3×26⁰`

Iterasi dari kanan ke kiri sambil melacak pangkat (`c`) memungkinkan kita menghitung kontribusi setiap huruf secara langsung.

______________________________________________________________________

## 🔍 Approach

### Konversi Base-26 (Right to Left)

1. Inisialisasi `ans = 0` dan `c = 0` sebagai eksponen (pangkat 26).
1. Loop dari indeks terakhir ke indeks pertama:
   - Hitung nilai digit = `(charAt(i) - 'A' + 1)` → `A=1, B=2, ..., Z=26`
   - Tambahkan `digit × 26^c` ke `ans`
   - Increment `c`
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — n = panjang string `columnTitle` |
| **Space** | O(1) — hanya menyimpan variabel integer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `"AB"`

| i | char | nilai digit | c | kontribusi | ans |
| --- | ---- | ----------- | --- | ------------ | --- |
| 1 | `B` | 2 | 0 | 2 × 26⁰ = 2 | 2 |
| 0 | `A` | 1 | 1 | 1 × 26¹ = 26 | 28 |

**Output: `28` ✅**

______________________________________________________________________

**Input:** `"ZY"`

| i | char | nilai digit | c | kontribusi | ans |
| --- | ---- | ----------- | --- | -------------- | --- |
| 1 | `Y` | 25 | 0 | 25 × 26⁰ = 25 | 25 |
| 0 | `Z` | 26 | 1 | 26 × 26¹ = 676 | 701 |

**Output: `701` ✅**

______________________________________________________________________

**Input:** `"AAA"`

| i | char | nilai digit | c | kontribusi | ans |
| --- | ---- | ----------- | --- | ------------- | --- |
| 2 | `A` | 1 | 0 | 1 × 26⁰ = 1 | 1 |
| 1 | `A` | 1 | 1 | 1 × 26¹ = 26 | 27 |
| 0 | `A` | 1 | 2 | 1 × 26² = 676 | 703 |

**Output: `703` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `"A"` → `1` (single character, minimum input)
- [ ] `"Z"` → `26` (nilai digit maksimum)
- [ ] `"AA"` → `27` (carry ke posisi berikutnya)
- [ ] String panjang seperti `"FXSHRXW"` → tidak overflow karena Java `int` cukup untuk constraints soal

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah konversi **Base-26 ke Base-10**, dengan perbedaan kecil: digit dimulai dari `1` bukan `0` (tidak ada nol dalam sistem Excel). Pola Horner's Method (`ans = ans * base + digit`) adalah cara paling bersih untuk konversi basis apapun dari kiri ke kanan, dan menghindari kebutuhan menghitung pangkat secara eksplisit. 🎯

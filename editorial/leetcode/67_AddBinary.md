# 67. Add Binary

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/add-binary/)
- **Solution**: [Code](../../leetcode/AddBinary.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string biner `a` dan `b`, kembalikan hasil penjumlahan keduanya dalam bentuk string biner.

Contoh:

- `a = "11", b = "1"` → `"100"`
- `a = "1010", b = "1011"` → `"10101"`

______________________________________________________________________

## 💡 Intuition

Simulasi penjumlahan biner **dari kanan ke kiri** seperti penjumlahan manual di atas kertas — tambahkan digit per digit sambil track **carry** (`rest`). Hasilnya dibalik di akhir karena dibangun dari digit paling kanan.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `indxa = a.length()-1`, `indxb = b.length()-1`, `rest = 0`
1. Loop selama `rest > 0` atau masih ada digit:
   - Ambil `ta = a[indxa]` (atau 0 kalau sudah habis)
   - Ambil `tb = b[indxb]` (atau 0 kalau sudah habis)
   - Hitung `s = ta + tb + rest`
   - Switch s:
     - `0` → append `'0'`, rest=0
     - `1` → append `'1'`, rest=0
     - `2` → append `'0'`, rest=1
     - `3` → append `'1'`, rest=1
   - `indxa--`, `indxb--`
1. Reverse hasil → return

______________________________________________________________________

## Tabel Penjumlahan Biner

| ta | tb | rest | s | digit | carry |
| --- | --- | ---- | --- | ----- | ----- |
| 0 | 0 | 0 | 0 | 0 | 0 |
| 0 | 1 | 0 | 1 | 1 | 0 |
| 1 | 0 | 0 | 1 | 1 | 0 |
| 1 | 1 | 0 | 2 | 0 | 1 |
| 1 | 1 | 1 | 3 | 1 | 1 |

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------- |
| **Time** | O(max(n,m)) — loop sepanjang string terpanjang |
| **Space** | O(max(n,m)) — string hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `a = "11", b = "1"`

**Init:** `indxa=1, indxb=0, rest=0`

| Step | indxa | indxb | ta | tb | s | digit | rest | temp |
| ---- | ----- | ----- | --- | --- | --- | ----- | ---- | ----- |
| 1 | 1 | 0 | 1 | 1 | 2 | '0' | 1 | "0" |
| 2 | 0 | -1 | 1 | 0 | 2 | '0' | 1 | "00" |
| 3 | -1 | -2 | 0 | 0 | 1 | '1' | 0 | "001" |

**Reverse:** `"001"` → `"100"`

**return `"100"` ✅**

______________________________________________________________________

**Input:** `a = "1010", b = "1011"`

**Init:** `indxa=3, indxb=3, rest=0`

| Step | ta | tb | s | digit | rest | temp |
| ---- | --- | --- | --- | ----- | ---- | ------- |
| 1 | 0 | 1 | 1 | '1' | 0 | "1" |
| 2 | 1 | 1 | 2 | '0' | 1 | "10" |
| 3 | 0 | 0 | 1 | '1' | 0 | "101" |
| 4 | 1 | 1 | 2 | '0' | 1 | "1010" |
| 5 | 0 | 0 | 1 | '1' | 0 | "10101" |

**Reverse:** `"10101"` → `"10101"`

**return `"10101"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String berbeda panjang → yang lebih pendek pakai `0` saat habis
- [ ] Carry tersisa setelah loop → `rest > 0` di kondisi while menangani ini
- [ ] `a = "0", b = "0"` → return `"0"`

______________________________________________________________________

## 📌 Key Takeaway

Kondisi loop `rest > 0 || indxa >= 0 || indxb >= 0` adalah kunci — memastikan carry tersisa tetap diproses meskipun kedua string sudah habis. Ternary operator `(indxa >= 0) ? a.charAt(indxa) - '0' : 0` menangani string yang berbeda panjang secara elegan tanpa perlu cek terpisah. Pola ini sama dengan soal **Plus One (#66)** — proses dari belakang sambil track carry. 🎯

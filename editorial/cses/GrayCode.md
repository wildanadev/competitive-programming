# 3. Gray Code

- **Platform**: CSES
- **Difficulty**: Easy
- **Topics**: Bit Manipulation
- **Link**: [Problem](https://cses.fi/problemset/task/2205)
- **Solution**: [Code](../../cses/GrayCode.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan sebuah integer `n`, generate semua **Gray Code** sepanjang `n` bit.

**Gray Code** adalah urutan biner dimana setiap dua angka berurutan hanya berbeda **1 bit saja**.

______________________________________________________________________

## 💡 Intuition

Cara brute force (generate semua binary lalu filter) itu ribet.

👉 Ada rumus langsung untuk Gray Code:

Untuk setiap angka `i`, Gray Code-nya adalah:

```
g = i ^ (i >> 1)
```

Kenapa ini bekerja?

- `i >> 1` menggeser bit ke kanan
- XOR (`^`) akan menghasilkan perubahan hanya pada bit tertentu
- Hasilnya menjamin hanya **1 bit berbeda antar elemen berurutan**

______________________________________________________________________

## 🔍 Approach

1. Loop dari `i = 0` sampai `(1 << n) - 1`

1. Hitung Gray Code:

   ```
   g = i ^ (i >> 1)
   ```

1. Convert ke binary string

1. Tambahkan leading zero agar panjangnya tetap `n`

1. Print hasil

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------- |
| **Time** | O(2ⁿ × n) — generate & print |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 3`

Iterasi:

| i (decimal) | i (binary) | i>>1 | Gray Code (g) | Output |
| ----------- | ---------- | ---- | ------------- | ------ |
| 0 | 000 | 000 | 000 | 000 |
| 1 | 001 | 000 | 001 | 001 |
| 2 | 010 | 001 | 011 | 011 |
| 3 | 011 | 001 | 010 | 010 |
| 4 | 100 | 010 | 110 | 110 |
| 5 | 101 | 010 | 111 | 111 |
| 6 | 110 | 011 | 101 | 101 |
| 7 | 111 | 011 | 100 | 100 |

👉 Perhatikan: setiap baris hanya beda **1 bit** dengan sebelumnya ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → output: `0`, `1`
- [ ] `n = 0` (tidak ada di soal, tapi konsepnya → hanya `0`)
- [ ] Harus pakai padding (`leading zero`) agar panjang tetap `n`

______________________________________________________________________

## 📌 Key Takeaway

- Gray Code punya **rumus langsung** → `i ^ (i >> 1)`
- Ini jauh lebih efisien dibanding generate permutasi
- XOR sering dipakai di problem bit manipulation untuk:
  - membandingkan bit
  - mendeteksi perubahan
  - optimasi representasi

👉 Kalau ketemu soal "hanya beda 1 bit", langsung ingat **Gray Code pattern** 🚀

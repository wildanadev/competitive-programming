# 4. Increasing Array

- **Platform**: CSES
- **Difficulty**: Easy
- **Topics**: Greedy
- **Link**: [Problem](https://cses.fi/problemset/task/1094)
- **Solution**: [Code](../../cses/IncreasingArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan sebuah array berisi `n` angka.

Dalam satu operasi, kamu boleh **menambah nilai suatu elemen sebesar 1**.

Tentukan jumlah minimum operasi agar array menjadi **non-decreasing**:

```
nums[i] >= nums[i-1]
```

______________________________________________________________________

## 💡 Intuition

Kita ingin memastikan setiap elemen **tidak lebih kecil dari sebelumnya**.

👉 Kalau `nums[i] < nums[i-1]`, maka:

- Kita harus menaikkan `nums[i]` sampai sama dengan `nums[i-1]`

👉 Jadi:

- Selisih yang perlu ditambah = `nums[i-1] - nums[i]`

Kita tidak perlu benar-benar mengubah array, cukup simpan nilai maksimum sebelumnya.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi:

   - `cur = 0` → menyimpan nilai maksimum sejauh ini
   - `ans = 0` → total operasi

1. Loop setiap elemen `x`:

   - Jika `x >= cur`:

     - update `cur = x`

   - Jika `x < cur`:

     - tambahkan `cur - x` ke `ans`

1. Output `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---- |
| **Time** | O(n) |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```
[3, 2, 5, 1, 7]
```

**Langkah:**

| i | x | cur | Operasi | ans |
| --- | --- | --- | ------- | --- |
| 0 | 3 | 3 | - | 0 |
| 1 | 2 | 3 | +1 | 1 |
| 2 | 5 | 5 | - | 1 |
| 3 | 1 | 5 | +4 | 5 |
| 4 | 7 | 7 | - | 5 |

👉 **Output: 5**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array sudah non-decreasing → output `0`
- [ ] Semua elemen sama
- [ ] Nilai besar → gunakan `long` untuk `ans`
- [ ] `n = 1` → selalu `0`

______________________________________________________________________

## 📌 Key Takeaway

Ini adalah problem klasik **greedy**:

- Selalu pastikan kondisi lokal (`nums[i] >= nums[i-1]`)
- Gunakan variabel untuk menyimpan **state sebelumnya (cur)**

👉 Tidak perlu ubah array, cukup hitung selisih yang dibutuhkan

Problem seperti ini sering muncul dengan pola:

- "minimum operasi"
- "buat array memenuhi kondisi tertentu"

Langsung pikirkan **greedy + tracking nilai sebelumnya** 🚀

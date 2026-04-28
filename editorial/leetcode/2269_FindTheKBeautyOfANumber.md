# 2269. Find the K-Beauty of a Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/find-the-k-beauty-of-a-number/)
- **Solution**: [Code](../../leetcode/FindTheKBeautyOfANumber.java)

______________________________________________________________________

## 📄 Problem Summary

**K-beauty** dari integer `num` adalah jumlah substring panjang `k` dari representasi string `num` yang merupakan **divisor** (pembagi) dari `num`.

Syarat substring dihitung:

- Tidak boleh bernilai `0` (pembagian dengan nol tidak valid).
- `num % substring == 0`.

Contoh:

- `num = 240`, `k = 2` → `2`
- `num = 430043`, `k = 2` → `2`

______________________________________________________________________

## 💡 Intuition

Konversi `num` ke string, lalu geser **fixed window** berukuran `k` dari kiri ke kanan menggunakan `String.substring(i, i+k)`. Setiap window menghasilkan substring yang langsung diparse ke integer, lalu dicek apakah membagi `num` habis.

______________________________________________________________________

## 🔍 Approach

### String + Fixed Sliding Window

1. Konversi `num` ke string `s`.
1. Loop `i` dari `0` sampai `s.length() - k` (inklusif):
   - Ekstrak substring: `s.substring(i, i + k)`
   - Parse ke integer: `Integer.parseInt(...)`
   - Jika `sub != 0` dan `num % sub == 0` → `ans++`
1. Return `ans`.

> `s.substring(i, i + k)` mengambil karakter dari indeks `i` hingga `i+k-1` (eksklusif di `i+k`).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------- |
| **Time** | O(n × k) — `substring` dan `parseInt` masing-masing O(k) per posisi |
| **Space** | O(k) — substring sementara ukuran `k` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `num = 240`, `k = 2`

`s = "240"`, `s.length() - k = 1`

| i | s.substring(i, i+2) | sub | sub != 0? | 240 % sub == 0? | ans |
| --- | ------------------- | --- | --------- | --------------- | --- |
| 0 | `"24"` | 24 | ✅ | 240 % 24 = 0 ✅ | 1 |
| 1 | `"40"` | 40 | ✅ | 240 % 40 = 0 ✅ | 2 |

**Output: `2` ✅**

______________________________________________________________________

**Input:** `num = 430043`, `k = 2`

`s = "430043"`, `s.length() - k = 4`

| i | s.substring(i, i+2) | sub | sub != 0? | 430043 % sub == 0? | ans |
| --- | ------------------- | --- | --------- | ------------------ | --- |
| 0 | `"43"` | 43 | ✅ | 430043 % 43 = 0 ✅ | 1 |
| 1 | `"30"` | 30 | ✅ | 430043 % 30 ≠ 0 ❌ | 1 |
| 2 | `"00"` | 0 | ❌ skip | — | 1 |
| 3 | `"04"` | 4 | ✅ | 430043 % 4 ≠ 0 ❌ | 1 |
| 4 | `"43"` | 43 | ✅ | 430043 % 43 = 0 ✅ | 2 |

**Output: `2` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Substring `"0"` atau `"00"` → `sub = 0` → di-skip (mencegah division by zero)
- [ ] `k == panjang digit num` → satu window saja, cek `num % num == 0` (selalu `true`)
- [ ] Leading zero seperti `"04"` → `Integer.parseInt("04") = 4`, valid dan langsung dipakai

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi fixed sliding window pada **string representasi angka** — `s.substring(i, i+k)` langsung mengekstrak window tanpa loop tambahan, jauh lebih ringkas dari membangun substring karakter per karakter. Dua hal penting: cek `sub != 0` sebelum modulo untuk menghindari division by zero, dan `Integer.parseInt` sudah menangani leading zero (`"04"` → `4`) tanpa penanganan khusus. 🎯

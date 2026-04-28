# 3912. Valid Elements in an Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Prefix Maximum, Suffix Maximum
- **Link**: [Problem](https://leetcode.com/problems/valid-elements-in-an-array/)
- **Solution**: [Code](../../leetcode/ValidElementsInAnArray.java)

______________________________________________________________________

## 📄 Problem Summary

Elemen `nums[i]` disebut **valid** jika memenuhi minimal satu kondisi:

- **Lebih besar dari semua elemen di kirinya**, ATAU
- **Lebih besar dari semua elemen di kanannya**

Elemen pertama dan terakhir **selalu valid**. Kembalikan semua elemen valid sesuai urutan kemunculannya.

Contoh:

- `nums = [1,2,4,2,3,2]` → `[1,2,4,3,2]`
- `nums = [5,5,5,5]` → `[5,5]` (hanya elemen pertama dan terakhir)

______________________________________________________________________

## 💡 Intuition

**"Lebih besar dari semua elemen di kiri"** = elemen ini adalah **prefix maximum** baru saat ditemukan dari kiri ke kanan.

**"Lebih besar dari semua elemen di kanan"** = elemen ini adalah **suffix maximum** baru saat ditemukan dari kanan ke kiri.

Gunakan array `boolean[] valid` sebagai flag — tandai indeks yang memenuhi salah satu kondisi. Karena `valid` diindeks dari `0` sampai `n-1`, iterasi akhir otomatis menghasilkan urutan yang benar tanpa perlu sorting tambahan.

______________________________________________________________________

## 🔍 Approach

### Prefix Maximum + Suffix Maximum dengan `boolean[]`

**Pass 1 — Kiri ke Kanan (Prefix Maximum):**

- Lacak `max` = nilai terbesar yang ditemui sejauh ini.
- Jika `nums[i] > max` → elemen baru prefix maximum → `valid[i] = true`, update `max`.

**Pass 2 — Kanan ke Kiri (Suffix Maximum):**

- Reset `max`, lacak dari kanan.
- Jika `nums[i] > max` → elemen baru suffix maximum → `valid[i] = true`, update `max`.
- Jika `valid[i]` sudah `true` dari pass 1 → tidak masalah, tetap `true`.

**Collect:**

- Loop `i` dari `0` sampai `n-1`, jika `valid[i]` → tambahkan `nums[i]` ke `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------- |
| **Time** | O(n) — tiga loop linear terpisah |
| **Space** | O(n) — array `valid` berukuran `n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,4,2,3,2]`

**Pass 1 — Prefix Maximum (kiri ke kanan):**

`max = Integer.MIN_VALUE`

| i | nums[i] | nums[i] > max? | valid[i] | max |
| --- | ------- | -------------- | -------- | --- |
| 0 | 1 | ✅ | true | 1 |
| 1 | 2 | ✅ | true | 2 |
| 2 | 4 | ✅ | true | 4 |
| 3 | 2 | ❌ | false | 4 |
| 4 | 3 | ❌ | false | 4 |
| 5 | 2 | ❌ | false | 4 |

`valid = [T, T, T, F, F, F]`

**Pass 2 — Suffix Maximum (kanan ke kiri):**

`max = Integer.MIN_VALUE`

| i | nums[i] | nums[i] > max? | valid[i] sebelum | valid[i] sesudah | max |
| --- | ------- | -------------- | ---------------- | ---------------- | --- |
| 5 | 2 | ✅ | false | **true** | 2 |
| 4 | 3 | ✅ | false | **true** | 3 |
| 3 | 2 | ❌ | false | false | 3 |
| 2 | 4 | ✅ | true | true (tetap) | 4 |
| 1 | 2 | ❌ | true | true (tetap) | 4 |
| 0 | 1 | ❌ | true | true (tetap) | 4 |

`valid = [T, T, T, F, T, T]`

**Collect:**

| i | valid[i] | nums[i] | ans |
| --- | -------- | ------- | ------------- |
| 0 | ✅ | 1 | `[1]` |
| 1 | ✅ | 2 | `[1,2]` |
| 2 | ✅ | 4 | `[1,2,4]` |
| 3 | ❌ | — | `[1,2,4]` |
| 4 | ✅ | 3 | `[1,2,4,3]` |
| 5 | ✅ | 2 | `[1,2,4,3,2]` |

**Output: `[1,2,4,3,2]` ✅**

______________________________________________________________________

**Input:** `nums = [5,5,5,5]`

**Pass 1:** hanya `nums[0]=5` yang lebih besar dari `MIN_VALUE` → `valid = [T,F,F,F]`

**Pass 2:** hanya `nums[3]=5` yang lebih besar dari `MIN_VALUE` dari kanan → `valid = [T,F,F,T]`

**Collect:** indeks 0 dan 3 → `[5,5]`

**Output: `[5,5]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → `valid[0] = true` dari kedua pass → return `[nums[0]]`
- [ ] Array terurut ascending → semua elemen prefix maximum → `valid` semua `true`
- [ ] Array terurut descending → semua elemen suffix maximum → `valid` semua `true`
- [ ] Semua elemen sama → hanya indeks `0` (prefix) dan `n-1` (suffix) yang `true`

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi **Prefix Maximum + Suffix Maximum** dengan tiga pass linear yang bersih. `boolean[]` sebagai visited/valid flag adalah pilihan optimal karena indeks array sudah sorted secara natural — tidak perlu TreeMap yang O(n log n). Union dari dua set (prefix max dan suffix max) ditangani secara elegan dengan `valid[i] = true` yang bisa di-set dari salah satu atau kedua pass. 🎯

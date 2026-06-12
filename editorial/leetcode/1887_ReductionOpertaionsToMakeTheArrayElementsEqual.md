# 1887. Reduction Operations to Make the Array Elements Equal

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Sorting
- **Link**: [Problem](https://leetcode.com/problems/reduction-operations-to-make-the-array-elements-equal/)
- **Solution**: [Code](../../leetcode/ReductionOperationsToMakeTheArrayElementsEqual.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`. Dalam satu operasi, pilih elemen terbesar yang bukan satu-satunya nilai terbesar, dan kurangi ke nilai terbesar berikutnya. Hitung total operasi untuk membuat semua elemen sama.

Contoh:

- `nums = [5,1,3]` → `3`
  - `[5,1,3]` → `[3,1,3]` (5→3, 1 ops)
  - `[3,1,3]` → `[1,1,3]` (3→1, 1 ops)
  - `[1,1,3]` → `[1,1,1]` (3→1, 1 ops)
  - Total = 3
- `nums = [1,1,1]` → `0`
- `nums = [1,2,4]` → `3`

______________________________________________________________________

## 💡 Intuition

Setelah sort, bayangkan setiap elemen yang berbeda sebagai "level". Untuk setiap elemen, ia harus turun satu level per operasi.

**Key insight**: elemen yang berbeda dari elemen sebelumnya (setelah sort) berarti ada "level baru". Elemen tersebut membutuhkan lebih banyak operasi untuk turun ke minimum.

Setiap kali ada nilai unik baru ditemukan (`up++`), **semua** elemen berikutnya perlu satu operasi lebih untuk melewati level ini.

```
sort: [1, 3, 5, 5]
       ↑  ↑  ↑
      lvl0 lvl1 lvl2

Elemen 3: harus turun 1 level → 1 ops
Elemen 5 pertama: harus turun 2 level → 2 ops
Elemen 5 kedua: harus turun 2 level → 2 ops
Total: 0 + 1 + 2 + 2 = 5
```

______________________________________________________________________

## 🔍 Approach

### Sort + Count Levels

1. Sort `nums` ascending.
1. Inisialisasi `up = 0` (jumlah level unik yang sudah ditemukan) dan `ans = 0`.
1. Loop `i` dari `1` sampai `n-1`:
   - Jika `nums[i] != nums[i-1]` → nilai baru (level baru) → `up++`.
   - `ans += up` (elemen ini perlu `up` operasi untuk turun ke minimum).
1. Return `ans`.

> Elemen pertama (`i=0`) selalu menjadi target minimum → tidak perlu operasi → skip.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------ |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(1) — hanya variabel `up` dan `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [5,1,3]`

Setelah sort: `[1, 3, 5]`

`up = 0, ans = 0`

| i | nums[i] | nums[i-1] | nums[i]!=nums[i-1]? | up | ans += up | ans |
| --- | ------- | --------- | ------------------- | --- | --------- | --- |
| 1 | 3 | 1 | ✅ → up++ | 1 | ans += 1 | 1 |
| 2 | 5 | 3 | ✅ → up++ | 2 | ans += 2 | 3 |

**Output: `3` ✅**

______________________________________________________________________

**Verifikasi:**

- `1`: sudah minimum → 0 ops
- `3`: turun ke `1` = 1 level → 1 ops
- `5`: turun ke `3` lalu ke `1` = 2 level → 2 ops
- Total = 0+1+2 = 3 ✅

______________________________________________________________________

**Input:** `nums = [1,2,4]`

Setelah sort: `[1, 2, 4]`

| i | nums[i] | nums[i-1] | up | ans |
| --- | ------- | --------- | --- | --- |
| 1 | 2 | 1 | 1 | 1 |
| 2 | 4 | 2 | 2 | 3 |

**Output: `3` ✅**

______________________________________________________________________

**Input:** `nums = [5,5,5,5]`

Setelah sort: `[5, 5, 5, 5]`

| i | nums[i]!=nums[i-1]? | up | ans |
| --- | ------------------- | --- | --- |
| 1 | ❌ | 0 | 0 |
| 2 | ❌ | 0 | 0 |
| 3 | ❌ | 0 | 0 |

**Output: `0` ✅** — semua sudah sama

______________________________________________________________________

**Input:** `nums = [4,1,3,2]`

Setelah sort: `[1, 2, 3, 4]`

| i | nums[i] | up | ans += up | ans |
| --- | ------- | --- | --------- | --- |
| 1 | 2 | 1 | 1 | 1 |
| 2 | 3 | 2 | 2 | 3 |
| 3 | 4 | 3 | 3 | 6 |

**Output: `6` ✅**

______________________________________________________________________

**Verifikasi:**

- `2`: 1 operasi (ke 1)
- `3`: 2 operasi (ke 2, ke 1)
- `4`: 3 operasi (ke 3, ke 2, ke 1)
- Total = 1+2+3 = 6 ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama → `up` selalu 0 → `ans = 0`
- [ ] Dua elemen berbeda → satu level → elemen ke-2 butuh 1 ops
- [ ] Array terurut ascending → setiap elemen berbeda → `up` naik tiap langkah

______________________________________________________________________

## 🔧 Mengapa `ans += up`, Bukan `ans += 1`?

Setiap elemen harus turun **melewati semua level** di bawahnya sampai mencapai minimum. `up` mewakili berapa banyak level unik yang sudah ditemukan — itulah jumlah operasi yang dibutuhkan elemen saat ini.

```
sort: [1, 3, 5, 5, 7]
level:  0  1  2  2  3

Elemen 7 (level 3):
- turun ke 5: 1 ops
- turun ke 3: 1 ops
- turun ke 1: 1 ops
Total: 3 ops = up saat i=4
```

Elemen dengan nilai yang sama berbagi `up` yang sama karena mereka berada di level yang sama.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan teknik **"hitung berdasarkan level setelah sort"** — setiap elemen butuh operasi sebanyak jumlah level unik di bawahnya. Variabel `up` yang bertambah saat nilai baru ditemukan dan diakumulasi ke `ans` adalah representasi elegan dari konsep ini. Setelah sort, elemen yang sama di posisi berbeda punya `up` yang sama karena mereka di level yang sama. 🎯

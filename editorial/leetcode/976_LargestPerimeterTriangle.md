# 976. Largest Perimeter Triangle

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Greedy, Sorting
- **Link**: [Problem](https://leetcode.com/problems/largest-perimeter-triangle/)
- **Solution**: [Code](../../leetcode/LargestPerimeterTriangle.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, kembalikan **perimeter terbesar** dari segitiga yang bisa dibentuk dari tiga elemen array. Jika tidak ada segitiga valid, return `0`.

Syarat segitiga valid (triangle inequality): jumlah dua sisi yang lebih pendek harus **lebih besar** dari sisi terpanjang:

```
a + b > c  (dengan a ≤ b ≤ c)
```

Contoh:

- `nums = [2,1,2]` → `5`
- `nums = [1,2,1]` → `0`
- `nums = [3,2,3,4]` → `10`

______________________________________________________________________

## 💡 Intuition

Setelah array diurutkan, untuk setiap triplet `(nums[i-2], nums[i-1], nums[i])` yang berurutan:

- `nums[i]` adalah sisi terbesar → hanya perlu cek `nums[i-2] + nums[i-1] > nums[i]`.
- Sisi terbesar selalu `nums[i]`, jadi kondisi lain (`nums[i-2] + nums[i] > nums[i-1]`) otomatis terpenuhi.

Karena kita ingin **perimeter terbesar**, iterasi dari kanan (elemen terbesar) ke kiri — triplet pertama yang valid langsung memberikan jawaban terbesar.

Mengapa triplet **berurutan** selalu optimal? Jika `(a, b, c)` tidak membentuk segitiga valid (`a + b ≤ c`), maka mengganti `b` dengan nilai yang lebih kecil hanya memperburuk kondisi. Jadi triplet berurutan adalah kandidat terbaik untuk setiap posisi `c`.

______________________________________________________________________

## 🔍 Approach

### Greedy + Sort

1. Sort `nums` ascending.
1. Loop `i` dari `nums.length - 1` turun ke `2`:
   - Jika `nums[i-2] + nums[i-1] > nums[i]` → valid, return `nums[i] + nums[i-1] + nums[i-2]`.
1. Jika tidak ada triplet valid → return `0`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(1) — hanya variabel iterator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,2,3,4]`

Setelah sort: `[2, 3, 3, 4]`

| i | nums[i-2] | nums[i-1] | nums[i] | nums[i-2]+nums[i-1] | > nums[i]? | Aksi |
| --- | --------- | --------- | ------- | ------------------- | ---------- | ------------------- |
| 3 | 3 | 3 | 4 | 6 | 6 > 4 ✅ | return 3+3+4=**10** |

**Output: `10` ✅**

______________________________________________________________________

**Input:** `nums = [2,1,2]`

Setelah sort: `[1, 2, 2]`

| i | nums[i-2] | nums[i-1] | nums[i] | nums[i-2]+nums[i-1] | > nums[i]? | Aksi |
| --- | --------- | --------- | ------- | ------------------- | ---------- | ------------------ |
| 2 | 1 | 2 | 2 | 3 | 3 > 2 ✅ | return 1+2+2=**5** |

**Output: `5` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,1]`

Setelah sort: `[1, 1, 2]`

| i | nums[i-2] | nums[i-1] | nums[i] | nums[i-2]+nums[i-1] | > nums[i]? | Aksi |
| --- | --------- | --------- | ------- | ------------------- | ---------- | ------ |
| 2 | 1 | 1 | 2 | 2 | 2 > 2 ❌ | lanjut |

Loop selesai tanpa hasil.

**Output: `0` ✅**

______________________________________________________________________

**Input:** `nums = [1,1,1,5,5,5]`

Setelah sort: `[1, 1, 1, 5, 5, 5]`

| i | nums[i-2] | nums[i-1] | nums[i] | sum dua kecil | > nums[i]? | Aksi |
| --- | --------- | --------- | ------- | ------------- | ---------- | ------------------- |
| 5 | 5 | 5 | 5 | 10 | 10 > 5 ✅ | return 5+5+5=**15** |

**Output: `15` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama → triplet pertama dari kanan selalu valid
- [ ] Array tersortir descending seperti Fibonacci → tidak ada triplet valid → return `0`
  - Contoh: `[1,1,2,3,5,8]` → setiap triplet `a+b == c` (tidak memenuhi `>`) → return `0`
- [ ] Tepat tiga elemen → satu triplet yang langsung dicek
- [ ] Semua elemen `1` → selalu valid

______________________________________________________________________

## 📌 Key Takeaway

Greedy dengan iterasi dari kanan pada array terurut adalah pola yang sangat efisien untuk soal "cari triplet optimal" — triplet berurutan selalu merupakan kandidat terbaik karena mengganti salah satu elemen dengan yang lebih kecil hanya memperburuk kondisi. Pemahaman bahwa **dua kondisi triangle inequality otomatis terpenuhi setelah sort** menyederhanakan pengecekan menjadi hanya satu kondisi. Pola serupa muncul di soal _Maximum Product of Three Numbers_ dan _3Sum_. 🎯

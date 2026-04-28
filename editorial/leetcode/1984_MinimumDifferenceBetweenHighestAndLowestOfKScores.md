# 1984. Minimum Difference Between Highest and Lowest of K Scores

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sliding Window, Sorting
- **Link**: [Problem](https://leetcode.com/problems/minimum-difference-between-highest-and-lowest-of-k-scores/)
- **Solution**: [Code](../../leetcode/MinimumDifferenceBetweenHighestAndLowestOfKScores.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `k`, pilih tepat `k` elemen dari `nums`. Kembalikan **selisih minimum** antara nilai tertinggi dan terendah dari `k` elemen yang dipilih.

Contoh:

- `nums = [90]`, `k = 1` → `0`
- `nums = [9,4,1,7]`, `k = 2` → `2` (pilih `[7,9]`)

______________________________________________________________________

## 💡 Intuition

Untuk meminimalkan selisih `max - min` dari `k` elemen yang dipilih, elemen-elemen tersebut haruslah yang **paling berdekatan nilainya**. Setelah array diurutkan, elemen paling berdekatan selalu berada di **posisi berurutan** — sehingga kita cukup mencari window berukuran `k` dengan selisih `nums[i+k-1] - nums[i]` terkecil.

Karena ukuran window tetap (`k`), dalam array terurut selisih antar window adalah `nums[ujung_kanan] - nums[ujung_kiri]`.

______________________________________________________________________

## 🔍 Approach

### Sort + Fixed Sliding Window

1. Sort `nums` ascending.
1. Inisialisasi `ans` dengan nilai yang cukup besar (`100001`).
1. Loop `i` dari `0` sampai `nums.length - k` (inklusif):
   - Hitung `nums[i + k - 1] - nums[i]` (selisih ujung kanan dan kiri window).
   - Update `ans = min(ans, selisih)`.
1. Return `ans`.

> Setelah sort, `nums[i]` adalah minimum window dan `nums[i+k-1]` adalah maksimumnya — tidak perlu cek elemen di antaranya.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(1) — hanya variabel tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [9,4,1,7]`, `k = 2`

Setelah sort: `[1, 4, 7, 9]`

`ans = 100001`

| i | nums[i] | nums[i+k-1] = nums[i+1] | selisih | ans |
| --- | ------- | ----------------------- | ------- | --------------------- |
| 0 | 1 | 4 | 3 | min(100001,3) = **3** |
| 1 | 4 | 7 | 3 | min(3,3) = **3** |
| 2 | 7 | 9 | 2 | min(3,2) = **2** |

**Output: `2` ✅**

______________________________________________________________________

**Input:** `nums = [1,3,6,10,15]`, `k = 3`

Setelah sort: `[1, 3, 6, 10, 15]`

| i | nums[i] | nums[i+2] | selisih | ans |
| --- | ------- | --------- | ------- | --- |
| 0 | 1 | 6 | 5 | 5 |
| 1 | 3 | 10 | 7 | 5 |
| 2 | 6 | 15 | 9 | 5 |

**Output: `5` ✅**

______________________________________________________________________

**Input:** `nums = [90]`, `k = 1`

Setelah sort: `[90]`

| i | nums[i] | nums[i+0] | selisih | ans |
| --- | ------- | --------- | ------- | --- |
| 0 | 90 | 90 | 0 | 0 |

**Output: `0` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k == 1` → `nums[i+0] - nums[i] = 0` selalu → return `0`
- [ ] `k == nums.length` → hanya satu window, selisih elemen terbesar dan terkecil seluruh array
- [ ] Semua elemen sama → semua selisih `0` → return `0`
- [ ] Dua elemen, `k = 2` → satu window, return selisih keduanya

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua insight: **sort untuk mendekatkan elemen** dan **fixed sliding window untuk mencari selisih minimum**. Setelah sort, window berukuran `k` yang berurutan selalu merupakan kandidat terbaik karena elemen di luar window yang sudah terurut pasti lebih jauh nilainya. Formula `nums[i+k-1] - nums[i]` langsung memberikan selisih max-min window tanpa perlu menelusuri elemen di antaranya. 🎯

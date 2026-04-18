# 561. Array Partition

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Greedy, Sorting, Counting Sort
- **Link**: [Problem](https://leetcode.com/problems/array-partition/)
- **Solution**: [Code](../../leetcode/ArrayPartition.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dengan panjang `2n`, bentuk `n` pasangan dan jumlahkan nilai minimum dari setiap pasangan. Kembalikan **jumlah maksimum** yang mungkin dari semua `min(a, b)`.

Contoh:

- `nums = [1,4,3,2]` → `4` (pasangan `(1,2)` dan `(3,4)` → `1+3=4`)
- `nums = [6,2,6,5,1,2]` → `9` (pasangan `(1,2)`, `(2,5)`, `(6,6)` → `1+2+6=9`)

______________________________________________________________________

## 💡 Intuition

Untuk memaksimalkan jumlah `min`, kita harus **meminimalkan "nilai yang terbuang"** — nilai yang diambil `min()` dari setiap pasangan selalu membuang nilai yang lebih besar. Agar kerugian ini sekecil mungkin, pasangkan elemen yang **berdekatan setelah diurutkan**.

Mengapa? Jika array terurut `[a1, a2, a3, a4]` dengan `a1 ≤ a2 ≤ a3 ≤ a4`:

- Pasangan berdekatan: `min(a1,a2) + min(a3,a4) = a1 + a3`
- Pasangan jauh: `min(a1,a4) + min(a2,a3) = a1 + a2`

Karena `a3 ≥ a2`, maka `a1 + a3 ≥ a1 + a2` → pasangan berdekatan selalu optimal.

______________________________________________________________________

## 🔍 Approach

### Greedy + Sorting

1. Sort `nums` secara ascending.
1. Iterasi dengan step `2` (ambil elemen di indeks genap):
   - Tambahkan `nums[i]` ke `ans`.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(1) — tidak ada struktur data tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,4,3,2]`

Setelah sort: `[1, 2, 3, 4]`

| i | nums[i] | nums[i+1] | min | ans |
| --- | ------- | --------- | --- | --- |
| 0 | 1 | 2 | 1 | 1 |
| 2 | 3 | 4 | 3 | 4 |

**Output: `4` ✅**

______________________________________________________________________

**Input:** `nums = [6,2,6,5,1,2]`

Setelah sort: `[1, 2, 2, 5, 6, 6]`

| i | nums[i] | nums[i+1] | min | ans |
| --- | ------- | --------- | --- | --- |
| 0 | 1 | 2 | 1 | 1 |
| 2 | 2 | 5 | 2 | 3 |
| 4 | 6 | 6 | 6 | 9 |

**Output: `9` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,3,4,5,6]`

Setelah sort: `[1, 2, 3, 4, 5, 6]`

| i | nums[i] | nums[i+1] | min | ans |
| --- | ------- | --------- | --- | --- |
| 0 | 1 | 2 | 1 | 1 |
| 2 | 3 | 4 | 3 | 4 |
| 4 | 5 | 6 | 5 | 9 |

**Output: `9` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → `nums` panjang 2 → satu pasangan → return `min(nums[0], nums[1])`
- [ ] Semua elemen sama → `[3,3,3,3]` → setiap pasangan `min = 3` → return `6`
- [ ] Elemen negatif → `[-4,-1,-2,-3]` → sort → `[-4,-3,-2,-1]` → `(-4)+(-2) = -6`

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan prinsip greedy **"minimasi kerugian"** — setiap pasangan pasti membuang satu nilai (yang lebih besar), dan cara terbaik meminimalkan total kerugian adalah memastikan yang terbuang selalu sesdekat mungkin nilainya dengan yang disimpan. Sorting lalu ambil indeks genap adalah implementasi paling bersih dari intuisi ini. Pola serupa muncul di soal seperti _Minimum Absolute Difference_ dan _Largest Perimeter Triangle_. 🎯

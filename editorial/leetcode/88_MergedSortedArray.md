# 88. Merge Sorted Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers, Sorting
- **Link**: [Problem](https://leetcode.com/problems/merge-sorted-array/)
- **Solution**: [Code](../../leetcode/MergedSortedArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array terurut `nums1` (ukuran `m+n`) dan `nums2` (ukuran `n`). Merge `nums2` ke dalam `nums1` secara **in-place** sehingga hasilnya tetap terurut.

`nums1` sudah punya slot kosong di akhir sebanyak `n` untuk menampung elemen dari `nums2`.

Contoh:

- `nums1 = [1,2,3,0,0,0], m=3, nums2 = [2,5,6], n=3` → `[1,2,2,3,5,6]`
- `nums1 = [1], m=1, nums2 = [], n=0` → `[1]`

______________________________________________________________________

## 💡 Intuition

Slot kosong di `nums1[m..m+n-1]` sudah disiapkan untuk elemen `nums2`. Tinggal isi slot kosong itu dengan elemen `nums2`, lalu sort seluruh `nums1`.

______________________________________________________________________

## 🔍 Approach

1. Kalau `n == 0` → tidak ada yang perlu di-merge, langsung return
1. Loop dari index `m` sampai akhir `nums1`:
   - Isi `nums1[i]` dengan `nums2[count++]`
1. Sort `nums1` dengan `Arrays.sort()`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------ |
| **Time** | O((m+n) log(m+n)) — karena `Arrays.sort()` |
| **Space** | O(1) — in-place, tidak pakai array baru |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums1 = [1,2,3,0,0,0], m=3, nums2=[2,5,6], n=3`

**Step 1 — Isi slot kosong:**

| i | count | nums1[i] = nums2[count] | nums1 |
| --- | ----- | ----------------------- | ------------- |
| 3 | 0 | nums1[3] = 2 | [1,2,3,2,0,0] |
| 4 | 1 | nums1[4] = 5 | [1,2,3,2,5,0] |
| 5 | 2 | nums1[5] = 6 | [1,2,3,2,5,6] |

**Step 2 — Arrays.sort():**

```
[1,2,3,2,5,6] → [1,2,2,3,5,6] ✅
```

**Output: `[1,2,2,3,5,6]` ✅**

______________________________________________________________________

**Input:** `nums1 = [0], m=0, nums2=[1], n=1`

**Step 1 — Isi slot kosong:**

| i | count | nums1[i] = nums2[count] | nums1 |
| --- | ----- | ----------------------- | ----- |
| 0 | 0 | nums1[0] = 1 | [1] |

**Step 2 — Arrays.sort():**

```
[1] → [1] ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n == 0` → tidak ada yang perlu di-merge, return langsung
- [ ] `m == 0` → `nums1` kosong, tinggal copy semua `nums2`
- [ ] Semua elemen `nums2` lebih besar dari `nums1` → sort tetap handle

______________________________________________________________________

## 📌 Key Takeaway

Pendekatan ini memanfaatkan slot kosong yang sudah disiapkan di `nums1`, lalu andalkan `Arrays.sort()` untuk urutkan ulang. Simpel tapi time complexity-nya O((m+n) log(m+n)) karena sorting.

Solusi optimal sebenarnya bisa O(m+n) dengan **Three Pointers dari belakang** — isi `nums1` dari index terbesar ke terkecil tanpa perlu sort. Tapi pendekatan ini lebih mudah dipahami. 🎯

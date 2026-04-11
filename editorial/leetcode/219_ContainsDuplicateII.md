# 219. Contains Duplicate II

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/contains-duplicate-ii/)
- **Solution**: [Code](../../leetcode/ContainsDuplicateII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dan integer `k`, return `true` jika ada dua index `i` dan `j` dimana `nums[i] == nums[j]` dan `abs(i - j) <= k`.

Contoh:

- `nums = [1,2,3,1], k = 3` → `true`
- `nums = [1,0,1,1], k = 1` → `true`
- `nums = [1,2,3,1,2,3], k = 2` → `false`

______________________________________________________________________

## 💡 Intuition

Gunakan **Sliding Window + HashSet** — jaga window berisi maksimal `k` elemen terakhir. Kalau elemen baru sudah ada di window → duplikat dalam jarak ≤ k ditemukan.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `seen = HashSet`
1. Loop index `i` dari `0` sampai `nums.length`:
   - Kalau `i > k` → hapus elemen yang sudah keluar window: `seen.remove(nums[i-k-1])`
   - Kalau `seen.contains(nums[i])` → return `true`
   - `seen.add(nums[i])`
1. Return `false`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(k) — HashSet max isi k elemen |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3,1], k = 3`

**Init:** `seen = {}`

| i | Hapus? | seen sebelum cek | contains(nums[i])? | seen setelah |
| --- | --------- | ---------------- | ------------------ | ------------ |
| 0 | i≤k, skip | {} | ❌ | {1} |
| 1 | i≤k, skip | {1} | ❌ | {1,2} |
| 2 | i≤k, skip | {1,2} | ❌ | {1,2,3} |
| 3 | i≤k, skip | {1,2,3} | ✅ **return true** | - |

**return `true` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,3,1,2,3], k = 2`

**Init:** `seen = {}`

| i | Hapus | seen sebelum cek | contains? | seen setelah |
| --- | --------------- | ---------------- | --------- | ------------ |
| 0 | - | {} | ❌ | {1} |
| 1 | - | {1} | ❌ | {1,2} |
| 2 | - | {1,2} | ❌ | {1,2,3} |
| 3 | hapus nums[0]=1 | {2,3} | ❌ | {2,3,1} |
| 4 | hapus nums[1]=2 | {3,1} | ❌ | {3,1,2} |
| 5 | hapus nums[2]=3 | {1,2} | ❌ | {1,2,3} |

**return `false` ✅**

______________________________________________________________________

## Kenapa `nums[i-k-1]`?

```
Window valid: index [i-k .. i-1]
Elemen yang harus dihapus: index i-k-1 (sudah di luar window)

Contoh k=3, i=4:
Window valid: index [1,2,3]
Hapus: index 0 = nums[4-3-1] = nums[0] ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k = 0` → tidak mungkin `abs(i-j) <= 0` dengan `i != j` → return `false`
- [ ] `k >= nums.length` → window mencakup seluruh array
- [ ] Semua elemen sama → return `true` kalau `k >= 1`

______________________________________________________________________

## 📌 Key Takeaway

Teknik **Sliding Window dengan HashSet** menjaga window berukuran tepat `k` — elemen yang sudah terlalu jauh dihapus manual dengan `seen.remove(nums[i-k-1])`. Ini adalah upgrade dari Contains Duplicate (#217) yang tidak ada batasan jarak. 🎯

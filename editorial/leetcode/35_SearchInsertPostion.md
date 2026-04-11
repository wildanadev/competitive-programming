# 35. Search Insert Position

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/search-insert-position/)
- **Solution**: [Code](../../leetcode/SearchInsertPosition.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` yang sudah terurut dan integer `target`, kembalikan index `target` jika ditemukan. Jika tidak ada, kembalikan index dimana `target` seharusnya disisipkan agar array tetap terurut.

Contoh:

- `nums = [1,3,5,6], target = 5` → `2`
- `nums = [1,3,5,6], target = 2` → `1`
- `nums = [1,3,5,6], target = 7` → `4`

______________________________________________________________________

## 💡 Intuition

Karena array sudah **terurut**, gunakan **Binary Search** untuk cari target dalam O(log n). Kalau target tidak ditemukan, pointer `l` akan berhenti tepat di posisi dimana target seharusnya disisipkan.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `l = 0`, `r = nums.length - 1`
1. Loop selama `l <= r`:
   - Hitung `m = l + (r - l) / 2`
   - Kalau `nums[m] == target` → return `m`
   - Kalau `target > nums[m]` → `l = m + 1` (cari ke kanan)
   - Kalau `target < nums[m]` → `r = m - 1` (cari ke kiri)
1. Return `l` → posisi insert jika target tidak ditemukan

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------ |
| **Time** | O(log n) — binary search |
| **Space** | O(1) — hanya beberapa variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1, 3, 5, 6]`, `target = 5`

**Init:** `l = 0, r = 3`

| Step | l | r | m | nums[m] | Aksi |
| ---- | --- | --- | --- | ------- | ------------------------------ |
| 1 | 0 | 3 | 1 | 3 | target(5) > 3 → l = 2 |
| 2 | 2 | 3 | 2 | 5 | nums[m]==target → **return 2** |

**return `2` ✅**

______________________________________________________________________

**Input:** `nums = [1, 3, 5, 6]`, `target = 2`

**Init:** `l = 0, r = 3`

| Step | l | r | m | nums[m] | Aksi |
| ---- | --- | --- | --- | ------- | --------------------- |
| 1 | 0 | 3 | 1 | 3 | target(2) < 3 → r = 0 |
| 2 | 0 | 0 | 0 | 1 | target(2) > 1 → l = 1 |
| 3 | 1 | 0 | - | - | l > r → stop |

**return `l = 1` ✅**

______________________________________________________________________

**Input:** `nums = [1, 3, 5, 6]`, `target = 7`

**Init:** `l = 0, r = 3`

| Step | l | r | m | nums[m] | Aksi |
| ---- | --- | --- | --- | ------- | --------------------- |
| 1 | 0 | 3 | 1 | 3 | target(7) > 3 → l = 2 |
| 2 | 2 | 3 | 2 | 5 | target(7) > 5 → l = 3 |
| 3 | 3 | 3 | 3 | 6 | target(7) > 6 → l = 4 |
| 4 | 4 | 3 | - | - | l > r → stop |

**return `l = 4` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Target lebih kecil dari semua elemen → return `0`
- [ ] Target lebih besar dari semua elemen → return `nums.length`
- [ ] Target ada di array → return index-nya
- [ ] Array satu elemen

______________________________________________________________________

## 📌 Key Takeaway

Kenapa `return l` bukan `return r`? Karena saat loop berhenti (`l > r`), pointer `l` selalu berada di posisi **pertama yang lebih besar dari target** — tepat dimana target seharusnya disisipkan. Ini adalah property penting dari Binary Search yang sering dipakai di banyak soal lain. 🎯

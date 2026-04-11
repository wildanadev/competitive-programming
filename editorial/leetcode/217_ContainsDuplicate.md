# 217. Contains Duplicate

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/contains-duplicate/)
- **Solution**: [Code](../../leetcode/ContainsDuplicate.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`, return `true` jika ada elemen yang muncul **lebih dari sekali**, return `false` jika semua elemen unik.

Contoh:

- `nums = [1,2,3,1]` → `true`
- `nums = [1,2,3,4]` → `false`
- `nums = [1,1,1,3,3,4,3,2,4,2]` → `true`

______________________________________________________________________

## 💡 Intuition

Gunakan **HashSet** untuk track elemen yang sudah dilihat. Kalau elemen sekarang sudah ada di set → duplikat ditemukan. Kalau belum ada → masukkan ke set dan lanjut.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `t = HashSet`
1. Loop setiap elemen `i` di `nums`:
   - Kalau `t.contains(i)` → return `true`
   - Kalau belum → `t.add(i)`
1. Return `false`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------ |
| **Time** | O(n) — satu kali loop, contains O(1) |
| **Space** | O(n) — HashSet menyimpan n elemen |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1, 2, 3, 1]`

**Init:** `t = {}`

| i | t.contains(i)? | Aksi | t |
| --- | -------------- | --------------- | ------- |
| 1 | ❌ | add(1) | {1} |
| 2 | ❌ | add(2) | {1,2} |
| 3 | ❌ | add(3) | {1,2,3} |
| 1 | ✅ | **return true** | - |

**return `true` ✅**

______________________________________________________________________

**Input:** `nums = [1, 2, 3, 4]`

**Init:** `t = {}`

| i | t.contains(i)? | Aksi | t |
| --- | -------------- | ------ | --------- |
| 1 | ❌ | add(1) | {1} |
| 2 | ❌ | add(2) | {1,2} |
| 3 | ❌ | add(3) | {1,2,3} |
| 4 | ❌ | add(4) | {1,2,3,4} |

**return `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → return `false`
- [ ] Semua elemen sama → return `true` di index 1
- [ ] Semua elemen unik → return `false`

______________________________________________________________________

## 📌 Key Takeaway

**Cek dulu sebelum add** — pattern ini lebih efisien dari add dulu lalu cek ukuran set, karena langsung return `true` saat duplikat pertama ditemukan tanpa perlu proses sisa array. HashSet dipilih karena `contains()` O(1) vs ArrayList yang O(n). 🎯

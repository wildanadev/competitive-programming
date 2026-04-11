# 1929. Concatenation of Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/concatenation-of-array/)
- **Solution**: [Code](../../leetcode/ConcatenationOfArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dengan panjang `n`, kembalikan array `ans` dengan panjang `2n` dimana `ans = [nums, nums]` — yaitu `nums` disambung dengan dirinya sendiri.

Contoh:

- `nums = [1, 2, 1]` → `ans = [1, 2, 1, 1, 2, 1]`
- `nums = [1, 3, 2, 1]` → `ans = [1, 3, 2, 1, 1, 3, 2, 1]`

______________________________________________________________________

## 💡 Intuition

Buat array baru berukuran `2 * n`, lalu isi dua kali:

- Index `i` → salin dari `nums[i]`
- Index `n + i` → salin lagi dari `nums[i]`

______________________________________________________________________

## 🔍 Approach

1. Buat array `ans` berukuran `2 * nums.length`
1. Loop dari `i = 0` sampai `n`:
   - `ans[i] = nums[i]` → isi bagian pertama
   - `ans[n + i] = nums[i]` → isi bagian kedua
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(n) — array baru ukuran 2n |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1, 2, 1]`, `n = 3`

**Init:** `ans = [0, 0, 0, 0, 0, 0]`

| i | ans[i] = nums[i] | ans[n+i] = nums[i] | ans |
| --- | ---------------- | ------------------ | ------------------ |
| 0 | ans[0] = 1 | ans[3] = 1 | [1, 0, 0, 1, 0, 0] |
| 1 | ans[1] = 2 | ans[4] = 2 | [1, 2, 0, 1, 2, 0] |
| 2 | ans[2] = 1 | ans[5] = 1 | [1, 2, 1, 1, 2, 1] |

**Output: `[1, 2, 1, 1, 2, 1]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → `[x]` jadi `[x, x]`
- [ ] Array kosong → return `[]`

______________________________________________________________________

## 📌 Key Takeaway

Trick sederhana: gunakan `n + i` sebagai offset untuk mengisi bagian kedua array dalam **satu loop yang sama**, tanpa perlu loop kedua. 🎯

# 1470. Shuffle the Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/shuffle-the-array/)
- **Solution**: [Code](../../leetcode/ShuffleTheArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dengan panjang `2n` dalam bentuk `[x1, x2, ..., xn, y1, y2, ..., yn]`, kembalikan array dalam bentuk `[x1, y1, x2, y2, ..., xn, yn]`.

Contoh:

- `nums = [2,5,1,3,4,7], n = 3` → `[2,3,5,4,1,7]`
- `nums = [1,2,3,4,4,3,2,1], n = 4` → `[1,4,2,3,3,2,4,1]`

______________________________________________________________________

## 💡 Intuition

Array dibagi dua bagian:

- Bagian pertama `[0..n-1]` → `x1, x2, ..., xn`
- Bagian kedua `[n..2n-1]` → `y1, y2, ..., yn`

Tinggal ambil bergantian dari kedua bagian pakai satu loop.

______________________________________________________________________

## 🔍 Approach

1. Buat array `ans` berukuran `nums.length`
1. Gunakan pointer `t` sebagai index pengisi `ans`
1. Loop `i` dari `0` sampai `n`:
   - `ans[t++] = nums[i]` → ambil dari bagian pertama
   - `ans[t++] = nums[n + i]` → ambil dari bagian kedua
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(n) — array baru ukuran 2n |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2, 5, 1, 3, 4, 7]`, `n = 3`

**Init:** `ans = [0, 0, 0, 0, 0, 0]`, `t = 0`

| i | ans[t++] = nums[i] | ans[t++] = nums[n+i] | t | ans |
| --- | -------------------- | -------------------- | --- | ------------------ |
| 0 | ans[0] = nums[0] = 2 | ans[1] = nums[3] = 3 | 2 | [2, 3, 0, 0, 0, 0] |
| 1 | ans[2] = nums[1] = 5 | ans[3] = nums[4] = 4 | 4 | [2, 3, 5, 4, 0, 0] |
| 2 | ans[4] = nums[2] = 1 | ans[5] = nums[5] = 7 | 6 | [2, 3, 5, 4, 1, 7] |

**Output: `[2, 3, 5, 4, 1, 7]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → array dua elemen, swap posisi
- [ ] Semua elemen sama → output sama dengan input

______________________________________________________________________

## 📌 Key Takeaway

Trick **pointer `t` dengan post-increment** (`t++`) memungkinkan mengisi dua posisi berbeda dalam satu iterasi loop tanpa perlu menghitung index secara manual. Teknik ini juga dipakai di soal **Concatenation of Array** dan soal manipulasi array lainnya. 🎯

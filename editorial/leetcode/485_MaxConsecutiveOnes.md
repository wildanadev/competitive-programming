# 485. Max Consecutive Ones

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/max-consecutive-ones/)
- **Solution**: [Code](../../leetcode/MaxConsecutiveOnes.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array binary `nums`, kembalikan jumlah maksimum angka `1` yang berurutan.

Contoh:

- `nums = [1,1,0,1,1,1]` → `3`
- `nums = [1,0,1,1,0,1]` → `2`

______________________________________________________________________

## 💡 Intuition

Loop array sambil hitung streak `1` yang sedang berjalan. Kalau ketemu `0`, reset counter ke `0`. Setiap iterasi update nilai maksimum.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `max = 0` dan `count = 0`
1. Loop setiap elemen `i` di `nums`:
   - Kalau `i == 1` → `count++`
   - Kalau `i == 0` → reset `count = 0`
   - Update `max` kalau `count > max`
1. Return `max`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — hanya dua variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1, 1, 0, 1, 1, 1]`

**Init:** `max = 0, count = 0`

| i | i==1? | count | max |
| --- | ----- | ------------- | ----- |
| 1 | ✅ | 0+1 = **1** | 1 |
| 1 | ✅ | 1+1 = **2** | 2 |
| 0 | ❌ | reset = **0** | 2 |
| 1 | ✅ | 0+1 = **1** | 2 |
| 1 | ✅ | 1+1 = **2** | 2 |
| 1 | ✅ | 2+1 = **3** | **3** |

**return `3` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen `1` → return `nums.length`
- [ ] Semua elemen `0` → return `0`
- [ ] Hanya satu elemen `1` → return `1`

______________________________________________________________________

## 📌 Key Takeaway

Pola **reset counter saat kondisi tidak terpenuhi** sangat umum di soal streak/consecutive. Kombinasi ternary `count = i == 1 ? count + 1 : 0` membuat kode lebih ringkas dari if-else biasa. Update `max` di setiap iterasi memastikan tidak ada streak yang terlewat. 🎯

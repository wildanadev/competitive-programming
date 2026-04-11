# 283. Move Zeroes

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/move-zeroes/)
- **Solution**: [Code](../../leetcode/MoveZeros.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`, pindahkan semua `0` ke akhir array secara **in-place** sambil mempertahankan urutan elemen non-zero.

Contoh:

- `nums = [0,1,0,3,12]` → `[1,3,12,0,0]`
- `nums = [0]` → `[0]`

______________________________________________________________________

## 💡 Intuition

Gunakan pointer `count` sebagai index pengisi elemen non-zero. Setiap kali elemen non-zero ditemukan, **swap** dengan elemen di posisi `count`, lalu `count++`. Dengan cara ini semua non-zero terkumpul di depan dan semua `0` otomatis terdorong ke belakang.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `count = 0`
1. Loop index `i` dari `0` sampai `nums.length`:
   - Kalau `nums[i] != 0` → swap `nums[i]` dengan `nums[count]`, lalu `count++`
1. Selesai — semua `0` sudah di belakang

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — in-place, tidak pakai array baru |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [0, 1, 0, 3, 12]`

**Init:** `count = 0`

| i | nums[i] | != 0? | Swap | count | nums |
| --- | ------- | ----- | ---------------------- | ----- | ------------ |
| 0 | 0 | ❌ | - | 0 | [0,1,0,3,12] |
| 1 | 1 | ✅ | swap(nums[1], nums[0]) | 1 | [1,0,0,3,12] |
| 2 | 0 | ❌ | - | 1 | [1,0,0,3,12] |
| 3 | 3 | ✅ | swap(nums[3], nums[1]) | 2 | [1,3,0,0,12] |
| 4 | 12 | ✅ | swap(nums[4], nums[2]) | 3 | [1,3,12,0,0] |

**Output: `[1,3,12,0,0]` ✅**

______________________________________________________________________

## Visualisasi Swap

```
[0, 1, 0, 3, 12]
 ^count

i=1: nums[1]=1 != 0 → swap(i=1, count=0)
[1, 0, 0, 3, 12]  count=1
    ^count

i=3: nums[3]=3 != 0 → swap(i=3, count=1)
[1, 3, 0, 0, 12]  count=2
       ^count

i=4: nums[4]=12 != 0 → swap(i=4, count=2)
[1, 3, 12, 0, 0]  count=3 ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen `0` → array tidak berubah
- [ ] Tidak ada `0` → array tidak berubah
- [ ] `0` hanya di awal → dipindah ke belakang
- [ ] `0` hanya di akhir → sudah di posisi yang benar

______________________________________________________________________

## 📌 Key Takeaway

Pendekatan **swap** berbeda dari Remove Element (#27) yang hanya overwrite — swap mempertahankan elemen `0` di array (hanya dipindah ke belakang) bukan dihilangkan. Ketika `i == count` (tidak ada `0` sebelumnya), swap dengan diri sendiri tidak mengubah apapun — ini yang membuat solusi tetap benar untuk kasus tanpa `0`. 🎯

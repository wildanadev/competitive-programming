# 75. Sort Colors

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Two Pointers, Sorting
- **Link**: [Problem](https://leetcode.com/problems/sort-colors/)
- **Solution**: [Code](../../leetcode/SortColors.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` yang hanya berisi `0`, `1`, dan `2` (merepresentasikan merah, putih, biru), urutkan **in-place** sehingga semua `0` di depan, diikuti `1`, lalu `2`.

Tidak boleh menggunakan fungsi sort bawaan.

Contoh:

- `nums = [2,0,2,1,1,0]` → `[0,0,1,1,2,2]`
- `nums = [2,0,1]` → `[0,1,2]`

______________________________________________________________________

## 💡 Intuition

Gunakan **Dutch National Flag Algorithm** (Dijkstra) — tiga pointer `l`, `m`, `h` yang menjaga invariant:

- `nums[0..l-1]` → semua `0`
- `nums[l..m-1]` → semua `1`
- `nums[h+1..n-1]` → semua `2`
- `nums[m..h]` → belum diproses

`m` bergerak dari kiri ke kanan. Setiap elemen yang ditemukan dimasukkan ke zona yang tepat dengan **temp variable swap**.

______________________________________________________________________

## 🔍 Approach

### Dutch National Flag Algorithm

1. Inisialisasi `l = 0`, `m = 0`, `h = nums.length - 1`.
1. Selama `m <= h`:
   - `nums[m] == 0` → swap `nums[l]↔nums[m]`, `l++`, `m++`
   - `nums[m] == 1` → sudah di posisi benar, `m++`
   - `nums[m] == 2` → swap `nums[m]↔nums[h]`, `h--` (**`m` tidak maju!**)
1. Return (modifikasi in-place).

> Saat swap dengan `h`, elemen yang datang dari kanan belum diproses — `m` tidak boleh maju sebelum elemen tersebut dicek.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n) — satu pass, setiap elemen dikunjungi maksimal sekali |
| **Space** | O(1) — hanya tiga pointer dan satu variabel temp |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,0,2,1,1,0]`

`l=0, m=0, h=5`

| m | nums[m] | Aksi | l | m | h | nums |
| ---------------- | ------- | ----------------- | --- | --- | --- | --------------- |
| 0 | 2 | swap m↔h, h-- | 0 | 0 | 4 | `[0,0,2,1,1,2]` |
| 0 | 0 | swap l↔m, l++,m++ | 1 | 1 | 4 | `[0,0,2,1,1,2]` |
| 1 | 0 | swap l↔m, l++,m++ | 2 | 2 | 4 | `[0,0,2,1,1,2]` |
| 2 | 2 | swap m↔h, h-- | 2 | 2 | 3 | `[0,0,1,1,2,2]` |
| 2 | 1 | m++ | 2 | 3 | 3 | `[0,0,1,1,2,2]` |
| 3 | 1 | m++ | 2 | 4 | 3 | `[0,0,1,1,2,2]` |
| m=4 > h=3 → stop | | | | | | |

**Output: `[0,0,1,1,2,2]` ✅**

______________________________________________________________________

**Input:** `nums = [2,0,1]`

`l=0, m=0, h=2`

| m | nums[m] | Aksi | l | m | h | nums |
| ---------------- | ------- | ----------------- | --- | --- | --- | --------- |
| 0 | 2 | swap m↔h, h-- | 0 | 0 | 1 | `[1,0,2]` |
| 0 | 1 | m++ | 0 | 1 | 1 | `[1,0,2]` |
| 1 | 0 | swap l↔m, l++,m++ | 1 | 2 | 1 | `[0,1,2]` |
| m=2 > h=1 → stop | | | | | | |

**Output: `[0,1,2]` ✅**

______________________________________________________________________

**Input:** `nums = [2]`

`l=0, m=0, h=0`

| m | nums[m] | Aksi | l | m | h | nums |
| ----------------- | ------- | ------------- | --- | --- | --- | ----- |
| 0 | 2 | swap m↔h, h-- | 0 | 0 | -1 | `[2]` |
| m=0 > h=-1 → stop | | | | | | |

> swap `nums[m]↔nums[h]` saat `m==h`: `temp=nums[0]=2`, `nums[h--]=nums[m]` → `nums[0]=nums[0]=2`, `nums[m]=temp=2` → nilai tidak berubah ✅

**Output: `[2]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu elemen → `m==h`, swap dengan dirinya sendiri → nilai tidak berubah ✅
- [ ] Semua elemen sama → hanya satu pointer yang bergerak
- [ ] Sudah terurut → tidak ada swap, `m` terus maju hingga melewati `h`
- [ ] `[0]` atau `[1]` atau `[2]` → satu iterasi, langsung stop

______________________________________________________________________

## 📌 Key Takeaway

**Dutch National Flag** adalah algoritma partisi tiga-arah yang elegan — satu pass O(n) dengan O(1) space. Kunci invariantnya: zona `[0..l-1]`, `[l..m-1]`, dan `[h+1..n-1]` selalu terjaga. Penggunaan temp variable lebih aman dari XOR swap karena tidak memiliki bug saat `l == m` atau `m == h`. 🎯

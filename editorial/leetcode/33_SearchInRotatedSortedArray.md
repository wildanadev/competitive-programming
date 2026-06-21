# 33. Search in Rotated Sorted Array

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/search-in-rotated-sorted-array/)
- **Solution**: [Code](../../leetcode/SearchInRotatedSortedArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array terurut ascending yang telah **dirotasi** di titik pivot tidak diketahui, dan integer `target`. Kembalikan indeks `target` jika ada, atau `-1`. Harus O(log n).

Contoh:

- `nums = [4,5,6,7,0,1,2]`, `target = 0` → `4`
- `nums = [4,5,6,7,0,1,2]`, `target = 3` → `-1`
- `nums = [1]`, `target = 0` → `-1`

______________________________________________________________________

## 💡 Intuition

Array yang dirotasi memiliki properti penting: **salah satu setengah dari `[l, m]` atau `[m, r]` pasti terurut normal**. Dengan mengecek `nums[m] >= nums[l]`, kita tahu setengah mana yang terurut, lalu cek apakah `target` ada di rentang setengah tersebut.

```
[4,5,6,7,0,1,2]
 l       m     r
nums[m]=7 >= nums[l]=4 → setengah kiri [l..m] terurut normal

Cek: apakah target ada di [nums[l], nums[m]]?
  Jika ya → cari di kiri
  Jika tidak → cari di kanan (yang memiliki bagian rotasi)
```

______________________________________________________________________

## 🔍 Approach

### Modified Binary Search

1. `l=0, r=n-1`.
1. Selama `l <= r`:
   - `m = l + (r-l)/2`
   - Jika `nums[m] == target` → return `m`.
   - **Cek setengah mana yang terurut:**
     - Jika `nums[m] >= nums[l]` → **kiri terurut**:
       - Jika `nums[l] <= target <= nums[m]` → target di kiri → `r = m-1`
       - Else → target di kanan → `l = m+1`
     - Else → **kanan terurut**:
       - Jika `nums[m] <= target <= nums[r]` → target di kanan → `l = m+1`
       - Else → target di kiri → `r = m-1`
1. Return `-1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------ |
| **Time** | O(log n) — binary search dengan kerja konstan tambahan |
| **Space** | O(1) — hanya pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [4,5,6,7,0,1,2]`, `target = 0`

`l=0, r=6`

**Iterasi 1:** `m=3`, `nums[3]=7`

```
nums[m]=7 != target=0
nums[m]=7 >= nums[l]=4 → kiri terurut [4,5,6,7]
nums[l]=4 <= 0 <= nums[m]=7? ❌ (0 < 4)
→ target di kanan → l = m+1 = 4
```

**Iterasi 2:** `l=4, r=6, m=5`, `nums[5]=1`

```
nums[m]=1 != 0
nums[m]=1 >= nums[l]=nums[4]=0 → kiri terurut [0,1]
nums[l]=0 <= 0 <= nums[m]=1? ✅
→ target di kiri → r = m-1 = 4
```

**Iterasi 3:** `l=4, r=4, m=4`, `nums[4]=0`

```
nums[m]=0 == target=0 ✅ → return 4
```

**Output: `4` ✅**

______________________________________________________________________

**Input:** `nums = [4,5,6,7,0,1,2]`, `target = 3`

`l=0, r=6`

**Iterasi 1:** `m=3`, `nums[3]=7`

```
7 != 3
7 >= nums[l]=4 → kiri terurut
4 <= 3 <= 7? ❌ (3 < 4)
→ kanan → l=4
```

**Iterasi 2:** `l=4, r=6, m=5`, `nums[5]=1`

```
1 != 3
1 >= nums[4]=0 → kiri terurut [0,1]
0 <= 3 <= 1? ❌ (3 > 1)
→ kanan → l=6
```

**Iterasi 3:** `l=6, r=6, m=6`, `nums[6]=2`

```
2 != 3
2 >= nums[6]=2 → kiri terurut (trivial, l==m==r)
2 <= 3 <= 2? ❌
→ kanan → l=7
```

`l=7 > r=6` → return `-1`

**Output: `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array tidak dirotasi (sudah terurut penuh) → selalu masuk cabang "kiri terurut", bekerja seperti binary search normal
- [ ] Array satu elemen → langsung cek `nums[0] == target`
- [ ] Target sama dengan pivot (elemen terkecil) → ditemukan via kondisi rentang
- [ ] Duplikat tidak ada di constraint soal ini (lihat _Search in Rotated Sorted Array II_ untuk versi dengan duplikat)

______________________________________________________________________

## 🔧 Kenapa `nums[m] >= nums[l]` Menentukan Setengah yang Terurut?

Jika array dirotasi sekali di titik pivot, maka:

```
[4,5,6,7,0,1,2]
       ↑ pivot di sini (0)

Jika m berada SEBELUM pivot: nums[l..m] tidak melewati rotasi → terurut normal
  → nums[m] >= nums[l] selalu benar

Jika m berada SESUDAH pivot: nums[m..r] tidak melewati rotasi → terurut normal
  → nums[m] < nums[l] (karena l masih di bagian sebelum pivot yang lebih besar)
```

`nums[m] >= nums[l]` adalah cara cepat memastikan **tidak ada rotasi** antara `l` dan `m` — artinya segmen ini pasti naik monoton.

______________________________________________________________________

## 🔧 Kenapa Perlu Cek Rentang `nums[l] <= target <= nums[m]`?

Mengetahui satu sisi "terurut" tidak cukup — kita masih perlu tahu apakah `target` ada **di dalam rentang nilai** sisi tersebut, atau harus dicari di sisi lain (yang mengandung rotasi).

```
Kiri terurut [4,5,6,7], target=0
0 tidak di rentang [4,7] → pasti tidak ada di kiri
→ harus cari di kanan, meskipun kanan punya rotasi
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah binary search yang dimodifikasi untuk menangani **satu titik rotasi** — kunci insight-nya adalah salah satu dari dua bagian `[l,m]` atau `[m,r]` selalu terurut normal. Dengan mengecek apakah target berada di rentang nilai bagian yang terurut, kita bisa menentukan arah pencarian yang benar meski array tidak fully sorted. Pola "identify sorted half, check range, decide direction" ini menjadi dasar untuk variasi rotated array lainnya. 🎯

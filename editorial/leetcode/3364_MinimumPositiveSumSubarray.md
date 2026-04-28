# 3364. Minimum Positive Sum Subarray

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sliding Window, Prefix Sum
- **Link**: [Problem](https://leetcode.com/problems/minimum-positive-sum-subarray/)
- **Solution**: [Code](../../leetcode/MinimumPositiveSumSubarray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan list integer `nums` dan dua integer `l` dan `r`, temukan **jumlah minimum yang positif** dari semua subarray dengan panjang antara `l` dan `r` (inklusif). Jika tidak ada subarray yang memenuhi, return `-1`.

Contoh:

- `nums = [3,-2,1,4]`, `l = 2`, `r = 3` → `1`
- `nums = [-2,2,-3]`, `l = 1`, `r = 2` → `-1` (tidak ada subarray positif)

______________________________________________________________________

## 💡 Intuition

Coba **semua ukuran window** dari `l` sampai `r`. Untuk setiap ukuran window, gunakan **sliding window** untuk menggeser window sepanjang array — hitung jumlahnya dan update `ans` jika jumlahnya positif dan lebih kecil dari `ans` saat ini.

Karena kita perlu mencoba semua panjang dari `l` sampai `r`, ada loop luar untuk ukuran window dan loop dalam untuk sliding.

______________________________________________________________________

## 🔍 Approach

### Multi-Size Sliding Window

1. Inisialisasi `ans = Integer.MAX_VALUE`.
1. Loop `l` dari nilai awal sampai `r` (coba semua ukuran window):
   - Hitung jumlah window pertama (`nums[0..l-1]`).
   - Jika positif → update `ans`.
   - Geser window dari `i = l` sampai `nums.size() - 1`:
     - `window += nums[i] - nums[i - l]`
     - Jika positif → update `ans`.
   - Increment `l` (naikkan ukuran window).
1. Return `ans` jika bukan `MAX_VALUE`, else return `-1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------- |
| **Time** | O((r-l+1) × n) — untuk setiap ukuran window, sliding O(n) |
| **Space** | O(1) — hanya variabel tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,-2,1,4]`, `l = 2`, `r = 3`

______________________________________________________________________

**Ukuran window = 2 (l=2):**

Window pertama `[3,-2]`: `window = 3 + (-2) = 1` → positif ✅ → `ans = 1`

Sliding:
| i | masuk | keluar | window | positif? | ans |
|---|-----------|-----------|---------------|----------|-----|
| 2 | nums[2]=1 | nums[0]=3 | 1+1-3 = **-1**| ❌ | 1 |
| 3 | nums[3]=4 | nums[1]=-2| -1+4-(-2) = **5**| ✅ | 1 |

______________________________________________________________________

**Ukuran window = 3 (l=3):**

Window pertama `[3,-2,1]`: `window = 3 + (-2) + 1 = 2` → positif ✅ → `ans = min(1,2) = 1`

Sliding:
| i | masuk | keluar | window | positif? | ans |
|---|-----------|-----------|---------------|----------|-----|
| 3 | nums[3]=4 | nums[0]=3 | 2+4-3 = **3** | ✅ | 1 |

**Output: `1` ✅**

______________________________________________________________________

**Input:** `nums = [-2,2,-3]`, `l = 1`, `r = 2`

**Ukuran window = 1:**

| window | positif? |
| ------ | ------------ |
| -2 | ❌ |
| 2 | ✅ → ans = 2 |
| -3 | ❌ |

**Ukuran window = 2:**

Window pertama `[-2,2]`: `window = 0` → tidak positif

| i | window | positif? |
| --- | -------------- | -------- |
| 2 | 0+(-3)-(-2)=-1 | ❌ |

`ans = 2`

**Output: `2`**

> Jika expected output adalah `-1`, berarti tidak ada subarray positif — untuk input ini `[2]` dengan panjang `1` valid, jadi output `2`.

______________________________________________________________________

**Input:** `nums = [-2,-3,-1]`, `l = 1`, `r = 2`

Semua window negatif → `ans` tetap `MAX_VALUE` → return `-1`

**Output: `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen negatif → tidak ada window positif → return `-1`
- [ ] `l == r` → hanya satu ukuran window yang dicoba
- [ ] Jumlah window tepat `0` → tidak positif, tidak dihitung
- [ ] Satu elemen positif dengan `l = 1` → langsung ditemukan di iterasi pertama

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan **loop ukuran window** dengan **sliding window** di dalamnya — pola yang cocok ketika ukuran window tidak tetap tapi berada dalam rentang `[l, r]`. Untuk setiap ukuran, sliding window memastikan semua posisi diperiksa dalam O(n). Kompleksitas total O((r-l+1) × n) bisa diterima selama rentang `r-l` tidak terlalu besar. 🎯

# 3904. Smallest Stable Index I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Prefix Maximum, Suffix Minimum
- **Link**: [Problem](https://leetcode.com/problems/smallest-stable-index-ii/)
- **Solution**: [Code](../../leetcode/SmallestStableIndexII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `k`, untuk setiap indeks `i` definisikan **instability score** sebagai:

```
instability(i) = max(nums[0..i]) - min(nums[i..n-1])
```

Indeks `i` disebut _stable_ jika `instability(i) <= k`. Kembalikan **indeks stable terkecil**. Jika tidak ada, return `-1`.

Contoh:

- `nums = [5,0,1,4]`, `k = 3` → `3`
- `nums = [3,2,1]`, `k = 1` → `-1`
- `nums = [0]`, `k = 0` → `0`

______________________________________________________________________

## 💡 Intuition

Untuk setiap indeks `i`, kita butuh dua nilai:

- **Max dari kiri** — nilai terbesar dari `nums[0]` sampai `nums[i]`
- **Min dari kanan** — nilai terkecil dari `nums[i]` sampai `nums[n-1]`

Jika kita menghitung keduanya dengan loop di setiap iterasi, kompleksitasnya O(n²). Tapi perhatikan: **max dari kiri hanya bisa naik atau tetap** seiring `i` bertambah, dan **min dari kanan hanya bisa naik atau tetap** seiring `i` bertambah dari kanan. Artinya kita bisa **precompute** kedua nilai ini sekali saja dan simpan hasilnya di dua array bantu — teknik ini disebut **Prefix Maximum** dan **Suffix Minimum**.

______________________________________________________________________

## 🔍 Approach

### Prefix Maximum + Suffix Minimum

**Step 1 — Build Prefix Maximum (`prefMax`)**

`prefMax[i]` = nilai maksimum dari `nums[0]` sampai `nums[i]`.

Dibangun dari **kiri ke kanan** — setiap posisi hanya perlu membandingkan nilai saat ini dengan maksimum sebelumnya:

```
prefMax[0] = nums[0]
prefMax[i] = max(prefMax[i-1], nums[i])
```

**Step 2 — Build Suffix Minimum (`sufMin`)**

`sufMin[i]` = nilai minimum dari `nums[i]` sampai `nums[n-1]`.

Dibangun dari **kanan ke kiri** — setiap posisi hanya perlu membandingkan nilai saat ini dengan minimum berikutnya:

```
sufMin[n-1] = nums[n-1]
sufMin[i]   = min(sufMin[i+1], nums[i])
```

**Step 3 — Scan dan Cek**

Loop dari kiri, cek apakah `prefMax[i] - sufMin[i] <= k`. Return `i` pertama yang memenuhi syarat.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------- |
| **Time** | O(n) — tiga loop linear terpisah |
| **Space** | O(n) — dua array bantu `prefMax` dan `sufMin` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [5,0,1,4]`, `k = 3`

**Step 1 — Build `prefMax` (kiri ke kanan):**
| i | nums[i] | Operasi | prefMax[i] |
|---|---------|--------------------|------------|
| 0 | 5 | = nums[0] | **5** |
| 1 | 0 | max(5, 0) = 5 | **5** |
| 2 | 1 | max(5, 1) = 5 | **5** |
| 3 | 4 | max(5, 4) = 5 | **5** |

`prefMax = [5, 5, 5, 5]`

**Step 2 — Build `sufMin` (kanan ke kiri):**
| i | nums[i] | Operasi | sufMin[i] |
|---|---------|--------------------|-----------|
| 3 | 4 | = nums[3] | **4** |
| 2 | 1 | min(1, 4) = 1 | **1** |
| 1 | 0 | min(0, 1) = 0 | **0** |
| 0 | 5 | min(5, 0) = 0 | **0** |

`sufMin = [0, 0, 1, 4]`

**Step 3 — Cek tiap indeks:**
| i | prefMax[i] | sufMin[i] | selisih | \<= 3? |
|---|------------|-----------|---------|-------|
| 0 | 5 | 0 | 5 | ❌ |
| 1 | 5 | 0 | 5 | ❌ |
| 2 | 5 | 1 | 4 | ❌ |
| 3 | 5 | 4 | 1 | ✅ → return `3` |

**Output: `3` ✅**

______________________________________________________________________

**Input:** `nums = [3,2,1]`, `k = 1`

**Build `prefMax`:** `[3, 3, 3]`

**Build `sufMin`:**
| i | nums[i] | Operasi | sufMin[i] |
|---|---------|---------------|-----------|
| 2 | 1 | = nums[2] | **1** |
| 1 | 2 | min(2, 1) = 1 | **1** |
| 0 | 3 | min(3, 1) = 1 | **1** |

`sufMin = [1, 1, 1]`

**Cek tiap indeks:**
| i | prefMax[i] | sufMin[i] | selisih | \<= 1? |
|---|------------|-----------|---------|-------|
| 0 | 3 | 1 | 2 | ❌ |
| 1 | 3 | 1 | 2 | ❌ |
| 2 | 3 | 1 | 2 | ❌ |

**Output: `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → `prefMax[0] = sufMin[0] = nums[0]` → selisih `0` → selalu stable jika `k >= 0`
- [ ] `k` sangat besar → indeks `0` selalu stable
- [ ] Array terurut ascending → `prefMax` naik, `sufMin` naik → selisih mengecil → jawaban cenderung di awal
- [ ] Array terurut descending → `prefMax` naik, `sufMin` tetap sama → selisih tidak pernah mengecil → kemungkinan `-1`

______________________________________________________________________

## 📌 Key Takeaway

**Prefix Maximum** dan **Suffix Minimum** adalah teknik precompute klasik — daripada menghitung ulang max/min dari nol di setiap indeks (O(n²)), kita hitung sekali dari kiri dan sekali dari kanan sehingga setiap query menjadi O(1), total O(n). Rumus intinya sederhana:

```
prefMax[i] = max(prefMax[i-1], nums[i])   // kiri ke kanan
sufMin[i]  = min(sufMin[i+1], nums[i])    // kanan ke kiri
```

Kapan pun soal membutuhkan **"max/min dari semua elemen sebelum i"** dan **"max/min dari semua elemen setelah i"** secara bersamaan, langsung pikirkan teknik ini. Pola yang sama muncul di _Trapping Rain Water_, _Product of Array Except Self_, dan _Maximum Product Subarray_. 🎯

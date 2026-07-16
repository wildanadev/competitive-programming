# 3867. Sum of GCD of Formed Pairs

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Math, Sorting
- **Link**: [Problem](https://leetcode.com/problems/sum-of-gcd-of-formed-pairs/)
- **Solution**: [Code](../../leetcode/SumOfGcdOfFormedPairs.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` panjang `n`. Konstruksi array `prefixGcd` di mana:

- `mxi = max(nums[0], ..., nums[i])`
- `prefixGcd[i] = gcd(nums[i], mxi)`

Setelah konstruksi:

1. Sort `prefixGcd` ascending.
1. Pasangkan elemen terkecil dengan terbesar, kedua terkecil dengan kedua terbesar, dst.
1. Jika `n` ganjil, elemen tengah diabaikan.
1. Return jumlah GCD dari semua pasangan.

Contoh:

- `nums = [2,6,4]` → `prefixGcd = [2,6,2]` → sort `[2,2,6]` → GCD(2,6)=2 → return `2`
- `nums = [3,6,2,8]` → `prefixGcd = [3,6,2,8]` → sort `[2,3,6,8]` → GCD(2,8)+GCD(3,6)=2+3=5

______________________________________________________________________

## 💡 Intuition

Dua langkah independen:

**Langkah 1 — Bangun `prefixGcd`:**
Untuk setiap indeks `i`, `prefixGcd[i] = gcd(nums[i], mxi)` di mana `mxi` adalah maksimum prefix sampai `i`. Perhatikan: `gcd(nums[i], mxi)` selalu `<= mxi` dan `<= nums[i]`.

**Langkah 2 — Pair dan hitung GCD:**
Setelah sort, pasangkan terkecil dengan terbesar (two-pointer) dan jumlahkan GCD setiap pasangan.

______________________________________________________________________

## 🔍 Approach

### Prefix Max + GCD + Sort + Two Pointer

1. Loop `i` dari `0` sampai `n-1`:
   - `mx = max(mx, nums[i])`
   - `prefixGcd[i] = gcd(nums[i], mx)`
1. Sort `prefixGcd`.
1. Two pointer `l=0, r=n-1`:
   - Selama `l < r`: `ans += gcd(prefixGcd[l], prefixGcd[r])`, `l++`, `r--`
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(n log n) — dominan di sorting; GCD tiap elemen O(log max) |
| **Space** | O(n) — array `prefixGcd` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,6,4]`

**Langkah 1 — Bangun prefixGcd:**

| i | nums[i] | mx | prefixGcd[i]=gcd(nums[i],mx) |
| --- | ------- | --- | ---------------------------- |
| 0 | 2 | 2 | gcd(2,2) = 2 |
| 1 | 6 | 6 | gcd(6,6) = 6 |
| 2 | 4 | 6 | gcd(4,6) = 2 |

`prefixGcd = [2,6,2]`

**Langkah 2 — Sort:** `[2,2,6]`

**Langkah 3 — Two Pointer:**
| l | r | prefixGcd[l] | prefixGcd[r] | GCD | ans |
|---|---|--------------|--------------|-----|-----|
| 0 | 2 | 2 | 6 | 2 | 2 |
| 1 | 1 | l==r → stop | | | |

(elemen tengah `2` diabaikan karena n=3 ganjil)

**Output: `2` ✅**

______________________________________________________________________

**Input:** `nums = [3,6,2,8]`

**Bangun prefixGcd:**

| i | nums[i] | mx | prefixGcd[i] |
| --- | ------- | --- | ------------ |
| 0 | 3 | 3 | gcd(3,3)=3 |
| 1 | 6 | 6 | gcd(6,6)=6 |
| 2 | 2 | 6 | gcd(2,6)=2 |
| 3 | 8 | 8 | gcd(8,8)=8 |

`prefixGcd = [3,6,2,8]`

**Sort:** `[2,3,6,8]`

**Two Pointer:**
| l | r | GCD | ans |
|---|---|-----|-----|
| 0 | 3 | gcd(2,8)=2 | 2 |
| 1 | 2 | gcd(3,6)=3 | 5 |

**Output: `5` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → tidak ada pasangan → return `0` (loop tidak jalan karena `l == r`)
- [ ] `n` ganjil → elemen tengah diabaikan (`l < r` berhenti sebelum `l == r`)
- [ ] Semua elemen sama → semua `prefixGcd[i] = nums[i]` → GCD semua pasangan = nilai tersebut

______________________________________________________________________

## 🔧 Kenapa `prefixGcd[i] = gcd(nums[i], mxi)`?

`mxi` adalah nilai maksimum dari `nums[0..i]`. GCD dari `nums[i]` dengan `mxi` menangkap hubungan setiap elemen dengan "konteks prefix"-nya. Elemen yang merupakan maksimum baru (`nums[i] == mxi`) akan menghasilkan `prefixGcd[i] = mxi` karena `gcd(x, x) = x`.

______________________________________________________________________

## 🔧 Kenapa Two Pointer (Terkecil-Terbesar)?

Soal mendefinisikan pasangan sebagai "elemen terkecil yang belum dipasangkan" dengan "elemen terbesar yang belum dipasangkan". Setelah sort, ini persis yang dilakukan two pointer `l++, r--`:

```
sorted: [2, 3, 6, 8]
         ↑           ↑
         l=0         r=3 → pasangan pertama (2,8)

         [2, 3, 6, 8]
              ↑  ↑
              l=1 r=2 → pasangan kedua (3,6)
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **simulasi dua tahap** yang dipisahkan secara bersih: bangun `prefixGcd` dengan prefix max, lalu sort dan pair dengan two pointer. Kunci implementasinya adalah `mx` yang di-track secara incremental (tidak perlu recompute dari awal setiap iterasi), dan two pointer yang menangkap definisi pairing "terkecil dengan terbesar" secara natural. 🎯

# 448. Find All Numbers Disappeared in an Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/)
- **Solution**: [Code](../../leetcode/FindAllNumbersDisappearedInAnArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dengan panjang `n` di mana setiap elemen berada di rentang `[1, n]`, kembalikan semua angka dari `1` sampai `n` yang **tidak muncul** di `nums`.

Contoh:

- `nums = [4,3,2,7,8,2,3,1]` → `[5,6]`
- `nums = [1,1]` → `[2]`

______________________________________________________________________

## 💡 Intuition

Setiap angka di `nums` berada di rentang `[1, n]`, artinya setiap nilai bisa dipetakan langsung ke **indeks array** (`nilai - 1`). Idenya: gunakan **tanda negatif** pada elemen di indeks tersebut sebagai "cap" bahwa angka itu pernah ditemukan.

Setelah semua elemen diproses, indeks yang nilainya **masih positif** berarti angka `(indeks + 1)` tidak pernah dikunjungi — itulah angka yang hilang. Pendekatan ini tidak membutuhkan struktur data tambahan sama sekali.

______________________________________________________________________

## 🔍 Approach

### Negative Marker (In-place)

1. **Pass 1 — Tandai:** Untuk setiap elemen, hitung `idx = abs(nums[i]) - 1`. Jika `nums[idx] > 0`, balik tandanya menjadi negatif.
   - `Math.abs()` diperlukan karena elemen yang sudah diproses sebelumnya mungkin sudah negatif.
   - Kondisi `> 0` mencegah pembalikan ganda pada duplikat.
1. **Pass 2 — Kumpulkan:** Loop `i` dari `0` sampai `n-1`. Jika `nums[i] > 0` → angka `i+1` tidak pernah dikunjungi → tambahkan ke `ans`.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------- |
| **Time** | O(n) — dua loop linear terpisah |
| **Space** | O(1) — tidak ada struktur data tambahan (tidak menghitung output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [4,3,2,7,8,2,3,1]`

**Pass 1 — Tandai dengan tanda negatif:**

| i | nums[i] | idx = abs(nums[i])-1 | nums[idx] sebelum | nums[idx] > 0? | Array setelah |
| --- | ------- | -------------------- | ----------------- | -------------- | ------------------------- |
| 0 | 4 | 3 | 7 | ✅ balik | `[4,3,2,-7,8,2,3,1]` |
| 1 | 3 | 2 | 2 | ✅ balik | `[4,3,-2,-7,8,2,3,1]` |
| 2 | -2 | abs(-2)-1 = **1** | 3 | ✅ balik | `[4,-3,-2,-7,8,2,3,1]` |
| 3 | -7 | abs(-7)-1 = **6** | 3 | ✅ balik | `[4,-3,-2,-7,8,2,-3,1]` |
| 4 | 8 | 7 | 1 | ✅ balik | `[4,-3,-2,-7,8,2,-3,-1]` |
| 5 | 2 | 1 | -3 | ❌ skip | `[4,-3,-2,-7,8,2,-3,-1]` |
| 6 | -3 | abs(-3)-1 = **2** | -2 | ❌ skip | `[4,-3,-2,-7,8,2,-3,-1]` |
| 7 | -1 | abs(-1)-1 = **0** | 4 | ✅ balik | `[-4,-3,-2,-7,8,2,-3,-1]` |

Array akhir: `[-4,-3,-2,-7,8,2,-3,-1]`

**Pass 2 — Cari indeks yang masih positif:**

| i | nums[i] | Positif? | ans |
| --- | ------- | -------- | ------- |
| 0 | -4 | ❌ | `[]` |
| 1 | -3 | ❌ | `[]` |
| 2 | -2 | ❌ | `[]` |
| 3 | -7 | ❌ | `[]` |
| 4 | **8** | ✅ | `[5]` |
| 5 | **2** | ✅ | `[5,6]` |
| 6 | -3 | ❌ | `[5,6]` |
| 7 | -1 | ❌ | `[5,6]` |

**Output: `[5,6]` ✅**

______________________________________________________________________

**Input:** `nums = [1,1]`

**Pass 1:**

| i | nums[i] | idx | nums[idx] sebelum | nums[idx] > 0? | Array setelah |
| --- | ------- | --- | ----------------- | -------------- | ------------- |
| 0 | 1 | 0 | 1 | ✅ balik | `[-1,1]` |
| 1 | 1 | 0 | -1 | ❌ skip | `[-1,1]` |

**Pass 2:**

| i | nums[i] | Positif? | ans |
| --- | ------- | -------- | ----- |
| 0 | -1 | ❌ | `[]` |
| 1 | **1** | ✅ | `[2]` |

**Output: `[2]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama → `[3,3,3]` → duplikat di-skip oleh kondisi `> 0`, indeks 0 dan 1 tetap positif → return `[1,2]`
- [ ] Tidak ada yang hilang → `[1,2,3]` → semua indeks jadi negatif → return `[]`
- [ ] Banyak duplikat → `[2,2,2,2]` → hanya indeks 1 yang dibalik, indeks 0,2,3 tetap positif → return `[1,3,4]`

______________________________________________________________________

## 🔧 Kenapa `Math.abs()` dan `> 0` Keduanya Penting?

```java
int idx = Math.abs(nums[i]) - 1; // (1)
if (nums[idx] > 0) nums[idx] = -nums[idx]; // (2)
```

**(1) `Math.abs()`** — Elemen yang sudah diproses di iterasi sebelumnya mungkin sudah bernilai negatif. Tanpa `abs()`, indeks akan negatif dan menyebabkan `ArrayIndexOutOfBoundsException`.

**(2) `> 0`** — Jika elemen di `nums[idx]` sudah negatif, berarti angka ini sudah pernah ditandai (duplikat). Membaliknya lagi akan mengembalikan ke positif dan merusak logika.

______________________________________________________________________

## 📌 Key Takeaway

Teknik **negative marker** adalah pola klasik untuk soal array dengan elemen di rentang `[1, n]` — nilai elemen bisa langsung dijadikan indeks, dan tanda negatif dipakai sebagai visited flag tanpa membutuhkan ruang tambahan. Dua kondisi krusialnya adalah `Math.abs()` untuk keamanan indeks dan `> 0` untuk menghindari pembalikan ganda pada duplikat. Pola yang sama juga muncul di soal _First Missing Positive_ dan _Find the Duplicate Number_. 🎯

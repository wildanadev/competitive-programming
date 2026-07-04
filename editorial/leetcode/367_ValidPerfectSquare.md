# 367. Valid Perfect Square

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/valid-perfect-square/)
- **Solution**: [Code](../../leetcode/ValidPerfectSquare.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer positif `num`, return `true` jika `num` adalah **perfect square** — bisa ditulis sebagai `k²` untuk suatu integer positif `k`. Tidak boleh menggunakan `Math.sqrt`.

Contoh:

- `num = 16` → `true` (4² = 16)
- `num = 14` → `false`

______________________________________________________________________

## 💡 Intuition

Cari integer `m` di rentang `[1, num]` sehingga `m² == num`. Karena fungsi `m²` monoton naik, gunakan **Binary Search**.

```
num = 16
m=8: 64 > 16 → cari kiri
m=4: 16 == 16 → true ✅

num = 14
m=7: 49 > 14 → cari kiri
m=3: 9 < 14 → cari kanan
m=5: 25 > 14 → cari kiri
m=4: 16 > 14 → cari kiri
l > r → false ✅
```

Mirip dengan _Sqrt(x)_ (#69), bedanya di sini kita return `true`/`false` bukan nilai floor-nya.

______________________________________________________________________

## 🔍 Approach

### Binary Search

1. `l=1, r=num`.
1. Selama `l <= r`:
   - `m = l + (r-l)/2`, cast ke `long` untuk mencegah overflow.
   - Jika `m*m == num` → return `true`.
   - Jika `m*m > num` → `r = m-1`.
   - Jika `m*m < num` → `l = m+1`.
1. Return `false`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------ |
| **Time** | O(log n) — binary search |
| **Space** | O(1) — hanya pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `num = 16`

`l=1, r=16`

| l | r | m | sqr=m² | Perbandingan | Aksi |
| --- | --- | --- | ------ | ------------ | ----------- |
| 1 | 16 | 8 | 64 | 64 > 16 | r=7 |
| 1 | 7 | 4 | 16 | 16 == 16 ✅ | return true |

**Output: `true` ✅**

______________________________________________________________________

**Input:** `num = 14`

`l=1, r=14`

| l | r | m | sqr | Perbandingan | Aksi |
| --- | --- | --- | --- | ------------ | ---- |
| 1 | 14 | 7 | 49 | 49 > 14 | r=6 |
| 1 | 6 | 3 | 9 | 9 < 14 | l=4 |
| 4 | 6 | 5 | 25 | 25 > 14 | r=4 |
| 4 | 4 | 4 | 16 | 16 > 14 | r=3 |

`l=4 > r=3` → return `false`

**Output: `false` ✅**

______________________________________________________________________

**Input:** `num = 1`

`l=1, r=1`

| l | r | m | sqr | Aksi |
| --- | --- | --- | --- | ------------------- |
| 1 | 1 | 1 | 1 | 1==1 ✅ return true |

**Output: `true` ✅** (1² = 1)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `num = 1` → perfect square (1² = 1)
- [ ] `num` perfect square besar (misal `2147395600 = 46340²`) → binary search tetap O(log n)
- [ ] `num` ganjil tapi bukan perfect square → `false`

______________________________________________________________________

## 🔧 Kenapa Perlu Cast ke `long`?

```java
long sqr = m;
sqr * sqr  // long * long = long ✅

// Tanpa cast:
m * m      // int * int bisa overflow jika m besar!
```

`num` bisa sampai `2³¹-1 ≈ 2.1×10⁹`. Saat `m` mendekati `√num ≈ 46340`, `m*m ≈ 2.1×10⁹` masih aman. Tapi saat binary search mencoba `m` besar di awal (misal `m = num/2`), `m*m` bisa jauh melebihi batas `int`. Cast ke `long` mencegah overflow.

______________________________________________________________________

## 🔧 Perbedaan dengan Sqrt(x) (#69)

| | Sqrt(x) | Valid Perfect Square |
| ----------------------- | ---------------- | ---------------------- |
| Tujuan | Cari floor(√x) | Cek apakah x adalah k² |
| Return | Nilai integer | Boolean |
| Kondisi exact match | return m | return true |
| Kondisi tidak ditemukan | return r (floor) | return false |

Keduanya menggunakan binary search yang sama, hanya interpretasi hasil berbeda.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi binary search yang mencari **exact match** pada fungsi kuadrat monoton. Berbeda dari Sqrt(x) yang mengembalikan floor value, di sini kita cukup return `true` saat exact match ditemukan dan `false` jika tidak. Cast `m` ke `long` sebelum perkalian adalah detail penting untuk mencegah overflow pada nilai `num` yang besar. 🎯

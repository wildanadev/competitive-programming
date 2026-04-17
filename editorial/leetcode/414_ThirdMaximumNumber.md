# 414. Third Maximum Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sorting
- **Link**: [Problem](https://leetcode.com/problems/third-maximum-number/)
- **Solution**: [Code](../../leetcode/ThridMaximumNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, kembalikan **nilai maksimum ketiga yang berbeda** (distinct). Jika nilai maksimum ketiga tidak ada, kembalikan nilai maksimum pertama.

Contoh:

- `nums = [3,2,1]` → `1`
- `nums = [1,2]` → `2`
- `nums = [2,2,3,1]` → `1`

______________________________________________________________________

## 💡 Intuition

Lacak tiga nilai maksimum terbesar secara bersamaan dalam satu pass. Gunakan tiga variabel `fMax`, `sMax`, `tMax` yang merepresentasikan **distinct** maksimum ke-1, ke-2, ke-3. Setiap elemen baru dimasukkan ke posisi yang tepat dengan menggeser nilai yang lebih kecil ke bawah — mirip insertion sort tapi hanya untuk 3 slot.

Tantangan utama: bagaimana mendeteksi apakah `tMax` pernah di-set? Solusinya adalah inisialisasi ketiga variabel dengan `Long.MIN_VALUE` sehingga kita bisa membedakan "belum pernah diisi" vs "diisi dengan `Integer.MIN_VALUE`".

______________________________________________________________________

## 🔍 Approach

### Single Pass — Three Variables

1. Inisialisasi `fMax = sMax = tMax = Long.MIN_VALUE`.
1. Iterasi setiap elemen `i`:
   - **Skip** jika `i` sudah sama dengan salah satu dari `fMax`, `sMax`, atau `tMax` (jaga keunikan/distinct).
   - Jika `i > fMax` → geser: `tMax ← sMax`, `sMax ← fMax`, `fMax ← i`
   - Else if `i > sMax` → geser: `tMax ← sMax`, `sMax ← i`
   - Else if `i > tMax` → `tMax ← i`
1. Jika `tMax == Long.MIN_VALUE` → maksimum ketiga tidak ada, return `fMax`. Jika tidak, return `tMax`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------- |
| **Time** | O(n) — satu kali iterasi array |
| **Space** | O(1) — hanya tiga variabel tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,2,3,1]`

| i | Skip? | fMax | sMax | tMax |
| --- | ------------ | ---- | ---- | ---- |
| — | — | MIN | MIN | MIN |
| 2 | ❌ | 2 | MIN | MIN |
| 2 | ✅ (== fMax) | 2 | MIN | MIN |
| 3 | ❌ | 3 | 2 | MIN |
| 1 | ❌ | 3 | 2 | 1 |

`tMax = 1 ≠ Long.MIN_VALUE` → return `1`

**Output: `1` ✅**

______________________________________________________________________

**Input:** `nums = [1,2]`

| i | Skip? | fMax | sMax | tMax |
| --- | ----- | ---- | ---- | ---- |
| — | — | MIN | MIN | MIN |
| 1 | ❌ | 1 | MIN | MIN |
| 2 | ❌ | 2 | 1 | MIN |

`tMax == Long.MIN_VALUE` → return `fMax = 2`

**Output: `2` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,2,5,3,5]`

| i | Skip? | fMax | sMax | tMax |
| --- | ------------ | ---- | ---- | ---- |
| — | — | MIN | MIN | MIN |
| 1 | ❌ | 1 | MIN | MIN |
| 2 | ❌ | 2 | 1 | MIN |
| 2 | ✅ (== fMax) | 2 | 1 | MIN |
| 5 | ❌ | 5 | 2 | 1 |
| 3 | ❌ | 5 | 3 | 2 |
| 5 | ✅ (== fMax) | 5 | 3 | 2 |

`tMax = 2 ≠ Long.MIN_VALUE` → return `2`

**Output: `2` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama → `[1,1,1]` → tidak ada distinct ke-3 → return `fMax = 1`
- [ ] Hanya dua distinct → `[1,2]` → return `fMax = 2`
- [ ] Array mengandung `Integer.MIN_VALUE` → `[-2147483648, 1, 2]` → harus return `-2147483648`; inilah alasan pakai `long` bukan `int`
- [ ] Array panjang 1 → `[5]` → return `5`

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan teknik **melacak top-K elemen dalam single pass** — pola yang sering muncul di berbagai variasi soal. Penggunaan `Long.MIN_VALUE` sebagai sentinel value adalah trik penting untuk menangani kasus di mana nilai `Integer.MIN_VALUE` mungkin hadir sebagai input valid. Untuk K yang lebih besar, pendekatan ini bisa digeneralisasi menggunakan `TreeSet` atau min-heap berukuran K. 🎯

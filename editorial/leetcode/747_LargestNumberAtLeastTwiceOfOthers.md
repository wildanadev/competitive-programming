# 747. Largest Number At Least Twice of Others

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sorting
- **Link**: [Problem](https://leetcode.com/problems/largest-number-at-least-twice-of-others/)
- **Solution**: [Code](../../leetcode/LargestNumberAtLeastTwiceOfOthers.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, cek apakah elemen terbesar **minimal dua kali lebih besar** dari semua elemen lainnya. Jika iya, kembalikan **indeks** elemen terbesar tersebut. Jika tidak, return `-1`.

Contoh:

- `nums = [3,6,1,0]` → `1` (6 >= 2×3, 6 >= 2×1, 6 >= 2×0)
- `nums = [1,2,3,4]` → `-1` (4 < 2×3)
- `nums = [1]` → `0`

______________________________________________________________________

## 💡 Intuition

Elemen terbesar hanya perlu dibandingkan dengan **elemen terbesar kedua** — jika ia minimal dua kali elemen terbesar kedua, maka otomatis ia juga minimal dua kali semua elemen lainnya (karena semua elemen lain ≤ elemen terbesar kedua).

Kode ini menggunakan pendekatan dua pass yang bersih:

- **Pass 1** — temukan nilai maksimum dan indeksnya.
- **Pass 2** — cek apakah ada elemen lain yang jika dikalikan 2 masih melebihi maksimum.

______________________________________________________________________

## 🔍 Approach

### Two Pass Linear Scan

1. **Pass 1** — Loop untuk menemukan `max` dan `idx` (indeks elemen terbesar).
1. **Pass 2** — Loop kembali, skip indeks `idx`. Jika ditemukan `nums[i] * 2 > max` → return `-1`.
1. Jika Pass 2 selesai tanpa masalah → return `idx`.

> Kondisi `nums[i] * 2 > max` ekuivalen dengan `nums[i] > max / 2`, tapi versi perkalian menghindari pembagian integer yang bisa kehilangan presisi.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------- |
| **Time** | O(n) — dua loop linear terpisah |
| **Space** | O(1) — hanya variabel `max` dan `idx` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,6,1,0]`

**Pass 1 — Cari max:**
| i | nums[i] | nums[i] > max? | max | idx |
|---|---------|----------------|-----|-----|
| 0 | 3 | 3 > -1 ✅ | 3 | 0 |
| 1 | 6 | 6 > 3 ✅ | 6 | 1 |
| 2 | 1 | 1 > 6 ❌ | 6 | 1 |
| 3 | 0 | 0 > 6 ❌ | 6 | 1 |

`max = 6`, `idx = 1`

**Pass 2 — Cek syarat:**
| i | Skip? | nums[i]*2 | > max(6)? | Aksi |
|---|-------|-----------|-----------|----------|
| 0 | ❌ | 3*2 = 6 | 6 > 6 ❌ | lanjut |
| 1 | ✅ | — | — | skip |
| 2 | ❌ | 1*2 = 2 | 2 > 6 ❌ | lanjut |
| 3 | ❌ | 0*2 = 0 | 0 > 6 ❌ | lanjut |

Tidak ada yang melanggar syarat.

**Output: `1` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,3,4]`

**Pass 1:** `max = 4`, `idx = 3`

**Pass 2:**
| i | Skip? | nums[i]*2 | > max(4)? | Aksi |
|---|-------|-----------|-----------|---------------|
| 0 | ❌ | 1*2 = 2 | 2 > 4 ❌ | lanjut |
| 1 | ❌ | 2*2 = 4 | 4 > 4 ❌ | lanjut |
| 2 | ❌ | 3*2 = 6 | 6 > 4 ✅ | return `-1` |

**Output: `-1` ✅**

______________________________________________________________________

**Input:** `nums = [0,0,3,2]`

**Pass 1:** `max = 3`, `idx = 2`

**Pass 2:**
| i | Skip? | nums[i]*2 | > max(3)? | Aksi |
|---|-------|-----------|-----------|--------|
| 0 | ❌ | 0*2 = 0 | 0 > 3 ❌ | lanjut |
| 1 | ❌ | 0*2 = 0 | 0 > 3 ❌ | lanjut |
| 2 | ✅ | — | — | skip |
| 3 | ❌ | 2*2 = 4 | 4 > 3 ✅ | return `-1` |

**Output: `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → Pass 2 tidak menemukan pelanggaran → return `0`
- [ ] Semua elemen sama → `nums[i] * 2 > max` → `max * 2 > max` selalu true (kecuali `max = 0`) → return `-1`
- [ ] Elemen terbesar di indeks `0` → `idx = 0`, Pass 2 skip indeks `0` dengan benar
- [ ] Mengandung `0` → `0 * 2 = 0`, tidak pernah melanggar syarat

______________________________________________________________________

## 📌 Key Takeaway

Dua pass yang bersih dan terpisah lebih mudah dibaca dan di-debug dibanding satu pass yang kompleks. Pass 1 murni mencari maksimum, Pass 2 murni melakukan validasi — masing-masing punya tanggung jawab tunggal yang jelas. Penggunaan `nums[i] * 2 > max` dibanding `nums[i] >= max / 2` adalah contoh kecil tapi penting tentang kehati-hatian dengan integer division dalam kondisi perbandingan. 🎯

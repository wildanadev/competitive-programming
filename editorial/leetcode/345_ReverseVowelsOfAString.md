# 345. Reverse Vowels of a String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Two Pointers, String
- **Link**: [Problem](https://leetcode.com/problems/reverse-vowels-of-a-string/)
- **Solution**: [Code](../../leetcode/ReverseVowelsOfAString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, balik (reverse) **hanya huruf vokalnya** saja. Huruf konsonan dan karakter lainnya tetap berada di posisi semula.

Vokal yang berlaku: `a, e, i, o, u` (huruf besar dan kecil).

Contoh:

- `"IceCreAm"` → `"AceCreIm"`
- `"leetcode"` → `"leotcede"`

______________________________________________________________________

## 💡 Intuition

Gunakan **Two Pointers** — satu dari kiri (`start`) dan satu dari kanan (`end`). Majukan `start` sampai menemukan vokal, mundurkan `end` sampai menemukan vokal, lalu swap keduanya. Ulangi hingga kedua pointer bertemu di tengah.

Pola ini identik dengan algoritma reverse string biasa, hanya saja pointer tidak maju/mundur satu per satu — melainkan **melompati non-vokal** hingga menemukan vokal berikutnya.

______________________________________________________________________

## 🔍 Approach

### Two Pointers

1. Konversi string `s` ke `char[]` agar bisa dimodifikasi.
1. Inisialisasi `start = 0`, `end = s.length() - 1`.
1. Selama `start < end`:
   - Geser `start` ke kanan selama bukan vokal.
   - Geser `end` ke kiri selama bukan vokal.
   - Swap `words[start]` dan `words[end]`.
   - Increment `start`, decrement `end`.
1. Konversi `char[]` kembali ke `String` dan return.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------- |
| **Time** | O(n) — setiap karakter dikunjungi maksimal sekali |
| **Space** | O(n) — `char[]` salinan dari string input |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `"leetcode"`

`words = ['l','e','e','t','c','o','d','e']`

Vokal di posisi: `1(e), 2(e), 5(o), 7(e)`

| Iterasi | start | end | words[start] | words[end] | Aksi | Array setelah swap |
| ------- | ------------------------------------------------------------------------- | --- | ------------ | ---------- | ---- | ------------------------------------------ |
| 1 | 1 | 7 | `e` | `e` | swap | `['l','e','e','t','c','o','d','e']` (sama) |
| 2 | 2 | 5 | `e` | `o` | swap | `['l','e','o','t','c','e','d','e']` |
| 3 | start=3 > end=4, tapi start lanjut ke 5, end ke 4 → `start >= end` → stop | | | | | |

Hasil akhir: `"leotcede"`

**Output: `"leotcede"` ✅**

______________________________________________________________________

**Input:** `"IceCreAm"`

`words = ['I','c','e','C','r','e','A','m']`

Vokal di posisi: `0(I), 2(e), 5(e), 6(A)`

| Iterasi | start | end | words[start] | words[end] | Aksi | Array setelah swap |
| ------- | ------------------------------------------------------- | --- | ------------ | ---------- | ---- | ------------------------------------------ |
| 1 | 0 | 6 | `I` | `A` | swap | `['A','c','e','C','r','e','I','m']` |
| 2 | 2 | 5 | `e` | `e` | swap | `['A','c','e','C','r','e','I','m']` (sama) |
| 3 | start=3 → geser ke 4 → 5, end=4 → `start >= end` → stop | | | | | |

Hasil akhir: `"AceCreIm"`

**Output: `"AceCreIm"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada vokal → `"bcdfg"` → return string asli tanpa perubahan
- [ ] Semua vokal → `"aeiou"` → reverse seluruhnya → `"uoiea"`
- [ ] Satu karakter → `"a"` → return `"a"`
- [ ] Vokal besar dan kecil → `"aA"` → swap → `"Aa"` (case-sensitive, posisi dibalik)
- [ ] String dengan satu vokal → `"hello"` → `start` dan `end` bertemu di vokal yang sama → swap dengan dirinya sendiri → tidak berubah

______________________________________________________________________

## 📌 Key Takeaway

Two Pointers adalah pola klasik untuk operasi **reverse in-place** pada array/string. Kuncinya di soal ini adalah inner `while` loop yang melompati non-vokal — pointer tidak bergerak satu langkah, melainkan langsung meloncat ke vokal berikutnya. Pola "skip until condition" ini sering muncul di berbagai variasi soal Two Pointers seperti _Remove Duplicates_, _Move Zeroes_, dan _Trapping Rain Water_. 🎯

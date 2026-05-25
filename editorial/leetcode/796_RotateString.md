# 796. Rotate String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, String Matching
- **Link**: [Problem](https://leetcode.com/problems/rotate-string/)
- **Solution**: [Code](../../leetcode/RotateString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `s` dan `goal`. Return `true` jika `s` bisa dirotasi menjadi `goal`.

Satu rotasi: pindahkan karakter paling kiri ke paling kanan.

Contoh:

- `s = "abcde"`, `goal = "cdeab"` → `true` (rotasi 2x: `abcde → bcdea → cdeab`)
- `s = "abcde"`, `goal = "abced"` → `false`

______________________________________________________________________

## 💡 Intuition

Semua kemungkinan rotasi `s` terkandung dalam `s + s`:

```
s = "abcde"
s+s = "abcdeabcde"

Rotasi 0: "abcde" → ada di posisi 0
Rotasi 1: "bcdea" → ada di posisi 1
Rotasi 2: "cdeab" → ada di posisi 2
Rotasi 3: "deabc" → ada di posisi 3
Rotasi 4: "eabcd" → ada di posisi 4
```

Jadi cukup cek apakah `goal` ada di dalam `s + s` — dan pastikan panjangnya sama (agar tidak ada substring yang lebih pendek ikut cocok).

______________________________________________________________________

## 🔍 Approach

### Double String + Contains

1. Jika `s.length() != goal.length()` → tidak mungkin rotasi → return `false`.
1. Buat `helper = s + s`.
1. Return `helper.contains(goal)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------- |
| **Time** | O(n²) — `contains` menggunakan string matching O(n²) worst case |
| **Space** | O(n) — `helper` berukuran `2n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abcde"`, `goal = "cdeab"`

```
s.length() == goal.length() → 5 == 5 ✅
helper = "abcde" + "abcde" = "abcdeabcde"
helper.contains("cdeab") ?

"abcdeabcde"
  ↑↑↑↑↑
  "cdeab" ditemukan di posisi 2 ✅

→ true
```

**Output: `true` ✅**

______________________________________________________________________

**Input:** `s = "abcde"`, `goal = "abced"`

```
helper = "abcdeabcde"
helper.contains("abced") ?

Cek setiap posisi:
posisi 0: "abcde" ≠ "abced"
posisi 1: "bcdea" ≠ "abced"
posisi 2: "cdeab" ≠ "abced"
posisi 3: "deabc" ≠ "abced"
posisi 4: "eabcd" ≠ "abced"
posisi 5: "abcde" ≠ "abced"
→ tidak ditemukan

→ false
```

**Output: `false` ✅**

______________________________________________________________________

**Input:** `s = "aa"`, `goal = "aa"`

```
helper = "aaaa"
helper.contains("aa") → ada di posisi 0 ✅

→ true
```

**Output: `true` ✅** — rotasi 0 (tidak dirotasi) tetap valid

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Panjang berbeda → langsung `false` sebelum proses
- [ ] `s == goal` → rotasi 0 → selalu `true`
- [ ] Satu karakter → `s + s` = dua karakter sama → `goal` pasti ada jika panjang sama
- [ ] Semua karakter sama `"aaa"` → semua rotasi identik → selalu `true`

______________________________________________________________________

## 🔧 Kenapa Perlu Cek `s.length() != goal.length()`?

Tanpa pengecekan ini, string yang lebih pendek dari `s` bisa ditemukan di `helper`:

```
s = "abcde", goal = "abc"
helper = "abcdeabcde"
helper.contains("abc") → true ❌ (salah! "abc" bukan rotasi dari "abcde")
```

Dengan pengecekan panjang, kita memastikan `goal` yang dicari **sama panjang** dengan `s` — sehingga jika ditemukan di `helper`, pasti merupakan rotasi yang valid.

______________________________________________________________________

## 🔧 Hubungan dengan _Repeated Substring Pattern_ (#459)

Kedua soal menggunakan trik **double string** yang sama:

| Soal | Trik | Yang Dicari |
| ------------------------- | ------------------------------- | ------------------------------- |
| Rotate String (#796) | `s + s` → `contains(goal)` | Apakah `goal` adalah rotasi `s` |
| Repeated Substring (#459) | `s + s` → strip → `contains(s)` | Apakah `s` punya pola berulang |

Kedua soal memanfaatkan fakta bahwa `s + s` mengandung semua kemungkinan "geseran" dari `s`.

______________________________________________________________________

## 📌 Key Takeaway

Trik **double string** (`s + s`) adalah teknik powerful untuk soal rotasi string — semua kemungkinan rotasi dari `s` terkandung di dalam `s + s` pada posisi `0` sampai `n-1`. Cukup satu panggilan `contains()` untuk mengecek semua `n` kemungkinan rotasi sekaligus, tanpa perlu loop eksplisit. Pengecekan panjang di awal adalah guard penting untuk mencegah false positive. 🎯

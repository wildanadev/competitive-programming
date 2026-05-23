# 459. Repeated Substring Pattern

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, String Matching
- **Link**: [Problem](https://leetcode.com/problems/repeated-substring-pattern/)
- **Solution**: [Code](../../leetcode/RepeatedSubstringPattern.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, return `true` jika `s` bisa dibentuk dengan mengulang sebuah substring minimal dua kali.

Contoh:

- `s = "abab"` → `true` (substring `"ab"` diulang 2x)
- `s = "aba"` → `false`
- `s = "abcabcabcabc"` → `true` (substring `"abc"` diulang 4x)

______________________________________________________________________

## 💡 Intuition

Ini adalah trik matematika yang elegan:

Jika `s` terbentuk dari pengulangan substring `p`, maka `s = p + p + ... + p`.

Sekarang buat `doubled = s + s`. Jika `s` adalah repeated pattern, maka di dalam `doubled` ada **setidaknya dua copy** penuh dari `s` — dan salah satunya dimulai bukan dari indeks `0` dan bukan dari indeks `n`.

Dengan menghapus karakter **pertama** dan **terakhir** dari `doubled` (→ `sub = doubled[1..2n-2]`), kita menghilangkan kemungkinan `s` ditemukan di posisi `0` dan posisi `n`. Jika `s` masih bisa ditemukan di `sub`, berarti `s` memang repeated pattern.

______________________________________________________________________

### Visualisasi

**`s = "abab"` (repeated):**

```
doubled = "abababab"
sub     = "bababab" (hapus 'a' pertama dan 'b' terakhir)
          ↑↑↑↑
sub.contains("abab") → "bababab" → ada di posisi 1 ✅
```

**`s = "aba"` (tidak repeated):**

```
doubled = "abaaba"
sub     = "baab" (hapus 'a' pertama dan 'a' terakhir)
sub.contains("aba") → "baab" → tidak ada ❌
```

______________________________________________________________________

## 🔍 Approach

### Double String + Substring Search

1. `doubled = s + s`
1. `sub = doubled.substring(1, doubled.length() - 1)` → hapus karakter pertama dan terakhir
1. Return `sub.contains(s)`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------- |
| **Time** | O(n²) — `contains` menggunakan string matching O(n²) worst case |
| **Space** | O(n) — `doubled` dan `sub` berukuran ~2n |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abab"`, `n = 4`

```
doubled = "abababab"   (length=8)
sub     = doubled.substring(1, 7) = "bababab"
sub.contains("abab") ?

"bababab"
 ↑ tidak cocok di sini
  ↑ cocok! "abab" ditemukan mulai indeks 1

→ true ✅
```

______________________________________________________________________

**Input:** `s = "aba"`, `n = 3`

```
doubled = "abaaba"   (length=6)
sub     = doubled.substring(1, 5) = "baab"
sub.contains("aba") ?

"baab" → tidak mengandung "aba"

→ false ✅
```

______________________________________________________________________

**Input:** `s = "abcabcabcabc"`, `n = 12`

```
doubled = "abcabcabcabcabcabcabcabc"   (length=24)
sub     = doubled.substring(1, 23) = "bcabcabcabcabcabcabcab"
sub.contains("abcabcabcabc") ?

"bcabcabcabcabcabcabcab"
  ↑ "abcabcabcabc" ada di posisi 2

→ true ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s` panjang 1 → tidak bisa repeated (butuh minimal 2 repetisi) → `false`
- [ ] `s` semua karakter sama `"aaaa"` → `true` (substring `"a"` diulang 4x)
- [ ] `s` panjang prima → hanya bisa repeated jika seluruh `s` = satu karakter diulang

______________________________________________________________________

## 🔧 Mengapa Hapus Karakter Pertama dan Terakhir?

```
doubled = s + s = "abababab"
                   ↑       ↑
                  s di sini (posisi 0) dan s di sini (posisi n=4)
```

Tanpa hapus, `doubled.contains(s)` selalu `true` (karena `s` pasti ada di posisi 0 dan n). Kita butuh membuktikan `s` ada di posisi **selain** 0 dan n.

Dengan menghapus karakter pertama:

- `s` di posisi 0 "dipotong" → tidak bisa ditemukan lagi dari posisi 0

Dengan menghapus karakter terakhir:

- `s` di posisi n "dipotong" → tidak bisa ditemukan lagi dari posisi n

Jika `s` masih ditemukan → pasti ada di posisi lain → `s` adalah repeated pattern.

______________________________________________________________________

```
doubled = "abababab"   posisi s: 0 dan 4
sub     = "bababab"    posisi s yang valid: 1,2,3 (selain 0 dan 4)

"bababab".contains("abab") → cek posisi 1 → ✅ found!
```

______________________________________________________________________

## 📌 Key Takeaway

Trik **double string + strip endpoints** adalah salah satu teknik string yang paling elegan — mengubah masalah "cari pola berulang" menjadi sekedar "cek substring". Ide dasarnya: jika `s` adalah repeated pattern, `s+s` pasti mengandung dua instance `s` yang **bertumpuk** di posisi di antara 0 dan n — dan itulah yang dicari setelah endpoints dihapus. 🎯

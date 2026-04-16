# 392. Is Subsequence

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/is-subsequence/)
- **Solution**: [Code](../../leetcode/easy/392_IsSubsequence.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` dan `t`, cek apakah `s` adalah **subsequence** dari `t`. Subsequence = karakter `s` muncul di `t` secara **berurutan** tapi tidak harus bersebelahan.

Contoh:

- `s = "ace", t = "abcde"` → `true` (a→c→e ada di t berurutan)
- `s = "aec", t = "abcde"` → `false` (e sebelum c di t)
- `s = "", t = "abcde"` → `true` (string kosong selalu subsequence)

______________________________________________________________________

## 💡 Intuition

Gunakan pointer `c` untuk track posisi karakter `s` yang sudah cocok. Loop `t` sambil cek apakah karakter sekarang cocok dengan `s[c]`. Kalau semua karakter `s` terpenuhi (`c == s.length`) berarti subsequence.

______________________________________________________________________

## 🔍 Approach

1. Kalau `s.length == 0` → return `true` langsung
1. Inisialisasi `c = 0`
1. Loop setiap karakter `i` di `t`:
   - Kalau `c >= s.length` → semua sudah cocok → break
   - Kalau `s[c] == i` → `c++`
1. Return `c == s.length`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------- |
| **Time** | O(n) — satu kali loop `t` |
| **Space** | O(n + m) — dua char array |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "ace", t = "abcde"`

**Init:** `c = 0, s1 = ['a','c','e'], t1 = ['a','b','c','d','e']`

| i | s1[c] | s1[c]==i? | c |
| --- | ----- | --------- | --- |
| 'a' | 'a' | ✅ | 1 |
| 'b' | 'c' | ❌ | 1 |
| 'c' | 'c' | ✅ | 2 |
| 'd' | 'e' | ❌ | 2 |
| 'e' | 'e' | ✅ | 3 |

`c=3 == s1.length=3` → **return `true` ✅**

______________________________________________________________________

**Input:** `s = "aec", t = "abcde"`

| i | s1[c] | s1[c]==i? | c |
| --- | ----- | --------- | --- |
| 'a' | 'a' | ✅ | 1 |
| 'b' | 'e' | ❌ | 1 |
| 'c' | 'e' | ❌ | 1 |
| 'd' | 'e' | ❌ | 1 |
| 'e' | 'e' | ✅ | 2 |

`c=2 != s1.length=3` → **return `false` ✅**

______________________________________________________________________

**Input:** `s = "", t = "abcde"`

`s.length == 0` → **return `true` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s` kosong → return `true`
- [ ] `t` kosong, `s` tidak → return `false`
- [ ] `s` lebih panjang dari `t` → tidak mungkin subsequence → return `false`
- [ ] `s == t` → return `true`

______________________________________________________________________

## 📌 Key Takeaway

Pola **pointer `c` sebagai tracker** — hanya maju kalau karakter cocok. Mirip dengan Ransom Note (#383) yang pakai pointer `c` untuk cocokkan dua array terurut, bedanya di sini tidak perlu sort karena urutan harus dipertahankan. Return `c == s.length` lebih bersih dari cek boolean terpisah. 🎯

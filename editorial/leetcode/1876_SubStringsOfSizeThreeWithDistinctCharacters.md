# 1876. Substrings of Size Three with Distinct Characters

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, String, Sliding Window, Counting
- **Link**: [Problem](https://leetcode.com/problems/substrings-of-size-three-with-distinct-characters/)
- **Solution**: [Code](../../leetcode/SubStringOfSizeThreeWithDistinctCharacters.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, hitung jumlah substring berukuran tepat **3** yang semua karakternya **berbeda** (distinct).

Substring seperti ini disebut **good**.

Contoh:

- `s = "xyzzaz"` → `1` (hanya `"xyz"` yang good)
- `s = "aababcabc"` → `4` (`"abc"`, `"bab"` tidak good... cek semua)
- `s = "abc"` → `1`

______________________________________________________________________

## 💡 Intuition

Karena ukuran window **tetap** (selalu 3), kita cukup periksa setiap substring panjang 3 dengan sliding window. Karena window hanya terdiri dari **3 karakter**, cukup bandingkan tiga pasang kemungkinan:

- `s[i] != s[i+1]`
- `s[i] != s[i+2]`
- `s[i+1] != s[i+2]`

Jika ketiganya berbeda → substring good → increment `ans`.

______________________________________________________________________

## 🔍 Approach

### Fixed Sliding Window (size 3)

1. Jika `s.length() < 3` → tidak ada substring panjang 3 → return `0`.
1. Loop `i` dari `0` sampai `s.length() - 3` (inklusif):
   - Cek apakah `s[i]`, `s[i+1]`, `s[i+2]` semua berbeda.
   - Jika ya → `ans++`.
1. Return `ans`.

> Batas atas loop `s.length() - 3 + 1` ekuivalen dengan `s.length() - 2` — memastikan indeks `i+2` tidak melebihi batas array.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------ |
| **Time** | O(n) — setiap posisi dikunjungi sekali, cek O(1) |
| **Space** | O(1) — hanya variabel counter |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "xyzzaz"`

| i | substring | s[i]!=s[i+1] | s[i]!=s[i+2] | s[i+1]!=s[i+2] | good? | ans |
| --- | --------- | ------------ | ------------ | -------------- | ----- | --- |
| 0 | `"xyz"` | x!=y ✅ | x!=z ✅ | y!=z ✅ | ✅ | 1 |
| 1 | `"yzz"` | y!=z ✅ | y!=z ✅ | z!=z ❌ | ❌ | 1 |
| 2 | `"zza"` | z!=z ❌ | — | — | ❌ | 1 |
| 3 | `"zaz"` | z!=a ✅ | z!=z ❌ | — | ❌ | 1 |

**Output: `1` ✅**

______________________________________________________________________

**Input:** `s = "aababcabc"`

| i | substring | Semua distinct? | ans |
| --- | --------- | ------------------- | --- |
| 0 | `"aab"` | a==a ❌ | 0 |
| 1 | `"aba"` | a==a ❌ | 0 |
| 2 | `"bab"` | b==b ❌ | 0 |
| 3 | `"aba"` | a==a ❌ | 0 |
| 4 | `"bca"` | b!=c, b!=a, c!=a ✅ | 1 |
| 5 | `"cab"` | c!=a, c!=b, a!=b ✅ | 2 |
| 6 | `"abc"` | a!=b, a!=c, b!=c ✅ | 3 |

**Output: `3` ✅**

______________________________________________________________________

**Input:** `s = "abc"`

| i | substring | Semua distinct? | ans |
| --- | --------- | --------------- | --- |
| 0 | `"abc"` | ✅ | 1 |

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s.length() < 3` → return `0` langsung
- [ ] Semua karakter sama → `"aaa"` → tidak ada yang good → `0`
- [ ] Semua substring good → `"abcdef"` → setiap window pasti distinct

______________________________________________________________________

## 📌 Key Takeaway

Fixed-size sliding window ukuran 3 adalah kasus paling sederhana — tidak perlu expand/shrink, cukup geser satu langkah per iterasi. Untuk window kecil dan tetap, pengecekan distinct langsung dengan perbandingan `!=` jauh lebih efisien dari HashSet. Pola ini bisa digeneralisasi ke ukuran `k` dengan HashSet atau frequency array untuk `k` yang lebih besar, seperti di soal _Longest Substring Without Repeating Characters_. 🎯

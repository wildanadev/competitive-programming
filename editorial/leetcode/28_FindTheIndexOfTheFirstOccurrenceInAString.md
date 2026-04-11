# 28. Find the Index of the First Occurrence in a String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/)
- **Solution**: [Code](../../leetcode/FindTheIndexOfTheFirstOccurrenceInAString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `haystack` dan `needle`, kembalikan index pertama kemunculan `needle` di dalam `haystack`. Kalau tidak ada, return `-1`.

Contoh:

- `haystack = "sadbutsad", needle = "sad"` → `0`
- `haystack = "leetcode", needle = "leeto"` → `-1`

______________________________________________________________________

## 💡 Intuition

Gunakan built-in method `indexOf()` Java yang mencari substring pertama dalam string. Method ini sudah dioptimasi secara internal oleh Java sehingga tidak perlu implementasi manual.

______________________________________________________________________

## 🔍 Approach

1. Return `haystack.indexOf(needle)` langsung
1. `indexOf()` return index pertama kemunculan `needle`, atau `-1` kalau tidak ditemukan

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------ |
| **Time** | O(n * m) — n panjang haystack, m panjang needle |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `haystack = "sadbutsad", needle = "sad"`

```
indexOf cari "sad" di "sadbutsad":
index 0: "sad" == "sad" ✅ → return 0
```

**return `0` ✅**

______________________________________________________________________

**Input:** `haystack = "leetcode", needle = "leeto"`

```
indexOf cari "leeto" di "leetcode":
index 0: "leetc" != "leeto" ❌
index 1: "eetco" != "leeto" ❌
index 2: "etcod" != "leeto" ❌
index 3: "tcode" != "leeto" ❌
tidak ditemukan → return -1
```

**return `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `needle` kosong `""` → `indexOf("")` return `0`
- [ ] `needle` lebih panjang dari `haystack` → return `-1`
- [ ] `needle` sama dengan `haystack` → return `0`
- [ ] `needle` muncul beberapa kali → return index **pertama**

______________________________________________________________________

## 📌 Key Takeaway

`indexOf()` adalah built-in Java yang sudah handle semua edge case secara internal. Di balik layar, Java menggunakan algoritma **brute force O(n\*m)** untuk string pendek. Untuk string sangat panjang, algoritma yang lebih optimal adalah **KMP (Knuth-Morris-Pratt)** yang O(n+m) atau **Boyer-Moore** yang O(n/m) rata-rata. 🎯

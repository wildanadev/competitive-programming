# 242. Valid Anagram

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Sorting, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/valid-anagram/)
- **Solution**: [Code](../../leetcode/ValidAnagram.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `s` dan `t`, cek apakah `t` adalah **anagram** dari `s`. Anagram = menggunakan karakter yang sama dengan frekuensi yang sama, hanya urutannya berbeda.

Contoh:

- `s = "anagram", t = "nagaram"` → `true`
- `s = "rat", t = "car"` → `false`

______________________________________________________________________

## 💡 Intuition

Kalau dua string adalah anagram, mereka akan **identik setelah di-sort**. Konversi ke char array, sort keduanya, lalu bandingkan.

______________________________________________________________________

## 🔍 Approach

1. Kalau `s.length() != t.length()` → langsung return `false`
1. Konversi `s` dan `t` ke char array
1. Sort kedua array
1. Return `Arrays.equals(sArr, tArr)`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(n log n) — karena sorting |
| **Space** | O(n) — dua char array |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "anagram", t = "nagaram"`

**Step 1 — Cek panjang:**

```
s.length() = 7 == t.length() = 7 ✅
```

**Step 2 — Convert & Sort:**

```
sArr = ['a','n','a','g','r','a','m'] → sort → ['a','a','a','g','m','n','r']
tArr = ['n','a','g','a','r','a','m'] → sort → ['a','a','a','g','m','n','r']
```

**Step 3 — Arrays.equals:**

```
['a','a','a','g','m','n','r'] == ['a','a','a','g','m','n','r'] ✅
```

**return `true` ✅**

______________________________________________________________________

**Input:** `s = "rat", t = "car"`

**Step 1 — Cek panjang:**

```
s.length() = 3 == t.length() = 3 ✅
```

**Step 2 — Convert & Sort:**

```
sArr = ['r','a','t'] → sort → ['a','r','t']
tArr = ['c','a','r'] → sort → ['a','c','r']
```

**Step 3 — Arrays.equals:**

```
['a','r','t'] != ['a','c','r'] ❌
```

**return `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Panjang berbeda → return `false` langsung tanpa sort
- [ ] String kosong → `"" == ""` → return `true`
- [ ] Satu karakter → `"a"` vs `"a"` → `true`, `"a"` vs `"b"` → `false`

______________________________________________________________________

## 📌 Key Takeaway

Cek panjang **di awal** sebelum sort adalah optimasi penting — kalau panjang berbeda pasti bukan anagram, tidak perlu proses lebih lanjut. Pendekatan sorting ini O(n log n), tapi bisa dioptimasi ke **O(n)** dengan array frekuensi `int[26]` — hitung frekuensi `s`, kurangi frekuensi `t`, kalau semua nol berarti anagram. 🎯

# 383. Ransom Note

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Hash Table, Sorting
- **Link**: [Problem](https://leetcode.com/problems/ransom-note/)
- **Solution**: [Code](../../leetcode/RansomNote.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `ransomNote` dan `magazine`. Cek apakah `ransomNote` bisa dibentuk menggunakan karakter dari `magazine`. Setiap karakter di `magazine` hanya boleh dipakai **sekali**.

Contoh:

- `ransomNote = "a", magazine = "b"` → `false`
- `ransomNote = "aa", magazine = "ab"` → `false`
- `ransomNote = "aa", magazine = "aab"` → `true`

______________________________________________________________________

## 💡 Intuition

Sort kedua array karakter. Lalu loop `magazine` sambil cocokkan satu-satu dengan karakter `ransomNote` yang sudah di-sort. Karena keduanya terurut, karakter yang sama pasti berdekatan sehingga bisa dicocokkan secara sequential.

______________________________________________________________________

## 🔍 Approach

1. Kalau `ransomNote.length > magazine.length` → langsung return `false`
1. Convert keduanya ke char array, lalu sort
1. Loop setiap karakter `i` di `y` (magazine):
   - Kalau `c == x.length` → semua karakter ransomNote sudah terpenuhi → break
   - Kalau `i == x[c]` → karakter cocok → `c++`
1. Return `c == x.length`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------ |
| **Time** | O(m log m + n log n) — sorting kedua array |
| **Space** | O(n + m) — dua char array |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `ransomNote = "aa", magazine = "aab"`

**Step 1 — Sort:**

```
x = ['a','a']  (sudah terurut)
y = ['a','a','b']  (sudah terurut)
```

**Step 2 — Loop y:**

| i | x[c] | i==x[c]? | c |
| --- | ------------------- | -------- | --- |
| 'a' | x[0]='a' | ✅ | 1 |
| 'a' | x[1]='a' | ✅ | 2 |
| 'b' | c==x.length → break | - | 2 |

`c == x.length` → **return `true` ✅**

______________________________________________________________________

**Input:** `ransomNote = "aa", magazine = "ab"`

**Step 1 — Sort:**

```
x = ['a','a']
y = ['a','b']
```

**Step 2 — Loop y:**

| i | x[c] | i==x[c]? | c |
| --- | -------- | -------- | --- |
| 'a' | x[0]='a' | ✅ | 1 |
| 'b' | x[1]='a' | ❌ | 1 |

`c=1 != x.length=2` → **return `false` ✅**

______________________________________________________________________

**Input:** `ransomNote = "aa", magazine = "a"`

**Step 1 — Cek panjang:**

```
x.length=2 > y.length=1 → return false ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `ransomNote` lebih panjang dari `magazine` → return `false` langsung
- [ ] `ransomNote` kosong → `c == x.length = 0` → return `true`
- [ ] Semua karakter sama → cek frekuensi mencukupi

______________________________________________________________________

## 📌 Key Takeaway

Pendekatan **sort + two pointer** ini O(m log m + n log n). Bisa dioptimasi ke **O(n + m)** pakai array frekuensi `int[26]` — hitung frekuensi `magazine`, kurangi per karakter `ransomNote`, kalau ada yang negatif berarti `false`. Cek panjang di awal sebagai early return yang efisien. 🎯

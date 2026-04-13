# 387. First Unique Character in a String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/first-unique-character-in-a-string/)
- **Solution**: [Code](../../leetcode/FirstUniqueCharacterInAString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, kembalikan index **karakter pertama yang tidak berulang**. Kalau tidak ada, return `-1`.

Contoh:

- `s = "leetcode"` → `0` (l muncul sekali)
- `s = "loveleetcode"` → `2` (v muncul sekali)
- `s = "aabb"` → `-1`

______________________________________________________________________

## 💡 Intuition

Dua kali loop:

1. **Loop pertama** → hitung frekuensi semua karakter pakai array `int[26]`
1. **Loop kedua** → cari index pertama yang frekuensinya `1`

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `un = int[26]`
1. Loop setiap karakter → `un[ch - 'a']++`
1. Loop index `i` dari `0` sampai `s.length()`:
   - Kalau `un[s.charAt(i) - 'a'] == 1` → return `i`
1. Return `-1`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------- |
| **Time** | O(n) — dua kali loop linear |
| **Space** | O(1) — array ukuran fixed 26 |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "leetcode"`

**Loop 1 — Hitung frekuensi:**

| Karakter | Index (ch-'a') | Frekuensi |
| -------- | -------------- | --------- |
| l | 11 | 1 |
| e | 4 | 3 |
| t | 19 | 1 |
| c | 2 | 1 |
| o | 14 | 1 |
| d | 3 | 1 |

```
un = [0,0,1,1,3,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0]
          c  d  e           l        o           t
```

**Loop 2 — Cari index pertama frekuensi 1:**

| i | s[i] | un\[s[i]-'a'\] | == 1? |
| --- | ---- | ------------ | ----------------- |
| 0 | 'l' | 1 | ✅ → **return 0** |

**return `0` ✅**

______________________________________________________________________

**Input:** `s = "aabb"`

**Loop 1 — Hitung frekuensi:**

```
un['a'-'a'] = 2
un['b'-'a'] = 2
```

**Loop 2:**

| i | s[i] | un\[s[i]-'a'\] | == 1? |
| --- | ---- | ------------ | ----- |
| 0 | 'a' | 2 | ❌ |
| 1 | 'a' | 2 | ❌ |
| 2 | 'b' | 2 | ❌ |
| 3 | 'b' | 2 | ❌ |

**return `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua karakter sama `"aaaa"` → return `-1`
- [ ] Satu karakter `"z"` → return `0`
- [ ] Karakter unik hanya di akhir `"aabbc"` → return `4`

______________________________________________________________________

## 📌 Key Takeaway

Pola **two-pass frequency count** — pass pertama hitung semua frekuensi, pass kedua cari yang pertama. Urutan loop kedua penting karena harus cari **index terkecil** bukan karakter terkecil. Array `int[26]` dipilih karena O(1) space dan lebih cepat dari HashMap untuk karakter alfabet. 🎯

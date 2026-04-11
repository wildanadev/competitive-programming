# 3884. First Matching Character From Both Ends

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/first-matching-character-from-both-ends/)
- **Solution**: [Code](../../leetcode/FirstMatchingCharacterFromBothEnds.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, kembalikan index terkecil `i` dimana `s[i] == s[n - i - 1]`. Jika tidak ada, return `-1`.

Contoh:

- `s = "abcacbd"` → `1` (s[1]='b' == s[5]='b')
- `s = "abcde"` → `-1` (tidak ada yang cocok)

______________________________________________________________________

## 💡 Intuition

Gunakan **Two Pointers** dari kedua ujung string — pointer `i` dari kiri dan pointer `n` dari kanan. Gerak ke tengah sambil cek apakah karakter di kedua pointer sama. Karena ingin index terkecil, pointer kiri (`i`) yang dikembalikan saat ketemu.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `i = 0`, `n = s.length() - 1`
1. Loop selama `i <= n`:
   - Kalau `s.charAt(i) == s.charAt(n)` → return `i`
   - Kalau tidak → `i++`, `n--` (gerak ke tengah)
1. Return `-1` jika tidak ada yang cocok

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------- |
| **Time** | O(n) — paling banyak n/2 iterasi |
| **Space** | O(1) — hanya dua pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abcacbd"`

`n = s.length() - 1 = 6`

| i | n | s[i] | s[n] | Match? | Aksi |
| --- | --- | ---- | ---- | ------ | ------------ |
| 0 | 6 | 'a' | 'd' | ❌ | i++, n-- |
| 1 | 5 | 'b' | 'b' | ✅ | **return 1** |

**return `1` ✅**

______________________________________________________________________

**Input:** `s = "abcde"`

`n = 4`

| i | n | s[i] | s[n] | Match? | Aksi |
| --- | --- | ---- | ---- | ------ | ------------ |
| 0 | 4 | 'a' | 'e' | ❌ | i++, n-- |
| 1 | 3 | 'b' | 'd' | ❌ | i++, n-- |
| 2 | 2 | 'c' | 'c' | ✅ | **return 2** |

Eh tapi tunggu — `i == n` berarti karakter yang sama dengan dirinya sendiri (tengah string). Ini valid karena `s[2] == s[4-2] = s[2]`. **return `2` ✅**

______________________________________________________________________

**Input:** `s = "abc"`

`n = 2`

| i | n | s[i] | s[n] | Match? | Aksi |
| --- | --- | ---- | ---- | ------ | ------------ |
| 0 | 2 | 'a' | 'c' | ❌ | i++, n-- |
| 1 | 1 | 'b' | 'b' | ✅ | **return 1** |

**return `1` ✅** (s[1]='b' == s[3-1-1]=s[1]='b')

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String panjang ganjil → elemen tengah dibandingkan dengan dirinya sendiri → selalu match
- [ ] Semua karakter berbeda → return `-1`
- [ ] String satu karakter → return `0`

______________________________________________________________________

## 📌 Key Takeaway

**Two Pointers dari kedua ujung** adalah teknik yang sangat efisien untuk soal yang melibatkan perbandingan elemen dari kiri dan kanan secara bersamaan. Pointer kiri (`i`) yang dikembalikan karena soal meminta **index terkecil**. 🎯

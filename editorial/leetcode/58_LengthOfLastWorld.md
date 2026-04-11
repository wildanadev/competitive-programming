# 58. Length of Last Word

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/length-of-last-word/)
- **Solution**: [Code](../../leetcode/LengthOfLastWorld.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` berisi kata-kata dan spasi, kembalikan panjang kata terakhir. Kata adalah substring yang hanya berisi karakter non-spasi.

Contoh:

- `s = "Hello World"` → `5`
- `s = "   fly me   to   the moon  "` → `4`
- `s = "luffy is still joyboy"` → `6`

______________________________________________________________________

## 💡 Intuition

Loop dari **belakang** — skip spasi di akhir dulu, lalu hitung karakter sampai ketemu spasi lagi. Karena kita butuh kata **terakhir**, loop dari belakang lebih efisien dari depan.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `ans = 0`
1. Loop `i` dari `s.length()-1` sampai `0`:
   - Kalau `s[i] == ' '` dan `ans > 0` → kata terakhir sudah dihitung → `break`
   - Kalau `s[i] != ' '` → `ans++`
1. Return `ans`

______________________________________________________________________

## Kenapa `ans > 0` Sebelum Break?

```
s = "   fly me   to   the moon  "
                              ^^
                          spasi trailing

Kalau langsung break saat ketemu spasi:
→ break sebelum hitung kata apapun ❌

Dengan cek ans > 0:
→ skip spasi trailing dulu
→ baru break saat spasi setelah kata ditemukan ✅
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — paling buruk loop seluruh string |
| **Space** | O(1) — hanya satu variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "Hello World"`

**Init:** `ans = 0`

| i | s[i] | s[i]==' ' && ans>0? | s[i]!=' '? | ans |
| --- | ---- | ------------------- | ---------- | --------- |
| 10 | d | ❌ | ✅ | 1 |
| 9 | l | ❌ | ✅ | 2 |
| 8 | r | ❌ | ✅ | 3 |
| 7 | o | ❌ | ✅ | 4 |
| 6 | W | ❌ | ✅ | 5 |
| 5 | ' ' | ✅ ans=5>0 | ❌ | **break** |

**return `5` ✅**

______________________________________________________________________

**Input:** `s = "   fly me   to   the moon  "`

**Init:** `ans = 0`

| i | s[i] | s[i]==' ' && ans>0? | s[i]!=' '? | ans |
| --- | ---- | ------------------- | ---------- | --------- |
| 27 | ' ' | ans=0 ❌ | ❌ | 0 |
| 26 | ' ' | ans=0 ❌ | ❌ | 0 |
| 25 | n | ❌ | ✅ | 1 |
| 24 | o | ❌ | ✅ | 2 |
| 23 | o | ❌ | ✅ | 3 |
| 22 | m | ❌ | ✅ | 4 |
| 21 | ' ' | ✅ ans=4>0 | ❌ | **break** |

**return `4` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Trailing spaces `"hello   "` → skip spasi, return `5`
- [ ] Satu kata `"hello"` → return `5`
- [ ] Banyak spasi antar kata `"a   b"` → return `1`
- [ ] Satu karakter `"a"` → return `1`

______________________________________________________________________

## 📌 Key Takeaway

Loop dari belakang + kondisi `ans > 0` sebelum break adalah kunci solusi ini — memastikan trailing spaces dilewati sebelum mulai hitung, dan berhenti tepat setelah kata terakhir selesai dihitung tanpa perlu proses seluruh string. 🎯

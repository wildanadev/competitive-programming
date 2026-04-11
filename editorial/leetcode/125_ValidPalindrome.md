# 125. Valid Palindrome

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/valid-palindrome/)
- **Solution**: [Code](../../leetcode/ValidPalindrome.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, cek apakah string tersebut **palindrome** setelah mengabaikan karakter non-alphanumeric dan case. Palindrome = dibaca sama dari depan dan belakang.

Contoh:

- `s = "A man, a plan, a canal: Panama"` → `true`
- `s = "race a car"` → `false`
- `s = " "` → `true`

______________________________________________________________________

## 💡 Intuition

1. **Normalisasi** string dulu — lowercase semua huruf, hapus karakter non-alphanumeric
1. Cek palindrome pakai **Two Pointers** dari kedua ujung

______________________________________________________________________

## 🔍 Approach

1. Normalisasi: `s = s.toLowerCase().replaceAll("[^a-z0-9]", "")`
1. Inisialisasi `left = 0`, `right = s.length() - 1`
1. Loop selama `left < right`:
   - Kalau `s[left] != s[right]` → return `false`
   - `left++`, `right--`
1. Return `true`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------ |
| **Time** | O(n) — normalisasi + loop |
| **Space** | O(n) — string baru hasil normalisasi |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "A man, a plan, a canal: Panama"`

**Step 1 — Normalisasi:**

```
toLowerCase  → "a man, a plan, a canal: panama"
replaceAll   → "amanaplanacanalpanama"
```

**Step 2 — Two Pointers:**

```
"amanaplanacanalpanama"
 ^                   ^
 l                   r
```

| left | right | s[left] | s[right] | Match? |
| ---- | ----- | ------- | -------- | ------ |
| 0 | 19 | a | a | ✅ |
| 1 | 18 | m | m | ✅ |
| 2 | 17 | a | a | ✅ |
| 3 | 16 | n | n | ✅ |
| 4 | 15 | a | a | ✅ |
| 5 | 14 | p | p | ✅ |
| 6 | 13 | l | l | ✅ |
| 7 | 12 | a | a | ✅ |
| 8 | 11 | n | n | ✅ |
| 9 | 10 | a | a | ✅ |

`left=10 >= right=9` → stop

**return `true` ✅**

______________________________________________________________________

**Input:** `s = "race a car"`

**Step 1 — Normalisasi:**

```
toLowerCase → "race a car"
replaceAll  → "raceacar"
```

**Step 2 — Two Pointers:**

| left | right | s[left] | s[right] | Match? |
| ---- | ----- | ------- | -------- | --------------------- |
| 0 | 7 | r | r | ✅ |
| 1 | 6 | a | a | ✅ |
| 2 | 5 | c | c | ✅ |
| 3 | 4 | e | a | ❌ → **return false** |

**return `false` ✅**

______________________________________________________________________

**Input:** `s = " "`

**Step 1 — Normalisasi:**

```
replaceAll → ""  (string kosong)
```

**Step 2:** `left=0, right=-1` → `left < right` tidak terpenuhi → langsung return `true` ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String kosong atau hanya spasi → normalisasi jadi `""` → return `true`
- [ ] Satu karakter → `left == right` → tidak masuk loop → return `true`
- [ ] Semua non-alphanumeric → normalisasi jadi `""` → return `true`
- [ ] Mixed case `"raceCAR"` → toLowerCase handle

______________________________________________________________________

## 📌 Key Takeaway

Dua langkah utama: **normalisasi dulu, baru cek palindrome**. Normalisasi dengan `toLowerCase()` + `replaceAll("[^a-z0-9]", "")` membuat pengecekan jauh lebih simpel karena tidak perlu handle case dan karakter spesial di dalam loop. Two Pointers `left < right` (bukan `<=`) karena elemen tengah tidak perlu dibandingkan dengan dirinya sendiri. 🎯

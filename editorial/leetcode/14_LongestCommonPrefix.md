# 14. Longest Common Prefix

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Sorting
- **Link**: [Problem](https://leetcode.com/problems/longest-common-prefix/)
- **Solution**: [Code](../../leetcode/LongestCommonPrefix.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array string `strs`, kembalikan prefix terpanjang yang sama di antara semua string. Jika tidak ada, return `""`.

Contoh:

- `strs = ["flower","flow","flight"]` → `"fl"`
- `strs = ["dog","racecar","car"]` → `""`

______________________________________________________________________

## 💡 Intuition

Setelah array di-sort secara leksikografis, string pertama dan terakhir adalah yang **paling berbeda**. Jadi cukup bandingkan karakter string **pertama** dengan semua string lainnya — kalau karakter di index `i` sudah berbeda, langsung return prefix yang terkumpul.

______________________________________________________________________

## 🔍 Approach

1. Sort array `strs` secara leksikografis dengan `Arrays.sort()`
1. Loop karakter di `strs[0]` (string terpendek setelah sort):
   - Bandingkan karakter `c = strs[0].charAt(i)` dengan semua string lain di index `i`
   - Kalau ada yang berbeda → return `ans`
   - Kalau semua sama → tambahkan `c` ke `ans`
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------- |
| **Time** | O(n log n + m) — sorting + loop karakter |
| **Space** | O(1) — tidak pakai struktur tambahan |

> `n` = jumlah string, `m` = panjang string terpendek

______________________________________________________________________

## 🧪 Dry Run

**Input:** `strs = ["flower", "flow", "flight"]`

**Step 1 — Sort:**

```
["flight", "flow", "flower"]
strs[0] = "flight"
```

**Step 2 — Loop karakter strs\[0\]:**

| i | c | strs[1][i] | strs[2][i] | Semua sama? | ans |
| --- | --- | ---------- | ---------- | ----------- | --------------- |
| 0 | 'f' | 'f' ✅ | 'f' ✅ | ✅ | "f" |
| 1 | 'l' | 'l' ✅ | 'l' ✅ | ✅ | "fl" |
| 2 | 'i' | 'o' ❌ | - | ❌ | **return "fl"** |

**return `"fl"` ✅**

______________________________________________________________________

**Input:** `strs = ["dog", "racecar", "car"]`

**Step 1 — Sort:**

```
["car", "dog", "racecar"]
strs[0] = "car"
```

**Step 2 — Loop karakter strs\[0\]:**

| i | c | strs[1][i] | Sama? | ans |
| --- | --- | ---------- | ----- | ------------- |
| 0 | 'c' | 'd' ❌ | ❌ | **return ""** |

**return `""` ✅**

______________________________________________________________________

**Input:** `strs = ["flower", "flower", "flower"]`

**Step 1 — Sort:**

```
["flower", "flower", "flower"]
```

**Step 2 — Loop semua karakter → semua sama:**

**return `"flower"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array hanya satu string → return string itu sendiri
- [ ] Tidak ada prefix yang sama → return `""`
- [ ] Semua string sama → return string itu sendiri
- [ ] Ada string kosong `""` → return `""`

______________________________________________________________________

## 📌 Key Takeaway

Trick utama solusi ini adalah **sort dulu** — setelah sort leksikografis, string pertama (`strs[0]`) menjadi kandidat prefix karena secara alfabet paling "kecil". Cukup bandingkan `strs[0]` dengan semua string lain, tidak perlu bandingkan semua pasangan. Ini yang membuat solusinya lebih efisien dari brute force. 🎯

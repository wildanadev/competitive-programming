# 13. Roman to Integer (Approach 2 - HashMap Lookahead)

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Hash Table, Math
- **Link**: [Problem](https://leetcode.com/problems/roman-to-integer/)
- **Solution**: [Code](../../leetcode/RomanToInteger.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string angka romawi `s`, konversi ke integer.

```
I=1, V=5, X=10, L=50, C=100, D=500, M=1000
```

Kasus subtraktif:

```
IV=4, IX=9, XL=40, XC=90, CD=400, CM=900
```

______________________________________________________________________

## 💡 Intuition

Dalam angka romawi, kalau karakter sekarang **lebih kecil** dari karakter **berikutnya** → kasus subtraktif → **kurangi** nilainya. Kalau sebaliknya → **tambahkan**. Karakter terakhir selalu ditambahkan.

______________________________________________________________________

## 🔍 Approach

1. Buat HashMap `roman` berisi mapping karakter → nilai
1. Loop `i` dari `0` sampai `s.length() - 1` (tidak termasuk terakhir):
   - Kalau `roman[s[i]] < roman[s[i+1]]` → **kurangi** `roman[s[i]]`
   - Kalau sebaliknya → **tambah** `roman[s[i]]`
1. Tambahkan karakter **terakhir** manual di luar loop
1. Return `res`

______________________________________________________________________

## Kenapa Karakter Terakhir Di Luar Loop?

```
Loop berhenti satu sebelum index terakhir supaya s.charAt(i+1) tidak out of bounds.
Karakter terakhir (s.length()-1) ditambahkan manual di luar loop.
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — HashMap ukuran fixed (7 entry) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "MCMXCIV"`

**HashMap:** `{I:1, V:5, X:10, L:50, C:100, D:500, M:1000}`

**Init:** `res = 0`

| i | s[i] | s[i+1] | roman[i] < roman[i+1]? | Aksi | res |
| --- | ---- | ------ | ---------------------- | ----------- | ---- |
| 0 | M | C | 1000 < 100? ❌ | res += 1000 | 1000 |
| 1 | C | M | 100 < 1000? ✅ | res -= 100 | 900 |
| 2 | M | X | 1000 < 10? ❌ | res += 1000 | 1900 |
| 3 | X | C | 10 < 100? ✅ | res -= 10 | 1890 |
| 4 | C | I | 100 < 1? ❌ | res += 100 | 1990 |
| 5 | I | V | 1 < 5? ✅ | res -= 1 | 1989 |

**Tambah karakter terakhir:** `res += roman['V'] = 5`

**return `1989 + 5 = 1994` ✅**

______________________________________________________________________

**Input:** `s = "LVIII"`

| i | s[i] | s[i+1] | roman[i] < roman[i+1]? | Aksi | res |
| --- | ---- | ------ | ---------------------- | --------- | --- |
| 0 | L | V | 50 < 5? ❌ | res += 50 | 50 |
| 1 | V | I | 5 < 1? ❌ | res += 5 | 55 |
| 2 | I | I | 1 < 1? ❌ | res += 1 | 56 |
| 3 | I | I | 1 < 1? ❌ | res += 1 | 57 |

**Tambah karakter terakhir:** `res += roman['I'] = 1`

**return `57 + 1 = 58` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String satu karakter → loop tidak jalan, langsung return nilai karakter itu
- [ ] Semua subtraktif → `"CMXCIX"` = 999
- [ ] Semua aditif → `"VIII"` = 8

______________________________________________________________________

## 📌 Perbandingan dengan Approach 1

| | Approach 1 (track `before`) | Approach 2 (lookahead) |
| --------------- | ------------------------------------- | ------------------------------------- |
| **Cara** | Bandingkan dengan karakter sebelumnya | Bandingkan dengan karakter berikutnya |
| **Koreksi** | Kurangi `-2`, `-20`, `-200` | Langsung kurangi nilai karakter |
| **Readability** | Lebih verbose | Lebih simpel & intuitif |
| **Edge case** | Karakter pertama di-handle terpisah | Karakter terakhir di-handle terpisah |

______________________________________________________________________

## 📌 Key Takeaway

**Lookahead** (`s[i+1]`) adalah cara paling intuitif untuk deteksi kasus subtraktif — kalau nilai sekarang lebih kecil dari berikutnya berarti harus dikurangi. Karakter terakhir selalu ditambahkan di luar loop karena tidak punya "next" untuk dibandingkan. Approach ini lebih **clean dan mudah dibaca** dibanding Approach 1. 🎯

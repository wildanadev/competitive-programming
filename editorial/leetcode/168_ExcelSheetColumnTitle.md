# 168. Excel Sheet Column Title

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String
- **Link**: [Problem](https://leetcode.com/problems/excel-sheet-column-title/)
- **Solution**: [Code](../../leetcode/ExcelSheetColumnTitle.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `columnNumber`, kembalikan judul kolom Excel yang bersesuaian.

```
1 → "A"
2 → "B"
...
26 → "Z"
27 → "AA"
28 → "AB"
...
```

______________________________________________________________________

## 💡 Intuition

Mirip konversi **base-10 ke base-26**, tapi Excel menggunakan **1-indexed** (tidak ada 0). Huruf A=1, B=2, ..., Z=26.

Trick utama: **kurangi 1 dulu** sebelum modulo — ini menggeser range dari `[1..26]` ke `[0..25]` sehingga bisa di-map ke index huruf dengan benar.

```
Tanpa -1:  26 % 26 = 0  → tidak ada 'A'+0 = 'A' yang benar untuk Z
Dengan -1: (26-1) % 26 = 25 → 'A'+25 = 'Z' ✅
```

______________________________________________________________________

## 🔍 Approach

1. Loop selama `columnNumber > 0`:
   - `columnNumber--` → shift ke 0-indexed
   - `temp += (char)(columnNumber % 26 + 'A')` → ambil karakter
   - `columnNumber /= 26` → kurangi satu digit
1. Reverse `temp` → return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------- |
| **Time** | O(log₂₆ n) — jumlah digit base-26 |
| **Space** | O(log₂₆ n) — panjang string hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `columnNumber = 1`

| Step | columnNumber | columnNumber-- | % 26 | char | temp |
| ---- | ------------ | -------------- | ---- | --------- | ---- |
| 1 | 1 | 0 | 0 | 'A'+0='A' | "A" |
| - | 0/26=0 | stop | - | - | - |

**Reverse:** `"A"` → `"A"`

**return `"A"` ✅**

______________________________________________________________________

**Input:** `columnNumber = 26`

| Step | columnNumber | columnNumber-- | % 26 | char | temp |
| ---- | ------------ | -------------- | ---- | ---------- | ---- |
| 1 | 26 | 25 | 25 | 'A'+25='Z' | "Z" |
| - | 25/26=0 | stop | - | - | - |

**Reverse:** `"Z"` → `"Z"`

**return `"Z"` ✅**

______________________________________________________________________

**Input:** `columnNumber = 27`

| Step | columnNumber | columnNumber-- | % 26 | char | temp |
| ---- | ------------ | -------------- | ---- | --------- | ---- |
| 1 | 27 | 26 | 0 | 'A'+0='A' | "A" |
| 2 | 26/26=1 | 0 | 0 | 'A'+0='A' | "AA" |
| - | 0/26=0 | stop | - | - | - |

**Reverse:** `"AA"` → `"AA"`

**return `"AA"` ✅**

______________________________________________________________________

**Input:** `columnNumber = 701`

| Step | columnNumber | columnNumber-- | % 26 | char | temp |
| ---- | ------------ | -------------- | ---- | ---------- | ---- |
| 1 | 701 | 700 | 24 | 'A'+24='Y' | "Y" |
| 2 | 700/26=26 | 25 | 25 | 'A'+25='Z' | "YZ" |
| - | 25/26=0 | stop | - | - | - |

**Reverse:** `"YZ"` → `"ZY"`

**return `"ZY"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `columnNumber = 1` → `"A"`
- [ ] `columnNumber = 26` → `"Z"` (bukan `"Z0"`)
- [ ] `columnNumber = 27` → `"AA"`

______________________________________________________________________

## 📌 Key Takeaway

**Kurangi 1 sebelum modulo** adalah trick kunci — mengubah 1-indexed Excel menjadi 0-indexed supaya bisa langsung di-map ke huruf. Tanpa `columnNumber--`, angka 26 akan menghasilkan karakter yang salah. Pola ini mirip konversi base-10 ke base lain, hanya perlu shift index karena tidak ada "digit nol" di sistem Excel. 🎯

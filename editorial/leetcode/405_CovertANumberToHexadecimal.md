# 405. Convert a Number to Hexadecimal

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/convert-a-number-to-hexadecimal/)
- **Solution**: [Code](../../leetcode/ConvertANumberToHexadecimal.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `num`, konversi ke representasi **hexadecimal** (lowercase). Untuk angka negatif, gunakan representasi **two's complement** 32-bit.

Contoh:

- `num = 26` → `"1a"`
- `num = -1` → `"ffffffff"`

______________________________________________________________________

## 💡 Intuition

Ada dua kasus:

1. **Positif** → langsung bagi `num` dengan 16 berulang kali, ambil sisa bagi
1. **Negatif** → konversi manual ke **two's complement** 32-bit:
   - Ambil nilai absolut
   - Konversi ke biner
   - **Flip semua bit** (NOT)
   - **Tambah 1** (two's complement)
   - Konversi 4 bit sekaligus ke hex

______________________________________________________________________

## 🔍 Approach

### HashMap Dictionary

```
{0→'0', 1→'1', ..., 9→'9', 10→'a', 11→'b', 12→'c', 13→'d', 14→'e', 15→'f'}
```

### Kasus Positif

```
num = 26
26 % 16 = 10 → 'a'
26 / 16 = 1
1  % 16 = 1  → '1'
1  / 16 = 0  → stop
ans = "a1" → reverse → "1a" ✅
```

### Kasus Negatif (Two's Complement)

```
num = -1
1. abs(-1) = 1 → biner: 00...0001
2. Flip:         11...1110
3. Tambah 1:     11...1111
4. Konversi per 4 bit → "ffffffff" ✅
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(1) — max 8 digit hex (32-bit integer) |
| **Space** | O(1) — array biner 32 bit |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `num = 26`

**Step 1 — Loop bagi 16:**

| num | num % 16 | dict | ans |
| --- | -------- | ---- | ---- |
| 26 | 10 | 'a' | "a" |
| 1 | 1 | '1' | "a1" |
| 0 | stop | - | - |

**Step 2 — Reverse:**

```
"a1" → "1a"
```

**return `"1a"` ✅**

______________________________________________________________________

**Input:** `num = -1`

**Step 1 — abs(-1) = 1 → biner:**

```
bit = [0,0,0,...,0,0,1]  (32 bit)
```

**Step 2 — Flip semua bit:**

```
bit = [1,1,1,...,1,1,0]
```

**Step 3 — Tambah 1:**

```
bit = [1,1,1,...,1,1,1]  (semua 1)
```

**Step 4 — Konversi per 4 bit dari kanan:**

```
1111 = 15 → 'f'
1111 = 15 → 'f'
... (8 kali)
ans = "ffffffff" (sudah terbalik saat dibangun dari kanan ke kiri)
```

**return `"ffffffff"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `num = 0` → return `"0"` langsung
- [ ] `num = -1` → semua bit 1 → `"ffffffff"`
- [ ] `num = Integer.MIN_VALUE` → `-2147483648` → `"80000000"`

______________________________________________________________________

## 📌 Key Takeaway

**Two's Complement** adalah cara representasi bilangan negatif di komputer:

```
1. Ambil nilai absolut → konversi ke biner
2. Flip semua bit (bitwise NOT)
3. Tambah 1
```

Solusi ini mengimplementasikan two's complement **secara manual**. Cara yang lebih simpel adalah pakai `num & 0xF` (bit masking) yang langsung ambil 4 bit terakhir tanpa perlu konversi biner manual. 🎯

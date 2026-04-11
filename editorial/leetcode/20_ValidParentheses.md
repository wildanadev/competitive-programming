# 20. Valid Parentheses

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Stack
- **Link**: [Problem](https://leetcode.com/problems/valid-parentheses/)
- **Solution**: [Code](../../leetcode/ValidParentheses.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` berisi karakter `(`, `)`, `{`, `}`, `[`, `]`. Cek apakah string valid. String valid jika:

- Setiap bracket buka punya pasangan bracket tutup yang sesuai
- Bracket ditutup dalam urutan yang benar

Contoh:

- `s = "()"` → `true`
- `s = "()[]{}"` → `true`
- `s = "(]"` → `false`
- `s = "([)]"` → `false`
- `s = "{[]}"` → `true`

______________________________________________________________________

## 💡 Intuition

Gunakan **Stack** — setiap bracket buka di-push ke stack. Setiap bracket tutup, cek apakah top stack adalah pasangannya. Kalau stack kosong di akhir → semua bracket cocok.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `stack = ArrayDeque`
1. Loop setiap karakter `c` di `s`:
   - Kalau bracket buka `( { [` → push ke stack
   - Kalau bracket tutup `) } ]`:
     - Kalau stack kosong → return `false`
     - Pop top stack, cek apakah pasangannya cocok
     - Kalau tidak cocok → return `false`
1. Return `stack.isEmpty()` → kalau masih ada sisa di stack berarti tidak valid

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(n) — stack menyimpan max n/2 elemen |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "{[]}"`

| c | Bracket buka? | Aksi | stack |
| --- | ------------- | --------------------- | ------- |
| { | ✅ | push '{' | [{] |
| \[ | ✅ | push '\[' | \[{, [] |
| \] | ❌ | pop '[', ']'=='\[' ✅ | [{] |
| } | ❌ | pop '{', '}'=='{' ✅ | [] |

`stack.isEmpty() = true` → **return `true` ✅**

______________________________________________________________________

**Input:** `s = "([)]"`

| c | Bracket buka? | Aksi | stack |
| --- | ------------- | ---------------------- | ------- |
| ( | ✅ | push '(' | [(] |
| \[ | ✅ | push '\[' | \[(, [] |
| ) | ❌ | pop '\[', ')'!='\[' ❌ | - |

**return `false` ✅**

______________________________________________________________________

**Input:** `s = "((("`

| c | Bracket buka? | Aksi | stack |
| --- | ------------- | -------- | --------- |
| ( | ✅ | push '(' | [(] |
| ( | ✅ | push '(' | [(, (] |
| ( | ✅ | push '(' | [(, (, (] |

`stack.isEmpty() = false` → **return `false` ✅**

______________________________________________________________________

**Input:** `s = ")"`

| c | Bracket buka? | Aksi | stack |
| --- | ------------- | ------------- | ----- |
| ) | ❌ | stack kosong! | [] |

**return `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String kosong → `stack.isEmpty() = true` → return `true`
- [ ] Hanya bracket tutup `")"` → stack kosong saat pop → return `false`
- [ ] Bracket buka semua `"((("` → stack tidak kosong di akhir → return `false`
- [ ] Nested bracket `"{[]}"` → tetap valid

______________________________________________________________________

## 📌 Key Takeaway

Dua kondisi penting yang harus dicek:

1. **Cek stack kosong sebelum pop** — kalau tidak, bracket tutup tanpa pasangan akan throw exception
1. **`return stack.isEmpty()`** — bukan `return true` karena kalau masih ada bracket buka yang tidak ditutup, stack tidak akan kosong

`ArrayDeque` dipilih karena lebih cepat dari `Stack` di Java. 🎯

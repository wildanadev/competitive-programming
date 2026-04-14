# 389. Find the Difference

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Bit Manipulation, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/find-the-difference/)
- **Solution**: [Code](../../leetcode/FindTheDifference.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` dan `t` dimana `t` dibuat dari karakter `s` yang di-shuffle lalu ditambah **satu karakter acak**. Temukan karakter tambahan tersebut.

Contoh:

- `s = "abcd", t = "abcde"` → `'e'`
- `s = "", t = "y"` → `'y'`

______________________________________________________________________

## 💡 Intuition

Gunakan **XOR** — XOR semua karakter di `s` dan `t` sekaligus. Semua karakter yang ada di keduanya akan **saling cancel** (`a ^ a = 0`), dan hanya karakter tambahan yang tersisa.

```
s = "abcd" → a^b^c^d
t = "abcde" → a^b^c^d^e

Gabung: a^b^c^d ^ a^b^c^d^e
       = (a^a)^(b^b)^(c^c)^(d^d)^e
       = 0^0^0^0^e
       = e ✅
```

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `ans = 0`
1. Loop semua karakter di `s` → `ans ^= i`
1. Loop semua karakter di `t` → `ans ^= i`
1. Return `(char) ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(n) — dua kali loop linear |
| **Space** | O(1) — hanya satu variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abcd", t = "abcde"`

**Init:** `ans = 0`

**Loop s:**

| i | ans ^= i | ans (decimal) |
| --- | -------- | ------------- |
| 'a' | 0 ^ 97 | 97 |
| 'b' | 97 ^ 98 | 3 |
| 'c' | 3 ^ 99 | 96 |
| 'd' | 96 ^ 100 | 4 |

**Loop t:**

| i | ans ^= i | ans (decimal) |
| --- | --------- | ------------- |
| 'a' | 4 ^ 97 | 101 |
| 'b' | 101 ^ 98 | 7 |
| 'c' | 7 ^ 99 | 100 |
| 'd' | 100 ^ 100 | 0 |
| 'e' | 0 ^ 101 | 101 |

`(char) 101 = 'e'`

**return `'e'` ✅**

______________________________________________________________________

**Input:** `s = "", t = "y"`

**Loop s:** tidak ada karakter, `ans = 0`

**Loop t:**

| i | ans ^= i | ans |
| --- | -------- | --- |
| 'y' | 0 ^ 121 | 121 |

`(char) 121 = 'y'`

**return `'y'` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s` kosong → return karakter satu-satunya di `t`
- [ ] Karakter tambahan sama dengan karakter lain di `t` → XOR tetap handle

______________________________________________________________________

## 📌 Key Takeaway

Teknik ini persis sama dengan **Single Number (#136)** dan **Missing Number (#268)** — XOR semua nilai dari dua sumber sekaligus, semua pasangan saling cancel, hanya yang tidak punya pasangan yang tersisa. Cast ke `(char)` di akhir karena XOR menghasilkan `int` tapi kita butuh `char`. 🎯

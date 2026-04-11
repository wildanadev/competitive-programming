# 136. Single Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/single-number/)
- **Solution**: [Code](../../leetcode/SingleNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dimana setiap elemen muncul **dua kali** kecuali satu elemen yang muncul **sekali**. Temukan elemen yang hanya muncul sekali.

Contoh:

- `nums = [2,2,1]` → `1`
- `nums = [4,1,2,1,2]` → `4`
- `nums = [1]` → `1`

______________________________________________________________________

## 💡 Intuition

Gunakan operasi **XOR (^)**. XOR punya dua sifat penting:

```
a ^ a = 0   → angka XOR dirinya sendiri = 0
a ^ 0 = a   → angka XOR 0 = angka itu sendiri
```

Karena semua elemen muncul dua kali kecuali satu, XOR semua elemen akan **menghilangkan pasangan** dan menyisakan elemen yang hanya muncul sekali.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `ans = 0`
1. Loop setiap elemen `i` di `nums`:
   - `ans ^= i`
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — hanya satu variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [4, 1, 2, 1, 2]`

**Init:** `ans = 0`

| i | ans ^= i | ans |
| --- | -------- | ----- |
| 4 | 0 ^ 4 | 4 |
| 1 | 4 ^ 1 | 5 |
| 2 | 5 ^ 2 | 7 |
| 1 | 7 ^ 1 | 6 |
| 2 | 6 ^ 2 | **4** |

**return `4` ✅**

______________________________________________________________________

## Kenapa Bisa?

```
0 ^ 4 ^ 1 ^ 2 ^ 1 ^ 2
= 4 ^ (1^1) ^ (2^2)   ← pasangan saling cancel
= 4 ^ 0 ^ 0
= 4 ✅
```

______________________________________________________________________

## XOR Truth Table

| A | B | A^B |
| --- | --- | --- |
| 0 | 0 | 0 |
| 0 | 1 | 1 |
| 1 | 0 | 1 |
| 1 | 1 | 0 |

> Bit yang **sama** → 0, bit yang **berbeda** → 1

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → return elemen itu sendiri
- [ ] Elemen tunggal di awal, tengah, atau akhir → XOR tetap handle

______________________________________________________________________

## 📌 Key Takeaway

Sifat XOR `a ^ a = 0` dan `a ^ 0 = a` membuat semua pasangan **saling cancel** dan hanya menyisakan elemen yang muncul sekali. Ini solusi O(1) space yang elegan — tanpa HashMap, tanpa sorting. Teknik XOR seperti ini sering muncul di soal **Bit Manipulation** lainnya. 🎯

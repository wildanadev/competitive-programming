# 268. Missing Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Bit Manipulation, Math, Hash Table, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/missing-number/)
- **Solution**: [Code](../../leetcode/MissingNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` berisi `n` angka berbeda dari range `[0, n]`, temukan satu angka yang hilang.

Contoh:

- `nums = [3,0,1]` → `2`
- `nums = [0,1]` → `2`
- `nums = [9,6,4,2,3,5,7,0,1]` → `8`

______________________________________________________________________

## 💡 Intuition

Gunakan sifat **XOR**:

```
a ^ a = 0   → angka XOR dirinya sendiri = 0
a ^ 0 = a   → angka XOR 0 = angka itu sendiri
```

XOR semua index `[0..n]` dengan semua elemen `nums`. Semua pasangan yang ada akan **saling cancel**, hanya angka yang hilang yang tersisa.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `ans = n` (index terakhir yang tidak ada di loop)
1. Loop `i` dari `0` sampai `n-1`:
   - `ans ^= i ^ nums[i]`
1. Return `ans`

______________________________________________________________________

## Kenapa `ans = n` di Awal?

Loop hanya jalan dari `i = 0` sampai `n-1`, tapi range angka adalah `[0..n]`. Nilai `n` tidak pernah di-XOR sebagai index di loop, jadi harus dimasukkan manual sebagai nilai awal `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — hanya satu variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3, 0, 1]`, `n = 3`

**Init:** `ans = 3`

| i | nums[i] | ans ^= i ^ nums[i] | ans |
| --- | ------- | ------------------ | --- |
| 0 | 3 | ans = 3^0^3 | 0 |
| 1 | 0 | ans = 0^1^0 | 1 |
| 2 | 1 | ans = 1^2^1 | 2 |

**return `2` ✅**

______________________________________________________________________

## Kenapa Bisa?

```
ans = n ^ (0^nums[0]) ^ (1^nums[1]) ^ (2^nums[2])
    = 3 ^ (0^3) ^ (1^0) ^ (2^1)
    = 3^0^3^1^0^2^1

Kelompokkan pasangan yang sama:
    = (3^3) ^ (0^0) ^ (1^1) ^ 2
    = 0 ^ 0 ^ 0 ^ 2
    = 2 ✅
```

______________________________________________________________________

**Input:** `nums = [9,6,4,2,3,5,7,0,1]`, `n = 9`

Semua angka `0-9` ada kecuali `8`:

```
ans = 9 ^ (0^9)^(1^6)^(2^4)^(3^2)^(4^3)^(5^5)^(6^7)^(7^0)^(8^1)
Semua pasangan cancel kecuali 8
= 8 ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `nums = [0]` → return `1`
- [ ] `nums = [1]` → return `0`
- [ ] Angka hilang di awal, tengah, atau akhir → XOR tetap handle

______________________________________________________________________

## 📌 Key Takeaway

Trick **XOR index dengan elemen** secara bersamaan membuat semua pasangan yang ada saling cancel, menyisakan angka yang hilang. Inisialisasi `ans = n` penting karena `n` tidak pernah muncul sebagai index di loop tapi harus ikut di-XOR. Teknik ini mirip dengan Single Number (#136) — keduanya memanfaatkan sifat `a ^ a = 0`. 🎯

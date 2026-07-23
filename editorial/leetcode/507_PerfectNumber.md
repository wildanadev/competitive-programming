# 507. Perfect Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/perfect-number/)
- **Solution**: [Code](../../leetcode/PerfectNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah bilangan bulat positif disebut **perfect number** jika jumlah semua **proper divisor**-nya (semua pembagi kecuali bilangan itu sendiri) sama dengan bilangan tersebut.

Contoh:

- `num = 28` → `true` (1+2+4+7+14 = 28)
- `num = 7` → `false` (1 ≠ 7)
- `num = 6` → `true` (1+2+3 = 6)

______________________________________________________________________

## 💡 Intuition

Untuk mencari semua proper divisor secara efisien, kita hanya perlu loop sampai `√num`. Untuk setiap `i` yang membagi `num`:

- `i` adalah pembagi
- `num/i` adalah pembagi pasangannya

Keduanya ditambahkan sekaligus, sehingga loop cukup sampai `√num` → O(√n).

`1` selalu ditambahkan di akhir karena `1` adalah pembagi dari semua bilangan (tapi tidak pernah muncul dari loop yang mulai dari `i=2`).

```
num = 28, √28 ≈ 5.29 → loop i=2..5

i=2: 28%2=0 → tambah 2 + 28/2=14
i=3: 28%3≠0 → skip
i=4: 28%4=0 → tambah 4 + 28/4=7
i=5: 28%5≠0 → skip

sum = 2+14+4+7 = 27, tambah 1 → 28 == num ✅
```

______________________________________________________________________

## 🔍 Approach

### Divisor Pair + √n Optimization

1. Jika `num < 2` → return `false` (0 dan 1 bukan perfect number).
1. Inisialisasi `sum = 0`.
1. Loop `i` dari `2` sampai `√num`:
   - Jika `num % i == 0` → `sum += i + num/i`
1. Return `sum + 1 == num` (tambah `1` untuk proper divisor `1`).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------- |
| **Time** | O(√n) — loop sampai akar num |
| **Space** | O(1) — hanya variabel sum |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `num = 28`

`√28 ≈ 5.29, sum = 0`

| i | 28%i==0? | sum += | sum |
| --- | -------- | ------- | --- |
| 2 | ✅ | 2+14=16 | 16 |
| 3 | ❌ | — | 16 |
| 4 | ✅ | 4+7=11 | 27 |
| 5 | ❌ | — | 27 |

`sum + 1 = 28 == num` → `true`

**Output: `true` ✅**

______________________________________________________________________

**Input:** `num = 6`

`√6 ≈ 2.44, sum = 0`

| i | 6%i==0? | sum += | sum |
| --- | ------- | ------ | --- |
| 2 | ✅ | 2+3=5 | 5 |

`sum + 1 = 6 == num` → `true`

**Output: `true` ✅**

______________________________________________________________________

**Input:** `num = 12`

`√12 ≈ 3.46, sum = 0`

| i | 12%i==0? | sum += | sum |
| --- | -------- | ------ | --- |
| 2 | ✅ | 2+6=8 | 8 |
| 3 | ✅ | 3+4=7 | 15 |

`sum + 1 = 16 ≠ 12` → `false`

**Output: `false` ✅** (1+2+3+4+6=16 ≠ 12)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `num = 1` → return `false` langsung (1 tidak punya proper divisor selain dirinya, dan 0 ≠ 1)
- [ ] `num = 2` → loop tidak ada yang terbagi → `sum = 0`, `0+1=1 ≠ 2` → `false`
- [ ] Perfect square (misal `num=4`): `i=2`, `i == num/i` → `sum += 2+2=4`, `4+1=5 ≠ 4` → `false`. Tapi ini **bug potensial!** Jika perfect square dan `i == num/i`, kita menambah `i` dua kali!

______________________________________________________________________

**Apakah ini bug?** Mari cek:

Untuk `num=4, i=2`: `4%2=0`, `sum += 2 + 4/2 = 2+2 = 4`. Tapi `2` hanya boleh dihitung **sekali** (karena `2×2=4`, bukan dua pembagi berbeda).

Kode asli menambah `2+2=4` padahal seharusnya hanya `+2`. Namun untuk soal ini **tidak masalah** karena semua perfect number yang ada (6, 28, 496, 8128, 33550336) **bukan perfect square** — tidak ada perfect square yang juga perfect number. Tapi secara matematika, kode ini memiliki bug untuk perfect square lain yang kebetulan merupakan perfect number (jika ada).

Fix aman:

```java
if (num % i == 0) {
    perfectNum += i;
    if (i != num / i) perfectNum += num / i;  // hindari double count
}
```

______________________________________________________________________

## 📌 Key Takeaway

Perfect number check menggunakan **divisor pair optimization** — loop sampai `√n` dan tambahkan dua pembagi sekaligus (`i` dan `n/i`). `1` selalu ditambah terpisah karena loop mulai dari `i=2`. Perlu hati-hati dengan **perfect square** (`i == n/i`) yang hanya punya satu pembagi di pasangan tersebut, bukan dua. 🎯

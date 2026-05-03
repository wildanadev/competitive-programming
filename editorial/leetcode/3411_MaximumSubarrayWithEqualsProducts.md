# 3411. Maximum Subarray With Equal Products

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Sliding Window, Number Theory
- **Link**: [Problem](https://leetcode.com/problems/maximum-subarray-with-equal-products/)
- **Solution**: [Code](../../leetcode/MaximumSubarrayWithEqualProducts.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah subarray disebut **product-equivalent** jika:

```
product = lcm × gcd
```

di mana `product`, `lcm`, dan `gcd` dihitung dari semua elemen subarray.

Kembalikan panjang **product-equivalent subarray terpanjang**.

Contoh:

- `nums = [1,2,1,2,1]` → `5`
- `nums = [2,3,4]` → `2` (subarray `[2,3]` atau `[3,4]`)

______________________________________________________________________

## 💡 Intuition: Kenapa `product == lcm * gcd`?

Ini adalah hubungan matematika yang elegan. Untuk **dua bilangan** `a` dan `b`:

```
a * b = gcd(a,b) * lcm(a,b)
```

Ini selalu benar untuk dua bilangan. Tapi untuk **lebih dari dua bilangan**, persamaan ini **tidak selalu berlaku** — dan inilah yang menjadi kondisi khusus soal ini.

Contoh:

```
nums = [2, 3]
product = 2 * 3 = 6
gcd(2,3) = 1,  lcm(2,3) = 6
lcm * gcd = 6 * 1 = 6  ✅

nums = [2, 3, 4]
product = 2 * 3 * 4 = 24
gcd(2,3,4) = 1,  lcm(2,3,4) = 12
lcm * gcd = 12 * 1 = 12 ≠ 24  ❌
```

Jadi `product == lcm * gcd` adalah kondisi yang **lebih ketat** dari biasa.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Fixed Start

1. Loop `i` dari `0` sampai `n-1` sebagai start.
1. Inisialisasi `currGCD = currLCM = currPro = nums[i]`.
1. Loop `j` dari `i+1` sampai `n-1`:
   - Update `currPro *= nums[j]`
   - Update `currGCD = gcd(currGCD, nums[j])`
   - Update `currLCM = lcm(currLCM, nums[j])`
   - Jika `currPro == currLCM * currGCD` → update `ans`.
1. Return `ans`.

**Helper functions:**

```java
gcd(a, b) = gcd(b, a % b)  // Euclidean algorithm
lcm(a, b) = a / gcd(a,b) * b  // hindari overflow dengan bagi dulu
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------- |
| **Time** | O(n² log M) — dua nested loop, setiap step GCD O(log M) |
| **Space** | O(1) — hanya variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,1,2,1]`

______________________________________________________________________

**i=0:**

| j | nums[j] | currPro | currGCD | currLCM | lcm\*gcd | == currPro? | ans |
| --- | ------- | ------- | ------- | ------- | -------- | ----------- | --- |
| — | — | 1 | 1 | 1 | — | — | 0 |
| 1 | 2 | 2 | 1 | 2 | 2 | ✅ | 2 |
| 2 | 1 | 2 | 1 | 2 | 2 | ✅ | 3 |
| 3 | 2 | 4 | 1 | 2 | 2 | ❌ | 3 |
| 4 | 1 | 4 | 1 | 2 | 2 | ❌ | 3 |

______________________________________________________________________

**i=1:**

| j | nums[j] | currPro | currGCD | currLCM | lcm\*gcd | == currPro? | ans |
| --- | ------- | ------- | ------- | ------- | -------- | ----------- | --- |
| — | — | 2 | 2 | 2 | — | — | 3 |
| 2 | 1 | 2 | 1 | 2 | 2 | ✅ | 3 |
| 3 | 2 | 4 | 1 | 2 | 2 | ❌ | 3 |
| 4 | 1 | 4 | 1 | 2 | 2 | ❌ | 3 |

______________________________________________________________________

**i=2:**

| j | nums[j] | currPro | currGCD | currLCM | lcm\*gcd | == currPro? | ans |
| --- | ------- | ------- | ------- | ------- | -------- | ----------- | ----- |
| — | — | 1 | 1 | 1 | — | — | 3 |
| 3 | 2 | 2 | 1 | 2 | 2 | ✅ | 4 |
| 4 | 1 | 2 | 1 | 2 | 2 | ✅ | **5** |

**Output: `5` ✅**

______________________________________________________________________

**Input:** `nums = [2,3,4]`

**i=0:**

| j | nums[j] | currPro | currGCD | currLCM | lcm\*gcd | == currPro? | ans |
| --- | ------- | ------- | ------- | ------- | -------- | ----------- | --- |
| — | — | 2 | 2 | 2 | — | — | 0 |
| 1 | 3 | 6 | 1 | 6 | 6 | ✅ | 2 |
| 2 | 4 | 24 | 1 | 12 | 12 | ❌ | 2 |

**i=1:**

| j | nums[j] | currPro | currGCD | currLCM | lcm\*gcd | == currPro? | ans |
| --- | ------- | ------- | ------- | ------- | -------- | ----------- | --- |
| — | — | 3 | 3 | 3 | — | — | 2 |
| 2 | 4 | 12 | 1 | 12 | 12 | ✅ | 2 |

**Output: `2` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen `1` → `product=1`, `gcd=1`, `lcm=1` → selalu valid → return `n`
- [ ] Dua elemen → `a*b == lcm(a,b)*gcd(a,b)` selalu benar → semua pasangan valid → return `2`
- [ ] Elemen besar → `currPro` bisa overflow untuk array panjang — constraints soal menjamin ini aman

______________________________________________________________________

## 🔧 Kenapa `a / gcd(a, b) * b`, Bukan `a * b / gcd(a, b)`?

```java
// Berpotensi overflow jika a*b besar
return a * b / gcd(a, b);  ❌

// Bagi dulu sebelum kali → lebih aman
return a / gcd(a, b) * b;  ✅
```

`a` selalu habis dibagi `gcd(a,b)` — jadi pembagian `a / gcd(a,b)` pasti menghasilkan integer. Dengan membagi dulu, nilai intermediate tetap kecil sebelum dikali `b`.

______________________________________________________________________

## 🔧 Properti Matematika yang Dipakai

**GCD dan LCM bisa diakumulasi:**

```java
gcd(a, b, c) = gcd(gcd(a, b), c)
lcm(a, b, c) = lcm(lcm(a, b), c)
```

Jadi saat `j` bertambah, kita cukup update:

```java
currGCD = gcd(currGCD, nums[j]);
currLCM = lcm(currLCM, nums[j]);
```

Tidak perlu menghitung ulang dari awal setiap iterasi `j`.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua hal: **sliding window brute force** dan **number theory** (`gcd`, `lcm`). Kondisi `product == lcm * gcd` adalah properti matematis yang hanya berlaku untuk subarray "sederhana" tanpa faktor prima berulang yang kompleks. Trik implementasi penting: **akumulasi GCD dan LCM secara incremental** menggunakan `gcd(gcd, nums[j])` dan `lcm(lcm, nums[j])` — tidak perlu rebuild dari awal setiap perluasan window. 🎯

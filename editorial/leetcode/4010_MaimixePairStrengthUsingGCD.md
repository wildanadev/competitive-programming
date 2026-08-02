# 4010. Maximize Pair Strength Using GCD

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Number Theory
- **Link**: [Problem](https://leetcode.com/problems/maximize-pair-strength-using-gcd/)
- **Solution**: [Code](../../leetcode/MaximizePairStrengthUsingGCD.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`, pilih tepat satu pasang indeks berbeda `i` dan `j`. **Strength** pasangan didefinisikan sebagai:

```
strength(i,j) = (nums[i] * nums[j]) / gcd(nums[i], nums[j])²
```

Kembalikan **strength maksimum** dari semua kemungkinan pasangan.

Contoh:

- `nums = [2,3,5]` → `15` (pair (3,5): 3×5/gcd(3,5)²=15/1=15)
- `nums = [4,6,8]` → `12` (pair (6,8): 6×8/gcd(6,8)²=48/4=12)
- `nums = [3,3]` → `1` (3×3/gcd(3,3)²=9/9=1)

______________________________________________________________________

## 💡 Intuition

**Makna matematis dari formula:**

```
(a × b) / gcd(a,b)² = lcm(a,b) / gcd(a,b)
```

Bukti: `a × b = gcd(a,b) × lcm(a,b)` → `a × b / gcd(a,b)² = lcm(a,b) / gcd(a,b)`

**Insight kunci**: strength dimaksimalkan ketika `lcm(a,b)` besar dan `gcd(a,b)` kecil. Pasangan bilangan yang **coprime** (gcd=1) menghasilkan strength = `a×b` — paling besar.

Karena constraint `n <= 2000`, brute force O(n²) mengecek semua `n(n-1)/2` pasang → maksimal `~2,000,000` iterasi → aman.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Cek Semua Pasangan

1. Loop `i` dari `0` sampai `n-1`.
1. Loop `j` dari `i+1` sampai `n-1`.
1. Hitung `strength = (nums[i] * nums[j]) / (gcd² )`.
1. Update `ans = max(ans, strength)`.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------- |
| **Time** | O(n² × log(max)) — n² pasang, GCD O(log max) |
| **Space** | O(1) — hanya variabel `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,3,5]`

| i,j | nums[i] | nums[j] | gcd | strength = (i×j)/gcd² | ans |
| --- | ------- | ------- | --- | --------------------- | --- |
| 0,1 | 2 | 3 | 1 | 6/1=6 | 6 |
| 0,2 | 2 | 5 | 1 | 10/1=10 | 10 |
| 1,2 | 3 | 5 | 1 | 15/1=15 | 15 |

**Output: `15` ✅**

______________________________________________________________________

**Input:** `nums = [4,6,8]`

| i,j | nums[i] | nums[j] | gcd | strength | ans |
| --- | ------- | ------- | --- | -------- | --- |
| 0,1 | 4 | 6 | 2 | 24/4=6 | 6 |
| 0,2 | 4 | 8 | 4 | 32/16=2 | 6 |
| 1,2 | 6 | 8 | 2 | 48/4=12 | 12 |

**Output: `12` ✅**

______________________________________________________________________

**Input:** `nums = [3,3]`

| i,j | gcd | strength |
| --- | --- | -------- |
| 0,1 | 3 | 9/9=1 |

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Dua elemen sama → gcd = nilai itu sendiri → strength = 1
- [ ] Semua elemen coprime (gcd=1) → strength = produk keduanya → pilih dua terbesar
- [ ] Dua elemen → hanya satu pasang yang mungkin

______________________________________________________________________

## 🔧 Kenapa Cast ke `long`?

```java
long numi = nums[i];
long numj = nums[j];
long gcdNum = gcd(nums[i], nums[j]);
ans = Math.max(ans, numi * numj / (gcdNum * gcdNum));
```

`nums[i]` bisa sampai `10^5`. Perkalian `nums[i] * nums[j]` bisa sampai `10^10` — melampaui batas `int` (`~2.1×10^9`). Cast ke `long` sebelum perkalian mencegah overflow.

______________________________________________________________________

## 🔧 Hubungan Formula dengan LCM dan GCD

Formula bisa ditulis ulang:

```
(a × b) / gcd(a,b)²

Karena a × b = gcd(a,b) × lcm(a,b):
= gcd(a,b) × lcm(a,b) / gcd(a,b)²
= lcm(a,b) / gcd(a,b)
```

Jadi kita memaksimalkan `lcm(a,b) / gcd(a,b)` — rasio antara LCM dan GCD.

Untuk pasangan coprime (`gcd=1`): `lcm/gcd = lcm = a×b` → maksimal.
Untuk pasangan sama (`a=b`): `lcm/gcd = a/a = 1` → minimal.

______________________________________________________________________

## 📌 Key Takeaway

Formula `(a×b)/gcd²` ekuivalen dengan `lcm/gcd` — dimaksimalkan oleh pasangan **coprime** (gcd=1) yang menghasilkan strength = `a×b`. Brute force O(n²) aman untuk constraint `n<=2000`. Cast ke `long` wajib karena perkalian dua angka hingga `10^5` bisa mencapai `10^10`. 🎯

# 3658. GCD of Odd and Even Sums

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Number Theory
- **Link**: [Problem](https://leetcode.com/problems/gcd-of-odd-and-even-sums/)
- **Solution**: [Code](../../leetcode/GcdOfOddAndEvenSums.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, hitung GCD dari:

- `sumOdd`: jumlah `n` bilangan **ganjil** positif terkecil (1+3+5+...+(2n-1))
- `sumEven`: jumlah `n` bilangan **genap** positif terkecil (2+4+6+...+2n)

Kembalikan `GCD(sumOdd, sumEven)`.

Contoh:

- `n = 4` → `GCD(1+3+5+7, 2+4+6+8) = GCD(16,20) = 4`
- `n = 5` → `GCD(1+3+5+7+9, 2+4+6+8+10) = GCD(25,30) = 5`

______________________________________________________________________

## 💡 Intuition

### Formula Deret

Ada rumus closed-form untuk kedua sum:

```
sumOdd  = 1+3+5+...+(2n-1) = n²
sumEven = 2+4+6+...+2n     = n(n+1)
```

Jadi masalah menjadi `GCD(n², n(n+1))`.

### Mengapa GCD = n?

```
GCD(n², n(n+1))
= n × GCD(n, n+1)   ← faktor n bisa dikeluarkan
= n × 1              ← n dan n+1 adalah bilangan berturutan, GCD selalu 1!
= n
```

**Jawabannya selalu `n`!** 🎯

______________________________________________________________________

## 🔍 Approach 1: Kode Asli (Simulasi — O(n))

```java
int odd = 0, even = 0;
for (int i = 1; i <= n * 2; i++) {
    if ((i & 1) == 0) even += i;
    else odd += i;
}
return gcd(odd, even);
```

Loop dari `1` sampai `2n`, akumulasikan sum ganjil dan genap, lalu hitung GCD.

## 🔍 Approach 2: Formula Matematika (O(1))

```java
class Solution {
    public int gcdOfOddEvenSums(int n) {
        return n;  // GCD(n², n(n+1)) = n selalu!
    }
}
```

______________________________________________________________________

## 🧮 Complexity

| Approach | Time | Space |
| -------------------- | ---- | ----- |
| Simulasi (kode asli) | O(n) | O(1) |
| Formula Math | O(1) | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 4`

**Simulasi:**

```
i=1 (ganjil): odd=1
i=2 (genap):  even=2
i=3 (ganjil): odd=4
i=4 (genap):  even=6
i=5 (ganjil): odd=9
i=6 (genap):  even=12
i=7 (ganjil): odd=16
i=8 (genap):  even=20
```

`GCD(16, 20) = 4` ✅

**Formula:**

```
sumOdd  = n² = 16
sumEven = n(n+1) = 20
GCD(16,20) = 4 = n ✅
```

______________________________________________________________________

**Input:** `n = 5`

```
sumOdd  = 5² = 25
sumEven = 5×6 = 30
GCD(25,30) = 5 = n ✅
```

______________________________________________________________________

## 🔧 Pembuktian Formula `sumOdd = n²`

Jumlah `n` bilangan ganjil pertama:

```
S = 1 + 3 + 5 + ... + (2n-1)

Gunakan rumus deret aritmetika: S = n/2 × (suku pertama + suku terakhir)
  = n/2 × (1 + (2n-1))
  = n/2 × 2n
  = n²  ✅
```

## 🔧 Pembuktian Formula `sumEven = n(n+1)`

```
S = 2 + 4 + 6 + ... + 2n
  = 2(1 + 2 + 3 + ... + n)
  = 2 × n(n+1)/2
  = n(n+1)  ✅
```

## 🔧 Pembuktian `GCD(n², n(n+1)) = n`

```
GCD(n², n(n+1))

Faktorkan n dari keduanya:
= n × GCD(n, n+1)

GCD(n, n+1): dua bilangan berturutan selalu coprime (GCD = 1)
Bukti: jika d | n dan d | (n+1), maka d | (n+1-n) = 1, jadi d=1

= n × 1 = n  ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → `GCD(1, 2) = 1 = n` ✅
- [ ] `n = 2` → `GCD(4, 6) = 2 = n` ✅
- [ ] Semua nilai positif n → selalu return `n`

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh di mana **insight matematika mengubah O(n) menjadi O(1)**. Formula deret aritmetika `sumOdd = n²` dan `sumEven = n(n+1)` dikombinasikan dengan sifat GCD bilangan berturutan (`GCD(n, n+1) = 1`) membuktikan bahwa jawabannya selalu `n`. Ini adalah soal yang terlihat perlu simulasi, tapi sebenarnya bisa diselesaikan dengan satu baris `return n`. 🎯

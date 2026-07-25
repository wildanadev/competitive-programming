# 3536. Maximum Product of Two Digits

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/maximum-product-of-two-digits/)
- **Solution**: [Code](../../leetcode/MaximumProductOfTwoDigits.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer positif `n`, kembalikan **perkalian maksimum** dari dua digit yang berbeda posisi di `n`.

Contoh:

- `n = 31` → `3` (3×1=3)
- `n = 22` → `4` (2×2=4)
- `n = 124` → `8` (2×4=8)

______________________________________________________________________

## 💡 Intuition

Cukup temukan **dua digit terbesar** dari `n`, kalikan keduanya. Karena perkalian dua bilangan positif dimaksimalkan ketika keduanya sebesar mungkin, dua digit terbesar selalu menghasilkan produk maksimum.

Ekstrak digit dengan `n % 10` dan `n /= 10`, track dua digit terbesar dengan dua variabel `max1` dan `max2`.

______________________________________________________________________

## 🔍 Approach

### Track Two Maximums — Single Pass

1. Inisialisasi `max1 = 0`, `max2 = 0`.
1. Loop selama `n != 0`:
   - `digit = n % 10`
   - Jika `digit > max1` → `max2 = max1`, `max1 = digit`
   - Else jika `digit > max2` → `max2 = digit`
   - `n /= 10`
1. Return `max1 * max2`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------- |
| **Time** | O(d) — d = jumlah digit `n` (maksimal 9 untuk `int`) |
| **Space** | O(1) — hanya dua variabel |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 124`

`max1=0, max2=0`

| n | digit = n%10 | digit>max1? | digit>max2? | max1 | max2 | n/=10 |
| --- | ------------ | ----------- | ------------- | ---- | ---- | ----- |
| 124 | 4 | 4>0 ✅ | max2=0,max1=4 | 4 | 0 | 12 |
| 12 | 2 | 2>4? ❌ | 2>0 ✅ | 4 | 2 | 1 |
| 1 | 1 | 1>4? ❌ | 1>2? ❌ | 4 | 2 | 0 |

`max1*max2 = 4*2 = 8`

**Output: `8` ✅**

______________________________________________________________________

**Input:** `n = 999`

| n | digit | max1 | max2 |
| --- | ----- | ---- | ---- |
| 999 | 9 | 9 | 0 |
| 99 | 9 | 9 | 9 |
| 9 | 9 | 9 | 9 |

`9*9 = 81`

**Output: `81` ✅**

______________________________________________________________________

**Input:** `n = 31`

| n | digit | max1 | max2 |
| --- | ----- | ---- | ---- |
| 31 | 1 | 1 | 0 |
| 3 | 3 | 3 | 1 |

`3*1 = 3`

**Output: `3` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua digit sama (`n=999`) → `max1=max2=9` → return `81`
- [ ] Dua digit (`n=31`) → dua iterasi, langsung dapat max1 dan max2
- [ ] Ada digit `0` (`n=102`) → `max1=2, max2=1` (0 tidak masuk karena `0 > max2=0` false) → `2*1=2`

______________________________________________________________________

## 🔧 Kenapa Kondisi `digit > max1` dan `digit > max2` Cukup?

```java
if (digit > max1) {
    max2 = max1;   // max1 lama jadi max2
    max1 = digit;  // digit baru jadi max1
} else if (digit > max2) {
    max2 = digit;  // digit baru jadi max2
}
```

Logika ini mempertahankan **invariant**: `max1 >= max2` selalu. Saat digit baru lebih besar dari `max1`, keduanya bergeser. Saat hanya lebih besar dari `max2`, hanya `max2` yang diupdate.

Kasus digit sama dengan `max1` atau `max2` tidak memperbarui apapun — tapi ini benar! Kita sudah punya nilai tersebut di `max1` atau `max2`.

______________________________________________________________________

## 🔧 Alternatif: Konversi ke String

```java
// Lebih verbose, pakai O(d) space
String s = String.valueOf(n);
int[] digits = new int[s.length()];
for (int i = 0; i < s.length(); i++) digits[i] = s.charAt(i) - '0';
Arrays.sort(digits);
return digits[digits.length-1] * digits[digits.length-2];
```

| Approach | Time | Space |
| -------------------- | ---------- | ----- |
| Track two max (kode) | O(d) | O(1) |
| String + sort | O(d log d) | O(d) |

Kode asli lebih efisien karena tidak perlu sorting dan tidak membuat array tambahan.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi sederhana dari **"track two maximums in one pass"** — pola yang sangat umum untuk soal yang membutuhkan dua nilai terbesar. Mengekstrak digit dengan `n%10` dan `n/=10` (tanpa konversi ke string) lebih efisien dan elegant. Pola track-two-max ini juga muncul di _Kth Largest Element_ dan _Third Maximum Number_. 🎯

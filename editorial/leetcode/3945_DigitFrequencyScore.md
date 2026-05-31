# 3945. Digit Frequency Score

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/contest/weekly-contest-504/problems/digit-frequency-score/)
- **Solution**: [Code](../../leetcode/DigitFrequencyScore.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, hitung **digit frequency score** — jumlah dari `digit × frekuensi digit` untuk setiap digit unik dalam `n`.

Contoh:

- `n = 1234` → `1×1 + 2×1 + 3×1 + 4×1 = 10`
- `n = 1122` → `1×2 + 2×2 = 2+4 = 6`
- `n = 1232` → `1×1 + 2×2 + 3×1 = 1+4+3 = 8`

______________________________________________________________________

## 💡 Intuition

**Insight kunci**: `digit × frekuensi` identik dengan menjumlahkan setiap kemunculan digit satu per satu.

```
n = 1232
Cara panjang: 1×1 + 2×2 + 3×1 = 1 + (2+2) + 3 = 8
Cara singkat: 1 + 2 + 3 + 2   = 8  ← sama!
```

Jadi `digit_frequency_score(n)` = **jumlah semua digit** `n`. Cukup ekstrak setiap digit dengan modulo dan jumlahkan.

______________________________________________________________________

## 🔍 Approach

### Sum of All Digits (Math)

1. Inisialisasi `sum = 0`.
1. Loop selama `n > 0`:
   - Tambahkan digit terakhir: `sum += n % 10`
   - Hapus digit terakhir: `n /= 10`
1. Return `sum`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------- |
| **Time** | O(d) — d = jumlah digit `n`, maksimal 10 untuk `int` |
| **Space** | O(1) — hanya variabel `sum` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 1232`

| n | n % 10 | sum | n / 10 |
| ---- | --------- | --- | ------ |
| 1232 | 2 | 2 | 123 |
| 123 | 3 | 5 | 12 |
| 12 | 2 | 7 | 1 |
| 1 | 1 | 8 | 0 |
| 0 | loop stop | | |

**Output: `8` ✅**

______________________________________________________________________

**Input:** `n = 1122`

| n | n % 10 | sum |
| ---- | ------ | --- |
| 1122 | 2 | 2 |
| 112 | 2 | 4 |
| 11 | 1 | 5 |
| 1 | 1 | 6 |

**Output: `6` ✅**

______________________________________________________________________

**Input:** `n = 100`

| n | n % 10 | sum |
| --- | ------ | --- |
| 100 | 0 | 0 |
| 10 | 0 | 0 |
| 1 | 1 | 1 |

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 0` → loop tidak jalan → return `0`
- [ ] Semua digit sama `n = 1111` → `1+1+1+1 = 4` = `1×4 = 4` ✅
- [ ] Digit `0` → tidak berkontribusi (`0 × freq = 0 = 0+0+...`)

______________________________________________________________________

## 🔧 Pembuktian Matematis

Mengapa `Σ(digit × freq) == Σ(semua digit)`?

```
Misalkan n = 1232, digit unik = {1,2,3}

Σ(digit × freq) = 1×1 + 2×2 + 3×1
               = 1 + (2+2) + 3        ← expand perkalian
               = 1 + 2 + 2 + 3        ← setiap kemunculan ditulis satu per satu
               = Σ(semua digit) ✅
```

Secara umum: `d × k` = menjumlahkan `d` sebanyak `k` kali = total kontribusi digit `d`.

______________________________________________________________________

## 📊 Perbandingan dengan HashMap Approach

```java
// HashMap approach — O(d) time, lebih verbose
HashMap<Integer, Integer> map = new HashMap<>();
for (char c : String.valueOf(n).toCharArray())
    map.put(c-'0', map.getOrDefault(c-'0', 0) + 1);
for (Map.Entry<Integer,Integer> e : map.entrySet())
    ans += e.getKey() * e.getValue();

// Math approach (kode ini) — O(d) time, lebih bersih
while (n > 0) { sum += n % 10; n /= 10; }
```

| Approach | Time | Space | Baris kode |
| ---------- | ---- | ------ | ---------- |
| HashMap | O(d) | O(1)\* | ~7 baris |
| Math (ini) | O(d) | O(1) | 3 baris |

\*HashMap maksimal 10 entry tapi ada overhead boxing/unboxing

______________________________________________________________________

## 📌 Key Takeaway

Insight bahwa `Σ(digit × freq) = Σ(semua digit)` menyederhanakan masalah dari "hitung frekuensi lalu kalikan" menjadi "jumlahkan semua digit" — dari 7 baris menjadi 3 baris. Mengekstrak digit dengan `n % 10` dan `n /= 10` adalah pola klasik untuk memproses setiap digit integer tanpa konversi ke string. 🎯

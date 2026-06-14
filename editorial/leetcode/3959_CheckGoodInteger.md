# 3959. Check Good Integer

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String
- **Link**: [Problem](https://leetcode.com/problems/check-good-integer/)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer positif `n`, hitung:

- `digitSum` = jumlah semua digit `n`
- `squareSum` = jumlah kuadrat semua digit `n`

Integer disebut **good** jika `squareSum - digitSum >= 50`. Return `true` jika good, `false` jika tidak.

Contoh:

- `n = 1000` → digits: `1,0,0,0` → `squareSum=1`, `digitSum=1` → `1-1=0` → `false`
- `n = 19` → digits: `1,9` → `squareSum=82`, `digitSum=10` → `82-10=72` → `true`

______________________________________________________________________

## 💡 Intuition

Ekstrak digit satu per satu menggunakan operasi modulo (`% 10`) dan pembagian integer (`/ 10`). Sambil mengekstrak, akumulasikan `digitSum` dan `squareSum` secara bersamaan dalam satu loop. Di akhir, tinggal cek apakah selisihnya memenuhi syarat.

```
n = 19
digit = 9  → digitSum += 9, squareSum += 81
digit = 1  → digitSum += 1, squareSum += 1
            digitSum = 10, squareSum = 82
            82 - 10 = 72 >= 50 ✅
```

______________________________________________________________________

## 🔍 Approach

### Single Pass Digit Extraction

1. Inisialisasi `digitSum = 0`, `squareSum = 0`.
1. Loop selama `n != 0`:
   - Ambil digit terakhir: `digit = n % 10`
   - Tambahkan ke `digitSum += digit`
   - Tambahkan kuadratnya ke `squareSum += digit * digit`
   - Geser ke digit berikutnya: `n /= 10`
1. Return `squareSum - digitSum >= 50`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------- |
| **Time** | O(d) — d = jumlah digit n (maks 10) |
| **Space** | O(1) — hanya variabel akumulator |

Karena constraint `n <= 10^9`, digit maksimal adalah 10 → praktisnya O(1).

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 19`

`digitSum=0, squareSum=0`

| Iterasi | digit | digitSum | squareSum | n setelah `/= 10` |
| ------- | ----- | -------- | --------- | ----------------- |
| 1 | 9 | 9 | 81 | 1 |
| 2 | 1 | 10 | 82 | 0 (loop berhenti) |

`squareSum - digitSum = 82 - 10 = 72 >= 50` → **`true` ✅**

______________________________________________________________________

**Input:** `n = 1000`

`digitSum=0, squareSum=0`

| Iterasi | digit | digitSum | squareSum | n setelah `/= 10` |
| ------- | ----- | -------- | --------- | ----------------- |
| 1 | 0 | 0 | 0 | 100 |
| 2 | 0 | 0 | 0 | 10 |
| 3 | 0 | 0 | 0 | 1 |
| 4 | 1 | 1 | 1 | 0 (loop berhenti) |

`squareSum - digitSum = 1 - 1 = 0 >= 50?` → **`false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` (satu digit) → `squareSum=1`, `digitSum=1` → `0 < 50` → `false`
- [ ] `n = 9` → `squareSum=81`, `digitSum=9` → `72 >= 50` → `true` (digit tunggal terbesar)
- [ ] `n = 10^9` (10 digit) → loop berjalan 10 kali, tetap efisien
- [ ] Digit 0 → tidak berkontribusi ke `digitSum` maupun `squareSum` (0² = 0)

______________________________________________________________________

## 🔧 Kenapa `long` untuk `digitSum` dan `squareSum`?

Sebenarnya untuk soal ini `int` sudah cukup — digit maks adalah 9, dan paling banyak 10 digit:

```
digitSum  maks = 9 × 10 = 90
squareSum maks = 81 × 10 = 810
```

Keduanya jauh di bawah batas `int`. Penggunaan `long` di solusi adalah **defensive programming** — kebiasaan baik agar tidak perlu memikirkan overflow jika constraint berubah.

______________________________________________________________________

## 🔧 Kenapa `digit * digit` bukan `Math.pow(digit, 2)`?

```java
// ❌ Math.pow mengembalikan double → perlu casting, risiko floating-point error
squareSum += (long) Math.pow(digit, 2);

// ✅ Integer multiplication langsung, lebih cepat dan akurat
squareSum += (digit * digit);
```

Untuk integer kecil seperti digit (0–9), perkalian langsung lebih idiomatik dan efisien.

______________________________________________________________________

## 📌 Key Takeaway

Check Good Integer adalah soal **digit decomposition** klasik — ekstrak digit dengan `% 10` dan `/ 10`, akumulasi nilai yang dibutuhkan dalam satu pass. Pola ini muncul di banyak soal LeetCode bertema matematika digit. Kompleksitas praktisnya O(1) karena jumlah digit dibatasi oleh constraint. 🎯

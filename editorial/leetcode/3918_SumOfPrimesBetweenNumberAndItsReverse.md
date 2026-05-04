# 3918. Sum of Primes Between Number and Its Reverse

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Number Theory
- **Link**: [Problem](https://leetcode.com/problems/sum-of-primes-between-number-and-its-reverse/)
- **Solution**: [Code](../../leetcode/SumOfPrimesBetweenNumberAndItsReverse.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, kembalikan **jumlah semua bilangan prima** dalam rentang `[min(n, reverse(n)), max(n, reverse(n))]` (inklusif).

`reverse(n)` adalah bilangan yang digitnya dibalik dari `n`.

Contoh:

- `n = 21` → reverse = `12` → rentang `[12, 21]` → prima: `13, 17, 19` → `13+17+19 = 49`
- `n = 13` → reverse = `31` → rentang `[13, 31]` → prima: `13, 17, 19, 23, 29, 31` → jumlahkan semua

______________________________________________________________________

## 💡 Intuition

Tiga langkah utama:

1. **Reverse** digit `n` untuk mendapat `rev`.
1. **Tentukan rentang** `[min, max]` dari `n` dan `rev`.
1. **Cari semua prima** dalam rentang tersebut dan jumlahkan.

Untuk mencari prima secara efisien, gunakan **Sieve of Eratosthenes** — precompute semua prima sampai `max` dalam O(max log log max).

______________________________________________________________________

## 🔍 Approach

### Reverse + Sieve of Eratosthenes

**Helper `reverse(n)`:**

- Ambil digit terakhir dengan `n % 10`, tambahkan ke `rev * 10`.
- Hapus digit terakhir dengan `n /= 10`.
- Ulangi sampai `n == 0`.

**Helper `sieve(n)`:**

- Buat array `boolean[] prime` berukuran `n+1`, isi semua `true`.
- Set `prime[0] = prime[1] = false`.
- Loop `i` dari `2` sampai `√n`:
  - Jika `prime[i]` masih `true` → tandai semua kelipatan `i` mulai dari `i²` sebagai `false`.

**Main:**

1. Hitung `rev = reverse(n)`.
1. `min = Math.min(n, rev)`, `max = Math.max(n, rev)`.
1. Jalankan `sieve(max)`.
1. Loop `i` dari `min` sampai `max`, jika `isPrime[i]` → `ans += i`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(max × log log max) — dominan di sieve |
| **Space** | O(max) — array boolean sieve |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 21`

**Step 1 — Reverse:**

```
n=21: rev = 0*10 + 1 = 1, n=2
n=2:  rev = 1*10 + 2 = 12, n=0
rev = 12
```

**Step 2 — Rentang:**

```
min = Math.min(21, 12) = 12
max = Math.max(21, 12) = 21
```

**Step 3 — Sieve sampai 21:**

Tandai bukan prima:

```
i=2: tandai 4,6,8,10,12,14,16,18,20
i=3: tandai 9,15,21
i=4: bukan prima, skip
```

Prima dalam `[12, 21]`:

```
12 → ❌ (kelipatan 2)
13 → ✅
14 → ❌
15 → ❌
16 → ❌
17 → ✅
18 → ❌
19 → ✅
20 → ❌
21 → ❌ (kelipatan 3)
```

**Step 4 — Sum:** `13 + 17 + 19 = 49`

**Output: `49` ✅**

______________________________________________________________________

**Input:** `n = 11`

**Reverse:** `11` → `11` (palindrom)

**Rentang:** `min = max = 11`

**Sieve:** `11` adalah prima ✅

**Sum:** `11`

**Output: `11` ✅**

______________________________________________________________________

**Input:** `n = 10`

**Reverse:** `10` → `01` = `1`

**Rentang:** `min = 1`, `max = 10`

**Prima dalam `[1, 10]`:** `2, 3, 5, 7`

**Sum:** `2+3+5+7 = 17`

**Output: `17` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n` palindrom (e.g. `11, 22`) → `min == max` → hanya cek satu angka
- [ ] `reverse(n)` punya leading zero (e.g. `10` → `01 = 1`) → `Integer` otomatis hapus leading zero ✅
- [ ] `n` dan `rev` keduanya bukan prima → tidak ada prima di rentang → return `0`

______________________________________________________________________

## 🔧 Cara Kerja Sieve of Eratosthenes

Sieve adalah cara paling efisien untuk menemukan semua prima sampai nilai `n`.

**Idenya**: setiap bilangan prima `p` pasti menyebabkan semua kelipatannya bukan prima. Kita tandai semua kelipatan tersebut.

```
Awal: [T,T,T,T,T,T,T,T,T,T] (indeks 0-9)
      set 0,1 = false

i=2 (prima): tandai 4,6,8 → [F,F,T,T,F,T,F,T,F,T]
i=3 (prima): tandai 9     → [F,F,T,T,F,T,F,T,F,F]
i=4: bukan prima, skip (i*i=16 > 9, loop selesai)

Prima: 2,3,5,7 ✅
```

**Kenapa mulai dari `i*i`, bukan `2*i`?**

Semua kelipatan `i` yang lebih kecil dari `i²` sudah ditandai oleh prima yang lebih kecil sebelumnya:

```
i=5: 5*2=10 sudah ditandai oleh i=2
     5*3=15 sudah ditandai oleh i=3
     5*4=20 sudah ditandai oleh i=2
     5*5=25 ← baru mulai dari sini!
```

**Kenapa loop `i` sampai `√n`?**

Jika `n` memiliki faktor prima `p > √n`, pasti ada faktor prima lain `q < √n` yang sudah menandainya. Jadi tidak perlu cek lebih dari `√n`.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan tiga teknik: **reverse digit**, **range query**, dan **Sieve of Eratosthenes**. Sieve adalah pilihan tepat karena kita butuh semua prima dalam rentang — bukan hanya mengecek satu angka. Optimasi `i*i` sebagai starting point inner loop dan loop outer sampai `√n` adalah dua detail penting Sieve yang sering terlewat. 🎯

# 7. Weird Algorithm

- **Platform**: CSES
- **Difficulty**: Easy
- **Topics**: Math, Simulation
- **Link**: [Problem](https://cses.fi/problemset/task/1068)
- **Solution**: [Code](../../cses/WeirdAlgorithm.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan sebuah bilangan `n`.

Lakukan proses berikut sampai `n = 1`:

- Jika `n` **genap** → `n = n / 2`
- Jika `n` **ganjil** → `n = 3n + 1`

Cetak seluruh urutan nilai `n`.

______________________________________________________________________

## 💡 Intuition

Ini adalah masalah klasik yang dikenal sebagai **Collatz Conjecture**.

Aturannya sederhana:

- Ganjil → naik dulu (`3n + 1`)
- Genap → turun (`n / 2`)

👉 Menariknya, untuk semua `n` yang diuji sejauh ini, hasilnya selalu berakhir di **1**.

Kita hanya perlu **mensimulasikan prosesnya**.

______________________________________________________________________

## 🔍 Approach

1. Baca input `n`

1. Selama `n != 1`:

   - Print `n`

   - Jika `n` ganjil:

     ```
     n = 3 * n + 1
     ```

   - Jika `n` genap:

     ```
     n = n / 2
     ```

1. Print `1` di akhir

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------ |
| **Time** | O(k), k = jumlah langkah |
| **Space** | O(1) |

> Nilai `k` tidak pasti, tapi untuk constraint soal aman.

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```id="dryrun1"
n = 6
```

**Proses:**

```id="dryrun2"
6 → 3 → 10 → 5 → 16 → 8 → 4 → 2 → 1
```

👉 **Output:**

```id="dryrun3"
6 3 10 5 16 8 4 2 1
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → langsung output `1`
- [ ] Nilai besar → gunakan `long` (karena `3n + 1` bisa overflow int)
- [ ] Pastikan loop berhenti saat `n == 1`

______________________________________________________________________

## 📌 Key Takeaway

- Ini adalah problem **simulasi sederhana**
- Gunakan operasi bit:
  - `(n & 1)` untuk cek ganjil/genap
  - `n >> 1` untuk bagi 2

👉 Kadang soal mudah hanya butuh implementasi tepat tanpa optimasi kompleks

Bonus insight:

- Problem ini terkenal di matematika sebagai **Collatz Conjecture**
- Sampai sekarang belum terbukti untuk semua bilangan, tapi selalu works di soal 😄

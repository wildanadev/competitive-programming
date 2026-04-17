# Coin Piles

- **Platform**: CSES
- **Difficulty**: Easy
- **Topics**: Math, Greedy
- **Link**: [Problem](https://cses.fi/problemset/task/1754/)
- **Solution**: [Code](../../cses/CoinPiles.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua tumpukan koin dengan jumlah `a` dan `b`.

Dalam satu langkah, kamu boleh:

- Mengambil **2 koin dari satu tumpukan**, dan
- Mengambil **1 koin dari tumpukan lainnya**

Tentukan apakah kedua tumpukan bisa menjadi **0 secara bersamaan**.

______________________________________________________________________

## 💡 Intuition

Setiap langkah selalu mengurangi total koin sebanyak **3**.

👉 Jadi syarat pertama:

- `(a + b)` harus habis dibagi 3

Selain itu:

- Kita tidak boleh mengambil lebih banyak dari satu tumpukan daripada yang tersedia
- Artinya, tumpukan yang lebih besar **tidak boleh lebih dari 2× tumpukan yang lebih kecil**

👉 Jadi syarat kedua:

- `max(a, b) <= 2 * min(a, b)`

Kalau dua kondisi ini terpenuhi → pasti bisa dibuat 0.

______________________________________________________________________

## 🔍 Approach

1. Input `a` dan `b`
1. Cek apakah `(a + b) % 3 == 0`
1. Cek apakah `max(a, b) <= 2 * min(a, b)`
1. Jika kedua kondisi true → `"YES"`
1. Jika tidak → `"NO"`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------- |
| **Time** | O(1) — per test case |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `a = 2, b = 1`

**Step 1 — Cek total:**

```
a + b = 3 → habis dibagi 3 ✅
```

**Step 2 — Cek rasio:**

```
max(2,1) = 2
2 <= 2 * 1 → 2 <= 2 ✅
```

👉 **Output: YES**

______________________________________________________________________

**Input:** `a = 2, b = 2`

```
a + b = 4 → tidak habis dibagi 3 ❌
```

👉 **Output: NO**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `a = 0, b = 0` → YES
- [ ] Salah satu 0, misal `a = 0, b = 5` → NO
- [ ] Nilai besar (gunakan `long`)
- [ ] `a = b` tapi tidak memenuhi `(a+b)%3==0`

______________________________________________________________________

## 📌 Key Takeaway

Kalau operasi selalu mengurangi jumlah tetap (di sini 3), coba cek:

- Apakah total memenuhi kondisi modular `(a + b) % k == 0`
- Apakah distribusi antar elemen masih valid

👉 Ini adalah pola umum di problem **math + greedy + invariant**

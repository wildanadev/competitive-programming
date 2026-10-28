# 914. X of a Kind in a Deck of Cards

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Math, Counting, Number Theory
- **Link**: [Problem](https://leetcode.com/problems/x-of-a-kind-in-a-deck-of-cards/)
- **Solution**: [Code](../../leetcode/XOfAKindInADeckOfCards.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `deck` berisi nilai kartu. Kamu ingin membagi **seluruh** kartu ke dalam satu atau lebih grup dengan syarat:

- Tiap grup berisi **tepat `X` kartu**, dengan `X >= 2`.
- **Semua kartu di dalam satu grup bernilai sama.**
- Nilai `X` **sama** untuk semua grup.

Kembalikan `true` kalau pembagian seperti itu mungkin.

Contoh:

- `deck = [1,2,3,4,4,3,2,1]` → `true` (`X = 2`: `[1,1]`, `[2,2]`, `[3,3]`, `[4,4]`)
- `deck = [1,1,1,2,2,2,3,3]` → `false` (tidak ada `X >= 2` yang cocok untuk semua nilai)

______________________________________________________________________

## 💡 Intuition

Karena kartu dalam satu grup harus bernilai sama, kita bisa memikirkan tiap **nilai** secara terpisah. Misalkan nilai `v` muncul `c(v)` kali. Semua `c(v)` kartu bernilai `v` harus habis dibagi ke grup-grup berukuran `X`, artinya **`X` harus membagi habis `c(v)`**. Karena `X` sama untuk semua nilai, syaratnya:

> `X` harus menjadi **pembagi persekutuan** dari semua `c(v)`, dengan `X >= 2`.

Pembagi persekutuan terbesar dari sekumpulan bilangan adalah **GCD** mereka, dan semua pembagi persekutuan lainnya adalah pembagi dari GCD itu. Jadi:

- Kalau `GCD >= 2`, ambil `X = GCD`, pembagian valid.
- Kalau `GCD == 1`, satu-satunya pembagi persekutuan adalah `1`, yang tidak diperbolehkan (`X >= 2`), jadi tidak ada pembagian valid.

Seluruh soal menyusut menjadi: **hitung frekuensi tiap nilai, lalu cek apakah GCD semua frekuensi lebih dari `1`.**

______________________________________________________________________

## 🔍 Approach

### Hitung Frekuensi, Lalu GCD dari Semua Frekuensi

1. Bangun `count` (`HashMap`): untuk tiap kartu, naikkan hitungannya.
1. `res = 0` (elemen identitas untuk GCD, karena `gcd(x, 0) = x`).
1. Untuk tiap frekuensi `c` di `count.values()`: `res = gcd(c, res)`.
1. Kembalikan `res > 1`.

**Fungsi `gcd(a, b)` (algoritma Euclid):** kalau `b > 0` → `gcd(b, a % b)`, kalau tidak → kembalikan `a`.

**Kenapa `res` dimulai dari `0`?** `gcd(c, 0) = c`, jadi frekuensi pertama langsung menjadi nilai awal `res`, dan frekuensi berikutnya terus "menggerus" GCD-nya. Urutan iterasi `HashMap` tidak mempengaruhi hasil, karena GCD bersifat komutatif dan asosiatif.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------- |
| **Time** | O(n + d × log C) — n untuk menghitung frekuensi, d nilai unik, C frekuensi terbesar; praktis O(n) |
| **Space** | O(d) — `HashMap` frekuensi |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `deck = [1,2,3,4,4,3,2,1]`

Frekuensi: `1 → 2`, `2 → 2`, `3 → 2`, `4 → 2`.

| Frekuensi c | res sebelum | gcd(c, res) | res sesudah |
| ----------- | ----------- | ----------- | ----------- |
| 2 | 0 | 2 | 2 |
| 2 | 2 | 2 | 2 |
| 2 | 2 | 2 | 2 |
| 2 | 2 | 2 | 2 |

`res = 2 > 1` → **Output: `true`** ✅

______________________________________________________________________

**Input:** `deck = [1,1,1,2,2,2,3,3]`

Frekuensi: `1 → 3`, `2 → 3`, `3 → 2`.

| Frekuensi c | res sebelum | gcd(c, res) | res sesudah |
| ----------- | ----------- | ------------------------------------------ | ----------- |
| 3 | 0 | 3 | 3 |
| 3 | 3 | 3 | 3 |
| 2 | 3 | `gcd(2,3) → gcd(3,2) → gcd(2,1) → gcd(1,0) = 1` | 1 |

`res = 1`, `1 > 1` salah → **Output: `false`** ✅

______________________________________________________________________

**Input tambahan:** `deck = [1,1,1,1,2,2,2,2,2,2]` (frekuensi `4` dan `6`)

`gcd(4, 0) = 4`, lalu `gcd(6, 4) = 2` → `res = 2 > 1` → **`true`** (dengan `X = 2`: dua nilai `1` menjadi dua grup `[1,1]`, nilai `2` menjadi tiga grup `[2,2]`).

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu kartu saja (`[1]`) → frekuensi `1`, GCD `1` → `false` (grup minimal harus berukuran `2`)
- [ ] Semua kartu bernilai sama dengan jumlah `>= 2` (`[7,7,7]`) → GCD sama dengan jumlah kartu itu, `>= 2` → `true`
- [ ] Dua kartu sama (`[1,1]`) → GCD `2` → `true`
- [ ] Frekuensi relatif prima (misal `3` dan `5`) → GCD `1` → `false`, walaupun masing-masing frekuensi `>= 2`
- [ ] Ada satu nilai yang cuma muncul sekali → frekuensinya `1` menarik GCD menjadi `1` → `false`
- [ ] Urutan iterasi `HashMap` berbeda → hasil tetap sama karena GCD tidak bergantung urutan

______________________________________________________________________

## 🔧 Kenapa GCD, Bukan Mencoba Setiap `X`?

Pendekatan naif mencoba tiap `X` dari `2` sampai frekuensi terkecil dan mengecek apakah semua frekuensi habis dibagi `X`. Itu benar tapi lebih lambat. GCD merangkum semua kemungkinan `X` sekaligus: `X` valid **jika dan hanya jika** `X` membagi GCD semua frekuensi. Jadi ada `X >= 2` yang valid **jika dan hanya jika** `GCD >= 2` (kalau `GCD >= 2`, ambil `X = GCD`; kalau ada `X >= 2` yang valid, `X` membagi `GCD` sehingga `GCD >= X >= 2`).

______________________________________________________________________

## 🔧 Alternatif: Array Frekuensi dan GCD Iteratif

Kalau constraint menjamin nilai kartu kecil (di sini nilai kurang dari `10^4`), `HashMap` bisa diganti array (direct address table), dan GCD ditulis iteratif dengan berhenti dini saat sudah `1`:

```java
public boolean hasGroupsSizeX(int[] deck) {
    int[] cnt = new int[10000];
    for (int v : deck) cnt[v]++;

    int g = 0;
    for (int c : cnt) {
        if (c == 0) continue;
        g = gcd(g, c);
        if (g == 1) return false;     // berhenti dini: tidak mungkin lagi >= 2
    }
    return g >= 2;
}

private int gcd(int a, int b) {
    while (b != 0) {
        int t = a % b;
        a = b;
        b = t;
    }
    return a;
}
```

Dua perbaikan kecil: akses array `O(1)` tanpa hashing, dan **early exit** begitu GCD menyentuh `1` (nilainya tidak akan naik lagi). GCD iteratif juga menghindari kedalaman rekursi (walaupun untuk `Euclid` kedalamannya hanya `O(log)`).

| Approach | Time | Space | Catatan |
| ----------------------------------------- | -------- | --------- | --------------------------------------- |
| `HashMap` dan GCD rekursif (kode asli) | O(n) | O(d) | Tidak bergantung pada rentang nilai |
| Array frekuensi dan GCD iteratif, early exit | O(n) | O(10000) | Lebih cepat, bergantung pada constraint nilai |

______________________________________________________________________

## 📌 Key Takeaway

Soal pembagian ke grup berukuran sama sering berujung pada **GCD**: ketika sebuah ukuran `X` harus membagi habis sejumlah hitungan sekaligus, himpunan `X` yang valid adalah pembagi persekutuan semuanya, dan keberadaannya (dengan batas `X >= 2`) cukup dicek lewat `GCD >= 2`. Trik memulai `res = 0` dengan `gcd(x, 0) = x` memudahkan menggabungkan GCD dari koleksi tanpa kasus khusus untuk elemen pertama. Pola serupa muncul di soal-soal seperti *Greatest Common Divisor of Strings* dan berbagai soal pembagian kelompok. 🎯

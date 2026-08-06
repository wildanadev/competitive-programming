# 3345. Smallest Divisible Digit Product I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Brute Force
- **Link**: [Problem](https://leetcode.com/problems/smallest-divisible-digit-product-i/)
- **Solution**: [Code](../../leetcode/SmallestDivisibleDigitProductI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua integer `n` dan `t`. Cari **integer terkecil yang lebih besar atau sama dengan `n`** sedemikian sehingga **hasil kali seluruh digitnya habis dibagi `t`**.

Contoh:

- `n = 10, t = 2` → `10` (hasil kali digit `1×0=0`, dan `0` habis dibagi berapa pun, termasuk `2`)
- `n = 15, t = 3` → `16` (`1×5=5` tidak habis dibagi 3, tapi `1×6=6` habis dibagi 3)

______________________________________________________________________

## 💡 Intuition

Constraint soal ini kecil (`n` dan `t` sama-sama dibatasi nilai yang tidak terlalu besar), jadi **brute force sederhana** — coba `n`, kalau tidak memenuhi syarat naikkan ke `n+1`, terus begitu sampai ketemu — sudah cukup efisien. Tidak perlu pendekatan matematis yang rumit (seperti membangun digit dari constraint faktorisasi `t`), karena jarak dari `n` ke jawaban yang valid biasanya tidak jauh mengingat banyak angka yang mengandung digit `0` (yang otomatis membuat produk `0`, selalu habis dibagi apa pun).

Untuk tiap kandidat, cukup hitung **hasil kali semua digitnya** dengan cara ekstraksi digit standar (`num % 10` lalu `num /= 10`), lalu cek keterbagian terhadap `t`.

______________________________________________________________________

## 🔍 Approach

### Brute Force — Increment Sampai Ketemu

1. Loop selama `productOfDigits(n) % t != 0`:
   - Naikkan `n` sebanyak 1 (`n++`).
1. Begitu kondisi terpenuhi, return `n`.

**Helper `productOfDigits(num)`:**

1. Inisialisasi `product = 1`.
1. Loop selama `num != 0`:
   - Kalikan `product *= num % 10` (ambil digit terakhir).
   - `num /= 10` (buang digit terakhir).
1. Return `product`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------------------------- |
| **Time** | O(k × d) — k = jarak dari `n` ke jawaban valid, d = jumlah digit tiap kandidat (kecil, tetap) |
| **Space** | O(1) — hanya variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 10, t = 2`

- Cek `n = 10`: `productOfDigits(10) = 1 × 0 = 0`. `0 % 2 == 0` → syarat langsung terpenuhi (angka mengandung digit `0`, jadi produknya pasti `0`, dan `0` selalu habis dibagi apapun kecuali `0` sendiri).

**Output: `10`** ✅

______________________________________________________________________

**Input:** `n = 15, t = 3`

| Kandidat | Digit | Produk | Produk % 3 | Valid? |
| -------- | ----- | ------ | ---------- | ------ |
| 15 | 1, 5 | 5 | 2 | tidak |
| 16 | 1, 6 | 6 | 0 | **ya** |

**Output: `16`** ✅

______________________________________________________________________

**Input:** `n = 5, t = 4`

| Kandidat | Digit | Produk | Produk % 4 | Valid? |
| -------- | ----- | ------ | ---------- | ------ |
| 5 | 5 | 5 | 1 | tidak |
| 6 | 6 | 6 | 2 | tidak |
| 7 | 7 | 7 | 3 | tidak |
| 8 | 8 | 8 | 0 | **ya** |

**Output: `8`**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n` sudah memenuhi syarat dari awal (`n=10, t=2`) → langsung return `n` tanpa iterasi tambahan
- [ ] Kandidat mengandung digit `0` → produk otomatis `0`, dan `0 % t == 0` untuk `t` apapun yang bukan nol, jadi kandidat seperti ini selalu valid dan sering jadi "jalan pintas" untuk brute force berhenti lebih cepat
- [ ] `t = 1` → semua kandidat langsung valid (produk apapun habis dibagi 1), jadi jawabannya selalu `n` itu sendiri
- [ ] `n` dengan banyak digit besar tapi `t` besar juga (misal `t` prima besar yang sulit dicapai lewat perkalian digit 1-9) → brute force tetap jalan, tapi jumlah iterasi bisa lebih banyak dibanding kasus kecil — tetap dalam batas wajar karena constraint soal ini kecil

______________________________________________________________________

## 🔧 Kenapa Brute Force Cukup di Sini (Bukan di Versi II)?

Soal ini punya versi lanjutan (**Smallest Divisible Digit Product II**) dengan constraint `n` dan `t` yang jauh lebih besar, sampai membuat brute force per-angka menjadi terlalu lambat. Di versi II, dibutuhkan pendekatan digit DP atau konstruksi angka berbasis faktorisasi `t` menjadi digit 1-9. Tapi di versi **I** ini, constraint dijaga cukup kecil sehingga pendekatan paling sederhana — coba tiap angka satu-satu mulai dari `n` — sudah cukup cepat tanpa perlu optimasi lebih lanjut.

| Versi | Constraint | Pendekatan yang Cukup |
| ------------ | ---------- | ------------------------------------------- |
| I (soal ini) | Kecil | Brute force per-angka |
| II | Besar | Digit DP / konstruksi angka dari faktor `t` |

______________________________________________________________________

## 🔧 Alternatif: Precompute Produk Digit Sekali, Update Incremental

Alih-alih memanggil `productOfDigits` penuh dari nol setiap kali `n` bertambah 1, sebagian solusi mencoba mengupdate produk secara incremental (hanya digit terakhir yang berubah kalau tidak ada carry). Tapi pendekatan ini jadi rumit begitu ada carry (misal dari `19` ke `20`, semua digit berubah), sehingga untuk constraint sekecil soal ini, **menghitung ulang penuh tiap kandidat** (seperti kode asli) tetap pilihan paling sederhana dan cukup cepat — tidak sepadan menambah kompleksitas untuk penghematan yang minim.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengingat bahwa **brute force yang jelas benar** sering kali sudah cukup ketika constraint soal memang dirancang kecil — jangan buru-buru mencari pendekatan matematis yang rumit sebelum memastikan pendekatan sederhana benar-benar tidak cukup. Perhatikan juga sifat khusus digit `0`: begitu sebuah angka mengandung digit `0`, produk digitnya otomatis `0`, yang selalu habis dibagi bilangan apapun — ini sering jadi alasan kenapa jawaban brute force biasanya ditemukan tidak terlalu jauh dari `n`. 🎯

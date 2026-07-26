# 4000. Largest Integer With Given Digit Sum

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Math, Greedy
- **Link**: [Problem](https://leetcode.com/problems/largest-integer-with-given-digit-sum/)
- **Solution**: [Code](../../leetcode/LargestIntegerWithGivenDigitSum.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua integer non-negatif `n` dan `s`. Kembalikan **integer terbesar** yang punya **paling banyak `n` digit** dan **jumlah digitnya sama dengan `s`**. Kalau tidak ada bilangan seperti itu, kembalikan `-1`.

Contoh:

- `n = 2, s = 9` → `90` (2 digit, sum = 9, dan ini yang terbesar)
- `n = 2, s = 19` → `-1` (jumlah digit maksimum untuk 2 digit hanya `9+9=18`, jadi 19 mustahil)
- `n = 5, s = 0` → `0` (satu-satunya bilangan non-negatif dengan digit sum 0 adalah 0 sendiri)

______________________________________________________________________

## 💡 Intuition

Ini soal **greedy klasik**: untuk membuat bilangan sebesar mungkin dengan jumlah digit tertentu dan total digit sum tetap, digit **paling kiri (paling signifikan)** harus dibuat sebesar mungkin lebih dulu.

Karena digit maksimal adalah `9`, strategi optimalnya:

1. Isi sebanyak mungkin digit dengan `9` (dari kiri), selama sisa `s` masih cukup.
1. Begitu sisa `s` kurang dari `9`, taruh sisa itu sebagai satu digit (bukan `9`).
1. Sisa slot digit yang belum terpakai (jika ada) diisi `0` — karena `0` tidak menambah nilai jumlah digit tapi tetap menambah "panjang" angka dari kanan, dan menaruh angka besar sejauh mungkin ke kiri selalu lebih besar daripada menaruhnya lebih ke kanan.

Sebelum itu semua, ada dua pengecekan validitas:

- Kalau `s` melebihi jumlah digit maksimum yang bisa dibentuk (`9 * n`), maka **mustahil** → return `-1`.
- Kalau `s == 0`, satu-satunya jawaban valid adalah `0` (bukan `000..0` dengan banyak digit, walau secara numerik sama saja).

______________________________________________________________________

## 🔍 Approach

### Greedy — Isi 9 dari Kiri, Sisa di Akhir, Sisanya Nol

1. **Validasi**: jika `s > n * 9` → return `-1`. Jika `s == 0` → return `0`.
1. `count = s / 9` → banyaknya digit `9` yang bisa dipakai penuh.
1. Selama `count > 0`: tambahkan `"9"` ke `StringBuilder`, kurangi `n` (karena satu slot digit terpakai), dan kurangi `count`.
1. Jika masih ada sisa slot digit (`n > 0`):
   - Tambahkan sisa `s % 9` sebagai satu digit (`s - (s/9*9)` sama dengan `s % 9`).
   - Kurangi `n` satu lagi.
   - Isi sisa slot yang tersisa (`n` sekarang) dengan `"0"` satu per satu.
1. Parse `StringBuilder` menjadi integer dan kembalikan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------- |
| **Time** | O(n) — n ≤ 5 sesuai constraint, jadi praktis O(1) |
| **Space** | O(n) — untuk `StringBuilder`, praktis O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 2, s = 9`

- `s (9) <= n*9 (18)` ✅, `s != 0`.
- `count = 9 / 9 = 1`.
- Loop: append `"9"` → `sb = "9"`, `n = 1`, `count = 0` (berhenti).
- `n = 1 > 0`: sisa `= 9 - (9/9*9) = 9 - 9 = 0` → append `"0"` → `sb = "90"`, `n = 0`.
- Tidak ada slot nol tambahan (`n = 0`).
- Parse → `90` ✅

______________________________________________________________________

**Input:** `n = 3, s = 11`

| Langkah | Aksi | sb | n |
| -------------------- | ------------------------------- | ----- | --- |
| Cek validitas | `11 <= 27` ✅, `s != 0` | "" | 3 |
| `count = 11/9 = 1` | append "9" | "9" | 2 |
| Loop count selesai | (count = 0, berhenti) | "9" | 2 |
| `n=2>0` | sisa `= 11 - 9 = 2`, append "2" | "92" | 1 |
| Isi nol sisa (`n=1`) | append "0" satu kali | "920" | 0 |

**Output: `920`** — digit sum `9+2+0=11`, dan ini memang bilangan 3-digit terbesar dengan sum 11 (lebih besar dari kandidat lain seperti `911`, `902`, dst).

______________________________________________________________________

**Input:** `n = 2, s = 19`

- Cek: `19 > 2*9=18` → langsung return `-1` ✅ (tidak masuk ke logika greedy sama sekali).

______________________________________________________________________

**Input:** `n = 5, s = 0`

- Cek: `s == 0` → langsung return `0` ✅ (bypass logika greedy yang kalau dijalankan tanpa pengecekan ini akan menghasilkan string kosong lalu error saat `Integer.parseInt`, karena `count = 0/9 = 0` dan blok `if (n > 0)` juga tidak pernah terpicu untuk kasus s=0 tanpa perlakuan khusus... makanya early-return ini penting).

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s == 0` → wajib early-return `0`, bukan dibiarkan lewat logika greedy biasa
- [ ] `s > n * 9` → mustahil dibentuk, return `-1` sebelum logika lain dijalankan
- [ ] `s` kelipatan `9` dan pas habis mengisi semua `n` digit (`count == n`) → semua digit jadi `9`, tidak ada sisa slot untuk digit remainder maupun nol (contoh: `n=2, s=18` → `"99"`)
- [ ] `s` kelipatan `9` tapi masih ada sisa slot (`count < n`) → remainder-nya `0`, jadi tetap muncul sebagai digit `"0"` tambahan sebelum sisa nol lain (contoh: `n=3, s=9` → `"900"`)
- [ ] `n = 1` → hanya ada satu slot digit, jadi hasilnya langsung `s` itu sendiri (asal `s <= 9`)

______________________________________________________________________

## 🔧 Kenapa Isi 9 Duluan (dari Kiri) Itu Optimal?

Untuk dua bilangan dengan jumlah digit yang sama, bilangan dengan **digit lebih besar di posisi lebih signifikan (kiri)** selalu lebih besar — sama seperti membandingkan angka secara leksikografis. Karena `9` adalah digit terbesar yang mungkin, menaruh sebanyak mungkin `9` di posisi paling kiri sambil tetap menyisakan cukup `s` untuk digit-digit berikutnya adalah pilihan yang selalu optimal — tidak ada cara lain menghasilkan angka lebih besar dengan digit sum dan jumlah digit yang sama. Setelah `9` habis dipakai, sisa `s % 9 < 9` diletakkan di posisi berikutnya (posisi ini otomatis jadi yang terbesar mungkin untuk slot itu, karena semua `9` sudah dipakai duluan), dan posisi-posisi sisanya diisi `0` supaya tidak menambah panjang angka secara tidak perlu maupun mengurangi digit sum yang sudah pas.

______________________________________________________________________

## 🔧 Alternatif: Greedy dengan Array of char / int[]

```java
public int largestInteger(int n, int s) {
    if (s > 9 * n) return -1;
    int[] digits = new int[n];
    for (int i = 0; i < n && s > 0; i++) {
        int d = Math.min(9, s);
        digits[i] = d;
        s -= d;
    }
    StringBuilder sb = new StringBuilder();
    for (int d : digits) sb.append(d);
    return Integer.parseInt(sb.toString());
}
```

Versi ini menggabungkan pengisian `9` dan sisa remainder dalam satu loop yang sama (tanpa perlu membagi jadi dua fase `count` dan `n>0`), sedikit lebih ringkas meski secara ide sama persis: isi digit sebesar mungkin dari kiri sampai `s` habis, sisanya otomatis `0` karena default array Java.

| Approach | Time | Space |
| --------------------------------------------- | ---- | ----- |
| Two-phase (count 9 lalu sisa+nol) — kode asli | O(n) | O(n) |
| Single loop dengan `Math.min` | O(n) | O(n) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi dari pola **"greedy dari digit paling signifikan"** — untuk memaksimalkan nilai suatu angka dengan panjang tetap, selalu maksimalkan digit yang paling kiri terlebih dahulu selama constraint masih terpenuhi. Pola serupa muncul di soal-soal seperti _Remove K Digits_, _Monotone Increasing Digits_, dan _Form Largest Integer With Digits That Add up to Target_ — semuanya berputar di sekitar ide "posisi lebih signifikan harus dioptimalkan lebih dulu". 🎯

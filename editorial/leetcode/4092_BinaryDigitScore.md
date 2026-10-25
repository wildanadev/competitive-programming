# 4092. Binary Digit Score

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Bit Manipulation, Math
- **Link**: [Problem](https://leetcode.com/problems/binary-digit-score/)
- **Solution**: [Code](../../leetcode/BinaryDigitScore.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua integer `a` dan `b`. Misalkan `s1` dan `s2` adalah **jumlah bit `1` (set bit)** pada representasi biner `a` dan `b`. **Skor** keduanya didefinisikan:

- Kalau `s1 >= s2`, skornya `s1 * s2`.
- Kalau tidak (`s1 < s2`), skornya `s2 / s1` dengan **pembagian bilangan bulat**.

Kembalikan skornya.

Contoh:

- `a = 14, b = 6` → `6` (`14 = 1110` punya `s1 = 3`, `6 = 110` punya `s2 = 2`; `s1 > s2`, jadi `3 * 2 = 6`)
- `a = 5, b = 15` → `2` (`5 = 101` punya `s1 = 2`, `15 = 1111` punya `s2 = 4`; `s1 < s2`, jadi `4 / 2 = 2`)
- `a = 10, b = 12` → `4` (`10 = 1010` dan `12 = 1100` sama-sama `2` set bit; `s1 == s2`, jadi `2 * 2 = 4`)

______________________________________________________________________

## 💡 Intuition

Soal ini langsung menerjemahkan definisi jadi kode, dengan dua bagian:

1. **Hitung set bit** tiap bilangan. Ini operasi klasik _Hamming weight_ (juga dikenal dari soal _Number of 1 Bits_).
1. **Pilih rumus** berdasarkan perbandingan `s1` dan `s2`, lalu hitung skornya.

Untuk menghitung set bit, kode memakai teknik paling dasar: ambil bit terendah lewat `value % 2` (hasilnya `1` kalau ganjil, berarti bit terendah menyala), tambahkan ke hitungan, lalu buang bit itu dengan `value /= 2` (sama dengan geser kanan satu bit). Ulangi sampai `value` habis.

______________________________________________________________________

## 🔍 Approach

### Hitung Set Bit, Lalu Pilih Rumus

**Fungsi `setBitBinary(value)`:**

1. `count = 0`.
1. Selama `value > 0`:
   - Kalau `value % 2 != 0` (bit terendah `1`) → `count++`.
   - `value /= 2` (membuang bit terendah).
1. Kembalikan `count`.

**Fungsi `calculateScore(a, b)`:**

1. `s1 = setBitBinary(a)`, `s2 = setBitBinary(b)`.
1. Kalau `s1 >= s2` → `ans = s1 * s2`.
1. Kalau tidak → `ans = s2 / s1`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------------- |
| **Time** | O(log a + log b) — banyaknya bit tiap bilangan, paling banyak 31 untuk `int` positif, jadi praktis O(1) |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Menghitung set bit `14`** (`1110`):

| value | value % 2 | count sesudah | value sesudah `/= 2` |
| ----- | --------- | ------------- | -------------------- |
| 14 | 0 | 0 | 7 |
| 7 | 1 | 1 | 3 |
| 3 | 1 | 2 | 1 |
| 1 | 1 | 3 | 0 |

`setBitBinary(14) = 3`.

______________________________________________________________________

**Input:** `a = 14, b = 6`

`s1 = 3` (`1110`), `s2 = 2` (`110`). `3 >= 2` → `3 * 2 = 6`.

**Output: `6`** ✅

______________________________________________________________________

**Input:** `a = 5, b = 15`

`s1 = 2` (`101`), `s2 = 4` (`1111`). `2 >= 4` salah → `4 / 2 = 2`.

**Output: `2`** ✅

______________________________________________________________________

**Input:** `a = 10, b = 12`

`s1 = 2` (`1010`), `s2 = 2` (`1100`). `2 >= 2` benar → `2 * 2 = 4`.

**Output: `4`** ✅

______________________________________________________________________

**Input tambahan (pembagian bulat memotong sisa):** `a = 3, b = 31`

`s1 = 2` (`11`), `s2 = 5` (`11111`). `2 >= 5` salah → `5 / 2 = 2` (pembagian bilangan bulat, sisa `1` dibuang).

**Output: `2`**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s1 == s2` → masuk cabang perkalian (`>=`), hasilnya `s1 * s2 = s1²`, bukan pembagian (yang akan memberi `1`)
- [ ] `s1 < s2` dengan hasil bagi tidak bulat (`5 / 2`) → dipotong ke bawah jadi `2`, sesuai "integer division" di soal
- [ ] `a` hanya punya satu set bit (pangkat dua, misal `8 = 1000`) → `s1 = 1`, jadi `s2 / 1 = s2` kalau `s2 > 1`
- [ ] **Pembagian dengan nol** → cabang `s2 / s1` aman selama `s1 >= 1`, yaitu `a >= 1`. Kalau `a = 0` maka `s1 = 0`, dan kalau `s2 > 0` terjadi `ArithmeticException`. Cek bagian **Constraints** di halaman soal untuk memastikan `a` tidak bisa `0` (di kode ini tidak ada penanganan khusus untuk kasus itu)
- [ ] Hasil kali maksimum → set bit paling banyak `31` untuk `int` positif, jadi `31 * 31 = 961`, jauh dari batas `int`

______________________________________________________________________

## 🔧 Alternatif 1: `Integer.bitCount`

Java sudah menyediakan fungsi bawaan untuk menghitung set bit:

```java
public int calculateScore(int a, int b) {
    int s1 = Integer.bitCount(a);
    int s2 = Integer.bitCount(b);
    return s1 >= s2 ? s1 * s2 : s2 / s1;
}
```

`Integer.bitCount` biasanya dikompilasi JVM menjadi satu instruksi CPU (`popcnt`) dan juga menangani bilangan negatif dengan benar (menghitung semua 32 bit), sedangkan loop `while (value > 0)` di kode asli akan mengembalikan `0` untuk bilangan negatif. Untuk input positif keduanya sama.

______________________________________________________________________

## 🔧 Alternatif 2: Brian Kernighan (Loop Sebanyak Set Bit)

```java
private int countSetBits(int value) {
    int count = 0;
    while (value != 0) {
        value &= value - 1;   // menghapus set bit paling kanan
        count++;
    }
    return count;
}
```

Operasi `value & (value - 1)` selalu **menghapus tepat satu set bit** (yang paling kanan), jadi loop berjalan sebanyak jumlah set bit, bukan sebanyak jumlah bit total. Contoh `14 = 1110`: `1110 & 1101 = 1100`, lalu `1100 & 1011 = 1000`, lalu `1000 & 0111 = 0000`, tiga iterasi, hasilnya `3` ✅. Untuk bilangan yang set bit-nya sedikit tapi bit-nya panjang (misal `2^30`), ini jauh lebih cepat; untuk soal ini selisihnya tidak terasa.

| Approach | Time | Space | Catatan |
| ------------------------------------ | ------------- | ----- | ---------------------------------- |
| `% 2` dan `/ 2` (kode asli) | O(jumlah bit) | O(1) | Paling mudah dibayangkan |
| `Integer.bitCount` | O(1) | O(1) | Paling ringkas, instruksi hardware |
| Brian Kernighan `value &= value - 1` | O(set bit) | O(1) | Loop hanya sebanyak bit `1` |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan dasar **menghitung set bit** (Hamming weight) yang dikombinasikan dengan percabangan sederhana. Tiga cara menghitungnya (`% 2` dan `/ 2`, Brian Kernighan, dan `Integer.bitCount`) sama-sama valid, dengan trade-off antara kemudahan dipahami dan kecepatan. Satu hal yang perlu diwaspadai adalah **pembagian dengan nol** pada cabang `s2 / s1`: aman hanya jika `s1 >= 1`, jadi pastikan constraint soal menjamin `a >= 1`. Teknik menghitung set bit ini juga jadi fondasi untuk soal-soal seperti _Number of 1 Bits_, _Counting Bits_, dan _Hamming Distance_. 🎯

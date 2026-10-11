# 2778. Sum of Squares of Special Elements

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Enumeration, Math
- **Link**: [Problem](https://leetcode.com/problems/sum-of-squares-of-special-elements/)
- **Solution**: [Code](../../leetcode/SumOfSquaresOfSpecialElements.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` berindeks **1** (indeks pertama adalah `1`, bukan `0`) dengan panjang `n`. Elemen `nums[i]` disebut **special** kalau `i` **membagi habis** `n` (`n % i == 0`). Kembalikan **jumlah kuadrat** dari semua elemen special.

Contoh:

- `nums = [1,2,3,4]` → `21`
  - `n = 4`. Indeks yang membagi `4`: `1`, `2`, `4`.
  - `1² + 2² + 4² = 1 + 4 + 16 = 21`
- `nums = [2,7,1,19,18,3]` → `63`
  - `n = 6`. Indeks yang membagi `6`: `1`, `2`, `3`, `6`.
  - `2² + 7² + 1² + 3² = 4 + 49 + 1 + 9 = 63`

______________________________________________________________________

## 💡 Intuition

Perhatikan bahwa status "special" **hanya bergantung pada posisi** elemen, bukan nilainya. Jadi soal ini murni:

1. Untuk tiap posisi `i` dari `1` sampai `n`, cek apakah `n % i == 0`.
1. Kalau ya, tambahkan kuadrat elemen di posisi itu.

Satu jebakan yang perlu dijaga: soal memakai indeks **berbasis 1**, sedangkan array Java berbasis **0**. Posisi "ke-`i`" pada soal adalah `nums[i - 1]` di Java. Solusi ini menanganinya dengan loop `i` dari `0` dan memakai `(i + 1)` sebagai posisi berbasis 1 saat mengecek keterbagian.

______________________________________________________________________

## 🔍 Approach

### Enumerasi Semua Posisi, Cek Keterbagian

1. `ans = 0`.
1. Loop `i` dari `0` sampai `nums.length - 1` (indeks Java berbasis 0):
   - Posisi berbasis 1 untuk elemen ini adalah `i + 1`.
   - Kalau `nums.length % (i + 1) == 0` → elemen ini special, tambahkan `nums[i] * nums[i]` ke `ans`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------- |
| **Time** | O(n) — satu kali pass, tiap cek `O(1)` |
| **Space** | O(1) — hanya satu variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3,4]` (`n = 4`)

| i (Java) | Posisi i+1 | n % (i+1) | Special? | Menambah ke ans | ans |
| -------- | ---------- | --------- | -------- | --------------- | --- |
| 0 | 1 | 0 | ya | `1 * 1 = 1` | 1 |
| 1 | 2 | 0 | ya | `2 * 2 = 4` | 5 |
| 2 | 3 | 1 | tidak | 0 | 5 |
| 3 | 4 | 0 | ya | `4 * 4 = 16` | 21 |

**Output: `21`** ✅

______________________________________________________________________

**Input:** `nums = [2,7,1,19,18,3]` (`n = 6`)

| i (Java) | Posisi i+1 | n % (i+1) | Special? | Menambah ke ans | ans |
| -------- | ---------- | --------- | -------- | --------------- | --- |
| 0 | 1 | 0 | ya | `2 * 2 = 4` | 4 |
| 1 | 2 | 0 | ya | `7 * 7 = 49` | 53 |
| 2 | 3 | 0 | ya | `1 * 1 = 1` | 54 |
| 3 | 4 | 2 | tidak | 0 | 54 |
| 4 | 5 | 1 | tidak | 0 | 54 |
| 5 | 6 | 0 | ya | `3 * 3 = 9` | 63 |

**Output: `63`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → hanya posisi `1` yang special (`1` membagi `1`), hasilnya `nums[0]²`
- [ ] `n` bilangan prima → hanya posisi `1` dan `n` yang special
- [ ] Posisi `1` dan posisi `n` **selalu** special untuk `n` berapa pun (`n % 1 == 0` dan `n % n == 0`)
- [ ] Nilai elemen terbesar sesuai constraint (`50`) di semua posisi special → jumlah kuadrat maksimal masih jauh di bawah batas `int` (`n` maksimal `50`, jadi paling banyak `50 × 2500 = 125000`), tidak ada risiko overflow

______________________________________________________________________

## 🔧 Kenapa Harus `(i + 1)`, Bukan `i`?

```java
if (nums.length % (i + 1) == 0)
```

Kalau ditulis `nums.length % i`, saat `i = 0` terjadi **pembagian dengan nol** (`n % 0`) yang melempar `ArithmeticException`. Di luar masalah crash itu, secara logika juga salah: soal mendefinisikan special berdasarkan indeks **berbasis 1**, jadi elemen pertama (`nums[0]`) punya posisi `1`, bukan `0`. `(i + 1)` mengonversi indeks Java berbasis 0 ke posisi berbasis 1 yang dimaksud soal. Kesalahan konversi indeks seperti ini umum terjadi tiap kali soal memakai 1-indexed sementara bahasa pemrograman memakai 0-indexed (pola serupa muncul di _Reverse Degree of a String_, yang juga mengalikan dengan posisi berbasis 1).

______________________________________________________________________

## 🔧 Alternatif: Enumerasi Pembagi Sampai Akar `n` (O(√n))

Karena yang dibutuhkan hanya posisi yang **membagi `n`**, kita tidak perlu memeriksa semua posisi. Pembagi `n` selalu berpasangan: kalau `d` membagi `n`, maka `n / d` juga membagi `n`. Jadi cukup periksa `d` sampai `√n`:

```java
public int sumOfSquares(int[] nums) {
    int n = nums.length;
    int ans = 0;
    for (int d = 1; d * d <= n; d++) {
        if (n % d == 0) {
            ans += nums[d - 1] * nums[d - 1];     // posisi d
            int pair = n / d;
            if (pair != d)                          // hindari hitung dua kali kalau d * d == n
                ans += nums[pair - 1] * nums[pair - 1]; // posisi n / d
        }
    }
    return ans;
}
```

Untuk `n = 6`: `d=1` menambahkan posisi `1` dan `6`; `d=2` menambahkan posisi `2` dan `3`; `d=3` berhenti karena `3*3 > 6`. Hasilnya sama persis dengan versi asli. Cek `pair != d` penting untuk bilangan kuadrat sempurna (misal `n = 4`, `d = 2`, `pair = 2`), supaya posisi `2` tidak dihitung dua kali.

| Approach | Time | Space | Catatan |
| ----------------------------- | ----- | ----- | ------------------------------------------- |
| Cek semua posisi (kode asli) | O(n) | O(1) | Sederhana dan jelas |
| Enumerasi pembagi sampai `√n` | O(√n) | O(1) | Lebih cepat, perlu waspada kuadrat sempurna |

Untuk constraint soal ini (`n` maksimal `50`), keduanya instan, jadi versi asli yang lebih sederhana sudah tepat.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini sederhana, tapi mengingatkan dua hal yang sering bikin bug kecil: **konversi indeks berbasis 1 ke berbasis 0** (selalu pakai `i + 1` saat soal bicara tentang "posisi ke-`i`" dan loop kamu mulai dari `0`), dan **keterbagian sebagai pengecekan posisi** (`n % posisi == 0`). Pola enumerasi pembagi sampai `√n` adalah trik umum yang menurunkan pengecekan keterbagian dari `O(n)` jadi `O(√n)`, berguna di soal-soal yang constraint-nya lebih besar. 🎯

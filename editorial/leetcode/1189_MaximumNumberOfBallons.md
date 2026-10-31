# 1189. Maximum Number of Balloons

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, String, Counting
- **Link**: [Problem](https://leetcode.com/problems/maximum-number-of-balloons/)
- **Solution**: [Code](../../leetcode/MaximumNumberOfBalloons.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `text` (huruf kecil). Kamu ingin menyusun kata **`"balloon"`** sebanyak mungkin dari huruf-huruf di `text`. **Setiap huruf di `text` hanya boleh dipakai sekali.** Kembalikan jumlah maksimum kata `"balloon"` yang bisa dibentuk.

Contoh:

- `text = "nlaebolko"` → `1`
- `text = "loonbalxballpoon"` → `2`
- `text = "leetcode"` → `0`

______________________________________________________________________

## 💡 Intuition

Satu kata `"balloon"` butuh huruf-huruf berikut:

| Huruf | Dibutuhkan per kata |
| ----- | ------------------- |
| `b` | 1 |
| `a` | 1 |
| `l` | **2** |
| `o` | **2** |
| `n` | 1 |

Perhatikan bahwa `l` dan `o` masing-masing dibutuhkan **dua kali** dalam satu kata. Ini mirip resep: jumlah kata yang bisa dibuat dibatasi oleh **bahan yang paling cepat habis** (bottleneck).

- `b` yang tersedia `x` buah → paling banyak `x` kata.
- `a` yang tersedia → paling banyak segitu kata.
- `l` yang tersedia `y` buah → paling banyak `y / 2` kata (pembagian bulat, karena tiap kata menghabiskan dua `l`).
- `o` yang tersedia → paling banyak `o / 2` kata.
- `n` yang tersedia → paling banyak segitu kata.

Jawabannya adalah **yang terkecil** di antara kelima batas itu, karena kata baru bisa dibuat kalau **semua** bahan tercukupi.

______________________________________________________________________

## 🔍 Approach

### Hitung Frekuensi Huruf, Lalu Ambil Minimum dari Lima Batas

1. `freq = new char[26]` (array penghitung huruf `a` sampai `z`). Untuk tiap huruf di `text`, naikkan `freq[huruf - 'a']`.
1. `ans = freq['b']`.
1. `ans = min(ans, freq['a'])`.
1. `ans = min(ans, freq['l'] / 2)`.
1. `ans = min(ans, freq['o'] / 2)`.
1. `ans = min(ans, freq['n'])`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(n) — satu kali pass untuk menghitung frekuensi, sisanya konstan |
| **Space** | O(1) — array berukuran tetap `26` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `text = "loonbalxballpoon"`

Frekuensi huruf yang relevan: `b = 2`, `a = 2`, `l = 4`, `o = 4`, `n = 2`.

| Huruf | Tersedia | Dibutuhkan per kata | Batas jumlah kata |
| ----- | -------- | ------------------- | ----------------- |
| `b` | 2 | 1 | `2 / 1 = 2` |
| `a` | 2 | 1 | `2 / 1 = 2` |
| `l` | 4 | 2 | `4 / 2 = 2` |
| `o` | 4 | 2 | `4 / 2 = 2` |
| `n` | 2 | 1 | `2 / 1 = 2` |

Minimum kelimanya: `2`.

**Output: `2`** ✅

______________________________________________________________________

**Input:** `text = "nlaebolko"`

Frekuensi: `b = 1`, `a = 1`, `l = 2`, `o = 2`, `n = 1` (huruf `e` dan `k` tidak relevan).

Batas: `1`, `1`, `2 / 2 = 1`, `2 / 2 = 1`, `1`. Minimum: `1`.

**Output: `1`** ✅

______________________________________________________________________

**Input:** `text = "leetcode"`

`b = 0` (tidak ada huruf `b`), jadi `ans = 0` sejak awal dan tetap `0`.

**Output: `0`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Ada huruf yang sama sekali tidak muncul (misal tidak ada `b`) → batas untuk huruf itu `0`, jawabannya `0`
- [ ] `l` atau `o` hanya muncul **sekali** → `1 / 2 = 0` (pembagian bulat), jawabannya `0`, karena satu `"balloon"` butuh dua
- [ ] `l` atau `o` berjumlah ganjil (misal `l = 3`) → `3 / 2 = 1`, satu `l` sisa tidak terpakai
- [ ] `text` lebih pendek dari `7` karakter → mustahil membentuk `"balloon"`, hasil `0`
- [ ] Banyak huruf lain yang tidak relevan (`e`, `k`, `x`, `p`) → diabaikan, tidak mempengaruhi hasil
- [ ] Jumlah huruf melimpah untuk semua bahan kecuali satu → bahan yang tersedikit menentukan jawaban

______________________________________________________________________

## 🔧 Catatan: `char[]` sebagai Penghitung

Kode ini memakai `char[] freq`, bukan `int[] freq`. Ini **berfungsi**: tipe `char` di Java adalah bilangan bulat tak bertanda 16-bit (`0` sampai `65535`), sehingga `freq[x]++` menambah nilainya, dan karena `text.length() <= 10^4` hitungan tidak akan melebihi `65535`. Pembagian dan `Math.min` juga bekerja karena `char` otomatis dipromosikan jadi `int`.

Tapi konvensi yang lebih umum dan lebih aman adalah `int[]`. Dua alasannya:

- `char` **tak bertanda**, jadi mengurangi nilai di bawah `0` akan "melingkar" ke `65535` alih-alih menjadi negatif (tidak terjadi di soal ini, tapi jadi jebakan kalau kodenya dimodifikasi untuk mengurangi hitungan).
- Pembaca kode mengasosiasikan `char[]` dengan array karakter, bukan array hitungan, sehingga `int[]` lebih jelas maksudnya.

______________________________________________________________________

## 🔧 Alternatif: Rumus Umum (Resep dan Rasio Minimum)

Alih-alih menulis lima baris `Math.min` khusus untuk `"balloon"`, kita bisa menghitung **kebutuhan per kata** dari kata target, lalu mengambil minimum rasio `tersedia / dibutuhkan` untuk setiap huruf yang dibutuhkan:

```java
public int maxNumberOfBalloons(String text) {
    int[] have = new int[26];
    int[] need = new int[26];
    for (char c : text.toCharArray()) have[c - 'a']++;
    for (char c : "balloon".toCharArray()) need[c - 'a']++;

    int ans = Integer.MAX_VALUE;
    for (int i = 0; i < 26; i++)
        if (need[i] > 0)
            ans = Math.min(ans, have[i] / need[i]);
    return ans;
}
```

Versi ini tidak perlu mengingat bahwa `l` dan `o` butuh dua; informasinya diambil otomatis dari kata `"balloon"` itu sendiri. Cek `"loonbalxballpoon"`: `have` untuk `b,a,l,o,n` adalah `2,2,4,4,2`, `need` adalah `1,1,2,2,1`, rasionya semua `2`, minimum `2` ✅. Cek `"leetcode"`: `have['b'] = 0`, rasionya `0`, minimum `0` ✅.

Keuntungannya, mengganti kata target hanya butuh mengubah satu string (misal `"banana"`), dan inilah soal yang digeneralisasi di *Rearrange Characters to Make Target String*.

| Approach | Time | Space | Catatan |
| -------------------------------------- | ---- | ----- | ---------------------------------------- |
| Lima `Math.min` khusus (kode asli) | O(n) | O(1) | Sederhana dan cepat untuk satu kata tetap |
| Rasio minimum dari `need` dan `have` | O(n) | O(1) | Umum, bisa dipakai untuk kata target apa pun |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola **bottleneck** (faktor pembatas): jumlah hasil yang bisa dibuat ditentukan oleh bahan dengan rasio `tersedia / dibutuhkan` yang **paling kecil**. Perhatikan detail bahwa sebagian huruf (`l` dan `o`) dibutuhkan lebih dari sekali, sehingga stok harus **dibagi** dengan jumlah kebutuhan per kata, dengan pembagian bulat yang otomatis membuang sisa yang tidak cukup untuk satu kata utuh. Pola "hitung frekuensi lalu ambil minimum rasio" ini muncul di banyak soal resep atau perakitan, dan menjadi dasar soal pengembangan seperti *Rearrange Characters to Make Target String*. 🎯

# 566. Reshape the Matrix

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Matrix, Simulation
- **Link**: [Problem](https://leetcode.com/problems/reshape-the-matrix/)
- **Solution**: [Code](../../leetcode/ReshapeTheMatrix.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan matrix `mat` berukuran `m x n`, dan dua integer `r` dan `c`. Bentuk ulang (reshape) matrix tersebut menjadi ukuran `r x c` baru, dengan **elemen-elemennya tetap sama** dan **urutannya tetap terjaga** (dibaca baris demi baris, dari kiri ke kanan, atas ke bawah).

Kalau reshape **tidak memungkinkan** (jumlah total elemen tidak cocok), kembalikan matrix asli tanpa perubahan.

Contoh:

- `mat = [[1,2],[3,4]], r = 1, c = 4` → `[[1,2,3,4]]`
- `mat = [[1,2],[3,4]], r = 2, c = 4` → `[[1,2],[3,4]]` (tidak valid: `2×2=4` elemen, tapi `2×4=8` slot dibutuhkan → return matrix asli)

______________________________________________________________________

## 💡 Intuition

Reshape matrix pada dasarnya adalah masalah **"perataan lalu pembentukan ulang"** (flatten, then reshape): kalau kita bayangkan seluruh elemen matrix asli dijejer jadi satu **urutan linear** (baris demi baris), lalu urutan linear itu dipotong-potong ulang menjadi baris-baris baru sepanjang `c`, hasilnya persis reshape yang diminta.

Triknya: kita **tidak perlu benar-benar membuat array 1D perantara**. Cukup gunakan **satu indeks linear `i`** (dari `0` sampai `total-1`) yang merepresentasikan "elemen ke-berapa" dalam urutan pembacaan, lalu **konversi** indeks linear itu ke koordinat 2D di kedua matrix (asal dan tujuan) memakai pembagian dan modulo:

- Posisi di matrix **asal** (`m x n`): baris `= i / n`, kolom `= i % n`.
- Posisi di matrix **tujuan** (`r x c`): baris `= i / c`, kolom `= i % c`.

Ini bekerja karena baik `i/n, i%n` maupun `i/c, i%c` sama-sama merepresentasikan **elemen linear yang sama** (`i`), hanya "dibungkus" ke lebar baris yang berbeda (`n` untuk asal, `c` untuk tujuan).

______________________________________________________________________

## 🔍 Approach

### Konversi Indeks Linear ↔ 2D

1. Hitung `total = m * n` (total elemen di matrix asal).
1. Kalau `r * c != total` → reshape **tidak valid**, kembalikan `mat` apa adanya.
1. Buat matrix baru `ans` berukuran `r x c`.
1. Loop `i` dari `0` sampai `total - 1`:
   - Ambil elemen dari matrix asal di posisi `mat[i/n][i%n]`.
   - Taruh ke matrix baru di posisi `ans[i/c][i%c]`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------- |
| **Time** | O(m × n) — satu kali pass ke seluruh elemen matrix |
| **Space** | O(r × c) — untuk matrix hasil (sama dengan `m×n` kalau valid) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `mat = [[1,2],[3,4]], r = 1, c = 4`

- `m=2, n=2, total=4`. `r*c = 1*4 = 4 == total` → valid, lanjut.

| i | i/n (baris asal) | i%n (kolom asal) | nilai `mat[i/n][i%n]` | i/c (baris tujuan) | i%c (kolom tujuan) |
| --- | ---------------- | ---------------- | --------------------- | ------------------ | ------------------ |
| 0 | 0 | 0 | 1 | 0 | 0 |
| 1 | 0 | 1 | 2 | 0 | 1 |
| 2 | 1 | 0 | 3 | 0 | 2 |
| 3 | 1 | 1 | 4 | 0 | 3 |

`ans = [[1,2,3,4]]`

**Output: `[[1,2,3,4]]`** ✅

______________________________________________________________________

**Input:** `mat = [[1,2],[3,4]], r = 2, c = 4`

- `m=2, n=2, total=4`. `r*c = 2*4 = 8 != 4` → tidak valid, langsung return `mat` asli.

**Output: `[[1,2],[3,4]]`** ✅

______________________________________________________________________

**Input:** `mat = [[1,2,3],[4,5,6]], r = 3, c = 2`

- `m=2, n=3, total=6`. `r*c=6==total` → valid.

| i | mat[i/3][i%3] | ans[i/2][i%2] |
| --- | ------------- | ------------- |
| 0 | mat[0][0]=1 | ans[0][0]=1 |
| 1 | mat[0][1]=2 | ans[0][1]=2 |
| 2 | mat[0][2]=3 | ans[1][0]=3 |
| 3 | mat[1][0]=4 | ans[1][1]=4 |
| 4 | mat[1][1]=5 | ans[2][0]=5 |
| 5 | mat[1][2]=6 | ans[2][1]=6 |

**Output: `[[1,2],[3,4],[5,6]]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `r*c != m*n` → reshape mustahil, kembalikan `mat` asli tanpa modifikasi
- [ ] `r == m && c == n` → reshape "ke ukuran yang sama", tetap valid, hasilnya identik dengan matrix asli (walau secara teknis membuat objek array baru, bukan reference yang sama)
- [ ] Matrix 1 baris (`m=1`) di-reshape jadi banyak baris, atau sebaliknya → tetap tertangani karena logikanya murni berbasis indeks linear, tidak peduli bentuk asal/tujuan
- [ ] `mat` hanya berisi 1 elemen (`m=1, n=1`) → hanya valid kalau `r=1, c=1` juga, karena `total=1`

______________________________________________________________________

## 🔧 Kenapa Konversi `i/n, i%n` dan `i/c, i%c` Selalu Konsisten?

Ini prinsip dasar konversi antara **indeks linear (1D)** dan **indeks 2D (baris, kolom)** untuk struktur data berbasis grid dengan lebar tetap. Kalau lebar barisnya `w`, maka elemen ke-`i` (0-indexed, dibaca baris demi baris) selalu berada di:

```
baris = i / w   (pembagian bulat, "berapa baris penuh sudah terlewati")
kolom = i % w   (sisa, "posisi di dalam baris saat ini")
```

Karena kedua matrix (asal `n` lebar, tujuan `c` lebar) sama-sama dibaca dengan urutan linear yang **identik** (baris demi baris, kiri ke kanan), elemen ke-`i` di urutan itu **selalu** merujuk ke elemen yang sama, terlepas dari lebar grid yang dipakai untuk "membungkusnya" kembali ke bentuk 2D. Ini yang membuat reshape bisa dilakukan tanpa perlu array 1D perantara sama sekali — cukup hitung indeks linear `i`, lalu konversi ke dua sistem koordinat berbeda secara langsung.

______________________________________________________________________

## 🔧 Alternatif: Pakai Array 1D Perantara (Lebih Eksplisit, Kurang Efisien)

```java
public int[][] matrixReshape(int[][] mat, int r, int c) {
    int m = mat.length, n = mat[0].length;
    if (m * n != r * c) return mat;

    int[] flat = new int[m * n];
    int idx = 0;
    for (int[] row : mat)
        for (int val : row)
            flat[idx++] = val;

    int[][] ans = new int[r][c];
    idx = 0;
    for (int i = 0; i < r; i++)
        for (int j = 0; j < c; j++)
            ans[i][j] = flat[idx++];

    return ans;
}
```

Versi ini benar-benar membuat array 1D perantara (`flat`) sebelum membentuknya ulang jadi 2D — lebih mudah dipahami secara konsep ("flatten lalu reshape" secara literal), tapi butuh memori tambahan `O(m×n)` untuk array perantara yang sebenarnya tidak diperlukan.

| Approach | Time | Space Tambahan (di luar hasil) |
| ------------------------------------ | ------ | ------------------------------ |
| Konversi indeks langsung (kode asli) | O(m×n) | O(1) |
| Array 1D perantara | O(m×n) | O(m×n) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang bagus untuk pola **"konversi indeks linear ke 2D dan sebaliknya"** — teknik `i/w` dan `i%w` untuk memetakan posisi antara representasi flat dan grid. Pola ini jauh lebih luas dari sekadar reshape matrix; ini fondasi untuk memahami bagaimana array multidimensi sebenarnya direpresentasikan secara internal sebagai memori linear (row-major order), dan berguna untuk soal-soal seperti _Spiral Matrix_, _Rotate Image_, atau optimasi memori pada struktur data 2D besar yang ingin disimpan sebagai array 1D. 🎯

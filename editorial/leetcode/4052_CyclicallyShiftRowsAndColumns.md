# 4052. Cyclically Shift Rows and Columns

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Matrix, Simulation
- **Link**: [Problem](https://leetcode.com/problems/cyclically-shift-rows-and-columns/)
- **Solution**: [Code](../../leetcode/CyclicallyShiftRowsAndColumns.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `grid` berukuran `n x n`, serta `rowShift` dan `colShift` (masing-masing panjang `n`).

- **Tahap 1**: geser tiap baris `i` secara **cyclic ke kiri** sebanyak `rowShift[i]` posisi. Elemen di kolom `j` pindah ke kolom `(j - rowShift[i] + n) % n`.
- **Tahap 2**: setelah **semua** baris selesai digeser, geser tiap kolom `j` secara **cyclic ke atas** sebanyak `colShift[j]` posisi. Elemen di baris `i` pindah ke baris `(i - colShift[j] + n) % n`.

Kembalikan grid akhir setelah **kedua** tahap ini selesai (urutan tahapnya penting: baris dulu, baru kolom).

Contoh:

- `n=2, grid=[[1,2],[3,4]], rowShift=[1,0], colShift=[0,1]` → `[[2,4],[3,1]]`
- `n=3, grid=[[1,2,3],[4,5,6],[7,8,9]], rowShift=[1,2,0], colShift=[2,2,1]` → `[[7,8,5],[2,3,9],[6,4,1]]`

______________________________________________________________________

## 💡 Intuition

Soal ini bisa diselesaikan **persis** mengikuti definisinya, langkah demi langkah — asal kita berhati-hati soal **urutan** dan **tidak menimpa data yang masih dibutuhkan**.

**Poin penting soal urutan**: pergeseran baris **tidak mengubah** indeks baris (elemen tetap di baris yang sama, cuma pindah kolom), dan pergeseran kolom **tidak mengubah** indeks kolom (elemen tetap di kolom yang sama, cuma pindah baris). Karena kedua transformasi ini **independen arahnya** (satu memindahkan horizontal, satu lagi vertikal), kita bisa memprosesnya secara **berurutan**: dulukan **semua** pergeseran baris (hasilkan grid antara), baru proses **semua** pergeseran kolom di atas hasil itu.

**Kenapa butuh array `res` (grid antara), bukan langsung memodifikasi `grid`?** Karena pergeseran baris memindahkan elemen **antar kolom dalam baris yang sama** — kalau kita menulis langsung ke `grid` sambil masih membaca dari `grid` (untuk baris yang sama), kita berisiko **menimpa** nilai asli sebelum sempat dibaca untuk perhitungan posisi lain di baris itu. Solusinya: tulis hasil pergeseran baris ke array `res` yang **terpisah**, baru gunakan `res` sebagai sumber baca untuk tahap pergeseran kolom (yang menulis balik ke `grid`, kali ini aman karena kita membaca dari `res`, bukan dari `grid` yang sedang ditimpa).

______________________________________________________________________

## 🔍 Approach

### Simulasi Dua Tahap dengan Array Perantara

**Tahap 1 — Geser tiap baris, tulis ke `res`:**

1. Untuk tiap baris `i`, loop `j` dari `0` sampai `n-1`.
1. Elemen `grid[i][j]` dipindah ke `res[i][(j - rowShift[i] + n) % n]` — rumus persis dari definisi soal (`+n` sebelum `%n` untuk menghindari hasil negatif dari pengurangan).

**Tahap 2 — Geser tiap kolom dari `res`, tulis balik ke `grid`:**

1. Untuk tiap kolom `j`, loop `i` dari `0` sampai `n-1`.

1. Elemen `res[i][j]` dipindah ke `grid[(i - colShift[j] + n) % n][j]` — rumus yang sama, kali ini untuk arah vertikal.

1. Kembalikan `grid` (sekarang sudah berisi hasil akhir kedua tahap).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------- |
| **Time** | O(n²) — dua tahap, masing-masing memproses seluruh `n×n` elemen sekali |
| **Space** | O(n²) — array perantara `res` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n=2, grid=[[1,2],[3,4]], rowShift=[1,0], colShift=[0,1]`

**Tahap 1 — geser baris ke `res`:**

| i | rowShift[i] | j | newCol=(j-rowShift[i]+n)%n | res[i][newCol] = grid[i][j] |
| --- | ----------- | --- | -------------------------- | --------------------------- |
| 0 | 1 | 0 | `(0-1+2)%2=1` | `res[0][1]=1` |
| 0 | 1 | 1 | `(1-1+2)%2=0` | `res[0][0]=2` |
| 1 | 0 | 0 | `(0-0+2)%2=0` | `res[1][0]=3` |
| 1 | 0 | 1 | `(1-0+2)%2=1` | `res[1][1]=4` |

`res = [[2,1],[3,4]]`

**Tahap 2 — geser kolom dari `res` ke `grid`:**

| j | colShift[j] | i | newRow=(i-colShift[j]+n)%n | grid[newRow][j] = res[i][j] |
| --- | ----------- | --- | -------------------------- | --------------------------- |
| 0 | 0 | 0 | `(0-0+2)%2=0` | `grid[0][0]=res[0][0]=2` |
| 0 | 0 | 1 | `(1-0+2)%2=1` | `grid[1][0]=res[1][0]=3` |
| 1 | 1 | 0 | `(0-1+2)%2=1` | `grid[1][1]=res[0][1]=1` |
| 1 | 1 | 1 | `(1-1+2)%2=0` | `grid[0][1]=res[1][1]=4` |

`grid` akhir: `[[2,4],[3,1]]`

**Output: `[[2,4],[3,1]]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` (matrix `1×1`) → constraint menjamin `0 <= shift < n`, jadi untuk `n=1` satu-satunya nilai shift yang valid adalah `0` — grid tidak berubah sama sekali
- [ ] `rowShift[i] = 0` untuk suatu baris → baris itu tidak berpindah kolom sama sekali (rumus `(j-0+n)%n = j`, posisi tetap)
- [ ] `colShift[j] = 0` untuk suatu kolom → kolom itu tidak berpindah baris sama sekali, dengan alasan yang sama
- [ ] Semua `rowShift` dan `colShift` bernilai `0` → grid akhir identik dengan grid awal
- [ ] Pergeseran maksimum (`shift = n-1`) → tetap tertangani normal lewat aritmatika modulo, tidak ada kasus khusus yang perlu ditangani terpisah

______________________________________________________________________

## 🔧 Kenapa Butuh `+ n` Sebelum `% n`?

```java
res[i][(j - rowShift[i] + n) % n] = grid[i][j];
```

Operator `%` (modulo) di Java **tidak** selalu menghasilkan bilangan non-negatif — kalau operand kiri (`j - rowShift[i]`) bernilai **negatif** (misal `j=0, rowShift[i]=1` → `0-1=-1`), maka `-1 % n` di Java menghasilkan angka **negatif** (`-1`, bukan `n-1` seperti yang diharapkan secara matematis untuk indexing array). Menambahkan `+ n` **sebelum** modulo (`(-1+n) % n`) memastikan hasilnya **selalu** berada dalam rentang `[0, n-1]` yang valid sebagai indeks array, terlepas dari apakah pengurangan awal menghasilkan angka negatif atau tidak.

______________________________________________________________________

## 🔧 Alternatif: Hitung Posisi Akhir Langsung Tanpa Array Perantara

```java
public int[][] cyclicShift(int n, int[][] grid, int[] rowShift, int[] colShift) {
    int[][] ans = new int[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int newCol = (j - rowShift[i] + n) % n;
            int newRow = (i - colShift[newCol] + n) % n;
            ans[newRow][newCol] = grid[i][j];
        }
    }
    return ans;
}
```

Versi ini menghitung **posisi akhir langsung** untuk tiap elemen `grid[i][j]` dalam **satu pass**, tanpa array perantara — karena baris tidak berubah akibat row-shift, kita bisa langsung tahu `newCol` (kolom tujuan setelah pergeseran baris), lalu pakai `newCol` itu untuk menghitung `newRow` (baris tujuan setelah pergeseran kolom, yang bergantung pada `colShift[newCol]`, bukan `colShift[j]` — karena kolom sudah berpindah ke `newCol` sebelum pergeseran kolom diterapkan). Ini menghemat satu array `O(n²)`, meski tetap butuh array output baru (`ans`) karena tidak bisa menimpa `grid` secara in-place tanpa merusak data yang masih dibutuhkan.

| Approach | Time | Space | Jumlah Pass |
| ---------------------------------- | ----- | ----- | ----------- |
| Dua tahap dengan `res` (kode asli) | O(n²) | O(n²) | 2 |
| Hitung posisi akhir langsung | O(n²) | O(n²) | 1 |

Kedua pendekatan tetap butuh `O(n²)` ruang tambahan (tidak bisa benar-benar in-place), tapi versi satu-pass sedikit lebih ringkas karena menggabungkan kedua transformasi jadi satu rumus per elemen.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan bagus untuk **simulasi transformasi matrix bertahap**, dengan dua pelajaran penting: pertama, ketika sebuah transformasi memindahkan elemen **dalam struktur yang sedang dibaca dan ditulis bersamaan**, array perantara (atau perhitungan posisi akhir langsung) diperlukan untuk mencegah data tertimpa sebelum sempat dibaca. Kedua, **selalu tambahkan `+n` sebelum modulo** ketika bekerja dengan pengurangan yang berpotensi negatif, supaya hasilnya tetap valid sebagai indeks array — pola ini sangat umum di soal-soal circular/cyclic seperti _Rotate Array_ dan _Count Good Cyclic Rotations_ yang sudah pernah dibahas. 🎯

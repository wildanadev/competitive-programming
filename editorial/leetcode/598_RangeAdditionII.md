# 598. Range Addition II

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math
- **Link**: [Problem](https://leetcode.com/problems/range-addition-ii/)
- **Solution**: [Code](../../leetcode/RangeAdditionII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan matrix kosong berukuran `m x n` (semua sel bernilai `0`), dan array 2D `ops`. Tiap `ops[i] = [ai, bi]` berarti: **tambahkan 1** ke semua sel dalam sub-persegi panjang `[0, ai) x [0, bi)` (dari pojok kiri atas sampai baris `ai-1`, kolom `bi-1`).

Setelah semua operasi dijalankan, kembalikan **jumlah sel** yang punya nilai **maksimum** di seluruh matrix.

Contoh:

- `m=3, n=3, ops=[[2,2],[3,3]]` → `4`
- `m=3, n=3, ops=[]` → `9` (tidak ada operasi, semua sel bernilai `0` alias sama-sama maksimum)

______________________________________________________________________

## 💡 Intuition

Kunci soal ini: **sel `(0,0)` selalu ikut ditambah oleh SETIAP operasi** — karena tiap operasi selalu dimulai dari pojok kiri atas (`[0, ai) x [0, bi)`, tidak pernah ada offset). Jadi sel `(0,0)` (dan area di sekitarnya) **selalu** menjadi sel dengan nilai tertinggi, tidak pernah ada sel lain yang bisa melampauinya.

Nilai maksimum sebenarnya tercapai di **irisan (intersection)** semua persegi panjang operasi — yaitu area `[0, min(ai)) x [0, min(bi))`. Kenapa? Karena sebuah sel `(r, c)` mendapat tambahan `+1` dari operasi `i` **hanya jika** `r < ai` **dan** `c < bi`. Supaya sebuah sel ditambah oleh **semua** operasi (nilai maksimum absolut), sel itu harus memenuhi `r < ai` untuk **setiap** `ai`, yang berarti `r < min(ai)`. Sama halnya untuk kolom: `c < min(bi)`.

Jadi **tidak perlu simulasi penambahan sungguhan ke seluruh matrix** — cukup cari `min(ai)` dan `min(bi)` dari semua operasi, luas persegi panjang irisannya (`min(ai) × min(bi)`) itulah jumlah sel yang mencapai nilai maksimum.

______________________________________________________________________

## 🔍 Approach

### Math — Cari Irisan Semua Operasi Lewat Minimum

1. Inisialisasi `ai = m`, `bi = n` (kalau `ops` kosong, seluruh matrix `m x n` otomatis jadi jawabannya, karena semua sel tetap `0`, sama-sama maksimum).
1. Loop tiap operasi `[a, b]` di `ops`:
   - `ai = Math.min(ai, a)`
   - `bi = Math.min(bi, b)`
1. Kembalikan `ai * bi` — luas area irisan, yaitu jumlah sel yang tersentuh oleh **semua** operasi sekaligus.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(k) — k = `ops.length`, satu kali pass |
| **Space** | O(1) — hanya dua variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `m=3, n=3, ops=[[2,2],[3,3]]`

| Operasi | ai (min a) | bi (min b) |
| ------------ | ------------ | ------------ |
| inisialisasi | 3 | 3 |
| `[2,2]` | `min(3,2)=2` | `min(3,2)=2` |
| `[3,3]` | `min(2,3)=2` | `min(2,3)=2` |

`ans = 2 * 2 = 4`

**Output: `4`** ✅ — sel `(0,0)`, `(0,1)`, `(1,0)`, `(1,1)` adalah satu-satunya sel yang tersentuh oleh **kedua** operasi (karena operasi `[2,2]` hanya menjangkau baris `0-1`, kolom `0-1`), jadi keduanya bernilai `2` (maksimum), sementara sel lain di luar area itu paling tinggi cuma tersentuh operasi `[3,3]` saja (nilai `1`).

______________________________________________________________________

**Input:** `m=3, n=3, ops=[]`

- Tidak ada operasi sama sekali, `ai` dan `bi` tetap `m=3` dan `n=3`.

**Output: `9`** ✅ (semua sel tetap `0`, seluruhnya sama-sama nilai maksimum)

______________________________________________________________________

**Input:** `m=3, n=3, ops=[[2,2]]`

| Operasi | ai | bi |
| ------------ | --- | --- |
| inisialisasi | 3 | 3 |
| `[2,2]` | 2 | 2 |

**Output: `4`** — sel `(0,0),(0,1),(1,0),(1,1)` semuanya bernilai `1` (satu-satunya operasi), sel lain tetap `0`, jadi nilai maksimum (`1`) dicapai oleh `2×2=4` sel.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `ops` kosong → jawabannya selalu `m * n` (seluruh matrix, karena semua sel bernilai `0` dan sama-sama maksimum)
- [ ] Hanya satu operasi → jawabannya persis ukuran operasi itu (`a * b`), karena tidak ada operasi lain yang membatasi irisan lebih lanjut
- [ ] Salah satu operasi berukuran `m x n` penuh (sama besar dengan matrix) → tidak mengubah hasil, karena `min` dengan nilai maksimum yang sudah ada tidak pernah memperkecil irisan
- [ ] Operasi dengan ukuran `1x1` (`[1,1]`) → langsung membatasi irisan jadi minimal mungkin, hasil `= 1 * 1 = 1` (hanya sel `(0,0)` yang maksimum) jika ini operasi dengan `a`/`b` terkecil

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu Simulasi Matrix Sungguhan?

Pendekatan naif untuk soal ini adalah **benar-benar membuat matrix `m x n`**, lalu untuk tiap operasi, iterasi seluruh sub-persegi panjang `[0,ai) x [0,bi)` dan tambahkan `1` ke tiap selnya — ini `O(k × m × n)` waktu dan `O(m×n)` memori, yang jadi lambat kalau `m`, `n`, atau `k` besar.

Tapi karena **semua operasi selalu dimulai dari pojok yang sama** (`(0,0)`), sel dengan nilai tertinggi **pasti** berada di sudut kiri atas, dan bentuknya **pasti** berupa persegi panjang juga (bukan bentuk acak) — yaitu irisan dari semua persegi panjang operasi. Ini membuat soal yang kelihatan seperti butuh simulasi 2D penuh, runtuh jadi soal 1D sederhana: cari `min` dari kolom pertama tiap operasi, dan `min` dari kolom kedua tiap operasi, secara terpisah — karena baris dan kolom saling independen dalam menentukan batas irisan.

______________________________________________________________________

## 🔧 Alternatif: Java Stream

```java
public int maxCount(int m, int n, int[][] ops) {
    int minA = Arrays.stream(ops).mapToInt(op -> op[0]).min().orElse(m);
    int minB = Arrays.stream(ops).mapToInt(op -> op[1]).min().orElse(n);
    return minA * minB;
}
```

Versi ini secara eksplisit memisahkan pencarian minimum kolom pertama dan kedua lewat Stream API, dengan `orElse` menangani kasus `ops` kosong (setara inisialisasi `ai=m, bi=n` di kode asli).

| Approach | Time | Space |
| ----------------------- | ---- | ----- |
| Loop manual (kode asli) | O(k) | O(1) |
| Java Stream | O(k) | O(1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh bagus dari **"simulasi yang runtuh jadi observasi matematis"** — begitu disadari bahwa semua operasi memiliki titik awal yang sama (pojok kiri atas), soal 2D yang kelihatan butuh iterasi penuh matrix ternyata cukup diselesaikan dengan mencari **irisan** semua operasi lewat dua nilai minimum independen. Pola "cari batas irisan lewat minimum" ini juga relevan untuk soal-soal interval overlap seperti _Interval List Intersections_ atau _Meeting Rooms_, di mana batas irisan beberapa rentang selalu ditentukan oleh `max` dari titik awal dan `min` dari titik akhir. 🎯

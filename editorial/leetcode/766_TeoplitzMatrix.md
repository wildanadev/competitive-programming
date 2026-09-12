# 766. Toeplitz Matrix

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Matrix
- **Link**: [Problem](https://leetcode.com/problems/toeplitz-matrix/)
- **Solution**: [Code](../../leetcode/ToeplitzMatrix.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah matrix disebut **Toeplitz** kalau **setiap diagonal** (dari kiri-atas ke kanan-bawah) berisi elemen yang **semuanya sama**. Diberikan `matrix`, kembalikan `true` kalau dia Toeplitz.

Contoh:

- `matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]` → `true`
  - Diagonal `[9]`, `[5,5]`, `[1,1,1]`, `[2,2,2]`, `[3,3]`, `[4]` — semua diagonal punya elemen seragam.
- `matrix = [[1,2],[2,2]]` → `false`
  - Diagonal `[2,2]` (dari `matrix[0][1]` dan `matrix[1][0]`... perhatikan ini beda diagonal) — sebenarnya yang gagal adalah diagonal utama: `matrix[0][0]=1` tapi `matrix[1][1]=2`, tidak seragam.

______________________________________________________________________

## 💡 Intuition

Sifat kunci matrix Toeplitz: **setiap elemen `matrix[i][j]` harus sama dengan tetangga diagonalnya**, yaitu `matrix[i+1][j+1]` (elemen satu baris ke bawah, satu kolom ke kanan — masih di **diagonal yang sama**). Kalau **semua** pasangan tetangga diagonal seperti ini sama, otomatis **seluruh** diagonal (sepanjang apapun) punya elemen yang seragam — karena kesamaan ini "merambat" secara transitif sepanjang diagonal (`matrix[i][j] == matrix[i+1][j+1] == matrix[i+2][j+2] == ...`).

Jadi kita **tidak perlu** mengelompokkan elemen per diagonal secara eksplisit — cukup **cek tiap elemen terhadap tetangga diagonalnya** (satu langkah ke kanan-bawah), dan kalau **semua** pengecekan lokal ini konsisten, seluruh matrix otomatis Toeplitz.

______________________________________________________________________

## 🔍 Approach

### Bandingkan Tiap Elemen dengan Tetangga Diagonal (i+1, j+1)

1. Loop `i` dari `0` sampai `matrix.length - 2` (baris kedua dari akhir, karena kita perlu akses `i+1`).
1. Loop `j` dari `0` sampai `matrix[i].length - 2` (kolom kedua dari akhir, karena perlu akses `j+1`).
1. Kalau `matrix[i][j] != matrix[i+1][j+1]` → tetangga diagonal tidak sama, matrix **bukan** Toeplitz → `return false`.
1. Kalau seluruh pasangan lolos tanpa ada yang gagal → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------- |
| **Time** | O(m × n) — m, n = jumlah baris dan kolom, tiap elemen (kecuali baris/kolom terakhir) dicek sekali |
| **Space** | O(1) — tidak ada struktur data tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]`

| i | j | matrix[i][j] | matrix[i+1][j+1] | Sama? |
| --- | --- | ------------ | ---------------- | ----- |
| 0 | 0 | 1 | `matrix[1][1]=1` | ya |
| 0 | 1 | 2 | `matrix[1][2]=2` | ya |
| 0 | 2 | 3 | `matrix[1][3]=3` | ya |
| 1 | 0 | 5 | `matrix[2][1]=5` | ya |
| 1 | 1 | 1 | `matrix[2][2]=1` | ya |
| 1 | 2 | 2 | `matrix[2][3]=2` | ya |

Semua pasangan cocok, tidak ada `return false` yang terpicu.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `matrix = [[1,2],[2,2]]`

| i | j | matrix[i][j] | matrix[i+1][j+1] | Sama? |
| --- | --- | ------------ | ---------------- | -------------------------- |
| 0 | 0 | 1 | `matrix[1][1]=2` | **tidak** → `return false` |

**Output: `false`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Matrix `1×1` (cuma satu elemen) → kedua loop (`i < 0`, `j < 0`... tepatnya `matrix.length-1=0`) tidak pernah jalan, langsung `return true` (matrix sepele selalu Toeplitz)
- [ ] Matrix `1` baris atau `1` kolom → salah satu loop (`i` atau `j`) tidak pernah jalan (karena `length-1` jadi `0`), langsung `true` — matrix dengan cuma satu baris/kolom otomatis Toeplitz (tidak ada tetangga diagonal untuk dibandingkan)
- [ ] Diagonal yang gagal ada di **tengah** matrix, bukan di elemen pertama → tetap terdeteksi, karena loop mengecek **seluruh** pasangan `(i,j)` yang valid, tidak berhenti di elemen pertama
- [ ] Matrix persegi vs matrix persegi panjang (bukan persegi) → tidak masalah, loop `i` dan `j` masing-masing memakai batas panjangnya sendiri (`matrix.length` untuk baris, `matrix[i].length` untuk kolom)

______________________________________________________________________

## 🔧 Kenapa Cukup Bandingkan dengan Tetangga `(i+1, j+1)` Saja (Bukan Seluruh Diagonal Sekaligus)?

Ini prinsip **transitivitas kesamaan**. Kalau `a == b` dan `b == c`, maka otomatis `a == c` — tidak perlu membandingkan `a` dengan `c` secara langsung. Diterapkan ke diagonal: kalau `matrix[i][j] == matrix[i+1][j+1]`, **dan** `matrix[i+1][j+1] == matrix[i+2][j+2]` (dicek di iterasi lain), maka secara transitif `matrix[i][j] == matrix[i+2][j+2]` juga otomatis benar — **tanpa** perlu dicek eksplisit. Karena loop ini mengecek **setiap** pasangan tetangga langsung di sepanjang tiap diagonal, transitivitas ini secara otomatis memastikan **seluruh** elemen di diagonal yang sama seragam, cukup lewat perbandingan lokal satu-satu.

______________________________________________________________________

## 🔧 Alternatif: Kelompokkan per Diagonal Secara Eksplisit dengan HashMap

```java
public boolean isToeplitzMatrix(int[][] matrix) {
    Map<Integer, Integer> diagonalValue = new HashMap<>();
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
            int diagonalId = i - j; // elemen di diagonal yang sama selalu punya (i-j) yang sama
            if (diagonalValue.containsKey(diagonalId)) {
                if (diagonalValue.get(diagonalId) != matrix[i][j])
                    return false;
            } else {
                diagonalValue.put(diagonalId, matrix[i][j]);
            }
        }
    }
    return true;
}
```

Versi ini memakai insight bahwa **semua elemen di diagonal yang sama punya nilai `i - j` yang identik** (ingat: bergerak ke kanan-bawah menambah `i` dan `j` sama-sama `1`, jadi selisihnya tetap). Dengan mengelompokkan elemen berdasarkan `i - j` sebagai key `HashMap`, kita bisa cek langsung apakah semua elemen di diagonal yang sama konsisten dengan nilai pertama yang ditemukan di diagonal itu. Secara logika ekuivalen dengan kode asli (berkat transitivitas yang sudah dijelaskan), tapi butuh `HashMap` tambahan dan mengecek **seluruh** elemen (termasuk baris/kolom terakhir), bukan cuma sampai `length-1`.

| Approach | Time | Space | Pendekatan |
| ------------------------------------------- | ------ | ------ | ------------------------------------ |
| Bandingkan tetangga `(i+1,j+1)` (kode asli) | O(m×n) | O(1) | Transitivitas lokal |
| Grouping via `i-j` dengan HashMap | O(m×n) | O(m+n) | Pengelompokan eksplisit per diagonal |

Kode asli lebih efisien (tidak perlu struktur data tambahan) karena memanfaatkan transitivitas kesamaan, sementara versi HashMap lebih eksplisit soal "diagonal mana yang sedang dicek" — berguna kalau butuh informasi lebih dari sekadar validitas boolean (misalnya, nilai tiap diagonal).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh bagus bagaimana **sifat transitivitas** bisa menyederhanakan pengecekan yang kelihatan butuh pengelompokan eksplisit (per diagonal) jadi cukup **perbandingan lokal antar tetangga**. Insight `matrix[i][j] == matrix[i+1][j+1]` untuk semua pasangan valid sudah cukup untuk menjamin seluruh diagonal konsisten, tanpa perlu tahu "diagonal ke berapa" suatu elemen berada. Pola matematis "elemen di garis diagonal yang sama punya `i-j` (atau `i+j`) konstan" tetap berguna untuk soal-soal matrix lain seperti _Diagonal Traverse_ atau soal-soal yang memang butuh pengelompokan diagonal secara eksplisit. 🎯

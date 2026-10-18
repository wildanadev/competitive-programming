# 4084. Last Visited Cell in a Directional Grid

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Matrix, Simulation
- **Link**: [Problem](https://leetcode.com/problems/last-visited-cell-in-a-directional-grid/)
- **Solution**: [Code](../../leetcode/LastVisitedCellInADirectionalGrid.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `grid` berukuran `m x n`, di mana tiap sel berisi salah satu karakter arah:

- `'L'`: pindah satu sel ke kiri
- `'R'`: pindah satu sel ke kanan
- `'U'`: pindah satu sel ke atas
- `'D'`: pindah satu sel ke bawah

Kamu mulai di sel `(0, 0)` (dianggap **sudah dikunjungi**). Di setiap langkah, bergerak sesuai arah yang tertulis di **sel saat ini**. Proses **berhenti** kalau langkah yang ditunjuk akan **keluar dari grid** atau **masuk ke sel yang sudah dikunjungi** (langkah itu tidak dilakukan). Kembalikan `[r, c]` dari **sel terakhir yang dikunjungi**.

Contoh:

- `grid = [["R","D"],["U","L"]]` → `[1,0]`
  - `(0,0)` → `(0,1)` → `(1,1)` → `(1,0)`, lalu `'U'` di `(1,0)` menunjuk `(0,0)` yang sudah dikunjungi, jadi berhenti di `(1,0)`.
- `grid = [["R","R","R"]]` → `[0,2]`
  - `(0,0)` → `(0,1)` → `(0,2)`, lalu `'R'` di `(0,2)` menunjuk `(0,3)` yang di luar grid, jadi berhenti di `(0,2)`.

______________________________________________________________________

## 💡 Intuition

Aturan geraknya **deterministik**: dari tiap sel hanya ada **satu** sel tujuan. Jadi tidak ada percabangan, tidak ada pilihan, dan tidak perlu BFS atau DFS. Soal ini murni **simulasi**: ikuti panah dari `(0, 0)`, satu langkah demi satu langkah, sampai ada alasan berhenti.

Dua alasan berhenti yang dicek **sebelum** melangkah:

1. Sel tujuan **di luar grid**.
1. Sel tujuan **sudah dikunjungi**.

Karena tiap sel punya tepat satu panah keluar, satu-satunya cara bertemu sel yang sudah dikunjungi adalah **berputar membentuk siklus** ke sel yang tadi dilewati. Jadi simulasi ini pada dasarnya **deteksi siklus** seperti pada _Linked List Cycle_: berjalan sampai keluar dari struktur atau kembali ke sel yang sudah pernah dikunjungi. Karena tiap sel dikunjungi **paling banyak sekali**, jumlah langkah dibatasi `m * n`, jadi pasti selesai.

______________________________________________________________________

## 🔍 Approach

### Simulasi dengan Array `visited`

1. Buat `visited[m][n]` berisi `false`, tandai `visited[0][0] = true`. Posisi awal `(r, c) = (0, 0)`.
1. Ulangi:
   - Baca arah `dir = grid[r][c]`.
   - Hitung tujuan `(nr, nc)`: `'U'` mengurangi baris, `'D'` menambah baris, `'L'` mengurangi kolom, `'R'` menambah kolom.
   - Kalau `(nr, nc)` di luar grid **atau** `visited[nr][nc]` sudah `true` → **berhenti**, kembalikan `[r, c]`.
   - Kalau tidak: tandai `visited[nr][nc] = true`, lalu pindah (`r = nr`, `c = nc`).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------ |
| **Time** | O(m × n) — tiap sel dikunjungi paling banyak sekali, tiap langkah `O(1)` |
| **Space** | O(m × n) — array `visited` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `grid = [["R","D"],["U","L"]]`

| Langkah | Posisi | Arah | Tujuan | Status tujuan | Aksi |
| ------- | ------ | ---- | ------ | ---------------------------- | ------------------------ |
| 1 | (0,0) | R | (0,1) | dalam grid, belum dikunjungi | pindah |
| 2 | (0,1) | D | (1,1) | dalam grid, belum dikunjungi | pindah |
| 3 | (1,1) | L | (1,0) | dalam grid, belum dikunjungi | pindah |
| 4 | (1,0) | U | (0,0) | **sudah dikunjungi** | berhenti, return `[1,0]` |

**Output: `[1,0]`** ✅

______________________________________________________________________

**Input:** `grid = [["R","R","R"]]`

| Langkah | Posisi | Arah | Tujuan | Status tujuan | Aksi |
| ------- | ------ | ---- | ------ | ------------------------------------ | ------------------------ |
| 1 | (0,0) | R | (0,1) | dalam grid, belum dikunjungi | pindah |
| 2 | (0,1) | R | (0,2) | dalam grid, belum dikunjungi | pindah |
| 3 | (0,2) | R | (0,3) | **di luar grid** (`nc = 3 >= n = 3`) | berhenti, return `[0,2]` |

**Output: `[0,2]`** ✅

______________________________________________________________________

**Input tambahan:** `grid = [["R","L"]]` (siklus dua sel)

`(0,0)` `'R'` → `(0,1)` (pindah). `(0,1)` `'L'` → `(0,0)` yang sudah dikunjungi → berhenti di `(0,1)`.

**Output: `[0,1]`**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Grid `1 x 1` → apa pun arahnya (`L`, `R`, `U`, `D`), tujuannya di luar grid, jawabannya `[0,0]`
- [ ] Panah di `(0,0)` langsung keluar grid (`[["L", ...]]` atau `[["U"], ...]`) → berhenti di `[0,0]` tanpa pernah melangkah
- [ ] Dua sel saling menunjuk (`R` lalu `L`) → siklus, berhenti di sel kedua
- [ ] Jalur panjang yang melewati hampir semua sel → maksimal `m * n` langkah (hingga `10^5`), tetap cepat
- [ ] Grid sangat "ramping" (misal `m = 10^5, n = 1`) → loop iteratif tidak bermasalah, sedangkan solusi **rekursif** bisa kena `StackOverflowError` pada jalur sepanjang itu; simulasi iteratif di sini aman

______________________________________________________________________

## 🔧 Alternatif: Tandai Langsung di `grid` (Tanpa Array `visited`)

```java
public int[] lastVisitedCell(char[][] grid) {
    int m = grid.length, n = grid[0].length;
    int r = 0, c = 0;
    while (true) {
        char d = grid[r][c];
        grid[r][c] = '#';                         // tandai dikunjungi (baca arah dulu!)
        int nr = r + (d == 'D' ? 1 : d == 'U' ? -1 : 0);
        int nc = c + (d == 'R' ? 1 : d == 'L' ? -1 : 0);
        if (nr < 0 || nr >= m || nc < 0 || nc >= n || grid[nr][nc] == '#')
            return new int[]{r, c};
        r = nr;
        c = nc;
    }
}
```

Versi ini memakai `grid` itu sendiri sebagai penanda kunjungan (`'#'`), jadi tidak butuh `boolean[m][n]` tambahan. Perhatikan urutannya: arah `d` harus **dibaca sebelum** sel ditimpa dengan `'#'`. Trade-off-nya: ia **memodifikasi input**, yang di LeetCode aman, tapi di kode nyata perlu dipertimbangkan (efek samping pada data milik pemanggil).

______________________________________________________________________

## 🔧 Catatan: `boolean[m][n]` untuk Bentuk Grid Ekstrem

Karena `m * n <= 10^5` tetapi `m` bisa sampai `10^5` dengan `n = 1`, `new boolean[m][n]` membuat **`m` array kecil** (satu per baris), masing-masing dengan overhead objek. Itu masih aman untuk ukuran ini, tapi kalau ingin lebih hemat, ratakan jadi array 1D `boolean[m * n]` dengan indeks `r * n + c`:

```java
boolean[] visited = new boolean[m * n];
visited[r * n + c] = true;   // contoh penandaan
```

| Approach | Time | Space | Catatan |
| ---------------------------- | -------- | ------------- | ------------------------------------------------- |
| `boolean[m][n]` (kode asli) | O(m × n) | O(m × n) | Paling mudah dibaca |
| `boolean[m * n]` (diratakan) | O(m × n) | O(m × n) | Lebih sedikit overhead objek untuk bentuk ramping |
| Tandai langsung di `grid` | O(m × n) | O(1) tambahan | Memodifikasi input |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **simulasi pada functional graph**: tiap node (sel) punya tepat satu tujuan, sehingga jalannya tunggal dan hanya bisa berakhir dengan keluar dari struktur atau masuk ke siklus. Cukup simulasikan langkah demi langkah dengan penanda kunjungan, dan alasan berhenti dicek **sebelum** melangkah. Karena tiap sel dikunjungi paling banyak sekali, kompleksitasnya `O(m × n)` tanpa perlu algoritma graf yang lebih canggih. Pola "ikuti pointer sampai keluar atau berulang" ini sama dengan deteksi siklus di linked list, dan juga muncul di soal-soal seperti _Robot Return to Origin_ (simulasi gerak) dan _Spiral Matrix_ (simulasi dengan penanda kunjungan). 🎯

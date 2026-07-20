# 1260. Shift 2D Grid

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Matrix, Simulation
- **Link**: [Problem](https://leetcode.com/problems/shift-2d-grid/)
- **Solution**: [Code](../../leetcode/Shift2DGrid.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan grid `m×n` dan integer `k`. Satu shift memindahkan setiap elemen ke **posisi berikutnya** secara linear (baris kiri ke kanan, lalu baris berikutnya), dan elemen terakhir berpindah ke posisi pertama. Lakukan `k` shift, kembalikan grid hasil.

Contoh:

```
grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1
Hasil: [[9,1,2],[3,4,5],[6,7,8]]
```

______________________________________________________________________

## 💡 Intuition

Daripada mensimulasikan `k` shift satu per satu (O(k×m×n)), kita bisa langsung hitung posisi asal setiap elemen setelah `k` shift.

**Insight kunci:** Flatten grid menjadi array 1D berindeks `0..m×n-1`. Setelah `k` shift, posisi `p` di grid baru berisi elemen dari posisi `(p - k + m×n) % (m×n)` di grid lama.

Atau sebaliknya: elemen di posisi `p` di grid lama pindah ke posisi `(p + k) % (m×n)` di grid baru.

**Pendekatan kode ini:** Alih-alih menghitung untuk setiap posisi baru, mulai dari posisi `begin = (dimension - k) % dimension` di grid lama (posisi yang akan menjadi `(0,0)` di grid baru), lalu iterasi maju sebanyak `dimension` langkah dengan wrap-around.

______________________________________________________________________

## 🔍 Approach

### Linear Index Mapping (Simulated Traversal)

1. Hitung `dimension = row × col`, `k %= dimension`.
1. Hitung `begin = dimension - k` → indeks linear di grid lama yang akan menjadi posisi `(0,0)` setelah shift.
1. Loop `i` dari `begin` sampai `begin + dimension - 1`:
   - `rowTemp = (i / col) % row` → baris di grid lama (dengan wrap-around)
   - `colTemp = i % col` → kolom di grid lama
   - Tambahkan `grid[rowTemp][colTemp]` ke `ans[cnt/col]`
   - `cnt++`
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------- |
| **Time** | O(m×n) — satu pass semua elemen |
| **Space** | O(m×n) — output grid |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `grid = [[1,2,3],[4,5,6],[7,8,9]]`, `k = 1`

`row=3, col=3, dimension=9, k=1, begin=9-1=8`

Loop `i` dari `8` sampai `16`:

| i | i/col | (i/col)%row | i%col | grid[row][col] | cnt | ans posisi |
| --- | ----- | ----------- | ----- | -------------- | --- | ---------- |
| 8 | 2 | 2 | 2 | grid[2][2]=9 | 0 | ans[0][0] |
| 9 | 3 | 0 | 0 | grid[0][0]=1 | 1 | ans[0][1] |
| 10 | 3 | 0 | 1 | grid[0][1]=2 | 2 | ans[0][2] |
| 11 | 3 | 0 | 2 | grid[0][2]=3 | 3 | ans[1][0] |
| 12 | 4 | 1 | 0 | grid[1][0]=4 | 4 | ans[1][1] |
| 13 | 4 | 1 | 1 | grid[1][1]=5 | 5 | ans[1][2] |
| 14 | 4 | 1 | 2 | grid[1][2]=6 | 6 | ans[2][0] |
| 15 | 5 | 2 | 0 | grid[2][0]=7 | 7 | ans[2][1] |
| 16 | 5 | 2 | 1 | grid[2][1]=8 | 8 | ans[2][2] |

**Output: `[[9,1,2],[3,4,5],[6,7,8]]` ✅**

______________________________________________________________________

**Input:** `grid = [[3,8,1,9],[19,7,2,5],[4,6,11,10],[12,0,21,13]]`, `k = 4`

`dimension=16, k=4, begin=12`

Elemen di posisi linear 12 di grid lama = `grid[12/4][12%4] = grid[3][0] = 12`.

Setelah 4 shift, `12` harus ada di `(0,0)` ✅.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k % dimension == 0` → `begin = 0` → grid tidak berubah
- [ ] `k >= dimension` → `k %= dimension` menangani ini
- [ ] Grid 1×1 → selalu return grid yang sama

______________________________________________________________________

## 🔧 Kenapa `begin = dimension - k`?

```
Setelah k shift, posisi (0,0) di grid baru berisi elemen dari posisi:
  (0 - k + dimension) % dimension = dimension - k  (untuk k > 0)
```

Contoh `k=1, dimension=9`:

```
Posisi (0,0) baru ← posisi (8) lama = 9-1 = 8 ✅
(elemen terakhir grid pindah ke posisi pertama)
```

______________________________________________________________________

## 🔧 Kenapa `(i / col) % row` untuk Row Index?

Saat `i >= dimension`, indeks bisa melampaui batas array. Contoh `i=9` dengan `col=3`:

```
i=9: i/col = 3, 3 % row = 3 % 3 = 0  → baris 0 ✅ (wrap ke awal)
i=12: i/col = 4, 4 % 3 = 1          → baris 1 ✅
```

`% row` meng-handle wrap-around saat traversal melampaui akhir grid kembali ke awal.

______________________________________________________________________

## 🚀 Alternatif: Pendekatan Langsung

```java
class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int total = m * n;
        k %= total;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ans.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                // posisi linear baru → cari posisi lama
                int newPos = i * n + j;
                int oldPos = (newPos - k + total) % total;
                ans.get(i).add(grid[oldPos / n][oldPos % n]);
            }
        }
        return ans;
    }
}
```

Lebih mudah dipahami: untuk setiap sel `(i,j)` di grid baru, hitung langsung dari posisi mana di grid lama elemen tersebut berasal.

| Approach | Time | Space | Readability |
| ----------------------------- | ----- | ----- | ----------- |
| Kode asli (begin + traversal) | O(mn) | O(mn) | ⭐⭐⭐ |
| Direct mapping | O(mn) | O(mn) | ⭐⭐⭐⭐⭐ |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan **1D ↔ 2D index conversion**: `linearIndex = row*col + col`, `row = linearIndex/numCols`, `col = linearIndex%numCols`. Dengan memandang grid sebagai array 1D, shift `k` langkah menjadi operasi offset sederhana dengan modulo. Pendekatan "direct mapping" (untuk setiap posisi baru cari posisi lama) lebih mudah dipahami daripada pendekatan "traversal dari begin". 🎯

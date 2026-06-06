# 463. Island Perimeter

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Depth-First Search, Breadth-First Search, Matrix
- **Link**: [Problem](https://leetcode.com/problems/island-perimeter/)
- **Solution**: [Code](../../leetcode/IslandPerimeter.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan grid `m × n` di mana `1` = tanah dan `0` = air. Grid hanya memiliki satu pulau (tidak ada danau di dalam pulau). Hitung **perimeter** pulau tersebut.

Contoh:

```
0 1 0 0
1 1 1 0
0 1 0 0
1 1 0 0
```

→ `16`

______________________________________________________________________

## 💡 Intuition

Setiap sel tanah (`1`) berkontribusi **4 sisi**. Tapi setiap sisi yang berbatasan dengan sel tanah lain **tidak dihitung** (sisi bersama = hilang dari perimeter).

Untuk setiap pasangan sel tanah yang bersebelahan, **2 sisi hilang** (satu dari masing-masing sel).

**Trik**: cukup cek dua arah saja (atas dan kiri) untuk setiap sel — karena pasangan bawah dan kanan akan dicek saat kita memproses sel tersebut nanti. Ini menghindari penghitungan ganda.

```
Setiap sel: +4
Tetangga atas ada: -2
Tetangga kiri ada: -2
```

______________________________________________________________________

## 🔍 Approach

### Linear Scan — Count and Subtract Shared Edges

1. Loop semua sel grid.
1. Jika sel `grid[i][j] == 1`:
   - `perimeter += 4`
   - Jika ada tetangga **atas** (`grid[i-1][j] == 1`) → `perimeter -= 2`
   - Jika ada tetangga **kiri** (`grid[i][j-1] == 1`) → `perimeter -= 2`
1. Return `perimeter`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(m × n) — setiap sel dikunjungi sekali |
| **Space** | O(1) — hanya variabel `perimeter` |

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```
0 1 0 0
1 1 1 0
0 1 0 0
1 1 0 0
```

| (i,j) | grid | +4 | atas ada? | kiri ada? | perimeter |
| ----- | ---- | --- | ------------- | ------------- | --------- |
| (0,1) | 1 | +4 | i=0, skip | j=0,skip | 4 |
| (1,0) | 1 | +4 | (0,0)=0 ❌ | j=0, skip | 8 |
| (1,1) | 1 | +4 | (0,1)=1 ✅ -2 | (1,0)=1 ✅ -2 | 8 |
| (1,2) | 1 | +4 | (0,2)=0 ❌ | (1,1)=1 ✅ -2 | 10 |
| (2,1) | 1 | +4 | (1,1)=1 ✅ -2 | (2,0)=0 ❌ | 12 |
| (3,0) | 1 | +4 | (2,0)=0 ❌ | j=0, skip | 16 |
| (3,1) | 1 | +4 | (2,1)=1 ✅ -2 | (3,0)=1 ✅ -2 | 16 |

**Output: `16` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Grid satu sel `[[1]]` → `perimeter=4`, tidak ada tetangga → return `4`
- [ ] Satu baris `[[1,1,1]]` → `4+4+4 - 2 - 2 = 8`
- [ ] Semua sel tanah (persegi panjang) → perimeter = `2 × (m+n)`

______________________________________________________________________

## 🔧 Kenapa Hanya Cek Atas dan Kiri, Bukan Empat Arah?

```
Jika cek empat arah (atas, bawah, kiri, kanan):
Setiap shared edge dihitung DUA KALI → perlu dibagi 2, lebih rumit.

Jika cek dua arah (atas, kiri):
Setiap shared edge hanya dihitung SEKALI saat memproses sel bawah/kanan dari pasangan.
```

Contoh: dua sel bersebelahan horizontal `(i,0)` dan `(i,1)`:

```
Cek empat arah:
  (i,0): kanan ada → -2
  (i,1): kiri ada  → -2
  Total dikurangi: 4 (benar!)

Cek dua arah (atas+kiri):
  (i,0): kiri tidak ada (j=0)
  (i,1): kiri ada → -2
  Total dikurangi: 2 (benar, karena +4 dua kali = 8, shared edge = 2 → 8-2=6 ✅)
```

Dengan cek dua arah, setiap shared edge dikurangi **tepat sekali** — lebih efisien.

______________________________________________________________________

## 🔧 Visualisasi Per Sel

```
Sel (1,1) di grid:
    [atas=1]
[kiri=1][1,1][kanan=1]
    [bawah=1]

+4 awalnya:
□□
□□  → 4 sisi

Atas ada (+1 tetangga) → -2:
□□
□□  → 2 sisi yang berbatasan dengan atas hilang

Kiri ada (+1 tetangga) → -2:
□□
□□  → 2 sisi yang berbatasan dengan kiri hilang

Kontribusi sel (1,1) = 4-2-2 = 0
(semua sisinya tertutup oleh tetangga kiri dan atas)
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan teknik **"hitung penuh lalu kurangi overlap"** — lebih mudah dari menghitung tepi secara langsung. Cek dua arah (atas + kiri) menghindari double-counting karena setiap edge bersama akan ditemukan tepat satu kali saat memproses sel yang berada di bawah atau di kanan. 🎯

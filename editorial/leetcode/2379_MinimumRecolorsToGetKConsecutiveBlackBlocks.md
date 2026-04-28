# 2379. Minimum Recolors to Get K Consecutive Black Blocks

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/minimum-recolors-to-get-k-consecutive-black-blocks/)
- **Solution**: [Code](../../leetcode/MinimumRecolorsToGetKConsecutiveBlackBlocks.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `blocks` yang terdiri dari karakter `'B'` (hitam) dan `'W'` (putih), dan integer `k`. Setiap operasi mengubah satu `'W'` menjadi `'B'`. Kembalikan **jumlah operasi minimum** agar terdapat `k` blok hitam berturut-turut.

Contoh:

- `blocks = "WBBWWBBWBW"`, `k = 7` → `3`
- `blocks = "WBWBBBW"`, `k = 2` → `0`

______________________________________________________________________

## 💡 Intuition

Kita perlu menemukan window berukuran `k` yang sudah memiliki blok hitam **terbanyak** — karena window tersebut membutuhkan **paling sedikit** recolor (hanya perlu mengubah yang putih).

Jumlah operasi untuk setiap window = **jumlah `'W'` di dalam window**. Cukup cari window dengan jumlah `'W'` terkecil menggunakan **fixed sliding window**.

______________________________________________________________________

## 🔍 Approach

### Fixed Sliding Window — Hitung 'W' per Window

1. Hitung jumlah `'W'` di window pertama (`blocks[0..k-1]`) → simpan ke `ans` dan `window`.
1. Geser window dari `i = k` sampai akhir:
   - Jika `blocks[i] == 'W'` → elemen baru masuk, `window++`
   - Jika `blocks[i-k] == 'W'` → elemen lama keluar, `window--`
   - Update `ans = min(ans, window)`
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------- |
| **Time** | O(n) — satu pass window pertama + satu pass sliding |
| **Space** | O(1) — hanya variabel counter |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `blocks = "WBBWWBBWBW"`, `k = 7`

**Hitung window pertama `blocks[0..6]` = `"WBBWWBB"`:**

```
W → ans++   → ans = 1
B → skip
B → skip
W → ans++   → ans = 2
W → ans++   → ans = 3
B → skip
B → skip
ans = 3, window = 3
```

**Sliding:**

| i | blocks[i] masuk | blocks[i-k]=blocks[i-7] keluar | window | ans |
| --- | --------------- | ------------------------------ | ------ | ------------ |
| 7 | `'W'` → +1 | blocks[0]=`'W'` → -1 | 3 | min(3,3) = 3 |
| 8 | `'B'` → +0 | blocks[1]=`'B'` → -0 | 3 | min(3,3) = 3 |
| 9 | `'W'` → +1 | blocks[2]=`'B'` → -0 | 4 | min(3,4) = 3 |

**Output: `3` ✅**

______________________________________________________________________

**Input:** `blocks = "WBWBBBW"`, `k = 2`

**Hitung window pertama `blocks[0..1]` = `"WB"`:**

```
W → ans = 1
B → skip
ans = 1, window = 1
```

**Sliding:**

| i | blocks[i] masuk | blocks[i-2] keluar | window | ans |
| --- | --------------- | -------------------- | ------ | ---------------- |
| 2 | `'W'` → +1 | blocks[0]=`'W'` → -1 | 1 | min(1,1) = 1 |
| 3 | `'B'` → +0 | blocks[1]=`'B'` → -0 | 1 | min(1,1) = 1 |
| 4 | `'B'` → +0 | blocks[2]=`'W'` → -1 | 0 | min(1,0) = **0** |
| 5 | `'B'` → +0 | blocks[3]=`'B'` → -0 | 0 | min(0,0) = 0 |
| 6 | `'W'` → +1 | blocks[4]=`'B'` → -0 | 1 | min(0,1) = 0 |

**Output: `0` ✅** — window `"BB"` di indeks `4..5` tidak butuh recolor

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Sudah ada `k` hitam berturut-turut → `ans = 0`
- [ ] Semua putih → `ans = k` (seluruh window harus di-recolor)
- [ ] `k == blocks.length()` → hanya satu window, return jumlah `'W'` di seluruh string
- [ ] `k == 1` → cari satu `'B'` saja → jika ada `'B'`, return `0`

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik di mana **fixed sliding window mereduksi masalah** — alih-alih menghitung jumlah `'W'` dari nol untuk setiap window (O(n×k)), kita cukup update `+1/-1` saat elemen masuk/keluar (O(n)). Kunci transformasinya: "minimum operasi" = "minimum `'W'` dalam window" — mengubah perspektif dari operasi ke hitungan karakter. 🎯

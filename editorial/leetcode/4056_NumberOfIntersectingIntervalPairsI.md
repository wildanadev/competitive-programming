# 4056. Number of Intersecting Interval Pairs I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sorting, Enumeration
- **Link**: [Problem](https://leetcode.com/problems/number-of-intersecting-interval-pairs-i/)
- **Solution**: [Code](../../leetcode/NumberOfIntersectingIntervalPairsI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array 2D `intervals`, di mana `intervals[i] = [starti, endi]` merepresentasikan interval **tertutup** (closed) dari `starti` sampai `endi`. Hitung jumlah **pasangan indeks** `(i, j)` dengan `i < j` di mana `intervals[i]` dan `intervals[j]` **beririsan**.

Dua interval dianggap beririsan kalau punya **minimal satu titik yang sama** — **termasuk** kalau cuma bersinggungan di satu endpoint saja.

Contoh:

- `intervals = [[1,2],[2,3],[3,4]]` → `2` (`[1,2]` & `[2,3]` beririsan di titik `2`; `[2,3]` & `[3,4]` beririsan di titik `3`)
- `intervals = [[1,5],[2,4],[3,6]]` → `3` (semua pasangan saling beririsan)
- `intervals = [[1,2],[3,4],[5,6]]` → `0` (tidak ada yang beririsan sama sekali)

______________________________________________________________________

## 💡 Intuition

Karena constraint soal ini kecil (`n <= 100`), **brute force** — cek **semua** pasangan `(i, j)` dengan `i < j`, tentukan apakah mereka beririsan — sudah lebih dari cukup cepat (`O(n²)`, maksimal `100²=10000` pasangan).

Untuk cek dua interval `[s1,e1]` dan `[s2,e2]` beririsan (termasuk bersinggungan di endpoint), gunakan prinsip klasik: mereka **beririsan** kalau `e1 >= s2` **dan** `s1 <= e2`. Ini adalah kebalikan dari "kapan pasti **tidak** beririsan" (mirip prinsip **separating axis** yang sudah dibahas di soal _Rectangle Overlap_, tapi versi 1-dimensi): dua interval **pasti tidak** beririsan kalau salah satunya berakhir **sebelum** yang lain dimulai (`e1 < s2` atau `e2 < s1`). Karena soal ini **menghitung sentuhan endpoint sebagai beririsan** (beda dengan _Rectangle Overlap_ yang butuh luas positif), operator perbandingannya `>=`/`<=` (non-strict), bukan `>`/`<` (strict).

______________________________________________________________________

## 🔍 Approach

### Brute Force — Cek Semua Pasangan `(i, j)`

1. Inisialisasi `ans = 0`.
1. Loop `i` dari `0` sampai `n-1`, ambil `start`, `end` dari `intervals[i]`.
1. Loop `j` dari `i+1` sampai `n-1` (memastikan `i < j`, tiap pasangan cuma dicek sekali), ambil `startTemp`, `endTemp` dari `intervals[j]`.
1. Kalau `end >= startTemp` **dan** `start <= endTemp` → kedua interval beririsan, `ans++`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------- |
| **Time** | O(n²) — mengecek semua pasangan `(i,j)` dengan `i<j` |
| **Space** | O(1) — hanya beberapa variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `intervals = [[1,2],[2,3],[3,4]]`

| (i,j) | intervals[i] | intervals[j] | end≥startTemp? | start≤endTemp? | Beririsan? |
| ----- | ------------ | ------------ | -------------- | -------------- | ---------- |
| (0,1) | [1,2] | [2,3] | `2≥2` ✅ | `1≤3` ✅ | **ya** |
| (0,2) | [1,2] | [3,4] | `2≥3`? ❌ | — | tidak |
| (1,2) | [2,3] | [3,4] | `3≥3` ✅ | `2≤4` ✅ | **ya** |

`ans = 2`

**Output: `2`** ✅

______________________________________________________________________

**Input:** `intervals = [[1,5],[2,4],[3,6]]`

| (i,j) | intervals[i] | intervals[j] | end≥startTemp? | start≤endTemp? | Beririsan? |
| ----- | ------------ | ------------ | -------------- | -------------- | ---------- |
| (0,1) | [1,5] | [2,4] | `5≥2` ✅ | `1≤4` ✅ | **ya** |
| (0,2) | [1,5] | [3,6] | `5≥3` ✅ | `1≤6` ✅ | **ya** |
| (1,2) | [2,4] | [3,6] | `4≥3` ✅ | `2≤6` ✅ | **ya** |

`ans = 3`

**Output: `3`** ✅

______________________________________________________________________

**Input:** `intervals = [[1,2],[3,4],[5,6]]`

Semua pasangan gagal syarat `end >= startTemp` (misal `(0,1)`: `2≥3`? tidak) → `ans = 0`.

**Output: `0`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Dua interval bersinggungan **tepat** di satu titik (`[1,2]` dan `[2,3]`, sama-sama menyentuh `2`) → **dihitung** beririsan (beda dengan _Rectangle Overlap_ yang butuh luas positif)
- [ ] Interval yang identik persis (`[a,b]` dan `[a,b]`) → beririsan (syarat `end>=start` dan `start<=end` otomatis lolos)
- [ ] Interval yang sepenuhnya **berada di dalam** interval lain (`[1,5]` mengandung `[2,4]`) → beririsan
- [ ] `n = 2` (minimum sesuai constraint) → cuma ada `1` pasangan untuk dicek
- [ ] Semua interval saling terpisah jauh, tidak ada yang bersinggungan sama sekali → `ans = 0`

______________________________________________________________________

## 🔧 Kenapa Operatornya `>=`/`<=` (Non-Strict), Bukan `>`/`<` (Strict) Seperti di _Rectangle Overlap_?

Ini perbedaan definisi yang penting untuk diperhatikan. Di soal _Rectangle Overlap_, overlap didefinisikan sebagai **luas irisan positif** — sentuhan tepi/sudut (luas `0`) **tidak dihitung**, makanya dipakai `<` strict. Di soal ini, definisinya **eksplisit berbeda**: "dua interval beririsan kalau punya **minimal satu titik yang sama**, **termasuk** kalau cuma bersinggungan di satu endpoint". Karena "satu titik yang sama" **sudah cukup** untuk dianggap beririsan (beda dengan "luas positif" yang butuh lebih dari satu titik), operator perbandingannya jadi non-strict (`>=`/`<=`) — mengizinkan kasus batas (`end == startTemp`) ikut dihitung sebagai beririsan.

______________________________________________________________________

## 🔧 Alternatif: Sort Dulu, Lalu Sliding Window/Counting — O(n log n)

Untuk `n` yang jauh lebih besar (di luar constraint soal versi ini), brute force `O(n²)` bisa jadi terlalu lambat. Pendekatan yang lebih efisien: **urutkan** interval berdasarkan `start`, lalu untuk tiap interval, gunakan **binary search** untuk mencari berapa banyak interval **lain** yang `start`-nya `<=` `end` interval saat ini (kandidat yang mungkin beririsan), dikurangi False positive dari interval yang sudah berakhir sebelum interval ini dimulai.

```java
public int countIntersectingIntervals(int[][] intervals) {
    int n = intervals.length;
    int[] starts = new int[n];
    for (int i = 0; i < n; i++) starts[i] = intervals[i][0];
    Arrays.sort(starts);

    long totalPairs = (long) n * (n - 1) / 2;
    long nonIntersecting = 0;
    // Hitung pasangan yang PASTI tidak beririsan: end_i < start_j untuk i yang diproses lebih dulu
    for (int[] interval : intervals) {
        int idx = upperBound(starts, interval[1]); // jumlah start yang <= end (tidak dihitung sebagai "pasti terpisah")
        nonIntersecting += (n - idx);
    }
    // (implementasi upperBound dan penyesuaian off-by-one disederhanakan untuk ilustrasi)
    return (int) (totalPairs - nonIntersecting / 2); // dibagi 2 karena tiap pasangan "tidak beririsan" terhitung dari kedua sisi
}
```

_(Catatan: pseudo-kode di atas untuk ilustrasi konsep "hitung yang pasti tidak beririsan, lalu kurangi dari total pasangan" — implementasi detail binary search dan penanganan duplikat `start` perlu penyesuaian cermat.)_

| Approach | Time | Space | Cocok untuk `n` Berapa? |
| ----------------------------- | ---------- | ----- | ------------------------------------ |
| Brute force (kode asli) | O(n²) | O(1) | Kecil (`n <= ~1000`) |
| Sort + binary search/counting | O(n log n) | O(n) | Besar (`n` sampai `10^5` atau lebih) |

Untuk `n <= 100` seperti pada soal **versi I** ini, brute force `O(n²)` (maksimal `10000` operasi) sudah sangat lebih dari cukup — pendekatan `O(n log n)` di atas lebih relevan kalau ada **versi II** dengan constraint `n` yang jauh lebih besar (pola yang sudah beberapa kali kita lihat di soal-soal LeetCode versi I/II lainnya).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang baik untuk konsep **pengecekan overlap interval 1-dimensi**, sekaligus latihan penting membedakan **definisi "intersect" yang tepat** — di sini termasuk sentuhan endpoint (non-strict), berbeda dengan soal-soal overlap lain yang mensyaratkan luas/area positif (strict). Constraint kecil (`n<=100`) membuat brute force `O(n²)` jadi pilihan yang wajar, tapi memahami pola "urutkan lalu hitung pasangan yang pasti tidak beririsan" tetap berguna untuk menghadapi variasi soal dengan constraint yang jauh lebih besar. 🎯

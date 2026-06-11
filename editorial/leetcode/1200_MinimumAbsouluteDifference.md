# 1200. Minimum Absolute Difference

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sorting
- **Link**: [Problem](https://leetcode.com/problems/minimum-absolute-difference/)
- **Solution**: [Code](../../leetcode/MinimumAbsoluteDifference.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `arr`, temukan semua pasangan `[a, b]` dengan `a < b` yang memiliki **selisih absolut minimum**. Return semua pasangan tersebut diurutkan secara ascending.

Contoh:

- `arr = [4,2,1,3]` → `[[1,2],[2,3],[3,4]]`
- `arr = [1,3,6,10,15]` → `[[1,3]]`
- `arr = [3,8,-10,23,19,-4,-14,27]` → `[[-14,-10],[19,23],[23,27]]`

______________________________________________________________________

## 💡 Intuition

Setelah array **diurutkan**, pasangan dengan selisih absolut minimum selalu berada di posisi **berurutan** — elemen yang paling dekat nilainya pasti berdekatan setelah sort.

Cukup satu pass: bandingkan setiap `arr[i]` dengan `arr[i+1]`, track `minAbs`, dan kumpulkan pasangan.

______________________________________________________________________

## 🔍 Approach

### Sort + Single Pass

1. Sort `arr` ascending.
1. Inisialisasi `minAbs = Integer.MAX_VALUE`, `al = []`.
1. Loop `i` dari `0` sampai `n-2`:
   - `diff = |arr[i] - arr[i+1]|`
   - Jika `diff < minAbs` → update `minAbs`, **clear** list, tambahkan pasangan baru.
   - Jika `diff == minAbs` → tambahkan pasangan (tanpa clear).
1. Return `al`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(1) — tidak termasuk output list |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `arr = [4,2,1,3]`

Setelah sort: `[1,2,3,4]`

`minAbs = MAX, al = []`

| i | arr[i] | arr[i+1] | diff | diff < minAbs? | diff == minAbs? | al | minAbs |
| --- | ------ | -------- | ---- | -------------- | --------------- | --------------------- | ------ |
| 0 | 1 | 2 | 1 | ✅ | — | `[[1,2]]` | 1 |
| 1 | 2 | 3 | 1 | ❌ | ✅ | `[[1,2],[2,3]]` | 1 |
| 2 | 3 | 4 | 1 | ❌ | ✅ | `[[1,2],[2,3],[3,4]]` | 1 |

**Output: `[[1,2],[2,3],[3,4]]` ✅**

______________________________________________________________________

**Input:** `arr = [3,8,-10,23,19,-4,-14,27]`

Setelah sort: `[-14,-10,-4,3,8,19,23,27]`

| i | pair | diff | Aksi | al |
| --- | ------- | ---- | ------- | ----------------------------- |
| 0 | -14,-10 | 4 | new min | `[[-14,-10]]`, minAbs=4 |
| 1 | -10,-4 | 6 | > min | — |
| 2 | -4,3 | 7 | > min | — |
| 3 | 3,8 | 5 | > min | — |
| 4 | 8,19 | 11 | > min | — |
| 5 | 19,23 | 4 | == min | `[[-14,-10],[19,23]]` |
| 6 | 23,27 | 4 | == min | `[[-14,-10],[19,23],[23,27]]` |

**Output: `[[-14,-10],[19,23],[23,27]]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array dua elemen → satu pasangan → return `[[a,b]]`
- [ ] Semua elemen sama → diff selalu 0 → semua pasangan berurutan masuk
- [ ] Semua diff berbeda → hanya satu pasangan dengan diff terkecil

______________________________________________________________________

## 🔧 Kenapa `al.clear()` saat diff lebih kecil?

```java
if (min < minAbs) {
    minAbs = min;
    al.clear();                          // hapus semua pasangan lama
    al.add(List.of(arr[i], arr[i + 1])); // mulai fresh dengan pasangan baru
}
```

Saat ditemukan `diff` yang lebih kecil dari `minAbs` sebelumnya, semua pasangan yang sudah dikumpulkan tidak lagi valid — mereka punya selisih yang lebih besar dari minimum baru. Jadi list harus di-clear dan dimulai dari awal.

______________________________________________________________________

**Kenapa tidak dua pass (hitung minAbs dulu, baru kumpulkan)?**

Dua pass juga valid tapi butuh loop dua kali. Satu pass dengan `clear` lebih efisien karena hanya satu kali traversal.

______________________________________________________________________

## 🔧 Kenapa `Math.abs(arr[i] - arr[i+1])` selalu `arr[i+1] - arr[i]` setelah sort?

Setelah sort, `arr[i] <= arr[i+1]` selalu berlaku → `arr[i+1] - arr[i] >= 0`. Jadi `Math.abs` sebenarnya tidak diperlukan:

```java
// Sama hasilnya setelah sort:
int diff = Math.abs(arr[i] - arr[i+1]);
int diff = arr[i+1] - arr[i];  // lebih efisien
```

`Math.abs` tetap aman dipakai — hanya sedikit redundan.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan pola **"sort + single pass dengan running minimum"** — setelah sort, pasangan terbaik selalu berurutan, jadi cukup cek pasangan yang berdekatan. Trik `al.clear()` saat menemukan minimum baru lebih efisien dari dua pass terpisah karena hanya satu kali traversal. 🎯

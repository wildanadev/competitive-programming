# 56. Merge Intervals

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Sorting
- **Link**: [Problem](https://leetcode.com/problems/merge-intervals/)
- **Solution**: [Code](../../leetcode/MergeIntervals.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array interval `[start, end]`, gabungkan semua interval yang **overlapping** dan kembalikan array interval yang tidak saling tumpang tindih.

Contoh:

- `[[1,3],[2,6],[8,10],[15,18]]` → `[[1,6],[8,10],[15,18]]`
- `[[1,4],[4,5]]` → `[[1,5]]` (menyentuh di titik 4)

______________________________________________________________________

## 💡 Intuition

Setelah interval **diurutkan berdasarkan start**, interval yang overlapping pasti berurutan. Kita cukup satu pass: pertahankan `minValue` dan `maxValue` interval saat ini. Jika interval berikutnya dimulai sebelum atau tepat di `maxValue`, extend interval saat ini. Jika tidak, simpan interval saat ini dan mulai yang baru.

```
sort:  [1,3] [2,6] [8,10] [15,18]
              ↑ overlap, extend maxValue ke 6
                    ↑ tidak overlap, simpan [1,6] mulai baru
```

______________________________________________________________________

## 🔍 Approach

### Sort + Linear Merge

1. Sort interval berdasarkan `start` ascending.
1. Inisialisasi `minValue = intervals[0][0]`, `maxValue = intervals[0][1]`.
1. Loop `i` dari `1` sampai `n-1`:
   - Jika `intervals[i][0] <= maxValue` → overlapping → extend: `maxValue = max(maxValue, intervals[i][1])`.
   - Jika tidak → simpan `[minValue, maxValue]` ke list, update `minValue` dan `maxValue`.
1. Simpan interval terakhir setelah loop.
1. Konversi list ke `int[][]` dan return.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(n) — list untuk menyimpan hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `[[1,3],[2,6],[8,10],[15,18]]`

Setelah sort: `[[1,3],[2,6],[8,10],[15,18]]` (sudah terurut)

`minValue=1, maxValue=3, list=[]`

| i | interval | start\<=maxValue? | Aksi | minValue | maxValue | list |
| --- | -------- | ---------------- | ----------------------------------- | -------- | -------- | ---------------- |
| 1 | [2,6] | 2\<=3 ✅ | extend maxValue=max(3,6)=6 | 1 | 6 | `[]` |
| 2 | [8,10] | 8\<=6? ❌ | simpan [1,6], update min=8,max=10 | 8 | 10 | `[[1,6]]` |
| 3 | [15,18] | 15\<=10? ❌ | simpan [8,10], update min=15,max=18 | 15 | 18 | `[[1,6],[8,10]]` |

Setelah loop: simpan `[15,18]` → list=`[[1,6],[8,10],[15,18]]`

**Output: `[[1,6],[8,10],[15,18]]` ✅**

______________________________________________________________________

**Input:** `[[1,4],[4,5]]`

`minValue=1, maxValue=4`

| i | interval | start\<=maxValue? | Aksi | maxValue |
| --- | -------- | ---------------- | -------------------------- | -------- |
| 1 | [4,5] | 4\<=4 ✅ | extend maxValue=max(4,5)=5 | 5 |

Setelah loop: simpan `[1,5]`

**Output: `[[1,5]]` ✅** — menyentuh di titik 4 dianggap overlapping

______________________________________________________________________

**Input:** `[[2,3],[4,5],[6,7],[8,9],[1,10]]`

Setelah sort: `[[1,10],[2,3],[4,5],[6,7],[8,9]]`

`minValue=1, maxValue=10`

Semua interval berikutnya `start <= 10` → semua di-extend (tapi `maxValue` tetap 10 karena tidak ada yang lebih besar).

Setelah loop: simpan `[1,10]`

**Output: `[[1,10]]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu interval → return langsung (loop tidak jalan, simpan interval pertama di akhir)
- [ ] Semua overlapping → satu interval besar di output
- [ ] Tidak ada yang overlapping → output sama dengan input (setelah sort)
- [ ] `[4,5]` dan `[4,5]` identik → 4\<=5 → extend (max=5 tetap 5) → digabung menjadi satu

______________________________________________________________________

## 🔧 Kenapa `maxValue = Math.max(maxValue, intervals[i][1])`?

Tidak semua interval yang overlapping punya end lebih besar:

```
[1,10] dan [2,3]:
  start=2 <= maxValue=10 ✅ (overlapping)
  tapi end=3 < maxValue=10

Jika tidak pakai max:
  maxValue = 3 ← SALAH! interval [1,10] terpotong
Dengan max:
  maxValue = max(10,3) = 10 ✅
```

Kasus ini sering terjadi saat ada interval yang "terkandung" di dalam interval yang lebih besar.

______________________________________________________________________

## 🔧 Kenapa Simpan Interval Terakhir Setelah Loop?

```java
// Loop hanya menyimpan interval ke list saat menemukan yang tidak overlap
// Interval terakhir tidak pernah disimpan di dalam loop!
list.add(List.of(minValue, maxValue));  // ← harus ditambahkan di sini
```

Di dalam loop, kita simpan interval ke list saat **menemukan interval yang tidak overlap** (yaitu saat pindah ke interval baru). Interval terakhir tidak pernah punya "trigger" untuk disimpan karena tidak ada interval setelahnya.

______________________________________________________________________

## 📌 Key Takeaway

Merge Intervals adalah soal klasik **"sort lalu linear merge"** — setelah sort by start, interval yang overlapping selalu berurutan sehingga satu pass sudah cukup. Dua detail penting: `Math.max` untuk menangani interval yang "terkandung" di dalam interval lain, dan menambahkan interval terakhir setelah loop karena tidak ada trigger di dalam loop. 🎯

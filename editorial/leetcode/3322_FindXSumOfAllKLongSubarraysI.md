# 3322. Find X-Sum of All K-Long Subarrays I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Sliding Window, Sorting
- **Link**: [Problem](https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-i/)
- **Solution**: [Code](../../leetcode/FindXSumOfAllKLongSubarraysI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`, integer `k` (ukuran window), dan integer `x`. Untuk setiap window berukuran `k`, hitung **x-sum** — jumlah dari elemen dengan frekuensi **x terbesar**. Jika frekuensi sama, elemen dengan nilai **lebih besar** diprioritaskan.

Kembalikan array hasil x-sum untuk setiap window.

Contoh:

- `nums = [1,1,2,2,3,4,2,3]`, `k = 6`, `x = 2`
  - Window `[1,1,2,2,3,4]`: freq `{1:2, 2:2, 3:1, 4:1}` → top-2: `(2,freq=2)` dan `(1,freq=2)` → `2*2 + 1*2 = 6`
  - Window `[1,2,2,3,4,2]`: freq `{1:1, 2:3, 3:1, 4:1}` → top-2: `(2,freq=3)` dan `(4,freq=1)` → `2*3 + 4*1 = 10`

______________________________________________________________________

## 💡 Intuition

Untuk setiap window, kita butuh **top-x elemen berdasarkan frekuensi** (tiebreak: nilai lebih besar). Pendekatannya:

1. Hitung frekuensi tiap elemen dalam window dengan **HashMap**.
1. Konversi ke **List of `[freq, nilai]`** lalu **sort** — frekuensi descending, tiebreak nilai descending.
1. Ambil `x` elemen teratas, jumlahkan `freq * nilai`.

______________________________________________________________________

## 🔍 Approach

### Brute Force per Window — HashMap + Sort

Untuk setiap window `i` sampai `i+k-1`:

1. Bangun `freqMap` — hitung frekuensi setiap elemen.
1. Konversi ke `List<int[]>` berisi `[freq, nilai]`.
1. Sort descending by freq, tiebreak by nilai.
1. Loop `x` elemen pertama, hitung `xSum += freq * nilai`.
1. Simpan ke `ans[i]`.

**Kenapa `[freq, nilai]` bukan `[nilai, freq]`?**

Karena sort utamanya berdasarkan **frekuensi** — lebih mudah jika frekuensi di indeks `[0]` sebagai primary sort key.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------------------------- |
| **Time** | O(n × k) — rebuild HashMap per window O(k), sort O(m log m) di mana m = elemen unik |
| **Space** | O(m) — HashMap dan List per window |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,1,2,2,3,4,2,3]`, `k = 6`, `x = 2`

______________________________________________________________________

**i=0, window `nums[0..5]` = `[1,1,2,2,3,4]`:**

**Build freqMap:**

```
{1:2, 2:2, 3:1, 4:1}
```

**Konversi ke List `[freq, nilai]`:**

```
[[2,1], [2,2], [1,3], [1,4]]
```

**Sort (freq desc, nilai desc):**

```
[2,2] → freq=2, nilai=2
[2,1] → freq=2, nilai=1  (freq sama, nilai 2 > 1)
[1,4] → freq=1, nilai=4
[1,3] → freq=1, nilai=3
```

**Ambil x=2 teratas:**

```
j=0: xSum += 2 * 2 = 4
j=1: xSum += 2 * 1 = 2
xSum = 6
```

`ans[0] = 6`

______________________________________________________________________

**i=1, window `nums[1..6]` = `[1,2,2,3,4,2]`:**

**Build freqMap:**

```
{1:1, 2:3, 3:1, 4:1}
```

**Konversi + Sort:**

```
[3,2] → freq=3 tertinggi
[1,4] → freq=1, nilai 4 > 3 > 1
[1,3]
[1,1]
```

**Ambil x=2 teratas:**

```
j=0: xSum += 3 * 2 = 6
j=1: xSum += 1 * 4 = 4
xSum = 10
```

`ans[1] = 10`

______________________________________________________________________

**i=2, window `nums[2..7]` = `[2,2,3,4,2,3]`:**

**Build freqMap:**

```
{2:3, 3:2, 4:1}
```

**Sort:**

```
[3,2] → freq=3
[2,3] → freq=2
[1,4] → freq=1
```

**Ambil x=2 teratas:**

```
j=0: xSum += 3 * 2 = 6
j=1: xSum += 2 * 3 = 6
xSum = 12
```

`ans[2] = 12`

**Output: `[6, 10, 12]` ✅**

______________________________________________________________________

**Input:** `nums = [3,8,7,8,7]`, `k = 3`, `x = 2`

| i | window | freqMap | sorted list | xSum |
| --- | --------- | --------------- | ------------------- | ------------------ |
| 0 | `[3,8,7]` | `{3:1,8:1,7:1}` | `[1,8],[1,7],[1,3]` | 1*8 + 1*7 = **15** |
| 1 | `[8,7,8]` | `{8:2,7:1}` | `[2,8],[1,7]` | 2*8 + 1*7 = **23** |
| 2 | `[7,8,7]` | `{7:2,8:1}` | `[2,7],[1,8]` | 2*7 + 1*8 = **22** |

**Output: `[15, 23, 22]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `x >= jumlah elemen unik` → `j < freq.size()` menjaga agar tidak out of bounds → jumlah semua elemen
- [ ] Semua elemen sama → satu entry di List → x-sum = freq * nilai
- [ ] `x = 1` → hanya elemen dengan frekuensi tertinggi (atau nilai terbesar jika seri)

______________________________________________________________________

## 🔧 Detail Comparator Sort

```java
freq.sort((a, b) -> (b[0] != a[0]) ? (b[0] - a[0]) : (b[1] - a[1]));
```

Breakdown:

- `b[0] != a[0]` → frekuensi berbeda → sort by `b[0] - a[0]` (descending freq)
- `b[0] == a[0]` → frekuensi sama → sort by `b[1] - a[1]` (descending nilai)

Contoh tiebreak: `[2,2]` vs `[2,1]` → freq sama (2==2), nilai `2 > 1` → `[2,2]` lebih dulu.

______________________________________________________________________

## 📌 Key Takeaway

Pola soal ini adalah **per-window processing** — rebuild HashMap dan sort untuk setiap window. Meski O(n×k) bukan yang paling optimal, kode ini sangat mudah dipahami dan benar secara logika. Untuk optimasi, sliding window yang update HashMap secara incremental (tambah elemen baru, hapus elemen lama) mengurangi rebuild ke O(1) per slide — namun tetap butuh cara efisien untuk query top-x, seperti dua `TreeSet` yang dipakai di versi II soal ini. 🎯

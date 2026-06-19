# 704. Binary Search

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/binary-search/)
- **Solution**: [Code](../../leetcode/BinarySearch.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` yang **terurut ascending** dan integer `target`, kembalikan indeks `target` jika ada, atau `-1` jika tidak ada. Harus O(log n).

Contoh:

- `nums = [-1,0,3,5,9,12]`, `target = 9` → `4`
- `nums = [-1,0,3,5,9,12]`, `target = 2` → `-1`

______________________________________________________________________

## 💡 Intuition

Karena array terurut, kita bisa membagi rentang pencarian menjadi dua setiap iterasi. Bandingkan elemen tengah dengan `target`:

- Sama → ditemukan!
- Target lebih besar → cari di **kanan**
- Target lebih kecil → cari di **kiri**

Setiap iterasi membuang setengah dari kandidat yang tersisa.

______________________________________________________________________

## 🔍 Approach

### Binary Search Standar

1. `l = 0`, `r = n-1`.
1. Selama `l <= r`:
   - `m = l + (r-l)/2`
   - Jika `nums[m] == target` → return `m`.
   - Jika `nums[m] < target` → `l = m+1` (cari kanan).
   - Jika `nums[m] > target` → `r = m-1` (cari kiri).
1. Return `-1` jika tidak ditemukan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------- |
| **Time** | O(log n) — rentang terbagi dua setiap iterasi |
| **Space** | O(1) — hanya tiga pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [-1,0,3,5,9,12]`, `target = 9`

`l=0, r=5`

| l | r | m | nums[m] | Perbandingan | Aksi |
| --- | --- | --- | ------- | ------------ | -------- |
| 0 | 5 | 2 | 3 | 3 < 9 | l=3 |
| 3 | 5 | 4 | 9 | 9 == 9 ✅ | return 4 |

**Output: `4` ✅**

______________________________________________________________________

**Input:** `nums = [-1,0,3,5,9,12]`, `target = 2`

`l=0, r=5`

| l | r | m | nums[m] | Perbandingan | Aksi |
| --- | --- | --- | ------- | ------------ | ---- |
| 0 | 5 | 2 | 3 | 3 > 2 | r=1 |
| 0 | 1 | 0 | -1 | -1 < 2 | l=1 |
| 1 | 1 | 1 | 0 | 0 < 2 | l=2 |

`l=2 > r=1` → loop berhenti → return `-1`

**Output: `-1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Target di indeks pertama/terakhir → binary search tetap menemukan dalam O(log n)
- [ ] Array satu elemen → satu iterasi
- [ ] Target tidak ada → loop berakhir natural dengan `l > r`

______________________________________________________________________

## 🔧 Kenapa `m = l + (r - l) / 2`?

```java
int m = l + (r - l) / 2;  // ✅ aman dari overflow
int m = (l + r) / 2;      // ❌ l+r bisa overflow untuk array sangat besar
```

`r - l` dijamin tidak overflow karena keduanya valid indeks array. `l + (r-l)/2` matematis ekuivalen dengan `(l+r)/2` tapi lebih aman.

______________________________________________________________________

## 📌 Key Takeaway

Binary Search adalah algoritma fundamental — syaratnya array harus **terurut**. Tiga kondisi (`==`, `<`, `>`) menentukan arah pencarian berikutnya, mempersempit rentang `[l, r]` secara eksponensial. Pola `l <= r` sebagai kondisi loop dan `l = m+1` / `r = m-1` sebagai update adalah template standar yang berlaku untuk hampir semua variasi binary search. 🎯

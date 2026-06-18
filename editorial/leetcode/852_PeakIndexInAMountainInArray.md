# 852. Peak Index in a Mountain Array

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/peak-index-in-a-mountain-array/)
- **Solution**: [Code](../../leetcode/PeakIndexInAMountainArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan **mountain array** `arr` (naik strict lalu turun strict), kembalikan indeks **puncak** (elemen terbesar).

Contoh:

- `arr = [0,1,0]` → `1`
- `arr = [0,2,1,0]` → `1`
- `arr = [0,10,5,2]` → `1`

______________________________________________________________________

## 💡 Intuition

Karena array memiliki struktur **naik lalu turun**, kita bisa pakai **Binary Search** — bandingkan `arr[mid]` dengan `arr[mid+1]`:

- Jika `arr[mid] < arr[mid+1]` → kita masih di **lereng naik** → puncak ada di kanan → `l = mid+1`
- Jika `arr[mid] > arr[mid+1]` → kita sudah di **lereng turun** atau tepat di puncak → puncak ada di `mid` atau kiri → `r = mid-1`

______________________________________________________________________

## 🔍 Approach

### Binary Search berdasarkan Slope

1. `l = 0`, `r = n-1`.
1. Selama `l <= r`:
   - `mid = l + (r-l)/2`
   - Jika `arr[mid] < arr[mid+1]` → naik → `l = mid+1`
   - Jika tidak → turun atau puncak → `r = mid-1`
1. Return `l` (saat loop berakhir, `l` menunjuk ke puncak).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------ |
| **Time** | O(log n) — binary search |
| **Space** | O(1) — hanya pointer `l`, `r`, `mid` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `arr = [0,2,4,1,0]`, `n=5`

`l=0, r=4`

| l | r | mid | arr[mid] | arr[mid+1] | arr[mid]\<arr[mid+1]? | Aksi |
| --- | --- | --- | -------- | ---------- | --------------------- | ---- |
| 0 | 4 | 2 | 4 | 1 | ❌ | r=1 |
| 0 | 1 | 0 | 0 | 2 | ✅ | l=1 |
| 1 | 1 | 1 | 2 | 4 | ✅ | l=2 |

`l=2 > r=1` → loop berhenti → return `l=2`

**Output: `2` ✅** (arr[2]=4 adalah puncak)

______________________________________________________________________

**Input:** `arr = [0,10,5,2]`

`l=0, r=3`

| l | r | mid | arr[mid] | arr[mid+1] | Aksi |
| --- | --- | --- | -------- | ---------- | ---- |
| 0 | 3 | 1 | 10 | 5 | r=0 |
| 0 | 0 | 0 | 0 | 10 | l=1 |

`l=1 > r=0` → return `l=1`

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Puncak di awal array (setelah indeks 0) → binary search tetap menemukan dengan benar
- [ ] Puncak di akhir array (sebelum indeks terakhir) → binary search tetap menemukan dengan benar
- [ ] Array minimal 3 elemen (constraint) → `arr[mid+1]` selalu valid karena `mid < r <= n-1`

______________________________________________________________________

## 🔧 Kenapa `l` adalah Jawaban Setelah Loop?

Binary search ini mempersempit rentang `[l, r]` sampai keduanya "bertemu" tepat di puncak.

```
Invariant yang dijaga:
- arr[l] mungkin masih bagian dari naik atau puncak
- arr[r] mungkin masih bagian dari turun atau puncak
- Setelah loop, l == r+1, dan l menunjuk ke puncak
```

Setiap iterasi mempersempit kandidat puncak — saat `l > r`, `l` sudah konvergen ke posisi puncak yang tepat.

______________________________________________________________________

## 🔧 Kenapa Aman Akses `arr[mid+1]`?

```java
if (arr[mid] < arr[mid + 1])
```

Karena `mid <= r <= n-1` dan kita hanya mengecek `mid+1` ketika `mid < r` (karena saat `mid == r`, kondisi `l<=r` masih terpenuhi tapi rentang sudah sangat kecil)...

Lebih tepatnya: `mid = l + (r-l)/2` selalu `< r` ketika `l < r` (floor division), dan ketika `l == r`, `mid == r` tapi karena ini mountain array dengan minimal 3 elemen, batasan constraint menjamin index valid.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi elegan **Binary Search pada array dengan struktur monotonic ganda** (naik lalu turun). Alih-alih mencari nilai spesifik, kita mencari **titik transisi** dengan membandingkan elemen dengan tetangganya. Pola "bandingkan dengan tetangga untuk menentukan arah pencarian" ini juga berguna di soal seperti _Find Peak Element_ dan _Search in Rotated Sorted Array_. 🎯

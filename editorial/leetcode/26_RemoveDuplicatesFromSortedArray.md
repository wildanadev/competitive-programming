# 26. Remove Duplicates from Sorted Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
- **Solution**: [Code](../../leetcode/RemoveDuplicatesFromSortedArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` yang sudah **terurut**, hapus duplikat secara **in-place** sehingga setiap elemen hanya muncul sekali. Kembalikan jumlah elemen unik `k`.

Contoh:

- `nums = [1,1,2]` → `2`, nums = `[1,2,_]`
- `nums = [0,0,1,1,1,2,2,3,3,4]` → `5`, nums = `[0,1,2,3,4,_,_,_,_,_]`

______________________________________________________________________

## 💡 Intuition

Karena array sudah terurut, duplikat pasti **bersebelahan**. Gunakan variabel `min` untuk track elemen terakhir yang dimasukkan — kalau elemen sekarang sama dengan `min`, skip. Kalau berbeda, masukkan ke posisi `count`.

`min` diinisialisasi `-101` karena constraint soal menyatakan nilai minimum adalah `-100`, jadi `-101` dijamin tidak ada di array.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `min = -101` dan `count = 0`
1. Loop setiap elemen `i` di `nums`:
   - Kalau `i != min` → masukkan ke `nums[count++]`, update `min = i`
   - Kalau `i == min` → skip (duplikat)
1. Return `count`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(1) — in-place, tidak pakai array baru |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1, 1, 2]`

**Init:** `min = -101, count = 0`

| i | i != min? | Aksi | min | count | nums |
| --- | --------- | ------------------ | --- | ----- | ------- |
| 1 | ✅ -101 | nums[0]=1, count++ | 1 | 1 | [1,1,2] |
| 1 | ❌ ==1 | skip | 1 | 1 | [1,1,2] |
| 2 | ✅ !=1 | nums[1]=2, count++ | 2 | 2 | [1,2,2] |

**return `2` ✅** → 2 elemen pertama `[1,2]` adalah hasil

______________________________________________________________________

**Input:** `nums = [0,0,1,1,1,2,2,3,3,4]`

**Init:** `min = -101, count = 0`

| i | i != min? | Aksi | min | count |
| --- | --------- | ------------------ | --- | ----- |
| 0 | ✅ | nums[0]=0, count++ | 0 | 1 |
| 0 | ❌ | skip | 0 | 1 |
| 1 | ✅ | nums[1]=1, count++ | 1 | 2 |
| 1 | ❌ | skip | 1 | 2 |
| 1 | ❌ | skip | 1 | 2 |
| 2 | ✅ | nums[2]=2, count++ | 2 | 3 |
| 2 | ❌ | skip | 2 | 3 |
| 3 | ✅ | nums[3]=3, count++ | 3 | 4 |
| 3 | ❌ | skip | 3 | 4 |
| 4 | ✅ | nums[4]=4, count++ | 4 | 5 |

**return `5` ✅** → 5 elemen pertama `[0,1,2,3,4]` adalah hasil

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → return `1`
- [ ] Semua elemen sama → return `1`
- [ ] Tidak ada duplikat → return `nums.length`
- [ ] Nilai minimum `-100` → aman karena `min` diinit `-101`

______________________________________________________________________

## 📌 Key Takeaway

Trick utama adalah inisialisasi `min = -101` — nilai di luar constraint soal (`-100 ≤ nums[i] ≤ 100`) yang menjamin elemen pertama array selalu dimasukkan. Pola **pointer `count` sebagai index pengisi** ini identik dengan soal Remove Element (#27), bedanya kondisi skip adalah elemen yang **sama** bukan elemen yang **sama dengan val**. 🎯

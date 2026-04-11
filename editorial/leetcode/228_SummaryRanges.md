# 228. Summary Ranges

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/summary-ranges/)
- **Solution**: [Code](../../leetcode/SummaryRanges.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer **terurut & unik** `nums`, kembalikan list range terkecil yang mencakup semua angka. Format output:

- `"a->b"` kalau `a != b`
- `"a"` kalau `a == b`

Contoh:

- `nums = [0,1,2,4,5,7]` → `["0->2","4->5","7"]`
- `nums = [0,2,3,4,6,8,9]` → `["0","2->4","6","8->9"]`

______________________________________________________________________

## 💡 Intuition

Jalan array sambil track `start` dan `end` range yang sedang berjalan. Kalau urutan **terputus** (`nums[i] - 1 != nums[i-1]`), simpan range sebelumnya dan mulai range baru. Setelah loop, tambah range terakhir secara manual.

______________________________________________________________________

## 🔍 Approach

1. Kalau array kosong → return `[]`
1. Inisialisasi `start = end = nums[0]`
1. Loop `i` dari `1` sampai `nums.length`:
   - Kalau `nums[i] - 1 != nums[i-1]` → urutan **putus**:
     - `end = nums[i-1]`
     - Tambah `"start"` atau `"start->end"` ke `ans`
     - Reset `start = nums[i]`
1. Setelah loop → tambah range terakhir manual
1. Return `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------- |
| **Time** | O(n) — satu kali loop |
| **Space** | O(n) — list hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [0,1,2,4,5,7]`

**Init:** `start=0, end=0`

| i | nums[i] | nums[i]-1==nums[i-1]? | Aksi | ans |
| --- | ------- | --------------------- | -------------------------- | --------------- |
| 1 | 1 | 1-1=0==0 ✅ lanjut | - | [] |
| 2 | 2 | 2-1=1==1 ✅ lanjut | - | [] |
| 3 | 4 | 4-1=3≠2 ❌ putus | end=2, add "0->2", start=4 | ["0->2"] |
| 4 | 5 | 5-1=4==4 ✅ lanjut | - | ["0->2"] |
| 5 | 7 | 7-1=6≠5 ❌ putus | end=5, add "4->5", start=7 | ["0->2","4->5"] |

**Setelah loop:** `end=7, start==end` → add `"7"`

**return `["0->2","4->5","7"]` ✅**

______________________________________________________________________

**Input:** `nums = [0,2,3,4,6,8,9]`

**Init:** `start=0, end=0`

| i | nums[i] | Putus? | Aksi | ans |
| --- | ------- | ---------------- | -------------------------- | ---------------- |
| 1 | 2 | 2-1=1≠0 ❌ putus | end=0, add "0", start=2 | ["0"] |
| 2 | 3 | 3-1=2==2 ✅ | - | ["0"] |
| 3 | 4 | 4-1=3==3 ✅ | - | ["0"] |
| 4 | 6 | 6-1=5≠4 ❌ putus | end=4, add "2->4", start=6 | ["0","2->4"] |
| 5 | 8 | 8-1=7≠6 ❌ putus | end=6, add "6", start=8 | ["0","2->4","6"] |
| 6 | 9 | 9-1=8==8 ✅ | - | ["0","2->4","6"] |

**Setelah loop:** `start=8, end=9` → add `"8->9"`

**return `["0","2->4","6","8->9"]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array kosong → return `[]`
- [ ] Array satu elemen → return `["x"]`
- [ ] Semua elemen berurutan → satu range saja
- [ ] Tidak ada elemen berurutan → setiap elemen jadi range sendiri

______________________________________________________________________

## 📌 Key Takeaway

Range terakhir **tidak pernah di-add di dalam loop** karena loop berhenti saat `i < nums.length` — range terakhir selalu harus ditambah manual setelah loop. Kondisi putus `nums[i] - 1 != nums[i-1]` lebih elegan dari cek `nums[i] != nums[i-1] + 1` karena secara matematis identik tapi lebih mudah dibaca. 🎯

# 455. Assign Cookies

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers, Greedy, Sorting
- **Link**: [Problem](https://leetcode.com/problems/assign-cookies/)
- **Solution**: [Code](../../leetcode/AssignCookies.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `g` (greed factor tiap anak) dan array `s` (ukuran tiap kue), assign kue ke anak semaksimal mungkin. Seorang anak `i` puas jika mendapat kue berukuran `s[j] >= g[i]`. Setiap anak hanya boleh mendapat **satu kue** dan setiap kue hanya boleh diberikan ke **satu anak**. Kembalikan jumlah anak yang bisa dipuaskan.

Contoh:

- `g = [1,2,3]`, `s = [1,1]` → `1`
- `g = [1,2]`, `s = [1,2,3]` → `2`

______________________________________________________________________

## 💡 Intuition

Gunakan strategi **Greedy** — cocokkan kue **terkecil yang cukup** ke anak dengan **greed factor terkecil** terlebih dahulu. Dengan mengurutkan kedua array dan memproses dari yang terkecil, kita menghindari pemborosan kue besar untuk anak yang sebenarnya bisa dipuaskan oleh kue kecil.

Analoginya: jika kue terkecil tidak bisa memuaskan anak paling "tidak lapar", maka kue itu tidak berguna untuk siapa pun → buang dan coba kue berikutnya.

______________________________________________________________________

## 🔍 Approach

### Greedy + Two Pointers

1. Sort `g` dan `s` secara ascending.
1. Inisialisasi `sg = 0` (pointer anak) dan `ss = 0` (pointer kue).
1. Selama masih ada anak dan kue:
   - Jika `s[ss] >= g[sg]` → kue cukup, anak terpuaskan → increment `sg`.
   - Selalu increment `ss` (kue dipakai atau dibuang).
1. Return `sg` (total anak yang terpuaskan).

> Kunci greedy: jika kue tidak cukup untuk anak terkecil sekalipun, kue itu tidak akan berguna → lewati. Jika cukup, selalu berikan ke anak terkecil yang tersisa agar kue lebih besar tersimpan untuk anak yang lebih "lapar".

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------- |
| **Time** | O(n log n + m log m) — dominan di sorting; n = panjang `g`, m = panjang `s` |
| **Space** | O(1) — hanya pointer tambahan (tidak termasuk space sorting) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `g = [1,2,3]`, `s = [1,1]`

Setelah sort: `g = [1,2,3]`, `s = [1,1]`

| ss | sg | s[ss] | g[sg] | s[ss] >= g[sg]? | Aksi |
| --- | --- | ----------------- | ----- | --------------- | ----------------- |
| 0 | 0 | 1 | 1 | ✅ | sg++ → sg=1, ss=1 |
| 1 | 1 | 1 | 2 | ❌ | ss=2 |
| — | — | ss=2 habis → stop | | | |

**Output: `1` ✅**

______________________________________________________________________

**Input:** `g = [1,2]`, `s = [1,2,3]`

Setelah sort: `g = [1,2]`, `s = [1,2,3]`

| ss | sg | s[ss] | g[sg] | s[ss] >= g[sg]? | Aksi |
| --- | --- | ----------------- | ----- | --------------- | ----------------- |
| 0 | 0 | 1 | 1 | ✅ | sg++ → sg=1, ss=1 |
| 1 | 1 | 2 | 2 | ✅ | sg++ → sg=2, ss=2 |
| — | — | sg=2 habis → stop | | | |

**Output: `2` ✅**

______________________________________________________________________

**Input:** `g = [1,2,3]`, `s = [3]`

Setelah sort: `g = [1,2,3]`, `s = [3]`

| ss | sg | s[ss] | g[sg] | s[ss] >= g[sg]? | Aksi |
| --- | --- | ----------------- | ----- | --------------- | ----------------- |
| 0 | 0 | 3 | 1 | ✅ | sg++ → sg=1, ss=1 |
| — | — | ss=1 habis → stop | | | |

**Output: `1` ✅** — kue terbesar diberikan ke anak paling kecil, bukan disia-siakan

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada kue → `s = []` → return `0`
- [ ] Tidak ada anak → `g = []` → return `0`
- [ ] Semua kue terlalu kecil → `g = [10]`, `s = [1,2,3]` → return `0`
- [ ] Lebih banyak kue dari anak → semua anak bisa terpuaskan jika ukuran cukup
- [ ] Semua greed factor sama → kue didistribusikan satu per satu dari terkecil

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik **Greedy Matching** — cocokkan resource terkecil ke kebutuhan terkecil yang bisa terpenuhi. Pola ini muncul di banyak variasi soal seperti _Task Scheduler_, _Meeting Rooms_, dan _Non-overlapping Intervals_. Kunci validitas greedy: membuang kue yang tidak cukup (bukan menyimpannya) dan selalu melayani anak termudah terlebih dahulu membuat setiap keputusan lokal juga optimal secara global. 🎯

# 202. Happy Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, Math, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/happy-number/)
- **Solution**: [Code](../../leetcode/HappyNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah angka disebut **happy number** jika proses berikut berakhir di `1`:

1. Mulai dari angka `n`.
1. Gantikan `n` dengan **jumlah kuadrat setiap digitnya**.
1. Ulangi sampai hasilnya `1` (happy) atau masuk ke **cycle** yang tidak pernah mencapai `1` (tidak happy).

Contoh:

- `n = 19` → `1² + 9² = 82` → `8² + 2² = 68` → ... → `1` ✅ → `true`
- `n = 2` → `4` → `16` → `37` → `58` → `89` → `145` → `42` → `20` → `4` (cycle!) → `false`

______________________________________________________________________

## 💡 Intuition

Proses ini pasti akan **berulang** jika tidak pernah mencapai `1` — karena nilai angka selalu terbatas (tidak bisa tumbuh selamanya). Ini identik dengan deteksi cycle pada linked list.

Gunakan **Floyd's Cycle Detection** (fast & slow pointer):

- `slow` mengambil satu langkah (satu kali `getNextNumber`).
- `fast` mengambil dua langkah (dua kali `getNextNumber`).

Dua kemungkinan:

1. `fast` mencapai `1` → happy number.
1. `fast == slow` (bertemu di cycle yang bukan `1`) → bukan happy number.

______________________________________________________________________

## 🔍 Approach

### Floyd's Cycle Detection + `getNextNumber`

**Helper `getNextNumber(n)`:**

- Loop selama `n > 0`:
  - Ambil digit terakhir: `digit = n % 10`
  - Tambahkan `digit²` ke `res`
  - Hapus digit terakhir: `n /= 10`
- Return `res`

**`isHappy(n)`:**

1. Inisialisasi `s = getNext(n)` dan `f = getNext(getNext(n))`.
1. Selama `s != f`:
   - Jika `f == 1` → return `true`.
   - `s` maju satu langkah, `f` maju dua langkah.
1. Setelah loop → return `f == 1`.

> `f == 1` dicek **di dalam loop** untuk menangani kasus ketika `fast` mencapai `1` sebelum bertemu `slow`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(log n) per langkah × O(n) langkah sampai cycle terdeteksi |
| **Space** | O(1) — hanya dua pointer tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 19`

**Langkah `getNextNumber`:**

- `19` → `1²+9² = 82`
- `82` → `8²+2² = 68`
- `68` → `6²+8² = 100`
- `100` → `1²+0²+0² = 1` ✅

`s = getNext(19) = 82`
`f = getNext(getNext(19)) = getNext(82) = 68`

| Iterasi | s | f | s==f? | f==1? |
| ------- | --- | --- | ----- | ------------------ |
| init | 82 | 68 | ❌ | — |
| 1 | 68 | 1 | ❌ | ✅ → return `true` |

**Output: `true` ✅**

______________________________________________________________________

**Input:** `n = 2`

Urutan dari `2`: `4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4` (cycle kembali ke `4`)

`s = getNext(2) = 4`
`f = getNext(getNext(2)) = getNext(4) = 16`

| Iterasi | s | f | s==f? | f==1? |
| ------- | --- | --- | --------- | ----- |
| init | 4 | 16 | ❌ | — |
| 1 | 16 | 58 | ❌ | ❌ |
| 2 | 37 | 145 | ❌ | ❌ |
| 3 | 58 | 20 | ❌ | ❌ |
| 4 | 89 | 89 | ✅ → stop | — |

`f = 89 ≠ 1` → return `false`

**Output: `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → `s = 1`, `f = 1` → `s == f` langsung, `f == 1` → return `true`
- [ ] `n = 7` → happy number, membutuhkan beberapa iterasi sebelum mencapai `1`
- [ ] Angka besar → `getNextNumber` selalu menghasilkan angka yang jauh lebih kecil sehingga proses konvergen

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh menarik di mana **Floyd's Cycle Detection** diterapkan di luar konteks linked list — ke dalam urutan angka yang dihasilkan oleh fungsi matematika. Selama ada kemungkinan cycle dalam suatu urutan nilai terbatas, algoritma fast & slow pointer bisa diterapkan dengan O(1) space. Pola yang sama bisa digunakan di soal _Find the Duplicate Number_ dan _Linked List Cycle_. 🎯

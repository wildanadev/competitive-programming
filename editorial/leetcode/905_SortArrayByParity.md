# 905. Sort Array By Parity

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers, Sorting
- **Link**: [Problem](https://leetcode.com/problems/sort-array-by-parity/)
- **Solution**: [Code](../../leetcode/SortArrayByParity.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, pindahkan semua **bilangan genap ke depan** diikuti semua **bilangan ganjil di belakang**. Urutan antar elemen genap maupun antar elemen ganjil boleh sembarang.

Contoh:

- `nums = [3,1,2,4]` → `[2,4,3,1]` (atau `[4,2,3,1]`, `[2,4,1,3]`, dll.)
- `nums = [0]` → `[0]`

______________________________________________________________________

## 💡 Intuition

Gunakan dua pointer `l` (kiri) dan `r` (kanan) yang bergerak dari kedua ujung menuju tengah. Invariant yang dijaga:

- Semua elemen di **kiri `l`** sudah pasti genap.
- Semua elemen di **kanan `r`** sudah pasti ganjil.

Ketika `nums[l]` ganjil dan `nums[r]` genap → keduanya di posisi salah → swap. Setelah swap (atau jika sudah di posisi benar), majukan pointer yang sudah berada di posisi yang benar.

Parity check menggunakan bitwise `(x & 1)`:

- `(x & 1) == 0` → genap
- `(x & 1) == 1` → ganjil

______________________________________________________________________

## 🔍 Approach

### Two Pointers In-place

1. Inisialisasi `l = 0`, `r = nums.length - 1`.
1. Selama `l < r`:
   - Jika `nums[l]` ganjil **dan** `nums[r]` genap → swap keduanya.
   - Jika `nums[l]` genap → `l++` (sudah di posisi benar).
   - Jika `nums[r]` ganjil → `r--` (sudah di posisi benar).
1. Return `nums`.

> Dua kondisi `if` terakhir (bukan `else if`) penting — setelah swap, kedua elemen langsung berada di posisi benar sehingga **kedua pointer bisa maju sekaligus**.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------- |
| **Time** | O(n) — setiap elemen dikunjungi maksimal sekali |
| **Space** | O(1) — swap in-place, tidak ada array tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,1,2,4]`

`l = 0`, `r = 3`

| l | r | nums[l] | nums[r] | Swap? | Aksi pointer | nums |
| --- | --- | ------------- | --------- | ----- | ------------ | ----------- |
| 0 | 3 | 3 (ganjil) | 4 (genap) | ✅ | l++, r-- | `[4,1,2,3]` |
| 1 | 2 | 1 (ganjil) | 2 (genap) | ✅ | l++, r-- | `[4,2,1,3]` |
| 2 | 1 | l >= r → stop | | | | |

**Output: `[4,2,1,3]` ✅**

______________________________________________________________________

**Input:** `nums = [0,2,1,3,4]`

`l = 0`, `r = 4`

| l | r | nums[l] | nums[r] | Swap? | Aksi pointer | nums |
| --- | --- | ------------- | --------- | ----- | ------------ | ------------- |
| 0 | 4 | 0 (genap) | 4 (genap) | ❌ | l++ | `[0,2,1,3,4]` |
| 1 | 4 | 2 (genap) | 4 (genap) | ❌ | l++ | `[0,2,1,3,4]` |
| 2 | 4 | 1 (ganjil) | 4 (genap) | ✅ | l++, r-- | `[0,2,4,3,1]` |
| 3 | 3 | l >= r → stop | | | | |

**Output: `[0,2,4,3,1]` ✅**

______________________________________________________________________

**Input:** `nums = [2,1,3,4,6]`

`l = 0`, `r = 4`

| l | r | nums[l] | nums[r] | Swap? | Aksi pointer | nums |
| --- | --- | ------------- | --------- | ----- | ------------ | ------------- |
| 0 | 4 | 2 (genap) | 6 (genap) | ❌ | l++ | `[2,1,3,4,6]` |
| 1 | 4 | 1 (ganjil) | 6 (genap) | ✅ | l++, r-- | `[2,6,3,4,1]` |
| 2 | 3 | 3 (ganjil) | 4 (genap) | ✅ | l++, r-- | `[2,6,4,3,1]` |
| 3 | 2 | l >= r → stop | | | | |

**Output: `[2,6,4,3,1]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua genap → `nums[r]` selalu genap → swap tidak pernah terjadi → `r` tidak bergerak, `l` terus maju hingga `l >= r`
- [ ] Semua ganjil → `nums[l]` selalu ganjil → swap tidak pernah terjadi → `l` tidak bergerak, `r` terus mundur hingga `l >= r`
- [ ] Satu elemen → `l == r` langsung → langsung return
- [ ] Sudah terurut genap-ganjil → tidak ada swap, pointer maju cepat

______________________________________________________________________

## 📌 Key Takeaway

Two Pointers in-place adalah pendekatan paling efisien untuk soal partisi array — tidak perlu alokasi memori tambahan. Kunci desainnya ada pada **dua `if` terpisah** (bukan `else if`) yang memungkinkan kedua pointer maju sekaligus setelah swap, menghindari iterasi yang tidak perlu. Pola swap in-place dengan dua pointer ini juga muncul di soal _Move Zeroes_, _Sort Colors_, dan _Partition Label_. 🎯

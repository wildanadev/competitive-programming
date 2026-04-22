# 922. Sort Array By Parity II

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers, Sorting
- **Link**: [Problem](https://leetcode.com/problems/sort-array-by-parity-ii/)
- **Solution**: [Code](../../leetcode/SortArrayByParityII.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dengan panjang genap di mana separuh elemen adalah genap dan separuh lainnya ganjil, susun ulang array sehingga:

- Indeks **genap** (0, 2, 4, ...) berisi bilangan **genap**.
- Indeks **ganjil** (1, 3, 5, ...) berisi bilangan **ganjil**.

Contoh:

- `nums = [4,2,5,7]` → `[4,5,2,7]` (atau susunan lain yang valid)
- `nums = [2,3]` → `[2,3]`

______________________________________________________________________

## 💡 Intuition

Gunakan dua pointer yang berjalan dengan **step 2** — `i` hanya mengunjungi indeks genap (0, 2, 4, ...) dan `j` hanya mengunjungi indeks ganjil (1, 3, 5, ...).

- Jika `nums[i]` sudah genap → posisi benar, `i` maju.
- Jika `nums[j]` sudah ganjil → posisi benar, `j` maju.
- Jika `nums[i]` ganjil **dan** `nums[j]` genap → keduanya salah posisi → swap, lalu keduanya maju.

Karena soal menjamin jumlah genap dan ganjil sama, setiap elemen yang salah posisi pasti punya pasangan yang salah posisi juga — swap selalu valid.

______________________________________________________________________

## 🔍 Approach

### Two Pointers dengan Step 2

1. Inisialisasi `i = 0` (pointer indeks genap), `j = 1` (pointer indeks ganjil).
1. Selama `i < n` dan `j < n`:
   - Jika `nums[i]` genap → `i += 2` (sudah benar).
   - Else if `nums[j]` ganjil → `j += 2` (sudah benar).
   - Else → `nums[i]` ganjil dan `nums[j]` genap → swap, `i += 2`, `j += 2`.
1. Return `nums`.

> `else if` digunakan secara sengaja — dalam satu iterasi, hanya satu pointer yang perlu diperiksa karena kondisi swap baru terjadi ketika **keduanya** salah posisi.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------- |
| **Time** | O(n) — setiap elemen dikunjungi maksimal sekali |
| **Space** | O(1) — swap in-place, tidak ada array tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [4,2,5,7]`

`i = 0`, `j = 1`

| i | j | nums[i] | nums[j] | Kondisi | Aksi | nums |
| --- | --- | ------------- | --------- | ------------------------- | ---------------- | ----------- |
| 0 | 1 | 4 (genap) | 2 (genap) | nums[i] genap ✅ | i+=2 | `[4,2,5,7]` |
| 2 | 1 | 5 (ganjil) | 2 (genap) | nums[j] ganjil? ❌ → swap | swap, i+=2, j+=2 | `[4,5,2,7]` |
| 4 | 3 | i >= n → stop | | | | |

**Output: `[4,5,2,7]` ✅**

______________________________________________________________________

**Input:** `nums = [3,4,1,2]`

`i = 0`, `j = 1`

| i | j | nums[i] | nums[j] | Kondisi | Aksi | nums |
| --- | --- | ------------- | --------- | -------------------------------------------- | ---------------- | ----------- |
| 0 | 1 | 3 (ganjil) | 4 (genap) | nums[i] genap? ❌, nums[j] ganjil? ❌ → swap | swap, i+=2, j+=2 | `[4,3,1,2]` |
| 2 | 3 | 1 (ganjil) | 2 (genap) | nums[i] genap? ❌, nums[j] ganjil? ❌ → swap | swap, i+=2, j+=2 | `[4,3,2,1]` |
| 4 | 5 | i >= n → stop | | | | |

**Output: `[4,3,2,1]` ✅**

______________________________________________________________________

**Input:** `nums = [2,1,4,3]`

`i = 0`, `j = 1`

| i | j | nums[i] | nums[j] | Kondisi | Aksi | nums |
| --- | --- | ------------- | ---------- | ---------------- | ---- | ----------- |
| 0 | 1 | 2 (genap) | 1 (ganjil) | nums[i] genap ✅ | i+=2 | `[2,1,4,3]` |
| 2 | 1 | 4 (genap) | 1 (ganjil) | nums[i] genap ✅ | i+=2 | `[2,1,4,3]` |
| 4 | 1 | i >= n → stop | | | | |

**Output: `[2,1,4,3]` ✅** — array sudah valid dari awal, tidak ada swap

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array sudah valid → tidak ada swap, semua pointer maju tanpa kondisi swap
- [ ] Semua indeks genap salah → `i` selalu menunggu, `j` maju sampai ketemu genap, lalu swap
- [ ] `n = 2` → satu pasang `(i=0, j=1)`, satu iterasi

______________________________________________________________________

## 📌 Key Takeaway

Variasi Two Pointers dengan **step 2** adalah pola yang natural untuk soal yang membedakan indeks genap dan ganjil. Kuncinya adalah setiap pointer hanya "bertanggung jawab" atas indeks miliknya — `i` hanya peduli indeks 0,2,4,... dan `j` hanya peduli 1,3,5,.... Penggunaan `else if` di sini berbeda dari #905 yang memakai dua `if` terpisah — pilihan antara keduanya bergantung pada apakah kedua pointer perlu maju **secara independen** atau hanya satu yang perlu maju per kondisi. 🎯

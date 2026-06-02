# 1. Two Sum

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/two-sum/)
- **Solution**: [Code](../../leetcode/TwoSum.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `target`, kembalikan **indeks dua elemen** yang jumlahnya sama dengan `target`. Setiap input punya tepat satu solusi dan elemen yang sama tidak boleh dipakai dua kali.

Contoh:

- `nums = [2,7,11,15]`, `target = 9` → `[0,1]` (2+7=9)
- `nums = [3,2,4]`, `target = 6` → `[1,2]` (2+4=6)
- `nums = [3,3]`, `target = 6` → `[0,1]`

______________________________________________________________________

## 💡 Intuition

Untuk setiap elemen `nums[i]`, kita butuh tahu apakah **complement-nya** (`target - nums[i]`) sudah pernah ditemukan sebelumnya. Gunakan **HashMap** untuk menyimpan `{nilai → indeks}` dari elemen-elemen yang sudah diproses.

Dengan memeriksa HashMap **sebelum** memasukkan elemen saat ini, kita memastikan:

1. Tidak ada elemen yang berpasangan dengan dirinya sendiri.
1. Jika complement ditemukan, pasti berasal dari indeks yang berbeda.

______________________________________________________________________

## 🔍 Approach

### One-Pass HashMap

1. Inisialisasi HashMap kosong.
1. Loop `i` dari `0` sampai `n-1`:
   - Hitung `comp = target - nums[i]`.
   - Jika `comp` ada di map → return `[map.get(comp), i]`.
   - Jika tidak → `map.put(nums[i], i)` (simpan untuk pencarian berikutnya).
1. Return `[]` (tidak akan tercapai per constraints soal).

> **Kunci**: `put` dilakukan **setelah** cek — sehingga elemen tidak bisa berpasangan dengan dirinya sendiri, dan duplikat ditangani secara natural.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------- |
| **Time** | O(n) — satu pass, setiap elemen diproses sekali |
| **Space** | O(n) — HashMap menyimpan maksimal n entry |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,7,11,15]`, `target = 9`

| i | nums[i] | comp = 9-nums[i] | map.containsKey(comp)? | Aksi | map |
| --- | ------- | ---------------- | ---------------------- | -------- | ------- |
| 0 | 2 | 7 | ❌ | put(2,0) | `{2:0}` |
| 1 | 7 | 2 | ✅ → return `[0,1]` | — | — |

**Output: `[0,1]` ✅**

______________________________________________________________________

**Input:** `nums = [3,2,4]`, `target = 6`

| i | nums[i] | comp | contains? | Aksi | map |
| --- | ------- | ---- | ------------------- | -------- | ----------- |
| 0 | 3 | 3 | ❌ | put(3,0) | `{3:0}` |
| 1 | 2 | 4 | ❌ | put(2,1) | `{3:0,2:1}` |
| 2 | 4 | 2 | ✅ → return `[1,2]` | — | — |

**Output: `[1,2]` ✅**

______________________________________________________________________

**Input:** `nums = [3,3]`, `target = 6`

| i | nums[i] | comp | contains? | Aksi | map |
| --- | ------- | ---- | ------------------- | -------- | ------- |
| 0 | 3 | 3 | ❌ | put(3,0) | `{3:0}` |
| 1 | 3 | 3 | ✅ → return `[0,1]` | — | — |

**Output: `[0,1]` ✅** — duplikat ditangani dengan benar!

______________________________________________________________________

**Input:** `nums = [2,2]`, `target = 4`

| i | nums[i] | comp | contains? | Aksi | map |
| --- | ------- | ---- | ------------------- | -------- | ------- |
| 0 | 2 | 2 | ❌ | put(2,0) | `{2:0}` |
| 1 | 2 | 2 | ✅ → return `[0,1]` | — | — |

**Output: `[0,1]` ✅** — tidak ada masalah karena `put` setelah cek

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Duplikat nilai `[2,2]` → aman karena `put` setelah cek
- [ ] Negatif `[-1,-2,-3,-4,-5]`, `target=-8` → `-3 + -5 = -8` → `[2,4]`
- [ ] Elemen `0` → `[0,4,3,0]`, `target=0` → `[0,3]`

______________________________________________________________________

## 🔧 Kenapa `put` Setelah Cek, Bukan Sebelum?

```java
// BENAR — put setelah cek
if (map.containsKey(comp)) return new int[]{map.get(comp), i};
map.put(nums[i], i);

// BERMASALAH — put sebelum cek
map.put(nums[i], i);
if (map.containsKey(comp) && map.get(comp) != i) ...
```

Dengan `put` **setelah** cek:

- Saat `i=0`, map masih kosong → elemen tidak bisa menemukan dirinya sendiri.
- Tidak perlu kondisi `map.get(comp) != i` — karena elemen saat ini belum ada di map.

```
nums = [3,3], target=6, i=1
Sebelum cek: map = {3:0} (hanya i=0)
comp = 3, map.get(3) = 0 ≠ 1 → valid ✅

Jika put sebelum cek: map = {3:1} (i=0 tertimpa i=1)
comp = 3, map.get(3) = 1 == i=1 → butuh cek tambahan!
```

______________________________________________________________________

## 📌 Key Takeaway

Two Sum adalah soal paling fundamental untuk pola **"complement lookup"** — simpan yang sudah dilihat, cari pasangannya. One-pass HashMap lebih elegan dari two-pass karena `put` setelah cek menghilangkan kebutuhan kondisi `map.get(comp) != i` dan menangani semua kasus duplikat secara natural. Pola ini menjadi dasar untuk banyak soal lain seperti _Two Sum II_, _Three Sum_, dan _Four Sum_. 🎯

# 215. Kth Largest Element in an Array

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Divide and Conquer, Sorting, Heap (Priority Queue), Quickselect
- **Link**: [Problem](https://leetcode.com/problems/kth-largest-element-in-an-array/)
- **Solution**: [Code](../../leetcode/KthLargestElementInAnArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` dan integer `k`, kembalikan elemen **terbesar ke-k** dalam array (bukan ke-k distinct).

Contoh:

- `nums = [3,2,1,5,6,4]`, `k = 2` → `5`
- `nums = [3,2,3,1,2,4,5,5,6]`, `k = 4` → `4`

______________________________________________________________________

## 💡 Intuition

Gunakan **Min Heap berukuran k** — pertahankan `k` elemen terbesar di heap. Elemen terkecil di heap (top) adalah elemen terbesar ke-k.

Cara kerjanya:

- Setiap elemen baru masuk ke heap.
- Jika ukuran heap melebihi `k`, buang elemen terkecil (pop dari top).
- Setelah semua elemen diproses, top heap adalah jawaban.

```
k=2, nums=[3,2,1,5,6,4]

Masuk 3: heap=[3]
Masuk 2: heap=[2,3]
Masuk 1: heap=[2,3], size=3>2 → pop 1... wait heap=[1,2,3]?

Min heap:
Masuk 3: [3]
Masuk 2: [2,3]
Masuk 1: [1,2,3] size>2 → pop min=1 → [2,3]
Masuk 5: [2,3,5] → pop 2 → [3,5]
Masuk 6: [3,5,6] → pop 3 → [5,6]
Masuk 4: [4,5,6] → pop 4 → [5,6]

top = 5 ✅ (terbesar ke-2)
```

______________________________________________________________________

## 🔍 Approach

### Min Heap of Size K

1. Buat min heap kosong.
1. Loop setiap `val` di `nums`:
   - `pq.offer(val)` → masukkan ke heap.
   - Jika `pq.size() > k` → `pq.poll()` → buang elemen terkecil.
1. Return `pq.poll()` → top heap = elemen terbesar ke-k.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------- |
| **Time** | O(n log k) — setiap elemen offer/poll O(log k), n elemen |
| **Space** | O(k) — heap berukuran maksimal k+1 |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,2,1,5,6,4]`, `k = 2`

| val | Setelah offer | size > k? | Setelah poll | heap |
| --- | ------------- | --------- | ------------ | ----- |
| 3 | [3] | 1>2? ❌ | — | [3] |
| 2 | [2,3] | 2>2? ❌ | — | [2,3] |
| 1 | [1,2,3] | 3>2? ✅ | pop 1 | [2,3] |
| 5 | [2,3,5] | 3>2? ✅ | pop 2 | [3,5] |
| 6 | [3,5,6] | 3>2? ✅ | pop 3 | [5,6] |
| 4 | [4,5,6] | 3>2? ✅ | pop 4 | [5,6] |

`pq.poll()` = 5

**Output: `5` ✅**

______________________________________________________________________

**Input:** `nums = [3,2,3,1,2,4,5,5,6]`, `k = 4`

Setup heap dengan semua elemen, pertahankan 4 terbesar:

Setelah semua: heap harus berisi `[4,5,5,6]` → top = `4`

**Output: `4` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k = 1` → elemen terbesar → heap selalu berukuran 1
- [ ] `k = nums.length` → elemen terkecil → heap berisi semua elemen → return top
- [ ] Duplikat → handled karena kita cek ukuran heap, bukan nilai unik

______________________________________________________________________

## 🔧 Kenapa Min Heap, Bukan Max Heap?

**Max Heap:**

```
Masukkan semua → pop k kali → jawaban di pop ke-k
Time: O(n + k log n) → lebih lambat saat k besar
```

**Min Heap of size k:**

```
Pertahankan k terbesar → top = terbesar ke-k
Time: O(n log k) → lebih cepat karena heap kecil
```

Dengan min heap berukuran `k`, kita selalu membuang elemen yang **tidak mungkin** menjadi kth largest (yang lebih kecil dari semua di heap).

______________________________________________________________________

**Visualisasi:**

```
k=2: pertahankan 2 terbesar
heap = [5,6]  ← min heap, top=5
         ↑ ini terbesar ke-2 ✅
```

______________________________________________________________________

## 🔧 Cara Kerja `pq.size() > k` vs `pq.size() >= k`

```java
if (pq.size() > k) pq.poll();  // buang setelah ukuran MELEBIHI k
```

Kita offer dulu, baru cek. Jadi kondisi `> k` berarti heap sudah punya `k+1` elemen → buang 1 → kembali ke k.

Jika pakai `>= k`: heap akan dibuang saat ukuran = k, sebelum elemen baru masuk → heap bisa berukuran k-1 → salah!

______________________________________________________________________

## 🔀 Alternatif: Sort

```java
Arrays.sort(nums);
return nums[nums.length - k];
```

| Approach | Time | Space | Catatan |
| ----------- | ---------- | ----- | --------------------------- |
| Min Heap | O(n log k) | O(k) | Optimal untuk k kecil |
| Sort | O(n log n) | O(1) | Sederhana tapi lebih lambat |
| QuickSelect | O(n) avg | O(1) | Paling cepat rata-rata |

______________________________________________________________________

## 📌 Key Takeaway

**Min Heap of size k** adalah pendekatan klasik untuk soal "kth largest" — pertahankan `k` elemen terbesar, dan elemen terkecil di heap (top) adalah jawaban. Lebih efisien dari sort O(n log n) karena heap hanya berukuran k. Pola ini juga dipakai di _K Closest Points to Origin_, _Find K Pairs with Smallest Sums_, dan _Top K Frequent Elements_. 🎯

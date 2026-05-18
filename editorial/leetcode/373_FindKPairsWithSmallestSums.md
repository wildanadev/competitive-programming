# 373. Find K Pairs with Smallest Sums

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Heap (Priority Queue)
- **Link**: [Problem](https://leetcode.com/problems/find-k-pairs-with-smallest-sums/)
- **Solution**: [Code](../../leetcode/FindKPairsWithSmallestSums.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array terurut ascending `nums1` dan `nums2`, dan integer `k`. Kembalikan `k` pasangan `(nums1[i], nums2[j])` dengan **jumlah terkecil**.

Contoh:

- `nums1 = [1,7,11]`, `nums2 = [2,4,6]`, `k = 3` → `[[1,2],[1,4],[1,6]]`
- `nums1 = [1,1,2]`, `nums2 = [1,2,3]`, `k = 2` → `[[1,1],[1,1]]`

______________________________________________________________________

## 💡 Intuition

Karena kedua array **terurut ascending**, pasangan dengan jumlah terkecil selalu dimulai dari `nums1[i]` yang kecil dipasangkan dengan `nums2[0]`.

**Ide**: untuk setiap `nums1[i]`, urutan pasangan terkecilnya adalah:

```
(nums1[i], nums2[0]) < (nums1[i], nums2[1]) < (nums1[i], nums2[2]) < ...
```

Gunakan **Min Heap** yang menyimpan **indeks** `(i, j)` — representasi pasangan `(nums1[i], nums2[j])`. Setiap kali kita poll pasangan terkecil `(i, j)`, kita masukkan kandidat berikutnya `(i, j+1)`.

**Inisialisasi**: masukkan `(i, 0)` untuk semua `i` dari `0` sampai `min(nums1.length, k) - 1` — kita tidak perlu lebih dari `k` baris karena tidak mungkin butuh lebih dari `k` pasangan dari satu baris.

______________________________________________________________________

## 🔍 Approach

### Min Heap dengan Indeks

1. Buat min heap dengan comparator berdasarkan `nums1[i] + nums2[j]`.
1. Inisialisasi heap dengan `(i, 0)` untuk `i = 0..min(n1, k)-1`.
1. Loop `k` kali:
   - Poll `(i, j)` terkecil → tambahkan `(nums1[i], nums2[j])` ke `ans`.
   - Jika `j+1 < nums2.length` → push `(i, j+1)`.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------- |
| **Time** | O(k log k) — setiap poll/offer O(log k), k iterasi |
| **Space** | O(k) — heap berisi maksimal k elemen |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums1 = [1,7,11]`, `nums2 = [2,4,6]`, `k = 3`

**Inisialisasi heap** (push `(i,0)` untuk `i=0,1,2`):

```
heap = [(0,0)=1+2=3, (1,0)=7+2=9, (2,0)=11+2=13]
```

______________________________________________________________________

**k=3, iterasi 1:**

- poll `(0,0)` → sum=3 → ans=`[[1,2]]`
- push `(0,1)` → sum=1+4=5
- heap = `[(0,1)=5, (1,0)=9, (2,0)=13]`

**k=2, iterasi 2:**

- poll `(0,1)` → sum=5 → ans=`[[1,2],[1,4]]`
- push `(0,2)` → sum=1+6=7
- heap = `[(0,2)=7, (1,0)=9, (2,0)=13]`

**k=1, iterasi 3:**

- poll `(0,2)` → sum=7 → ans=`[[1,2],[1,4],[1,6]]`
- j+1=3 >= nums2.length=3 → tidak push
- heap = `[(1,0)=9, (2,0)=13]`

**Output: `[[1,2],[1,4],[1,6]]` ✅**

______________________________________________________________________

**Input:** `nums1 = [1,1,2]`, `nums2 = [1,2,3]`, `k = 2`

**Inisialisasi** (push `(i,0)` untuk `i=0,1` — min(3,2)=2):

```
heap = [(0,0)=1+1=2, (1,0)=1+1=2]
```

**k=2, iterasi 1:**

- poll `(0,0)` → sum=2 → ans=`[[1,1]]`
- push `(0,1)` → sum=1+2=3
- heap = `[(1,0)=2, (0,1)=3]`

**k=1, iterasi 2:**

- poll `(1,0)` → sum=2 → ans=`[[1,1],[1,1]]`
- push `(1,1)` → sum=1+2=3
- heap = `[(0,1)=3, (1,1)=3]`

**Output: `[[1,1],[1,1]]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k > nums1.length * nums2.length` → tidak mungkin terjadi per constraints
- [ ] `k = 1` → hanya poll sekali → return pasangan dengan sum terkecil
- [ ] Semua elemen sama → semua pasangan sama sum → return k pasangan pertama

______________________________________________________________________

## 🔧 Kenapa Push `(i, 0)` Saja di Awal, Bukan Semua Pasangan?

Jika kita push semua `n1 × n2` pasangan → O(n1 × n2 × log(n1×n2)) hanya untuk inisialisasi — sangat mahal!

Karena `nums2` terurut, setelah mengambil `(i, j)` kita tahu kandidat berikutnya dari baris `i` adalah `(i, j+1)`. Kita hanya perlu track satu kandidat per baris di heap — ini yang membuat heap selalu berisi maksimal `k` elemen.

```
Heap = "frontier" dari setiap baris nums1
       selalu berisi kandidat terkecil yang belum dipilih
       dari setiap baris yang aktif
```

______________________________________________________________________

## 🔧 Kenapa `Math.min(nums1.length, k)`?

```java
for (int i = 0; i < Math.min(nums1.length, k); i++)
    pq.offer(new int[] { i, 0 });
```

Kita hanya butuh `k` pasangan total. Mustahil butuh lebih dari `k` baris dari `nums1` — jika `k = 3`, paling banyak kita butuh 3 baris berbeda. Membatasi inisialisasi ke `min(n1, k)` menghemat memori dan waktu.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh **K-way Merge** — menggabungkan `n` urutan terurut dan mengambil `k` elemen terkecilnya. Min Heap yang menyimpan "frontier" (satu kandidat terkecil dari setiap baris) adalah pola optimal: O(k log k) jauh lebih baik dari brute force O(n1 × n2 × log(n1×n2)). Pola yang sama muncul di _Merge K Sorted Lists_ dan _Kth Smallest Element in a Sorted Matrix_. 🎯

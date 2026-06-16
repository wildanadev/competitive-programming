# 912. Sort an Array

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Divide and Conquer, Sorting, Merge Sort
- **Link**: [Problem](https://leetcode.com/problems/sort-an-array/)
- **Solution**: [Code](../../leetcode/SortAnArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, urutkan secara ascending dan kembalikan hasilnya. Solusi harus O(n log n) time dan tidak boleh menggunakan built-in sort.

Contoh:

- `nums = [5,2,3,1]` → `[1,2,3,5]`
- `nums = [5,1,1,2,0,0]` → `[0,0,1,1,2,5]`

______________________________________________________________________

## 💡 Intuition

**Merge Sort** adalah algoritma Divide and Conquer klasik:

1. **Divide**: bagi array menjadi dua bagian di tengah.
1. **Conquer**: rekursif sort kedua bagian.
1. **Merge**: gabungkan dua bagian yang sudah terurut.

Kuncinya ada di **merge** — menggabungkan dua array terurut menjadi satu array terurut dalam O(n).

```
[5,2,3,1]
↙         ↘
[5,2]     [3,1]
↙  ↘      ↙  ↘
[5] [2]  [3] [1]
↘  ↙      ↘  ↙
[2,5]     [1,3]
     ↘   ↙
   [1,2,3,5]
```

______________________________________________________________________

## 🔍 Approach

### Merge Sort (Recursive)

**`mergeSort(arr, l, r)`:**

- Base case: `l >= r` → array satu elemen, sudah terurut → return.
- Hitung `m = l + (r-l)/2` (titik tengah).
- Rekursi kiri: `mergeSort(arr, l, m)`.
- Rekursi kanan: `mergeSort(arr, m+1, r)`.
- Gabung: `merge(arr, l, m, r)`.

**`merge(arr, l, m, r)`:**

1. Salin `arr[l..m]` ke `L[]` dan `arr[m+1..r]` ke `R[]`.
1. Bandingkan `L[i]` dan `R[j]`, ambil yang lebih kecil, tulis ke `arr[k]`.
1. Salin sisa elemen yang belum terpakai.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------- |
| **Time** | O(n log n) — log n level rekursi, setiap level O(n) merge |
| **Space** | O(n) — array temp `L[]` dan `R[]` di setiap merge |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [5,2,3,1]`

**Pohon rekursi:**

```
mergeSort([5,2,3,1], 0, 3), m=1
├── mergeSort([5,2], 0, 1), m=0
│   ├── mergeSort([5], 0, 0) → return
│   ├── mergeSort([2], 1, 1) → return
│   └── merge([5,2], 0, 0, 1):
│       L=[5], R=[2]
│       5>2 → arr[0]=2; sisa L → arr[1]=5
│       arr=[2,5,3,1]
└── mergeSort([3,1], 2, 3), m=2
    ├── mergeSort([3], 2, 2) → return
    ├── mergeSort([1], 3, 3) → return
    └── merge([3,1], 2, 2, 3):
        L=[3], R=[1]
        3>1 → arr[2]=1; sisa L → arr[3]=3
        arr=[2,5,1,3]

merge([2,5,1,3], 0, 1, 3):
L=[2,5], R=[1,3]
2>1 → arr[0]=1; k=1
2>3? ❌ → arr[1]=2; k=2, i=1
5>3 → arr[2]=3; k=3, j=1
sisa L → arr[3]=5
arr=[1,2,3,5] ✅
```

**Output: `[1,2,3,5]` ✅**

______________________________________________________________________

## ⚠️ Penjelasan Proses Merge

**Input:** `L=[2,5]`, `R=[1,3]`, tulis ke `arr[0..3]`

| k | L[i] | R[j] | L[i]\<=R[j]? | arr[k] | i | j |
| --- | ---- | ---- | ----------- | ---------- | --- | --- |
| 0 | 2 | 1 | 2\<=1? ❌ | 1 (R) | 0 | 1 |
| 1 | 2 | 3 | 2\<=3? ✅ | 2 (L) | 1 | 1 |
| 2 | 5 | 3 | 5\<=3? ❌ | 3 (R) | 1 | 2 |
| 3 | — | — | j habis | 5 (sisa L) | 2 | 2 |

`arr = [1,2,3,5]` ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → base case langsung return
- [ ] Semua elemen sama → selalu `L[i] <= R[j]` → urutan terjaga (stable sort)
- [ ] Sudah terurut → merge tidak swap, tetap O(n log n)
- [ ] Terurut descending → banyak swap di merge, tetap O(n log n)

______________________________________________________________________

## 🔧 Kenapa `m = l + (r - l) / 2`, Bukan `(l + r) / 2`?

```java
int m = l + (r - l) / 2;  // ✅ aman
int m = (l + r) / 2;      // ❌ bisa overflow jika l+r > Integer.MAX_VALUE
```

`l + r` bisa overflow untuk array besar. `l + (r - l) / 2` selalu aman karena `r - l` pasti tidak overflow (r dan l keduanya valid indeks).

______________________________________________________________________

## 🔧 Kenapa Dua Array Temp `L[]` dan `R[]`?

Kita tidak bisa merge in-place secara langsung karena mengubah elemen di `arr` saat membaca dari `arr` yang sama akan merusak data yang belum diproses.

```
arr = [2,5,1,3]
       ↑↑ bagian kiri  ↑↑ bagian kanan

Jika merge langsung:
  arr[0] = min(2,1) = 1 → arr = [1,5,1,3]
  Tapi sekarang nilai 2 HILANG dari arr!
```

Dengan menyalin ke `L[]` dan `R[]` dulu, data asli terlindungi saat proses merge.

______________________________________________________________________

## 🔧 Mengapa Sisa Elemen Hanya dari Satu Sisi?

```java
while (i < n1) { arr[k++] = L[i++]; }  // sisa L
while (j < n2) { arr[k++] = R[j++]; }  // sisa R
```

Saat loop utama berhenti, salah satu dari `L` atau `R` sudah habis. Yang tersisa di sisi lain sudah pasti lebih besar dari semua yang sudah ditulis → langsung salin ke akhir.

Tidak mungkin keduanya punya sisa sekaligus karena loop berhenti ketika salah satu habis.

______________________________________________________________________

## 📊 Perbandingan Sorting Algorithms

| Algorithm | Time Best | Time Avg | Time Worst | Space | Stable? |
| ----------- | ---------- | ---------- | ---------- | -------- | ------- |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | ✅ |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | ❌ |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | ❌ |
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | ✅ |

Merge Sort dipilih karena **guaranteed O(n log n)** di semua kasus dan **stable** (urutan relatif elemen sama tetap terjaga).

______________________________________________________________________

## 📌 Key Takeaway

Merge Sort adalah contoh sempurna **Divide and Conquer** — masalah besar dibagi menjadi sub-masalah kecil yang diselesaikan secara rekursif, lalu digabung. Kunci algoritmanya ada di fungsi `merge` yang efisien O(n). Dua detail penting: gunakan `l + (r-l)/2` untuk mencegah overflow, dan array temp diperlukan karena merge tidak bisa dilakukan in-place dengan benar. 🎯

# 2089. Find Target Indices After Sorting Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Binary Search, Sorting
- **Link**: [Problem](https://leetcode.com/problems/find-target-indices-after-sorting-array/)
- **Solution**: [Code](../../leetcode/FindTargetIndicesAfterSortingArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` dan sebuah `target`. **Urutkan `nums` secara menaik**, lalu kembalikan **semua indeks** `i` di array yang sudah terurut itu di mana `nums[i] == target`. Hasilnya harus berupa daftar indeks yang **terurut naik**. Kalau `target` tidak ada, kembalikan daftar kosong.

Contoh:

- `nums = [1,2,5,2,3], target = 2` → `[1,2]` (setelah diurutkan: `[1,2,2,3,5]`, angka `2` ada di indeks `1` dan `2`)
- `nums = [1,2,5,2,3], target = 3` → `[3]`
- `nums = [1,2,5,2,3], target = 5` → `[4]`

______________________________________________________________________

## 💡 Intuition

Soal ini menyuruh kita melakukan persis apa yang tertulis: **urutkan**, lalu **catat posisi `target`**. Setelah diurutkan, semua kemunculan `target` pasti berkumpul di satu **blok yang bersebelahan**, dan scan sekali dari kiri ke kanan mengumpulkan indeksnya **otomatis dalam urutan naik** (sesuai syarat soal).

Kode ini mengikuti jalur langsung itu: `Arrays.sort`, lalu satu loop untuk mengumpulkan indeks yang cocok. Karena `nums.length <= 100`, pendekatan ini lebih dari cukup cepat.

______________________________________________________________________

## 🔍 Approach

### Sort, Lalu Scan

1. `Arrays.sort(nums)` — urutkan array secara menaik (in-place).
1. Loop `i` dari `0` sampai `nums.length - 1`:
   - Kalau `nums[i] == target`, tambahkan `i` ke `ans`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------------- |
| **Time** | O(n log n) — dominan dari proses sorting, scan-nya `O(n)` |
| **Space** | O(log n) — tumpukan rekursi internal `Arrays.sort` untuk `int[]`, di luar daftar hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,5,2,3], target = 2`

Setelah `Arrays.sort`: `[1,2,2,3,5]`.

| i | nums[i] | Sama dengan 2? | ans |
| --- | ------- | -------------- | ------- |
| 0 | 1 | tidak | `[]` |
| 1 | 2 | ya | `[1]` |
| 2 | 2 | ya | `[1,2]` |
| 3 | 3 | tidak | `[1,2]` |
| 4 | 5 | tidak | `[1,2]` |

**Output: `[1,2]`** ✅

______________________________________________________________________

**Input:** `nums = [1,2,5,2,3], target = 3` → terurut `[1,2,2,3,5]`, `3` hanya di indeks `3`.

**Output: `[3]`** ✅

**Input:** `nums = [1,2,5,2,3], target = 5` → terurut `[1,2,2,3,5]`, `5` hanya di indeks `4`.

**Output: `[4]`** ✅

**Input tambahan:** `nums = [1,2,5,2,3], target = 4` → terurut `[1,2,2,3,5]`, tidak ada `4`.

**Output: `[]`**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `target` tidak ada di `nums` → tidak ada kecocokan, `ans` tetap kosong
- [ ] Semua elemen sama dengan `target` → hasilnya seluruh indeks `[0, 1, ..., n-1]`
- [ ] `target` lebih kecil dari semua elemen (tapi ada, jadi dia elemen terkecil) → blok indeksnya dimulai dari `0`
- [ ] `target` lebih besar dari semua elemen → tidak ada di array, hasil kosong
- [ ] Array satu elemen → hasil `[0]` kalau elemennya sama dengan `target`, selain itu kosong
- [ ] `Arrays.sort(nums)` **mengubah array input** secara permanen. Di LeetCode tidak masalah, tapi di kode nyata perlu dipertimbangkan kalau pemanggil masih memakai urutan aslinya

______________________________________________________________________

## 🔧 Alternatif: Tanpa Sorting, Cukup Menghitung (O(n))

Perhatikan bahwa posisi `target` di array terurut **hanya bergantung pada dua hitungan**:

- `less` = banyaknya elemen yang **lebih kecil** dari `target`.
- `equal` = banyaknya elemen yang **sama dengan** `target`.

Di array terurut, semua elemen yang lebih kecil dari `target` ada di depan (menempati indeks `0` sampai `less - 1`), lalu menyusul `equal` kemunculan `target`. Jadi indeksnya adalah **`less, less+1, ..., less+equal-1`**.

```java
public List<Integer> targetIndices(int[] nums, int target) {
    int less = 0, equal = 0;
    for (int x : nums) {
        if (x < target) less++;
        else if (x == target) equal++;
    }
    List<Integer> ans = new ArrayList<>();
    for (int i = 0; i < equal; i++)
        ans.add(less + i);
    return ans;
}
```

Cek `nums = [1,2,5,2,3], target = 2`: elemen yang lebih kecil dari `2` hanya `1`, jadi `less = 1`; `2` muncul dua kali, jadi `equal = 2`. Hasil `[1, 2]` ✅. Untuk `target = 3`: `less = 3` (`1,2,2`), `equal = 1` → `[3]` ✅. Untuk `target = 4`: `less = 4`, `equal = 0` → `[]` ✅.

Versi ini tidak perlu mengurutkan sama sekali, tidak mengubah array input, dan hanya butuh satu pass.

| Approach | Time | Space | Catatan |
| --------------------------- | ---------- | ------------------ | ----------------------------------------------- |
| Sort, lalu scan (kode asli) | O(n log n) | O(log n) | Langsung mengikuti kalimat soal, mengubah input |
| Hitung `less` dan `equal` | O(n) | O(1) di luar hasil | Tanpa sorting, input tidak berubah |

Untuk `n <= 100`, kedua versi sama-sama instan, jadi versi asli yang lebih sederhana sudah tepat. Versi menghitung jadi menarik kalau `n` besar, atau kalau kamu ingin menghindari efek samping pada array input.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menunjukkan bahwa pertanyaan tentang **posisi di array yang terurut** sering bisa dijawab **tanpa benar-benar mengurutkan**: posisi sebuah nilai di array terurut hanya ditentukan oleh **berapa banyak elemen yang lebih kecil darinya**. Pola "hitung `less` dan `equal` untuk menebak indeks terurut" juga muncul di soal-soal seperti _Count Smaller Numbers_ dan _How Many Numbers Are Smaller Than the Current Number_, dan mengubah kompleksitas dari `O(n log n)` menjadi `O(n)`. Untuk constraint sekecil soal ini, solusi sort-lalu-scan yang kamu tulis sudah sepenuhnya memadai. 🎯

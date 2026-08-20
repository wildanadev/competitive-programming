# 3069. Distribute Elements Into Two Arrays I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Simulation
- **Link**: [Problem](https://leetcode.com/problems/distribute-elements-into-two-arrays-i/)
- **Solution**: [Code](../../leetcode/DistributeElementsIntoTwoArraysI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array **1-indexed** `nums` berisi integer **berbeda-beda (distinct)**, panjang `n`. Distribusikan seluruh elemen ke dua array, `arr1` dan `arr2`, lewat `n` operasi berurutan:

- Operasi 1: `arr1` diisi `nums[1]` (elemen pertama).
- Operasi 2: `arr2` diisi `nums[2]` (elemen kedua).
- Operasi ke-`i` (untuk `i >= 3`): bandingkan **elemen terakhir** `arr1` dan `arr2` saat ini.
  - Kalau elemen terakhir `arr1` **lebih besar** → `nums[i]` masuk ke `arr1`.
  - Kalau tidak → `nums[i]` masuk ke `arr2`.

Kembalikan hasil **konkatenasi** `arr1` diikuti `arr2`.

Contoh:

- `nums = [2,1,3]` → `[2,3,1]`
  - `arr1=[2], arr2=[1]`. Operasi 3: `2 > 1` → `3` masuk `arr1` → `arr1=[2,3]`. Hasil: `[2,3] + [1] = [2,3,1]`
- `nums = [5,4,3,8]` → `[5,3,4,8]`
  - `arr1=[5], arr2=[4]`. Operasi 3: `5>4` → `3` masuk `arr1` → `arr1=[5,3]`. Operasi 4: elemen terakhir `arr1=3`, `arr2=4`, `3>4`? tidak → `8` masuk `arr2` → `arr2=[4,8]`. Hasil: `[5,3]+[4,8]=[5,3,4,8]`

______________________________________________________________________

## 💡 Intuition

Soal ini murni **simulasi langsung dari deskripsi soal** — tidak ada trik matematis tersembunyi, cukup ikuti aturan persis seperti yang dijelaskan. Satu-satunya hal yang perlu diperhatikan: keputusan penempatan **hanya bergantung pada elemen terakhir** dari kedua array saat ini, jadi kita cuma perlu melacak **posisi elemen terakhir** di masing-masing array (bukan seluruh isinya) untuk membuat keputusan tiap langkah.

Solusi ini mengimplementasikan `arr1` dan `arr2` sebagai **array berukuran tetap** (`nums.length`), dengan pointer `j` dan `k` yang menandai indeks elemen terakhir yang terisi di masing-masing array — mirip pola "stack berbasis array" yang juga muncul di soal-soal simulasi lain seperti _Baseball Game_.

______________________________________________________________________

## 🔍 Approach

### Simulasi dengan Dua Array + Pointer Elemen Terakhir

1. Siapkan `arr1` dan `arr2`, masing-masing array kosong (default berisi `0`) seukuran `nums.length`.
1. `arr1[0] = nums[0]`, `arr2[0] = nums[1]` (dua operasi pertama).
1. Loop `i` dari `2` (indeks ke-3, 0-indexed) sampai akhir `nums`, dengan `j` dan `k` sebagai pointer elemen terakhir di `arr1`/`arr2` (mulai keduanya di `0`):
   - Kalau `arr1[j] > arr2[k]` → `nums[i]` masuk ke `arr1`, di posisi `++j` (majukan pointer dulu, baru isi).
   - Kalau tidak → `nums[i]` masuk ke `arr2`, di posisi `++k`.
1. Bangun `ans` dengan menyalin isi `arr1` (berhenti begitu ketemu `0`, penanda "belum terisi") diikuti isi `arr2` (dengan cara yang sama).
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------- |
| **Time** | O(n) — satu pass untuk distribusi, ditambah dua pass linear untuk menyusun `ans` |
| **Space** | O(n) — tiga array (`arr1`, `arr2`, `ans`) masing-masing seukuran `nums.length` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [5,4,3,8]`

**Distribusi:**

| i | arr1[j] | arr2[k] | arr1[j] > arr2[k]? | Aksi | j | k |
| --- | ------- | ------- | ------------------ | ----------------------- | --- | --- |
| — | 5 (j=0) | 4 (k=0) | — | inisialisasi awal | 0 | 0 |
| 2 | 5 | 4 | `5>4` ✅ | `arr1[1] = nums[2] = 3` | 1 | 0 |
| 3 | 3 | 4 | `3>4` ❌ | `arr2[1] = nums[3] = 8` | 1 | 1 |

`arr1 = [5,3,0,0]`, `arr2 = [4,8,0,0]`

**Bangun `ans`:**

- Dari `arr1`: `5, 3` (berhenti di `0`) → `ans = [5,3]`
- Dari `arr2`: `4, 8` (berhenti di `0`) → `ans = [5,3,4,8]`

**Output: `[5,3,4,8]`** ✅

______________________________________________________________________

**Input:** `nums = [2,1,3]`

**Distribusi:**

| i | arr1[j] | arr2[k] | Cek | Aksi | j | k |
| --- | ------- | ------- | -------- | ----------------------- | --- | --- |
| — | 2 | 1 | — | inisialisasi | 0 | 0 |
| 2 | 2 | 1 | `2>1` ✅ | `arr1[1] = nums[2] = 3` | 1 | 0 |

`arr1 = [2,3,0]`, `arr2 = [1,0,0]`

**Bangun `ans`:** dari `arr1`: `2,3`; dari `arr2`: `1` → `ans = [2,3,1]`

**Output: `[2,3,1]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 3` (minimum sesuai constraint) → hanya satu operasi perbandingan (`i=2`), tetap tertangani normal
- [ ] Semua elemen berikutnya konsisten masuk ke satu array saja (misal `arr1` selalu menang perbandingan) → `arr2` cuma berisi elemen inisialnya (`nums[1]`) sepanjang program, sisanya tetap `0` dan otomatis berhenti disalin saat membangun `ans`
- [ ] Nilai `nums[i]` kebetulan sama dengan sentinel `0` → **tidak mungkin terjadi**, karena constraint soal menjamin `nums[i] >= 1` (integer positif), sehingga `0` aman dipakai sebagai penanda "slot belum terisi" tanpa risiko ambigu dengan nilai asli
- [ ] Semua nilai berbeda (constraint "distinct") → tidak ada elemen yang membingungkan perbandingan `>` (tidak ada isu "sama besar" yang perlu penanganan khusus)

______________________________________________________________________

## 🔧 Kenapa Aman Memakai `0` Sebagai Penanda "Slot Kosong"?

```java
for (int i : arr1) {
    if (i == 0) break;
    else ans[currIndx++] = i;
}
```

Trik ini **valid** karena constraint soal menjamin `nums[i] >= 1` — artinya, **tidak ada nilai asli yang bisa bernilai `0`**. Karena array Java otomatis diisi `0` sebagai nilai default (`int[]` baru), begitu iterasi menemukan `0` yang pertama, itu **pasti** menandakan "sudah lewat elemen valid terakhir, sisanya slot kosong yang belum pernah ditulis" — bukan elemen asli yang kebetulan bernilai `0`. Kalau constraint soal mengizinkan `0` sebagai nilai valid, trik ini akan **gagal** dan salah menghitung panjang array — solusi yang lebih aman dalam kasus itu adalah melacak panjang eksplisit (misalnya lewat `j` dan `k` yang sudah ada) alih-alih mengandalkan nilai sentinel.

______________________________________________________________________

## 🔧 Alternatif: Pakai `List<Integer>` (Lebih Aman, Tidak Bergantung Sentinel)

```java
public int[] resultArray(int[] nums) {
    List<Integer> arr1 = new ArrayList<>(List.of(nums[0]));
    List<Integer> arr2 = new ArrayList<>(List.of(nums[1]));

    for (int i = 2; i < nums.length; i++) {
        if (arr1.get(arr1.size() - 1) > arr2.get(arr2.size() - 1))
            arr1.add(nums[i]);
        else
            arr2.add(nums[i]);
    }

    arr1.addAll(arr2);
    return arr1.stream().mapToInt(Integer::intValue).toArray();
}
```

Versi ini memakai `ArrayList`, yang panjangnya tumbuh secara natural sesuai elemen yang benar-benar ditambahkan — tidak perlu penanda sentinel sama sekali, dan **tidak rentan** terhadap constraint yang berubah (misalnya kalau suatu saat nilai `0` diizinkan). Trade-off-nya: sedikit overhead dari boxing `Integer` dan operasi list dibanding array primitif.

| Approach | Time | Space | Bergantung pada Constraint Nilai Positif? |
| ----------------------------------------- | ---- | ----- | ----------------------------------------- |
| Array primitif + sentinel `0` (kode asli) | O(n) | O(n) | Ya |
| `ArrayList<Integer>` | O(n) | O(n) | Tidak |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan bagus untuk **menerjemahkan deskripsi soal langkah-demi-langkah menjadi kode secara langsung**, tanpa perlu insight matematis tambahan — kuncinya cukup melacak **elemen terakhir** dari dua struktur yang sedang dibangun secara paralel. Perhatikan juga trik memakai nilai sentinel (`0`) untuk menandai "belum terisi" — ini valid **hanya** kalau nilai sentinel itu **dijamin tidak pernah muncul** sebagai data asli (di sini dijamin lewat constraint `nums[i] >= 1`); kalau tidak yakin soal jaminan ini, lebih aman melacak panjang secara eksplisit alih-alih mengandalkan nilai default array. 🎯

# 1051. Height Checker

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sorting, Counting Sort
- **Link**: [Problem](https://leetcode.com/problems/height-checker/)
- **Solution**: [Code](../../leetcode/HeightChecker.java)

______________________________________________________________________

## 📄 Problem Summary

Sekolah ingin siswa berbaris berdasarkan tinggi badan, **naik dari kiri ke kanan**. Diberikan array `heights`, yaitu urutan tinggi badan siswa **saat ini** (barisan aktual, belum tentu terurut).

Hitung **berapa banyak siswa** yang berada di posisi yang salah — yaitu posisinya berbeda dibanding kalau barisan diurutkan naik secara sempurna (`expected`).

Contoh:

- `heights = [1,1,4,2,1,3]` → `3` (indeks 2, 4, 5 tidak sesuai posisi yang seharusnya)

______________________________________________________________________

## 💡 Intuition

Soal ini sebenarnya cuma minta **bandingkan array asli dengan versi terurutnya, posisi demi posisi**. Kalau di suatu indeks nilainya sama antara array asli dan versi terurut, siswa itu sudah berada di tempat yang benar. Kalau beda, berarti siswa itu salah posisi.

Jadi strateginya sangat langsung:

1. Buat salinan `heights` yang sudah diurutkan (`expected`) — ini merepresentasikan urutan barisan yang "benar".
1. Bandingkan elemen per elemen antara `heights` (posisi aktual) dan `expected` (posisi seharusnya).
1. Hitung berapa banyak indeks yang nilainya **tidak cocok**.

Tidak perlu tahu **siapa harus pindah ke mana** — cukup hitung **berapa banyak** yang salah tempat.

______________________________________________________________________

## 🔍 Approach

### Sort & Compare

1. Salin `heights` ke array baru `expected` (`Arrays.copyOf`), supaya array asli tidak ikut berubah saat di-sort.
1. Urutkan `expected` naik (`Arrays.sort`).
1. Loop tiap indeks `i`: kalau `heights[i] != expected[i]`, tambahkan ke counter `ans`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------- |
| **Time** | O(n log n) — didominasi oleh `Arrays.sort` |
| **Space** | O(n) — untuk array `expected` (di luar output, tidak menghitung ruang yang dipakai sorting internal) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `heights = [1,1,4,2,1,3]`

- `expected` (setelah disalin & disort) `= [1,1,1,2,3,4]`

| Indeks | heights[i] | expected[i] | Beda? |
| ------ | ---------- | ----------- | ------ |
| 0 | 1 | 1 | tidak |
| 1 | 1 | 1 | tidak |
| 2 | 4 | 1 | **ya** |
| 3 | 2 | 2 | tidak |
| 4 | 1 | 3 | **ya** |
| 5 | 3 | 4 | **ya** |

Total beda `= 3`.

**Output: `3`** ✅

______________________________________________________________________

**Input:** `heights = [1,2,3,4,5]`

- `expected = [1,2,3,4,5]` (sudah terurut, sorting tidak mengubah apapun).
- Semua indeks cocok, tidak ada yang beda.

**Output: `0`** ✅ (barisan sudah sempurna)

______________________________________________________________________

**Input:** `heights = [5,1,2,3,4]`

- `expected = [1,2,3,4,5]`

| Indeks | heights[i] | expected[i] | Beda? |
| ------ | ---------- | ----------- | ----- |
| 0 | 5 | 1 | ya |
| 1 | 1 | 2 | ya |
| 2 | 2 | 3 | ya |
| 3 | 3 | 4 | ya |
| 4 | 4 | 5 | ya |

**Output: `5`** — seluruh posisi salah, karena satu elemen (`5`) terletak paling depan padahal seharusnya paling belakang, sehingga menggeser posisi semua elemen lainnya.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array sudah terurut sempurna → `0` mismatch
- [ ] Ada duplikat nilai (`[1,1,4,2,1,3]`, ada tiga `1`) → duplikat yang kebetulan berada di posisi yang sama antara `heights` dan `expected` tetap dianggap "benar", tidak masalah walau nilainya sama dengan elemen duplikat lain
- [ ] Array berukuran 1 → otomatis selalu terurut (tidak ada yang perlu dibandingkan selain dirinya sendiri) → `0`
- [ ] Elemen terbesar berada di posisi paling depan (`[5,1,2,3,4]`) → bisa menyebabkan **semua** posisi dianggap salah meski hanya satu elemen yang "sebenarnya" out of place secara intuitif — ini menegaskan bahwa soal menghitung **mismatch posisi**, bukan "berapa elemen yang perlu dipindah dengan swap minimum"

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu Melacak "Siapa Pindah ke Mana"?

Godaan umum saat melihat soal ini adalah berpikir soal ini butuh simulasi swap atau pelacakan pergerakan siswa. Tapi definisi soal sebenarnya sangat sederhana: **hitung berapa banyak posisi yang nilainya beda** antara array asli dan versi terurutnya. Tidak ada bobot tambahan untuk "seberapa jauh" suatu elemen salah posisi, atau siapa "harusnya" menggantikan siapa — satu mismatch dihitung sama beratnya dengan mismatch lainnya, apapun besarnya pergeseran posisi yang sebenarnya dibutuhkan untuk memperbaikinya.

______________________________________________________________________

## 🔧 Alternatif: Counting Sort (Memanfaatkan Constraint Nilai Kecil)

```java
public int heightChecker(int[] heights) {
    int[] count = new int[101]; // constraint: 1 <= heights[i] <= 100
    for (int h : heights) count[h]++;

    int ans = 0, idx = 0;
    for (int h = 1; h <= 100; h++) {
        while (count[h]-- > 0) {
            if (heights[idx] != h) ans++;
            idx++;
        }
    }
    return ans;
}
```

Karena constraint soal ini membatasi `heights[i]` dalam rentang kecil (`1..100`), **counting sort** bisa dipakai untuk menghasilkan `expected` tanpa perlu comparison-based sort, menurunkan kompleksitas waktu sorting dari `O(n log n)` jadi `O(n + k)` dengan `k` = rentang nilai (konstan, 100).

| Approach | Time | Space |
| ------------------------- | ---------- | -------- |
| `Arrays.sort` (kode asli) | O(n log n) | O(n) |
| Counting sort | O(n + k) | O(n + k) |

Untuk `n` yang kecil seperti pada constraint soal ini, perbedaan performanya nyaris tidak terasa, tapi counting sort jadi opsi menarik kalau `n` jauh lebih besar dan rentang nilai tetap kecil.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan pola sederhana namun sering terlewat: **kalau soal minta "berapa banyak elemen salah posisi", biasanya cukup bandingkan array asli dengan versi idealnya (di sini: versi terurut) posisi demi posisi** — tidak perlu memodelkan proses perpindahan atau swap sama sekali. Pola "bandingkan dengan versi ideal" ini juga relevan untuk soal-soal seperti _Sort Array By Parity_ atau _Relative Sort Array_, di mana definisi "benar" ditentukan lebih dulu sebelum membandingkan posisi aktual terhadapnya. 🎯

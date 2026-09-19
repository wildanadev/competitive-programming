# 806. Number of Lines To Write String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, String
- **Link**: [Problem](https://leetcode.com/problems/number-of-lines-to-write-string/)
- **Solution**: [Code](../../leetcode/NumberOfLinesToWriteString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `widths` (lebar tiap huruf `'a'`–`'z'` dalam satuan unit) dan string `s`. Tulis `s` huruf demi huruf ke dalam **baris** yang masing-masing punya **kapasitas maksimum 100 unit**. Kalau menambahkan huruf berikutnya akan **melebihi** 100 unit di baris saat ini, mulai **baris baru** untuk huruf itu.

Kembalikan `[jumlah baris yang dipakai, lebar baris terakhir]`.

Contoh:

- `widths = [10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10], s = "abcdefghijklmnopqrstuvwxyz"` → `[3,60]` (26 huruf × 10 unit = 260 unit total; `100/10=10` huruf muat per baris, jadi `10+10+6` → 3 baris, baris terakhir `6×10=60`)
- `widths = [4,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10], s = "bbbcccdddaaa"` → `[2,4]`

______________________________________________________________________

## 💡 Intuition

Ini soal **simulasi langsung** — proses `s` huruf demi huruf, akumulasikan lebar di baris saat ini, dan begitu penambahan huruf berikutnya akan **melebihi** 100, "pindah" ke baris baru (reset akumulator, mulai hitung dari `0` lagi untuk huruf itu).

Poin penting: pengecekan **overflow** harus dilakukan **sebelum** menambahkan lebar huruf ke akumulator saat ini — supaya kita tahu **apakah** huruf ini masih muat di baris saat ini, atau harus jadi huruf pertama di baris baru.

Untuk mengambil lebar tiap huruf, dipakai trik **lookup array** `widths[i - 'a']` — sama seperti trik `dict[j-'a']` yang sudah dibahas di soal _Unique Morse Code Words_, memanfaatkan urutan ASCII huruf kecil yang berurutan untuk akses `O(1)` langsung tanpa hashing.

______________________________________________________________________

## 🔍 Approach

### Simulasi Akumulasi Lebar per Baris

1. `curr = 0` (akumulator lebar baris saat ini). `ans[0]` (jumlah baris) mulai dari `0`.
1. Loop tiap karakter `i` di `s`:
   - Kalau `curr + widths[i-'a'] > 100` (menambahkan huruf ini akan **melebihi** kapasitas baris) → baris saat ini **selesai**: `ans[0]++` (baris ini dihitung), reset `curr = 0` (mulai baris baru).
   - Tambahkan lebar huruf ke `curr` (baik itu melanjutkan baris saat ini, atau memulai baris baru yang baru saja di-reset).
1. Setelah loop selesai, **baris terakhir** (yang sedang "berjalan" saat loop berakhir) belum sempat dihitung di dalam loop (karena penghitungan baris hanya terjadi saat _overflow_, bukan saat baris betulan berakhir secara alami di akhir string) → tambahkan `ans[0]++` sekali lagi di luar loop.
1. `ans[1] = curr` — lebar baris terakhir adalah nilai akumulator yang tersisa.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------- |
| **Time** | O(n) — n = panjang `s`, satu kali pass |
| **Space** | O(1) — hanya beberapa variabel akumulator (di luar array hasil ukuran tetap `2`) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `widths = [4,10,10,...(sama untuk sisanya)], s = "bbbcccdddaaa"`

(`widths[0]='a'=4`, sisanya `=10`)

| char | width | curr+width > 100? | Aksi | curr sesudah | ans[0] sesudah |
| ---- | ----- | ------------------ | ------------------------------------ | ------------ | -------------- |
| b | 10 | `0+10=10`, tidak | `curr += 10` | 10 | 0 |
| b | 10 | `10+10=20`, tidak | `curr += 10` | 20 | 0 |
| b | 10 | `20+10=30`, tidak | `curr += 10` | 30 | 0 |
| c | 10 | `30+10=40`, tidak | `curr += 10` | 40 | 0 |
| c | 10 | `40+10=50`, tidak | `curr += 10` | 50 | 0 |
| c | 10 | `50+10=60`, tidak | `curr += 10` | 60 | 0 |
| d | 10 | `60+10=70`, tidak | `curr += 10` | 70 | 0 |
| d | 10 | `70+10=80`, tidak | `curr += 10` | 80 | 0 |
| d | 10 | `80+10=90`, tidak | `curr += 10` | 90 | 0 |
| a | 4 | `90+4=94`, tidak | `curr += 4` | 94 | 0 |
| a | 4 | `94+4=98`, tidak | `curr += 4` | 98 | 0 |
| a | 4 | `98+4=102`, **ya** | `ans[0]++`, `curr=0`, lalu `curr+=4` | 4 | 1 |

Loop selesai. `ans[0]++` (baris terakhir) → `ans[0]=2`. `ans[1]=curr=4`.

**Output: `[2, 4]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s` cuma satu karakter → loop cuma satu iterasi (tidak pernah overflow karena baris kosong), `ans[0]++` di akhir tetap membuat `ans[0]=1`, dan `ans[1]` = lebar karakter itu
- [ ] Seluruh `s` muat dalam **satu** baris (total lebar `<= 100`) → tidak pernah ada overflow di dalam loop, `ans[0]` tetap `0` sampai penambahan terakhir di luar loop, hasil `ans[0]=1`
- [ ] Karakter yang lebarnya **tepat** membuat `curr` mencapai **persis** `100` (bukan melebihi) → **tidak** memicu baris baru (kondisi `> 100`, bukan `>= 100`), karakter itu tetap masuk baris saat ini
- [ ] `s` cukup panjang sehingga butuh banyak baris → tetap tertangani, karena logika overflow-dan-reset berlaku konsisten setiap iterasi
- [ ] Semua huruf punya lebar yang sama besar (seperti contoh alfabet lengkap dengan lebar `10`) → baris terisi penuh secara merata sampai baris terakhir yang mungkin tidak penuh

______________________________________________________________________

## 🔧 Kenapa Kondisinya `> 100`, Bukan `>= 100`?

Definisi soal: baris **penuh** kalau totalnya **melebihi** 100, bukan kalau **mencapai tepat** 100. Kapasitas baris **memang** 100 unit — jadi kalau suatu huruf membuat `curr` jadi **persis** `100`, huruf itu **masih muat** (baris terisi penuh-pas, tidak overflow). Baru kalau totalnya **melewati** 100 (`> 100`), huruf itu **tidak muat** dan harus jadi huruf pertama baris berikutnya. Inilah kenapa operator perbandingannya `>` (strict), bukan `>=`.

______________________________________________________________________

## 🔧 Kenapa `ans[0]++` Diperlukan Lagi Setelah Loop Selesai?

Perhatikan bahwa di dalam loop, `ans[0]++` **hanya** terpicu saat terjadi **overflow** (pindah ke baris baru) — ini menghitung **baris-baris sebelumnya** yang sudah "selesai" karena baris berikutnya dimulai. Tapi **baris terakhir** yang sedang "berjalan" ketika string `s` habis **tidak pernah** memicu kondisi overflow (karena tidak ada karakter berikutnya yang perlu dicek) — sehingga baris ini **tidak pernah dihitung** di dalam loop. Baris `ans[0]++` di **luar** loop inilah yang menghitung baris terakhir ini, memastikan **setiap** baris (termasuk yang terakhir) ikut terhitung. Pola ini mirip dengan kebutuhan "pengecekan tambahan setelah loop" yang juga muncul di soal _Find All Numbers Disappeared in an Array II_ untuk menangani rentang yang belum "ditutup" di akhir.

______________________________________________________________________

## 🔧 Alternatif: Hitung Dulu Total Lebar per Karakter, Baru Simulasikan

```java
public int[] numberOfLines(int[] widths, String s) {
    int lines = 1, currentWidth = 0;
    for (char c : s.toCharArray()) {
        int w = widths[c - 'a'];
        if (currentWidth + w > 100) {
            lines++;
            currentWidth = 0;
        }
        currentWidth += w;
    }
    return new int[]{lines, currentWidth};
}
```

Versi ini secara logika **identik** dengan kode asli, cuma menginisialisasi `lines = 1` di awal (bukan `0`) untuk menghindari kebutuhan `ans[0]++` tambahan di akhir — karena baris pertama **selalu** ada (asalkan `s` tidak kosong), jadi lebih masuk akal memulai hitungan dari `1` alih-alih menambahkannya belakangan.

| Approach | Time | Space | Inisialisasi Jumlah Baris |
| ----------------------------------------- | ---- | ----- | ----------------------------------- |
| Mulai dari `0`, `++` di akhir (kode asli) | O(n) | O(1) | `0`, ditambah manual setelah loop |
| Mulai dari `1` (alternatif) | O(n) | O(1) | `1`, karena baris pertama pasti ada |

Keduanya menghasilkan output yang sama persis — bedanya cuma soal kapan "baris pertama" dihitung: di awal (asumsi implisit) atau di akhir (penambahan eksplisit).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola umum **"simulasi akumulasi dengan reset saat overflow"** — mirip soal-soal binpacking sederhana, di mana kita terus menambah ke "wadah" saat ini sampai tidak muat, lalu mulai wadah baru. Perhatikan detail penting: **kapan** baris terakhir dihitung (butuh langkah tambahan di luar loop kalau inisialisasi dimulai dari `0`), dan **operator perbandingan yang tepat** (`>` vs `>=`) untuk menentukan kapan sesuatu benar-benar "tidak muat" versus "pas penuh". Pola akumulasi dengan reset ini juga relevan untuk soal-soal seperti _Divide Chocolate_ atau _Capacity To Ship Packages Within D Days_ yang melibatkan pembagian ke dalam kapasitas tetap. 🎯

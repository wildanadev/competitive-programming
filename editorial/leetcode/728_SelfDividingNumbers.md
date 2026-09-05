# 728. Self Dividing Numbers

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/self-dividing-numbers/)
- **Solution**: [Code](../../leetcode/SelfDividingNumbers.java)

______________________________________________________________________

## 📄 Problem Summary

Sebuah angka disebut **self-dividing** kalau **setiap digitnya** bisa membagi angka itu sendiri **tanpa sisa**, dan **tidak ada digit `0`** di dalamnya (karena pembagian dengan `0` tidak terdefinisi).

Diberikan `left` dan `right`, kembalikan **daftar** semua angka self-dividing dalam rentang `[left, right]`.

Contoh:

- `left=1, right=22` → `[1,2,3,4,5,6,7,8,9,11,12,15,22]`
- `left=47, right=85` → `[48,55,66,77]`

______________________________________________________________________

## 💡 Intuition

Ini soal **cek per-angka** yang murni brute force — untuk tiap angka dalam rentang `[left, right]`, cek apakah dia self-dividing, lalu kumpulkan yang memenuhi syarat. Karena constraint soal ini kecil (`left, right <= 10^4`), tidak ada kebutuhan optimasi khusus.

Pengecekan "apakah angka ini self-dividing" sendiri adalah soal **ekstraksi digit** standar: ambil tiap digit satu per satu, dan untuk tiap digit cek dua syarat:

1. Digitnya **bukan `0`** (kalau `0`, langsung gagal — karena pembagian dengan `0` mustahil, dan aturan soal memang mengharuskan tidak ada digit `0`).
1. Angka aslinya **habis dibagi** digit tersebut (`number % digit == 0`).

Begitu salah satu digit gagal memenuhi kedua syarat itu, seluruh angka dinyatakan **bukan** self-dividing — bisa langsung `return false` tanpa perlu cek digit sisanya (short-circuit).

______________________________________________________________________

## 🔍 Approach

### Brute Force per Angka + Ekstraksi Digit untuk Validasi

**Fungsi utama `selfDividingNumbers`:**

1. Loop `i` dari `left` sampai `right`.
1. Kalau `isSelfDividingNumber(i)` bernilai `true`, tambahkan `i` ke `ans`.
1. Kembalikan `ans`.

**Helper `isSelfDividingNumber(number)`:**

1. Salin `number` ke `temp` (supaya `number` asli tetap utuh untuk dipakai di pengecekan pembagian, sementara `temp` "dihabisi" lewat ekstraksi digit).
1. Loop selama `temp > 0`:
   - Ambil digit terakhir: `curr = temp % 10`.
   - Kalau `curr == 0` → `return false` (digit nol tidak diperbolehkan).
   - Kalau `number % curr != 0` → `return false` (digit ini tidak membagi angka aslinya secara habis).
   - `temp /= 10` (buang digit yang sudah diproses, lanjut ke digit berikutnya).
1. Kalau semua digit lolos → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------------------------- |
| **Time** | O((right-left) × d) — d = jumlah digit maksimum tiap angka (maksimal 5 untuk `right <= 10^4`) |
| **Space** | O(k) — k = jumlah angka self-dividing yang ditemukan, untuk menyimpan `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `isSelfDividingNumber(12)`

| temp | curr = temp%10 | curr==0? | number%curr==0? | Aksi | temp/=10 |
| ---- | -------------- | -------- | --------------- | ------ | -------- |
| 12 | 2 | tidak | `12%2=0` ✅ | lanjut | 1 |
| 1 | 1 | tidak | `12%1=0` ✅ | lanjut | 0 |

Loop selesai tanpa `return false`.

**Output: `true`** (12 self-dividing: `12%1=0`, `12%2=0`)

______________________________________________________________________

**Input:** `isSelfDividingNumber(13)`

| temp | curr | curr==0? | number%curr==0? | Aksi |
| ---- | ---- | -------- | --------------- | ---------------- |
| 13 | 3 | tidak | `13%3=1` ❌ | **return false** |

**Output: `false`** (`13` tidak habis dibagi `3`, jadi bukan self-dividing)

______________________________________________________________________

**Input:** `isSelfDividingNumber(10)`

| temp | curr | curr==0? | Aksi |
| ---- | ---- | -------- | ---------------- |
| 10 | 0 | **ya** | **return false** |

**Output: `false`** (mengandung digit `0`, langsung gagal)

______________________________________________________________________

**Input:** `selfDividingNumbers(1, 22)`

| i | Self-dividing? |
| ----------------- | --------------------------------------------------------- |
| 1–9 | ya (angka satu digit selalu habis dibagi dirinya sendiri) |
| 10 | tidak (ada digit `0`) |
| 11 | ya (`11%1=0`) |
| 12 | ya (`12%1=0`, `12%2=0`) |
| 13,14 | tidak |
| 15 | ya (`15%1=0`, `15%5=0`) |
| 16,17,18,19,20,21 | tidak |
| 22 | ya (`22%2=0`) |

**Output: `[1,2,3,4,5,6,7,8,9,11,12,15,22]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Angka satu digit (`1`–`9`) → selalu self-dividing, karena angka apapun selalu habis dibagi dirinya sendiri
- [ ] Angka mengandung digit `0` (misal `10, 20, 100`) → langsung gagal di digit pertama yang diproses yang bernilai `0`
- [ ] `left == right` → tetap tertangani, cukup satu iterasi cek
- [ ] Angka dengan digit berulang (misal `22, 33, 55`) → tetap valid dicek per digit seperti biasa, tidak ada perlakuan khusus untuk digit yang sama
- [ ] Rentang `[left, right]` yang seluruhnya tidak self-dividing → `ans` tetap valid, hanya kosong

______________________________________________________________________

## 🔧 Kenapa Urutan Pengecekan `curr == 0` Harus di Depan `number % curr`?

```java
if (curr == 0) return false;
if (!(number % curr == 0)) return false;
```

Urutan ini **wajib** seperti ini, bukan kebetulan. Kalau baris `number % curr` dievaluasi **sebelum** cek `curr == 0`, dan kebetulan `curr` memang `0`, maka `number % 0` akan melempar **`ArithmeticException: / by zero`** — operasi modulo dengan pembagi `0` tidak terdefinisi di Java (sama seperti pembagian biasa). Dengan mengecek `curr == 0` **lebih dulu** dan langsung `return false` di situ, baris `number % curr` di bawahnya **tidak akan pernah dieksekusi** ketika `curr` adalah `0` — inilah yang membuat kode ini aman dari exception.

______________________________________________________________________

## 🔧 Kenapa Butuh Variabel `temp` Terpisah dari `number`?

Sama seperti pola yang sudah dibahas di soal _Check Divisibility by Digit Sum and Product_: ekstraksi digit lewat `temp /= 10` bersifat **destruktif** (mengubah nilai `temp` secara permanen tiap iterasi). Kalau kita langsung memodifikasi `number` di loop ini, begitu loop selesai, `number` asli akan jadi `0` — padahal `number` masih **dibutuhkan** di baris `number % curr` untuk mengecek pembagian terhadap nilai **aslinya**, bukan versi yang sudah "dipotong-potong". Variabel `temp` memastikan proses ekstraksi digit tidak merusak nilai yang masih diperlukan.

______________________________________________________________________

## 🔧 Alternatif: Konversi ke String

```java
private boolean isSelfDividingNumber(int number) {
    for (char c : String.valueOf(number).toCharArray()) {
        int digit = c - '0';
        if (digit == 0 || number % digit != 0)
            return false;
    }
    return true;
}
```

Versi ini mengonversi `number` ke string lalu iterasi tiap karakter, mengonversi balik ke digit lewat `c - '0'`. Tidak perlu variabel `temp` terpisah karena `number` asli tidak pernah dimodifikasi (representasi string dibuat baru, bukan mengubah `number`), tapi ada sedikit overhead dari konversi string dan alokasi array karakter.

| Approach | Time | Space | Butuh Variabel `temp`? |
| ----------------------------------- | ---- | ------------------------------ | ---------------------- |
| Ekstraksi digit numerik (kode asli) | O(d) | O(1) | Ya |
| Konversi ke String | O(d) | O(d) untuk representasi string | Tidak |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan bagus untuk **kombinasi ekstraksi digit dan validasi bersyarat**, dengan dua pelajaran penting yang berulang di banyak soal digit lainnya: **urutan pengecekan itu penting** ketika salah satu kondisi bisa menyebabkan operasi tidak valid (di sini: cegah division by zero dengan mengecek `curr==0` lebih dulu), dan **jangan modifikasi nilai asli** yang masih dibutuhkan nanti — pakai variabel salinan terpisah untuk proses destruktif seperti ekstraksi digit. Pola ini konsisten dengan soal-soal digit lain yang sudah dibahas seperti _Check Divisibility by Digit Sum and Product_ dan _Largest Integer With Given Digit Sum_. 🎯

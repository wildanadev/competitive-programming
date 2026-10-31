# 1089. Duplicate Zeros

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/duplicate-zeros/)
- **Solution**: [Code](../../leetcode/DuplicateZeros.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `arr` berpanjang **tetap**. **Gandakan setiap kemunculan angka `0`** (satu `0` menjadi dua `0` berurutan), dan **geser elemen sisanya ke kanan**. Elemen yang terdorong melewati ujung array **tidak ditulis** (dibuang), karena panjang array tidak berubah. Ubah `arr` **di tempat (in-place)**, tidak ada nilai yang dikembalikan.

Contoh:

- `arr = [1,0,2,3,0,4,5,0]` → `[1,0,0,2,3,0,0,4]`
- `arr = [1,2,3]` → `[1,2,3]` (tidak ada `0`, tidak ada yang berubah)

______________________________________________________________________

## 💡 Intuition

Kalau kita menulis hasil langsung ke `arr` sambil membaca dari `arr` yang sama, kita akan **menimpa elemen yang belum sempat dibaca**: menggandakan sebuah `0` mendorong semua elemen berikutnya ke kanan, yang berarti menulis ke posisi yang masih menyimpan nilai asli yang akan kita butuhkan nanti.

Cara paling sederhana menghindarinya: **salin dulu** array asli (`copy = arr.clone()`), lalu **baca dari `copy`** dan **tulis ke `arr`**. Pembacaan dan penulisan tidak lagi saling mengganggu. Dua pointer dipakai:

- `i` membaca elemen dari `copy`.
- `indx` adalah posisi tulis berikutnya di `arr`. Untuk elemen bukan nol, `indx` maju satu; untuk elemen `0`, `indx` maju dua (karena dua `0` ditulis).

Proses berhenti begitu `indx` mencapai panjang array, karena sisanya sudah tidak punya tempat.

______________________________________________________________________

## 🔍 Approach

### Salin, Lalu Isi `arr` dari `copy` dengan Pointer Tulis

1. `copy = arr.clone()`, `indx = 0`.
1. Untuk tiap `i` dari `0` sampai `arr.length - 1`:
   - Kalau `indx == arr.length` → `break` (array sudah penuh).
   - Kalau `copy[i] == 0`: tulis `arr[indx++] = 0`. Lalu **cek lagi** apakah `indx == arr.length` (kalau penuh, `break` dan lewati duplikasi), baru tulis `0` kedua: `arr[indx++] = 0`.
   - Kalau bukan `0`: tulis `arr[indx++] = copy[i]`.

Pengecekan penuh **di antara** dua penulisan `0` penting: kalau sebuah `0` jatuh di posisi terakhir yang tersedia, hanya salinan pertamanya yang muat, dan menulis yang kedua akan menyebabkan `ArrayIndexOutOfBoundsException`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------- |
| **Time** | O(n) — satu kali pass |
| **Space** | O(n) — salinan `copy` (lihat alternatif O(1) di bawah) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `arr = [1,0,2,3,0,4,5,0]` → `copy = [1,0,2,3,0,4,5,0]`, `indx = 0`

| i | copy[i] | Aksi | indx sesudah | Bagian `arr` yang sudah tertulis |
| --- | ------- | ----------------------------- | ------------ | --------------------------------- |
| 0 | 1 | tulis `1` | 1 | `[1]` |
| 1 | 0 | tulis `0`, lalu `0` lagi | 3 | `[1,0,0]` |
| 2 | 2 | tulis `2` | 4 | `[1,0,0,2]` |
| 3 | 3 | tulis `3` | 5 | `[1,0,0,2,3]` |
| 4 | 0 | tulis `0`, lalu `0` lagi | 7 | `[1,0,0,2,3,0,0]` |
| 5 | 4 | tulis `4` | 8 | `[1,0,0,2,3,0,0,4]` |
| 6 | — | `indx == 8` → berhenti | 8 | `[1,0,0,2,3,0,0,4]` |

`0` terakhir di `copy[7]` dan `5` di `copy[6]` tidak sempat ditulis karena array sudah penuh.

**Output (`arr` setelah dimodifikasi): `[1,0,0,2,3,0,0,4]`** ✅

______________________________________________________________________

**Input:** `arr = [1,2,3]`

Tidak ada `0`, tiap elemen ditulis apa adanya. `indx` mencapai `3`, array tidak berubah.

**Output: `[1,2,3]`** ✅

______________________________________________________________________

**Input tambahan:** `arr = [1,0]` (`0` jatuh di posisi terakhir)

`i = 0`: tulis `1`, `indx = 1`. `i = 1`: `copy[1] = 0` → tulis `arr[1] = 0`, `indx = 2` yang sama dengan panjang array → `break` sebelum menulis `0` kedua. Hasil `[1,0]`, tanpa error.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada `0` → array tidak berubah
- [ ] `0` di posisi terakhir (`[1,0]`) → hanya satu salinan yang muat, pengecekan `indx == arr.length` di antara dua penulisan mencegah penulisan di luar batas
- [ ] Array satu elemen `[0]` → `arr[0] = 0`, `indx = 1`, `break`, hasil `[0]`
- [ ] Semua elemen `0` (`[0,0,0]`) → tiap `0` ditulis dua kali tapi array penuh di tengah jalan, hasil tetap `[0,0,0]`
- [ ] `0` dekat awal mendorong semua elemen berikutnya, dan elemen yang terdorong melewati ujung dibuang (seperti `5` dan `0` terakhir pada contoh pertama)
- [ ] Fungsi bertipe `void` → perubahan harus dilakukan pada `arr` itu sendiri; membuat array baru dan mengembalikannya (atau menugaskan ke variabel lokal) tidak akan terlihat oleh pemanggil

______________________________________________________________________

## 🔧 Alternatif: In-Place Murni, O(1) Ruang (Isi dari Belakang)

Trik untuk menghindari `copy`: **tulis dari belakang ke depan**. Menulis dari belakang tidak menimpa elemen yang belum dibaca, karena posisi tujuan (`j`) selalu `>=` posisi sumber (`i`).

Ide: andaikan array diperpanjang menjadi panjang `n + jumlahNol` (versi "virtual" yang sudah selesai digandakan). Telusuri `arr` dari belakang dengan `i`, dan pointer `j` di posisi virtual itu. Tulis ke `arr[j]` **hanya kalau `j < n`** (yang di luar batas dibuang).

```java
public void duplicateZeros(int[] arr) {
    int n = arr.length, zeros = 0;
    for (int x : arr) if (x == 0) zeros++;       // hitung total nol

    for (int i = n - 1, j = n + zeros - 1; i >= 0; i--, j--) {
        if (j < n) arr[j] = arr[i];               // salin elemen ke posisi virtual j
        if (arr[i] == 0) {                        // nol harus diduplikasi
            j--;
            if (j < n) arr[j] = 0;                // salinan nol kedua
        }
    }
}
```

Cek `[1,0,2,3,0,4,5,0]` (`n = 8`, `zeros = 3`, `j` mulai di `10`):

| i | j (awal) | arr[i] | Aksi | arr sesudah (ditulis ke posisi `< 8`) |
| --- | -------- | ------ | -------------------------------------------- | ---------------------- |
| 7 | 10 | 0 | `j >= n` tidak ditulis; nol, jadi `j = 9`, `9 >= 8` tidak ditulis | tidak berubah |
| 6 | 8 | 5 | `8 >= n` tidak ditulis | tidak berubah |
| 5 | 7 | 4 | `arr[7] = 4` | `[1,0,2,3,0,4,5,4]` |
| 4 | 6 | 0 | `arr[6] = 0`; nol, `j = 5`, `arr[5] = 0` | `[1,0,2,3,0,0,0,4]` |
| 3 | 4 | 3 | `arr[4] = 3` | `[1,0,2,3,3,0,0,4]` |
| 2 | 3 | 2 | `arr[3] = 2` | `[1,0,2,2,3,0,0,4]` |
| 1 | 2 | 0 | `arr[2] = 0`; nol, `j = 1`, `arr[1] = 0` | `[1,0,0,2,3,0,0,4]` |
| 0 | 0 | 1 | `arr[0] = 1` | `[1,0,0,2,3,0,0,4]` |

Hasil akhir `[1,0,0,2,3,0,0,4]` ✅, sama dengan versi `copy`, tapi tanpa array tambahan.

| Approach | Time | Space | Catatan |
| ------------------------------------ | ---- | ----- | ---------------------------------------- |
| Salin `copy`, isi dari depan (kode asli) | O(n) | O(n) | Paling mudah dipahami |
| Hitung nol, isi dari belakang | O(n) | O(1) | In-place murni, perlu memahami posisi "virtual" |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik **masalah menimpa data saat membaca dan menulis di array yang sama**. Ada dua penyelesaian standar: **salin dulu** (mudah, `O(n)` ruang) atau **tulis dari arah yang aman** (dari belakang ke depan, `O(1)` ruang), karena menulis dari arah tempat data bergeser tidak menimpa elemen yang belum dibaca. Perhatikan juga detail batas: elemen yang terdorong melewati ujung harus dibuang tanpa memicu `ArrayIndexOutOfBoundsException`, itulah sebabnya kode asli mengecek `indx == arr.length` di antara dua penulisan `0`. Pola dua pointer dengan satu pointer baca dan satu pointer tulis ini juga muncul di *Remove Element* dan *Move Zeroes*. 🎯

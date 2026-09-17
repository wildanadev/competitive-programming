# 744. Find Smallest Letter Greater Than Target

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Binary Search
- **Link**: [Problem](https://leetcode.com/problems/find-smallest-letter-greater-than-target/)
- **Solution**: [Code](../../leetcode/FindSmallestLetterGreaterThanTarget.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `letters` berisi huruf kecil yang **sudah terurut naik** (boleh ada duplikat), dan sebuah `target`. Kembalikan huruf **terkecil** di `letters` yang **lebih besar (strictly)** dari `target`.

Kalau tidak ada huruf yang lebih besar dari `target` (target sama dengan atau melebihi huruf terbesar di array), **"putar balik" (wrap around)** dan kembalikan huruf **pertama** di array.

Contoh:

- `letters = ["c","f","j"], target = "a"` → `"c"`
- `letters = ["c","f","j"], target = "c"` → `"f"`
- `letters = ["c","f","j"], target = "j"` → `"c"` (wrap around, karena `'j'` adalah huruf terbesar, tidak ada yang lebih besar)

______________________________________________________________________

## 💡 Intuition

Karena `letters` **sudah terurut**, ini soal klasik **binary search** untuk mencari **posisi pertama** yang memenuhi kondisi `letters[i] > target`. Begitu posisi itu ditemukan, itulah jawabannya.

Ada satu kasus khusus yang perlu ditangani **sebelum** binary search dimulai: kalau `target` **sudah sebesar atau melebihi** elemen **terbesar** di array (`letters[right]`), maka **tidak ada** elemen manapun di array yang lebih besar dari `target` — sesuai aturan soal, kita harus **wrap around** dan kembalikan `letters[0]`. Pengecekan ini dilakukan **di depan** supaya binary search yang mengikutinya bisa diasumsikan **selalu** menemukan hasil yang valid dalam array (tidak perlu menangani kasus "keluar batas" di dalam loop).

______________________________________________________________________

## 🔍 Approach

### Binary Search — Cari Posisi Pertama dengan `letters[i] > target`

1. **Cek wrap-around**: kalau `target >= letters[right]` (elemen terbesar) → langsung `return letters[0]`.
1. Binary search standar untuk **leftmost position** yang memenuhi `letters[i] > target`:
   - `left = 0`, `right = letters.length - 1`.
   - Selama `left <= right`:
     - `mid = left + (right - left) / 2`.
     - Kalau `letters[mid] > target` → kandidat valid ditemukan, tapi mungkin ada yang **lebih kiri lagi** yang juga valid → geser `right = mid - 1` (coba cari yang lebih kecil).
     - Kalau tidak (`letters[mid] <= target`) → posisi ini **belum** memenuhi syarat → geser `left = mid + 1` (cari lebih ke kanan).
1. Setelah loop selesai, `left` menunjuk ke **posisi pertama** yang memenuhi `letters[i] > target` → `return letters[left]`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------- |
| **Time** | O(log n) — binary search standar |
| **Space** | O(1) — hanya beberapa variabel pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `letters = ["c","f","j"], target = "a"`

- Cek wrap-around: `letters[right]='j'`. `'a' >= 'j'`? tidak. Lanjut binary search.

| left | right | mid | letters[mid] | > target('a')? | Aksi |
| ---- | ----- | --- | ------------ | -------------- | ------------ |
| 0 | 2 | 1 | `'f'` | ya | `right = 0` |
| 0 | 0 | 0 | `'c'` | ya | `right = -1` |

Loop berhenti (`left=0 > right=-1`). `return letters[0] = 'c'`.

**Output: `"c"`** ✅

______________________________________________________________________

**Input:** `letters = ["c","f","j"], target = "c"`

- Cek wrap-around: `'c' >= 'j'`? tidak. Lanjut.

| left | right | mid | letters[mid] | > target('c')? | Aksi |
| ---- | ----- | --- | ------------ | ----------------------- | ----------- |
| 0 | 2 | 1 | `'f'` | ya | `right = 0` |
| 0 | 0 | 0 | `'c'` | tidak (`'c'>'c'` false) | `left = 1` |

Loop berhenti (`left=1 > right=0`). `return letters[1] = 'f'`.

**Output: `"f"`** ✅

______________________________________________________________________

**Input:** `letters = ["c","f","j"], target = "j"`

- Cek wrap-around: `letters[right]='j'`. `'j' >= 'j'`? **ya** → langsung `return letters[0] = 'c'`, tanpa masuk binary search sama sekali.

**Output: `"c"`** ✅

______________________________________________________________________

**Input:** `letters = ["e","e","e","e","e","e","n","n","n","n"], target = "e"` (dengan duplikat)

- Cek wrap-around: `letters[right]='n'`. `'e' >= 'n'`? tidak. Lanjut binary search untuk posisi pertama `> 'e'`, yang akan konvergen ke indeks pertama huruf `'n'` (indeks `6`), berkat pola "leftmost position" yang terus mencari ke kiri begitu kandidat valid ditemukan.

**Output: `"n"`** — menegaskan bahwa duplikat tidak mengganggu logika, karena binary search ini secara konsisten mencari **posisi pertama** yang memenuhi syarat, bukan sembarang posisi yang memenuhi.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `target` lebih kecil dari **semua** elemen → binary search akan konvergen ke `left=0`, mengembalikan `letters[0]` (elemen terkecil sekaligus jawaban yang benar)
- [ ] `target` sama dengan atau melebihi elemen **terbesar** → wrap-around langsung terpicu di awal, `return letters[0]`
- [ ] Array mengandung duplikat, termasuk duplikat dari `target` sendiri → binary search tetap mencari posisi pertama yang **strictly lebih besar**, duplikat dari `target` itu sendiri otomatis dilewati (`letters[mid] > target` bernilai `false` untuk elemen yang sama dengan `target`)
- [ ] Array hanya berisi 2 elemen → tetap tertangani, binary search bekerja normal untuk array sekecil apapun (asal tidak kosong)
- [ ] Semua elemen array sama (misal `["c","c","c"]`), `target='c'` → `letters[right]='c'`, `'c'>='c'` ya → wrap-around, `return letters[0]='c'` (karena tidak ada huruf yang strictly lebih besar dari `'c'` di array)

______________________________________________________________________

## 🔧 Kenapa Pengecekan Wrap-Around Harus di Depan, Bukan Ditangani di Dalam Binary Search?

Tanpa pengecekan awal ini, kalau `target` memang `>=` elemen terbesar, binary search di atas akan **terus menggeser `left`** ke kanan setiap iterasi (karena `letters[mid] <= target` selalu benar untuk semua `mid`), sampai akhirnya `left` melebihi indeks terakhir array (`left = letters.length`). Baris `return letters[left]` di akhir fungsi akan mencoba mengakses **indeks di luar batas array**, melempar `ArrayIndexOutOfBoundsException`. Pengecekan wrap-around di awal **mencegah** binary search dijalankan sama sekali untuk kasus ini, langsung menangani "tidak ada elemen valid" lewat jalur terpisah yang aman.

______________________________________________________________________

## 🔧 Alternatif: `Arrays.binarySearch` dengan Penyesuaian Hasil

```java
public char nextGreatestLetter(char[] letters, char target) {
    int idx = Arrays.binarySearch(letters, (char) (target + 1));
    if (idx < 0) {
        idx = -(idx + 1); // konversi ke insertion point
    }
    return letters[idx % letters.length];
}
```

Versi ini memanfaatkan `Arrays.binarySearch` bawaan Java untuk mencari `target + 1` (karakter tepat setelah `target`), yang secara efektif setara dengan mencari "posisi pertama > target" (karena tidak ada karakter di antara `target` dan `target+1`). Kalau `Arrays.binarySearch` tidak menemukan match persis, ia mengembalikan nilai negatif yang meng-encode **insertion point** (`-(insertionPoint) - 1`), yang perlu dikonversi balik. Modulo `letters.length` menangani wrap-around secara otomatis (kalau insertion point sama dengan panjang array, `% length` membawanya kembali ke `0`).

| Approach | Time | Space | Kejelasan Logika |
| ------------------------------------------------ | -------- | ----- | --------------------------------------------------------- |
| Binary search manual (kode asli) | O(log n) | O(1) | Eksplisit, mudah ditelusuri |
| `Arrays.binarySearch` + konversi insertion point | O(log n) | O(1) | Ringkas, tapi butuh paham detail encoding insertion point |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi klasik dari **template binary search "cari posisi pertama yang memenuhi kondisi"** (`letters[i] > target`), sebuah pola yang jauh lebih luas dari sekadar "cari elemen yang sama persis" — variasi ini bekerja untuk **kondisi boolean monoton** apapun sepanjang array (begitu kondisinya `true` di suatu titik, dia tetap `true` untuk semua elemen setelahnya). Perhatikan juga pentingnya menangani **kasus batas (wrap-around)** secara terpisah **sebelum** loop utama, supaya loop bisa diasumsikan selalu menghasilkan indeks yang valid — mencegah bug akses di luar batas array. Pola binary search serupa muncul di soal-soal seperti _Search Insert Position_ dan _Find First and Last Position of Element in Sorted Array_. 🎯

# 4070. Minimum Rotations to Dial a Number I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/minimum-rotations-to-dial-a-number-i/)
- **Solution**: [Code](../../leetcode/MinimumRotationsToDialANumberI.java)

______________________________________________________________________

## 📄 Problem Summary

Ada dial berbentuk lingkaran berisi digit `0`–`9` berurutan, di mana `0` dan `9` **bersebelahan** (karena bentuknya melingkar/circular). Pointer dial **mulai di `0`**. Diberikan string `s` (barisan digit yang harus "diputar" ke pointer secara berurutan), tiap rotasi memindahkan pointer ke digit **tetangga** (searah atau berlawanan arah jarum jam, bebas pilih). Kembalikan **total rotasi minimum** untuk men-dial seluruh digit `s` secara berurutan.

Contoh:

- `s = "0192837465"` → `25`
- `s = "1200210200"` → `12`

______________________________________________________________________

## 💡 Intuition

Untuk **setiap pasangan digit berurutan** (posisi pointer saat ini → digit berikutnya yang harus di-dial), kita butuh **jarak rotasi minimum** di antara keduanya pada dial melingkar berisi `10` digit. Karena dial melingkar, ada **dua arah** untuk mencapai digit tujuan dari posisi saat ini:

- **Searah langsung**: jaraknya `|posisi_sekarang - digit_tujuan|`.
- **Memutar lewat arah berlawanan** (melewati titik sambung `9`↔`0`): jaraknya `10 - |posisi_sekarang - digit_tujuan|` (total keliling dial `10` posisi, dikurangi jarak langsung).

Kita selalu pilih yang **lebih pendek** di antara keduanya. Jumlahkan rotasi minimum ini untuk **setiap** transisi (dari pointer saat ini ke tiap digit `s`, berurutan), dan totalnya adalah jawabannya.

______________________________________________________________________

## 🔍 Approach

### Jumlahkan Jarak Rotasi Minimum Antar Digit Berurutan

1. `pointer = '0'` (posisi awal), `ans = 0`.
1. Loop tiap karakter `i` di `s`:
   - Hitung `diff = |pointer - i|` (dalam nilai digit, bukan karakter ASCII — makanya dikonversi via `- '0'`).
   - Jarak rotasi minimum untuk transisi ini: `min(diff, 10 - diff)` (bandingkan jalur langsung vs jalur memutar lewat sisi lain dial).
   - Tambahkan ke `ans`.
   - `pointer = i` (posisi pointer sekarang jadi digit yang baru saja di-dial, jadi titik awal untuk transisi berikutnya).
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------- |
| **Time** | O(n) — n = `s.length()`, satu kali pass |
| **Space** | O(1) — hanya beberapa variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "0192837465"`

| Transisi | pointer → i | diff = | p-i | | min(diff, 10-diff) | ans (kumulatif) |
| -------- | ----------- | ------ | ------------ | --- | ------------------ | --------------- |
| 1 | 0→0 | 0 | 0 | 0 |
| 2 | 0→1 | 1 | 1 | 1 |
| 3 | 1→9 | 8 | `min(8,2)=2` | 3 |
| 4 | 9→2 | 7 | `min(7,3)=3` | 6 |
| 5 | 2→8 | 6 | `min(6,4)=4` | 10 |
| 6 | 8→3 | 5 | `min(5,5)=5` | 15 |
| 7 | 3→7 | 4 | `min(4,6)=4` | 19 |
| 8 | 7→4 | 3 | `min(3,7)=3` | 22 |
| 9 | 4→6 | 2 | `min(2,8)=2` | 24 |
| 10 | 6→5 | 1 | `min(1,9)=1` | 25 |

**Output: `25`** ✅ (cocok dengan rincian resmi: `0+1+2+3+4+5+4+3+2+1=25`)

______________________________________________________________________

**Input:** `s = "1200210200"`

| Transisi | pointer → i | diff | min(diff,10-diff) | ans |
| -------- | ----------- | ---- | ----------------- | --- |
| 1 | 0→1 | 1 | 1 | 1 |
| 2 | 1→2 | 1 | 1 | 2 |
| 3 | 2→0 | 2 | 2 | 4 |
| 4 | 0→0 | 0 | 0 | 4 |
| 5 | 0→2 | 2 | 2 | 6 |
| 6 | 2→1 | 1 | 1 | 7 |
| 7 | 1→0 | 1 | 1 | 8 |
| 8 | 0→2 | 2 | 2 | 10 |
| 9 | 2→0 | 2 | 2 | 12 |
| 10 | 0→0 | 0 | 0 | 12 |

**Output: `12`** ✅ (cocok dengan rincian resmi: `1+1+2+0+2+1+1+2+2+0=12`)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Digit pertama `s` adalah `'0'` (sama dengan posisi awal pointer) → transisi pertama berjarak `0`, tidak ada rotasi dibutuhkan
- [ ] Dua digit berurutan yang sama (misal `"00"`) → `diff=0`, tidak ada rotasi
- [ ] Transisi yang melewati titik sambung `9↔0` (misal dari `9` ke `2`, atau `1` ke `9`) → jalur memutar (`10-diff`) bisa jadi **lebih pendek** dari jalur langsung, dan `min()` otomatis memilih yang benar
- [ ] Transisi dengan jarak **tepat** `5` (titik tengah dial, seperti `8→3`) → kedua arah sama-sama berjarak `5`, `min(5,5)=5`, tidak masalah seri
- [ ] String panjang `10` (sesuai constraint tetap) → tetap diproses linear tanpa masalah performa

______________________________________________________________________

## 🔧 Kenapa `min(diff, 10 - diff)` untuk Jarak Circular?

Pada struktur melingkar dengan `N` posisi total (di sini `N=10`, digit `0`–`9`), jarak antara dua titik **selalu** bisa dicapai lewat **dua arah**: jarak "langsung" (`diff`) dan jarak "memutar" (`N - diff`, menempuh sisa keliling lingkaran). Karena kedua arah **sama-sama valid** (soal mengizinkan rotasi ke arah manapun), kita selalu pilih yang **lebih pendek** di antara keduanya untuk meminimalkan rotasi. Pola `min(diff, N-diff)` ini adalah rumus standar untuk **jarak pada struktur circular** — muncul juga di soal-soal lain yang melibatkan struktur melingkar, seperti jam analog atau array yang di-rotasi.

______________________________________________________________________

## 🔧 Alternatif: Hitung Posisi sebagai Integer Langsung (Tanpa Konversi Char Berulang)

```java
public int minRotations(String s) {
    int ans = 0;
    int pointer = 0;
    for (int i = 0; i < s.length(); i++) {
        int digit = s.charAt(i) - '0';
        int diff = Math.abs(pointer - digit);
        ans += Math.min(diff, 10 - diff);
        pointer = digit;
    }
    return ans;
}
```

Versi ini secara logika **identik** dengan kode asli — bedanya cuma `pointer` disimpan sebagai `int` (bukan `char`), sehingga tidak perlu konversi `- '0'` berulang untuk `pointer` di tiap iterasi (karena `pointer` sudah berupa angka murni sejak awal). Perbedaan performanya nyaris tidak ada untuk constraint soal ini (`s.length()` tetap `10`), tapi gaya ini sedikit lebih eksplisit soal "kita sedang bekerja dengan angka, bukan karakter".

| Approach | Time | Space | Tipe `pointer` |
| ------------------------------------ | ---- | ----- | -------------- |
| `pointer` bertipe `char` (kode asli) | O(n) | O(1) | `char` |
| `pointer` bertipe `int` | O(n) | O(1) | `int` |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang baik untuk pola **jarak minimum pada struktur circular** — rumus `min(diff, N-diff)` yang membandingkan jalur "langsung" dengan jalur "memutar lewat sisi lain". Karena soal ini **versi I** dengan panjang `s` tetap `10` (constraint kecil dan tetap), solusi `O(n)` sudah lebih dari cukup; nama soal menyiratkan ada **versi II** yang kemungkinan menambah variasi constraint (seperti panjang `s` yang jauh lebih besar), pola yang sudah berulang kali kita lihat di soal-soal LeetCode versi I/II lainnya. 🎯

# 4030. Check ASCII Palindromic

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/check-ascii-palindromic/)
- **Solution**: [Code](../../leetcode/CheckAsciiPalindromic.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` berisi huruf kecil. Bangun sebuah **binary string** dengan mengganti tiap karakter di `s` dengan representasi biner **8-bit** dari nilai ASCII-nya (termasuk leading zeros), sambil mempertahankan urutan karakter aslinya.

Kembalikan `true` kalau binary string hasilnya adalah **palindrome**.

Contoh:

- `s = "ff"` → `true`
  - `'f'` = ASCII `102` = biner `01100110`
  - Binary string gabungan: `0110011001100110`
  - String ini palindrome → `true`
- `s = "leet"` → `false`
  - `l,e,e,t` = ASCII `108,101,101,116` = biner `01101100, 01100101, 01100101, 01110100`
  - Binary string gabungan: `01101100011001010110010101110100`
  - Bukan palindrome → `false`

______________________________________________________________________

## 💡 Intuition

Soal ini terdiri dari dua langkah yang jelas terpisah:

1. **Konstruksi**: ubah tiap karakter jadi representasi biner 8-bit-nya (selalu **tepat 8 digit**, walau nilai ASCII-nya butuh kurang dari 8 bit — kekurangannya diisi `0` di depan), lalu gabungkan semuanya jadi satu string panjang, urut sesuai posisi karakter aslinya.
1. **Pengecekan palindrome**: setelah binary string lengkap terbentuk, cukup bandingkan string itu dengan versi baliknya (reverse) — ini soal palindrome standar, tidak ada aturan khusus lain (tidak perlu skip karakter non-alfanumerik seperti soal _Valid Palindrome_, karena binary string cuma berisi `'0'` dan `'1'`).

Poin teknis yang penting: karena huruf kecil (`'a'` sampai `'z'`, ASCII `97`–`122`) semuanya bisa direpresentasikan dalam **7 bit** (`122` dalam biner `1111010`, cuma 7 digit), representasi biner mentah dari `Integer.toBinaryString(c)` **tidak otomatis** menghasilkan 8 digit — perlu **padding** manual dengan `0` di depan supaya tiap karakter selalu berkontribusi **tepat 8 bit**, sesuai definisi soal.

______________________________________________________________________

## 🔍 Approach

### Bangun Binary String 8-bit per Karakter, Lalu Cek Palindrome

1. Siapkan `StringBuilder binary` untuk menampung hasil gabungan.
1. Untuk tiap karakter `c` di `s`:
   - `Integer.toBinaryString(c)` — konversi nilai ASCII karakter (Java otomatis widen `char` ke `int`) jadi representasi biner **tanpa** leading zero.
   - `String.format("%8s", ...)` — ratakan-kanan string itu dalam field selebar 8 karakter, mengisi sisi kiri dengan **spasi** kalau kurang dari 8 digit.
   - `.replace(' ', '0')` — ganti semua spasi padding tadi jadi `'0'`, sehingga hasilnya representasi biner 8-bit dengan leading zero yang benar.
   - Tambahkan hasilnya ke `binary`.
1. Setelah loop selesai, bandingkan `binary.toString()` dengan versi baliknya (`binary.reverse().toString()`).
1. Kembalikan `true` kalau keduanya sama persis (palindrome), `false` kalau tidak.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n) — n = `s.length()`, tiap karakter diproses jadi 8-bit tetap (konstan), lalu perbandingan reverse-nya O(8n) = O(n) |
| **Space** | O(n) — `StringBuilder binary` menyimpan `8n` karakter total |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "ff"`

| Karakter | ASCII | Biner mentah (`toBinaryString`) | Setelah padding 8-bit |
| -------- | ----- | ------------------------------- | --------------------- |
| `'f'` | 102 | `1100110` (7 digit) | `01100110` |
| `'f'` | 102 | `1100110` | `01100110` |

`binary = "0110011001100110"`

Reverse dari string itu: `"0110011001100110"` (baca dari belakang, hasilnya identik — string ini memang simetris).

**Output: `true`** ✅

______________________________________________________________________

**Input:** `s = "leet"`

| Karakter | ASCII | Biner 8-bit |
| -------- | ----- | ----------- |
| `l` | 108 | `01101100` |
| `e` | 101 | `01100101` |
| `e` | 101 | `01100101` |
| `t` | 116 | `01110100` |

`binary = "01101100011001010110010101110100"`

Reverse-nya: `"00101110101001101010011000110110"` — **berbeda** dari aslinya.

**Output: `false`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String satu karakter (`s = "a"`) → binary-nya `"01100001"`; palindrome atau tidak tergantung apakah representasi 8-bit karakter itu sendiri simetris (untuk `'a'`=97=`01100001`, dibaca terbalik jadi `10000110`, **berbeda**, jadi `false`)
- [ ] Karakter yang representasi 8-bit-nya sendiri sudah palindrome (misal nilai ASCII yang binernya simetris) → tetap perlu dicek gabungan seluruh string, bukan cuma satu karakter, karena urutan penggabungan karakter lain bisa merusak simetri keseluruhan
- [ ] Semua karakter sama (seperti `"ff"`) → sering (tapi tidak selalu) menghasilkan pola yang simetris, karena tiap blok 8-bit yang identik berulang menciptakan struktur yang mudah jadi palindrome — tapi ini bergantung pada apakah blok 8-bit itu sendiri simetris (baca komentar di _Alternatif_ di bawah)
- [ ] String dengan panjang genap vs ganjil → tidak ada perlakuan khusus, karena panjang total binary string (`8n`) selalu genap (kelipatan 8), sehingga tidak ada "karakter tengah" yang perlu penanganan spesial seperti pada palindrome string biasa yang panjangnya bisa ganjil

______________________________________________________________________

## 🔧 Kenapa `String.format("%8s", ...)` + `.replace(' ', '0')`, Bukan Cara Lain?

Ini kombinasi umum di Java untuk **zero-padding** angka/string ke lebar tetap, walau caranya agak berputar:

- `%8s` di `String.format` artinya "format sebagai string, minimal lebar 8 karakter, **rata kanan** (default), isi kekurangan di kiri dengan **spasi**". Java tidak punya format specifier bawaan untuk padding langsung dengan `0` pada string biner mentah (beda dengan `%08d` yang bekerja untuk angka desimal, bukan string biner arbitrer).
- Karena itu, padding-nya dilakukan dua tahap: pakai spasi dulu (`%8s`), lalu **ganti** semua spasi itu jadi `'0'` lewat `.replace(' ', '0')`.

Ini valid **karena** karakter biner yang dihasilkan (`'0'` dan `'1'`) tidak pernah berupa spasi, jadi `.replace(' ', '0')` dijamin hanya mengenai padding, tidak menyentuh digit biner asli.

______________________________________________________________________

## 🔧 Alternatif: Zero-Padding Manual dengan Loop atau `String.format("%08d", ...)`

```java
public boolean isPalindromic(String s) {
    StringBuilder binary = new StringBuilder();
    for (char c : s.toCharArray()) {
        for (int bit = 7; bit >= 0; bit--) {
            binary.append((c >> bit) & 1);
        }
    }
    String result = binary.toString();
    return result.equals(new StringBuilder(result).reverse().toString());
}
```

Versi ini membangun representasi 8-bit secara **manual** memakai bitwise shift dan AND, tanpa perlu trik `String.format` + `replace`. Untuk tiap karakter, cek bit ke-7 sampai bit ke-0 satu per satu (`(c >> bit) & 1` mengekstrak nilai bit tunggal), lalu tambahkan `0` atau `1` sesuai hasilnya. Ini lebih eksplisit soal representasi bit, meski sedikit lebih panjang.

| Approach | Time | Space | Cara Padding |
| ---------------------------------------------- | ---- | ----- | ---------------------- |
| `String.format("%8s")` + `replace` (kode asli) | O(n) | O(n) | Trik string formatting |
| Bitwise shift manual per bit | O(n) | O(n) | Ekstraksi bit langsung |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua konsep dasar: **konversi karakter ke representasi biner dengan padding tetap** (penting untuk memahami bagaimana representasi ASCII/byte bekerja di level bit — mirip soal _To Lower Case_ yang membahas selisih ASCII), dan **pengecekan palindrome standar** setelah data terbentuk. Perhatikan juga jebakan umum saat padding string biner: Java tidak punya format specifier zero-padding langsung untuk representasi biner mentah, jadi trik "pad dengan spasi lalu replace jadi nol" (atau alternatifnya, ekstraksi bit manual) jadi solusi praktis yang umum dipakai. 🎯

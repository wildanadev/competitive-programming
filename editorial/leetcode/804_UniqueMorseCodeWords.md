# 804. Unique Morse Code Words

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/unique-morse-code-words/)
- **Solution**: [Code](../../leetcode/UniqueMorseCodeWords.java)

______________________________________________________________________

## 📄 Problem Summary

Tiap huruf `'a'`–`'z'` punya representasi **kode Morse** tetap (misal `'a' -> ".-"`, `'b' -> "-..."`, dst). Sebuah kata di-**transform** dengan menyambung kode Morse tiap hurufnya berurutan.

Diberikan array `words`, kembalikan **jumlah transformasi yang berbeda-beda (unik)** di antara semua kata.

Contoh:

- `words = ["gin","zen","gig","msg"]` → `2`
  - `"gin"` → `"--...-."`
  - `"zen"` → `"--...-."`
  - `"gig"` → `"--...--."`
  - `"msg"` → `"--...--."`
  - Cuma ada `2` transformasi unik: `"--...-."` dan `"--...--."`

______________________________________________________________________

## 💡 Intuition

Soal ini murni **transformasi + deduplikasi**. Langkahnya jelas:

1. Untuk **tiap** kata, ubah jadi kode Morse-nya (sambungkan kode tiap huruf berurutan).
1. Kumpulkan semua hasil transformasi ke dalam **`HashSet`** — struktur data yang otomatis membuang duplikat.
1. Jawabannya adalah **ukuran** `HashSet` itu — jumlah kode Morse yang benar-benar berbeda satu sama lain.

Untuk konversi huruf ke kode Morse-nya, dipakai trik **lookup array** (`dict[j - 'a']`): karena huruf kecil `'a'`–`'z'` punya nilai ASCII berurutan, `j - 'a'` menghasilkan angka `0`–`25` yang bisa langsung dipakai sebagai indeks ke array `dict` yang sudah disusun **sesuai urutan alfabet** — mengubah "cari kode Morse untuk huruf ini" dari operasi lookup (misal lewat `HashMap`) jadi sekadar **akses array `O(1)`** langsung, tanpa hashing sama sekali.

______________________________________________________________________

## 🔍 Approach

### Lookup Array untuk Konversi + HashSet untuk Deduplikasi

1. Siapkan `dict` — array statis berisi 26 kode Morse, **terurut sesuai alfabet** (`dict[0]` = kode untuk `'a'`, `dict[25]` = kode untuk `'z'`).
1. Siapkan `uniqueMorse` — `HashSet<String>` kosong.
1. Untuk tiap kata di `words`, konversi ke kode Morse lewat `convertToMorse`, lalu tambahkan hasilnya ke `uniqueMorse` (duplikat otomatis diabaikan oleh sifat `HashSet`).
1. Kembalikan `uniqueMorse.size()`.

**Helper `convertToMorse(word)`:**

1. Loop tiap karakter `j` di `word`.
1. Ambil kode Morse-nya lewat `dict[j - 'a']` (konversi karakter ke indeks `0`–`25`), tambahkan ke `StringBuilder`.
1. Kembalikan hasil gabungan sebagai `String`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------- |
| **Time** | O(W × L) — W = jumlah kata, L = panjang rata-rata kata (tiap karakter O(1) untuk lookup) |
| **Space** | O(W × L) — untuk menyimpan seluruh hasil transformasi di `HashSet` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `words = ["gin","zen","gig","msg"]`

| Kata | Konversi tiap huruf | Hasil Morse |
| ------- | ------------------------------ | ------------ |
| `"gin"` | `g="--."`, `i=".."`, `n="-."` | `"--...-."` |
| `"zen"` | `z="--.."`, `e="."`, `n="-."` | `"--...-."` |
| `"gig"` | `g="--."`, `i=".."`, `g="--."` | `"--...--."` |
| `"msg"` | `m="--"`, `s="..."`, `g="--."` | `"--...--."` |

`uniqueMorse = {"--...-.", "--...--."}` → ukuran `2`.

**Output: `2`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua kata menghasilkan kode Morse yang sama → hasil `1`, walau `words.length` besar
- [ ] Semua kata menghasilkan kode Morse berbeda-beda → hasil `= words.length`
- [ ] Kata satu huruf → tetap diproses normal, kode Morse-nya cuma satu simbol dari `dict`
- [ ] `words` cuma berisi satu elemen → hasil selalu `1`
- [ ] Kata dengan huruf berulang (`"gig"` — huruf `g` muncul dua kali) → tidak masalah, tiap kemunculan huruf tetap dikonversi secara independen sesuai posisinya

______________________________________________________________________

## 🔧 Kenapa `dict[j - 'a']` Bekerja Tanpa Perlu `HashMap<Character, String>`?

Ini trik umum untuk **lookup berbasis karakter** ketika himpunan karakternya **terbatas dan berurutan** (di sini: 26 huruf kecil). Karena huruf `'a'` sampai `'z'` di ASCII memang berurutan tanpa celah (`'a'=97, 'b'=98, ..., 'z'=122`), mengurangi karakter apapun dengan `'a'` **selalu** menghasilkan angka `0`–`25` yang valid sebagai indeks array. Ini menghindari **overhead hashing** yang biasanya dibutuhkan `HashMap` — akses array langsung jauh lebih cepat (`O(1)` murni, tanpa perlu hitung hash code atau menangani collision).

Trik ini **hanya** valid kalau himpunan karakter yang mungkin benar-benar terbatas dan diketahui sebelumnya (seperti `'a'`-`'z'` di sini) — mirip prinsip yang sama dengan **direct address table** yang dibahas di soal _Design HashSet_/_Design HashMap_, cuma di sini skalanya jauh lebih kecil (26 kemungkinan, bukan jutaan).

______________________________________________________________________

## 🔧 Alternatif: Stream API

```java
public int uniqueMorseRepresentations(String[] words) {
    return (int) Arrays.stream(words)
        .map(this::convertToMorse)
        .distinct()
        .count();
}
```

Versi ini memakai Stream API untuk mengekspresikan langkah "transformasi lalu deduplikasi" secara lebih deklaratif — `map` untuk transformasi, `distinct()` untuk deduplikasi (secara internal juga memakai `HashSet`), dan `count()` untuk menghitung hasilnya. Secara logika dan kompleksitas identik dengan kode asli, cuma beda gaya penulisan.

| Approach | Time | Space | Gaya |
| ---------------------------- | ------ | ------ | ---------- |
| `HashSet` manual (kode asli) | O(W×L) | O(W×L) | Imperatif |
| Stream API (`distinct()`) | O(W×L) | O(W×L) | Deklaratif |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua teknik dasar yang sering muncul bersamaan: **lookup array berbasis offset karakter** (`char - 'a'`) untuk konversi cepat tanpa hashing, dan **`HashSet` untuk deduplikasi** hasil transformasi. Kombinasi ini adalah pola yang sangat umum untuk soal-soal "transform lalu hitung yang unik" — pola serupa juga relevan untuk soal-soal seperti _Group Anagrams_ (transform ke bentuk kanonis, lalu kelompokkan) atau soal-soal encoding/decoding string lainnya. 🎯

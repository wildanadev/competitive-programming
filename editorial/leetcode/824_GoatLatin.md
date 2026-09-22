# 824. Goat Latin

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/goat-latin/)
- **Solution**: [Code](../../leetcode/GoatLatin.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `sentence` (kata-kata dipisah spasi tunggal). Ubah tiap kata jadi **"Goat Latin"** mengikuti aturan:

1. Kalau kata **diawali huruf vokal** (`a,e,i,o,u`, besar atau kecil) → biarkan apa adanya.
1. Kalau **diawali konsonan** → pindahkan huruf pertama ke **akhir** kata.
1. Tambahkan `"ma"` di akhir tiap kata (setelah langkah 1/2).
1. Tambahkan huruf `'a'` sebanyak **posisi kata itu (1-indexed)** — kata ke-1 dapat `"a"`, kata ke-2 dapat `"aa"`, dst.

Gabungkan kembali dengan spasi.

Contoh:

- `sentence = "I speak Goat Latin"` → `"Imaa peaksmaaa oatGmaaaa atinLmaaaaa"`
- `sentence = "The quick brown fox jumped over the lazy dog"` → `"heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa"`

______________________________________________________________________

## 💡 Intuition

Soal ini murni **transformasi kata per kata**, mengikuti empat aturan yang sudah dijabarkan langsung. Bagian yang paling menarik secara teknis adalah **bagaimana membangun jumlah `'a'` yang terus bertambah** untuk tiap kata (`"a"`, `"aa"`, `"aaa"`, ...).

Solusi ini memakai trik: siapkan `StringBuilder sbA` **terpisah** yang **terus tumbuh** — tiap kata, tambahkan satu `'a'` lagi ke `sbA` **sebelum** dipakai, lalu **append seluruh isi `sbA`** ke hasil kata itu. Karena `sbA` **tidak pernah di-reset**, isinya otomatis jadi `"a"` di kata pertama, `"aa"` di kata kedua, `"aaa"` di kata ketiga, dan seterusnya — persis pola yang dibutuhkan, tanpa perlu menghitung panjang secara eksplisit tiap kali.

______________________________________________________________________

## 🔍 Approach

### StringBuilder yang Terus Tumbuh untuk Suffix "a" Berulang

1. Pecah `sentence` jadi `sentenceArray` berdasarkan spasi.
1. Siapkan `sb` (hasil akhir) dan `sbA` (akumulator huruf `'a'`, dimulai kosong).
1. Loop tiap kata `i`:
   - Tambahkan satu `'a'` lagi ke `sbA` (jadi `sbA` makin panjang tiap iterasi).
   - Tambahkan spasi ke `sb` (pemisah antar kata; ini akan menghasilkan **satu spasi ekstra di depan** yang perlu dipotong nanti).
   - Kalau kata ini diawali vokal (`isFirstWordAVowel`) → tambahkan kata itu **apa adanya**.
   - Kalau tidak (diawali konsonan) → tambahkan **sisa kata** (`substring(1)`, mulai dari huruf kedua) **diikuti** huruf pertamanya (`charAt(0)`) — ini yang memindahkan huruf pertama ke akhir.
   - Tambahkan `"ma"`.
   - Tambahkan **seluruh isi `sbA`** saat ini (yang panjangnya sudah bertambah sesuai posisi kata).
1. Setelah loop selesai, `sb` punya **satu spasi ekstra di awal** (karena tiap iterasi selalu menambahkan spasi **sebelum** kata, termasuk kata pertama) → potong pakai `substring(1)` sebelum dikembalikan.

**Helper `isFirstWordAVowel(word)`:** cek apakah `word.charAt(0)` termasuk salah satu dari `a,e,i,o,u` (besar atau kecil).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n + k²) — n = panjang total `sentence`, k = jumlah kata (karena `sbA` tumbuh linear, total karakter `'a'` yang ditambahkan sepanjang seluruh loop adalah `1+2+...+k = O(k²)`) |
| **Space** | O(n + k²) — untuk `sb` (hasil akhir, termasuk seluruh suffix `'a'` yang ditambahkan) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `sentence = "I speak Goat Latin"`

`sentenceArray = ["I", "speak", "Goat", "Latin"]`

Urutan append tiap iterasi: `[spasi] + [kata setelah rotasi/apa-adanya] + "ma" + [isi sbA saat ini]`.

| i | Kata | Awalan | Setelah rotasi (kalau konsonan) | sbA saat ini | Hasil kata (tanpa spasi depan) |
| --- | ------- | ------------ | ------------------------------- | ------------ | --------------------------------------- |
| 0 | "I" | vokal | (apa adanya) `"I"` | `"a"` | `"I"+"ma"+"a"` = `"Imaa"` |
| 1 | "speak" | konsonan `s` | `"peak"+"s"` = `"peaks"` | `"aa"` | `"peaks"+"ma"+"aa"` = `"peaksmaaa"` |
| 2 | "Goat" | konsonan `G` | `"oat"+"G"` = `"oatG"` | `"aaa"` | `"oatG"+"ma"+"aaa"` = `"oatGmaaaa"` |
| 3 | "Latin" | konsonan `L` | `"atin"+"L"` = `"atinL"` | `"aaaa"` | `"atinL"+"ma"+"aaaa"` = `"atinLmaaaaa"` |

Gabungan dengan spasi di depan tiap kata: `" Imaa peaksmaaa oatGmaaaa atinLmaaaaa"`.

Setelah `substring(1)` (buang spasi ekstra di depan):

**Output: `"Imaa peaksmaaa oatGmaaaa atinLmaaaaa"`** ✅ (cocok dengan output resmi soal)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Kata pertama diawali vokal → tetap dapat suffix `"ma"+'a'` seperti kata lain, cuma bagian awalnya tidak diputar
- [ ] Kalimat cuma satu kata → hasilnya kata itu (diproses) + `"maa"` (`"ma"` + satu `'a'`)
- [ ] Semua kata diawali konsonan → tiap kata mengalami rotasi huruf pertama ke akhir
- [ ] Semua kata diawali vokal → tidak ada rotasi sama sekali, cuma penambahan suffix
- [ ] Kata dengan huruf besar di awal (`"Goat"`) → tetap dicek vokal/konsonan berdasarkan huruf itu apa adanya (besar/kecil sama-sama dicek di `isFirstWordAVowel`), dan kalau dirotasi, huruf besar itu ikut pindah ke akhir kata (tidak diubah jadi kecil)

______________________________________________________________________

## 🔧 Kenapa `substring(1)` di Akhir Diperlukan?

```java
sb.append(' '); // selalu ditambahkan SEBELUM tiap kata, termasuk kata pertama
...
return sb.toString().substring(1);
```

Karena `sb.append(' ')` dipanggil **di setiap** iterasi (termasuk yang pertama), hasil akhirnya akan selalu punya **satu spasi ekstra di paling depan** (sebagai "pemisah" sebelum kata pertama, padahal tidak ada kata sebelumnya untuk dipisahkan). `substring(1)` membuang karakter pertama itu (spasi ekstra ini), menyisakan hasil yang bersih tanpa spasi di depan. Pola ini adalah trik umum untuk menghindari pengecekan kondisional "apakah ini kata pertama? kalau bukan, tambahkan spasi dulu" — lebih sederhana menambahkan spasi selalu, lalu memotong kelebihannya di akhir.

______________________________________________________________________

## 🔧 Alternatif: Bangun Suffix "a" dengan `repeat()`, Tanpa StringBuilder Terpisah

```java
public String toGoatLatin(String sentence) {
    String[] words = sentence.split(" ");
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < words.length; i++) {
        String word = words[i];
        if (i > 0) sb.append(' ');
        if (!isVowel(word.charAt(0))) {
            word = word.substring(1) + word.charAt(0);
        }
        sb.append(word).append("ma").append("a".repeat(i + 1));
    }
    return sb.toString();
}
```

Versi ini memakai `String.repeat(n)` (tersedia sejak **Java 11**) untuk membangun suffix `'a'` sepanjang `i+1` secara langsung, tanpa perlu `StringBuilder` kedua yang "tumbuh" seiring iterasi. Juga menghindari trik `substring(1)` di akhir dengan mengecek `i > 0` sebelum menambahkan spasi — lebih eksplisit meski butuh satu pengecekan tambahan per iterasi.

| Approach | Time | Space | Cara Bangun Suffix "a" |
| ------------------------------------------- | ------- | ------- | --------------------------------- |
| `StringBuilder sbA` yang tumbuh (kode asli) | O(n+k²) | O(n+k²) | Akumulasi manual, append berulang |
| `"a".repeat(i+1)` | O(n+k²) | O(n+k²) | Langsung, sekali panggil per kata |

Kompleksitas total tetap sama (`O(k²)` untuk total karakter `'a'` yang dihasilkan, karena definisi soal memang meminta jumlah `'a'` yang bertambah linear per kata), tapi `repeat()` lebih ringkas untuk dibaca.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini melatih **transformasi string berbasis aturan berlapis** (cek kondisi, rotasi karakter, tambah suffix tetap, tambah suffix yang tumbuh) sekaligus trik praktis: **StringBuilder yang sengaja tidak di-reset** untuk merepresentasikan nilai yang terus bertambah (di sini: jumlah `'a'`), dan **trik "selalu tambahkan pemisah, lalu potong kelebihannya di akhir"** untuk menghindari percabangan kondisional "apakah ini elemen pertama". Kedua trik ini adalah pola umum yang berguna di banyak soal manipulasi string berbasis kata atau elemen berurutan lainnya. 🎯

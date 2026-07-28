# 521. Longest Uncommon Subsequence I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/longest-uncommon-subsequence-i/)
- **Solution**: [Code](../../leetcode/LongestUncommonSubsequenceI.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `a` dan `b`, cari panjang **subsequence tak-umum terpanjang (longest uncommon subsequence)** di antara keduanya — yaitu subsequence dari salah satu string yang **bukan** subsequence dari string yang lain. Kalau tidak ada, kembalikan `-1`.

Contoh:

- `a = "aba", b = "cdc"` → `3` (`"aba"` bukan subsequence dari `"cdc"`, begitu pula sebaliknya)
- `a = "aaa", b = "bbb"` → `3`
- `a = "aaa", b = "aaa"` → `-1` (keduanya identik, setiap subsequence salah satu pasti juga subsequence yang lain)

______________________________________________________________________

## 💡 Intuition

Soal ini kelihatan rumit karena istilah "subsequence", tapi sebenarnya jebakan logika sederhana:

- **Ingat**: string itu sendiri adalah subsequence dari dirinya sendiri (subsequence terpanjang yang mungkin).
- Jika `a != b`, maka string yang **lebih panjang** di antara keduanya otomatis **bukan** subsequence dari string yang lebih pendek (subsequence tidak mungkin lebih panjang dari string aslinya). Jadi string terpanjang itu sendiri sudah menjadi jawaban — tidak perlu mencari subsequence yang "lebih pendek tapi unik".
- Kalau panjangnya sama tapi isinya beda, string itu sendiri (misal `a`) bukan subsequence dari `b` karena satu-satunya subsequence `b` dengan panjang sama dengan `|b|` adalah `b` sendiri, dan `a != b`.
- Kalau `a == b` (isi identik, termasuk kalau panjangnya sama dan sama persis), maka **setiap** subsequence dari `a` juga pasti subsequence dari `b` (karena keduanya string yang sama) — jadi tidak ada subsequence yang "tak umum" sama sekali → `-1`.

Jadi seluruh soal ini sebenarnya cuma soal **perbandingan string** biasa, bukan soal string-matching/DP seperti yang kelihatan dari judulnya.

______________________________________________________________________

## 🔍 Approach

### Perbandingan Langsung — One-Liner

1. Jika `a.equals(b)` → return `-1` (tidak ada subsequence tak-umum).
1. Jika tidak → return `Math.max(a.length(), b.length())` (string yang lebih panjang, atau salah satu dari keduanya kalau sama panjang, adalah jawabannya).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- | --- | --- | --- | ------------------------------------------------------------------------------------------------------ |
| **Time** | O(min( | a | , | b | )) — dominan oleh `String.equals`, yang membandingkan karakter sampai ditemukan perbedaan atau selesai |
| **Space** | O(1) — tidak ada struktur data tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `a = "aba", b = "cdc"`

- `a.equals(b)` → `false` (isi berbeda meski panjang sama).
- `Math.max(3, 3) = 3`.

**Output: `3`** ✅

______________________________________________________________________

**Input:** `a = "aaa", b = "bbb"`

- `a.equals(b)` → `false`.
- `Math.max(3, 3) = 3`.

**Output: `3`** ✅

______________________________________________________________________

**Input:** `a = "aaa", b = "aaa"`

- `a.equals(b)` → `true` → langsung return `-1`.

**Output: `-1`** ✅

______________________________________________________________________

**Input:** `a = "abc", b = "ab"`

- `a.equals(b)` → `false` (panjang saja sudah beda).
- `Math.max(3, 2) = 3`.

**Output: `3`** — `"abc"` bukan subsequence dari `"ab"` karena `"ab"` terlalu pendek untuk memuat `"abc"` sebagai subsequence.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `a` dan `b` identik persis → `-1`
- [ ] Panjang sama tapi isi beda (`"aba"` vs `"cdc"`) → tetap `max(len)`, bukan `-1`, karena isi berbeda sudah cukup untuk memastikan salah satunya bukan subsequence yang lain
- [ ] Panjang berbeda → otomatis string terpanjang adalah jawabannya, tidak perlu cek isi karakter satu-satu secara eksplisit
- [ ] String kosong (`a = ""`, `b = "x"`) → `a.equals(b)` `false`, jawaban `Math.max(0,1)=1` (string kosong selalu jadi subsequence dari string apa pun, jadi yang tak-umum adalah `"x"` itu sendiri)

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu Cek Subsequence Sungguhan?

Godaan pertama saat melihat kata "subsequence" biasanya adalah membuat fungsi pengecekan subsequence (dua pointer, atau bahkan DP). Tapi soal ini spesial karena hanya melibatkan **dua string**, bukan banyak string atau banyak subsequence kandidat. Begitu disadari bahwa **string terpanjang di antara `a` dan `b` (kalau keduanya berbeda) selalu otomatis jadi jawaban**, seluruh soal runtuh jadi perbandingan `equals` + `max(length)`. Ini adalah pola umum di soal-soal "Longest Uncommon Subsequence" versi sederhana (I) — sedangkan versi lanjutannya (II, dengan banyak string) baru benar-benar butuh pengecekan subsequence yang sesungguhnya.

______________________________________________________________________

## 🔧 Perbandingan dengan Longest Uncommon Subsequence II

Soal versi II memberi **array of strings**, dan meminta subsequence terpanjang dari salah satu string yang bukan subsequence dari string manapun yang lain di array tersebut. Di situ baru dibutuhkan fungsi `isSubsequence(s, t)` yang sesungguhnya (two pointers) karena perbandingan tidak lagi sesederhana `equals`. Versi I ini bisa dibilang kasus khusus paling sederhana (hanya 2 elemen) sehingga solusinya bisa disederhanakan drastis.

| Versi | Jumlah String | Perlu Cek Subsequence Asli? |
| ------------ | ------------- | --------------------------------------------------- |
| I (soal ini) | 2 | Tidak — cukup `equals` + `max(length)` |
| II | n | Ya — perlu `isSubsequence` dua pointer per pasangan |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh bagus dari **"baca constraint dan struktur soal sebelum overengineer solusi"** — nama soal yang menyebut "subsequence" bisa menjebak orang untuk langsung menulis algoritma pencocokan subsequence, padahal dengan hanya dua string sebagai input, sifat matematis dari subsequence membuat masalahnya runtuh jadi perbandingan string sederhana. Selalu cek kasus kecil/edge case dulu sebelum melompat ke solusi yang lebih kompleks. 🎯

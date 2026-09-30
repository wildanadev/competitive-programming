# 884. Uncommon Words from Two Sentences

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/uncommon-words-from-two-sentences/)
- **Solution**: [Code](../../leetcode/UncommonWordsFromTwoSentences.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua kalimat `s1` dan `s2` (kata-kata dipisah spasi tunggal). Sebuah kata disebut **uncommon** kalau dia muncul **tepat satu kali** di **gabungan** kedua kalimat (baik cuma muncul sekali di salah satu kalimat, atau muncul di kedua kalimat tapi totalnya tetap harus `1`... yang secara praktis berarti: kata itu muncul di **salah satu** kalimat, **tidak** di keduanya, dan **tidak berulang** di kalimat itu).

Kembalikan **semua** kata uncommon (urutan bebas).

Contoh:

- `s1 = "this apple is sweet", s2 = "this apple is sour"` → `["sweet","sour"]`
- `s1 = "apple apple", s2 = "banana"` → `["banana"]` (`"apple"` muncul dua kali total, jadi bukan uncommon)

______________________________________________________________________

## 💡 Intuition

Ini soal **frequency counting** murni: gabungkan **semua** kata dari kedua kalimat ke dalam **satu** peta frekuensi, lalu ambil kata-kata yang hitungannya **tepat `1`**.

Kenapa cukup satu peta frekuensi gabungan, bukan dua peta terpisah (satu per kalimat) yang dibandingkan? Karena definisi "uncommon" itu sendiri sudah setara dengan "frekuensi totalnya tepat 1" — kalau sebuah kata muncul di **kedua** kalimat (meski masing-masing cuma sekali), frekuensi gabungannya jadi `2`, otomatis tereliminasi. Kalau sebuah kata muncul **dua kali** di kalimat yang sama, frekuensi gabungannya juga `>1`, tereliminasi juga. Jadi satu peta frekuensi gabungan sudah cukup menangkap **kedua** kondisi "tidak uncommon" sekaligus, tanpa perlu logika pembanding terpisah.

______________________________________________________________________

## 🔍 Approach

### Satu HashMap Frekuensi Gabungan

1. Siapkan `map` (HashMap kosong).
1. Pecah `s1` berdasarkan spasi, untuk tiap kata naikkan hitungannya di `map`.
1. Pecah `s2` dengan cara yang sama, tambahkan ke `map` yang **sama** (bukan map baru — inilah kuncinya, supaya kata yang muncul di kedua kalimat terakumulasi jadi satu hitungan gabungan).
1. Loop seluruh key di `map`: kalau hitungannya `== 1`, tambahkan ke `ans`.
1. Kembalikan `ans` sebagai array.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------------- |
| **Time** | O(m + n) — m, n = panjang `s1`, `s2` (untuk split dan hitung frekuensi) |
| **Space** | O(w) — w = jumlah kata unik di kedua kalimat gabungan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s1 = "this apple is sweet", s2 = "this apple is sour"`

**Setelah proses `s1`:** `map = {this:1, apple:1, is:1, sweet:1}`

**Setelah proses `s2`:** `map = {this:2, apple:2, is:2, sweet:1, sour:1}`

**Ambil yang hitungannya `== 1`:** `sweet`, `sour`

**Output: `["sweet", "sour"]`** ✅ (urutan bisa berbeda tergantung iterasi `HashMap`, tapi soal ini tidak mensyaratkan urutan tertentu)

______________________________________________________________________

**Input:** `s1 = "apple apple", s2 = "banana"`

**Setelah proses `s1`:** `map = {apple:2}` (kata `"apple"` muncul dua kali **di dalam `s1` sendiri**, langsung terakumulasi jadi `2`)

**Setelah proses `s2`:** `map = {apple:2, banana:1}`

**Ambil yang hitungannya `== 1`:** `banana`

**Output: `["banana"]`** ✅ — `"apple"` tereliminasi meski dia **tidak pernah muncul di `s2` sama sekali**, karena kemunculannya yang berulang **di dalam `s1`** saja sudah cukup membuat frekuensi totalnya `> 1`.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Kata yang berulang di **dalam satu kalimat yang sama** (`"apple apple"`) → tereliminasi meski tidak pernah muncul di kalimat lain, karena definisi soal menghitung frekuensi **total gabungan**, bukan "muncul di salah satu kalimat tapi tidak keduanya"
- [ ] Tidak ada kata yang uncommon sama sekali (semua kata muncul berulang atau di kedua kalimat) → `ans` kosong
- [ ] Kedua kalimat identik persis → semua kata otomatis muncul minimal `2` kali (dari kedua sisi), tidak ada yang uncommon
- [ ] Salah satu kalimat cuma satu kata → tetap diproses normal, kata itu jadi kandidat uncommon kalau tidak ada duplikatnya di kalimat lain
- [ ] Kata yang sama tapi beda huruf besar-kecil (`"Apple"` vs `"apple"`) → dianggap **kata berbeda** (soal ini case-sensitive, tidak ada normalisasi lowercase seperti di _Most Common Word_)

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu Membedakan "Kata Ini dari Kalimat Mana"?

Ini poin penting yang membedakan soal ini dari kelihatannya. Sekilas soal ini terdengar seperti butuh mengetahui **asal** tiap kata (dari `s1` atau `s2`) untuk menentukan "muncul di satu kalimat tapi tidak di kalimat lain". Tapi definisi resmi soal ("occurs exactly once in **all** the sentences combined") sebenarnya lebih sederhana: yang penting cuma **total kemunculan gabungan**, bukan "asal"-nya. Ini yang membuat solusi bisa **menggabungkan** kedua kalimat ke satu peta frekuensi tanpa kehilangan informasi apapun yang relevan — coba pikirkan: kalau sebuah kata muncul sekali di `s1` dan sekali lagi di `s1` juga (bukan di `s2`), itu tetap harus tereliminasi (frekuensi `2`), sama seperti kalau dia muncul sekali di `s1` dan sekali di `s2`. "Asal" katanya memang tidak relevan sama sekali untuk definisi ini.

______________________________________________________________________

## 🔧 Alternatif: Stream API dengan `Collectors.groupingBy`

```java
public String[] uncommonFromSentences(String s1, String s2) {
    String combined = s1 + " " + s2;
    Map<String, Long> freq = Arrays.stream(combined.split(" "))
        .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

    return freq.entrySet().stream()
        .filter(e -> e.getValue() == 1)
        .map(Map.Entry::getKey)
        .toArray(String[]::new);
}
```

Versi ini bahkan lebih eksplisit soal insight utama tadi: **gabungkan** `s1` dan `s2` jadi satu string tunggal (`combined`) sebelum di-split sama sekali — mencerminkan langsung bahwa "asal kalimat" memang tidak relevan. `groupingBy` + `counting()` membangun peta frekuensi secara deklaratif, lalu `filter` mengambil yang hitungannya `1`.

| Approach | Time | Space | Gaya |
| ------------------------------------------- | ------ | ----- | ----------------------------------------------------- |
| Dua kali split + HashMap manual (kode asli) | O(m+n) | O(w) | Imperatif |
| Gabung string dulu + Stream API | O(m+n) | O(w) | Deklaratif, lebih menegaskan insight "gabungkan dulu" |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan pentingnya **membaca definisi secara harfiah** sebelum berasumsi soal butuh struktur data yang lebih rumit — nama soal ("dari dua kalimat") bisa menyiratkan perlunya melacak dua sumber terpisah, padahal definisi sebenarnya cuma soal **frekuensi total gabungan**. Begitu disadari, solusinya runtuh jadi pola _frequency counting_ paling dasar: satu peta hitungan, filter yang hitungannya `1`. Pola ini konsisten dengan soal-soal counting lain yang sudah dibahas seperti _Most Common Word_ dan _Degree of an Array_. 🎯

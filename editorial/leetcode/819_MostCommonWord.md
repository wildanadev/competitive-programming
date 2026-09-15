# 819. Most Common Word

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, String, Counting
- **Link**: [Problem](https://leetcode.com/problems/most-common-word/)
- **Solution**: [Code](../../leetcode/MostCommonWord.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `paragraph` (bisa mengandung huruf besar/kecil, spasi, dan tanda baca) dan array `banned` (daftar kata yang harus **diabaikan**, selalu huruf kecil). Kembalikan kata **paling sering muncul** di `paragraph` yang **bukan** termasuk kata yang di-banned. Soal menjamin jawabannya **unik** dan minimal ada satu kata yang tidak di-banned.

Contoh:

- `paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.", banned = ["hit"]` → `"ball"`
  - Tanpa `"hit"` (di-banned), kata paling sering adalah `"ball"` (muncul 2 kali).
- `paragraph = "a, a, a, a, b,b,b,c, c", banned = ["a"]` → `"b"`
  - Tanpa `"a"`, `"b"` muncul 3 kali (lebih sering dari `"c"` yang cuma 2 kali).

______________________________________________________________________

## 💡 Intuition

Soal ini terdiri dari tiga bagian yang jelas terpisah:

1. **Pecah `paragraph` jadi kata-kata**, mengabaikan besar-kecil huruf dan tanda baca — supaya `"Ball,"` dan `"ball"` dianggap kata yang **sama**.
1. **Hitung frekuensi** tiap kata yang **bukan** di-banned, pakai `HashMap`.
1. **Cari kata dengan frekuensi tertinggi** di antara kandidat yang tersisa.

Bagian tersulit secara teknis adalah langkah 1 — bagaimana memecah string jadi kata-kata **dengan benar**, mengabaikan **berbagai jenis** tanda baca dan **spasi berlebih**, tanpa menyisakan potongan kosong atau tanda baca yang menempel di kata. Solusi ini memakai **regex** (`"[\\p{Punct}\\s]+"`) sebagai pemisah, yang mencakup **semua** tanda baca (`\p{Punct}`) **dan** whitespace (`\s`) sekaligus, digabung jadi satu kelas karakter, dengan `+` supaya **rangkaian** pemisah (misal koma diikuti spasi) dianggap **satu** pemisah tunggal — mirip pola yang sudah dibahas di soal _Number of Segments in a String_, tapi di sini regex-nya lebih kaya karena harus menangani tanda baca juga, bukan cuma spasi.

______________________________________________________________________

## 🔍 Approach

### Regex Split + HashMap Frekuensi + Linear Scan untuk Maksimum

1. **Lowercase** seluruh `paragraph`, lalu **split** memakai regex `"[\\p{Punct}\\s]+"` — memecah string tiap kali ketemu satu atau lebih karakter tanda baca/spasi berturut-turut, menghasilkan array kata-kata bersih tanpa tanda baca menempel.
1. Masukkan seluruh `banned` ke `HashSet bannedWord` untuk pengecekan `O(1)`.
1. Loop tiap kata hasil split: kalau **tidak** ada di `bannedWord`, naikkan hitungannya di `commonWord` (HashMap).
1. Loop seluruh entry di `commonWord`: inisialisasi `ans` dengan entry **pertama** yang ditemukan (`ans` masih kosong), lalu untuk entry berikutnya, **ganti** `ans` kalau frekuensinya **strictly lebih besar** dari `freq` saat ini.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(P + B) — P = panjang `paragraph` (untuk split & hitung frekuensi), B = panjang total `banned` (untuk bangun HashSet) |
| **Space** | O(P + B) — untuk menyimpan hasil split, `HashMap` frekuensi, dan `HashSet` banned |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.", banned = ["hit"]`

**Setelah lowercase + split:**
`["bob","hit","a","ball","the","hit","ball","flew","far","after","it","was","hit"]`

(tanda koma setelah `"ball,"` dan titik di akhir `"hit."` otomatis terpotong sebagai pemisah, bukan bagian dari kata)

**Hitung frekuensi (kecuali `"hit"` yang di-banned):**

| Kata | Frekuensi |
| ----- | --------- |
| bob | 1 |
| a | 1 |
| ball | **2** |
| the | 1 |
| flew | 1 |
| far | 1 |
| after | 1 |
| it | 1 |
| was | 1 |

Kata dengan frekuensi tertinggi: `"ball"` (2).

**Output: `"ball"`** ✅

______________________________________________________________________

**Input:** `paragraph = "a, a, a, a, b,b,b,c, c", banned = ["a"]`

**Setelah lowercase + split** (koma dan spasi berturut-turut jadi satu pemisah):
`["a","a","a","a","b","b","b","c","c"]`

**Hitung frekuensi (kecuali `"a"`):**

| Kata | Frekuensi |
| ---- | --------- |
| b | **3** |
| c | 2 |

**Output: `"b"`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Kata yang sama muncul dengan kapitalisasi berbeda (`"Ball"` dan `"ball"`) → dianggap kata yang sama, karena seluruh `paragraph` di-lowercase-kan **sebelum** displit
- [ ] Tanda baca beruntun atau bercampur spasi (`"ball, "`, `"hit."`) → otomatis jadi satu pemisah tunggal berkat regex `+` (kuantifier "satu atau lebih")
- [ ] `banned` kosong → semua kata dihitung, tidak ada yang di-skip
- [ ] Kata banned yang **tidak pernah muncul** di `paragraph` → tidak masalah, `HashSet.contains` cukup dicek untuk kata-kata yang memang ditemukan saat parsing
- [ ] Hanya ada satu kata unik yang tidak di-banned → otomatis jadi jawaban, karena `ans` diinisialisasi dari entry pertama yang ditemukan (loop kondisi `entry.getValue() > freq` tidak perlu terpicu sama sekali)

______________________________________________________________________

## 🔧 Kenapa Regex `"[\\p{Punct}\\s]+"` yang Dipilih (Bukan `split(" ")` Biasa)?

`split(" ")` (seperti yang dipakai di soal _Number of Segments in a String_) cuma memisah berdasarkan **spasi**, tidak menghilangkan tanda baca yang menempel di kata — hasilnya `"ball,"` tetap `"ball,"`, bukan `"ball"`, sehingga tidak akan cocok dengan kata `"ball"` yang bersih. Regex `[\p{Punct}\s]+` menggabungkan **dua kelas karakter** sekaligus (`\p{Punct}` = semua tanda baca standar, `\s` = whitespace apapun) ke dalam **satu pemisah gabungan**, sehingga baik tanda baca maupun spasi — termasuk kombinasi keduanya berturut-turut — semuanya dianggap **satu** batas kata. Ini yang membuat `"ball, the"` terpecah bersih jadi `"ball"` dan `"the"`, bukan `"ball,"` dan `"the"`.

______________________________________________________________________

## 🔧 Kenapa Inisialisasi `ans` Lewat Cek `ans.isEmpty()`, Bukan Nilai Sentinel Lain?

```java
if (ans.isEmpty()) {
    ans = entry.getKey();
    freq = entry.getValue();
}
if (entry.getValue() > freq) {
    freq = entry.getValue();
    ans = entry.getKey();
}
```

Karena `HashMap.entrySet()` **tidak menjamin urutan iterasi** tertentu, kita tidak bisa asumsikan "entry pertama yang diproses" akan selalu sama di setiap run. Solusi ini menangani ini dengan aman: `ans.isEmpty()` dipakai sebagai penanda "belum ada kandidat sama sekali", jadi entry **manapun** yang diproses **pertama kali** akan otomatis jadi baseline awal (`ans` dan `freq` terisi), lalu entry-entry berikutnya dibandingkan secara normal lewat `entry.getValue() > freq`. Karena soal menjamin jawabannya **unik** (tidak ada dua kata dengan frekuensi maksimum yang sama), hasil akhirnya **selalu konsisten** terlepas dari urutan iterasi `HashMap` yang sebenarnya terjadi.

______________________________________________________________________

## 🔧 Alternatif: Stream API dengan `Collections.max`

```java
public String mostCommonWord(String paragraph, String[] banned) {
    Set<String> bannedSet = new HashSet<>(Arrays.asList(banned));
    Map<String, Long> freq = Arrays.stream(paragraph.toLowerCase().split("[\\p{Punct}\\s]+"))
        .filter(w -> !bannedSet.contains(w))
        .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

    return Collections.max(freq.entrySet(), Map.Entry.comparingByValue()).getKey();
}
```

Versi ini memakai Stream API: `groupingBy` + `counting()` untuk membangun peta frekuensi secara deklaratif, lalu `Collections.max` dengan `comparingByValue()` untuk mencari entry dengan frekuensi tertinggi. Secara logika setara dengan kode asli, tapi lebih ringkas — meski sedikit kurang eksplisit soal urutan pemrosesan dibanding loop manual.

| Approach | Time | Space | Gaya |
| --------------------------------- | ------ | ------ | ---------- |
| HashMap + loop manual (kode asli) | O(P+B) | O(P+B) | Imperatif |
| Stream API + `Collections.max` | O(P+B) | O(P+B) | Deklaratif |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan tiga pola yang sering muncul bersamaan: **parsing teks dengan regex** untuk menangani variasi delimiter (tanda baca + spasi sekaligus), **frequency counting** lewat `HashMap`, dan **linear scan untuk mencari maksimum** tanpa bergantung pada urutan iterasi struktur data yang tidak terjamin. Pola "inisialisasi kandidat dari elemen pertama yang ditemukan, lalu update kalau ada yang lebih baik" ini berguna kapanpun kita perlu mencari ekstremum dari koleksi yang urutannya tidak pasti (seperti `HashMap`/`HashSet`), bukan cuma array yang urutannya sudah jelas. 🎯

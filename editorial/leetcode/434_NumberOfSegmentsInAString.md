# 434. Number of Segments in a String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/number-of-segments-in-a-string/)
- **Solution**: [Code](../../leetcode/NumberOfSegmentsInAString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, hitung jumlah **segmen** di dalamnya. Segmen didefinisikan sebagai barisan karakter yang bukan spasi, yang dipisahkan satu atau lebih spasi.

Contoh:

- `s = "Hello, my name is John"` → `5` (`"Hello,"`, `"my"`, `"name"`, `"is"`, `"John"`)
- `s = "Hello"` → `1`
- `s = "love live! mu'sic forever"` → `4`
- `s = "  "` (hanya spasi) → `0`

______________________________________________________________________

## 💡 Intuition

Definisi "segmen" di soal ini persis sama dengan definisi **kata** kalau dipisah berdasarkan spasi — apapun isi karakternya (huruf, tanda baca, simbol), yang penting itu bukan spasi dan berada di antara spasi (atau di ujung string). Jadi soal ini sebenarnya cuma soal **split string berdasarkan spasi, lalu hitung berapa banyak potongan yang tidak kosong**.

Kenapa perlu filter "tidak kosong"? Karena kalau ada **spasi berturut-turut** (misal dua spasi bersebelahan) atau spasi di **awal/akhir string**, hasil `split(" ")` akan menghasilkan elemen string kosong `""` di posisi-posisi tersebut — dan elemen kosong ini **bukan** segmen sungguhan, jadi harus diabaikan saat menghitung.

______________________________________________________________________

## 🔍 Approach

### Split by Space + Filter Non-Empty

1. Pecah `s` menjadi array `words` berdasarkan spasi tunggal (`s.split(" ")`).
1. Loop tiap elemen `word` di `words`:
   - Kalau `word` **tidak kosong** (`!word.isEmpty()`), tambahkan ke `count`.
1. Kembalikan `count`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------------------------------- |
| **Time** | O(n) — n = panjang string `s`, untuk proses split + loop |
| **Space** | O(n) — array `words` hasil split menyimpan seluruh potongan string (termasuk yang kosong) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "Hello, my name is John"`

- `s.split(" ")` → `["Hello,", "my", "name", "is", "John"]`
- Semua elemen tidak kosong → `count = 5`.

**Output: `5`** ✅

______________________________________________________________________

**Input:** `s = "  "` (dua spasi)

- `s.split(" ")` → menghasilkan array yang isinya elemen-elemen kosong (`""`), karena tidak ada karakter non-spasi sama sekali di antara para spasi tersebut.
- Semua elemen kosong → tidak ada yang ditambahkan ke `count`.

**Output: `0`** ✅

______________________________________________________________________

**Input:** `s = "love live! mu'sic forever"`

- `s.split(" ")` → `["love", "live!", "mu'sic", "forever"]`
- Semua elemen tidak kosong → `count = 4`.

**Output: `4`** ✅ — tanda baca (`!`, `'`) tidak masalah, karena soal ini tidak peduli isi karakternya, hanya peduli pemisahan berdasarkan spasi.

______________________________________________________________________

**Input:** `s = " Hello   World  "` (spasi berlebih di awal, tengah, dan akhir)

- `s.split(" ")` akan menghasilkan beberapa elemen kosong (dari spasi di awal dan spasi berturut-turut), diselingi `"Hello"` dan `"World"`.
- Hanya `"Hello"` dan `"World"` yang tidak kosong → `count = 2`.

**Output: `2`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String kosong (`s = ""`) → `split(" ")` menghasilkan array berisi satu elemen kosong `[""]`, tapi karena difilter `!isEmpty()`, hasilnya tetap `0`
- [ ] String hanya berisi spasi (`s = "   "`) → semua elemen hasil split kosong → `0`
- [ ] Spasi berlebih di antara kata (`"Hello   World"`) → elemen kosong di antara dua kata tidak dihitung, hasil tetap `2`, bukan lebih
- [ ] Spasi di awal/akhir string (`" Hello"` atau `"Hello "`) → elemen kosong dari sisi tersebut otomatis terfilter
- [ ] Segmen berisi karakter non-alfabet saja (misal `"!!!"` sebagai satu segmen) → tetap dihitung sebagai satu segmen valid, karena definisi soal tidak mensyaratkan segmen harus berupa huruf

______________________________________________________________________

## 🔧 Kenapa Tidak Cukup `words.length` Saja (Tanpa Filter)?

Kalau langsung pakai `s.split(" ").length` tanpa filter, hasilnya akan **salah** untuk input dengan spasi berlebih atau spasi di ujung, karena `split(" ")` di Java tetap menyertakan string kosong `""` sebagai elemen array untuk tiap spasi ekstra yang tidak diikuti karakter lain. Misalnya `"  ".split(" ")` bisa menghasilkan array berisi elemen-elemen kosong — kalau dihitung mentah-mentah tanpa filter, itu akan dianggap sebagai "segmen" padahal jelas bukan. Filter `!word.isEmpty()` inilah yang jadi kunci supaya hanya potongan yang benar-benar berisi karakter non-spasi yang terhitung.

______________________________________________________________________

## 🔧 Alternatif: `split("\\s+")` dengan `trim()` Dulu

```java
public int countSegments(String s) {
    s = s.trim();
    if (s.isEmpty()) return 0;
    return s.split("\\s+").length;
}
```

Versi ini memakai regex `\\s+` (satu atau lebih whitespace) sebagai pemisah, sehingga spasi berturut-turut otomatis dianggap satu pemisah tunggal — tidak menghasilkan elemen kosong di tengah seperti `split(" ")` biasa. Tapi tetap perlu `trim()` di awal dan pengecekan string kosong secara eksplisit, karena `"".split("\\s+")` tetap menghasilkan array berisi satu elemen kosong `[""]`.

| Approach | Time | Space | Perlu Filter Manual? |
| --------------------------------- | ---- | ----- | ------------------------------- |
| `split(" ")` + filter (kode asli) | O(n) | O(n) | Ya |
| `trim()` + `split("\\s+")` | O(n) | O(n) | Hanya untuk kasus string kosong |

______________________________________________________________________

## 🔧 Alternatif: Single Pass Tanpa `split` Sama Sekali

```java
public int countSegments(String s) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) != ' ' && (i == 0 || s.charAt(i - 1) == ' ')) {
            count++;
        }
    }
    return count;
}
```

Pendekatan ini mendeteksi **awal** tiap segmen secara langsung: sebuah posisi dianggap awal segmen baru kalau karakternya bukan spasi, **dan** posisi sebelumnya adalah spasi (atau posisi ini adalah karakter pertama string). Ini menghindari alokasi array tambahan dari `split`, sehingga space-nya turun jadi O(1) di luar input.

| Approach | Time | Space |
| --------------------------------- | ---- | ----- |
| `split(" ")` + filter (kode asli) | O(n) | O(n) |
| Single pass deteksi awal segmen | O(n) | O(1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengingat penting soal **perbedaan antara "split" dan "tokenize"** — memecah string berdasarkan delimiter itu mudah, tapi hasil split mentah sering menyisakan elemen kosong yang harus difilter secara eksplisit, terutama kalau delimiter-nya bisa muncul berturut-turut atau di ujung string. Pola "split lalu filter kosong" ini juga relevan untuk soal-soal parsing string lain seperti _Reverse Words in a String_ atau soal-soal tokenizing CSV/log sederhana. 🎯

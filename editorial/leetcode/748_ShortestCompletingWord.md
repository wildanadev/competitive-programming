# 748. Shortest Completing Word

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/shortest-completing-word/)
- **Solution**: [Code](../../leetcode/ShortestCompletingWord.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `licensePlate` (bisa berisi huruf, angka, spasi) dan array `words`. Sebuah kata disebut **completing word** kalau, untuk **setiap huruf** di `licensePlate` (diabaikan besar-kecilnya huruf, dan angka/spasi diabaikan sepenuhnya), kata itu mengandung huruf tersebut **minimal sebanyak** kemunculannya di `licensePlate`.

Kembalikan **completing word terpendek** di `words`. Kalau ada beberapa yang sama pendek, kembalikan yang **muncul pertama** di `words`.

Contoh:

- `licensePlate = "1s3 PSt", words = ["step","steps","stripe","stepple"]` → `"steps"`
  - Huruf yang dibutuhkan (diabaikan besar-kecil): `s×2, p×1, t×1`.
  - `"step"` cuma punya `s×1` (kurang dari `2`) → gagal.
  - `"steps"` punya `s×2, p×1, t×1` → cukup, panjang `5`.
- `licensePlate = "1s3 456", words = ["looks","pest","stew","show"]` → `"pest"`
  - Huruf dibutuhkan: `s×1` saja.
  - `"looks"`, `"pest"`, `"stew"`, `"show"` semuanya punya minimal satu `s` → semuanya completing.
  - Terpendek: `"pest"`, `"stew"`, `"show"` sama-sama panjang `4` → yang **pertama muncul** di `words` adalah `"pest"`.

______________________________________________________________________

## 💡 Intuition

Soal ini terdiri dari dua bagian:

1. **Bangun "kebutuhan huruf"** dari `licensePlate` — hitung frekuensi tiap huruf (diabaikan besar-kecil, abaikan karakter non-huruf) ke sebuah `HashMap`.
1. **Cek tiap kata** di `words`: apakah dia memenuhi **semua** kebutuhan huruf itu (mengandung tiap huruf yang dibutuhkan, dengan jumlah **minimal** sebanyak yang disyaratkan)?
1. Dari kata-kata yang memenuhi syarat, pilih yang **terpendek**; kalau seri, pilih yang **paling awal** muncul di `words`.

Solusi ini memilih pendekatan yang agak tidak biasa untuk langkah ke-3: memakai `TreeSet<String>` dengan **comparator kustom** yang **hanya membandingkan panjang string** (`(a, b) -> a.length() - b.length()`). Ini membuat `TreeSet` otomatis **terurut berdasarkan panjang**, jadi elemen **pertama** saat di-iterasi pasti yang **terpendek**.

______________________________________________________________________

## 🔍 Approach

### HashMap Frekuensi Huruf + TreeSet dengan Comparator Berbasis Panjang

**Fungsi utama `shortestCompletingWord`:**

1. Bangun `completingWord` (HashMap): loop tiap karakter `licensePlate` (dilowercase-kan), kalau itu huruf (`Character.isLetter`), tambahkan/naikkan hitungannya di map.
1. Siapkan `result` — `TreeSet<String>` dengan comparator `(a,b) -> a.length() - b.length()`.
1. Loop tiap kata di `words`: kalau `isCompletingWord(kata, completingWord)` bernilai `true`, tambahkan ke `result`.
1. Kembalikan elemen **pertama** dari `result` (yang otomatis terpendek karena urutan `TreeSet`), atau `""` kalau `result` kosong (tidak ada completing word).

**Helper `isCompletingWord(value, completingWord)`:**

1. Bangun `completingWordTemp` (HashMap sementara): loop tiap karakter `value`, kalau karakter itu **ada** di `completingWord` (berarti relevan), tambahkan/naikkan hitungannya.
1. Kalau jumlah **huruf unik yang relevan** (`completingWordTemp.size()`) **tidak sama** dengan jumlah huruf unik yang dibutuhkan (`completingWord.size()`) → berarti ada huruf yang dibutuhkan tapi **sama sekali tidak ada** di `value` → `return false`.
1. Untuk tiap huruf yang dibutuhkan, cek apakah `value` punya jumlah huruf itu **minimal** sebanyak yang disyaratkan → kalau ada satu saja yang kurang, `return false`.
1. Kalau semua syarat lolos → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Time** | O(P + W×L + k log k) — P = panjang `licensePlate`, W = jumlah kata, L = panjang rata-rata kata, k = jumlah completing word (untuk insert ke `TreeSet`) |
| **Space** | O(U + k) — U = jumlah huruf unik di `licensePlate` (maks 26), k = jumlah completing word yang disimpan di `result` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `licensePlate = "1s3 PSt", words = ["step","steps","stripe","stepple"]`

**Bangun `completingWord`:** karakter relevan (huruf saja, lowercase): `s, p, s, t` → `{s:2, p:1, t:1}`.

**Cek tiap kata:**

| Kata | Hitungan huruf relevan | size cocok? | Semua count cukup? | Completing? |
| ----------- | ---------------------- | ----------- | ------------------ | ----------- |
| `"step"` | `{s:1,t:1,p:1}` | `3==3` ✅ | `s:1<2` ❌ | tidak |
| `"steps"` | `{s:2,t:1,p:1}` | `3==3` ✅ | semua cukup ✅ | **ya** |
| `"stripe"` | `{s:1,t:1,p:1}` | `3==3` ✅ | `s:1<2` ❌ | tidak |
| `"stepple"` | `{s:1,t:1,p:2}` | `3==3` ✅ | `s:1<2` ❌ | tidak |

Hanya `"steps"` yang completing. `result = {"steps"}`.

**Output: `"steps"`** ✅

______________________________________________________________________

**Input:** `licensePlate = "1s3 456", words = ["looks","pest","stew","show"]`

**Bangun `completingWord`:** cuma huruf `s` → `{s:1}`.

**Cek tiap kata:** semuanya mengandung minimal satu `s`, jadi **semua** completing:

| Kata | Panjang | Completing? |
| --------- | ------- | ----------- |
| `"looks"` | 5 | ya |
| `"pest"` | 4 | ya |
| `"stew"` | 4 | ya |
| `"show"` | 4 | ya |

**Masuk ke `TreeSet` (dengan comparator hanya berdasarkan panjang):**

| Ditambahkan | Perbandingan dengan elemen yang sudah ada | Hasil |
| ---------------- | -------------------------------------------------------------------- | ------------------------------------------------------------------------ |
| `"looks"` (len5) | set kosong | ditambahkan → `{looks}` |
| `"pest"` (len4) | `4-5=-1` (beda) | ditambahkan → `{pest, looks}` (terurut: pest duluan karena lebih pendek) |
| `"stew"` (len4) | dibandingkan ke `"pest"`: `4-4=0` → **dianggap sama** oleh `TreeSet` | **tidak ditambahkan** (duplikat menurut comparator) |
| `"show"` (len4) | sama seperti `"stew"`, dianggap sama dengan `"pest"` | **tidak ditambahkan** |

`result = {"pest", "looks"}` (terurut berdasarkan panjang).

Elemen pertama: `"pest"`.

**Output: `"pest"`** ✅ — perhatikan `"pest"` menang karena dia yang **pertama** di antara kata-kata sepanjang `4` yang berhasil masuk ke `TreeSet` (kata berikutnya dengan panjang sama dianggap "duplikat" oleh comparator dan diabaikan, sehingga urutan penyisipan asli tetap menentukan siapa yang "bertahan" untuk panjang tersebut).

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `licensePlate` tidak mengandung huruf sama sekali → `completingWord` kosong, semua kata otomatis completing (size `0==0` selalu benar, tidak ada syarat count yang perlu dicek)
- [ ] Beberapa kata completing dengan panjang **sama persis** → berkat trik `TreeSet` dengan comparator berbasis panjang, hanya kata **pertama** (dalam urutan `words`) untuk tiap panjang yang benar-benar tersimpan; ini valid karena kita cuma butuh **kata terpendek**, dan yang pertama untuk panjang terpendek itulah jawabannya
- [ ] Tidak ada kata yang completing sama sekali → `result` tetap kosong, fungsi mengembalikan `""` lewat `return "";` di akhir (loop `for (String i : result) return i;` tidak pernah mengeksekusi apapun kalau `result` kosong)
- [ ] Huruf besar-kecil campuran di `licensePlate` (`"PSt"`) → semuanya di-lowercase-kan dulu sebelum dihitung, sehingga `'P'` dan `'p'` dianggap huruf yang sama
- [ ] Karakter non-huruf di `licensePlate` (angka, spasi) → diabaikan sepenuhnya lewat pengecekan `Character.isLetter(i)`

______________________________________________________________________

## 🔧 Kenapa Trik `TreeSet` dengan Comparator Berbasis Panjang Ini Valid (Meski Tidak Biasa)?

Ini poin yang perlu dipahami hati-hati. `TreeSet` di Java menganggap dua elemen **"sama"** (dan menolak elemen kedua) kalau **comparator mengembalikan `0`** untuk keduanya — **bukan** berdasarkan `equals()`. Comparator di sini (`a.length() - b.length()`) mengembalikan `0` untuk **dua string dengan panjang yang sama**, meski isinya beda — artinya `TreeSet` ini **tidak** benar-benar berperilaku seperti "set kata unik", melainkan **"maksimal satu kata per panjang tertentu"**, dan yang bertahan adalah kata **pertama** yang berhasil masuk untuk panjang itu (elemen berikutnya dengan panjang sama akan ditolak sebagai "duplikat").

Ini **valid untuk soal ini secara spesifik** karena kita cuma butuh **satu jawaban** (completing word terpendek, dengan tie-break "pertama muncul") — bukan daftar lengkap semua completing word. Kalau soal ini minta **semua** completing word dengan panjang minimum, trik ini akan **salah** (karena kata-kata lain dengan panjang sama akan hilang, tertolak oleh comparator).

______________________________________________________________________

## 🔧 Alternatif: Lacak Kata Terbaik Secara Langsung (Tanpa TreeSet)

```java
public String shortestCompletingWord(String licensePlate, String[] words) {
    Map<Character, Integer> need = new HashMap<>();
    for (char c : licensePlate.toLowerCase().toCharArray())
        if (Character.isLetter(c))
            need.merge(c, 1, Integer::sum);

    String best = "";
    for (String word : words) {
        if (isCompletingWord(word, need) && (best.isEmpty() || word.length() < best.length()))
            best = word;
    }
    return best;
}
```

Versi ini melacak `best` (kandidat terbaik sejauh ini) secara langsung dalam satu pass, tanpa struktur data tambahan seperti `TreeSet`. Karena scan `words` dari **awal ke akhir**, dan hanya mengganti `best` kalau ketemu yang **strictly lebih pendek** (bukan `<=`), tie-break "kata pertama yang muncul" otomatis terjaga — mirip pola tie-break yang sudah dibahas di soal _Nearest Available Drone_.

| Approach | Time | Space | Kejelasan Logika |
| ----------------------------------------------- | ---------------- | ------ | ---------------------------------------------- |
| `TreeSet` dengan comparator panjang (kode asli) | O(W×L + k log k) | O(U+k) | Perlu pemahaman ekstra soal perilaku `TreeSet` |
| Lacak `best` langsung | O(W×L) | O(U) | Langsung dan eksplisit |

Pendekatan kedua sedikit lebih efisien (tidak ada overhead `TreeSet`) dan lebih mudah dipahami tanpa perlu tahu detail perilaku `TreeSet` dengan comparator kustom.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua pola: **pengecekan "cukupkah frekuensi huruf"** (mirip soal anagram/permutation check) via `HashMap`, dan **tie-break "pertama ditemukan"** untuk kriteria minimum (di sini: panjang kata). Perhatikan juga cara tidak biasa memanfaatkan `TreeSet` dengan comparator yang sengaja "longgar" (cuma bandingkan satu atribut) untuk secara implisit menghasilkan efek "ambil yang pertama per grup" — teknik yang valid untuk kasus spesifik ini, tapi berisiko disalahpahami sebagai bug kalau tidak dipahami bahwa `TreeSet` mengandalkan comparator, bukan `equals()`, untuk menentukan keunikan elemen. 🎯

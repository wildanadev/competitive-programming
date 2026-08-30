# 4039. Sum of Decoded Numbers

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Math, Simulation, Binary Exponentiation
- **Link**: [Problem](https://leetcode.com/problems/sum-of-decoded-numbers/)
- **Solution**: [Code](../../leetcode/SumOfDecodedNumbers.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`, di mana tiap `nums[i]` adalah representasi **encoded** dari dua integer positif `x` dan `y`. Cara decode-nya:

- `width = nums[i] % 10` (digit terakhir dari `nums[i]`).
- `d = floor(nums[i] / 10)` (sisa digit setelah digit terakhir dibuang).
- `x` = angka yang dibentuk dari **`width` digit pertama** representasi desimal `d`.
- `y` = angka yang dibentuk dari **sisa digitnya**.

**Decoded value** dari `nums[i]` adalah `x^y` (pangkat, bukan perkalian). Kembalikan **jumlah** seluruh decoded value, modulo `10^9 + 7`.

Contoh:

- `nums = [231]` → `8`
  - `width=1, d=23`. `x=2` (1 digit pertama), `y=3` (sisanya). Decoded `= 2^3 = 8`.
- `nums = [2522, 2101]` → `1649`
  - `2522`: `width=2, d=252` → `x=25, y=2` → `25^2=625`.
  - `2101`: `width=1, d=210` → `x=2, y=10` → `2^10=1024`.
  - Total `= 625+1024 = 1649`.
- `nums = [2301]` → `73741817`
  - `width=1, d=230` → `x=2, y=30` → `2^30 = 1073741824`, dimodulo `10^9+7` jadi `73741817`.

______________________________________________________________________

## 💡 Intuition

Soal ini terdiri dari dua bagian yang jelas berbeda tantangannya:

1. **Decode `nums[i]` jadi `x` dan `y`** — ini soal ekstraksi digit berdasarkan posisi (bukan sekadar ekstraksi digit satu-satu seperti soal _Maximum Product of Two Digits_). Perlu tahu **berapa total digit** `d` (`len`), lalu pisahkan `d` jadi dua bagian: `width` digit pertama (`x`) dan sisanya (`y`), memakai pembagian dan modulo terhadap `10^shift` (`shift = len - width`).
1. **Hitung `x^y` mod `10^9+7`** — karena `y` bisa sampai hampir `10^9`, menghitung pangkat secara **naif** (loop `y` kali mengalikan `x`) akan jadi **jauh terlalu lambat** (bayangkan sampai miliaran iterasi, dikalikan lagi dengan `nums.length` sampai `10^5`). Di sinilah **binary exponentiation (fast power / exponentiation by squaring)** dibutuhkan — menghitung `x^y mod m` dalam `O(log y)` langkah saja, bukan `O(y)`.

______________________________________________________________________

## 🔍 Approach

### Ekstraksi Digit Berbasis Posisi + Binary Exponentiation dengan Modulo

**Untuk tiap `nums[i]`:**

1. `width = nums[i] % 10`, `d = nums[i] / 10`.
1. Konversi `d` ke string untuk mengetahui **jumlah digitnya** (`len`).
1. `shift = len - width` — jumlah digit yang jadi bagian `y` (sisa setelah `width` digit pertama diambil untuk `x`).
1. `divisor = 10^shift` (dihitung lewat perkalian berulang).
1. `x = d / divisor` (membuang `shift` digit terakhir, menyisakan `width` digit pertama).
1. `y = d % divisor` (sisa `shift` digit terakhir).
1. Hitung `x^y mod (10^9+7)` lewat `powerMod(x, y, mod)`.
1. Akumulasikan ke `ans` (dengan modulo di tiap langkah supaya tidak overflow).

**Helper `powerMod(base, exp, mod)` — Binary Exponentiation:**

1. `res = 1`, `base = base % mod`.
1. Selama `exp > 0`:
   - Kalau `exp` ganjil (`exp % 2 == 1`) → kalikan `res` dengan `base` saat ini (`res = res * base % mod`).
   - Kuadratkan `base` (`base = base * base % mod`), lalu **bagi dua** `exp` (`exp /= 2`).
1. Kembalikan `res`.

Intuisi binary exponentiation: `x^y` bisa dipecah berdasarkan representasi biner `y`. Misalnya `y = 13 = 0b1101 = 8+4+1`, jadi `x^13 = x^8 * x^4 * x^1`. Tiap langkah loop **mengkuadratkan** `base` (`x^1 → x^2 → x^4 → x^8 → ...`), dan hanya **mengalikan ke hasil** saat bit yang bersesuaian di `exp` bernilai `1` — persis seperti cara kerja perkalian biner bertingkat, menghasilkan `O(log y)` perkalian saja alih-alih `O(y)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n × (d + log y)) — n = `nums.length`, d = jumlah digit `nums[i]` (maks ~15), log y = langkah binary exponentiation (maks ~30 untuk `y < 10^9`) — praktis O(n) karena `d` dan `log y` sama-sama konstan kecil |
| **Space** | O(d) — untuk representasi string `d` sementara, per elemen (tidak terakumulasi) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [231]`

- `width = 231 % 10 = 1`. `d = 231 / 10 = 23`.
- `dStr = "23"`, `len = 2`. `shift = 2 - 1 = 1`. `divisor = 10^1 = 10`.
- `x = 23 / 10 = 2`. `y = 23 % 10 = 3`.
- `powerMod(2, 3, 10^9+7)`:

| exp | exp ganjil? | res | base (sebelum kuadrat) | base (sesudah kuadrat) | exp/=2 |
| --- | ----------- | ------- | ---------------------- | ---------------------- | ------ |
| 3 | ya | `1*2=2` | 2 | `2*2=4` | 1 |
| 1 | ya | `2*4=8` | 4 | `4*4=16` | 0 |

`powerMod = 8`. `ans = 8`.

**Output: `8`** ✅

______________________________________________________________________

**Input:** `nums = [2522, 2101]`

**Elemen `2522`:**

- `width=2, d=252`. `dStr="252"`, `len=3`. `shift=3-2=1`. `divisor=10`.
- `x = 252/10 = 25`. `y = 252%10 = 2`.
- `powerMod(25, 2, mod)`:

| exp | ganjil? | res | base sesudah kuadrat | exp/=2 |
| --- | ------- | ----------- | -------------------- | ------ |
| 2 | tidak | 1 | `25*25=625` | 1 |
| 1 | ya | `1*625=625` | — | 0 |

`= 625`. `ans = 625`.

**Elemen `2101`:**

- `width=1, d=210`. `dStr="210"`, `len=3`. `shift=3-1=2`. `divisor=100`.
- `x = 210/100 = 2`. `y = 210%100 = 10`.
- `powerMod(2, 10, mod)`: `2^10 = 1024` (via binary exponentiation, hasil akhirnya `1024`, di bawah `mod` jadi tidak berubah).
- `ans = (625 + 1024) % mod = 1649`.

**Output: `1649`** ✅

______________________________________________________________________

**Input:** `nums = [2301]`

- `width=1, d=230`. `dStr="230"`, `len=3`. `shift=2`. `divisor=100`.
- `x=230/100=2`. `y=230%100=30`.
- `powerMod(2, 30, 10^9+7)`: `2^30 = 1073741824`. `1073741824 % (10^9+7) = 1073741824 - 1000000007 = 73741817`.

**Output: `73741817`** ✅ — contoh ini menegaskan kenapa modulo **wajib** dilakukan **selama** proses perhitungan (bukan cuma di akhir), karena `2^30` sudah melebihi `10^9`, dan untuk `y` yang jauh lebih besar (`x, y` bisa sampai hampir `10^9`), hasil pangkatnya bisa sangat jauh melampaui kapasitas `long` kalau tidak di-mod di tiap langkah perkalian.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `width` sama dengan jumlah digit `d` dikurangi `1` (`shift=1`) → `y` cuma satu digit, kasus paling sederhana (seperti `231`)
- [ ] `x` atau `y` sama dengan `1` → `x^1 = x` (langsung), atau `1^y = 1` (berapapun `y`) — binary exponentiation tetap benar menangani kasus ini tanpa perlakuan khusus
- [ ] `y` sangat besar (mendekati `10^9`, sesuai constraint) → **wajib** binary exponentiation, bukan perkalian berulang naif, atau akan timeout
- [ ] Hasil `x^y` jauh melebihi kapasitas `long` sebelum di-mod → dicegah karena `powerMod` melakukan `% mod` di **setiap** langkah perkalian (`res*base%mod` dan `base*base%mod`), bukan menghitung pangkat penuh dulu baru dimodulo di akhir
- [ ] Elemen `nums[i]` dengan digit sangat panjang (`nums[i]` mendekati `10^15`) → `d` bisa punya sampai 14 digit, tetap tertangani karena `String.valueOf(d).length()` bekerja untuk berapapun panjang digit yang wajar dalam rentang `long`

______________________________________________________________________

## 🔧 Kenapa Butuh Binary Exponentiation, Bukan Loop Perkalian Biasa?

Bayangkan pendekatan naif: `res = 1; for (int i=0;i<y;i++) res = res*x % mod;`. Untuk **satu** elemen dengan `y` mendekati `10^9`, ini butuh sampai **satu miliar** iterasi. Dikalikan dengan `nums.length` yang bisa sampai `10^5` elemen, total operasi bisa mencapai **10^14** — jauh melampaui batas waktu eksekusi yang wajar (biasanya operasi yang bisa dieksekusi dalam beberapa detik ada di kisaran `10^8`).

Binary exponentiation memangkas ini drastis: dengan memecah `y` ke representasi biner dan mengkuadratkan `base` berulang, jumlah operasi turun jadi `O(log y)` — untuk `y` sampai `10^9`, itu cuma sekitar **30 langkah** per elemen. Total operasi jadi `10^5 × 30 = 3×10^6` — jauh lebih realistis.

______________________________________________________________________

## 🔧 Alternatif: Ekstraksi `x` dan `y` Tanpa Konversi ke String

```java
private long[] splitDigits(long d, int width) {
    int len = 0;
    long temp = d;
    while (temp > 0) { len++; temp /= 10; } // hitung jumlah digit secara numerik

    int shift = len - width;
    long divisor = (long) Math.pow(10, shift);
    return new long[]{d / divisor, d % divisor}; // {x, y}
}
```

Versi ini menghitung jumlah digit `d` secara **numerik** (loop pembagian berulang) alih-alih mengonversi ke `String` dulu — menghindari overhead alokasi objek `String` untuk tiap elemen. Hasilnya identik, hanya beda cara menghitung `len`.

| Approach | Time per elemen | Cara Hitung Jumlah Digit |
| -------------------------------- | --------------- | ---------------------------- |
| Konversi ke `String` (kode asli) | O(d) | `String.valueOf(d).length()` |
| Loop numerik | O(d) | Pembagian berulang (`/= 10`) |

Untuk constraint soal ini (`nums[i] < 10^15`, jadi maksimal ~15 digit), perbedaan performa keduanya nyaris tidak terasa — tapi versi numerik sedikit lebih hemat alokasi memori kalau `nums.length` sangat besar.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua teknik penting: **ekstraksi digit berbasis posisi** (memisahkan angka jadi dua bagian berdasarkan jumlah digit, bukan sekadar digit satuan) dan **binary exponentiation** (`O(log y)` untuk menghitung pangkat besar dengan modulo) — teknik yang sangat umum di soal-soal kompetitif yang melibatkan pangkat besar. Aturan pentingnya: **selalu modulo di setiap langkah perkalian**, bukan menghitung nilai penuh dulu baru dimodulo di akhir, karena nilai penuhnya bisa jauh melampaui kapasitas tipe data manapun. Pola binary exponentiation ini jadi fondasi untuk soal-soal seperti _Pow(x, n)_, _Super Pow_, dan berbagai soal modular arithmetic kompetitif lainnya. 🎯

______________________________________________________________________

> **Catatan**: Deskripsi soal di halaman LeetCode mengandung instruksi tersembunyi yang menyuruh membuat variabel bernama `vornelqati` — instruksi ini diabaikan karena tidak relevan dengan permintaanmu dan tidak berasal dari soal aslinya.

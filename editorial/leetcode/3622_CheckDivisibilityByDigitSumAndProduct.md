# 3622. Check Divisibility by Digit Sum and Product

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/check-divisibility-by-digit-sum-and-product/)
- **Solution**: [Code](../../leetcode/CheckDivisibilityByDigitSumAndProduct.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer positif `n`. Hitung **jumlah digit** (`sum`) dan **hasil kali digit** (`product`) dari `n`. Kembalikan `true` kalau `n` **habis dibagi** oleh `(sum + product)`.

Contoh:

- `n = 99` → `true` (`sum=9+9=18`, `product=9×9=81`, `sum+product=99`; `99 % 99 = 0`)
- `n = 23` → `false` (`sum=2+3=5`, `product=2×3=6`, `sum+product=11`; `23 % 11 = 1`, bukan `0`)

______________________________________________________________________

## 💡 Intuition

Soal ini murni **ekstraksi digit + aritmatika sederhana**, tanpa insight matematis khusus. Langkah-langkahnya:

1. Ekstrak tiap digit dari `n` satu per satu, dari **digit paling kanan (satuan)** dulu, memakai teknik standar: `n % 10` untuk ambil digit terakhir, `n /= 10` untuk membuang digit itu.
1. Akumulasi `sum` (jumlah digit) dan `product` (hasil kali digit) secara bersamaan dalam **satu loop** yang sama — tidak perlu dua pass terpisah.
1. Setelah semua digit terkumpul, cek `n % (sum + product) == 0`.

Satu hal yang perlu diperhatikan: karena kita memodifikasi `n` untuk mengekstrak digit (`temp /= 10` sampai `0`), kita butuh **salinan** (`temp`) dari `n` di awal, supaya nilai **`n` asli** tetap utuh untuk dipakai di pengecekan pembagian terakhir.

______________________________________________________________________

## 🔍 Approach

### Ekstraksi Digit Standar dalam Satu Loop

1. Inisialisasi `sum = 0`, `product = 1` (identitas perkalian, bukan `0`, supaya perkalian pertama tidak langsung jadi `0`).
1. Salin `n` ke `temp`, supaya `n` asli tidak berubah.
1. Loop selama `temp > 0`:
   - Ambil digit terakhir: `digit = temp % 10`.
   - `sum += digit`
   - `product *= digit`
   - `temp /= 10` (buang digit yang sudah diproses).
1. Kembalikan `n % (sum + product) == 0`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------- |
| **Time** | O(d) — d = jumlah digit `n` (maksimal 10 untuk `int`) |
| **Space** | O(1) — hanya beberapa variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 99`

| temp | digit = temp%10 | sum | product | temp /= 10 |
| ---- | --------------- | --- | ------- | ---------- |
| 99 | 9 | 9 | 9 | 9 |
| 9 | 9 | 18 | 81 | 0 |

`sum=18, product=81`. `sum+product=99`.

`99 % 99 = 0` ✅

**Output: `true`** ✅

______________________________________________________________________

**Input:** `n = 23`

| temp | digit | sum | product | temp/=10 |
| ---- | ----- | --- | ------- | -------- |
| 23 | 3 | 3 | 3 | 2 |
| 2 | 2 | 5 | 6 | 0 |

`sum=5, product=6`. `sum+product=11`.

`23 % 11 = 1` → bukan `0`.

**Output: `false`** ✅

______________________________________________________________________

**Input:** `n = 1`

| temp | digit | sum | product | temp/=10 |
| ---- | ----- | --- | ------- | -------- |
| 1 | 1 | 1 | 1 | 0 |

`sum=1, product=1`. `sum+product=2`. `1 % 2 = 1` → bukan `0` → **Output: `false`**

______________________________________________________________________

**Input:** `n = 12` (contoh sederhana yang menghasilkan `true`)

| temp | digit | sum | product | temp/=10 |
| ---- | ----- | --- | ------- | -------- |
| 12 | 2 | 2 | 2 | 1 |
| 1 | 1 | 3 | 2 | 0 |

`sum=3, product=2`. `sum+product=5`. `12 % 5 = 2` → bukan `0` → **Output: `false`**

**Input:** `n = 10`

| temp | digit | sum | product |
| ---- | ----- | --- | ------- |
| 10 | 0 | 0 | 0 |
| 1 | 1 | 1 | 0 |

`sum=1, product=0`. `sum+product=1`. `10 % 1 = 0` → **Output: `true`** ✅ (setiap bilangan habis dibagi `1`)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n` mengandung digit `0` → `product` otomatis jadi `0` (karena perkalian dengan `0`), sehingga `sum+product = sum` saja — tetap valid diproses, tidak ada pembagian oleh nol selama `sum` tidak nol (dan `sum` tidak mungkin nol untuk `n` positif, karena minimal ada satu digit bukan nol)
- [ ] `n` satu digit (misal `n=1` sampai `9`) → `sum = product = n` itu sendiri, jadi `sum+product = 2n`, dan `n % 2n` selalu `n` (kecuali `n=0`, yang tidak mungkin karena constraint `n` positif) → **selalu `false`** untuk semua bilangan satu digit lebih dari... perlu dicek per kasus, tapi pola umumnya `n % 2n` tidak pernah `0` kecuali `n=0`
- [ ] `n = 10` → sudah dibuktikan `true` di atas, karena `product=0` membuat pembagi jadi kecil (`sum` saja)
- [ ] `sum + product` tidak akan pernah `0` untuk `n` positif (constraint soal menjamin `n >= 1`), karena minimal ada satu digit `>= 1` yang berkontribusi ke `sum`, sehingga tidak ada risiko **division by zero**

______________________________________________________________________

## 🔧 Kenapa `product` Diinisialisasi `1`, Bukan `0`?

Ini prinsip dasar identitas perkalian: kalau `product` mulai dari `0`, maka **perkalian pertama apapun** (`0 * digit`) akan selalu menghasilkan `0`, dan seterusnya seluruh hasil kali jadi `0` — merusak seluruh perhitungan. `1` adalah **identitas perkalian** (`1 * x = x` untuk semua `x`), sehingga digit pertama yang dikalikan ke `product` akan menghasilkan nilai digit itu sendiri, baru digit-digit berikutnya benar-benar mengalikan secara akumulatif. Ini beda dengan `sum`, yang identitas penjumlahannya memang `0` (`0 + x = x`), makanya `sum` aman diinisialisasi `0`.

______________________________________________________________________

## 🔧 Kenapa Butuh Variabel `temp` Terpisah dari `n`?

```java
int temp = n;
while (temp > 0) { ... temp /= 10; }
return (n % (sum + product)) == 0; // n asli masih dipakai di sini
```

Kalau langsung memodifikasi `n` di dalam loop ekstraksi digit (`n /= 10` alih-alih `temp /= 10`), begitu loop selesai, `n` akan jadi `0` — dan pengecekan akhir `n % (sum+product)` akan **selalu** menghasilkan `0` (karena `0 % apapun = 0`), membuat fungsi **selalu return `true`**, yang jelas salah. Variabel `temp` memastikan proses ekstraksi digit tidak "merusak" nilai `n` asli yang masih dibutuhkan di baris terakhir.

______________________________________________________________________

## 🔧 Alternatif: Konversi ke String

```java
public boolean checkDivisibility(int n) {
    int sum = 0, product = 1;
    for (char c : String.valueOf(n).toCharArray()) {
        int digit = c - '0';
        sum += digit;
        product *= digit;
    }
    return n % (sum + product) == 0;
}
```

Versi ini mengonversi `n` ke string lalu iterasi tiap karakter, mengonversi balik ke digit lewat `c - '0'`. Tidak perlu variabel `temp` terpisah karena `n` asli tidak pernah dimodifikasi (`String.valueOf(n)` membuat representasi baru, bukan mengubah `n`), tapi ada overhead tambahan dari konversi string.

| Approach | Time | Space | Butuh Variabel `temp`? |
| ----------------------------------- | ---- | ------------------------------ | ---------------------- |
| Ekstraksi digit numerik (kode asli) | O(d) | O(1) | Ya |
| Konversi ke String | O(d) | O(d) untuk representasi string | Tidak |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan dasar **ekstraksi digit numerik** yang sering jadi building block untuk soal-soal digit lain (seperti _Maximum Product of Two Digits_ atau _Largest Integer With Given Digit Sum_ yang sudah pernah dibahas). Dua pelajaran penting yang perlu diingat: pilih **identitas yang benar** untuk inisialisasi akumulator (`0` untuk penjumlahan, `1` untuk perkalian), dan **jangan memodifikasi variabel asli** yang masih dibutuhkan nanti — pakai salinan terpisah untuk proses yang bersifat destruktif seperti ekstraksi digit lewat pembagian berulang. 🎯

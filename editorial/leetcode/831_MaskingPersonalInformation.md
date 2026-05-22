# 831. Masking Personal Information

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/masking-personal-information/)
- **Solution**: [Code](../../leetcode/MaskingPersonalInformation.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` yang berisi **email** atau **nomor telepon**, masking informasinya:

**Email:**

- Format: `name@domain.com`
- Masked: `huruf_pertama + "*****" + huruf_terakhir_sebelum_@ + "@" + domain`
- Semua huruf lowercase

**Nomor Telepon:**

- Hapus semua non-digit
- 10 digit lokal: `"***-***-XXXX"`
- Dengan kode negara (11-13 digit): tambahkan prefix `"+*-"`, `"+**-"`, atau `"+***-"`

Contoh:

- `"LeetCode@LeetCode.com"` → `"l*****e@leetcode.com"`
- `"1(234)567-890"` → `"***-***-7890"`
- `"+86(10)12345678"` → `"+**-***-***-5678"`

______________________________________________________________________

## 💡 Intuition

Kunci pertama: **tentukan tipe input** (email atau telepon) dengan mengecek apakah ada `'@'`.

**Email**: gunakan `indexOf('@')` untuk menemukan posisi `@`, ambil huruf pertama dan huruf tepat sebelum `@`, sisanya disembunyikan.

**Telepon**: hapus semua non-digit dengan regex, lalu tentukan prefix kode negara berdasarkan panjang total digit (10, 11, 12, atau 13 digit).

______________________________________________________________________

## 🔍 Approach

**Email:**

1. `s.toLowerCase()` — semua jadi lowercase.
1. Ambil `s.charAt(0)` (huruf pertama) dan `s.substring(at - 1)` (huruf terakhir + "@" + domain).
1. Gabungkan: `huruf_pertama + "*****" + s.substring(at-1)`.

**Telepon:**

1. `s.replaceAll("[^0-9]", "")` — hapus semua bukan digit.
1. Tentukan prefix dari `country[s.length() - 10]`:
   - 10 digit → `country[0] = ""` (tidak ada kode negara)
   - 11 digit → `country[1] = "+*-"`
   - 12 digit → `country[2] = "+**-"`
   - 13 digit → `country[3] = "+***-"`
1. Gabungkan: `prefix + "***-***-" + 4 digit terakhir`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------ |
| **Time** | O(n) — indexOf, replaceAll, substring semua O(n) |
| **Space** | O(n) — string hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "LeetCode@LeetCode.com"`

`at = indexOf('@') = 8` → ada `@` → ini email

```
s = s.toLowerCase() = "leetcode@leetcode.com"
s.charAt(0) = 'l'
s.substring(at - 1) = s.substring(7) = "e@leetcode.com"
hasil = 'l' + "*****" + "e@leetcode.com"
      = "l*****e@leetcode.com"
```

\*\*Output: `"l\*\*\***e@leetcode.com"` ✅\*\*

______________________________________________________________________

**Input:** `s = "1(234)567-890"`

`at = indexOf('@') = -1` → tidak ada `@` → ini telepon

```
s.replaceAll("[^0-9]", "") = "1234567890"  (10 digit)
s.length() - 10 = 0 → country[0] = ""
s.substring(s.length() - 4) = "7890"
hasil = "" + "***-***-" + "7890"
      = "***-***-7890"
```

**Output: `"\***-**\*-7890"` ✅**

______________________________________________________________________

**Input:** `s = "+86(10)12345678"`

`at = -1` → telepon

```
s.replaceAll("[^0-9]", "") = "861012345678"  (12 digit)
s.length() - 10 = 2 → country[2] = "+**-"
s.substring(s.length() - 4) = "5678"
hasil = "+**-" + "***-***-" + "5678"
      = "+**-***-***-5678"
```

\*\*Output: `"+**-**_-_**-5678"` ✅\*\*

______________________________________________________________________

**Input:** `s = "ab@example.com"`

`at = 2` → email

```
s = "ab@example.com"
s.charAt(0) = 'a'
s.substring(at - 1) = s.substring(1) = "b@example.com"
hasil = "a*****b@example.com"
```

\*\*Output: `"a\*\*\***b@example.com"` ✅\*\*

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Email dengan nama satu huruf → tidak mungkin per constraints (minimal 2 huruf sebelum `@`)
- [ ] Telepon 10 digit → prefix kosong `""`
- [ ] Telepon 13 digit → prefix `"+***-"` (3 digit kode negara)

______________________________________________________________________

## 🔧 Penjelasan `country` Array dan Indexing

```java
String[] country = { "", "+*-", "+**-", "+***-" };
//                   [0]  [1]    [2]     [3]
// index:         10 digit 11d   12d     13d
```

Indeks dihitung dari `s.length() - 10`:

- 10 digit → `10 - 10 = 0` → `country[0] = ""`
- 11 digit → `11 - 10 = 1` → `country[1] = "+*-"`
- 12 digit → `12 - 10 = 2` → `country[2] = "+**-"`
- 13 digit → `13 - 10 = 3` → `country[3] = "+***-"`

Ini lebih elegan dari `if-else` bertumpuk.

______________________________________________________________________

## 🔧 Penjelasan `s.substring(at - 1)` untuk Email

```
s = "leetcode@leetcode.com"
         ↑ at = 8

s.substring(at - 1) = s.substring(7) = "e@leetcode.com"
                                         ↑ huruf terakhir sebelum @
```

`at - 1` menunjuk ke **karakter tepat sebelum `@`** — itulah huruf terakhir nama yang perlu ditampilkan. `substring(at - 1)` mengambil dari situ sampai akhir string → sudah termasuk `@` dan domain.

______________________________________________________________________

## 🔧 `"[^0-9]"` — Regex untuk Hapus Non-Digit

```java
s.replaceAll("[^0-9]", "")
```

`[^0-9]` artinya "karakter yang **bukan** digit 0-9". `replaceAll` mengganti semua yang cocok dengan `""` (string kosong) → menghapus semua non-digit sekaligus.

```
"1(234)567-890" → hapus (, ), - → "1234567890"
"+86(10)12345678" → hapus +, (, ), - → "861012345678"
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan **branching berdasarkan tipe input** dengan satu cek sederhana (`indexOf('@')`). Trik elegan di sini adalah array `country[]` yang menggunakan `length - 10` sebagai indeks langsung — menggantikan 4 kondisi `if-else` menjadi satu lookup O(1). `replaceAll("[^0-9]", "")` adalah idiom regex Java yang sangat berguna untuk membersihkan input dari karakter non-digit. 🎯

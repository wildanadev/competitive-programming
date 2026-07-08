# 415. Add Strings

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/add-strings/)
- **Solution**: [Code](../../leetcode/AddStrings.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string bilangan non-negatif `num1` dan `num2`, kembalikan **jumlahnya sebagai string**. Tidak boleh mengkonversi ke integer secara langsung (misal `Integer.parseInt`).

Contoh:

- `num1 = "11"`, `num2 = "123"` → `"134"`
- `num1 = "456"`, `num2 = "77"` → `"533"`
- `num1 = "0"`, `num2 = "0"` → `"0"`

______________________________________________________________________

## 💡 Intuition

Simulasikan **penjumlahan manual** seperti di sekolah dasar — mulai dari digit paling kanan (LSB), hitung `digit1 + digit2 + carry`, tulis digit hasilnya, bawa sisa ke posisi berikutnya.

```
  1 1
+ 1 2 3
-------
        indx1=1, indx2=2: 1+3+0=4, carry=0 → tulis 4
      indx1=0, indx2=1: 1+2+0=3, carry=0 → tulis 3
   indx1=-1, indx2=0: 0+1+0=1, carry=0 → tulis 1

sb = "431" → reverse → "134" ✅
```

______________________________________________________________________

## 🔍 Approach

### Simulasi Penjumlahan dari Kanan ke Kiri

1. Inisialisasi `indx1 = num1.length()-1`, `indx2 = num2.length()-1`, `carry = 0`.
1. Loop selama `indx1 >= 0` **atau** `indx2 >= 0` **atau** `carry != 0`:
   - Ambil digit dari kanan (atau `0` jika sudah habis).
   - `total = digit1 + digit2 + carry`
   - `carry = total / 10`, append `total % 10` ke StringBuilder.
   - `indx1--`, `indx2--`.
1. Reverse StringBuilder, return hasilnya.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(max(m,n)) — m=len(num1), n=len(num2) |
| **Space** | O(max(m,n)) — StringBuilder untuk hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `num1 = "456"`, `num2 = "77"`

`indx1=2, indx2=1, carry=0`

| Step | indx1 | indx2 | d1 | d2 | total | carry | sb |
| ---- | ----- | ----- | --- | --- | ----- | ----- | ----- |
| 1 | 2 | 1 | 6 | 7 | 13 | 1 | "3" |
| 2 | 1 | 0 | 5 | 7 | 13 | 1 | "33" |
| 3 | 0 | -1 | 4 | 0 | 5 | 0 | "335" |

`sb = "335"` → reverse → `"533"`

**Output: `"533"` ✅**

______________________________________________________________________

**Input:** `num1 = "9"`, `num2 = "99"`

`indx1=0, indx2=1, carry=0`

| Step | indx1 | indx2 | d1 | d2 | total | carry | sb |
| ---- | ----- | ----- | --- | --- | ----- | ----- | ----- |
| 1 | 0 | 1 | 9 | 9 | 18 | 1 | "8" |
| 2 | -1 | 0 | 0 | 9 | 10 | 1 | "80" |
| 3 | -2 | -1 | 0 | 0 | 1 | 0 | "801" |

`sb = "801"` → reverse → `"108"`

**Output: `"108"` ✅** — carry terus berlanjut meski kedua index habis

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Panjang berbeda → digit yang lebih pendek dianggap `0` saat indexnya habis
- [ ] Carry tersisa setelah keduanya habis → `|| carry != 0` memastikan carry tetap diproses
- [ ] `num1 = "0"`, `num2 = "0"` → total=0, carry=0, loop sekali → `"0"`
- [ ] Hasil lebih panjang dari keduanya (carry di akhir) → misal `"9"+"9"="18"`

______________________________________________________________________

## 🔧 Kenapa Tiga Kondisi di While Loop?

```java
while (indx1 >= 0 || indx2 >= 0 || carry != 0)
```

- `indx1 >= 0` → masih ada digit di `num1` yang belum diproses
- `indx2 >= 0` → masih ada digit di `num2` yang belum diproses
- `carry != 0` → masih ada sisa yang perlu ditulis (contoh: `"9"+"9"` → carry=1 di akhir → perlu tulis digit tambahan "1")

Tanpa kondisi ketiga, carry terakhir akan terlewat dan hasil akan salah:

```
"9" + "9" = 18
Tanpa carry check: "8" ← SALAH!
Dengan carry check: "18" ← BENAR
```

______________________________________________________________________

## 🔧 Kenapa `charAt(i) - '0'`?

```java
int digit1 = indx1 >= 0 ? num1.charAt(indx1) - '0' : 0;
```

`'0'` memiliki nilai ASCII `48`. Setiap karakter digit `'d'` punya nilai ASCII `48 + d`. Jadi:

```
'5' - '0' = 53 - 48 = 5  ✅
'9' - '0' = 57 - 48 = 9  ✅
```

Ini cara standar mengkonversi karakter digit ke nilai integer tanpa `Integer.parseInt`.

______________________________________________________________________

## 🔧 Kenapa StringBuilder di-Reverse di Akhir?

Kita append digit dari **kanan ke kiri** (LSB dulu), sehingga digit yang dihasilkan terbalik. Reverse di akhir mengembalikan urutan yang benar.

Alternatif: insert di depan setiap iterasi (`sb.insert(0, ...)`) — tapi ini O(n) per insert, total O(n²). Reverse di akhir jauh lebih efisien: O(n).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah simulasi **penjumlahan digit-per-digit** dari kanan ke kiri — teknik yang sama dipakai untuk _Add Binary_ (#67), _Multiply Strings_, dan _Add Two Numbers_ (linked list). Tiga pola penting: `charAt(i) - '0'` untuk ekstrak digit, `carry = total / 10` dan `total % 10` untuk hitung carry dan digit, serta kondisi `|| carry != 0` di loop untuk menangani carry akhir. 🎯

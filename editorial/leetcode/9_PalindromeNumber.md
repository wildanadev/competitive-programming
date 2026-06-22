# 9. Palindrome Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/palindrome-number/)
- **Solution**: [Code](../../leetcode/PalindromeNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `x`, return `true` jika `x` adalah **palindrome** (sama jika dibaca dari kiri ke kanan dan kanan ke kiri).

Contoh:

- `x = 121` → `true`
- `x = -121` → `false` (jadi "121-" jika dibaca terbalik, tidak sama)
- `x = 10` → `false`
- `x = 0` → `true` (satu digit selalu palindrome)

______________________________________________________________________

## 💡 Intuition

Balik digit `x` menggunakan teknik modulo dan pembagian, lalu bandingkan dengan nilai asli. Jika sama → palindrome.

```
x = 121
rev = 0

iterasi 1: rev = 0*10 + 121%10 = 1, x = 12
iterasi 2: rev = 1*10 + 12%10 = 12, x = 1
iterasi 3: rev = 12*10 + 1%10 = 121, x = 0

rev = 121 == x asli (121) → true ✅
```

______________________________________________________________________

## 🔍 Approach

### Reverse Digits + Compare

1. Jika `x < 0` → tidak mungkin palindrome (tanda minus tidak simetris) → return `false`.
1. Simpan `xcopy = x` untuk perbandingan nanti.
1. Balik digit `x` ke `rev` menggunakan modulo dan pembagian.
1. Return `rev == xcopy`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(d) — d = jumlah digit `x` |
| **Space** | O(1) — hanya variabel `rev` dan `xcopy` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `x = 121`

`xcopy = 121, rev = 0`

| x | x%10 | rev = rev\*10+x%10 | x = x/10 |
| --- | ---- | ------------------ | -------- |
| 121 | 1 | 0\*10+1=1 | 12 |
| 12 | 2 | 1\*10+2=12 | 1 |
| 1 | 1 | 12\*10+1=121 | 0 |

`x=0` → loop berhenti. `rev=121 == xcopy=121` → `true`

**Output: `true` ✅**

______________________________________________________________________

**Input:** `x = 10`

`xcopy = 10, rev = 0`

| x | x%10 | rev | x=x/10 |
| --- | ---- | --- | ------ |
| 10 | 0 | 0 | 1 |
| 1 | 1 | 1 | 0 |

`rev=1 != xcopy=10` → `false`

**Output: `false` ✅**

______________________________________________________________________

**Input:** `x = 0`

`x < 0`? ❌ → lanjut

Loop `while (x > 0)` → `0 > 0`? ❌ → loop tidak jalan sama sekali

`rev = 0 == xcopy = 0` → `true`

**Output: `true` ✅**

______________________________________________________________________

**Input:** `x = -121`

`x < 0`? ✅ → return `false` langsung

**Output: `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `x = 0` → palindrome (loop tidak jalan, rev tetap 0, sama dengan xcopy)
- [ ] `x < 0` → selalu `false` (tanda minus membuat tidak simetris)
- [ ] `x` dengan trailing zero seperti `10`, `100` → selalu `false` (digit pertama tidak boleh 0 kecuali x=0 itu sendiri)
- [ ] Palindrome ganjil seperti `12321` dan genap seperti `1221` → keduanya tertangani sama

______________________________________________________________________

## 🔧 Bug Umum: `x == 0` Dianggap Bukan Palindrome

```java
// SALAH — 0 seharusnya palindrome!
if (x < 0 || x == 0)
    return false;

// BENAR
if (x < 0)
    return false;
```

`0` dibaca dari kiri ke kanan tetap `0`, dan dari kanan ke kiri juga `0` — jelas palindrome. Kondisi `x == 0` yang ditambahkan ke pengecekan negatif adalah bug logika.

______________________________________________________________________

## 🔧 Kenapa `x < 0` Selalu `false`?

Jika dibaca terbalik, tanda minus akan pindah ke akhir:

```
-121 dibalik secara naif → "121-"
```

Tapi ini bukan representasi angka yang valid, dan secara konseptual, angka negatif **tidak pernah** dianggap palindrome dalam soal ini karena tidak ada bilangan negatif yang "simetris" termasuk tandanya.

______________________________________________________________________

## 🚀 Alternatif: Reverse Setengah Saja (Hindari Overflow)

Pendekatan di atas membalik **seluruh** angka — bisa overflow jika `x` mendekati `Integer.MAX_VALUE`. Solusi lebih aman: balik **setengah** angka saja dan bandingkan dengan setengah sisanya.

```java
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return x == rev || x == rev / 10;  // x==rev/10 untuk digit ganjil
    }
}
```

`x % 10 == 0 && x != 0` → angka berakhiran `0` (kecuali `0` itu sendiri) tidak mungkin palindrome (digit pertama tidak akan pernah `0`).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini sederhana tapi punya **bug klasik**: lupa bahwa `0` adalah palindrome yang valid. Pendekatan reverse-and-compare adalah O(d) yang sudah cukup efisien. Untuk menghindari overflow pada angka besar, pendekatan **reverse half** (balik hanya setengah digit) adalah optimasi standar yang sering ditanyakan sebagai follow-up. 🎯

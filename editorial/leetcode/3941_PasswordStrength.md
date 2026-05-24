# 3931. Password Strength

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/password-strength/)
- **Solution**: [Code](../../leetcode/PasswordStrength.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `password`, hitung **strength** (kekuatan) password berdasarkan karakter **unik** yang dikandungnya:

- Huruf kecil (`a-z`) → +1 per karakter unik
- Huruf besar (`A-Z`) → +2 per karakter unik
- Digit (`0-9`) → +3 per karakter unik
- Simbol khusus (`!`, `@`, `#`, `$`) → +5 per karakter unik

Setiap karakter dihitung **hanya sekali** meskipun muncul berkali-kali.

Contoh:

- `password = "aA1!"` → `1+2+3+5 = 11`
- `password = "aaaaAAAA"` → `1+2 = 3` (hanya `a` dan `A` yang unik)

______________________________________________________________________

## 💡 Intuition

Karena setiap karakter dihitung **hanya sekali**, gunakan **HashSet** untuk menyimpan karakter unik. Kemudian hitung skor dari setiap karakter unik berdasarkan tipenya.

______________________________________________________________________

## 🔍 Approach

### HashSet + Scoring

1. Masukkan semua karakter `password` ke `HashSet` → otomatis deduplikasi.
1. Loop setiap karakter unik di set:
   - Digit → `+3`
   - Simbol (`!`,`@`,`#`,`$`) → `+5`
   - Huruf besar → `+2`
   - Huruf kecil → `+1`
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------- |
| **Time** | O(n) — satu pass build HashSet + iterasi set O(m) di mana m = karakter unik |
| **Space** | O(m) — HashSet menyimpan karakter unik (maksimal 62 karakter berbeda) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `password = "aA1!"`

**Build HashSet:** `{a, A, 1, !}`

**Hitung skor:**

| char | tipe | skor | ans |
| ---- | ----------- | ---- | --- |
| a | huruf kecil | +1 | 1 |
| A | huruf besar | +2 | 3 |
| 1 | digit | +3 | 6 |
| ! | simbol | +5 | 11 |

**Output: `11` ✅**

______________________________________________________________________

**Input:** `password = "aaaaAAAA"`

**Build HashSet:** `{a, A}` ← duplikat otomatis dihapus

**Hitung skor:**

| char | tipe | skor | ans |
| ---- | ----------- | ---- | --- |
| a | huruf kecil | +1 | 1 |
| A | huruf besar | +2 | 3 |

**Output: `3` ✅**

______________________________________________________________________

**Input:** `password = "aB3$aB3$"`

**Build HashSet:** `{a, B, 3, $}` ← semua duplikat dihapus

| char | skor | ans |
| ---- | ---- | --- |
| a | +1 | 1 |
| B | +2 | 3 |
| 3 | +3 | 6 |
| $ | +5 | 11 |

**Output: `11` ✅** — sama dengan `"aB3$"` karena duplikat tidak dihitung

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua karakter sama → HashSet hanya berisi satu karakter → skor minimal
- [ ] Hanya simbol → setiap simbol unik `+5`
- [ ] Password panjang dengan banyak duplikat → hanya karakter unik yang dihitung

______________________________________________________________________

## 🔧 Kenapa Pakai HashSet, Bukan Loop Langsung?

**Tanpa HashSet** — perlu tracking manual:

```java
// Harus tandai karakter yang sudah dihitung → lebih rumit
boolean[] seen = new boolean[128];
for (char ch : password.toCharArray()) {
    if (!seen[ch]) {
        seen[ch] = true;
        // hitung skor
    }
}
```

**Dengan HashSet** — deduplikasi otomatis:

```java
// HashSet langsung handle duplikat
for (char ch : password.toCharArray()) set.add(ch);
// tinggal iterate set yang sudah unik
```

Keduanya ekuivalen, tapi HashSet lebih ekspresif dan bersih.

______________________________________________________________________

## 🔧 Urutan Pengecekan Kondisi

```java
if (Character.isDigit(ch)) ans += 3;
else if (ch == '!' || ch == '@' || ch == '#' || ch == '$') ans += 5;
else {
    if (Character.isUpperCase(ch)) ans += 2;
    else ans++;
}
```

Urutan ini penting karena `else` di akhir hanya dieksekusi jika bukan digit dan bukan simbol. Pada saat itu kita tahu karakter pasti huruf → cukup cek apakah uppercase atau tidak.

Alternatif yang lebih ringkas dengan `switch` (Java 14+):

```java
for (char ch : set) {
    ans += Character.isDigit(ch) ? 3
         : "!@#$".indexOf(ch) >= 0 ? 5
         : Character.isUpperCase(ch) ? 2
         : 1;
}
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh **counting dengan deduplikasi** — HashSet menangani keunikan secara otomatis sehingga kita tidak perlu tracking manual. Struktur `if-else if-else` dengan nested `if` di dalamnya memastikan setiap karakter masuk ke tepat satu kategori. Pola "masukkan ke set, lalu proses set" sangat umum untuk soal yang butuh menghitung berdasarkan elemen unik. 🎯

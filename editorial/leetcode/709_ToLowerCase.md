# 709. To Lower Case

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/to-lower-case/)
- **Solution**: [Code](../../leetcode/ToLowerCase.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, kembalikan versi lowercase (huruf kecil) dari string tersebut.

Contoh:

- `s = "Hello"` → `"hello"`
- `s = "here"` → `"here"`
- `s = "LOVELY"` → `"lovely"`

______________________________________________________________________

## 💡 Intuition

Soal ini sebenarnya soal **latihan implementasi manual** — tujuannya bukan cari solusi tercepat, tapi memahami **bagaimana konversi upper-ke-lowercase bekerja di level karakter/ASCII**. Java sudah menyediakan `String.toLowerCase()` bawaan yang langsung menyelesaikan soal ini dalam satu baris, tapi nilai edukatifnya justru ada di memahami **kenapa** dan **bagaimana** metode itu bekerja di balik layar.

Intinya: huruf besar (`'A'`–`'Z'`) dan huruf kecil (`'a'`–`'z'`) di tabel ASCII punya **selisih tetap** sebesar `32` (`'a' - 'A' = 97 - 65 = 32`). Jadi untuk mengubah huruf besar jadi kecil, cukup **tambahkan 32** ke nilai ASCII-nya (atau setara: set bit ke-6 dari kanan, karena `32 = 0b100000`). Karakter yang **bukan** huruf besar (sudah lowercase, angka, simbol) dibiarkan apa adanya.

______________________________________________________________________

## 🔍 Approach

### Bawaan Java — `String.toLowerCase()`

Solusi paling langsung: panggil method bawaan `String.toLowerCase()`, yang secara internal melakukan iterasi per karakter dan mengonversi tiap huruf besar ke huruf kecil sesuai aturan locale (untuk kasus ASCII sederhana seperti soal ini, hasilnya sama dengan pendekatan manual di bawah).

```java
return s.toLowerCase();
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------------- |
| **Time** | O(n) — n = panjang string, method ini tetap iterasi tiap karakter secara internal |
| **Space** | O(n) — string di Java immutable, hasilnya string baru |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "Hello"`

- `s.toLowerCase()` mengonversi tiap huruf besar (`H`) jadi kecil (`h`), huruf yang sudah kecil (`e`, `l`, `l`, `o`) dibiarkan.

**Output: `"hello"`** ✅

______________________________________________________________________

**Input:** `s = "here"`

- Semua karakter sudah lowercase, tidak ada yang berubah.

**Output: `"here"`** ✅

______________________________________________________________________

**Input:** `s = "LOVELY"`

- Semua huruf besar dikonversi jadi kecil.

**Output: `"lovely"`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String kosong (`s = ""`) → hasilnya tetap `""`
- [ ] String sudah full lowercase → tidak ada perubahan sama sekali
- [ ] String campuran huruf besar-kecil (`"HeLLo"`) → hanya huruf besar yang dikonversi, huruf kecil tetap
- [ ] Karakter non-alfabet (angka, simbol, spasi) → dibiarkan apa adanya, tidak ikut terkonversi

______________________________________________________________________

## 🔧 Implementasi Manual — Memanfaatkan Selisih ASCII

```java
public String toLowerCase(String s) {
    char[] chars = s.toCharArray();
    for (int i = 0; i < chars.length; i++) {
        if (chars[i] >= 'A' && chars[i] <= 'Z') {
            chars[i] += 32; // atau: chars[i] = (char) (chars[i] + ('a' - 'A'));
        }
    }
    return new String(chars);
}
```

**Cara kerjanya:**

1. Cek apakah karakter berada di rentang huruf besar (`'A'` sampai `'Z'`, kode ASCII `65`–`90`).
1. Kalau ya, tambahkan `32` ke nilai ASCII-nya — otomatis mendarat di rentang huruf kecil yang bersesuaian (`'a'` sampai `'z'`, kode ASCII `97`–`122`).
1. Karakter lain (sudah lowercase, angka, simbol) dilewati tanpa perubahan.

Ini persis logika yang dijalankan `toLowerCase()` bawaan Java untuk karakter ASCII biasa, hanya saja ditulis eksplisit.

______________________________________________________________________

## 🔧 Implementasi Manual — Pakai Bitwise OR

```java
public String toLowerCase(String s) {
    char[] chars = s.toCharArray();
    for (int i = 0; i < chars.length; i++) {
        if (chars[i] >= 'A' && chars[i] <= 'Z') {
            chars[i] |= 0b100000; // set bit ke-6 (nilai 32)
        }
    }
    return new String(chars);
}
```

Karena selisih `32` itu sebenarnya cuma **satu bit** (`0b100000` dalam biner) yang membedakan huruf besar dan huruf kecil di tabel ASCII, operasi `|= 32` (bitwise OR) punya efek yang sama persis dengan `+= 32` untuk kasus ini — tapi secara konsep menegaskan bahwa perbedaan besar/kecil huruf di ASCII itu murni soal **satu bit yang di-set atau tidak**.

| Approach | Time | Space | Menjelaskan Mekanisme ASCII? |
| ---------------------------------- | ----- | ----- | ---------------------------- | ----------------------------------------- |
| `String.toLowerCase()` (kode asli) | O(n) | O(n) | Tidak (black box) |
| Manual dengan `+= 32` | O(n) | O(n) | Ya |
| Manual dengan bitwise `            | = 32` | O(n) | O(n) | Ya, lebih eksplisit soal representasi bit |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini kelihatan trivial kalau langsung pakai method bawaan, tapi nilai sebenarnya ada di memahami **representasi karakter di level ASCII** — bahwa huruf besar dan kecil punya selisih kode tetap (`32`), yang secara bit hanya beda satu posisi. Pemahaman ini berguna di luar soal ini juga, misalnya untuk soal-soal manipulasi string tingkat karakter seperti _Caesar Cipher_, _ROT13_, atau soal-soal yang meminta implementasi fungsi string built-in dari nol (`strStr`, `atoi`, dst) — di mana memahami representasi ASCII/byte di baliknya jadi kunci utama. 🎯

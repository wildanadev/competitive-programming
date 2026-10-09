# 917. Reverse Only Letters

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Two Pointers, String
- **Link**: [Problem](https://leetcode.com/problems/reverse-only-letters/)
- **Solution**: [Code](../../leetcode/ReverseOnlyLetters.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`. Balik urutan **hanya huruf-hurufnya** (huruf besar dan kecil), sementara semua karakter **non-huruf** tetap berada di **posisi aslinya**. Kembalikan string hasilnya.

Contoh:

- `s = "ab-cd"` → `"dc-ba"` (`-` tetap di indeks `2`, huruf `a,b,c,d` dibalik jadi `d,c,b,a` dan mengisi slot huruf)
- `s = "a-bC-dEf-ghIj"` → `"j-Ih-gfE-dCba"`

______________________________________________________________________

## 💡 Intuition

Membalik seluruh string itu mudah (two pointers dari kedua ujung, tukar). Yang bikin soal ini menarik: karakter non-huruf **tidak boleh pindah**. Idenya, tetap pakai pola two pointers, tapi **lewati** karakter non-huruf di kedua ujung sebelum menukar:

- Pointer `l` maju dari kiri sampai ketemu **huruf**.
- Pointer `r` mundur dari kanan sampai ketemu **huruf**.
- Tukar kedua huruf itu, lalu lanjutkan ke dalam.

Dengan begini, slot non-huruf tidak pernah tersentuh (pointer hanya "melompatinya"), dan huruf-huruf saling bertukar dari luar ke dalam, yang secara efektif **membalik urutan huruf** di dalam kerangka posisi yang sama.

Karena `String` di Java **immutable**, kita salin dulu ke `char[]` supaya bisa ditukar langsung di tempat (in-place).

______________________________________________________________________

## 🔍 Approach

### Two Pointers dengan Skip Non-Huruf

1. Salin `s` ke `char[] letters`. Set `l = 0`, `r = s.length() - 1`.
1. Selama `l < r`:
   - Majukan `l` selama `l < r` dan `letters[l]` **bukan huruf**.
   - Mundurkan `r` selama `l < r` dan `letters[r]` **bukan huruf**.
   - Tukar `letters[l]` dan `letters[r]`.
   - `l++`, `r--`.
1. Kembalikan `new String(letters)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n) — tiap pointer paling banyak menyapu string satu kali |
| **Space** | O(n) — untuk `char[]` salinan (string di Java immutable) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "ab-cd"` (indeks `0..4`)

| Langkah | l | r | Aksi | Array sesudah |
| ------- | --- | --- | ---------------------------- | ------------- |
| 1 | 0 | 4 | `a` huruf, `d` huruf → tukar | `d b - c a` |
| 2 | 1 | 3 | `b` huruf, `c` huruf → tukar | `d c - b a` |
| 3 | 2 | 2 | `l < r` salah, loop berhenti | `d c - b a` |

**Output: `"dc-ba"`** ✅

______________________________________________________________________

**Input:** `s = "a-bC-dEf-ghIj"` (indeks `0..12`)

| Langkah | l | r | Aksi | Array sesudah |
| ------- | --- | --- | -------------------------------------------------------------------- | --------------------------- |
| 1 | 0 | 12 | `a` dan `j` sama-sama huruf → tukar | `j - b C - d E f - g h I a` |
| 2 | 1 | 11 | `l` melewati `-` ke indeks `2` (`b`); `r` di `I` → tukar `b` dan `I` | `j - I C - d E f - g h b a` |
| 3 | 3 | 10 | `C` dan `h` → tukar | `j - I h - d E f - g C b a` |
| 4 | 4 | 9 | `l` melewati `-` ke indeks `5` (`d`); `r` di `g` → tukar | `j - I h - g E f - d C b a` |
| 5 | 6 | 8 | `l` di `E`; `r` melewati `-` ke indeks `7` (`f`) → tukar `E` dan `f` | `j - I h - g f E - d C b a` |
| 6 | 7 | 6 | `l < r` salah, loop berhenti | `j - I h - g f E - d C b a` |

**Output: `"j-Ih-gfE-dCba"`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada huruf sama sekali (misal `"123-!"`) → kedua pointer saling mendekat tanpa pernah menemukan huruf, string tidak berubah
- [ ] Semua karakter huruf (`"abcd"`) → perilakunya sama dengan membalik string biasa (`"dcba"`)
- [ ] Hanya satu karakter → `l < r` salah sejak awal, loop tidak jalan
- [ ] Pointer bertemu di karakter non-huruf di tengah (seperti indeks `2` pada `"ab-cd"`) → `l == r`, terjadi penukaran elemen dengan dirinya sendiri atau loop langsung berhenti; keduanya tidak berbahaya
- [ ] Huruf besar dan kecil campur (`"Ab-Cd"`) → `Character.isLetter` menganggap keduanya huruf, kapitalisasi ikut berpindah bersama hurufnya (`"dC-bA"`)

______________________________________________________________________

## 🔧 Catatan: `&` Bukan `&&` di Kode Ini

```java
while (l < r & !Character.isLetter(letters[l]))
    l++;
```

Kode ini memakai operator `&` (bitwise AND yang juga valid untuk boolean), **bukan** `&&` (logical AND). Perbedaannya penting: `&&` bersifat **short-circuit** (kalau sisi kiri `false`, sisi kanan tidak dievaluasi), sedangkan `&` **selalu** mengevaluasi **kedua** sisi.

Untuk soal ini hasilnya tetap benar, karena indeks `l` dan `r` **tidak pernah keluar batas** array (`l` hanya naik selagi `l < r`, jadi `l <= r <= n-1`; `r` hanya turun selagi `l < r`, jadi `r >= l >= 0`), sehingga mengakses `letters[l]` atau `letters[r]` di sisi kanan tetap aman walaupun sisi kiri sudah `false`.

Tapi secara idiom, `&&` hampir selalu lebih baik: ia mengekspresikan maksud "cek batas dulu, baru akses array" dan tidak akan rusak kalau suatu saat kondisinya diubah sehingga akses array di sisi kanan bisa keluar batas. Mengandalkan `&` supaya aman adalah kebiasaan yang rapuh.

______________________________________________________________________

## 🔧 Alternatif: Kumpulkan Huruf, Balik, Lalu Isi Ulang

```java
public String reverseOnlyLetters(String s) {
    StringBuilder letters = new StringBuilder();
    for (char c : s.toCharArray())
        if (Character.isLetter(c)) letters.append(c);
    letters.reverse();

    StringBuilder ans = new StringBuilder();
    int idx = 0;
    for (char c : s.toCharArray())
        ans.append(Character.isLetter(c) ? letters.charAt(idx++) : c);
    return ans.toString();
}
```

Versi dua tahap: (1) kumpulkan semua huruf lalu balik, (2) susun ulang string dengan mengisi tiap slot huruf memakai huruf dari daftar terbalik secara berurutan, sementara slot non-huruf disalin apa adanya. Lebih mudah dijelaskan, tapi memakai memori tambahan dan dua pass.

| Approach | Time | Space | Catatan |
| --------------------------------- | ---- | ----- | ------------------------------------- |
| Two pointers in-place (kode asli) | O(n) | O(n) | Satu pass, tukar langsung di `char[]` |
| Kumpulkan, balik, isi ulang | O(n) | O(n) | Dua pass, lebih mudah dibayangkan |

Keduanya `O(n)`, jadi pilihannya soal selera dan keterbacaan.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi dari **two pointers dari kedua ujung** (seperti membalik string atau mengecek palindrome) dengan tambahan aturan **lewati elemen yang tidak relevan**. Polanya: sebelum menukar atau membandingkan, geser dulu tiap pointer sampai menemukan elemen yang memenuhi syarat. Pola ini juga muncul di _Valid Palindrome_ (lewati karakter non-alfanumerik) dan _Reverse Vowels of a String_ (hanya tukar huruf vokal). Jangan lupa menjaga `l < r` di dalam loop skip supaya pointer tidak saling melewati. 🎯

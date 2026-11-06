# 1221. Split a String in Balanced Strings

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Greedy, Counting
- **Link**: [Problem](https://leetcode.com/problems/split-a-string-in-balanced-strings/)
- **Solution**: [Code](../../leetcode/SplitAStringInBalancedStrings.java)

______________________________________________________________________

## 📄 Problem Summary

String **seimbang (balanced)** adalah string yang jumlah karakter `'L'` dan `'R'`-nya **sama**. Diberikan string seimbang `s`, pecah `s` menjadi **substring-substring berurutan** yang masing-masing seimbang, dan kembalikan **jumlah maksimum** substring yang bisa didapat.

Contoh:

- `s = "RLRRLLRLRL"` → `4` (`"RL"`, `"RRLL"`, `"RL"`, `"RL"`)
- `s = "RLRRRLLRLL"` → `2` (`"RL"`, `"RRRLLRLL"`)
- `s = "LLLLRRRR"` → `1` (tidak bisa dipecah lagi, seluruh string adalah satu bagian seimbang)

______________________________________________________________________

## 💡 Intuition

Kita ingin memotong `s` sebanyak mungkin, dan tiap potongan harus seimbang. Strategi **greedy** yang alami: **potong secepat mungkin**, yaitu begitu bagian yang sedang dibaca sudah seimbang.

Untuk mengetahui kapan bagian itu seimbang, cukup lacak satu bilangan: **selisih** `jumlah R - jumlah L` sejak potongan terakhir. Tambah `1` untuk tiap `'R'`, kurangi `1` untuk tiap `'L'`. Begitu selisih **kembali ke `0`**, bagian yang dibaca sejak potongan terakhir seimbang, jadi kita bisa memotong di situ (`ans++`). Selisihnya tidak perlu di-reset, karena ia sudah `0` tepat setelah potongan.

**Kenapa potong-secepatnya itu optimal?** Setiap titik potong dalam pemecahan yang valid harus berada di posisi di mana **seluruh prefix sebelumnya seimbang** (prefix itu gabungan potongan-potongan seimbang, jadi total `R` dan `L`-nya sama). Artinya titik potong hanya mungkin di posisi di mana selisih kumulatif bernilai `0`. Jumlah potongan paling banyak adalah jumlah posisi seperti itu, dan memotong di **semua** posisi itu benar-benar menghasilkan potongan seimbang semua (selisih di antara dua titik `0` berurutan adalah `0`). Jadi jawabannya persis **jumlah kali selisih kumulatif bernilai `0`**.

______________________________________________________________________

## 🔍 Approach

### Counter Selisih R dan L, Hitung Kemunculan Nol

1. `freq = 0` (selisih kumulatif), `ans = 0`.
1. Untuk tiap karakter `c` di `s`:
   - Kalau `c == 'R'` → `freq++`, selain itu (`'L'`) → `freq--`.
   - Kalau `freq == 0` → `ans++` (satu potongan seimbang selesai).
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------- |
| **Time** | O(n) — satu kali pass |
| **Space** | O(1) — hanya dua variabel integer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "RLRRLLRLRL"`

| Indeks | Karakter | freq | Potongan selesai? | ans |
| ------ | -------- | ---- | ----------------- | --- |
| 0 | R | 1 | tidak | 0 |
| 1 | L | 0 | ya (`"RL"`) | 1 |
| 2 | R | 1 | tidak | 1 |
| 3 | R | 2 | tidak | 1 |
| 4 | L | 1 | tidak | 1 |
| 5 | L | 0 | ya (`"RRLL"`) | 2 |
| 6 | R | 1 | tidak | 2 |
| 7 | L | 0 | ya (`"RL"`) | 3 |
| 8 | R | 1 | tidak | 3 |
| 9 | L | 0 | ya (`"RL"`) | 4 |

**Output: `4`** ✅

______________________________________________________________________

**Input:** `s = "RLRRRLLRLL"`

| Indeks | Karakter | freq | Potongan selesai? | ans |
| ------ | -------- | ---- | ----------------- | --- |
| 0 | R | 1 | tidak | 0 |
| 1 | L | 0 | ya (`"RL"`) | 1 |
| 2 | R | 1 | tidak | 1 |
| 3 | R | 2 | tidak | 1 |
| 4 | R | 3 | tidak | 1 |
| 5 | L | 2 | tidak | 1 |
| 6 | L | 1 | tidak | 1 |
| 7 | R | 2 | tidak | 1 |
| 8 | L | 1 | tidak | 1 |
| 9 | L | 0 | ya (`"RRRLLRLL"`) | 2 |

**Output: `2`** ✅

______________________________________________________________________

**Input:** `s = "LLLLRRRR"`

`freq`: `-1, -2, -3, -4, -3, -2, -1, 0`. Hanya di akhir `freq == 0`.

**Output: `1`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `"RL"` atau `"LR"` → satu potongan, hasil `1`
- [ ] `"RRLL"` (bersarang) → `freq` naik ke `2` lalu turun ke `0`, hasil `1`
- [ ] `"RLRLRL"` (bergantian) → `freq` kembali ke `0` setiap dua karakter, hasil `n / 2`, yaitu potongan sebanyak mungkin
- [ ] String yang prefix-nya tidak pernah seimbang sebelum akhir (`"LLLRRR"`) → satu potongan saja (seluruh string)
- [ ] Selisih negatif di tengah jalan (mulai dengan `'L'`) → tidak masalah, yang dicek hanya apakah nilainya kembali ke `0`
- [ ] Soal menjamin `s` seimbang secara keseluruhan, jadi `freq` selalu berakhir di `0` dan potongan terakhir selalu tertutup. Kalau input boleh tidak seimbang, bagian ekor yang belum seimbang tidak terhitung sebagai potongan

______________________________________________________________________

## 🔧 Catatan: `else` Menganggap Semua Selain `'R'` sebagai `'L'`

Baris `if (s.charAt(i) == 'R') freq++; else freq--;` memperlakukan karakter apa pun selain `'R'` sebagai `'L'`. Itu aman karena constraint menjamin hanya ada `'L'` dan `'R'`. Kalau input bisa memuat karakter lain, sebaiknya `else if (s.charAt(i) == 'L')` agar karakter asing tidak ikut mengubah hitungan.

______________________________________________________________________

## 🔧 Alternatif: Dua Counter Terpisah

```java
public int balancedStringSplit(String s) {
    int l = 0, r = 0, ans = 0;
    for (char c : s.toCharArray()) {
        if (c == 'L') l++;
        else r++;
        if (l == r) ans++;
    }
    return ans;
}
```

Versi ini menghitung jumlah `'L'` dan `'R'` secara terpisah, lalu memotong saat jumlahnya sama. Secara matematis setara dengan satu counter selisih (`l == r` sama dengan `r - l == 0`), tapi memakai satu variabel tambahan. Satu counter selisih sedikit lebih ringkas.

| Approach | Time | Space | Catatan |
| ------------------------------- | ---- | ----- | ------------------------------- |
| Satu counter selisih (kode asli) | O(n) | O(1) | Paling ringkas |
| Dua counter `l` dan `r` | O(n) | O(1) | Lebih eksplisit soal "jumlah sama" |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh **greedy dengan balance counter**: ubah syarat "jumlah `L` dan `R` sama" menjadi satu bilangan selisih yang naik dan turun, lalu hitung **berapa kali bilangan itu kembali ke nol**. Pola counter ini sama dengan yang dipakai di *Maximum Nesting Depth of the Parentheses*, *Valid Parentheses*, dan *Remove Outermost Parentheses*: tiap kali sesuatu "dibuka" nilainya naik dan tiap kali "ditutup" nilainya turun, dan titik di mana nilainya kembali ke nol menandai batas bagian yang seimbang. 🎯
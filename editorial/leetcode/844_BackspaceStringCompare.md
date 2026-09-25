# 844. Backspace String Compare

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Stack, Two Pointers, String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/backspace-string-compare/)
- **Solution**: [Code](../../leetcode/BackspaceStringCompare.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `s` dan `t`, di mana karakter `'#'` berarti **backspace** (hapus karakter sebelumnya). Kembalikan `true` kalau **hasil akhir** kedua string (setelah semua backspace diproses) **sama persis**.

Contoh:

- `s = "ab#c", t = "ad#c"` → `true` (`"ab#c"` → hapus `'b'` → `"ac"`; `"ad#c"` → hapus `'d'` → `"ac"`; sama)
- `s = "ab##", t = "c#d#"` → `true` (keduanya jadi string kosong `""`)
- `s = "a#c", t = "b"` → `false` (`"a#c"` → `"c"`; `"b"` tetap `"b"`; beda)

______________________________________________________________________

## 💡 Intuition

Soal ini adalah aplikasi klasik pola **stack**: `'#'` berarti "pop" (hapus elemen paling atas), karakter biasa berarti "push" (tambahkan ke atas). Ini persis perilaku **undo terakhir** — backspace selalu menghapus karakter yang **paling baru** ditambahkan, bukan sembarang posisi.

Strategi paling langsung: **simulasikan** proses ini untuk **kedua** string secara independen, hasilkan string "final" masing-masing (setelah semua backspace diterapkan), lalu **bandingkan** hasilnya.

Solusi ini memakai `StringBuilder` sebagai **stack implisit**: `append` untuk push (tambah karakter ke belakang), `deleteCharAt(length-1)` untuk pop (hapus karakter terakhir) — `StringBuilder` memang cocok dipakai sebagai stack karena operasi tambah/hapus di **akhir** string sama-sama `O(1)` amortized.

______________________________________________________________________

## 🔍 Approach

### Simulasi Stack dengan StringBuilder untuk Kedua String

1. Untuk **tiap** string (`s` dan `t`, diproses terpisah dengan logika yang sama):
   - Siapkan `StringBuilder` kosong.
   - Loop tiap karakter:
     - Kalau `'#'` → kalau `StringBuilder` **tidak kosong**, hapus karakter terakhirnya (`deleteCharAt`). Kalau kosong, abaikan saja (backspace pada string kosong tidak melakukan apa-apa, tidak error).
     - Kalau bukan `'#'` → tambahkan karakter itu ke `StringBuilder`.
1. Bandingkan **hasil akhir** kedua `StringBuilder` (dikonversi ke `String`) lewat `.equals()`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------------------------- |
| **Time** | O(m + n) — m = panjang `s`, n = panjang `t`, satu pass untuk tiap string, masing-masing independen |
| **Space** | O(m + n) — untuk `StringBuilder` hasil, di kasus terburuk (tidak ada backspace sama sekali) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "ab#c", t = "ad#c"`

**Proses `s`:**

| char | Aksi | sTemp |
| ---- | --------------- | ----- |
| a | push | "a" |
| b | push | "ab" |
| # | pop (hapus 'b') | "a" |
| c | push | "ac" |

**Proses `t`:**

| char | Aksi | tTemp |
| ---- | --------------- | ----- |
| a | push | "a" |
| d | push | "ad" |
| # | pop (hapus 'd') | "a" |
| c | push | "ac" |

`sTemp = "ac"`, `tTemp = "ac"` → sama.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `s = "ab##", t = "c#d#"`

**Proses `s`:** `a`→`"a"`; `b`→`"ab"`; `#`→pop→`"a"`; `#`→pop→`""`.

**Proses `t`:** `c`→`"c"`; `#`→pop→`""`; `d`→`"d"`; `#`→pop→`""`.

`sTemp = ""`, `tTemp = ""` → sama.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `s = "a#c", t = "b"`

**Proses `s`:** `a`→`"a"`; `#`→pop→`""`; `c`→`"c"`.

**Proses `t`:** `b`→`"b"`.

`sTemp = "c"`, `tTemp = "b"` → beda.

**Output: `false`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `'#'` muncul saat `StringBuilder` **kosong** (backspace berlebih, misal `"###"` atau `"#a"`) → diabaikan begitu saja lewat pengecekan `if (sTemp.length() > 0)`, tidak melempar exception
- [ ] Kedua string sama persis tanpa `'#'` sama sekali → langsung sama setelah diproses, tidak ada perubahan
- [ ] String kosong (`s=""` atau `t=""`) → loop tidak pernah jalan untuk string itu, hasilnya tetap `""`
- [ ] Backspace beruntun menghapus **semua** karakter (`"ab##"` → `""`) → tertangani normal, `StringBuilder` bisa kosong sepenuhnya
- [ ] Panjang `s` dan `t` berbeda, tapi hasil akhirnya (setelah backspace) sama → tetap `true`, karena yang dibandingkan adalah **hasil akhir**, bukan panjang string asli

______________________________________________________________________

## 🔧 Kenapa `StringBuilder` Cocok Dipakai sebagai Stack di Sini?

`StringBuilder` di Java sebenarnya adalah **mutable character array** dengan resizing otomatis — operasi `append` (tambah di akhir) dan `deleteCharAt(length-1)` (hapus dari akhir) **keduanya** `O(1)` amortized, persis seperti operasi `push`/`pop` pada stack sungguhan. Karena backspace **selalu** menghapus karakter **paling akhir yang ditambahkan** (bukan posisi sembarang), sifat **LIFO (Last In First Out)** dari stack **persis** cocok dengan perilaku yang dibutuhkan — tidak perlu struktur data `Stack`/`Deque` eksplisit, `StringBuilder` sudah cukup sekaligus langsung menghasilkan representasi string yang bisa dibandingkan di akhir.

______________________________________________________________________

## 🔧 Alternatif: Two Pointers dari Belakang — O(1) Space Tambahan

```java
public boolean backspaceCompare(String s, String t) {
    int i = s.length() - 1, j = t.length() - 1;
    int skipS = 0, skipT = 0;

    while (i >= 0 || j >= 0) {
        while (i >= 0) {
            if (s.charAt(i) == '#') { skipS++; i--; }
            else if (skipS > 0) { skipS--; i--; }
            else break;
        }
        while (j >= 0) {
            if (t.charAt(j) == '#') { skipT++; j--; }
            else if (skipT > 0) { skipT--; j--; }
            else break;
        }
        if (i >= 0 && j >= 0) {
            if (s.charAt(i) != t.charAt(j)) return false;
        } else if (i >= 0 || j >= 0) {
            return false; // salah satu masih punya karakter tersisa, yang lain sudah habis
        }
        i--; j--;
    }
    return true;
}
```

Versi ini memproses **dari belakang** ke depan tanpa membangun string hasil sama sekali — melacak berapa banyak "hutang backspace" (`skipS`/`skipT`) yang perlu dilewati saat mundur. Ini menurunkan **space tambahan** dari `O(m+n)` jadi `O(1)` (di luar input), karena tidak perlu menyimpan representasi string hasil akhir — cukup bandingkan karakter demi karakter langsung dari string asli.

| Approach | Time | Space Tambahan |
| --------------------------------------- | ------ | -------------- |
| StringBuilder sebagai stack (kode asli) | O(m+n) | O(m+n) |
| Two pointers dari belakang | O(m+n) | O(1) |

Untuk constraint soal ini (panjang `s` dan `t` maksimal `200`), perbedaan performanya nyaris tidak terasa — tapi pendekatan two pointers jadi pilihan menarik kalau soal lanjutan (versi "follow-up" resmi di LeetCode) meminta solusi `O(1)` extra space secara eksplisit.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang baik untuk pola **stack tersembunyi** — meski soal tidak secara eksplisit menyebut "stack", perilaku backspace (hapus yang paling baru ditambahkan) adalah definisi persis dari LIFO. `StringBuilder` sering jadi pilihan praktis untuk mensimulasikan stack berbasis karakter di Java, karena `append`/`deleteCharAt(length-1)` sudah cukup tanpa perlu struktur data stack eksplisit. Soal ini juga membuka diskusi trade-off ruang: solusi stack yang jelas dan mudah dipahami (`O(n)` space) versus solusi two-pointer yang lebih hemat memori (`O(1)` space) tapi sedikit lebih rumit logikanya — pola yang juga relevan untuk soal-soal seperti _Valid Parentheses_ dan _Remove All Adjacent Duplicates In String_. 🎯

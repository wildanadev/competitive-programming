# 1047. Remove All Adjacent Duplicates In String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Stack
- **Link**: [Problem](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/)
- **Solution**: [Code](../../leetcode/RemoveAllAdjacentDuplicatesInString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`. Lakukan operasi berulang: pilih **dua huruf bersebelahan yang sama**, hapus keduanya. Ulangi sampai **tidak ada lagi** pasangan huruf bersebelahan yang sama. Kembalikan hasil akhirnya.

Contoh:

- `s = "abbaca"` → `"ca"`
  - `"abbaca"` → hapus `"bb"` → `"aaca"` → hapus `"aa"` → `"ca"` (tidak ada lagi pasangan bersebelahan sama)
- `s = "azxxzy"` → `"ay"`
  - `"azxxzy"` → hapus `"xx"` → `"azzy"` → hapus `"zz"` → `"ay"`

______________________________________________________________________

## 💡 Intuition

Ini soal **stack** yang menarik karena ada efek **berantai (cascading)**: menghapus satu pasangan bisa membuat dua huruf yang **sebelumnya tidak bersebelahan** jadi bersebelahan (seperti `"azxxzy"` — setelah `"xx"` dihapus, `z` dan `z` yang sebelumnya terpisah oleh `"xx"` jadi bersebelahan, dan ikut terhapus juga).

Stack **secara alami** menangani efek berantai ini tanpa perlu logika tambahan apapun: untuk tiap karakter baru, **bandingkan** dengan karakter **teratas** di stack (karakter yang paling baru "selamat" dari proses sebelumnya).

- Kalau **sama** → berarti pasangan baru saja terbentuk, **pop** (hapus keduanya — karakter baru tidak pernah masuk stack, dan karakter lama di stack dikeluarkan).
- Kalau **beda** → **push** karakter baru ke stack (dia "selamat" untuk sementara, menunggu kemungkinan pasangan di masa depan).

Begitu seluruh string diproses, **isi stack dari bawah ke atas** adalah hasil akhirnya — tidak ada lagi pasangan bersebelahan yang sama, karena setiap kali ada pasangan terbentuk (baik langsung maupun lewat efek berantai), stack langsung menanganinya.

______________________________________________________________________

## 🔍 Approach

### Stack dengan Push/Pop Berdasarkan Perbandingan Top

1. Siapkan `stack` (`Deque<Character>`).
1. Loop tiap karakter `i` di `s`:
   - Kalau `stack` kosong → `push(i)` (tidak ada yang dibandingkan, langsung masuk).
   - Kalau `stack` tidak kosong:
     - Kalau `i == stack.peek()` (sama dengan karakter teratas) → `pop()` (pasangan ditemukan, keduanya terhapus).
     - Kalau tidak → `push(i)`.
1. Setelah seluruh string diproses, isi `stack` (dari **atas** ke **bawah**, karena itu urutan alami saat `pop` berulang) adalah hasil akhir **dalam urutan terbalik**. Bangun `StringBuilder` dengan terus `pop` sampai `stack` kosong, lalu **reverse** hasilnya untuk mendapatkan urutan yang benar (kiri ke kanan).
1. Kembalikan hasil string yang sudah dibalik.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n) — setiap karakter paling banyak satu kali `push` dan satu kali `pop` |
| **Space** | O(n) — stack di kasus terburuk menyimpan hampir seluruh karakter (kalau tidak ada duplikat bersebelahan sama sekali) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abbaca"`

| char | Aksi | stack sesudah (bawah→atas) |
| ---- | -------------------- | -------------------------- |
| a | kosong → push | `[a]` |
| b | `b != top(a)` → push | `[a,b]` |
| b | `b == top(b)` → pop | `[a]` |
| a | `a == top(a)` → pop | `[]` |
| c | kosong → push | `[c]` |
| a | `a != top(c)` → push | `[c,a]` |

Stack akhir (bawah→atas): `[c, a]`. Pop berurutan menghasilkan `a` lalu `c` → `sb = "ac"`. Reverse → `"ca"`.

**Output: `"ca"`** ✅

______________________________________________________________________

**Input:** `s = "azxxzy"`

| char | Aksi | stack sesudah |
| ---- | ------------------- | ------------- |
| a | push | `[a]` |
| z | `z != a` → push | `[a,z]` |
| x | `x != z` → push | `[a,z,x]` |
| x | `x == top(x)` → pop | `[a,z]` |
| z | `z == top(z)` → pop | `[a]` |
| y | `y != a` → push | `[a,y]` |

Stack akhir: `[a, y]`. Pop: `y` lalu `a` → `sb = "ya"`. Reverse → `"ay"`.

**Output: `"ay"`** ✅ — contoh ini menunjukkan efek **berantai**: `x` dan `x` bersebelahan terhapus duluan, yang membuat `z` dan `z` (sebelumnya terpisah oleh `"xx"`) jadi bersebelahan dan ikut terhapus — semua tertangani otomatis lewat perbandingan dengan `top` stack, tanpa perlu logika tambahan untuk mendeteksi "efek berantai" secara eksplisit.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada pasangan bersebelahan sama sekali (misal `"abc"`) → semua karakter ter-push tanpa pernah di-pop, hasil akhirnya `s` itu sendiri
- [ ] Seluruh string terhapus habis (misal `"aabbcc"` atau `"aabb"`) → stack kosong di akhir, hasilnya string kosong `""`
- [ ] Efek berantai yang panjang (seperti `"azxxzy"`) → tertangani otomatis, tidak perlu deteksi khusus
- [ ] String satu karakter → tidak ada yang bisa dihapus, hasilnya karakter itu sendiri
- [ ] Pasangan yang terbentuk tepat di awal pemrosesan (`"aabc"`) → `pop` langsung terjadi di iterasi kedua, stack jadi kosong lagi sebelum lanjut ke karakter berikutnya

______________________________________________________________________

## 🔧 Kenapa Butuh `.reverse()` di Akhir?

Ini detail teknis yang penting dipahami. Saat kita `pop()` dari stack **berulang kali**, urutan yang keluar adalah **dari atas ke bawah** — yaitu karakter yang **paling terakhir** masuk stack keluar **duluan**. Tapi posisi "atas stack" sebenarnya merepresentasikan karakter yang **paling kanan** di string hasil akhir (karena itu karakter yang paling terakhir "selamat" dan ditambahkan). Jadi kalau kita langsung `append` hasil `pop` tanpa dibalik, kita akan mendapat string dalam **urutan terbalik** (kanan ke kiri). `.reverse()` di akhir mengoreksi ini, mengembalikannya ke urutan normal (kiri ke kanan).

______________________________________________________________________

## 🔧 Alternatif: `StringBuilder` Langsung sebagai Stack (Tanpa Reverse)

```java
public String removeDuplicates(String s) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
        int len = sb.length();
        if (len > 0 && sb.charAt(len - 1) == c) {
            sb.deleteCharAt(len - 1);
        } else {
            sb.append(c);
        }
    }
    return sb.toString();
}
```

Versi ini memakai `StringBuilder` **langsung** sebagai stack — `append` untuk push, `deleteCharAt(length-1)` untuk pop, dan `charAt(length-1)` untuk mengintip elemen teratas (`peek`). Karena `StringBuilder` secara alami mempertahankan urutan **kiri ke kanan** (tidak seperti `Deque` yang di-pop dari atas), hasil akhirnya **sudah dalam urutan yang benar** tanpa perlu `.reverse()` sama sekali — sedikit lebih ringkas dan menghindari satu pass tambahan untuk membalik string.

| Approach | Time | Space | Butuh Reverse di Akhir? |
| ---------------------------------------- | ---- | ----- | ----------------------- |
| `Deque<Character>` + reverse (kode asli) | O(n) | O(n) | Ya |
| `StringBuilder` sebagai stack | O(n) | O(n) | Tidak |

Keduanya sama-sama valid dan `O(n)`, tapi versi `StringBuilder` sedikit lebih efisien secara konstanta karena tidak perlu transfer data dari `Deque` ke `StringBuilder` lalu dibalik — semuanya terjadi langsung di satu struktur data yang sama.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik **stack untuk menangani efek berantai (cascading)** — ketika menghapus sesuatu bisa "membuka" peluang penghapusan baru yang sebelumnya tidak terlihat, stack menanganinya **secara otomatis** lewat perbandingan sederhana dengan elemen teratas, tanpa perlu deteksi eksplisit untuk pola berantai. Perhatikan juga detail teknis soal **arah urutan** saat membangun hasil dari stack — `pop` berulang menghasilkan urutan terbalik, yang perlu dikoreksi (baik lewat `.reverse()` eksplisit, atau dengan memilih struktur data yang secara alami mempertahankan urutan seperti `StringBuilder`). Pola ini juga relevan untuk soal-soal seperti _Remove All Adjacent Duplicates in String II_ (versi dengan `k` duplikat, bukan cuma pasangan) dan _Minimum Remove to Make Valid Parentheses_. 🎯

# 1021. Remove Outermost Parentheses

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Stack
- **Link**: [Problem](https://leetcode.com/problems/remove-outermost-parentheses/)
- **Solution**: [Code](../../leetcode/RemoveOutermostParentheses.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string kurung valid `s`. String ini bisa dipecah jadi satu atau lebih **primitive valid parentheses string**, yaitu string kurung valid tak kosong yang **tidak bisa dipecah lagi** jadi dua string valid yang lebih kecil. Hapus **kurung terluar** dari **setiap** primitive, lalu gabungkan hasilnya.

Contoh:

- `s = "(()())(())"` → `"()()()"`
  - Primitive: `"(()())"` dan `"(())"`. Setelah kurung terluar dihapus: `"()()"` dan `"()"`. Digabung: `"()()()"`.
- `s = "(()())(())(()(()))"` → `"()()()()(())"`
  - Primitive: `"(()())"`, `"(())"`, `"(()(()))"`. Setelah kurung terluar dihapus: `"()()"`, `"()"`, `"()(())"`.
- `s = "()()"` → `""`
  - Primitive: `"()"` dan `"()"`. Menghapus kurung terluar dari masing-masing menyisakan string kosong.

______________________________________________________________________

## 💡 Intuition

Kunci soal ini: **kurung terluar** sebuah primitive adalah kurung `(` yang membuka saat **tidak ada kurung lain yang sedang terbuka**, dan kurung `)` yang menutup sehingga **tidak ada lagi kurung yang terbuka**. Jadi kita cuma perlu tahu **kedalaman saat ini** di tiap titik string.

Kedalaman bisa dilacak dengan satu counter (`open`), pola yang sama dengan soal _Maximum Nesting Depth of the Parentheses_. Bedanya, di sana kita mencari kedalaman **maksimum**, sedangkan di sini kita memakai counter untuk mendeteksi **batas primitive**:

- Sebuah `(` adalah **terluar** kalau `open == 0` **sebelum** karakter itu diproses (belum ada kurung terbuka sama sekali). Karakter ini **dibuang**.
- Sebuah `)` adalah **terluar** kalau `open == 1` **sebelum** karakter itu diproses (hanya tersisa satu kurung terbuka, yaitu pasangannya sendiri). Karakter ini **dibuang**.
- Selain itu, karakter berada **di dalam** primitive, jadi **disimpan** ke hasil.

Karena input dijamin valid, kita tidak butuh stack: cukup ukuran kedalamannya.

______________________________________________________________________

## 🔍 Approach

### Counter Kedalaman dengan Post-Increment dan Post-Decrement

1. `open = 0` (kedalaman saat ini), `sb` kosong (hasil).
1. Loop tiap karakter `i` di `s`:
   - Kalau `i == '('`: pakai **nilai lama** `open` untuk memutuskan. Kalau `open > 0` (sudah ada kurung terbuka, jadi ini bukan yang terluar) → append. Lalu `open` bertambah satu.
   - Kalau `i == ')'`: pakai **nilai lama** `open` untuk memutuskan. Kalau `open > 1` (setelah ditutup masih ada kurung terbuka, jadi ini bukan yang terluar) → append. Lalu `open` berkurang satu.
1. Kembalikan `sb.toString()`.

**Trik penulisan:** kode memakai `open++ > 0` dan `open-- > 1`. Operator post-increment/decrement **membandingkan nilai lama dulu**, baru mengubah `open`. Jadi pengecekan dan pembaruan counter terjadi dalam satu ekspresi.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh string |
| **Space** | O(n) — untuk `StringBuilder` hasil (di luar itu hanya satu counter) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "(()())(())"`

| No | Karakter | open sebelum | Diappend? | open sesudah | sb |
| --- | -------- | ------------ | ------------------------------ | ------------ | ---------- |
| 1 | `(` | 0 | tidak (`0 > 0` salah, terluar) | 1 | `""` |
| 2 | `(` | 1 | ya (`1 > 0`) | 2 | `"("` |
| 3 | `)` | 2 | ya (`2 > 1`) | 1 | `"()"` |
| 4 | `(` | 1 | ya (`1 > 0`) | 2 | `"()("` |
| 5 | `)` | 2 | ya (`2 > 1`) | 1 | `"()()"` |
| 6 | `)` | 1 | tidak (`1 > 1` salah, terluar) | 0 | `"()()"` |
| 7 | `(` | 0 | tidak (terluar) | 1 | `"()()"` |
| 8 | `(` | 1 | ya | 2 | `"()()("` |
| 9 | `)` | 2 | ya | 1 | `"()()()"` |
| 10 | `)` | 1 | tidak (terluar) | 0 | `"()()()"` |

**Output: `"()()()"`** ✅

______________________________________________________________________

**Input:** `s = "(()())(())(()(()))"`

Counter yang sama menghasilkan, per primitive:

- `"(()())"` → `"()()"`
- `"(())"` → `"()"`
- `"(()(()))"` → `"()(())"` (kedalaman sempat naik ke `3` di tengah, dan kurung di kedalaman `2` ke atas tetap disimpan)

Digabung: `"()()" + "()" + "()(())"`.

**Output: `"()()()()(())"`** ✅

______________________________________________________________________

**Input:** `s = "()()"`

Tiap `(` datang saat `open=0` (dibuang), dan tiap `)` datang saat `open=1` (dibuang). Tidak ada satupun karakter yang diappend.

**Output: `""`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu primitive tunggal `"()"` → kedua karakternya terluar, hasil `""`
- [ ] Bersarang penuh `"((()))"` → hanya pasangan terluar yang dibuang, hasil `"(())"`
- [ ] Banyak primitive berjajar (`"()()()"`) → semuanya habis, hasil `""`
- [ ] Primitive dengan kedalaman bervariasi (`"(()(()))"`) → kurung yang kedalamannya tidak nol tetap disimpan, termasuk saat kedalaman naik dan turun beberapa kali
- [ ] Input tidak valid (misal `")("`) → **tidak mungkin terjadi**, soal menjamin `s` adalah string kurung valid. Kalau input boleh tidak valid, `open` bisa jadi negatif dan solusi ini perlu validasi tambahan

______________________________________________________________________

## 🔧 Kenapa Dua `if` Terpisah Tidak Saling Mengganggu?

```java
if (i == '(' && open++ > 0)
    sb.append(i);
if (i == ')' && open-- > 1)
    sb.append(i);
```

Sekilas, dua `if` berurutan yang masing-masing mengubah `open` kelihatan berbahaya. Tapi aman, karena dua alasan:

1. Satu karakter hanya bisa `(` **atau** `)`, tidak mungkin keduanya. Jadi untuk setiap karakter, **hanya satu** dari dua kondisi yang benar-benar masuk ke bagian yang mengubah `open`.
1. Operator `&&` bersifat **short-circuit**: kalau `i == '('` salah, bagian `open++ > 0` **tidak pernah dievaluasi**, jadi `open` tidak berubah. Hal yang sama berlaku untuk `i == ')'` dan `open-- > 1`.

Hasilnya, tiap karakter menyentuh `open` tepat satu kali, persis seperti yang dimaksud.

______________________________________________________________________

## 🔧 Alternatif: Versi Eksplisit (Lebih Mudah Dibaca)

```java
public String removeOuterParentheses(String s) {
    StringBuilder sb = new StringBuilder();
    int depth = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') {
            if (depth > 0) sb.append(c); // bukan kurung buka terluar
            depth++;
        } else {
            depth--;
            if (depth > 0) sb.append(c); // bukan kurung tutup terluar
        }
    }
    return sb.toString();
}
```

Logikanya identik dengan kode asli, tapi pembaruan `depth` dipisahkan dari pengecekan, jadi tidak ada ekspresi yang punya efek samping tersembunyi. Perhatikan perbedaan halus di cabang `)`: karena `depth--` dilakukan **sebelum** pengecekan, kondisinya jadi `depth > 0` (nilai **baru**), yang setara dengan `open-- > 1` pada kode asli (nilai **lama** `> 1`). Dua penulisan itu ekuivalen.

| Approach | Time | Space | Keterbacaan |
| ------------------------------------ | ---- | ----- | -------------------------------------------- |
| Post-increment/decrement (kode asli) | O(n) | O(n) | Ringkas, tapi perlu paham nilai lama vs baru |
| Update `depth` terpisah dari cek | O(n) | O(n) | Lebih eksplisit |

______________________________________________________________________

## 🔧 Alternatif: Potong per Primitive Pakai Substring

```java
public String removeOuterParentheses(String s) {
    StringBuilder sb = new StringBuilder();
    int depth = 0, start = 0;
    for (int i = 0; i < s.length(); i++) {
        depth += s.charAt(i) == '(' ? 1 : -1;
        if (depth == 0) { // satu primitive selesai
            sb.append(s, start + 1, i); // buang karakter pertama dan terakhir primitive
            start = i + 1;
        }
    }
    return sb.toString();
}
```

Versi ini memakai pola yang lebih dekat ke definisi soal: begitu `depth` kembali ke `0`, satu primitive lengkap sudah ketemu (dari `start` sampai `i`), lalu cukup ambil isinya tanpa karakter pertama dan terakhir. Mirip dengan pola deteksi batas grup di _Positions of Large Groups_.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi dari pola **counter kedalaman** yang sama dengan _Maximum Nesting Depth of the Parentheses_, tapi dipakai untuk tujuan berbeda: bukan mencari nilai maksimum, melainkan mendeteksi **titik transisi** (kedalaman `0` ke `1` dan `1` ke `0`) yang menandai batas primitive. Karena inputnya dijamin valid dan hanya ada satu jenis kurung, stack tidak diperlukan, cukup satu integer. Perhatikan juga trik post-increment/decrement di kode asli: ringkas, tapi menyembunyikan perbedaan antara nilai **lama** dan **baru**, jadi versi eksplisit sering lebih aman untuk dibaca ulang beberapa bulan kemudian. 🎯

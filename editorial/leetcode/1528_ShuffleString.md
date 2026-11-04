# 1528. Shuffle String

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, String
- **Link**: [Problem](https://leetcode.com/problems/shuffle-string/)
- **Solution**: [Code](../../leetcode/ShuffleString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` dan array integer `indices` dengan panjang yang sama (`n`). String `s` akan **diacak (shuffle)**: karakter di posisi ke-`i` pada `s` dipindahkan ke posisi `indices[i]` pada string hasil. Kembalikan string hasil pengacakan itu.

`indices` dijamin berisi bilangan `0` sampai `n - 1` yang **semuanya berbeda** (sebuah permutasi), jadi setiap posisi tujuan terisi tepat satu kali.

Contoh:

- `s = "codeleet", indices = [4,5,6,7,0,2,1,3]` → `"leetcode"`
- `s = "abc", indices = [0,1,2]` → `"abc"` (tiap karakter tetap di tempatnya)

______________________________________________________________________

## 💡 Intuition

`indices[i]` memberi tahu **tujuan** karakter `s[i]`. Jadi kita tinggal memindahkan tiap karakter langsung ke tujuannya: `hasil[indices[i]] = s[i]`. Pendekatan ini disebut **scatter** (menyebarkan tiap elemen ke posisi tujuannya).

Karena `indices` adalah permutasi, kita tidak perlu khawatir ada dua karakter yang berebut posisi yang sama, atau ada posisi yang tidak terisi. Satu pass sudah cukup, tanpa sorting.

String di Java **immutable**, jadi hasilnya dibangun di dalam `char[]`, lalu diubah jadi `String` di akhir.

______________________________________________________________________

## 🔍 Approach

### Tulis Tiap Karakter ke Posisi Tujuannya

1. Buat `ans = new char[n]`.
1. Untuk tiap `i` dari `0` sampai `n - 1`: `ans[indices[i]] = s.charAt(i)`.
1. Kembalikan `new String(ans)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------- |
| **Time** | O(n) — satu kali pass |
| **Space** | O(n) — array `char[]` hasil (ukuran keluaran) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "codeleet"`, `indices = [4,5,6,7,0,2,1,3]`

| i | s[i] | indices[i] | Aksi | ans sesudah (`_` = belum terisi) |
| --- | ---- | ---------- | ------------- | -------------------------------- |
| 0 | c | 4 | `ans[4] = c` | `_ _ _ _ c _ _ _` |
| 1 | o | 5 | `ans[5] = o` | `_ _ _ _ c o _ _` |
| 2 | d | 6 | `ans[6] = d` | `_ _ _ _ c o d _` |
| 3 | e | 7 | `ans[7] = e` | `_ _ _ _ c o d e` |
| 4 | l | 0 | `ans[0] = l` | `l _ _ _ c o d e` |
| 5 | e | 2 | `ans[2] = e` | `l _ e _ c o d e` |
| 6 | e | 1 | `ans[1] = e` | `l e e _ c o d e` |
| 7 | t | 3 | `ans[3] = t` | `l e e t c o d e` |

**Output: `"leetcode"`** ✅

______________________________________________________________________

**Input:** `s = "abc"`, `indices = [0,1,2]`

Tiap karakter ditulis ke posisinya sendiri: `ans = ['a','b','c']`.

**Output: `"abc"`** ✅

______________________________________________________________________

**Input tambahan:** `s = "abc"`, `indices = [1,2,0]`

`a` ke indeks `1`, `b` ke indeks `2`, `c` ke indeks `0` → `ans = ['c','a','b']`.

**Output: `"cab"`**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → satu karakter, `indices = [0]`, hasilnya `s` itu sendiri
- [ ] `indices` berupa identitas (`[0,1,...,n-1]`) → hasilnya sama dengan `s`
- [ ] `indices` berupa urutan terbalik (`[n-1,...,0]`) → hasilnya `s` yang dibalik
- [ ] Karakter berulang di `s` → tidak masalah, tiap karakter punya tujuan sendiri berdasarkan indeksnya
- [ ] Kalau `indices` **bukan** permutasi (ada indeks ganda atau ada yang tidak muncul), sebagian posisi `ans` akan tertimpa atau tetap berisi karakter default `'\u0000'`. Soal menjamin ini tidak terjadi, jadi tidak perlu validasi

______________________________________________________________________

## 🔧 Catatan: `indices[i]` adalah Tujuan, Bukan Sumber

Hati-hati dengan arah pemetaannya. Di soal ini, `indices[i]` berarti "karakter `s[i]` **pergi ke** posisi `indices[i]`", sehingga rumusnya `ans[indices[i]] = s[i]`. Kalau tafsirannya dibalik ("posisi `i` pada hasil **berasal dari** `s[indices[i]]`"), rumusnya jadi `ans[i] = s[indices[i]]`. Dua rumus itu hanya sama kalau permutasinya kebetulan simetris (misal identitas), dan menghasilkan jawaban berbeda pada kasus umum. Contoh `s = "abc"`, `indices = [1,2,0]`: rumus pertama menghasilkan `"cab"`, rumus kedua menghasilkan `"bca"`. Selalu cocokkan dengan contoh soal untuk memastikan arahnya.

______________________________________________________________________

## 🔧 Alternatif: Tukar Langsung di Tempat (Cycle Placement)

Kalau ingin menghindari array hasil terpisah selain `char[]` dari `s` itu sendiri, permutasi bisa diterapkan **di tempat** dengan menukar elemen sampai tiap elemen berada di tujuannya:

```java
public String restoreString(String s, int[] indices) {
    char[] c = s.toCharArray();
    for (int i = 0; i < c.length; i++) {
        while (indices[i] != i) {               // karakter di i belum di tujuannya
            int j = indices[i];
            char tc = c[i]; c[i] = c[j]; c[j] = tc;            // kirim karakter ke posisi j
            int ti = indices[i]; indices[i] = indices[j]; indices[j] = ti;
        }
    }
    return new String(c);
}
```

Tiap penukaran menaruh satu karakter di posisi akhirnya (`indices[j]` menjadi `j`), jadi total penukaran paling banyak `n`, dan waktunya tetap `O(n)`. Cek `s = "abc"`, `indices = [1,2,0]`: `i = 0` menghasilkan `c = [b,a,c]`, `indices = [2,1,0]`, lalu `c = [c,a,b]`, `indices = [0,1,2]`; hasil `"cab"` ✅.

Trade-off-nya: versi ini **mengubah array `indices` milik pemanggil** (efek samping), dan hemat memorinya kecil (`char[]` tetap dibutuhkan karena `String` immutable). Untuk `n <= 100`, solusi scatter sederhana jelas lebih pantas dipilih.

| Approach | Time | Space | Catatan |
| ------------------------------------ | ---- | ----- | -------------------------------------------- |
| Scatter ke `char[]` baru (kode asli) | O(n) | O(n) | Sederhana, tidak mengubah input |
| Tukar di tempat (cycle placement) | O(n) | O(1) tambahan | Mengubah `indices`, lebih rumit |

______________________________________________________________________

## 📌 Key Takeaway

Ketika setiap elemen sudah tahu **tujuannya** (di sini `indices[i]`), cara paling langsung adalah **scatter**: tulis elemen itu ke posisi tujuannya di array hasil, tanpa sorting. Pola penerapan permutasi lewat array indeks ini muncul di banyak soal pemetaan posisi, misalnya konversi indeks di *Reshape the Matrix*. Perhatikan selalu **arah** pemetaan (tujuan atau sumber), karena salah arah menghasilkan jawaban berbeda yang tetap terlihat masuk akal. Untuk penerapan permutasi tanpa memori tambahan, ada teknik cycle placement yang berkaitan dengan *cyclic sort*. 🎯

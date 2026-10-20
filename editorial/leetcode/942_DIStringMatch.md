# 942. DI String Match

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Two Pointers, String, Greedy
- **Link**: [Problem](https://leetcode.com/problems/di-string-match/)
- **Solution**: [Code](../../leetcode/DIStringMatch.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` sepanjang `n` yang hanya berisi `'I'` (**Increase**) dan `'D'` (**Decrease**). Bangun **permutasi** `perm` dari bilangan `0` sampai `n` (panjangnya `n + 1`) yang cocok dengan `s`:

- Kalau `s[i] == 'I'` maka `perm[i] < perm[i+1]`.
- Kalau `s[i] == 'D'` maka `perm[i] > perm[i+1]`.

Kalau ada beberapa permutasi yang valid, **kembalikan salah satunya saja**.

Contoh:

- `s = "IDID"` → `[0,4,1,3,2]`
- `s = "III"` → `[0,1,2,3]`
- `s = "DDI"` → `[3,2,0,1]`

______________________________________________________________________

## 💡 Intuition

Bayangkan kita punya sekumpulan angka `0..n` yang belum terpakai, dan kita mengisi `perm` dari kiri ke kanan. Untuk tiap karakter `s[i]` kita harus memilih `perm[i]` sedemikian rupa sehingga **dijamin** ada angka yang cocok untuk `perm[i+1]` di sisa angka yang tersedia.

Triknya sederhana dan elegan:

- Kalau `s[i] == 'I'` (angka berikutnya harus **lebih besar**), pilih angka **terkecil** yang tersisa untuk `perm[i]`. Semua sisa angka pasti lebih besar darinya, jadi `perm[i+1]` (apa pun yang nanti dipilih) otomatis lebih besar.
- Kalau `s[i] == 'D'` (angka berikutnya harus **lebih kecil**), pilih angka **terbesar** yang tersisa untuk `perm[i]`. Semua sisa angka pasti lebih kecil darinya, jadi `perm[i+1]` otomatis lebih kecil.

Dua pointer `left` (angka terkecil yang tersisa) dan `right` (angka terbesar yang tersisa) cukup untuk melacak ini, karena angka yang belum terpakai **selalu membentuk rentang bersambung `[left, right]`**: tiap kali kita mengambil angka dari ujung kiri atau ujung kanan, rentangnya hanya menyusut dari ujung itu.

Setelah `n` karakter diproses, tersisa tepat **satu** angka (`left == right`), yang menjadi elemen terakhir `perm[n]`.

______________________________________________________________________

## 🔍 Approach

### Greedy dengan Dua Pointer `left` dan `right`

1. `left = 0`, `right = n`, `ans` array berukuran `n + 1`.
1. Untuk tiap `i` dari `0` sampai `n - 1`:
   - Kalau `s[i] == 'I'` → `ans[i] = left`, lalu `left++`.
   - Kalau `s[i] == 'D'` → `ans[i] = right`, lalu `right--`.
1. Isi elemen terakhir: `ans[n] = left` (pada titik ini `left == right`, jadi `right` juga boleh).
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------- |
| **Time** | O(n) — satu kali pass |
| **Space** | O(1) tambahan — di luar array hasil sepanjang `n + 1` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "IDID"` (`n = 4`, angka tersedia `0..4`)

| i | s[i] | Aksi | ans[i] | left | right |
| --- | ---- | ------------------------ | ------ | ---- | ----- |
| — | — | awal | — | 0 | 4 |
| 0 | I | ambil terkecil (`left`) | 0 | 1 | 4 |
| 1 | D | ambil terbesar (`right`) | 4 | 1 | 3 |
| 2 | I | ambil terkecil | 1 | 2 | 3 |
| 3 | D | ambil terbesar | 3 | 2 | 2 |

Elemen terakhir: `ans[4] = left = 2`.

**Output: `[0,4,1,3,2]`** ✅ (cek: `0 < 4` untuk `I`, `4 > 1` untuk `D`, `1 < 3` untuk `I`, `3 > 2` untuk `D`)

______________________________________________________________________

**Input:** `s = "III"` (`n = 3`)

Semua `'I'`: `ans = [0, 1, 2]`, lalu `ans[3] = left = 3`.

**Output: `[0,1,2,3]`** ✅

______________________________________________________________________

**Input:** `s = "DDI"` (`n = 3`, angka tersedia `0..3`)

| i | s[i] | Aksi | ans[i] | left | right |
| --- | ---- | -------------- | ------ | ---- | ----- |
| 0 | D | ambil terbesar | 3 | 0 | 2 |
| 1 | D | ambil terbesar | 2 | 0 | 1 |
| 2 | I | ambil terkecil | 0 | 1 | 1 |

Elemen terakhir: `ans[3] = left = 1`.

**Output: `[3,2,0,1]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua `'I'` (`"IIII"`) → hasilnya urutan naik `[0,1,2,3,4]`
- [ ] Semua `'D'` (`"DDDD"`) → hasilnya urutan turun `[4,3,2,1,0]`
- [ ] Panjang `s` satu (`"I"` atau `"D"`) → `"I"` menghasilkan `[0,1]`, `"D"` menghasilkan `[1,0]`
- [ ] Pergantian `I` dan `D` bergantian (`"IDID"`) → hasil zig-zag antara angka kecil dan besar
- [ ] Beberapa jawaban valid → soal menerima salah satunya, jadi hasilmu boleh berbeda dari jawaban lain selama memenuhi syarat `I`/`D` dan merupakan permutasi `0..n`

______________________________________________________________________

## 🔧 Kenapa Strategi Ini Selalu Berhasil?

Alasannya ada pada invarian: **angka yang belum terpakai selalu membentuk rentang bersambung `[left, right]`**, dan sebelum memproses `s[i]` jumlahnya `n - i + 1 >= 2` (masih ada setidaknya dua angka: satu untuk `perm[i]`, satu lagi untuk `perm[i+1]`).

- Untuk `'I'`: `perm[i] = left` adalah **minimum** dari rentang. Semua angka sisa lebih besar darinya, jadi `perm[i+1]`, yang akan diambil dari sisa itu, pasti lebih besar.
- Untuk `'D'`: `perm[i] = right` adalah **maksimum** dari rentang. Semua angka sisa lebih kecil darinya, jadi `perm[i+1]` pasti lebih kecil.

Karena syarat hanya melibatkan **pasangan bertetangga**, dan setiap pasangan dijamin terpenuhi saat `perm[i]` dipilih (tanpa peduli angka mana yang nanti dipilih untuk `perm[i+1]`), seluruh permutasi valid. Pilihan ujung kiri atau ujung kanan juga menjaga rentang sisa tetap bersambung, jadi argumen yang sama berlaku di langkah berikutnya.

______________________________________________________________________

## 🔧 Alternatif: Stack (Balik Setiap Rangkaian `D`)

```java
public int[] diStringMatch(String s) {
    int n = s.length();
    int[] ans = new int[n + 1];
    Deque<Integer> stack = new ArrayDeque<>();
    int idx = 0;
    for (int i = 0; i <= n; i++) {
        stack.push(i);                               // masukkan 0, 1, 2, ... berurutan
        if (i == n || s.charAt(i) == 'I') {          // di akhir, atau saat ketemu 'I'
            while (!stack.isEmpty())
                ans[idx++] = stack.pop();            // keluarkan = membalik rangkaian 'D'
        }
    }
    return ans;
}
```

Ide versi ini: tulis `0, 1, 2, ..., n` berurutan, lalu **balik** setiap segmen yang berkaitan dengan rangkaian `'D'` berturut-turut (stack otomatis membalik urutannya). Untuk `"IDID"` hasilnya `[0,2,1,4,3]`. Ini **berbeda** dari contoh resmi `[0,4,1,3,2]`, tapi tetap valid (`0<2`, `2>1`, `1<4`, `4>3`), dan soal menerima jawaban valid mana pun.

| Approach | Time | Space | Catatan |
| ------------------------------ | ---- | ------------- | ------------------------------------------- |
| Greedy dua pointer (kode asli) | O(n) | O(1) tambahan | Ringkas, cocok persis dengan contoh resmi |
| Stack, balik rangkaian `D` | O(n) | O(n) | Intuitif secara visual, hasil valid berbeda |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini contoh greedy yang elegan: dengan selalu mengambil **angka ekstrem** (terkecil untuk `'I'`, terbesar untuk `'D'`) dari rentang yang tersisa, setiap syarat bertetangga terpenuhi secara otomatis, tanpa perlu memikirkan angka-angka di masa depan. Kuncinya ada pada invarian bahwa sisa angka selalu membentuk rentang bersambung `[left, right]`. Perhatikan juga bahwa soal ini menerima **jawaban valid mana pun**, jadi kebenaran dicek dari terpenuhinya syarat, bukan dari kecocokan persis dengan satu keluaran tertentu. Pola dua pointer dari kedua ujung yang menyusut ke dalam ini juga muncul di banyak soal lain seperti _Reverse Only Letters_ dan _Valid Palindrome II_. 🎯

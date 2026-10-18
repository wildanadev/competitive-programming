# 4083. Minimum Deletions to Make Array Sharp

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Greedy, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/minimum-deletions-to-make-array-sharp/)
- **Solution**: [Code](../../leetcode/MinimumDeletionsToMakeArraySharp.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`. Kamu boleh **menghapus sejumlah elemen**, dan elemen yang tersisa **mempertahankan urutan aslinya**. Array hasil `a` disebut **sharp** kalau `a[i] XOR a[i+1] != 0` untuk setiap pasangan elemen **bertetangga**. Array dengan paling banyak satu elemen otomatis sharp. Kembalikan **jumlah penghapusan minimum** supaya `nums` menjadi sharp.

Contoh:

- `nums = [1,1,2,2,2,3]` → `3` (hapus satu `1` dan dua `2`, tersisa `[1,2,3]`)
- `nums = [4,4,9,4]` → `1` (hapus salah satu `4` di depan, tersisa `[4,9,4]`)

______________________________________________________________________

## 💡 Intuition

Kata kunci "XOR" di soal ini cuma **pembungkus**. Sifat dasar XOR: `a XOR b == 0` **jika dan hanya jika** `a == b`, karena XOR menghasilkan bit `1` di setiap posisi bit yang berbeda, jadi hasilnya `0` hanya kalau tidak ada bit yang berbeda. Maka syarat "XOR tetangga tidak nol" sama persis dengan **"tidak ada dua elemen bertetangga yang sama"**.

Sekarang soalnya jadi sederhana: hapus elemen minimum supaya tidak ada tetangga yang sama. Di sebuah **run** (deretan elemen sama berturut-turut) sepanjang `L`, kita harus menyisakan **satu** saja, jadi butuh `L - 1` penghapusan. Total penghapusan adalah jumlah `(L - 1)` untuk semua run, dan itu sama persis dengan **banyaknya pasangan bertetangga yang sama** di array asli (run sepanjang `L` mengandung `L - 1` pasangan seperti itu).

Jadi jawabannya cukup: **hitung berapa pasangan `(i, i+1)` dengan `nums[i] == nums[i+1]`**.

**Kenapa ini juga minimum?** Satu penghapusan hanya bisa mengurangi banyaknya pasangan bertetangga-sama sebesar paling banyak `1`:

- Menghapus elemen `x` di tengah menghapus dua pasangan lama (kiri dan kanan `x`) dan membuat satu pasangan baru (tetangga kiri dan kanannya sekarang bersebelahan).
- Kalau kedua pasangan lama sama-sama "sama" (`kiri == x == kanan`), pasangan baru juga "sama", jadi total turun dari `2` menjadi `1`, yaitu berkurang `1`.
- Kalau hanya satu pasangan lama yang "sama", pasangan baru pasti "beda", jadi total turun `1`.
- Kalau tidak ada pasangan lama yang "sama", total tidak bisa berkurang (bahkan bisa bertambah, misal menghapus `9` dari `[4,9,4]` menciptakan pasangan `4,4`).

Karena setiap penghapusan mengurangi hitungan paling banyak `1`, dibutuhkan **minimal** sebanyak pasangan yang ada, dan menghapus semua kecuali satu elemen per run mencapainya. Batas bawah dan batas atas bertemu.

______________________________________________________________________

## 🔍 Approach

### Hitung Pasangan Bertetangga yang Sama

1. `ans = 0`.
1. Untuk tiap `i` dari `0` sampai `nums.length - 2`:
   - Kalau `(nums[i] ^ nums[i+1]) == 0` (artinya `nums[i] == nums[i+1]`) → `ans++`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh array |
| **Space** | O(1) — hanya satu variabel penghitung |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,1,2,2,2,3]`

| Pasangan (i, i+1) | Nilai | XOR | XOR sama dengan 0? | ans |
| ----------------- | ------ | --- | ------------------ | --- |
| (0, 1) | `1, 1` | 0 | ya | 1 |
| (1, 2) | `1, 2` | 3 | tidak | 1 |
| (2, 3) | `2, 2` | 0 | ya | 2 |
| (3, 4) | `2, 2` | 0 | ya | 3 |
| (4, 5) | `2, 3` | 1 | tidak | 3 |

**Output: `3`** ✅

______________________________________________________________________

**Input:** `nums = [4,4,9,4]`

| Pasangan (i, i+1) | Nilai | XOR | XOR sama dengan 0? | ans |
| ----------------- | ------ | --- | ------------------ | --- |
| (0, 1) | `4, 4` | 0 | ya | 1 |
| (1, 2) | `4, 9` | 13 | tidak | 1 |
| (2, 3) | `9, 4` | 13 | tidak | 1 |

**Output: `1`** ✅ (elemen `4` terakhir tidak menyebabkan masalah walau nilainya sama dengan `4` di depan, karena keduanya **tidak bertetangga**)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada tetangga yang sama (`[1,2,1,2]`) → `0`, array sudah sharp
- [ ] Semua elemen sama (`[5,5,5,5]`) → `3`, cukup menyisakan satu elemen (array satu elemen dianggap sharp)
- [ ] Nilai sama tapi tidak bertetangga (`[4,9,4]`) → `0`, tidak perlu menghapus apa pun
- [ ] Dua elemen (`n = 2`): `[x, x]` → `1`, `[x, y]` → `0`
- [ ] Beberapa run dengan panjang berbeda (`[1,1,1,2,2,3]`) → `(3-1) + (2-1) + (1-1) = 3`

______________________________________________________________________

## 🔧 Catatan: `^` atau `==`?

`(nums[i] ^ nums[i + 1]) == 0` benar, tapi setara dengan `nums[i] == nums[i + 1]` yang lebih langsung dan lebih mudah dibaca. Soal sengaja memakai istilah XOR; begitu disadari bahwa XOR bernilai `0` hanya untuk dua nilai yang identik, operasi bitwise itu bisa diganti perbandingan biasa tanpa mengubah hasil. Berguna untuk diingat: kalau sebuah soal "menyembunyikan" kondisi sederhana di balik operasi bitwise, cek dulu apakah kondisinya setara dengan sesuatu yang lebih mudah.

______________________________________________________________________

## 🔧 Alternatif: `n` Dikurangi Jumlah Run

Karena tiap run sepanjang `L` menyumbang `L - 1` penghapusan, total penghapusan sama dengan `n - (jumlah run)`:

```java
public int minDeletions(int[] nums) {
    int runs = 1;
    for (int i = 1; i < nums.length; i++)
        if (nums[i] != nums[i - 1]) runs++;   // awal run baru
    return nums.length - runs;
}
```

Ini mencerminkan strategi greedy "sisakan satu elemen dari tiap run" secara langsung: `runs` adalah jumlah elemen yang disisakan, dan sisanya dihapus. Contoh `[1,1,2,2,2,3]`: `runs = 3` (`1`, `2`, `3`) → `6 - 3 = 3` ✅. Contoh `[4,4,9,4]`: `runs = 3` → `4 - 3 = 1` ✅.

| Approach | Time | Space | Sudut pandang |
| -------------------------------------------- | ---- | ----- | ---------------------------------------------- |
| Hitung pasangan bertetangga sama (kode asli) | O(n) | O(1) | Berapa pasangan "buruk" yang harus dihilangkan |
| `n` dikurangi jumlah run | O(n) | O(1) | Berapa elemen yang boleh disisakan |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan dua hal. Pertama, **kenali kondisi sederhana di balik istilah yang tampak rumit**: `a XOR b != 0` hanyalah `a != b`, sehingga "array sharp" berarti "tidak ada tetangga yang sama". Kedua, untuk soal "hapus minimum supaya tidak ada tetangga yang sama", jawabannya adalah banyaknya pasangan bertetangga yang sama (setara `n` dikurangi jumlah run), dan minimalitasnya bisa dibuktikan dengan memeriksa bahwa satu penghapusan mengurangi jumlah pasangan "buruk" paling banyak satu. Pola menghitung pasangan bertetangga atau menghitung run juga muncul di _Positions of Large Groups_ dan _Count Binary Substrings_. 🎯

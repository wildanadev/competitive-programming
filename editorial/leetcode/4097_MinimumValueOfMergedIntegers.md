# 4097. Minimum Value of Merged Integers

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/minimum-value-of-merged-integers/)
- **Solution**: [Code](../../leetcode/MinimumValueOfMergedIntegers.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` berpanjang **genap** `n`. Gabungkan elemennya **berpasangan**: `nums[0]` dengan `nums[1]`, `nums[2]` dengan `nums[3]`, dan seterusnya sampai `nums[n-2]` dengan `nums[n-1]`. Cara menggabungkan dua bilangan adalah **menyambung digit desimal** bilangan pertama, lalu digit bilangan kedua. Kembalikan **nilai terkecil** di antara semua hasil penggabungan.

Constraint: `2 <= n <= 100`, `n` genap, `1 <= nums[i] <= 1000`.

Contoh:

- `nums = [23,55,42,21]` → `2355` (`23` + `55` = `2355`, `42` + `21` = `4221`; minimum `2355`)
- `nums = [34,7,11,11]` → `347` (`34` + `7` = `347`, `11` + `11` = `1111`; minimum `347`)
- `nums = [9,20]` → `920` (hanya satu pasangan)

______________________________________________________________________

## 💡 Intuition

Soal ini murni **simulasi**: pasangannya sudah ditentukan (`(0,1)`, `(2,3)`, ...), jadi tidak ada pilihan pasangan yang perlu dioptimalkan. Kita cukup:

1. Untuk tiap pasangan, hitung hasil penggabungannya.
1. Ambil yang paling kecil.

Penggabungan dua bilangan dengan menyambung digit paling mudah dilakukan lewat **string**: ubah keduanya ke `String`, sambung, lalu ubah balik ke `int` dengan `Integer.parseInt`. Mengubah kembali ke `int` itu **penting**: kita ingin membandingkan **nilai numeriknya**, bukan urutan abjadnya.

Perbedaannya nyata. Pada contoh kedua, hasil gabungannya `347` dan `1111`. Secara numerik `347 < 1111`, tapi kalau dibandingkan sebagai string (urutan kamus), `"1111" < "347"` karena karakter pertamanya `'1' < '3'`. Membandingkan string mentah akan memilih jawaban yang salah.

______________________________________________________________________

## 🔍 Approach

### Gabung Tiap Pasangan Lewat String, Ambil Minimum

1. `ans = Integer.MAX_VALUE`.
1. Untuk `i` dari `0` sampai `nums.length - 1` dengan langkah `2`:
   - `merged = Integer.parseInt(String.valueOf(nums[i]) + String.valueOf(nums[i + 1]))`.
   - `ans = Math.min(ans, merged)`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------- |
| **Time** | O(n) — `n / 2` pasangan, tiap penggabungan paling banyak 8 digit (konstan) |
| **Space** | O(1) — string sementara berukuran kecil dan tetap |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [23,55,42,21]`

| i | Pasangan | Penggabungan | ans sesudah |
| --- | ------------ | ------------ | ----------- |
| 0 | `23` dan `55` | `"23" + "55" = "2355"` → `2355` | `min(MAX, 2355) = 2355` |
| 2 | `42` dan `21` | `"42" + "21" = "4221"` → `4221` | `min(2355, 4221) = 2355` |

**Output: `2355`** ✅

______________________________________________________________________

**Input:** `nums = [34,7,11,11]`

| i | Pasangan | Penggabungan | ans sesudah |
| --- | ------------ | ------------ | ----------- |
| 0 | `34` dan `7` | `"34" + "7" = "347"` → `347` | `347` |
| 2 | `11` dan `11` | `"11" + "11" = "1111"` → `1111` | `min(347, 1111) = 347` |

**Output: `347`** ✅

______________________________________________________________________

**Input:** `nums = [9,20]`

Hanya satu pasangan: `"9" + "20" = "920"` → `920`.

**Output: `920`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 2` → hanya satu pasangan, hasilnya penggabungan itu sendiri
- [ ] Dua bilangan dengan jumlah digit berbeda (`34` dan `7`) → penggabungan tetap sekadar menyambung digit (`347`), bukan `34 + 7`
- [ ] Urutan penting: `merge(a, b)` tidak sama dengan `merge(b, a)` (`23` dan `55` menghasilkan `2355`, bukan `5523`)
- [ ] Nilai terbesar yang mungkin: `"1000" + "1000"` = `10001000` (8 digit, sekitar `10^7`), jauh di bawah batas `int` (`2.147 × 10^9`), jadi `Integer.parseInt` aman
- [ ] Panjang `nums` genap dijamin constraint, jadi akses `nums[i + 1]` dengan `i` genap tidak pernah keluar batas
- [ ] Tidak ada angka `0` di depan: `nums[i] >= 1`, sehingga tidak ada masalah leading zero setelah penggabungan

______________________________________________________________________

## 🔧 Alternatif 1: Penggabungan Aritmatika (Tanpa String)

Menyambung `b` di belakang `a` setara dengan **menggeser `a` sebanyak jumlah digit `b`**, lalu menambah `b`:

```
merge(a, b) = a * 10^(jumlah digit b) + b
```

Contoh: `merge(34, 7) = 34 * 10 + 7 = 347`, dan `merge(11, 11) = 11 * 100 + 11 = 1111`.

```java
public int minMergedInteger(int[] nums) {
    int ans = Integer.MAX_VALUE;
    for (int i = 0; i < nums.length; i += 2)
        ans = Math.min(ans, merge(nums[i], nums[i + 1]));
    return ans;
}

private int merge(int a, int b) {
    int p = 1;
    for (int t = b; t > 0; t /= 10)   // hitung 10^(jumlah digit b)
        p *= 10;
    return a * p + b;
}
```

Versi ini tidak membuat objek `String` sama sekali (tidak ada `String.valueOf`, penyambungan, atau `parseInt`), jadi lebih ringan. Cek `merge(1000, 1000)`: `p` menjadi `10^4`, hasilnya `1000 * 10000 + 1000 = 10001000` ✅.

______________________________________________________________________

## 🔧 Alternatif 2: Stream

```java
public int minMergedInteger(int[] nums) {
    return IntStream.range(0, nums.length / 2)
            .map(k -> merge(nums[2 * k], nums[2 * k + 1]))
            .min()
            .getAsInt();
}
```

Memakai `IntStream.range` untuk mengindeks pasangan ke-`k` (`nums[2k]` dan `nums[2k+1]`), dengan `merge` yang sama seperti di atas. Hasilnya identik, hanya gaya penulisannya deklaratif.

| Approach | Time | Space | Catatan |
| ------------------------------------ | ---- | ----- | ------------------------------------------ |
| Gabung lewat `String` (kode asli) | O(n) | O(1) | Paling mudah dibayangkan |
| `a * 10^(digit b) + b` | O(n) | O(1) | Tanpa objek `String`, lebih ringan |
| `IntStream.range` dan `merge` | O(n) | O(1) | Deklaratif |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini simulasi langsung dengan satu jebakan halus: **bandingkan hasil sebagai angka, bukan sebagai string**. Menyambung digit dua bilangan setara dengan `a * 10^(jumlah digit b) + b`, dan constraint yang kecil (`nums[i] <= 1000`, hasil gabungan paling besar sekitar `10^7`) menjamin hasilnya muat di `int` tanpa risiko overflow. Cek constraint seperti ini sebelum memakai `Integer.parseInt`: kalau nilai elemen lebih besar, hasil gabungannya bisa melewati batas `int` dan perlu `long`. 🎯

# 1464. Maximum Product of Two Elements in an Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sorting
- **Link**: [Problem](https://leetcode.com/problems/maximum-product-of-two-elements-in-an-array/)
- **Solution**: [Code](../../leetcode/MaximumProductOfTwoElementsInAnArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` (ukuran ≥ 2), pilih dua indeks berbeda `i` dan `j`, lalu maksimalkan `(nums[i] - 1) * (nums[j] - 1)`.

Contoh:

- `nums = [3,4,5,2]` → `16` (pilih `5` dan `4` → `(5-1)*(4-1) = 16`)
- `nums = [1,5,4,5]` → `16` (pilih dua `5` → `(5-1)*(5-1) = 16`)
- `nums = [3,7]` → `12` (`(7-1)*(3-1) = 12`)

______________________________________________________________________

## 💡 Intuition

Karena `nums[i] >= 1` untuk semua elemen (lihat constraint), fungsi `f(x) = x - 1` **monoton naik**. Artinya, elemen asli terbesar akan menghasilkan `(x-1)` terbesar juga. Untuk memaksimalkan hasil kali dua bilangan **non-negatif** (`x-1 >= 0`), caranya sama seperti banyak soal "maksimalkan produk dua elemen" lain: **ambil dua elemen terbesar** dari array, lalu kurangi masing-masing dengan 1 dan kalikan.

Ini persis pola yang sama dengan soal _Maximum Product of Two Digits_: cukup **track dua nilai terbesar** dalam satu kali pass, tidak perlu sorting.

______________________________________________________________________

## 🔍 Approach

### Track Two Maximums — Single Pass

1. Inisialisasi `max1 = 0`, `max2 = 0` (aman karena constraint menjamin `nums[i] >= 1`, jadi nilai asli pasti akan menggantikan 0 di iterasi-iterasi awal).
1. Loop tiap elemen `i` di `nums`:
   - Jika `i > max1` → `max2 = max1`, `max1 = i`
   - Else jika `i > max2` → `max2 = i`
1. Return `(max1 - 1) * (max2 - 1)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh array |
| **Space** | O(1) — hanya dua variabel tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,4,5,2]`

`max1=0, max2=0`

| i | i>max1? | i>max2? | max1 | max2 |
| --- | ------- | -------------- | ---- | ---- |
| 3 | 3>0 ✅ | max2=0, max1=3 | 3 | 0 |
| 4 | 4>3 ✅ | max2=3, max1=4 | 4 | 3 |
| 5 | 5>4 ✅ | max2=4, max1=5 | 5 | 4 |
| 2 | 2>5? ❌ | 2>4? ❌ | 5 | 4 |

`(max1-1)*(max2-1) = (5-1)*(4-1) = 4*3 = 12` ✅

______________________________________________________________________

**Input:** `nums = [1,5,4,5]`

| i | i>max1? | i>max2? | max1 | max2 |
| --- | ------- | -------------- | ---- | ---- |
| 1 | 1>0 ✅ | max2=0, max1=1 | 1 | 0 |
| 5 | 5>1 ✅ | max2=1, max1=5 | 5 | 1 |
| 4 | 4>5? ❌ | 4>1? ✅ | 5 | 4 |
| 5 | 5>5? ❌ | 5>4? ✅ | 5 | 5 |

`(5-1)*(5-1) = 4*4 = 16` ✅

______________________________________________________________________

**Input:** `nums = [3,7]`

| i | i>max1? | i>max2? | max1 | max2 |
| --- | ------- | -------------- | ---- | ---- |
| 3 | 3>0 ✅ | max2=0, max1=3 | 3 | 0 |
| 7 | 7>3 ✅ | max2=3, max1=7 | 7 | 3 |

`(7-1)*(3-1) = 6*2 = 12` ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array hanya 2 elemen (`nums=[3,7]`) → langsung `max1` dan `max2` terisi setelah 2 iterasi
- [ ] Ada duplikat nilai terbesar (`nums=[1,5,4,5]`) → `max1` dan `max2` bisa sama-sama `5`, tetap valid karena soal mengizinkan `i != j` bukan `nums[i] != nums[j]`
- [ ] Semua elemen bernilai `1` (nilai minimum sesuai constraint) → `max1=max2=1` → hasil `(1-1)*(1-1)=0`
- [ ] Elemen terkecil yang mungkin adalah `1`, sehingga inisialisasi `max1=max2=0` selalu aman dan pasti tertimpa oleh nilai asli — beda dengan kasus di mana elemen array bisa `0` atau negatif, di situ inisialisasi `0` akan berbahaya

______________________________________________________________________

## 🔧 Kenapa Ambil Dua Nilai Terbesar (Bukan Dua Nilai dengan `(x-1)` Terbesar Secara Terpisah)?

Karena `f(x) = x - 1` adalah fungsi **monoton naik**, urutan elemen berdasarkan nilai asli (`nums[i]`) akan **sama persis** dengan urutan setelah dikurangi 1. Jadi dua elemen dengan `nums[i]` terbesar otomatis juga menghasilkan dua nilai `(nums[i]-1)` terbesar — tidak perlu menghitung `x-1` dulu baru dibandingkan, cukup bandingkan `x` aslinya lalu kurangi 1 di akhir. Ini yang membuat solusi bisa langsung memakai pola "track two max" standar tanpa modifikasi.

______________________________________________________________________

## 🔧 Alternatif: Sort Lalu Ambil Dua Elemen Terakhir

```java
public int maxProduct(int[] nums) {
    Arrays.sort(nums);
    int n = nums.length;
    return (nums[n - 1] - 1) * (nums[n - 2] - 1);
}
```

| Approach | Time | Space |
| --------------------- | ---------- | --------------- |
| Track two max (kode) | O(n) | O(1) |
| Sort lalu ambil ujung | O(n log n) | O(log n)–O(n)\* |

\*tergantung implementasi sorting yang dipakai bahasa/JVM-nya.

Kode asli lebih efisien secara asimptotik karena tidak perlu sorting — cukup satu pass linier untuk menemukan dua nilai terbesar.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi lain dari pola **"track two maximums in one pass"**, dengan tambahan transformasi `(x-1)` yang ternyata tidak mengubah strategi sama sekali karena sifatnya monoton naik dan constraint menjamin non-negativitas hasil. Pola ini juga sama persis dengan soal _Maximum Product of Two Digits_ dan _Kth Largest Element_ — begitu suatu soal berbentuk "cari dua nilai terbesar untuk dikalikan/dijumlahkan", cek dulu apakah transformasinya monoton sebelum langsung sorting; kalau iya, single-pass tracking sudah cukup. 🎯

# Prime Number of Set Bits in Binary Representation

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Bit Manipulation
- **Link**: [Problem](https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/)
- **Solution**: [Code](../../leetcode/PrimeNumberOfSetBitsInBinaryRepresentation.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua integer `left` dan `right`. Hitung berapa banyak bilangan di rentang `[left, right]` (inklusif) yang **jumlah bit `1` (set bit)** pada representasi binernya adalah **bilangan prima**.

Constraint: `1 <= left <= right <= 10^6` dan `0 <= right - left <= 10^4`.

Contoh:

- `left = 6, right = 10` → `4`
   - `6 = 110` (2 set bit, prima), `7 = 111` (3, prima), `8 = 1000` (1, bukan prima), `9 = 1001` (2, prima), `10 = 1010` (2, prima).
- `left = 10, right = 15` → `5`
   - `10 = 1010` (2), `11 = 1011` (3), `12 = 1100` (2), `13 = 1101` (3), `14 = 1110` (3) prima semua; `15 = 1111` (4) bukan prima.

______________________________________________________________________

## 💡 Intuition

Soal ini murni **enumerasi**: periksa tiap bilangan di rentang, hitung jumlah set bit-nya, lalu cek apakah jumlah itu prima. Karena rentangnya paling lebar `10^4 + 1` bilangan, enumerasi langsung sudah cepat.

Dua bagian:

1. **Menghitung set bit**: `Integer.bitCount(x)` bawaan Java.
1. **Mengecek prima**: uji pembagian dengan semua `i` dari `2` sampai `√n`. Kalau ada yang membagi habis, bukan prima. Perlu diingat bahwa `0` dan `1` bukan bilangan prima (itulah fungsi `if (value <= 1) return false`).

______________________________________________________________________

## 🔍 Approach

### Enumerasi, `bitCount`, Lalu Uji Prima

1. `ans = 0`.
1. Untuk tiap `x` dari `left` sampai `right`:
   - `count = Integer.bitCount(x)`.
   - Kalau `isPrime(count)` → `ans++`.
1. Kembalikan `ans`.

**Fungsi `isPrime(value)`:**

1. Kalau `value <= 1` → `false`.
1. Untuk `i` dari `2` selama `i <= √value`: kalau `value % i == 0` → `false`.
1. Selain itu → `true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------- |
| **Time** | O(R × √b) — R = `right - left + 1` (maksimal `10001`), b = jumlah set bit (maksimal `20`), jadi `√b` sangat kecil; praktis O(R) |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `left = 6, right = 10`

| x | Biner | Jumlah set bit | Prima? | ans |
| --- | ------ | -------------- | ------ | --- |
| 6 | `110` | 2 | ya | 1 |
| 7 | `111` | 3 | ya | 2 |
| 8 | `1000` | 1 | tidak | 2 |
| 9 | `1001` | 2 | ya | 3 |
| 10 | `1010` | 2 | ya | 4 |

**Output: `4`** ✅

______________________________________________________________________

**Input:** `left = 10, right = 15`

| x | Biner | Jumlah set bit | Prima? | ans |
| --- | ------ | -------------- | ------ | --- |
| 10 | `1010` | 2 | ya | 1 |
| 11 | `1011` | 3 | ya | 2 |
| 12 | `1100` | 2 | ya | 3 |
| 13 | `1101` | 3 | ya | 4 |
| 14 | `1110` | 3 | ya | 5 |
| 15 | `1111` | 4 | tidak | 5 |

**Output: `5`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Pangkat dua (`1, 2, 4, 8, ...`) → tepat **satu** set bit, dan `1` **bukan prima**, jadi tidak pernah dihitung
- [ ] `left == right` → satu bilangan saja, loop berjalan sekali
- [ ] Jumlah set bit `2` atau `3` → prima, jadi bilangan seperti `3`, `5`, `6`, `7` ikut dihitung
- [ ] Jumlah set bit `4` atau `6` → bukan prima (komposit), tidak dihitung
- [ ] `isPrime(2)` dan `isPrime(3)` → loop `i = 2` tidak berjalan (`2 > √2` dan `2 > √3`), sehingga mengembalikan `true` dengan benar
- [ ] Jumlah set bit nol tidak mungkin terjadi, karena `left >= 1`

______________________________________________________________________

## 🔧 Observasi: Jumlah Set Bit Paling Banyak 20, Jadi Prima yang Relevan Hanya 8

Karena `right <= 10^6` dan `10^6 < 2^20 = 1.048.576`, setiap bilangan di rentang punya **paling banyak 20 bit**, sehingga jumlah set bit-nya paling banyak `20`. Bilangan prima yang `<= 20` hanya delapan: **`2, 3, 5, 7, 11, 13, 17, 19`**. Jadi pengujian prima dengan pembagian sebenarnya tidak diperlukan; cukup **tabel pencarian (lookup)** berukuran tetap.

```java
public int countPrimeSetBits(int left, int right) {
    boolean[] isPrime = new boolean[21];
    for (int p : new int[]{2, 3, 5, 7, 11, 13, 17, 19})
        isPrime[p] = true;

    int ans = 0;
    for (int x = left; x <= right; x++)
        if (isPrime[Integer.bitCount(x)])
            ans++;
    return ans;
}
```

Pengecekan prima menjadi akses array `O(1)`. Ini lagi-lagi pola **direct address table**: rentang kemungkinan nilai (`0..20`) kecil dan diketahui dari constraint.

______________________________________________________________________

## 🔧 Trik Bitmask: Tabel Prima dalam Satu Angka

Tabel delapan prima itu juga bisa disimpan di **satu integer**, dengan bit ke-`k` bernilai `1` kalau `k` prima:

```java
private static final int PRIME_MASK = 665772;   // = 2^2 + 2^3 + 2^5 + 2^7 + 2^11 + 2^13 + 2^17 + 2^19

public int countPrimeSetBits(int left, int right) {
    int ans = 0;
    for (int x = left; x <= right; x++)
        if (((PRIME_MASK >> Integer.bitCount(x)) & 1) == 1)
            ans++;
    return ans;
}
```

`PRIME_MASK >> k` menggeser bit ke-`k` ke posisi paling kanan, dan `& 1` mengambilnya. Cek: untuk `k = 2`, `665772 >> 2 = 166443` (ganjil) → bit `1`, prima ✅; untuk `k = 4`, `665772 >> 4 = 41610` (genap) → bit `0`, bukan prima ✅. Versi ini tidak butuh array sama sekali.

| Approach | Time | Space | Catatan |
| ------------------------------------------ | ---------- | ----- | ---------------------------------------- |
| Uji prima dengan pembagian (kode asli) | O(R × √b) | O(1) | Umum, tidak bergantung pada batas set bit |
| Tabel `boolean[21]` | O(R) | O(1) | Memanfaatkan batas 20 bit |
| Bitmask `665772` | O(R) | O(1) | Satu konstanta, paling ringkas |

______________________________________________________________________

## 🔧 Catatan Kecil: `Math.sqrt` di Kondisi Loop

Di kode asli, kondisi `i <= Math.sqrt(value)` memanggil `Math.sqrt` pada **setiap** iterasi dan membandingkan `int` dengan `double`. Idiom yang lebih umum dan menghindari pembulatan floating-point adalah `i * i <= value`:

```java
for (int i = 2; i * i <= value; i++)
    if (value % i == 0) return false;
```

Untuk nilai sekecil ini (`value <= 20`) kedua cara benar, tapi `i * i <= value` murni bilangan bulat dan lebih aman untuk kasus umum.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan **menghitung set bit** (`Integer.bitCount`) dengan **uji bilangan prima**. Kuncinya adalah membaca constraint: karena nilai paling besar `10^6 < 2^20`, jumlah set bit paling banyak `20`, sehingga hanya delapan bilangan prima yang relevan, dan uji prima bisa diganti tabel pencarian atau bitmask. Menggunakan batas dari constraint untuk mengganti komputasi dengan tabel kecil adalah pola yang sering muncul (*Design HashSet*, *Count Largest Group*). Perhatikan juga bahwa pangkat dua hanya punya satu set bit, dan `1` bukan bilangan prima. 🎯
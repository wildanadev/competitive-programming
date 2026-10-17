# 1399. Count Largest Group

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, Math
- **Link**: [Problem](https://leetcode.com/problems/count-largest-group/)
- **Solution**: [Code](../../leetcode/CountLargestGroup.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`. Kelompokkan bilangan `1` sampai `n` berdasarkan **jumlah digitnya** (dua bilangan satu grup kalau jumlah digitnya sama). Kembalikan **banyaknya grup** yang punya **ukuran terbesar**.

Contoh:

- `n = 13` → `4`
  - Grup: `[1,10]`, `[2,11]`, `[3,12]`, `[4,13]`, `[5]`, `[6]`, `[7]`, `[8]`, `[9]`.
  - Ukuran terbesar adalah `2`, dan ada `4` grup dengan ukuran itu.
- `n = 2` → `2` (grup `[1]` dan `[2]`, keduanya berukuran `1`, jadi ada `2` grup terbesar)

______________________________________________________________________

## 💡 Intuition

Soal ini adalah **pengelompokan berdasarkan kunci turunan**: kuncinya adalah jumlah digit tiap bilangan, dan yang dihitung adalah **berapa banyak anggota per kunci**. Itu setara dengan **frequency counting** dengan kunci "jumlah digit".

Langkah-langkahnya:

1. Untuk tiap bilangan `1..n`, hitung jumlah digitnya, lalu naikkan hitungan untuk kunci itu.
1. Selama menghitung, lacak **ukuran grup terbesar** (`max`) yang pernah muncul.
1. Setelah semua bilangan diproses, hitung **berapa kunci** yang hitungannya sama dengan `max`.

Karena `n <= 10^4`, jumlah digit terbesar yang mungkin adalah `9 + 9 + 9 + 9 = 36` (dari `9999`). Rentang kuncinya kecil dan diketahui, jadi array berukuran `37` (indeks `0..36`) sudah cukup sebagai **direct address table**, tanpa perlu `HashMap`.

______________________________________________________________________

## 🔍 Approach

### Array Frekuensi Berindeks Jumlah Digit

1. `digits = new int[37]`, `max = 0`, `ans = 0`.
1. Untuk tiap `i` dari `1` sampai `n`:
   - `sumD = sumOfDigits(i)` (jumlah digit dengan `% 10` dan `/ 10` berulang).
   - `digits[sumD]++`.
   - `max = Math.max(max, digits[sumD])`.
1. Loop semua elemen `digits`: untuk tiap elemen yang nilainya `== max`, naikkan `ans`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------- |
| **Time** | O(n × d) — d = jumlah digit tiap bilangan (maksimal `5`), praktis O(n) |
| **Space** | O(1) — array `digits` berukuran tetap `37` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 13`

| i | Jumlah digit | digits[jumlah] sesudah | max |
| --- | ------------ | ---------------------- | --- |
| 1 | 1 | 1 | 1 |
| 2 | 2 | 1 | 1 |
| 3 | 3 | 1 | 1 |
| 4 | 4 | 1 | 1 |
| 5 | 5 | 1 | 1 |
| 6 | 6 | 1 | 1 |
| 7 | 7 | 1 | 1 |
| 8 | 8 | 1 | 1 |
| 9 | 9 | 1 | 1 |
| 10 | 1 | 2 | 2 |
| 11 | 2 | 2 | 2 |
| 12 | 3 | 2 | 2 |
| 13 | 4 | 2 | 2 |

Isi akhir: `digits[1..4] = 2` dan `digits[5..9] = 1`. Elemen yang bernilai `max = 2`: ada `4`.

**Output: `4`** ✅

______________________________________________________________________

**Input:** `n = 2`

`digits[1] = 1`, `digits[2] = 1`, `max = 1`. Elemen yang bernilai `1`: ada `2`.

**Output: `2`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → satu grup `[1]` berukuran `1`, hasil `1`
- [ ] `n = 9` → sembilan grup masing-masing berukuran `1`, hasil `9`
- [ ] `n = 10` → grup jumlah digit `1` berisi `[1,10]` (ukuran `2`), sisanya berukuran `1`, hasil `1`
- [ ] Indeks `0` pada `digits` → tidak pernah terisi (tidak ada bilangan `>= 1` dengan jumlah digit `0`), nilainya `0`. Karena `max >= 1` untuk `n >= 1`, elemen ini tidak pernah ikut terhitung di loop terakhir
- [ ] Batas array → untuk `n <= 10^4`, jumlah digit maksimal `36`, jadi indeks maksimal `36` dan array `37` cukup. Kalau `n` boleh lebih besar (misal `99999` → jumlah digit `45`), array harus diperbesar atau diganti `HashMap`, kalau tidak akan terjadi `ArrayIndexOutOfBoundsException`

______________________________________________________________________

## 🔧 Alternatif: Hitung Jawaban dalam Satu Pass

Alih-alih menghitung `max` dulu lalu menghitung ulang lewat loop kedua, jawabannya bisa dijaga langsung selama loop utama:

```java
public int countLargestGroup(int n) {
    int[] count = new int[37];
    int max = 0, ans = 0;
    for (int i = 1; i <= n; i++) {
        int s = sumOfDigits(i);
        count[s]++;
        if (count[s] > max) {        // rekor baru
            max = count[s];
            ans = 1;                 // baru satu grup yang mencapai rekor ini
        } else if (count[s] == max) {
            ans++;                   // grup lain menyamai rekor
        }
    }
    return ans;
}
```

Ini bekerja karena hitungan sebuah grup selalu naik **satu per satu**: begitu hitungan melewati `max`, grup itu menjadi satu-satunya pemegang rekor (`ans = 1`); kalau hitungannya menyamai `max`, ia bergabung dengan pemegang rekor lain (`ans++`). Contoh `n = 13`: hingga `i = 9` setiap grup baru menyamai `max = 1` sehingga `ans` naik sampai `9`; saat `i = 10`, `count[1]` naik ke `2` (rekor baru) sehingga `max = 2`, `ans = 1`; lalu `i = 11, 12, 13` menyamai rekor, `ans` naik ke `4`.

______________________________________________________________________

## 🔧 Alternatif: Jumlah Digit Secara Inkremental (DP)

Jumlah digit sebuah bilangan bisa diturunkan dari bilangan yang lebih kecil: `sum(i) = sum(i / 10) + i % 10`. Jadi `sumOfDigits` tidak perlu mengulang `% 10` dan `/ 10` untuk tiap bilangan:

```java
int[] sum = new int[n + 1];
for (int i = 1; i <= n; i++) {
    sum[i] = sum[i / 10] + i % 10;   // contoh: sum[13] = sum[1] + 3 = 4
    count[sum[i]]++;
}
```

Hasilnya sama, dengan biaya `O(1)` per bilangan, tapi memakai memori tambahan `O(n)`. Untuk `n <= 10^4`, perbedaannya tidak terasa.

| Approach | Time | Space | Catatan |
| ----------------------------------------- | -------- | ----- | ------------------------------------ |
| Hitung `max`, lalu loop kedua (kode asli) | O(n × d) | O(1) | Dua fase yang jelas |
| Jaga `ans` selama loop (satu pass) | O(n × d) | O(1) | Tanpa loop kedua |
| Jumlah digit inkremental | O(n) | O(n) | `sum(i)` diturunkan dari `sum(i/10)` |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh **frequency counting berdasarkan kunci turunan** (jumlah digit): hitung kunci tiap elemen, naikkan hitungan, lacak maksimum sambil jalan, lalu hitung berapa kunci yang menyentuh maksimum itu. Karena rentang kunci kecil dan diketahui dari constraint (`0..36`), array berukuran tetap menggantikan `HashMap` (pola **direct address table** yang berulang di banyak soal sebelumnya). Perhatikan juga ketergantungan ukuran array pada constraint: `37` hanya aman karena `n <= 10^4`. 🎯

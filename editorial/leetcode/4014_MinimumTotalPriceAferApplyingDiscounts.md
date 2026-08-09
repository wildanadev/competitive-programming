# 4014. Minimum Total Price After Applying Discounts

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Greedy, Sorting
- **Link**: [Problem](https://leetcode.com/problems/minimum-total-price-after-applying-discounts/)
- **Solution**: [Code](../../leetcode/MinimumTotalPriceAfterApplyingDiscounts.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array integer `prices` dan `discounts`. `prices[i]` adalah harga item ke-`i`, dan `discounts[j]` adalah persentase diskon yang tersedia.

Aturan:

- Tiap **diskon** hanya boleh dipakai untuk **paling banyak satu item**.
- Tiap **item** hanya boleh menerima **paling banyak satu diskon**.
- Sebuah item **boleh tidak dapat diskon sama sekali**.
- Kalau diskon `d`% diterapkan ke item berharga `p`, harga akhirnya jadi `p * (100 - d) / 100` (tidak dibulatkan).

Kembalikan **jumlah harga akhir minimum** setelah diskon dialokasikan seoptimal mungkin.

Contoh:

- `prices = [10,30,21]`, `discounts = [50,60]` → `32.50000` (diskon `60%` dipakai untuk harga `30` → jadi `12`; diskon `50%` dipakai untuk harga `21` → jadi `10.5`; harga `10` tidak dapat diskon, tetap `10`; total `12+10.5+10=32.5`)

______________________________________________________________________

## 💡 Intuition

Harga akhir tiap item = `price * (1 - discount/100)`. Kalau dijumlahkan semua item:

```
totalFinal = sum(price) - sum(price * discount / 100)
```

Karena `sum(price)` itu **tetap** (tidak berubah apapun strategi alokasi diskonnya), untuk **meminimalkan** `totalFinal`, kita justru harus **memaksimalkan** `sum(price * discount / 100)` — yaitu total "potongan harga" yang berhasil didapat.

Ini jadi soal klasik: _dari dua himpunan angka (harga dan diskon), pasangkan satu-satu supaya jumlah hasil kalinya maksimal_. Berdasarkan **rearrangement inequality**, jumlah hasil kali dua deret angka akan **maksimal** kalau kedua deret diurutkan **searah** (sama-sama naik, lalu dipasangkan berurutan) — bukan dipasangkan sembarangan.

Karena jumlah diskon bisa **lebih sedikit** dari jumlah item, sebagian item pasti tidak kebagian diskon (diskon efektifnya `0`). Supaya total hasil kali tetap maksimal, diskon yang tersedia (walau jumlahnya lebih sedikit) harus dipasangkan ke harga-harga **terbesar** — karena harga besar dikali diskon besar menghasilkan potongan absolut yang lebih besar dibanding harga kecil dikali diskon yang sama.

______________________________________________________________________

## 🔍 Approach

### Greedy — Pasangkan Harga Terbesar dengan Diskon Terbesar

1. Urutkan `prices` naik dan `discounts` naik.
1. Mulai dari **ujung belakang kedua array** (nilai terbesar), pasangkan harga terbesar yang tersisa dengan diskon terbesar yang tersisa.
1. Untuk tiap pasangan, hitung harga akhir `price * (100 - discount) / 100` dan akumulasikan ke `ans`.
1. Kalau diskon sudah habis (`j < 0`) sementara masih ada harga tersisa, sisa harga tersebut dihitung **tanpa diskon** (`discount = 0`).
1. Lanjutkan sampai semua harga (`i`) selesai diproses, lalu kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n log n + m log m) — n = `prices.length`, m = `discounts.length`, dominan dari sorting |
| **Space** | O(log n) – O(n) — tergantung implementasi sorting yang dipakai (in-place sort untuk primitive array di Java umumnya dual-pivot quicksort) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `prices = [10,30,21]`, `discounts = [50,60]`

- `prices` diurutkan → `[10, 21, 30]`
- `discounts` diurutkan → `[50, 60]`
- `i = 2` (index terakhir prices), `j = 1` (index terakhir discounts)

| Iterasi | i | j | price dipakai | discount dipakai | Hasil | ans (akumulasi) |
| ------- | --- | --- | ------------- | ---------------- | ------------------------ | --------------- |
| 1 | 2 | 1 | 30 | 60 | `30*(100-60)/100 = 12` | 12 |
| 2 | 1 | 0 | 21 | 50 | `21*(100-50)/100 = 10.5` | 22.5 |
| 3 | 0 | -1 | 10 | 0 (habis) | `10*(100-0)/100 = 10` | 32.5 |

**Output: `32.50000`** ✅

______________________________________________________________________

**Input:** `prices = [10]`, `discounts = [50,60]` (diskon lebih banyak dari item)

- `prices` sorted → `[10]`, `discounts` sorted → `[50, 60]`
- `i = 0`, `j = 1`: pasangkan harga `10` dengan diskon **terbesar yang tersedia**, yaitu `60` (bukan `50`) → `10*(100-60)/100 = 4`.
- Diskon `50` tidak pernah terpakai karena hanya ada 1 item.

**Output: `4.00000`** — menegaskan bahwa kalau diskon lebih banyak dari item, yang dipakai selalu **diskon terbesar** yang tersedia, sisanya diabaikan begitu saja (tidak pernah diproses karena loop berhenti saat `i < 0`).

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `discounts.length < prices.length` → sebagian item (yang harganya paling kecil setelah diurutkan) tidak kebagian diskon sama sekali, dihitung dengan `discount = 0`
- [ ] `discounts.length > prices.length` → hanya diskon-diskon **terbesar** yang benar-benar terpakai, sisanya tidak berpengaruh ke hasil
- [ ] `discounts.length == prices.length` → semua item mendapat diskon, tidak ada yang di-skip
- [ ] Ada diskon `0` di antara `discounts` → efeknya sama saja dengan tidak diberi diskon untuk item yang menerimanya
- [ ] Satu item saja (`prices.length == 1`) → diskon terbesar yang tersedia otomatis dipasangkan ke item itu

______________________________________________________________________

## 🔧 Kenapa Harus Sorting Naik Lalu Dipasangkan dari Belakang (Bukan Sorting Turun)?

Kedua cara ini **secara logika setara** — tujuannya sama, yaitu memasangkan nilai terbesar dengan terbesar, kedua terbesar dengan kedua terbesar, dst. Kode ini memilih sorting **naik** lalu mengambil dari **indeks belakang ke depan** (`i--`, `j--`), yang secara efektif sama dengan sorting turun lalu diambil dari depan. Pilihan ini murni gaya implementasi — `Arrays.sort` di Java untuk tipe primitif (`int[]`) hanya menyediakan sorting ascending secara langsung tanpa comparator, jadi lebih praktis sort naik lalu iterasi mundur, dibanding harus konversi ke `Integer[]` supaya bisa pakai `Comparator.reverseOrder()`.

______________________________________________________________________

## 🔧 Kenapa Rearrangement Inequality Berlaku di Sini?

Secara intuitif: bayangkan ada dua pasangan alokasi berbeda yang **menukar** diskon antara dua item. Kalau harga besar `P1 > P2` dan diskon besar `D1 > D2` dipasangkan **bersilang** (`P1` dengan `D2`, `P2` dengan `D1`), maka total potongan yang didapat adalah `P1*D2 + P2*D1`. Bandingkan dengan pemasangan **searah** (`P1` dengan `D1`, `P2` dengan `D2`): totalnya `P1*D1 + P2*D2`. Selisih keduanya adalah `(P1-P2)*(D1-D2)`, yang **selalu non-negatif** karena `P1>P2` dan `D1>D2` sama-sama positif — artinya pemasangan searah **selalu setidaknya sama baik**, tidak pernah lebih buruk. Inilah bukti informal kenapa strategi "pasangkan besar dengan besar" selalu optimal untuk memaksimalkan total hasil kali.

______________________________________________________________________

## 🔧 Alternatif: Sorting Descending Eksplisit

```java
public double minPrice(int[] prices, int[] discounts) {
    Integer[] boxedPrices = Arrays.stream(prices).boxed().toArray(Integer[]::new);
    Integer[] boxedDiscounts = Arrays.stream(discounts).boxed().toArray(Integer[]::new);
    Arrays.sort(boxedPrices, Collections.reverseOrder());
    Arrays.sort(boxedDiscounts, Collections.reverseOrder());

    double ans = 0;
    for (int i = 0; i < boxedPrices.length; i++) {
        int discount = i < boxedDiscounts.length ? boxedDiscounts[i] : 0;
        ans += (double) boxedPrices[i] * (100 - discount) / 100;
    }
    return ans;
}
```

Versi ini sort **descending** secara eksplisit lalu iterasi maju dari depan, secara hasil identik dengan kode asli. Trade-off-nya: perlu boxing ke `Integer[]` karena `int[]` primitif di Java tidak mendukung `Comparator` langsung, sehingga sedikit lebih boros memori dibanding sorting `int[]` primitif seperti kode asli.

| Approach | Time | Space | Perlu Boxing? |
| ---------------------------------------- | ---------- | ------------- | ------------- |
| Sort ascending + iterasi mundur (asli) | O(n log n) | O(log n)–O(n) | Tidak |
| Sort descending eksplisit + iterasi maju | O(n log n) | O(n) (boxing) | Ya |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi konkret dari **rearrangement inequality**: untuk memaksimalkan (atau meminimalkan) jumlah hasil kali dua deret angka yang dipasangkan satu-satu, urutkan kedua deret dan pasangkan **searah** (besar dengan besar) untuk memaksimalkan, atau **berlawanan arah** (besar dengan kecil) untuk meminimalkan. Trik mengubah soal "minimalkan harga akhir" menjadi "maksimalkan total potongan" (dengan memisahkan `sum(price)` yang konstan) adalah pola umum di soal-soal optimasi greedy — sering disebut _complementary counting_ atau _invariant extraction_. Pola pairing seperti ini juga muncul di soal-soal seperti _Maximum Sum of Products of Two Arrays_ atau varian _Task Scheduler_ dengan bobot. 🎯

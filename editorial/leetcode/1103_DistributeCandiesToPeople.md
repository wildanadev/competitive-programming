# 1103. Distribute Candies to People

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Simulation
- **Link**: [Problem](https://leetcode.com/problems/distribute-candies-to-people/)
- **Solution**: [Code](../../leetcode/DistributeCandiesToPeople.java)

______________________________________________________________________

## 📄 Problem Summary

Kamu punya `candies` permen dan `num_people` orang yang berbaris. Permen dibagikan dalam putaran:

- Orang pertama dapat `1` permen, orang kedua `2`, ..., orang ke-`num_people` dapat `num_people` permen.
- Lalu kembali ke orang pertama, yang sekarang dapat `num_people + 1`, orang kedua `num_people + 2`, dan seterusnya (jumlah yang diberikan **terus naik satu per orang**).
- Kalau sisa permen **kurang** dari jumlah yang seharusnya diberikan, orang itu mendapat **semua sisa permen**, dan pembagian selesai.

Kembalikan array berukuran `num_people` yang berisi total permen tiap orang.

Contoh:

- `candies = 7, num_people = 4` → `[1,2,3,1]` (`1 + 2 + 3 = 6`, sisa `1` permen untuk orang keempat yang seharusnya dapat `4`)
- `candies = 10, num_people = 3` → `[5,2,3]` (putaran pertama `1, 2, 3`, lalu orang pertama dapat `4` lagi, total `5`)

Constraint: `1 <= candies <= 10^9`, `1 <= num_people <= 1000`.

______________________________________________________________________

## 💡 Intuition

Soal ini **simulasi langsung**. Perhatikan pola jumlah permen yang seharusnya diberikan pada langkah ke-`i` (mulai dari `0`): **`i + 1`**. Dan orang yang menerimanya adalah **`i % num_people`** (berputar kembali ke awal barisan setelah orang terakhir).

Jadi satu loop dengan satu penghitung `i` sudah cukup, tanpa loop bersarang untuk "putaran". Pada tiap langkah:

- Berikan `min(candies, i + 1)` ke orang ke-`i % num_people`. `min` menangani aturan "kalau sisa kurang dari yang seharusnya, berikan semua sisa".
- Kurangi sisa permen dengan `i + 1`.

Perhatikan bahwa pengurangan memakai `i + 1` (jumlah yang **seharusnya** diberikan), bukan jumlah yang **sebenarnya** diberikan. Di langkah terakhir, sisa bisa menjadi **negatif**, tapi itu tidak masalah: loop berhenti karena `candies > 0` sudah tidak benar, dan jumlah yang diberikan di langkah itu sudah dihitung dengan `min` sebelum pengurangan.

______________________________________________________________________

## 🔍 Approach

### Satu Loop dengan Penghitung `i`

1. `ans = new int[num_people]`.
1. Untuk `i = 0, 1, 2, ...` selama `candies > 0`:
   - `ans[i % num_people] += Math.min(candies, i + 1)`.
   - `candies -= i + 1`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------- |
| **Time** | O(√candies) — setelah `k` langkah, total yang dibagikan `k(k+1)/2`, jadi loop berhenti saat `k ≈ √(2 × candies)` (sekitar `44.700` langkah untuk `10^9`) |
| **Space** | O(num_people) — array hasil |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `candies = 10, num_people = 3`

| i | Penerima (`i % 3`) | Seharusnya (`i + 1`) | Diberikan (`min`) | Sisa sesudah | ans |
| --- | ------------------ | -------------------- | ----------------- | ------------ | --------- |
| 0 | orang 0 | 1 | 1 | 9 | `[1,0,0]` |
| 1 | orang 1 | 2 | 2 | 7 | `[1,2,0]` |
| 2 | orang 2 | 3 | 3 | 4 | `[1,2,3]` |
| 3 | orang 0 | 4 | `min(4,4) = 4` | 0 | `[5,2,3]` |

Sisa `0`, loop berhenti.

**Output: `[5,2,3]`** ✅

______________________________________________________________________

**Input:** `candies = 7, num_people = 4`

| i | Penerima (`i % 4`) | Seharusnya | Diberikan (`min`) | Sisa sesudah | ans |
| --- | ------------------ | ---------- | ----------------- | ------------ | ----------- |
| 0 | orang 0 | 1 | 1 | 6 | `[1,0,0,0]` |
| 1 | orang 1 | 2 | 2 | 4 | `[1,2,0,0]` |
| 2 | orang 2 | 3 | 3 | 1 | `[1,2,3,0]` |
| 3 | orang 3 | 4 | `min(1,4) = 1` | `1 - 4 = -3` | `[1,2,3,1]` |

Sisa menjadi negatif (`-3`), `candies > 0` salah, loop berhenti.

**Output: `[1,2,3,1]`** ✅ (orang keempat hanya dapat `1`, yaitu semua sisa)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `candies < num_people` (misal `candies = 2, num_people = 5`) → orang pertama dapat `1`, orang kedua dapat sisa `1` (bukan `2`), hasil `[1,1,0,0,0]`
- [ ] `num_people = 1` → semua permen jatuh ke satu orang, `ans[0] = candies`
- [ ] Sisa permen pas habis tepat di akhir sebuah giliran (seperti `10, 3`) → tidak ada pembagian parsial, penerima terakhir mendapat jumlah penuh
- [ ] `candies` sangat besar (`10^9`) → sekitar `44.700` iterasi, cepat. Nilai di `ans` paling besar `10^9` muat di `int`
- [ ] Sisa negatif di akhir → aman, karena `min` dihitung sebelum pengurangan, dan loop berhenti
- [ ] Jumlah permen yang seharusnya naik tanpa batas (`i + 1`) tidak di-reset tiap putaran, hanya penerimanya yang berputar lewat `% num_people`

______________________________________________________________________

## 🔧 Alternatif: Rumus Matematis (O(num_people))

Karena total permen yang dibagikan penuh setelah `k` langkah adalah `1 + 2 + ... + k = k(k+1)/2`, kita bisa menghitung langsung:

- `k` = banyaknya langkah yang diberi penuh: `k` terbesar dengan `k(k+1)/2 <= candies`.
- `rounds = k / num_people` putaran penuh, `r = k % num_people` orang yang dapat jatah pada putaran parsial terakhir.
- `leftover = candies - k(k+1)/2` permen sisa, untuk orang ke-`r`.

Orang ke-`j` (mulai `0`) mendapat `rounds × (j + 1) + num_people × rounds × (rounds − 1) / 2` dari putaran penuh, ditambah `(j + 1) + rounds × num_people` kalau `j < r`, ditambah `leftover` kalau `j == r`.

```java
public int[] distributeCandies(int candies, int num_people) {
    int n = num_people;
    long k = (long) ((Math.sqrt(8.0 * candies + 1) - 1) / 2);
    while (k * (k + 1) / 2 > candies) k--;              // koreksi bila pembulatan terlalu besar
    while ((k + 1) * (k + 2) / 2 <= candies) k++;       // koreksi bila terlalu kecil

    long rounds = k / n;
    int r = (int) (k % n);
    long leftover = candies - k * (k + 1) / 2;

    int[] ans = new int[n];
    for (int j = 0; j < n; j++) {
        long total = rounds * (j + 1) + (long) n * rounds * (rounds - 1) / 2;
        if (j < r) total += (j + 1) + rounds * n;       // jatah pada putaran parsial
        if (j == r) total += leftover;                   // semua sisa permen
        ans[j] = (int) total;
    }
    return ans;
}
```

Cek `candies = 10, num_people = 3`: `k = 4` (`4 × 5 / 2 = 10`), `rounds = 1`, `r = 1`, `leftover = 0`. Orang `0`: `1 × 1 + 0 = 1`, `j < r` → `+ 1 + 3 = 4`, total `5`. Orang `1`: `1 × 2 = 2`, `j == r` → `+ 0`, total `2`. Orang `2`: `1 × 3 = 3`. Hasil `[5,2,3]` ✅. Cek `candies = 7, num_people = 4`: `k = 3`, `rounds = 0`, `r = 3`, `leftover = 1`. Orang `0, 1, 2` mendapat `1, 2, 3` (lewat `j < r`), orang `3` mendapat `leftover = 1`. Hasil `[1,2,3,1]` ✅.

Karena simulasi hanya butuh sekitar `44.700` iterasi untuk batas terbesar, rumus ini **tidak diperlukan** untuk lolos; ia hanya menunjukkan bahwa soal ini punya struktur aritmatika yang bisa diselesaikan tanpa loop panjang.

| Approach | Time | Space | Catatan |
| ------------------------------------- | ------------ | ------------- | ------------------------------------------ |
| Simulasi satu loop (kode asli) | O(√candies) | O(num_people) | Sederhana dan jelas |
| Rumus jumlah deret aritmatika | O(num_people) | O(num_people) | Lebih rumit, rawan salah di pembulatan |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menunjukkan cara menyederhanakan simulasi berputar: **satu penghitung langkah `i`** menentukan sekaligus **jumlah yang diberikan** (`i + 1`) dan **penerimanya** (`i % num_people`), sehingga tidak perlu loop bersarang untuk putaran. Dua detail yang perlu diperhatikan: `min(candies, i + 1)` menangani pemberian parsial di akhir, dan pengurangan memakai `i + 1` (bukan jumlah yang sebenarnya diberikan), yang aman karena loop berhenti begitu sisa tidak positif. Pola indeks melingkar dengan `%` ini sama dengan yang dipakai di *Distance Between Bus Stops* dan *Count Good Cyclic Rotations*. 🎯
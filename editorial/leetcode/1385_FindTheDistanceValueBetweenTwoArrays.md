# 1385. Find the Distance Value Between Two Arrays

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers, Binary Search, Sorting
- **Link**: [Problem](https://leetcode.com/problems/find-the-distance-value-between-two-arrays/)
- **Solution**: [Code](../../leetcode/FindTheDistanceValueBetweenTwoArrays.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array `arr1` dan `arr2` dan sebuah integer `d`. **Distance value** antara kedua array adalah **jumlah elemen `arr1[i]`** yang **tidak punya** elemen `arr2[j]` dengan jarak `|arr1[i] - arr2[j]| <= d`. Dengan kata lain, `arr1[i]` dihitung kalau **semua** elemen `arr2` berjarak **lebih dari `d`** darinya.

Contoh:

- `arr1 = [4,5,8], arr2 = [10,9,1,8], d = 2` → `2` (`4` dan `5` dihitung; `8` tidak, karena `8` ada di `arr2` dengan jarak `0`)
- `arr1 = [1,4,2,3], arr2 = [-4,-3,6,10,20,30], d = 3` → `2` (`1` dan `2` dihitung; `4` berjarak `2` dari `6`, dan `3` berjarak tepat `3` dari `6`, keduanya `<= 3`)
- `arr1 = [2,1,100,3], arr2 = [-5,-2,10,-3,7], d = 6` → `1` (hanya `100` yang jauh dari semua elemen `arr2`)

______________________________________________________________________

## 💡 Intuition

Soal ini langsung menerjemahkan definisi jadi kode. Untuk tiap `arr1[i]`, kita periksa **setiap** elemen `arr2`. Kalau ada satu saja yang jaraknya `<= d`, elemen itu **tidak dihitung**. Kalau tidak ada satupun, elemen itu dihitung.

Cara pandang yang membantu: `arr1[i]` dihitung kalau **jarak terdekatnya ke `arr2` lebih besar dari `d`**. Perhatikan juga batasnya: jarak yang **tepat sama dengan `d`** sudah dianggap konflik (kondisinya `<= d`, bukan `< d`).

Karena ukuran array kecil (`<= 500`), pemeriksaan semua pasangan (`O(n × m)`, maksimal `250.000` perbandingan) sudah cukup cepat.

______________________________________________________________________

## 🔍 Approach

### Brute Force: Periksa Semua Pasangan

1. `ans = 0`.
1. Untuk tiap `arr1[i]`:
   - `isSafe = true`.
   - Untuk tiap `arr2[j]`: kalau `Math.abs(arr1[i] - arr2[j]) <= d` → `isSafe = false`.
   - Kalau `isSafe` masih `true` setelah semua `arr2` diperiksa → `ans++`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------ |
| **Time** | O(n × m) — n = `arr1.length`, m = `arr2.length` |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `arr1 = [4,5,8], arr2 = [10,9,1,8], d = 2`

| arr1[i] | Selisih mutlak ke `10, 9, 1, 8` | Jarak terdekat | Ada yang `<= 2`? | Dihitung? |
| ------- | ------------------------------- | -------------- | ---------------- | --------- |
| 4 | 6, 5, 3, 4 | 3 | tidak | ya |
| 5 | 5, 4, 4, 3 | 3 | tidak | ya |
| 8 | 2, 1, 7, 0 | 0 | ya | tidak |

**Output: `2`** ✅

______________________________________________________________________

**Input:** `arr1 = [1,4,2,3], arr2 = [-4,-3,6,10,20,30], d = 3`

| arr1[i] | Jarak terdekat ke `arr2` | Ada yang `<= 3`? | Dihitung? |
| ------- | ------------------------ | ---------------- | --------- |
| 1 | `4` (ke `-3`) | tidak | ya |
| 4 | `2` (ke `6`) | ya | tidak |
| 2 | `4` (ke `-3` dan ke `6`) | tidak | ya |
| 3 | `3` (ke `6`) | ya (`3 <= 3`) | tidak |

**Output: `2`** ✅ (baris `3` menunjukkan batasnya: jarak **tepat** `d` tetap dianggap konflik)

______________________________________________________________________

**Input:** `arr1 = [2,1,100,3], arr2 = [-5,-2,10,-3,7], d = 6`

`2`: jarak ke `-2` adalah `4 <= 6` → tidak. `1`: jarak ke `-5` adalah `6 <= 6` → tidak. `100`: jarak terdekat `90` → ya. `3`: jarak ke `-2` adalah `5 <= 6` → tidak.

**Output: `1`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `d = 0` → hanya elemen `arr1` yang **persis sama** dengan suatu elemen `arr2` yang tidak dihitung, jadi jawabannya jumlah elemen `arr1` yang tidak ada di `arr2`
- [ ] Jarak tepat sama dengan `d` → dianggap konflik (`<=`), elemen tidak dihitung
- [ ] Elemen `arr1` yang berulang → tiap kemunculan dihitung sendiri-sendiri (misal `arr1 = [1,1]` dan keduanya aman, hasilnya `2`)
- [ ] Bilangan negatif → ditangani `Math.abs` pada selisih, jadi tidak perlu perlakuan khusus
- [ ] Semua elemen `arr1` jauh dari `arr2` → hasilnya `arr1.length`; semua dekat → hasilnya `0`

______________________________________________________________________

## 🔧 Catatan: Tambahkan `break` untuk Berhenti Dini

Di kode asli, setelah `isSafe` menjadi `false` loop dalam **tetap berjalan** sampai akhir `arr2`, padahal hasilnya sudah pasti (elemen itu tidak dihitung). Menambahkan `break` menghemat pekerjaan tanpa mengubah hasil:

```java
for (int j = 0; j < arr2.length; j++) {
    if (Math.abs(arr1[i] - arr2[j]) <= d) {
        isSafe = false;
        break;                 // sudah ada konflik, tidak perlu memeriksa sisanya
    }
}
```

Untuk ukuran soal ini perbedaannya tidak terasa, tapi kebiasaan yang baik untuk pola "cari apakah ada elemen yang memenuhi kondisi".

______________________________________________________________________

## 🔧 Alternatif: Urutkan `arr2` dan Binary Search, O((n + m) log m)

Kalau `arr2` **sudah diurutkan**, `arr1[x]` punya konflik **jika dan hanya jika** ada elemen `arr2` di rentang `[x - d, x + d]`. Cukup cari elemen **pertama** `arr2` yang `>= x - d` (lower bound), lalu periksa apakah elemen itu `<= x + d`.

```java
public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
    Arrays.sort(arr2);
    int ans = 0;
    for (int x : arr1) {
        int idx = lowerBound(arr2, x - d);                   // elemen pertama >= x - d
        if (idx == arr2.length || arr2[idx] > x + d)         // tidak ada elemen di [x-d, x+d]
            ans++;
    }
    return ans;
}

private int lowerBound(int[] a, int target) {
    int lo = 0, hi = a.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (a[mid] >= target) hi = mid;
        else lo = mid + 1;
    }
    return lo;
}
```

Cek contoh pertama (`arr2` terurut `[1,8,9,10]`, `d = 2`): `x = 4`: lower bound dari `2` adalah indeks `1` (nilai `8`), `8 > 6` → dihitung. `x = 5`: lower bound dari `3` adalah `8`, `8 > 7` → dihitung. `x = 8`: lower bound dari `6` adalah `8`, `8 <= 10` → tidak dihitung. Total `2` ✅.

Kalau rentang nilai kecil dan diketahui (di sini `-1000` sampai `1000`), ada juga pendekatan **array kehadiran dengan prefix sum**: tandai nilai-nilai `arr2`, hitung prefix sum, lalu tiap `x` cukup cek jumlah elemen `arr2` di `[x - d, x + d]` dalam `O(1)`. Itu `O(n + m + rentang)` tapi bergantung pada constraint nilai.

| Approach | Time | Space | Catatan |
| ------------------------------------- | -------------- | ------------ | ----------------------------------------- |
| Brute force (kode asli) | O(n × m) | O(1) | Paling sederhana, cukup untuk `n, m <= 500` |
| Sort `arr2` dan binary search | O((n+m) log m) | O(log m) | Cocok untuk ukuran besar |
| Array kehadiran dan prefix sum | O(n + m + V) | O(V) | Bergantung pada rentang nilai kecil |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah latihan dasar **"apakah ada elemen di himpunan lain yang cukup dekat"**. Untuk ukuran kecil, brute force dua loop sudah tepat dan paling mudah diverifikasi. Untuk ukuran besar, pertanyaan "ada elemen di rentang `[x - d, x + d]`?" diubah menjadi pencarian cepat: urutkan salah satu array lalu pakai **lower bound** dengan binary search. Perhatikan juga detail batas (`<= d` berarti jarak tepat `d` pun konflik) dan manfaatkan `break` begitu jawabannya sudah pasti. Pola yang sama muncul di soal-soal pencarian pasangan terdekat seperti *Minimum Absolute Difference* dan variasi two pointers pada array terurut. 🎯
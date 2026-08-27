# 724. Find Pivot Index

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Prefix Sum
- **Link**: [Problem](https://leetcode.com/problems/find-pivot-index/)
- **Solution**: [Code](../../leetcode/FindPivotIndex.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`. **Pivot index** adalah indeks `i` di mana **jumlah semua elemen di sisi kiri** `i` (`nums[0..i-1]`) sama persis dengan **jumlah semua elemen di sisi kanan** `i` (`nums[i+1..n-1]`).

Kembalikan pivot index **paling kiri (terkecil)**. Kalau tidak ada, kembalikan `-1`.

Contoh:

- `nums = [1,7,3,6,5,6]` → `3` (`nums[3]=6`; kiri `=1+7+3=11`, kanan `=5+6=11`)
- `nums = [1,2,3]` → `-1` (tidak ada indeks yang memenuhi syarat)
- `nums = [2,1,-1]` → `0` (`nums[0]=2`; kiri `=0` (kosong), kanan `=1+(-1)=0`)

______________________________________________________________________

## 💡 Intuition

Pendekatan naif untuk soal ini adalah, untuk **tiap** indeks `i`, hitung ulang jumlah sisi kiri dan kanan dari nol — ini `O(n)` per indeks, jadi `O(n²)` total. Tapi ada cara yang jauh lebih efisien memakai **prefix sum**.

Insight kuncinya: kalau kita tahu **total keseluruhan** array (`total`) dan **jumlah kumulatif dari kiri sampai sebelum `i`** (`leftTotal`), maka jumlah sisi kanan bisa dihitung **langsung** tanpa perlu iterasi ulang:

```
rightTotal = total - leftTotal - nums[i]
```

(total keseluruhan, dikurangi bagian kiri, dikurangi elemen `i` itu sendiri — sisanya otomatis jumlah bagian kanan).

Dengan begini, kita cukup **satu kali pass** dari kiri ke kanan, sambil terus mengakumulasi `leftTotal`, dan di tiap langkah langsung cek apakah `rightTotal == leftTotal` tanpa perlu hitung ulang dari awal.

______________________________________________________________________

## 🔍 Approach

### Prefix Sum — Hitung `total` Sekali, Lalu Satu Pass untuk Cari Pivot

1. Hitung `total` — jumlah seluruh elemen `nums` (satu pass awal).
1. Inisialisasi `leftTotal = 0` (jumlah kumulatif sisi kiri, dimulai dari kosong).
1. Loop `i` dari `0` sampai akhir:
   - Hitung `rightTotal = total - leftTotal - nums[i]`.
   - Kalau `rightTotal == leftTotal` → indeks `i` ini adalah pivot, langsung `return i`.
   - Kalau tidak, akumulasikan `leftTotal += nums[i]` (bergerak maju, `nums[i]` sekarang jadi bagian dari "kiri" untuk indeks berikutnya).
1. Kalau loop selesai tanpa ketemu → `return -1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n) — satu pass untuk `total`, satu pass untuk cari pivot |
| **Space** | O(1) — hanya beberapa variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,7,3,6,5,6]`

`total = 1+7+3+6+5+6 = 28`

| i | nums[i] | leftTotal (sebelum) | rightTotal = 28-leftTotal-nums[i] | leftTotal==rightTotal? | leftTotal (sesudah) |
| --- | ------- | ------------------- | --------------------------------- | ----------------------------- | ------------------- |
| 0 | 1 | 0 | `28-0-1=27` | `0==27`? tidak | 1 |
| 1 | 7 | 1 | `28-1-7=20` | `1==20`? tidak | 8 |
| 2 | 3 | 8 | `28-8-3=17` | `8==17`? tidak | 11 |
| 3 | 6 | 11 | `28-11-6=11` | `11==11`? **ya** → return `3` | — |

**Output: `3`** ✅

______________________________________________________________________

**Input:** `nums = [1,2,3]`

`total = 6`

| i | nums[i] | leftTotal | rightTotal | sama? | leftTotal baru |
| --- | ------- | --------- | ---------- | ---------------- | -------------- |
| 0 | 1 | 0 | `6-0-1=5` | tidak | 1 |
| 1 | 2 | 1 | `6-1-2=3` | tidak | 3 |
| 2 | 3 | 3 | `6-3-3=0` | tidak (`3 != 0`) | 6 |

Loop selesai tanpa ketemu pivot.

**Output: `-1`** ✅

______________________________________________________________________

**Input:** `nums = [2,1,-1]`

`total = 2+1+(-1) = 2`

| i | nums[i] | leftTotal | rightTotal | sama? |
| --- | ------- | --------- | ---------- | ---------------------- |
| 0 | 2 | 0 | `2-0-2=0` | `0==0` ✅ → return `0` |

**Output: `0`** ✅ — perhatikan `nums[0]` dianggap punya sisi kiri "kosong" (jumlahnya `0`), yang memang valid dianggap sama dengan jumlah sisi kanan kalau kebetulan sisi kanan juga totalnya `0`.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen (`nums=[5]`) → `leftTotal=0`, `rightTotal = total-0-5 = 0` (karena `total=5`) → selalu `true`, pivot `= 0` (kiri dan kanan sama-sama "kosong")
- [ ] Pivot di indeks `0` (sisi kiri kosong) atau di indeks terakhir (sisi kanan kosong) → tetap tertangani secara natural, karena `leftTotal` dan `rightTotal` masing-masing bisa bernilai `0` tanpa perlakuan khusus
- [ ] Elemen negatif (`nums=[2,1,-1]`) → tidak masalah, penjumlahan aritmatika biasa tetap valid untuk bilangan negatif
- [ ] Tidak ada pivot sama sekali (`nums=[1,2,3]`) → loop selesai penuh tanpa `return` di tengah, jatuh ke `return -1` di akhir
- [ ] Banyak indeks yang memenuhi syarat pivot → karena scan dari kiri ke kanan dan langsung `return` begitu ketemu, otomatis dikembalikan yang **paling kiri**, sesuai spesifikasi soal

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu Array Prefix Sum Eksplisit?

Pendekatan prefix sum "klasik" biasanya membangun array `prefix[]` terlebih dahulu (`prefix[i] = nums[0]+...+nums[i]`), lalu memakainya untuk menjawab query rentang. Tapi soal ini **tidak butuh** array itu sama sekali — karena kita cuma butuh **satu nilai kumulatif yang terus berubah** (`leftTotal`) sambil berjalan dari kiri ke kanan, bukan mengakses prefix sum di posisi sembarang berkali-kali. Ini membuat solusi bisa memakai **satu variabel** saja (`O(1)` space) alih-alih array tambahan (`O(n)` space) — variasi hemat memori dari pola prefix sum yang umum dipakai ketika akses cuma dibutuhkan secara sekuensial, bukan acak.

______________________________________________________________________

## 🔧 Alternatif: Precompute `total`, Lalu Bandingkan Langsung Tanpa Turunan `rightTotal`

```java
public int pivotIndex(int[] nums) {
    int total = Arrays.stream(nums).sum();
    int leftTotal = 0;
    for (int i = 0; i < nums.length; i++) {
        // total - nums[i] adalah jumlah sisa (kiri + kanan) selain elemen i
        // kiri harus setengah dari sisa itu supaya kanan == kiri
        if (leftTotal * 2 + nums[i] == total)
            return i;
        leftTotal += nums[i];
    }
    return -1;
}
```

Versi ini menyusun ulang persamaan `leftTotal == total - leftTotal - nums[i]` menjadi `leftTotal*2 + nums[i] == total`, menghindari perhitungan `rightTotal` sebagai variabel terpisah. Secara logika identik, hanya different aljabar penulisan kondisinya.

| Approach | Time | Space |
| ----------------------------------------- | ---- | ----- |
| Hitung `rightTotal` eksplisit (kode asli) | O(n) | O(1) |
| Aljabar `leftTotal*2 + nums[i] == total` | O(n) | O(1) |

Kode asli sedikit lebih mudah dibaca karena `rightTotal` punya makna yang jelas ("jumlah sisi kanan"), sementara versi aljabar sedikit lebih ringkas tapi butuh sedikit usaha ekstra untuk memahami transformasi persamaannya.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang bagus untuk pola **prefix sum "streaming"** — di mana kita tidak perlu membangun array prefix sum lengkap, cukup satu variabel kumulatif yang terus diupdate sambil berjalan, karena kebutuhan aksesnya sekuensial (bukan query acak di posisi sembarang). Insight `rightTotal = total - leftTotal - nums[i]` (turunkan sisi kanan dari total dan sisi kiri yang sudah diketahui) adalah trik umum yang menghindari perhitungan ulang berulang — pola serupa muncul di soal-soal seperti _Product of Array Except Self_ dan berbagai variasi _Subarray Sum_ lainnya. 🎯

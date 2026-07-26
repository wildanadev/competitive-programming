# 4001. Aggregate Two Time Series

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Two Pointers, Sorting, Simulation
- **Link**: [Problem](https://leetcode.com/problems/aggregate-two-time-series/)
- **Solution**: [Code](../../leetcode/AggregateTwoTimeSeries.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua array 2D `series1` dan `series2`, masing-masing berisi pasangan `[timestamp, value]` yang **sudah terurut naik ketat** berdasarkan `timestamp`.

Aturan penting: kalau sebuah `timestamp` **tidak ada** di salah satu series, nilainya diambil dari **timestamp berikutnya yang tersedia** di series tersebut (bukan yang sebelumnya!). Kalau tidak ada timestamp berikutnya, nilainya dianggap `0`.

Untuk setiap timestamp yang muncul di **salah satu** series, jumlahkan nilai dari kedua series pada timestamp itu. Kembalikan hasilnya sebagai array `[timestamp, sum]`, terurut naik ketat berdasarkan timestamp.

Contoh:

- `series1 = [[1,3],[4,1]]`, `series2 = [[2,2],[5,2]]` → `[[1,5],[2,3],[4,3],[5,2]]`
- `series1 = [[1,5]]`, `series2 = [[1000000000,2]]` → `[[1,7],[1000000000,2]]`

______________________________________________________________________

## 💡 Intuition

Karena kedua array **sudah terurut**, ini adalah kasus klasik **two pointers / merge-like traversal** — mirip _merge step_ pada merge sort, atau _interval intersection_.

Poin kunci yang membedakan soal ini dari two-pointer biasa: aturan "ambil dari timestamp berikutnya yang tersedia" berarti untuk timestamp `t` di `series1`, kita perlu mencari **elemen pertama di `series2` yang timestamp-nya `>= t`** — bukan elemen terakhir yang `<= t` seperti pada "carry forward" biasa. Sekali pointer itu ditemukan, dia valid dipakai untuk semua `t` berikutnya yang lebih kecil, sehingga pointer **hanya bergerak maju** — inilah yang membuat pendekatan two-pointer bekerja dalam waktu linier per pass.

Strategi solusi:

1. Pass pertama: iterasi semua elemen `series1`, untuk tiap timestamp cari elemen `series2` pertama yang timestamp-nya `>= timestamp` saat ini, lalu jumlahkan.
1. Pass kedua: iterasi semua elemen `series2` yang timestamp-nya **belum pernah muncul** di `series1` (supaya tidak dobel), lakukan hal yang sama tapi mencari di `series1`.
1. Karena hasil dari kedua pass digabung tanpa urutan yang terjamin, urutkan hasil akhir berdasarkan timestamp.

______________________________________________________________________

## 🔍 Approach

### Two Pointers (maju saja) + Set Penanda + Sort di Akhir

1. Siapkan `ans` (List hasil) dan `markTimeStamp` (HashSet untuk menandai timestamp yang sudah diproses dari `series1`).
1. **Pass 1 — dari `series1`:**
   - Untuk tiap `series1Data = series1[seriesIndx1]`:
     - `sum = series1Data[1]`
     - Majukan `seriesIndx2` selama `series2[seriesIndx2][0] < series1Data[0]` (pointer tidak pernah mundur).
     - Jika `seriesIndx2` masih valid, tambahkan `series2[seriesIndx2][1]` ke `sum`.
     - Catat timestamp ini ke `markTimeStamp`, lalu simpan `[timestamp, sum]` ke `ans`.
1. **Pass 2 — dari `series2`, reset kedua pointer ke 0:**
   - Untuk tiap `series2Data = series2[seriesIndx2]`:
     - Kalau timestamp-nya sudah ada di `markTimeStamp` → **skip** (sudah dihitung di pass 1).
     - Kalau belum, ulangi logika yang sama tapi arah sebaliknya: majukan `seriesIndx1` sampai `series1[seriesIndx1][0] >= series2Data[0]`, tambahkan nilainya kalau valid.
1. Urutkan `ans` berdasarkan timestamp (`Collections.sort`), lalu kembalikan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------ |
| **Time** | O((n + m) log(n + m)) — dua pass two-pointer O(n+m) amortized, didominasi sort |
| **Space** | O(n + m) — untuk `ans` dan `markTimeStamp` |

_Catatan: kalau hasil digabung dengan teknik merge murni (bukan dua pass terpisah + sort), kompleksitas bisa ditekan jadi O(n + m). Lihat bagian alternatif di bawah._

______________________________________________________________________

## 🧪 Dry Run

**Input:** `series1 = [[1,3],[4,1]]`, `series2 = [[2,2],[5,2]]`

**Pass 1 (dari series1):**

| series1Data | seriesIndx2 bergerak ke | series2 dipakai | sum | ans |
| ----------- | ----------------------- | --------------- | ----- | --------------- |
| [1,3] | 0 (`2 >= 1`) | [2,2] | 3+2=5 | \[[1,5]\] |
| [4,1] | 1 (`5 >= 4`) | [5,2] | 1+2=3 | \[[1,5],[4,3]\] |

`markTimeStamp = {1, 4}`

**Pass 2 (dari series2, pointer direset):**

| series2Data | sudah ditandai? | seriesIndx1 bergerak ke | series1 dipakai | sum | ans |
| ----------- | --------------- | ----------------------- | --------------- | ----- | ------------- |
| [2,2] | tidak | 1 (`4 >= 2`) | [4,1] | 2+1=3 | \[...,[2,3]\] |
| [5,2] | tidak | 2 (habis, `4 < 5`) | tidak ada | 2+0=2 | \[...,[5,2]\] |

**Sebelum sort:** `[[1,5],[4,3],[2,3],[5,2]]`
**Setelah sort:** `[[1,5],[2,3],[4,3],[5,2]]` ✅ (cocok dengan expected output)

______________________________________________________________________

**Input:** `series1 = [[1,5]]`, `series2 = [[1000000000,2]]`

- Pass 1: `series1Data=[1,5]`. `seriesIndx2` langsung berhenti di index 0 karena `1000000000 >= 1`. `sum = 5+2 = 7`. `ans=[[1,7]]`.
- Pass 2: `series2Data=[1000000000,2]`, belum ditandai. `seriesIndx1` dimajukan selama `series1[0][0] < 1000000000` → `1 < 1000000000` true → `seriesIndx1=1` → keluar dari batas array. Tidak ada nilai `series1` yang ditambahkan → `sum = 2 + 0 = 2`. `ans=[[1,7],[1000000000,2]]`.
- Sort: sudah terurut. **Output: `[[1,7],[1000000000,2]]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Timestamp identik di kedua series (`series1=[[1,3]]`, `series2=[[1,2]]`) → langsung dijumlahkan, tidak butuh pergerakan pointer tambahan
- [ ] Salah satu series habis duluan sebelum ditemukan timestamp `>=` → kontribusi series itu dianggap `0` (lihat contoh `1000000000` di atas)
- [ ] Semua timestamp `series2` juga ada di `series1` → pass kedua tidak menambahkan apa pun karena semua sudah masuk `markTimeStamp`
- [ ] `series1` atau `series2` hanya berisi satu elemen → tetap ditangani karena loop while pada pointer aman terhadap out-of-bounds (`seriesIndx2 < series2.length`)

______________________________________________________________________

## 🔧 Kenapa Pointer Tidak Pernah Mundur (Reset)?

```java
while (seriesIndx2 < series2.length && series2[seriesIndx2][0] < series1Data[0])
    seriesIndx2++;
```

Karena `series1` diproses berurutan naik (`series1Data[0]` makin besar tiap iterasi) dan `series2` juga sudah terurut naik, target pencarian "elemen pertama `>= series1Data[0]`" **juga bergerak monoton maju**. Itu sebabnya `seriesIndx2` tidak perlu di-reset di dalam pass yang sama — setiap elemen di `series2` dilewati pointer paling banyak satu kali per pass, membuat total pergerakan pointer O(m) untuk keseluruhan pass 1, bukan O(n×m).

Pointer baru direset ke `0` saat pindah ke **pass 2**, karena arah pencariannya berbalik (sekarang mencari di `series1` untuk tiap elemen `series2`).

______________________________________________________________________

## 🔧 Alternatif: True Merge Tanpa Sort di Akhir — O(n + m)

Solusi di atas melakukan sort di akhir karena hasil pass 1 dan pass 2 tidak digabung dalam urutan timestamp. Ini bisa dihindari dengan **merge sesungguhnya**: jalan bersamaan di kedua array sekali saja, pada tiap langkah pilih timestamp terkecil di antara pointer `i` (series1) dan `j` (series2), lalu cari pasangannya di series lain dengan cara yang sama (pointer "lookahead" terpisah untuk pencocokan nilai). Pendekatan ini menghasilkan array sudah terurut sejak awal sehingga tidak perlu `Collections.sort`, menurunkan kompleksitas total ke **O(n + m)**.

| Approach | Time | Space |
| -------------------------------- | ----------------- | ------ |
| Two-pass + HashSet + sort (kode) | O((n+m) log(n+m)) | O(n+m) |
| True merge, single pass | O(n + m) | O(n+m) |

Kode asli lebih mudah dinalar (dua pass simetris yang mirip), sedangkan true merge lebih optimal tapi butuh sedikit lebih banyak bookkeeping pointer.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi dari pola **"merge dua array terurut dengan two pointers"**, dengan twist: pencarian nilai "next available" alih-alih "last known value" seperti pada soal step-function pada umumnya. Karena kedua pointer sama-sama bergerak monoton naik dalam satu pass, total pergerakan tetap linier meskipun ada nested while loop — pola pergerakan pointer seperti ini juga sering muncul di soal _Merge Intervals_, _Interval List Intersections_, dan _Two Sum II (Sorted Array)_. 🎯

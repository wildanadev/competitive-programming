# 697. Degree of an Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/degree-of-an-array/)
- **Solution**: [Code](../../leetcode/DegreeOfAnArray.java)

______________________________________________________________________

## 📄 Problem Summary

**Degree** dari array `nums` didefinisikan sebagai **frekuensi kemunculan tertinggi** di antara elemen-elemennya.

Cari **panjang subarray terpendek** (contiguous) dari `nums` yang punya degree **sama persis** dengan degree array aslinya.

Contoh:

- `nums = [1,2,2,3,1]` → `2` (degree `=2`, dicapai oleh `1` dan `2`; subarray terpendek dengan degree 2 adalah `[2,2]`, panjang 2)
- `nums = [1,2,2,3,1,4,2]` → `6` (degree `=3` dicapai oleh `2`; subarray terpendek yang mengandung ketiga kemunculan `2` adalah `[2,2,3,1,4,2]`, dari indeks 1 sampai 6)

______________________________________________________________________

## 💡 Intuition

Kunci soal ini: begitu kita tahu **elemen mana** yang mencapai degree tertinggi, subarray terpendek yang mempertahankan degree itu untuk elemen tersebut **wajib** membentang dari **kemunculan pertama** sampai **kemunculan terakhir** elemen itu — tidak boleh kurang, karena kalau dipangkas sedikit saja dari salah satu ujung, jumlah kemunculan elemen itu di dalam subarray akan berkurang (turun di bawah degree aslinya).

Jadi strateginya:

1. Untuk **tiap** nilai unik di `nums`, catat: **posisi kemunculan pertama** (`left`), **posisi kemunculan terakhir** (`right`), dan **total frekuensi** (`count`).
1. Cari `degree` = frekuensi maksimum di antara semua nilai.
1. Di antara nilai-nilai yang frekuensinya **sama dengan** `degree` (bisa lebih dari satu nilai punya frekuensi tertinggi yang sama), hitung panjang subarray minimalnya (`right - left + 1`), lalu ambil yang **paling pendek**.

Kenapa cukup cek nilai-nilai dengan frekuensi `== degree` saja (bukan semua nilai)? Karena subarray hasil harus **mempertahankan degree yang sama** dengan array asli — kalau kita ambil subarray berdasarkan elemen dengan frekuensi lebih rendah dari `degree`, degree subarray itu pasti lebih kecil dari degree aslinya, jadi tidak valid sebagai jawaban.

______________________________________________________________________

## 🔍 Approach

### Hash Map — Track First/Last Occurrence + Frequency

1. Siapkan tiga `HashMap`: `left` (posisi kemunculan pertama tiap nilai), `right` (posisi kemunculan terakhir), `count` (frekuensi tiap nilai).
1. Loop `nums` sekali:
   - Kalau `x = nums[i]` **belum pernah** tercatat di `left`, catat `left.put(x, i)` (hanya kemunculan **pertama** yang boleh mengisi ini, karena `containsKey` dicek dulu).
   - `right.put(x, i)` selalu diupdate tiap kali ketemu `x`, sehingga di akhir loop otomatis berisi posisi kemunculan **terakhir**.
   - `count.put(x, count.getOrDefault(x, 0) + 1)` mengakumulasi frekuensi.
1. Cari `degree = Collections.max(count.values())` — frekuensi tertinggi di antara semua nilai unik.
1. Loop semua nilai unik `x` di `count.keySet()`: kalau `count.get(x) == degree`, hitung panjang subarray `right.get(x) - left.get(x) + 1`, lalu update `ans = Math.min(ans, panjang ini)`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n) — n = `nums.length`, satu pass untuk membangun map + satu pass untuk cari jawaban (jumlah nilai unik ≤ n) |
| **Space** | O(n) — tiga `HashMap` masing-masing menyimpan sampai `n` entri berbeda |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,2,3,1]`

**Pass 1 — bangun `left`, `right`, `count`:**

| i | x | left | right | count |
| --- | --- | --------------------------------------- | ----------------- | ----------------- |
| 0 | 1 | `{1:0}` | `{1:0}` | `{1:1}` |
| 1 | 2 | `{1:0, 2:1}` | `{1:0, 2:1}` | `{1:1, 2:1}` |
| 2 | 2 | `{1:0, 2:1}` (tidak berubah, sudah ada) | `{1:0, 2:2}` | `{1:1, 2:2}` |
| 3 | 3 | `{1:0, 2:1, 3:3}` | `{1:0, 2:2, 3:3}` | `{1:1, 2:2, 3:1}` |
| 4 | 1 | (tidak berubah) | `{1:4, 2:2, 3:3}` | `{1:2, 2:2, 3:1}` |

`degree = max(2, 2, 1) = 2` (dicapai oleh `1` dan `2`).

**Pass 2 — cek nilai dengan `count == 2`:**

| x | left | right | panjang = right-left+1 |
| --- | ---- | ----- | ---------------------- |
| 1 | 0 | 4 | `5` |
| 2 | 1 | 2 | `2` |

`ans = min(5, 2) = 2`.

**Output: `2`** ✅

______________________________________________________________________

**Input:** `nums = [1,2,2,3,1,4,2]`

- `count = {1:2, 2:3, 3:1, 4:1}` → `degree = 3` (dicapai oleh `2` saja).
- `left[2] = 1`, `right[2] = 6` → panjang `= 6-1+1 = 6`.
- Tidak ada nilai lain dengan `count == 3`, jadi cuma satu kandidat.

**Output: `6`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen unik (`degree = 1` untuk semua) → semua nilai jadi kandidat, jawabannya selalu `1` (subarray berisi satu elemen)
- [ ] Lebih dari satu nilai mencapai `degree` tertinggi yang sama (`1` dan `2` di contoh pertama) → dicek **semua** kandidat, ambil yang subarray-nya paling pendek
- [ ] Array hanya berisi 1 elemen → `degree = 1`, jawabannya `1`
- [ ] Elemen dengan degree tertinggi muncul berjauhan tapi ada elemen lain dengan degree sama yang muncul berdekatan (seperti contoh pertama: `1` berjarak jauh, `2` berdekatan) → jawabannya tetap mengambil yang **terpendek** di antara kandidat, bukan asal ambil kandidat pertama yang ditemukan

______________________________________________________________________

## 🔧 Kenapa `left` Hanya Diupdate Sekali (Pakai `containsKey`), Tapi `right` Selalu Diupdate?

Ini detail penting yang membedakan dua map ini:

```java
if (!left.containsKey(x))
    left.put(x, i);      // hanya di-set SEKALI, saat kemunculan pertama
right.put(x, i);          // selalu di-overwrite tiap kemunculan
```

`left` butuh nilai dari **kemunculan pertama**, jadi begitu sudah tercatat, tidak boleh tertimpa oleh kemunculan berikutnya — makanya dicek `containsKey` dulu. Sebaliknya, `right` justru **harus** terus di-overwrite di **setiap** kemunculan, supaya di akhir loop nilainya otomatis jadi indeks kemunculan **terakhir** — karena overwrite terus menerus, nilai yang tersisa di akhir pasti dari kemunculan paling akhir yang diproses.

______________________________________________________________________

## 🔧 Alternatif: Satu Map Saja, Simpan Array `[left, right, count]`

```java
public int findShortestSubArray(int[] nums) {
    Map<Integer, int[]> info = new HashMap<>(); // [left, right, count]
    for (int i = 0; i < nums.length; i++) {
        info.computeIfAbsent(nums[i], k -> new int[]{i, i, 0});
        int[] data = info.get(nums[i]);
        data[1] = i;      // update right
        data[2]++;        // increment count
    }

    int degree = 0;
    for (int[] data : info.values()) degree = Math.max(degree, data[2]);

    int ans = nums.length;
    for (int[] data : info.values())
        if (data[2] == degree)
            ans = Math.min(ans, data[1] - data[0] + 1);
    return ans;
}
```

Versi ini menggabungkan tiga `HashMap` jadi satu, dengan tiap entry menyimpan array `[left, right, count]` sekaligus. Mengurangi jumlah struktur data dari 3 map jadi 1, meski isinya secara konsep tetap sama — cuma dikemas berbeda.

| Approach | Time | Space | Jumlah Map |
| ----------------------------------- | ---- | ----- | ---------- |
| Tiga `HashMap` terpisah (kode asli) | O(n) | O(n) | 3 |
| Satu `HashMap<Integer, int[]>` | O(n) | O(n) | 1 |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola umum **"cari elemen dengan frekuensi tertinggi, lalu manfaatkan posisi kemunculan pertama & terakhirnya"** — begitu elemen kandidat (yang mencapai degree maksimum) sudah ditemukan, batas subarray minimalnya langsung ditentukan oleh rentang `[first occurrence, last occurrence]` elemen itu, tanpa perlu mencoba-coba kombinasi subarray lain. Perlu diingat juga kemungkinan **banyak kandidat** dengan frekuensi tertinggi yang sama — solusi harus mengecek **semua** kandidat itu untuk memastikan hasil akhirnya benar-benar yang terpendek. Pola "track first & last occurrence per value" ini juga relevan untuk soal-soal seperti _Partition Labels_. 🎯

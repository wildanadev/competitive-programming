# 2409. Count Days Spent Together

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String
- **Link**: [Problem](https://leetcode.com/problems/count-days-spent-together/)
- **Solution**: [Code](../../leetcode/CountDaysSpentTogether.java)

______________________________________________________________________

## 📄 Problem Summary

Alice dan Bob sama-sama berkunjung ke Roma di tahun yang sama. Diberikan tanggal tiba dan pergi mereka dalam format `"MM-DD"`:

- `arriveAlice`, `leaveAlice`
- `arriveBob`, `leaveBob`

Tanggal tiba dan pergi **sama-sama dihitung** (rentang bersifat inklusif). Tahun dianggap **bukan tahun kabisat** (Februari selalu `28` hari). Kembalikan **jumlah hari** di mana keduanya berada di Roma **bersamaan**.

Contoh:

- `arriveAlice = "08-15", leaveAlice = "08-18", arriveBob = "08-16", leaveBob = "08-19"` → `3` (hari `08-16`, `08-17`, `08-18`)
- `arriveAlice = "10-01", leaveAlice = "10-31", arriveBob = "11-01", leaveBob = "12-31"` → `0` (rentang mereka tidak bersinggungan)

______________________________________________________________________

## 💡 Intuition

Tanggal dalam format `"MM-DD"` sulit dibandingkan langsung, jadi langkah pertama adalah mengubahnya menjadi **satu angka**: **hari ke-berapa dalam setahun** (`1` sampai `365`). Caranya dengan **prefix sum jumlah hari tiap bulan**: hari ke-berapa dalam tahun = (jumlah hari dari semua bulan **sebelumnya**) + `DD`.

Setelah semua tanggal jadi angka, soalnya berubah menjadi masalah sederhana: **dua rentang bilangan bulat**, `[startAlice, endAlice]` dan `[startBob, endBob]`. Berapa banyak angka yang ada di **kedua** rentang?

Kode ini menjawabnya dengan cara yang paling mudah dibayangkan: siapkan array `dateOfYear` berukuran `366` (satu slot per hari), **tandai** tiap hari di rentang Alice (tambah `1`), lalu **tandai** tiap hari di rentang Bob (tambah `1`). Hari yang bernilai `2` adalah hari yang dilalui **keduanya**, dan jumlah hari seperti itu adalah jawabannya. Karena setahun cuma `365` hari, pendekatan menandai satu per satu ini murah.

______________________________________________________________________

## 🔍 Approach

### Prefix Sum untuk Konversi Tanggal, Lalu Tandai Rentang di Array 365 Hari

1. Susun `monthlyDayCounts = {31,28,31,30,31,30,31,31,30,31,30,31}` dan bangun `prefixSum` sepanjang `13`, di mana `prefixSum[i]` adalah **jumlah hari dari `i` bulan pertama**: `[0,31,59,90,120,151,181,212,243,273,304,334,365]`.
1. Ubah tiap tanggal `"MM-DD"` menjadi hari-ke-N: `prefixSum[MM - 1] + DD` (di kode, `MM - 1` dihitung saat parsing bulan).
1. Buat `dateOfYear[366]` (indeks `1..365` dipakai, indeks `0` tidak).
1. Untuk tiap hari dari `startAlice` sampai `endAlice` → `dateOfYear[i]++`. Lakukan yang sama untuk Bob.
1. Hitung jumlah elemen `dateOfYear` yang bernilai `2` dan kembalikan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------- |
| **Time** | O(365) = O(1) — jumlah hari dalam setahun tetap |
| **Space** | O(366) = O(1) — array berukuran tetap |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `"08-15", "08-18", "08-16", "08-19"`

Konversi ke hari-ke-N (Agustus adalah bulan ke-`8`, jadi `prefixSum[7] = 212`):

| Tanggal | Perhitungan | Hari ke-N |
| --------------------- | ----------- | --------- |
| `arriveAlice` `08-15` | `212 + 15` | 227 |
| `leaveAlice` `08-18` | `212 + 18` | 230 |
| `arriveBob` `08-16` | `212 + 16` | 228 |
| `leaveBob` `08-19` | `212 + 19` | 231 |

Rentang Alice: `227..230`. Rentang Bob: `228..231`.

| Hari ke-N | Dari Alice | Dari Bob | Nilai di `dateOfYear` |
| --------- | ---------- | -------- | --------------------- |
| 227 | ya | tidak | 1 |
| 228 | ya | ya | 2 |
| 229 | ya | ya | 2 |
| 230 | ya | ya | 2 |
| 231 | tidak | ya | 1 |

Hari yang bernilai `2`: `228`, `229`, `230` → `3` hari.

**Output: `3`** ✅

______________________________________________________________________

**Input:** `"10-01", "10-31", "11-01", "12-31"`

Konversi: `arriveAlice = 273 + 1 = 274`, `leaveAlice = 273 + 31 = 304`, `arriveBob = 304 + 1 = 305`, `leaveBob = 334 + 31 = 365`.

Rentang Alice `274..304` dan rentang Bob `305..365` tidak berbagi satu hari pun, jadi tidak ada elemen yang bernilai `2`.

**Output: `0`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Alice pergi **di hari yang sama** Bob tiba (`"08-18"` dan `"08-18"`) → tepat `1` hari bersama (rentang inklusif, hari itu dihitung)
- [ ] Satu rentang berada sepenuhnya **di dalam** rentang lain → jawabannya panjang rentang yang lebih pendek
- [ ] Dua rentang identik → jawabannya panjang rentang itu
- [ ] Rentang tidak bersinggungan (satu selesai sebelum yang lain mulai) → `0`
- [ ] Menyeberang bulan (`02-28` ke `03-01`) → `02-28` adalah hari ke-`59`, `03-01` hari ke-`60` (Februari `28` hari), jadi berurutan dengan benar
- [ ] Rentang penuh `01-01` sampai `12-31` → hari ke-`1` sampai `365`; indeks maksimal `365` masih muat di array berukuran `366`
- [ ] Menginap satu hari saja (`tiba == pergi`) → rentang berisi satu hari

______________________________________________________________________

## 🔧 Alternatif: Rumus Irisan Dua Rentang (Tanpa Array)

Setelah tanggal diubah jadi angka, irisan dua rentang bisa dihitung **langsung**:

```java
public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
    int start = Math.max(toDayOfYear(arriveAlice), toDayOfYear(arriveBob));
    int end = Math.min(toDayOfYear(leaveAlice), toDayOfYear(leaveBob));
    return Math.max(0, end - start + 1);
}

private static final int[] DAYS_BEFORE_MONTH = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};

private int toDayOfYear(String date) {
    int month = Integer.parseInt(date.substring(0, 2));
    int day = Integer.parseInt(date.substring(3, 5));
    return DAYS_BEFORE_MONTH[month - 1] + day;
}
```

Ide: hari bersama dimulai di **yang lebih akhir** dari dua tanggal tiba (`max`) dan berakhir di **yang lebih awal** dari dua tanggal pergi (`min`). Jumlah hari = `end - start + 1`, tapi kalau rentangnya tidak bersinggungan nilainya nol atau negatif, sehingga dibatasi dengan `Math.max(0, ...)`.

Cek contoh pertama: `start = max(227, 228) = 228`, `end = min(230, 231) = 230`, `230 - 228 + 1 = 3` ✅. Cek contoh kedua: `start = max(274, 305) = 305`, `end = min(304, 365) = 304`, `304 - 305 + 1 = 0` → `max(0, 0) = 0` ✅.

Versi ini juga menggantikan `split("-")` yang dipanggil dua kali per tanggal di kode asli (sekali untuk bulan, sekali untuk hari) dengan satu helper `toDayOfYear`, sehingga kode lebih pendek dan tidak mengulang parsing.

| Approach | Time | Space | Catatan |
| ----------------------------------------- | ------ | ------ | ------------------------------------------------------- |
| Tandai tiap hari di array 366 (kode asli) | O(365) | O(366) | Mudah dibayangkan, bekerja karena setahun cuma 365 hari |
| `max(0, min(akhir) - max(awal) + 1)` | O(1) | O(1) | Berlaku untuk rentang sebesar apa pun |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini punya dua langkah yang berguna di banyak soal tanggal dan interval: **ubah representasi yang sulit dibandingkan menjadi satu angka** (di sini hari-ke-N lewat prefix sum jumlah hari per bulan), lalu selesaikan sebagai **irisan dua rentang** `[a, b]` dan `[c, d]`, yaitu `max(0, min(b, d) - max(a, c) + 1)`. Pendekatan menandai tiap hari di array valid dan jelas selama rentang nilainya kecil (di sini `365`), sedangkan rumus irisan tetap efisien walau rentangnya sangat besar. Pola irisan interval ini sama dengan versi 1 dimensi dari _Rectangle Overlap_ dan sejalan dengan _Number of Intersecting Interval Pairs I_. 🎯

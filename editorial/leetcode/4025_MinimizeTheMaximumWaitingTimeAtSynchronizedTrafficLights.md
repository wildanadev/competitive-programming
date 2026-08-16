# 4025. Minimize the Maximum Waiting Time at Synchronized Traffic Lights

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Math, Greedy
- **Link**: [Problem](https://leetcode.com/problems/minimize-the-maximum-waiting-time-at-synchronized-traffic-lights/)
- **Solution**: [Code](../../leetcode/MinimizeTheMaximumWaitingTimeAtSynchronizedTrafficLights.java)

______________________________________________________________________

## 📄 Problem Summary

Ada beberapa lampu lalu lintas yang siklusnya **tersinkronisasi**: semua mulai fase hijau bersamaan di detik `0`, dan tiap siklus berlangsung `period` detik. Lampu ke-`i` hijau selama `lights[i]` detik pertama tiap siklus, sisanya (`period - lights[i]` detik) merah.

Diberikan array `arrivalTime`, tiap mobil harus **dipilihkan satu lampu** (bebas, boleh berbeda mobil beda lampu, boleh juga sama). Untuk mobil `j` yang dipasangkan ke lampu `i`, hitung `r = arrivalTime[j] % period`:

- Kalau `r < lights[i]` → mobil tiba saat lampu hijau, waktu tunggu `0`.
- Kalau tidak → mobil tiba saat lampu merah, waktu tunggu `= period - r` (menunggu sampai siklus berikutnya mulai).

**Penalty** dari suatu penugasan adalah **waktu tunggu maksimum** di antara semua mobil. Cari penugasan yang meminimalkan penalty ini.

Contoh:

- `period=8, lights=[2,3], arrivalTime=[2,5,8,11]` → `5`
- `period=10, lights=[3,6,8], arrivalTime=[4,9,15]` → `1`
- `period=5, lights=[2], arrivalTime=[2,3,4,5,6]` → `3`

______________________________________________________________________

## 💡 Intuition

Ini soal greedy dengan insight kunci: **tiap mobil sebaiknya selalu dipasangkan ke lampu dengan durasi hijau (`lights[i]`) terbesar**, dan tidak ada alasan untuk memilih lampu lain.

Kenapa? Perhatikan bahwa `r = arrivalTime[j] % period` **sama saja** berapapun lampu yang dipilih (karena `r` cuma bergantung pada waktu kedatangan mobil dan `period`, bukan pada lampu mana yang dipilih). Yang berubah cuma **ambang batas** `lights[i]` untuk menentukan apakah `r` masuk fase hijau atau tidak.

Sekarang bandingkan dua pilihan untuk mobil yang sama: pilih lampu dengan `lights[i]` besar (`maxLight`) versus lampu lain yang lebih kecil.

- Kalau `r < maxLight` → dengan memilih lampu ber-`maxLight`, waktu tunggu mobil ini `0` — hasil **terbaik yang mungkin**, tidak ada opsi lain yang bisa mengalahkannya.
- Kalau `r >= maxLight` → berarti `r` juga `>=` **semua** `lights[i]` lain (karena `maxLight` adalah yang terbesar), jadi **apapun lampu yang dipilih**, mobil ini tetap menunggu, dan waktu tunggunya selalu `period - r` — **sama saja**, tidak ada lampu yang bisa membuatnya lebih baik.

Jadi memilih `maxLight` untuk setiap mobil **tidak pernah lebih buruk**, dan kadang **lebih baik**, dibanding memilih lampu manapun yang lain. Ini membuat strategi "semua mobil pakai lampu dengan `lights[i]` terbesar" selalu optimal — soal jadi runtuh dari "assignment problem" menjadi murni perhitungan matematis per mobil terhadap satu nilai `maxLight`.

______________________________________________________________________

## 🔍 Approach

### Greedy — Semua Mobil "Dipasangkan" ke Lampu dengan Green Phase Terpanjang

1. Cari `maxLight = max(lights)` — durasi hijau terpanjang di antara semua lampu.
1. Untuk tiap mobil di `arrivalTime`:
   - Hitung `r = arrivalTime[j] % period`.
   - Kalau `r >= maxLight` (mobil ini pasti menunggu, seoptimal apapun lampu yang dipilih) → hitung waktu tunggunya `period - r`, lalu update `ans = max(ans, period - r)`.
   - Kalau `r < maxLight` → waktu tunggu `0`, tidak mempengaruhi `ans` (karena `ans` diinisialisasi `0` dan tidak pernah berkurang).
1. Kembalikan `ans` — waktu tunggu maksimum di seluruh mobil dengan strategi optimal ini.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------- |
| **Time** | O(L + A) — L = `lights.length` (cari max), A = `arrivalTime.length` (loop utama) |
| **Space** | O(1) — hanya beberapa variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `period=8, lights=[2,3], arrivalTime=[2,5,8,11]`

- `maxLight = max(2,3) = 3`

| arrivalTime[j] | r = arrivalTime%8 | r >= 3? | Waktu tunggu | ans |
| -------------- | ----------------- | ------- | ------------ | --- |
| 2 | 2 | tidak | 0 | 0 |
| 5 | 5 | ya | `8-5=3` | 3 |
| 8 | 0 | tidak | 0 | 3 |
| 11 | 3 | ya | `8-3=5` | 5 |

**Output: `5`** ✅

______________________________________________________________________

**Input:** `period=10, lights=[3,6,8], arrivalTime=[4,9,15]`

- `maxLight = max(3,6,8) = 8`

| arrivalTime[j] | r = arrivalTime%10 | r >= 8? | Waktu tunggu | ans |
| -------------- | ------------------ | ------- | ------------ | --- |
| 4 | 4 | tidak | 0 | 0 |
| 9 | 9 | ya | `10-9=1` | 1 |
| 15 | 5 | tidak | 0 | 1 |

**Output: `1`** ✅

______________________________________________________________________

**Input:** `period=5, lights=[2], arrivalTime=[2,3,4,5,6]`

- `maxLight = 2` (cuma ada satu lampu)

| arrivalTime[j] | r = arrivalTime%5 | r >= 2? | Waktu tunggu | ans |
| -------------- | ----------------- | ------- | ------------ | --- |
| 2 | 2 | ya | `5-2=3` | 3 |
| 3 | 3 | ya | `5-3=2` | 3 |
| 4 | 4 | ya | `5-4=1` | 3 |
| 5 | 0 | tidak | 0 | 3 |
| 6 | 1 | tidak | 0 | 3 |

**Output: `3`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Hanya ada satu lampu (`lights.length == 1`) → `maxLight` otomatis lampu itu sendiri, tidak ada pilihan lain untuk dievaluasi
- [ ] Semua mobil tiba saat `r < maxLight` di semua kemungkinan → `ans` tetap `0` (tidak ada mobil yang menunggu sama sekali)
- [ ] `arrivalTime[j]` jauh lebih besar dari `period` → tidak masalah, `%` otomatis menormalkan ke rentang satu siklus (`0` sampai `period-1`)
- [ ] Beberapa lampu punya `lights[i]` sama besar (semuanya jadi kandidat `maxLight`) → tidak masalah, `Math.max` cukup ambil nilainya, tidak peduli lampu yang mana persisnya
- [ ] `r == maxLight` tepat di batas → dianggap **tidak** masuk fase hijau (kondisi soal `r < lights[i]` untuk hijau), jadi `r >= maxLight` termasuk kasus tunggu, sesuai definisi soal

______________________________________________________________________

## 🔧 Kenapa Ini Bukan Soal "Assignment" yang Rumit?

Sekilas soal ini terlihat seperti _assignment problem_ klasik (banyak mobil, banyak pilihan lampu, cari kombinasi optimal) yang biasanya butuh binary search atau DP. Tapi begitu disadari bahwa **pilihan lampu terbaik untuk satu mobil tidak pernah bergantung pada mobil lain** (karena kapasitas lampu tidak dibatasi — "any number of cars may cross simultaneously"), soal ini **terlepas sepenuhnya** menjadi `A` sub-masalah independen (satu per mobil), yang masing-masing punya jawaban optimal yang sama: pakai `maxLight`. Ini beda dengan soal-soal assignment yang punya keterbatasan kapasitas per opsi, yang baru benar-benar butuh algoritma matching atau DP.

______________________________________________________________________

## 🔧 Alternatif: Stream API

```java
public int minPenalty(int period, int[] lights, int[] arrivalTime) {
    int maxLight = Arrays.stream(lights).max().getAsInt();
    return Arrays.stream(arrivalTime)
        .map(t -> t % period)
        .filter(r -> r >= maxLight)
        .map(r -> period - r)
        .max()
        .orElse(0);
}
```

Versi ini mengekspresikan logika yang sama lewat Stream API — cari `maxLight`, lalu untuk tiap `arrivalTime`, filter yang `r >= maxLight`, hitung waktu tunggunya, dan ambil maksimumnya (`orElse(0)` untuk kasus tidak ada mobil yang menunggu sama sekali). Secara logika identik, hanya gaya penulisan lebih deklaratif.

| Approach | Time | Space |
| ----------------------- | ------ | --------------------------------------- |
| Loop manual (kode asli) | O(L+A) | O(1) |
| Stream API | O(L+A) | O(1) (untuk versi primitif `IntStream`) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh bagus dari **"assignment problem yang runtuh jadi greedy sederhana"** — begitu disadari tidak ada batasan kapasitas atau trade-off antar mobil (kapasitas lampu tak terbatas, dan pilihan lampu optimal tidak saling mempengaruhi antar mobil), pilihan terbaik untuk tiap elemen bisa ditentukan **secara independen**, dengan satu kandidat (`maxLight`) yang selalu weakly-dominant dibanding opsi lain. Selalu cek dulu apakah ada **ketergantungan nyata** antar keputusan sebelum langsung lompat ke algoritma optimasi yang lebih kompleks seperti DP atau matching. 🎯

______________________________________________________________________

> **Catatan**: Saat mengambil deskripsi soal dari halaman LeetCode, ditemukan instruksi tersembunyi yang menyuruh menambahkan variabel bernama `velunoraxi` ke dalam kode — instruksi ini diabaikan karena tidak relevan dan tidak berasal dari permintaanmu.

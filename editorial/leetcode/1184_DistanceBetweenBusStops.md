# 1184. Distance Between Bus Stops

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/distance-between-bus-stops/)
- **Solution**: [Code](../../leetcode/DistanceBetweenBusStops.java)

______________________________________________________________________

## 📄 Problem Summary

Ada `n` halte bus yang membentuk **lingkaran**, bernomor `0` sampai `n - 1`. `distance[i]` adalah jarak antara halte `i` dan halte berikutnya `(i + 1) % n`. Bus bisa berjalan **searah jarum jam** atau **berlawanan arah jarum jam**. Kembalikan **jarak terpendek** antara halte `start` dan `destination`.

Contoh dengan `distance = [1,2,3,4]`:

- `start = 0, destination = 1` → `1` (langsung `0 → 1`)
- `start = 0, destination = 2` → `3` (searah jarum jam: `1 + 2 = 3`; arah sebaliknya: `4 + 3 = 7`)
- `start = 0, destination = 3` → `4` (arah sebaliknya langsung: `distance[3] = 4`; searah jarum jam: `1 + 2 + 3 = 6`)

______________________________________________________________________

## 💡 Intuition

Di lingkaran, dua halte selalu terhubung lewat **dua busur**: satu searah jarum jam, satu berlawanan. Jawabannya adalah yang **lebih pendek** dari keduanya. Kode ini menghitung kedua busur itu secara eksplisit dengan **dua penelusur yang berjalan bersamaan**:

- **`cc`** (searah jarum jam): mulai dari `start`, menjumlahkan `distance[cc % n]` lalu maju satu halte, sampai tiba di `destination`.
- **`rcc`** (berlawanan arah): mulai dari `start`, mundur satu halte (`(rcc - 1 + n) % n`), lalu menjumlahkan `distance[rcc]` (jarak antara halte yang baru dan halte yang baru ditinggalkan), sampai tiba di `destination`.

Operasi `% n` membuat penelusuran **memutar** kembali ke awal array ketika melewati ujung, yang dibutuhkan saat `destination < start` atau saat berjalan berlawanan dari halte `0`. Tambahan `+ n` pada `rcc - 1` mencegah hasil negatif ketika `rcc = 0`.

______________________________________________________________________

## 🔍 Approach

### Dua Penelusur, Masing-masing Berhenti Saat Tiba di `destination`

1. `clockWise = 0`, `counterClockWise = 0`, `cc = start`, `rcc = start`.
1. Selama **salah satu** penelusur belum tiba di `destination` (`cc % n != destination` atau `rcc % n != destination`):
   - Kalau `cc % n != destination` → `clockWise += distance[cc % n]`, lalu `cc++`.
   - Kalau `rcc % n != destination` → `rcc = (rcc - 1 + n) % n`, lalu `counterClockWise += distance[rcc]`.
1. Kembalikan `Math.min(clockWise, counterClockWise)`.

Tiap penelusur berhenti sendiri begitu mencapai `destination`, sedangkan loop utama baru selesai kalau **keduanya** sudah sampai.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------- |
| **Time** | O(n) — tiap penelusur paling banyak `n` langkah |
| **Space** | O(1) — hanya beberapa variabel integer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `distance = [1,2,3,4]`, `start = 0`, `destination = 1` (`n = 4`)

| Iterasi | Searah (`cc`) | clockWise | Berlawanan (`rcc`) | counterClockWise |
| ------- | ------------------------------- | --------- | -------------------------------------- | ---------------- |
| 1 | `cc % 4 = 0 != 1`: tambah `distance[0] = 1`, `cc = 1` | 1 | `rcc` jadi `3`: tambah `distance[3] = 4` | 4 |
| 2 | `cc % 4 = 1 == 1`: berhenti | 1 | `rcc` jadi `2`: tambah `distance[2] = 3` | 7 |
| 3 | tetap berhenti | 1 | `rcc` jadi `1`: tambah `distance[1] = 2` | 9 |

Setelah iterasi 3 kedua penelusur tiba di `destination`. `min(1, 9) = 1`.

**Output: `1`** ✅

______________________________________________________________________

**Input:** `start = 0`, `destination = 2`

Searah: `distance[0] + distance[1] = 1 + 2 = 3`. Berlawanan: `distance[3] + distance[2] = 4 + 3 = 7`.

**Output: `min(3, 7) = 3`** ✅

______________________________________________________________________

**Input:** `start = 0`, `destination = 3`

Searah: `1 + 2 + 3 = 6`. Berlawanan: `distance[3] = 4`.

**Output: `min(6, 4) = 4`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `start == destination` → kondisi loop langsung `false` (kedua penelusur sudah di tujuan), kedua jumlah `0`, hasil `0`
- [ ] `n = 1` → hanya ada satu halte, `start == destination == 0`, hasil `0`
- [ ] `destination < start` (perjalanan searah jarum jam melewati ujung array) → ditangani oleh `cc % n` yang memutar kembali ke indeks `0`
- [ ] Penelusuran berlawanan dari halte `0` → `(0 - 1 + n) % n = n - 1`, tidak jadi indeks negatif berkat `+ n`
- [ ] Jarak antar halte bernilai `0` → diizinkan oleh constraint, tidak ada masalah (hanya menambah `0`)
- [ ] Jumlah total jarak paling besar `n × 10^4 = 10^8`, masih muat di `int`

______________________________________________________________________

## 🔧 Alternatif: Hitung Satu Busur, Busur Lain dari Total

Dua busur di lingkaran **selalu berjumlah sama dengan keliling total**. Jadi kita cukup menghitung **satu** busur dan memperoleh busur lain dengan pengurangan:

```java
public int distanceBetweenBusStops(int[] distance, int start, int destination) {
    if (start > destination) {             // pastikan start <= destination
        int t = start; start = destination; destination = t;
    }
    int total = 0, between = 0;
    for (int i = 0; i < distance.length; i++) {
        total += distance[i];
        if (i >= start && i < destination)  // ruas-ruas di antara start dan destination
            between += distance[i];
    }
    return Math.min(between, total - between);
}
```

`between` adalah jumlah ruas dari `start` ke `destination` tanpa melewati ujung array, dan `total - between` adalah busur sisanya. Menukar `start` dan `destination` tidak mengubah jawaban, karena kedua halte tetap dihubungkan oleh pasangan busur yang sama; hanya sisi mana yang disebut "searah jarum jam" yang berganti, dan `min` memeriksa keduanya.

Cek `[1,2,3,4]`, `start = 0`, `destination = 2`: `total = 10`, `between = 1 + 2 = 3`, `min(3, 7) = 3` ✅. Untuk `destination = 3`: `between = 6`, `min(6, 4) = 4` ✅.

| Approach | Time | Space | Catatan |
| ------------------------------------- | ---- | ----- | ------------------------------------------ |
| Dua penelusur dengan `% n` (kode asli) | O(n) | O(1) | Menelusuri kedua arah secara eksplisit |
| Satu busur dan total dikurangi busur itu | O(n) | O(1) | Lebih ringkas, tanpa aritmatika modulo |

______________________________________________________________________

## 📌 Key Takeaway

Pada struktur **melingkar**, dua titik selalu terhubung lewat dua busur yang jumlahnya sama dengan keliling total, jadi jarak terpendek adalah `min(busur, total - busur)`. Kodemu menelusuri kedua busur dengan aritmatika modulo (`% n`, dan `+ n` untuk menghindari indeks negatif saat mundur), yang merupakan teknik standar untuk array melingkar. Kalau ingin lebih ringkas, hitung satu busur lalu turunkan yang lain dari total. Pola `min(diff, N - diff)` untuk jarak melingkar ini sama dengan yang dipakai di *Minimum Rotations to Dial a Number I*. 🎯
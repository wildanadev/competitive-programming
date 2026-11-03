# 1232. Check If It Is a Straight Line

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Geometry
- **Link**: [Problem](https://leetcode.com/problems/check-if-it-is-a-straight-line/)
- **Solution**: [Code](../../leetcode/CheckIfItIsAStraightLine.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `coordinates`, di mana `coordinates[i] = [x, y]` adalah sebuah titik di bidang XY. Tentukan apakah **semua titik berada pada satu garis lurus**.

Constraint: `2 <= coordinates.length <= 1000`, koordinat dalam rentang `-10^4` sampai `10^4`, dan tidak ada titik yang duplikat.

Contoh:

- `coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]` → `true` (semua titik di garis `y = x + 1`)
- `coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]` → `false`

______________________________________________________________________

## 💡 Intuition

Soal ini adalah versi **banyak titik** dari *Valid Boomerang*. Titik-titik berada pada satu garis lurus kalau **kemiringan (gradien)** antara tiap dua titik berurutan **sama**: gradien dari titik pertama ke kedua harus sama dengan gradien dari kedua ke ketiga, dari ketiga ke keempat, dan seterusnya.

**Kenapa membandingkan gradien segmen berurutan cukup?** Misalkan `p0 → p1` berada di garis `L`. Kalau `p1 → p2` punya gradien yang sama, dan `p1` juga di `L`, maka `p2` pasti di `L` juga (satu garis dengan kemiringan tertentu yang melewati `p1` hanya ada satu: `L` sendiri). Argumen ini diulang untuk tiap titik berikutnya (induksi), jadi semua titik ada di `L`.

Gradien dua titik `(x1, y1)` dan `(x2, y2)` adalah `(y2 - y1) / (x2 - x1)`. Kasus **garis vertikal** (`x2 == x1`) harus ditangani terpisah karena pembagian dengan nol. Kode ini memakai `Double.POSITIVE_INFINITY` sebagai penanda "gradien tak terhingga" untuk garis vertikal, sehingga dua segmen vertikal tetap dianggap punya gradien yang sama.

______________________________________________________________________

## 🔍 Approach

### Bandingkan Gradien Tiap Segmen Berurutan dengan Gradien Segmen Pertama

1. Kalau hanya ada `2` titik → `return true` (dua titik selalu membentuk garis lurus).
1. Hitung gradien segmen pertama (`coordinates[0]` ke `coordinates[1]`): kalau selisih `x` bukan nol, pakai `(double) dy / dx`; kalau nol (vertikal), pakai `Double.POSITIVE_INFINITY`.
1. Untuk `i` dari `1` sampai `n - 2`: hitung gradien segmen `(i, i+1)` dengan cara yang sama. Kalau berbeda dari gradien pertama → `return false`.
1. Kalau semua segmen punya gradien yang sama → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------- |
| **Time** | O(n) — satu kali pass |
| **Space** | O(1) — beberapa variabel `double` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]`

Gradien pertama: `(3 - 2) / (2 - 1) = 1.0`.

| i | Segmen | dy / dx | Gradien | Sama dengan 1.0? |
| --- | ----------------- | ------- | ------- | ---------------- |
| 1 | `(2,3) → (3,4)` | `1 / 1` | 1.0 | ya |
| 2 | `(3,4) → (4,5)` | `1 / 1` | 1.0 | ya |
| 3 | `(4,5) → (5,6)` | `1 / 1` | 1.0 | ya |
| 4 | `(5,6) → (6,7)` | `1 / 1` | 1.0 | ya |

Semua cocok → **Output: `true`** ✅

______________________________________________________________________

**Input:** `coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]`

Gradien pertama: `(2 - 1) / (2 - 1) = 1.0`.

| i | Segmen | dy / dx | Gradien | Sama dengan 1.0? |
| --- | ----------------- | ------- | ------- | ---------------- |
| 1 | `(2,2) → (3,4)` | `2 / 1` | 2.0 | **tidak** → `return false` |

**Output: `false`** ✅

______________________________________________________________________

**Input tambahan (garis vertikal):** `coordinates = [[1,1],[1,5],[1,9]]`

Gradien pertama: `dx = 0` → `+Infinity`. `i = 1`: segmen `(1,5) → (1,9)` juga `dx = 0` → `+Infinity`. Sama.

**Output: `true`** ✅

**Input tambahan (vertikal lalu miring):** `coordinates = [[1,1],[1,5],[2,9]]` → gradien pertama `+Infinity`, segmen kedua `(9 - 5) / (2 - 1) = 4.0`, beda → **`false`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Dua titik saja → selalu `true`, ditangani di awal
- [ ] Garis vertikal (semua `x` sama) → `dx = 0` di tiap segmen, semuanya diberi `+Infinity`, jadi sama
- [ ] Garis horizontal (semua `y` sama) → gradien `0.0`; kalau titiknya berurutan ke kiri, `dx` negatif dan hasilnya `-0.0`, tapi di Java `0.0 == -0.0` bernilai `true` untuk tipe primitif `double`, jadi perbandingan `!=` tetap benar
- [ ] Vertikal ke atas lalu ke bawah (`dy` berbeda tanda) → keduanya tetap `+Infinity` karena `dx = 0`, jadi dianggap sama (benar, keduanya satu garis vertikal)
- [ ] Titik tidak harus terurut sepanjang garis (`[[0,0],[2,2],[1,1]]`) → semua gradien segmen berurutan tetap `1.0`, hasil `true` (benar, ketiganya segaris)
- [ ] Gradien pertama berbeda dari kedua → langsung `false` di iterasi pertama, tanpa memeriksa sisanya

______________________________________________________________________

## 🔧 Apakah Membandingkan `double` dengan `!=` Aman di Sini?

Membandingkan bilangan pecahan dengan `==` atau `!=` biasanya berbahaya karena ketidaktelitian floating-point. Tapi di sini aman, dengan dua alasan:

1. **Pecahan yang sama persis menghasilkan `double` yang sama persis.** Pembagian IEEE 754 mengembalikan `double` terdekat dari hasil sebenarnya. Selisih koordinat (bilangan bulat kecil) bisa direpresentasikan tepat, jadi misalnya `1/3` dan `2/6` (nilai matematis sama) menghasilkan `double` yang identik.
1. **Pecahan berbeda tidak akan "bertabrakan".** Dua gradien yang berbeda `a/b` dan `c/d` selisihnya minimal `1 / (b × d)`. Dengan koordinat sampai `±10^4`, selisih koordinat paling besar `2 × 10^4`, jadi selisih minimal dua gradien berbeda sekitar `1 / (4 × 10^8) = 2,5 × 10^-9`. Sementara jarak antar `double` yang berdekatan (ulp) untuk nilai sebesar `2 × 10^4` hanya sekitar `4 × 10^-12`, jauh lebih kecil. Jadi dua gradien yang berbeda selalu menghasilkan `double` yang berbeda.

Kesimpulannya, **untuk constraint soal ini** pendekatan gradien dengan `double` benar. Tapi ini bergantung pada batas koordinat yang kecil. Kalau koordinatnya jauh lebih besar (misal sampai `10^9`), dua gradien yang berbeda bisa menghasilkan `double` yang sama dan solusi ini bisa salah. Pendekatan perkalian silang di bawah tidak punya masalah ini.

______________________________________________________________________

## 🔧 Alternatif: Perkalian Silang (Cross Product), Eksak dengan Bilangan Bulat

Sama seperti di *Valid Boomerang*, kita bisa menghindari pembagian dengan memeriksa apakah tiap titik `pi` segaris dengan dua titik pertama `p0` dan `p1` lewat **perkalian silang**:

```java
public boolean checkStraightLine(int[][] c) {
    int dx = c[1][0] - c[0][0];
    int dy = c[1][1] - c[0][1];
    for (int i = 2; i < c.length; i++) {
        int x = c[i][0] - c[0][0];
        int y = c[i][1] - c[0][1];
        if (dx * y != x * dy)      // cross product tidak nol: tidak segaris
            return false;
    }
    return true;
}
```

Kondisi `dx * y == x * dy` berarti vektor `p0 → p1` dan `p0 → pi` sejajar, jadi `pi` ada di garis yang melewati `p0` dan `p1`. Semuanya bilangan bulat, jadi **eksak** (tanpa pembulatan), dan tidak perlu menangani garis vertikal secara khusus (`dx = 0` otomatis bekerja). Hasil kali paling besar `(2 × 10^4) × (2 × 10^4) = 4 × 10^8`, masih di bawah batas `int`.

Cek contoh kedua: `p0 = (1,1)`, `p1 = (2,2)` sehingga `dx = 1`, `dy = 1`. Titik `(3,4)`: `x = 2`, `y = 3`, `dx * y = 3`, `x * dy = 2`, tidak sama → `false` ✅. Cek contoh pertama: semua titik di `y = x + 1`, jadi `dx * y == x * dy` selalu terpenuhi → `true` ✅.

| Approach | Time | Space | Catatan |
| ----------------------------------------- | ---- | ----- | --------------------------------------------------- |
| Gradien dengan `double` (kode asli) | O(n) | O(1) | Benar untuk constraint ini, perlu kasus khusus vertikal |
| Perkalian silang terhadap dua titik pertama | O(n) | O(1) | Eksak, tanpa pembagian dan tanpa kasus khusus |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini memperluas ide dari *Valid Boomerang* dari tiga titik ke banyak titik: semua titik segaris kalau gradien tiap pasangan berurutan sama. Pendekatan gradien dengan `double` bekerja untuk constraint kecil (dan kamu sudah menangani garis vertikal dengan benar lewat `POSITIVE_INFINITY`), tapi ia bergantung pada kenyataan bahwa selisih koordinat kecil. Perkalian silang adalah versi yang lebih kokoh: murni bilangan bulat, tanpa pembagian, tanpa kasus khusus. Aturan praktis: **kalau bisa dihindari, jangan bandingkan hasil pembagian lewat `double`; kalikan silang saja**. Teknik ini juga jadi dasar soal *Max Points on a Line*. 🎯
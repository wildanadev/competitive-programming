# 1037. Valid Boomerang

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math, Geometry
- **Link**: [Problem](https://leetcode.com/problems/valid-boomerang/)
- **Solution**: [Code](../../leetcode/ValidBoomerang.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `points` berisi **tiga titik** di bidang 2D (`points[i] = [xi, yi]`). Sebuah **boomerang** adalah himpunan tiga titik yang **semuanya berbeda** dan **tidak berada pada satu garis lurus**. Kembalikan `true` kalau ketiga titik membentuk boomerang.

Contoh:

- `points = [[1,1],[2,3],[3,2]]` → `true`
- `points = [[1,1],[2,2],[3,3]]` → `false` (ketiganya segaris)

______________________________________________________________________

## 💡 Intuition

Tiga titik `P0, P1, P2` **segaris** kalau kemiringan (slope) `P0→P1` sama dengan kemiringan `P0→P2`:

```
(y1 - y0) / (x1 - x0)  ==  (y2 - y0) / (x2 - x0)
```

Tapi menghitung dengan pembagian punya dua masalah: **pembagian dengan nol** kalau garisnya vertikal (`x1 == x0`), dan **kesalahan floating-point** kalau memakai `double`. Solusinya adalah **perkalian silang (cross-multiply)** untuk menghilangkan pembagian:

```
(y1 - y0) * (x2 - x0)  ==  (y2 - y0) * (x1 - x0)
```

Ini ekuivalen dengan mengecek apakah **cross product** dua vektor `P0→P1` dan `P0→P2` bernilai nol. Cross product nol berarti kedua vektor sejajar, jadi ketiga titik segaris.

Bonus yang elegan: kalau **dua titik sama**, cross product otomatis menjadi nol juga (salah satu vektor jadi vektor nol). Jadi **satu pengecekan** ini sekaligus menangani dua syarat soal: "tidak segaris" **dan** "semua titik berbeda".

______________________________________________________________________

## 🔍 Approach

### Cross Product Tidak Nol

Kode memakai bentuk:

```java
(x0 - x1) * (y0 - y2) != (x0 - x2) * (y0 - y1)
```

Bentuk ini sama dengan cross product standar. Perhatikan bahwa `(x0 - x1) * (y0 - y2)` sama dengan `(x1 - x0) * (y2 - y0)` (dua tanda negatif saling menghapus), dan `(x0 - x2) * (y0 - y1)` sama dengan `(x2 - x0) * (y1 - y0)`. Jadi kode mengecek:

```
(x1 - x0) * (y2 - y0)  !=  (x2 - x0) * (y1 - y0)
```

Kalau kedua sisi **berbeda** → titik **tidak** segaris dan berbeda → `true`. Kalau **sama** → segaris atau ada titik yang sama → `false`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------- |
| **Time** | O(1) — beberapa operasi aritmatika |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `points = [[1,1],[2,3],[3,2]]` (`P0=(1,1)`, `P1=(2,3)`, `P2=(3,2)`)

| Sisi | Perhitungan | Hasil |
| ------------------------ | --------------------------- | ----- |
| Kiri: `(x0-x1)*(y0-y2)` | `(1-2) * (1-2) = (-1)*(-1)` | 1 |
| Kanan: `(x0-x2)*(y0-y1)` | `(1-3) * (1-3) = (-2)*(-2)` | 4 |

`1 != 4` → **Output: `true`** ✅

______________________________________________________________________

**Input:** `points = [[1,1],[2,2],[3,3]]`

| Sisi | Perhitungan | Hasil |
| ------------------------ | --------------------------- | ----- |
| Kiri: `(x0-x1)*(y0-y2)` | `(1-2) * (1-3) = (-1)*(-2)` | 2 |
| Kanan: `(x0-x2)*(y0-y1)` | `(1-3) * (1-2) = (-2)*(-1)` | 2 |

`2 != 2` salah → **Output: `false`** ✅

______________________________________________________________________

**Input:** `points = [[1,1],[1,2],[1,3]]` (garis vertikal)

| Sisi | Perhitungan | Hasil |
| ------------------------ | ------------------------ | ----- |
| Kiri: `(x0-x1)*(y0-y2)` | `(1-1) * (1-3) = 0*(-2)` | 0 |
| Kanan: `(x0-x2)*(y0-y1)` | `(1-1) * (1-2) = 0*(-1)` | 0 |

`0 != 0` salah → **Output: `false`** ✅ (kalau memakai pembagian untuk slope, kasus vertikal ini akan membagi dengan nol)

______________________________________________________________________

**Input:** `points = [[1,1],[1,1],[2,3]]` (dua titik sama)

| Sisi | Perhitungan | Hasil |
| ------------------------ | ------------------------ | ----- |
| Kiri: `(x0-x1)*(y0-y2)` | `(1-1) * (1-3) = 0*(-2)` | 0 |
| Kanan: `(x0-x2)*(y0-y1)` | `(1-2) * (1-1) = (-1)*0` | 0 |

`0 != 0` salah → **Output: `false`** ✅ (titik duplikat otomatis terdeteksi tanpa pengecekan khusus)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Garis vertikal (semua `x` sama) → kedua sisi bernilai `0`, `false`; perkalian silang menghindari pembagian dengan nol
- [ ] Garis horizontal (semua `y` sama) → kedua sisi bernilai `0`, `false`
- [ ] Dua titik identik di posisi manapun (`P0=P1`, `P1=P2`, atau `P0=P2`) → salah satu vektor jadi vektor nol, kedua sisi `0`, `false`
- [ ] Ketiga titik identik → `false`
- [ ] Titik segaris dengan kemiringan tidak bulat (misal `(0,0)`, `(1,2)`, `(3,6)`) → cross product tetap tepat nol karena semua perhitungan memakai bilangan bulat, tanpa pembulatan
- [ ] Nilai koordinat besar → pada soal ini koordinat berada di rentang kecil (`0` sampai `100`), jadi hasil kali paling besar sekitar `10^4` dan aman di `int`

______________________________________________________________________

## 🔧 Kenapa Perkalian Silang, Bukan Membandingkan Slope dengan `double`?

Membandingkan slope lewat pembagian punya dua jebakan:

1. **Pembagian dengan nol**: garis vertikal (`x1 == x0`) membuat penyebut `0`. Dengan `double`, hasilnya `Infinity`, yang bisa kebetulan sama untuk dua titik tapi membuat logika jadi rapuh; dengan `int`, langsung `ArithmeticException`.
1. **Ketidakpresisian floating-point**: dua slope yang secara matematis sama bisa berbeda di bit terakhir karena pembulatan, sehingga pengecekan `==` pada `double` tidak andal.

Perkalian silang hanya memakai **perkalian dan pengurangan bilangan bulat**, jadi **tepat** (tanpa pembulatan) dan **bebas pembagian**. Ini teknik standar di geometri komputasional untuk menguji kesejajaran dan orientasi tiga titik.

______________________________________________________________________

## 🔧 Alternatif: Rumus Luas Segitiga (Shoelace)

```java
public boolean isBoomerang(int[][] p) {
    int area2 = p[0][0] * (p[1][1] - p[2][1])
              + p[1][0] * (p[2][1] - p[0][1])
              + p[2][0] * (p[0][1] - p[1][1]);
    return area2 != 0;
}
```

Ekspresi `area2` adalah **dua kali luas bertanda** segitiga yang dibentuk tiga titik (rumus shoelace). Tiga titik segaris membentuk segitiga **degenerate** dengan luas nol, begitu juga kalau ada titik yang sama. Jadi `area2 != 0` ekuivalen dengan pengecekan cross product pada kode asli. Contoh `[[1,1],[2,3],[3,2]]`: `1*(3-2) + 2*(2-1) + 3*(1-3) = 1 + 2 - 6 = -3`, tidak nol → `true`. Contoh `[[1,1],[2,2],[3,3]]`: `1*(2-3) + 2*(3-1) + 3*(1-2) = -1 + 4 - 3 = 0` → `false`.

| Approach | Time | Space | Catatan |
| ------------------------------------ | ---- | ----- | ------------------------------------------------- |
| Cross product dua vektor (kode asli) | O(1) | O(1) | Ringkas, satu perbandingan |
| Luas segitiga (shoelace) | O(1) | O(1) | Sudut pandang luas: segitiga degenerate = segaris |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini memperkenalkan **cross product** sebagai alat serbaguna di geometri: hasil nol berarti dua vektor sejajar (tiga titik segaris), dan satu pengecekan itu sekaligus menangkap kasus titik duplikat karena vektor nol menghasilkan cross product nol. Prinsip "hindari pembagian, pakai perkalian silang dengan bilangan bulat" menjaga hasil tetap tepat dan bebas dari pembagian dengan nol. Teknik yang sama jadi dasar untuk soal-soal seperti _Max Points on a Line_ dan berbagai soal orientasi/konveksitas titik. 🎯

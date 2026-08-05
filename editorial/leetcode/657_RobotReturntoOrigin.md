# 657. R# Robot Return to Origin

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/robot-return-to-origin/)
- **Solution**: [Code](../../leetcode/RobotReturnToOrigin.java)

______________________________________________________________________

## 📄 Problem Summary

Robot mulai di posisi `(0, 0)` pada bidang 2D. Diberikan string `moves` berisi urutan gerakan, di mana tiap karakter adalah salah satu dari:

- `'U'` — bergerak ke atas (`y++`)
- `'D'` — bergerak ke bawah (`y--`)
- `'L'` — bergerak ke kiri (`x--`)
- `'R'` — bergerak ke kanan (`x++`)

Kembalikan `true` kalau setelah menjalankan **semua** gerakan, robot kembali tepat ke posisi awal `(0, 0)`.

Contoh:

- `moves = "UD"` → `true` (naik lalu turun, balik ke posisi awal)
- `moves = "LL"` → `false` (dua kali ke kiri, berakhir di `(-2, 0)`)

______________________________________________________________________

## 💡 Intuition

Soal ini murni **simulasi posisi** dengan dua koordinat independen, `x` (horizontal) dan `y` (vertikal). Karena tiap gerakan cuma mempengaruhi salah satu sumbu:

- `U`/`D` cuma mengubah `y`
- `L`/`R` cuma mengubah `x`

...robot kembali ke titik asal **jika dan hanya jika** jumlah gerakan `U` sama dengan jumlah gerakan `D` (saling menetralkan di sumbu `y`), **dan** jumlah gerakan `R` sama dengan jumlah gerakan `L` (saling menetralkan di sumbu `x`). Tidak perlu melacak jejak lintasan penuh — cukup akumulasi posisi akhir, lalu cek apakah `(x, y) == (0, 0)`.

______________________________________________________________________

## 🔍 Approach

### Simulasi Posisi — Single Pass

1. Inisialisasi `x = 0`, `y = 0`.
1. Loop tiap karakter `i` di `moves`:
   - `'U'` → `y++`
   - `'D'` → `y--`
   - `'R'` → `x++`
   - `'L'` → `x--`
1. Setelah loop selesai, return `x == 0 && y == 0`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh string |
| **Space** | O(1) — hanya dua variabel koordinat |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `moves = "UD"`

| char | x | y |
| ---- | --- | --- |
| U | 0 | 1 |
| D | 0 | 0 |

`x==0 && y==0` → **Output: `true`** ✅

______________________________________________________________________

**Input:** `moves = "LL"`

| char | x | y |
| ---- | --- | --- |
| L | -1 | 0 |
| L | -2 | 0 |

`x==0 && y==0` → `-2 != 0` → **Output: `false`** ✅

______________________________________________________________________

**Input:** `moves = "UDLR"`

| char | x | y |
| ---- | --- | --- |
| U | 0 | 1 |
| D | 0 | 0 |
| L | -1 | 0 |
| R | 0 | 0 |

**Output: `true`** ✅ — kombinasi gerakan apa pun tetap valid selama tiap arah punya pasangan penyeimbang, tidak harus berurutan bersebelahan.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String kosong (`moves = ""`) → loop tidak jalan, `x=0, y=0` tetap → `true`
- [ ] Gerakan tidak seimbang di salah satu sumbu saja (misal `"UUD"`) → `y` berakhir di `1`, langsung `false` meski sumbu `x` tidak pernah disentuh
- [ ] Urutan gerakan campur aduk tapi tetap seimbang secara total (`"RLUD"`, `"URDL"`, dst) → tetap `true`, karena posisi akhir tidak bergantung urutan, hanya total akumulasi tiap sumbu
- [ ] Semua karakter sama (`"UUUU"`) → jelas tidak seimbang → `false`

______________________________________________________________________

## 🔧 Kenapa Urutan Gerakan Tidak Berpengaruh?

Karena `x` dan `y` adalah **penjumlahan/akumulasi murni** (`+1` atau `-1` di tiap langkah), hasil akhirnya hanya bergantung pada **total** berapa kali tiap arah muncul — bukan urutan kemunculannya. Ini sifat operasi penjumlahan yang **komutatif**: `1 - 1 + 1 - 1` hasilnya sama saja dengan `1 + 1 - 1 - 1`, yaitu `0`. Itu sebabnya solusi ini tidak perlu melacak lintasan atau posisi di tiap titik waktu — cukup akumulasi akhir dari `x` dan `y`.

______________________________________________________________________

## 🔧 Alternatif: Hitung Selisih Frekuensi Karakter

```java
public boolean judgeCircle(String moves) {
    int u = 0, d = 0, l = 0, r = 0;
    for (char c : moves.toCharArray()) {
        switch (c) {
            case 'U': u++; break;
            case 'D': d++; break;
            case 'L': l++; break;
            case 'R': r++; break;
        }
    }
    return u == d && l == r;
}
```

Versi ini menghitung frekuensi tiap arah secara eksplisit, lalu bandingkan pasangan yang berlawanan (`U` vs `D`, `L` vs `R`). Secara logika identik dengan versi asli (menjumlah `+1`/`-1` langsung ke koordinat), tapi versi asli sedikit lebih ringkas karena tidak perlu 4 variabel counter terpisah — cukup akumulasi bersih per sumbu.

| Approach | Time | Space |
| ------------------------------- | ---- | ----- |
| Akumulasi koordinat (kode asli) | O(n) | O(1) |
| Hitung frekuensi tiap arah | O(n) | O(1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang bagus untuk pola **"simulasi posisi 2D dengan akumulasi delta"** — alih-alih melacak seluruh lintasan robot, cukup jumlahkan efek bersih tiap gerakan ke koordinat akhir, karena operasi penjumlahan bersifat komutatif dan tidak peduli urutan. Pola ini jadi fondasi untuk soal-soal simulasi posisi yang lebih kompleks, seperti _Robot Bounded In Circle_ atau _Number of Distinct Islands_, di mana melacak posisi/arah relatif jadi kunci utama solusinya. 🎯obot Return to Origin

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Simulation
- **Link**: [Problem](https://leetcode.com/problems/robot-return-to-origin/)
- **Solution**: [Code](../../leetcode/RobotReturnToOrigin.java)

______________________________________________________________________

## 📄 Problem Summary

Robot mulai di posisi `(0, 0)` pada bidang 2D. Diberikan string `moves` berisi urutan gerakan, di mana tiap karakter adalah salah satu dari:

- `'U'` — bergerak ke atas (`y++`)
- `'D'` — bergerak ke bawah (`y--`)
- `'L'` — bergerak ke kiri (`x--`)
- `'R'` — bergerak ke kanan (`x++`)

Kembalikan `true` kalau setelah menjalankan **semua** gerakan, robot kembali tepat ke posisi awal `(0, 0)`.

Contoh:

- `moves = "UD"` → `true` (naik lalu turun, balik ke posisi awal)
- `moves = "LL"` → `false` (dua kali ke kiri, berakhir di `(-2, 0)`)

______________________________________________________________________

## 💡 Intuition

Soal ini murni **simulasi posisi** dengan dua koordinat independen, `x` (horizontal) dan `y` (vertikal). Karena tiap gerakan cuma mempengaruhi salah satu sumbu:

- `U`/`D` cuma mengubah `y`
- `L`/`R` cuma mengubah `x`

...robot kembali ke titik asal **jika dan hanya jika** jumlah gerakan `U` sama dengan jumlah gerakan `D` (saling menetralkan di sumbu `y`), **dan** jumlah gerakan `R` sama dengan jumlah gerakan `L` (saling menetralkan di sumbu `x`). Tidak perlu melacak jejak lintasan penuh — cukup akumulasi posisi akhir, lalu cek apakah `(x, y) == (0, 0)`.

______________________________________________________________________

## 🔍 Approach

### Simulasi Posisi — Single Pass

1. Inisialisasi `x = 0`, `y = 0`.
1. Loop tiap karakter `i` di `moves`:
   - `'U'` → `y++`
   - `'D'` → `y--`
   - `'R'` → `x++`
   - `'L'` → `x--`
1. Setelah loop selesai, return `x == 0 && y == 0`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh string |
| **Space** | O(1) — hanya dua variabel koordinat |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `moves = "UD"`

| char | x | y |
| ---- | --- | --- |
| U | 0 | 1 |
| D | 0 | 0 |

`x==0 && y==0` → **Output: `true`** ✅

______________________________________________________________________

**Input:** `moves = "LL"`

| char | x | y |
| ---- | --- | --- |
| L | -1 | 0 |
| L | -2 | 0 |

`x==0 && y==0` → `-2 != 0` → **Output: `false`** ✅

______________________________________________________________________

**Input:** `moves = "UDLR"`

| char | x | y |
| ---- | --- | --- |
| U | 0 | 1 |
| D | 0 | 0 |
| L | -1 | 0 |
| R | 0 | 0 |

**Output: `true`** ✅ — kombinasi gerakan apa pun tetap valid selama tiap arah punya pasangan penyeimbang, tidak harus berurutan bersebelahan.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String kosong (`moves = ""`) → loop tidak jalan, `x=0, y=0` tetap → `true`
- [ ] Gerakan tidak seimbang di salah satu sumbu saja (misal `"UUD"`) → `y` berakhir di `1`, langsung `false` meski sumbu `x` tidak pernah disentuh
- [ ] Urutan gerakan campur aduk tapi tetap seimbang secara total (`"RLUD"`, `"URDL"`, dst) → tetap `true`, karena posisi akhir tidak bergantung urutan, hanya total akumulasi tiap sumbu
- [ ] Semua karakter sama (`"UUUU"`) → jelas tidak seimbang → `false`

______________________________________________________________________

## 🔧 Kenapa Urutan Gerakan Tidak Berpengaruh?

Karena `x` dan `y` adalah **penjumlahan/akumulasi murni** (`+1` atau `-1` di tiap langkah), hasil akhirnya hanya bergantung pada **total** berapa kali tiap arah muncul — bukan urutan kemunculannya. Ini sifat operasi penjumlahan yang **komutatif**: `1 - 1 + 1 - 1` hasilnya sama saja dengan `1 + 1 - 1 - 1`, yaitu `0`. Itu sebabnya solusi ini tidak perlu melacak lintasan atau posisi di tiap titik waktu — cukup akumulasi akhir dari `x` dan `y`.

______________________________________________________________________

## 🔧 Alternatif: Hitung Selisih Frekuensi Karakter

```java
public boolean judgeCircle(String moves) {
    int u = 0, d = 0, l = 0, r = 0;
    for (char c : moves.toCharArray()) {
        switch (c) {
            case 'U': u++; break;
            case 'D': d++; break;
            case 'L': l++; break;
            case 'R': r++; break;
        }
    }
    return u == d && l == r;
}
```

Versi ini menghitung frekuensi tiap arah secara eksplisit, lalu bandingkan pasangan yang berlawanan (`U` vs `D`, `L` vs `R`). Secara logika identik dengan versi asli (menjumlah `+1`/`-1` langsung ke koordinat), tapi versi asli sedikit lebih ringkas karena tidak perlu 4 variabel counter terpisah — cukup akumulasi bersih per sumbu.

| Approach | Time | Space |
| ------------------------------- | ---- | ----- |
| Akumulasi koordinat (kode asli) | O(n) | O(1) |
| Hitung frekuensi tiap arah | O(n) | O(1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang bagus untuk pola **"simulasi posisi 2D dengan akumulasi delta"** — alih-alih melacak seluruh lintasan robot, cukup jumlahkan efek bersih tiap gerakan ke koordinat akhir, karena operasi penjumlahan bersifat komutatif dan tidak peduli urutan. Pola ini jadi fondasi untuk soal-soal simulasi posisi yang lebih kompleks, seperti _Robot Bounded In Circle_ atau _Number of Distinct Islands_, di mana melacak posisi/arah relatif jadi kunci utama solusinya. 🎯

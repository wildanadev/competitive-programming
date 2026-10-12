# 4078. Three Fibonacci Sum

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Simulation
- **Link**: [Problem](https://leetcode.com/problems/three-fibonacci-sum/)
- **Solution**: [Code](../../leetcode/ThreeFibonacciSum.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan bilangan bulat `n`. Deret Fibonacci dimulai dari `0` dan `1`, dan tiap suku berikutnya adalah jumlah dua suku sebelumnya: `0, 1, 1, 2, 3, 5, 8, 13, ...`. Kembalikan `true` kalau `n` bisa dinyatakan sebagai jumlah **tiga suku berurutan** dari deret Fibonacci, dan `false` kalau tidak.

Contoh:

- `n = 16` → `true`
  - Tiga suku berurutan `3, 5, 8` berjumlah `3 + 5 + 8 = 16`.
- `n = 8` → `false`
  - `1 + 2 + 5 = 8`, tapi suku-sukunya **tidak berurutan**. Tidak ada tiga suku berurutan yang berjumlah `8`.
- `n = 2` → `true`
  - Tiga suku pertama: `0 + 1 + 1 = 2`.

**Constraint:** `1 <= n <= 10^9`

______________________________________________________________________

## 💡 Intuition

Jumlah tiga suku berurutan `F(k) + F(k+1) + F(k+2)` **selalu naik** seiring `k` bertambah:

`2, 4, 6, 10, 16, 26, ...`

Karena monoton naik, kita tidak perlu mencoba semua kombinasi. Cukup geser "jendela" tiga suku ke kanan sampai jumlahnya **mencapai atau melewati** `n`, lalu cek apakah jumlahnya tepat `n`.

Karena Fibonacci tumbuh eksponensial, jendela hanya perlu digeser sekitar 40-an kali untuk `n` sampai `10^9`.

______________________________________________________________________

## 🔍 Approach

### Simulasi Jendela Geser

1. Mulai dari jendela `(a, b, c) = (0, 1, 1)` dengan jumlah `2`.
1. Selama `a + b + c < n`, geser jendela satu langkah: `(a, b, c) → (b, c, b + c)`.
1. Setelah loop berhenti, jumlah jendela sudah `>= n`. Kembalikan `a + b + c == n`.

```java
class Solution {
    public boolean threeFibonacciSum(int n) {
        long a = 0, b = 1, c = 1;
        while (a + b + c < n) {
            a = b;
            b = c;
            c = a + b;
        }
        return a + b + c == n;
    }
}
```

Variabel memakai `long` supaya aman dari overflow. Jumlah jendela terakhir paling banyak sekitar `2n`, dan itu masih lebih besar dari batas `int` kalau `n` mendekati `10^9`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------- |
| **Time** | O(log n) — Fibonacci tumbuh eksponensial, loop hanya ~40-an kali |
| **Space** | O(1) — hanya tiga variabel jendela |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 16`

| Jendela (a, b, c) | Jumlah | Jumlah < 16? | Aksi |
| ----------------- | ------ | ------------ | -------- |
| (0, 1, 1) | 2 | ya | geser |
| (1, 1, 2) | 4 | ya | geser |
| (1, 2, 3) | 6 | ya | geser |
| (2, 3, 5) | 10 | ya | geser |
| (3, 5, 8) | 16 | tidak | berhenti |

`16 == 16` → **Output: `true`** ✅

______________________________________________________________________

**Input:** `n = 8`

| Jendela (a, b, c) | Jumlah | Jumlah < 8? | Aksi |
| ----------------- | ------ | ----------- | -------- |
| (0, 1, 1) | 2 | ya | geser |
| (1, 1, 2) | 4 | ya | geser |
| (1, 2, 3) | 6 | ya | geser |
| (2, 3, 5) | 10 | tidak | berhenti |

`10 != 8` → **Output: `false`** ✅

______________________________________________________________________

**Input:** `n = 2`

| Jendela (a, b, c) | Jumlah | Jumlah < 2? | Aksi |
| ----------------- | ------ | ----------- | -------- |
| (0, 1, 1) | 2 | tidak | berhenti |

`2 == 2` → **Output: `true`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → jumlah terkecil adalah `0 + 1 + 1 = 2`, jadi hasilnya `false`
- [ ] `n = 2` → jendela awal langsung cocok, hasilnya `true` (loop tidak berjalan sama sekali)
- [ ] `n` ganjil → selalu `false` (lihat bagian pendekatan matematis di bawah)
- [ ] `n = 10^9` → loop sekitar 40-an iterasi, `long` mencegah overflow

______________________________________________________________________

## 🔧 Kenapa Harus `<`, Bukan `<=`?

```java
while (a + b + c < n)   // benar
while (a + b + c <= n)  // salah
```

Loop berhenti saat kondisinya **salah**. Kalau kondisinya `<=`, loop baru berhenti ketika `a + b + c > n`, sehingga pengecekan `a + b + c == n` setelah loop **tidak akan pernah benar**. Untuk `n = 2`, jendela `(0, 1, 1)` berjumlah `2`, loop tetap menggeser ke `(1, 1, 2)` yang berjumlah `4`, lalu mengembalikan `false`, padahal jawabannya `true`.

Dengan `<`, loop berhenti tepat ketika jumlah jendela pertama kali **`>= n`**. Pada titik itu pengecekan `== n` bermakna: kalau jumlahnya sama, `n` ditemukan; kalau lebih besar, `n` dilewati dan tidak mungkin ketemu lagi karena jumlah hanya naik.

______________________________________________________________________

## 🔧 Alternatif: Observasi Matematis

Karena `F(k) + F(k+1) = F(k+2)`, maka:

```
F(k) + F(k+1) + F(k+2) = F(k+2) + F(k+2) = 2 · F(k+2)
```

Jadi `n` adalah jumlah tiga suku berurutan **jika dan hanya jika** `n` genap dan `n / 2` adalah bilangan Fibonacci.

```java
public boolean threeFibonacciSum(int n) {
    if (n % 2 != 0) return false;
    int m = n / 2;
    long a = 1, b = 1;
    while (b < m) {
        long t = a + b;
        a = b;
        b = t;
    }
    return b == m;
}
```

Cek: `n = 16` → `16 / 2 = 8` adalah Fibonacci → `true`. `n = 8` → `8 / 2 = 4` bukan Fibonacci → `false`. `n = 2` → `2 / 2 = 1` adalah Fibonacci → `true`.

| Approach | Time | Space | Catatan |
| --------------------------------- | -------- | ----- | -------------------------------------------- |
| Simulasi jendela geser | O(log n) | O(1) | Langsung mengikuti definisi soal |
| Cek `n` genap dan `n/2` Fibonacci | O(log n) | O(1) | Lebih ringkas, butuh langkah penurunan rumus |

Keduanya sama cepat. Versi simulasi lebih mudah dibuktikan benar karena persis menerjemahkan definisi soal.

______________________________________________________________________

## 📌 Key Takeaway

Pola "geser sampai mencapai atau melewati target, lalu cek kesamaan" bergantung pada **kondisi berhenti loop yang tepat**. Pakai `<` agar loop berhenti di jumlah pertama yang `>= n`, bukan `<=` yang selalu melewati target. Selalu uji dengan kasus terkecil dari contoh soal (`n = 2`) karena bug seperti ini langsung ketahuan di sana. Selain itu, mengenali identitas `F(k) + F(k+1) = F(k+2)` mengubah soal jadi pengecekan "apakah `n/2` bilangan Fibonacci". 🎯

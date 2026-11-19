
# 1137. N-th Tribonacci Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Dynamic Programming, Memoization
- **Link**: [Problem](https://leetcode.com/problems/n-th-tribonacci-number/)
- **Solution**: [Code](../../leetcode/NthTribonacciNumber.java)

---

## 📄 Problem Summary

Barisan **Tribonacci** didefinisikan sebagai:

- `T(0) = 0`, `T(1) = 1`, `T(2) = 1`
- `T(n + 3) = T(n) + T(n + 1) + T(n + 2)` untuk `n >= 0`

Diberikan `n`, kembalikan `T(n)`. Jawabannya dijamin muat di `int` 32-bit (soal membatasi `n` sampai `37`).

Contoh:

- `n = 4` → `4` (`T(3) = 0 + 1 + 1 = 2`, `T(4) = 1 + 1 + 2 = 4`)
- `n = 25` → `1389537`

---

## 💡 Intuition

Ini versi tiga suku dari barisan Fibonacci: tiap suku adalah **jumlah tiga suku sebelumnya**. Kita hanya butuh **tiga suku terakhir** untuk menghitung suku berikutnya, jadi tidak perlu menyimpan seluruh barisan. Cukup tiga variabel, `a`, `b`, `c`, yang menyimpan tiga suku terakhir, dan "digeser" satu langkah setiap iterasi:

```
(a, b, c) = (T(k), T(k+1), T(k+2))   →   (b, c, a + b + c) = (T(k+1), T(k+2), T(k+3))
```

Pendekatan rekursif langsung (`tribonacci(n-1) + tribonacci(n-2) + tribonacci(n-3)`) akan menghitung ulang suku yang sama berkali-kali dan tumbuh **eksponensial** (sekitar `1,84^n` pemanggilan), terlalu lambat untuk `n = 37`. Iterasi dengan tiga variabel menghilangkan pengulangan itu.

---

## 🔍 Approach

### Iterasi dengan Tiga Variabel Bergeser

1. Kasus dasar: kalau `n <= 1` → kembalikan `n` (`T(0) = 0`, `T(1) = 1`). Kalau `n == 2` → kembalikan `1`.
2. Inisialisasi `a = 0`, `b = 1`, `c = 1` (yaitu `T(0)`, `T(1)`, `T(2)`).
3. Untuk `i` dari `3` sampai `n`:
   - `ans = a + b + c` (itu `T(i)`).
   - Geser: `a = b`, `b = c`, `c = ans`.
4. Kembalikan `ans`.

---

## 🧮 Complexity

|                 |                                |
| --------------- | ------------------------------ |
| **Time**  | O(n) — satu kali loop         |
| **Space** | O(1) — empat variabel integer |

---

## 🧪 Dry Run

**Input:** `n = 4`

Awal: `a = 0`, `b = 1`, `c = 1`.

| i | ans = a + b + c   | a sesudah | b sesudah | c sesudah |
| - | ----------------- | --------- | --------- | --------- |
| 3 | `0 + 1 + 1 = 2` | 1         | 1         | 2         |
| 4 | `1 + 1 + 2 = 4` | 1         | 2         | 4         |

**Output: `4`** ✅

---

**Input:** `n = 25`

Deretan suku yang dihasilkan:

| n | T(n) | n  | T(n)  | n  | T(n)              |
| - | ---- | -- | ----- | -- | ----------------- |
| 0 | 0    | 9  | 81    | 18 | 19513             |
| 1 | 1    | 10 | 149   | 19 | 35890             |
| 2 | 1    | 11 | 274   | 20 | 66012             |
| 3 | 2    | 12 | 504   | 21 | 121415            |
| 4 | 4    | 13 | 927   | 22 | 223317            |
| 5 | 7    | 14 | 1705  | 23 | 410744            |
| 6 | 13   | 15 | 3136  | 24 | 755476            |
| 7 | 24   | 16 | 5768  | 25 | **1389537** |
| 8 | 44   | 17 | 10609 |    |                   |

Suku terakhir: `T(25) = T(24) + T(23) + T(22) = 755476 + 410744 + 223317 = 1389537`.

**Output: `1389537`** ✅

---

## ⚠️ Edge Cases

- [ ] `n = 0` → `0` (kasus dasar)
- [ ] `n = 1` → `1` (kasus dasar)
- [ ] `n = 2` → `1` (kasus dasar; tanpanya, loop yang dimulai dari `i = 3` tidak berjalan dan `ans` tetap `0`, hasil salah)
- [ ] `n = 3` → loop berjalan sekali, `ans = 0 + 1 + 1 = 2`
- [ ] `n = 37` (batas atas) → `T(37) = 2082876103`, masih di bawah batas `int` (`2147483647`)
- [ ] `n = 38` (di luar constraint) → `T(38) = 3831006429` akan **overflow** `int`; itu sebabnya soal membatasi `n <= 37`. Kalau batas `n` dinaikkan, gunakan `long`

---

## 🔧 Alternatif 1: Tanpa Kasus Khusus

Dengan menggeser jendela tiga suku sebanyak `n` kali dan mengembalikan `a` (suku paling kiri), tidak perlu cabang `n <= 1` dan `n == 2`:

```java
public int tribonacci(int n) {
    int a = 0, b = 1, c = 1;           // T(0), T(1), T(2)
    for (int i = 0; i < n; i++) {
        int next = a + b + c;
        a = b;
        b = c;
        c = next;
    }
    return a;                          // setelah n pergeseran, a = T(n)
}
```

Cek: `n = 0` → tidak ada iterasi, `a = 0` ✅. `n = 1` → satu pergeseran: `a = 1` ✅. `n = 2` → `a = 1` ✅. `n = 4` → `a = 4` ✅. Versi ini menghitung dua suku lebih jauh dari yang dikembalikan (variabel `c` menyimpan `T(n + 2)`), yang untuk `n` mendekati batas bisa melewati batas `int` secara diam-diam (Java tidak melempar error saat overflow, nilainya hanya "membungkus"). Itu tidak mengubah hasil karena yang dikembalikan adalah `a`, tapi perlu dipahami.

---

## 🔧 Alternatif 2: Memoization (Rekursi dengan Cache)

```java
class Solution {
    private int[] memo = new int[38];

    public int tribonacci(int n) {
        if (n <= 1) return n;
        if (n == 2) return 1;
        if (memo[n] != 0) return memo[n];                 // sudah pernah dihitung
        return memo[n] = tribonacci(n - 1) + tribonacci(n - 2) + tribonacci(n - 3);
    }
}
```

Tiap `T(k)` dihitung sekali lalu disimpan, sehingga totalnya `O(n)`, tapi memakai `O(n)` ruang dan kedalaman rekursi `O(n)`. Pendekatan ini berguna untuk memperlihatkan cara memperbaiki rekursi naif yang eksponensial, tapi iterasi dengan tiga variabel lebih hemat.

| Approach                          | Time      | Space | Catatan                                                        |
| --------------------------------- | --------- | ----- | -------------------------------------------------------------- |
| Rekursi naif                      | O(1,84^n) | O(n)  | Terlalu lambat untuk `n = 37`                                |
| Memoization                       | O(n)      | O(n)  | Rekursi dengan cache                                           |
| Iterasi tiga variabel (kode asli) | O(n)      | O(1)  | Paling hemat dan sederhana                                     |
| Eksponensiasi matriks             | O(log n)  | O(1)  | Berlebihan untuk `n <= 37`, berguna untuk `n` sangat besar |

---

## 📌 Key Takeaway

Barisan yang suku berikutnya hanya bergantung pada beberapa suku sebelumnya bisa dihitung dengan **jendela variabel yang bergeser**, tanpa array atau rekursi, dalam `O(n)` waktu dan `O(1)` ruang. Pola ini adalah bentuk paling sederhana dari **dynamic programming dengan optimasi ruang**: simpan hanya subhasil yang masih dibutuhkan. Perhatikan juga dua hal praktis: kasus dasar (`n = 2` mudah terlewat karena loop dimulai dari `i = 3`), dan batas overflow (`n <= 37` adalah suku terbesar yang muat di `int`). Pola serupa muncul di *Fibonacci Number*, *Climbing Stairs*, dan *Min Cost Climbing Stairs*. 🎯

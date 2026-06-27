# 258. Add Digits

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Simulation, Number Theory
- **Link**: [Problem](https://leetcode.com/problems/add-digits/)
- **Solution**: [Code](../../leetcode/AddDigits.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer non-negatif `num`, ulangi proses menjumlahkan semua digitnya sampai hasilnya **satu digit**. Kembalikan hasil akhir tersebut.

Contoh:

- `num = 38` → `2` (3+8=11, 1+1=2)
- `num = 0` → `0`

______________________________________________________________________

## 💡 Intuition

Proses berulang "jumlahkan semua digit sampai satu digit" disebut **digital root** dalam matematika. Ada rumus closed-form berdasarkan teori modulo 9:

```
digital_root(n) = 1 + (n - 1) % 9   untuk n > 0
digital_root(0) = 0
```

Atau ekuivalen dengan pendekatan kode ini:

```
Jika num % 9 == 0 (dan num != 0) → hasil = 9
Jika tidak → hasil = num % 9
```

**Mengapa rumus ini bekerja?** Karena `10 ≡ 1 (mod 9)`, setiap digit di posisi manapun berkontribusi nilai yang sama terhadap modulo 9 sebagai dirinya sendiri (bukan dikali kelipatan 10). Jadi `num mod 9` = `sum_of_digits(num) mod 9` = `digital_root(num) mod 9`.

______________________________________________________________________

## 🔍 Approach

### Math Formula — Digital Root via Modulo 9

1. Jika `num == 0` → return `0`.
1. Jika `num % 9 == 0` (dan `num != 0`) → return `9`.
1. Selain itu → return `num % 9`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(1) — hanya operasi modulo, tanpa loop |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `num = 38`

```
num == 0? ❌
38 % 9 = 2 (38 = 4×9 + 2), bukan 0
→ return 38 % 9 = 2
```

**Output: `2` ✅** (Verifikasi: 3+8=11, 1+1=2)

______________________________________________________________________

**Input:** `num = 0`

```
num == 0? ✅ → return 0
```

**Output: `0` ✅**

______________________________________________________________________

**Input:** `num = 9`

```
num == 0? ❌
9 % 9 = 0 → return 9
```

**Output: `9` ✅** (Verifikasi: digit tunggal 9, sudah satu digit)

______________________________________________________________________

**Input:** `num = 18`

```
num == 0? ❌
18 % 9 = 0 → return 9
```

**Output: `9` ✅** (Verifikasi: 1+8=9, sudah satu digit)

______________________________________________________________________

**Input:** `num = 132`

```
132 % 9 = 6 (132 = 14×9 + 6)
→ return 6
```

**Output: `6` ✅** (Verifikasi: 1+3+2=6)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `num = 0` → kasus khusus, return `0` (bukan `9`, karena `0 % 9 = 0` tapi seharusnya `0`)
- [ ] `num` kelipatan 9 (selain 0) → selalu return `9`
- [ ] `num` satu digit → return `num` itu sendiri (karena `num % 9` atau aturan kelipatan 9 tetap konsisten)

______________________________________________________________________

## 🔧 Mengapa `10 ≡ 1 (mod 9)` Membuat Trik Ini Bekerja?

```
10 mod 9 = 1
100 mod 9 = 1
1000 mod 9 = 1
...
10^k mod 9 = 1 untuk semua k >= 0
```

Karena setiap pangkat 10 ≡ 1 (mod 9), maka:

```
num = d_n×10^n + d_(n-1)×10^(n-1) + ... + d_0×10^0

num mod 9 = (d_n×1 + d_(n-1)×1 + ... + d_0×1) mod 9
          = (d_n + d_(n-1) + ... + d_0) mod 9
          = sum_of_digits(num) mod 9
```

Inilah mengapa `num % 9` setara dengan menjumlahkan semua digitnya berulang kali sampai modulo 9 — **tanpa perlu loop sama sekali**!

______________________________________________________________________

## 🔧 Kenapa Kasus Khusus untuk Kelipatan 9?

```
num = 9: 9 % 9 = 0, tapi digital root yang benar adalah 9, bukan 0!
num = 18: 1+8=9, digital root = 9, tapi 18 % 9 = 0
```

Modulo 9 menghasilkan rentang `[0, 8]`, tapi digital root yang valid untuk angka positif kelipatan 9 adalah **9 itu sendiri** (tidak pernah `0` kecuali `num` aslinya `0`). Itulah mengapa perlu pengecekan khusus: `num % 9 == 0 && num != 0 → return 9`.

______________________________________________________________________

## 🚀 Perbandingan dengan Pendekatan Simulasi (Loop)

```java
// Simulasi — loop sampai satu digit
public int addDigits(int num) {
    while (num >= 10) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        num = sum;
    }
    return num;
}
```

| Approach | Time | Space | Catatan |
| ------------------- | ----------------------------------- | ----- | ------------------------------------- |
| Math Formula (kode) | O(1) | O(1) | Elegan, butuh tahu teori digital root |
| Simulasi | O(log num) per putaran, total kecil | O(1) | Lebih intuitif tanpa perlu tahu teori |

______________________________________________________________________

## 📌 Key Takeaway

**Digital root** memiliki rumus matematis elegan berbasis sifat `10 ≡ 1 (mod 9)` — setiap digit berkontribusi nilainya sendiri terhadap modulo 9, tidak peduli posisinya. Ini mengubah soal yang tampak butuh loop berulang menjadi solusi O(1) murni matematika. Kasus khusus kelipatan 9 (selain 0) penting karena range modulo `[0,8]` tidak mencakup `9` sebagai hasil valid secara langsung. 🎯

# 263. Ugly Number

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/ugly-number/)
- **Solution**: [Code](../../leetcode/UglyNumber.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan integer `n`, return `true` jika `n` adalah **ugly number** — bilangan positif yang faktor primanya hanya `2`, `3`, dan/atau `5`.

Contoh:

- `n = 6` → `true` (6 = 2×3)
- `n = 1` → `true` (tidak ada faktor prima, dianggap valid)
- `n = 14` → `false` (14 = 2×7, ada faktor prima 7)

______________________________________________________________________

## 💡 Intuition

Jika `n` hanya punya faktor prima `2`, `3`, dan `5`, maka kita bisa **terus membagi** `n` dengan `2`, `3`, dan `5` selama masih bisa dibagi habis. Jika di akhir hasilnya `1`, berarti semua faktor sudah "terpakai" — `n` adalah ugly number.

Jika ada sisa selain `1` setelah pembagian habis oleh 2, 3, 5 — berarti ada faktor prima lain yang tidak diizinkan.

______________________________________________________________________

## 🔍 Approach

### Repeated Division

1. Jika `n <= 0` → bukan ugly number (harus positif) → return `false`.
1. Bagi `n` dengan `2` selama habis dibagi.
1. Bagi `n` dengan `3` selama habis dibagi.
1. Bagi `n` dengan `5` selama habis dibagi.
1. Return `n == 1` — jika tersisa `1`, semua faktor adalah 2/3/5.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(log n) — setiap pembagian mengurangi `n` minimal setengah |
| **Space** | O(1) — hanya variabel `n` yang dimodifikasi |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 6`

```
n=6 > 0 → lanjut

while n%2==0: n=6→3 (6/2=3), 3%2≠0 → stop
while n%3==0: n=3→1 (3/3=1), 1%3≠0 → stop
while n%5==0: 1%5≠0 → tidak jalan

n == 1? ✅ → true
```

**Output: `true` ✅**

______________________________________________________________________

**Input:** `n = 14`

```
n=14 > 0 → lanjut

while n%2==0: n=14→7 (14/2=7), 7%2≠0 → stop
while n%3==0: 7%3≠0 → tidak jalan
while n%5==0: 7%5≠0 → tidak jalan

n == 1? ❌ (n=7) → false
```

**Output: `false` ✅** — 7 adalah faktor prima yang tidak diizinkan

______________________________________________________________________

**Input:** `n = 1`

```
n=1 > 0 → lanjut
while n%2==0: 1%2≠0 → tidak jalan
while n%3==0: 1%3≠0 → tidak jalan
while n%5==0: 1%5≠0 → tidak jalan

n == 1? ✅ → true
```

**Output: `true` ✅**

______________________________________________________________________

**Input:** `n = 0`

```
n <= 0? ✅ → return false langsung
```

**Output: `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 1` → ugly number valid (tidak ada faktor prima sama sekali)
- [ ] `n <= 0` → selalu `false` (constraint: ugly number harus positif)
- [ ] `n` adalah pangkat dari 2, 3, atau 5 (misal `n=32=2⁵`) → habis dibagi sampai 1
- [ ] `n` prima besar selain 2,3,5 (misal `n=7,11,13`) → tidak terbagi sama sekali → return `false`

______________________________________________________________________

## 🔧 Kenapa Urutan Pembagian (2, 3, 5) Tidak Masalah?

```java
while (n % 2 == 0) n /= 2;
while (n % 3 == 0) n /= 3;
while (n % 5 == 0) n /= 5;
```

Urutan pembagian **tidak mempengaruhi hasil akhir** karena setiap faktor prima dibagi habis sebelum lanjut ke faktor berikutnya. Hasil akhir `n` setelah ketiga loop selalu sama tidak peduli urutannya — ini adalah sifat dari faktorisasi prima (unique factorization).

```
n = 60 = 2² × 3 × 5

Urutan 2,3,5: 60→30→15→5→1 (bagi 2 dua kali, 3 sekali, 5 sekali)
Urutan 5,3,2: 60→12→4→2→1 (bagi 5 sekali, 3 sekali, 2 dua kali)

Keduanya hasil akhir = 1 ✅
```

______________________________________________________________________

## 🔧 Kenapa `while`, Bukan `if`?

```java
while (n % 2 == 0) n /= 2;  // ✅ bagi berulang kali
if (n % 2 == 0) n /= 2;     // ❌ hanya bagi sekali
```

Faktor `2` bisa muncul **lebih dari sekali** dalam faktorisasi (misal `8 = 2×2×2`). `while` memastikan semua kemunculan faktor tersebut dibagi habis, bukan hanya sekali.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi sederhana **faktorisasi prima** — bagi habis dengan setiap faktor yang diizinkan, dan cek apakah hasil akhirnya `1`. Kunci dari pendekatan ini: jika setelah membagi habis dengan 2, 3, dan 5 hasilnya bukan `1`, pasti ada faktor prima lain yang "tersisa" dan tidak diizinkan. Pola "repeated division by allowed factors" ini juga muncul di soal terkait seperti _Super Ugly Number_ dengan faktor prima yang lebih banyak. 🎯

# 3974. Maximum Total Sum of K Selected Elements

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Greedy, Sorting
- **Link**: [Problem](https://leetcode.com/problems/maximum-total-sum-of-k-selected-elements/)
- **Solution**: [Code](../../leetcode/MaximumTotalSumOfKSelectedElements.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`, integer `k`, dan `mul`. Pilih tepat `k` elemen dan proses satu per satu (urutan bebas). Untuk setiap elemen, pilih salah satu:

- Tambahkan nilainya ke total sum, **atau**
- Kalikan dengan `mul` saat ini, lalu tambahkan ke total sum.

Setelah setiap elemen diproses, `mul` **berkurang 1** (bisa jadi 0 atau negatif). Kembalikan total sum maksimum.

Contoh:

- `nums=[6,1,2,9]`, `k=3, mul=2` → `26`
- `nums=[3,7,5,2]`, `k=2, mul=4` → `43`

______________________________________________________________________

## 💡 Intuition

Dua keputusan greedy yang independen:

**1. Elemen mana yang dipilih?**
Karena setiap elemen minimal berkontribusi nilainya sendiri (opsi "tambah" tanpa kali), elemen dengan nilai **terbesar** selalu lebih baik dipilih. Pilih `k` elemen terbesar.

**2. Urutan pemrosesan & pilihan kali/tambah?**
`mul` berkurang setiap step — semakin awal diproses, semakin besar `mul`-nya. Untuk **memaksimalkan** kontribusi, proses elemen **terbesar lebih dulu** dengan `mul` yang masih besar.

**Insight kunci:** Karena `mul` hanya berkurang (tidak bisa pilih kapan menggunakannya), setiap elemen otomatis "dipasangkan" dengan nilai `mul` tertentu berdasarkan urutan prosesnya. Pilihan terbaik adalah `max(nums[i] * mul, nums[i])` — pilih multiply jika hasilnya lebih besar (relevan saat `mul` negatif atau 0, di mana addition lebih baik).

______________________________________________________________________

## 🔍 Approach

### Greedy: Sort + Pilih K Terbesar + Multiply dari Terbesar ke Terkecil

1. Sort `nums` ascending.
1. Loop dari elemen **terbesar** (`nums.length-1`) ke elemen ke-`k` dari belakang:
   - Untuk setiap elemen, ambil `max(mul * nums[i], nums[i])`.
   - `mul--` setelah setiap elemen.
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(1) — tidak ada struktur data tambahan (sorting in-place) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums=[6,1,2,9]`, `k=3, mul=2`

**Sort:** `[1,2,6,9]`

Loop dari `i=3` (terbesar) sampai `i=1` (3 elemen terbesar: 9,6,2):

| i | nums[i] | mul | mul\*nums[i] | max(mul\*nums[i], nums[i]) | ans | mul setelah |
| --- | ------- | --- | ------------ | -------------------------- | --- | ----------- |
| 3 | 9 | 2 | 18 | max(18,9)=18 | 18 | 1 |
| 2 | 6 | 1 | 6 | max(6,6)=6 | 24 | 0 |
| 1 | 2 | 0 | 0 | max(0,2)=2 | 26 | -1 |

**Output: `26` ✅**

______________________________________________________________________

**Input:** `nums=[3,7,5,2]`, `k=2, mul=4`

**Sort:** `[2,3,5,7]`

Loop dari `i=3` sampai `i=2` (2 elemen terbesar: 7,5):

| i | nums[i] | mul | mul\*nums[i] | max | ans | mul setelah |
| --- | ------- | --- | ------------ | --- | --- | ----------- |
| 3 | 7 | 4 | 28 | 28 | 28 | 3 |
| 2 | 5 | 3 | 15 | 15 | 43 | 2 |

**Output: `43` ✅**

______________________________________________________________________

**Input:** `nums=[4,4]`, `k=1, mul=1`

**Sort:** `[4,4]`

Loop dari `i=1` sampai `i=1` (1 elemen terbesar):

| i | nums[i] | mul | mul\*nums[i] | max | ans |
| --- | ------- | --- | ------------ | --- | --- |
| 1 | 4 | 1 | 4 | 4 | 4 |

**Output: `4` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `mul` menjadi negatif saat elemen masih diproses → `max(mul*nums[i], nums[i])` otomatis pilih addition
- [ ] `mul = 0` saat memproses → `max(0, nums[i])` → pilih `nums[i]` (addition lebih baik, karena `nums[i] >= 1`)
- [ ] `k = nums.length` → semua elemen dipilih
- [ ] Semua elemen sama → pemilihan elemen mana saja menghasilkan hasil sama

______________________________________________________________________

## 🔧 Kenapa Pilih K Elemen Terbesar?

Setiap elemen yang dipilih minimal berkontribusi nilainya sendiri (opsi addition selalu tersedia). Karena kita harus memilih tepat `k` elemen dan setiap elemen berkontribusi minimal `nums[i]`, memilih elemen dengan `nums[i]` terbesar memaksimalkan kontribusi minimum — dan karena opsi multiply hanya bisa menambah lebih jika `mul` positif, elemen besar tetap lebih menguntungkan dalam skenario manapun.

```
Bukti sederhana: tukar elemen kecil dengan elemen besar yang tidak terpilih
selalu menghasilkan total sum yang sama atau lebih besar
→ greedy pilih k terbesar optimal
```

______________________________________________________________________

## 🔧 Kenapa Proses dari Terbesar ke Terkecil (dengan `mul` Menurun)?

Ini adalah **prinsip rearrangement** — untuk memaksimalkan sum dari hasil kali dua sequence, pasangkan elemen terbesar dengan multiplier terbesar.

```
Elemen terpilih (desc): 9, 6, 2
mul yang tersedia (desc): 2, 1, 0

Pairing optimal: 9×2, 6×1, 2×0
```

Jika dipasangkan terbalik (9×0, 6×1, 2×2), hasilnya akan lebih kecil:

```
9×2+6×1+2×0 = 18+6+0 = 24 (sebelum pilihan addition)
9×0+6×1+2×2 = 0+6+4  = 10 (lebih kecil!)
```

Memasangkan nilai besar dengan multiplier besar selalu menghasilkan sum yang lebih besar atau sama (rearrangement inequality).

______________________________________________________________________

## 🔧 Kenapa `Math.max(mul * nums[i], nums[i])`, Bukan Selalu Multiply?

```java
ans += Math.max((long) mul * nums[i], nums[i]);
```

Saat `mul` sudah mengecil (0 atau negatif), mengalikan `nums[i]` dengan `mul` bisa menghasilkan nilai yang **lebih kecil** dari `nums[i]` sendiri (terutama jika `mul < 1`). Pengecekan ini memastikan kita selalu mengambil opsi terbaik antara multiply atau addition.

```
nums[i]=2, mul=0: max(0, 2) = 2  → pilih addition
nums[i]=2, mul=-1: max(-2, 2) = 2 → pilih addition
nums[i]=2, mul=3: max(6, 2) = 6  → pilih multiply
```

______________________________________________________________________

## 🔧 Kenapa Cast ke `(long)`?

```java
(long) mul * nums[i]
```

`mul` dan `nums[i]` masing-masing bisa sampai `10^5`, hasil kali bisa sampai `10^10` — melebihi batas `int` (`~2.1×10^9`). Cast ke `long` mencegah overflow.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan dua keputusan greedy independen: **pilih elemen terbesar** (karena setiap elemen minimal berkontribusi nilainya) dan **pasangkan dengan multiplier terbesar** (rearrangement inequality). Pengecekan `max(mul*nums[i], nums[i])` per elemen menangani kasus di mana `mul` sudah turun ke nilai yang membuat addition lebih baik daripada multiply. 🎯

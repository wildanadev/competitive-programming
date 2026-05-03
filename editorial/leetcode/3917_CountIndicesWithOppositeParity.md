# 3917. Count Indices With Opposite Parity

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Prefix Count
- **Link**: [Problem](https://leetcode.com/problems/count-indices-with-opposite-parity/)
- **Solution**: [Code](../../leetcode/CountIndicesWithOppositeParity.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums`, untuk setiap indeks `i` hitung berapa banyak indeks `j > i` yang memiliki **parity berbeda** dari `nums[i]`:

- Jika `nums[i]` **genap** → hitung indeks `j > i` yang `nums[j]` **ganjil**
- Jika `nums[i]` **ganjil** → hitung indeks `j > i` yang `nums[j]` **genap**

Kembalikan array hasil hitungan tersebut.

Contoh:

- `nums = [1,2,3,4]` → `[2,1,1,0]`
  - `i=0`: nums[0]=1 (ganjil) → hitung genap di kanan: {2,4} → 2
  - `i=1`: nums[1]=2 (genap) → hitung ganjil di kanan: {3} → 1
  - `i=2`: nums[2]=3 (ganjil) → hitung genap di kanan: {4} → 1
  - `i=3`: nums[3]=4 (genap) → tidak ada di kanan → 0

______________________________________________________________________

## 💡 Intuition

Daripada untuk setiap `i` loop ke kanan O(n) untuk menghitung parity berbeda — yang menghasilkan O(n²) — kita bisa **precompute** dari kanan ke kiri.

Saat kita iterasi dari **kanan ke kiri**, kita sudah tahu berapa elemen genap dan ganjil yang ada di **sebelah kanan** indeks saat ini. Saat kita tiba di indeks `i`:

- Jika `nums[i]` ganjil → jawaban adalah `evenCount` (jumlah genap di kanan)
- Jika `nums[i]` genap → jawaban adalah `oddCount` (jumlah ganjil di kanan)
- Setelah menyimpan jawaban, update counter

______________________________________________________________________

## 🔍 Approach

### Single Pass dari Kanan ke Kiri (Suffix Count)

1. Inisialisasi `oddCount = 0`, `evenCount = 0`.
1. Loop `i` dari `n-1` sampai `0`:
   - Jika `nums[i]` ganjil → `ans[i] = evenCount`, lalu `oddCount++`
   - Jika `nums[i]` genap → `ans[i] = oddCount`, lalu `evenCount++`
1. Return `ans`.

> Counter di-update **setelah** menyimpan `ans[i]` — elemen `i` sendiri tidak dihitung sebagai "di kanan i".

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------- |
| **Time** | O(n) — satu pass dari kanan ke kiri |
| **Space** | O(1) — hanya dua variabel counter (tidak termasuk output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,3,4]`

Iterasi dari kanan ke kiri:

| i | nums[i] | parity | ans[i] | Update | oddCount | evenCount |
| --- | ------- | ------ | ----------- | ----------- | -------- | --------- |
| — | — | — | — | init | 0 | 0 |
| 3 | 4 | genap | oddCount=0 | evenCount++ | 0 | 1 |
| 2 | 3 | ganjil | evenCount=1 | oddCount++ | 1 | 1 |
| 1 | 2 | genap | oddCount=1 | evenCount++ | 1 | 2 |
| 0 | 1 | ganjil | evenCount=2 | oddCount++ | 2 | 2 |

`ans = [2, 1, 1, 0]`

**Output: `[2,1,1,0]` ✅**

______________________________________________________________________

**Input:** `nums = [2,2,2]`

| i | nums[i] | parity | ans[i] | Update | oddCount | evenCount |
| --- | ------- | ------ | ---------- | ----------- | -------- | --------- |
| — | — | — | — | init | 0 | 0 |
| 2 | 2 | genap | oddCount=0 | evenCount++ | 0 | 1 |
| 1 | 2 | genap | oddCount=0 | evenCount++ | 0 | 2 |
| 0 | 2 | genap | oddCount=0 | evenCount++ | 0 | 3 |

`ans = [0, 0, 0]`

**Output: `[0,0,0]` ✅** — tidak ada elemen ganjil, semua jawaban 0

______________________________________________________________________

**Input:** `nums = [1,3,2,4,1]`

| i | nums[i] | parity | ans[i] | Update | oddCount | evenCount |
| --- | ------- | ------ | ----------- | ----------- | -------- | --------- |
| — | — | — | — | init | 0 | 0 |
| 4 | 1 | ganjil | evenCount=0 | oddCount++ | 1 | 0 |
| 3 | 4 | genap | oddCount=1 | evenCount++ | 1 | 1 |
| 2 | 2 | genap | oddCount=1 | evenCount++ | 1 | 2 |
| 1 | 3 | ganjil | evenCount=2 | oddCount++ | 2 | 2 |
| 0 | 1 | ganjil | evenCount=2 | oddCount++ | 3 | 2 |

`ans = [2, 2, 1, 1, 0]`

**Output: `[2,2,1,1,0]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama parity → semua `ans[i] = 0`
- [ ] Satu elemen → `ans[0] = 0` (tidak ada elemen di kanan)
- [ ] Elemen terakhir → selalu `ans[n-1] = 0`

______________________________________________________________________

## 🔧 Kenapa Iterasi dari Kanan, Bukan Kiri?

Jika iterasi dari **kiri ke kanan**, saat kita di indeks `i` kita belum tahu elemen-elemen di kanannya → perlu loop ke kanan O(n) → total O(n²).

Jika iterasi dari **kanan ke kiri**, saat kita di indeks `i`, kita sudah memproses semua elemen di kanannya → `oddCount` dan `evenCount` sudah berisi jumlah yang tepat → langsung ambil jawaban O(1).

```
Kiri ke kanan (O(n²)):
i=0: loop j=1..n-1 untuk hitung parity berbeda

Kanan ke kiri (O(n)):
i=n-1: simpan ans, update counter
i=n-2: counter sudah ada, langsung ambil
...
```

Ini adalah teknik **suffix count** — precompute dari kanan agar setiap query O(1).

______________________________________________________________________

## 🔧 Kenapa `(nums[i] & 1) == 1` untuk Cek Ganjil?

```java
(nums[i] & 1) == 1  // ganjil → bit terakhir = 1
(nums[i] & 1) == 0  // genap  → bit terakhir = 0
```

Bitwise AND dengan `1` hanya mengecek **bit terakhir** — lebih cepat dari `nums[i] % 2`. Untuk bilangan ganjil, bit terakhirnya selalu `1`; untuk genap selalu `0`.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik teknik **suffix count** — dengan iterasi dari kanan ke kiri dan mempertahankan dua counter (`oddCount` dan `evenCount`), setiap `ans[i]` bisa dijawab dalam O(1) tanpa nested loop. Pola "hitung sesuatu di sebelah kanan setiap indeks" hampir selalu bisa diselesaikan dengan suffix precompute, mirip dengan **suffix maximum/minimum** yang sudah kita pelajari sebelumnya. 🎯

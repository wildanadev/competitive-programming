# 1354. Construct Target Array with Multiple Sums

- **Platform**: LeetCode
- **Difficulty**: Hard
- **Topics**: Array, Heap (Priority Queue)
- **Link**: [Problem](https://leetcode.com/problems/construct-target-array-with-multiple-sums/)
- **Solution**: [Code](../../leetcode/ConstructTargetArrayWithMultipleSums.java)

______________________________________________________________________

## 📄 Problem Summary

Mulai dari array `[1,1,...,1]` (n elemen), dalam satu operasi gantikan satu elemen dengan **jumlah seluruh elemen array**. Apakah mungkin mencapai array `target`?

Contoh:

- `target = [9,3,5]` → `true`
- `target = [1,1,1,2]` → `false`
- `target = [8,5]` → `true`

______________________________________________________________________

## 💡 Intuition: Berpikir Mundur (Reverse)

Daripada maju dari `[1,1,...,1]` ke `target` — yang memiliki kemungkinan tak terbatas — kita **mundur dari `target` ke `[1,1,...,1]`**.

**Observasi kunci**: elemen terbesar di `target` pasti merupakan hasil operasi terakhir. Sebelum operasi itu, elemen tersebut adalah `curr - (sum of others)` = `curr - (sum - curr)`.

```
Maju:  [1,1,3] → ganti 1 dengan sum=5 → [5,1,3]
Mundur: [5,1,3] → elemen terbesar=5, sum_others=4 → sebelumnya adalah 5-4=1 → [1,1,3]
```

Kita terus mundurkan elemen terbesar sampai semua elemen `= 1`.

______________________________________________________________________

## 🔍 Approach

### Reverse Simulation dengan Max Heap

**Setup:**

- Masukkan semua elemen ke max heap, hitung `sum` total.

**Loop sampai `pq.peek() == 1`:**

1. Poll `curr` (elemen terbesar).
1. Hitung `rest = sum - curr` (jumlah elemen lainnya).
1. Jika `rest == 1` → bisa kembali ke `[1,1,...,1]` → return `true`.
1. Hitung `x = curr % rest` — nilai sebelum operasi (mundur banyak langkah sekaligus).
1. Jika `x == 0` atau `x == curr` → tidak mungkin → return `false`.
1. Update `sum = rest + x`, push `x` ke heap.

**Setelah loop:** semua elemen `= 1` → return `true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------- |
| **Time** | O(n log n × log(max)) — setiap iterasi O(log n), bisa ada log(max) iterasi per elemen |
| **Space** | O(n) — max heap |

______________________________________________________________________

## 🧪 Kenapa `curr % rest`, Bukan `curr - rest`?

Anggap `curr = 100`, `rest = 3`. Jika kita mundur satu langkah:

```
100 → 100 - 3 = 97 → 97 - 3 = 94 → 94 - 3 = 91 → ... → 1
```

Ini butuh ~33 langkah — sangat lambat untuk nilai besar!

Dengan modulo, kita mundur **banyak langkah sekaligus**:

```
100 % 3 = 1 → langsung dapat nilai sebelumnya!
```

Ini ekuivalen dengan mengurangi `rest` berulang kali sampai hasilnya < `rest`.

______________________________________________________________________

## 🧪 Dry Run

**Input:** `target = [9,3,5]`

`sum = 17`, heap = `[9,5,3]`

______________________________________________________________________

**Iterasi 1:**

- `curr = 9`, `rest = 17-9 = 8`
- `rest == 1`? ❌
- `x = 9 % 8 = 1`
- `x == 0`? ❌, `x == curr`? ❌
- `sum = 8 + 1 = 9`, push `1`
- heap = `[5,3,1]`, sum=9

______________________________________________________________________

**Iterasi 2:**

- `curr = 5`, `rest = 9-5 = 4`
- `rest == 1`? ❌
- `x = 5 % 4 = 1`
- `sum = 4 + 1 = 5`, push `1`
- heap = `[3,1,1]`, sum=5

______________________________________________________________________

**Iterasi 3:**

- `curr = 3`, `rest = 5-3 = 2`
- `rest == 1`? ❌
- `x = 3 % 2 = 1`
- `sum = 2 + 1 = 3`, push `1`
- heap = `[1,1,1]`, sum=3

______________________________________________________________________

`pq.peek() == 1` → return `true` ✅

**Verifikasi maju:**

```
[1,1,1] → ganti 1 dengan sum=3 → [3,1,1]
[3,1,1] → ganti 1 dengan sum=5 → [5,3,1]
[5,3,1] → ganti 1 dengan sum=9 → [9,5,3] = [9,3,5] ✅
```

______________________________________________________________________

**Input:** `target = [1,1,1,2]`

`sum = 5`, heap = `[2,1,1,1]`

**Iterasi 1:**

- `curr = 2`, `rest = 5-2 = 3`
- `rest == 1`? ❌
- `x = 2 % 3 = 2` → `x == curr`? ✅ → return `false`

**Output: `false` ✅**

______________________________________________________________________

**Mengapa `x == curr` berarti false?**

`x = curr % rest`. Jika `curr < rest`, maka `curr % rest = curr` → `x == curr`. Ini berarti nilai elemen tidak berubah saat kita mundur — kita stuck di loop tak berakhir → tidak mungkin mencapai `[1,1,...,1]`.

______________________________________________________________________

**Input:** `target = [8,5]`

`sum = 13`, heap = `[8,5]`

**Iterasi 1:**

- `curr = 8`, `rest = 13-8 = 5`
- `rest == 1`? ❌
- `x = 8 % 5 = 3`
- `sum = 5+3 = 8`, push `3`
- heap = `[5,3]`, sum=8

**Iterasi 2:**

- `curr = 5`, `rest = 8-5 = 3`
- `rest == 1`? ❌
- `x = 5 % 3 = 2`
- `sum = 3+2 = 5`, push `2`
- heap = `[3,2]`, sum=5

**Iterasi 3:**

- `curr = 3`, `rest = 5-3 = 2`
- `rest == 1`? ❌
- `x = 3 % 2 = 1`
- `sum = 2+1 = 3`, push `1`
- heap = `[2,1]`, sum=3

**Iterasi 4:**

- `curr = 2`, `rest = 3-2 = 1`
- `rest == 1`? ✅ → return `true`

**Output: `true` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `target = [1]` → satu elemen, harus `= 1` → langsung cek
- [ ] `target = [1,1000000000]` → modulo menghindari loop jutaan kali
- [ ] `x == 0` → berarti `curr` habis dibagi `rest` → tidak valid karena elemen tidak bisa `0`

______________________________________________________________________

## 🔧 Kenapa `rest == 1` Langsung Return `true`?

Jika `rest = sum - curr = 1`, artinya semua elemen selain `curr` berjumlah `1`. Ini hanya mungkin jika semua elemen lain `= 1`. Kita bisa langsung mundur `curr` menjadi `1` (karena `1 % 1` tidak valid, tapi `curr - 1×rest = curr - 1 = ... = 1` setelah banyak langkah). Jadi kondisi ini selalu bisa dikembalikan ke `[1,1,...,1]`.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan teknik **reverse simulation** — mundur dari target ke kondisi awal lebih mudah dari maju. Optimasi krusialnya adalah `curr % rest` yang memungkinkan kita mundur banyak langkah sekaligus, mengubah O(max_value) menjadi O(log max_value) per elemen. Max Heap memastikan kita selalu mundurkan elemen terbesar — karena elemen terbesar pasti hasil operasi terakhir. 🎯

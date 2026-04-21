# 888. Fair Candy Swap

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Binary Search, Sorting
- **Link**: [Problem](https://leetcode.com/problems/fair-candy-swap/)
- **Solution**: [Code](../../leetcode/FairCandySwap.java)

______________________________________________________________________

## 📄 Problem Summary

Alice dan Bob masing-masing memiliki beberapa kotak permen. Mereka ingin bertukar tepat **satu kotak** sehingga setelah pertukaran, total permen keduanya **sama**. Kembalikan `[a, b]` di mana Alice memberikan kotak `a` dan Bob memberikan kotak `b`.

Contoh:

- `aliceSizes = [1,1]`, `bobSizes = [2,2]` → `[1,2]`
- `aliceSizes = [1,2]`, `bobSizes = [2,3]` → `[1,2]`
- `aliceSizes = [2]`, `bobSizes = [1,3]` → `[2,3]`

______________________________________________________________________

## 💡 Intuition

Misalkan total permen Alice = `sumA` dan Bob = `sumB`. Setelah Alice memberikan kotak `a` dan menerima kotak `b`:

```
sumA - a + b = sumB - b + a
```

Sederhanakan:

```
sumA - a + b = sumB - b + a
2b - 2a = sumB - sumA
b - a = (sumB - sumA) / 2
b = a + (sumB - sumA) / 2
```

Jadi untuk setiap kotak `a` milik Alice, kita tinggal cek apakah `b = a + (sumB - sumA) / 2` ada di kotak milik Bob. HashSet memungkinkan pengecekan ini dalam O(1).

______________________________________________________________________

## 🔍 Approach

### Math + HashSet

1. Hitung `sumA` dan `sumB`, sekaligus masukkan semua elemen `bobSizes` ke HashSet `setB`.
1. Hitung `diff = (sumB - sumA) / 2` — selisih yang harus "dipindahkan".
1. Iterasi setiap `a` di `aliceSizes`:
   - Jika `setB.contains(a + diff)` → return `[a, a + diff]`.
1. Return array kosong (soal menjamin selalu ada jawaban).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------- |
| **Time** | O(n + m) — build HashSet O(m) + iterasi Alice O(n) |
| **Space** | O(m) — HashSet menyimpan semua elemen `bobSizes` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `aliceSizes = [1,2]`, `bobSizes = [2,3]`

**Step 1 — Hitung sum dan build HashSet:**

- `sumA = 1 + 2 = 3`
- `sumB = 2 + 3 = 5`
- `setB = {2, 3}`

**Step 2 — Hitung diff:**

```
diff = (sumB - sumA) / 2 = (5 - 3) / 2 = 1
```

**Step 3 — Iterasi `aliceSizes`:**
| a | a + diff | setB.contains(a+diff)? | Aksi |
|---|----------|------------------------|----------------|
| 1 | 1+1 = 2 | ✅ | return `[1,2]` |

**Verifikasi:** Alice: `3 - 1 + 2 = 4`, Bob: `5 - 2 + 1 = 4` ✅

**Output: `[1,2]` ✅**

______________________________________________________________________

**Input:** `aliceSizes = [2]`, `bobSizes = [1,3]`

**Step 1:**

- `sumA = 2`, `sumB = 4`
- `setB = {1, 3}`

**Step 2:**

```
diff = (4 - 2) / 2 = 1
```

**Step 3:**
| a | a + diff | setB.contains? | Aksi |
|---|----------|----------------|----------------|
| 2 | 2+1 = 3 | ✅ | return `[2,3]` |

**Verifikasi:** Alice: `2 - 2 + 3 = 3`, Bob: `4 - 3 + 2 = 3` ✅

**Output: `[2,3]` ✅**

______________________________________________________________________

**Input:** `aliceSizes = [1,1]`, `bobSizes = [2,2]`

**Step 1:**

- `sumA = 2`, `sumB = 4`
- `setB = {2}`

**Step 2:**

```
diff = (4 - 2) / 2 = 1
```

**Step 3:**
| a | a + diff | setB.contains? | Aksi |
|---|----------|----------------|----------------|
| 1 | 1+1 = 2 | ✅ | return `[1,2]` |

**Output: `[1,2]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `sumA == sumB` → `diff = 0` → cari `a` yang juga ada di `setB` (tukar kotak sama besar)
- [ ] Alice punya satu kotak → satu-satunya kandidat, langsung dicek
- [ ] Banyak duplikat di `bobSizes` → HashSet otomatis deduplikasi, tidak mempengaruhi kebenaran

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh klasik **mengubah masalah pencarian menjadi persamaan matematika**. Daripada mencoba semua pasangan `(a, b)` secara brute force O(n×m), derivasi sederhana `b = a + diff` mereduksi pencarian menjadi O(n) dengan bantuan HashSet. Pola "hitung target lalu cek di HashSet" ini identik dengan pendekatan optimal di soal _Two Sum_. 🎯

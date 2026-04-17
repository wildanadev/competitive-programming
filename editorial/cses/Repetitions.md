# 6. Repetitions

- **Platform**: CSES
- **Difficulty**: Easy
- **Topics**: String, Two Pointers
- **Link**: [Problem](https://cses.fi/problemset/task/1069)
- **Solution**: [Code](../../cses/Repetitons.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan sebuah string `s`.

Tugasmu adalah mencari panjang maksimum dari substring yang terdiri dari **karakter yang sama secara berurutan**.

______________________________________________________________________

## 💡 Intuition

Kita ingin mencari **segmen terpanjang** yang isinya karakter yang sama.

Contoh:

```id="a1b2c3"
ATTCGGGA
```

Segmen:

- `TT` → panjang 2
- `GGG` → panjang 3

👉 Jawaban: **3**

Daripada cek semua substring (O(n²)), kita cukup **scan sekali** sambil menghitung panjang streak.

______________________________________________________________________

## 🔍 Approach

Gunakan teknik **two pointers**:

- `j` → awal segmen
- `i` → posisi saat ini

Langkah:

1. Loop `i` dari 0 sampai n-1

1. Jika `s[i] == s[j]`:

   - Update panjang: `i - j + 1`

1. Jika berbeda:

   - Geser `j = i` (mulai segmen baru)

1. Simpan maksimum di `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---- |
| **Time** | O(n) |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```id="dry1"
ATTCGGGA
```

**Proses:**

| i | j | s[i] | s[j] | Panjang | ans |
| --- | --- | ---- | ---- | ------- | --- |
| 0 | 0 | A | A | 1 | 1 |
| 1 | 1 | T | A | reset | 1 |
| 2 | 1 | T | T | 2 | 2 |
| 3 | 3 | C | T | reset | 2 |
| 4 | 4 | G | C | reset | 2 |
| 5 | 4 | G | G | 2 | 2 |
| 6 | 4 | G | G | 3 | 3 |
| 7 | 7 | A | G | reset | 3 |

👉 **Output: 3**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String panjang 1 → output 1
- [ ] Semua karakter sama → output = panjang string
- [ ] Semua karakter berbeda → output 1
- [ ] String kosong (tidak ada di soal)

______________________________________________________________________

## 📌 Key Takeaway

- Ini adalah problem klasik **longest consecutive sequence**
- Tidak perlu substring → cukup tracking panjang saat ini

👉 Pola penting:

- Gunakan **two pointers / sliding window sederhana**
- Reset saat kondisi rusak

Kalau ketemu soal:

- "subarray/substring dengan kondisi berurutan"
- "maksimum panjang segmen"

👉 langsung kepikiran **linear scan + tracking streak** 🚀

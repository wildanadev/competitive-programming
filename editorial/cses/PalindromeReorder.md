# 5. Palindrome Reorder

- **Platform**: CSES
- **Difficulty**: Easy
- **Topics**: String, Greedy, Counting
- **Link**: [Problem](https://cses.fi/problemset/task/1755)
- **Solution**: [Code](../../cses/PalindromeReorder.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan sebuah string berisi huruf kapital (`A-Z`).

Tugasmu adalah menyusun ulang karakter sehingga membentuk **palindrome**.

Jika tidak memungkinkan, output:

```id="u3k91f"
NO SOLUTION
```

______________________________________________________________________

## 💡 Intuition

Agar sebuah string bisa menjadi **palindrome**:

- Karakter dengan jumlah **genap** → bisa dibagi kiri & kanan
- Karakter dengan jumlah **ganjil** → hanya boleh **1 saja** (jadi tengah)

👉 Jadi syarat utama:

- Maksimal hanya **1 karakter dengan frekuensi ganjil**

Kalau lebih dari itu → tidak mungkin membentuk palindrome ❌

______________________________________________________________________

## 🔍 Approach

1. Hitung frekuensi setiap karakter (pakai `HashMap`)

1. Hitung berapa karakter yang memiliki frekuensi ganjil

1. Jika lebih dari 1 → print `"NO SOLUTION"`

1. Jika valid:

   - Karakter genap → ambil setengah, simpan di kiri (`f`)
   - Karakter ganjil → simpan semua di tengah (`m`)

1. Hasil akhir:

```id="qk1z3x"
answer = f + m + reverse(f)
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(n log n) — karena sorting |
| **Space** | O(n) |

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```id="n1k92s"
AABB
```

**Frekuensi:**

```
A → 2
B → 2
```

Semua genap → valid ✅

**Bangun palindrome:**

```
f = "AB"
m = ""
reverse(f) = "BA"
```

👉 **Output:**

```id="x9c2kd"
ABBA
```

______________________________________________________________________

**Input:**

```id="k3d91p"
AAABB
```

**Frekuensi:**

```
A → 3 (ganjil)
B → 2
```

1 ganjil → masih valid ✅

```
f = "A"
m = "AAA"
reverse(f) = "A"
```

👉 **Output:**

```
AABAA
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua karakter unik → langsung `NO SOLUTION`
- [ ] Panjang string ganjil → boleh 1 karakter ganjil
- [ ] Panjang string genap → tidak boleh ada ganjil
- [ ] String panjang → gunakan `StringBuilder` (lebih efisien)

______________________________________________________________________

## 📌 Key Takeaway

- Palindrome sangat erat dengan **frekuensi karakter**

- Kunci utama:
  👉 **maksimal 1 karakter dengan jumlah ganjil**

- Pola umum:

  - Hitung frekuensi
  - Bangun hasil dari **setengah kiri + tengah + setengah kanan**

👉 Ini adalah kombinasi **counting + greedy construction** 🚀

# 3996. Even Number of Knight Moves

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Chess
- **Link**: [Problem](https://leetcode.com/problems/even-number-of-knight-moves/)
- **Solution**: [Code](../../leetcode/EvenNumberOfKnightMoves.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan posisi `start` dan `target` di papan catur 8×8. Return `true` jika knight bisa mencapai `target` dari `start` dalam **jumlah langkah genap**.

Contoh:

- `start=[1,1], target=[2,2]` → `true` (4 langkah: (1,1)→(3,2)→(2,4)→(4,3)→(2,2))
- `start=[4,5], target=[6,6]` → `false`

______________________________________________________________________

## 💡 Intuition: Properti Warna Papan Catur

Papan catur memiliki properti penting: setiap langkah knight **selalu berganti warna petak** (dari hitam ke putih atau sebaliknya).

Mengapa? Setiap langkah knight bergerak `(±1, ±2)` atau `(±2, ±1)`. Jumlah koordinat berubah sebesar `1+2=3` atau `2+1=3` — selalu **ganjil**. Karena parity (ganjil/genap) dari `x+y` berganti ketika ditambah bilangan ganjil, knight **selalu pindah ke petak dengan parity berbeda**.

```
Petak hitam: (x+y) % 2 == 0  →  misalnya (0,0), (1,1), (2,2)
Petak putih: (x+y) % 2 == 1  →  misalnya (0,1), (1,0), (1,2)

Setiap langkah: hitam → putih atau putih → hitam
```

**Kesimpulan:**

- Setelah **genap** langkah → knight ada di petak dengan **parity sama** seperti awal
- Setelah **ganjil** langkah → knight ada di petak dengan **parity berbeda** dari awal

Jadi knight bisa sampai ke `target` dalam langkah **genap** jika dan hanya jika `start` dan `target` berada di petak dengan **parity yang sama**.

```
parity(start)  = (start[0]  + start[1])  % 2
parity(target) = (target[0] + target[1]) % 2

Jawaban = (parity(start) == parity(target))
```

______________________________________________________________________

## 🔍 Approach

### Parity Check — O(1)

1. Hitung `(start[0] + start[1]) & 1` → parity start
1. Hitung `(target[0] + target[1]) & 1` → parity target
1. Return keduanya sama.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------- |
| **Time** | O(1) — hanya operasi bitwise |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `start = [1,1]`, `target = [2,2]`

```
parity(start)  = (1+1) & 1 = 2 & 1 = 0  (petak hitam)
parity(target) = (2+2) & 1 = 4 & 1 = 0  (petak hitam)

0 == 0 → true ✅
```

Verifikasi: rute (1,1)→(3,2)→(2,4)→(4,3)→(2,2) = 4 langkah (genap) ✅

______________________________________________________________________

**Input:** `start = [4,5]`, `target = [6,6]`

```
parity(start)  = (4+5) & 1 = 9 & 1 = 1  (petak putih)
parity(target) = (6+6) & 1 = 12 & 1 = 0 (petak hitam)

1 != 0 → false ✅
```

______________________________________________________________________

**Input:** `start = [0,0]`, `target = [0,0]`

```
parity(start)  = 0
parity(target) = 0
0 == 0 → true ✅ (0 langkah, genap)
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `start == target` → 0 langkah (genap) → parity sama → `true` ✅
- [ ] Start dan target di warna berbeda → tidak mungkin jumlah genap → `false`

______________________________________________________________________

## 🔧 Mengapa Setiap Langkah Knight Mengubah Parity?

Knight bergerak `(dx, dy)` di mana `|dx| + |dy| = 3` (selalu).

```
Semua kemungkinan: (±1,±2) atau (±2,±1)
|dx| + |dy| = 1+2 = 3 atau 2+1 = 3  → selalu ganjil
```

Parity `(x+y)` setelah langkah = parity `((x+dx) + (y+dy))` = parity `(x+y+dx+dy)`.

Karena `dx+dy` selalu **ganjil** (karena `|dx|+|dy|=3` dan salah satunya ganjil, satunya genap), menambahkan `dx+dy` **selalu mengubah parity** dari `x+y`.

```
Ganjil + Ganjil = Genap  ↓  parity berubah
Genap + Ganjil = Ganjil  ↑  parity berubah
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan **invariant papan catur** — setiap langkah knight selalu mengubah warna petak (karena `|dx|+|dy|=3` selalu ganjil). Ini langsung berarti setelah genap langkah, knight pasti berada di petak **sama warna** dengan posisi awal. Kondisi parity `(x+y) % 2` adalah cara membedakan warna petak secara matematis tanpa perlu BFS atau simulasi. 🎯

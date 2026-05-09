# 84. Largest Rectangle in Histogram

- **Platform**: LeetCode
- **Difficulty**: Hard
- **Topics**: Array, Stack, Monotonic Stack
- **Link**: [Problem](https://leetcode.com/problems/largest-rectangle-in-histogram/)
- **Solution**: [Code](../../leetcode/LargestRectangleInHistogram.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `heights` yang merepresentasikan tinggi bar histogram (lebar setiap bar = 1), kembalikan **luas persegi panjang terbesar** yang bisa dibentuk di dalam histogram.

Contoh:

- `heights = [2,1,5,6,2,3]` → `10`
- `heights = [2,4]` → `4`

______________________________________________________________________

## 💡 Intuition

Untuk setiap bar `i`, luas terbesar yang bisa dibentuk dengan tinggi `heights[i]` adalah:

```
luas = heights[i] × width
width = right - left - 1

di mana:
right = indeks bar pertama di KANAN yang lebih pendek dari heights[i]
left  = indeks bar pertama di KIRI  yang lebih pendek dari heights[i]
```

Kita butuh **Next Smaller Element** dari dua arah sekaligus. **Monotonic Increasing Stack** bisa menemukan keduanya dalam satu pass O(n).

**Kapan bar di-pop?** Saat bar baru yang **lebih pendek** datang — bar baru itu adalah `right boundary`, dan elemen di bawahnya di stack adalah `left boundary`.

______________________________________________________________________

## 🔍 Approach

### Monotonic Increasing Stack + Flush

**Pass utama (loop `i`):**

- Push indeks `i` ke stack selama `heights[i] >= heights[stack.peek()]`.
- Saat `heights[i] < heights[stack.peek()]` → pop dan hitung luas:
  - `height = heights[ad.pop()]`
  - `width = ad.isEmpty() ? i : i - ad.peek() - 1`
  - Update `ans`
- Push `i` ke stack.

**Flush sisa stack (setelah loop):**

- Bar yang tersisa di stack tidak punya `right boundary` → right boundary = `n` (ujung array).
- Pop satu per satu dan hitung luas dengan `width = ad.isEmpty() ? n : n - ad.peek() - 1`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------- |
| **Time** | O(n) — setiap indeks di-push dan di-pop tepat satu kali |
| **Space** | O(n) — stack menyimpan maksimal semua indeks |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `heights = [2,1,5,6,2,3]`

`ans=0, stack=[]`

______________________________________________________________________

**i=0, h=2:** stack kosong → push 0 → `stack=[0]`

**i=1, h=1:**

- `h[0]=2 > 1` → pop 0:
  - `height = 2`
  - stack kosong → `width = i = 1`
  - `ans = max(0, 2×1) = 2`
- push 1 → `stack=[1]`

**i=2, h=5:** `h[1]=1 > 5`? ❌ → push 2 → `stack=[1,2]`

**i=3, h=6:** `h[2]=5 > 6`? ❌ → push 3 → `stack=[1,2,3]`

**i=4, h=2:**

- `h[3]=6 > 2` → pop 3:
  - `height = 6`
  - peek=2 → `width = 4-2-1 = 1`
  - `ans = max(2, 6×1) = 6`
- `h[2]=5 > 2` → pop 2:
  - `height = 5`
  - peek=1 → `width = 4-1-1 = 2`
  - `ans = max(6, 5×2) = 10`
- `h[1]=1 > 2`? ❌ → push 4 → `stack=[1,4]`

**i=5, h=3:** `h[4]=2 > 3`? ❌ → push 5 → `stack=[1,4,5]`

______________________________________________________________________

**Flush sisa stack (`n=6`):**

- pop 5: `height=3`, peek=4 → `width=6-4-1=1` → `ans=max(10,3)=10`
- pop 4: `height=2`, peek=1 → `width=6-1-1=4` → `ans=max(10,8)=10`
- pop 1: `height=1`, stack kosong → `width=n=6` → `ans=max(10,6)=10`

**Output: `10` ✅**

______________________________________________________________________

**Input:** `heights = [3,3]`

**i=0:** push 0 → `stack=[0]`

**i=1, h=3:** `h[0]=3 > 3`? ❌ → push 1 → `stack=[0,1]`

**Flush (n=2):**

- pop 1: `height=3`, peek=0 → `width=2-0-1=1` → `ans=3`
- pop 0: `height=3`, stack kosong → `width=2` → `ans=6`

**Output: `6` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu bar → luas = `heights[0]`
- [ ] Semua bar sama tinggi → luas = `heights[0] × n`
- [ ] Terurut ascending → tidak ada pop di loop utama, semua di-flush
- [ ] Terurut descending → setiap bar langsung di-pop saat bar berikutnya datang

______________________________________________________________________

## 🔧 Rumus Width — Kenapa `i - peek - 1` dan `n - peek - 1`?

```
Saat di-pop karena bar baru di index i:
  left boundary  = stack.peek() (bar di bawahnya, lebih pendek)
  right boundary = i (bar yang menyebabkan pop)
  width = right - left - 1 = i - peek - 1

Saat di-flush (tidak ada right boundary):
  left boundary  = stack.peek()
  right boundary = n (ujung array, di luar indeks valid)
  width = n - peek - 1

Jika stack kosong saat hitung width:
  left boundary = -1 (tidak ada bar lebih pendek di kiri)
  right boundary = i atau n
  width = i - (-1) - 1 = i
       atau n - (-1) - 1 = n
```

______________________________________________________________________

## 🔧 Kenapa Pop Dulu, Baru Peek?

```java
int height = heights[ad.pop()];          // pop bar yang dihitung
int width = ... : i - ad.peek() - 1;    // peek sisa = left boundary
```

Setelah pop, `peek` menunjuk ke bar **di bawah** bar yang baru di-pop — itulah left boundary yang benar. Kalau peek sebelum pop, kita mendapat indeksnya sendiri → width = 0 → luas = 0.

______________________________________________________________________

## 📊 Perbandingan Brute Force vs Monotonic Stack

| Approach | Time | Space | Catatan |
| --------------- | ----- | ----- | ------------------------- |
| Brute Force | O(n²) | O(1) | TLE untuk n besar |
| Monotonic Stack | O(n) | O(n) | Setiap elemen push/pop 1x |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi **Monotonic Increasing Stack** untuk mencari next smaller element dari **dua arah sekaligus** dalam satu pass. Setiap kali pop terjadi, kita sudah tahu `right boundary` (bar yang menyebabkan pop) dan `left boundary` (elemen baru di top stack) — cukup untuk menghitung luas secara langsung. Flush stack di akhir menangani bar-bar yang tidak punya right boundary. Pola yang sama muncul di _Trapping Rain Water_ dan _Maximal Rectangle_. 🎯

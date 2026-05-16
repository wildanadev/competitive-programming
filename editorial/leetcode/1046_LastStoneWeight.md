# 1046. Last Stone Weight

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Heap (Priority Queue)
- **Link**: [Problem](https://leetcode.com/problems/last-stone-weight/)
- **Solution**: [Code](../../leetcode/LastStoneWeight.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `stones` berisi berat batu. Setiap langkah, ambil **dua batu terberat** dan hancurkan:

- Jika beratnya sama → keduanya hancur.
- Jika berbeda → batu yang lebih ringan hancur, batu yang lebih berat berkurang sebesar berat batu yang lebih ringan.

Ulangi sampai tersisa maksimal satu batu. Return beratnya, atau `0` jika tidak ada.

Contoh:

- `stones = [2,7,4,1,8,1]` → `1`
- `stones = [1]` → `1`

______________________________________________________________________

## 💡 Intuition

Setiap langkah kita butuh **dua elemen terbesar**. Struktur data yang paling efisien untuk ini adalah **Max Heap** — mengambil elemen terbesar dalam O(log n).

Karena `poll()` selalu mengambil yang terbesar dulu:

- `one = pq.poll()` → batu terbesar
- `two = pq.poll()` → batu terbesar kedua
- `one >= two` selalu berlaku → `one - two >= 0` selalu

Jadi hasil hancuran langsung `pq.poll() - pq.poll()` — tidak perlu `Math.abs()` atau cek `if one == two`. Jika sama, hasilnya `0` yang otomatis masuk heap dan tidak berpengaruh.

______________________________________________________________________

## 🔍 Approach

### Max Heap Simulation — One-liner Loop

1. Masukkan semua `stones` ke max heap.
1. Selama `pq.size() > 1`:
   - `pq.offer(pq.poll() - pq.poll())` — hancurkan dua terbesar, masukkan sisa.
1. Return `pq.poll()`.

> Saat kedua batu sama, hasilnya `0` — `0` masuk heap tapi tidak akan pernah menang melawan batu lain karena heap selalu mengambil yang terbesar.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(n log n) — setiap poll/offer O(log n), maksimal n iterasi |
| **Space** | O(n) — heap menyimpan semua batu |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `stones = [2,7,4,1,8,1]`

**Build heap:** `[8,7,4,2,1,1]`

| Iterasi | poll() | poll() | one-two | heap setelah |
| ------- | ------ | ------ | ------- | ------------- |
| 1 | 8 | 7 | 1 | `[4,2,1,1,1]` |
| 2 | 4 | 2 | 2 | `[2,1,1,1]` |
| 3 | 2 | 1 | 1 | `[1,1,1]` |
| 4 | 1 | 1 | 0 | `[1,0]` |
| 5 | 1 | 0 | 1 | `[1]` |

`pq.size() == 1` → return `pq.poll() = 1`

**Output: `1` ✅**

______________________________________________________________________

**Input:** `stones = [2,2]`

| Iterasi | poll() | poll() | one-two | heap |
| ------- | ------ | ------ | ------- | ----- |
| 1 | 2 | 2 | 0 | `[0]` |

return `pq.poll() = 0`

**Output: `0` ✅** — kedua batu hancur sempurna

______________________________________________________________________

**Input:** `stones = [3,1]`

| Iterasi | poll() | poll() | one-two | heap |
| ------- | ------ | ------ | ------- | ----- |
| 1 | 3 | 1 | 2 | `[2]` |

return `2`

**Output: `2` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu batu → loop tidak jalan → return batu itu langsung
- [ ] Semua batu sama → semua hancur → tersisa `0` → return `0`
- [ ] Dua batu → satu iterasi saja

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu `Math.abs()` dan `if one == two`?

```java
pq.offer(pq.poll() - pq.poll());
```

Karena max heap selalu mengeluarkan elemen **terbesar** dulu:

```
poll() pertama  = one (terbesar)
poll() kedua    = two (terbesar kedua)
one >= two selalu → one - two >= 0 selalu ✅
```

Kasus `one == two`:

```
one - two = 0 → push 0 ke heap
0 tidak pernah memenangkan duel (selalu jadi "two")
→ tidak mempengaruhi hasil akhir
```

Sehingga kondisi `if (one == two)` dan `Math.abs()` tidak diperlukan — keduanya sudah tertangani secara implisit.

______________________________________________________________________

## 🔧 Kenapa `(a, b) -> b - a` untuk Max Heap?

Java `PriorityQueue` secara default adalah **min heap**. Untuk membalik menjadi max heap:

```java
// Min heap (default) — elemen terkecil di atas
new PriorityQueue<>();

// Max heap — elemen terbesar di atas
new PriorityQueue<>((a, b) -> b - a);
```

`b - a` menghasilkan nilai positif saat `b > a` → comparator menganggap `b` "lebih kecil" → `b` naik ke atas → efek: max heap. ✅

______________________________________________________________________

## 📌 Key Takeaway

**Max Heap** adalah pilihan natural untuk soal yang berulang kali membutuhkan elemen terbesar. Kode ini sangat ringkas — satu baris `pq.offer(pq.poll() - pq.poll())` menggantikan beberapa kondisi `if` karena memanfaatkan dua properti: max heap menjamin `one >= two`, dan hasil `0` tidak mempengaruhi logika. Pola Max Heap simulation ini juga muncul di soal _Kth Largest Element_, _K Closest Points to Origin_, dan _Merge K Sorted Lists_. 🎯

# 160. Intersection of Two Linked Lists

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, Linked List, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/intersection-of-two-linked-lists/)
- **Solution**: [Code](../../leetcode/IntersectionOfTwoLinkedLists.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `headA` dan `headB` dari dua linked list, kembalikan **node tempat kedua linked list berpotongan**. Jika tidak ada perpotongan, return `null`.

Perpotongan ditentukan berdasarkan **referensi node** (bukan nilai) — kedua list harus menunjuk ke node yang **sama persis** di memori.

Contoh:

```
List A: a1 → a2 ↘
                  c1 → c2 → c3
List B: b1 → b2 ↗
```

- Intersection di `c1`

______________________________________________________________________

## 💡 Intuition

Masalah utama: dua list mungkin memiliki panjang berbeda, sehingga pointer yang mulai dari `headA` dan `headB` tidak akan mencapai titik intersection secara bersamaan.

**Kunci solusi**: jika pointer A selesai menelusuri list A lalu dilanjutkan ke `headB`, dan pointer B selesai menelusuri list B lalu dilanjutkan ke `headA` — keduanya akan menempuh **total jarak yang sama** (`lenA + lenB`). Akibatnya, mereka pasti akan berada di posisi yang **simetris** saat mencapai intersection.

```
Pointer A: a1 → a2 → c1 → c2 → c3 → b1 → b2 → [c1] ✅
Pointer B: b1 → b2 → c1 → c2 → c3 → a1 → a2 → [c1] ✅
```

Jika tidak ada intersection, keduanya akan bertemu di `null` secara bersamaan setelah menempuh `lenA + lenB` langkah.

______________________________________________________________________

## 🔍 Approach

### Two Pointers dengan Path Switching

1. Inisialisasi `lista = headA` dan `listb = headB`.
1. Selama `lista != listb`:
   - Jika `lista != null` → `lista = lista.next`, jika tidak → `lista = headB`.
   - Jika `listb != null` → `listb = listb.next`, jika tidak → `listb = headA`.
1. Return `lista` (bisa berupa node intersection atau `null`).

> Saat pointer mencapai `null` (ujung list), ia "berpindah" ke kepala list yang lain — bukan `lista.next` dari `null`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------- |
| **Time** | O(n + m) — n dan m = panjang masing-masing list |
| **Space** | O(1) — hanya dua pointer tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```
List A (len=4): a1 → a2 → c1 → c2
List B (len=3):       b1 → c1 → c2
Intersection di: c1
```

`lista = a1`, `listb = b1`

| Step | lista | listb | lista == listb? |
| ---- | --------------------- | --------------------- | --------------- |
| 1 | a2 | c1 | ❌ |
| 2 | c1 | c2 | ❌ |
| 3 | c2 | **null → headA = a1** | ❌ |
| 4 | **null → headB = b1** | a2 | ❌ |
| 5 | b1 | c1 | ❌ |
| 6 | c1 | c2 | ❌ |
| 7 | **c1 == c1** ✅ | | return `c1` |

**Output: node `c1` ✅**

______________________________________________________________________

**Input:** Tidak ada intersection

```
List A (len=3): a1 → a2 → a3 → null
List B (len=2): b1 → b2 → null
```

| Step | lista | listb |
| ---- | ----------------- | ----------------- |
| 1 | a2 | b2 |
| 2 | a3 | null → headA = a1 |
| 3 | null → headB = b1 | a2 |
| 4 | b1 | a3 |
| 5 | b2 | null → headA = a1 |
| 6 | null → headB = b1 | ... |

Setelah `lenA + lenB = 5` langkah: `lista = null`, `listb = null` → `null == null` ✅ → return `null`

**Output: `null` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Salah satu list kosong (`head = null`) → pointer langsung beralih ke list lain, bertemu di `null`
- [ ] Kedua list sama persis → intersection di `headA = headB`, langsung return di iterasi pertama
- [ ] Intersection di node pertama list yang lebih pendek → path switching memastikan sinkronisasi
- [ ] Panjang list sama → tidak ada path switching yang terjadi, pointer bertemu langsung

______________________________________________________________________

## 📌 Key Takeaway

Trik **path switching** adalah salah satu teknik Two Pointers paling elegan dalam linked list — dengan membuat kedua pointer menempuh total jarak yang sama (`lenA + lenB`), kita "menyamakan" posisi mereka secara implisit tanpa perlu menghitung panjang list terlebih dahulu. Kasus tidak ada intersection juga tertangani secara natural karena `null == null`. Pola ini menunjukkan bahwa simetri jarak bisa menggantikan kebutuhan sinkronisasi eksplisit. 🎯

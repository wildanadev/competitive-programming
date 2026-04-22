# 141. Linked List Cycle

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, Linked List, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/linked-list-cycle/)
- **Solution**: [Code](../../leetcode/LinkedListCycle.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `head` dari sebuah linked list, tentukan apakah linked list tersebut memiliki **cycle** (ada node yang `next`-nya menunjuk ke node sebelumnya sehingga membentuk lingkaran).

Contoh:

- `[3,2,0,-4]` dengan tail terhubung ke indeks `1` → `true`
- `[1,2]` dengan tail terhubung ke indeks `0` → `true`
- `[1]` tanpa cycle → `false`

______________________________________________________________________

## 💡 Intuition

Gunakan **Floyd's Cycle Detection Algorithm** (algoritma kelinci dan kura-kura). Dua pointer bergerak dengan kecepatan berbeda:

- `slow` bergerak **1 langkah** per iterasi.
- `fast` bergerak **2 langkah** per iterasi.

Jika ada cycle, `fast` yang berputar di dalam cycle pasti akan "mengejar" dan **bertemu** dengan `slow` pada suatu titik. Jika tidak ada cycle, `fast` akan mencapai `null` terlebih dahulu dan loop berakhir.

Analoginya: dua pelari di lintasan melingkar — yang lebih cepat pasti akan menyusul yang lebih lambat.

______________________________________________________________________

## 🔍 Approach

### Floyd's Cycle Detection (Fast & Slow Pointers)

1. Inisialisasi `slow = head` dan `fast = head`.
1. Selama `fast != null` dan `fast.next != null`:
   - `fast` maju dua langkah: `fast = fast.next.next`
   - `slow` maju satu langkah: `slow = slow.next`
   - Jika `fast == slow` → cycle ditemukan → return `true`.
1. Jika loop berakhir → tidak ada cycle → return `false`.

> Kondisi `fast != null && fast.next != null` menjaga agar `fast.next.next` tidak menyebabkan `NullPointerException`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n) — fast pointer melewati setiap node maksimal dua kali |
| **Space** | O(1) — hanya dua pointer tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `3 → 2 → 0 → -4 → (kembali ke node 2)`

```
head: 3 → 2 → 0 → -4
               ↑_______↓
```

`slow = 3`, `fast = 3`

| Iterasi | slow | fast | fast == slow? |
| ------- | ---- | ---------- | ------------------ |
| 1 | 2 | 0 | ❌ |
| 2 | 0 | 2 (cycle!) | ❌ |
| 3 | -4 | -4 | ✅ → return `true` |

**Output: `true` ✅**

______________________________________________________________________

**Input:** `1 → 2 → null` (tanpa cycle)

`slow = 1`, `fast = 1`

| Iterasi | slow | fast | fast == slow? |
| ------- | ---- | ---- | ------------- |
| 1 | 2 | null | ❌ |

`fast == null` → kondisi while gagal → return `false`

**Output: `false` ✅**

______________________________________________________________________

**Input:** `1 → null` (satu node, tanpa cycle)

`fast = 1`, `fast.next = null` → kondisi `fast.next != null` langsung gagal → loop tidak pernah berjalan → return `false`

**Output: `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `head = null` → `fast = null` → kondisi `fast != null` langsung gagal → return `false`
- [ ] Satu node tanpa cycle → `fast.next = null` → loop tidak jalan → return `false`
- [ ] Satu node dengan self-loop (`node.next = node`) → iterasi pertama: `fast = node`, `slow = node` → return `true`
- [ ] Cycle di awal (seluruh list adalah cycle) → fast dan slow bertemu dengan cepat

______________________________________________________________________

## 🔧 Kenapa Cek `fast == slow` Setelah Move, Bukan Sebelum?

Keduanya diinisialisasi di `head` yang sama, sehingga jika cek dilakukan **sebelum** bergerak, kondisi `fast == slow` akan langsung `true` meski tidak ada cycle. Dengan mengecek **setelah** keduanya bergerak, kita menghindari false positive ini.

______________________________________________________________________

## 📌 Key Takeaway

**Floyd's Cycle Detection** adalah algoritma klasik O(1) space untuk mendeteksi cycle — jauh lebih efisien dari HashSet yang O(n) space. Prinsip utamanya: dalam ruang terbatas (cycle), pointer cepat **pasti** mengejar pointer lambat karena selisih kecepatannya konstan (1 langkah per iterasi). Algoritma ini juga bisa diperluas untuk **menemukan titik awal cycle** seperti di soal _Linked List Cycle II_. 🎯

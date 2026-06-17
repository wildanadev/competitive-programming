# 147. Insertion Sort List

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Linked List, Sorting
- **Link**: [Problem](https://leetcode.com/problems/insertion-sort-list/)
- **Solution**: [Code](../../leetcode/InsertionSortList.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `head` dari linked list, urutkan menggunakan **insertion sort** dan kembalikan head yang sudah terurut.

Contoh:

- `4 → 2 → 1 → 3` → `1 → 2 → 3 → 4`
- `-1 → 5 → 3 → 4 → 0` → `-1 → 0 → 3 → 4 → 5`

______________________________________________________________________

## 💡 Intuition

**Insertion Sort**: ambil setiap elemen satu per satu dan sisipkan ke posisi yang tepat di bagian yang sudah terurut.

Untuk linked list, gunakan **dummy node** sebagai sentinel head agar penyisipan di posisi pertama bisa ditangani secara seragam (tidak perlu kasus khusus).

Untuk setiap node `curr`:

1. Cari posisi `prev` di sorted list di mana `prev.next.val > curr.val`.
1. Sisipkan `curr` di antara `prev` dan `prev.next`.

______________________________________________________________________

## 🔍 Approach

### Insertion Sort dengan Dummy Node

**Setup:** `dummy → (sorted list)`, `curr = head`

**Setiap iterasi:**

1. Simpan `next = curr.next` (agar tidak kehilangan sisa list).
1. Cari `prev` di sorted list: maju selama `prev.next != null && prev.next.val <= curr.val`.
1. Sisipkan: `curr.next = prev.next`, `prev.next = curr`.
1. `curr = next` (lanjut ke node berikutnya).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n²) — untuk setiap node, cari posisi O(n) di sorted list |
| **Space** | O(1) — hanya pointer tambahan (dummy, prev, next) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `4 → 2 → 1 → 3`

`dummy → ∅, curr = 4`

______________________________________________________________________

**Iterasi 1 — curr=4:**

```
prev = dummy, prev.next = null → loop tidak jalan
next = 2
sisipkan: curr.next = null, dummy.next = 4
curr = 2

sorted: dummy → 4
```

______________________________________________________________________

**Iterasi 2 — curr=2:**

```
prev = dummy, prev.next = 4
prev.next.val=4 <= curr.val=2? ❌ → stop
next = 1
sisipkan: curr.next = 4, dummy.next = 2
curr = 1

sorted: dummy → 2 → 4
```

______________________________________________________________________

**Iterasi 3 — curr=1:**

```
prev = dummy, prev.next = 2
prev.next.val=2 <= 1? ❌ → stop
next = 3
sisipkan: curr.next = 2, dummy.next = 1
curr = 3

sorted: dummy → 1 → 2 → 4
```

______________________________________________________________________

**Iterasi 4 — curr=3:**

```
prev = dummy
prev.next.val=1 <= 3? ✅ → prev = node(1)
prev.next.val=2 <= 3? ✅ → prev = node(2)
prev.next.val=4 <= 3? ❌ → stop
next = null
sisipkan: curr.next = 4, node(2).next = 3
curr = null

sorted: dummy → 1 → 2 → 3 → 4
```

**Output: `1 → 2 → 3 → 4` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `head = null` → loop tidak jalan → return `dummy.next = null`
- [ ] Satu node → tidak ada perbandingan → return node itu sendiri
- [ ] Sudah terurut → `prev` selalu berjalan sampai akhir → O(n²) tetap tapi tidak ada swap
- [ ] Terurut descending → setiap node selalu disisipkan di awal → paling banyak operasi

______________________________________________________________________

## 🔧 Kenapa Dummy Node?

Tanpa dummy node, penyisipan di posisi pertama (sebelum head) butuh kasus khusus:

```java
// Tanpa dummy — perlu kasus khusus
if (curr.val < head.val) {
    curr.next = head;
    head = curr;  // update head
} else {
    // cari posisi lain
}

// Dengan dummy — semua kasus sama
ListNode prev = dummy;
while (prev.next != null && prev.next.val <= curr.val)
    prev = prev.next;
curr.next = prev.next;
prev.next = curr;
```

Dummy node membuat semua penyisipan seragam — tidak perlu `if` khusus.

______________________________________________________________________

## 🔧 Langkah Penyisipan — Urutan Penting!

```java
ListNode next = curr.next;  // (1) simpan next dulu!

curr.next = prev.next;      // (2) curr menunjuk ke node setelah prev
prev.next = curr;           // (3) prev menunjuk ke curr

curr = next;                // (4) maju ke node berikutnya
```

Urutan `(1)` sebelum `(2)` dan `(3)` **wajib** — jika `curr.next` diubah di `(2)` sebelum disimpan di `(1)`, kita kehilangan referensi ke sisa list yang belum diproses.

______________________________________________________________________

**Visualisasi penyisipan curr=2 ke `dummy → 4`:**

```
Sebelum:
dummy → 4 → null
curr=2, next=1

(2) curr.next = prev.next = 4  →  2 → 4
(3) prev.next = curr           →  dummy → 2 → 4

Setelah:
dummy → 2 → 4 ✅
curr = next = 1
```

______________________________________________________________________

## 📌 Key Takeaway

Insertion Sort pada linked list lebih natural dari array karena penyisipan di posisi mana pun O(1) (cukup ubah pointer) — tidak perlu shift elemen seperti di array. Dua kunci implementasi: **dummy node** untuk menyeragamkan penyisipan, dan **simpan `next` sebelum modifikasi pointer** agar tidak kehilangan sisa list. 🎯

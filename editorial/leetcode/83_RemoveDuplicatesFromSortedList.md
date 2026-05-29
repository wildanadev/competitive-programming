# 83. Remove Duplicates from Sorted List

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Linked List, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/remove-duplicates-from-sorted-list/)
- **Solution**: [Code](../../leetcode/RemoveDuplicatesFromSortedList.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `head` dari linked list yang **terurut**, hapus semua duplikat sehingga setiap nilai muncul **tepat satu kali**. Kembalikan linked list yang sudah dimodifikasi.

Contoh:

- `1 → 1 → 2` → `1 → 2`
- `1 → 1 → 2 → 3 → 3` → `1 → 2 → 3`

______________________________________________________________________

## 💡 Intuition

Karena list **sudah terurut**, duplikat selalu berurutan. Dengan satu pointer `curr` yang berjalan dari head:

- Jika `curr.val == curr.next.val` → skip `curr.next` dengan `curr.next = curr.next.next` (tidak maju, karena elemen baru di `curr.next` perlu dicek lagi).
- Jika berbeda → maju `curr = curr.next`.

______________________________________________________________________

## 🔍 Approach

### Single Pass — Skip Duplicates In-place

1. Inisialisasi `curr = head`.
1. Selama `curr != null` dan `curr.next != null`:
   - Jika `curr.val == curr.next.val` → `curr.next = curr.next.next` (skip duplikat).
   - Jika tidak → `curr = curr.next` (maju).
1. Return `head`.

> `curr` **tidak maju** saat skip — karena elemen baru yang menggantikan `curr.next` perlu dicek lagi (bisa saja juga duplikat).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------ |
| **Time** | O(n) — setiap node dikunjungi tepat satu kali |
| **Space** | O(1) — modifikasi in-place, hanya pointer `curr` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `1 → 1 → 2 → 3 → 3`

`curr = node(1)`

| curr.val | curr.next.val | Sama? | Aksi | List setelah |
| -------- | ------------- | ----- | ---------------------- | --------------- |
| 1 | 1 | ✅ | curr.next = node(2) | `1 → 2 → 3 → 3` |
| 1 | 2 | ❌ | curr = node(2) | `1 → 2 → 3 → 3` |
| 2 | 3 | ❌ | curr = node(3) | `1 → 2 → 3 → 3` |
| 3 | 3 | ✅ | curr.next = null | `1 → 2 → 3` |
| 3 | null | — | curr.next==null → stop | `1 → 2 → 3` |

**Output: `1 → 2 → 3` ✅**

______________________________________________________________________

**Input:** `1 → 1 → 1`

`curr = node(1)`

| curr.val | curr.next.val | Sama? | Aksi | List |
| -------- | ------------- | ----- | -------------------- | ------------------------------- |
| 1 | 1 | ✅ | curr.next=node(1)\_3 | `1 → 1 → 1` (node ke-2 di-skip) |
| 1 | 1 | ✅ | curr.next=null | `1` |
| 1 | null | — | stop | `1` |

**Output: `1` ✅**

______________________________________________________________________

**Input:** `1 → 2 → 3`

Tidak ada duplikat → `curr` terus maju tanpa skip apapun.

**Output: `1 → 2 → 3` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `head = null` → kondisi while langsung gagal → return `null`
- [ ] Satu node → `curr.next = null` → kondisi while langsung gagal → return head
- [ ] Semua node sama → skip semua, tersisa satu node
- [ ] Tidak ada duplikat → tidak ada skip, return list asli

______________________________________________________________________

## 🔧 Kenapa `curr` Tidak Maju Saat Skip?

```java
if (curr.val == curr.next.val) {
    curr.next = curr.next.next;  // skip, tapi curr tetap
} else {
    curr = curr.next;            // baru maju
}
```

Contoh: `1 → 1 → 1 → 2`

```
curr=1, next=1 (sama) → skip → curr.next = node(1)_ke3
curr=1, next=1 (sama) → skip → curr.next = node(2)
curr=1, next=2 (beda) → maju → curr=2
curr=2, next=null → stop
```

Jika `curr` langsung maju saat skip:

```
curr=1, next=1 (sama) → skip → curr.next = node(1)_ke3, lalu curr maju ke node(1)_ke3
curr=1, next=2 (beda) → maju → melewatkan pengecekan node(1)_ke3 dengan node(1)_ke2? ❌
```

Tidak maju memastikan kita tetap di `curr` untuk mengecek `curr.next` yang baru.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh linked list in-place modification yang paling bersih — tidak butuh dummy node atau dua pointer. Kunci utamanya: **tidak maju saat skip** agar elemen pengganti tetap dicek. Karena list sudah terurut, duplikat selalu bersebelahan sehingga satu pass linear sudah cukup. 🎯

# 206. Reverse Linked List

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Linked List, Recursion
- **Link**: [Problem](https://leetcode.com/problems/reverse-linked-list/)
- **Solution**: [Code](../../leetcode/ReverseLinkedList.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `head` dari linked list, balik list tersebut dan kembalikan head baru.

Contoh:

- `1 → 2 → 3 → 4 → 5` → `5 → 4 → 3 → 2 → 1`
- `1 → 2` → `2 → 1`
- `[]` → `[]`

______________________________________________________________________

## 💡 Intuition

Gunakan tiga pointer: `prev`, `curr`, dan `next`. Untuk setiap node, balik arah pointer `curr.next` dari menunjuk ke depan menjadi menunjuk ke belakang (`prev`). Maju keduanya satu langkah.

```
Sebelum: null ← prev   curr → next → ...
Setelah: null ← prev ← curr   next → ...
```

______________________________________________________________________

## 🔍 Approach

### Iterative — Three Pointers

1. Inisialisasi `prev = null`, `curr = head`.
1. Loop selama `curr != null`:
   - Simpan `next = curr.next` (agar tidak hilang setelah balik pointer)
   - Balik pointer: `curr.next = prev`
   - Maju: `prev = curr`, `curr = next`
1. Return `prev` (head baru).

> `prev` harus diinisialisasi `null` karena node terakhir (yang akan menjadi head baru tail) harus menunjuk ke `null`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------ |
| **Time** | O(n) — setiap node dikunjungi sekali |
| **Space** | O(1) — hanya tiga pointer tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `1 → 2 → 3 → 4 → 5`

`prev=null, curr=1`

| curr | next disimpan | curr.next dibalik ke | prev | curr baru |
| ---- | ------------- | -------------------- | ---- | --------- |
| 1 | 2 | null (prev) | 1 | 2 |
| 2 | 3 | 1 (prev) | 2 | 3 |
| 3 | 4 | 2 (prev) | 3 | 4 |
| 4 | 5 | 3 (prev) | 4 | 5 |
| 5 | null | 4 (prev) | 5 | null |

`curr = null` → loop berhenti → return `prev = 5`

```
Hasil: 5 → 4 → 3 → 2 → 1 → null ✅
```

**Output: `5 → 4 → 3 → 2 → 1` ✅**

______________________________________________________________________

**Input:** `1 → 2`

| curr | next | curr.next | prev | curr baru |
| ---- | ---- | --------- | ---- | --------- |
| 1 | 2 | null | 1 | 2 |
| 2 | null | 1 | 2 | null |

Return `prev = 2` → `2 → 1 → null`

**Output: `2 → 1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `head = null` → `curr = null` → loop tidak jalan → return `prev = null` ✅
- [ ] Satu node → satu iterasi → `curr.next = null`, return node itu sendiri ✅

______________________________________________________________________

## 🔧 Kenapa Simpan `next` Dulu Sebelum Balik Pointer?

```java
ListNode next = curr.next;  // (1) simpan dulu
curr.next = prev;           // (2) balik pointer → curr.next sekarang menunjuk ke belakang
prev = curr;                // (3) maju prev
curr = next;                // (4) maju curr menggunakan next yang sudah disimpan
```

Jika `(2)` dilakukan sebelum `(1)`:

```java
curr.next = prev;    // curr.next sudah dibalik!
curr = curr.next;    // ← curr.next sekarang adalah prev (ke belakang), bukan ke depan! ❌
```

Tanpa menyimpan `next`, kita kehilangan referensi ke node berikutnya setelah pointer dibalik.

______________________________________________________________________

## 🔧 Perbandingan Kode Lama vs Baru

```java
// Kode lama — nama variabel membingungkan
ListNode next = head;
ListNode prev1 = null, prev2 = null;
while (next != null) {
    prev1 = next;
    next = next.next;
    prev1.next = prev2;
    prev2 = prev1;
}
return prev2;

// Kode baru — nama eksplisit, urutan logis
ListNode prev = null;
ListNode curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev;
```

Kedua kode identik secara logika — perbedaannya hanya penamaan variabel. Nama `prev`, `curr`, `next` jauh lebih mudah dibaca karena mencerminkan peran masing-masing pointer.

______________________________________________________________________

## 🔀 Alternatif: Rekursif

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
```

**Cara kerja:**

- Rekursi sampai node terakhir → itu menjadi `newHead`
- Saat kembali: `head.next.next = head` (balik pointer), `head.next = null` (putus pointer lama)

| Approach | Time | Space | Catatan |
| --------------- | ---- | ----- | --------------- |
| Iteratif (kode) | O(n) | O(1) | Lebih efisien |
| Rekursif | O(n) | O(n) | Call stack O(n) |

______________________________________________________________________

## 📌 Key Takeaway

Reverse linked list adalah operasi fundamental yang mengajarkan pola **tiga pointer**: `prev`, `curr`, `next`. Urutan empat langkah — simpan next, balik pointer, maju prev, maju curr — harus diingat dengan baik karena menjadi building block untuk soal linked list yang lebih kompleks seperti _Reverse Linked List II_ dan _Palindrome Linked List_. 🎯

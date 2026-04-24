# 234. Palindrome Linked List

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Linked List, Two Pointers, Recursion
- **Link**: [Problem](https://leetcode.com/problems/palindrome-linked-list/)
- **Solution**: [Code](../../leetcode/PalindromeLinkedList.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `head` dari sebuah linked list, tentukan apakah nilai-nilainya membentuk **palindrom**.

Contoh:

- `1 → 2 → 2 → 1` → `true`
- `1 → 2` → `false`
- `1 → 2 → 3 → 2 → 1` → `true`

______________________________________________________________________

## 💡 Intuition

Tidak seperti array, linked list tidak bisa diakses secara random dari belakang. Strateginya:

1. **Temukan titik tengah** dengan fast & slow pointer.
1. **Balik paruh kedua** list secara in-place.
1. **Bandingkan** paruh pertama dan paruh kedua yang sudah dibalik node per node.

Jika semua nilai cocok → palindrom.

______________________________________________________________________

## 🔍 Approach

### Fast & Slow Pointer + Reverse Second Half

**Step 1 — Cari tengah:**

- `slow` maju 1 langkah, `fast` maju 2 langkah.
- Saat `fast` mencapai akhir, `slow` tepat berada di **tengah**.

**Step 2 — Balik paruh kedua:**

- Mulai dari `slow`, balik arah pointer setiap node.
- Setelah selesai, `prev` adalah `head` dari paruh kedua yang sudah terbalik.

**Step 3 — Bandingkan:**

- `left` mulai dari `head` (paruh pertama).
- `right` mulai dari `prev` (paruh kedua terbalik).
- Jika ada nilai yang tidak cocok → return `false`.
- Jika semua cocok → return `true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------- |
| **Time** | O(n) — tiga pass linear terpisah |
| **Space** | O(1) — hanya pointer tambahan, reverse in-place |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `1 → 2 → 2 → 1`

**Step 1 — Cari tengah:**

`slow = 1`, `fast = 1`

| Iterasi | slow | fast |
| ------- | ------------- | ------------- |
| 1 | 2 | 2 (node ke-3) |
| 2 | 2 (node ke-3) | null → stop |

`slow` berhenti di node ke-3 (nilai `2`) — awal paruh kedua.

```
1 → 2 → [2] → 1
         ↑
        slow
```

**Step 2 — Balik paruh kedua mulai dari `slow`:**

`prev = null`, `cur = node(2)`

| cur | next disimpan | cur.next diubah ke | prev | cur berikutnya |
| -------- | ------------- | ------------------ | ---- | -------------- |
| 2 (ke-3) | 1 | null (prev) | 2 | 1 |
| 1 | null | 2 (prev) | 1 | null |

Paruh kedua terbalik: `1 → 2 → null`, `prev = node(1)`

**Step 3 — Bandingkan:**

`left = node(1)` (head), `right = node(1)` (prev)

| left.val | right.val | Cocok? |
| ------------------- | --------- | ------ |
| 1 | 1 | ✅ |
| 2 | 2 | ✅ |
| right = null → stop | | |

**Output: `true` ✅**

______________________________________________________________________

**Input:** `1 → 2 → 3 → 2 → 1`

**Step 1 — Cari tengah:**

| Iterasi | slow | fast |
| ------- | ---- | ------------ |
| 1 | 2 | 3 |
| 2 | 3 | 1 (terakhir) |

`slow` berhenti di node `3` (tengah).

**Step 2 — Balik paruh kedua `3 → 2 → 1`:**

Hasil: `1 → 2 → 3 → null`, `prev = node(1)`

**Step 3 — Bandingkan:**

| left.val | right.val | Cocok? |
| ------------------- | --------- | ------ |
| 1 | 1 | ✅ |
| 2 | 2 | ✅ |
| 3 | 3 | ✅ |
| right = null → stop | | |

**Output: `true` ✅**

______________________________________________________________________

**Input:** `1 → 2` (negative test case)

**Step 1:** `slow` berhenti di node `2`.

**Step 2 — Balik `2`:** `prev = node(2)`

**Step 3 — Bandingkan:**

| left.val | right.val | Cocok? |
| -------- | --------- | ------------------- |
| 1 | 2 | ❌ → return `false` |

**Output: `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu node → `fast` langsung `null`, `slow = head`, paruh kedua = satu node → selalu `true`
- [ ] Dua node sama `1 → 1` → `slow` di node ke-2, reverse → `left=1, right=1` → `true`
- [ ] Dua node beda `1 → 2` → `false`
- [ ] Panjang ganjil → `slow` berhenti di node tengah; perbandingan berhenti saat `right = null` sehingga node tengah tidak perlu dicek

______________________________________________________________________

## 📌 Key Takeaway

Tiga langkah klasik untuk palindrom linked list: **cari tengah → balik paruh kedua → bandingkan**. Masing-masing langkah O(n) menghasilkan total O(n) time dan O(1) space. Perlu diingat bahwa approach ini **memodifikasi struktur list asli** — jika list harus dijaga tetap utuh, tambahkan step keempat untuk mengembalikan list ke kondisi semula dengan membalik lagi paruh kedua. 🎯

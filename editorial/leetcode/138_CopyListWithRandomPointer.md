# 138. Copy List with Random Pointer

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Hash Table, Linked List
- **Link**: [Problem](https://leetcode.com/problems/copy-list-with-random-pointer/)
- **Solution**: [Code](../../leetcode/CopyListWithRandomPointer.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan linked list di mana setiap node memiliki pointer `next` dan `random` (bisa menunjuk ke node mana saja atau `null`). Buat **deep copy** — salinan baru yang sepenuhnya independen dari list asli.

Contoh:

```
Original: 1 → 2 → 3
random:   1→3, 2→1, 3→null
```

Buat salinan dengan semua pointer `next` dan `random` menunjuk ke node **baru** yang sesuai.

______________________________________________________________________

## 💡 Intuition

Masalah utama: pointer `random` bisa menunjuk ke node yang **belum dibuat** salinannya.

Solusi HashMap: **dua pass** — buat semua node salinan dulu, baru hubungkan pointer.

Solusi Interleave: **sisipkan** node salinan langsung setelah node asli — sehingga `curr.next` selalu salinan dari `curr`, tidak perlu HashMap!

______________________________________________________________________

## 🔍 Approach 1: Two-Pass + HashMap

**Pass 1 — Buat semua node copy:**

```java
while (curr != null) {
    map.put(curr, new Node(curr.val));
    curr = curr.next;
}
```

**Pass 2 — Hubungkan pointer:**

```java
while (curr != null) {
    map.get(curr).next   = map.get(curr.next);
    map.get(curr).random = map.get(curr.random);
    curr = curr.next;
}
return map.get(head);
```

______________________________________________________________________

## 🔍 Approach 2: Interleave Nodes (O(1) Space)

Sisipkan node salinan tepat setelah node asli — struktur jadi:

```
1 → 1' → 2 → 2' → 3 → 3' → null
```

Karena setiap `curr.next` adalah salinannya, kita bisa set `random` tanpa HashMap:

```
curr.next.random = curr.random.next
```

Lalu pisahkan dua list.

**Implementasi:**

```java
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) return null;

        // Step 1: Sisipkan copy setelah setiap node
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;  // lompat ke node asli berikutnya
        }

        // Step 2: Hubungkan random
        curr = head;
        while (curr != null) {
            if (curr.random != null)
                curr.next.random = curr.random.next;
            curr = curr.next.next;  // lompat dua langkah (skip copy)
        }

        // Step 3: Pisahkan dua list
        curr = head;
        Node copyHead = head.next;
        while (curr != null) {
            Node copy = curr.next;
            curr.next = copy.next;           // restore original
            copy.next = (copy.next != null) ? copy.next.next : null;
            curr = curr.next;
        }

        return copyHead;
    }
}
```

______________________________________________________________________

## 🧮 Complexity

| Approach | Time | Space |
| ---------- | ---- | ----- |
| HashMap | O(n) | O(n) |
| Interleave | O(n) | O(1) |

______________________________________________________________________

## 🧪 Dry Run — HashMap Approach

**Input:** `7 → 13 → 11`, random: `7→null, 13→7, 11→7`

**Pass 1:**

```
map = {Node7: Copy7, Node13: Copy13, Node11: Copy11}
```

**Pass 2:**
| curr | copy.next | copy.random |
|------|-----------|-------------|
| Node7 | Copy13 | null |
| Node13 | Copy11 | Copy7 |
| Node11 | null | Copy7 |

**Output:** `Copy7 → Copy13 → Copy11` dengan random yang benar ✅

______________________________________________________________________

## 🧪 Dry Run — Interleave Approach

**Input:** `7 → 13 → 11`, random: `7→null, 13→7, 11→7`

**Step 1 — Sisipkan copy:**

```
7 → 7' → 13 → 13' → 11 → 11' → null
```

**Step 2 — Hubungkan random:**

```
curr=7:  random=null → skip
curr=13: random=Node7 → 13'.random = Node7.next = 7'
curr=11: random=Node7 → 11'.random = Node7.next = 7'
```

**Step 3 — Pisahkan:**

```
Original: 7 → 13 → 11 → null
Copy:     7' → 13' → 11' → null
          random: null, 7', 7'
```

**Output:** `7' → 13' → 11'` dengan random yang benar ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `head = null` → return `null` langsung
- [ ] `random = null` → HashMap: `map.get(null)=null` ✅; Interleave: `if (curr.random != null)` ✅
- [ ] `random` menunjuk dirinya sendiri → kedua approach menangani dengan benar
- [ ] Satu node → satu iterasi per step

______________________________________________________________________

## 🔧 Kenapa `curr.next.random = curr.random.next` di Step 2?

```
Struktur setelah step 1:
curr → copy → curr.random → copy_of_random → ...

curr.random       = node asli yang ditunjuk random
curr.random.next  = salinan dari node tersebut

Jadi: copy.random = salinan dari node yang ditunjuk curr.random ✅
```

Ini hanya bekerja karena setiap node asli **selalu diikuti** salinannya — itulah keindahan interleave!

______________________________________________________________________

## 🔧 Kenapa `map.get(null) = null` di HashMap Approach?

HashMap Java mengembalikan `null` untuk key yang tidak ada di map:

```java
copy.next   = map.get(curr.next);    // curr.next=null → copy.next=null ✅
copy.random = map.get(curr.random);  // curr.random=null → copy.random=null ✅
```

Menghilangkan kebutuhan kondisi `if (curr.next != null)` dan `if (curr.random != null)`.

______________________________________________________________________

## 📌 Key Takeaway

Dua approach yang kontras: **HashMap** mudah dipahami dengan O(n) space, sementara **Interleave** hemat memori dengan O(1) space tapi memerlukan tiga step yang hati-hati. Kunci Interleave adalah fakta bahwa setelah sisipan, `curr.random.next` selalu salinan dari node yang ditunjuk `random` — menghilangkan kebutuhan HashMap sebagai jembatan lookup. 🎯

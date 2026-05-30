# 328. Odd Even Linked List
- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Linked List
- **Link**: [Problem](https://leetcode.com/problems/odd-even-linked-list/)
- **Solution**: [Code](../../leetcode/OddEvenLinkedList.java)
______________________________________________________________________
## 📄 Problem Summary
Diberikan `head` linked list, kelompokkan semua node dengan **indeks ganjil** (1,3,5,...) diikuti node dengan **indeks genap** (2,4,6,...). Pertahankan urutan relatif dalam masing-masing kelompok.

> Indeks berbasis 1, bukan nilai node.

Contoh:
- `1 → 2 → 3 → 4 → 5` → `1 → 3 → 5 → 2 → 4`
- `2 → 1 → 3 → 5 → 6 → 4 → 7` → `2 → 3 → 6 → 7 → 1 → 5 → 4`
______________________________________________________________________
## 💡 Intuition
Gunakan **dua pointer** yang berjalan secara paralel:
- `odd` → pointer untuk node-node ganjil, loncat dua langkah
- `even` → pointer untuk node-node genap, loncat dua langkah
- `evenHead` → simpan head dari list genap agar bisa disambungkan di akhir

Setelah loop selesai, sambungkan tail dari list ganjil ke `evenHead`.

```
Original: 1 → 2 → 3 → 4 → 5
odd:      1 ------→ 3 ------→ 5
even:         2 ------→ 4

Gabung: 1 → 3 → 5 → 2 → 4
```

______________________________________________________________________
## 🔍 Approach
### Two Pointer — Weave Odd and Even
1. Jika `head == null` → return `null`.
2. Inisialisasi `odd = head`, `even = head.next`, `evenHead = even`.
3. Loop selama `odd != null && even != null && even.next != null`:
   - `odd.next = odd.next.next` → odd skip satu node
   - `odd = odd.next` → maju ke node ganjil berikutnya
   - `even.next = even.next.next` → even skip satu node
   - `even = even.next` → maju ke node genap berikutnya
4. `odd.next = evenHead` → sambungkan tail ganjil ke head genap.
5. Return `head`.

______________________________________________________________________
## 🧮 Complexity
| | |
| --------- | ----------------------------------------------- |
| **Time**  | O(n) — setiap node dikunjungi sekali |
| **Space** | O(1) — hanya pointer tambahan, modifikasi in-place |

______________________________________________________________________
## 🧪 Dry Run
**Input:** `1 → 2 → 3 → 4 → 5`

`odd=1, even=2, evenHead=2`

**Iterasi 1:**
```
odd.next = odd.next.next = node(3)   → 1 → 3 → 4 → 5
odd = node(3)
even.next = even.next.next = node(4) → 2 → 4 → 5
even = node(4)
```
State: `odd=3, even=4`

**Iterasi 2:**
```
odd.next = odd.next.next = node(5)   → 1 → 3 → 5
odd = node(5)
even.next = even.next.next = null    → 2 → 4 → null
even = null
```
State: `odd=5, even=null`

Loop berhenti (`even == null`)

**Sambung:** `odd.next = evenHead` → `1 → 3 → 5 → 2 → 4`

**Output: `1 → 3 → 5 → 2 → 4` ✅**

______________________________________________________________________
**Input:** `1 → 2 → 3 → 4`

`odd=1, even=2, evenHead=2`

**Iterasi 1:**
```
odd.next = node(3), odd = node(3)
even.next = node(4), even = node(4)
```
State: `odd=3, even=4`, `even.next=null`

Loop berhenti (`even.next == null`)

**Sambung:** `odd.next = evenHead=2` → `1 → 3 → 2 → 4`

**Output: `1 → 3 → 2 → 4` ✅**

______________________________________________________________________
**Input:** `1 → 2`

`odd=1, even=2, evenHead=2`

`even.next = null` → loop tidak jalan sama sekali

**Sambung:** `odd.next = evenHead=2` → `1 → 2` (tidak berubah)

**Output: `1 → 2` ✅**

______________________________________________________________________
## ⚠️ Edge Cases
- [ ] `head = null` → return null langsung
- [ ] Satu node → `even = null` → loop tidak jalan → `odd.next = null` (evenHead) → return head
- [ ] Dua node → loop tidak jalan, sambung langsung
- [ ] List panjang genap vs ganjil → kondisi loop menangani keduanya

______________________________________________________________________
## 🔧 Kenapa Kondisi Loop `odd != null && even != null && even.next != null`?

```java
while (odd != null && even != null && even.next != null)
```

Tiga kondisi ini memastikan semua akses pointer aman:

**`even != null`**: diperlukan sebelum akses `even.next`

**`even.next != null`**: diperlukan sebelum akses `even.next.next` di dalam loop:
```java
even.next = even.next.next;  // butuh even.next != null
```

**`odd != null`**: diperlukan sebelum akses `odd.next` (meski dalam praktik jika `even` tidak null, `odd` juga tidak null — karena odd selalu satu langkah di depan even)

---

Contoh tanpa cek `even.next != null`:
```
List: 1 → 2 → 3 → 4
Setelah iterasi 1: odd=3, even=4, even.next=null
Loop coba lagi: even.next.next → NullPointerException! ❌
```

______________________________________________________________________
## 🔧 Kenapa Simpan `evenHead` di Awal?

```java
ListNode evenHead = even;  // simpan sebelum even berubah
```

Pointer `even` akan terus maju sampai `null` di akhir loop. Jika tidak disimpan, kita tidak bisa tahu di mana list genap dimulai untuk disambungkan di akhir.

```
Tanpa evenHead:
  even akhirnya = null (atau tail)
  odd.next = even = null ❌ (list genap hilang!)

Dengan evenHead:
  evenHead tetap menunjuk ke node(2) sepanjang waktu
  odd.next = evenHead ✅
```

______________________________________________________________________
## 📌 Key Takeaway
Soal ini adalah contoh **in-place linked list rearrangement** menggunakan dua pointer paralel. Kuncinya adalah menyimpan `evenHead` sebelum pointer `even` bergerak, dan kondisi loop yang memastikan semua akses pointer aman. Pola "pisah menjadi dua sub-list, sambung di akhir" ini juga muncul di soal seperti *Partition List* dan *Sort List*. 🎯

# 3892. Design Event Manager

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Hash Table, Design, Ordered Set
- **Link**: [Problem](https://leetcode.com/problems/design-event-manager/)
- **Solution**: [Code](../../leetcode/DesignEventManager.java)

______________________________________________________________________

## 📄 Problem Summary

Implementasi class `EventManager` dengan tiga operasi:

- `EventManager(int[][] events)` — inisialisasi dengan list event `[eventId, priority]`
- `updatePriority(int eventId, int newPriority)` — update priority sebuah event
- `pollHighest()` — hapus dan return `eventId` dengan priority tertinggi. Jika priority sama, return `eventId` terkecil. Jika kosong return `-1`

Contoh:

```
events = [[5,7], [2,7], [9,4]]
pollHighest()          → 2  (priority 7, terkecil antara 5 dan 2)
updatePriority(9, 7)   → null
pollHighest()          → 5  (priority 7, terkecil antara 5 dan 9)
pollHighest()          → 9
```

______________________________________________________________________

## 💡 Intuition

Butuh struktur data yang bisa:

1. **Cari & hapus** event berdasarkan priority → **TreeSet** (ordered)
1. **Lookup priority** berdasarkan eventId → **HashMap**

Kombinasi keduanya memungkinkan semua operasi efisien.

______________________________________________________________________

## 🔍 Approach

### Struktur Data

```
HashMap  → eventId → priority (untuk lookup O(1))
TreeSet  → sorted by: priority DESC, eventId ASC (untuk poll O(log n))
```

### Comparator TreeSet

```java
(a, b) -> a[1] != b[1] ? b[1] - a[1] : a[0] - b[0]
//         priority berbeda? → sort DESC    → sort eventId ASC
```

### Constructor

- Loop semua event → masukkan ke HashMap dan TreeSet

### updatePriority

1. Ambil `oldPriority` dari HashMap
1. Update HashMap dengan `newPriority`
1. **Hapus** `[eventId, oldPriority]` dari TreeSet
1. **Tambah** `[eventId, newPriority]` ke TreeSet

### pollHighest

1. Kalau kosong → return `-1`
1. Ambil elemen pertama TreeSet (`getFirst()`) → priority tertinggi, eventId terkecil
1. Hapus dari TreeSet dan HashMap
1. Return `eventId`

______________________________________________________________________

## 🧮 Complexity

| Operasi | Time | Keterangan |
| -------------- | ---------- | ---------------------------- |
| Constructor | O(n log n) | Insert n elemen ke TreeSet |
| updatePriority | O(log n) | Remove + add di TreeSet |
| pollHighest | O(log n) | getFirst + remove di TreeSet |
| **Space** | O(n) | HashMap + TreeSet |

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```
events = [[5,7], [2,7], [9,4]]
```

**Init:**

```
HashMap: {5→7, 2→7, 9→4}
TreeSet (sorted priority DESC, eventId ASC):
  [2,7] ← first (priority 7, eventId terkecil)
  [5,7]
  [9,4]
```

______________________________________________________________________

**`pollHighest()`**

```
first = [2, 7]
hapus dari TreeSet dan HashMap
return 2 ✅

HashMap: {5→7, 9→4}
TreeSet: [5,7], [9,4]
```

______________________________________________________________________

**`updatePriority(9, 7)`**

```
oldPriority = HashMap.get(9) = 4
HashMap.put(9, 7)
TreeSet.remove([9, 4])
TreeSet.add([9, 7])

HashMap: {5→7, 9→7}
TreeSet: [5,7], [9,7]  ← keduanya priority 7
```

______________________________________________________________________

**`pollHighest()`**

```
first = [5, 7]  ← priority sama, eventId 5 < 9
hapus dari TreeSet dan HashMap
return 5 ✅
```

______________________________________________________________________

**`pollHighest()`**

```
first = [9, 7]
hapus dari TreeSet dan HashMap
return 9 ✅
```

## ⚠️ Edge Cases

- [ ] `pollHighest()` saat kosong → return `-1`
- [ ] Dua event dengan priority sama → return eventId terkecil
- [ ] `updatePriority` ke priority yang sama → tidak berubah urutan

______________________________________________________________________

## 📌 Key Takeaway

Kombinasi **HashMap + TreeSet** adalah pola klasik untuk soal Design yang butuh:

- Lookup cepat O(1) via HashMap
- Ordered retrieval O(log n) via TreeSet

Penting: saat `updatePriority`, harus **hapus dulu dengan oldPriority** sebelum tambah yang baru — karena TreeSet menggunakan comparator berbasis nilai `[eventId, priority]`, bukan referensi objek. 🎯

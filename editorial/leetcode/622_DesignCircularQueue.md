# 622. Design Circular Queue

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Linked List, Design, Queue
- **Link**: [Problem](https://leetcode.com/problems/design-circular-queue/)
- **Solution**: [Code](../../leetcode/MyCircularQueue.java)

______________________________________________________________________

## 📄 Problem Summary

Implementasikan **Circular Queue** (ring buffer) dengan operasi:

- `enQueue(value)` → tambah elemen ke belakang
- `deQueue()` → hapus elemen dari depan
- `Front()` → lihat elemen paling depan
- `Rear()` → lihat elemen paling belakang
- `isEmpty()` → cek apakah kosong
- `isFull()` → cek apakah penuh

**Circular**: setelah posisi terakhir array, kembali ke posisi `0` — memanfaatkan ruang yang sudah dikosongkan oleh `deQueue`.

______________________________________________________________________

## 💡 Intuition

Queue biasa dengan array membuang ruang di depan saat `deQueue`. **Circular Queue** mengatasinya dengan melacak `front` dan `rear` secara independen menggunakan **modulo** — ketika pointer mencapai akhir array, ia "memutar" kembali ke awal.

```
Array: [_, _, _, _, _]  (size=5)
        ↑           ↑
       front       rear

Setelah beberapa enQueue dan deQueue:
Array: [3, 4, _, 1, 2]
              ↑  ↑
            rear front  ← front dan rear "memutar"!
```

**Kondisi kosong**: `front == -1 && rear == -1`

**Kondisi penuh**: `(rear + 1) % size == front`

______________________________________________________________________

## 🔍 Approach

### Array + Two Pointers (front & rear)

**`enQueue(value)`:**

- Jika penuh → return `false`
- Jika kosong → set `front = 0`
- `rear = (rear + 1) % size`, simpan value di `arr[rear]`

**`deQueue()`:**

- Jika kosong → return `false`
- Jika `front == rear` (satu elemen) → reset `front = rear = -1`
- Jika tidak → `front = (front + 1) % size`

**`Front()`:** return `arr[front]` (atau `-1` jika kosong)

**`Rear()`:** return `arr[rear]` (atau `-1` jika kosong)

**`isEmpty()`:** `front == -1 && rear == -1`

**`isFull()`:** `(rear + 1) % size == front`

______________________________________________________________________

## 🧮 Complexity

| Operasi | Time | Space |
| ------------- | ---- | ------------------------ |
| Semua operasi | O(1) | O(k) — array berukuran k |

______________________________________________________________________

## 🧪 Dry Run

**`MyCircularQueue(5)`** — size=5

```
arr = [_, _, _, _, _]
front = -1, rear = -1
```

______________________________________________________________________

**`enQueue(1)`:**

- `isEmpty()` → `front=-1 && rear=-1` ✅ → set `front=0`
- `rear = (−1+1)%5 = 0`, `arr[0]=1`

```
arr = [1, _, _, _, _]
front=0, rear=0
```

______________________________________________________________________

**`enQueue(2)`:** rear=(0+1)%5=1, arr[1]=2
**`enQueue(3)`:** rear=2, arr[2]=3
**`enQueue(4)`:** rear=3, arr[3]=4
**`enQueue(5)`:** rear=4, arr[4]=5

```
arr = [1, 2, 3, 4, 5]
front=0, rear=4
isFull(): (4+1)%5=0==front=0 ✅ PENUH
```

______________________________________________________________________

**`deQueue()`:**

- `front != rear` → `front=(0+1)%5=1`

```
arr = [1, 2, 3, 4, 5]  (arr[0] masih ada tapi diabaikan)
front=1, rear=4
```

______________________________________________________________________

**`enQueue(6)`:** (ada ruang sekarang!)

- `isFull()`: (4+1)%5=0 == front=1? ❌ → tidak penuh
- `rear=(4+1)%5=0`, `arr[0]=6` ← **memutar ke awal!**

```
arr = [6, 2, 3, 4, 5]
front=1, rear=0
```

______________________________________________________________________

**`Front()`:** return `arr[1] = 2` ✅

**`Rear()`:** return `arr[0] = 6` ✅

______________________________________________________________________

**`deQueue()` 4x:** front → 2,3,4,0

**Setelah front=0, rear=0 (satu elemen tersisa = 6):**

**`deQueue()`:**

- `front == rear` (0==0) → reset `front=rear=-1`

```
arr = [6, 2, 3, 4, 5]  (semua diabaikan)
front=-1, rear=-1
isEmpty() ✅
```

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Queue satu elemen → `front == rear` → deQueue reset keduanya ke `-1`
- [ ] Full → front=0, rear=size-1 → `(rear+1)%size = 0 = front` ✅
- [ ] Wrap around → rear memutar ke 0 saat mencapai akhir array

______________________________________________________________________

## 🔧 Kenapa `front == rear` Berarti Satu Elemen?

```
front dan rear menunjuk ke indeks yang sama
→ hanya ada satu slot yang terisi
→ setelah deQueue, queue kosong
→ reset ke -1 (kondisi kosong)
```

Tanpa pengecekan ini dan langsung `front = (front+1)%size`:

```
front=2, rear=2 (satu elemen di index 2)
deQueue: front = (2+1)%5 = 3
→ front=3, rear=2
→ isFull(): (2+1)%5=3==3 ✅ → dianggap PENUH! ❌
```

Reset ke `-1` penting untuk mempertahankan konsistensi kondisi kosong.

______________________________________________________________________

## 🔧 Kenapa `isFull()` = `(rear+1)%size == front`?

Satu slot sengaja **dibiarkan kosong** untuk membedakan kondisi penuh dan kosong:

```
isEmpty: front == -1  (sentinel value)
isFull:  (rear+1)%size == front

Jika tidak ada sentinel dan tidak ada slot kosong:
  isEmpty: front == rear  ← AMBIGU dengan satu elemen!
  isFull:  front == rear  ← sama!
```

Dengan `-1` sebagai sentinel untuk kosong, dan `(rear+1)%size == front` untuk penuh, keduanya tidak pernah bentrok.

______________________________________________________________________

## 📌 Key Takeaway

Circular Queue menggunakan **modulo** untuk "memutar" pointer — `(rear+1) % size` mengembalikan ke `0` saat mencapai ujung array. Dua detail penting: sentinel `-1` untuk membedakan kondisi kosong dari kondisi lain, dan pengecekan `front == rear` saat `deQueue` elemen terakhir untuk reset ke kondisi kosong. Struktur data ini adalah dasar dari **ring buffer** yang dipakai di OS, audio streaming, dan producer-consumer systems. 🎯

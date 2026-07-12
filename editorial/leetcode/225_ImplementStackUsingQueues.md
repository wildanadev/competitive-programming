# 225. Implement Stack using Queues

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Stack, Queue, Design
- **Link**: [Problem](https://leetcode.com/problems/implement-stack-using-queues/)
- **Solution**: [Code](../../leetcode/MyStack.java)

______________________________________________________________________

## 📄 Problem Summary

Implementasikan struktur data **Stack** (LIFO) menggunakan hanya **Queue** (FIFO). Operasi yang harus didukung:

- `push(x)` → tambah elemen ke atas stack
- `pop()` → hapus dan return elemen teratas
- `top()` → return elemen teratas tanpa menghapus
- `empty()` → return true jika stack kosong

Contoh:

```
push(1), push(2), top() → 2, pop() → 2, empty() → false
```

______________________________________________________________________

## 💡 Intuition

Stack adalah **LIFO** (Last In First Out), Queue adalah **FIFO** (First In First Out). Untuk mensimulasikan LIFO dengan FIFO, kita perlu memastikan elemen yang baru masuk selalu berada di **depan** queue.

Pendekatan **push-expensive**: setiap kali `push`, langsung putar ulang semua elemen lama ke belakang elemen baru. Sehingga elemen terbaru selalu ada di depan queue — `pop` dan `top` menjadi O(1).

```
push(1): queue = [1]
push(2): offer 2 → [1,2]
         rotate 1 kali: [2,1]
         → 2 ada di depan (top of stack)

push(3): offer 3 → [2,1,3]
         rotate 2 kali: [3,2,1]
         → 3 ada di depan
```

______________________________________________________________________

## 🔍 Approach

### Push-Expensive (Rotasi Queue)

**`push(x)`:**

1. `queue.offer(x)` → tambahkan `x` ke belakang.
1. Rotasi `queue.size() - 1` kali: pindahkan elemen dari depan ke belakang.
1. Setelah rotasi, `x` ada di depan.

**`pop()`:** `queue.poll()` → O(1)

**`top()`:** `queue.peek()` → O(1)

**`empty()`:** `queue.isEmpty()` → O(1)

______________________________________________________________________

## 🧮 Complexity

| Operasi | Time | Keterangan |
| --------- | ---- | ---------------------------- |
| `push` | O(n) | Rotasi n-1 kali |
| `pop` | O(1) | Langsung dari depan queue |
| `top` | O(1) | Langsung peek depan queue |
| `empty` | O(1) | Cek isEmpty |
| **Space** | O(n) | Queue menyimpan semua elemen |

______________________________________________________________________

## 🧪 Dry Run

**Operasi:** `push(1)`, `push(2)`, `push(3)`, `top()`, `pop()`

______________________________________________________________________

**`push(1)`:** offer 1 → `[1]`, rotasi 0 kali (size-1=0)

```
queue = [1]
```

______________________________________________________________________

**`push(2)`:** offer 2 → `[1,2]`, rotasi 1 kali

```
pindah 1 dari depan ke belakang: [2,1]
queue = [2,1]  ← 2 di depan (top)
```

______________________________________________________________________

**`push(3)`:** offer 3 → `[2,1,3]`, rotasi 2 kali

```
pindah 2 dari depan ke belakang: [1,3,2]
pindah 1 dari depan ke belakang: [3,2,1]
queue = [3,2,1]  ← 3 di depan (top)
```

______________________________________________________________________

**`top()`:** peek → `3` ✅

**`pop()`:** poll → `3`, queue = `[2,1]` ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `push` ke stack kosong → rotasi 0 kali (loop tidak jalan) → elemen langsung di depan
- [ ] Satu elemen → `top` = `pop` = elemen tersebut
- [ ] Banyak push berturut-turut → setiap push O(n) kali rotasi

______________________________________________________________________

## 🔧 Kenapa `for (int i = 1; i < queue.size(); i++)`?

Setelah `queue.offer(x)`, queue memiliki `n` elemen (termasuk `x` yang baru). Kita perlu merotasi `n-1` elemen (semua kecuali `x`) ke belakang `x`:

```
queue.size() = n
rotasi sebanyak: n-1 = queue.size() - 1

for (int i = 1; i < queue.size(); i++)  // i dari 1 sampai n-1 = n-1 kali
```

Dengan `i` mulai dari `1` (bukan `0`), loop jalan `size-1` kali — persis yang dibutuhkan.

______________________________________________________________________

## 🔧 Perbandingan dengan Implement Queue using Stacks (#232)

| | Queue using Stacks | Stack using Queues |
| ----------------- | --------------------------------- | ------------------ |
| **Mahal di mana** | Push O(n) atau Pop O(n) amortized | Push O(n) |
| **Murah di mana** | Pop/Peek O(1) atau Push O(1) | Pop/Top O(1) |
| **Struktur data** | Dua stack | Satu queue |
| **Trik** | Transfer antar stack | Rotasi queue |

Keduanya mensimulasikan struktur data LIFO↔FIFO dengan "membalik" urutan menggunakan properti masing-masing.

______________________________________________________________________

## 🚀 Alternatif: Pop-Expensive

Jika `pop`/`top` yang diizinkan mahal (O(n)), `push` bisa O(1):

```java
public void push(int x) {
    queue.offer(x);  // O(1), langsung ke belakang
}

public int pop() {
    // putar semua kecuali elemen terakhir
    for (int i = 1; i < queue.size(); i++)
        queue.offer(queue.poll());
    return queue.poll();  // elemen terakhir = top stack
}
```

| | Push-Expensive (kode) | Pop-Expensive |
| ----------- | --------------------- | ----------------- |
| `push` | O(n) | O(1) |
| `pop` | O(1) | O(n) |
| Cocok untuk | Lebih banyak pop/top | Lebih banyak push |

______________________________________________________________________

## 📌 Key Takeaway

Stack dengan queue mensimulasikan LIFO dengan mempertahankan elemen terbaru selalu di **depan** queue. Pendekatan push-expensive merotasi `n-1` elemen setiap push — memastikan `pop` dan `top` selalu O(1). Berbeda dengan implementasi Queue using Stacks (#232) yang bisa mencapai amortized O(1) dengan dua stack dan lazy transfer, Stack using Queue tidak bisa lebih baik dari O(n) untuk salah satu operasinya. 🎯

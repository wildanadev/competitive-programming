# 232. Implement Queue using Stacks

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Stack, Queue, Design
- **Link**: [Problem](https://leetcode.com/problems/implement-queue-using-stacks/)
- **Solution**: [Code](../../leetcode/MyQueue.java)

______________________________________________________________________

## 📄 Problem Summary

Implementasikan struktur data **Queue** (FIFO) menggunakan hanya dua **Stack** (LIFO). Operasi yang harus didukung:

- `push(x)` → tambah elemen ke belakang queue
- `pop()` → hapus dan return elemen paling depan
- `peek()` → return elemen paling depan tanpa menghapus
- `empty()` → return true jika queue kosong

Contoh:

```
push(1), push(2), peek() → 1, pop() → 1, empty() → false
```

______________________________________________________________________

## 💡 Intuition

Stack adalah **LIFO** (Last In First Out), Queue adalah **FIFO** (First In First Out). Untuk mensimulasikan FIFO dengan LIFO, kita perlu **membalik urutan** elemen.

Pendekatan kode ini: **push yang mahal** — saat `push`, pindahkan semua elemen dari `stack` ke `stack1` (urutan terbalik), tambahkan elemen baru ke `stack` yang kosong, lalu kembalikan semua dari `stack1` ke `stack`.

Hasilnya: elemen yang pertama masuk selalu ada di **top** `stack` → `pop` dan `peek` langsung O(1).

```
push(1): stack = [1]
push(2): pindah ke stack1=[1], push 2 ke stack=[2], kembalikan → stack=[2,1]
                                                                         ↑ top
peek() → 1 ✅ (elemen pertama masuk ada di top)
```

______________________________________________________________________

## 🔍 Approach

### Push-Expensive (Costly Push)

**`push(x)`:**

1. Jika `stack` kosong → langsung push `x`.
1. Jika tidak:
   - Pindah semua dari `stack` ke `stack1` (balik urutan).
   - Push `x` ke `stack` yang kini kosong.
   - Kembalikan semua dari `stack1` ke `stack`.

**`pop()`:** langsung `stack.pop()` → O(1)

**`peek()`:** langsung `stack.peek()` → O(1)

**`empty()`:** langsung `stack.isEmpty()` → O(1)

______________________________________________________________________

## 🧮 Complexity

| Operasi | Time | Keterangan |
| --------- | ---- | --------------------------------- |
| `push` | O(n) | Memindahkan semua elemen dua kali |
| `pop` | O(1) | Langsung dari top stack |
| `peek` | O(1) | Langsung dari top stack |
| `empty` | O(1) | Cek isEmpty |
| **Space** | O(n) | Dua stack menyimpan semua elemen |

______________________________________________________________________

## 🧪 Dry Run

**Operasi:** `push(1)`, `push(2)`, `push(3)`, `peek()`, `pop()`, `push(4)`, `pop()`

______________________________________________________________________

**`push(1)`:** stack kosong → langsung push

```
stack = [1]   (top=1)
stack1 = []
```

______________________________________________________________________

**`push(2)`:** stack tidak kosong

```
pindah ke stack1: stack1=[1], stack=[]
push 2:           stack=[2]
kembalikan:       stack=[2,1], stack1=[]
                        ↑ top=1 (elemen pertama masuk)
```

______________________________________________________________________

**`push(3)`:** stack tidak kosong

```
pindah ke stack1: stack1=[2,1], stack=[]
                        ↑ (1 di atas)... tunggu, stack awalnya [2,1] dengan top=1
pindah satu per satu:
  pop 1 → stack1=[1], stack=[2]
  pop 2 → stack1=[1,2], stack=[]
push 3: stack=[3]
kembalikan:
  pop 2 → stack=[3,2], stack1=[1]
  pop 1 → stack=[3,2,1], stack1=[]
                    ↑ top=1
```

______________________________________________________________________

**`peek()`:** return `stack.peek() = 1` ✅

______________________________________________________________________

**`pop()`:** return `stack.pop() = 1`

```
stack = [3,2]   (top=2)
```

______________________________________________________________________

**`push(4)`:** stack tidak kosong

```
pindah: stack1=[3,2]→ stack1=[2,3] (satu per satu: pop 2, pop 3)
        ... stack1=[2,3], stack=[]
push 4: stack=[4]
kembalikan: pop 3 → stack=[4,3], pop 2 → stack=[4,3,2]
                                                    ↑ top=2
```

______________________________________________________________________

**`pop()`:** return `stack.pop() = 2` ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `push` ke queue kosong → langsung push tanpa pindah-pindah
- [ ] `pop` atau `peek` setelah satu elemen → stack berisi satu elemen → langsung return
- [ ] Banyak push berturut-turut → setiap push O(n), total bisa O(n²) untuk n push

______________________________________________________________________

## 🚀 Alternatif: Pop-Expensive (Lazy Transfer) — Lebih Optimal

Pendekatan kode ini mahal di `push` (O(n) setiap saat). Ada pendekatan lebih efisien: **lazy transfer** — hanya pindahkan saat `pop`/`peek` diperlukan dan `stack` output kosong.

```java
class MyQueue {
    ArrayDeque<Integer> inbox  = new ArrayDeque<>(); // untuk push
    ArrayDeque<Integer> outbox = new ArrayDeque<>(); // untuk pop/peek

    public void push(int x) {
        inbox.push(x);  // O(1) selalu
    }

    public int pop() {
        transfer();
        return outbox.pop();
    }

    public int peek() {
        transfer();
        return outbox.peek();
    }

    public boolean empty() {
        return inbox.isEmpty() && outbox.isEmpty();
    }

    private void transfer() {
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty())
                outbox.push(inbox.pop());
        }
    }
}
```

**Kenapa lebih efisien?** Transfer hanya dilakukan saat `outbox` kosong — setiap elemen dipindah **paling banyak satu kali** sepanjang hidupnya → **amortized O(1)** per operasi.

| | Push-Expensive (kode) | Pop-Expensive (lazy) |
| -------- | --------------------- | ------------------------ |
| `push` | O(n) | O(1) |
| `pop` | O(1) | O(1) amortized |
| `peek` | O(1) | O(1) amortized |
| Transfer | Setiap push | Hanya saat outbox kosong |

______________________________________________________________________

## 🔧 Mengapa Dua Stack Bisa Membentuk Queue?

```
Stack = LIFO → urutan terbalik
Stack + Stack = urutan terbalik dua kali = urutan normal = FIFO ✅

push [1,2,3] ke stack1:  stack1 = [3,2,1] (top=1, terbalik)
pindah ke stack2:        stack2 = [1,2,3] (top=3, terbalik lagi = normal)
pop dari stack2:         3,2,1 → FIFO ✅
```

Dua kali pembalikan = urutan asli. Itulah matematis mengapa dua stack bisa mensimulasikan queue.

______________________________________________________________________

## 📌 Key Takeaway

Dua stack bisa membentuk queue karena **dua kali pembalikan menghasilkan urutan asli**. Kode ini menggunakan **push-expensive** (O(n) per push) yang memastikan top stack selalu elemen terdepan queue. Pendekatan yang lebih optimal adalah **lazy transfer** (pop-expensive) yang hanya mentransfer elemen saat dibutuhkan — menghasilkan amortized O(1) untuk semua operasi. 🎯

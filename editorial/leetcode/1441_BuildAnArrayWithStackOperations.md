# 1441. Build an Array with Stack Operations

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Stack, Simulation
- **Link**: [Problem](https://leetcode.com/problems/build-an-array-with-stack-operations/)
- **Solution**: [Code](../../leetcode/BuildAnArrayWithStackOperations.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `target` dan integer `n`. Kita membaca angka `1, 2, 3, ..., n` secara berurutan dan menggunakan operasi stack:

- **`"Push"`** → masukkan angka saat ini ke stack.
- **`"Pop"`** → keluarkan angka teratas dari stack.

Kembalikan sequence operasi minimum agar stack akhirnya berisi array `target`.

Contoh:

- `target = [1,3]`, `n = 3` → `["Push","Push","Pop","Push"]`
- `target = [1,2,3]`, `n = 3` → `["Push","Push","Push"]`
- `target = [1,2]`, `n = 4` → `["Push","Push"]`

______________________________________________________________________

## 💡 Intuition

Kita membaca angka `1` sampai `n` secara berurutan. Untuk setiap angka:

- Jika angka **ada di `target`** → `Push` saja (simpan di stack).
- Jika angka **tidak ada di `target`** → `Push` lalu `Pop` (masuk sebentar lalu dibuang).
- Setelah mencapai semua elemen `target`, **berhenti** — tidak perlu lanjut ke angka berikutnya.

Kode menggunakan pointer `curr` yang melacak angka saat ini (`1..n`), dan loop terhadap setiap elemen `target`. Untuk setiap target `i`, skip semua angka di bawah `i` dengan Push+Pop, lalu Push `i` sendiri.

______________________________________________________________________

## 🔍 Approach

### Simulasi dengan Pointer

1. Inisialisasi `curr = 1` (angka saat ini yang dibaca).
1. Loop setiap elemen `i` di `target`:
   - Selama `curr < i` → angka `curr` bukan bagian target → `Push + Pop`, `curr++`.
   - Saat `curr == i` → angka ini masuk target → `Push`, `curr++`.
1. Return `ans`.

> Loop berhenti otomatis setelah semua elemen `target` diproses — tidak perlu lanjut ke `n`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------------------- |
| **Time** | O(max(target)) — `curr` jalan sampai elemen target terbesar |
| **Space** | O(1) — hanya pointer `curr` (tidak termasuk output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `target = [1,3]`, `n = 3`

`curr = 1`

______________________________________________________________________

**i=1 (target[0]=1):**

- `curr=1 < 1`? ❌ → tidak ada Push+Pop
- Push `curr=1` → `ans=["Push"]`, `curr=2`

______________________________________________________________________

**i=3 (target[1]=3):**

- `curr=2 < 3`? ✅ → Push+Pop untuk angka 2 → `ans=["Push","Push","Pop"]`, `curr=3`
- `curr=3 < 3`? ❌ → stop while
- Push `curr=3` → `ans=["Push","Push","Pop","Push"]`, `curr=4`

**Output: `["Push","Push","Pop","Push"]` ✅**

Stack akhir: `[1, 3]` ✅

______________________________________________________________________

**Input:** `target = [1,2,3]`, `n = 3`

`curr = 1`

| i | while curr\<i? | Aksi | ans | curr |
| --- | ------------- | ------ | ------------------------ | ---- |
| 1 | 1\<1? ❌ | Push 1 | `["Push"]` | 2 |
| 2 | 2\<2? ❌ | Push 2 | `["Push","Push"]` | 3 |
| 3 | 3\<3? ❌ | Push 3 | `["Push","Push","Push"]` | 4 |

**Output: `["Push","Push","Push"]` ✅**

______________________________________________________________________

**Input:** `target = [2,3,4]`, `n = 4`

`curr = 1`

| i | while curr\<i? | Aksi | curr |
| --- | ------------- | -------------------------- | ---- |
| 2 | 1\<2? ✅ | Push+Pop (angka 1), curr=2 | 2 |
| | 2\<2? ❌ | Push (angka 2), curr=3 | 3 |
| 3 | 3\<3? ❌ | Push (angka 3), curr=4 | 4 |
| 4 | 4\<4? ❌ | Push (angka 4), curr=5 | 5 |

`ans = ["Push","Pop","Push","Push","Push"]`

**Output: `["Push","Pop","Push","Push","Push"]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `target = [1]` → hanya satu elemen, tidak ada skip → `["Push"]`
- [ ] `target = [n]` → semua angka 1..n-1 di-Push+Pop → panjang operasi `2*(n-1) + 1`
- [ ] `target` sudah berurutan `[1,2,...,n]` → tidak ada Pop sama sekali → hanya Push

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu Loop Sampai `n`?

```java
for (int i : target) {  // loop sampai target habis, bukan sampai n
    ...
}
```

Setelah semua elemen `target` sudah di-Push, kita berhenti. Angka-angka setelah `max(target)` tidak relevan karena stack sudah berisi `target` dengan benar — tidak perlu diproses.

Contoh: `target = [1,2]`, `n = 100` → cukup proses angka 1 dan 2, tidak perlu sampai 100.

______________________________________________________________________

## 🔧 Mengapa `curr` Tidak Di-reset?

```java
int curr = 1;
for (int i : target) {
    while (curr < i) { ... curr++; }
    ans.add("Push"); curr++;
}
```

`curr` tidak di-reset di setiap iterasi `for` karena angka dibaca **secara berurutan** — setelah kita baca angka `3`, kita tidak kembali ke angka `1` lagi. `curr` terus maju monotonically. Ini membuat total pergerakan `curr` hanya O(max(target)).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah simulasi sederhana yang terlihat lebih rumit dari yang sebenarnya. Kuncinya: untuk setiap angka yang **tidak ada di target**, lakukan Push+Pop (masuk sebentar lalu buang). Untuk angka yang **ada di target**, cukup Push. Loop terhadap `target` (bukan `1..n`) dan pointer `curr` yang tidak di-reset memastikan solusi O(max(target)) tanpa redundansi. 🎯

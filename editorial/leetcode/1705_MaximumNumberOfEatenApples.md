# 1705. Maximum Number of Eaten Apples

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Greedy, Heap (Priority Queue)
- **Link**: [Problem](https://leetcode.com/problems/maximum-number-of-eaten-apples/)
- **Solution**: [Code](../../leetcode/MaximumNumberOfEatenApples.java)

______________________________________________________________________

## 📄 Problem Summary

Setiap hari `i` (0-indexed), kamu mendapat `apples[i]` apel yang akan busuk setelah `days[i]` hari (busuk di hari `i + days[i]`). Makan maksimal **satu apel per hari**. Kembalikan jumlah maksimum apel yang bisa dimakan.

Strategi optimal: selalu makan apel yang **paling cepat busuk** terlebih dahulu (greedy).

Contoh:

- `apples = [1,2,3,5,2]`, `days = [3,2,1,4,2]` → `7`
- `apples = [3,0,0,0,0,2]`, `days = [3,0,0,0,0,2]` → `5`

______________________________________________________________________

## 💡 Intuition

Gunakan **Min Heap** berdasarkan tanggal kadaluarsa — apel yang paling cepat busuk diprioritaskan. Setiap hari:

1. Tambahkan apel hari ini ke heap (jika ada).
1. Buang apel yang sudah kadaluarsa dari top heap.
1. Makan satu apel dari yang paling cepat busuk (kurangi jumlahnya).

Loop berlanjut setelah hari ke-`n` habis selama masih ada apel di heap — kita bisa terus makan dari stok yang ada.

______________________________________________________________________

## 🔍 Approach

### Greedy + Min Heap (by expiry date)

Heap menyimpan `int[] {expiry_day, count}`:

- `expiry_day = day + days[day]` → hari ke berapa apel ini busuk
- `count` → sisa jumlah apel

**Setiap iterasi:**

1. Jika `day < n && apples[day] > 0` → push `{day + days[day], apples[day]}` ke heap.
1. Buang semua entry di top heap yang kadaluarsa (`expiry <= day`) atau habis (`count == 0`).
1. Jika heap tidak kosong → `heap.peek()[1]--`, `count++`.
1. `day++`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------ |
| **Time** | O(n log n) — setiap entry di-push dan di-pop tepat satu kali |
| **Space** | O(n) — heap menyimpan maksimal n entry |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `apples = [1,2,3,5,2]`, `days = [3,2,1,4,2]`

Format heap: `{expiry, count}`, diurutkan by expiry ascending.

______________________________________________________________________

**day=0:** push `{0+3=3, 1}` → heap=`[{3,1}]`

- tidak ada kadaluarsa
- makan dari `{3,1}` → `{3,0}` → count=1
- heap=`[{3,0}]`

**day=1:** push `{1+2=3, 2}` → heap=`[{3,0},{3,2}]`

- buang `{3,0}` (count==0) → heap=`[{3,2}]`
- makan dari `{3,2}` → `{3,1}` → count=2

**day=2:** push `{2+1=3, 3}` → heap=`[{3,1},{3,3}]`

- tidak ada kadaluarsa (expiry=3 > day=2)
- makan dari `{3,1}` → `{3,0}` → count=3

**day=3:** push `{3+4=7, 5}` → heap=`[{3,0},{3,3},{7,5}]`

- buang `{3,0}` (count==0)
- buang `{3,3}` (expiry=3 \<= day=3, kadaluarsa!)
- heap=`[{7,5}]`
- makan dari `{7,5}` → `{7,4}` → count=4

**day=4:** push `{4+2=6, 2}` → heap=`[{6,2},{7,4}]`

- tidak ada kadaluarsa
- makan dari `{6,2}` → `{6,1}` → count=5

______________________________________________________________________

**day=5 (melebihi n=5, tapi heap tidak kosong):**

- tidak push (day >= n)
- heap=`[{6,1},{7,4}]`
- makan dari `{6,1}` → `{6,0}` → count=6

**day=6:** heap=`[{6,0},{7,4}]`

- buang `{6,0}` (count==0)
- makan dari `{7,4}` → `{7,3}` → count=7

**day=7:** heap=`[{7,3}]`

- buang `{7,3}` (expiry=7 \<= day=7, kadaluarsa!)
- heap kosong → tidak makan

**day=8:** heap kosong → loop berakhir

**Output: `7` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `apples[i] = 0` → tidak push ke heap (sudah dicek `apples[day] > 0`)
- [ ] Apel kadaluarsa sebelum sempat dimakan → dibuang saat `expiry <= day`
- [ ] Masih ada apel setelah hari ke-`n` → loop berlanjut selama heap tidak kosong

______________________________________________________________________

## 🔧 Dua Kondisi Buang di Heap

```java
while (!pq.isEmpty() && (pq.peek()[0] <= day || pq.peek()[1] == 0))
    pq.poll();
```

**`pq.peek()[0] <= day`** → expiry hari ini atau sebelumnya → sudah kadaluarsa

**`pq.peek()[1] == 0`** → stok habis (sudah semua dimakan)

Keduanya perlu dicek karena:

- Kadaluarsa: tidak bisa dimakan lagi meskipun stoknya masih banyak
- Stok habis: sudah tidak ada yang bisa dimakan

______________________________________________________________________

## 🔧 Kenapa `expiry = day + days[day]`, Bukan `day + days[day] - 1`?

```java
pq.offer(new int[] { day + days[day], apples[day] });
```

Apel di hari `i` bertahan `days[i]` hari, busuk di hari `i + days[i]`. Kita set `expiry = i + days[i]` dan buang saat `expiry <= day` — artinya di hari `expiry`, apel sudah tidak bisa dimakan.

```
Apel di hari 0, days=3 → busuk di hari 3
Bisa dimakan di hari 0, 1, 2 (tidak termasuk hari 3)
expiry = 0+3 = 3, buang saat day >= 3 ✅
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi **Greedy + Min Heap** — selalu ambil yang paling mendesak (paling cepat kadaluarsa). Loop berlanjut melewati `n` hari untuk menghabiskan stok yang tersisa — ini sering terlupakan. Dua kondisi pembersihan heap (`kadaluarsa` dan `stok habis`) memastikan heap selalu berisi entry yang valid sebelum kita memutuskan untuk makan. 🎯

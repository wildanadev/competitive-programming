# 636. Exclusive Time of Functions

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Stack
- **Link**: [Problem](https://leetcode.com/problems/exclusive-time-of-functions/)
- **Solution**: [Code](../../leetcode/ExclusiveTimeOfFunctions.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `n` fungsi (id `0` sampai `n-1`) dan log eksekusi, hitung **exclusive time** setiap fungsi — waktu yang dihabiskan fungsi itu sendiri (tidak termasuk waktu fungsi yang dipanggil di dalamnya).

Setiap log berformat: `"id:start/end:timestamp"`.

Contoh:

```
logs = ["0:start:0","1:start:2","1:end:5","0:end:6"], n = 2
```

```
Timeline:
t=0: fungsi 0 mulai
t=2: fungsi 1 mulai (fungsi 0 jeda)
t=5: fungsi 1 selesai → exclusive time fungsi 1 = 5-2+1 = 4
t=6: fungsi 0 selesai → exclusive time fungsi 0 = (2-0) + (6-5) = 2+1 = 3 ... tapi pakai rumus: total 0 = 7 - 4 = 3
```

Output: `[3, 4]`

______________________________________________________________________

## 💡 Intuition

Fungsi bisa **bersarang** (nested) — fungsi 0 memanggil fungsi 1. Ini adalah pola **LIFO** (Last In, First Out) → cocok untuk **stack**.

Saat fungsi baru mulai (`start`), push ke stack. Saat fungsi selesai (`end`), pop dari stack dan hitung waktu eksekusinya.

**Masalah**: waktu fungsi luar harus dikurangi oleh waktu fungsi dalam yang berjalan di dalamnya. Solusinya: saat fungsi dalam selesai, kurangi waktu yang sama dari fungsi luar yang ada di top stack.

______________________________________________________________________

## 🔍 Approach

### Stack Simulation

**Saat `start`:**

- Push log ke stack.

**Saat `end`:**

- Pop `top` dari stack.
- Hitung durasi: `duration = log.time - top.time + 1` (+1 karena inklusif).
- `result[top.id] += duration`.
- Jika stack tidak kosong → fungsi yang ada di bawah sudah "menunggu" selama `duration` → `result[stack.peek().id] -= duration`.

**Helper class `Log`:**

```java
id       = strs[0]         // id fungsi
isStart  = strs[1]=="start"// true/false
time     = strs[2]         // timestamp
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------- |
| **Time** | O(n) — satu pass semua log |
| **Space** | O(n) — stack menyimpan fungsi yang sedang berjalan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n=2`, `logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]`

```
Timeline visual:
t: 0  1  2  3  4  5  6
   [0][0][1][1][1][1][0]
   fungsi 0 jalan di t=0,1 dan t=6
   fungsi 1 jalan di t=2,3,4,5
```

| log | Aksi | stack | result |
| ----------- | ----------------------------------- | ------- | ---------- |
| `0:start:0` | push Log(id=0, t=0) | `[0]` | `[0,0]` |
| `1:start:2` | push Log(id=1, t=2) | `[0,1]` | `[0,0]` |
| `1:end:5` | pop top=Log(1,t=2), dur=5-2+1=**4** | `[0]` | `[0,4]` |
| | stack tidak kosong → result[0] -= 4 | `[0]` | `[-4,4]` |
| `0:end:6` | pop top=Log(0,t=0), dur=6-0+1=**7** | `[]` | `[-4+7,4]` |
| | stack kosong → tidak kurangi | `[]` | `[3,4]` |

**Output: `[3,4]` ✅**

______________________________________________________________________

**Input:** `n=1`, `logs = ["0:start:0","0:start:2","0:end:5","0:end:6"]`

Fungsi rekursif — fungsi 0 memanggil dirinya sendiri!

| log | Aksi | stack | result |
| ----------- | --------------------------------- | ------- | ------ |
| `0:start:0` | push (id=0, t=0) | `[0]` | `[0]` |
| `0:start:2` | push (id=0, t=2) | `[0,0]` | `[0]` |
| `0:end:5` | pop top=(0,t=2), dur=5-2+1=4 | `[0]` | `[4]` |
| | stack tidak kosong → result[0]-=4 | `[0]` | `[0]` |
| `0:end:6` | pop top=(0,t=0), dur=6-0+1=7 | `[]` | `[7]` |
| | stack kosong → tidak kurangi | `[]` | `[7]` |

**Output: `[7]` ✅** — total waktu = 7 (t=0 sampai t=6 inklusif)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Fungsi rekursif (memanggil dirinya sendiri) → stack bisa punya banyak frame dengan id sama → tetap benar karena setiap frame punya timestamp sendiri
- [ ] Hanya satu fungsi → stack tidak pernah punya dua elemen → tidak ada pengurangan
- [ ] Fungsi nested dalam → setiap level memotong waktu dari level di atasnya

______________________________________________________________________

## 🔧 Kenapa `result[stack.peek().id] -= duration`?

Ini adalah inti dari solusi yang sering membingungkan. Mari kita pahami:

```
Timeline: [0][0][1][1][1][1][0]
                ^-----------^
                fungsi 1 berjalan selama ini
                (fungsi 0 "tertahan" / tidak berjalan)
```

Saat kita hitung `result[0] += 6-0+1 = 7`, ini menghitung **seluruh rentang** dari start sampai end fungsi 0, termasuk waktu fungsi 1 berjalan di dalamnya. Kita perlu **memotong** waktu fungsi 1 dari hasil fungsi 0:

```
result[0] = 7 (total rentang) - 4 (waktu fungsi 1) = 3 ✅
```

Pengurangan dilakukan saat fungsi dalam (`1`) selesai, bukan saat fungsi luar (`0`) selesai — karena di situlah kita tahu durasi fungsi dalam.

______________________________________________________________________

## 🔧 Kenapa `log.time - top.time + 1`?

Waktu bersifat **inklusif** di kedua ujungnya:

```
start=2, end=5:
t=2 ✅ (fungsi berjalan di t=2)
t=3 ✅
t=4 ✅
t=5 ✅ (fungsi berjalan di t=5)
total = 5-2+1 = 4 unit waktu
```

Tanpa `+1`, kita melewatkan satu unit waktu di timestamp akhir.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah simulasi **call stack** yang sesungguhnya — seperti cara CPU melacak fungsi yang berjalan. Stack dipakai karena fungsi bersifat LIFO (yang terakhir dipanggil, yang pertama selesai). Trik utamanya: saat fungsi dalam selesai, **kurangi durasinya dari fungsi luar** yang sedang menunggu di top stack — ini memastikan fungsi luar hanya menghitung waktu yang benar-benar dihabiskannya sendiri. 🎯

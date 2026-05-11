# 941. Valid Mountain Array

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/valid-mountain-array/)
- **Solution**: [Code](../../leetcode/ValidMountainArray.java)

______________________________________________________________________

## 📄 Problem Summary

Array `A` disebut **valid mountain array** jika:

1. Panjang `A` minimal `3`.
1. Ada indeks puncak `i` dengan `0 < i < n-1` (bukan di ujung).
1. `A[0] < A[1] < ... < A[i]` (naik ke puncak).
1. `A[i] > A[i+1] > ... > A[n-1]` (turun dari puncak).

Contoh:

- `A = [0,3,2,1]` → `true`
- `A = [3,5,5]` → `false` (ada nilai sama)
- `A = [0,1,2,3,4,5,6,7,8,9]` → `false` (hanya naik, tidak turun)

______________________________________________________________________

## 💡 Intuition

Gunakan **dua pointer** dari kedua ujung — `i` dari kiri naik ke puncak, `j` dari kanan naik ke puncak. Jika keduanya bertemu di indeks yang **sama** dan **bukan di ujung array**, maka array adalah valid mountain.

```
[0, 3, 2, 1]
 i →         ← j
       ↑
    bertemu di indeks 1 (puncak) ✅
```

______________________________________________________________________

## 🔍 Approach

### Two Pointers — Climb from Both Ends

1. `i` mulai dari `0`, maju selama `A[i] < A[i+1]` (naik ke kanan).
1. `j` mulai dari `n-1`, mundur selama `A[j] < A[j-1]` (naik ke kiri).
1. Valid jika **ketiganya** terpenuhi:
   - `i != 0` → `i` tidak stuck di awal (ada kenaikan dari kiri)
   - `j != n-1` → `j` tidak stuck di akhir (ada penurunan ke kanan)
   - `i == j` → keduanya bertemu di puncak yang sama

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------- |
| **Time** | O(n) — dua pointer bergerak linear |
| **Space** | O(1) — hanya dua variabel pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `A = [0,3,2,1]`, `n=4`

**Pointer `i` dari kiri:**

```
i=0: A[0]=0 < A[1]=3 ✅ → i=1
i=1: A[1]=3 < A[2]=2 ❌ → stop
i = 1
```

**Pointer `j` dari kanan:**

```
j=3: A[3]=1 < A[2]=2 ✅ → j=2
j=2: A[2]=2 < A[1]=3 ✅ → j=1
j=1: A[1]=3 < A[0]=0 ❌ → stop
j = 1
```

**Cek kondisi:**

- `i != 0` → `1 != 0` ✅
- `j != n-1` → `1 != 3` ✅
- `i == j` → `1 == 1` ✅

**Output: `true` ✅**

______________________________________________________________________

**Input:** `A = [0,1,2,3,4,5,6,7,8,9]`, `n=10`

**Pointer `i`:** terus naik sampai `i=9` (ujung array)

**Pointer `j`:** `A[9]=9 < A[8]=8`? ❌ → langsung stop, `j=9`

**Cek:**

- `i != 0` → `9 != 0` ✅
- `j != n-1` → `9 != 9` ❌ → **false**

**Output: `false` ✅** — hanya naik, tidak ada penurunan

______________________________________________________________________

**Input:** `A = [3,5,5]`, `n=3`

**Pointer `i`:**

```
i=0: A[0]=3 < A[1]=5 ✅ → i=1
i=1: A[1]=5 < A[2]=5 ❌ → stop (sama, bukan naik strict)
i = 1
```

**Pointer `j`:**

```
j=2: A[2]=5 < A[1]=5 ❌ → stop (sama, bukan naik strict)
j = 2
```

**Cek:**

- `i == j` → `1 == 2` ❌ → **false**

**Output: `false` ✅** — ada nilai sama di puncak

______________________________________________________________________

**Input:** `A = [1,2,1]`, `n=3`

**Pointer `i`:** `i=0→1`, stop di `i=1`

**Pointer `j`:** `j=2→1`, stop di `j=1`

**Cek:**

- `i != 0` ✅, `j != 2` ✅, `i == j` ✅

**Output: `true` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array panjang < 3 → `i` tidak bisa mencapai puncak → `false`
- [ ] Array terurut ascending → `j` tidak bergerak → `j == n-1` → `false`
- [ ] Array terurut descending → `i` tidak bergerak → `i == 0` → `false`
- [ ] Puncak di ujung kiri `[3,2,1]` → `i=0` → `i != 0` gagal → `false`
- [ ] Puncak di ujung kanan `[1,2,3]` → `j=n-1` → `j != n-1` gagal → `false`

______________________________________________________________________

## 🔧 Kenapa Tiga Kondisi Harus Semuanya Terpenuhi?

```java
return i != 0 && j != n-1 && i == j;
```

| Kondisi | Gagal berarti |
| ---------- | -------------------------------------------------------------------------------------------------------------- |
| `i != 0` | Array langsung turun dari awal → tidak ada kenaikan sama sekali |
| `j != n-1` | Array terus naik sampai akhir → tidak ada penurunan sama sekali |
| `i == j` | Dua pointer tidak bertemu di titik yang sama → ada "dataran" di puncak (nilai sama) atau bentuk bukan mountain |

Ketiga kondisi harus benar sekaligus karena masing-masing menangkap kasus invalid yang berbeda.

______________________________________________________________________

## 📌 Key Takeaway

Pola **"climb from both ends and meet at peak"** adalah cara elegan untuk memvalidasi struktur mountain. Daripada mencari puncak dulu lalu validasi naik/turun secara terpisah, dua pointer dari kedua arah menyederhanakan logika menjadi tiga kondisi sederhana. Kondisi `i != 0` dan `j != n-1` memastikan puncak tidak di ujung array — detail kecil yang sering terlewat. 🎯

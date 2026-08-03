# 605. Can Place Flowers

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Greedy
- **Link**: [Problem](https://leetcode.com/problems/can-place-flowers/)
- **Solution**: [Code](../../leetcode/CanPlaceFlowers.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `flowerbed` berisi `0` (kosong) dan `1` (ada bunga). Bunga tidak boleh ditanam di **slot bersebelahan**. Diberikan `n` bunga baru, return `true` jika bisa ditanam semua tanpa melanggar aturan.

Contoh:

- `flowerbed = [1,0,0,0,1]`, `n = 1` → `true`
- `flowerbed = [1,0,0,0,1]`, `n = 2` → `false`

______________________________________________________________________

## 💡 Intuition

**Greedy**: scan dari kiri ke kanan, tanam bunga sesegera mungkin. Jika slot kosong (`0`) dan kedua tetangganya juga kosong (atau tidak ada), tanam di sini dan kurangi `n`. Modifikasi `flowerbed[i] = 1` mencegah slot berikutnya dari menganggap slot ini kosong.

Greedy bekerja di sini karena menanam lebih awal tidak pernah memperburuk peluang — slot yang ditanam lebih awal tidak menghalangi pilihan optimal di kanan (karena kita tidak akan menanam di slot yang bersebelahan bagaimanapun).

______________________________________________________________________

## 🔍 Approach

### Greedy — Tanam Sesegera Mungkin

1. Jika `n == 0` → return `true` langsung.
1. Loop setiap slot `i`:
   - Jika `flowerbed[i] == 0` **DAN** tetangga kiri kosong/tidak ada **DAN** tetangga kanan kosong/tidak ada:
     - Tanam: `flowerbed[i] = 1`, `n--`.
     - Jika `n == 0` → return `true` (early exit).
1. Return `false`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------- |
| **Time** | O(n) — satu pass linear |
| **Space** | O(1) — modifikasi in-place, tidak ada struktur tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `flowerbed = [1,0,0,0,1]`, `n = 1`

| i | flowerbed[i] | kiri kosong? | kanan kosong? | Tanam? | n | flowerbed |
| --- | ------------ | ------------ | ------------- | ------------------- | --- | ----------- |
| 0 | 1 | — | — | ❌ (bukan 0) | 1 | [1,0,0,0,1] |
| 1 | 0 | [0]=1 ❌ | — | ❌ (kiri ada bunga) | 1 | [1,0,0,0,1] |
| 2 | 0 | [1]=0 ✅ | [3]=0 ✅ | ✅ → n=0 | 0 | [1,0,1,0,1] |

`n == 0` → **return true**

**Output: `true` ✅**

______________________________________________________________________

**Input:** `flowerbed = [1,0,0,0,1]`, `n = 2`

| i | flowerbed[i] | kiri? | kanan? | Tanam? | n | flowerbed |
| --- | ------------ | -------- | -------- | ------ | --- | ----------- |
| 0 | 1 | — | — | ❌ | 2 | [1,0,0,0,1] |
| 1 | 0 | [0]=1 ❌ | — | ❌ | 2 | [1,0,0,0,1] |
| 2 | 0 | [1]=0 ✅ | [3]=0 ✅ | ✅ | 1 | [1,0,1,0,1] |
| 3 | 0 | [2]=1 ❌ | — | ❌ | 1 | [1,0,1,0,1] |
| 4 | 1 | — | — | ❌ | 1 | [1,0,1,0,1] |

Loop selesai, `n=1 > 0` → **return false**

**Output: `false` ✅**

______________________________________________________________________

**Input:** `flowerbed = [0,0,0,0,0]`, `n = 3`

| i | Tanam? | n | flowerbed |
| --- | ------------------------------------- | --------------- | ----------- |
| 0 | ✅ (i=0, kiri tidak ada, kanan [1]=0) | 2 | [1,0,0,0,0] |
| 1 | ❌ (kiri [0]=1) | 2 | [1,0,0,0,0] |
| 2 | ✅ (kiri [1]=0, kanan [3]=0) | 1 | [1,0,1,0,0] |
| 3 | ❌ (kiri [2]=1) | 1 | [1,0,1,0,0] |
| 4 | ✅ (kiri [3]=0, kanan tidak ada) | 0 → return true | [1,0,1,0,1] |

**Output: `true` ✅** (3 bunga bisa ditanam)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 0` → selalu `true` (tidak perlu tanam apapun)
- [ ] `flowerbed` satu slot `[0]`, `n=1` → `i=0`, kiri dan kanan tidak ada → tanam → `true`
- [ ] `flowerbed` sudah penuh `[1,0,1]`, `n=1` → tidak ada slot yang memenuhi syarat → `false`

______________________________________________________________________

## 🔧 Kenapa Modifikasi `flowerbed[i] = 1`?

```java
flowerbed[i] = 1;
```

Ini mencegah slot `i+1` dari berpikir bahwa slot `i` masih kosong. Tanpa modifikasi ini:

```
flowerbed = [0,0,0], n=2

i=0: kiri tidak ada ✅, kanan [1]=0 ✅ → tanam
i=1: kiri [0]=0 ✅ (belum dimodifikasi!), kanan [2]=0 ✅ → tanam LAGI ❌
     (seharusnya [0,1,0,1] bukan [0,1,1] — dua bunga bersebelahan!)
```

Dengan modifikasi, `flowerbed[0]` menjadi `1` setelah ditanam → `i=1` tidak bisa tanam karena kirinya sudah ada bunga → benar.

______________________________________________________________________

## 🔧 Kenapa Greedy Optimal?

Menanam lebih awal (paling kiri yang memungkinkan) selalu optimal karena:

- Menanam di slot `i` menghalangi slot `i-1` (sudah lewat) dan `i+1` (tidak bisa tanam).
- Menunda ke slot `i+2` atau lebih kanan tidak memberikan keuntungan — slot yang sama akan memblokir slot yang sama.
- Menanam lebih awal memberi kesempatan lebih banyak di sisi kanan.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **greedy klasik** — scan kiri ke kanan, tanam sesegera mungkin. Kunci implementasi adalah modifikasi `flowerbed[i] = 1` agar slot yang baru ditanam dianggap "terisi" saat mengecek slot berikutnya. Early exit saat `n == 0` mengoptimalkan kasus di mana bunga sudah cukup sebelum akhir array. 🎯

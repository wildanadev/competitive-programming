# 575. Distribute Candies

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/distribute-candies/)
- **Solution**: [Code](../../leetcode/DistributeCandies.java)

______________________________________________________________________

## 📄 Problem Summary

Alice mendapat `n` permen (n selalu genap). Dia membagi permen sama rata dengan kakaknya (masing-masing `n/2` permen). Alice ingin memaksimalkan **jumlah tipe permen unik** yang dia dapatkan. Return jumlah tipe unik maksimum yang bisa Alice miliki.

Contoh:

- `candyType = [1,1,2,2,3,3]` → `3` (Alice bisa dapat tipe 1,2,3)
- `candyType = [1,1,2,3]` → `2` (Alice dapat 2 permen, bisa tipe 1,2 atau 1,3 atau 2,3)
- `candyType = [6,6,6,6]` → `1` (hanya satu tipe, apapun yang dipilih Alice)

______________________________________________________________________

## 💡 Intuition

Jawaban = `min(jumlah tipe unik, n/2)`:

- Alice bisa dapat maksimal `n/2` permen.
- Alice bisa dapat maksimal `distinctTypes` tipe berbeda.
- Jika `distinctTypes >= n/2` → Alice bisa pilih `n/2` tipe berbeda → jawaban = `n/2`.
- Jika `distinctTypes < n/2` → Alice ambil semua tipe, sisanya duplikat → jawaban = `distinctTypes`.

**Optimasi kode:** Early exit — begitu jumlah tipe unik mencapai `n/2`, langsung return karena tidak mungkin lebih besar.

______________________________________________________________________

## 🔍 Approach

### HashSet + Early Exit

1. Buat `HashSet` untuk track tipe unik.
1. Loop setiap permen:
   - Tambahkan ke set.
   - Jika `set.size() >= n/2` → **return `n/2` langsung** (sudah cukup tipe berbeda).
1. Jika loop selesai → return `candy.size()` (tipe unik < n/2).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------- |
| **Time** | O(n) worst case, tapi early exit sering membuatnya lebih cepat |
| **Space** | O(k) — k = jumlah tipe unik |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `candyType = [1,1,2,2,3,3]`, `n/2 = 3`

| i | candy[i] | set setelah | set.size() >= 3? | Aksi |
| --- | -------- | ----------- | ---------------- | ------------ |
| 0 | 1 | {1} | 1>=3? ❌ | lanjut |
| 1 | 1 | {1} | 1>=3? ❌ | lanjut |
| 2 | 2 | {1,2} | 2>=3? ❌ | lanjut |
| 3 | 2 | {1,2} | 2>=3? ❌ | lanjut |
| 4 | 3 | {1,2,3} | 3>=3? ✅ | **return 3** |

**Output: `3` ✅**

______________________________________________________________________

**Input:** `candyType = [1,1,2,3]`, `n/2 = 2`

| i | candy[i] | set | size>=2? | Aksi |
| --- | -------- | ----- | -------- | ------------ |
| 0 | 1 | {1} | ❌ | lanjut |
| 1 | 1 | {1} | ❌ | lanjut |
| 2 | 2 | {1,2} | 2>=2? ✅ | **return 2** |

**Output: `2` ✅**

______________________________________________________________________

**Input:** `candyType = [6,6,6,6]`, `n/2 = 2`

| i | candy[i] | set | size>=2? |
| --- | -------- | --- | -------- |
| 0 | 6 | {6} | ❌ |
| 1 | 6 | {6} | ❌ |
| 2 | 6 | {6} | ❌ |
| 3 | 6 | {6} | ❌ |

Loop selesai → return `candy.size() = 1`

**Output: `1` ✅** (hanya satu tipe, tipe unik < n/2)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua tipe sama → return `1` (tipe unik < n/2)
- [ ] Semua tipe berbeda → set mencapai `n/2` dengan cepat → early return
- [ ] `n = 2` → `n/2 = 1`, satu permen untuk Alice → return `1` selalu

______________________________________________________________________

## 🔧 Kenapa Early Exit Optimal?

```java
if (candyType.length / 2 <= candy.size())
    return candyType.length / 2;
```

Begitu `set.size() >= n/2`, jawaban pasti `n/2` — tidak mungkin lebih besar. Loop sisanya tidak akan mengubah jawaban. Early exit menghemat iterasi di best case (tipe permen beragam di awal array).

```
[1,2,3,4,1,1,1,1], n/2=4
Tanpa early exit: loop 8 kali
Dengan early exit: loop 4 kali (berhenti di i=3) → 50% lebih cepat
```

______________________________________________________________________

## 🔧 Simplifikasi: Tanpa Early Exit

```java
class Solution {
    public int distributeCandies(int[] candyType) {
        Set<Integer> unique = new HashSet<>();
        for (int c : candyType) unique.add(c);
        return Math.min(unique.size(), candyType.length / 2);
    }
}
```

Lebih mudah dibaca — `min(distinct, n/2)` secara eksplisit. Kode asli memiliki early exit sebagai optimasi praktis.

______________________________________________________________________

## 📌 Key Takeaway

Jawaban adalah `min(distinct_types, n/2)` — Alice dibatasi oleh jumlah permen yang dia dapat (`n/2`) dan jumlah tipe yang tersedia. Early exit saat `set.size() >= n/2` adalah optimasi cerdas: begitu sudah tahu jawabannya `n/2`, tidak perlu proses sisa array. 🎯

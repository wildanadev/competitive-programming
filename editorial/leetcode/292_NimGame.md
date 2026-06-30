# 292. Nim Game

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, Brainteaser, Game Theory, Dynamic Programming
- **Link**: [Problem](https://leetcode.com/problems/nim-game/)
- **Solution**: [Code](../../leetcode/NimGame.java)

______________________________________________________________________

## 📄 Problem Summary

Ada `n` batu di tumpukan. Kamu dan teman bergantian mengambil `1`, `2`, atau `3` batu. Yang mengambil batu **terakhir** menang. Kamu mulai duluan. Asumsikan kamu dan teman bermain optimal. Return `true` jika kamu bisa menang.

Contoh:

- `n = 4` → `false`
- `n = 1` → `true`
- `n = 2` → `true`

______________________________________________________________________

## 💡 Intuition

Mari analisis pola kemenangan dari nilai `n` kecil:

```
n=1: ambil 1, sisa 0 → menang
n=2: ambil 2, sisa 0 → menang
n=3: ambil 3, sisa 0 → menang
n=4: apapun yang diambil (1,2,3), lawan bisa habiskan sisanya → KALAH
     ambil 1 → sisa 3 → lawan ambil 3 → menang
     ambil 2 → sisa 2 → lawan ambil 2 → menang
     ambil 3 → sisa 1 → lawan ambil 1 → menang
n=5: ambil 1 → sisa 4 (posisi KALAH untuk lawan!) → MENANG
n=6: ambil 2 → sisa 4 → MENANG
n=7: ambil 3 → sisa 4 → MENANG
n=8: apapun diambil, sisa 5,6,7 (semua posisi MENANG untuk lawan) → KALAH
```

**Pola:** kalah hanya ketika `n` adalah **kelipatan 4**. Karena rentang pengambilan (1-3) selalu bisa "menetralkan" sisa apapun **kecuali** kelipatan 4.

______________________________________________________________________

## 🔍 Approach

### Math — Modulo 4

1. Return `n % 4 != 0`.

Jika `n` bukan kelipatan 4 → kamu menang (selalu bisa membuat sisa menjadi kelipatan 4 untuk lawan).
Jika `n` kelipatan 4 → kamu kalah (apapun yang kamu ambil, lawan bisa membuat sisa kelipatan 4 lagi untukmu).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------- |
| **Time** | O(1) — hanya operasi modulo |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 4`

```
4 % 4 = 0 → false
```

**Output: `false` ✅**

______________________________________________________________________

**Input:** `n = 5`

```
5 % 4 = 1 ≠ 0 → true
```

**Output: `true` ✅**

**Strategi menang:** ambil `1` batu → sisa `4` (posisi kalah untuk lawan).

______________________________________________________________________

**Input:** `n = 8`

```
8 % 4 = 0 → false
```

**Output: `false` ✅**

**Pembuktian:** apapun yang diambil (1,2,3), sisa menjadi `7,6,5` — semuanya bukan kelipatan 4, artinya lawan berada di posisi menang.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `n = 0` (di luar constraint biasa `n >= 1`) → tidak relevan
- [ ] `n` kelipatan 4 besar (misal `n=400`) → tetap `false`
- [ ] `n = 1,2,3` → selalu `true` (langsung habiskan semua batu)

______________________________________________________________________

## 🔧 Pembuktian Lengkap dengan Induksi

**Basis:** `n=1,2,3` adalah posisi menang (langsung ambil semua).

**Langkah induktif:** Asumsikan kelipatan 4 (`4,8,12,...`) adalah posisi **kalah**. Buktikan:

**Posisi kelipatan 4 (`n=4k`) adalah KALAH:**

```
Pemain saat ini ambil x ∈ {1,2,3}
Sisa = 4k - x, di mana x ∈ {1,2,3}
Sisa ini BUKAN kelipatan 4 (karena 4k-1, 4k-2, 4k-3 tidak habis dibagi 4)
→ Lawan berada di posisi MENANG (karena bukan kelipatan 4)
→ Pemain saat ini KALAH
```

**Posisi bukan kelipatan 4 adalah MENANG:**

```
n = 4k + r, di mana r ∈ {1,2,3}
Pemain saat ini ambil r batu → sisa = 4k (kelipatan 4!)
→ Lawan berada di posisi KALAH
→ Pemain saat ini MENANG
```

Pembuktian ini konsisten untuk semua `n` — kelipatan 4 selalu kalah, selainnya selalu menang.

______________________________________________________________________

## 🔧 Hubungan dengan Dynamic Programming

Soal ini bisa diselesaikan dengan DP, tapi insight matematika membuatnya O(1):

```java
// DP — O(n) time, O(n) space
boolean[] dp = new boolean[n+1];
dp[0] = false;  // tidak ada batu = kalah (tidak bisa ambil)
for (int i = 1; i <= n; i++) {
    dp[i] = !dp[i-1] || !dp[i-2] || !dp[i-3];
    // menang jika ADA pilihan yang membuat lawan di posisi kalah
}
return dp[n];

// Math — O(1) (kode ini)
return n % 4 != 0;
```

Menjalankan DP untuk beberapa nilai awal akan **mengungkap pola** `n % 4 != 0` yang kemudian bisa langsung dipakai tanpa perlu DP sama sekali.

______________________________________________________________________

## 📌 Key Takeaway

Nim Game adalah contoh klasik **game theory** di mana insight matematis (pola kelipatan 4) menggantikan solusi DP yang lebih lambat. Strategi optimal: selalu sisakan kelipatan 4 untuk lawan. Pendekatan untuk soal game theory semacam ini: coba dulu nilai kecil dengan DP/brute force untuk **menemukan pola**, baru buktikan secara matematis (induksi) bahwa pola tersebut berlaku untuk semua `n`. 🎯

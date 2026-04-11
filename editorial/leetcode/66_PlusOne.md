# 66. Plus One

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Math
- **Link**: [Problem](https://leetcode.com/problems/plus-one/)
- **Solution**: [Code](../../leetcode/PlusOne.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `digits` yang merepresentasikan sebuah bilangan bulat besar, tambahkan 1 ke bilangan tersebut dan kembalikan hasilnya sebagai array.

Contoh:

- `digits = [1,2,3]` → `[1,2,4]`
- `digits = [4,3,2,1]` → `[4,3,2,2]`
- `digits = [9]` → `[1,0]`
- `digits = [9,9,9]` → `[1,0,0,0]`

______________________________________________________________________

## 💡 Intuition

Ada dua kasus utama:

1. **Semua digit adalah 9** → hasil pasti `1` diikuti semua `0`, ukuran array bertambah 1
1. **Tidak semua digit 9** → tambah 1 dari belakang, kalau carry (`digit+1==10`) set ke `0` dan lanjut ke kiri

______________________________________________________________________

## 🔍 Approach

1. Cek apakah **semua digit 9** dengan flag `f`:
   - Kalau iya → return array baru ukuran `n+1` dengan `ans[0] = 1`
1. Kalau tidak semua 9 → loop dari belakang:
   - Kalau `digits[i] + 1 == 10` → set `digits[i] = 0`, lanjut ke kiri (carry)
   - Kalau tidak → tambah 1 dan `break`
1. Return `digits`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------- |
| **Time** | O(n) — dua kali loop linear |
| **Space** | O(1) — in-place kecuali kasus semua 9 |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `digits = [1, 2, 3]`

**Step 1 — Cek semua 9?**

```
1 != 9 → f = false, break
```

**Step 2 — Loop dari belakang:**

| i | digits[i] | +1 == 10? | Aksi | digits |
| --- | --------- | --------- | ------------------ | ------- |
| 2 | 3 | ❌ | digits[2]=4, break | [1,2,4] |

**return `[1,2,4]` ✅**

______________________________________________________________________

**Input:** `digits = [1, 9, 9]`

**Step 1 — Cek semua 9?**

```
1 != 9 → f = false, break
```

**Step 2 — Loop dari belakang:**

| i | digits[i] | +1 == 10? | Aksi | digits |
| --- | --------- | --------- | ------------------ | ------- |
| 2 | 9 | ✅ | digits[2]=0 | [1,9,0] |
| 1 | 9 | ✅ | digits[1]=0 | [1,0,0] |
| 0 | 1 | ❌ | digits[0]=2, break | [2,0,0] |

**return `[2,0,0]` ✅**

______________________________________________________________________

**Input:** `digits = [9, 9, 9]`

**Step 1 — Cek semua 9?**

```
9==9, 9==9, 9==9 → f = true
```

**Step 2 — Semua 9:**

```
ans = new int[4] → [0,0,0,0]
ans[0] = 1       → [1,0,0,0]
```

**return `[1,0,0,0]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua digit `9` → array baru ukuran `n+1`
- [ ] Hanya satu digit `9` → `[1,0]`
- [ ] Tidak ada carry sama sekali → tambah digit terakhir saja

______________________________________________________________________

## 📌 Key Takeaway

Kasus **semua digit 9** perlu ditangani secara khusus karena butuh array yang lebih besar. Pendekatan ini menggunakan flag `f` untuk deteksi awal sebelum masuk loop utama. Alternatifnya bisa tanpa flag — langsung loop dari belakang dan kalau loop selesai tanpa `break` berarti semua digit jadi `0`, tinggal buat array baru. 🎯

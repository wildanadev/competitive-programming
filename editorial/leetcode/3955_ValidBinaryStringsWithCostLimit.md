# 3955. Valid Binary Strings With Cost Limit

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: String, Backtracking, Recursion
- **Link**: [Problem](https://leetcode.com/problems/valid-binary-strings-with-cost-limit/)
- **Solution**: [Code](../../leetcode/ValidBinaryStringsWithCostLimit.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua integer `n` dan `k`. **Cost** dari binary string adalah jumlah semua **indeks** (0-based) di mana karakter-nya `'1'`.

String binary disebut **valid** jika:

1. Tidak mengandung dua `'1'` berurutan.
1. Cost-nya `<= k`.

Kembalikan semua binary string valid berukuran `n`.

Contoh:

- `n=3, k=1` → `["000","010","100"]`
- `n=1, k=0` → `["0","1"]`

______________________________________________________________________

## 💡 Intuition

Gunakan **backtracking** — bangun string karakter per karakter. Di setiap posisi `i`, coba tambahkan `'0'` atau `'1'`. Prune (potong) cabang rekursi jika:

- `cost > k` (sudah melebihi batas)
- `'1'` akan ditempatkan setelah `'1'` sebelumnya (dua `'1'` berurutan)

Karena constraint `n <= 12`, jumlah string yang mungkin sangat terbatas → backtracking aman.

______________________________________________________________________

## 🔍 Approach

### Backtracking dengan Pruning

**Base cases:**

- `cost > k` → prune, return
- `i == n` → string selesai → tambahkan ke `ans`

**Rekursi:**

- Selalu coba tambah `'0'` (tidak ada constraint)
- Coba tambah `'1'` hanya jika:
  - `i == 0` (posisi pertama, tidak ada karakter sebelumnya), ATAU
  - Karakter terakhir di string bukan `'1'` (tidak dua `'1'` berurutan)
  - Jika `'1'` ditambah, `cost += i` (cost bertambah sebesar indeks saat ini)

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------- |
| **Time** | O(2^n) worst case — tapi pruning memotong banyak cabang |
| **Space** | O(n) — kedalaman rekursi maksimal `n` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n=3, k=1`

Pohon rekursi (pruned):

```
solve(i=0, cost=0, s="")
├── '0': solve(i=1, cost=0, s="0")
│   ├── '0': solve(i=2, cost=0, s="00")
│   │   ├── '0': solve(i=3, cost=0, s="000") → ADD "000" ✅
│   │   └── '1': cost=0+2=2 > k=1 → PRUNE ❌
│   └── '1': solve(i=2, cost=0+1=1, s="01")
│       ├── '0': solve(i=3, cost=1, s="010") → ADD "010" ✅
│       └── '1': consecutive '1' → PRUNE ❌
└── '1': solve(i=1, cost=0+0=0, s="1")  ← cost += i=0
    ├── '0': solve(i=2, cost=0, s="10")
    │   ├── '0': solve(i=3, cost=0, s="100") → ADD "100" ✅
    │   └── '1': cost=0+2=2 > k=1 → PRUNE ❌
    └── '1': consecutive '1' → PRUNE ❌
```

**Output: `["000","010","100"]` ✅**

______________________________________________________________________

**Input:** `n=1, k=0`

```
solve(i=0, cost=0, s="")
├── '0': solve(i=1, cost=0, s="0") → ADD "0" ✅
└── '1': cost=0+0=0 <= 0 ✅, solve(i=1, cost=0, s="1") → ADD "1" ✅
```

**Output: `["0","1"]` ✅** — cost `'1'` di index 0 = `0×1 = 0`

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k=0` → hanya string tanpa `'1'` di indeks > 0 yang valid (`'1'` di indeks 0 cost=0 valid, `'1'` di indeks 1+ tidak)
- [ ] `n=1` → hanya `"0"` dan `"1"` (cost `'1'` di index 0 = 0)
- [ ] `k` sangat besar → semua string tanpa dua `'1'` berurutan valid

______________________________________________________________________

## 🔧 Penjelasan Detail: Kondisi untuk Tambah `'1'`

```java
if (i == 0 || s.charAt(s.length() - 1) != '1')
    solve(i + 1, n, k, cost + i, s + "1", ans);
```

**`i == 0`**: posisi pertama, tidak ada karakter sebelumnya → aman tambah `'1'`

**`s.charAt(s.length() - 1) != '1'`**: karakter terakhir bukan `'1'` → tidak terjadi dua `'1'` berurutan

Jika salah satu kondisi terpenuhi → boleh tambah `'1'` dengan `cost + i`.

______________________________________________________________________

**Mengapa `cost + i`, bukan `cost + 1`?**

Cost didefinisikan sebagai **indeks** di mana `'1'` muncul — bukan jumlah `'1'`. Jika `'1'` ditempatkan di posisi `i`, kontribusinya ke cost adalah `i` (bukan 1).

```
s = "010", cost:
'1' di indeks 1 → cost += 1 = 1

s = "001", cost:
'1' di indeks 2 → cost += 2 = 2
```

______________________________________________________________________

## 🔧 Kenapa Prune `cost > k` di Awal Rekursi?

```java
if (cost > k)
    return;
```

Cek ini di awal fungsi (bukan sebelum rekursi) memungkinkan **early exit** — begitu cost melebihi `k`, seluruh subtree dipotong tanpa perlu memproses lebih lanjut. Ini efisien karena cost hanya bisa bertambah (tidak pernah berkurang).

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **backtracking dengan dua constraint sekaligus**: no consecutive `'1'` dan cost limit. Dua pruning bekerja secara independen — consecutive `'1'` dipotong saat memilih karakter, cost limit dipotong di awal setiap call. Dengan `n <= 12`, jumlah string yang mungkin sangat terbatas sehingga backtracking aman. Perhatikan bahwa cost adalah **indeks** bukan jumlah `'1'` — ini detail yang mudah terlewat. 🎯

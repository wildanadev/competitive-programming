# D. Dikit Lagi Lulus

- **Platform**: TLX Toki
- **Contest**: Arkavidia 10 CP Penyisihan
- **Difficulty**: -
- **Topics**: Adhoc
- **Link**: [Problem](https://tlx.toki.id/problems/arkavidia-10-cp-penyisihan/D)
- **Solution**: [Code](../../tlx/DikitLagiLulus.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `A` berisi `N` bilangan bulat dan index `X`. Kita boleh **memindahkan elemen di posisi X ke mana saja** (sekali saja). Cek apakah array bisa menjadi **terurut non-decreasing**. Output `1` kalau bisa, `0` kalau tidak.

Contoh:

- `A = [1,2,5,3,4,6], X = 3` → `1` (pindahkan `5` ke setelah `4` → `[1,2,3,4,5,6]`)
- `A = [1,2,5,3,4,6], X = 1` → `0` (tidak ada posisi yang membuat array terurut)

______________________________________________________________________

## 💡 Intuition

Kalau elemen ke-X dilewati (skip), sisa array harus sudah **terurut non-decreasing**. Kenapa?

Karena jika sisa array (tanpa elemen X) sudah terurut, kita pasti bisa **menyisipkan** elemen X di posisi yang tepat sehingga seluruh array menjadi terurut. Kalau sisa array tidak terurut, tidak ada posisi manapun yang bisa membuat array menjadi terurut hanya dengan memindahkan satu elemen.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `temp = arr[0] = 0` (nilai awal sebelum loop), `ans = 1`
1. Loop `i` dari `1` sampai `N`:
   - Kalau `i == X` → skip elemen ini
   - Kalau `temp > arr[i]` → array tidak terurut → `ans = 0`, break
   - Update `temp = arr[i]`
1. Print `ans`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------- |
| **Time** | O(N) — satu kali loop |
| **Space** | O(N) — array input |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `N=6, X=3, A=[1,2,5,3,4,6]`

**Init:** `temp=0, ans=1`

| i | arr[i] | i==X? | temp > arr[i]? | Aksi | temp |
| --- | ------ | ------ | -------------- | ---- | ---- |
| 1 | 1 | ❌ | 0>1? ❌ | - | 1 |
| 2 | 2 | ❌ | 1>2? ❌ | - | 2 |
| 3 | 5 | ✅ X=3 | skip | skip | 2 |
| 4 | 3 | ❌ | 2>3? ❌ | - | 3 |
| 5 | 4 | ❌ | 3>4? ❌ | - | 4 |
| 6 | 6 | ❌ | 4>6? ❌ | - | 6 |

**return `1` ✅** → sisa array tanpa X terurut, elemen `5` bisa disisipkan di posisi yang tepat

______________________________________________________________________

**Input:** `N=6, X=1, A=[1,2,5,3,4,6]`

**Init:** `temp=0, ans=1`

| i | arr[i] | i==X? | temp > arr[i]? | Aksi | temp |
| --- | ------ | ------ | -------------- | ------------ | ---- |
| 1 | 1 | ✅ X=1 | skip | skip | 0 |
| 2 | 2 | ❌ | 0>2? ❌ | - | 2 |
| 3 | 5 | ❌ | 2>5? ❌ | - | 5 |
| 4 | 3 | ❌ | 5>3? ✅ | ans=0, break | - |

**return `0` ✅** → sisa array tanpa X tidak terurut

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `X = 1` → elemen pertama yang dipindah, cek sisa array
- [ ] `X = N` → elemen terakhir yang dipindah, cek sisa array
- [ ] Array sudah terurut → output `1` apapun nilai X
- [ ] Semua elemen sama → output `1`

______________________________________________________________________

## 📌 Key Takeaway

Kunci solusi ini adalah observasi ad hoc: kalau sisa array tanpa elemen X sudah terurut, elemen X pasti bisa disisipkan di posisi yang tepat. Tidak perlu coba semua kemungkinan posisi — cukup cek apakah sisa array terurut atau tidak. Inisialisasi temp = 0 memanfaatkan constraint soal (Ai ≥ 1) sehingga elemen pertama selalu lolos pengecekan. 🎯

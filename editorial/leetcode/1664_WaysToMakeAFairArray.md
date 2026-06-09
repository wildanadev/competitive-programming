# 1664. Ways to Make a Fair Array

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Prefix Sum, Greedy
- **Link**: [Problem](https://leetcode.com/problems/ways-to-make-a-fair-array/)
- **Solution**: [Code](../../leetcode/WaysToMakeAFairArray.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`. Dalam satu operasi, hapus tepat satu elemen. Array disebut **fair** jika jumlah elemen di indeks genap = jumlah elemen di indeks ganjil. Kembalikan jumlah cara menghapus elemen sehingga array menjadi fair.

Contoh:

- `nums = [2,1,6,4]` → `1` (hapus indeks 1 → `[2,6,4]`, genap=2+4=6, ganjil=6 ✅)
- `nums = [1,1,1]` → `3`
- `nums = [1,2,3]` → `0`

______________________________________________________________________

## 💡 Intuition

Saat elemen di indeks `i` dihapus, **semua elemen setelah `i`** mengalami pergeseran indeks — yang genap menjadi ganjil dan sebaliknya.

Jadi setelah menghapus indeks `i`:

- Elemen **sebelum `i`**: indeks tidak berubah → kontribusi tetap sama
- Elemen **setelah `i`**: indeks bergeser → yang tadinya genap jadi ganjil, ganjil jadi genap

Untuk mengecek fair tanpa rebuild array, lacak empat nilai:

- `left[0]` = sum elemen **genap** di kiri `i`
- `left[1]` = sum elemen **ganjil** di kiri `i`
- `right[0]` = sum elemen **genap** di kanan `i` (termasuk `i`)
- `right[1]` = sum elemen **ganjil** di kanan `i` (termasuk `i`)

Setelah hapus `i`, total genap = `left[0] + right[1]`, total ganjil = `left[1] + right[0]`.

______________________________________________________________________

## 🔍 Approach

### Prefix + Suffix Sum dengan Two Arrays

**Setup:** Hitung `right[0]` dan `right[1]` (sum semua elemen genap dan ganjil).

**Loop setiap `i`:**

1. Kurangi `nums[i]` dari `right[i%2]` (hapus kontribusi `i` dari suffix).
1. Cek: `left[0] + right[1] == left[1] + right[0]` → fair → `res++`.
1. Tambahkan `nums[i]` ke `left[i%2]` (pindahkan ke prefix).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu pass setup + satu pass loop |
| **Space** | O(1) — hanya dua array ukuran 2 |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [2,1,6,4]`

**Setup `right`:**

```
i=0: right[0] += 2 → right=[2,0]
i=1: right[1] += 1 → right=[2,1]
i=2: right[0] += 6 → right=[8,1]
i=3: right[1] += 4 → right=[8,5]
```

`left=[0,0], right=[8,5]`

______________________________________________________________________

**i=0, nums[0]=2 (genap, i%2=0):**

```
right[0] -= 2 → right=[6,5]
Cek: left[0]+right[1] == left[1]+right[0]
     0+5 == 0+6 → 5==6 ❌
left[0] += 2 → left=[2,0]
```

**i=1, nums[1]=1 (ganjil, i%2=1):**

```
right[1] -= 1 → right=[6,4]
Cek: left[0]+right[1] == left[1]+right[0]
     2+4 == 0+6 → 6==6 ✅ → res=1
left[1] += 1 → left=[2,1]
```

**i=2, nums[2]=6 (genap, i%2=0):**

```
right[0] -= 6 → right=[0,4]
Cek: left[0]+right[1] == left[1]+right[0]
     2+4 == 1+0 → 6==1 ❌
left[0] += 6 → left=[8,1]
```

**i=3, nums[3]=4 (ganjil, i%2=1):**

```
right[1] -= 4 → right=[0,0]
Cek: left[0]+right[1] == left[1]+right[0]
     8+0 == 1+0 → 8==1 ❌
left[1] += 4 → left=[8,5]
```

**Output: `1` ✅**

**Verifikasi:** hapus `nums[1]=1` → `[2,6,4]`

- genap (idx 0,2): 2+4=6
- ganjil (idx 1): 6
- 6==6 ✅

______________________________________________________________________

**Input:** `nums = [1,1,1]`

**Setup:** `right=[1,2]` (right[0]=nums[0]+nums[2]=2, right[1]=nums[1]=1... wait)

Mari trace:

```
i=0: right[0]+=1 → [1,0]
i=1: right[1]+=1 → [1,1]
i=2: right[0]+=1 → [2,1]
```

`left=[0,0], right=[2,1]`

| i | nums[i] | right setelah -= | left[0]+right[1] vs left[1]+right[0] | res | left setelah += |
| --- | ---------- | ---------------- | ------------------------------------ | --- | --------------- |
| 0 | 1 (genap) | [1,1] | 0+1 vs 0+1 = 1==1 ✅ | 1 | [1,0] |
| 1 | 1 (ganjil) | [1,0] | 1+0 vs 0+1 = 1==1 ✅ | 2 | [1,1] |
| 2 | 1 (genap) | [0,1] | 1+1 vs 1+0 = 2==1 ❌ | 2 | [2,1] |

**Output: `2`** ... hmm expected `3`. Mari cek.

Wait, `n=3`: hapus idx 0 → `[1,1]` genap=1,ganjil=1 ✅; hapus idx 1 → `[1,1]` genap=1,ganjil=1 ✅; hapus idx 2 → `[1,1]` genap=1,ganjil=1 ✅. Seharusnya `3`.

Trace ulang setup:

```
i=0: right[0%2=0] += nums[0]=1 → right=[1,0]
i=1: right[1%2=1] += nums[1]=1 → right=[1,1]
i=2: right[2%2=0] += nums[2]=1 → right=[2,1]
```

i=0: right[0] -= 1 → right=[1,1]; 0+1 vs 0+1 ✅ res=1; left[0]+=1 → left=[1,0]
i=1: right[1] -= 1 → right=[1,0]; 1+0 vs 0+1 ✅ res=2; left[1]+=1 → left=[1,1]
i=2: right[0] -= 1 → right=[0,0]; 1+0 vs 1+0 ✅ res=3; left[0]+=1 → left=[2,1]

**Output: `3` ✅** (saya salah trace sebelumnya, right[1] di i=2 = 0 bukan 1)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → hapus → array kosong → sum genap=0=sum ganjil → fair ✅
- [ ] Semua elemen sama → banyak cara yang valid
- [ ] Array sudah fair sebelum hapus apapun → hanya elemen yang "tidak merusak" fair yang dihitung

______________________________________________________________________

## 🔧 Mengapa `left[0] + right[1] == left[1] + right[0]`?

Setelah menghapus elemen di indeks `i`:

- Elemen kiri `i`: posisi tidak berubah → genap tetap genap, ganjil tetap ganjil
- Elemen kanan `i`: semua bergeser → genap jadi ganjil, ganjil jadi genap

```
Total genap setelah hapus i = left[0] (genap kiri) + right[1] (ganjil kanan, jadi genap)
Total ganjil setelah hapus i = left[1] (ganjil kiri) + right[0] (genap kanan, jadi ganjil)

Fair jika: left[0] + right[1] == left[1] + right[0]
```

______________________________________________________________________

**Contoh visual:** `[2,1,6,4]`, hapus indeks `1`:

```
Sebelum hapus: [2, 1, 6, 4]
                0  1  2  3  ← indeks

Setelah hapus: [2, 6, 4]
                0  1  2  ← indeks bergeser!
               ↑  ↑  ↑
              kiri  kanan (bergeser)
```

Elemen `6` tadinya indeks 2 (genap), sekarang indeks 1 (ganjil) → masuk ke `right[0]` tapi kontribusinya ke ganjil.

______________________________________________________________________

## 📌 Key Takeaway

oal ini mengajarkan teknik **"simulasi pergeseran indeks tanpa rebuild array"** — dengan melacak suffix sum terpisah untuk genap/ganjil, pergeseran indeks setelah penghapusan terwakili oleh penukaran `right[0]` dan `right[1]`. Array `left[2]` dan `right[2]` yang masing-masing berukuran 2 adalah representasi yang sangat elegan dan efisien O(1) space. 🎯

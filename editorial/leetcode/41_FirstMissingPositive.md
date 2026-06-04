# 41. First Missing Positive

- **Platform**: LeetCode
- **Difficulty**: Hard
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/first-missing-positive/)
- **Solution**: [Code](../../leetcode/FirstMissingPositive.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `nums` yang tidak terurut, temukan **bilangan positif terkecil yang tidak ada** di array. Solusi harus O(n) time dan O(1) space.

Contoh:

- `nums = [1,2,0]` → `3`
- `nums = [3,4,-1,1]` → `2`
- `nums = [7,8,9,11,12]` → `1`

______________________________________________________________________

## 💡 Intuition

Jawaban pasti berada di rentang `[1, n+1]` — jika semua `1..n` ada di array, jawabannya `n+1`.

**Ide kunci**: gunakan array itu sendiri sebagai "visited marker" dengan **tanda negatif**. Indeks `i` merepresentasikan angka `i+1`. Jika `nums[i]` negatif → angka `i+1` **sudah ada** di array.

Tiga pass:

1. **Normalisasi** — ganti semua angka di luar `[1,n]` dengan `n+1` (tidak relevan).
1. **Flag** — untuk setiap angka yang ditemukan, tandai indeks yang sesuai dengan tanda negatif.
1. **Scan** — indeks pertama yang masih positif → angka tersebut tidak ada.

______________________________________________________________________

## 🔍 Approach

### Negative Marking (In-place)

**Pass 1 — Normalisasi:**

```java
if (nums[i] <= 0 || nums[i] > n)
    nums[i] = n + 1;
```

Angka ≤ 0 atau > n tidak relevan → ganti dengan `n+1` (sentinel).

**Pass 2 — Flag:**

```java
int num = Math.abs(nums[i]);  // abs karena bisa sudah di-flag negatif
if (num - 1 < n)
    nums[num - 1] = -Math.abs(nums[num - 1]);
```

Angka `num` ada di array → tandai indeks `num-1` menjadi negatif.
`Math.abs` dipakai dua kali:

- `Math.abs(nums[i])` → ambil nilai asli meski sudah di-flag
- `-Math.abs(nums[num-1])` → set negatif tanpa double-negate

**Pass 3 — Scan:**

```java
if (nums[i] > 0)
    return i + 1;
```

Indeks `i` yang masih positif → angka `i+1` tidak pernah ditemukan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------ |
| **Time** | O(n) — tiga pass linear |
| **Space** | O(1) — modifikasi in-place, tidak ada struktur data tambahan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [3,4,-1,1]`, `n=4`

**Pass 1 — Normalisasi:**
| i | nums[i] | Kondisi | Setelah |
|---|---------|---------|---------|
| 0 | 3 | 1≤3≤4 ✅ | 3 |
| 1 | 4 | 1≤4≤4 ✅ | 4 |
| 2 | -1 | -1≤0 ✅ | **5** |
| 3 | 1 | 1≤1≤4 ✅ | 1 |

`nums = [3,4,5,1]`

______________________________________________________________________

**Pass 2 — Flag (inti algorithm):**

`i=0, nums[0]=3` → `num=3`, flag indeks `num-1=2`:

```
nums[2] = -|nums[2]| = -|5| = -5
nums = [3,4,-5,1]
```

`i=1, nums[1]=4` → `num=4`, flag indeks `3`:

```
nums[3] = -|nums[3]| = -|1| = -1
nums = [3,4,-5,-1]
```

`i=2, nums[2]=-5` → `num=|-5|=5`, `num-1=4`, `4 < n=4`? ❌ → skip

`i=3, nums[3]=-1` → `num=|-1|=1`, flag indeks `0`:

```
nums[0] = -|nums[0]| = -|3| = -3
nums = [-3,4,-5,-1]
```

**Pass 3 — Scan:**
| i | nums[i] | > 0? | |
|---|---------|------|-|
| 0 | -3 | ❌ | (angka 1 ada) |
| 1 | 4 | ✅ | → return `i+1 = 2` |

**Output: `2` ✅**

______________________________________________________________________

**Input:** `nums = [1,2,3]`, `n=3`

**Pass 1:** semua valid → `[1,2,3]`

**Pass 2:**

```
i=0: num=1 → flag idx 0 → nums=[-1,2,3]
i=1: num=2 → flag idx 1 → nums=[-1,-2,3]
i=2: num=3 → flag idx 2 → nums=[-1,-2,-3]
```

**Pass 3:** semua negatif → return `n+1 = 4`

**Output: `4` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua negatif `[-1,-2,-3]` → pass 1 ganti semua jadi `n+1` → pass 2 tidak ada flag → return `1`
- [ ] `[1]` → flag indeks 0 → semua negatif → return `2`
- [ ] Semua angka sama `[2,2,2]` → indeks 0 tidak di-flag → return `1`

______________________________________________________________________

## 🔧 Penjelasan Detail Pass 2 (Flag)

Ini bagian yang paling membingungkan. Mari pahami step by step.

### Konsep Dasar

Kita ingin menandai "angka X sudah ada" tanpa ruang tambahan. Caranya: **gunakan tanda negatif di indeks X-1** sebagai penanda.

```
Angka 3 ada → tandai nums[2] menjadi negatif
Angka 1 ada → tandai nums[0] menjadi negatif
Angka 5 ada (tapi n=4, tidak relevan) → skip
```

Akhirnya: indeks yang masih positif → angka itu tidak ada!

### Kenapa `Math.abs(nums[i])`?

```java
int num = Math.abs(nums[i]);
```

Saat kita memproses indeks `i`, `nums[i]` **mungkin sudah di-flag negatif** oleh iterasi sebelumnya. `Math.abs` mengambil nilai aslinya.

Contoh: `nums = [2,1]`

```
i=0: nums[0]=2, flag nums[1]=-1 → nums=[-2... wait
i=0: nums[0]=2 (belum di-flag), num=2, flag nums[1]
i=1: nums[1] sudah -1! num = Math.abs(-1) = 1, flag nums[0]
```

Tanpa `Math.abs`: `num = -1` → `num-1 = -2` → indeks negatif → crash!

### Kenapa `-Math.abs(nums[num-1])`?

```java
nums[num - 1] = -Math.abs(nums[num - 1]);
```

Kita ingin **set negatif**, bukan toggle. Jika `nums[num-1]` sudah negatif dan kita lakukan `-nums[num-1]`, hasilnya positif lagi (double negative)!

```
nums[2] = -5 (sudah di-flag)
Angka 3 muncul lagi → kita flag lagi
Tanpa Math.abs: nums[2] = -(-5) = 5 → tiba-tiba positif lagi! ❌
Dengan Math.abs: nums[2] = -|(-5)| = -5 → tetap negatif ✅
```

### Visualisasi Pass 2

```
nums = [3,4,5,1]

Angka 3 ada → "pasang bendera" di pos 2 → nums[2] negatif
Angka 4 ada → "pasang bendera" di pos 3 → nums[3] negatif
Angka 5 ada → pos 4, tapi array hanya 0..3, skip!
Angka 1 ada → "pasang bendera" di pos 0 → nums[0] negatif

Hasil: [-3, 4, -5, -1]
              ↑
         pos 1 masih positif → angka 2 tidak ada!
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh paling klasik dari teknik **negative marking** — menggunakan tanda negatif sebagai "visited flag" in-place tanpa alokasi memori tambahan. Dua detail krusial: `Math.abs(nums[i])` untuk membaca nilai yang mungkin sudah di-flag, dan `-Math.abs(nums[num-1])` untuk mencegah double-negative yang akan membatalkan flag. 🎯

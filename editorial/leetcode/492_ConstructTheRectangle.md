# 492. Construct the Rectangle

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math
- **Link**: [Problem](https://leetcode.com/problems/construct-the-rectangle/)
- **Solution**: [Code](../../leetcode/ConstructTheRectangle.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan area persegi panjang, cari dimensi `[L, W]` (L >= W >= 1) sehingga:

- `L × W = area`
- Selisih `L - W` seminimal mungkin (mendekati persegi)

Kembalikan `[L, W]`.

Contoh:

- `area = 4` → `[2,2]` (bukan [4,1] karena selisih lebih besar)
- `area = 37` → `[37,1]` (bilangan prima, satu-satunya pembagi)
- `area = 122122` → `[427,286]`

______________________________________________________________________

## 💡 Intuition

Untuk meminimalkan `L - W`, kita ingin `L` dan `W` se**dekat** mungkin — idealnya keduanya mendekati `√area`.

**Strategi**: mulai dari `W = ⌊√area⌋` (nilai terbesar yang mungkin untuk W), turunkan `W` satu per satu sampai `area % W == 0`. Pembagi pertama yang ditemukan dari atas adalah `W` optimal karena paling mendekati `√area`.

```
area = 4
√4 = 2

i=2: 4%2==0 → W=2, L=2 ✅ (selisih 0, optimal!)
```

```
area = 6
√6 ≈ 2.44 → mulai dari i=2

i=2: 6%2==0 → W=2, L=3 ✅ (selisih 1)
(tidak mencoba i=1 karena sudah ketemu)
```

______________________________________________________________________

## 🔍 Approach

### Greedy — Cari Pembagi Terbesar ≤ √area

1. Loop `i` dari `⌊√area⌋` turun ke `1`.
1. Jika `area % i == 0` → ditemukan pasangan `(i, area/i)`.
1. Return `{max(i, area/i), min(i, area/i)}` agar L ≥ W.

> Loop pasti berhenti sebelum `i=0` karena `area % 1 == 0` selalu terpenuhi.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------- |
| **Time** | O(√area) — loop dari √area sampai pembagi pertama |
| **Space** | O(1) — hanya variabel `i` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `area = 4`

`i = ⌊√4⌋ = 2`

| i | area%i==0? | Aksi |
| --- | ---------- | ----------------------------------- |
| 2 | 4%2=0 ✅ | return {max(2,2), min(2,2)} = {2,2} |

**Output: `[2,2]` ✅**

______________________________________________________________________

**Input:** `area = 37`

`i = ⌊√37⌋ = 6`

| i | area%i==0? |
| --- | ------------------------- |
| 6 | 37%6=1 ❌ |
| 5 | 37%5=2 ❌ |
| 4 | 37%4=1 ❌ |
| 3 | 37%3=1 ❌ |
| 2 | 37%2=1 ❌ |
| 1 | 37%1=0 ✅ → return {37,1} |

**Output: `[37,1]` ✅** (37 adalah bilangan prima)

______________________________________________________________________

**Input:** `area = 6`

`i = ⌊√6⌋ = 2`

| i | area%i==0? | Aksi |
| --- | ---------- | ----------------------------------- |
| 2 | 6%2=0 ✅ | return {max(2,3), min(2,3)} = {3,2} |

**Output: `[3,2]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `area = 1` → `i=1`, `1%1=0` → return `{1,1}`
- [ ] `area` bilangan prima → hanya pembagi `1` yang ditemukan → return `{area,1}`
- [ ] `area` perfect square → `i=√area` langsung ditemukan → return `{√area, √area}`

______________________________________________________________________

## 🔧 Kenapa Mulai dari `√area`, Bukan `area`?

Karena untuk setiap pasangan pembagi `(d, area/d)`:

- Jika `d < √area` → `area/d > √area` → selisih `area/d - d` lebih besar
- Jika `d = √area` → selisih minimum (persegi sempurna)
- Jika `d > √area` → `area/d < √area` → keduanya bertukar peran

Dengan mulai dari `√area` dan turun, **pembagi pertama yang ditemukan** selalu menghasilkan selisih `L - W` terkecil.

```
area = 12, pembagi: (1,12), (2,6), (3,4)
√12 ≈ 3.46 → mulai i=3
i=3: 12%3=0 → {4,3}, selisih=1 ✅ (paling kecil)

Jika mulai dari i=1:
i=1 → {12,1}, selisih=11 ❌ (bukan optimal)
```

______________________________________________________________________

## 🔧 Kenapa `Math.max` dan `Math.min` di Return?

```java
return new int[] { Math.max(i, area / i), Math.min(i, area / i) };
```

Soal mensyaratkan `L >= W`. Saat `i < √area`, `area/i > i` → `L = area/i`, `W = i`. Saat `i = √area` (perfect square), keduanya sama. `Math.max/min` memastikan urutan selalu `[L, W]` yang benar tanpa perlu if-else.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah **greedy mencari pembagi terdekat √area** — dengan mulai dari √area dan turun, pembagi pertama yang ditemukan selalu menghasilkan pasangan (L, W) dengan selisih terkecil. Loop pasti berakhir karena `area % 1 == 0` selalu benar — `{area, 1}` adalah fallback terburuk untuk bilangan prima. 🎯

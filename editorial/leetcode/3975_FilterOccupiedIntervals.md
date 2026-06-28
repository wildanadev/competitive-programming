# 3975. Filter Occupied Intervals

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Sorting
- **Link**: [Problem](https://leetcode.com/problems/filter-occupied-intervals/)
- **Solution**: [Code](../../leetcode/FilterOccupiedIntervals.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array interval `occupiedIntervals` (boleh overlap atau bersentuhan) dan satu interval `[freeStart, freeEnd]`. Tugas:

1. **Merge** semua occupied interval yang overlap atau **bersentuhan** (`[1,1]` dan `[2,2]` dianggap bersentuhan → digabung jadi `[1,2]`).
1. **Hapus** bagian yang tumpang tindih dengan `[freeStart, freeEnd]` dari hasil merge.

Kembalikan interval yang tersisa, terurut dan non-overlapping.

Contoh:

- `occupiedIntervals=[[2,6],[4,8],[10,10],[10,12],[14,16]]`, `freeStart=7, freeEnd=11` → `[[2,6],[12,12],[14,16]]`
- `occupiedIntervals=[[1,5],[2,3]]`, `freeStart=3, freeEnd=8` → `[[1,2]]`

______________________________________________________________________

## 💡 Intuition

Dua tahap independen:

**Tahap 1 — Merge:** Sort by start, gabungkan interval yang overlap **atau bersentuhan**. Bersentuhan berarti `next.start <= curr.end + 1` (bukan hanya `<=`).

**Tahap 2 — Subtract:** Untuk setiap merged interval `[s,e]`, ada 4 kemungkinan relasi dengan `[freeStart,freeEnd]`:

```
1. Free menutupi seluruh [s,e]     → interval hilang sepenuhnya
2. Free tidak overlap sama sekali  → interval tetap utuh
3. Free overlap di tengah/sebagian → interval terpotong jadi 0, 1, atau 2 bagian
```

______________________________________________________________________

## 🔍 Approach

### Merge Intervals (dengan toleransi +1) + Interval Subtraction

**Step 1 — Merge:**

```java
Arrays.sort(intervals, (a,b) -> a[0]-b[0]);
for setiap interval:
    if (start <= currentMax + 1)  // overlap ATAU bersentuhan
        extend currentMax
    else
        simpan interval lama, mulai baru
```

**Step 2 — Subtract freeInterval dari setiap merged interval `[s,e]`:**

```
Case 1: freeStart <= s && freeEnd >= e
    → free menutupi seluruhnya → skip (tidak ditambahkan)

Case 2: freeEnd < s || freeStart > e
    → tidak overlap → tambahkan [s,e] utuh

Case 3: overlap parsial
    → jika s < freeStart: tambahkan [s, freeStart-1] (bagian kiri tersisa)
    → jika e > freeEnd: tambahkan [freeEnd+1, e] (bagian kanan tersisa)
```

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------- |
| **Time** | O(n log n) — dominan di sorting |
| **Space** | O(n) — list hasil merge dan output |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `occupiedIntervals=[[2,6],[4,8],[10,10],[10,12],[14,16]]`, `freeStart=7, freeEnd=11`

**Step 1 — Sort:** sudah terurut by start: `[[2,6],[4,8],[10,10],[10,12],[14,16]]`

**Step 1 — Merge:**

```
min=2, max=6
i=1 [4,8]: 4<=6+1=7 ✅ → max=max(6,8)=8
i=2 [10,10]: 10<=8+1=9? ❌ → simpan [2,8], min=10,max=10
i=3 [10,12]: 10<=10+1=11 ✅ → max=max(10,12)=12
i=4 [14,16]: 14<=12+1=13? ❌ → simpan [10,12], min=14,max=16
selesai → simpan [14,16]

merged = [[2,8],[10,12],[14,16]]
```

**Step 2 — Subtract `[7,11]` dari setiap merged interval:**

| interval [s,e] | Case | Hasil |
| -------------- | ------------------------------------------------------------------------------------------------------------- | --------- |
| [2,8] | freeStart=7\<=8=e tapi freeStart>s=2 → overlap parsial. s\<freeStart(7)? ✅ → tambah [2,6]. e>freeEnd(11)? ❌ | `[2,6]` |
| [10,12] | freeStart=7\<=10=s? Tidak relevan; overlap parsial. s\<freeStart? ❌ (10>7). e>freeEnd(11)? ✅ → tambah [12,12] | `[12,12]` |
| [14,16] | freeEnd=11 < s=14 → tidak overlap → tambah utuh | `[14,16]` |

**Output: `[[2,6],[12,12],[14,16]]` ✅**

______________________________________________________________________

**Input:** `occupiedIntervals=[[1,5],[2,3]]`, `freeStart=3, freeEnd=8`

**Merge:** sort → `[[1,5],[2,3]]`; `min=1,max=5`; i=1 \[2,3\]: 2\<=6 → max=max(5,3)=5 → merged=`[[1,5]]`

**Subtract `[3,8]` dari `[1,5]`:**

```
freeStart=3<=s=1? ❌ (case 1 gagal)
freeEnd=8 < s=1? ❌; freeStart=3 > e=5? ❌ → overlap parsial
s=1 < freeStart=3? ✅ → tambah [1, 3-1] = [1,2]
e=5 > freeEnd=8? ❌ → tidak tambah bagian kanan
```

**Output: `[[1,2]]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Interval bersentuhan `[1,1]` dan `[2,2]` → harus di-merge jadi `[1,2]` (toleransi +1)
- [ ] Free interval menutupi seluruh occupied → interval tersebut hilang dari output
- [ ] Free interval tidak overlap dengan apapun → semua occupied tetap utuh
- [ ] Free interval di tengah occupied → split menjadi dua bagian

______________________________________________________________________

## 🔧 Kenapa Toleransi `+1` Saat Merge?

```java
if (lap[i][0] <= max + 1)  // bukan cuma <= max
```

Soal mendefinisikan "bersentuhan" sebagai `[1,1]` dan `[2,2]` yang harus digabung — meskipun `2 > 1` (tidak overlap dalam arti biasa), keduanya **bersebelahan langsung** (`2 == 1+1`). Toleransi `+1` menangkap kasus ini.

______________________________________________________________________

## 🔧 Refactor: Kode Lebih Bersih

Kode asli punya nested if-else yang agak sulit dibaca. Berikut versi yang lebih clear:

```java
class Solution {
    public List<List<Integer>> filterOccupiedIntervals(
            int[][] occupiedIntervals, int freeStart, int freeEnd) {

        // Step 1: Sort dan merge interval yang overlap/bersentuhan
        Arrays.sort(occupiedIntervals, (a, b) -> a[0] - b[0]);
        List<int[]> merged = new ArrayList<>();
        int curStart = occupiedIntervals[0][0];
        int curEnd = occupiedIntervals[0][1];

        for (int i = 1; i < occupiedIntervals.length; i++) {
            int[] iv = occupiedIntervals[i];
            if (iv[0] <= curEnd + 1) {
                curEnd = Math.max(curEnd, iv[1]);
            } else {
                merged.add(new int[]{curStart, curEnd});
                curStart = iv[0];
                curEnd = iv[1];
            }
        }
        merged.add(new int[]{curStart, curEnd});

        // Step 2: Subtract free interval dari setiap merged interval
        List<List<Integer>> result = new ArrayList<>();
        for (int[] iv : merged) {
            int s = iv[0], e = iv[1];

            // Tidak overlap sama sekali dengan free interval
            if (freeEnd < s || freeStart > e) {
                result.add(List.of(s, e));
                continue;
            }

            // Bagian kiri tersisa (sebelum freeStart)
            if (s < freeStart) {
                result.add(List.of(s, freeStart - 1));
            }
            // Bagian kanan tersisa (setelah freeEnd)
            if (e > freeEnd) {
                result.add(List.of(freeEnd + 1, e));
            }
            // Jika freeStart<=s && freeEnd>=e: seluruh interval tertutup, tidak tambah apapun
        }

        return result;
    }
}
```

**Perbaikan dari kode asli:**

1. Hapus pengecekan "Case 1" terpisah — sudah otomatis tertangani karena jika `freeStart<=s && freeEnd>=e`, baik "bagian kiri" maupun "bagian kanan" tidak terpenuhi kondisinya → tidak ada yang ditambahkan.
1. Hapus nested if-else berlapis — gunakan `continue` untuk early exit pada kasus "tidak overlap".
1. Gunakan `int[]` untuk interval intermediate (merged) — lebih ringan dari `List<Integer>` yang baru dikonversi di akhir saja.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menggabungkan **interval merging** (dengan definisi "bersentuhan" yang unik, +1 toleransi) dan **interval subtraction** (memotong interval berdasarkan rentang yang dihapus). Insight penting: kondisi "interval tertutup penuh" tidak perlu dicek secara eksplisit — itu adalah kasus di mana **baik kondisi kiri maupun kanan gagal**, sehingga otomatis tidak ada yang ditambahkan ke hasil. 🎯

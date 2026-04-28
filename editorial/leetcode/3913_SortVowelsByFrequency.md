# Sort Vowels by Frequency

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: String, Hash Table, Sorting, Priority Queue (Heap)
- **Link**: [Problem](https://leetcode.com/problems/sort-vowels-by-frequency/)
- **Solution**: [Code](../../leetcode/SortVowelsByFrequency.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s`, susun ulang sehingga:

- **Konsonan** tetap di posisi aslinya.
- **Vokal** diurutkan berdasarkan **frekuensi descending** di posisi vokal yang sama. Jika frekuensi sama, urutkan berdasarkan **kemunculan pertama** di string (lebih awal = lebih dulu).

Contoh:

- `s = "aeiaaioooa"` → `"aaaaoooiie"`
  - `a` muncul 4x, `o` muncul 3x, `i` muncul 2x, `e` muncul 1x

______________________________________________________________________

## 💡 Intuition

Kita perlu mengurutkan vokal berdasarkan frekuensinya — vokal yang paling sering muncul diletakkan lebih dulu. Jika dua vokal memiliki frekuensi sama, yang muncul **lebih awal** di string asli diletakkan lebih dulu.

Langkah besarnya:

1. **Hitung frekuensi** setiap vokal dan catat **indeks pertama** kemunculannya.
1. **Urutkan vokal** menggunakan PriorityQueue — prioritas: frekuensi tinggi dulu, jika sama maka indeks pertama lebih kecil dulu.
1. **Bangun list vokal terurut** — expand setiap vokal sebanyak frekuensinya.
1. **Isi kembali** posisi vokal di string asli dengan list tersebut.

______________________________________________________________________

## 🔍 Approach

### HashMap + PriorityQueue + List

**Step 1 — Build HashMap:**

- Key = karakter vokal
- Value = `int[2]` di mana `[0]` = frekuensi, `[1]` = indeks pertama kemunculan
- `putIfAbsent` memastikan `[1]` hanya diisi saat **pertama kali** vokal ditemukan

**Step 2 — PriorityQueue dengan Custom Comparator:**

```java
(a, b) -> {
    if (freq_a != freq_b) return freq_b - freq_a;  // frekuensi tinggi dulu
    else return firstIndex_a - firstIndex_b;        // indeks kecil dulu jika frekuensi sama
}
```

**Step 3 — Expand ke List:**

- Poll setiap vokal dari PriorityQueue
- Tambahkan ke list sebanyak frekuensinya

**Step 4 — Isi Kembali:**

- Loop string asli — jika vokal, ambil dari list secara berurutan; jika konsonan, ambil aslinya

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------ |
| **Time** | O(n + k log k) — n = panjang string, k = jumlah vokal unik (max 5) |
| **Space** | O(n) — HashMap, list, StringBuilder |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "aeiaaioooa"`

**Step 1 — Build HashMap:**

Loop karakter satu per satu:

| i | ch | putIfAbsent? | map setelah |
| --- | --- | ------------ | ----------------------------- |
| 0 | a | ✅ insert | `{a:[1,0]}` |
| 1 | e | ✅ insert | `{a:[1,0], e:[1,1]}` |
| 2 | i | ✅ insert | `{a:[1,0], e:[1,1], i:[1,2]}` |
| 3 | a | ❌ skip | count[a]++ → `{a:[2,0], ...}` |
| 4 | a | ❌ skip | count[a]++ → `{a:[3,0], ...}` |
| 5 | i | ❌ skip | count[i]++ → `{..., i:[2,2]}` |
| 6 | o | ✅ insert | `{..., o:[1,6]}` |
| 7 | o | ❌ skip | count[o]++ → `{..., o:[2,6]}` |
| 8 | o | ❌ skip | count[o]++ → `{..., o:[3,6]}` |
| 9 | a | ❌ skip | count[a]++ → `{a:[4,0], ...}` |

HashMap akhir:
| key | freq | first index |
|-----|------|-------------|
| a | 4 | 0 |
| e | 1 | 1 |
| i | 2 | 2 |
| o | 3 | 6 |

**Step 2 — PriorityQueue (sort by freq desc, lalu first index asc):**

Masukkan semua key: `{a, e, i, o}`

Urutan poll berdasarkan comparator:
| urutan | vokal | freq | first index | alasan |
|--------|-------|------|-------------|--------|
| 1 | a | 4 | 0 | freq tertinggi |
| 2 | o | 3 | 6 | freq kedua |
| 3 | i | 2 | 2 | freq ketiga |
| 4 | e | 1 | 1 | freq terendah |

**Step 3 — Expand ke List:**

- poll `a` (freq=4) → tambah `a,a,a,a`
- poll `o` (freq=3) → tambah `o,o,o`
- poll `i` (freq=2) → tambah `i,i`
- poll `e` (freq=1) → tambah `e`

`list = ['a','a','a','a','o','o','o','i','i','e']`

**Step 4 — Isi Kembali:**

| char asli | vokal? | diambil | hasil |
| --------- | ------ | ----------- | ----- |
| a | ✅ | list[0] = a | a |
| e | ✅ | list[1] = a | a |
| i | ✅ | list[2] = a | a |
| a | ✅ | list[3] = a | a |
| a | ✅ | list[4] = o | o |
| i | ✅ | list[5] = o | o |
| o | ✅ | list[6] = o | o |
| o | ✅ | list[7] = i | i |
| o | ✅ | list[8] = i | i |
| a | ✅ | list[9] = e | e |

**Output: `"aaaaoooiie"` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Tidak ada vokal → HashMap kosong, list kosong → string dikembalikan apa adanya
- [ ] Semua karakter vokal yang sama → satu entry di HashMap, satu vokal di PriorityQueue
- [ ] Frekuensi semua vokal sama → diurutkan berdasarkan indeks pertama kemunculan

______________________________________________________________________

## 📌 Key Takeaway

Pola **HashMap + PriorityQueue** adalah pendekatan klasik untuk soal "urutkan berdasarkan frekuensi dengan tiebreaker". HashMap menyimpan informasi yang dibutuhkan comparator, PriorityQueue mengurutkan berdasarkan kriteria tersebut. Kunci detail yang sering terlewat: gunakan `putIfAbsent` untuk menjaga **indeks pertama** kemunculan agar tidak tertimpa. 🎯

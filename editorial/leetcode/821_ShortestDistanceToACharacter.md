# 821. Shortest Distance to a Character

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Two Pointers, String
- **Link**: [Problem](https://leetcode.com/problems/shortest-distance-to-a-character/)
- **Solution**: [Code](../../leetcode/ShortestDistanceToACharacter.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `s` dan karakter `c` (dijamin muncul minimal sekali di `s`). Kembalikan array `answer` di mana `answer[i]` adalah **jarak terpendek** dari indeks `i` ke kemunculan `c` **terdekat** di `s` (jarak `= |i - j|`).

Contoh:

- `s = "loveleetcode", c = "e"` → `[3,2,1,0,1,0,0,1,2,2,1,0]`
  - `'e'` muncul di indeks `3, 5, 6, 11`. Untuk indeks `0`, `'e'` terdekat di indeks `3` → jarak `3`. Untuk indeks `4`, `'e'` di indeks `3` dan `5` sama-sama berjarak `1` (seri).
- `s = "aaab", c = "b"` → `[3,2,1,0]`

______________________________________________________________________

## 💡 Intuition

Pendekatan naif: untuk **tiap** indeks `i`, cari kemunculan `c` **terdekat** dengan scan seluruh string — ini `O(n²)` di kasus terburuk. Tapi ada cara **dua pass** yang jauh lebih efisien.

**Insight kuncinya:** jarak terpendek dari indeks `i` ke `c` **hanya** bisa datang dari salah satu dari dua arah — **kemunculan `c` terdekat di sebelah kiri**, atau **kemunculan `c` terdekat di sebelah kanan**. Kita bisa menghitung **masing-masing arah secara terpisah** dalam satu pass linear, lalu ambil **minimum** dari keduanya:

1. **Pass kiri-ke-kanan**: lacak posisi kemunculan `c` **terakhir yang sudah dilewati**. Untuk tiap indeks, jaraknya (sementara) adalah `i - posisi_terakhir`. Ini benar untuk arah "kiri" — tapi untuk indeks **sebelum** kemunculan `c` pertama, jaraknya jadi salah (karena belum ada `c` yang terlewati) — makanya butuh pass kedua untuk memperbaikinya.
1. **Pass kanan-ke-kiri**: lacak posisi kemunculan `c` **terakhir yang sudah dilewati** (kali ini dari kanan). Untuk tiap indeks, bandingkan jarak dari pass pertama dengan jarak dari arah kanan ini, ambil yang **lebih kecil**.

______________________________________________________________________

## 🔍 Approach

### Two-Pass: Kiri-ke-Kanan, Lalu Kanan-ke-Kiri dengan Minimum

**Pass 1 (kiri ke kanan):**

1. `pos = -n` — sentinel "sangat jauh di kiri", memastikan jarak awal untuk indeks sebelum kemunculan `c` pertama otomatis sangat besar (akan pasti diperbaiki di pass kedua).
1. Loop `i` dari `0` sampai `n-1`:
   - Kalau `s.charAt(i) == c` → update `pos = i` (kemunculan `c` terbaru yang ditemukan).
   - `ans[i] = i - pos` (jarak dari kemunculan `c` terakhir di sebelah kiri, atau jarak "sangat besar" kalau belum pernah ketemu `c` sama sekali).

**Pass 2 (kanan ke kiri, mulai dari kemunculan `c` terakhir hasil pass 1):**

1. `pos` sekarang bernilai **indeks kemunculan `c` paling kanan** di seluruh string (hasil akhir dari pass 1).
1. Loop `i` dari `pos` **turun** sampai `0`:
   - Kalau `s.charAt(i) == c` → update `pos = i`.
   - `ans[i] = Math.min(ans[i], pos - i)` — bandingkan dengan jarak dari sisi kanan, ambil yang lebih kecil.
1. Kembalikan `ans`.

**Catatan optimasi:** pass kedua **sengaja** dimulai dari `pos` (kemunculan `c` paling kanan), **bukan** dari `n-1`. Untuk indeks **setelah** kemunculan `c` paling kanan, tidak ada `c` lagi di sebelah kanan mereka — jadi jarak dari pass 1 (`i - pos`) sudah pasti **optimal** untuk indeks-indeks itu, tanpa perlu diperiksa ulang di pass 2.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------------------------ |
| **Time** | O(n) — dua pass linear (pass kedua bisa lebih pendek berkat optimasi di atas, tapi tetap `O(n)` di kasus terburuk) |
| **Space** | O(1) — di luar array hasil `ans` (yang memang bagian dari output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "loveleetcode", c = 'e'` (`n=12`, indeks `'e'`: `3,5,6,11`)

**Pass 1 (kiri ke kanan, `pos` mulai `-12`):**

| i | char | ==c? | pos | ans[i]=i-pos |
| --- | ---- | ------ | --- | ------------ |
| 0 | l | tidak | -12 | 12 |
| 1 | o | tidak | -12 | 13 |
| 2 | v | tidak | -12 | 14 |
| 3 | e | **ya** | 3 | 0 |
| 4 | l | tidak | 3 | 1 |
| 5 | e | **ya** | 5 | 0 |
| 6 | e | **ya** | 6 | 0 |
| 7 | t | tidak | 6 | 1 |
| 8 | c | tidak | 6 | 2 |
| 9 | o | tidak | 6 | 3 |
| 10 | d | tidak | 6 | 4 |
| 11 | e | **ya** | 11 | 0 |

Setelah pass 1: `pos=11` (kemunculan `'e'` paling kanan, kebetulan sama dengan indeks terakhir string ini).

**Pass 2 (kanan ke kiri, mulai dari `i=pos=11`):**

| i | char | ==c? | pos | ans[i]=min(ans[i], pos-i) |
| --- | ---- | ----- | --- | ------------------------- |
| 11 | e | ya | 11 | `min(0,0)=0` |
| 10 | d | tidak | 11 | `min(4,1)=1` |
| 9 | o | tidak | 11 | `min(3,2)=2` |
| 8 | c | tidak | 11 | `min(2,3)=2` |
| 7 | t | tidak | 11 | `min(1,4)=1` |
| 6 | e | ya | 6 | `min(0,0)=0` |
| 5 | e | ya | 5 | `min(0,0)=0` |
| 4 | l | tidak | 5 | `min(1,1)=1` |
| 3 | e | ya | 3 | `min(0,0)=0` |
| 2 | v | tidak | 3 | `min(14,1)=1` |
| 1 | o | tidak | 3 | `min(13,2)=2` |
| 0 | l | tidak | 3 | `min(12,3)=3` |

**Output: `[3,2,1,0,1,0,0,1,2,2,1,0]`** ✅ (cocok dengan output resmi soal)

______________________________________________________________________

**Input:** `s = "aaab", c = 'b'` (`n=4`)

**Pass 1:** `pos=-4` awal. `i0:a,ans=4`. `i1:a,ans=5`. `i2:a,ans=6`. `i3:b,pos=3,ans=0`.

Setelah pass 1: `pos=3` (`=n-1`, kebetulan indeks terakhir).

**Pass 2 (mulai `i=3`):** `i3:b,pos=3,ans=min(0,0)=0`. `i2:a,ans=min(6,1)=1`. `i1:a,ans=min(5,2)=2`. `i0:a,ans=min(4,3)=3`.

**Output: `[3,2,1,0]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `s[0] == c` (kemunculan `c` di indeks pertama) → pass 1 langsung memberi jarak `0` untuk indeks itu dan seterusnya sampai kemunculan berikutnya
- [ ] `c` cuma muncul **sekali** di seluruh `s` → kedua pass tetap bekerja normal, `pos` cuma pernah di-update sekali
- [ ] Indeks tepat di **tengah** dua kemunculan `c` yang jaraknya sama (seri, seperti indeks `4` di `"loveleetcode"`) → kedua pass menghasilkan jarak yang sama, `min` tidak mengubah apapun tapi tetap benar
- [ ] Kemunculan `c` **paling kanan** bukan di indeks terakhir string (misal `s="cabca"`, `c='c'`, kemunculan terakhir di indeks `3` bukan `4`) → pass 2 yang dimulai dari `pos` (bukan `n-1`) tetap benar, karena indeks setelah `pos` (di sini indeks `4`) sudah punya jarak optimal dari pass 1 saja
- [ ] String hanya berisi karakter `c` semua → `ans` seluruhnya `0`

______________________________________________________________________

## 🔧 Kenapa Sentinel `pos = -n` (Bukan Nilai Lain)?

```java
int pos = -n;
```

Sebelum kemunculan `c` **pertama** ditemukan di pass 1, kita butuh `ans[i] = i - pos` menghasilkan angka yang **cukup besar** sehingga **pasti** akan digantikan oleh nilai yang lebih kecil di pass 2 (via `Math.min`). Karena `i` maksimal `n-1` dan `pos` awal `-n`, nilai awal `ans[i]` untuk indeks-indeks ini akan berada di rentang `[n, 2n-1]` — selalu **lebih besar** dari jarak maksimum yang mungkin dalam string sepanjang `n` (yang paling banyak `n-1`). Ini menjamin nilai sementara ini **selalu** kalah saat dibandingkan di pass 2, tanpa perlu nilai sentinel spesial seperti `Integer.MAX_VALUE` yang berisiko overflow kalau dioperasikan lebih lanjut.

______________________________________________________________________

## 🔧 Kenapa Pass 2 Aman Dimulai dari `pos` (Bukan `n-1`)?

Ini optimasi kecil tapi valid secara matematis. Setelah pass 1 selesai, `pos` berisi **indeks kemunculan `c` paling kanan** di seluruh string. Untuk **setiap** indeks `i > pos`, **tidak ada** kemunculan `c` di sebelah kanannya (karena `pos` adalah yang paling kanan) — sehingga jarak terpendek untuk indeks-indeks itu **pasti** datang dari sisi kiri saja, dan nilai itu **sudah benar** dari pass 1 (`ans[i] = i - pos`, jarak ke `c` terdekat di kiri, yang juga satu-satunya kandidat). Memeriksa ulang indeks-indeks ini di pass 2 tidak akan pernah mengubah hasilnya — jadi melewatkannya (mulai loop dari `pos`, bukan `n-1`) aman dan sedikit menghemat iterasi.

______________________________________________________________________

## 🔧 Alternatif: Kumpulkan Semua Indeks `c`, Lalu Binary Search per Posisi

```java
public int[] shortestToChar(String s, char c) {
    List<Integer> positions = new ArrayList<>();
    for (int i = 0; i < s.length(); i++)
        if (s.charAt(i) == c) positions.add(i);

    int[] ans = new int[s.length()];
    for (int i = 0; i < s.length(); i++) {
        int idx = Collections.binarySearch(positions, i);
        if (idx >= 0) { ans[i] = 0; continue; }
        int insertionPoint = -(idx + 1);
        int distLeft = insertionPoint > 0 ? i - positions.get(insertionPoint - 1) : Integer.MAX_VALUE;
        int distRight = insertionPoint < positions.size() ? positions.get(insertionPoint) - i : Integer.MAX_VALUE;
        ans[i] = Math.min(distLeft, distRight);
    }
    return ans;
}
```

Versi ini mengumpulkan **semua** indeks kemunculan `c` dulu, lalu untuk tiap posisi `i`, memakai **binary search** untuk mencari kemunculan `c` terdekat (kiri dan kanan) secara `O(log k)` per query (`k` = jumlah kemunculan `c`). Totalnya `O(n log k)` — sedikit lebih lambat dibanding pendekatan two-pass `O(n)`, tapi pendekatan ini lebih fleksibel kalau butuh query berulang untuk banyak variasi `c` yang berbeda tanpa membangun ulang seluruh array tiap kali.

| Approach | Time | Space |
| -------------------------------- | ---------- | ------------- |
| Two-pass linear (kode asli) | O(n) | O(1) tambahan |
| Kumpulkan posisi + binary search | O(n log k) | O(k) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar klasik untuk pola **"two-pass, satu arah masing-masing, lalu ambil minimum"** — teknik yang sangat berguna ketika sebuah nilai bisa dipengaruhi dari **dua arah berlawanan** (kiri dan kanan), dan menghitung keduanya secara terpisah lebih sederhana daripada mencoba menghitung keduanya sekaligus. Perhatikan juga detail optimasi kecil (memulai pass kedua dari titik yang tepat, bukan dari ujung array) yang valid berkat pemahaman properti data yang sudah dihasilkan dari pass pertama. Pola two-pass serupa muncul di soal-soal seperti _Trapping Rain Water_ dan _Product of Array Except Self_. 🎯

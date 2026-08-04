# 3731. Find Missing Elements

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/find-missing-elements/)
- **Solution**: [Code](../../leetcode/FindMissingElements.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums` berisi integer-integer **unik**. Awalnya `nums` berisi **semua integer** dalam suatu rentang tertentu, tapi beberapa di antaranya sudah hilang. Yang pasti, integer **terkecil dan terbesar** dari rentang aslinya masih ada di `nums`.

Kembalikan daftar (terurut) semua integer yang **hilang** dari rentang tersebut. Kalau tidak ada yang hilang, kembalikan list kosong.

Contoh:

- `nums = [1,4,2,5]` → `[3]` (rentang asli `1..5`, hanya `3` yang hilang)
- `nums = [7,8,6,9]` → `[]` (rentang asli `6..9`, semua sudah lengkap)
- `nums = [5,1]` → `[2,3,4]` (rentang asli `1..5`, `2`, `3`, `4` hilang)

______________________________________________________________________

## 💡 Intuition

Karena `nums` **tidak dijamin terurut**, dan kita hanya tahu bahwa **nilai terkecil dan terbesar** yang benar-benar merepresentasikan rentang asli (bukan sekadar elemen pertama/terakhir di array — array bisa saja tidak terurut seperti pada `nums = [5,1]`), langkah pertama wajib adalah **cari `min` dan `max` sesungguhnya** dengan full scan, bukan asumsi dari posisi elemen.

Setelah rentang `[min, max]` diketahui, cukup bandingkan: **integer mana saja dalam rentang itu yang tidak muncul di `nums`**. Karena elemen di `nums` unik dan (berdasarkan constraint) nilainya terbatas dalam rentang kecil, cara paling efisien untuk cek keberadaan adalah **array boolean (lookup table)** — bukan `HashSet`, karena rentang nilainya sudah diketahui kecil sehingga bisa langsung dipetakan ke index array.

______________________________________________________________________

## 🔍 Approach

### Boolean Lookup Table + Cari Min/Max via Full Scan

1. Siapkan `seen` — array boolean berukuran cukup besar untuk menampung semua kemungkinan nilai `nums[i]` (di sini `101`, sesuai batas atas constraint nilai `nums[i]`).
1. Inisialisasi `min` dan `max` dari elemen pertama (`nums[0]`).
1. Loop sekali ke seluruh `nums`:
   - Update `min = Math.min(min, i)` dan `max = Math.max(max, i)`.
   - Tandai `seen[i] = true`.
1. Loop dari `min` sampai `max`: kalau `!seen[i]`, berarti `i` hilang dari rentang aslinya → tambahkan ke `ans`.
1. Kembalikan `ans` (otomatis terurut, karena loop berjalan naik dari `min` ke `max`).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------- |
| **Time** | O(n + range) — n untuk scan `nums`, `range = max-min` untuk cek yang hilang |
| **Space** | O(V) — V = ukuran tetap array `seen` (101), praktis O(1) karena konstan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,4,2,5]`

**Pass 1 — cari min/max & tandai seen:**

| i | min | max | seen[i] = true |
| --- | --- | --- | -------------- |
| 1 | 1 | 1 | seen[1] |
| 4 | 1 | 4 | seen[4] |
| 2 | 1 | 4 | seen[2] |
| 5 | 1 | 5 | seen[5] |

`min=1, max=5`

**Pass 2 — cek 1..5:**

| i | seen[i]? | ditambahkan? |
| --- | --------- | ------------ |
| 1 | true | tidak |
| 2 | true | tidak |
| 3 | **false** | **ya** |
| 4 | true | tidak |
| 5 | true | tidak |

**Output: `[3]`** ✅

______________________________________________________________________

**Input:** `nums = [5,1]`

**Pass 1:** `min` diupdate dari `5` (awal) → `1` (saat ketemu elemen `1`); `max` tetap `5`. `seen[5]=true, seen[1]=true`.

**Pass 2 — cek 1..5:**

| i | seen[i]? | ditambahkan? |
| --- | -------- | ------------ |
| 1 | true | tidak |
| 2 | false | ya |
| 3 | false | ya |
| 4 | false | ya |
| 5 | true | tidak |

**Output: `[2,3,4]`** ✅ — contoh ini penting karena membuktikan kode **tidak boleh** hanya mengandalkan `nums[0]` sebagai min dan elemen terakhir sebagai max; di sini elemen pertama (`5`) justru adalah nilai maksimum, bukan minimum.

______________________________________________________________________

**Input:** `nums = [7,8,6,9]`

- `min=6, max=9` setelah full scan.
- Cek `6,7,8,9` semuanya `seen=true`.

**Output: `[]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array tidak terurut dan elemen pertama **bukan** nilai minimum (`nums=[5,1]`) → wajib full scan untuk `min`/`max`, tidak boleh asumsi dari posisi elemen
- [ ] Tidak ada yang hilang (`nums=[7,8,6,9]`, rentang sudah lengkap) → return list kosong
- [ ] Array hanya 2 elemen (`min` dan `max` itu sendiri) → tetap tertangani, loop pencarian missing tetap jalan dari `min` ke `max`
- [ ] Nilai `nums[i]` mendekati batas atas `seen` (di sini 100) → penting ukuran array `seen` mengikuti constraint nilai maksimum `nums[i]`, kalau constraint berubah (misal sampai `10^5`), ukuran array ini juga harus disesuaikan atau diganti `HashSet`/menormalisasi index relatif ke `min`

______________________________________________________________________

## 🔧 Kenapa Harus Full Scan untuk Cari Min/Max, Bukan Asumsi dari Ujung Array?

Ada laporan bug nyata di komunitas LeetCode untuk soal ini (issue #33263): beberapa solusi yang diterima ternyata **hanya membandingkan elemen-elemen bersebelahan setelah di-sort**, atau berasumsi rentang ditentukan cuma dari elemen pertama/terakhir array tanpa scan penuh — pendekatan ini bisa salah kalau urutan array tidak mencerminkan urutan nilai. Kode di atas sudah aman dari jebakan ini karena `min` dan `max` **selalu** dihitung lewat perbandingan eksplisit (`Math.min`/`Math.max`) terhadap **setiap** elemen di `nums`, bukan diasumsikan dari posisi indeksnya.

______________________________________________________________________

## 🔧 Alternatif: HashSet (Tanpa Bergantung ke Batas Nilai Tetap)

```java
public List<Integer> findMissingElements(int[] nums) {
    List<Integer> ans = new ArrayList<>();
    Set<Integer> seen = new HashSet<>();
    int min = nums[0], max = nums[0];
    for (int i : nums) {
        min = Math.min(min, i);
        max = Math.max(max, i);
        seen.add(i);
    }
    for (int i = min; i <= max; i++)
        if (!seen.contains(i))
            ans.add(i);
    return ans;
}
```

Versi ini tidak bergantung pada batas nilai tetap (`101`), sehingga lebih fleksibel kalau constraint nilai `nums[i]` ternyata jauh lebih besar. Trade-off-nya: sedikit overhead dari hashing dibanding akses langsung ke index array boolean.

| Approach | Time | Space | Bergantung pada batas nilai tetap? |
| ------------------------------------- | ------------ | ------------ | ---------------------------------- |
| Boolean array `seen[101]` (kode asli) | O(n + range) | O(1) (tetap) | Ya |
| `HashSet<Integer>` | O(n + range) | O(n) | Tidak |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini menegaskan dua pelajaran penting: pertama, **jangan pernah asumsikan urutan atau posisi elemen array mencerminkan nilai min/max** — selalu scan eksplisit kalau array tidak dijamin terurut. Kedua, ketika rentang nilai yang mungkin **diketahui dan kecil**, array boolean sebagai lookup table biasanya lebih cepat dan sederhana dibanding `HashSet`, asalkan batas nilainya memang tetap sesuai constraint. Pola "tandai lalu scan rentang" ini juga muncul di soal-soal seperti _Missing Number_ dan _Find All Numbers Disappeared in an Array_. 🎯

# 4038. Count Integers Appearing in a Single Block

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/count-integers-appearing-in-a-single-block/)
- **Solution**: [Code](../../leetcode/CountIntegersAppearingInASingleBlock.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`. Sebuah integer `x` disebut **special** kalau **semua kemunculan** `x` di `nums` membentuk **satu blok contiguous** (bersebelahan tanpa terputus).

Kembalikan **jumlah** integer berbeda (distinct) yang special.

Contoh:

- `nums = [1,2,2,1]` → `1`
  - `1` muncul di indeks `0` dan `3` — dua blok terpisah → **bukan** special.
  - `2` muncul di indeks `[1,2]` — satu blok utuh → **special**.
- `nums = [3,3,1,2,2,1]` → `2`
  - `3` di `[0,1]` — satu blok → special.
  - `1` di indeks `2` dan `5` — dua blok terpisah → bukan special.
  - `2` di `[3,4]` — satu blok → special.

______________________________________________________________________

## 💡 Intuition

Kunci soal ini: kita cuma perlu peduli pada **titik awal tiap blok** (bukan tiap elemen individual). Sebuah "blok baru" dimulai setiap kali nilai saat ini **berbeda** dari nilai sebelumnya (atau ini elemen pertama array).

Untuk tiap kali sebuah nilai `v` **memulai blok baru**:

- Kalau `v` **belum pernah** memulai blok sebelumnya → ini kemungkinan besar `v` masih valid jadi special (untuk sementara), catat sebagai kandidat.
- Kalau `v` **sudah pernah** memulai blok sebelumnya (di titik lain, lebih awal di array) → berarti `v` muncul di **lebih dari satu blok terpisah** → `v` otomatis **gugur** dari daftar kandidat special (dihapus, kalau sebelumnya sempat dicatat).

Dua `HashSet` dipakai untuk dua tujuan berbeda: `seen` melacak **semua nilai yang pernah memulai blok** (dipakai untuk mendeteksi blok kedua/berikutnya), sementara `ans` melacak **kandidat yang masih valid sebagai special** (dihapus begitu ketahuan punya blok kedua).

______________________________________________________________________

## 🔍 Approach

### Deteksi Titik Awal Blok + Dua HashSet (Seen & Kandidat)

1. Siapkan `seen` (semua nilai yang pernah memulai blok) dan `ans` (kandidat nilai yang masih special sejauh ini), keduanya `HashSet`.
1. Loop `i` dari `0` sampai akhir `nums`:
   - Cek apakah posisi `i` ini adalah **awal blok baru**: `i == 0` (elemen pertama) **atau** `nums[i] != nums[i-1]` (beda dari elemen sebelumnya).
   - Kalau ya (ini awal blok baru untuk `nums[i]`):
     - Kalau `nums[i]` **sudah ada** di `seen` (pernah memulai blok sebelumnya) → hapus dari `ans` (`ans.remove(nums[i])`), karena sekarang terbukti muncul di lebih dari satu blok.
     - Kalau **belum** → tambahkan ke `seen` **dan** `ans` (kandidat baru yang masih valid).
1. Kembalikan `ans.size()` — jumlah nilai yang **tidak pernah** terhapus, alias yang seluruh kemunculannya benar-benar cuma satu blok.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh array |
| **Space** | O(k) — k = jumlah nilai unik di `nums`, untuk `seen` dan `ans` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,2,2,1]`

| i | nums[i] | Awal blok baru? | seen sebelum | Aksi | seen sesudah | ans sesudah |
| --- | ------- | --------------- | ------------ | ------------------------------------------ | ------------ | ----------- |
| 0 | 1 | ya (`i==0`) | `{}` | belum pernah → `seen.add(1)`, `ans.add(1)` | `{1}` | `{1}` |
| 1 | 2 | ya (`2 != 1`) | `{1}` | belum pernah → `seen.add(2)`, `ans.add(2)` | `{1,2}` | `{1,2}` |
| 2 | 2 | tidak (`2==2`) | — | (skip, bukan awal blok) | `{1,2}` | `{1,2}` |
| 3 | 1 | ya (`1 != 2`) | `{1,2}` | **sudah pernah** → `ans.remove(1)` | `{1,2}` | `{2}` |

`ans = {2}` → **Output: `1`** ✅

______________________________________________________________________

**Input:** `nums = [3,3,1,2,2,1]`

| i | nums[i] | Awal blok baru? | Aksi | ans sesudah |
| --- | ------- | --------------- | ---------------------------- | ----------- |
| 0 | 3 | ya | belum pernah → tambah | `{3}` |
| 1 | 3 | tidak (`3==3`) | skip | `{3}` |
| 2 | 1 | ya (`1!=3`) | belum pernah → tambah | `{3,1}` |
| 3 | 2 | ya (`2!=1`) | belum pernah → tambah | `{3,1,2}` |
| 4 | 2 | tidak (`2==2`) | skip | `{3,1,2}` |
| 5 | 1 | ya (`1!=2`) | **sudah pernah** → hapus `1` | `{3,2}` |

`ans = {3,2}` → **Output: `2`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua elemen sama (`[5,5,5,5]`) → hanya satu "awal blok" (di `i=0`), tidak pernah terhapus → `1` (satu-satunya nilai, dan dia special)
- [ ] Semua elemen berbeda (`[1,2,3,4]`) → tiap elemen jadi blok sendiri-sendiri, tidak ada yang punya blok kedua → semua special, hasil `= nums.length`
- [ ] Nilai yang blok-nya terpisah oleh **banyak** nilai lain di antaranya (bukan cuma satu) → tetap terdeteksi dengan benar, karena deteksi "awal blok" tidak peduli seberapa jauh jaraknya, cukup bandingkan dengan elemen **tepat sebelumnya**
- [ ] Nilai yang punya **lebih dari dua** blok terpisah (misal `[1,2,1,3,1]`, nilai `1` muncul di tiga blok) → begitu blok kedua terdeteksi, langsung dihapus dari `ans`; blok ketiga dan seterusnya tidak perlu penanganan khusus tambahan (`ans.remove` pada nilai yang sudah tidak ada di `ans` cukup jadi no-op, tidak error)
- [ ] Array satu elemen → otomatis satu blok, satu nilai special, hasil `1`

______________________________________________________________________

## 🔧 Kenapa Cukup Bandingkan dengan Elemen Sebelumnya (Bukan Cek Semua Elemen Lain)?

Definisi "blok contiguous" berarti kemunculan suatu nilai harus **bersebelahan tanpa jeda**. Untuk mendeteksi apakah posisi `i` adalah **awal** dari sebuah blok baru, cukup cek: **apakah elemen tepat sebelumnya berbeda?** Kalau ya, ini pasti awal blok baru (baik itu blok pertama nilai ini, atau blok kedua/ketiga dst). Kita **tidak perlu** melihat elemen-elemen yang lebih jauh ke belakang, karena definisi contiguous block hanya peduli pada **kesinambungan langsung** dengan tetangga terdekat — begitu ada satu saja elemen berbeda yang menyela, blok otomatis dianggap terputus di situ.

______________________________________________________________________

## 🔧 Alternatif: HashMap Simpan Pasangan First/Last Occurrence + Frequency

```java
public int countSpecialIntegers(int[] nums) {
    Map<Integer, int[]> info = new HashMap<>(); // [firstIdx, lastIdx, count]
    for (int i = 0; i < nums.length; i++) {
        info.computeIfAbsent(nums[i], k -> new int[]{i, i, 0});
        int[] data = info.get(nums[i]);
        data[1] = i;
        data[2]++;
    }

    int ans = 0;
    for (int[] data : info.values())
        if (data[1] - data[0] + 1 == data[2]) // rentang penuh terisi tanpa jeda
            ans++;
    return ans;
}
```

Versi ini memakai insight berbeda: sebuah nilai `v` benar-benar satu blok contiguous **jika dan hanya jika** jumlah kemunculannya (`count`) sama dengan **rentang** antara kemunculan pertama dan terakhirnya (`lastIdx - firstIdx + 1`). Kalau ada jeda di antaranya (kemunculan `v` terpisah), rentangnya pasti **lebih besar** dari jumlah kemunculan sebenarnya. Pendekatan ini lebih ke arah _"pattern matching pada rentang"_, dibanding pendekatan asli yang _"deteksi kejadian saat traversal"_.

| Approach | Time | Space | Insight Utama |
| ------------------------------------------- | ---- | ----- | ------------------------------------ |
| Deteksi awal blok + dua HashSet (kode asli) | O(n) | O(k) | Blok kedua = otomatis tidak special |
| First/last occurrence + count == range | O(n) | O(k) | Rentang penuh tanpa jeda = satu blok |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola umum **"deteksi batas blok/segment saat traversal"** — alih-alih memproses tiap elemen secara individual, cukup fokus pada **titik transisi** (di sini: awal blok baru), karena itulah momen yang benar-benar membawa informasi baru. Kombinasi dua `HashSet` dengan peran berbeda (`seen` untuk deteksi historis, `ans` untuk kandidat yang masih valid) adalah pola yang berguna ketika sebuah kandidat bisa "digugurkan" berdasarkan kejadian di masa lalu. Pola serupa (melacak blok/segmen kontiguous) juga relevan untuk soal-soal seperti _Encode and Decode Strings_ atau analisis run-length encoding pada umumnya. 🎯

# 830. Positions of Large Groups

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/positions-of-large-groups/)
- **Solution**: [Code](../../leetcode/PositionsOfLargeGroups.java)

______________________________________________________________________

## 📄 Problem Summary

String `s` terdiri dari beberapa **grup karakter berurutan yang sama** (mirip run-length encoding). Sebuah grup disebut **large group** kalau panjangnya **3 atau lebih**. Kembalikan daftar `[start, end]` (indeks awal dan akhir) untuk **setiap** large group, terurut sesuai kemunculannya di `s`.

Contoh:

- `s = "abbxxxxzyy"` → `[[3,6]]` (grup `x` di indeks `3`–`6`, panjang `4`)
- `s = "abc"` → `[]` (semua grup cuma panjang `1`)

______________________________________________________________________

## 💡 Intuition

Ini soal **deteksi batas grup** — pola yang sama seperti yang sudah dibahas di _Count Binary Substrings_ dan _Count Integers Appearing in a Single Block_: lacak **titik awal** grup yang sedang berjalan, dan begitu ketemu karakter yang **berbeda** dari awal grup itu, grup dianggap **selesai** — saat itulah kita cek apakah grup ini cukup panjang untuk dicatat.

Dua pointer yang dipakai:

- `start` — indeks awal grup yang sedang diproses.
- `end` — pointer yang berjalan maju, dipakai untuk membandingkan karakter saat ini dengan karakter di `start`.

Selama `s.charAt(start) == s.charAt(end)`, kita masih berada di grup yang sama — `end` terus maju. Begitu berbeda, grup `[start, end-1]` baru saja selesai (karena `end` sekarang menunjuk ke karakter **pertama grup berikutnya**), dan `start` digeser ke `end` untuk memulai grup baru.

______________________________________________________________________

## 🔍 Approach

### Two Pointers — Deteksi Batas Grup, Reset `start` Saat Karakter Berubah

1. `start = 0`. Loop `end` dari `0` sampai `s.length() - 1`:
   - Kalau `s.charAt(start) != s.charAt(end)` (karakter berubah, grup lama berakhir tepat sebelum `end`):
     - Kalau panjang grup lama (`end - start`) `>= 3` → tambahkan `[start, end-1]` ke `ans`.
     - Geser `start = end` (mulai grup baru dari sini).
1. Setelah loop selesai, **grup terakhir** belum sempat diperiksa di dalam loop (karena grup itu tidak pernah "diputus" oleh karakter berbeda — string sudah habis duluan) → cek sekali lagi di luar loop: kalau `end - start >= 3` → tambahkan `[start, end-1]`.
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh string |
| **Space** | O(k) — k = jumlah large group yang ditemukan (untuk `ans`) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "abbxxxxzyy"` (indeks 0–9)

| end | s[start] vs s[end] | Beda? | Aksi | start sesudah |
| --- | ------------------ | ----- | --------------------------- | ------------- |
| 0 | a vs a | tidak | — | 0 |
| 1 | a vs b | ya | `1-0=1<3`, tidak ditambah | 1 |
| 2 | b vs b | tidak | — | 1 |
| 3 | b vs x | ya | `3-1=2<3`, tidak ditambah | 3 |
| 4 | x vs x | tidak | — | 3 |
| 5 | x vs x | tidak | — | 3 |
| 6 | x vs x | tidak | — | 3 |
| 7 | x vs z | ya | `7-3=4>=3` → tambah `[3,6]` | 7 |
| 8 | z vs y | ya | `8-7=1<3`, tidak ditambah | 8 |
| 9 | y vs y | tidak | — | 8 |

Loop selesai (`end` mencapai `10`, keluar dari kondisi `end < s.length()`). Cek grup terakhir: `end(10) - start(8) = 2 < 3` → tidak ditambah.

**Output: `[[3,6]]`** ✅

______________________________________________________________________

**Input:** `s = "abc"`

| end | s[start] vs s[end] | Beda? | Aksi | start sesudah |
| --- | ------------------ | ----- | ------------------------- | ------------- |
| 0 | a vs a | tidak | — | 0 |
| 1 | a vs b | ya | `1-0=1<3`, tidak ditambah | 1 |
| 2 | b vs c | ya | `2-1=1<3`, tidak ditambah | 2 |

Loop selesai. Cek grup terakhir: `end(3)-start(2)=1<3` → tidak ditambah.

**Output: `[]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Seluruh string adalah satu grup besar (misal `"aaaa"`) → tidak pernah ada karakter berbeda di dalam loop, grup ini cuma tertangkap lewat pengecekan **setelah** loop selesai
- [ ] String satu karakter (`"a"`) → loop cuma satu iterasi tanpa perbedaan terdeteksi, lalu dicek di luar loop: `1-0=1<3`, hasil `[]`
- [ ] Tidak ada grup yang mencapai panjang `3` sama sekali → `ans` tetap kosong
- [ ] Beberapa large group terpisah oleh grup-grup kecil (seperti `"aaabbbccccd"`) → tiap large group terdeteksi independen saat karakter berubah, tanpa saling mengganggu
- [ ] Large group tepat di **akhir** string (tidak diikuti karakter lain) → butuh pengecekan tambahan **setelah** loop (baris terakhir kode), karena grup ini tidak pernah "diputus" oleh perbedaan karakter di dalam loop

______________________________________________________________________

## 🔧 Kenapa Butuh Pengecekan Tambahan Setelah Loop Selesai?

Di dalam loop, sebuah grup **hanya** diperiksa dan dicatat saat kita menemukan **karakter berbeda** yang menandai grup itu berakhir. Tapi kalau grup **terakhir** dalam string kebetulan berlanjut sampai karakter paling akhir (tidak pernah "diputus" karena stringnya sudah habis duluan), grup itu **tidak akan pernah** dicek di dalam loop — persis seperti pola yang sudah dibahas di soal _Find All Numbers Disappeared in an Array II_. Pengecekan `if (end - start >= 3)` di luar loop inilah yang menangkap grup terakhir ini, memastikan **tidak ada** grup yang terlewat hanya karena posisinya kebetulan di ujung string.

______________________________________________________________________

## 🔧 Alternatif: Single Pass dengan Deteksi "Awal Grup" (Bukan "Akhir Grup")

```java
public List<List<Integer>> largeGroupPositions(String s) {
    List<List<Integer>> ans = new ArrayList<>();
    int n = s.length();
    int i = 0;
    while (i < n) {
        int j = i;
        while (j < n && s.charAt(j) == s.charAt(i)) j++;
        if (j - i >= 3) ans.add(List.of(i, j - 1));
        i = j;
    }
    return ans;
}
```

Versi ini memakai dua loop bersarang (`while` di dalam `while`), tapi setiap karakter tetap **hanya diproses sekali** secara total (`j` terus maju tanpa pernah mundur), jadi tetap `O(n)`. Bedanya dengan kode asli: di sini grup **langsung** diperiksa panjangnya begitu ketemu ujungnya (`j` berhenti), tanpa perlu pengecekan tambahan terpisah di luar loop — karena loop `while (i < n)` secara alami mencakup grup terakhir juga.

| Approach | Time | Space | Butuh Pengecekan Tambahan di Luar Loop? |
| ---------------------------------------------- | ---- | ----- | --------------------------------------- |
| Two pointers, deteksi saat berubah (kode asli) | O(n) | O(k) | Ya |
| Nested while, deteksi ujung grup langsung | O(n) | O(k) | Tidak |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pola umum **"deteksi grup/segmen kontiguous"** yang sudah beberapa kali muncul dalam bentuk berbeda (_Count Binary Substrings_, _Count Integers Appearing in a Single Block_). Kuncinya selalu sama: lacak titik awal grup, deteksi kapan grup berakhir (baik lewat "karakter berubah" atau "string habis"), dan **jangan lupa** menangani grup terakhir secara eksplisit kalau logika utamamu hanya memicu pemeriksaan saat ada transisi karakter — karena grup terakhir sering kali tidak pernah mengalami transisi itu. 🎯

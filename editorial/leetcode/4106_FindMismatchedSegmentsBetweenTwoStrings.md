# 4106. Find Mismatched Segments Between Two Strings

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: String, Two Pointers, Simulation
- **Link**: [Problem](https://leetcode.com/problems/find-mismatched-segments-between-two-strings/)
- **Solution**: [Code](../../leetcode/FindMismatchedSegmentsBetweenTwoStrings.java)

---

## 📄 Problem Summary

Diberikan dua string `s1` dan `s2` yang **sama panjang**. Sebuah **mismatched segment** adalah rentang indeks yang **bersambung dan maksimal** di mana karakter `s1` dan `s2` **berbeda di setiap indeks**. Kembalikan, untuk tiap segmen itu, pasangan `[sub1, sub2]` berupa substring yang sesuai dari `s1` dan `s2`, berurutan menurut indeks awal. Kalau tidak ada segmen, kembalikan array kosong.

Constraint: `1 <= s1.length == s2.length <= 100`, hanya huruf kecil.

Contoh:

- `s1 = "earth", s2 = "empty"` → `[["ar","mp"],["h","y"]]` (indeks `1` dan `2` berbeda, indeks `3` sama (`t`), indeks `4` berbeda)
- `s1 = "action", s2 = "nation"` → `[["ac","na"]]` (hanya indeks `0` dan `1` yang berbeda)
- `s1 = "hot", s2 = "hot"` → `[]` (tidak ada perbedaan)

---

## 💡 Intuition

Soal ini adalah **deteksi segmen berurutan** yang sama pola dengan *Positions of Large Groups*: telusuri string dari kiri ke kanan, dan kelompokkan indeks-indeks yang memenuhi suatu kondisi (di sini "karakter `s1` dan `s2` berbeda") menjadi **segmen maksimal**.

Satu segmen dimulai saat kita menemukan indeks yang berbeda setelah indeks yang sama (atau di awal string), dan berakhir tepat sebelum indeks berikutnya yang sama (atau di akhir string). Selama kita berada di dalam segmen, kumpulkan karakter `s1` dan `s2` di indeks itu ke dua `StringBuilder`. Begitu bertemu indeks yang **sama**, segmen yang sedang dikumpulkan (kalau ada) selesai dan disimpan ke hasil.

Karena indeks yang sama **memutus** segmen, dua rentang perbedaan yang dipisahkan oleh bahkan **satu** indeks yang sama dihitung sebagai dua segmen terpisah (seperti pada `"earth"` dan `"empty"`, di mana indeks `3` memisahkan `"ar"/"mp"` dari `"h"/"y"`).

---

## 🔍 Approach

### Kumpulkan Karakter Berbeda di Dua `StringBuilder`, Simpan Saat Terputus

1. Siapkan `ans` (daftar hasil) dan dua `StringBuilder`: `sb1` dan `sb2`.
2. Untuk tiap indeks `i` dari `0` sampai `n - 1`:
   - Kalau `s1[i] != s2[i]` → tambahkan `s1[i]` ke `sb1` dan `s2[i]` ke `sb2` (segmen berlanjut atau baru dimulai).
   - Kalau sama dan `sb1` tidak kosong → segmen sebelumnya selesai: tambahkan `[sb1, sb2]` ke `ans`, lalu kosongkan keduanya.
3. Setelah loop, kalau `sb1` masih berisi sesuatu → ada segmen yang berlanjut sampai akhir string, tambahkan juga ke `ans`.
4. Kembalikan `ans`.

Langkah ketiga **wajib**: segmen yang berakhir di indeks terakhir tidak pernah "terputus" oleh indeks yang sama di dalam loop, jadi tidak akan tersimpan tanpa pengecekan setelah loop (pola yang sama dengan *Positions of Large Groups* dan *Find All Numbers Disappeared in an Array II*).

---

## 🧮 Complexity

|                 |                                                                        |
| --------------- | ---------------------------------------------------------------------- |
| **Time**  | O(n) — satu kali pass, tiap karakter ditambahkan paling banyak sekali |
| **Space** | O(n) — untuk hasil (substring-substring yang disimpan)                |

---

## 🧪 Dry Run

**Input:** `s1 = "earth"`, `s2 = "empty"`

| i | s1[i] | s2[i] | Sama? | sb1      | sb2      | Aksi                                    |
| - | ----- | ----- | ----- | -------- | -------- | --------------------------------------- |
| 0 | e     | e     | ya    | `""`   | `""`   | `sb1` kosong, tidak ada yang disimpan |
| 1 | a     | m     | tidak | `"a"`  | `"m"`  | mulai segmen                            |
| 2 | r     | p     | tidak | `"ar"` | `"mp"` | segmen berlanjut                        |
| 3 | t     | t     | ya    | `""`   | `""`   | simpan `["ar","mp"]`, kosongkan       |
| 4 | h     | y     | tidak | `"h"`  | `"y"`  | mulai segmen baru                       |

Setelah loop: `sb1 = "h"` tidak kosong → simpan `["h","y"]`.

**Output: `[["ar","mp"],["h","y"]]`** ✅

---

**Input:** `s1 = "action"`, `s2 = "nation"`

Indeks `0` (`a` vs `n`) dan `1` (`c` vs `a`) berbeda → `sb1 = "ac"`, `sb2 = "na"`. Indeks `2` (`t`, `t`) sama → simpan `["ac","na"]`. Indeks `3`, `4`, `5` (`i`, `o`, `n`) semuanya sama dan `sb1` kosong, jadi tidak ada yang disimpan. Setelah loop `sb1` kosong.

**Output: `[["ac","na"]]`** ✅

---

**Input:** `s1 = "hot"`, `s2 = "hot"`

Tidak ada indeks yang berbeda, `sb1` selalu kosong.

**Output: `[]`** ✅

---

## ⚠️ Edge Cases

- [ ] String identik → hasil kosong (`[]`)
- [ ] Semua indeks berbeda (`"abc"` dan `"xyz"`) → satu segmen yang mencakup seluruh string, `[["abc","xyz"]]`; tersimpan lewat pengecekan setelah loop
- [ ] Perbedaan hanya di indeks terakhir (`"ab"` dan `"ac"`) → juga hanya tersimpan lewat pengecekan setelah loop
- [ ] Perbedaan hanya di indeks pertama → tersimpan di dalam loop begitu bertemu indeks yang sama
- [ ] Dua rentang perbedaan dipisahkan oleh **satu** indeks yang sama → dua segmen terpisah, bukan satu
- [ ] String satu karakter: `"a"` dan `"b"` → `[["a","b"]]`; `"a"` dan `"a"` → `[]`
- [ ] Karakter berbeda tapi huruf yang sama muncul di tempat lain (misal `s1[1] = 'a'` dan `s2[3] = 'a'`) → tidak relevan, yang dibandingkan hanya karakter di indeks yang sama

---

## 🔧 Catatan: Variabel yang Tidak Terpakai

Di kode ada deklarasi `boolean isMismatchedContiguous = false;` yang **tidak pernah dipakai**. Ia aman (tidak mengubah hasil), tapi sebaiknya dihapus supaya kode lebih bersih dan tidak membingungkan pembaca. Pelacakan "sedang berada di dalam segmen atau tidak" sudah ditangani secara implisit lewat `sb1.length() > 0`.

Di bagian setelah loop, pemanggilan `sb1.setLength(0)` dan `sb2.setLength(0)` juga tidak diperlukan, karena keduanya tidak dipakai lagi setelahnya.

---

## 🔧 Alternatif: Dua Pointer dengan `substring`

Daripada mengumpulkan karakter satu per satu ke `StringBuilder`, kita bisa menandai **indeks awal** dan **indeks akhir** segmen, lalu memotong dengan `substring`:

```java
public List<List<String>> findMismatchedSegments(String s1, String s2) {
    List<List<String>> ans = new ArrayList<>();
    int n = s1.length();
    int i = 0;
    while (i < n) {
        if (s1.charAt(i) == s2.charAt(i)) {     // sama: lewati
            i++;
            continue;
        }
        int j = i;                               // i adalah awal segmen
        while (j < n && s1.charAt(j) != s2.charAt(j))
            j++;                                 // perluas sampai ketemu indeks yang sama atau akhir
        ans.add(List.of(s1.substring(i, j), s2.substring(i, j)));
        i = j;                                   // lanjut dari indeks setelah segmen
    }
    return ans;
}
```

Cek `"earth"` dan `"empty"`: `i = 0` sama → `i = 1`. Di `i = 1` berbeda, `j` maju ke `3` (indeks `3` sama), simpan `["ar","mp"]`, `i = 3`. Indeks `3` sama → `i = 4`. Di `i = 4` berbeda, `j` maju ke `5` (akhir string), simpan `["h","y"]`, `i = 5`, selesai ✅.

Keuntungan versi ini: **tidak butuh pengecekan terpisah setelah loop**, karena loop dalam `j` otomatis berhenti di akhir string, jadi segmen terakhir sudah ikut tersimpan. Tidak ada `StringBuilder`, dan kodenya langsung mencerminkan definisi "rentang maksimal yang bersambung".

| Approach                                   | Time | Space | Catatan                                    |
| ------------------------------------------ | ---- | ----- | ------------------------------------------ |
| `StringBuilder` dengan flush (kode asli) | O(n) | O(n)  | Perlu flush tambahan di akhir              |
| Dua pointer dan `substring`              | O(n) | O(n)  | Tanpa flush akhir, lebih dekat ke definisi |

---

## 📌 Key Takeaway

Soal ini adalah pola umum **"kelompokkan indeks bersambung yang memenuhi suatu kondisi"**, yang juga muncul di *Positions of Large Groups*, *Count Binary Substrings*, dan *Find All Numbers Disappeared in an Array II*. Perhatikan jebakan khasnya: segmen yang berakhir di **ujung string** tidak pernah diputus oleh kondisi di dalam loop, jadi butuh penanganan tambahan setelah loop, atau gunakan struktur dua pointer yang menanganinya secara alami. 🎯

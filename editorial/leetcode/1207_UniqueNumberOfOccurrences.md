# 1207. Unique Number of Occurrences

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/unique-number-of-occurrences/)
- **Solution**: [Code](../../leetcode/UniqueNumberOfOccurrences.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array integer `arr`. Kembalikan `true` kalau **jumlah kemunculan** tiap nilai di `arr` **berbeda-beda (unik)** satu sama lain — tidak ada dua nilai berbeda yang punya frekuensi kemunculan yang sama.

Contoh:

- `arr = [1,2,2,1,1,3]` → `true`
  - `1` muncul `3` kali, `2` muncul `2` kali, `3` muncul `1` kali — ketiga frekuensi ini (`3,2,1`) semuanya berbeda.
- `arr = [1,2]` → `false`
  - `1` muncul `1` kali, `2` muncul `1` kali — frekuensinya **sama** (`1` dan `1`), jadi tidak unik.
- `arr = [-3,0,1,-3,1,1,1,-3,10,0]` → `true`

______________________________________________________________________

## 💡 Intuition

Soal ini terdiri dari **dua lapis** pengecekan "keunikan" yang berbeda level:

1. **Hitung frekuensi tiap nilai** — ini standar, pakai `HashMap<nilai, frekuensi>`.
1. **Cek apakah frekuensi-frekuensi itu sendiri unik** — di sinilah soal ini sedikit "meta": kita butuh himpunan kedua (`HashSet<frekuensi>`) untuk mendeteksi apakah ada **frekuensi yang terulang** di antara nilai-nilai yang berbeda.

Strateginya: loop semua **nilai frekuensi** (bukan nilai asli array) hasil dari `HashMap`, dan untuk tiap frekuensi, cek apakah dia **sudah pernah muncul sebelumnya** di `HashSet`. Begitu ketemu frekuensi yang sudah ada (duplikat), langsung `false`. Kalau semua frekuensi berhasil dimasukkan ke `HashSet` tanpa ada yang duplikat, berarti semuanya unik → `true`.

______________________________________________________________________

## 🔍 Approach

### Dua Tingkat HashMap/HashSet: Frekuensi Nilai, Lalu Keunikan Frekuensi

1. Bangun `map` — `HashMap<Integer, Integer>` berisi frekuensi tiap nilai di `arr`.
1. Siapkan `uniqueOccurrences` — `HashSet<Integer>` kosong (untuk melacak frekuensi mana saja yang sudah pernah ditemui).
1. Loop tiap **nilai frekuensi** (`map.values()`):
   - Kalau frekuensi ini **sudah ada** di `uniqueOccurrences` → ada dua nilai berbeda dengan frekuensi sama → `return false`.
   - Kalau belum → tambahkan ke `uniqueOccurrences`.
1. Kalau loop selesai tanpa pernah `return false` → semua frekuensi unik → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(n) — n = `arr.length`, untuk membangun `map` dan mengecek `uniqueOccurrences` |
| **Space** | O(n) — di kasus terburuk (semua elemen unik), `map` dan `uniqueOccurrences` sama-sama bisa menyimpan sampai `n` entri |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `arr = [1,2,2,1,1,3]`

**Bangun `map`:** `{1:3, 2:2, 3:1}`

**Cek keunikan frekuensi:**

| Frekuensi (dari map.values()) | Ada di uniqueOccurrences? | Aksi |
| ----------------------------- | ------------------------- | --------------------- |
| 3 | tidak | tambahkan → `{3}` |
| 2 | tidak | tambahkan → `{3,2}` |
| 1 | tidak | tambahkan → `{3,2,1}` |

Semua frekuensi berhasil ditambahkan tanpa duplikat.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `arr = [1,2]`

**Bangun `map`:** `{1:1, 2:1}`

**Cek keunikan frekuensi:**

| Frekuensi | Ada di uniqueOccurrences? | Aksi |
| ---------------- | ------------------------- | ----------------- |
| 1 (dari nilai 1) | tidak | tambahkan → `{1}` |
| 1 (dari nilai 2) | **ya** (sudah ada `1`) | **return false** |

**Output: `false`** ✅ — meski `1` dan `2` adalah **nilai** yang berbeda di array asli, **frekuensi** kemunculan mereka sama-sama `1`, dan itulah yang membuat hasilnya `false`.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua nilai di `arr` unik (tidak ada duplikat sama sekali) → semua frekuensi bernilai `1`, dan begitu ada **lebih dari satu** nilai unik, frekuensi `1` akan terdeteksi duplikat (`false`) — **kecuali** kalau `arr` cuma punya **satu** nilai unik saja
- [ ] `arr` cuma berisi **satu** nilai berulang (misal `[5,5,5]`) → cuma ada satu frekuensi (`3`), otomatis unik (tidak ada yang dibandingkan) → `true`
- [ ] `arr` cuma satu elemen (`[7]`) → frekuensi `{7:1}`, cuma satu frekuensi untuk dicek → `true`
- [ ] Nilai negatif di `arr` (`[-3,0,1,...]`) → tidak masalah, `HashMap<Integer,Integer>` menangani nilai negatif sama seperti nilai positif
- [ ] Dua nilai dengan frekuensi sama **persis** di titik manapun dalam array (bukan cuma di akhir) → tetap terdeteksi, karena **seluruh** `map.values()` diperiksa, bukan cuma sebagian

______________________________________________________________________

## 🔧 Kenapa Butuh Dua Struktur Data Berbeda (HashMap **dan** HashSet)?

Ini poin penting yang membedakan soal ini dari soal "frequency counting" biasa. `HashMap` dibutuhkan untuk **menghitung** — tugasnya mentransformasi array mentah jadi pasangan "nilai → berapa kali muncul". Tapi pertanyaan sebenarnya di soal ini **bukan** soal nilai aslinya sama sekali — ini soal apakah **angka-angka frekuensi itu sendiri** (hasil dari `HashMap`) mengandung duplikat. Untuk mendeteksi duplikat di antara kumpulan angka, `HashSet` adalah alat yang tepat (cek keanggotaan `O(1)`, otomatis menolak penambahan ulang). Jadi `HashMap` menjawab "berapa kali nilai `x` muncul", sementara `HashSet` menjawab pertanyaan **meta**: "apakah ada dua nilai `x` dan `y` yang frekuensinya kebetulan sama".

______________________________________________________________________

## 🔧 Alternatif: Bandingkan Ukuran HashMap dan HashSet Langsung

```java
public boolean uniqueOccurrences(int[] arr) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int x : arr) freq.merge(x, 1, Integer::sum);

    Set<Integer> uniqueFreqs = new HashSet<>(freq.values());
    return freq.size() == uniqueFreqs.size();
}
```

Versi ini memakai insight yang sedikit berbeda: alih-alih loop manual dan `return false` di tengah jalan, bangun `HashSet` langsung dari **seluruh** `freq.values()` sekaligus (constructor `HashSet` otomatis membuang duplikat). Kalau **tidak ada** duplikat frekuensi, ukuran `uniqueFreqs` akan **sama** dengan jumlah nilai unik di `freq` (`freq.size()`); kalau **ada** duplikat, ukuran `uniqueFreqs` akan **lebih kecil** (karena beberapa frekuensi "digabung" jadi satu entri di set). Perbandingan ukuran ini jadi pengganti pengecekan manual satu per satu.

| Approach | Time | Space | Gaya |
| -------------------------------------- | ---- | ----- | ------------------------------------ |
| Loop manual + early return (kode asli) | O(n) | O(n) | Imperatif, bisa berhenti lebih awal |
| Bandingkan ukuran set vs map | O(n) | O(n) | Deklaratif, selalu proses semua dulu |

Kode asli punya keunggulan **early exit** (berhenti begitu ketemu duplikat pertama), sementara versi kedua selalu memproses **seluruh** data dulu sebelum membandingkan ukuran — untuk constraint soal ini (`n<=1000`), perbedaannya tidak signifikan.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh menarik dari **"duplikat pada level meta"** — bukan duplikat nilai asli, tapi duplikat pada **hasil agregasi** (frekuensi) dari nilai-nilai itu. Polanya tetap sama seperti deteksi duplikat biasa (`HashSet` untuk cek keanggotaan), cuma diterapkan **satu tingkat lebih abstrak**: pertama agregasi (`HashMap` untuk frekuensi), baru deteksi duplikat di atas hasil agregasi itu (`HashSet` untuk frekuensi). Pola "agregasi lalu deteksi duplikat pada hasil agregasi" ini berguna untuk soal-soal lain yang bertanya tentang sifat distribusi data, bukan cuma nilai individual. 🎯

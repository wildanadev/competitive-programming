# 717. 1-bit and 2-bit Characters

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/1-bit-and-2-bit-characters/)
- **Solution**: [Code](../../leetcode/OneBitAndTwoBitCharacters.java)

______________________________________________________________________

## 📄 Problem Summary

Ada dua jenis karakter yang dienkode dalam bit:

- Karakter **1-bit**: selalu `0`.
- Karakter **2-bit**: `10` atau `11`.

Diberikan array `bits` yang **selalu diakhiri dengan `0`**. Array ini adalah hasil enkode dari serangkaian karakter (bisa campuran 1-bit dan 2-bit) yang disambung tanpa pemisah. Tentukan apakah **karakter terakhir** dalam enkode ini **wajib** berupa karakter 1-bit.

Contoh:

- `bits = [1,0,0]` → `true` — parsing: `10` (2-bit) lalu `0` (1-bit). Karakter terakhir adalah `0` yang berdiri sendiri (1-bit).
- `bits = [1,1,1,0]` → `false` — parsing: `11` (2-bit) lalu `10` (2-bit). Karakter terakhir adalah bagian dari `10` (2-bit), bukan 1-bit.

______________________________________________________________________

## 💡 Intuition

Karena aturan enkode-nya jelas — kalau ketemu `1`, itu **pasti** awal dari karakter 2-bit (harus "menelan" bit berikutnya juga, apapun nilainya); kalau ketemu `0`, itu **selalu** karakter 1-bit yang berdiri sendiri — kita bisa **parse array dari kiri ke kanan** mengikuti aturan ini, dan cek: begitu parsing selesai, apakah **karakter terakhir yang terbentuk** persis berupa `0` tunggal (bukan bagian dari `10`)?

Solusi ini menyimulasikan proses parsing itu memakai `StringBuilder` sebagai **buffer sementara**, merepresentasikan karakter yang "sedang dibangun":

- Ketemu `1` → tambahkan ke buffer. Kalau buffer sudah mencapai 2 karakter (`"1X"`), berarti satu karakter 2-bit baru saja selesai terbentuk → **kosongkan buffer**, siap untuk karakter berikutnya.
- Ketemu `0` → tambahkan ke buffer, lalu **cek khusus**: kalau ini **elemen terakhir** dari array, dan buffer-nya **bukan** cuma `"0"` sendirian (berarti sebelumnya ada `1` yang masih menunggu pasangannya, jadi buffer isinya `"10"`) → berarti karakter terakhir itu 2-bit, bukan 1-bit → `return false`. Kalau tidak (buffer memang cuma `"0"`) → karakter ini valid berdiri sendiri sebagai 1-bit, lanjutkan. Buffer selalu **dikosongkan** setelah `0` diproses (karena `0` selalu menandai akhir dari karakter, entah dia sendirian atau jadi pasangan kedua dari `1` sebelumnya).

______________________________________________________________________

## 🔍 Approach

### Simulasi Parsing dengan Buffer `StringBuilder`

1. Siapkan `sb` (buffer kosong).
1. Loop tiap `bits[i]`:
   - Kalau `bits[i] == 0`:
     - Tambahkan `'0'` ke `sb`.
     - Kalau `i` adalah **indeks terakhir** array **dan** isi `sb` **bukan** `"0"` saja (artinya ada `'1'` yang tertunda sebelumnya, sehingga bit terakhir ini jadi bagian dari karakter 2-bit) → `return false`.
     - Kosongkan `sb` (karakter ini — entah 1-bit atau bagian akhir 2-bit — sudah selesai diproses).
   - Kalau `bits[i] == 1`:
     - Tambahkan `'1'` ke `sb`.
   - Kalau `sb` sudah panjang `2` (dua `1` berturut-turut yang membentuk `"11"`) → kosongkan `sb` (karakter 2-bit `"11"` sudah selesai terbentuk).
1. Kalau loop selesai tanpa `return false` di tengah jalan → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh array `bits` |
| **Space** | O(1) — buffer `sb` maksimal berisi 2 karakter |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `bits = [1,0,0]`

| i | bits[i] | Aksi | sb sesudah | Cek khusus (kalau bits[i]=0 & i=last) |
| --- | ------- | -------------------------------------------------------------------------------- | ---------- | ------------------------------------- |
| 0 | 1 | append `'1'` | `"1"` | — |
| 1 | 0 | append `'0'` → `sb="10"`; bukan last (i=1≠2); reset | `""` | tidak dicek (bukan last index) |
| 2 | 0 | append `'0'` → `sb="0"`; **i=2=last**; `sb=="0"` → **tidak** return false; reset | `""` | `sb=="0"` ✅ → lolos |

Loop selesai tanpa `return false`.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `bits = [1,1,1,0]`

| i | bits[i] | Aksi | sb sesudah |
| --- | ------- | ---------------------------------------------------------------------- | ---------- |
| 0 | 1 | append `'1'` | `"1"` |
| 1 | 1 | append `'1'` → `sb="11"`; panjang==2 → reset | `""` |
| 2 | 1 | append `'1'` | `"1"` |
| 3 | 0 | append `'0'` → `sb="10"`; **i=3=last**; `sb != "0"` → **return false** | — |

**Output: `false`** ✅

______________________________________________________________________

**Input:** `bits = [1,0]`

| i | bits[i] | Aksi | sb sesudah |
| --- | ------- | ---------------------------------------------------------------------- | ---------- |
| 0 | 1 | append `'1'` | `"1"` |
| 1 | 0 | append `'0'` → `sb="10"`; **i=1=last**; `sb != "0"` → **return false** | — |

**Output: `false`** — bit terakhir (`0`) ternyata jadi bagian dari karakter 2-bit `"10"`, bukan berdiri sendiri.

______________________________________________________________________

**Input:** `bits = [0]`

| i | bits[i] | Aksi | sb sesudah |
| --- | ------- | --------------------------------------------------------------- | ---------- |
| 0 | 0 | append `'0'` → `sb="0"`; **i=0=last**; `sb=="0"` → lolos; reset | `""` |

**Output: `true`** ✅ — array satu elemen `[0]` selalu berupa satu karakter 1-bit tunggal.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen (`[0]`, karena constraint soal menjamin diakhiri `0`) → selalu `true`, karakter tunggal 1-bit
- [ ] Bit terakhir "ditelan" oleh `1` tepat sebelumnya (`[...,1,0]` di mana `1` itu belum sempat direset buffer-nya) → `false`
- [ ] Rangkaian `11` berturut-turut sebelum bit terakhir (`[1,1,1,0]`) → tetap tertangani, karena tiap `"11"` yang selesai langsung mereset buffer, tidak "menyisakan" apapun untuk bit berikutnya
- [ ] Banyak karakter 1-bit berturut-turut sebelum akhir (`[0,0,0]`) → tiap `0` berdiri sendiri, buffer selalu bersih di awal tiap iterasi, `true`
- [ ] Kombinasi campuran 2-bit dan 1-bit yang kompleks → parsing tetap konsisten karena aturan "buffer 2 karakter langsung reset" dan "0 selalu menyelesaikan karakter" berlaku seragam di setiap posisi

______________________________________________________________________

## 🔧 Kenapa Solusi Ini Berhasil Meski Memakai `StringBuilder` (Bukan Pendekatan Umum "Skip Index")?

Pendekatan yang lebih umum untuk soal ini biasanya **melompati indeks** secara langsung (`i += 2` untuk karakter 2-bit, `i += 1` untuk karakter 1-bit), tanpa perlu buffer string sama sekali. Solusi ini justru **mensimulasikan** proses yang sama lewat panjang `StringBuilder` sebagai pengganti index-skipping — `sb.length()==2` setara dengan "baru saja melompati 2 indeks", dan reset buffer setara dengan "mulai parsing karakter baru dari awal". Hasilnya **secara logika identik**, cuma jalur implementasinya lebih berputar (dan sedikit lebih boros karena operasi string dibanding aritmatika indeks murni) — tapi tetap **benar** karena esensi aturan parsing (1 selalu 2-bit, 0 selalu menyelesaikan karakter) terjaga sepenuhnya.

______________________________________________________________________

## 🔧 Alternatif: Index-Skipping (Pendekatan Standar, Lebih Ringkas)

```java
public boolean isOneBitCharacter(int[] bits) {
    int i = 0;
    while (i < bits.length - 1) {
        i += bits[i] + 1; // bits[i]==1 → lompat 2 (karakter 2-bit); bits[i]==0 → lompat 1
    }
    return i == bits.length - 1;
}
```

Versi ini jauh lebih ringkas: `i += bits[i] + 1` otomatis melompat **2** langkah kalau `bits[i]==1` (karena `1+1=2`), atau **1** langkah kalau `bits[i]==0` (karena `0+1=1`) — memanfaatkan nilai `bits[i]` itu sendiri sebagai bagian dari aritmatika lompatan. Loop berhenti begitu `i` mencapai atau melewati indeks terakhir; kalau `i` **persis** mendarat di indeks terakhir (bukan melompatinya), berarti elemen terakhir itu diproses sebagai karakter 1-bit tersendiri.

| Approach | Time | Space | Kejelasan Logika |
| ------------------------------------ | ---- | ----- | ----------------------------------------- |
| Simulasi `StringBuilder` (kode asli) | O(n) | O(1) | Berputar, tapi tetap benar |
| Index-skipping (`i += bits[i]+1`) | O(n) | O(1) | Langsung merepresentasikan aturan parsing |

Keduanya sama-sama `O(n)` waktu dan `O(1)` ruang tambahan, tapi versi index-skipping jauh lebih idiomatis untuk soal semacam ini — tanpa perlu objek `StringBuilder` sama sekali.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah soal **parsing greedy** — begitu ketemu `1`, kita **wajib** menelan bit berikutnya sebagai pasangannya (tidak ada pilihan lain, tidak perlu backtracking), yang membuat pendekatan greedy left-to-right selalu benar. Perhatikan juga bagaimana **cara merepresentasikan state parsing** bisa bermacam-macam (buffer string vs pointer indeks) tapi tetap menghasilkan logika yang sama — pilih representasi yang paling natural dan efisien untuk soal yang dihadapi. Pola "greedy consume pasangan bit" ini juga relevan untuk soal-soal decoding/parsing sekuensial lain seperti _Decode Ways_ (meski itu butuh DP karena ada ambiguitas, beda dengan soal ini yang aturannya deterministik). 🎯

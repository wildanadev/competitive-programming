# 696. Count Binary Substrings

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String
- **Link**: [Problem](https://leetcode.com/problems/count-binary-substrings/)
- **Solution**: [Code](../../leetcode/CountBinarySubstrings.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string biner `s` (hanya berisi `'0'` dan `'1'`). Hitung jumlah **substring** yang memenuhi syarat:

- Jumlah karakter `'0'` dan `'1'` di dalamnya **sama banyak**.
- Semua `'0'` dalam substring itu **berkelompok bersebelahan**, begitu juga semua `'1'`-nya (tidak boleh selang-seling acak seperti `"010"`).

Contoh:

- `s = "00110011"` → `6` (substring valid: `"01"`, `"10"`, `"0011"`, `"1100"`, `"01"` (di posisi lain), `"10"` (di posisi lain))
- `s = "10101"` → `4` (`"10"`, `"01"`, `"10"`, `"01"`)

______________________________________________________________________

## 💡 Intuition

Kunci soal ini: kalau kita pecah `s` jadi **grup-grup karakter berurutan yang sama** (run-length encoding), misal `"00110011"` jadi `[2,2,2,2]` (dua `0`, dua `1`, dua `0`, dua `1`), maka **setiap substring valid** pasti terbentuk dari **dua grup bersebelahan** — sebagian dari grup kiri (semuanya karakter yang sama) dan sebagian dari grup kanan (karakter yang berbeda), dengan jumlah yang **sama**.

Untuk dua grup bersebelahan dengan panjang `len1` dan `len2`, jumlah substring valid yang bisa dibentuk dari pasangan grup ini adalah **`min(len1, len2)`** — karena substring valid harus punya jumlah `0` dan `1` yang **sama persis**, dan itu dibatasi oleh grup yang **lebih pendek** di antara keduanya (grup yang lebih panjang, kelebihannya tidak bisa dipakai karena akan merusak keseimbangan jumlah).

Jadi solusinya: lakukan **run-length encoding** secara implisit (tanpa array eksplisit), lalu untuk **tiap pasangan grup bersebelahan**, akumulasikan `min(panjang grup sebelumnya, panjang grup saat ini)` ke hasil.

______________________________________________________________________

## 🔍 Approach

### Run-Length Encoding Implisit + Akumulasi Min Antar Grup Bersebelahan

1. `cur = 1` (panjang grup karakter saat ini, dimulai dari karakter pertama), `pre = 0` (panjang grup sebelumnya, belum ada di awal), `res = 0` (hasil akumulasi).
1. Loop `i` dari `1` sampai akhir `s`:
   - Kalau `s[i] == s[i-1]` (masih dalam grup yang sama) → `cur++` (perpanjang grup saat ini).
   - Kalau tidak (grup baru dimulai) → **tutup** grup sebelumnya: `res += Math.min(cur, pre)` (kontribusi pasangan grup `pre` dan `cur` yang baru saja selesai), lalu geser `pre = cur` (grup yang baru ditutup jadi "grup sebelumnya" untuk pasangan berikutnya), dan `cur = 1` (mulai hitung grup baru).
1. Setelah loop selesai, **grup terakhir** belum sempat "ditutup" di dalam loop (karena penutupan hanya terjadi saat transisi karakter) — makanya ditambahkan sekali lagi di luar loop: `return res + Math.min(cur, pre)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh string |
| **Space** | O(1) — hanya tiga variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "00110011"`

| i | s[i] vs s[i-1] | Aksi | cur | pre | res |
| --- | -------------- | ----------------------------------- | --- | --- | --- |
| — | — | inisialisasi | 1 | 0 | 0 |
| 1 | `0==0` | `cur++` | 2 | 0 | 0 |
| 2 | `1!=0` | `res+=min(2,0)=0`, `pre=2`, `cur=1` | 1 | 2 | 0 |
| 3 | `1==1` | `cur++` | 2 | 2 | 0 |
| 4 | `0!=1` | `res+=min(2,2)=2`, `pre=2`, `cur=1` | 1 | 2 | 2 |
| 5 | `0==0` | `cur++` | 2 | 2 | 2 |
| 6 | `1!=0` | `res+=min(2,2)=2`, `pre=2`, `cur=1` | 1 | 2 | 4 |
| 7 | `1==1` | `cur++` | 2 | 2 | 4 |

Loop selesai. Tambahkan grup terakhir: `res + min(cur=2, pre=2) = 4+2 = 6`.

**Output: `6`** ✅

______________________________________________________________________

**Input:** `s = "10101"`

| i | s[i] vs s[i-1] | Aksi | cur | pre | res |
| --- | -------------- | -------------------------------- | --- | --- | --- |
| — | — | inisialisasi | 1 | 0 | 0 |
| 1 | `0!=1` | `res+=min(1,0)=0`, `pre=1,cur=1` | 1 | 1 | 0 |
| 2 | `1!=0` | `res+=min(1,1)=1`, `pre=1,cur=1` | 1 | 1 | 1 |
| 3 | `0!=1` | `res+=min(1,1)=1`, `pre=1,cur=1` | 1 | 1 | 2 |
| 4 | `1!=0` | `res+=min(1,1)=1`, `pre=1,cur=1` | 1 | 1 | 3 |

Loop selesai. Tambahkan grup terakhir: `3 + min(1,1) = 4`.

**Output: `4`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] String satu karakter (`s="0"`) → loop tidak pernah jalan, langsung `res + min(cur=1, pre=0) = 0` (tidak ada substring valid, karena butuh minimal 2 karakter berbeda)
- [ ] Semua karakter sama (`s="0000"`) → tidak ada transisi sama sekali, `pre` tetap `0` sepanjang program, hasil akhir `min(cur=4, pre=0) = 0` (tidak ada `1` sama sekali, jadi tidak mungkin ada substring seimbang)
- [ ] Grup dengan panjang berbeda jauh (misal `"1110000011"`, grup `[3,5,2]`) → tiap pasangan grup bersebelahan dihitung terpisah: pasangan `(3,5)` kontribusi `min(3,5)=3`, pasangan `(5,2)` kontribusi `min(5,2)=2`, total `5`
- [ ] String alternating sempurna (`"1010101"`) → tiap grup panjang `1`, semua pasangan kontribusi `min(1,1)=1`, hasil `= jumlah transisi` (panjang string - 1)

______________________________________________________________________

## 🔧 Kenapa Cuma Perlu Bandingkan **Grup Bersebelahan** (Bukan Semua Kombinasi Grup)?

Definisi soal mensyaratkan substring valid harus punya `0` dan `1` yang **berkelompok bersebelahan tanpa selang** (misal `"0011"` valid, tapi `"0101"` **tidak** valid meski jumlah `0` dan `1`-nya sama). Karena syarat ini, satu-satunya cara membentuk substring valid adalah mengambil **akhir dari satu grup** dan **awal dari grup berikutnya** — tidak mungkin "melompati" grup di tengah tanpa merusak syarat "berkelompok bersebelahan tanpa selang". Itu sebabnya solusi ini **hanya** perlu membandingkan grup `i` dengan grup `i+1` (tetangga langsung), tidak perlu mempertimbangkan kombinasi grup yang lebih jauh.

______________________________________________________________________

## 🔧 Alternatif: Bangun Array Run-Length Eksplisit

```java
public int countBinarySubstrings(String s) {
    List<Integer> groups = new ArrayList<>();
    int cur = 1;
    for (int i = 1; i < s.length(); i++) {
        if (s.charAt(i) == s.charAt(i - 1)) {
            cur++;
        } else {
            groups.add(cur);
            cur = 1;
        }
    }
    groups.add(cur);

    int res = 0;
    for (int i = 1; i < groups.size(); i++)
        res += Math.min(groups.get(i - 1), groups.get(i));
    return res;
}
```

Versi ini membangun array `groups` berisi panjang tiap grup secara eksplisit terlebih dahulu, baru kemudian loop terpisah untuk menjumlahkan `min` antar pasangan bersebelahan. Secara logika identik dengan kode asli, tapi butuh memori tambahan `O(jumlah grup)` untuk menyimpan array `groups` — kode asli menghindari ini dengan melacak cuma **dua** grup terakhir (`cur` dan `pre`) secara on-the-fly, tanpa perlu menyimpan seluruh riwayat grup.

| Approach | Time | Space |
| ----------------------------------------------- | ---- | -------------- |
| Dua variabel `cur`/`pre` on-the-fly (kode asli) | O(n) | O(1) |
| Array `groups` eksplisit | O(n) | O(jumlah grup) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah aplikasi bagus dari **run-length encoding** yang diproses secara **on-the-fly** (tanpa perlu membangun array run-length eksplisit) — cukup lacak panjang grup **saat ini** dan **sebelumnya**, karena kontribusi ke jawaban akhir hanya pernah melibatkan **dua grup bersebelahan** sekaligus, tidak pernah lebih. Pola "proses grup konsekutif tanpa menyimpan seluruh riwayat" ini menghemat memori secara signifikan dan relevan untuk soal-soal run-length lain seperti _String Compression_ atau variasi _Count and Say_. 🎯

# 3014. Minimum Number of Pushes to Type Word I

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Math, String, Greedy
- **Link**: [Problem](https://leetcode.com/problems/minimum-number-of-pushes-to-type-word-i/)
- **Solution**: [Code](../../leetcode/MinimumNumberOfPushesToTypeWordI.java)

______________________________________________________________________

## 📄 Problem Summary

Keypad telepon punya 8 tombol (angka 2–9) yang bisa dipetakan ulang ke sekumpulan huruf apa saja. Kalau sebuah tombol dipetakan ke beberapa huruf, huruf **pertama** butuh 1 kali pencet, huruf **kedua** butuh 2 kali pencet, huruf **ketiga** butuh 3 kali, dan seterusnya.

Diberikan `word` yang **hanya berisi huruf kecil berbeda (distinct)**, cari jumlah pencet minimum untuk mengetik `word`, dengan asumsi pemetaan huruf ke tombol bisa diatur bebas (optimal) sebelumnya.

Contoh:

- `word = "abcde"` → `5` (5 huruf, cukup 1 huruf per tombol → semua cost 1)
- `word = "xycdefghij"` → `12` (10 huruf > 8 tombol, jadi 2 huruf harus berbagi tombol dengan huruf lain)

______________________________________________________________________

## 💡 Intuition

Karena semua huruf di `word` **berbeda** (constraint penting!), setiap huruf hanya diketik **tepat satu kali**. Ini artinya total biaya tinggal soal: **di posisi keberapa (1st, 2nd, 3rd, dst.) tiap huruf ditempatkan pada tombolnya**, dijumlahkan semua.

Supaya biaya total minimum, strategi greedy paling jelas: **isi posisi termurah dulu di semua tombol**. Ada 8 tombol, jadi:

- 8 huruf pertama bisa masing-masing dapat **posisi 1** (cost 1 per huruf) — satu huruf per tombol.
- 8 huruf berikutnya jadi **posisi 2** di tombol-tombol yang sama (cost 2 per huruf).
- 8 huruf berikutnya lagi jadi **posisi 3** (cost 3 per huruf).
- Dan seterusnya, kelipatan 8 berikutnya menaikkan posisi (dan cost) satu tingkat.

Karena huruf-hurufnya identik nilainya (tidak ada bobot/frekuensi — beda dengan versi II yang boleh ada huruf berulang), urutan huruf mana yang ditaruh di posisi mana **tidak penting**, yang penting hanya **berapa banyak** huruf ada di tiap "gelombang" 8 huruf. Ini yang membuat soal murni jadi soal matematika/aritmatika, tanpa perlu hitung frekuensi atau sorting sama sekali.

______________________________________________________________________

## 🔍 Approach

### Math — Deret Aritmatika untuk Grup Penuh + Sisa

1. `n = word.length() / 8` → banyaknya **grup penuh** berisi 8 huruf (grup ke-1 cost 1/huruf, grup ke-2 cost 2/huruf, dst).
1. Biaya tiap grup penuh ke-`k` adalah `8 * k` (8 huruf × cost `k`). Biaya grup 1 sampai grup `n` membentuk deret aritmatika: `8, 16, 24, ..., 8n` (suku awal `a=8`, beda `d=8`).
1. Jumlah deret ini dihitung pakai rumus deret aritmatika:
   ```
   nForm = n * (2*a + (n-1)*d) / 2
   ```
1. Sisa huruf yang belum masuk grup penuh (`word.length() - n*8`) semuanya masuk **grup ke-`(n+1)`**, masing-masing berbiaya `(n+1)`.
1. Total = `nForm + sisa * (n+1)`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ----------------------------------------------- |
| **Time** | O(1) — murni operasi aritmatika, tidak ada loop |
| **Space** | O(1) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `word = "abcde"` (panjang 5)

- `n = 5 / 8 = 0` (belum ada grup penuh, karena 5 < 8).
- `nForm = 0 * (2*8 + (0-1)*8) / 2 = 0`.
- Sisa `= 5 - (0*8) = 5`, semuanya masuk grup ke-`(0+1)=1`, cost `1` per huruf.
- Total `= 0 + 5*1 = 5`.

**Output: `5`** ✅ (cocok dengan penjelasan resmi: semua huruf cukup 1 kali pencet karena ≤ 8 tombol tersedia)

______________________________________________________________________

**Input:** `word = "xycdefghij"` (panjang 10)

- `n = 10 / 8 = 1` (1 grup penuh berisi 8 huruf dengan cost 1/huruf).
- `a=8, d=8`: `nForm = 1 * (2*8 + (1-1)*8) / 2 = 1 * 16 / 2 = 8`.
  _(Ini merepresentasikan 8 huruf pertama × cost 1 = 8 total pencet.)_
- Sisa `= 10 - (1*8) = 2` huruf, masuk grup ke-`(1+1)=2`, cost `2` per huruf → `2*2=4`.
- Total `= 8 + 4 = 12`.

**Output: `12`** ✅ — cocok dengan penjelasan resmi: 8 huruf pertama (`x,c,e,f,g,h,i,j`) cost 1 masing-masing = 8, dan 2 huruf (`y,d`) jadi huruf kedua di tombolnya masing-masing, cost 2 masing-masing = 4. Total `8+4=12`.

______________________________________________________________________

**Input:** `word` berisi seluruh 26 huruf alfabet (kasus terpadat)

- `n = 26 / 8 = 3` (3 grup penuh: cost 1, 2, 3, masing-masing 8 huruf).
- `nForm = 3 * (2*8 + (3-1)*8) / 2 = 3 * (16+16) / 2 = 3*32/2 = 48`.
  _(8 huruf cost 1 = 8, 8 huruf cost 2 = 16, 8 huruf cost 3 = 24 → total 48.)_
- Sisa `= 26 - 24 = 2` huruf, masuk grup ke-4, cost `4` per huruf → `2*4=8`.
- Total `= 48 + 8 = 56`.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `word.length() <= 8` → `n=0`, semua huruf otomatis cost 1 (satu huruf per tombol, tidak ada yang berbagi)
- [ ] `word.length()` tepat kelipatan 8 (misal 16) → sisa `= 0`, jadi tidak ada tambahan dari grup terakhir, semuanya dari `nForm`
- [ ] `word.length() = 26` (seluruh alfabet) → skenario terpadat, tetap tertangani karena constraint memang membatasi maksimal 26 huruf distinct
- [ ] `word.length() = 1` → `n=0`, sisa `1`, total `= 1*1 = 1`, hasil paling minimal yang mungkin

______________________________________________________________________

## 🔧 Kenapa Tidak Perlu Hitung Frekuensi Huruf?

Ini beda krusial dengan soal **Minimum Number of Pushes to Type Word II**, yang mengizinkan huruf berulang di `word`. Di versi II, huruf yang sering muncul harus ditempatkan di posisi termurah (cost 1) supaya total pencet minimal — butuh hitung frekuensi lalu greedy sort berdasarkan frekuensi terbanyak.

Tapi di versi **I** ini, constraint menegaskan **semua huruf di `word` berbeda**. Karena tiap huruf cuma diketik sekali, "siapa" yang menempati posisi mana **tidak mempengaruhi total biaya** — yang menentukan total biaya murni **berapa banyak huruf** ada di tiap gelombang grup 8. Makanya solusi ini bisa lompat langsung ke rumus matematika tanpa struktur data tambahan sama sekali (tidak ada `HashMap`, tidak ada sorting).

| Versi | Huruf Boleh Berulang? | Perlu Frequency Count? | Pendekatan |
| ------------ | --------------------- | ---------------------- | -------------------------- |
| I (soal ini) | Tidak | Tidak | Rumus matematika langsung |
| II | Ya | Ya | Greedy + sort by frequency |

______________________________________________________________________

## 🔧 Alternatif: Simulasi dengan Loop

```java
public int minimumPushes(String word) {
    int n = word.length();
    int total = 0;
    for (int i = 0; i < n; i++) {
        total += (i / 8) + 1;
    }
    return total;
}
```

Versi ini mensimulasikan penempatan huruf ke-`i` (0-indexed) langsung mendapat cost `(i/8)+1` — huruf ke-0 sampai ke-7 cost 1, huruf ke-8 sampai ke-15 cost 2, dst. Secara matematis identik dengan rumus deret aritmatika, tapi lebih mudah dipahami sebagai simulasi langsung, meski jadi O(n) alih-alih O(1).

| Approach | Time | Space |
| ---------------------------------- | ---- | ----- |
| Rumus deret aritmatika (kode asli) | O(1) | O(1) |
| Simulasi loop per huruf | O(n) | O(1) |

Karena `n` di soal ini dibatasi maksimal 26, perbedaan performa keduanya secara praktik nyaris tidak terasa — tapi rumus matematika tetap lebih elegan karena mencerminkan langsung struktur "deret aritmatika" dari masalahnya.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh bagus dari **"greedy yang runtuh jadi rumus matematika murni"** — begitu constraint menjamin semua elemen "setara" (di sini: semua huruf distinct, jadi tidak ada bobot/frekuensi yang membedakan), strategi optimal (isi kapasitas termurah dulu) bisa langsung dihitung lewat rumus deret aritmatika tanpa perlu loop, sorting, atau struktur data tambahan. Selalu perhatikan constraint seperti "elements are distinct" — itu sering jadi sinyal bahwa soal bisa disederhanakan jauh dari yang terlihat di permukaan. 🎯

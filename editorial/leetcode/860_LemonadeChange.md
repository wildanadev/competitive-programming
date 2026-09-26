# 860. Lemonade Change

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Greedy
- **Link**: [Problem](https://leetcode.com/problems/lemonade-change/)
- **Solution**: [Code](../../leetcode/LemonadeChange.java)

______________________________________________________________________

## 📄 Problem Summary

Kamu jualan limun seharga `$5` per gelas. Pelanggan datang **berurutan** sesuai array `bills`, tiap orang bayar pakai `$5`, `$10`, atau `$20`, dan kamu harus memberi **kembalian yang tepat** (selisih dari `$5`). Kamu **mulai tanpa uang kembalian sama sekali**. Kembalikan `true` kalau kamu bisa memberi kembalian yang benar untuk **semua** pelanggan secara berurutan.

Contoh:

- `bills = [5,5,5,10,20]` → `true`
  - `$5,$5,$5`: tidak perlu kembalian. `$10`: kembalian `$5` (pakai satu lembar `$5`). `$20`: kembalian `$15`, bisa dikasih `$10+$5`.
- `bills = [5,5,10,10,20]` → `false`
  - Setelah dua `$10`, kamu cuma punya `0` lembar `$5` tersisa. Untuk `$20` berikutnya, kembalian `$15` **tidak bisa** dibentuk dari `$10+$10` (karena kembalian tidak boleh pakai `$10` sebagai bagian dari `$15` tanpa `$5`, dan tidak punya `$5` sama sekali).

______________________________________________________________________

## 💡 Intuition

Ini soal **greedy** klasik. Kunci utamanya: uang kembalian yang bisa kamu terima cuma **`$5`** dan **`$10`** (karena `$20` selalu **diberikan**, tidak pernah jadi kembalian), jadi kamu cuma perlu melacak **berapa lembar `$5`** dan **berapa lembar `$10`** yang kamu punya sejauh ini.

- Bayar `$5` → tidak perlu kembalian, cuma nambah stok `$5`-mu.
- Bayar `$10` → kembalian `$5` **wajib** dari selembar `$5` (tidak ada opsi lain, karena kembalian `$5` cuma bisa dibentuk dari satu lembar `$5`).
- Bayar `$20` → kembalian `$15`, ada **dua cara** membentuknya: `$10 + $5`, atau `$5+$5+$5`. **Insight greedy pentingnya**: **selalu prioritaskan** `$10+$5` **dulu** kalau memungkinkan, dan simpan lembar `$5` sebanyak mungkin untuk kebutuhan masa depan — karena lembar `$5` **lebih fleksibel** (bisa dipakai untuk kembalian `$10` **atau** `$20`), sementara lembar `$10` **cuma** berguna untuk kembalian `$20` (kombinasi `$10+$5`). Kalau kamu boros memakai `$5` sekarang padahal ada `$10` yang bisa dipakai, kamu berisiko kehabisan `$5` untuk pelanggan `$10` berikutnya.

______________________________________________________________________

## 🔍 Approach

### Greedy — Lacak Stok `$5` dan `$10`, Prioritaskan `$10+$5` untuk Kembalian `$20`

1. `fiveCnt = 0`, `tenCnt = 0`.
1. Loop tiap `bill` di `bills`:
   - **`bill == 5`** → `fiveCnt++` (tidak perlu kembalian).
   - **`bill == 10`** → kalau `fiveCnt == 0` (tidak ada `$5` untuk kembalian) → `return false`. Kalau ada, `fiveCnt--`, `tenCnt++`.
   - **`bill == 20`** (kembalian `$15`):
     - Kalau ada **minimal satu** `$5` **dan** satu `$10` → pakai kombinasi ini (`fiveCnt--`, `tenCnt--`) — **prioritas utama**.
     - Kalau tidak, tapi ada **minimal 3** lembar `$5` → pakai `$5×3` (`fiveCnt -= 3`).
     - Kalau keduanya tidak memungkinkan → `return false`.
1. Kalau seluruh pelanggan berhasil dilayani → `return true`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------- |
| **Time** | O(n) — satu kali pass ke `bills` |
| **Space** | O(1) — cuma dua variabel counter |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `bills = [5,5,5,10,20]`

| bill | Aksi | fiveCnt | tenCnt |
| ---- | ---------------------------------------------------- | ------- | ------ |
| 5 | `fiveCnt++` | 1 | 0 |
| 5 | `fiveCnt++` | 2 | 0 |
| 5 | `fiveCnt++` | 3 | 0 |
| 10 | `fiveCnt>0` ✅ → `fiveCnt--`, `tenCnt++` | 2 | 1 |
| 20 | `fiveCnt>0 && tenCnt>0` ✅ → `fiveCnt--`, `tenCnt--` | 1 | 0 |

Semua pelanggan terlayani.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `bills = [5,5,10,10,20]`

| bill | Aksi | fiveCnt | tenCnt |
| ---- | ------------------------------------------------------------------------------------------ | ------- | ------ |
| 5 | `fiveCnt++` | 1 | 0 |
| 5 | `fiveCnt++` | 2 | 0 |
| 10 | `fiveCnt--`, `tenCnt++` | 1 | 1 |
| 10 | `fiveCnt--`, `tenCnt++` | 0 | 2 |
| 20 | `fiveCnt>0 && tenCnt>0`? `fiveCnt=0` → tidak. `fiveCnt>2`? `0>2` tidak. → **return false** | — | — |

**Output: `false`** ✅ — meski ada `2` lembar `$10` (total nilai `$20`), kembalian `$15` **tidak bisa** dibentuk dari `$10+$10` (aturan kembalian harus tepat `$15`, dan tidak ada kombinasi valid dari `$10` saja untuk itu — kembalian cuma boleh berupa lembar `$5` dan `$10` yang totalnya pas `$15`, yaitu `$10+$5` atau `$5×3`, keduanya butuh minimal satu `$5`).

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Pelanggan pertama bayar `$10` atau `$20` (tidak mungkin ada `$5` untuk kembalian) → langsung `false` (meski constraint LeetCode menjamin `bills[0]` selalu `5`, kode ini tetap aman menangani kasus lain)
- [ ] Kembalian `$20` dengan stok `$10` cukup tapi `$5` kosong → **gagal**, karena `$10+$10` bukan kombinasi kembalian yang sah untuk `$15` (`$20` cuma butuh kembalian `$15`, bukan `$20`)
- [ ] Kembalian `$20` dengan `$5>=3` tapi `$10=0` → tetap bisa, pakai `$5×3`
- [ ] Semua pelanggan bayar pas `$5` → tidak pernah butuh kembalian, selalu `true`
- [ ] Stok `$5` dan `$10` sama-sama cukup untuk kembalian `$20` lewat kedua cara → **prioritaskan** `$10+$5` (bukan `$5×3`), sesuai strategi greedy untuk menghemat `$5`

______________________________________________________________________

## 🔧 Kenapa Harus Prioritaskan `$10+$5` Daripada `$5×3` untuk Kembalian `$20`?

Ini inti dari strategi greedy soal ini. Lembar `$10` **hanya** berguna untuk **satu** skenario: jadi bagian dari kembalian `$20` (dikombinasikan dengan `$5`). Sebaliknya, lembar `$5` **serba guna** — bisa dipakai untuk kembalian `$10` (butuh 1 lembar `$5`) **atau** kembalian `$20` (butuh 1-3 lembar `$5`, tergantung kombinasi). Karena `$5` lebih "berharga" secara fleksibilitas, strategi optimal adalah **menghabiskan `$10` duluan** kapanpun bisa (karena `$10` tidak bisa dipakai untuk apa-apa lagi selain ini), dan **menyimpan `$5`** untuk kebutuhan mendatang yang lebih beragam. Kalau kita pakai `$5×3` padahal ada `$10` yang bisa dipakai, kita "memboroskan" tiga `$5` yang berharga, padahal cukup satu `$5` + satu `$10` (yang memang tidak berguna untuk hal lain).

______________________________________________________________________

## 🔧 Kenapa `fiveCnt > 2` Setara dengan "Minimal 3 Lembar $5"?

```java
else if (fiveCnt > 2)
    fiveCnt -= 3;
```

Karena `fiveCnt` bertipe `int` (bilangan bulat), `fiveCnt > 2` **secara matematis identik** dengan `fiveCnt >= 3` — tidak ada nilai integer yang memenuhi `> 2` tapi tidak `>= 3` (misalnya `2.5` tidak mungkin muncul di sini). Keduanya valid, cuma beda gaya penulisan; `fiveCnt >= 3` mungkin sedikit lebih eksplisit soal maksudnya ("minimal 3"), tapi `fiveCnt > 2` sama sekali tidak salah.

______________________________________________________________________

## 🔧 Alternatif: Simulasi dengan Array Kecil untuk Kejelasan

```java
public boolean lemonadeChange(int[] bills) {
    int[] change = new int[2]; // index 0 = jumlah $5, index 1 = jumlah $10
    for (int bill : bills) {
        if (bill == 5) {
            change[0]++;
        } else if (bill == 10) {
            if (change[0] == 0) return false;
            change[0]--; change[1]++;
        } else {
            if (change[0] > 0 && change[1] > 0) { change[0]--; change[1]--; }
            else if (change[0] >= 3) change[0] -= 3;
            else return false;
        }
    }
    return true;
}
```

Versi ini secara logika **identik** dengan kode asli, cuma mengganti dua variabel terpisah (`fiveCnt`, `tenCnt`) dengan satu array kecil (`change[0]`, `change[1]`). Tidak ada perbedaan performa maupun kejelasan yang signifikan — ini murni soal preferensi gaya penulisan.

| Approach | Time | Space |
| --------------------------------- | ---- | ----- |
| Dua variabel terpisah (kode asli) | O(n) | O(1) |
| Array kecil `int[2]` | O(n) | O(1) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar yang bagus untuk **strategi greedy berbasis "kelangkaan sumber daya"** — ketika ada beberapa cara untuk mencapai tujuan yang sama (di sini: dua cara membentuk kembalian `$15`), pilih cara yang **menghemat sumber daya paling fleksibel** (di sini: lembar `$5`, yang berguna untuk lebih banyak skenario dibanding `$10`). Pola "gunakan yang paling terbatas kegunaannya duluan, simpan yang paling serba guna" ini adalah prinsip umum di banyak soal greedy lain, termasuk soal-soal penjadwalan dan alokasi sumber daya. 🎯

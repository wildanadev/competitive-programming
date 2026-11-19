
# 3105. Longest Strictly Increasing or Strictly Decreasing Subarray

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/longest-strictly-increasing-or-strictly-decreasing-subarray/)
- **Solution**: [Code](../../leetcode/LongestStrictlyIncreasingOrStrictlyDecreasingSubarray.java)

---

## 📄 Problem Summary

Diberikan array `nums`. Kembalikan **panjang subarray terpanjang** (bagian **bersambung**) yang **naik secara ketat (strictly increasing)** atau **turun secara ketat (strictly decreasing)**. "Ketat" berarti elemen yang sama berurutan **tidak** diperbolehkan.

Contoh:

- `nums = [1,4,3,3,2]` → `2` (subarray naik: `[1,4]`; subarray turun: `[4,3]` dan `[3,2]`; tidak ada yang lebih panjang dari `2`)
- `nums = [3,3,3,3]` → `1` (tidak ada pasangan yang naik atau turun, jadi satu elemen saja)
- `nums = [3,2,1]` → `3` (seluruh array turun ketat)

---

## 💡 Intuition

Soal ini adalah gabungan dua pola yang sudah kita kenal:

- *Longest Continuous Increasing Subsequence*: **streak counter** untuk barisan naik ketat.
- *Monotonic Array*: melacak **dua arah** (naik dan turun) sekaligus.

Idenya: telusuri array sambil membandingkan tiap pasangan bertetangga, dan jaga **dua penghitung panjang**:

- `inc`: panjang barisan **naik ketat** yang berakhir di elemen saat ini.
- `dec`: panjang barisan **turun ketat** yang berakhir di elemen saat ini.

Untuk tiap pasangan `(nums[i], nums[i+1])`:

- Kalau **naik** (`nums[i] < nums[i+1]`): barisan naik **bertambah** (`inc++`), dan barisan turun **putus** (`dec = 1`).
- Kalau **turun** (`nums[i] > nums[i+1]`): barisan turun bertambah (`dec++`), dan barisan naik putus (`inc = 1`).
- Kalau **sama**: kedua barisan putus (`inc = 1`, `dec = 1`), karena soal meminta ketat.

Setelah tiap langkah, perbarui jawaban dengan `max(inc, dec)`.

**Kenapa mereset ke `1` (bukan `0`)?** Pada saat barisan "putus", elemen saat ini tetap menjadi **awal** barisan baru dengan panjang minimal `1`. Contoh: pada `[1,4,3]`, langkah `4 → 3` menurunkan `inc` ke `1`, dan `dec` yang sebelumnya `1` (direset di langkah naik `1 → 4`) menjadi `2`. Itu benar: barisan turun `[4,3]` berisi dua elemen, dimulai dari `4` (elemen puncak yang sama-sama jadi ujung barisan naik sebelumnya).

---

## 🔍 Approach

### Dua Penghitung Streak, Reset Sesuai Arah

1. `ans = 1`, `inc = 1`, `dec = 1` (satu elemen selalu merupakan subarray valid dengan panjang `1`).
2. Untuk `i` dari `0` sampai `nums.length - 2`:
   - Kalau `nums[i] < nums[i+1]` → `inc++`, `dec = 1`.
   - Kalau `nums[i] > nums[i+1]` → `dec++`, `inc = 1`.
   - Kalau sama → `inc = 1`, `dec = 1`.
   - `ans = Math.max(ans, Math.max(inc, dec))`.
3. Kembalikan `ans`.

---

## 🧮 Complexity

|                 |                        |
| --------------- | ---------------------- |
| **Time**  | O(n) — satu kali pass |
| **Space** | O(1) — tiga variabel  |

---

## 🧪 Dry Run

**Input:** `nums = [1,4,3,3,2]`

| i    | Pasangan | Perbandingan | inc | dec | ans |
| ---- | -------- | ------------ | --- | --- | --- |
| awal | —       | —           | 1   | 1   | 1   |
| 0    | `1, 4` | naik         | 2   | 1   | 2   |
| 1    | `4, 3` | turun        | 1   | 2   | 2   |
| 2    | `3, 3` | sama         | 1   | 1   | 2   |
| 3    | `3, 2` | turun        | 1   | 2   | 2   |

**Output: `2`** ✅

---

**Input:** `nums = [3,3,3,3]`

Tiap pasangan sama, jadi `inc` dan `dec` selalu direset ke `1`.

**Output: `1`** ✅

---

**Input:** `nums = [3,2,1]`

| i    | Pasangan | Perbandingan | inc | dec | ans |
| ---- | -------- | ------------ | --- | --- | --- |
| awal | —       | —           | 1   | 1   | 1   |
| 0    | `3, 2` | turun        | 1   | 2   | 2   |
| 1    | `2, 1` | turun        | 1   | 3   | 3   |

**Output: `3`** ✅

---

**Input tambahan:** `nums = [1,2,3,2,1]` (puncak di tengah)

`1 → 2` naik (`inc = 2`), `2 → 3` naik (`inc = 3`, `ans = 3`), `3 → 2` turun (`dec = 2`, `inc = 1`), `2 → 1` turun (`dec = 3`, `ans = 3`).

**Output: `3`** (naik `[1,2,3]` dan turun `[3,2,1]` sama-sama sepanjang `3`, dan keduanya berbagi elemen puncak `3`)

---

## ⚠️ Edge Cases

- [ ] Array satu elemen → loop tidak berjalan, hasil `1`
- [ ] Semua elemen sama → hasil `1`
- [ ] Seluruh array naik ketat atau turun ketat → hasil `n`
- [ ] Zig-zag (`[1,3,2,4,3]`) → arah berganti di tiap langkah, hasil `2`
- [ ] Elemen sama di tengah memutus kedua barisan (`[1,2,2,3]` → `2`), karena soal meminta **ketat** (berbeda dengan *Monotonic Array* yang mengizinkan nilai sama)
- [ ] Puncak atau lembah di tengah → barisan naik dan turun saling berbagi elemen puncak, dan penghitung terpisah menanganinya tanpa kasus khusus

---

## 🔧 Alternatif: Satu Penghitung dengan Penanda Arah

Karena pada satu waktu hanya satu arah yang bisa "aktif", kita bisa memakai **satu panjang** `len` dan satu penanda arah `dir` (`1` naik, `-1` turun, `0` belum ada arah):

```java
public int longestMonotonicSubarray(int[] nums) {
    int ans = 1, len = 1, dir = 0;
    for (int i = 1; i < nums.length; i++) {
        int c = Integer.compare(nums[i], nums[i - 1]);   // 1, 0, atau -1
        if (c == 0) {              // sama: semua barisan putus
            len = 1;
            dir = 0;
        } else if (c == dir) {     // melanjutkan arah yang sama
            len++;
        } else {                   // arah baru (atau arah pertama): mulai dengan 2 elemen
            len = 2;
            dir = c;
        }
        ans = Math.max(ans, len);
    }
    return ans;
}
```

Cek `[1,4,3,3,2]`: `1 → 4` arah baru, `len = 2`; `4 → 3` arah berlawanan, `len = 2`; `3 → 3` sama, `len = 1`, `dir = 0`; `3 → 2` arah baru, `len = 2`. `ans = 2` ✅. Cek `[3,2,1]`: `3 → 2` arah baru turun, `len = 2`; `2 → 1` arah sama, `len = 3`. `ans = 3` ✅.

Saat arah berganti, panjang baru dimulai dari `2` (elemen sebelumnya dan elemen sekarang), sama dengan apa yang terjadi di versi dua penghitung lewat reset ke `1` lalu `++`.

| Approach                                       | Time | Space | Catatan                                                       |
| ---------------------------------------------- | ---- | ----- | ------------------------------------------------------------- |
| Dua penghitung `inc` dan `dec` (kode asli) | O(n) | O(1)  | Tiap arah dilacak terpisah, tanpa percabangan arah sebelumnya |
| Satu `len` dan `dir`                       | O(n) | O(1)  | Lebih ringkas, perlu memperhatikan awal barisan baru          |

---

## 📌 Key Takeaway

Soal ini menggabungkan dua pola **streak counter** (satu untuk naik, satu untuk turun), dengan aturan reset yang sederhana: langkah naik mematikan streak turun, langkah turun mematikan streak naik, dan langkah sama mematikan keduanya. Perhatikan detail kecil bahwa reset memakai `1` (bukan `0`) karena elemen yang memutus streak tetap menjadi awal streak baru. Pola serupa muncul di *Longest Continuous Increasing Subsequence*, *Monotonic Array*, dan *Longest Turbulent Subarray*. 🎯

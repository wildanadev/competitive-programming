# 674. Longest Continuous Increasing Subsequence

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array
- **Link**: [Problem](https://leetcode.com/problems/longest-continuous-increasing-subsequence/)
- **Solution**: [Code](../../leetcode/LongestContinuousIncreasingSubsequence.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `nums`, cari **panjang subarray (contiguous) yang naik secara ketat terpanjang** — yaitu barisan berurutan `nums[l..r]` di mana tiap elemen **lebih besar** dari elemen sebelumnya.

Contoh:

- `nums = [1,3,5,4,7]` → `3` (subarray `[1,3,5]`)
- `nums = [2,2,2,2,2]` → `1` (tidak ada yang naik sama sekali, jadi tiap elemen sendirian adalah subarray naik terpanjang yang mungkin)

______________________________________________________________________

## 💡 Intuition

Meski namanya "subsequence" (dan memang begitu istilah resminya di LeetCode untuk soal ini), definisinya sebenarnya **contiguous** — beda dengan "subsequence" pada umumnya yang boleh melompat-lompat. Karena itu soal ini jauh lebih sederhana dari kelihatannya: cukup **streak counter** — lacak **panjang barisan naik yang sedang berjalan saat ini**, dan reset begitu polanya terputus (ketemu elemen yang **tidak** lebih besar dari elemen sebelumnya).

Ini persis pola yang sama dengan soal _Student Attendance Record I_ untuk menghitung `late` berturut-turut: counter bertambah selama pola berlanjut, dan **reset** begitu terputus — bedanya di sini reset-nya bukan ke `0`, tapi ke `1` (karena elemen yang "memutus" pola tetap jadi awal dari barisan naik yang baru, dengan panjang minimal `1`).

______________________________________________________________________

## 🔍 Approach

### Streak Counter — Reset ke 1 Saat Pola Terputus

1. Inisialisasi `ans = 0` (jawaban akhir) dan `cnt = 0` (panjang streak naik saat ini).
1. Loop `i` dari `0` sampai akhir `nums`:
   - Kalau `i == 0` (elemen pertama, tidak ada pembanding) **atau** `nums[i-1] < nums[i]` (melanjutkan pola naik) → naikkan `cnt` (`++cnt`), lalu update `ans = Math.max(ans, cnt)`.
   - Kalau tidak (pola terputus, `nums[i-1] >= nums[i]`) → **reset** `cnt = 1` (elemen ini jadi awal barisan baru, panjang minimal `1` untuk dirinya sendiri).
1. Kembalikan `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------- |
| **Time** | O(n) — satu kali pass ke seluruh array |
| **Space** | O(1) — hanya dua variabel akumulator |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `nums = [1,3,5,4,7]`

| i | nums[i-1] < nums[i]? (atau i==0) | Aksi | cnt | ans |
| --- | -------------------------------- | ------------------------ | --- | --- |
| 0 | `i==0` | `cnt=1` (`++cnt` dari 0) | 1 | 1 |
| 1 | `1<3` ✅ | `cnt=2` | 2 | 2 |
| 2 | `3<5` ✅ | `cnt=3` | 3 | 3 |
| 3 | `5<4`? ❌ | reset `cnt=1` | 1 | 3 |
| 4 | `4<7` ✅ | `cnt=2` | 2 | 3 |

**Output: `3`** ✅ (dari barisan `[1,3,5]`)

______________________________________________________________________

**Input:** `nums = [2,2,2,2,2]`

| i | nums[i-1] < nums[i]? | Aksi | cnt | ans |
| --- | -------------------- | ------------- | --- | --- |
| 0 | `i==0` | `cnt=1` | 1 | 1 |
| 1 | `2<2`? ❌ | reset `cnt=1` | 1 | 1 |
| 2 | `2<2`? ❌ | reset `cnt=1` | 1 | 1 |
| 3 | `2<2`? ❌ | reset `cnt=1` | 1 | 1 |
| 4 | `2<2`? ❌ | reset `cnt=1` | 1 | 1 |

**Output: `1`** ✅ — karena elemen sama **tidak** dianggap "naik" (harus strictly increasing), tiap elemen dianggap barisan naik terpanjang untuk dirinya sendiri (panjang `1`).

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array satu elemen → langsung `ans=1` (elemen tunggal selalu jadi barisan naik dengan panjang `1`)
- [ ] Seluruh array naik terus (misal `[1,2,3,4,5]`) → `cnt` terus bertambah tanpa reset, `ans` akhirnya sama dengan panjang seluruh array
- [ ] Ada elemen yang sama persis berturut-turut (`[2,2,2]`) → dianggap **memutus** pola (karena syaratnya strictly increasing, `<`, bukan `<=`), reset ke `cnt=1`
- [ ] Array menurun terus (`[5,4,3,2,1]`) → tiap elemen mereset `cnt` ke `1`, hasil akhirnya tetap `1`
- [ ] Pola naik-turun berulang (`[1,3,2,4,1,5]`) → tiap streak dihitung dan dibandingkan terpisah, `ans` menyimpan yang terpanjang di antara semuanya

______________________________________________________________________

## 🔧 Kenapa Reset ke `1`, Bukan `0` (Beda dengan Pola Streak di _Student Attendance Record I_)?

Ini perbedaan penting dibanding soal _Student Attendance Record I_ yang membahas pola serupa. Di soal itu, `late` di-reset ke `0` karena tujuannya menghitung **berapa banyak karakter `'L'` berturut-turut** — dan elemen yang memutus pola (bukan `'L'`) **tidak ikut dihitung sama sekali** dalam streak itu.

Tapi di soal ini, elemen yang "memutus" pola naik **tetap ikut dihitung** — dia jadi **elemen pertama** dari barisan naik yang baru (barisan naik minimal selalu berisi **dirinya sendiri**, panjang `1`). Itu sebabnya reset-nya ke `1`, bukan `0` — mencerminkan bahwa elemen itu sendiri sudah membentuk barisan naik yang valid (walau cuma sepanjang satu elemen).

______________________________________________________________________

## 🔧 Alternatif: Sliding Window dengan Dua Pointer Eksplisit

```java
public int findLengthOfLCIS(int[] nums) {
    int ans = 0, left = 0;
    for (int right = 0; right < nums.length; right++) {
        if (right > 0 && nums[right - 1] >= nums[right])
            left = right; // mulai window baru dari sini
        ans = Math.max(ans, right - left + 1);
    }
    return ans;
}
```

Versi ini memakai dua pointer eksplisit (`left`, `right`) untuk menandai batas window barisan naik saat ini, alih-alih counter tunggal (`cnt`). Panjang window dihitung langsung dari `right - left + 1`. Secara logika identik dengan kode asli, cuma beda representasi — versi ini lebih menonjolkan konsep "sliding window", sementara versi asli lebih menonjolkan konsep "streak counter".

| Approach | Time | Space | Representasi |
| -------------------------- | ---- | ----- | ------------------------------ |
| Streak counter (kode asli) | O(n) | O(1) | Counter tunggal (`cnt`) |
| Sliding window dua pointer | O(n) | O(1) | Batas window (`left`, `right`) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi lain dari pola **"streak counter dengan reset saat pola terputus"** yang juga muncul di _Student Attendance Record I_ — bedanya di sini nilai reset-nya `1`, bukan `0`, karena elemen yang memutus pola tetap valid sebagai awal barisan baru. Selalu perhatikan **apa yang terjadi pada elemen yang memutus pola**: apakah dia ikut dihitung sebagai awal baru (reset ke `1`), atau benar-benar dibuang dari perhitungan (reset ke `0`) — perbedaan kecil ini menentukan nilai reset yang tepat. Pola streak seperti ini juga relevan untuk soal-soal seperti _Max Consecutive Ones_ dan _Longest Turbulent Subarray_. 🎯

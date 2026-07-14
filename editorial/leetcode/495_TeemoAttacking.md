# 495. Teemo Attacking

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Simulation
- **Link**: [Problem](https://leetcode.com/problems/teemo-attacking/)
- **Solution**: [Code](../../leetcode/TeemoAttacking.java)

______________________________________________________________________

## 📄 Problem Summary

Teemo menyerang musuh di waktu-waktu tertentu (`timeSeries`). Setiap serangan membuat musuh **keracunan selama `duration` detik**. Jika diserang saat masih keracunan, durasi **direset** (tidak bertambah kumulatif). Hitung total detik keracunan.

Contoh:

- `timeSeries = [1,4]`, `duration = 2` → `4`
  - Serang di t=1: racun t=1,2
  - Serang di t=4: racun t=4,5
  - Total = 4
- `timeSeries = [1,2]`, `duration = 2` → `3`
  - Serang di t=1: racun t=1,2
  - Serang di t=2: reset → racun t=2,3
  - Total = 3 (bukan 4 karena t=2 tumpang tindih)

______________________________________________________________________

## 💡 Intuition

Untuk setiap serangan di `timeSeries[i]`, kontribusi keracunannya bergantung pada jarak ke serangan berikutnya:

- Jika jarak ke serangan berikutnya `>= duration` → kontribusi penuh = `duration`
- Jika jarak `< duration` → kontribusi = jarak (serangan berikutnya memotong efek)

Untuk serangan terakhir, selalu berkontribusi penuh `duration`.

```
timeSeries = [1,4], duration = 2
Jarak antar serangan = 4-1 = 3 >= 2 → keduanya penuh = 2+2 = 4

timeSeries = [1,2], duration = 2
Jarak = 2-1 = 1 < 2 → serangan pertama hanya kontribusi 1 (dipotong)
Serangan terakhir penuh = 1+2 = 3
```

______________________________________________________________________

## 🔍 Approach — Refactor: Interval Overlap

```java
class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int ans = 0;
        for (int i = 0; i < timeSeries.length - 1; i++) {
            int gap = timeSeries[i + 1] - timeSeries[i];
            ans += Math.min(gap, duration);
        }
        ans += duration;  // serangan terakhir selalu penuh
        return ans;
    }
}
```

**Logika per pasangan:**

- `gap = timeSeries[i+1] - timeSeries[i]`
- Kontribusi serangan `i` = `min(gap, duration)`
- Setelah loop, tambahkan `duration` untuk serangan terakhir

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------- |
| **Time** | O(n) — satu pass |
| **Space** | O(1) — hanya variabel `ans` dan `gap` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `timeSeries = [1,4]`, `duration = 2`

| i | timeSeries[i] | timeSeries[i+1] | gap | min(gap,2) | ans |
| --- | ------------- | --------------- | --- | ---------- | --- |
| 0 | 1 | 4 | 3 | min(3,2)=2 | 2 |

Setelah loop: `ans += 2` → `ans = 4`

**Output: `4` ✅**

______________________________________________________________________

**Input:** `timeSeries = [1,2]`, `duration = 2`

| i | gap | min(gap,2) | ans |
| --- | --- | ---------- | --- |
| 0 | 1 | min(1,2)=1 | 1 |

Setelah loop: `ans += 2` → `ans = 3`

**Output: `3` ✅**

______________________________________________________________________

**Input:** `timeSeries = [1,2,3,4,5]`, `duration = 5`

| i | gap | min(gap,5) | ans |
| --- | --- | ---------- | --- |
| 0 | 1 | 1 | 1 |
| 1 | 1 | 1 | 2 |
| 2 | 1 | 1 | 3 |
| 3 | 1 | 1 | 4 |

Setelah loop: `ans += 5` → `ans = 9`

**Output: `9` ✅**
(Serangan tiap detik, racun selalu direset, total = 4 detik overlap + 5 detik terakhir)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Satu serangan → tidak ada loop → return `duration`
- [ ] Semua serangan berjarak > duration → tidak ada overlap → return `n * duration`
- [ ] Semua serangan berturut-turut (gap=1) dengan duration besar → banyak overlap

______________________________________________________________________

## 🔧 Masalah di Kode Asli

```java
if (lastTimer == 0)  // ❌ hanya benar jika timeSeries[0] == 0
    ans += duration;
```

Jika `timeSeries[0] = 5`, `lastTimer = 0 != 5` → masuk `else`. Kebetulan hasilnya benar tapi logika salah — `lastTimer >= i` (0 >= 5) false → `ans += duration`. Ini **coincidentally correct** bukan karena logika yang tepat.

Refactor menghilangkan masalah ini dengan pendekatan yang lebih jelas: cukup bandingkan setiap pasangan serangan tanpa perlu tracking `lastTimer`.

______________________________________________________________________

## 🔧 Kenapa `min(gap, duration)` Cukup?

```
gap >= duration: serangan i habis sebelum serangan i+1 → kontribusi penuh = duration
gap < duration:  serangan i+1 datang lebih cepat → kontribusi = gap (dipotong)
```

`Math.min(gap, duration)` menangkap kedua kasus dalam satu ekspresi — tidak perlu if-else.

______________________________________________________________________

## 📊 Perbandingan Kode Asli vs Refactor

| | Kode Asli | Refactor |
| ------------- | ---------------------------------------------- | ------------------------------- |
| Pendekatan | Tracking `lastTimer` | Perbandingan gap antar serangan |
| Bug potensial | `lastTimer == 0` hanya benar untuk t[0]=0 | Tidak ada |
| Readability | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| Baris kode | ~12 baris | 5 baris |
| Logika inti | `duration - lastTimer + i - 1` (membingungkan) | `min(gap, duration)` (jelas) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini bisa disederhanakan dengan melihat setiap pasangan serangan berturutan — kontribusi setiap serangan adalah `min(jarak ke serangan berikutnya, duration)`. Serangan terakhir selalu berkontribusi penuh. Pola `min(gap, duration)` jauh lebih bersih dari tracking timer manual. 🎯

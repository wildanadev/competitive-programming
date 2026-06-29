# 278. First Bad Version

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Binary Search, Interactive
- **Link**: [Problem](https://leetcode.com/problems/first-bad-version/)
- **Solution**: [Code](../../leetcode/FirstBadVersion.java)

______________________________________________________________________

## 📄 Problem Summary

Ada `n` versi software dari `1` sampai `n`. Versi yang buruk (`bad`) menyebabkan semua versi **setelahnya** juga buruk. Diberikan API `isBadVersion(version)`, temukan versi buruk **pertama** dengan **panggilan API seminimal mungkin**.

Contoh:

- `n = 5`, versi buruk mulai dari `4` → `isBadVersion: [false,false,false,true,true]` → return `4`
- `n = 1`, versi buruk dari `1` → return `1`

______________________________________________________________________

## 💡 Intuition

Karena begitu satu versi buruk, **semua versi setelahnya juga buruk**, urutan `isBadVersion` membentuk pola: `false, false, ..., false, true, true, ..., true`. Ini adalah array **monoton** — cocok untuk **Binary Search**.

Kita cari **titik transisi pertama** dari `false` ke `true`.

```
versi:  1     2     3     4     5
status: false false false true  true
                          ↑
                    jawaban: 4
```

______________________________________________________________________

## 🔍 Approach

### Binary Search untuk Titik Transisi

1. `l = 1`, `r = n`.
1. Selama `l <= r`:
   - `m = l + (r-l)/2`
   - Jika `isBadVersion(m)` → `m` mungkin jawaban, tapi mungkin masih ada yang lebih awal → `r = m-1` (cari di kiri).
   - Jika tidak (`m` bagus) → jawaban pasti di kanan `m` → `l = m+1`.
1. Return `l` — saat loop berakhir, `l` adalah versi buruk pertama.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------- |
| **Time** | O(log n) — binary search, setiap iterasi 1 panggilan API |
| **Space** | O(1) — hanya pointer |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `n = 5`, versi buruk mulai dari `4`

`status = [F,F,F,T,T]` (indeks 1-5)

`l=1, r=5`

| l | r | m | isBadVersion(m) | Aksi |
| --- | --- | --- | --------------- | ---- |
| 1 | 5 | 3 | false | l=4 |
| 4 | 5 | 4 | true | r=3 |

`l=4 > r=3` → loop berhenti → return `l=4`

**Output: `4` ✅**

______________________________________________________________________

**Input:** `n = 1`, versi buruk dari `1`

`l=1, r=1`

| l | r | m | isBadVersion(m) | Aksi |
| --- | --- | --- | --------------- | ---- |
| 1 | 1 | 1 | true | r=0 |

`l=1 > r=0` → return `l=1`

**Output: `1` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua versi buruk (`isBadVersion(1) = true`) → return `1`
- [ ] Hanya versi terakhir buruk → binary search tetap O(log n)
- [ ] `n = 1` → satu panggilan API saja

______________________________________________________________________

## 🔧 Kenapa Return `l`, Bukan `r`, Setelah Loop?

```
Invariant yang dijaga sepanjang loop:
- l selalu kandidat "mungkin jawaban atau lebih kecil dari jawaban yang belum dieliminasi"
- r selalu menyusut ke kiri saat menemukan versi buruk

Saat l > r:
  l = posisi PERTAMA yang ditemukan bad (atau yang terkecil)
  r = posisi terakhir yang dipastikan GOOD
```

Setiap kali `isBadVersion(m) == true`, kita TIDAK langsung return `m` — karena mungkin ada versi buruk yang **lebih awal**. Kita simpan kemungkinan ini dengan `r = m-1` dan terus cari di kiri. `l` secara bertahap "naik" mendekati titik transisi sampai akhirnya `l` berhenti tepat di titik transisi.

______________________________________________________________________

## 🔧 Kenapa Tidak `return m` Langsung Saat `isBadVersion(m)` True?

```java
if (isBadVersion(m)) {
    return m;  // ❌ SALAH! m belum tentu yang PERTAMA buruk
}
```

Contoh: `n=5`, buruk mulai dari `2`. Jika `m=4` dicek dan `isBadVersion(4)=true`, langsung `return 4` akan **salah** — jawaban yang benar adalah `2`.

```java
if (isBadVersion(m)) {
    r = m - 1;  // ✅ BENAR! simpan kemungkinan, cari lebih awal lagi
}
```

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah variasi binary search untuk **mencari titik transisi pertama** dalam array boolean yang monoton (`false...false, true...true`). Berbeda dari binary search standar yang mencari nilai eksak, di sini kita terus mempersempit ke kiri setiap menemukan `true` — karena mungkin ada `true` yang lebih awal. `l` akan konvergen tepat ke titik transisi pertama. Pola ini sangat umum disebut **binary search on answer** atau **boundary search**. 🎯

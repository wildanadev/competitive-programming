# 1652. Defuse the Bomb

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Sliding Window
- **Link**: [Problem](https://leetcode.com/problems/defuse-the-bomb/)
- **Solution**: [Code](../../leetcode/DefuseTheBomb.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array sirkular `code` dan integer `k`, gantikan setiap elemen `code[i]` dengan:

- Jumlah `k` elemen **berikutnya** jika `k > 0`
- Jumlah `k` elemen **sebelumnya** jika `k < 0`
- `0` jika `k == 0`

Array bersifat **sirkular** — elemen setelah indeks terakhir kembali ke indeks `0`.

Contoh:

- `code = [5,7,1,4]`, `k = 3` → `[12,10,16,13]`
- `code = [1,2,3,4]`, `k = 0` → `[0,0,0,0]`
- `code = [2,4,9,3]`, `k = -2` → `[12,5,6,13]`

______________________________________________________________________

## 💡 Intuition

Setiap elemen `ans[i]` adalah jumlah window berukuran `|k|` yang posisinya **relatif terhadap `i`**. Saat `i` bergeser satu langkah ke kanan, window juga bergeser satu langkah — satu elemen masuk dari kanan, satu elemen keluar dari kiri.

Ini adalah pola **Sliding Window** klasik, dengan tambahan bahwa array bersifat sirkular sehingga semua akses indeks menggunakan **modulo `n`**.

Kunci sederhananya: daripada menentukan window relatif terhadap setiap `i`, tentukan posisi **`l` dan `r` absolut** untuk window pertama (`i = 0`), lalu geser keduanya bersama `i`.

______________________________________________________________________

## 🔍 Approach

### Sliding Window dengan Posisi `l` dan `r` Absolut

**Step 1 — Tentukan window awal untuk `i = 0`:**

- `k > 0`: `l = 1`, `r = k` → elemen `code[1]` sampai `code[k]`
- `k < 0`: `l = n + k`, `r = n - 1` → elemen `code[n+k]` sampai `code[n-1]`

**Step 2 — Hitung `wSum` awal** dengan menjumlahkan `code[l..r]` (indeks di-modulo `n`).

**Step 3 — Sliding:** untuk setiap `i` dari `0` sampai `n-1`:

- Simpan `wSum` ke `ans[i]`.
- Geser window: `wSum += code[(r+1) % n] - code[l % n]`
- Increment `l++` dan `r++`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------- | --- | ---------------- |
| **Time** | O(n) — hitung window awal O( | k | ) + sliding O(n) |
| **Space** | O(1) — tidak ada struktur data tambahan (tidak termasuk output) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `code = [5,7,1,4]`, `k = 3`

`n = 4`, `k > 0` → `l = 1`, `r = 3`

**Hitung `wSum` awal `code[1..3]`:**

```
wSum = code[1] + code[2] + code[3] = 7 + 1 + 4 = 12
```

**Sliding:**

| i | ans[i] | masuk: code[(r+1)%n] | keluar: code[l%n] | wSum baru | l | r |
| --- | ------ | ----------------------- | ----------------------- | --------------- | --- | --- |
| 0 | 12 | code[4%4] = code[0] = 5 | code[1%4] = code[1] = 7 | 12+5-7 = **10** | 2 | 4 |
| 1 | 10 | code[5%4] = code[1] = 7 | code[2%4] = code[2] = 1 | 10+7-1 = **16** | 3 | 5 |
| 2 | 16 | code[6%4] = code[2] = 1 | code[3%4] = code[3] = 4 | 16+1-4 = **13** | 4 | 6 |
| 3 | 13 | — | — | — | — | — |

**Output: `[12,10,16,13]` ✅**

______________________________________________________________________

**Input:** `code = [2,4,9,3]`, `k = -2`

`n = 4`, `k < 0` → `l = n+k = 4+(-2) = 2`, `r = n-1 = 3`

**Hitung `wSum` awal `code[2..3]`:**

```
wSum = code[2] + code[3] = 9 + 3 = 12
```

**Sliding:**

| i | ans[i] | masuk: code[(r+1)%n] | keluar: code[l%n] | wSum baru | l | r |
| --- | ------ | ----------------------- | ----------------------- | -------------- | --- | --- |
| 0 | 12 | code[4%4] = code[0] = 2 | code[2%4] = code[2] = 9 | 12+2-9 = **5** | 3 | 4 |
| 1 | 5 | code[5%4] = code[1] = 4 | code[3%4] = code[3] = 3 | 5+4-3 = **6** | 4 | 5 |
| 2 | 6 | code[6%4] = code[2] = 9 | code[4%4] = code[0] = 2 | 6+9-2 = **13** | 5 | 6 |
| 3 | 13 | — | — | — | — | — |

**Output: `[12,5,6,13]` ✅**

______________________________________________________________________

**Input:** `code = [1,2,3,4]`, `k = 0`

Return `[0,0,0,0]` langsung.

**Output: `[0,0,0,0]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k == 0` → return array nol tanpa proses
- [ ] `|k| == n - 1` → window mencakup hampir seluruh array kecuali elemen `i` sendiri
- [ ] `|k| == 1` → window hanya satu elemen
- [ ] `k == n` → window mencakup seluruh array termasuk elemen `i` (tidak terjadi di constraint soal)

______________________________________________________________________

## 📌 Key Takeaway

Kunci soal ini adalah menyatukan kasus `k > 0` dan `k < 0` dengan **menentukan posisi `l` dan `r` absolut** di awal — setelah itu sliding window yang sama bekerja untuk keduanya. Modulo `n` pada setiap akses menangani sirkularitas secara natural. Formula sliding: `wSum += code[(r+1)%n] - code[l%n]` adalah inti dari semua soal fixed sliding window, dengan tambahan `% n` untuk array sirkular. 🎯

# 739. Daily Temperatures

- **Platform**: LeetCode
- **Difficulty**: Medium
- **Topics**: Array, Stack, Monotonic Stack
- **Link**: [Problem](https://leetcode.com/problems/daily-temperatures/)
- **Solution**: [Code](../../leetcode/DailyTemperatures.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array `temperatures`, untuk setiap hari `i` hitung berapa hari lagi sampai ada hari yang lebih hangat. Jika tidak ada, return `0`.

Contoh:

- `temperatures = [73,74,75,71,69,72,76,73]` → `[1,1,4,2,1,1,0,0]`
- `temperatures = [30,40,50,60]` → `[1,1,1,0]`
- `temperatures = [30,60,90]` → `[1,1,0]`

______________________________________________________________________

## 💡 Intuition

Untuk setiap hari, kita butuh **Next Greater Element** — hari pertama berikutnya yang suhunya lebih tinggi. Ini adalah pola klasik **Monotonic Stack**.

Stack menyimpan **indeks** hari-hari yang **belum menemukan hari lebih hangat**. Saat hari baru `i` ditemukan:

- Jika suhu hari `i` lebih tinggi dari suhu di indeks top stack → hari `i` adalah jawaban untuk top stack → pop dan hitung selisih indeks.
- Ulangi sampai stack kosong atau suhu top stack ≥ suhu hari `i`.
- Push indeks `i` ke stack.

Stack selalu dalam kondisi **monotonically non-increasing** (suhu dari bawah ke atas semakin kecil atau sama).

______________________________________________________________________

## 🔍 Approach

### Monotonic Stack (Next Greater Element)

1. Inisialisasi `ans` berisi semua `0` (default jika tidak ada hari lebih hangat).
1. Loop `i` dari `0` sampai `n-1`:
   - Selama stack tidak kosong **dan** `temperatures[stack.peek()] < temperatures[i]`:
     - `ans[stack.peek()] = i - stack.pop()` → selisih indeks = jumlah hari
   - Push `i` ke stack.
1. Indeks yang tersisa di stack tidak punya hari lebih hangat → `ans[idx] = 0` (sudah default).
1. Return `ans`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------- |
| **Time** | O(n) — setiap indeks di-push dan di-pop tepat satu kali |
| **Space** | O(n) — stack menyimpan maksimal semua indeks |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `temperatures = [73,74,75,71,69,72,76,73]`

`ans = [0,0,0,0,0,0,0,0]`

| i | temp[i] | Stack sebelum | Aksi (while) | Stack setelah | ans |
| --- | ------- | ------------- | ----------------------------------------------------------------------------------------------------- | ------------- | ------------------- |
| 0 | 73 | `[]` | kosong → push 0 | `[0]` | `[0,0,0,0,0,0,0,0]` |
| 1 | 74 | `[0]` | temp[0]=73 < 74 → ans[0]=1-0=1, pop; push 1 | `[1]` | `[1,0,0,0,0,0,0,0]` |
| 2 | 75 | `[1]` | temp[1]=74 < 75 → ans[1]=2-1=1, pop; push 2 | `[2]` | `[1,1,0,0,0,0,0,0]` |
| 3 | 71 | `[2]` | temp[2]=75 < 71? ❌ → push 3 | `[2,3]` | `[1,1,0,0,0,0,0,0]` |
| 4 | 69 | `[2,3]` | temp[3]=71 < 69? ❌ → push 4 | `[2,3,4]` | `[1,1,0,0,0,0,0,0]` |
| 5 | 72 | `[2,3,4]` | temp[4]=69 < 72 → ans[4]=5-4=1, pop; temp[3]=71 < 72 → ans[3]=5-3=2, pop; temp[2]=75 < 72? ❌; push 5 | `[2,5]` | `[1,1,0,2,1,0,0,0]` |
| 6 | 76 | `[2,5]` | temp[5]=72 < 76 → ans[5]=6-5=1, pop; temp[2]=75 < 76 → ans[2]=6-2=4, pop; push 6 | `[6]` | `[1,1,4,2,1,1,0,0]` |
| 7 | 73 | `[6]` | temp[6]=76 < 73? ❌ → push 7 | `[6,7]` | `[1,1,4,2,1,1,0,0]` |

Stack sisa `[6,7]` → `ans[6]=0`, `ans[7]=0` (sudah default)

**Output: `[1,1,4,2,1,1,0,0]` ✅**

______________________________________________________________________

**Input:** `temperatures = [30,40,50,60]`

| i | temp[i] | Aksi | Stack | ans |
| --- | ------- | ------------------------ | ----- | ----------- |
| 0 | 30 | push 0 | `[0]` | `[0,0,0,0]` |
| 1 | 40 | pop 0 (ans[0]=1), push 1 | `[1]` | `[1,0,0,0]` |
| 2 | 50 | pop 1 (ans[1]=1), push 2 | `[2]` | `[1,1,0,0]` |
| 3 | 60 | pop 2 (ans[2]=1), push 3 | `[3]` | `[1,1,1,0]` |

**Output: `[1,1,1,0]` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Array terurut descending → tidak ada yang lebih hangat → semua `0` → stack tidak pernah di-pop
- [ ] Array terurut ascending → setiap hari di-pop saat hari berikutnya diproses → semua `1` kecuali terakhir
- [ ] Satu elemen → tidak ada hari berikutnya → return `[0]`
- [ ] Suhu sama berurutan → `<` (strict) → tidak di-pop, dibiarkan di stack → `0`

______________________________________________________________________

## 🔧 Kenapa Stack Menyimpan Indeks, Bukan Suhu?

```java
stack.push(i);           // simpan indeks ✅
// bukan:
stack.push(temperatures[i]);  // simpan suhu ❌
```

Karena kita butuh **dua informasi**:

1. Berapa suhu hari tersebut (untuk perbandingan) → `temperatures[stack.peek()]`
1. Di mana hari tersebut (untuk hitung selisih) → `stack.peek()` sebagai indeks

Dengan menyimpan indeks, kita bisa mengakses kedua informasi dari satu nilai: `temperatures[idx]` untuk suhu dan `idx` untuk posisi.

______________________________________________________________________

## 🔧 Kenapa `<` bukan `<=`?

```java
temperatures[stack.peek()] < temperatures[i]  // strict less than
```

Jika suhu sama, kita **tidak** pop — hari dengan suhu sama bukan "hari yang lebih hangat". Soal meminta hari yang **strictly warmer**.

Contoh: `[70, 70, 71]`

```
i=2, temp=71:
  pop indeks 1 (temp=70 < 71) → ans[1] = 1
  pop indeks 0 (temp=70 < 71) → ans[0] = 2
Output: [2,1,0] ✅

Bukan: [1,1,0] yang salah jika suhu sama dihitung
```

______________________________________________________________________

## 📌 Key Takeaway

**Monotonic Stack** adalah solusi optimal untuk soal **Next Greater/Smaller Element** — O(n) karena setiap elemen hanya masuk dan keluar stack sekali. Stack dipertahankan dalam kondisi **monotonically non-increasing** (suhu semakin kecil dari bawah ke atas), sehingga saat elemen baru yang lebih besar ditemukan, ia langsung bisa "menyelesaikan" banyak indeks sekaligus. Pola yang sama muncul di _Final Prices_, _Next Greater Element I/II_, dan _Largest Rectangle in Histogram_. 🎯

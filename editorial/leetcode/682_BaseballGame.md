# 682. Baseball Game

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Stack, Simulation
- **Link**: [Problem](https://leetcode.com/problems/baseball-game/)
- **Solution**: [Code](../../leetcode/BaseballGame.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan array string `operations`, merepresentasikan urutan pencatatan skor dalam permainan baseball unik. Tiap elemen bisa berupa:

- **Angka** (misal `"5"`) → tambahkan skor baru ke rekaman.
- `"+"` → rekaman baru = **jumlah** dua skor terakhir yang tercatat.
- `"D"` → rekaman baru = **dua kali** skor terakhir yang tercatat.
- `"C"` → **hapus** skor terakhir yang tercatat (dianggap tidak valid/batal).

Kembalikan **total jumlah** seluruh skor yang tercatat setelah semua operasi selesai.

Contoh:

- `ops = ["5","2","C","D","+"]` → `30`
  - `"5"` → catat `5` → rekaman: `[5]`
  - `"2"` → catat `2` → rekaman: `[5, 2]`
  - `"C"` → hapus skor terakhir (`2`) → rekaman: `[5]`
  - `"D"` → catat `2× skor terakhir` (`2×5=10`) → rekaman: `[5, 10]`
  - `"+"` → catat `jumlah 2 skor terakhir` (`10+5=15`) → rekaman: `[5, 10, 15]`
  - Total `= 5+10+15 = 30`

______________________________________________________________________

## 💡 Intuition

Ini soal **simulasi berbasis stack** yang klasik: kita cuma pernah butuh **elemen paling atas (terakhir)** — baik untuk membaca dua skor terakhir (`"+"`), menggandakan skor terakhir (`"D"`), atau menghapus skor terakhir (`"C"`). Pola akses "selalu dari ujung, LIFO (Last In First Out)" ini persis definisi **stack**.

Solusi ini mengimplementasikan stack itu **secara manual pakai array** (bukan `Deque`/`Stack` bawaan Java), dengan variabel `size` sebagai penanda "seberapa penuh" stack saat ini (setara `top` pointer). Ini valid karena constraint soal menjamin batas atas jumlah operasi, jadi ukuran array bisa dialokasikan sekali di awal (`operations.length`) tanpa risiko overflow.

______________________________________________________________________

## 🔍 Approach

### Simulasi Stack dengan Array + Pointer `size`

1. Siapkan `records` — array integer seukuran `operations.length` (cukup untuk menampung skenario terburuk, semua elemen adalah angka valid tanpa `"C"`).
1. `size = 0` menandai posisi "slot kosong berikutnya" di stack (setara jumlah elemen aktif saat ini).
1. Loop tiap `operations[i]`:
   - `"+"` → `records[size] = records[size-1] + records[size-2]`, lalu `size++`.
   - `"D"` → `records[size] = records[size-1] * 2`, lalu `size++`.
   - `"C"` → `size--` (elemen lama tidak perlu dihapus fisik, cukup dianggap "di luar jangkauan" karena `size` sudah mundur).
   - **Angka** → `records[size++] = Integer.parseInt(i)` (simpan lalu geser pointer).
1. Setelah semua operasi diproses, jumlahkan `records[0]` sampai `records[size-1]` (hanya bagian yang masih "aktif" sesuai `size` terakhir).
1. Kembalikan total.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------- |
| **Time** | O(n) — n = `operations.length`, satu pass untuk proses + satu pass untuk sum |
| **Space** | O(n) — array `records` dialokasikan seukuran total operasi |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `operations = ["5","2","C","D","+"]`

| Operasi | Aksi | records | size |
| ------- | --------------------------------------------------- | ------------------------------ | ---- |
| `"5"` | `records[0]=5`, `size++` | `[5]` | 1 |
| `"2"` | `records[1]=2`, `size++` | `[5,2]` | 2 |
| `"C"` | `size--` (skor `2` dianggap batal) | `[5,2]` (2 tidak dipakai lagi) | 1 |
| `"D"` | `records[1] = records[0]*2 = 10`, `size++` | `[5,10]` | 2 |
| `"+"` | `records[2] = records[1]+records[0] = 15`, `size++` | `[5,10,15]` | 3 |

Sum `records[0..2] = 5+10+15 = 30`.

**Output: `30`** ✅

______________________________________________________________________

**Input:** `operations = ["5","-2","4","C","D","9","+","+"]`

| Operasi | Aksi | records aktif | size |
| ------- | ------------------------- | ------------------ | ---- |
| `"5"` | catat `5` | `[5]` | 1 |
| `"-2"` | catat `-2` | `[5,-2]` | 2 |
| `"4"` | catat `4` | `[5,-2,4]` | 3 |
| `"C"` | hapus skor terakhir (`4`) | `[5,-2]` | 2 |
| `"D"` | catat `2×(-2)=-4` | `[5,-2,-4]` | 3 |
| `"9"` | catat `9` | `[5,-2,-4,9]` | 4 |
| `"+"` | catat `9+(-4)=5` | `[5,-2,-4,9,5]` | 5 |
| `"+"` | catat `5+9=14` | `[5,-2,-4,9,5,14]` | 6 |

Sum `= 5-2-4+9+5+14 = 27`.

**Output: `27`** ✅ (cocok dengan contoh resmi soal)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `"C"` sebagai operasi pertama tidak mungkin terjadi (constraint soal menjamin operasi selalu valid — `"C"`, `"+"`, dan `"D"` selalu punya cukup skor sebelumnya untuk diproses)
- [ ] Skor negatif (misal `"-2"`) → tetap diproses sama seperti angka positif, karena `Integer.parseInt` menangani tanda minus dengan benar
- [ ] `"C"` beruntun (misal `"C","C"`) → `size` cukup dikurangi dua kali, tidak masalah selama operasi sebelumnya memang valid
- [ ] Semua operasi berupa angka tanpa `"+"`, `"D"`, `"C"` → langsung sum semua elemen apa adanya

______________________________________________________________________

## 🔧 Kenapa `"C"` Tidak Perlu Menghapus Nilai di Array Secara Fisik?

Perhatikan implementasi `"C"`:

```java
else if (i.equals("C"))
    size--;
```

Nilai lama di `records[size]` (sebelum dikurangi) **tidak dihapus/di-reset ke 0** — cukup `size` yang mundur. Ini valid karena **semua operasi lain hanya pernah membaca/menulis berdasarkan `size` saat ini** (`records[size-1]`, `records[size-2]`, `records[size]`), jadi elemen "sisa" di luar jangkauan `size` yang aktif **tidak akan pernah terbaca lagi** — baik oleh operasi berikutnya maupun loop sum di akhir (yang cuma loop sampai `size` terakhir). Kalau nanti ada operasi baru (misal angka baru) yang menulis ke `records[size]`, nilai lama itu otomatis **tertimpa**. Ini pola umum "logical deletion" di struktur data berbasis array — lebih cepat daripada benar-benar menghapus/menggeser elemen.

______________________________________________________________________

## 🔧 Alternatif: Pakai `Deque<Integer>` Bawaan Java

```java
public int calPoints(String[] operations) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (String op : operations) {
        switch (op) {
            case "+" -> {
                int top = stack.pop();
                int newTop = top + stack.peek();
                stack.push(top);
                stack.push(newTop);
            }
            case "D" -> stack.push(stack.peek() * 2);
            case "C" -> stack.pop();
            default -> stack.push(Integer.parseInt(op));
        }
    }
    return stack.stream().mapToInt(Integer::intValue).sum();
}
```

Versi ini memakai `Deque` sebagai stack sungguhan lewat `push`/`pop`/`peek`, lebih idiomatis dan mudah dibaca karena langsung memakai istilah stack, tapi butuh sedikit logika ekstra untuk kasus `"+"` (harus `pop` dulu untuk baca dua nilai teratas, lalu `push` balik supaya tidak kehilangan elemen).

| Approach | Time | Space | Butuh Alokasi Ukuran Tetap di Awal? |
| ----------------------------------------- | ---- | ----- | ----------------------------------- |
| Array manual + pointer `size` (kode asli) | O(n) | O(n) | Ya |
| `Deque<Integer>` bawaan Java | O(n) | O(n) | Tidak (tumbuh dinamis) |

Array manual sedikit lebih cepat secara konstanta (tidak ada overhead boxing/unboxing berulang atau resizing internal), tapi `Deque` lebih fleksibel kalau ukuran maksimum tidak diketahui di awal.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah contoh baik dari **stack yang diimplementasikan manual pakai array + pointer**, sebuah teknik umum ketika ukuran maksimum data sudah diketahui di awal (di sini: `operations.length`) — menghindari overhead struktur data dinamis seperti `Deque`/`Stack` bawaan. Kuncinya memahami bahwa "penghapusan" di stack berbasis array **tidak perlu** menghapus nilai secara fisik, cukup menggeser pointer `size`, karena data lama otomatis menjadi tidak relevan dan akan tertimpa kalau ada penulisan baru. Pola simulasi stack seperti ini juga muncul di soal-soal seperti _Evaluate Reverse Polish Notation_ dan _Remove All Adjacent Duplicates In String_. 🎯

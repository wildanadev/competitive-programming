# 1700. Number of Students Unable to Eat Lunch

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Stack, Queue, Simulation, Counting
- **Link**: [Problem](https://leetcode.com/problems/number-of-students-unable-to-eat-lunch/)
- **Solution**: [Code](../../leetcode/NumberOfStudentsUnableToEatLunch.java)

______________________________________________________________________

## 📄 Problem Summary

Ada `n` siswa dalam antrian dan `n` sandwich dalam stack. Setiap siswa punya preferensi sandwich:

- `0` = sandwich lingkaran
- `1` = sandwich kotak

Proses:

- Jika siswa paling depan **suka** sandwich paling atas stack → ambil sandwich, keluar antrian.
- Jika **tidak suka** → pindah ke belakang antrian.
- Proses berhenti jika tidak ada siswa yang bisa mengambil sandwich teratas.

Kembalikan jumlah siswa yang tidak bisa makan.

Contoh:

- `students = [1,1,0,0]`, `sandwiches = [0,1,0,1]` → `0`
- `students = [1,1,1,0,0,1]`, `sandwiches = [1,0,0,0,1,1]` → `3`

______________________________________________________________________

## 💡 Intuition

Kunci insight: **urutan siswa tidak penting** — yang penting adalah **berapa banyak siswa yang suka tipe tertentu**.

Mengapa? Karena siswa yang tidak suka sandwich teratas akan terus berputar di antrian sampai ada yang mau. Jika **tidak ada satupun** siswa yang suka tipe sandwich teratas → proses berhenti.

Jadi kita tidak perlu simulasi antrian — cukup hitung `circle` (siswa suka 0) dan `square` (siswa suka 1), lalu iterasi stack sandwich dari atas:

- Jika sandwich `0` tapi `circle == 0` → tidak ada yang mau → return `square`.
- Jika sandwich `1` tapi `square == 0` → tidak ada yang mau → return `circle`.
- Jika masih ada yang mau → kurangi counter yang sesuai.

______________________________________________________________________

## 🔍 Approach

### Counting — Tanpa Simulasi Antrian

1. Hitung `circle` dan `square` dari array `students`.
1. Iterasi `sandwiches` dari depan (top stack):
   - Jika `sandwich == 0 && circle == 0` → tidak ada yang mau → return `square`.
   - Jika `sandwich == 1 && square == 0` → tidak ada yang mau → return `circle`.
   - Jika tidak → ada yang mau → kurangi counter yang sesuai.
1. Return `0` — semua siswa bisa makan.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------- |
| **Time** | O(n) — dua loop linear |
| **Space** | O(1) — hanya dua variabel counter |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `students = [1,1,0,0]`, `sandwiches = [0,1,0,1]`

**Hitung counter:**

```
circle = 2 (dua siswa suka 0)
square = 2 (dua siswa suka 1)
```

**Iterasi sandwiches:**

| sandwich | circle==0? | square==0? | Aksi | circle | square |
| -------- | ---------- | ---------- | -------- | ------ | ------ |
| 0 | 2==0? ❌ | — | circle-- | 1 | 2 |
| 1 | — | 2==0? ❌ | square-- | 1 | 1 |
| 0 | 1==0? ❌ | — | circle-- | 0 | 1 |
| 1 | — | 1==0? ❌ | square-- | 0 | 0 |

Semua sandwich diambil → return `0`

**Output: `0` ✅**

______________________________________________________________________

**Input:** `students = [1,1,1,0,0,1]`, `sandwiches = [1,0,0,0,1,1]`

**Hitung counter:**

```
circle = 2 (dua siswa suka 0)
square = 4 (empat siswa suka 1)
```

**Iterasi sandwiches:**

| sandwich | circle==0? | square==0? | Aksi | circle | square |
| -------- | ------------ | ---------- | ------------------- | ------ | ------ |
| 1 | — | 4==0? ❌ | square-- | 2 | 3 |
| 0 | 2==0? ❌ | — | circle-- | 1 | 3 |
| 0 | 1==0? ❌ | — | circle-- | 0 | 3 |
| 0 | **0==0? ✅** | — | return square=**3** | — | — |

**Output: `3` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Semua siswa suka tipe yang sama dan sandwich pertama tipe lain → langsung return semua
- [ ] Semua sandwich tipe yang sama dan tidak ada siswa yang suka → return semua siswa
- [ ] Satu siswa satu sandwich → match → return `0`, tidak match → return `1`

______________________________________________________________________

## 🔧 Kenapa Urutan Siswa Tidak Penting?

Bayangkan `students = [0,1,0]` dan sandwich teratas = `1`:

```
Siswa 0 (suka 0) → tidak mau → ke belakang
Siswa 1 (suka 1) → MAU → ambil sandwich
```

Hasilnya sama dengan `students = [1,0,0]`:

```
Siswa 0 (suka 1) → MAU → langsung ambil sandwich
```

Selama **ada minimal satu siswa** yang suka tipe sandwich teratas, sandwich itu pasti akan diambil — tidak peduli di posisi mana siswa itu berada. Inilah yang membuat simulasi antrian tidak diperlukan, cukup counter.

______________________________________________________________________

Bandingkan dengan simulasi O(n²) yang lebih intuitif tapi lebih lambat:

```java
// Simulasi dengan Queue — O(n²)
Queue<Integer> queue = new LinkedList<>();
for (int s : students) queue.offer(s);
Stack<Integer> stack = new Stack<>();
for (int i = sandwiches.length - 1; i >= 0; i--) stack.push(sandwiches[i]);

int attempts = 0;
while (!queue.isEmpty() && attempts < queue.size()) {
    if (queue.peek().equals(stack.peek())) {
        queue.poll();
        stack.pop();
        attempts = 0;
    } else {
        queue.offer(queue.poll());
        attempts++;
    }
}
return queue.size();
```

| Approach | Time | Space | Catatan |
| --------------- | ----- | ----- | ----------------------------- |
| Counting (kode) | O(n) | O(1) | Insight: urutan tidak penting |
| Simulasi Queue | O(n²) | O(n) | Intuitif tapi lambat |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini mengajarkan untuk **tidak langsung mensimulasikan** proses yang dideskripsikan soal. Dengan menyadari bahwa urutan siswa tidak mempengaruhi hasil — yang penting hanya jumlah siswa per tipe — kita bisa mengganti simulasi O(n²) dengan counting O(n). Kapan pun ada antrian sikular dengan elemen yang bisa "skip" dan kembali belakang, tanya dulu: "Apakah urutannya benar-benar penting?" 🎯

# 2073. Time Needed to Buy Tickets

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Queue, Simulation
- **Link**: [Problem](https://leetcode.com/problems/time-needed-to-buy-tickets/)
- **Solution**: [Code](../../leetcode/TimeNeededToBuyTickets.java)

______________________________________________________________________

## 📄 Problem Summary

Ada `n` orang dalam antrian, setiap orang ke-`i` ingin membeli `tickets[i]` tiket. Setiap putaran, orang paling depan membeli **1 tiket** (1 detik), lalu ke belakang jika masih butuh tiket. Kembalikan waktu yang dibutuhkan sampai orang ke-`k` selesai membeli semua tiketnya.

Contoh:

- `tickets = [2,3,2]`, `k = 2` → `6`
- `tickets = [5,1,1,1]`, `k = 0` → `8`

______________________________________________________________________

## 💡 Intuition

Daripada mensimulasikan antrian (O(n × max(tickets))), kita bisa langsung hitung kontribusi waktu setiap orang.

Perhatikan dua kasus untuk setiap orang `i`:

**Orang di depan atau sama dengan `k` (`i <= k`):**
Mereka akan terus membeli tiket sampai orang `k` selesai. Tapi mereka tidak akan membeli lebih dari `tickets[k]` kali (karena begitu `k` selesai, proses berhenti). Jadi kontribusinya = `min(tickets[i], tickets[k])`.

**Orang di belakang `k` (`i > k`):**
Mereka hanya sempat membeli **sebelum** orang `k` selesai di putaran terakhir. Di putaran terakhir `k`, giliran `k` datang sebelum `i` — jadi `i` hanya sempat membeli `tickets[k] - 1` kali. Kontribusinya = `min(tickets[i], tickets[k] - 1)`.

______________________________________________________________________

## 🔍 Approach

### Greedy — Hitung Kontribusi Per Orang

1. Loop semua orang `i` dari `0` sampai `n-1`.
1. Jika `i <= k` → `time += min(tickets[i], tickets[k])`.
1. Jika `i > k` → `time += min(tickets[i], tickets[k] - 1)`.
1. Return `time`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------- |
| **Time** | O(n) — satu pass linear |
| **Space** | O(1) — hanya variabel `time` |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `tickets = [2,3,2]`, `k = 2`

`tickets[k] = tickets[2] = 2`

| i | i \<= k? | tickets[i] | min dengan... | kontribusi | time |
| --- | --------- | ---------- | ------------- | ---------- | ---- |
| 0 | ✅ (0\<=2) | 2 | min(2,2) = 2 | 2 | 2 |
| 1 | ✅ (1\<=2) | 3 | min(3,2) = 2 | 2 | 4 |
| 2 | ✅ (2\<=2) | 2 | min(2,2) = 2 | 2 | 6 |

**Output: `6` ✅**

**Verifikasi simulasi:**

```
Putaran 1: [2,3,2] → [1,3,2] → [1,2,2] → [1,2,1] → t=3
Putaran 2: [1,2,1] → [0,2,1] → [0,1,1] → [0,1,0] → t=6
Orang k=2 selesai di t=6 ✅
```

______________________________________________________________________

**Input:** `tickets = [5,1,1,1]`, `k = 0`

`tickets[k] = tickets[0] = 5`

| i | i \<= k? | tickets[i] | min dengan... | kontribusi | time |
| --- | ------- | ---------- | -------------------------- | ---------- | ---- |
| 0 | ✅ | 5 | min(5,5) = 5 | 5 | 5 |
| 1 | ❌ | 1 | min(1, 5-1) = min(1,4) = 1 | 1 | 6 |
| 2 | ❌ | 1 | min(1,4) = 1 | 1 | 7 |
| 3 | ❌ | 1 | min(1,4) = 1 | 1 | 8 |

**Output: `8` ✅**

______________________________________________________________________

**Input:** `tickets = [2,3,2,1]`, `k = 1`

`tickets[k] = tickets[1] = 3`

| i | i \<= k? | tickets[i] | min dengan... | kontribusi | time |
| --- | ------- | ---------- | -------------------------- | ---------- | ---- |
| 0 | ✅ | 2 | min(2,3) = 2 | 2 | 2 |
| 1 | ✅ | 3 | min(3,3) = 3 | 3 | 5 |
| 2 | ❌ | 2 | min(2, 3-1) = min(2,2) = 2 | 2 | 7 |
| 3 | ❌ | 1 | min(1,2) = 1 | 1 | 8 |

**Output: `8` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `k = 0` → semua orang di belakang `k` → semua pakai `tickets[k] - 1`
- [ ] `k = n-1` → semua orang di depan atau sama → semua pakai `tickets[k]`
- [ ] `tickets[i] < tickets[k]` → orang itu selesai lebih dulu → kontribusi penuh `tickets[i]`
- [ ] `tickets[i] > tickets[k]` → orang itu belum selesai saat `k` selesai → kontribusi dibatasi `tickets[k]` atau `tickets[k]-1`

______________________________________________________________________

## 🔧 Kenapa `tickets[k] - 1` untuk `i > k`?

Visualisasi putaran terakhir orang `k`:

```
Antrian (putaran terakhir k):
[... i=0 ... k ... i=n-1]

Orang k butuh 1 tiket lagi di putaran ini.
Urutan giliran:
  → i=0 beli dulu (i < k, sempat beli)
  → i=1 beli dulu
  → ...
  → k beli → SELESAI → proses berhenti!
  → i=k+1, k+2, ... tidak sempat beli di putaran ini
```

Orang di belakang `k` hanya sempat mengikuti **putaran lengkap** sebelum putaran terakhir `k`. Jumlah putaran lengkap yang mereka ikuti = `tickets[k] - 1`. Di putaran ke-`tickets[k]`, proses berhenti saat giliran `k` tiba — sebelum giliran mereka.

______________________________________________________________________

## 🔧 Perbandingan dengan Simulasi Queue

```java
// Simulasi — O(n × max(tickets))
Queue<int[]> q = new LinkedList<>();
for (int i = 0; i < tickets.length; i++) q.offer(new int[]{i, tickets[i]});
int time = 0;
while (!q.isEmpty()) {
    int[] front = q.poll();
    time++;
    front[1]--;
    if (front[0] == k && front[1] == 0) return time;
    if (front[1] > 0) q.offer(front);
}
```

| Approach | Time | Space | Catatan |
| -------------- | ---------- | ----- | ---------------------------------------- |
| Greedy (kode) | O(n) | O(1) | Insight matematika, tidak perlu simulasi |
| Simulasi Queue | O(n × max) | O(n) | Intuitif tapi lambat untuk tiket banyak |

______________________________________________________________________

## 📌 Key Takeaway

Seperti soal _Number of Students Unable to Eat Lunch_, kunci soal ini adalah **tidak mensimulasikan proses** secara literal. Dengan memahami bahwa setiap orang hanya bisa berkontribusi maksimal `tickets[k]` kali (jika di depan/sama) atau `tickets[k]-1` kali (jika di belakang), kita langsung hitung total waktu dalam O(n). Perbedaan `i <= k` vs `i > k` mencerminkan apakah seseorang sempat atau tidak sempat membeli di putaran terakhir `k`. 🎯

# 703. Kth Largest Element in a Stream

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Design, Heap (Priority Queue), Stream
- **Link**: [Problem](https://leetcode.com/problems/kth-largest-element-in-a-stream/)
- **Solution**: [Code](../../leetcode/KthLargest.java)

______________________________________________________________________

## 📄 Problem Summary

Rancang kelas `KthLargest` yang melacak **elemen terbesar ke-`k`** dalam sebuah **stream** angka yang terus bertambah. Konstruktor menerima `k` dan array awal `nums`. Method `add(val)` menambahkan angka baru ke stream, lalu **mengembalikan** elemen terbesar ke-`k` **setelah** penambahan itu.

Contoh:

- `k=3, nums=[4,5,8,2]`. Panggilan berurutan:
  - `add(3)` → `4` (stream jadi `{4,5,8,2,3}`, terbesar ke-3 adalah `4`)
  - `add(5)` → `5`
  - `add(10)` → `5`
  - `add(9)` → `8`
  - `add(4)` → `8`

______________________________________________________________________

## 💡 Intuition

Pendekatan naif: simpan **semua** angka, dan tiap kali `add` dipanggil, **sort ulang** semuanya lalu ambil elemen ke-`k` terbesar — ini `O(n log n)` per panggilan `add`, terlalu lambat kalau `add` dipanggil berkali-kali.

Insight kuncinya: kita **tidak perlu tahu semua angka**, cukup **`k` angka terbesar** yang pernah masuk stream — karena elemen terbesar ke-`k` **pasti** adalah yang **terkecil** di antara `k` angka terbesar itu. Ini pola klasik **"min-heap berukuran tetap `k`"**:

- Simpan `k` kandidat terbesar di **min-heap** (`PriorityQueue` dengan urutan alami, terkecil di puncak).
- Puncak heap (`peek()`) selalu merepresentasikan **elemen terbesar ke-`k`** — karena dialah yang paling kecil di antara `k` kandidat teratas.
- Kalau ada angka baru yang **lebih besar** dari puncak heap, berarti angka itu **layak** masuk ke `k` besar; buang yang paling kecil (puncak lama), masukkan yang baru.
- Kalau angka baru **lebih kecil atau sama** dengan puncak heap, dia **tidak** cukup besar untuk masuk `k` besar, abaikan saja (heap tidak berubah).

______________________________________________________________________

## 🔍 Approach

### Min-Heap Berukuran Tetap `k`

**Konstruktor:**

1. Simpan `k`, siapkan `nums` sebagai `PriorityQueue<Integer>` kosong (min-heap, urutan alami Java).
1. Untuk tiap elemen di array `nums` awal, panggil `add(i)` — supaya heap terisi lewat logika yang **sama** dengan penambahan biasa (tidak ada duplikasi logika).

**Method `add(val)`:**

1. Kalau ukuran heap **belum** mencapai `k` (`nums.size() < k`) → langsung `offer(val)` (heap belum penuh, semua kandidat awal diterima tanpa syarat).
1. Kalau heap **sudah** penuh (`size == k`) **dan** `val` **lebih besar** dari puncak heap saat ini (`nums.peek() < val`) → `val` layak masuk: buang puncak lama (`poll()`), masukkan `val` (`offer(val)`).
1. Kalau tidak memenuhi kedua kondisi di atas (heap penuh dan `val` tidak lebih besar dari puncak) → tidak ada perubahan pada heap.
1. Kembalikan `nums.peek()` — puncak heap saat ini, yaitu elemen terbesar ke-`k`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | --------------------------------------------------------------------------------------------- |
| **Time** | Konstruktor: O(n log k) — n elemen awal, tiap `add` O(log k). Tiap `add` berikutnya: O(log k) |
| **Space** | O(k) — heap selalu menyimpan maksimal `k` elemen |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `k=3, nums=[4,5,8,2]`, lalu panggilan `add(3), add(5), add(10), add(9), add(4)`

**Konstruktor (memanggil `add` untuk tiap elemen awal):**

| Panggilan | Kondisi | Aksi | Heap sesudah | peek() |
| --------- | ---------------------------------- | ------------- | ------------ | ------ |
| `add(4)` | `size(0) < k(3)` | `offer(4)` | `{4}` | 4 |
| `add(5)` | `size(1) < 3` | `offer(5)` | `{4,5}` | 4 |
| `add(8)` | `size(2) < 3` | `offer(8)` | `{4,5,8}` | 4 |
| `add(2)` | `size(3)==3`, `peek(4) < 2`? tidak | tidak berubah | `{4,5,8}` | 4 |

Heap final setelah konstruktor: `{4,5,8}` (min-heap, puncak `4`).

**Panggilan eksplisit:**

| Panggilan | Kondisi | Aksi | Heap sesudah | Return |
| --------- | ------------------------------- | ------------------------------- | ------------ | ------ |
| `add(3)` | `size==3`, `peek(4) < 3`? tidak | tidak berubah | `{4,5,8}` | **4** |
| `add(5)` | `size==3`, `peek(4) < 5`? ya | `poll()` (buang 4), `offer(5)` | `{5,5,8}` | **5** |
| `add(10)` | `size==3`, `peek(5) < 10`? ya | `poll()` (buang 5), `offer(10)` | `{5,8,10}` | **5** |
| `add(9)` | `size==3`, `peek(5) < 9`? ya | `poll()` (buang 5), `offer(9)` | `{8,9,10}` | **8** |
| `add(4)` | `size==3`, `peek(8) < 4`? tidak | tidak berubah | `{8,9,10}` | **8** |

**Output berurutan: `4, 5, 5, 8, 8`** ✅ (cocok dengan contoh resmi soal)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `nums` awal kosong (`[]`) → heap dimulai kosong, terisi murni lewat panggilan `add` berikutnya sampai mencapai ukuran `k`
- [ ] `add` dipanggil sebelum heap mencapai ukuran `k` → selalu diterima tanpa syarat (`offer` langsung), karena belum ada dasar perbandingan yang valid
- [ ] Nilai duplikat ditambahkan (`add(5)` dua kali dengan nilai yang sama) → tetap diproses seperti nilai biasa, heap bisa berisi nilai yang sama lebih dari sekali (constraint soal mengizinkan duplikat di stream)
- [ ] `val` baru **persis sama** dengan puncak heap (`nums.peek() == val`) → **tidak** menggantikan puncak (kondisi kode adalah `<`, bukan `<=`), karena nilai yang sama tidak membuat hasil "elemen terbesar ke-`k`" berubah
- [ ] `k` lebih besar dari jumlah elemen `nums` awal → heap belum penuh setelah konstruktor, `add` berikutnya masih akan diterima tanpa syarat sampai heap mencapai ukuran `k`

______________________________________________________________________

## 🔧 Kenapa Puncak Min-Heap Berukuran `k` Selalu Merepresentasikan Elemen Terbesar ke-`k`?

Bayangkan heap berisi **`k` angka terbesar** yang pernah masuk stream sejauh ini (dijamin oleh logika `add`, yang selalu membuang kandidat terlemah begitu ada yang lebih kuat). Di antara `k` angka ini, yang **terkecil** (yaitu puncak min-heap) pastilah **lebih kecil atau sama** dengan `k-1` angka lainnya di heap, dan **lebih besar** dari **semua** angka yang **tidak** masuk heap (karena kalau ada angka di luar heap yang lebih besar dari puncak, dia **pasti** sudah menggantikan puncak lewat logika `add`). Jadi puncak heap ini **persis** berada di posisi ke-`k` kalau seluruh stream diurutkan turun — definisi tepat dari "elemen terbesar ke-`k`".

______________________________________________________________________

## 🔧 Kenapa Konstruktor Memanggil `add()`, Bukan Membangun Heap Langsung dari Array?

```java
public KthLargest(int k, int[] nums) {
    this.k = k;
    this.nums = new PriorityQueue<Integer>();
    for (int i : nums)
        add(i);
}
```

Ini pilihan desain yang **menghindari duplikasi logika**. Alternatifnya, konstruktor bisa saja langsung memasukkan semua `nums` ke heap lalu memangkas heap sampai ukurannya `k` — tapi itu berarti aturan "kapan menerima, kapan menolak, kapan mengganti puncak" harus **ditulis ulang** secara terpisah dari method `add`. Dengan memanggil `add(i)` untuk tiap elemen awal, konstruktor **menggunakan ulang** persis logika yang sama yang dipakai untuk stream berikutnya — lebih ringkas dan mengurangi risiko bug dari dua implementasi yang seharusnya identik tapi ditulis dua kali.

______________________________________________________________________

## 🔧 Alternatif: Simpan Semua Elemen, Sort Tiap Query (Naif, Untuk Perbandingan)

```java
class KthLargest {
    List<Integer> nums;
    int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.nums = new ArrayList<>();
        for (int n : nums) this.nums.add(n);
    }

    public int add(int val) {
        nums.add(val);
        nums.sort(Collections.reverseOrder());
        return nums.get(k - 1);
    }
}
```

Versi ini menyimpan **semua** elemen (bukan cuma `k` teratas), dan tiap `add` men-sort ulang **seluruh** list untuk mengambil elemen ke-`k` terbesar. Jauh lebih lambat karena sorting penuh (`O(n log n)`) dipanggil di **setiap** `add`, dibanding heap yang cuma `O(log k)` per operasi — signifikan bedanya kalau `add` dipanggil ribuan kali pada stream yang terus bertambah panjang.

| Approach | Time per `add` | Space |
| ---------------------------------- | -------------- | ----- |
| Min-heap berukuran `k` (kode asli) | O(log k) | O(k) |
| Simpan semua + sort tiap query | O(n log n) | O(n) |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar klasik untuk pola **"min-heap berukuran tetap `k` untuk melacak `k` elemen terbesar"** — trik penting yang menghindari kebutuhan menyimpan atau mengurutkan seluruh data setiap kali query baru datang. Pola ini sangat umum di soal-soal streaming/online seperti _Find Median from Data Stream_, _Top K Frequent Elements_, dan _Sliding Window Maximum_ — di mana kuncinya selalu sama: **jangan simpan lebih dari yang dibutuhkan**, cukup pertahankan struktur data berukuran tetap yang merepresentasikan kandidat-kandidat terbaik sejauh ini. 🎯

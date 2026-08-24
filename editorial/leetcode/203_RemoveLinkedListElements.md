# 203. Remove Linked List Elements

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Linked List, Two Pointers
- **Link**: [Problem](https://leetcode.com/problems/remove-linked-list-elements/)
- **Solution**: [Code](../../leetcode/RemoveLinkedListElements.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan `head` dari sebuah singly linked list, dan integer `val`. Hapus **semua** node yang nilainya `== val`, lalu kembalikan `head` yang baru.

Contoh:

- `head = [1,2,6,3,4,5,6], val = 6` → `[1,2,3,4,5]`
- `head = [], val = 1` → `[]`
- `head = [7,7,7,7], val = 7` → `[]`

______________________________________________________________________

## 💡 Intuition

Kalau kita hapus node biasa di linked list, prosesnya sederhana: cari node **sebelum** node yang mau dihapus, lalu sambungkan `next`-nya langsung ke node **setelah** yang dihapus (skip node target). Tapi ada **kasus khusus yang merepotkan**: kalau node yang harus dihapus adalah `head` itu sendiri, tidak ada "node sebelumnya" untuk mengubah pointer-nya — logikanya jadi beda (harus update variabel `head` secara langsung, bukan `prev.next`).

Solusi ini menghindari percabangan logika seperti itu dengan trik klasik: **dummy node** (`temp`). Dengan menaruh sebuah node "palsu" **sebelum** `head` asli, **setiap** node asli — termasuk `head` — sekarang punya "node sebelumnya" yang valid. Ini menyatukan kasus "hapus head" dan "hapus node di tengah/akhir" jadi **satu logika seragam**, tanpa perlu `if` khusus untuk head.

______________________________________________________________________

## 🔍 Approach

### Dummy Node + Traversal Satu Pointer

1. Buat `temp` (dummy node) dengan nilai sembarang (`0`), dan sambungkan `temp.next = head`.
1. `curr = temp` — pointer yang akan berjalan, mulai dari dummy node.
1. Loop selama `curr.next != null`:
   - Kalau `curr.next.val == val` (node **berikutnya** perlu dihapus) → **skip** node itu dengan `curr.next = curr.next.next` (curr **tidak** maju, supaya bisa cek node baru yang sekarang jadi `curr.next` — penting untuk kasus nilai berulang berturut-turut).
   - Kalau tidak → maju `curr = curr.next` (node ini valid, lanjut cek node berikutnya).
1. Kembalikan `temp.next` — ini otomatis jadi `head` yang baru, baik `head` asli tetap ada (tidak dihapus) atau sudah ikut terhapus dan digantikan node lain.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------------------------------------------------------- |
| **Time** | O(n) — satu kali traversal ke seluruh linked list |
| **Space** | O(1) — hanya dua pointer tambahan (`temp`, `curr`), tidak ada struktur data baru |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `head = [1,2,6,3,4,5,6], val = 6`

`temp -> 1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6 -> null`, `curr = temp`.

| curr saat ini | curr.next.val | == 6? | Aksi |
| ------------- | ------------- | ------ | --------------------------------------------------------------------- |
| temp | 1 | tidak | `curr = curr.next` (curr→1) |
| 1 | 2 | tidak | `curr = curr.next` (curr→2) |
| 2 | 6 | **ya** | `curr.next = curr.next.next` (skip node 6, curr.next sekarang → 3) |
| 2 | 3 | tidak | `curr = curr.next` (curr→3) |
| 3 | 4 | tidak | `curr = curr.next` (curr→4) |
| 4 | 5 | tidak | `curr = curr.next` (curr→5) |
| 5 | 6 | **ya** | `curr.next = curr.next.next` (skip node 6, curr.next sekarang → null) |
| 5 | null | — | loop berhenti (`curr.next == null`) |

List akhir: `temp -> 1 -> 2 -> 3 -> 4 -> 5 -> null`

**Output: `[1,2,3,4,5]`** ✅

______________________________________________________________________

**Input:** `head = [7,7,7,7], val = 7`

`temp -> 7 -> 7 -> 7 -> 7 -> null`, `curr = temp`.

| curr saat ini | curr.next.val | == 7? | Aksi |
| ------------- | ------------- | ----- | ------------------------------------------------ |
| temp | 7 | ya | skip, `curr.next` sekarang node `7` kedua |
| temp | 7 | ya | skip lagi, `curr.next` sekarang node `7` ketiga |
| temp | 7 | ya | skip lagi, `curr.next` sekarang node `7` keempat |
| temp | 7 | ya | skip lagi, `curr.next` sekarang `null` |
| temp | null | — | loop berhenti |

List akhir: `temp -> null`. `temp.next = null`.

**Output: `[]`** ✅ — contoh ini penting: **semua** node terhapus termasuk `head` asli, dan karena `curr` tidak pernah maju (selalu `temp`), seluruh rangkaian nilai `7` yang berurutan langsung tersapu dalam satu pass tanpa masalah.

______________________________________________________________________

**Input:** `head = [], val = 1`

`temp.next = null` dari awal (karena `head` sudah `null`). Loop `while (curr.next != null)` langsung `false` (kondisi awal saja sudah gagal), tidak pernah jalan.

**Output: `[]`** ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `head == null` (list kosong) → loop tidak pernah jalan, langsung return `temp.next` yang tetap `null`
- [ ] `head` sendiri perlu dihapus (`head.val == val`) → tertangani otomatis lewat dummy node, tidak butuh percabangan khusus
- [ ] Semua node punya nilai `== val` → seluruh list terhapus, hasil `[]` (dibuktikan di contoh `[7,7,7,7]`)
- [ ] Nilai yang dihapus muncul **berurutan** (`6,6` bersebelahan) → tertangani karena `curr` **tidak maju** setelah skip, sehingga node berikutnya (yang sekarang jadi `curr.next`) langsung dicek lagi di iterasi yang sama tanpa kehilangan node manapun
- [ ] Tidak ada satupun node dengan nilai `== val` → list tidak berubah sama sekali, `curr` cuma maju terus sampai akhir

______________________________________________________________________

## 🔧 Kenapa `curr` Tidak Maju Setelah Node Dihapus?

```java
if (curr.next.val == val)
    curr.next = curr.next.next;   // curr TIDAK maju di sini
else
    curr = curr.next;              // curr baru maju kalau TIDAK dihapus
```

Ini detail krusial. Setelah `curr.next = curr.next.next`, **node baru** yang sekarang jadi `curr.next` **belum pernah dicek** — bisa jadi node itu **juga** punya nilai `== val` (kasus nilai berulang berturut-turut, seperti `[7,7,7,7]`). Kalau `curr` ikut dimajukan setelah skip, iterasi berikutnya akan melompati pengecekan node baru ini, dan node dengan nilai `val` yang seharusnya dihapus bisa **lolos** tidak terhapus. Dengan `curr` tetap diam setelah skip, loop otomatis mengecek ulang `curr.next` yang baru di iterasi berikutnya — memastikan **rangkaian** nilai `val` berurutan tersapu habis, bukan cuma satu per satu.

______________________________________________________________________

## 🔧 Kenapa Dummy Node Lebih Baik Dibanding Percabangan Manual untuk `head`?

Tanpa dummy node, solusinya harus menangani dua kasus terpisah:

```java
public ListNode removeElements(ListNode head, int val) {
    // Kasus 1: hapus head yang beruntun di depan
    while (head != null && head.val == val)
        head = head.next;
    if (head == null) return null;

    // Kasus 2: hapus node di tengah/akhir
    ListNode curr = head;
    while (curr.next != null) {
        if (curr.next.val == val)
            curr.next = curr.next.next;
        else
            curr = curr.next;
    }
    return head;
}
```

Versi ini **berfungsi sama**, tapi butuh loop terpisah di awal khusus untuk menangani rangkaian `head` yang perlu dihapus. Dummy node menyatukan kedua kasus ini jadi satu loop tunggal — lebih ringkas dan tidak ada logika yang terduplikasi.

| Approach | Time | Space | Butuh Logika Terpisah untuk Head? |
| ----------------------------- | ---- | ----- | --------------------------------- |
| Dummy node (kode asli) | O(n) | O(1) | Tidak |
| Percabangan manual untuk head | O(n) | O(1) | Ya |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah pengantar klasik untuk teknik **dummy node (sentinel node)** di linked list — trik yang sangat umum dipakai untuk menyederhanakan operasi yang berpotensi mengubah `head` (penghapusan, penyisipan di depan, penggabungan list, dst), karena menghilangkan kebutuhan percabangan logika khusus untuk kasus "head berubah". Perhatikan juga detail penting soal **kapan pointer traversal boleh maju** — saat menghapus elemen dari struktur linked, pointer sebaiknya **tidak maju** sampai dipastikan elemen di posisi saat ini valid, supaya rangkaian elemen yang perlu dihapus berturut-turut tidak ada yang terlewat. Pola dummy node ini juga sangat relevan untuk soal-soal seperti _Merge Two Sorted Lists_, _Remove Duplicates from Sorted List II_, dan _Partition List_. 🎯

# 21. Merge Two Sorted Lists

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Linked List, Two Pointers, Recursion
- **Link**: [Problem](https://leetcode.com/problems/merge-two-sorted-lists/)
- **Solution**: [Code](../../leetcode/MergeTwoSortedLists.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua singly-linked list, `list1` dan `list2`, yang masing-masing **sudah terurut naik**. Gabungkan (merge) keduanya jadi **satu list terurut**, dengan cara menyambung node-node yang sudah ada (bukan membuat node baru), lalu kembalikan `head` dari list gabungan itu.

Contoh:

- `list1 = [1,2,4], list2 = [1,3,4]` → `[1,1,2,3,4,4]`
- `list1 = [], list2 = []` → `[]`
- `list1 = [], list2 = [0]` → `[0]`

______________________________________________________________________

## 💡 Intuition

Karena **kedua** list sudah terurut, ini adalah pola klasik **merge step** dari merge sort — bedanya di sini kita menggabungkan **linked list**, bukan array. Strateginya: bandingkan node **terdepan** dari kedua list secara bergantian, ambil yang nilainya **lebih kecil**, sambungkan ke list hasil, lalu majukan pointer di list yang node-nya baru saja diambil. Ulangi sampai salah satu list habis.

Begitu salah satu list habis duluan, **sisa list yang lain otomatis sudah terurut** (karena constraint soal menjamin kedua list awalnya terurut) — jadi tidak perlu diproses satu-satu lagi, cukup **sambungkan langsung** sisa list itu ke ekor hasil gabungan.

Untuk menyederhanakan proses "menyambung node hasil", solusi ini memakai **dummy node** (`ans`) — trik yang sama seperti di soal _Remove Linked List Elements_ — supaya tidak perlu penanganan khusus untuk node pertama dari hasil gabungan.

______________________________________________________________________

## 🔍 Approach

### Dummy Node + Two Pointers, Bandingkan & Sambung

1. Buat dummy node `ans`, dan `curr = ans` (pointer yang menandai "ekor" list hasil yang sedang dibangun).
1. Loop selama **kedua** `list1` dan `list2` masih punya node (`!= null`):
   - Bandingkan `list1.val` dan `list2.val`. Sambungkan `curr.next` ke node dengan nilai **lebih kecil**, lalu majukan pointer list itu (`list1 = list1.next` atau `list2 = list2.next`).
   - Majukan `curr = curr.next` (ekor hasil gabungan sekarang jadi node yang baru disambung).
1. Setelah loop berhenti (salah satu list sudah `null`), sambungkan **sisa** list yang belum habis (`list1` kalau `list2` yang habis, atau sebaliknya) langsung ke `curr.next` — tidak perlu loop lagi, karena sisa list itu sudah terurut dengan sendirinya.
1. Kembalikan `ans.next` (melewati dummy node, langsung ke node pertama hasil gabungan yang sesungguhnya).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ------------------------------------------------------------------------------------------------- |
| **Time** | O(m + n) — m, n = panjang `list1`, `list2`; tiap node dikunjungi tepat sekali |
| **Space** | O(1) — tidak ada node baru dibuat (kecuali dummy node), cuma menyambung ulang node yang sudah ada |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `list1 = [1,2,4], list2 = [1,3,4]`

`ans -> null`, `curr = ans`.

| list1 | list2 | list1.val < list2.val? | Aksi | curr sekarang |
| ----- | ----- | --------------------------------- | ---------------------------------- | --------------------- |
| 1,2,4 | 1,3,4 | `1<1`? tidak (sama, masuk `else`) | `curr.next=list2(1)`, `list2=3,4` | node `1` (dari list2) |
| 1,2,4 | 3,4 | `1<3` ✅ | `curr.next=list1(1)`, `list1=2,4` | node `1` (dari list1) |
| 2,4 | 3,4 | `2<3` ✅ | `curr.next=list1(2)`, `list1=4` | node `2` |
| 4 | 3,4 | `4<3`? tidak | `curr.next=list2(3)`, `list2=4` | node `3` |
| 4 | 4 | `4<4`? tidak (sama, masuk `else`) | `curr.next=list2(4)`, `list2=null` | node `4` (dari list2) |

Loop berhenti (`list2 == null`). Sisa: `curr.next = list1` (yang masih berisi node `4`).

Hasil: `ans -> 1(list2) -> 1(list1) -> 2 -> 3(list2) -> 4(list2) -> 4(list1) -> null`

**Output: `[1,1,2,3,4,4]`** ✅

______________________________________________________________________

**Input:** `list1 = [], list2 = [0]`

Loop `while (list1 != null && list2 != null)` langsung `false` karena `list1 == null` dari awal — loop tidak pernah jalan.

`curr.next = list1 == null ? list2 : list1` → karena `list1 == null`, `curr.next = list2`.

**Output: `[0]`** ✅

______________________________________________________________________

**Input:** `list1 = [], list2 = []`

Loop tidak jalan. `curr.next = list1 == null ? list2 : list1` → `list1==null` → `curr.next = list2`, yang juga `null`.

**Output: `[]`** ✅ (`ans.next` tetap `null`)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Salah satu atau kedua list kosong → tertangani otomatis lewat loop yang tidak pernah jalan, langsung ke penyambungan sisa di baris terakhir
- [ ] Ada nilai yang sama di kedua list (`1` muncul di `list1` dan `list2`) → kondisi `list1.val < list2.val` bernilai `false` untuk nilai sama, sehingga masuk `else` dan **`list2` didahulukan** — urutan node dengan nilai sama tetap konsisten (list2 duluan) tanpa mempengaruhi validitas hasil (soal tidak mensyaratkan urutan spesifik untuk nilai yang sama)
- [ ] Salah satu list jauh lebih panjang dari yang lain → begitu list pendek habis, sisa list panjang langsung disambung sekaligus tanpa loop tambahan, tetap `O(m+n)` total
- [ ] List dengan elemen negatif → tidak masalah, perbandingan `<` tetap berlaku normal untuk bilangan negatif

______________________________________________________________________

## 🔧 Kenapa Sisa List Bisa Langsung Disambung Tanpa Diproses Satu-Satu?

```java
curr.next = list1 == null ? list2 : list1;
```

Ini valid **karena constraint soal menjamin `list1` dan `list2` masing-masing sudah terurut naik dari awal**. Begitu salah satu list (misal `list2`) habis, **seluruh sisa node** di list yang lain (`list1`) sudah otomatis dalam urutan yang benar relatif satu sama lain (karena memang sudah terurut sejak awal), dan **semuanya** pasti `>=` node terakhir yang sudah disambung ke hasil (karena node terakhir itu tadi "menang" perbandingan melawan node depan `list1`, atau `list1` memang belum pernah dibandingkan lagi setelah `list2` habis). Jadi tidak ada elemen tersisa yang perlu "disisipkan" di tengah — cukup sambung seluruh sisa list itu sebagai satu blok.

______________________________________________________________________

## 🔧 Alternatif: Rekursif

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    if (list1 == null) return list2;
    if (list2 == null) return list1;

    if (list1.val < list2.val) {
        list1.next = mergeTwoLists(list1.next, list2);
        return list1;
    } else {
        list2.next = mergeTwoLists(list1, list2.next);
        return list2;
    }
}
```

Versi ini menyelesaikan soal secara rekursif: base case-nya adalah salah satu list kosong (langsung kembalikan list yang lain apa adanya), dan langkah rekursifnya membandingkan node terdepan, lalu "menyerahkan" sisa penggabungan ke pemanggilan rekursif berikutnya. Lebih ringkas dan elegan secara notasi, tapi memakai **call stack** sedalam `O(m+n)` — bisa berisiko `StackOverflowError` untuk list yang sangat panjang, beda dengan versi iteratif yang space-nya benar-benar `O(1)`.

| Approach | Time | Space | Risiko Stack Overflow? |
| --------------------------------- | ------ | ------------------- | ----------------------------- |
| Iteratif + dummy node (kode asli) | O(m+n) | O(1) | Tidak |
| Rekursif | O(m+n) | O(m+n) — call stack | Ya, untuk list sangat panjang |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah fondasi penting untuk memahami **merge step** yang jadi inti dari algoritma _merge sort_, diterapkan langsung ke linked list. Kombinasi **dummy node** (menyederhanakan penyambungan node pertama) dan **two pointers** (bandingkan & majukan bergantian) adalah pola yang sangat umum untuk soal-soal linked list yang melibatkan penggabungan atau penyisipan terurut. Perhatikan juga observasi penting bahwa begitu salah satu list terurut habis, sisa list yang lain **tidak perlu diproses lagi satu-satu** — cukup disambung sebagai satu blok, karena keterurutannya sudah terjamin dari awal. Pola ini jadi fondasi untuk soal-soal lanjutan seperti _Merge k Sorted Lists_ dan _Sort List_. 🎯

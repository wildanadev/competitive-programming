# 705. Design HashSet

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Design
- **Link**: [Problem](https://leetcode.com/problems/design-hashset/)
- **Solution**: [Code](../../leetcode/MyHashSet.java)

______________________________________________________________________

## 📄 Problem Summary

Rancang struktur data yang meniru perilaku `HashSet` **tanpa memakai library `HashSet` bawaan bahasa pemrograman**. Perlu diimplementasikan tiga operasi:

- `add(key)` — masukkan `key` ke dalam set.
- `remove(key)` — hapus `key` dari set (kalau ada).
- `contains(key)` — cek apakah `key` ada di dalam set.

Constraint kunci: `0 <= key <= 10^6`, dengan jumlah pemanggilan operasi dibatasi (maksimal `10^4` kali).

______________________________________________________________________

## 💡 Intuition

Ini soal "design", tujuannya bukan mencari algoritma yang rumit, tapi **mengimplementasikan sendiri** perilaku hash set dari nol. Trik di balik solusi ini: karena `key` **dibatasi rentang kecil dan tetap** (`0` sampai `10^6`), kita bisa memakai **direct addressing** — bikin array berukuran `10^6 + 1`, lalu pakai `key` itu sendiri **langsung sebagai index array**.

Ini berbeda dari hash table sungguhan (yang memetakan key ke index lewat _fungsi hash_ + mekanisme _collision handling_ seperti chaining atau open addressing). Di sini, karena rentang key sudah diketahui kecil dan pas untuk dijadikan array langsung, **tidak dibutuhkan fungsi hash maupun penanganan collision sama sekali** — index array **adalah** key-nya sendiri, satu-satu, tanpa risiko dua key berbeda saling bertabrakan di slot yang sama.

Untuk menandai "ada/tidaknya" suatu key, solusi ini memakai `String[]`: slot berisi representasi string dari key kalau ada, atau `null` kalau tidak ada.

______________________________________________________________________

## 🔍 Approach

### Direct Addressing Array (Bukan Hashing Sungguhan)

1. **Constructor**: siapkan `arr` berukuran `10^6 + 1` (menampung seluruh kemungkinan `key` dari `0` sampai `10^6` inklusif), semua slot awalnya `null` (default array objek di Java).
1. **`add(key)`**: set `arr[key] = String.valueOf(key)` — menandai slot itu "terisi".
1. **`remove(key)`**: set `arr[key] = null` — menandai slot itu "kosong" lagi.
1. **`contains(key)`**: cek `arr[key] == null` — kalau `null` berarti tidak ada (`false`), kalau tidak berarti ada (`true`).

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------- |
| **Time** | O(1) untuk `add`, `remove`, dan `contains` — akses langsung ke index array |
| **Space** | O(N) — N = `10^6 + 1`, **tetap** (fixed), tidak bergantung banyaknya key yang benar-benar dimasukkan |

______________________________________________________________________

## 🧪 Dry Run

**Input:**

```
MyHashSet hashSet = new MyHashSet();
hashSet.add(1);      // set = [1]
hashSet.add(2);      // set = [1, 2]
hashSet.contains(1); // true
hashSet.contains(3); // false
hashSet.add(2);      // set = [1, 2] (tidak ada perubahan, key sudah ada)
hashSet.contains(2); // true
hashSet.remove(2);   // set = [1]
hashSet.contains(2); // false
```

| Panggilan | Aksi di `arr` | Return |
| ------------- | -------------------------------------------- | ------- |
| `add(1)` | `arr[1] = "1"` | - |
| `add(2)` | `arr[2] = "2"` | - |
| `contains(1)` | cek `arr[1]` → `"1"` (bukan null) | `true` |
| `contains(3)` | cek `arr[3]` → `null` | `false` |
| `add(2)` | `arr[2] = "2"` (overwrite dengan nilai sama) | - |
| `contains(2)` | cek `arr[2]` → `"2"` | `true` |
| `remove(2)` | `arr[2] = null` | - |
| `contains(2)` | cek `arr[2]` → `null` | `false` |

Semua output sesuai ekspektasi ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `key = 0` (batas bawah) → tetap valid, `arr[0]` adalah slot pertama yang sah
- [ ] `key = 10^6` (batas atas) → tetap valid, makanya ukuran array `10^6 + 1` (bukan cuma `10^6`), supaya index `10^6` tidak out-of-bounds
- [ ] `add` key yang sudah ada → tidak masalah, cuma overwrite dengan nilai yang sama, tidak ada efek samping
- [ ] `remove` key yang **belum pernah** ditambahkan → tetap aman, `arr[key] = null` (yang memang sudah `null` dari awal), tidak menyebabkan error
- [ ] `contains` pada key yang belum pernah disentuh sama sekali → default array `null`, otomatis `false`

______________________________________________________________________

## 🔧 Kenapa Ini Bukan "Hash Table Sungguhan"?

Menarik untuk dibahas: solusi ini **bekerja dengan benar**, tapi secara teknis **bukan** implementasi hash table dalam pengertian sebenarnya. Hash table sejati punya dua komponen penting yang **tidak ada** di solusi ini:

1. **Fungsi hash** — biasanya key (yang bisa berukuran/rentang berapa saja) dipetakan ke index array yang **lebih kecil** lewat `hash(key) % arraySize`.
1. **Collision handling** — karena banyak key berbeda bisa menghasilkan hash yang sama (bertabrakan di slot yang sama), perlu mekanisme seperti _chaining_ (tiap slot berisi linked list) atau _open addressing_ (probing ke slot lain).

Solusi di atas **melewati keduanya sepenuhnya**: karena constraint menjamin `key` sudah berupa integer kecil dan langsung bisa dipakai sebagai index, tidak ada fungsi hash yang dibutuhkan (identitasnya sendiri sudah jadi "hash"-nya), dan tidak ada collision yang mungkin terjadi (index unik untuk tiap key). Teknik ini disebut **direct addressing** — kasus spesial di mana hashing "sempurna" karena domain key sudah cukup kecil untuk dipetakan 1-ke-1 langsung ke array.

Kalau constraint soal ini diubah jadi `key` bisa berupa angka **berapa saja** (misal sampai `10^18`, atau bahkan string), pendekatan direct addressing ini **tidak akan work** (array sebesar itu mustahil dialokasikan) — barulah di situ kita **wajib** memakai hash table sungguhan dengan fungsi hash + collision handling.

______________________________________________________________________

## 🔧 Alternatif: Hash Table dengan Chaining (Lebih Mendekati Implementasi Asli)

```java
class MyHashSet {
    private static final int SIZE = 769; // bilangan prima, mengurangi collision
    private final List<Integer>[] buckets;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        buckets = new List[SIZE];
        for (int i = 0; i < SIZE; i++) buckets[i] = new LinkedList<>();
    }

    private int hash(int key) {
        return key % SIZE;
    }

    public void add(int key) {
        int idx = hash(key);
        if (!buckets[idx].contains(key)) buckets[idx].add(key);
    }

    public void remove(int key) {
        buckets[hash(key)].remove((Integer) key);
    }

    public boolean contains(int key) {
        return buckets[hash(key)].contains(key);
    }
}
```

Versi ini **benar-benar** mengimplementasikan mekanisme hash table: `key` dipetakan lewat fungsi hash (`key % SIZE`) ke salah satu dari `SIZE` bucket, dan tiap bucket berupa `LinkedList` untuk menangani collision (kalau dua key berbeda kebetulan hash ke bucket yang sama). Trade-off-nya: `contains`/`add`/`remove` jadi **O(1) rata-rata**, tapi bisa memburuk ke **O(k)** (k = jumlah elemen dalam satu bucket) kalau banyak collision, dan space-nya jauh lebih hemat kalau jumlah key yang benar-benar dipakai jauh lebih sedikit dibanding rentang key maksimum.

| Approach | Time (avg) | Space | Perlu Fungsi Hash + Collision Handling? |
| ----------------------------------- | -------------- | -------------------------------------------------------- | --------------------------------------- |
| Direct addressing array (kode asli) | O(1) pasti | O(range key), tetap besar meski key sedikit yang dipakai | Tidak |
| Chaining dengan bucket (alternatif) | O(1) rata-rata | O(jumlah key yang benar-benar dipakai) | Ya |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini bagus untuk memahami **kapan direct addressing cukup, dan kapan wajib pakai hashing sungguhan**. Kalau domain key sudah **diketahui kecil dan bisa langsung jadi index array**, direct addressing selalu lebih sederhana dan menjamin `O(1)` pasti tanpa risiko collision — tidak perlu "over-engineer" dengan fungsi hash segala. Tapi begitu domain key **besar atau tidak diketahui batasnya** (string sembarang, angka besar, dll), barulah hash table sungguhan (dengan fungsi hash + collision handling) jadi satu-satunya pilihan praktis. Ini juga menjelaskan kenapa `HashSet` bawaan Java (yang dibahas di percakapan sebelumnya) memang memakai `HashMap` dengan hashing asli — karena `HashSet` bawaan harus bisa menampung **key jenis apa saja**, bukan cuma integer kecil dalam rentang tetap. 🎯

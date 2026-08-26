# 706. Design HashMap

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Array, Hash Table, Design
- **Link**: [Problem](https://leetcode.com/problems/design-hashmap/)
- **Solution**: [Code](../../leetcode/MyHashMap.java)

______________________________________________________________________

## 📄 Problem Summary

Rancang struktur data yang meniru perilaku `HashMap`, tanpa memakai `HashMap`/`TreeMap` bawaan bahasa pemrograman. Implementasikan tiga operasi:

- `put(key, value)` — masukkan pasangan `(key, value)`. Kalau `key` sudah ada, **timpa** value-nya.
- `get(key)` — kembalikan value untuk `key` tersebut, atau `-1` kalau `key` tidak ada di map.
- `remove(key)` — hapus pasangan `(key, value)` untuk `key` tersebut, kalau ada.

Constraint kunci: `0 <= key, value <= 10^6`.

______________________________________________________________________

## 💡 Intuition

Soal ini adalah kelanjutan langsung dari [_Design HashSet_](DesignHashSet.md) — bedanya, di sini kita perlu menyimpan **value**, bukan cuma menandai keberadaan `key`. Karena rentang nilai `key` diketahui dan cukup kecil (`0` sampai `10^6`), strategi **direct address table** yang sama tetap berlaku: pakai `key` itu sendiri sebagai **indeks array**, langsung menyimpan `value`-nya di situ — tanpa fungsi hash maupun penanganan collision sama sekali.

Satu tantangan tambahan dibanding _Design HashSet_: kita butuh cara membedakan **"key belum pernah di-put"** dari **"key ada, tapi value-nya kebetulan `0`"**. Solusinya: pakai nilai **sentinel** `-1` untuk menandai slot kosong — ini valid **karena** constraint soal menjamin `value >= 0`, jadi `-1` **tidak mungkin** jadi value asli yang bentrok dengan penanda ini.

______________________________________________________________________

## 🔍 Approach

### Direct Address Table dengan Sentinel `-1`

1. **Konstruktor**: buat array `map` berukuran `10^6 + 1` (menampung semua kemungkinan `key`), lalu **isi semua slot dengan `-1`** (`Arrays.fill`) sebagai penanda "belum ada value di sini".
1. **`put(key, value)`**: langsung `map[key] = value` — menimpa value lama kalau `key` sudah pernah di-put sebelumnya (overwrite otomatis, tidak perlu pengecekan khusus).
1. **`get(key)`**: langsung `return map[key]` — otomatis mengembalikan `-1` kalau `key` belum pernah di-put (karena nilai default dari konstruktor), atau value asli kalau sudah pernah.
1. **`remove(key)`**: `map[key] = -1` — mengembalikan slot itu ke kondisi "kosong".

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| **Time** | O(1) untuk `put`, `get`, `remove` — akses langsung lewat indeks array (di luar `O(R)` konstruksi awal di constructor) |
| **Space** | O(R) — R = rentang nilai maksimum `key` (di sini `10^6 + 1`), **tetap**, tidak bergantung berapa banyak pasangan yang sungguhan disimpan |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `put(1,1)`, `put(2,2)`, `get(1)`, `get(3)`, `put(2,1)`, `get(2)`, `remove(2)`, `get(2)`

| Operasi | Efek pada `map` | Return |
| ----------- | --------------------------------- | ------ |
| `put(1,1)` | `map[1]=1` | — |
| `put(2,2)` | `map[2]=2` | — |
| `get(1)` | `map[1]=1` | `1` |
| `get(3)` | `map[3]=-1` (belum pernah di-set) | `-1` |
| `put(2,1)` | `map[2]=1` (menimpa `2` lama) | — |
| `get(2)` | `map[2]=1` | `1` |
| `remove(2)` | `map[2]=-1` | — |
| `get(2)` | `map[2]=-1` | `-1` |

Semua operasi sesuai ekspektasi ✅

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] `put` dengan `value = 0` → tetap tersimpan dan terbaca dengan benar (`get` mengembalikan `0`, bukan `-1`), karena sentinel yang dipakai adalah `-1`, bukan `0` — inilah alasan kenapa `0` **tidak** bisa dipakai sebagai sentinel di soal ini (beda dengan _Design HashSet_ yang boleh pakai `null`/`0` karena tidak menyimpan value asli)
- [ ] `put` dua kali dengan `key` sama, `value` berbeda → value terbaru yang tersimpan (overwrite), sesuai spesifikasi soal
- [ ] `remove` untuk `key` yang belum pernah di-`put` → aman, cuma menimpa slot yang memang sudah `-1` dari awal dengan `-1` lagi (no-op secara efektif)
- [ ] `get` untuk `key` yang tidak pernah disentuh sama sekali → mengembalikan `-1` (nilai default hasil `Arrays.fill` di constructor)

______________________________________________________________________

## 🔧 Kenapa Butuh `Arrays.fill(map, -1)` di Constructor (Beda dengan Design HashSet)?

Di _Design HashSet_, array `String[]` otomatis terisi `null` sebagai default Java — dan `null` **memang** representasi yang pas untuk "belum ada elemen ini". Tapi di soal ini, array-nya bertipe `int[]`, yang defaultnya `0` — dan `0` adalah **value yang valid** (bisa jadi value asli yang di-`put` user). Kalau kita biarkan default `0` tanpa `Arrays.fill(-1)`, maka `get(key)` untuk `key` yang **belum pernah** di-`put` akan salah mengembalikan `0`, padahal seharusnya `-1`.

Solusinya: inisialisasi eksplisit semua slot ke `-1` di constructor. Ini valid karena constraint soal menjamin `value` yang sah selalu `>= 0`, sehingga `-1` dijamin **tidak pernah** bentrok dengan value asli manapun.

______________________________________________________________________

## 🔧 Alternatif: Hash Table Sungguhan dengan Chaining

```java
class MyHashMap {
    private static final int BUCKET_COUNT = 769;
    private LinkedList<int[]>[] buckets; // tiap entry: {key, value}

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = new LinkedList[BUCKET_COUNT];
        for (int i = 0; i < BUCKET_COUNT; i++) buckets[i] = new LinkedList<>();
    }

    private int hash(int key) {
        return key % BUCKET_COUNT;
    }

    public void put(int key, int value) {
        LinkedList<int[]> bucket = buckets[hash(key)];
        for (int[] entry : bucket) {
            if (entry[0] == key) {
                entry[1] = value;
                return;
            }
        }
        bucket.add(new int[]{key, value});
    }

    public int get(int key) {
        for (int[] entry : buckets[hash(key)])
            if (entry[0] == key) return entry[1];
        return -1;
    }

    public void remove(int key) {
        buckets[hash(key)].removeIf(entry -> entry[0] == key);
    }
}
```

Versi ini adalah hash table **sungguhan** dengan fungsi hash (`key % BUCKET_COUNT`) dan **chaining** untuk menangani collision — tiap bucket menyimpan daftar pasangan `[key, value]`, dan operasi harus scan bucket yang bersesuaian untuk cari `key` yang cocok. Ini jauh lebih hemat memori untuk rentang `key` yang sangat besar, tapi `put`/`get`/`remove` jadi `O(n/BUCKET_COUNT)` di kasus rata-rata (tergantung jumlah collision), bukan `O(1)` murni.

| Approach | Time | Space | Butuh Fungsi Hash & Collision Handling? |
| ------------------------------------ | ---------------- | ----------------------------- | --------------------------------------- |
| Direct address table (kode asli) | O(1) murni | O(R) — rentang nilai key | Tidak |
| Chaining dengan bucket + linked list | O(1) rata-rata\* | O(n) — jumlah pasangan aktual | Ya |

\*rata-rata dengan asumsi distribusi hash yang baik dan jumlah bucket cukup besar relatif terhadap jumlah data.

______________________________________________________________________

## 📌 Key Takeaway

Soal ini adalah lanjutan langsung dari _Design HashSet_, dengan tambahan satu pertimbangan penting: begitu struktur data perlu menyimpan **value asli** (bukan cuma menandai keberadaan), pemilihan **nilai sentinel** untuk "slot kosong" jadi krusial — sentinel itu harus berupa nilai yang **dijamin tidak pernah muncul** sebagai data asli, sesuai batasan constraint (`-1` valid di sini karena `value >= 0`). Prinsip direct address table (pakai key sebagai indeks langsung) tetap sama efektifnya selama rentang nilai key diketahui dan cukup kecil untuk dialokasikan sebagai array. 🎯

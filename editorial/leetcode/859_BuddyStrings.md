# 859. Buddy Strings

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/buddy-strings/)
- **Solution**: [Code](../../leetcode/BuddyStrings.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `s` dan `goal`. Kembalikan `true` kalau kamu bisa mengubah `s` jadi `goal` dengan **tepat satu kali swap** (tukar posisi dua karakter di `s`).

Contoh:

- `s = "ab", goal = "ba"` → `true` (tukar posisi `a` dan `b`)
- `s = "ab", goal = "ab"` → `false` (sudah sama, tapi **tidak ada** karakter berulang untuk ditukar tanpa mengubah hasil — swap wajib mengubah string menjadi berbeda dari sebelum-sesudah kalau dua karakternya beda)
- `s = "aa", goal = "aa"` → `true` (tukar dua `a` yang sama — hasilnya tetap `"aa"`, secara teknis tetap dianggap "satu swap" yang sah)

______________________________________________________________________

## 💡 Intuition

Soal ini punya **dua skenario terpisah** yang butuh penanganan berbeda:

**Skenario 1 — `s` dan `goal` sudah identik.** Kelihatannya jawabannya otomatis `true` (tidak perlu apa-apa), tapi soal ini **mewajibkan** kita melakukan **tepat satu** swap — bukan nol swap. Untuk string yang sudah sama, satu-satunya cara "swap yang sah" (hasil akhirnya tetap sama) adalah menukar **dua posisi yang isinya sama**. Ini cuma mungkin kalau ada **karakter yang berulang (duplikat)** di `s` — kalau semua karakter unik, swap apapun **pasti** mengubah string, jadi tidak ada swap valid yang menghasilkan string yang sama.

**Skenario 2 — `s` dan `goal` berbeda.** Untuk bisa dicapai lewat **satu** swap, harus ada **tepat 2 posisi** yang berbeda antara `s` dan `goal`, dan menukar dua posisi itu **harus benar-benar menghasilkan** `goal` — artinya karakter di posisi pertama `s` harus sama dengan karakter di posisi kedua `goal`, dan sebaliknya.

______________________________________________________________________

## 🔍 Approach

### Percabangan Dua Skenario: Identik vs Berbeda

1. Kalau panjang `s` dan `goal` berbeda → langsung `false` (mustahil dicapai lewat swap, karena swap tidak mengubah panjang).
1. **Kalau `s` sama persis dengan `goal`:**
   - Kumpulkan seluruh karakter unik `s` ke `HashSet`.
   - Kalau `set.size() < s.length()` → berarti **ada** karakter berulang (minimal satu karakter muncul lebih dari sekali) → bisa ditukar tanpa mengubah hasil → `true`.
   - Kalau semua karakter unik (`set.size() == s.length()`) → tidak ada swap valid yang menjaga string tetap sama → `false`.
1. **Kalau `s` berbeda dari `goal`:**
   - Kumpulkan semua **indeks** di mana `s` dan `goal` berbeda ke `dif`.
   - Kalau jumlah indeks berbeda **bukan tepat 2** → `false` (terlalu sedikit atau terlalu banyak perbedaan untuk diselesaikan lewat **satu** swap).
   - Kalau tepat 2 (`dif.get(0)` dan `dif.get(1)`) → cek apakah **menukar** karakter di dua posisi itu **benar-benar** menghasilkan `goal`: `s[dif0] == goal[dif1]` **dan** `s[dif1] == goal[dif0]`.

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------------------------------------------------------------------- |
| **Time** | O(n) — n = panjang string, untuk membangun `HashSet` atau `dif` |
| **Space** | O(n) — untuk `HashSet` (skenario identik) atau `dif` (skenario berbeda, maksimal menyimpan sedikit indeks) |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "ab", goal = "ba"`

- Panjang sama. `s.equals(goal)`? `"ab" != "ba"` → tidak, masuk skenario berbeda.
- Cari indeks berbeda: `i=0`: `s[0]='a'` vs `goal[0]='b'` → beda, tambahkan `0`. `i=1`: `s[1]='b'` vs `goal[1]='a'` → beda, tambahkan `1`.
- `dif = [0, 1]`, ukuran `2` ✅.
- Cek: `s[dif[0]]='a' == goal[dif[1]]='a'`? ya. `s[dif[1]]='b' == goal[dif[0]]='b'`? ya.

**Output: `true`** ✅

______________________________________________________________________

**Input:** `s = "ab", goal = "ab"`

- Panjang sama. `s.equals(goal)`? ya → masuk skenario identik.
- `set = {'a', 'b'}`, ukuran `2`. `s.length() = 2`.
- `set.size() < s.length()`? `2 < 2` → **tidak**.

**Output: `false`** ✅

______________________________________________________________________

**Input:** `s = "aa", goal = "aa"`

- `s.equals(goal)` → ya, skenario identik.
- `set = {'a'}`, ukuran `1`. `s.length() = 2`.
- `set.size() < s.length()`? `1 < 2` → **ya**.

**Output: `true`** ✅ — karena ada duplikat (`'a'` muncul dua kali), menukar kedua posisi `'a'` itu tetap menghasilkan `"aa"`, jadi swap yang "sah" tetap ada meski hasilnya tidak kelihatan berubah.

______________________________________________________________________

**Input:** `s = "abcaa", goal = "abcbb"`

- Panjang sama. Tidak identik (beda di beberapa posisi).
- Cari indeks berbeda: `i=3`: `s[3]='a'` vs `goal[3]='b'` → beda. `i=4`: `s[4]='a'` vs `goal[4]='b'` → beda. (posisi 0,1,2 sama: `a,b,c`)
- `dif = [3, 4]`, ukuran `2` ✅.
- Cek: `s[3]='a' == goal[4]='b'`? **tidak**.

**Output: `false`** ✅ — meski jumlah perbedaan tepat `2`, menukar posisi `3` dan `4` di `s` (`"abcaa"` → `"abcaa"`, karena keduanya sama-sama `'a'`) **tidak** menghasilkan `goal` (`"abcbb"`), jadi swap ini tidak valid.

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Panjang `s` dan `goal` berbeda → langsung `false`, tanpa perlu analisis lebih lanjut
- [ ] `s == goal` dan semua karakter unik (`"ab"`) → `false`, karena swap wajib mengubah string kalau tidak ada duplikat
- [ ] `s == goal` dengan duplikat (`"aa"`, `"aabb"`, dst) → `true`, selama ada minimal satu karakter yang berulang
- [ ] `s != goal` tapi jumlah posisi berbeda **bukan** tepat `2` (misal `1` atau `3` posisi berbeda) → `false`, karena satu swap cuma bisa mengubah **tepat dua** posisi
- [ ] `s != goal`, tepat `2` posisi berbeda, tapi **isinya tidak saling melengkapi** (seperti contoh `"abcaa"` vs `"abcbb"` di atas) → `false`, meski jumlah perbedaannya pas `2`

______________________________________________________________________

## 🔧 Kenapa Kasus "String Identik" Butuh Penanganan Terpisah dari "Tepat 2 Perbedaan"?

Kalau `s` dan `goal` **identik**, jumlah posisi yang berbeda adalah **`0`**, bukan `2` — jadi logika "cek tepat 2 posisi berbeda" di skenario kedua **tidak akan pernah** menangkap kasus ini dengan benar (akan selalu `false` untuk `dif.size() == 0 != 2`). Padahal untuk string identik, jawabannya **bisa** `true` (asalkan ada duplikat) — sebuah aturan yang **sama sekali berbeda logikanya** dari "cek dua posisi yang saling bertukar". Inilah kenapa soal ini **wajib** dipecah jadi dua skenario terpisah dengan aturan masing-masing, bukan digabung jadi satu kondisi umum.

______________________________________________________________________

## 🔧 Alternatif: Hitung Frekuensi Karakter untuk Deteksi Duplikat (Tanpa HashSet)

```java
public boolean buddyStrings(String s, String goal) {
    if (s.length() != goal.length()) return false;

    if (s.equals(goal)) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
            if (count[c - 'a'] > 1) return true; // ketemu duplikat lebih awal
        }
        return false;
    }

    List<Integer> dif = new ArrayList<>();
    for (int i = 0; i < s.length(); i++)
        if (s.charAt(i) != goal.charAt(i)) dif.add(i);

    return dif.size() == 2
        && s.charAt(dif.get(0)) == goal.charAt(dif.get(1))
        && s.charAt(dif.get(1)) == goal.charAt(dif.get(0));
}
```

Versi ini mengganti `HashSet<Character>` dengan **array frekuensi** (`int[26]`), memanfaatkan prinsip **direct address table** yang sudah dibahas di soal _Design HashSet_ — karena karakter dibatasi huruf kecil `'a'`-`'z'` (26 kemungkinan), array langsung lebih cepat daripada `HashSet` (tidak perlu hashing). Bonus tambahan: bisa **berhenti lebih awal** (`return true` segera) begitu duplikat pertama ditemukan, tanpa perlu memproses seluruh string.

| Approach | Time | Space | Bisa Early Exit? |
| -------------------------------- | ---- | ---------------------- | ---------------- |
| `HashSet<Character>` (kode asli) | O(n) | O(k) — k karakter unik | Tidak |
| Array frekuensi `int[26]` | O(n) | O(1) — ukuran tetap | Ya |

______________________________________________________________________

## 📌 Key Takeaway

Soal ini melatih kemampuan **memecah soal jadi kasus-kasus terpisah dengan aturan berbeda** — godaan untuk mencari satu kondisi umum yang menangani semua kasus sering kali membuat solusi lebih rumit (atau salah) dibanding mengenali bahwa "string identik" dan "string dengan tepat 2 perbedaan" itu **dua skenario logika yang fundamental berbeda**, meski sama-sama berujung pada pertanyaan "bisakah satu swap mencapai ini?". Perhatikan juga detail counter-intuitive bahwa **swap boleh menghasilkan string yang sama** kalau menukar dua posisi berisi karakter identik — sesuatu yang mudah terlewat kalau tidak membaca definisi soal dengan cermat. 🎯

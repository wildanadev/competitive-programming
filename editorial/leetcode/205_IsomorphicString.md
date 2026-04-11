# 205. Isomorphic Strings

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: String, Hash Table
- **Link**: [Problem](https://leetcode.com/problems/isomorphic-strings/)
- **Solution**: [Code](../../leetcode/IsomorphicString.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan dua string `s` dan `t`, cek apakah keduanya **isomorphic** — setiap karakter di `s` bisa di-mapping ke karakter di `t` secara konsisten dan bijektif (satu-satu).

Aturan:

- Setiap karakter di `s` harus mapping ke **tepat satu** karakter di `t`
- Tidak boleh dua karakter berbeda di `s` mapping ke karakter yang sama di `t`

Contoh:

- `s = "egg", t = "add"` → `true` (e→a, g→d)
- `s = "foo", t = "bar"` → `false` (o→a lalu o→r, inkonsisten)
- `s = "paper", t = "title"` → `true` (p→t, a→i, e→e, r→r)

______________________________________________________________________

## 💡 Intuition

Gunakan **HashMap** untuk track mapping `s[i] → t[i]`. Kalau karakter sudah ada di map, cek apakah mapping-nya konsisten. Kalau belum ada, cek apakah nilai `t[i]` sudah dipakai karakter lain — kalau sudah berarti dua karakter berbeda mapping ke karakter yang sama.

______________________________________________________________________

## 🔍 Approach

1. Inisialisasi `iso = HashMap`
1. Loop setiap index `i`:
   - Kalau `s[i]` **sudah ada** di map:
     - Cek `iso.get(s[i]) == t[i]` → kalau tidak → return `false`
   - Kalau `s[i]` **belum ada** di map:
     - Cek `iso.containsValue(t[i])` → kalau sudah dipakai → return `false`
     - Tambah `iso.put(s[i], t[i])`
1. Return `true`

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | ---------------------------------------------- |
| **Time** | O(n²) — `containsValue()` adalah O(n) per call |
| **Space** | O(n) — HashMap menyimpan mapping |

> Bisa dioptimasi ke O(n) dengan dua HashMap (satu untuk s→t, satu untuk t→s)

______________________________________________________________________

## 🧪 Dry Run

**Input:** `s = "egg", t = "add"`

**Init:** `iso = {}`

| i | s[i] | t[i] | containsKey? | Aksi | iso |
| --- | ---- | ---- | ------------ | --------------------------------- | ---------- |
| 0 | e | a | ❌ | containsValue('a')? ❌ → put(e,a) | {e→a} |
| 1 | g | d | ❌ | containsValue('d')? ❌ → put(g,d) | {e→a, g→d} |
| 2 | g | d | ✅ | get(g)=d == d ✅ | {e→a, g→d} |

**return `true` ✅**

______________________________________________________________________

**Input:** `s = "foo", t = "bar"`

| i | s[i] | t[i] | containsKey? | Aksi | iso |
| --- | ---- | ---- | ------------ | ----------------------------------- | ---------- |
| 0 | f | b | ❌ | containsValue('b')? ❌ → put(f,b) | {f→b} |
| 1 | o | a | ❌ | containsValue('a')? ❌ → put(o,a) | {f→b, o→a} |
| 2 | o | r | ✅ | get(o)=a != r ❌ → **return false** | - |

**return `false` ✅**

______________________________________________________________________

**Input:** `s = "ab", t = "aa"`

| i | s[i] | t[i] | containsKey? | Aksi | iso |
| --- | ---- | ---- | ------------ | ----------------------------------------- | ----- |
| 0 | a | a | ❌ | containsValue('a')? ❌ → put(a,a) | {a→a} |
| 1 | b | a | ❌ | containsValue('a')? ✅ → **return false** | - |

**return `false` ✅** → dua karakter berbeda (`a` dan `b`) tidak boleh mapping ke karakter yang sama (`a`)

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Karakter mapping ke dirinya sendiri `s="abc", t="abc"` → `true`
- [ ] Dua karakter berbeda mapping ke karakter yang sama `s="ab", t="aa"` → `false`
- [ ] String satu karakter → selalu `true`

______________________________________________________________________

## 📌 Key Takeaway

Dua pengecekan kritis:

1. **`containsKey`** → cek konsistensi mapping yang sudah ada
1. **`containsValue`** → cek apakah nilai `t[i]` sudah dipakai karakter lain (bijektif)

Solusi ini O(n²) karena `containsValue()` adalah O(n). Bisa dioptimasi ke **O(n)** dengan dua HashMap — satu untuk `s→t` dan satu untuk `t→s` — sehingga tidak perlu `containsValue()` lagi. 🎯

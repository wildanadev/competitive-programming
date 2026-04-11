# 290. Word Pattern

- **Platform**: LeetCode
- **Difficulty**: Easy
- **Topics**: Hash Table, String
- **Link**: [Problem](https://leetcode.com/problems/word-pattern/)
- **Solution**: [Code](../leetcode/WordPattern.java)

______________________________________________________________________

## 📄 Problem Summary

Diberikan string `pattern` dan string `s`, kembalikan `true` jika `s` mengikuti pola yang sama dengan `pattern`.

Contoh:

- `pattern = "abba"`, `s = "dog cat cat dog"` → `true`
- `pattern = "abba"`, `s = "dog cat cat fish"` → `false`
- `pattern = "aaaa"`, `s = "dog cat cat dog"` → `false`

______________________________________________________________________

## 💡 Intuition

Setiap karakter di `pattern` harus **mapping ke tepat satu kata** di `s`, dan setiap kata di `s` harus **mapping ke tepat satu karakter** di `pattern` (bijective mapping).

Contoh kasus yang harus ditangani:

```
pattern = "ab", s = "dog dog"
a → dog ✅
b → dog ❌ "dog" sudah dipakai oleh "a"
```

______________________________________________________________________

## 🔍 Approach

1. Split string `s` menjadi array kata pakai `split(" ")`
1. Cek apakah panjang `pattern` sama dengan jumlah kata → kalau beda langsung `false`
1. Loop setiap karakter `pattern` dan kata di `s`:
   - Kalau karakter **sudah ada di map** atau kata **sudah ada sebagai value**:
     - Kalau karakter tidak ada di map → `false` (kata sudah dipakai karakter lain)
     - Kalau mapping tidak sesuai → `false`
   - Kalau belum ada → tambahkan mapping baru

______________________________________________________________________

## 🧮 Complexity

| | |
| --------- | -------------------------------- |
| **Time** | O(n) — loop sepanjang pattern |
| **Space** | O(n) — HashMap menyimpan mapping |

______________________________________________________________________

## 🧪 Dry Run

**Input:** `pattern = "abba"`, `s = "dog cat cat dog"`

**Init:** `sMap = ["dog", "cat", "cat", "dog"]`, `mapping = {}`

| i | t | word | mapping.containsKey(t)? | mapping.containsValue(word)? | Aksi |
| --- | --- | ---- | ----------------------- | ---------------------------- | -------------------- |
| 0 | a | dog | ❌ | ❌ | put(a, dog) |
| 1 | b | cat | ❌ | ❌ | put(b, cat) |
| 2 | b | cat | ✅ | ✅ | get(b)=cat == cat ✅ |
| 3 | a | dog | ✅ | ✅ | get(a)=dog == dog ✅ |

**return `true` ✅**

______________________________________________________________________

**Input:** `pattern = "abba"`, `s = "dog cat cat fish"`

| i | t | word | Aksi |
| --- | --- | ---- | ---------------------------------------- |
| 0 | a | dog | put(a, dog) |
| 1 | b | cat | put(b, cat) |
| 2 | b | cat | get(b)=cat == cat ✅ |
| 3 | a | fish | get(a)=dog != fish ❌ → **return false** |

**return `false` ✅**

______________________________________________________________________

**Input:** `pattern = "ab"`, `s = "dog dog"`

| i | t | word | Aksi |
| --- | --- | ---- | ------------------------------------------------------------------------------- |
| 0 | a | dog | put(a, dog) |
| 1 | b | dog | containsKey(b)=❌, containsValue(dog)=✅ → containsKey(b)=❌ → **return false** |

**return `false` ✅**

______________________________________________________________________

## ⚠️ Edge Cases

- [ ] Panjang `pattern` berbeda dengan jumlah kata di `s`
- [ ] Dua karakter berbeda mapping ke kata yang sama → `false`
- [ ] Satu karakter mapping ke dua kata berbeda → `false`
- [ ] Pattern satu karakter, satu kata

______________________________________________________________________

## 📌 Key Takeaway

Soal ini tentang **bijective mapping** — setiap karakter harus punya pasangan kata yang unik dan sebaliknya. Kuncinya adalah cek **dua arah**:

- `containsKey(t)` → cek karakter sudah punya mapping
- `containsValue(word)` → cek kata sudah dipakai karakter lain

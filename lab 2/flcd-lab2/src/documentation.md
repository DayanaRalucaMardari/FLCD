# SymbolTable

Implemented a SymbolTable based on a **HashTable** which can be used for both the IDENTIFIERS and CONSTANTS, 
in separate tables or in the same one.
The methods of the **HashTable** are also implemented int the **SymbolTable**, except the `hash(String key)` method.

### Pair
Implemented the pair class which has the integer `firstItem` and `secondItem` attributes, used for storing the position of an element from the SymbolTable (HashTable).

## HashTable
It is implemented as a list of lists, meaning that on each position from a list there is another list.

It has the following methods:
* `getSize()` - returns the size of the list which has as elements other lists
*  `hash(String key)` - returns the hash value of a given element as parameter
* `findElemPosition(String elem)` - returns the position of the element as a `Pair` object if found; otherwise, it 
returns null
* `containsElem(String elem)` - returns `true` whether the given element is found; otherwise, it returns `false`
* `findByPosition(Pair position)` - returns the element found on the given position. If the position is invalid, it throws `IndexOutOfBoundsException`
* `addElem(String elem)` - returns `true` whether the given element was added successfully; otherwise, it returns `false` if the element already exists
* `toString()` - override method that returns the size of the hash table and the elements
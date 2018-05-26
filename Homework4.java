import java.util.*;

class DSAHashTable<K,V> {

    Set<Pair<K, V>>[] array;
    int size;

    // Vytvori prazdnou instanci DSAHashTable, delka vnitrniho pole je nastavena na size, obsah vnitrniho pole je inicializovan na prazdne mnoziny.
    DSAHashTable(int size) { 
        this.size = 0;
        createNewArray(size);
    }

    // Ulozi dvojici (key, value) do rozptylovaci tabulky. Pokud uz v tabulce je jina dvojice se stejnym klicem, je smazana. 
    // Klic ani hodnota nesmi byt null. Pokud by pocet dvojic v tabulce po vykonani put mel vzrust nad dvojnasobek delky vnitrniho pole,
    // vnitrni pole zdvojnasobi.
    void put(K key, V value) { 
        remove(key, getSetOf(key));    
        Pair<K, V> foo = new Pair<K, V>(key, value);
        getSetOf(key).add(foo);
        size = size + 1;
        if (!sizeOK()){
            resize(array.length * 2);
        }
    }

    // Vrati hodnotu asociovanou s danym klicem nebo null, pokud dany klic v tabulce neni.
    V get(K key) {
        Set<Pair<K, V>> set = getSetOf(key);
        for (Pair<K, V> p : set) {
            if (p.key.equals(key)) {
                return p.value;
            }
        }
        return null;
    }

    // Smaze dvojici s danym klicem. Pokud v tabulce dany klic neni, nedela nic.
    void remove(K key) {
        int step = 1;
        Set<Pair<K, V>> foo = getSetOf(key);
        Iterator<Pair<K, V>> i = foo.iterator();
        while (i.hasNext()){
            boolean question = i.next().key.equals(key);            
            if (question) {
                i.remove();
                size = size - step;
            }
        }
    }
    
    void remove(K key, Set<Pair<K, V>> set) {
        int step = 1;
        Iterator<Pair<K, V>> i = set.iterator();
        while (i.hasNext()){
            boolean question = i.next().key.equals(key);            
            if (question) {
                i.remove();
                size = size - step;
            }
        }
    }

    // Vrati vnitrni pole. Prvky vnitrniho pole mohou byt instance trid v balicku java.util, tzn. nemusite psat vlastni implementaci rozhrani java.util.Set.
    Set<Pair<K,V>>[] getArray() { 
        return array;
    }

    // Pro dany klic vrati index v poli. Jako hashovaci funkce se pouzije key.hashCode.
    int getIndexOf(K key) {
        int result = key.hashCode() % array.length;
        return result;
    }

    // Pokud je pocet prvku mensi nebo roven dvojnasobku delky vnitrniho pole, vrati true, jinak vrati false.
    boolean sizeOK() { 
        return (size <= array.length * 2) ? true : false;
    }

    // Zmeni delku vnitrniho pole, nainicializuje jej prazdnymi mnozinami a zkopiruje do nej vsechny dvojice.
    void resize(int newSize) { 
        Iterator<Pair<K, V>> i = iterator();
        Pair<K, V> p;
        createNewArray(newSize);
        while (i.hasNext()) {
            p = i.next();
            getSetOf(p.key).add(p);
        }
    }

    // Vrati iterator pres vsechny dvojice v tabulce. Iterator nemusi mit implementovanou metodu remove.
    Iterator<Pair<K,V>> iterator() { 
        return new Iterator<Pair<K, V>>() {
            Iterator<Pair<K, V>> it;
            int offset = 0;
            Set<Pair<K, V>>[] array = DSAHashTable.this.array;

            public boolean hasNext() {
                boolean question = offset < array.length;
                
                do {
                    if (isNull(it)) {
                        it = array[offset].iterator();
                    }
                    if (it.hasNext()) {
                        return true;
                    }
                    if (!isNull(it)) {
                        it = null;
                    }
                    offset = offset + 1;
                    question = offset < array.length;
                } while(question);
                
                return false;
            }

            public Pair<K, V> next() {
                boolean question = offset < array.length;
                
                do {
                    if (isNull(it)) {
                        it = array[offset].iterator();
                    }
                    if (it.hasNext()) {
                        return it.next();
                    }
                    if (!isNull(it)) {
                        it = null;
                    }
                    offset = offset + 1;
                    question = offset < array.length;
                } while (question);
                
                throw new NoSuchElementException();
            }

            public void remove() {
            }
            
            private boolean isNull(Iterator<Pair<K, V>> it) {
                if (it == null) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    //
    //
    // VLASTNÍ FUNKCE

    boolean createNewArray(int size) {
        array = (Set<Pair<K, V>>[]) new Set<?>[size];
        int end = size;
        int index = 0;
        while (index < end) {
            array[index] = new HashSet<Pair<K, V>>();
            index++;
        }
        return true;
    }

    Set<Pair<K, V>> getSetOf(K key) {
        return array[getIndexOf(key)];
    }

}
import java.util.Arrays;

class Homework3<E extends DSAComparable<E>> implements HeapStorage<E> { 

    private E[] heapStorage;
    private int size;
    
    // Vytvori novy objekt HeapStorage nad polem elements, jeho velikost je stejna jako delka pole. 
    Homework3(E[] elements) { 
        this.heapStorage = elements;
        this.size = elements.length;
    } 

    // metody 
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (getSize() == 0) ? true : false;
    }

    @Override
    public E getElement(int index) {
        E element;
        if (index <= 0) {
            element = this.heapStorage[0];
            for (int i = 0; i < getSize(); i++) {
                element = (!element.less(heapStorage[i])) ? heapStorage[i] : element;
            }
        } else if (index > getSize()) {
            element = this.heapStorage[0];
            for (int i = 0; i < getSize(); i++) {
                element = (!element.less(heapStorage[i])) ? heapStorage[i] : element;
            }
        } else {
            index = index - 1;
            return heapStorage[index];
        }
        return element;
    }

    @Override
    public void swap(int index, int index2) {
        E element = heapStorage[index - 1];
        heapStorage[index - 1] = heapStorage[index2 - 1];
        heapStorage[index2 - 1] = element;
    }

    @Override
    public E extractLast() {
        E retElement = heapStorage[getSize() - 1];
        size--;
        return retElement;
    }

    @Override
    public int insertLast(E element) {
        heapStorage[getSize()] = element;
        size = size + 1;
        return getSize();
    }
} 

// Trida Heap reprezentuje haldu (s maximem ve vrcholu). 
class Heap<E extends DSAComparable<E>> { 

    int size;
    HeapStorage<E> storage; // Vytvori haldu nad danym HeapStorage (tzn. zavola algoritmus build heap). 

    Heap(HeapStorage<E> storage) { 
        this.storage = storage;
        this.size = storage.getSize();
        int i = this.size;
        while (i > 0) {
            heapify(i);
            i--;
        }
    } 

    // Zavola algoritmus heapify nad uzlem na indexu index.
    void heapify(int index) { 
        int foo;
        int left = index * 2;
        int right = index * 2 + 1;

        if (rightLowerOrEqualInSize(right, size)) {
            foo = (finalCheck(left, right)) ? right : left;
        } else if (leftLowerOrEqualInSize(left, size)) {
            foo = left;
        } else {
            foo = ((int)Math.round(Math.pow(left - right, 1)) < 0) ? -1 : left;
        }

        if (indexCheck(foo, index)) {
            storage.swap(index, foo);
            heapify(foo);
        }
    } 

    // Vlozi do haldy novy prvek. Muzete predpokladat, ze v poli uvnitr HeapStorage na nej misto je. 
    void insert(E element) { 
        size++;
        storage.insertLast(element);

        for (int i = size; i > 0; i--) {
            heapify(i);
        }
    } 

    // Odstrani a vrati z haldy maximalni prvek. 
    E extractMax() { 
        if (isEmpty()) {
            return null;
        }
        this.storage.swap(1, size);
        this.size = this.size - 1;
        E max = storage.extractLast();
        heapify(1);
        return max;
    } 

    // Vrati true, pokud je halda prazdna. 
    boolean isEmpty() { 
        return (storage.getSize() == 0) ? true : false;
    }

    // Pomoci algoritmu trideni haldou vzestupne setridi pole array. 
    static <E extends DSAComparable<E>> void heapsort(E[] array) { 
        HeapStorage storage = new Homework3(array);
        Heap heap = new Heap(storage);
        int size = heap.size;
        int i = size;
        
        while (i >= 2) {
            storage.swap(1, i);
            i = i - 1;
            heap.size = heap.size - 1;
            heap.heapify(1);
        }
    }
    
    //
    //
    // VLASTNÍ FUNKCE
    
    private boolean leftLowerOrEqualInSize(int left, int size) {
        return (left <= size) ? true : false;
    }

    private boolean finalCheck(int left, int right) {
        return (storage.getElement(left).less(storage.getElement(right))) ? true : false;
    }

    private boolean indexCheck(int anotherIndex, int index) {
        return (anotherIndex > -1 && storage.getElement(index).less(storage.getElement(anotherIndex))) ? true : false;
    }

    private boolean rightLowerOrEqualInSize(int right, int size) {
        return (right <= size) ? true : false;
    }
}
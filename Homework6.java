/*
 * Naimplementujte třídy Tree a Node implementující B-strom. Kód uložte do souboru Homework6.java.
 */

import java.util.List;
import java.util.ArrayList;

// Instance tridy Tree reprezentuji B-strom.

class Tree {

    // Koren tohoto stromu.

    Node root;

    // vase pomocne atributy

    int nodeCapacity;
    int nodeMin;

    /* atributy */

    // Vytvori prazdny B-strom se zadanou kapacitou uzlu. Kapacita uzlu je maximalni pocet klicu, ktere se v jednom uzlu mohou objevit.
    Tree(int nodeCapacity) { 
        this.root = new Node(nodeCapacity, 1);
        this.nodeCapacity = nodeCapacity;
        if (nodeCapacity % 2 == 1) {
            this.nodeMin = (nodeCapacity - 1) / 2;
        } else {
            this.nodeMin = nodeCapacity / 2;
        }
        
        System.out.println("[INFO] Creating tree with nodeCapacity of: " + nodeCapacity);
        
    }

    // Indikuje, jestli tento strom obsahuje zadany klic.
    boolean contains(Integer key) {
        System.out.println("[CONTAINS] Testing element: " + key);
        return this.containsInternal(key);
    }

    // Prida zadany klic do stromu. Pokud tento strom zadany klic uz obsahuje, neudela nic.
    void add(Integer key) {
        System.out.println("[ADD] Adding element " + key);
        this.addInternal(key);
    } 

    // Odebere zadany klic ze stromu. Pokud tento strom zadany klic neobsahuje, neudela nic.

    void remove(Integer key) {
        if ((root.equals(null)) || (!this.containsInternal(key))) {
            return;
        } else {
            System.out.println("[REMOVE] Removing element " + key);
            
            Node node = root.searchNode(key);
            
            if (node.isLeaf()) {
                node.keys.remove(key);
                
                if (node.keys.size() == 0) {
                    root = (node == root) ? null : root;
                    node = (node != root) ? null : node;
                }
            } else {
                List<Integer> list = new ArrayList<>();
                copyTree(list, root);
                list.remove(key);
                this.root = new Node(nodeCapacity, 1);
                
                for (Integer number : list) {
                    this.addInternal(number);
                }
            }
        }
    } 

    //
    // MOJE FUNKCE

    /* dalsi potrebny kod */
    
    Node divideNode(List<Node> list) {
        Node node = list.get(list.size()-1);
        Integer middle = node.getKeys().get(nodeMin);
        Node temp = new Node(nodeCapacity,nodeMin);
        
        node.min = (list.size() == 1) ? nodeMin : node.min;
            
        for (int i = 0; i < node.getKeys().size(); i++) {
            Integer valueOfKey = node.keys.get(i).intValue();
            
            if (valueOfKey > middle.intValue()) {
                temp.keys.add(node.keys.get(i));
                node.keys.remove(i);

                if (!node.isLeaf()) {
                        temp.children.add(node.children.get(i));
                        node.children.remove(i);
                        int numberOfKeys = node.keys.size();
                        
                        if (i == numberOfKeys) {
                            temp.children.add(node.children.get(numberOfKeys));
                            node.children.remove(numberOfKeys);
                        }
                }

                i = i - 1;
            }   
        }
            
        if (list.size() != 1) {
            temp.middle = middle;
            node.nonfull = true;
            temp.leaf = node.leaf;
            Node curr = list.get(list.size()-2);
            
            if (curr.isFull()) {
                List<Node> pomArray = new ArrayList<>();
                Integer middleValue = middle;
                list.remove(node);
                Node newNode = divideNode(list);
                
                if (middle < newNode.middle) {
                    curr.keys.add(middleValue);
                    curr.keys= curr.getKeys();
                    int i = 0;                    
                    
                    while (i < curr.children.size()) {
                        pomArray.add(curr.children.get(i));
                        
                        if (curr.children.get(i) == node) {
                            pomArray.add(temp);
                        }
                        
                        i = i + 1;                    
                    }
                    
                    curr.children = pomArray;
                } else {
                    newNode.keys.add(middleValue);
                    newNode.keys= newNode.getKeys();
                    int i = 0;
                    
                    while (i < newNode.children.size()) {
                        pomArray.add(newNode.children.get(i));
                        
                        if (newNode.children.get(i) == node) {
                            pomArray.add(temp);
                        }
                        
                        i = i + 1;
                    }
                    newNode.children = pomArray;
                }
                node.keys.remove(middleValue);
            } else {
                List<Node> pomArray = new ArrayList<>();
                Integer middleValue = (Integer)middle;
                curr.keys.add(middleValue);
                node.keys.remove(middleValue);
                curr.keys=curr.getKeys();
                int i = 0;
                
                while (i < curr.children.size()) {
                    pomArray.add(curr.children.get(i));
                    
                    if (curr.children.get(i) == node) {
                        pomArray.add(temp);
                    }
                    
                    i = i + 1;
                }
                
                curr.children = pomArray;
                curr.nonfull = (curr.keys.size() == nodeCapacity) ? false : curr.nonfull;
            }
            
        } else {
            
            Node foo = new Node(nodeCapacity,1);
            foo.keys.add(middle);
            node.nonfull = true;
            node.keys.remove((Integer)middle);
            temp.leaf = node.leaf;
            foo.children.add(node);
            foo.children.add(temp);
            
            root = foo;
            foo.leaf = false;
            temp.middle = middle;
        }
        
        return temp;
    }
    
    void copyTree(List list, Node node) {
        for (int i = 0; i < node.keys.size(); i++) {
            list.add(node.keys.get(i));
        }

        if (node.isLeaf()) {
            return;
        } else {
            for (Node foo : node.children) {
                copyTree(list, foo);
            }
        }
    }
    
    boolean containsInternal(Integer key) {
        if (root.equals(null)) {
            return false;
        } else {
            if (root.searchNode(key) != null) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    void addInternal(Integer key) {
        if (!containsInternal(key)) {
            int newKey = key.intValue();
            Node current = root;
            List<Node> list = new ArrayList<>();
            
            while (current != null) {
                list.add(current);
                
                if (!current.isLeaf()) {
                    for (int i = 0; i < current.keys.size(); i++) {
                        if ((Integer)newKey < current.keys.get(i)) {
                            current = current.children.get(i);
                            break;
                        }
                        int numberOfKeys = current.keys.size();
                        if (current.keys.get(i) == current.keys.get(numberOfKeys - 1)) {
                            current = current.children.get(numberOfKeys);
                            break;
                        }
                    }
                } else {
                    if (!current.isFull()) {
                        current.keys.add(key);
                        current.keys = current.getKeys();
                        if (nodeCapacity == current.getKeys().size()) {
                            current.nonfull = false;
                        }
                    } else {
                        if (newKey < current.keys.get(nodeMin).intValue()) {
                            current.keys.add(key);
                            current.keys = current.getKeys();
                        } else {
                            Node foo = divideNode(list);
                            foo.keys.add(key);
                            foo.keys = foo.getKeys();
                        }
                    }
                    
                    return;
                }
            }   
        }
    }

}

class Node {

    // atributy
    int middle;
    int maxc;
    int max;
    int min;
    boolean nonfull;
    boolean leaf;
    List<Integer> keys;
    List<Node> children;
    
    public Node(int max, int min) {
        this.maxc = max + 1;
        this.max = max;
        this.min = min;
        this.nonfull = true;
        this.leaf = true;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    // Vrati ukazatele na podstromy ulozene v tomto uzlu serazene v poradi odpovidajicim poradi klicu. Pozor: vzdy musi platit,
    // ze pocet_ukazatelu_na_podstromy = pocet_klicu + 1, u listovych uzlu proto musite vracet seznam nullu.
    List<Node> getSubtrees() {        
        List<Node> listOfNulls = new ArrayList<Node>();
        
        for (int i = children.size(); i < maxc; i++) {
            listOfNulls.add(null);
        }
        
        List<Node> toReturn = new ArrayList<Node>();
        toReturn.addAll(children);
        toReturn.addAll(listOfNulls);
        
        return toReturn;
    }
    
    // Vrati klice ulozene v tomto uzlu serazene vzestupne.
    List<Integer> getKeys() {
        int [] pole = new int[keys.size()];
        List<Integer> list = new ArrayList<Integer>();
        
        for (int i = 0; i < pole.length; i++) {
            pole[i] = keys.get(i);
        }
        
        pole = this.sort(pole);
        for (Integer i : pole) {
            list.add(i);
        }
        
        return list;
    }
    
    //
    // MOJE FUNKCE
    
    /* moje funkce */
    
    boolean isLeaf() {
        return leaf;
    }
    
    boolean isFull() {
        return !nonfull;
    }
    
    public Node searchNode(Integer k) {
        for (int i : keys) {
            if(i == k.intValue()){
                return this;
            }
        }
        
        if (!isLeaf()) {
            for (int i = 0; i < keys.size(); i++) {
                if ((k.intValue() < keys.get(i)) && (children.get(i) != null)) {
                    Node temp = children.get(i).searchNode(k.intValue());
                    if (temp != null) {
                        return temp;
                    }
                }   
            }
            return children.get(keys.size()).searchNode(k.intValue());
        }
        return null;
    }
    
    int [] sort(int [] array) {
        MyMergesort sorting = new MyMergesort();
        int[] sort = new int[array.length];
        sort = sorting.mergesort(array);
        return sort;
    }

}

class MyMergesort {

    public int[] mergesort(int[] array) {
        boolean alreadySorted = array.length <= 1;
        if (alreadySorted) {
            return array;
        } else {
            int[] firstHalf = getFirstHalfOf(array);
            int[] secondHalf = getSecondHalfOf(array);
            int[] firstHalfSorted = mergesort(firstHalf);
            int[] secondHalfSorted = mergesort(secondHalf);

            return merge(firstHalfSorted, secondHalfSorted);
        }
    }

    public int[] getFirstHalfOf(int[] array) {
        int length = (array.length + 1) / 2;
        int[] result = new int[length];
        
        for (int i = 0; i < result.length; i++) {
            result[i] = array[i];
        }
        
        return result;
    }

    public int[] getSecondHalfOf(int[] array) {
        int length = array.length / 2;
        int[] result = new int[length];
        int startingPossition = (array.length + 1) / 2;
        int endingPossition = startingPossition + result.length;
        
        for (int i = startingPossition; i < endingPossition; i++) {
            result[i - startingPossition] = array[i];
        }
        
        return result;
    }

    public int[] merge(int[] firstHalf, int[] secondHalf) {
        int first = 0;
        int second = 0;
        
        int totalLength = firstHalf.length + secondHalf.length;
        int[] result = new int[totalLength];
        int resultIndex = 0;
        
        while (resultIndex < result.length) {
            if (first == firstHalf.length) {
                result[resultIndex] = secondHalf[second];
                second++;
            }
            else
            {
                if (second == secondHalf.length) {
                    result[resultIndex] = firstHalf[first];
                    first++;
                }
                else
                {
                    if (firstHalf[first] < secondHalf[second]) {
                        result[resultIndex] = firstHalf[first];
                        first++;
                    }
                    else
                    {
                        result[resultIndex] = secondHalf[second];
                        second++;
                    }
                }
            }
            
            resultIndex++;
        }
        
        return result;
    }
}
import java.util.*;

// Trida Tree reprezentuje binarni vyhledavaci strom, ve kterem pro kazdy uzel n plati
// n.left == null || n.left.contents.less(n.contents) a
// n.right == null || n.right.contents.greater(n.contents).
class Tree<E extends DSAComparable<E>> {

    Node<E> root;
    int size;
    
    Tree() {
        root = null;
        size = 0;
    }

    // Vrati minimum z tohoto stromu nebo null, pokud je strom prazdny.
    E minimum() {
        return subtreeMin(root);
    }

    // Vrati minimum ze zadaneho podstromu nebo null, pokud je podstrom prazdny.
    E subtreeMin(Node<E> n) {
        if (nodeExists(n)) {
            while (n.left != null) {
                n = n.left;
            }
        }
        return n.contents;
    }

    // Vrati maximum z tohoto podstromu nebo null, pokud je podstrom prazdny.
    E maximum() {
        return subtreeMax(root);
    }

    // Vrati maximum ze zadaneho podstromu nebo null, pokud je podstrom prazdny.
    E subtreeMax(Node<E> n) {
        if (!nodeExists(n)) {
            return null;
        }

        Node<E> tmp = n;
        while (nodeExists(tmp.right)) {
            tmp = tmp.right;
        }

        return tmp.contents;
    }

    // Vlozi prvek do stromu (duplicity jsou zakazane)
    void insert(E elem) {
        root = insert(elem, root);
    }
    
    // Vrati korenovy uzel tohoto stromu.
    Node<E> getRootNode() {
        return root;
    }

    // Projde strom a vrati:
    // - uzel s hodnotou elem, pokud existuje,
    // - null pokud uzel s hodnotou elem existuje
    Node<E> find(E elem) {
        if (!nodeExists(root)) {
            return null;
        }
        Node<E> node = root;

        while (!node.contents.equal(elem)) {
            if (node.contents.less(elem)) {
                if (nodeExists(node.right)) {
                    node = node.right;
                } else {
                    return null;
                }
            } else {
                if (nodeExists(node.left)) {
                    node = node.left;
                } else {
                    return null;
                }
            }
        }
        return node;
    }

    // Vrati true, pokud tento strom obsahuje prvek elem.
    boolean contains(E elem) {
        if (find(elem) == null) {
            return false;
        } else {
            return true;
        }
    }

    // Odstrani vyskyt prvku elem z tohoto stromu.
    void remove(E elem) {
        root = remove(elem, root);
    }

    // Vrati iterator pres cely strom (od nejmensiho po nejvetsi). Metoda remove() nemusí být implementována
    Iterator<E> iterator() {
        Iterator<E> iter = new Iterator<E>() {
            private int currentindex = 0;
            private List<E> array = getInOrder();

            @Override
            public E next() {
                currentindex = currentindex + 1;
                return array.get(currentindex - 1);
            }
            
            @Override
            public boolean hasNext() {
                if (currentindex >= array.size() || (array.size() == 0)) {
                    return false;
                }
                return true;
            }

            @Override
            public void remove() {
            }
        };

        return iter;
    }
    
    
    //
    //
    // VLASTNÍ FUNKCE
    
    private List<E> getInOrder() {
        Stack<Node<E>> stack = new Stack<Node<E>>();
        boolean finished = false;
        List<E> list = new ArrayList<E>();
        Node<E> current = root;
        stack.clear();

        while (!finished) {
            if (nodeExists(current)) {
                stack.push(current);
                current = current.left;
            } else {
                if (stack.empty()) {
                    finished = true;
                } else {
                    current = stack.pop();
                    list.add(current.contents);
                    current = current.right;
                }
            }
        }
        return list;
    }
    
    private Node<E> insert(DSAComparable<E> x, Node<E> t) {
        if (!nodeExists(t)) {
            t = new Node<E>((E) x, null);
            return t;
        } else if (x.less(t.contents)) {
            t.left = insert(x, t.left);
            return t;
        } else if (x.greater(t.contents)) {
            t.right = insert(x, t.right);
            return t;
        } else {
            return t;
        }
    }

    private Node<E> remove(DSAComparable x, Node<E> t) {
        if (!nodeExists(t)) {
            return t;
        }
        if (x.less(t.contents)) {
            t.left = remove(x, t.left);
            return t;
        } else if (x.greater(t.contents)) {
            t.right = remove(x, t.right);
            return t;
        } else if (nodeExists(t.left) && nodeExists(t.right)) {
            t.contents = subtreeMin(t.right);
            t.right = remove(t.contents, t.right);
            return t;
        } else {
            if (nodeExists(t.left)) {
                t = t.left;
            } else {
                t = t.right;
            }
            return t;
        }
    }
    
    private boolean nodeExists(Node<E> node) {
        if (node != null) {
            return true;
        } else {
            return false;
        }
    }
}
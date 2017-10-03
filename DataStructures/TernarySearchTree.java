/*
A Ternary Search Tree is used to store a collection of items (generally strings).
Each node contains a character, and has three pointers:
Left: Stores strings starting with character less than current character
Equal: Stores strings starting with character equal to current character
Right: Stores strings starting with character greater than current character
Also, each node has a boolean flag "isLast" which is true is that character denotes end of some string.
Using TST, we can find if a given string of length m is present in a dictionary in O(m) TC.
We can also find all strings matching a given prefix.
TST is similar to a Trie, but it is more space efficient.
Below is an implementation of TST with following methods: insert, search and traversal.
 */

public class TernarySearchTree {
    class Node {
        char c;
        boolean isLast;
        Node left, right, equal;

        Node(char c)
        {
            this.c = c;
            isLast = false;
            left = right = equal = null;
        }
    }

    private Node root = null;

    private Node insertHelper(Node node, String word, int i)
    {
        if(node == null)
        {
            node = new Node(word.charAt(i));
        }
        if(node.c > word.charAt(i))
        {
            node.left = insertHelper(node.left, word, i);
        }
        else if(node.c < word.charAt(i))
        {
            node.right = insertHelper(node.right, word, i);
        }
        else
        {
            if(i == word.length() - 1)
                node.isLast = true;
            else
                node.equal = insertHelper(node.equal, word, i + 1);
        }
        return node;
    }

    public void insert(String word)
    {
        root = insertHelper(root, word, 0);
    }

    private void traversalHelper(Node node, char[] buffer, int i)
    {
        if(node != null)
        {
            traversalHelper(node.left, buffer, i);
            buffer[i] = node.c;
            if(node.isLast)
                System.out.println(String.valueOf(buffer, 0, i + 1));
            traversalHelper(node.equal, buffer, i + 1);
            traversalHelper(node.right, buffer, i);
        }
    }

    public void traverse()
    {
        char[] buffer = new char[256];
        traversalHelper(root, buffer, 0);
    }

    private boolean searchHelper(Node node, String query, int i)
    {
        if(node == null)
            return false;
        if(node.c > query.charAt(i))
            return searchHelper(node.left, query, i);
        else if(node.c < query.charAt(i))
            return searchHelper(node.right, query, i);
        else
        {
            if(i == query.length() - 1)
                return node.isLast;
            return searchHelper(node.equal, query, i + 1);
        }
    }

    public boolean search(String query)
    {
        return searchHelper(root, query, 0);
    }

    public static void main(String[] args) {
        TernarySearchTree tst = new TernarySearchTree();

        tst.insert("cat");
        tst.insert("bugs");
        tst.insert("cats");
        tst.insert("up");

        System.out.println("Ternary Search Tree Contents:");
        tst.traverse();

        System.out.println("Is cat present? " + tst.search("cat"));
        System.out.println("Is up present? " + tst.search("up"));
        System.out.println("Is bank present? " + tst.search("bank"));
    }
}

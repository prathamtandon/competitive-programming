import java.util.HashMap;

class LFUCache<TKey, TValue> {

    private class LFUTable {
        private class CacheEntry {
            TValue val;
            ItemList.Node node;
        }

        private HashMap<TKey, CacheEntry> table = new HashMap<TKey, CacheEntry>();

        boolean hasKey(TKey key)
        {
            return table.containsKey(key);
        }

        public CacheEntry get(TKey key)
        {
            return table.get(key);
        }

        public CacheEntry put(TKey key, TValue val)
        {
            CacheEntry cacheEntry = new CacheEntry();
            cacheEntry.val = val;
            cacheEntry.node = null;
            table.put(key, cacheEntry);
            return cacheEntry;
        }

        void delete(TKey key)
        {
            table.remove(key);
        }
    }

    private class FrequencyList {
        private class Node {
            int val;
            Node next;
            Node prev;
            ItemList itemList;

            Node(int val)
            {
                this.val = val;
                this.itemList = new ItemList();
            }
        }

        Node head = null;
        Node tail = null;

        public Node add(int val, Node before, Node after)
        {
            Node freqNode = new Node(val);
            if(before != null) {
                before.next = freqNode;
                freqNode.prev = before;
            }
            if(after != null)
            {
                after.prev = freqNode;
                freqNode.next = after;
            }
            if(head == null)
            {
                head = tail = freqNode;
            }
            else if(before == tail)
            {
                tail = freqNode;
            }
            return freqNode;
        }

        public boolean isEmpty()
        {
            return head == null;
        }

        void delete(Node node)
        {
            if(head == node && head == tail)
            {
                head = tail = null;
            }
            else if(head == node)
            {
                head = head.next;
            }
            else if(tail == node)
            {
                tail = tail.prev;
            }
            if(node.prev != null)
                node.prev.next = node.next;
            if(node.next != null)
                node.next.prev = node.prev;
        }
    }

    private class ItemList {
        private class Node {
            TKey val;
            Node next;
            Node prev;
            FrequencyList.Node parent;

            Node(TKey val, FrequencyList.Node parent)
            {
                this.val = val;
                this.parent = parent;
                this.next = this.prev = null;
            }
        }

        Node head = null;
        Node tail = null;

        public boolean isEmpty()
        {
            return head == null;
        }

        public Node add(TKey val, FrequencyList.Node parent)
        {
            Node itemNode = new Node(val, parent);
            if(head == null)
            {
                head = tail = itemNode;
            }
            else
            {
                tail.next = itemNode;
                itemNode.prev = tail;
                tail = itemNode;
            }
            return itemNode;
        }

        void delete(Node node)
        {
            if(head == node && head == tail)
            {
                head = tail = null;
            }
            else if(head == node)
            {
                head = head.next;
            }
            else if(tail == node)
            {
                tail = tail.prev;
            }
            if(node.prev != null)
                node.prev.next = node.next;
            if(node.next != null)
                node.next.prev = node.prev;
        }
    }

    private LFUTable lfuTable;
    private FrequencyList frequencyList;

    LFUCache()
    {
        lfuTable = new LFUTable();
        frequencyList = new FrequencyList();
    }

    public TValue get(TKey key) throws IllegalArgumentException
    {
        if(!lfuTable.hasKey(key))
            throw new IllegalArgumentException("Key not found");

        LFUTable.CacheEntry cacheEntry = lfuTable.get(key);
        FrequencyList.Node freqNode = cacheEntry.node.parent;
        freqNode.itemList.delete(cacheEntry.node);
        FrequencyList.Node nextNode = freqNode.next;
        if(nextNode == null)
        {
            nextNode = frequencyList.add(freqNode.val + 1, frequencyList.tail, null);
        }
        else if(nextNode.val != freqNode.val + 1)
        {
            nextNode = frequencyList.add(freqNode.val + 1, freqNode, nextNode);
        }
        cacheEntry.node = nextNode.itemList.add(key, nextNode);
        if(freqNode.itemList.isEmpty())
            frequencyList.delete(freqNode);
        return cacheEntry.val;
    }

    public void put(TKey key, TValue val)
    {
        LFUTable.CacheEntry cacheEntry;
        if(!lfuTable.hasKey(key))
        {
            cacheEntry = lfuTable.put(key, val);
            FrequencyList.Node freqNode;
            if(frequencyList.isEmpty())
            {
                freqNode = frequencyList.add(1, null, null);
            }
            else
            {
                freqNode = frequencyList.head;
            }
            cacheEntry.node = freqNode.itemList.add(key, freqNode);
        }
        else
        {
            cacheEntry = lfuTable.get(key);
            FrequencyList.Node freqNode = cacheEntry.node.parent;
            freqNode.itemList.delete(cacheEntry.node);
            FrequencyList.Node nextNode = freqNode.next;
            if(nextNode == null)
            {
                nextNode = frequencyList.add(freqNode.val + 1, frequencyList.tail, null);
            }
            else if(nextNode.val != freqNode.val + 1)
            {
                nextNode = frequencyList.add(freqNode.val + 1, freqNode, nextNode);
            }
            cacheEntry.node = nextNode.itemList.add(key, nextNode);
            if(freqNode.itemList.isEmpty())
                frequencyList.delete(freqNode);
        }
    }

    TKey evict() throws UnsupportedOperationException
    {
        if(frequencyList.isEmpty())
            throw new UnsupportedOperationException("Operation not allowed");
        FrequencyList.Node freqNode = frequencyList.head;
        ItemList.Node itemNode = freqNode.itemList.head;
        freqNode.itemList.delete(itemNode);
        if(freqNode.itemList.isEmpty())
            frequencyList.delete(freqNode);
        TKey result = itemNode.val;
        lfuTable.delete(result);
        return result;
    }
}


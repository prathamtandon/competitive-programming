import java.util.HashMap;

class LFUCache<TKey, TValue> {

    private class LFUTable {
        private class Entry {
            TValue val;
            ItemList.Node node;
        }

        private HashMap<TKey, Entry> table = new HashMap<TKey, Entry>();

        boolean hasKey(TKey key)
        {
            return table.containsKey(key);
        }

        public Entry get(TKey key)
        {
            return table.get(key);
        }

        public Entry put(TKey key, TValue val)
        {
            Entry entry = new Entry();
            entry.val = val;
            entry.node = null;
            table.put(key, entry);
            return entry;
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

        Node add(int val, Node before, Node after)
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

        private void delete(Node node)
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

        private void deleteIfEmpty(Node freqNode)
        {
            if(freqNode.itemList.isEmpty())
                delete(freqNode);
        }

        ItemList.Node incrementFrequency(ItemList.Node itemNode)
        {
            Node currentFreqNode = itemNode.parent;
            currentFreqNode.itemList.delete(itemNode);
            Node nextFreqNode = currentFreqNode.next;
            if(nextFreqNode == null)
            {
                nextFreqNode = add(currentFreqNode.val + 1, tail, null);
            }
            else if(nextFreqNode.val != currentFreqNode.val + 1)
            {
                nextFreqNode = add(currentFreqNode.val + 1, currentFreqNode, nextFreqNode);
            }
            deleteIfEmpty(currentFreqNode);
            return nextFreqNode.itemList.add(itemNode.val, nextFreqNode);
        }

        ItemList.Node evict()
        {
            ItemList.Node firstNode = head.itemList.head;
            head.itemList.delete(firstNode);
            if(head.itemList.isEmpty())
                delete(head);
            return firstNode;
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

        LFUTable.Entry entry = lfuTable.get(key);
        entry.node = frequencyList.incrementFrequency(entry.node);
        return entry.val;
    }

    public void put(TKey key, TValue val)
    {
        LFUTable.Entry entry;
        if(!lfuTable.hasKey(key))
        {
            entry = lfuTable.put(key, val);
            FrequencyList.Node freqNode = frequencyList.head;
            if(frequencyList.isEmpty())
            {
                freqNode = frequencyList.add(1, null, null);
            }
            else if(frequencyList.head.val != 1)
            {
                freqNode = frequencyList.add(1, null, frequencyList.head);
            }
            entry.node = freqNode.itemList.add(key, freqNode);
        }
        else
        {
            entry = lfuTable.get(key);
            entry.val = val;
            entry.node = frequencyList.incrementFrequency(entry.node);
        }
    }

    TKey evict() throws UnsupportedOperationException
    {
        if(frequencyList.isEmpty())
            throw new UnsupportedOperationException("Operation not allowed");
        ItemList.Node itemNode = frequencyList.evict();
        TKey result = itemNode.val;
        lfuTable.delete(result);
        return result;
    }
}


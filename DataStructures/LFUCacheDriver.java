public class LFUCacheDriver {
    public static void main(String[] args) throws UnsupportedOperationException, IllegalArgumentException {

        LFUCache<String, Integer> cache = new LFUCache<>();
        cache.put("First", 1024);
        cache.put("Second", 87);
        cache.put("Third", 298);
        System.out.println(cache.get("First"));
        System.out.println(cache.get("First"));
        System.out.println(cache.get("Second"));
        System.out.println(cache.get("Third"));
        System.out.println(cache.get("Third"));
        System.out.println(cache.get("Third"));
        cache.put("Third", 301);
        cache.put("Fourth", 1011);
        System.out.println(cache.get("Fourth"));
        System.out.println(cache.evict());
        System.out.println(cache.evict());
    }
}

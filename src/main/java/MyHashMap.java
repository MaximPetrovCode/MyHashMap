import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @param <K>
 * @param <V>
 * @author downey
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

    protected static final double FACTOR = 1.0;

    @Override
    public V put(K key, V value) {
        V oldValue = super.put(key, value);

        System.out.println("PUT key: " + key + " in maps: " + maps + " size (amount maps): " + maps.size());

        int totalAmountOfEntries = size();
        int amountOfSubMaps = maps.size();

        if (totalAmountOfEntries > amountOfSubMaps * FACTOR) { // ( totalAmountOfEntries / amountOfSubMaps) > FACTOR
            rehash();
        }
        return oldValue;
    }

    /**
     * Doubles the number of maps and rehashes the existing entries.
     */
    protected void rehash() {
        List<MyLinearMap<K, V>> oldMaps = this.maps;
        makeMaps(this.maps.size() * 2);
        for (MyLinearMap<K, V> map : oldMaps) {
            List<Entry<K, V>> listEntries = new ArrayList<>(map.getEntries());
            for (Entry<K, V> entry : listEntries) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Integer> map = new MyHashMap<String, Integer>();
        for (int i = 0; i < 10; i++) {
            map.put(Integer.toString(i), i);
        }

        Integer number = map.get("6");
        System.out.println("Number " + number + "is found!");
    }
}

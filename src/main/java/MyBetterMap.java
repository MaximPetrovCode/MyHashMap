import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a Map using a collection of MyLinearMap, and
 * using `hashCode` to determine which map each key should go in.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyBetterMap<K, V> implements Map<K, V> {

	protected List<MyLinearMap<K, V>> maps;

	/**
	 * Initialize the map with 2 sub-maps.
	 *
	 */
	public MyBetterMap() {
		makeMaps(2);
	}

	public int getAmountOfSubmaps() {
		return this.maps.size();
	}

	/**
	 * Makes a collection of `k` MyLinearMap
	 *
	 * @param k
	 */
	protected void makeMaps(int k) {
		maps = new ArrayList<MyLinearMap<K, V>>(k);
		for (int i=0; i<k; i++) {
			maps.add(new MyLinearMap<K, V>());
		}
	}

	@Override
	public void clear() {
		// clear the sub-maps
		for (int i=0; i<maps.size(); i++) {
			maps.get(i).clear();
		}
	}

	/**
	 * Uses the hashCode to find the map that would/should contain the given key.
	 *
	 * @param key
	 * @return
	 */
	protected MyLinearMap<K, V> chooseMap(Object key) {
		int index = getIndexByHash(key);
		return maps.get(index);
	}

	private int getIndexByHash(Object key) {
		return key == null ? 0 :  Math.abs(key.hashCode()) % maps.size();
	}

	@Override
	public boolean containsKey(Object target) {
		// to find a key, we only have to search one map
		V v = this.get(target);
		return v != null;
	}

	@Override
	public boolean containsValue(Object target) {
		Set<K> keys = this.keySet();
		for (K key : keys) {
			V value = this.get(key);
			if (target.equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {
		MyLinearMap<K, V> map = chooseMap(key);
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Set<K> keySet() {
		// add up the keySets from the sub-maps
		Set<K> set = new HashSet<K>();
		for (MyLinearMap<K, V> map: maps) {
			set.addAll(map.keySet());
		}
		return set;
	}

	@Override
	public V put(K key, V value) {
		MyLinearMap<K, V> map = chooseMap(key);
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Entry<? extends K, ? extends V> entry: map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		MyLinearMap<K, V> map = chooseMap(key);
		return map.remove(key);
	}

	@Override
	public int size() {
		// add up the sizes of the sub-maps
		int total = 0;
		for (MyLinearMap<K, V> map: maps) {
			total += map.size();
		}
		return total;
	}

	@Override
	public Collection<V> values() {
		// add up the valueSets from the sub-maps
		Set<V> set = new HashSet<V>();
		for (MyLinearMap<K, V> map: maps) {
			set.addAll(map.values());
		}
		return set;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyBetterMap<>();
		map.put("Word1", 1);
		map.put("Word2", 2);
		Integer value = map.get("Word1");
		System.out.println(value);

		for (String key: map.keySet()) {
			System.out.println(key + ", " + map.get(key));
		}
		System.out.println(map.size());
	}
}

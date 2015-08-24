package asciigame.items;

public class Inventory {

	private Item[] items;
	public Item[] getItems() { return items; }

	private int max;
	private int load;

	public Inventory(int max) {
		this.items = new Item[max];
		this.max = max;
		this.load = 0;
	}

	public int find(Item item) {
		for (int i = 0; i < max; i++) {
			if (items[i] == item) {
				return i;
			}
		}

		return -1;
	}

	public int find(String itemName) {
		for (int i = 0; i < max; i++) {
			if (items[i] != null && items[i].getName().equals(itemName)) {
				return i;
			}
		}

		return -1;
	}

	public void add(Item item) {
		for (int i = 0; i < max; i++) {
			if (items[i] == null) {
				items[i] = item;
				load++;
				return;
			}
		}
	}

	public void remove(int idx) {
		items[idx] = null;
		load--;
	}

	public boolean isFull() {
		return load >= max;
	}

	public void increaseMax(int increase) {
		Item[] temp = new Item[max + increase];

		System.arraycopy(items, 0, temp, 0, max);

		items = temp;
		max += increase;
	}

	// Maybe this is unnecessary, if I only ever increase inventory size
	/*public void setSize(int newSize) {

		// No change
		if (newSize == max) {
			return;
		}

		// Size is increasing
		if (newSize > max) {
			Item[] temp = new Item[newSize];

			for (int i = 0; i < max; i++) {
				temp[i] = items[i];
			}

			items = temp;
			max = newSize;
		}

		// Size is decreasing
		if (newSize < max) {
			// TODO
		}
	}*/
}

package asciigame.items;

public class Inventory {

	private Item[] items;
	private int max;
	private int load;

	public Item[] getItems() { return items; }

	public Inventory(int max) {
		this.items = new Item[max];
		this.max = max;
		this.load = 0;
	}

	public Item get(int i) {
		return items[i];
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

	public void remove(Item item) {
		for (int i = 0; i < max; i++) {
			if (items[i] == item) {
				items[i] = null;
				load--;
				return;
			}
		}
	}

	public boolean isFull() {
		return load >= max;
	}
}

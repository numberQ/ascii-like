package asciigame.items;

import java.util.ArrayList;
import java.util.List;

public class ItemPile {

	private List<Item> items;

	public ItemPile() {
		this.items = new ArrayList<>();
	}

	public Item getItem(int idx) {
		return items.get(idx);
	}

	public Item getTopItem() {
		return items.get(items.size() - 1);
		}

	public void addItem(Item item) {
		items.add(item);
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

	public int getPileSize() {
		return items.size();
	}

	public boolean hasNoItems() {
		return items.size() == 0;
	}
}

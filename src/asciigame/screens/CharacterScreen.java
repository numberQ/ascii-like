package asciigame.screens;

import asciiPanel.AsciiPanel;
import asciigame.creatures.Creature;
import java.awt.event.KeyEvent;

public class CharacterScreen implements Screen {

	private Creature player;

	public CharacterScreen(Creature player) {
		this.player = player;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int bylineY = terminal.getHeightInCharacters() - 2;
		int sheetWidthStart = terminal.getWidthInCharacters() / 6;
		int sheetHeightStart = terminal.getHeightInCharacters() / 6;
		int sheetWidth = terminal.getWidthInCharacters() * 2 / 3;
		int sheetHeight = terminal.getHeightInCharacters() * 2 / 3;

		// Create space
		terminal.clear(' ', 0, bylineY - 1, terminal.getWidthInCharacters() - 1, 3);
		for (int i = 0; i < sheetHeight; i++) {
			if (i == 0 || i == sheetHeight - 1) {
				for (int j = 0; j <= sheetWidth; j++) {
					terminal.write('-', sheetWidthStart + j, sheetHeightStart + i);
				}
			} else {
				terminal.write('|', sheetWidthStart, sheetHeightStart + i);
				terminal.clear(' ', sheetWidthStart + 1, sheetHeightStart + i, sheetWidth - 1, 1);
				terminal.write('|', sheetWidthStart + sheetWidth, sheetHeightStart + i);
			}
		}

		// Write stats
		int x = sheetWidthStart + 2;
		int y = sheetHeightStart + 3;
		terminal.write("Level " + player.getLevel() + ", " + player.getXp() + "/" + player.nextXpThreshold() + "xp", x, y++);
		terminal.write("Health: " + player.getHealth() + "/" + player.getMaxHealth(), x, y++);
		terminal.write("Hunger: " + player.hungerLevel(), x, y++);
		terminal.write("Attack: " + player.getMinAttack() + " - " + player.getMaxAttack(), x, y++);
		terminal.write("Item Attack Bonus: " + player.getItemAttack(), x, y++);
		terminal.write("Defense: " + player.getDefense(), x, y++);
		terminal.write("Item Defense Bonus: " + player.getItemDefense(), x, y++);
		terminal.write("Vision: " + player.getVisionRadius(), x, y++);
		terminal.write("Weapon: " + (player.getWeapon() != null ? player.getWeapon().getName() : "nothing"), x, y++);
		terminal.write("Armor: " + (player.getArmor() != null ? player.getArmor().getName() : "nothing"), x, y);

		terminal.write(player.getName() + " (", sheetWidthStart + 2, sheetHeightStart + 1);
		terminal.write(player.getGlyph(), player.getColor());
		terminal.write(")");
		terminal.writeCenter("Press [escape] or [enter] to exit.", bylineY);
	}

	@Override
	public Screen respondToUserInputAndUpdate(KeyEvent key) {
		switch (key.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_ENTER:
				return null;
			default:
				return this;
		}
	}
}

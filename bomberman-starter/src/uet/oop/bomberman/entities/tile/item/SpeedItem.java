package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {

	public SpeedItem(int x, int y, int level, Sprite sprite) {
        super(x, y, level, sprite);
    }

    @Override
    public void setValues() {
        _active = true;
        Game.addBomberSpeed(0.25);
    }
}

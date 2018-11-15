/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.entities.tile.powerup;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;
/**
 *
 * @author Hoang Vu Huong
 */
public abstract class Powerup extends Tile {

	protected int _duration = -1; // -1 is infinite, duration in lifes
	protected boolean _active = false;
	protected int _level;
	
	public Powerup(int x, int y, int level, Sprite sprite) {
		super(x, y, sprite);
		_level = level;
	}
	
	public abstract void setValues();
	
	public void removeLive() {
		if(_duration > 0)
			_duration--;
		
		if(_duration == 0)
			_active = false;
	}
	
	public int getDuration() {
		return _duration;
	}
	
	public int getLevel() {
		return _level;
	}

	public void setDuration(int duration) {
		this._duration = duration;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		this._active = active;
	}
}

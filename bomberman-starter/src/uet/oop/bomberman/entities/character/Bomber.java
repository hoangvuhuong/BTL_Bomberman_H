package uet.oop.bomberman.entities.character;

import java.awt.Color;
import java.util.ArrayList;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.level.Coordinates;

import uet.oop.bomberman.music.ListSound;
import uet.oop.bomberman.music.Sound;
public class Bomber extends Character {

    public Sound Die = new Sound("C:\\Users\\Hoang Vu Huong\\Desktop\\Bom\\bomberman-starter\\res\\music\\choang.wav");

    private List<Bomb> _bombs;
    protected Keyboard _input;
    public static List<Item> _items = new ArrayList<>();
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb
     * tiếp theo, cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ
     * được reset v�? 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;
   

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) {
            _timeBetweenPutBombs = 0;
        } else {
            _timeBetweenPutBombs--;
        }

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive) {
            chooseSprite();
        } else {
            _sprite = Sprite.player_dead1;
        }

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt
     * bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím đi�?u khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có th�?a mãn hay không
        // TODO:  Game.getBombRate() sẽ trả v�? số lượng bom có thể đặt liên tiếp tại th�?i điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng th�?i gian quá ngắn
        // TODO: nếu 3 đi�?u kiện trên th�?a mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs v�? 0
        if (_input.space && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {

            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile((_y + _sprite.getSize() / 2) - _sprite.getSize()); //subtract half player height and minus 1 y position

            placeBomb(xt, yt);
            Game.addBombRate(-1);

            _timeBetweenPutBombs = 30;
        }
    }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb b = new Bomb(x, y, _board);
        _board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) {
            return;
        }
        _alive = false;
        ListSound.music.stop();
        Sound die = new Sound("res\\music\\die_laugh.wav");
        die.play();
        
    }

    @Override
    protected void afterKill() {
       if (_timeAfter > 0) {
            --_timeAfter;
        } else {
            _board.endGame();
        }
  
    }

    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu đi�?u khiển hướng đi từ _input và g�?i move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị c�? _moving khi thay đổi trạng thái di chuyển
        int xa = 0, ya = 0;
        if (_input.up) {
            ya--;
        }
        if (_input.down) {
            ya++;
        }
        if (_input.left) {
            xa--;
        }
        if (_input.right) {
            xa++;
        }

        if (xa != 0 || ya != 0) {
            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            _moving = true;
        } else {
            _moving = false;
        }

    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
            double xt = ((_x + x) + c % 2 * 11) / Game.TILES_SIZE; //divide with tiles size to pass to tile coordinate
            double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE; //these values are the best from multiple tests

            Entity a = _board.getEntity(xt, yt, this);

            if (!a.collide(this)) {
                return false;
            }
        }

        return true;
// TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
//        return false;
    }

    @Override
    public void move(double xa, double ya) {
        if(xa > 0) _direction = 1;
		if(xa < 0) _direction = 3;
		if(ya > 0) _direction = 2;
		if(ya < 0) _direction = 0;
		
		if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
			_y += ya;
		}
		
		if(canMove(xa, 0)) {
			_x += xa;
		}
    }

    @Override
    public boolean collide(Entity e) {
       if(e instanceof Flame) {
			kill();
			return false;
		}
		
		if(e instanceof Enemy) {
			kill();
			return false;
		}
		
		return true;

     
    }

    /*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
//	public void addItem(Item p) {
//		if(p.isRemoved()) return;
//		
//		_powerups.add(p);
//		
//		p.setValues();
//	}
//	
//	public void clearUsedPowerups() {
//		Powerup p;
//		for (int i = 0; i < _powerups.size(); i++) {
//			p = _powerups.get(i);
//			if(p.isActive() == false)
//				_powerups.remove(i);
//		}
//	}
//	
//	public void removePowerups() {
//		for (int i = 0; i < _powerups.size(); i++) {
//				_powerups.remove(i);
//		}
//	}
	
   
    private void chooseSprite() {
        switch(_direction) {
		case 0:
			_sprite = Sprite.player_up;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
			}
			break;
		case 1:
			_sprite = Sprite.player_right;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
			}
			break;
		case 2:
			_sprite = Sprite.player_down;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
			}
			break;
		case 3:
			_sprite = Sprite.player_left;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
			}
			break;
                        
		default:
			_sprite = Sprite.player_right;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
			}
			break;
		}
	}
     public void addItem(Item aThis) {
        if (aThis.isRemoved()) {
            return;
        }
        _items.add(aThis);
        aThis.setValues();
    }
    
     public void clearUsedPowerups() {
        for (int i = 0; i < _items.size(); i++) {
            if (!_items.get(i).isActive()) {
                _items.remove(i);
                i--;
            }
        }
    }

    public void removePowerups() {
        for (int i = 0; i < _items.size(); i++) {
            _items.remove(i);
            i--;
        }
    }
}

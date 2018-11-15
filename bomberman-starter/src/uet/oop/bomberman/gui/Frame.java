package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import uet.oop.bomberman.exceptions.GameException;
import uet.oop.bomberman.music.Sound;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {
	
	public GamePanel _gamepane;
	private JPanel _containerpane;
	private InfoPanel _infopanel;
	
	private Game _game;

	public Frame()  {
		         
		_containerpane = new JPanel(new BorderLayout());
            try {
                _gamepane = new GamePanel(this);
            } catch (GameException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
		_infopanel = new InfoPanel(_gamepane.getGame());
		
		_containerpane.add(_infopanel, BorderLayout.PAGE_START);
		_containerpane.add(_gamepane, BorderLayout.PAGE_END);
		
		_game = _gamepane.getGame();
		
		add(_containerpane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		_game.start();
	}
	
	
	
	public void setTime(int time) {
		_infopanel.setTime(time);
	}
	
	
	public void setPoints(int points) {
		_infopanel.setPoints(points);
	}
	
//	public boolean validCode(String str) {
//		return _game.getBoard().getLevel().validCode(str) != -1;
//	}
//	
//	public void changeLevelByCode(String str) {
//		_game.getBoard().changeLevelByCode(str);
//	}
	
}

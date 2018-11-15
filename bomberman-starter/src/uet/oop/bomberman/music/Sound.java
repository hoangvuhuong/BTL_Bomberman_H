/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.music;

/**
 *
 * @author Hoang Vu Huong
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/*///
Tran huy
 * Mot tien ich cua congdongjava.com
*/

//public class Sound extends Thread {
//
//    private String filename;
//
//    private Position curPosition;
//
//    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
//
//    enum Position {
//        LEFT, RIGHT, NORMAL
//    };
//
//    public Sound(String wavfile) {
//        filename = wavfile;
//        curPosition = Position.NORMAL;
//    }
//
//    public Sound(String wavfile, Position p) {
//        filename = wavfile;
//        curPosition = p;
//    }
//
//    public void run() {
//
//        while(true)
//        {
//        File soundFile = new File(filename);
//        if (!soundFile.exists()) {
//            System.err.println("Wave file not found: " + filename);
//            return;
//        }
//
//        AudioInputStream audioInputStream = null;
//        try {
//            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
//        } catch (UnsupportedAudioFileException e1) {
//            e1.printStackTrace();
//            return;
//        } catch (IOException e1) {
//            e1.printStackTrace();
//            return;
//        }
//
//        AudioFormat format = audioInputStream.getFormat();
//        SourceDataLine auline = null;
//        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//
//        try {
//            auline = (SourceDataLine) AudioSystem.getLine(info);
//            auline.open(format);
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        if (auline.isControlSupported(FloatControl.Type.PAN)) {
//            FloatControl pan = (FloatControl) auline
//                    .getControl(FloatControl.Type.PAN);
//            if (curPosition == Position.RIGHT)
//                pan.setValue(1.0f);
//            else if (curPosition == Position.LEFT)
//                pan.setValue(-1.0f);
//        }
//
//        auline.start();
//        int nBytesRead = 0;
//        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
//
//        try {
//            while (nBytesRead != -1) {
//                nBytesRead = audioInputStream.read(abData, 0, abData.length);
//                if (nBytesRead >= 0)
//                    auline.write(abData, 0, nBytesRead);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        } finally {
//            auline.drain();
//            auline.close();
//        }
//        }
//    }
//}







import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.JFrame;

public class Sound extends JFrame {
  AudioClip click;

  public static void main(String[] args) {
    new Sound();
  }

  public Sound() {
    this.setSize(400, 400);
    this.setTitle("Mouse Clicker");
    this.addMouseListener(new Clicker());

    URL urlClick = Sound.class.getResource("C:\\Users\\Hoang Vu Huong\\Desktop\\Bom\\bomberman-starter\\Music\\Bomd.wav");
    click = Applet.newAudioClip(urlClick);

    this.setVisible(true);
  }

  private class Clicker extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
      click.play();
    }
  }
}
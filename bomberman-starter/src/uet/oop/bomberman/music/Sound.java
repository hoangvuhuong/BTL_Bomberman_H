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
import java.io.File;
import javax.sound.sampled.*;
public class Sound{
   public  File clap;
   Clip clip;
    public Sound(String name_file){
         clap = new File(name_file);
        
    }
    public void play(){
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(clap));
            clip.start();
            
           // Thread.sleep(clip.getMicrosecondLength()/10000);
        } catch (Exception e) {
        }
    }
    public void stop(){
        clip.stop();
        
    }
    public boolean isRun(){
        return clip.isRunning();
    }
    
}
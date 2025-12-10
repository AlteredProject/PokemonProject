package main;

import javax.sound.sampled.*;
import java.net.URL;

import entity.Player;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    Player player;
    GamePanel gp;
    int musicZone = 0;      // Default twin leaf

    // ===== Fade variables =====
    float volume = -10f;           // current volume
    float targetVolume = -10f;     // Target fade
    float fadeSpeed = 1.0f;        // Fade speed
    boolean isFading = false;
    FloatControl gainControl;

    // ===== Collision cooldown =====
    private long lastCollisionSoundTime = 0;
    private final long collisionCooldown = 800; // ms

    // ============================
    // MUSIC ZONE HANDLING
    // ============================
    public int getMusicZone() {
        int x = player.worldX / gp.tileSize;
        int y = player.worldY / gp.tileSize;

        if (x == 19 && y == 52) return 2; // Lab
        if (x <= 27 && y >= 38 && y <= 56) return 0; // twinleaf
        if (x > 27 && x <= 55 && y >= 14 && y <= 64) return 1; // route 1
        if (x > 55 && x <= 70 && y >= 14 && y <= 64) return 1; // route 1
        else return 3;
    }

    public void updateMusic() {
        int newZone = getMusicZone();

        if (newZone != musicZone) {
            musicZone = newZone;
            stop();
            setFile();
            play();
            loop();
        }
    }

    // ============================
    //     MUSIC PLAYBACK
    // ============================
    public Sound(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        soundURL[0] = getClass().getResource("/sound/TwinleafTown.wav");
        soundURL[1] = getClass().getResource("/sound/Route201.wav");
        soundURL[2] = getClass().getResource("/sound/Lab.wav");
        soundURL[3] = getClass().getResource("/sound/Lake.wav");
        soundURL[4] = getClass().getResource("/sound/FloaromaTown.wav");
        soundURL[20] = getClass().getResource("/sound/collision.wav");
        soundURL[21] = getClass().getResource("/sound/button.wav");

    }

    public void setFile() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[musicZone]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PlayCollisionSound() {
        long now = System.currentTimeMillis();
        if (now - lastCollisionSoundTime < collisionCooldown) return;
        lastCollisionSoundTime = now;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[20]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            playEffect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playButtonSound() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[21]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            if (gp.keyH.enterPressed) {
                playEffect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playEffect() {
        clip.start();
    }


    public void play() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    // ============================
    //         FADE UPDATE
    // ============================


    /*public void updateFade() {
        if (!isFading || gainControl == null) return;

        if (Math.abs(volume - targetVolume) > 0.5f) {

            if (volume < targetVolume)
                volume += fadeSpeed;       // fade IN
            else
                volume -= fadeSpeed;       // fade OUT

            gainControl.setValue(volume);

        } else {
            // Fade completed
            volume = targetVolume;
            gainControl.setValue(volume);
            isFading = false;
        }
    }

    public void fadeIn() {
        volume = -40f;
        targetVolume = -10f;
        fadeSpeed = 1.0f;
        isFading = true;

        gainControl.setValue(volume);

    }

    public void fadeOut() {
        volume = -10f;
        targetVolume = -40f;
        fadeSpeed = 1.0f;
        isFading = true;
    }

     */

}

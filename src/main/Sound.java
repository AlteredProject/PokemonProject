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
    private final long collisionCooldown = 400; // ms

    // ============================
    // MUSIC ZONE HANDLING
    // ============================
    public int getMusicZone() {
        int x = (player.worldX / gp.tileSize) + 1;
        int y = (player.worldY / gp.tileSize) + 1;

        if (x > 42 && x <= 71 && y >= 5 && y <= 22) return 4;// floaroma town
        else if (x>=49 && x <= 55 && y>=62 && y <=66) return 6; // route 203 extra corner
        else if (x == 18 && y == 85) return 10; // PokeMart
        else if (x == 11 && y == 85) return 9; // PokeCenter
        else if (x == 11 && y == 76) return 11; // BattleArena
        else if (x >= 38 && x < 94 && y >= 83 && y <= 94) return 8; // valley
        else if (x <= 38 && y >= 62 && y <= 91) return 7; // big town
        else if (x >= 27 && x <= 54 && y >= 17 && y <= 65) return 1; // route 1
        else if (x >= 40 && x <= 92 && y >= 55 && y <= 73) return 6; // route 203
        else if (x == 12 && y == 39) return 2; // Lab
        else if (x <= 27 && y >= 38 && y <= 57) return 0; // twinleaf
        else if (x <= 42 && y < 34) return 3; // lake
        else if (x >= 71 && x <= 92 && y >= 7 && y <= 54) return 5; // forest





        else return 1;
    }

    public void updateMusic() {
        int newZone = getMusicZone();

        if (newZone != musicZone) {
            musicZone = newZone;
            stop();
            setFile();
            play();
            loop();
            //fadeIn();
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
        soundURL[5] = getClass().getResource("/sound/EternaForest.wav");
        soundURL[6] = getClass().getResource("/sound/Route203.wav");
        soundURL[7] = getClass().getResource("/sound/SolaceonTown.wav");
        soundURL[8] = getClass().getResource("/sound/Mt.Coronet.wav");
        soundURL[9] = getClass().getResource("/sound/PokeCenter.wav");
        soundURL[10] = getClass().getResource("/sound/PokeMart.wav");
        soundURL[11] = getClass().getResource("/sound/BattleTheme.wav");
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


    public void updateFade() {
        if (!isFading || gainControl == null) return;

        if (Math.abs(volume - targetVolume) > 0.5f) {

            if (volume < targetVolume)
                volume += fadeSpeed;       // fade IN
            else
                volume -= fadeSpeed;

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
        fadeSpeed = 0.2f;
        isFading = true;

        gainControl.setValue(volume);

    }

    public void fadeOut() {
        volume = -10f;
        targetVolume = -40f;
        fadeSpeed = 0.2f;
        isFading = true;
    }


}

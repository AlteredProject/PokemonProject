package main;

import javax.sound.sampled.*;
import java.net.URL;

import entity.Player;

public class Sound {
    Clip musicClip;
    Clip sfxClip;
    URL[] soundURL = new URL[42];
    Player player;
    GamePanel gp;
    int musicZone = 0;      // Default twin leaf

    // ===== Fade variables =====
    float volume = -10f;           // current volume
    float targetVolume = -10f;     // Target fade
    float fadeSpeed = 1.0f;        // Fade speed
    boolean fadingIn = false;
    boolean fadingOut = false;
    FloatControl gainControl;

    // ===== Collision cooldown =====
    private long lastCollisionSoundTime = 0;
    private final long collisionCooldown = 400; // ms

    // ===== Button cooldown =====
    private long lastButtonSound = 0;
    private final long buttonCooldown = 400; // ms

    // ============================
    // MUSIC ZONE HANDLING
    // ============================
    public int getMusicZone() {
        int x = (player.worldX / gp.tileSize) + 1;
        int y = (player.worldY / gp.tileSize) + 1;

        if (x > 42 && x <= 71 && y >= 5 && y <= 22) return 4;// floaroma town
        else if (x>=49 && x <= 55 && y>=62 && y <=66) return 6; // route 202 extra corner
        else if (x == 18 && y == 85) return 10; // PokeMart
        else if (x == 11 && y == 85) return 9; // PokeCenter
        else if (x == 11 && y == 76) return 11; // BattleArena
        else if (x >= 38 && x < 94 && y >= 83 && y <= 94) return 8; // valley
        else if (x <= 38 && y >= 62 && y <= 91) return 7; // big town
        else if (x >= 27 && x <= 54 && y >= 17 && y <= 65) return 1; // route 201
        else if (x >= 40 && x <= 92 && y >= 55 && y <= 73) return 6; // route 202
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
            fadeOut();
        }

        if (gp.gameState == gp.battleState) {
            stopMusic();
            musicZone = 11;
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
        soundURL[5] = getClass().getResource("/sound/EternaForest.wav");
        soundURL[6] = getClass().getResource("/sound/Route203.wav");
        soundURL[7] = getClass().getResource("/sound/SolaceonTown.wav");
        soundURL[8] = getClass().getResource("/sound/Mt.Coronet.wav");
        soundURL[9] = getClass().getResource("/sound/PokeCenter.wav");
        soundURL[10] = getClass().getResource("/sound/PokeMart.wav");
        soundURL[11] = getClass().getResource("/sound/BattleTheme.wav");
        soundURL[20] = getClass().getResource("/sound/collision.wav");
        soundURL[21] = getClass().getResource("/sound/button.wav");
        soundURL[22] = getClass().getResource("/sound/MachopCry.wav");

        // Starter cries
        soundURL[23] = getClass().getResource("/sound/turtwigCry.wav");
        soundURL[24] = getClass().getResource("/sound/ChimcharCry .wav");
        soundURL[25] = getClass().getResource("/sound/piplupCry.wav");

        // Battle sounds
        soundURL[26] = getClass().getResource("/sound/potion.wav");
        soundURL[27] = getClass().getResource("/sound/flee.wav");

        // Battle moves
        soundURL[28] = getClass().getResource("/sound/bite.wav");
        soundURL[29] = getClass().getResource("/sound/bubblebeam.wav");
        soundURL[30] = getClass().getResource("/sound/Ember.wav");
        soundURL[31] = getClass().getResource("/sound/flameWheel.wav");
        soundURL[32] = getClass().getResource("/sound/hit.wav");
        soundURL[33] = getClass().getResource("/sound/leer.wav");
        soundURL[34] = getClass().getResource("/sound/machPunch.wav");
        soundURL[35] = getClass().getResource("/sound/peck.wav");
        soundURL[36] = getClass().getResource("/sound/pound.wav");
        soundURL[37] = getClass().getResource("/sound/razorLeaf.wav");
        soundURL[38] = getClass().getResource("/sound/tackle.wav");
        soundURL[39] = getClass().getResource("/sound/thunderbolt.wav");

        soundURL[40] = getClass().getResource("/sound/titleTheme.wav");

        soundURL[41] = getClass().getResource("/sound/grassStep.wav");





    }

    public void setFile() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[musicZone]);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
            gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PlayCollisionSound() {
        long now = System.currentTimeMillis();
        if (now - lastButtonSound < buttonCooldown) return;
        lastButtonSound = now;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[20]);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(ais);
            playEffect(-10f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playGrassStep() {
        long now = System.currentTimeMillis();
        if (now - lastButtonSound < buttonCooldown) return;
        lastButtonSound = now;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[41]);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(ais);
            playEffect(-1f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playButtonSound() {
        long now = System.currentTimeMillis();
        if (now - lastCollisionSoundTime < collisionCooldown) return;
        lastCollisionSoundTime = now;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[21]);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(ais);
            if (gp.keyH.enterPressed) {
                playEffect(-15f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void machopSound() {
        long now = System.currentTimeMillis();
        if (now - lastCollisionSoundTime < collisionCooldown) return;
        lastCollisionSoundTime = now;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[22]);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(ais);
            playEffect(-5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void playEffect(float volume) {
        gainControl = (FloatControl) sfxClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
        sfxClip.start();
    }

    public void playSound(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            sfxClip = AudioSystem.getClip();
            sfxClip.open(ais);
            playEffect(-10f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void play() {
        musicClip.start();
    }

    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }

    public void stopSound() {
        if (sfxClip != null) {
            sfxClip.stop();
            sfxClip.close();
        }
    }


    // ============================
    //         FADE UPDATE
    // ============================
    public void updateFade() {
        // Fade OUT
        if (fadingOut) {
            if (volume > targetVolume) {
                volume -= fadeSpeed;
                gainControl.setValue(volume);
            } else {
                volume = targetVolume;
                gainControl.setValue(volume);
                stopMusic();
                stopSound();
                fadingOut = false;
                setFile();
                fadeIn();
            }
        }
        // Fade IN
        else if (fadingIn) {
            if (volume < targetVolume) {
                volume += fadeSpeed;
                gainControl.setValue(volume);
            } else {
                volume = targetVolume;
                gainControl.setValue(volume);
                fadingIn = false;
            }
        }
    }

    public void fadeIn() {
        fadingIn = true;
        fadingOut = false;

        volume = -40f;
        targetVolume = -10f;
        fadeSpeed = 0.3f;

        gainControl.setValue(volume);
        play();
        loop();
    }

    public void fadeOut() {
        fadingOut = true;
        fadingIn = false;

        volume = -10f;
        targetVolume = -40f;
        fadeSpeed = 0.2f;

        gainControl.setValue(volume);
    }
}

package dev.midnightcoder.rpg.assets.audio;

import dev.midnightcoder.engine.audio.Audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public class MusicTrack extends Audio {

    @Override
    public void loadAudioFiles() {
        if (sources == null)
            sources = new ArrayList<>();

        if (!sources.isEmpty())
            sources.clear();

        var theme = getTrackByName("midnight-theme");
        var tutorialBliss = getTrackByName("tutorial-bliss");

        sources.add(theme);
        sources.add(tutorialBliss);
    }

    @Override
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void play() {
        clip.start();
    }

    @Override
    public void stop() {
        clip.stop();
    }

    public void setTrack(int index) {
        if (index < 0 || index >= sources.size())
            throw new IllegalArgumentException("Invalid track index: " + index);
        try {
            var ais = AudioSystem.getAudioInputStream(sources.get(index));
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load track: " + sources.get(index), e);
        }
    }

    private InputStream getTrackByName(String name) {
        try {
            var track = getClass().getResourceAsStream("/audio/track/%s.wav".formatted(name));
            if (track == null)
                throw new IllegalArgumentException("Track not found: " + name);
            return track;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load track: " + name, e);
        }
    }
}

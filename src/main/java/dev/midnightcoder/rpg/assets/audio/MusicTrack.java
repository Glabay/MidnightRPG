package dev.midnightcoder.rpg.assets.audio;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.engine.audio.Audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;
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

        var audioDefs = CacheReader.getInstance().getCacheManager().getAudio();

        audioDefs.forEach(def -> {
            var track = (InputStream) new ByteArrayInputStream(def.getDecompressedData());
            sources.add(def.getId(), track);
        });
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

    public void setTrack(String trackName) {
        try {
            var ais = AudioSystem.getAudioInputStream(getTrackByName(trackName));
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load track: " + trackName, e);
        }
    }

    private InputStream getTrackByName(String name) {
        return CacheReader.getInstance().getAudioDefinition(name)
                .map(def -> (InputStream) new ByteArrayInputStream(def.getDecompressedData()))
                .orElseGet(() -> {
                    var track = getClass().getResourceAsStream("/audio/track/%s.wav".formatted(name));
                    if (track == null)
                        throw new IllegalArgumentException("Track not found in cache or resources: " + name);
                    return track;
                });
    }
}

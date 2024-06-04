package com.jordanbunke.anim.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class AnimBuilder {
    private int impWidth, impHeight;
    private final List<AnimFrame> frames;

    public AnimBuilder() {
        frames = new ArrayList<>();
    }

    public Animation build() {
        if (frames.isEmpty()) return null;

        return new Animation(impWidth, impHeight,
                frames.toArray(AnimFrame[]::new));
    }

    public AnimBuilder addFrame(final AnimFrame frame) {
        if (frames.isEmpty()) {
            impWidth = frame.img().getWidth();
            impHeight = frame.img().getHeight();
        }

        frames.add(frame);

        return this;
    }

    public AnimBuilder addFrame(final BufferedImage img, final int durationMillis) {
        return addFrame(new AnimFrame(img, durationMillis));
    }
}

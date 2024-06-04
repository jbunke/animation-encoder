package com.jordanbunke.anim.data;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public record Animation(int width, int height, AnimFrame... frames) {
    public int durationMillis() {
        return Arrays.stream(frames)
                .map(AnimFrame::durationMillis)
                .reduce(0, Integer::sum);
    }

    public static Animation makeUniform(
            final int frameDurationMillis, final BufferedImage... imgs
    ) {
        if (imgs.length == 0) return null;

        final int width = imgs[0].getWidth(), height = imgs[0].getHeight();

        final AnimFrame[] frames = Arrays.stream(imgs)
                .map(img -> new AnimFrame(img, frameDurationMillis))
                .toArray(AnimFrame[]::new);

        return new Animation(width, height, frames);
    }
}

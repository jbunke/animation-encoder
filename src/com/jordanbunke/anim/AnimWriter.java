package com.jordanbunke.anim;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public interface AnimWriter {
    void write(
            final Path filepath, final BufferedImage[] images,
            final int intervalMillis
    );
}

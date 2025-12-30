package com.jordanbunke.anim.writers;

import com.jordanbunke.anim.data.AnimFrame;
import com.jordanbunke.anim.data.Animation;
import com.madgag.gif.fmsware.AnimatedGifEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public final class GIFWriter implements AnimWriter {
    private static final GIFWriter INSTANCE;

    private static final Color TP_STAND_IN = new Color(0, 255, 0, 0);

    static {
        INSTANCE = new GIFWriter();
    }

    private GIFWriter() {
    }

    public static GIFWriter get() {
        return INSTANCE;
    }

    @Override
    public void write(final Path filepath, final Animation animation) {
        final AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();

        try (final FileOutputStream outputStream = new FileOutputStream(filepath.toFile())) {
            gifEncoder.start(outputStream);
            gifEncoder.setTransparent(TP_STAND_IN, true);
            gifEncoder.setRepeat(0);
            gifEncoder.setQuality(1);
            gifEncoder.setSize(animation.width(), animation.height());

            for (AnimFrame frame : animation.frames()) {
                uniformTransparency(frame.img());
                gifEncoder.setDelay(frame.durationMillis());
                gifEncoder.addFrame(frame.img());
            }

            gifEncoder.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void uniformTransparency(final BufferedImage img) {
        final int w = img.getWidth(), h = img.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final Color px = new Color(img.getRGB(x, y), true);

                if (px.getAlpha() == 0)
                    img.setRGB(x, y, TP_STAND_IN.getRGB());
            }
        }
    }

    @Override
    public String fileSuffix() {
        return ".gif";
    }
}

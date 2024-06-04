package com.jordanbunke.anim.writers;

import com.jordanbunke.anim.data.AnimFrame;
import com.jordanbunke.anim.data.Animation;
import com.squareup.gifencoder.FloydSteinbergDitherer;
import com.squareup.gifencoder.GifEncoder;
import com.squareup.gifencoder.ImageOptions;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public final class GIFWriter implements AnimWriter {
    private static final GIFWriter INSTANCE;

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
        write(filepath, animation, 0);
    }

    public void write(
            final Path filepath, final Animation animation, final int reps
    ) {
        try (final FileOutputStream outputStream = new FileOutputStream(filepath.toFile())) {
            if (animation.frames().length == 0)
                return;

            final GifEncoder gifEncoder = new GifEncoder(outputStream,
                    animation.width(), animation.height(), reps);
            final ImageOptions options = new ImageOptions()
                    .setDitherer(FloydSteinbergDitherer.INSTANCE);

            for (AnimFrame frame : animation.frames()) {
                options.setDelay(frame.durationMillis(), TimeUnit.MILLISECONDS);
                gifEncoder.addImage(convertImageToArray(frame.img()), options);
            }

            gifEncoder.finishEncoding();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String fileSuffix() {
        return ".gif";
    }

    private static int[][] convertImageToArray(final BufferedImage image) {
        final int[][] colors = new int[image.getHeight()][image.getWidth()];

        for (int y = 0; y < colors.length; y++) {
            for (int x = 0; x < colors[y].length; x++) {
                colors[y][x] = image.getRGB(x, y);
            }
        }

        return colors;
    }
}

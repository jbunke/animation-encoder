package com.jordanbunke.anim;

import com.squareup.gifencoder.FloydSteinbergDitherer;
import com.squareup.gifencoder.GifEncoder;
import com.squareup.gifencoder.ImageOptions;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class GIFWriter implements AnimWriter {
    private static final GIFWriter INSTANCE;

    static {
        INSTANCE = new GIFWriter();
    }

    private GIFWriter() {

    }

    public static GIFWriter get() {
        return INSTANCE;
    }

    public void write(
            final Path filepath, final BufferedImage[] images,
            final int intervalMillis, final int repetitions
    ) {
        try (final FileOutputStream outputStream = new FileOutputStream(filepath.toFile())) {
            if (images.length == 0)
                return;

            final int width = images[0].getWidth(), height = images[0].getHeight();

            final GifEncoder gifEncoder = new GifEncoder(outputStream,
                    width, height, repetitions);
            final ImageOptions options = new ImageOptions()
                    .setDelay(intervalMillis, TimeUnit.MILLISECONDS)
                    .setDitherer(FloydSteinbergDitherer.INSTANCE);

            for (BufferedImage image : images)
                gifEncoder.addImage(convertImageToArray(image), options);

            gifEncoder.finishEncoding();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(
            final Path filepath, final BufferedImage[] images,
            final int intervalMillis
    ) {
        write(filepath, images, intervalMillis, 0);
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

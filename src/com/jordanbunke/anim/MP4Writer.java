package com.jordanbunke.anim;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class MP4Writer implements AnimWriter {
    private static final MP4Writer INSTANCE;

    private MP4Writer() {

    }

    static {
        INSTANCE = new MP4Writer();
    }

    public static MP4Writer get() {
        return INSTANCE;
    }

    @Override
    public void write(final Path filepath, final BufferedImage[] images, final int intervalMillis) {
        final int MILLIS_IN_SECOND = 1000;

        SeekableByteChannel writeTo = null;

        try {
            writeTo = NIOUtils.writableChannel(filepath.toFile());
            final AWTSequenceEncoder encoder = new AWTSequenceEncoder(
                    writeTo, Rational.R(MILLIS_IN_SECOND, intervalMillis));

            for (BufferedImage image : images)
                encoder.encodeImage(image);

            encoder.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            NIOUtils.closeQuietly(writeTo);
        }
    }

    @Override
    public String fileSuffix() {
        return ".mp4";
    }
}

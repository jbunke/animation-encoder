package com.jordanbunke.anim.writers;

import com.jordanbunke.anim.data.AnimFrame;
import com.jordanbunke.anim.data.Animation;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public final class MP4Writer implements AnimWriter {
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
    public void write(final Path filepath, final Animation animation) {
        final int MILLIS_IN_SECOND = 1000, INVALID = -1,
                gcf = Arrays.stream(animation.frames())
                        .map(AnimFrame::durationMillis)
                        .reduce(this::gcf).orElse(INVALID);

        if (gcf == INVALID)
            return;

        SeekableByteChannel writeTo = null;

        try {
            writeTo = NIOUtils.writableChannel(filepath.toFile());
            final AWTSequenceEncoder encoder = new AWTSequenceEncoder(
                    writeTo, Rational.R(MILLIS_IN_SECOND, gcf));

            for (AnimFrame frame : animation.frames()) {
                final int shownFor = frame.durationMillis() / gcf;

                for (int i = 0; i < shownFor; i++)
                    encoder.encodeImage(frame.img());
            }

            encoder.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            NIOUtils.closeQuietly(writeTo);
        }
    }

    private int gcf(final int a, final int b) {
        return b == 0 ? a : gcf(b, a % b);
    }

    @Override
    public String fileSuffix() {
        return ".mp4";
    }
}

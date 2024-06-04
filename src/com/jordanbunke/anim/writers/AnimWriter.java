package com.jordanbunke.anim.writers;

import com.jordanbunke.anim.data.Animation;

import java.nio.file.Path;

public interface AnimWriter {
    void write(final Path filepath, final Animation animation);

    String fileSuffix();
}

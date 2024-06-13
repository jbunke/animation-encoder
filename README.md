# Animation Encoder

*Animation Encoder* is a simple Java wrapper library that is designed to turn the exporting of a series of images ([`java.awt.image.BufferedImage`](https://docs.oracle.com/en/java/javase/17/docs//api/java.desktop/java/awt/image/BufferedImage.html)) to various animation file formats into a one-line task.

### Export formats

This is a list of currently supported export formats and their underlying libraries:

* **GIF**: [gifencoder](https://github.com/square/gifencoder)
* **MP4**: [jcodec](https://github.com/jcodec/jcodec)

### Requisite dependency in my other programs

You may be interested in *Animation Encoder* for its use as an external dependency in my other programs. You can download the JAR file needed to compile [*Stipple Effect*](https://github.com/jbunke/stipple-effect) and other programs [here](https://github.com/jbunke/animation-encoder/releases/tag/auxiliary).

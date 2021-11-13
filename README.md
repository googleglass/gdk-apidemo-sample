ApiDemo
=======

This sample is a gallery of some GDK APIs usage:

- Card and CardScrollView
- GestureDetector
- Theming with textAppearance
- OpenGL in a LiveCard

## Getting started

Check out our documentation to learn how to get started on
https://developers.google.com/glass/gdk/index

## Running the sample on Glass

You can use your IDE to compile and install the sample or use
[`adb`](https://developer.android.com/tools/help/adb.html)
on the command line:

    $ adb install -r ApiDemo.apk

To start the sample, say "ok glass, show me a demo" from the Glass clock
screen or use the touch menu.

## Notices for Linux users

The Android SDK build tools ship outdated 32bit binaries in version
20.0.0. Users of modern Linux kernels in 64bit mode might need to install
additional libraries to run the build tools. In particular, this is:

- `util-linux` (32bit)
- `clang` (32bit)
- `zstd` (32bit)
- `zlib` (32bit)


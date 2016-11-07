# BlinkStick for Java

Java library to control [BlinkStick](http://www.blinkstick.com) devices via USB.

The API is based on the the [BlinkStick Android](https://github.com/arvydas/blinkstick-android/) and [BlinkStick for Processing](https://github.com/arvydas/blinkstick-processing) libraries by [arvydas](https://github.com/arvydas).


## Connecting the BlinkStick

### Mac OS

Goto `About this Mac` > `System Report...` then find the `USB` section under `Hardware`. When you plug the stick in, you should see something like the following.

If you've checked out the code, you can run the `BlinkStickInfo` application from your IDE.

If you have the `blinkstick` command line application installed, you can also run `blinkstick --info` to check the output.

> I've found it isn't always recognised and removing and re-inserting doesn't always trigger it to be recognised. I haven't found a reliable way to force the stick to be recognised. I'm testing with the BlinkStick square.


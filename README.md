# Haptic Midi Controller

Controls multiple [DRV2605](http://www.ti.com/lit/ds/symlink/drv2605.pdf) Haptic Drivers via [I²C](https://en.wikipedia.org/wiki/I%C2%B2C) protocol using midi files.

## Mapping
Midi notes read from a midi file are mapped to 8 DR2605 drivers using a [TCA9548A](http://www.ti.com/lit/ds/symlink/tca9548a.pdf) switch.
The midi notes velocity is used as the drivers amplitude.

| Midi Note | Note velocity |    |  Driver | Amplitude |
|-----------|---------------|----|---------|-----------|
|     0     |       1       | -> |  0x01   |     1     |
|     1     |       10      | -> |  0x02   |     10    |
|     3     |      100      | -> |  0x03   |    100    |
|     ...   |      ...      | -> |  ...    |    ...    |


## Hardware Setup
   * [Raspberry Pi 4 Model B](https://www.raspberrypi.org/)
   * DRV2605 Haptic Motor Driver
   * Multiplexer TCA9548A1
   * ERM or LRA Vibration Motor
   
   Connecting the Multiplexer to the Raspberry Pi via GPIO (Vin/Ground/SDA/SCL). On the multiplexer use SDA/SCA 0-7 to connect up to 8 DRV2605L. You need one DRV2605 for each motor, since they all have the same adress 0x5A. 5V or 3.3V (both can be used, but has impact on Motor Strength) Connection and Ground from the Raspberry Pi can be in serial and soldered together. 
   
   ERM Motor is cheap but less precise. To accomplish good haptic Effects you need LRAs. Mode for each, LRA and ERM, is planned to be adjustable in the software.
## How to use (instructions)
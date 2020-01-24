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
   
   
## How to use (instructions)
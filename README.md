# Haptic Midi Controller

Controls multiple [DRV2605](http://www.ti.com/lit/ds/symlink/drv2605.pdf) Haptic Drivers via [I²C](https://en.wikipedia.org/wiki/I%C2%B2C) protocol using midi files.

## Mapping
Midi notes read from a midi file are mapped to 8 DR2605 drivers using a [TCA9548A](http://www.ti.com/lit/ds/symlink/tca9548a.pdf) switch.
The midi notes velocity is used as the drivers amplitude.

### Mapping Table
| Midi Note | Note velocity |    |  Driver | Amplitude |
|-----------|---------------|----|---------|-----------|
|     0     |       1       | -> |  0x01   |     1     |
|     1     |       10      | -> |  0x02   |     10    |
|     3     |      100      | -> |  0x03   |    100    |
|     ...   |      ...      | -> |  ...    |    ...    |


## Hardware Setup
   
   
## How to use (Instructions)

A [Makefile](https://opensource.com/article/18/8/what-how-makefile) for basic usage is available ([Maven Wrapper](https://github.com/takari/maven-wrapper) is included). 

Install dependencies with

`make build`

Start the program with. Lets you select a midi file from the midi folder.

`make start-midi`

Start the program without any I²C devices connected (only reads and logs the selected midi file).

`start-log`

Prints help to the console.

`make help`
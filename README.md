# Lethal Metal

Lethal Metal is a jump'n'run (platform) game written in Java and J2ME, targeting Nokia Series 40 and other J2ME-enabled cellphones. The gameplay resembles Blizzard Black Thorne, with a balanced mixture of puzzles, combat and story elements.

The purpose was to develop a commercial quality J2ME game, a platform game engine and a set of tools (map editor, sprite editor), having in mind the specifics of cellphone platforms: small package size (64kb) and small heap memory (200kb).

This is a [mirror of the project released on SourceForge](https://sourceforge.net/projects/lethalmetal/) in 2005.

## History
Lethal Metal project began in march 2003 as a commercial project under the code-name Cyberninja. The game was developed in the spare time, from scratch, in less than eight months. A playable mockup was available in august 2003, the graphics were created in autumn 2003 and a fully playable version was available in march 2004. It was pitched for a possible investor around that time, but it failed to secure funding.

The project then gathered dust as the lead game designer lost interest (unfortunately) and was ressurected in order to release it as opensource in 2005.

## Features

Brief list of engine capabilities:
* player character with fluid animations (over 70 animation frames, including running, jumping, crouching, climbing ladders and edges, firing, hiding)
* 3 types of enemies, with the possibility to easily remove or add, depending on the phone capabilities (jar size, heap)
* AI for the enemies, based on state machines
* powerup items (health boxes, ammo)
* traps, elevators, elevator terminals, triggers
* various props
* eye-candy physics (not extremely realistic, but fun and easy to tweak)
* support to create basic narrative
* localization
* map and sprite editors

## Setup&Build&Run

**Note that the SDKs required to build are no longer available, obsolete and/or may only work on Windows XP.**

In order to compile the project, you need to:
* download and install the Nokia 3510i SDK from http://www.forum.nokia.com/main/0,,034-10,00.html (24Mb)
  (you will need to register on Nokia Forum site to obtain the serial number for the SDK)
* download and install the Sun WTK 2.1 from http://java.sun.com/products/j2mewtoolkit/download-2_1.html (16Mb)
* download and install the JDK from http://java.sun.com/j2se/1.5.0/download.jsp (55Mb)
* go to ${HOME}/dev/ant/Nokia3510i_opt and open build.xml; modify SDK_HOME.dir to point to the Nokia 3510i SDK path
  and wtk.home to point to the Sun WTK path
* go to ${HOME} and open build.bat, modify the JAVA_HOME to point to the JDK location
* open a console and change directory to ${HOME}
* type build run Nokia3510i_opt LethalMetal EN
* enjoy! :)

Builds may be run in an emulator, most phone SDKs provide one. 

To upload to device, you will need a data cable + phone manager software or a WAP connection.

In order to compile the project specific tools, you will need the Borland C++Builder 6 (version 5 might work as well). 

Most recent builds of the tools may be found at [Piron Games GameToolkit released page](https://github.com/stefandee/gametoolkit/releases)

## Known Issues/Limitations

For the moment, Lethal Metal has only one version, the master version, for Nokia 3510i. The version is also tuned for the 3510i screen resolution, 96x65. It may be possible to run it on other Nokia devices (Nokia Series 30/40, even E50) "as it is", but because most of them have high resolution, the game will be displayed in the top-left corner. 

The main problem is the fact that a cellphone game must be tailored specifically for each device, given the fact that devices have different screen size, heap memory, processing power, graphics api specifics, bugs in hardware, various firmware versions for one device. 

Rendering is done through Nokia DirectGraphic, this should be replaced when targeting other phones SDKs.

I tried to make the engine as modular as possible to address most of the problems above and also tried to design a build system/framework to deal with the huge number of versions (depending on manufacturer, midp 1.0/2.0) usually emerging during such a project.

Several architectures are not supported by the java game engine (places where tile collisions are only left and the player collides from right, this will cause teleportation). Such map configurations should be avoided.

You may jump from an elevator and hang on a edge, while the elevator is moving behind you. I like it as a feature, tho :)

The player may stand on an edge, with only 1 pixel on the solid ground and the rest in the air. Also, recovery may come with the same visual effect. This could be further tweaked but well, I'm too lazy :)

Animations are not perfect, it's pretty difficult to tweak them using the current action system.

Sometimes, weird combinations result combining a mine and a player getting hit by a bullet. Not a bug, tho.

The editors may have small issues, as they were not the target of testing :)

There is only one map. That's ok. You should do the rest of them :>

The sprite editor could use several big enhancements regarding the sprite concept. Hint: a sprite is composed of many zones that repeat.

## Credits
  
Stefan "Karg" Dicu - engine programming and tools
Bogdan "Dahn" Hodorog - build system and additional programming
Andrei Fantana - game and level design
Iulian Agapie - original Cyberninja art assets 

## The Road Ahead (ToDo)

Command mode for tools.
Make the build-data.xml to build the data using the command mode of the tools.
Some more levels (another 5 would be cool).
More ports, with MIDP1.0 and MIDP2.0 functionalities.
Sounds.

## Licenses

Code license:
https://www.gnu.org/licenses/gpl-3.0.en.html

Graphics and audio assets:
https://creativecommons.org/licenses/by-nc-sa/4.0/















Programming languages: 
* Java(J2ME) for the actual game, supporting MIDP 1.0 and MIDP 2.0 and various vendor specific API (eg )
* C++ for the tools (with Borland VCL extension)

IDE/project setup and build tools:
* Borland JBuilder 8, Eclipse
* Borland C++ Builder 6
* Apache Ant and Antenna (http://antenna.sourceforge.net)

Other tools:
* Sun WTK
* Nokia SDK for Series 30









# KStereo
Welcome to KStereo, a stereograms generator written in Pascal.

Original version was coded in 1997, and a couple of stereograms were created. The release, "kstereo.exe", a self-extracting archive, might still be around on some sites.

The FPC/SDL2 version was created in 2024, and introduces more features (scenes, a basic UI interface to preview/generate/reload/save, support for any output resolution, etc). 

Code is not very well documented or straight-forward and in places down right ugly, unfortunately. As I was working on the FPC/SDL2 port, I realized that writing it from scratch would have been a far better approach.

## Media
For the original stereograms, check out the [image gallery](https://www.pirongames.com/nostalgia-kstereo-a-stereograms-generator/)!

For the newest ones in glorious HD, check the [renders](fpc-sdl2/data/renders/) folder. Here's an appetizer!
![Planetarium Stereogram](.media/planetarium.png "Planetarium stereogram")

## Setup (General)

This project uses submodules and they need to be initialized. 

After cloning the project, run:

```
git submodule init
git submodule update
```

Alternatively, pass --recurse-submodules to git clone (or tick the box in your clone popup if you have a visual git client).

## Setup&Install&Build (FPC SDL2)
Install Free Pascal Compiler (version 3.2.2+)

Run [build_kstereo.bat](fpc-sdl2/build_kstereo.bat) to build and run. 

The executable requires the SDL2 dll: SDL2.dll, SDL2_image.dll and SDL2_ttf.dll. You may find them in one of the releases or use your own.

The executable requires a scene parameter. Test scenes may be found in the [data](fpc-sdl2/data/) folder.

You can modifiy a scene file using an external text editor, then reload it in the program using the "R" key (useful if you want to make adjustments on the fly)

For the list of 3d objects ("obj" group) in the scene, please use the list of objects format. Couldn't figure out how Free Pascal TJSONConfig/jsonconf library reads array of objects, so this is a workaround.

The 3D object type can be:
* "mesh" (in which case provide a mesh in the "var" format)
* "convexsphere" for a SDF-like sphere ("radius" and "pos" properties should be specified)
* "concavesphere" for a SDF-like sphere ("radius" and "pos" properties should be specified)

## Setup&Install&Build (original, DOS)
Turbo Pascal/Borland Pascal is needed to build it. You may want to build [fast_r.pas](original/FAST_R.PAS), as it's, well, fast.

## 3D Objects format
The original 3D objects are in OBJ format, which is data disguised as code in the [OMF 16](https://en.wikipedia.org/wiki/Object_Module_Format_(Intel)) format. A conversion tool is provided, [ExtractVARFromObj.pas](fpc-sdl2/ExtractVARFromOBJ.pas) that converts them back to VAR format.

Not sure why I thought at that time that linking data would be more flexible than reading an external file, but hey, ~30 years after I've learned better :)

The original 3d objects and a few from my Amorphis demo are available in the [obj3d](fpc-sdl2/data/obj3d/) folder. 

The VAR format is quite simple:
* number of vertices (UInt16)
* each vertex contains x, y, z as Int16 (6 bytes)
* number of faces (UInt16)
* each face has 3 vertex indices (UInt16) and a color (byte) (7 bytes) - make sure you triangulate before exporting from your 3D modeler program.

## TODO
* support for floating point
* port the fast version of the generator to SDL2
* maybe a Unity plugin ;)

## License

Code license:
https://opensource.org/licenses/MIT

Original stereograms:
https://creativecommons.org/licenses/by-nc-sa/4.0/

# Oak Tree

[ ![Download](https://api.bintray.com/packages/redstoneparadox/mods/OakTree/images/download.svg?version=0.4.0-beta) ](https://bintray.com/redstoneparadox/mods/OakTree/0.4.0-beta/link)

Oak Tree is a GUI toolkit for use in Modded Minecraft alongside the Fabric modding API.

Discord: https://discord.gg/xSZHvCc

### Adding Oak Tree To Your Project

build.gradle:
```gradle
repositories {
    maven {
        url = "https://dl.bintray.com/redstoneparadox/mods"
    }
}

dependencies {
    modApi("io.github.redstoneparadox:OakTree:${project.oak_tree_version}") {
        exclude group: 'net.fabricmc.fabric-api'
    }
    include "io.github.redstoneparadox:OakTree:${project.oak_tree_version}"
}
```

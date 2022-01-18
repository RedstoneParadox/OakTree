# Oak Tree

Oak Tree is a GUI toolkit for use in Modded Minecraft alongside the Fabric modding API.

Discord: https://discord.gg/crZpcjtdJR

### Adding Oak Tree To Your Project

(**Note: If this doesn't work, use JitPack instead.**)

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

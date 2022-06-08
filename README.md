# Oak Tree

Oak Tree is a GUI toolkit for use in Modded Minecraft alongside the Fabric modding API.

Discord: https://discord.gg/crZpcjtdJR

### Adding Oak Tree To Your Project

build.gradle:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation("com.github.RedstoneParadox:OakTree:${project.oak_tree_version}") {
        exclude group: 'net.fabricmc.fabric-api'
    }
}
```

# Oak Tree

Oak Tree is a GUI toolkit for use in Modded Minecraft alongside the Fabric modding API.

Discord: https://discord.gg/crZpcjtdJR

### Adding Oak Tree To Your Project

`build.gradle`:

```gradle
maven {
    name "redstoneparadoxRepositoryReleases"
    url "https://maven.redstoneparadox.xyz/releases"
}

dependencies {
    modImplementation "io.github.redstoneparadox:OakTree:<version>"
}
```

`build.gradle.kts`:

```kotlin
maven {
    name = "redstoneparadoxRepositoryReleases"
    url = uri("https://maven.redstoneparadox.xyz/releases")
}

dependencies {
	modImplementation("io.github.redstoneparadox:OakTree:0.5.1-beta")
}
```

Note that versions before 0.5.0-beta are not on the maven.

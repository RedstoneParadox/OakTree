# Oak Tree

Oak Tree is a GUI toolkit for use in Modded Minecraft alongside the Fabric modding API.

Discord: https://discord.gg/crZpcjtdJR

### Adding Oak Tree To Your Project

build.gradle:

```gradle
repositories {
    maven {
		name = "JamalamMavenRelease"
		url = uri("https://maven.jamalam.tech/releases")
	}
}

dependencies {
    modImplementation("io.github.redstoneparadox:OakTree:<version>")
}
```

Note that versions prior to 0.5.0-beta are not on the maven.

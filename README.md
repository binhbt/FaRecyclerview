
```sh
//All project
allprojects {
    repositories {
        google()
        maven { url "https://jitpack.io" }
    }
}
//App project
compile 'com.github.binhbt:FaRec:$version'
```

# repo
build.gradle for allprojects
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
add to app build.gradle
```gradle
dependencies {
```gradle
dependencies {
    compile 'com.github.binhbt:VegaRecyclerView:1.0.1'
}
```

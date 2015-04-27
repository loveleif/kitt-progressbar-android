Use this Android ProgressBar to fill all your users need for a Knight Rider related waiting experience.

![The progress bar in action](http://i.imgur.com/HDkLpV9.gif)

### Usage

**Step 1.** Add the following to your build file.

If you're using gradle:

```
repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
  compile 'com.github.peoplesbeer:kitt-progressbar-android:v0.0.1'
}
```

If maven is your thing:

```
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>

<dependency>
  <groupId>com.github.peoplesbeer</groupId>
  <artifactId>kitt-progressbar-android</artifactId>
  <version>v0.0.1</version>
</dependency>
```

**Step 2.** Add KittProgressBar to your layout:

```
<se.leiflandia.kittprogressbar.KittProgressBar
  android:layout_width="match_parent"
  android:layout_height="16dp"
  android:indeterminate="true"/>
```

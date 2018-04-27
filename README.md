[![](https://jitpack.io/v/Omega-R/OmegaNavigationMenu.svg)](https://jitpack.io/#Omega-R/OmegaNavigationMenu)

# OmegaNavigationMenu
Custom NavigationMenu imitate NavigationDrawer

<p align="center">
    <img src="/images/navigation_menu.gif?raw=true" width="300" height="533" />
</p>

# Installation
To get a Git project into your build:

**Step 1.** Add the JitPack repository to your build file
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.** Add the dependency
```
dependencies {
    implementation 'com.github.Omega-R:OmegaNavigationMenu:1.0.1'
}
```

# Usage
Example of usage in xml layout
```
<com.omega_r.libs.navigationmenu.ContentMenuLayout
        android:id="@+id/layout_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white"
        tools:visibility="gone">
```
For more details see example project

# License
```
Copyright 2017 Omega-R

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

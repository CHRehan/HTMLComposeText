# HTMLComposeText
## Current Compose Version: 1.1.1
Compose HtmlText is capable to render HTML string from resources or from anywhere in the android project.



# Add it into your project.
Add it in your root build.gradle at the end of repositories:
```groovy

repositories {
			...
			maven { url 'https://jitpack.io' }
		}
```
Add this line in your app/build.gradle file.
```groovy
dependencies {
    implementation 'com.github.CHRehan:HTMLComposeText:1.0.0'
}
```


# How does it work?

Use it like a normal Text composable

```kotlin
HtmlText(textId = stringResource(R.string.hello_world))
```

```xml
<resources>
    <string name="hello_world">Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href="https://github.com/CHRehan/HTMLComposeText">HtmlText</a></string>
</resources>
```

# Preview

<img   src="/art/1.png" width="220"/>   <img   src="/art/2.png" width="220"/>   <img  src="/art/3.png" width="220"/>


License
--------

    Copyright 2022 Rehan Sarwar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

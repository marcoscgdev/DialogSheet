# DialogSheet   [![](https://jitpack.io/v/marcoscgdev/DialogSheet.svg)](https://jitpack.io/#marcoscgdev/DialogSheet)  ![open-issues](https://badgen.net/github/open-issues/marcoscgdev/DialogSheet)  ![last commit](https://badgen.net/github/last-commit/marcoscgdev/DialogSheet)
An Android library to create fully material designed bottom dialogs similar to the Android Pay app.

---

## Releases:

#### Current release: 2.1.2.

 - This library is now based on Kotlin and AndroidX.

You can see all the library releases [here](https://github.com/marcoscgdev/DialogSheet/releases).

---

## Screenshots
<kbd><img src="https://raw.githubusercontent.com/marcoscgdev/DialogSheet/master/screenshots/sc_1.png" width="250"></kbd>&nbsp;&nbsp;&nbsp;&nbsp;<kbd><img src="https://raw.githubusercontent.com/marcoscgdev/DialogSheet/master/screenshots/sc_2.png" width="250"></kbd>&nbsp;&nbsp;&nbsp;&nbsp;<kbd><img src="https://raw.githubusercontent.com/marcoscgdev/DialogSheet/master/screenshots/sc_4.png" width="250"></kbd>

<kbd><img src="https://raw.githubusercontent.com/marcoscgdev/DialogSheet/master/screenshots/sc_3.png" width="620"></kbd>

Download the sample apk [here](https://github.com/marcoscgdev/DialogSheet/releases/download/2.1.1/app-debug.apk).

---

## Usage:

### Adding the depencency

Add this to your root *build.gradle* file:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Now add the dependency to your app build.gradle file:

```groovy
implementation 'com.github.marcoscgdev:DialogSheet:2.1.2'
```

### Creating the dialog

Here is a complete snippet of it usage:

```kotlin
val dialogSheet = DialogSheet(this)
    .setTitle(R.string.app_name)
    .setMessage(R.string.lorem)
    .setColoredNavigationBar(true)
    .setTitleTextSize(20) // In SP
    .setCancelable(false)
    .setPositiveButton(android.R.string.ok) {
        // Your action
    }
    .setNegativeButton(android.R.string.cancel) {
        // Your action
    }
    .setNeutralButton("Neutral")
    .setRoundedCorners(false) // Default value is true
    .setBackgroundColor(Color.BLACK) // Your custom background color
    .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
    .setNeutralButtonColor(Color.WHITE)
    .show()
```

### Creating the dialog using the new style

Simply use the new _DialogSheet2_ class:

```kotlin
val dialogSheet = DialogSheet2(this)
    ...
    ...
    .show()
```

Or add a new boolean-type argument to the dialog constructor:

```kotlin
val dialogSheet = DialogSheet2(this, true)
    ...
    ...
    .show()
```

### Colorize buttons

You can do it programmatically

```kotlin
.setButtonsColorRes(R.color.colorPrimary)
```

```kotlin
.setPositiveButtonColorRes(R.color.colorPrimary)
.setNegativeButtonColorRes(R.color.colorNegative)
.setNeutralButtonColorRes(R.color.colorNeutral)
```

Or by adding this atribute to your main app theme

```xml
<item name="dialogSheetAccent">@color/colorAccent</item>
```

### Customize corner radius

Simply override this dimen with your desired size

```xml
<dimen name="dialog_sheet_corner_radius">16dp</dimen>
```

### Adding a custom view:
 
  - Via inflated view:
  
  ```kotlin
  val view = View.inflate(context, R.layout.custom_dialog_view, null)
  dialogSheet.setView(view)
  ```
  
  - Via layout resource:
 
 ```kotlin
 dialogSheet.setView(R.layout.custom_dialog_view)
 
 // Access dialog custom inflated view
val inflatedView = dialogSheet.getInflatedView()
val button = inflatedView.findViewById(R.id.customButton)
...
 ```

### Custom resources:

Override it if you want :P

```xml
<dimen name="dialog_sheet_corner_radius">16dp</dimen>
<dimen name="dialog_sheet_icon_size">56dp</dimen>
<dimen name="dialog_sheet_v2_icon_size">60dp</dimen>
<dimen name="dialog_sheet_button_text_size">15sp</dimen>
```

---
>See the *sample project* to clarify any queries you may have.

---

## License

```
The MIT License (MIT)

Copyright (c) 2017 Marcos Calvo García

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


# DialogSheet
An Android library to create fully material designed bottom dialogs similar to the Android Pay app.

---

## Releases:

#### Current release: 1.0.1.

You can see all the library releases [here](https://github.com/marcoscgdev/DialogSheet/releases).

---

## Screenshots
<kbd><img src="https://raw.githubusercontent.com/marcoscgdev/DialogSheet/master/screenshots/1.png" width="350"></kbd>&nbsp;&nbsp;&nbsp;&nbsp;<kbd><img src="https://raw.githubusercontent.com/marcoscgdev/DialogSheet/master/screenshots/2.png" width="350"></kbd>

<kbd><img src="https://raw.githubusercontent.com/marcoscgdev/DialogSheet/master/screenshots/3.png" width="620"></kbd>

---

## Usage:

### Adding the depencency

Add this to your root *build.gradle* file:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Now add the dependency to your app build.gradle file:

```
compile 'com.github.marcoscgdev:DialogSheet:1.0.1'
```

### Creating the dialog

Here is a complete snippet of it usage:

```java
new DialogSheet(this)
    .setTitle(R.string.app_name)
    .setMessage(R.string.lorem)
    .setPositiveButton(android.R.string.ok, new DialogSheet.OnPositiveClickListener() {
        @Override
        public void onClick(View v) {
            // Your action
        }
    })
    .setNegativeButton(android.R.string.cancel, new DialogSheet.OnNegativeClickListener() {
        @Override
        public void onClick(View v) {
            // Your action
        }
    })
    .setButtonsColorRes(R.color.colorPrimary)  // Default color is accent
    .show();
```

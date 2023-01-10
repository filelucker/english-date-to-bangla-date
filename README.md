# English To Bangla Date Library

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```gradel
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
 add this to ```build.gradle (Module: app)``` or if you are using "Android Studio Arctic Fox" or heigher add this to ```settings.gradle (Project Settings)```
  
  Step 2. Add the dependency
```gradel
	dependencies {
		...
	        implementation 'com.github.filelucker:english-date-to-bangla-date:1.1.1'
		...
	}
  ```
  
 # Usage
 
 ```kotlin
 import com.filelucker.englishdatetobangladate.Bangla
 ```
 
 Create an Object
 
 ```kotlin
 val bangla = Bangla()
 ```
 
 Get result from object
 
 ```kotlin
 //Input englishYear, englishMonth, englishDay are integer value 
 val res = bangla.getBanglaDate(englishYear, englishMonth, englishDay)   // Return format "MMM DD, YYYY" format.  e.g. BOISHAKH 18, 1429
 
 //Input banglaYear, banglaMonth, banglaDay are integer value 
 val res = bangla.getEnglishDate(banglaYear, banglaMonth, banglaDay)     // Return format "MMM DD, YYYY" format.  e.g. JANUARY 18, 2023
 ```
 
 ```kotlin
 val month = res.getMonth()!!.banglaName  // e.g. বৈশাখ
 ```

Share this release:

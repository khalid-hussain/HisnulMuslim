Hisnul Muslim Android App
=========================

![](http://s25.postimg.org/jhhci09aj/app_github.png)

Assalamuʻalaikum to all Muslims and greetings to everyone else,

This repository is about an Android app which would include all the dua from Hisnul Muslim. The aim of this app is to create the
best Hisnul Muslim app the Play Store has ever seen, in terms of usability and performance.

![](http://s25.postimg.org/hsll1qsun/device_2015_07_05_013454.png)
![](http://s25.postimg.org/ezsdhpsi7/device_2015_07_05_013519.png)

# Requirements
1. No Ads. The app should be given to the people free of charge with all features accessible.
2. Must adhere to Google design guidelines in terms of icon design, font use, and to a large extent even app patterns.
3. The Arabic text should be clear with the correct tashkeel/harakaat.
4. The app should have no linguistic mistakes with regard to grammar and syntax for the interface as well as duas.
I'm getting tired of poorly written apps.

# Constraints
1. App will support platforms 4.1.2 (JellyBean) and upwards.
2. Layout mirroring for Arabic language is only supported on 4.2 and above.

# (Intended) Features
1. All duas from Hisnul Muslim. I also hope to add verification for the sources in the future. The app should not have duas which
are not present in Hisnul Muslim. This is after all a Hisnul Muslim app.
2. Bookmarking function. Users should be allowed to bookmark a certain dua so that they can easily access it later on. Each
separate dua should have this feature. So, if there are 2 duas for "waking up", then each of them would have the ability to be
bookmarked.
3. Users should be able to "share" the dua on social networks (or other apps as well).
4. The app should be optimized for performance.
5. Night Mode.

# Libraries Used
- [Amiri Font Project](http://www.amirifont.org/)
- Android Support Library
- [TextJustify-Android](https://github.com/bluejamesbond/TextJustify-Android)
- [Google Material Design Icons](https://github.com/google/material-design-icons)

# Text Sources
- [Sharĥ Ĥiŝn al-Muslim](http://www.muslim-library.com/dl/books/arabic_sharh_hisn_almuslim_min_adhkar_alkitab_wa_alsunnah.pdf)
- [HisnulMuslim.Com](http://www.hisnulmuslim.com/index-page-liste-lang-en.html)
	- However, a LOT of edits were made since a lot of the times, the tashkîl was incorrect or the English had formatting mistakes.

# Credits
- [Ahmed El-Helw](http://twitter.com/ahmedre)
- [Ahmad Sabree](https://twitter.com/sabree01)
- [Azhar Ali](https://www.linkedin.com/pub/md-azhar-ali/90/251/140)
- [Abdul Majed Ahmed](https://www.facebook.com/abdulmajed.ahmed)
- [Hussaini Zulkifli](https://twitter.com/HussainiZul)
- [Moaz Mahdi](https://www.facebook.com/moaaz.mahdi)
- [StackOverFlow Question: Custom Adapter for ListView](http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view)
- [StackOverFlow Question: Default Font Set on Android](http://stackoverflow.com/questions/6809944/default-font-set-on-android)
- [Font Awesome CSS Library](http://fortawesome.github.io/Font-Awesome/)
- [Custom font for TextView](http://www.tutorialspoint.com/android/android_custom_fonts.htm)
- [ActionBar in Preferences Activity](http://stackoverflow.com/questions/26439139/getactionbar-returns-null-in-preferenceactivity-appcompat-v7-21)

# To Do
- Sort out the contents, there is a lot of problem with the Arabic vowels and sometimes the translations.
- Add references to all Du'a.
- Implement the bookmark feature.
	- Add button to each dua to enable bookmarking.
	- Implement a 'fetch' on the bookmarks activity/fragment depending on the stage the app is at.
- Add fragments and a viewpager to switch between all du'a and bookmarks (For reference, please see YouTube android app).

# Changelog
11 Ramadan 1436, 28th June 2015:
- Dua database is about 98% complete. Only the 'Condolence' dua group has issues and that may affect numbering of other groups. The rest of the dua have either incomplete translations, reference(s), mismatching translation and Arabic text due to different sources being used, and editing issues.
- Night-mode has been removed (temporarily or permanently, it is still unsure).
- Work should mostly now shift to adding Bookmarks function and a ViewPager like the YouTube app.
- README.md updated with Text sources used.

12th February 2015:
- App has a new icon.
- A lot of mistakes were found in the contents. It is currently undergoing thorough editing. Both Arabic and English.
- About page in the app has been edited and a copy of the icon shown.
- The app version details added.
- Some parts of the app have been translated into Arabic.
- Night mode settings have been added. There is no implementation yet.

17th January 2015: 
- Actionbar in Preferences activity has been fixed to certain extent. It doesn't crash now and it follows the app theme.

9th Jan 2015:
- As of today, basic search has been implemented (thanks to [Ahmed El-Helw](www.twitter.com/ahmedre)).
- Amiri font has been implemented for the Arabic parts.
- A lot of code cleanup (once again, thanks to [Ahmed El-Helw](www.twitter.com/ahmedre)). 
- One screenshot has been updated.

13th Dec 2014: 
- Added 'About' activity to the app. 
- Added 'Search' widget in DuaList activity menu.
- Updated credits in README.

7th Dec 2014: 
- Uploaded refactored code. Added new screenshots.

6th Dec 2014: 
- Updated README. A lot of refactoring underway.

26th Nov 2014: 
- Started work on implementing Material Design.
- Started keeping a simple changelog.

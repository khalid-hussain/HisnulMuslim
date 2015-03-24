Dua Content Style Guide
=======================
This document explains the style that should be followed when entering dua into the database or when reporting errors or mistakes in the contents. The app supports the original Arabic along with its translation and reference. Transliteration has been deliberately left out due to the different types available and also the requirement of providing different transliteration tables for each language since latin pronunciation changes between languages. Users are better off learning how to read Arabic as opposed to getting used to transliteration.

# Arabic
1. ~~Convey meaning in and of itself. If a literate Arab was to read the text and understand what section he's in, he would immediately understand what the text is for. This rule is up for discussion and may change soon.~~
2. It should have complete *tashkeel*/*harakaat*. Dua which are lacking in this are to be reported. The aim is for complete and correct diacritics for the Arabic text. Neither completeness or correctness should be compromised. This rule may be ignored if the content in question is a *hadith* or the description of an event.
3. Any numberals that appear in the text must be [Eastern](http://en.wikipedia.org/wiki/Eastern_Arabic_numerals).

# English
To Complete.

# Reference
1. All references must be from books of *hadith*. This is assumed and the original book probably used this but the translated books we went through sometimes added references to an audio lecture (yes, that is weird). At all times, try to follow what the original author of Hisnul Muslim used.

1. Reference should be to a hadith number and not to a page. This is because most works have various publishers and the page number inconsistencies render the ease of checking references invalid. Due to the nature of these works (i.e. manuscripts to print), sometimes the print (or some editions) would have a slightly different organization or numbering. In this case, try to stick the original. ~~Example: Section 80 of this app is not numbered in the original Hisnul Muslim book.~~

1. At this point in time, only the reference should be provided and nothing else. There should be no attempts at providing authentication information (*takreej*). This is because different scholars have different methodogies for authentication and this then results in an app which has an amalgam of different authentication rulings for different dua. This information may be added later if a consistent method is found.

2. All references must point to the Arabic versions of the books. This has a couple of benefits. 
  1. The original Arabic books of *Hadith* are more readily available.
  2. The original (book) Hisnul Muslim uses the Arabic versions as well and therefore saves a lot of effort *translating* them into the reference numbers found in the English editions due to summarizing and removal of *ahadith* with differing chains of narration.

3. Names of Imams, Muhadditheen and 'Ulama should be consistent (in spelling) throughout and follow conventional English transliteration. A table will be added later on, Insha'Allah.

4. Most of the referenced works are voluminous and therefore the reference should follow this format:
		
	`[Scholar/Book/Work] [[Hadith Number]][(Volume Number/Page Number)]`

The rules of this format are as follows:
  1. If a work has become identified with the name of the author, then the author's name will take preference over the name of the actual work. Example: 'Saheeh Bukhari' instead of 'al-Jāmi’ al-Musnad al-Sahīh al-Mukhtaṣar min umūr Rasûl Allāh wa sunnanihi wa ayyāmihi'.
  2. If the hadith is found in a book which is written by an author whose name is already identified with his *magnum opus* then
    1. If the work is known by its name without mentioning the author, then follow the aforementioned style. Example: Adab al-Mufrad 234.
    2. If the work is not well known or other books with the same title exist and naming the author is required to differentiate, then the following style would apply (Volume number should be omitted if the work consists of only one volume):
		
		`[Scholar][[Work]][[Hadith Number]][(Volume Number/Page Number)]`
		
		Example: `Imâm Nawawî [Al-Adhkaar][235](2/100)`
		
  3. The default font on Android 'Roboto' has poor choices for diacritics and thus a new system needed to be developed to avoid adding yet another font. The following table explains some of them:

Arabic | Latin     |
-------|-----------|
ا      |   â|
ب     | b |
ت     | t |
ث     | th |
ج     | j |
ح     | ĥ |
خ     | kh|
د     | d|
ذ     | dh|
ر     | r|
ز     | z|
س     | s|
ش     | sh|
ص    | ŝ |
ض    | Ď/ď|
ط     | Ť/ť|
ظ     | Ž/ž|
ع     | 'a/'|
غ     | gh|
ف    | f|
ق    | q|
ك    | k|
ل    | l|
م    | m |
ن    | n|
و    | w |
ه    | h|
ي   | y/î|
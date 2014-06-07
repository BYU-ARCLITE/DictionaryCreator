package edu.byu.arclite.dictionary.loaders

import java.io.File
import java.util.Date
import edu.byu.arclite.dictionary.Dictionary

/**
 * This handles all the creation of dictionaries based on the dictionaries Giovanni supplied
 * Date: 2/5/2013
 * @author Josh Monson
 */
object GiovanniDictionaryCreator {

  // List of dictionaries to load
  val giovanniDictionaries = List(
    "englishToDutch",
    "englishToGerman"
    /*"englishToPortuguese",
    "englishToKorean",
    "englishToJapanese",
    "englishToFrench",
    "englishToGerman", //The German and Dutch dictionaries haven't been redone
    "englishToItalian",
    "englishToSpanish",
    "englishToDutch",
    "englishToRussian",
    "englishToChinese",
    "englishToSwedish",
    "englishToHebrew"*/
  )

  // URIs of the text files
  val giovanniDictionaryTextFiles = Map(
	"tester" -> "src/main/resources/tester.txt",
    "englishToFrench" -> "src/main/resources/English-French.txt",
    "englishToGerman" -> "src/main/resources/English-German.txt",
    "englishToItalian" -> "src/main/resources/English-Italian.txt",
    "englishToSpanish" -> "src/main/resources/English-Spanish.txt",
    "englishToDutch" -> "src/main/resources/English-Dutch.txt",
    "englishToRussian" -> "src/main/resources/English-Russian.txt",
    "englishToPortuguese" -> "src/main/resources/English-Portuguese.txt",
    "englishToChinese" -> "src/main/resources/English-Chinese(T).txt",
    "englishToKorean" -> "src/main/resources/English-Korean.txt",
    "englishToJapanese" -> "src/main/resources/English-Japanese.txt",
    "englishToSwedish" -> "src/main/resources/English-Swedish.txt",
    "englishToHebrew" -> "src/main/resources/English-Hebrew.txt"
  )

  // URIs of the finished dictionary files
  val giovanniDictionaryFiles = Map(
	"tester" -> "src/main/resources/aa-zz.bin",
    "englishToFrench" -> "src/main/resources/en-fr.bin",
    "englishToGerman" -> "src/main/resources/en-de.bin",
    "englishToItalian" -> "src/main/resources/en-it.bin",
    "englishToSpanish" -> "src/main/resources/en-es.bin",
    "englishToDutch" -> "src/main/resources/en-nl.bin",
    "englishToRussian" -> "src/main/resources/en-ru.bin",
    "englishToPortuguese" -> "src/main/resources/en-pt.bin",
    "englishToChinese" -> "src/main/resources/en-zh.bin",
    "englishToKorean" -> "src/main/resources/en-ko.bin",
    "englishToJapanese" -> "src/main/resources/en-ja.bin",
    "englishToSwedish" -> "src/main/resources/en-sv.bin",
    "englishToHebrew" -> "src/main/resources/en-he.bin"
  )

  // URIs of the reverse dictionary files
  val giovanniReverseDictionaryFiles = Map(
	"tester" -> "src/main/resources/zz-aa.bin",
    "englishToFrench" -> "src/main/resources/fr-en.bin",
    "englishToGerman" -> "src/main/resources/de-en.bin",
    "englishToItalian" -> "src/main/resources/it-en.bin",
    "englishToSpanish" -> "src/main/resources/es-en.bin",
    "englishToDutch" -> "src/main/resources/nl-en.bin",
    "englishToRussian" -> "src/main/resources/ru-en.bin",
    "englishToPortuguese" -> "src/main/resources/pt-en.bin",
    "englishToChinese" -> "src/main/resources/zh-en.bin",
    "englishToKorean" -> "src/main/resources/ko-en.bin",
    "englishToJapanese" -> "src/main/resources/ja-en.bin",
    "englishToSwedish" -> "src/main/resources/sv-en.bin",
    "englishToHebrew" -> "src/main/resources/he-en.bin"
  )

  /**
   * This creates a dictionary based on the name
   * @param dictionaryName The name of the dictionary to create
   */
  def createDictionary(dictionaryName: String) {
    // Keep track of time
    val start = new Date().getTime

    // Create the dictionary loader
    val dictionaryTextFile = new File(giovanniDictionaryTextFiles(dictionaryName))
    val loader = new GiovanniDictionaryLoader(dictionaryTextFile)

    // Create the dictionary and save it to file
    val dictionary = Dictionary.createDictionary(loader)
    val dictionaryFile = new File(giovanniDictionaryFiles(dictionaryName))
    Dictionary.saveToFile(dictionary, dictionaryFile)

    // How long did it take?
    val end = new Date().getTime
    println("Dictionary \"" + dictionaryName + "\" created in " + (end - start) + " ms.")
  }

  /**
   * This creates a reverse dictionary based on the name
   * @param dictionaryName The name of the dictionary to create
   */
  def createReverseDictionary(dictionaryName: String) {
    // Keep track of time
    val start = new Date().getTime

    // Create the dictionary loader
    val dictionaryTextFile = new File(giovanniDictionaryTextFiles(dictionaryName))
    val loader = new GiovanniReverseDictionaryLoader(dictionaryTextFile)

    // Create the dictionary and save it to file
    val dictionary = Dictionary.createDictionary(loader)
    val dictionaryFile = new File(giovanniReverseDictionaryFiles(dictionaryName))
    Dictionary.saveToFile(dictionary, dictionaryFile)

    // How long did it take?
    val end = new Date().getTime
    println("Reverse dictionary \"" + dictionaryName + "\" created in " + (end - start) + " ms.")
  }

  /**
   * This creates all the dictionaries
   */
  def createDictionaries() {
    println("Loading Giovanni's dictionaries...")
    for (dictionaryName <- giovanniDictionaries) {
      createDictionary(dictionaryName)
      createReverseDictionary(dictionaryName)
    }
  }

  def test(dictionaryName: String, keys: List[String]) {

    val dictionaryFileName =
      if (dictionaryName.substring(0,2) == "R_")
        giovanniReverseDictionaryFiles(dictionaryName.substring(2))
      else
        giovanniDictionaryFiles(dictionaryName)

    val dictionaryFile = new File(dictionaryFileName)
    val dictionary = Dictionary.loadFromFile(dictionaryFile)
    for (key <- keys) {
      println("Dictionary entry for \"" + key + "\":")
      println(dictionary(key))
    }

  }

}

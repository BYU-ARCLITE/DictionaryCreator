package edu.byu.arclite.dictionary.loaders

import java.io.File
import java.util.Date
import edu.byu.arclite.dictionary.Dictionary

/**
 * Handles the creation of dictionaries based on the dictionaries provided by Tim Buckwalter at International Copus of Arabic (ICA)
 */
object ICADictionaryCreator {

  val icaDictionaries = List(
    "egyptianArabicToEnglish",
    "levantineArabicToEnglish"
  )

  val icaDictionaryCSVFiles = Map(
    "egyptianArabicToEnglish" -> "src/main/resources/EgyptianArabicToEnglish.csv",
    "levantineArabicToEnglish" -> "src/main/resources/LevantineArabicToEnglish.csv"
  )

  val icaDictionaryFiles = Map(
    "egyptianArabicToEnglish" -> "src/main/resources/arz-eng.bin",
    "levantineArabicToEnglish" -> "src/main/resources/apc-eng.bin"
  )

  def createDictionary(dictionaryName: String) = {
    val start = new Date().getTime

    val dictionaryTextFile = new File(icaDictionaryCSVFiles(dictionaryName))
    val loader = new ICADictionaryLoader(dictionaryTextFile)

    // Create the dictionary and save it to file
    val dictionary = Dictionary.createDictionary(loader)
    val dictionaryFile = new File(icaDictionaryFiles(dictionaryName))
    Dictionary.saveToFile(dictionary, dictionaryFile)

    // How long did it take?
    val end = new Date().getTime
    println("Dictionary \"" + dictionaryName + "\" created in " + (end - start) + " ms.")
  }

  def tester(): Unit = {
    val dict = Dictionary.loadFromFile(new File("src/main/tests/apc-eng.bin"))
    println("Asdfasdf")
  }

  /**
   * This creates all the dictionaries
   */
  def createDictionaries() {
    println("Loading ICA's dictionaries...")
    for (dictionaryName <- icaDictionaries) {
      createDictionary(dictionaryName)
    }
  }
}
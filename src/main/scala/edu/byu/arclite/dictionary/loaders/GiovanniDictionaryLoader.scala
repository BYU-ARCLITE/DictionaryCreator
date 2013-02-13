package edu.byu.arclite.dictionary.loaders

import java.io.File
import org.apache.commons.lang3.text.StrTokenizer
import collection.JavaConversions._
import io.Source
import java.text.Normalizer
import edu.byu.arclite.dictionary.DictionaryLoader

/**
 * This a dictionary loader for Giovanni's dictionary files saved as .cvs text files.
 * Date: 2/5/2013
 * @author Josh Monson
 * @param textFile The .csv file from which the dictionary will be loaded
 */
class GiovanniDictionaryLoader(textFile: File) extends DictionaryLoader {

  // The dictionary text file
  protected val dictionaryFile = textFile

  // A CSV tokenizer for tokenizing the lines of the text files
  val tokenizer = StrTokenizer.getCSVInstance

  /**
   * This takes a line from a .CSV file as a string, tokenizes it, and return the tokens as a list of strings.
   * @param line The .csv line to parse and tokenize
   * @return The list of entries in the .csv line
   */
  def parseCsvLine(line: String): List[String] = tokenizer.reset(line).getTokenList.toList

  /**
   * This loads the dictionary text file, parses each line, and creates a dictionary entry from it
   * @return
   */
  override def getEntries: List[(String, String)] = {
    val contents = Source.fromFile(dictionaryFile).getLines()
    contents.map(str => {
      val parts = parseCsvLine(str)
      val entry = Normalizer.normalize(parts(1) + " (" + parts(2) + ")", Normalizer.Form.NFC)
      (Normalizer.normalize(parts(0), Normalizer.Form.NFC), entry)
    }).toList
  }
}
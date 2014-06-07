package edu.byu.arclite.dictionary.loaders

import java.io.File
import io.Source
import java.text.Normalizer

/**
 * This builds upon the standard Giovanni dictionary loader, but swaps the entry order in order to create a reverse
 * dictionary.
 * Date: 2/5/2013
 * @author Josh Monson
 * @param textFile The .csv file from which the dictionary will be loaded
 */
class GiovanniReverseDictionaryLoader(textFile: File) extends GiovanniDictionaryLoader(textFile) {

  /**
   * This loads the dictionary text file, parses each line, and creates a dictionary entry from it, reversing the entry
   * order and omitting the part of speech.
   * @return
   * edit: Part of speech is included
   */
  override def getEntries: List[(String, String)] = {
    val contents = Source.fromFile(dictionaryFile).getLines()
    contents.map(str => {
      val parts = parseCsvLine(str)
      val pos = if(parts(2).filterNot("[]()" contains _) != "") {" (" + parts(2).filterNot("[]()" contains _) + ")"} else ""
      val entry = Normalizer.normalize(parts(0) + pos, Normalizer.Form.NFC)
      (Normalizer.normalize(parts(1), Normalizer.Form.NFC), entry)
    }).toList
  }

}

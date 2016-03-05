package edu.byu.arclite.dictionary

import java.io.File
import loaders.GiovanniDictionaryCreator
import loaders.ICADictionaryCreator

/**
 * This provides easy ways of creating dictionaries from text files.
 * Date: 2/5/2013
 * @author Josh Monson
 */
object DictionaryCreator {

  def printUsage() {
    println("Usage: create [dictionarySet]")
    println("Usage: test   [dictionarySet] [dictionary] [entries ...]\n")
    println("The following are the available dictionary sets:")
    println("   giovanni  -- The dictionaries provided by Giovanni")
    println("   ICA       -- The dictionaries provided by Tim Buckwalter from the ICA")
  }

  def main(args: Array[String]) {
    println("ARCLITE Dictionary Tool")

    if (args.size == 0)
      printUsage()
    else {

      // Check the verb
      if (args(0) == "create") {

        // Check for giovanni dictionaries
        if (args(1) == "giovanni")
          GiovanniDictionaryCreator.createDictionaries()
        // Check for ICA dictionaries
        if (args(1).toUpperCase == "ICA")
          ICADictionaryCreator.createDictionaries()

      } else if (args(0) == "test") {
        // Check for giovanni dictionaries
        if (args(1) == "giovanni")
          GiovanniDictionaryCreator.test(args(2), args.slice(3, args.size).toList)

      } else
        printUsage()
    }
  }



}

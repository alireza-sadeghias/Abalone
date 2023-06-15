package ir.alireza.sadeghi.shiraz.ai.weight.optimisation

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix
import org.apache.logging.log4j.LogManager
import java.io.*

object ReadResult {
    const val COMMENT = "//"
    var fileNumber = 0
    private val logger = LogManager.getLogger(ReadResult::class.java)
    fun readIn(File: String): IntArray {
        val Results = IntArray(2)
        if (File.length < 1) {
            logger.error("Error! No filename specified.")
            System.exit(0)
        }
        var name = 0
        var gen = 0
        var moves = 0
        var marbles = 0
        try {
            val fr = FileReader(File)
            val br = BufferedReader(fr)
            var record = String()

            //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.
            while (br.readLine().also { record = it } != null) {
                if (record.startsWith("//")) continue
                break // Saw a line that did not start with a comment -- time to start reading the data in!
            }
            if (record.startsWith("AInumber: ")) {
                name = record.substring(10).toInt()
                logger.trace(COMMENT + " AInumber: " + name)
            }
            record = br.readLine()
            if (record.startsWith("Gen: ")) {
                gen = record.substring(5).toInt()
                logger.trace(COMMENT + " Gen: " + gen)
            }
            record = br.readLine()
            if (record.startsWith("Moves: ")) {
                moves = record.substring(7).toInt()
                Results[0] = moves
                logger.trace(COMMENT + " Moves: " + moves)
            }
            record = br.readLine()
            if (record.startsWith("Marbles: ")) {
                marbles = record.substring(9).toInt()
                Results[1] = marbles
                logger.trace(COMMENT + " Marbles: " + marbles)
            }
        } catch (ex: IOException) {
            logger.error("Error! Problem reading file $File")
            System.exit(0)
        }
        Results[0] = moves
        Results[1] = marbles
        return Results
    }

    fun readOut() {
        fileNumber++
        for (i in 1..3) {
            val doc =
                File(System.getProperty("user.dir") + ReadMatrix.slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + ReadMatrix.slash + "Results" + ReadMatrix.slash + "AIResult" + ReadMatrix.gen + "_" + fileNumber + "_" + i + ".txt")
            logger.trace("Path : " + doc.absolutePath)
            try {
                val fw = FileWriter(doc, true)
                fw.write("AInumber: " + fileNumber)
                fw.write(System.lineSeparator())
                fw.write("Gen: " + ReadMatrix.gen)
                fw.write(System.lineSeparator())
                fw.write("Moves: 0")
                fw.write(System.lineSeparator())
                fw.write("Marbles: 0")
                fw.write(System.lineSeparator())
                fw.close()
            } catch (ex: IOException) {
                System.err.println("Couldn't log this")
            }
        }
    }

    fun addResult(filePath: String?, result: IntArray?) {
        val fileToBeModified = File(filePath)
        var oldContent = ""
        var reader: BufferedReader? = null
        var writer: FileWriter? = null
        var oldString1: String? = null
        var oldString2: String? = null
        try {
            reader = BufferedReader(FileReader(fileToBeModified))

            //Reading all the lines of input text file into oldContent
            var line = reader.readLine()
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator()
                if (line.startsWith("Moves: ")) {
                    oldString1 = line
                }
                if (line.startsWith("Marbles: ")) {
                    oldString2 = line
                }
                line = reader.readLine()
            }

            //Replacing oldString with newString in the oldContent
            var newContent =
                oldContent.replace(oldString1!!.toRegex(), "Moves: " + (oldString1.substring(7).toInt() + result!![0]))
            newContent =
                newContent.replace(oldString2!!.toRegex(), "Marbles: " + (oldString2.substring(9).toInt() + result[1]))

            //Rewriting the input text file with newContent
            writer = FileWriter(fileToBeModified)
            writer.write(newContent)
            //writer.write(newContent2);
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                //Closing the resources
                reader!!.close()
                writer!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
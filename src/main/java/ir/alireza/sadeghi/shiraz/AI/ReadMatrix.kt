package ir.alireza.sadeghi.shiraz.ai

import org.apache.logging.log4j.LogManager
import java.io.*
import kotlin.system.exitProcess

object ReadMatrix {
    private val logger = LogManager.getLogger(ReadMatrix::class.java)
    private const val COMMENT = "//"

    //! n is the number of weights per mode
    private const val n = 8

    //! m is the number of modes
    private const val m = 5
    var gen = 0
    var fileNumber = 0
    var slash: String? = null
    fun readIn(filePath: String?): Array<DoubleArray> {
        val weightMatrix = Array(m) { DoubleArray(n) }
        if (filePath == null || filePath.isEmpty()) {
            logger.error("Error! No filename specified.")
            exitProcess(0)
        }
//        var name = 0
        gen = 0
        try {
            val fr = FileReader(filePath)
            val br = BufferedReader(fr)
            var record: String

            //! The first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while (br.readLine().also { record = it } != null) {
                if (record.startsWith("//")) continue
                break // Saw a line that did not start with a comment -- time to start reading the data in!
            }
            record = br.readLine()
            if (record.startsWith("Gen: ")) {
                gen = record.substring(5).toInt()
            }
            for ((row, _) in (0 until m).withIndex()) {
                record = br.readLine()
                val data = record.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (data.size != n) {
                    logger.error("Error! Malformed weight line: $record")
                    exitProcess(0)
                }
                for (i in 0 until n) {
                    weightMatrix[row][i] = data[i].toDouble()
                }
            }
            val surplus = br.readLine()
            if (surplus != null) {
                if (surplus.length >= n) {
                    logger.trace("$COMMENT Warning: there appeared to be data in your file after the last weight: '$surplus'")
                }
            }
        } catch (ex: IOException) {
            logger.error(ex)
            // catch possible io errors from readLine()
            logger.error("Error! Problem reading file {}", filePath)
            exitProcess(0)
        }
        return weightMatrix
    }

    fun readOut(weights: Array<DoubleArray?>?, folder: String) {
        fileNumber++
        val doc =
            File(System.getProperty("user.dir") + slash + "\\src\\main\\java\\ir\\alireza\\sadeghi\\shiraz\\ai" + slash + folder + slash + "AInumber" + gen + "_" + fileNumber + ".txt")
        logger.trace("Path : " + doc.absolutePath)
        try {
            val fw = FileWriter(doc, true)
            fw.write("AI number: $fileNumber")
            fw.write(System.lineSeparator())
            fw.write("Gen: $gen")
            fw.write(System.lineSeparator())
            for (k in 0 until m) {
                for (l in 0 until n) {
                    fw.write(weights!![k]!![l].toString() + " ")
                }
                fw.write(System.lineSeparator())
            }
            fw.close()
        } catch (ex: IOException) {
            logger.error("Couldn't log this")
        }
    }
}
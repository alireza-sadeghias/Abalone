package ir.alireza.sadeghi.shiraz

/*
 * Manages some data needed to play the game.
 */
object GameData {
    var move = Move()
    var score = IntArray(3)
    var numberPlayers = 2
    lateinit var rows: BoardRows
    var tb = Traceback()
}
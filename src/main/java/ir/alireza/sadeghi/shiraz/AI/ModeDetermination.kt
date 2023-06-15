package ir.alireza.sadeghi.shiraz.ai

class ModeDetermination(  //  Structure of the weights
    // { Distance , Cohesion , Break , strength , Won , Lost , DistanceOpp , Danger }
    private val weightMatrix_AI: Array<DoubleArray>
) {
    fun determineMode(f1: Double, Opp: Double, Own: Double): DoubleArray {
        return if (Own == 9.0) {
            weightMatrix_AI[4]
        } else if (Opp == 9.0) {
            weightMatrix_AI[3]
        } else if (Own > Opp) {
            weightMatrix_AI[2]
        } else {
            if (f1 < 0.75 && ModeDetermination.Companion.counter < 2) return weightMatrix_AI[0] else ModeDetermination.Companion.counter =
                2
            weightMatrix_AI[1]
        }
    }

    companion object {
        /*In this class, the mode that the game is currently in, is determined. Given is the boardState. Look into paper as reference,
     the mapping into a specific mode is solved there by delimiting it by the center and cohesion strategies, the value of the weights in the evalFunction
     is determined by the mode in a static value association
    */
        // all weigths should be positive, the function values f1..f8 have been instead altered according to heuristic observations
        var counter = 1
    }
}
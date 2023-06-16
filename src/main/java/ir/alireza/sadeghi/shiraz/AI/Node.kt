package ir.alireza.sadeghi.shiraz.ai

/*
 * Basic node structure.
 */
class Node<T>(var data: T) {
    var parent: Node<T>? = null
    var children: MutableList<Node<T>>? = ArrayList()
    var nVisits = 0
    var wins = 0.0
    var totValue = 0.0

    fun addChild(childData: T) {
        val child = Node(childData)
        child.parent = this
        children!!.add(child)
    }

    fun returnData(): T {
        return data
    }

    val isLeaf: Boolean
        get() = children!!.size == 0

    fun calcMCTSvalue() {
        totValue = MonteCarlo.calculateUCB(wins, nVisits, parent!!.nVisits)
    }
}
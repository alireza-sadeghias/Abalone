package ir.alireza.sadeghi.shiraz.ai

class GameTree(root: Node<GameState?>?) {
    var gameTree: Tree<GameState?>

    init {
        gameTree = Tree(root)
    }

//    fun setNewRoot(newRoot: Node<GameState?>) {
//        DeleteLayers.deleteEverythingAbove(newRoot, gameTree)
//    }

    fun buildFullTree(layers: Int) {
        //builds the entire tree we need (can be a LOT of nodes)
        gameTree?.root?.children?.clear()
        var expanding: MutableList<Node<GameState?>?> = ArrayList()
        expanding.add(gameTree?.root)

        //create layers
        for (i in 1 until layers + 1) {
            AddNodes.addForMultiple(expanding)
            expanding = findAtDepth(i)
        }
    }

    //finds all the nodes at a certain depth
    private fun findAtDepth(depth: Int): MutableList<Node<GameState?>?> {
        val current = gameTree.root
        val currentKids: MutableList<Node<GameState?>?> = ArrayList()
        val newKids: MutableList<Node<GameState?>?> = ArrayList()
        currentKids.add(current)
        for (i in 0 until depth) {
            for (j in currentKids.indices) {
                if (currentKids[j]!!.children != null) {
                    newKids.addAll(currentKids[j]!!.children!!)
                }
            }
            currentKids.clear()
            currentKids.addAll(newKids)
            newKids.clear()
        }
        return currentKids
    }

    val root: Node<GameState?>?
        get() = gameTree.root
}
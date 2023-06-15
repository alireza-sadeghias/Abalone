package ir.alireza.sadeghi.shiraz.ai

/*
 * Can delete layers or even whole branches of a tree.
 * Made so it's easy to delete parts of the tree when it gets too big.
 */
object DeleteLayers {
    //set new root and disconnect it from the parent
    fun deleteEverythingAbove(newRoot: Node<GameState?>, tree: Tree<GameState?>) {
        tree.root =newRoot
        newRoot.parent = null
    }

    //delete a branch of a certain node
    fun deleteBranch(start: Node<GameState?>) {
        start.children!!.clear()
    }
}
package idiotamspielen.vttproject.services

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent

class MouseEventHandler : EventHandler<MouseEvent?> {
    private lateinit var initialPosition: DoubleArray
    private var grabbedNode: Node? = null

    override fun handle(p0: MouseEvent?) {
        val node = p0!!.getSource() as Node?

        if (p0.getEventType() == MouseEvent.MOUSE_PRESSED) {
            initialPosition = doubleArrayOf(p0.sceneX, p0.sceneY)
            grabbedNode = node
        } else if (p0.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (grabbedNode != null) {
                val deltaX = p0.sceneX - initialPosition[0]
                val deltaY = p0.sceneY - initialPosition[1]

                grabbedNode!!.translateX = grabbedNode!!.translateX + deltaX
                grabbedNode!!.translateY = grabbedNode!!.translateY + deltaY

                // update saved position for next drag-event
                initialPosition = doubleArrayOf(p0.sceneX, p0.sceneY)
            }
        }
    }
}
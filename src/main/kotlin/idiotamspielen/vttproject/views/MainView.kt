package idiotamspielen.vttproject.views

import idiotamspielen.vttproject.controllers.MainController
import idiotamspielen.vttproject.userInterface.TableTop
import tornadofx.View
import tornadofx.action
import tornadofx.borderpane
import tornadofx.hbox
import tornadofx.insets
import tornadofx.item
import tornadofx.menu
import tornadofx.menubar
import tornadofx.pane
import tornadofx.stackpane
import tornadofx.vbox

class MainView : View() {
    private val controller: MainController by inject()

    val tableTopPane = pane{
        prefWidth = 1.0
        prefHeight = 1.0
        add(TableTop())
    }

    val rootPane = borderpane {
        top = menubar {
            menu("Create") {
                item("Spell").action { controller.createSpell() }
                item("Item").action {}
                item("Character").action {}
                item("Race").action {}
                item("Class").action {}
            }
            menu("Search") {
                item("Spell").action {}
                item("Character").action {}
                item("Race").action {}
                item("Class").action {}
            }
        }
        center = vbox {
            padding = insets(10)
            hbox {
                stackpane{
                    padding = insets(0, 10, 10, 0)
                    add(tableTopPane)
                }
            }
        }
    }

    init {
        controller.setup(tableTopPane, rootPane)
        title = "VTT V0.3.0"
    }

    override val root = rootPane
}
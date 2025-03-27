package idiotamspielen.vttproject.views

import idiotamspielen.vttproject.controllers.MainController
import idiotamspielen.vttproject.views.TableTop
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

/**
 * The MainView class represents the main interface of the application.
 *
 * It provides the primary layout, including a menu bar for creating and searching different entities like spells, items,
 * characters, races, and classes. The center of the view contains a resizable tabletop Pane.
 *
 * @constructor Creates an instance of MainView, initializes the UI elements, and sets up the controller.
 *
 * @property controller An instance of [MainController] for handling actions triggered from the UI.
 * @property tableTopPane A pane that holds a [TableTop] instance.
 * @property rootPane The root BorderPane of the view containing the menu bar and the tableTopPane.
 */
class MainView : View("VTT 0.3.0") {
    private val controller: MainController by inject()

    val tableTopPane = pane{
        prefWidth = 1.0
        prefHeight = 1.0
        add(TableTop())
    }

    val rootPane = borderpane {
        top = menubar {
            menu("Create") {
                item("Spell").action { controller.openSpellCreator(primaryStage) }
                item("Item").action {}
                item("Character").action {}
                item("Race").action {}
                item("Class").action {}
            }
            menu("Search") {
                item("Spell").action {controller.openSpellSearch()}
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
    }

    override val root = rootPane
}
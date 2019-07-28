/*
 * Jexer - Java Text User Interface
 *
 * The MIT License (MIT)
 *
 * Copyright (C) 2019 Kevin Lamonte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * @author Kevin Lamonte [kevin.lamonte@gmail.com]
 * @version 1
 */
package jexer;

import java.util.ResourceBundle;

import jexer.event.TCommandEvent;
import jexer.event.TKeypressEvent;
import jexer.event.TMouseEvent;
import jexer.event.TResizeEvent;
import jexer.menu.TMenu;
import static jexer.TCommand.*;
import static jexer.TKeypress.*;

/**
 * TTableWindow is used to display and edit regular two-dimensional tables of
 * cells.
 */
public class TTableWindow extends TScrollableWindow {

    /**
     * Translated strings.
     */
    private static final ResourceBundle i18n = ResourceBundle.getBundle(TTableWindow.class.getName());

    // ------------------------------------------------------------------------
    // Variables --------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * The table widget.
     */
    private TTableWidget tableField;

    // ------------------------------------------------------------------------
    // Constructors -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Public constructor sets window title.
     *
     * @param parent the main application
     * @param title the window title
     */
    public TTableWindow(final TApplication parent, final String title) {

        super(parent, title, 0, 0, parent.getScreen().getWidth() / 2,
            parent.getScreen().getHeight() / 2 - 2, RESIZABLE | CENTERED);

        tableField = new TTableWidget(this, 0, 0, getWidth() - 2, getHeight() - 2);
        setupAfterTable();
    }

    // ------------------------------------------------------------------------
    // Event handlers ---------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Called by application.switchWindow() when this window gets the
     * focus, and also by application.addWindow().
     */
    public void onFocus() {
        // Enable the table menu items.
        getApplication().enableMenuItem(TMenu.MID_CUT);
        getApplication().enableMenuItem(TMenu.MID_TABLE_VIEW_ROW_LABELS);
        getApplication().enableMenuItem(TMenu.MID_TABLE_VIEW_COLUMN_LABELS);
        getApplication().enableMenuItem(TMenu.MID_TABLE_VIEW_HIGHLIGHT_ROW);
        getApplication().enableMenuItem(TMenu.MID_TABLE_VIEW_HIGHLIGHT_COLUMN);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_NONE);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_ALL);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_RIGHT);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_LEFT);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_TOP);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_BOTTOM);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_DOUBLE_BOTTOM);
        getApplication().enableMenuItem(TMenu.MID_TABLE_BORDER_THICK_BOTTOM);
        getApplication().enableMenuItem(TMenu.MID_TABLE_DELETE_LEFT);
        getApplication().enableMenuItem(TMenu.MID_TABLE_DELETE_UP);
        getApplication().enableMenuItem(TMenu.MID_TABLE_DELETE_ROW);
        getApplication().enableMenuItem(TMenu.MID_TABLE_DELETE_COLUMN);
        getApplication().enableMenuItem(TMenu.MID_TABLE_INSERT_LEFT);
        getApplication().enableMenuItem(TMenu.MID_TABLE_INSERT_RIGHT);
        getApplication().enableMenuItem(TMenu.MID_TABLE_INSERT_ABOVE);
        getApplication().enableMenuItem(TMenu.MID_TABLE_INSERT_BELOW);
        getApplication().enableMenuItem(TMenu.MID_TABLE_COLUMN_NARROW);
        getApplication().enableMenuItem(TMenu.MID_TABLE_COLUMN_WIDEN);
        getApplication().enableMenuItem(TMenu.MID_TABLE_FILE_SAVE_CSV);
        getApplication().enableMenuItem(TMenu.MID_TABLE_FILE_SAVE_TEXT);
    }

    /**
     * Called by application.switchWindow() when another window gets the
     * focus.
     */
    public void onUnfocus() {
        // Disable the table menu items.
        getApplication().disableMenuItem(TMenu.MID_CUT);
        getApplication().disableMenuItem(TMenu.MID_TABLE_VIEW_ROW_LABELS);
        getApplication().disableMenuItem(TMenu.MID_TABLE_VIEW_COLUMN_LABELS);
        getApplication().disableMenuItem(TMenu.MID_TABLE_VIEW_HIGHLIGHT_ROW);
        getApplication().disableMenuItem(TMenu.MID_TABLE_VIEW_HIGHLIGHT_COLUMN);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_NONE);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_ALL);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_RIGHT);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_LEFT);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_TOP);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_BOTTOM);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_DOUBLE_BOTTOM);
        getApplication().disableMenuItem(TMenu.MID_TABLE_BORDER_THICK_BOTTOM);
        getApplication().disableMenuItem(TMenu.MID_TABLE_DELETE_LEFT);
        getApplication().disableMenuItem(TMenu.MID_TABLE_DELETE_UP);
        getApplication().disableMenuItem(TMenu.MID_TABLE_DELETE_ROW);
        getApplication().disableMenuItem(TMenu.MID_TABLE_DELETE_COLUMN);
        getApplication().disableMenuItem(TMenu.MID_TABLE_INSERT_LEFT);
        getApplication().disableMenuItem(TMenu.MID_TABLE_INSERT_RIGHT);
        getApplication().disableMenuItem(TMenu.MID_TABLE_INSERT_ABOVE);
        getApplication().disableMenuItem(TMenu.MID_TABLE_INSERT_BELOW);
        getApplication().disableMenuItem(TMenu.MID_TABLE_COLUMN_NARROW);
        getApplication().disableMenuItem(TMenu.MID_TABLE_COLUMN_WIDEN);
        getApplication().disableMenuItem(TMenu.MID_TABLE_FILE_SAVE_CSV);
        getApplication().disableMenuItem(TMenu.MID_TABLE_FILE_SAVE_TEXT);
    }

    // ------------------------------------------------------------------------
    // TWindow ----------------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Draw the window.
     */
    @Override
    public void draw() {
        // Draw as normal.
        super.draw();

        // Add borders on rows and columns.
        // TODO
    }

    /**
     * Handle mouse press events.
     *
     * @param mouse mouse button press event
     */
    @Override
    public void onMouseDown(final TMouseEvent mouse) {
        // Use TWidget's code to pass the event to the children.
        super.onMouseDown(mouse);

        if (mouseOnTable(mouse)) {
            // The table might have changed, update the scollbars.
            // TODO
            /*
            setBottomValue(editField.getMaximumRowNumber());
            setVerticalValue(editField.getVisibleRowNumber());
            setRightValue(editField.getMaximumColumnNumber());
            setHorizontalValue(editField.getEditingColumnNumber());
            */
        } else {
            if (mouse.isMouseWheelUp() || mouse.isMouseWheelDown()) {
                // Vertical scrollbar actions
                // TODO
                // editField.setVisibleRowNumber(getVerticalValue());
            }
        }
    }

    /**
     * Handle mouse release events.
     *
     * @param mouse mouse button release event
     */
    @Override
    public void onMouseUp(final TMouseEvent mouse) {
        // Use TWidget's code to pass the event to the children.
        super.onMouseUp(mouse);

        if (mouse.isMouse1() && mouseOnVerticalScroller(mouse)) {
            // Clicked on vertical scrollbar
            // TODO
            // editField.setVisibleRowNumber(getVerticalValue());
        }

        // TODO: horizontal scrolling
    }

    /**
     * Method that subclasses can override to handle mouse movements.
     *
     * @param mouse mouse motion event
     */
    @Override
    public void onMouseMotion(final TMouseEvent mouse) {
        // Use TWidget's code to pass the event to the children.
        super.onMouseMotion(mouse);

        if (mouseOnTable(mouse) && mouse.isMouse1()) {
            // The editor might have changed, update the scollbars.
            // TODO
            /*
            setBottomValue(editField.getMaximumRowNumber());
            setVerticalValue(editField.getVisibleRowNumber());
            setRightValue(editField.getMaximumColumnNumber());
            setHorizontalValue(editField.getEditingColumnNumber());
            */
        } else {
            if (mouse.isMouse1() && mouseOnVerticalScroller(mouse)) {
                // Clicked/dragged on vertical scrollbar
                // TODO
                // editField.setVisibleRowNumber(getVerticalValue());
            }

            // TODO: horizontal scrolling
        }

    }

    /**
     * Handle keystrokes.
     *
     * @param keypress keystroke event
     */
    @Override
    public void onKeypress(final TKeypressEvent keypress) {
        // Use TWidget's code to pass the event to the children.
        super.onKeypress(keypress);

        // The editor might have changed, update the scollbars.
        // TODO
        /*
        setBottomValue(editField.getMaximumRowNumber());
        setVerticalValue(editField.getVisibleRowNumber());
        setRightValue(editField.getMaximumColumnNumber());
        setHorizontalValue(editField.getEditingColumnNumber());
         */
    }

    /**
     * Handle window/screen resize events.
     *
     * @param event resize event
     */
    @Override
    public void onResize(final TResizeEvent event) {
        if (event.getType() == TResizeEvent.Type.WIDGET) {
            // Resize the table
            TResizeEvent tableSize = new TResizeEvent(TResizeEvent.Type.WIDGET,
                event.getWidth() - 2, event.getHeight() - 2);
            tableField.onResize(tableSize);

            // Have TScrollableWindow handle the scrollbars
            super.onResize(event);
            return;
        }

        // Pass to children instead
        for (TWidget widget: getChildren()) {
            widget.onResize(event);
        }
    }

    // ------------------------------------------------------------------------
    // TTableWindow -----------------------------------------------------------
    // ------------------------------------------------------------------------

    /**
     * Setup other fields after the table is created.
     */
    private void setupAfterTable() {
        hScroller = new THScroller(this, 17, getHeight() - 2, getWidth() - 20);
        vScroller = new TVScroller(this, getWidth() - 2, 0, getHeight() - 2);
        setMinimumWindowWidth(25);
        setMinimumWindowHeight(10);
        setTopValue(1);
        // setBottomValue(editField.getMaximumRowNumber());
        setLeftValue(1);
        setRightValue(tableField.getMaximumWidth());

        statusBar = newStatusBar(i18n.getString("statusBar"));
        statusBar.addShortcutKeypress(kbF1, cmHelp,
            i18n.getString("statusBarHelp"));
        /*
        statusBar.addShortcutKeypress(kbF2, cmSave,
            i18n.getString("statusBarSave"));
        statusBar.addShortcutKeypress(kbF3, cmOpen,
            i18n.getString("statusBarOpen"));
        */
        statusBar.addShortcutKeypress(kbF10, cmMenu,
            i18n.getString("statusBarMenu"));
    }

    /**
     * Check if a mouse press/release/motion event coordinate is over the
     * table.
     *
     * @param mouse a mouse-based event
     * @return whether or not the mouse is on the table
     */
    private boolean mouseOnTable(final TMouseEvent mouse) {
        if ((mouse.getAbsoluteX() >= getAbsoluteX() + 1)
            && (mouse.getAbsoluteX() <  getAbsoluteX() + getWidth() - 1)
            && (mouse.getAbsoluteY() >= getAbsoluteY() + 1)
            && (mouse.getAbsoluteY() <  getAbsoluteY() + getHeight() - 1)
        ) {
            return true;
        }
        return false;
    }

}
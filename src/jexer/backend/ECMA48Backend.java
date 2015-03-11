/**
 * Jexer - Java Text User Interface
 *
 * License: LGPLv3 or later
 *
 * This module is licensed under the GNU Lesser General Public License
 * Version 3.  Please see the file "COPYING" in this directory for more
 * information about the GNU Lesser General Public License Version 3.
 *
 *     Copyright (C) 2015  Kevin Lamonte
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, see
 * http://www.gnu.org/licenses/, or write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * @author Kevin Lamonte [kevin.lamonte@gmail.com]
 * @version 1
 */
package jexer.backend;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import jexer.event.TInputEvent;
import jexer.io.ECMA48Screen;
import jexer.io.ECMA48Terminal;

/**
 * This class uses an xterm/ANSI X3.64/ECMA-48 type terminal to provide a
 * screen, keyboard, and mouse to TApplication.
 */
public class ECMA48Backend extends Backend {

    /**
     * Input events are processed by this Terminal.
     */
    private ECMA48Terminal terminal;

    /**
     * Public constructor.
     *
     * @param input an InputStream connected to the remote user, or null for
     * System.in.  If System.in is used, then on non-Windows systems it will
     * be put in raw mode; shutdown() will (blindly!) put System.in in cooked
     * mode.  input is always converted to a Reader with UTF-8 encoding.
     * @param output an OutputStream connected to the remote user, or null
     * for System.out.  output is always converted to a Writer with UTF-8
     * encoding.
     * @throws UnsupportedEncodingException if an exception is thrown when
     * creating the InputStreamReader
     */
    public ECMA48Backend(final InputStream input,
        final OutputStream output) throws UnsupportedEncodingException {

        // Create a terminal and explicitly set stdin into raw mode
        terminal = new ECMA48Terminal(input, output);

        // Keep the terminal's sessionInfo so that TApplication can see it
        sessionInfo = terminal.getSessionInfo();

        // Create a screen
        screen = new ECMA48Screen(terminal);

        // Clear the screen
        terminal.getOutput().write(terminal.clearAll());
        terminal.flush();
    }

    /**
     * Sync the logical screen to the physical device.
     */
    @Override
    public final void flushScreen() {
        screen.flushPhysical();
    }

    /**
     * Get keyboard, mouse, and screen resize events.
     *
     * @param queue list to append new events to
     * @param timeout maximum amount of time (in millis) to wait for an
     * event.  0 means to return immediately, i.e. perform a poll.
     */
    @Override
    public void getEvents(final List<TInputEvent> queue, final int timeout) {
        if (timeout > 0) {
            // Try to sleep, let the terminal's input thread wake me up if
            // something came in.
            synchronized (terminal) {
                try {
                    terminal.wait(timeout);
                    if (terminal.hasEvents()) {
                        // System.err.println("getEvents()");
                        terminal.getEvents(queue);
                    } else {
                        // If I got here, then I timed out.  Call
                        // terminal.getIdleEvents() to pick up stragglers
                        // like bare resize.
                        // System.err.println("getIdleEvents()");
                        terminal.getIdleEvents(queue);
                    }
                } catch (InterruptedException e) {
                    // Spurious interrupt, pretend it was like a timeout.
                    // System.err.println("[interrupt] getEvents()");
                    terminal.getIdleEvents(queue);
                }
            }
        } else {
            // Asking for a poll, go get it.
            // System.err.println("[polled] getEvents()");
            terminal.getEvents(queue);
        }
    }

    /**
     * Close the I/O, restore the console, etc.
     */
    @Override
    public final void shutdown() {
        terminal.shutdown();
    }

}
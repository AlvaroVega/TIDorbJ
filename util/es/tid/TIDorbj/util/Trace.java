/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 211 $
* Date: $Date: 2008-02-22 07:46:45 +0100 (Fri, 22 Feb 2008) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telefónica Investigación y Desarrollo
*     S.A.Unipersonal (Telefónica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/Licensing
*/    
package es.tid.TIDorbj.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;

public class Trace
{

    public final static int NONE = 0;

    public final static int ERROR = 1;

    public final static int USER = 2;

    public final static int DEBUG = 3;

    public final static int DEEP_DEBUG = 4;

    public final static int DUMP = 5;

    private final static String[] st_levels = { "NONE ", "ERROR ", "USER ",
                                               "DEBUG ", "DEEP_DEBUG ", "DUMP " };

    /**
     * Trace Log Stream.
     */
    private PrintWriter m_log;

    /**
     * Aplication Name printed in log messages.
     */
    protected String m_application_name;

    /**
     * Current trace level.
     */
    protected int m_level = NONE;

    /**
     * Include the date in log messages.
     */
    protected boolean m_include_date = true;

    /**
     * Invokes the stream flush each x times. It improves the file output
     * performance.
     */
    protected int m_do_flush_at = 1;

    /**
     * Printed messages counter
     */
    protected int m_message_count = 0;

    /**
     * Global static Trace instance.
     */
    public static Trace global = null;

    /** ****************************************************** */
    private boolean m_activated_trace = true;

    private String m_circular_trace_file_name = "";

    private CircularTraceFile m_circular_trace_file = null;

    /** ****************************************************** */

    /**
     * Private Constructor.
     */
    private Trace(PrintWriter log, String applicationName, int level)
    {
        m_log = log;
        m_level = level;
        m_application_name = applicationName;
        m_activated_trace = true;
    }

    /**
     * Creates the trace using the standard error output.
     * 
     * @param applicationName
     *            may be null
     * @param level
     *            trace level addopted
     * @return a new Trace instance
     */
    public static Trace createTrace(String applicationName, int level)
        throws IOException
    {
        PrintWriter writer = 
            new PrintWriter(
                new BufferedWriter(
                    new OutputStreamWriter(
                        new OutputStreamWrapper(System.err))));

        return new Trace(writer, applicationName, level);

    }

    /**
     * Creates the trace using the given output stream.
     * 
     * @param log
     *            the output stream where the messages will be written.
     * @param applicationName
     *            may be null
     * @param level
     *            trace level addopted
     * @return a new Trace instance
     */
    public static Trace createTrace(OutputStream log, String applicationName,
                                    int level)
        throws IOException
    {
        PrintWriter writer =
            new PrintWriter(
                new BufferedWriter(
                    new OutputStreamWriter(log)));

        return new Trace(writer, applicationName, level);

    }

    /**
     * Creates the trace using the given writer stream.
     * 
     * @param log
     *            the output stream where the messages will be written.
     * @param applicationName
     *            may be null
     * @param level
     *            trace level addopted
     * @return a new Trace instance
     */
    public static Trace createTrace(Writer log, String applicationName,
                                    int level)
        throws IOException
    {
        PrintWriter writer = new PrintWriter(new BufferedWriter(log));

        return new Trace(writer, applicationName, level);
    }

    /**
     * Creates the trace that will write messages in the given file.
     * 
     * @param fileName
     *            messages file name.
     * @param applicationName
     *            application name. It may be null.
     * @param level
     *            trace level addopted
     * @return a new Trace instance
     */
    public static Trace createTrace(String fileName, String applicationName,
                                    int level)
        throws IOException
    {
        PrintWriter writer =
            new PrintWriter(
                new BufferedWriter(
                    new FileWriter(fileName)));

        return new Trace(writer, applicationName, level);

    }

    /**
     * Creates the trace using the given circular trace file.
     * 
     * @param log
     *            the output stream where the messages will be written.
     * @param applicationName
     *            may be null
     * @param level
     *            trace level addopted
     * @return a new Trace instance
     */
    public static Trace createTrace(CircularTraceFile log,
                                    String applicationName, int level)
        throws IOException
    {
        PrintWriter writer = new PrintWriter(new BufferedWriter(log));

        return new Trace(writer, applicationName, level);

    }

    /**
     * Sets the trace level. If level is 0 traces will be deactivated.
     * 
     * @param level
     *            new trace level
     */
    public void setLevel(int level)
    {
        m_level = level;
    }
    public int getLevel()
    {
        return m_level;
    }

    /**
     * Activates/deactivates the date inclussion in trace messages.
     * 
     * @param value
     *            if <code>true</code> include it, otherwise do not.
     */
    public void setIncludeDate(boolean value)
    {
        m_include_date = value;
    }

    /**
     * Sets each times the stream flush operation is invoked. It improves the
     * file output performance.
     * 
     * @param value
     *            buffer flush round.
     */
    public void setDoFlushAt(int value)
    {
        if (value > 0)
            m_do_flush_at = value;
    }

    /**
     * @return the buffer flush round value.
     */
    public int getDoFlushAt()
    {
        return m_do_flush_at;
    }

    /**
     * @return the log stream used by the trace instance.
     */
    public PrintWriter getLog()
    {
        return m_log;
    }

    /**
     * Forces the buffered stream flushing.
     */
    public void flush()
    {
        if (m_log != null) {
            m_log.flush();
        }
    }

    /**
     * Closes the trace log stream.
     */
    public synchronized void close()
    {
        if (m_log != null) {
            m_log.println("LOG CLOSED");
            m_log.close();
            m_log = null;
        }
    }

    /**
     * Prints the message if the trace level is less or equal than current.
     * 
     * @param level
     *            message trace level
     * @param message
     *            the trace message to be written.
     */
    public void print(int level, String message)
    {
        if ((m_log == null) || (!m_activated_trace))
            return;

        if (level <= m_level) {
            synchronized (m_log) {
                try {
                    m_log.print('[');
                    m_log.print(m_message_count++);
                    m_log.print("] ");
                    m_log.print(st_levels[level]);
                    if (m_include_date) {
                        m_log.print((new Date()).toString());
                        m_log.print(" ");
                    }
                    if (m_application_name != null) {
                        m_log.print(m_application_name);
                        m_log.print(" ");
                    }
                    m_log.println(message);
                    if ((m_message_count % m_do_flush_at) == 0)
                        m_log.flush();
                }
                catch (Throwable th) {}
            }
        }
    }

    /**
     * Compounds and Prints the message chunks if the trace level is less or
     * equal than current.
     * 
     * @param level
     *            message trace level
     * @param message
     *            the trace message chunks to be written.
     */
    public void print(int level, String[] message)
    {

        if ((m_log == null) || (!m_activated_trace))
            return;

        if (level <= m_level) {
            synchronized (m_log) {
                try {
                    m_log.print('[');
                    m_log.print(m_message_count++);
                    m_log.print("] ");

                    m_log.print(st_levels[level]);

                    if (m_include_date) {
                        m_log.print((new Date()).toString());
                        m_log.print(" ");
                    }

                    if (m_application_name != null) {
                        m_log.print(m_application_name);
                        m_log.print(" ");
                    }

                    for (int i = 0; i < message.length; i++)
                        m_log.print(message[i]);

                    m_log.println();

                    if ((m_message_count % m_do_flush_at) == 0)
                        m_log.flush();

                }
                catch (Throwable th) {}
            }
        }
    }

    /**
     * Prints the Exception "e" and its backtrace to the _log print writer if
     * the trace level is less or equal than current.
     * 
     * @param level
     *            message trace level
     * @param e
     *            the Exception object with the backtrace to be written.
     */
    public void printStackTrace(int level, String msg, Throwable e)
    {
        if ((m_log == null) || (!m_activated_trace))
            return;

        if (level <= m_level) {
            synchronized (m_log) {
                try {
                    m_log.print('[');
                    m_log.print(m_message_count++);
                    m_log.print("] ");

                    m_log.print(st_levels[level]);

                    if (m_include_date) {
                        m_log.print((new Date()).toString());
                        m_log.print(" ");
                    }

                    if (m_application_name != null)
                        if (m_application_name.length() > 0) {
                            m_log.print(m_application_name);
                            m_log.print(" ");
                        }

                    m_log.print(msg);
                    m_log.println();

                    e.printStackTrace(m_log);

                    m_log.println();

                    if ((m_message_count % m_do_flush_at) == 0)
                        m_log.flush();
                }
                catch (Throwable th) {}
            }
        }
    }

    /**
     * Compounds and Prints the message chunks if the trace level is less or
     * equal than current.
     * 
     * @param level
     *            message trace level
     * @param message
     *            the trace message chunks to be written.
     */
    public void printStackTrace(int level, String[] message, Throwable ex)
    {
        if (m_log == null)
            return;

        if (level <= m_level) {
            synchronized (m_log) {
                try {

                    m_log.print('[');
                    m_log.print(m_message_count++);
                    m_log.print("] ");

                    m_log.print(st_levels[level]);

                    if (m_include_date) {
                        m_log.print((new Date()).toString());
                        m_log.print(" ");
                    }

                    if (m_application_name != null) {
                        m_log.print(m_application_name);
                        m_log.print(" ");
                    }

                    for (int i = 0; i < message.length; i++)
                        m_log.print(message[i]);

                    m_log.println();

                    ex.printStackTrace(m_log);

                    m_log.println();

                    if ((m_message_count % m_do_flush_at) == 0)
                        m_log.flush();

                }
                catch (Throwable th) {}
            }
        }
    }

    public static void printStackTrace_st(int level, String[] msg, Throwable e)
    {
        if (global != null)
            global.printStackTrace(level, msg, e);
    }

    public static void printStackTrace_st(int level, String msg, Throwable e)
    {
        if (global != null)
            global.printStackTrace(level, msg, e);
    }

    /**
     * Prints the messages in the global trace instance if the trace level is
     * less or equal than current.
     * 
     * @param level
     *            message trace level
     * @param message
     *            the trace message to be written.
     */

    public static void print_st(int level, String message)
    {
        if (global != null)
            global.print(level, message);
    }

    /**
     * Compounds and Prints the message chunks in the global trace instance if
     * the trace level is less or equal than current.
     * 
     * @param level
     *            message trace level
     * @param message
     *            the trace message chunks to be written.
     */

    public static void print_st(int level, String[] message)
    {
        if (global != null)
            global.print(level, message);
    }

    /** ********************************************************************* */
    /** **************** METODOS PARA GESTION REMOTA *********************** */
    /** ********************************************************************* */

    public void setTraceFile(String name)
        throws CannotProceedException
    {
        if (!m_circular_trace_file_name.equals(""))
            throw new CannotProceedException(
               "The Circular Trace File nas already been stablished");
        else {
            m_circular_trace_file_name = name;
            try {
                m_circular_trace_file = new CircularTraceFile(0, 0, name);
                //init circular trace files with default values (0,0)
            }
            catch (java.io.IOException ioex) {
                throw new 
                CannotProceedException("Cannot crate circular trace file");
            }
            PrintWriter writer = 
                new PrintWriter(
                    new BufferedWriter(m_circular_trace_file));
            m_log = writer;
        }
    } //set_trace_file

    public String getTraceFile()
        throws NoFileEspecifiedException
    {
        if (m_circular_trace_file_name.equals(""))
            throw new NoFileEspecifiedException();
        else
            return m_circular_trace_file_name;
    }

    public void activateTrace()
        throws AlreadyActivatedException
    {
        if (m_activated_trace)
            throw new AlreadyActivatedException();
        else
            m_activated_trace = true;
    } //activate_trace

    public void deactivateTrace()
        throws AlreadyDeactivatedException
    {
        if (!m_activated_trace)
            throw new AlreadyDeactivatedException();
        else
            m_activated_trace = false;
        return;
    }//deactivate_trace

    public void setTraceLevel(int tr_l)
        throws NotActivatedException,
        InvalidTraceLevelException
    {
        if ((tr_l < NONE) || (tr_l > DEEP_DEBUG))
            throw new InvalidTraceLevelException();

        if (!m_activated_trace)
            throw new NotActivatedException();

        m_level = tr_l;
        return;
    } //set_trace_level

    /** ********************************************************************* */
    /** ************** FIN METODOS GESTION REMOTA *************************** */
    /** ********************************************************************* */

    /**
     * Prints dump of a GIOP message if the trace level is less or equal than current.
     * 
     * @param level
     *            message trace level
     * @param message
     *            the trace message to be written.
     * @param length
     *            the trace length message to be written.
     */
    public void dump(int level, byte[] message, int length)
    {
        if ((m_log == null) || (!m_activated_trace))
            return;

        if (level <= m_level) {
            synchronized (m_log) {
                try {
                    
                    char line[] = new char[17];
                    int position = 0;

                    for(int i = 0; i < length; i++) {
                        
                        if ( ((i %  8) == 0) && (i > 0) )
                            m_log.print(" "); 
                        
                        if ( ((i % 16) == 0) && (i > 0) ){
                            line[16] = '\0';
                            m_log.print(" ");
                            for (int j = 0; j < 16; j++)
                                m_log.print(line[j]);
                            m_log.print('\n'); 
                            position = 0;
                        }
                        
                        // Fix bug [#399] Compilation error with JDK 1.4
                        //m_log.print(String.format("%02X", message[i]) + " ");
                        String hex = Integer.toHexString(message[i]);
                        int hlength = hex.length();
                        switch (hlength) {
                        case 0:
                            break;
                        case 1:
                            m_log.print("0" + hex);
                            break;
                        case 2:
                            m_log.print(hex);
                            break;
                        default:
                            m_log.print(hex.substring(hlength-2, hlength));
                        }
                        m_log.print(" ");

                        if ( (message[i] >= 32) && (message[i] <= 126) ) // Is in ascii char set?
                            line[position] = (char)message[i];
                        else
                            line[position] = '.';
                        
                        position++;
                    }
                    
                    line[position] = '\0';
                    
                    int blank_fill_length = 49 - (position*3) + (position > 8 ? 2 : 3);
                    for (int k = 1; k < blank_fill_length; k++)
                        m_log.print(" ");
                    for (int j = 0; j < position; j++)
                        m_log.print(line[j]);

                    m_log.print('\n');

                }
                catch (Throwable th) {}
            }
        }
    }



} //Trace

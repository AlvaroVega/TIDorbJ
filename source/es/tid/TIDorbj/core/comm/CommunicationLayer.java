/*
 * MORFEO Project
 * http://www.morfeo-project.org
 *
 * Component: TIDorbJ
 * Programming Language: Java
 *
 * File: $Source$
 * Version: $Revision: 274 $
 * Date: $Date: 2008-04-12 16:33:40 +0200 (Sat, 12 Apr 2008) $
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

package es.tid.TIDorbj.core.comm;


import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.iop.TaggedComponent;
;

/**
 * The interface of every <code>CommunicationLayer</code> implementation.
 *
 * @see CommunicationManager
 */
public interface CommunicationLayer {

	
	public abstract void setORB( TIDORB orb );
	public abstract TIDORB getORB();
	
	public void setServerModeEnabled( boolean value ) throws CommunicationException;
	public boolean isServerModeEnabled();
    
    public void shutdown( boolean waitForCompletion );
	
	
    /**
     * Attempts to make a connection to the given service.
     * The layer should return "null" if it realizes it is the wrong kind
     * of layer to connect to the given URL.  
     *
     * <P>The layer may throw a <code>CommunicationException</code> if it is the right 
     * layer to connect to the given URL but has trouble connecting to
     * the desired destination.
     *
     * @param url the URL of the service to which to connect
     * 
     * @return a <code>CommunicationDelegate</code> object which will manage
     *         requests to the service referenced.
     * @exception CommunicationException if an error occurs
     */
    CommunicationDelegate createDelegate(String url) throws CommunicationException;

    /**
     * Attempts to make a connection to the given service.
     * The layer should return "null" if it realizes it is the wrong kind
     * of layer to connect to the given URL.  
     *
     * <P>The layer may throw a <code>CommunicationException</code> if it is the right 
     * layer to connect to the given URL but has trouble connecting to
     * the desired destination.
     *
     * <P>The <code>java.util.Properties</code> argument can be used to pass
     * arbitrary string tag/value pairs as connection arguments.
     *
     * @param url the URL of the service to which to connect
     * @param info a list of arbitrary string tag/value pairs as arguments.
     * 
     * @return a <code>CommunicationDelegate</code> object which will manage
     *         requests to the service referenced.
     * @exception CommunicationException if an error occurs
     */
    CommunicationDelegate createDelegate(String url, java.util.Properties info)
        throws CommunicationException;


    /**
     * Attempts to make a connection to the given service.
     * The layer should return "null" if it realizes it is the wrong kind
     * of layer to connect to the service identified by the given reference.  
     *
     * <P>The layer may throw a <code>CommunicationException</code> if it is the right 
     * layer to connect to the given URL but has trouble connecting to
     * the desired destination.
     *
     * @param url the URL of the service to which to connect
     * 
     * @return a <code>CommunicationDelegate</code> object which will manage
     *         requests to the service referenced.
     * @exception CommunicationException if an error occurs
     */
    CommunicationDelegate createDelegate(IOR ior) throws CommunicationException;

    /**
     * Attempts to make a connection to the given service.
     * The layer should return "null" if it realizes it is the wrong kind
     * of layer to connect to the service identified by the given reference.  
     *
     * <P>The layer may throw a <code>CommunicationException</code> if it is the right 
     * layer to connect to the given URL but has trouble connecting to
     * the desired destination.
     *
     * <P>The <code>java.util.Properties</code> argument can be used to pass
     * arbitrary string tag/value pairs as connection arguments.
     *
     * @param ior the IOR of the service to which to connect
     * @param info a list of arbitrary string tag/value pairs as
     * connection arguments.
     * 
     * @return a <code>CommunicationDelegate</code> object which will manage
     *         requests to the service referenced.
     * @exception CommunicationException if an error occurs
     */
    CommunicationDelegate createDelegate(IOR ior, java.util.Properties info)
        throws CommunicationException;    
    
    /**
     * Retrieves whether the driver thinks that it can open a connection
     * to the resource identified by the given URL.  
     *
     * @param url the URL of the desired resource
     * @return <code>true</code> if this driver understands the given URL;
     *         <code>false</code> otherwise  
     * @exception CommunicationException if a database access error occurs
     */
    boolean accepts(String url) throws CommunicationException;


    /**
     * Retrieves whether the driver thinks that it can open a connection
     * to the resource identified by the given URL.  
     *
     * @param ior the IOR associated with the desired service
     * @return <code>true</code> if this driver understands the given URL;
     *         <code>false</code> otherwise  
     * @exception CommunicationException if a database access error occurs
     */
    boolean accepts(IOR ior) throws CommunicationException;
    
    /**
     * Creates an base ior containing the information regarding the
     * communication's configuration (ie, prototol, version, host & port used)
     * @return an ior filled with the communications info available for
     * current layer.
     * @throws CommunicationException
     */
    IOR createIOR( String repositoryId, ObjectKey key, TaggedComponent[] components )
    	throws CommunicationException;
    
    /**
     * Creates an base url containing the information regarding the
     * communication's configuration (ie, prototol, version, host & port used)
     * @return an String filled with the communications info available for
     * current layer.
     * @throws CommunicationException
     */
    String createURL( String repositoryId, ObjectKey key ) throws CommunicationException;
    
    
    //TODO: check the convenience of these method
    public String getInitialReference( IOR ior    ) throws CommunicationException;
    public String getInitialReference( String url ) throws CommunicationException;
    
    
    public boolean isLocal( IOR ior    ) throws CommunicationException;
    public boolean isLocal( String url ) throws CommunicationException;
    
    
    /**
     * Gets information about the possible properties for this driver.
     * <P>
     * The <code>getPropertyInfo</code> method is intended to allow a generic 
     * GUI tool to discover what properties it should prompt 
     * a human for in order to get 
     * enough information to connect to a certain service.  Note that depending on
     * the values the human has supplied so far, additional values may become
     * necessary, so it may be necessary to iterate though several calls
     * to the <code>getPropertyInfo</code> method.
     *
     * @param url the URL of the database to which to connect
     * @param info a proposed list of tag/value pairs that will be sent on
     *          connect open
     * @return an array of <code>DriverPropertyInfo</code> objects describing 
     *          possible properties.  This array may be an empty array if 
     *          no properties are required.
     * @exception CommunicationException if some error occurs
     */
    PropertyInfo[] getPropertiesInfo();
    PropertyInfo   getPropertyInfo( String name );


    /**
     * Retrieves the Layer's major version number. Initially this should be 1.
     *
     * @return this Layer's major version number
     */
    int getMajorVersion();

    /**
     * Gets the Layer's minor version number. Initially this should be 0.
     * @return this Layers's minor version number
     */
    int getMinorVersion();

    /**
     * Gets the Layers identification string. 
     */
    String getId();

    /**
     * Gets the Layers identification string. 
     */
    void dump(java.io.PrintWriter writer);

    
}

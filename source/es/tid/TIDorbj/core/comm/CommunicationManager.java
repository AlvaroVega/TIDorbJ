/*
 * MORFEO Project
 * http://www.morfeo-project.org
 *
 * Component: TIDorbJ
 * Programming Language: Java
 *
 * File: $Source$
 * Version: $Revision: 478 $
 * Date: $Date: 2011-04-29 16:42:47 +0200 (Fri, 29 Apr 2011) $
 * Last modified by: $Author: avega $
 *
 * (C) Copyright 2004 Telefnica Investigacin y Desarrollo
 *     S.A.Unipersonal (Telefnica I+D)
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

import java.util.Hashtable;

import es.tid.TIDorbj.core.ConfORB;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayer;
import es.tid.TIDorbj.core.comm.ssliop.SSLIOPCommunicationLayer;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.util.Trace;

/**
 * Based on the jdbc interface and driver registry implementation,
 * CommunicationManager will provide a way to uncouple the underlying
 * communications infraestructure from the logic regarding the processing,
 * execution and result retrieval of incoming and outgoing requests.
 * 
 * Through the <code>CommunicationManager</code>, ORB will be able to obtain a
 * concrete <code>CommunicationDelegate</code> depending on the URL / IOR 
 * provided, so that the actual communication process will be managed by the 
 * underlying implementation
 * 
 * From the internal side of each <code>CommunicationLayer</code>, it will 
 * provide a reference to a <code>RequestDispatcher</code> object that will hold
 * the process of request enqueual into ORB's infraestructure
 * 
 * @author Juan Pablo Rojas
 * 
 * 
 */
public class CommunicationManager {

	
    private boolean offline;

    /**
     * Attempts to establish a connection to the given database URL. The
     * <code>CommunicationManager</code> attempts to select an appropriate
     * driver from the set of registered layers.
     * 
     * @param url
     *            a service url of the form
     *            <code> protocol:<em>subprotocol</em>:<em>subname</em></code>
     * @return a connection to the URL
     * @exception CommunicationException
     *                if a database access error occurs
     */
    public synchronized CommunicationDelegate createDelegate(String url)
        throws CommunicationException {
        
        java.util.Properties info = new java.util.Properties();
        
        return ( createDelegate( url, info ) );
    }

    /**
     * Attempts to create a delegate for the given URL. The
     * <code>CommunicationManager</code> attempts to select the appropriate
     * <code>CommunicationLayer</code> from the set of registered layers.
     * 
     * @param ior a service ior
     * 
     * @return a CommunicationDelegate to the desired service
     * @exception CommunicationException
     *                if a database access error occurs
     */
    public synchronized CommunicationDelegate createDelegate( IOR ior )
        throws CommunicationException {
        
        java.util.Properties info = new java.util.Properties();
        
        return ( createDelegate( ior, info ) );
    }
    
    /**
     * Attempts to locate a layer that understands the given URL. The
     * <code>CommunicationManager</code> attempts to select an appropriate
     * driver from the set of registered layers.
     * 
     * @param url
     *            a URL of the form
     *            <code>:<em></em>:<em></em></code>
     * @return a <code>CommunicationsLayer</code> object representing a layer that can
     *         connect to the given service
     * @exception CommunicationException
     *                if an error occurs
     */
    public synchronized CommunicationLayer getLayer(String url)
        throws CommunicationException {
        println("CommunicationManager.getLayer(\"" + url + "\")");
        
        if (!initialized) {
            initialize();
        }
        
        // Walk through the loaded availableLayers attempting to locate someone
        // who understands the given URL.
        for (int i = 0; i < availableLayers.size(); i++) {
            LayerInfo li = (LayerInfo) availableLayers.elementAt(i);
            try {
                println("    trying " + li);
                if (li.layer.accepts(url)) {
                    // Success!
                    println("getLayer returning " + li);
                    return (li.layer);
                }
            } catch (CommunicationException ex) {
                // Drop through and try the next driver.
            }
        }
        
        println("getLayer: no suitable layer");
        throw new CommunicationException("No suitable layer");
    }
    
    
    
    /**
     * Attempts to locate a layer that matches the given Id. .
     * 
     * @param id
     *
     * @return a <code>CommunicationsLayer</code> object representing a layer 
     *         identified as id, or <code>null</code> if the layer wasn't found 
     */
    public CommunicationLayer getLayerById( String id ) {

        // TODO trace it
        // println("CommunicationManager.getLayerById(\"" + id + "\")");

        if (!initialized) {
            initialize();
        }
        CommunicationLayer desiredLayer = null;
        // Walk through the loaded availableLayers attempting to locate someone
        // who understands the given URL.
        for (int i = 0; i < availableLayers.size(); i++) {
            LayerInfo li = (LayerInfo) availableLayers.elementAt(i);
			
            //println( "    trying " + li );
            if (li.layer.getId().equals( id )) {
                // Success!
                //	println( "getLayer returning " + li );
                desiredLayer = li.layer;
            }
			
        }
        return desiredLayer;
    }//getLayerById
	
    
    /**
     * Attempts to locate a layer that can connect to the service bound to the
     * IOR object passed as an argument. The <code>CommunicationManager</code> 
     * attempts to select an appropriate layer from the set of registered communication
     * layers.
     * 
     * @param ior
     *            a service IOR
     * @return a <code>CommunicationLayer</code> object representing a driver that can
     *         connect to the given URL
     * @exception CommunicationException
     *                if a database access error occurs
     */
    public synchronized CommunicationLayer getLayer( IOR ior )
        throws CommunicationException {
        
        println("CommunicationManager.getDriver(\"" + ior + "\")");

        if (!initialized) {
            initialize();
        }

        // Walk through the loaded availableLayers attempting to locate someone
        // who understands the given URL.
        for (int i = 0; i < availableLayers.size(); i++) {
            LayerInfo li = (LayerInfo) availableLayers.elementAt(i);
            try {
                println( "    trying " + li );
                if (li.layer.accepts( ior )) {
                    // Success!
                    println( "getLayer returning " + li );
                    return ( li.layer );
                }
            } catch ( CommunicationException ex ) {
                // Drop through and try the next driver.
            }
        }

        println( "getLayer: no suitable layer" );
        throw new CommunicationException( "No suitable layer" );
    }
	
    /**
     * Registers the given layer with the <code>CommunicationManager</code>.
     * A newly-loaded driver class should call the method
     * <code>registerDriver</code> to make itself known to the
     * <code>CommunicationManager</code>.
     * 
     * @param layer
     *            the new CommunicationLayer that is to be registered with the
     *            <code>CommunicationManager</code>
     * @exception CommunicationException
     *                if an error occurs
     */
    public synchronized void registerLayer(CommunicationLayer layer)
        throws CommunicationException {
		
        if (!initialized) {
            initialize();
        }

        LayerInfo li = new LayerInfo();
        li.layer = layer;
        li.layerClass = layer.getClass();
        li.layerClassName = li.layerClass.getName();
        availableLayers.addElement(li);
        println( "registerLayer: " + li );
    }

    /**
     * Drops a layer from the <code>CommunicationManager</code>'s list.
     * Applets can only deregister availableLayers from their own classloaders.
     * 
     * @param layer
     *            the CommunicationLayer to drop
     * @exception CommunicationException
     *                if an error occurs
     */
    public synchronized void deregisterLayer(CommunicationLayer layer)
        throws CommunicationException {

        println( "CommunicationManager.deregisterLayer: " + layer );

        // Walk through the loaded availableLayers.
        int i;
        LayerInfo li = null;
        for (i = 0; i < availableLayers.size(); i++) {
            li = (LayerInfo) availableLayers.elementAt(i);
            if (li.layer == layer) {
                break;
            }
        }
        // If we can't find the driver just return.
        if (i >= availableLayers.size()) {
            println("    couldn't find driver to unload");
            return;
        }

        // Remove the layer. Other entries in availableLayers get shuffled down.
        availableLayers.removeElementAt(i);
    }//deregisterLayer

    /**
     * Prints a message to the current log stream.
     * 
     * @param message
     *            a log or tracing message
     */
    public void println( String message ) {
        if ( this.orb != null ){
            if ( this.orb.m_trace != null ){
                this.orb.m_trace.print( Trace.DEEP_DEBUG, message );
            }
        }
    }
    
    public void println( String msg, Throwable th ){
        if ( this.orb != null ){
            if ( this.orb.m_trace != null ){
                this.orb.m_trace.printStackTrace( Trace.DEEP_DEBUG, msg, th );
            }
        }
    }

    //------------------------------------------------------------------------
    //Initialization stuff

    private void loadInitialLayers() {
        String layers;
        try {
            //if (this.orb.m_conf.
            layers = this.orb.m_conf.comm_layers;
        } catch (Exception ex) {
            layers = null;
        }
        println("CommunicationManager.initialize: = " + ConfORB.comm_layers_name + ": " + layers );
        if (layers == null) {
            return;
        }
        while (layers.length() != 0) {
            int x = layers.indexOf(':');
            String layer;
            if (x < 0) {
                layer = layers;
                layers = "";
            } else {
                layer = layers.substring(0, x);
                layers = layers.substring(x + 1);
            }
            if (layer.length() == 0) {
                continue;
            }
            try {
                println("CommunicationManager.Initialize: loading " + layer);
                Class currentLayerClass = Class.forName(layer);
                //Class currentLayerClass = Class.forName(layer, true, ClassLoader.getSystemClassLoader());
                if ( currentLayerClass != null ){
                    CommunicationLayer currentLayer; 
                    currentLayer = (CommunicationLayer) currentLayerClass.newInstance();

                    LayerInfo li = new LayerInfo();
                    li.layerClassName = layer;
                    li.layerClass = currentLayerClass;
                    li.layer = currentLayer;
                    availableLayers.add( li );
                    //TODO: modify apis to provide a reference to it's own layer
                    //as soon as possible, at this moment, they access it's pare
                    //nt layer through communicationManager, so it must be added
                    //prior to initialization (performed at setOrb's).
                    currentLayer.setORB( this.orb );
                                    
                    currentLayer.dump(this.orb.m_trace.getLog());
                    
                }
            } catch (Exception ex) {
                println("CommunicationManager.Initialize: load failed: " + ex, ex);
            }
        }
    }

    /**
     * Attempts to establish a connection to the given service URL. The
     * <code>CommunicationManager</code> attempts to select an appropriate
     * driver from the set of registered JDBC availableLayers.
     * 
     * @param url
     *            a service url of the form
     *            <code> :<em></em>:<em></em></code>
     * @param info
     *            a list of arbitrary string tag/value pairs as connection
     *            arguments; 
     * @return a <code>CommunicationDelegate</code> instance which will manage
     * 			  requests to the specified service 
     * @exception SQLException
     *                if a database access error occurs
     */
    public synchronized CommunicationDelegate createDelegate(
                                                             String url,
                                                             java.util.Properties info) 
	throws CommunicationException {
        
        if (url == null) {
            throw new CommunicationException( "Parameter 'url' cannot be null" );
        }

        println("CommunicationManager.connect(\"" + url + "\")");

        if (!initialized) {
            initialize();
        }

        // Walk through the loaded availableLayers attempting to make a connection.
        // Remember the first exception that gets raised so we can reraise it.
        Exception reason = null;
        CommunicationLayer commLayer;
        for (int i = 0; i < availableLayers.size(); i++) {
            LayerInfo li = (LayerInfo) availableLayers.elementAt(i);

            // If the caller does not have permission to load the driver then
            // skip it.
            try {
                println( "    trying " + li );
                if ( li.layer.accepts( url ) ){
                    CommunicationDelegate result = li.layer.createDelegate(url, info);
                    if (result != null) {
                        // Success!
                        println( "connect returning " + li );
                        return (result);
                    }	
                }
            } catch (CommunicationException ce ){
                if ( reason != null ){
                    reason = ce;
                }
            }
        }

        // if we got here nobody could connect.
        if (reason != null) {
            println( "connect failed: " + reason );
            throw new CommunicationException( "connect failed", reason );
        }

        println( "connect: no suitable driver" );
        throw new CommunicationException( "No suitable driver" );
    }
	
    /**
     * Attempts to establish a connection to the given service IOR. The
     * <code>CommunicationManager</code> attempts to select an appropriate
     * CommunicationLayer from the set of registered layers.
     * 
     * @param ior the service IOR
     *            
     *            <code> :<em></em>:<em></em></code>
     * @param info
     *            a list of arbitrary string tag/value pairs as connection
     *            arguments; 
     * @return a <code>CommunicationDelegate</code> instance which will manage
     * 			  requests to the specified service 
     * @exception SQLException
     *                if a database access error occurs
     */
    public synchronized CommunicationDelegate createDelegate(
                                                             IOR ior,
                                                             java.util.Properties info) 
	throws CommunicationException {
		
        if (ior == null) {
            throw new CommunicationException( "Parameter 'ior' cannot be null" );
        }

        println("CommunicationManager.connect(\"" + ior + "\")");

        if (!initialized) {
            initialize();
        }

        // Walk through the loaded availableLayers attempting to make a connection.
        // Remember the first exception that gets raised so we can reraise it.
        Exception reason = null;
        CommunicationLayer commLayer;
        for (int i = 0; i < availableLayers.size(); i++) {
            LayerInfo li = (LayerInfo) availableLayers.elementAt(i);

            // If the caller does not have permission to load the driver then
            // skip it.
            try {
                println( "    trying " + li );
                if ( li.layer.accepts( ior ) ){
                    CommunicationDelegate result = li.layer.createDelegate(ior, info);
                    if (result != null) {
                        // Success!
                        println( "connect returning " + li );
                        return (result);
                    }	
                }
            } catch (CommunicationException ce ){
                if ( reason != null ){
                    reason = ce;
                }
            }
        }

        // if we got here nobody could connect.
        if (reason != null) {
            println( "connect failed: " + reason );
            throw new CommunicationException( "connect failed", reason );
        }

        println( "connect: no suitable driver" );
        throw new CommunicationException( "No suitable layer" );
    }

    //TODO: check the convenience of these method
    public String getInitialReference( IOR ior    ) throws CommunicationException {
        if (!initialized) {
            initialize();
        }

        String initialReference;
        CommunicationLayer peerLayer; 
        peerLayer = this.getLayer( ior );
        if ( peerLayer != null ){
            initialReference = peerLayer.getInitialReference( ior ); 
        } else {
            initialReference = null;
        }
        return initialReference;
    }

    public String getInitialReference( String url ) throws CommunicationException {

        if (!initialized) {
            initialize();
        }

        String initialReference;
        CommunicationLayer peerLayer; 
        peerLayer = this.getLayer( url );
        if ( peerLayer != null ){
            initialReference = peerLayer.getInitialReference( url ); 
        } else {
            initialReference = null;
        }
        return initialReference;
    }
    
    
    public boolean isLocal( IOR ior    ) throws CommunicationException {

        if (!initialized) {
            initialize();
        }

        boolean isLocal;
        CommunicationLayer peerLayer; 
        peerLayer = this.getLayer( ior );
        if ( peerLayer != null ){
            isLocal = peerLayer.isLocal( ior ); 
        } else {
            throw new CommunicationException( "Invalid ior: no suitable layer" );
        }
        return isLocal;
    }

    
    public boolean isLocal( String url ) throws CommunicationException{

        if (!initialized) {
            initialize();
        }

        boolean isLocal;
        CommunicationLayer peerLayer; 
        peerLayer = this.getLayer( url );
        if ( peerLayer != null ){
            isLocal = peerLayer.isLocal( url ); 
        } else {
            throw new CommunicationException( "Invalid url: no suitable layer" );
        }
        return isLocal;
    }
    
    /**
     * @param b
     */
    public synchronized void setServerModeEnabled( boolean b ) throws CommunicationException {

        if (!initialized) {
            initialize();
        }

        if ( this.offline ) {
            
            this.getLayerById( IIOPCommunicationLayer.ID ).setServerModeEnabled( 
                                                                                b 
                                                                                 );
            
            int layersCount = this.availableLayers.size();
            LayerInfo actualLayerInfo;
            for ( int i = layersCount - 1; i >= 0; i-- ){
                actualLayerInfo = ( LayerInfo )this.availableLayers.get( i );
                actualLayerInfo.layer.setServerModeEnabled( b );
            }
            this.offline = false;
        }
    }//setServerModeEnabled
    
    
    /**
     * @param waitForCompletion
     */
    public synchronized void shutdown( boolean waitForCompletion ) {
        
        if (!initialized) {
            initialize();
        }

        if ( !this.offline ) {
            int layersCount = this.availableLayers.size();
            LayerInfo actualLayerInfo;
            for ( int i = layersCount - 1; i >= 0; i-- ){
                actualLayerInfo = ( LayerInfo )this.availableLayers.get( i );
                actualLayerInfo.layer.shutdown( waitForCompletion );
            }
            this.offline = true;
        }
    }

    /**
     * 
     */
    public void destroy() {
            
        int layersCount = this.availableLayers.size();
        LayerInfo actualLayerInfo;
        for ( int i = layersCount - 1; i >= 0; i-- ){
            actualLayerInfo = ( LayerInfo )this.availableLayers.get( i );
            actualLayerInfo.layer.shutdown(false);
        }
  
    }
    
    public synchronized RequestDispatcher getRequestDispatcher()  {
        if ( this.dispatcher == null ){
            this.dispatcher = new RequestDispatcher( this.orb );
        } 
        return this.dispatcher;
    }//getRequestDispatcher
	
	
    public static synchronized CommunicationManager getInstance( TIDORB orb ){
        if ( CommunicationManager.instances == null ){
            CommunicationManager.instances = new Hashtable();
        }
        if ( orb != null ){
            CommunicationManager manager;
            manager = ( CommunicationManager )CommunicationManager.instances.get( orb ); 
            if ( manager == null ){
                manager = new CommunicationManager( orb );
                CommunicationManager.instances.put( orb, manager );
            }
            return manager;
        } else {
            throw new IllegalArgumentException( "Parameter 'orb' cannot be null" );
        }
    }//setOrb
	
    public synchronized String toString(){
        StringBuffer toString;
        toString = new StringBuffer();
        int layersCount = this.availableLayers.size();
        LayerInfo actualLayerInfo;
        for ( int i=0; i < layersCount; i++ ){
            actualLayerInfo = ( LayerInfo )this.availableLayers.get( i );
            toString.append( actualLayerInfo.layer);
            if ( i < layersCount - 1 ){
                toString.append( ' ' );
            }
        }
        return toString.toString();
    }
	
    private CommunicationManager( TIDORB orb ){
        this.orb = orb;		
    }
	
    public TIDORB getORB(){
        return this.orb;
    }
		
    // Class initialization.
    private void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        this.loadInitialLayers();
        this.offline = true;
        println(" CommunicationManager initialized");
    }


    private TIDORB orb = null;
	
    private RequestDispatcher dispatcher = null;
	
    private java.util.Vector availableLayers = new java.util.Vector();
	
    private static Hashtable instances;
	
    private boolean initialized = false;

    

}

// LayerInfo is a package-private support class.

class LayerInfo {
    CommunicationLayer layer;

    Class layerClass;

    String layerClassName;

    public String toString() {
        return ("driver[className=" + layerClassName + "," + layer + "]");
    }

}

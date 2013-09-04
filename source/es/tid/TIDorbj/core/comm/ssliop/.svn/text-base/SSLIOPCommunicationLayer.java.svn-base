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
package es.tid.TIDorbj.core.comm.ssliop;

import java.util.Properties;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ORBPackage.InvalidName;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.CommunicationDelegate;
import es.tid.TIDorbj.core.comm.CommunicationException;
import es.tid.TIDorbj.core.comm.CommunicationLayer;
import es.tid.TIDorbj.core.comm.PropertyInfo;
import es.tid.TIDorbj.core.comm.RequestDispatcher;
import es.tid.TIDorbj.core.comm.giop.ServiceContextList;
import es.tid.TIDorbj.core.comm.iiop.CommLayer;
import es.tid.TIDorbj.core.comm.iiop.IIOPCorbaloc;
import es.tid.TIDorbj.core.comm.iiop.IIOPIOR;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayer;
import es.tid.TIDorbj.core.iop.DefaultIOR;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.iop.TaggedComponent;
import es.tid.TIDorbj.core.util.Corbaloc;

/**
 * Entry point for the SSLIOPCommunicationLayer
 * 
 * @author 
 *  
 * 
 */
public class SSLIOPCommunicationLayer implements CommunicationLayer {

    public static String ID = "SSLIOPCommunicationLayer";
    
    /*
     * used for incoming requests notifications
     */
    private RequestDispatcher dispatcher;
    
    /*
     * the underlying ORB implementation 
     */
    private TIDORB orb;
    
    private CommLayer commLayer;

    private SSLIOPCommunicationLayerPropertiesInfo propertiesInfo;

    /**
     * 
     */
    public SSLIOPCommunicationLayer() {
        IIOPCommunicationLayer.ID = "SSLIOPCommunicationLayer";
    }

    public void setORB(TIDORB orb) {
        if ( orb != null ){
            if ( this.orb != null ){
                this.commLayer.shutdown();
                this.propertiesInfo = null;
                this.commLayer = null;
            }
            this.orb = orb;
            this.propertiesInfo = SSLIOPCommunicationLayerPropertiesInfo.getInstance();
            this.orb.m_conf.fillPropertyInfo( 
                (PropertyInfo[])
                    this.propertiesInfo.map.values().toArray( 
                        new PropertyInfo[]{} 
                        ) 
                );
            
            
            this.commLayer = new CommLayer( this.orb );
        } else {
            throw new IllegalArgumentException( "Parameter 'orb' cannot be null" );
        }
    }//setORB
    
    public TIDORB getORB() {
        return this.orb;
    }//getORB
    
    public CommLayer getCommLayer(){
        return this.commLayer;
    }
    
    
    public void setServerModeEnabled( boolean value ){
        if ( value ){
            this.commLayer.getExternalLayer().initServerListener();
        }
    }
    
    public boolean isServerModeEnabled(){
        return this.commLayer.getExternalLayer().hasServerListener();
    }
    
    public synchronized ServiceContextList getBidirectionalService() {
    	return this.commLayer.getExternalLayer().getBidirectionalService();
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#connect(java.lang.String)
     */
    public CommunicationDelegate createDelegate(String url) throws CommunicationException {
        CommunicationDelegate delegate;
        if ( this.accepts( url ) ){
            IOR ior;
            try {
                if ( url.startsWith( "corbaloc:tidorb:" ) ){
                    ior = Corbaloc.getIOR( url );
                } else if ( url.startsWith( "corbaloc:ssliop:" )) {
                    ior = SSLIOPCorbaloc.getIOR( url );
                } else if ( url.startsWith( "corbaloc:" )) {
                    ior = IIOPCorbaloc.getIOR( url );
                } else {
                    ior = IIOPIOR.fromString(this.orb, url);
                }
            } catch ( InvalidName in ){
                throw new CommunicationException( "Invalid url: " + in.getMessage(), in );
            }
            delegate = createDelegate( ior );
        } else {
            delegate = null;
        }
        return delegate;
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#connect(java.lang.String, java.util.Properties)
     */
    public CommunicationDelegate createDelegate(String url, Properties info) 
        throws CommunicationException 
    {
        return createDelegate( url );
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#createDelegate(es.tid.TIDorbj.core.iop.IOR)
     */
    public CommunicationDelegate createDelegate(IOR ior) 
        throws CommunicationException 
    {
        SSLIOPCommunicationDelegate delegate = new SSLIOPCommunicationDelegate( this );
        delegate.setReference( ior );
        return delegate;
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#createDelegate(es.tid.TIDorbj.core.iop.IOR, java.util.Properties)
     */
    public CommunicationDelegate createDelegate(IOR ior, Properties info) 
        throws CommunicationException 
    {
        return createDelegate( ior );
    }
	
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#accepts(java.lang.String)
     */
    public boolean accepts(String url) 
        throws CommunicationException 
    {
        return url!= null && 
            ( url.startsWith( "corbaloc:ssliop:"   ) ||
              url.startsWith( "corbaloc:tidorb:" ) ||
              url.startsWith( "corbaloc::"       ) ||
              ( 
               url.startsWith( "IOR" ) && 
               this.accepts( DefaultIOR.fromString( this.orb, url ) )
               )
              );
    }//accepts

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#accepts(org.omg.IOP.IOR)
     */
    public boolean accepts(IOR ior) 
        throws CommunicationException 
    {
        boolean accepted;
        accepted = ior instanceof IIOPIOR;
        
        int profilesCount = ior.memberCount();
        for (int i = 0; !accepted && i < profilesCount; i++) {
            accepted = ior.getProfile( i ).tag == org.omg.IOP.TAG_INTERNET_IOP.value;
        }
        
        return accepted;
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#createIOR(java.lang.String, es.tid.TIDorbj.core.comm.iiop.ObjectKey)
     */
    public IOR createIOR(String repositoryId, 
                         ObjectKey key, 
                         TaggedComponent[] components) 
	throws CommunicationException 
    {
        return this.commLayer.getExternalLayer().createIOR(
                                                           repositoryId,
                                                           key,
                                                           components
                                                           );
    }//createIOR
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#createURL(java.lang.String, es.tid.TIDorbj.core.comm.iiop.ObjectKey)
     */
    public String createURL(String repositoryId, ObjectKey key) 
        throws CommunicationException 
    {
        throw new NO_IMPLEMENT();
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#getInitialReference(es.tid.TIDorbj.core.iop.IOR)
     */
    public String getInitialReference( IOR ior ) 
        throws CommunicationException 
    {
        throw new NO_IMPLEMENT();
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#getInitialReference(java.lang.String)
     */
    public String getInitialReference( String url ) 
        throws CommunicationException 
    {
        String initialReference;
        try {
            initialReference = IIOPCorbaloc.getObjectId( url );
        } catch ( Throwable th ){
            initialReference = null;
        }
        return initialReference;
    }

    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#isLocal(es.tid.TIDorbj.core.iop.IOR)
     */
    public boolean isLocal(IOR ior) 
        throws CommunicationException 
    {
        return ior != null && this.accepts( ior ) && this.commLayer.isLocal( (IIOPIOR)ior );
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#isLocal(java.lang.String)
     */
    public boolean isLocal(String url) throws CommunicationException {
        boolean isLocal;
        if ( this.accepts( url ) ){
            IOR ior;
            try {
                if ( url.startsWith( "corbaloc:tidorbj:" ) ){
                    ior = Corbaloc.getIOR( url );
                } else {
                    ior = IIOPCorbaloc.getIOR( url );
                }
            } catch ( InvalidName in ){
                throw new CommunicationException( "Invalid url: " + in.getMessage(), in );
            }
            isLocal = this.isLocal( ior );
        } else {
            isLocal = false;
        }
        return isLocal;
    }//isLocal
    
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#getPropertyInfo(java.lang.String, java.util.Properties)
     */
    public PropertyInfo[] getPropertiesInfo() {
        return ( PropertyInfo[] )this.propertiesInfo.map.entrySet().toArray( new PropertyInfo[0] );
    }//getPropertiInfo
    
    public PropertyInfo getPropertyInfo( String name){
        return ( PropertyInfo )this.propertiesInfo.map.get( name );
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#getMajorVersion()
     */
    public int getMajorVersion() {
        return 1;
    }//getMajorVersion
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#getMinorVersion()
     */
    public int getMinorVersion() {
        return 0;
    }//getMinorVersion
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#getId()
     */
    public String getId() {
        return SSLIOPCommunicationLayer.ID;
    }
    
    public String toString(){
        
        String hostSpec; 
        hostSpec = this.propertiesInfo.hostName.value; 
        if ( hostSpec == null ){
            hostSpec = this.propertiesInfo.hostAddress.value;
        }
        if ( hostSpec == null ){
            try {
                hostSpec = java.net.InetAddress.getLocalHost().getHostName();
            }
            catch (Exception e) {}
        }
        
        StringBuffer toString = new StringBuffer( "[ " )
            .append( SSLIOPCommunicationLayer.ID )
            .append( " ( ssliop://" ) // TODO: change to ssliop://
                    .append( hostSpec )
                    .append( ':' )
                    .append( this.propertiesInfo.ssl_port.value )
                .append( " )" )
            .append( " ]");
        
        return toString.toString();
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationLayer#shutdown(boolean)
     */
    public void shutdown( boolean waitForCompletion ) {
        if ( this.commLayer != null ){
            if ( waitForCompletion ){
                this.commLayer.shutdown();
            } else {
                this.commLayer.destroy();
            }
            this.commLayer = null;
        }
    }


    /**
     * PRIVATE METHODS
     * @author avega
     */
    
    public void dump(java.io.PrintWriter writer){
        this.propertiesInfo.dump(writer);
    }
}

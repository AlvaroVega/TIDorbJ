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
* (C) Copyright 2004 Telef�nica Investigaci�n y Desarrollo
*     S.A.Unipersonal (Telef�nica I+D)
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
package es.tid.TIDorbj.core;

import java.applet.Applet;
import java.util.Arrays;
import java.util.Properties;
import java.util.Vector;

import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.PolicyError;
import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.comm.PropertyInfo;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayerPropertiesInfo;
import es.tid.TIDorbj.core.messaging.RelativeRoundtripTimeoutPolicyImpl;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.core.util.InitialReference;
import es.tid.TIDorbj.util.SystemProperties;
import es.tid.TIDorbj.util.Trace;
import es.tid.TIDorbj.util.UTC;

/**
 * Configuration set for TIDorb.
 * <p>
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class ConfORB
{

    /**
     * ORB Policies
     */

    PolicyContext policy_context;

    /**
     * Default ORB Policies
     */

    final static PolicyContext st_default_policy_context = 
        createDefaultPolicies();

    /**
     * TIDorb ORB Identifier: 666. This value must be registered in OMG.
     */

    public final static es.tid.TIDorbj.core.iop.ORBComponent ORB_TYPE = 
        new es.tid.TIDorbj.core.iop.ORBComponent(1414087680);

    /**
     * TIDorb ORB_id
     */

    public final static String orb_id_name = "-ORBid";

    public final static String DEFAULT_ORB_ID = "TIDorbJ";

    public String orb_id = DEFAULT_ORB_ID;

    /**
     * TIDorb Initial References
     */

    public java.util.Vector initial_references = new java.util.Vector();

    /**
     * TIDorb Default Initial References
     */
    public final static String init_refs_name = "-ORBInitRef";

    /**
     * TIDorb Default Initial References
     */

    public String default_initial_reference = null;

    public final static String def_init_ref_name = "-ORBDefaultInitRef";

    /**
     * NamingService Property name.
     */

    public final static String naming_service_name = 
        "es.tid.TIDorbj.naming_service";

    /**
     * Default chunk size for CDR buffers. Default value: <code>256</code>.
     */

    public final static int DEFAULT_BLOCK_SIZE = 256;

    /**
     * Chunk size for CDR buffers.
     * <p>
     * Values must be > 256 and multiple of 8
     */

    public int block_size = DEFAULT_BLOCK_SIZE;

    /**
     * Block_size Property name.
     */

    public final static String block_size_name = "es.tid.TIDorbj.block_size";

    /**
     * Default exhaustive TypeCode matching.
     */
    public final static boolean DEFAULT_EXHAUSTIVE_EQUAL = false;

    /**
     * Make or not an exhaustive TypeCode matching:
     * <ul>
     * <li>If <code>true</code> two TypeCodes equals each other if its
     * RepositoryId and member names and TypeCodes equals.
     * <li>If <code>false</code> only RepositoryIds are compared.
     * </ul>
     * Values must be "true" or "false".
     */

    public boolean exhaustive_equal = DEFAULT_EXHAUSTIVE_EQUAL;

    /**
     * Exhaustive equal Property name.
     */

    public final static String exhaustive_equal_name =
        "es.tid.TIDorbj.exhaustive_equal";


    /**
     * Maximun time, in miliseconds, waiting a request reply.
     * <p>
     * Values must be greater or equal than 0. Default value: <code>5000</code>.
     */

    public final static long DEFAULT_MAX_BLOCKED_TIME = 5000;

    /**
     * max_blocked_time Property name.
     */

    public final static String max_blocked_time_name =
        "es.tid.TIDorbj.max_blocked_time";



    public long max_blocked_time = DEFAULT_MAX_BLOCKED_TIME;

    /**
     * Default TypeCode Cache Size. Default value: <code>0</code>, no cache.
     */

    public final static int DEFAULT_TYPECODE_CACHE_SIZE = 0;

    /**
     * Maximum of TypeCodes stored in the TypeCodeCache.
     * <p>
     * Values must be greater or equal than 0.
     */

    public int typecode_cache_size = DEFAULT_TYPECODE_CACHE_SIZE;

    /**
     * typecode_cache_size Property name.
     */

    public final static String typecode_cache_size_name =
        "es.tid.TIDorbj.typecode_cache_size";

    
    /**
     * Comma separated list of CommunicationLayer implementatios to be loaded at
     * ORB's startup. By default, only IIOP comm layer will be loaded.
     */
    public final static String DEFAULT_COMM_LAYER = 
        "es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayer";
    
    public String comm_layers = DEFAULT_COMM_LAYER;
    
    public final static String comm_layers_name =
        "es.tid.TIDorbj.comm_layers";
    

    /**
     * BidirectionalPolicy used. Values allowed NORMAL or BOTH
     */
    public final static short DEFAULT_BIDIRECTIONAL = 
        org.omg.BiDirPolicy.NORMAL.value;
    /**
     * BIDIRECTIONAL Property name.
     */
    public final static String bidirectional_name = 
        "es.tid.TIDorbj.bidirectional";


    /**
     * Default ORB's maximum threads number per POAManager.
     * <p>
     * Default value: <code>20</code>.
     */

    public final static int DEFAULT_POA_MAX_THREADS = 20;

    /**
     * ORB's maximum threads number per POAManager.
     * <p>
     * Values must be greater than 0.
     */

    public int poa_max_threads = DEFAULT_POA_MAX_THREADS;

    /**
     * max_threads Property name.
     */

    public final static String poa_max_threads_name = 
        "es.tid.TIDorbj.poa.max_threads";

    /**
     * Default ORB's minimum threads number per POAManager.
     * <p>
     * Default value: <code>0</code>.
     */

    public final static int DEFAULT_POA_MIN_THREADS = 1;

    /**
     * ORB's minimum threads number per POAManager.
     * <p>
     * Values must be greater or equal than 0.
     */

    public int poa_min_threads = DEFAULT_POA_MIN_THREADS;

    /**
     * min_threads Property name.
     */

    public final static String poa_min_threads_name =
        "es.tid.TIDorbj.poa.min_threads";

    /**
     * Default ORB's queued requests number per POAManager.
     * <p>
     * Default value: <code>1000</code>.
     */

    public final static int DEFAULT_POA_MAX_QUEUED_REQUESTS = 1000;

    /**
     * ORB's maximum queued requests number per POAManager.
     * <p>
     * Values must be greater or equal than 0.
     */

    public int poa_max_queued_requests = DEFAULT_POA_MAX_QUEUED_REQUESTS;

    /**
     * max_queued_requests Property name.
     */

    public final static String poa_max_queued_requests_name =
        "es.tid.TIDorbj.poa.max_queued_requests";

    /**
     * Default thread's maximum idle time per POAManager.
     * <p>
     * Default value: <code>0</code>.
     */

    public final static int DEFAULT_POA_STARVING_TIME = 0;

    /**
     * Thread's maximum idle time per per POAManager.
     * <p>
     * If it is 0, a thread can be idle forever.
     */

    public int poa_starving_time = DEFAULT_POA_STARVING_TIME;

    /**
     * max_queued_requests Property name.
     */

    public final static String poa_starving_time_name =
        "es.tid.TIDorbj.poa.starving_time";



    /**
     * Default AMIManager's maximum threads number 
     * <p>
     * Default value: <code>20</code>.
     */

    public final static int DEFAULT_AMI_MAX_THREADS = 20;

    /**
     * AMIManager's maximum threads number 
     * <p>
     * Values must be greater than 0.
     */

    public int ami_max_threads = DEFAULT_AMI_MAX_THREADS;

    /**
     * max_threads Property name.
     */

    public final static String ami_max_threads_name = 
        "es.tid.TIDorbj.ami.max_threads";

    /**
     * Default AMIManager's minimum threads number.
     * <p>
     * Default value: <code>0</code>.
     */

    public final static int DEFAULT_AMI_MIN_THREADS = 1;

    /**
     * AMIManager's minimum threads number.
     * <p>
     * Values must be greater or equal than 0.
     */

    public int ami_min_threads = DEFAULT_AMI_MIN_THREADS;

    /**
     * ami_threads Property name.
     */

    public final static String ami_min_threads_name =
        "es.tid.TIDorbj.ami.min_threads";

    /**
     * Default AMIManager's queued handled requests number .
     * <p>
     * Default value: <code>1000</code>.
     */

    public final static int DEFAULT_AMI_MAX_QUEUED_HANDLED_REQUESTS = 1000;

    /**
     * AMIManager's maximum queued handled requests number .
     * <p>
     * Values must be greater or equal than 0.
     */

    public int ami_max_queued_handled_requests = DEFAULT_AMI_MAX_QUEUED_HANDLED_REQUESTS;

    /**
     * max_queued_requests Property name.
     */

    public final static String ami_max_queued_handled_requests_name =
        "es.tid.TIDorbj.ami.max_queued_handled_requests";

    /**
     * Default thread's maximum idle time at AMIManager.
     * <p>
     * Default value: <code>0</code>.
     */

    public final static int DEFAULT_AMI_STARVING_TIME = 0;

    /**
     * Thread's maximum idle time per at AMIManager.
     * <p>
     * If it is 0, a thread can be idle forever.
     */

    public int ami_starving_time = DEFAULT_AMI_STARVING_TIME;

    /**
     * max_queued_requests Property name.
     */

    public final static String ami_starving_time_name =
        "es.tid.TIDorbj.ami.starving_time";

    /**
     * Default Maximun blocket time waiting for the shutdown completion. Default
     * value: <code>5000</code>.
     */

    public final static int DEFAULT_MAX_TIME_IN_SHUTDOWN = 5000;

    /**
     * Maximun time, in miliseconds, waiting for the shutdown completion.
     * <p>
     * Values must be greater or equal than 0.
     */

    public int max_time_in_shutdown = DEFAULT_MAX_TIME_IN_SHUTDOWN;

    /**
     * max_time_in_shutdown Property name.
     */

    public final static String max_time_in_shutdown_name = 
        "es.tid.TIDorbj.max_time_in_shutdown";

    /**
     * Default Trace Level. Default value: <code>Traces.NONE</code>.
     */

    public final static int DEFAULT_TRACE_LEVEL = Trace.NONE;

    /**
     * Trace Level. Values must be between 0 and 4.
     */

    public int trace_level = DEFAULT_TRACE_LEVEL;

    /**
     * Trace Level Property name.
     */

    public final static String trace_level_name = 
        "es.tid.TIDorbj.trace.level";

    /**
     * Trace file name.
     */

    public String trace_file = null;

    /**
     * Trace file property name.
     */

    public final static String trace_file_name = 
        "es.tid.TIDorbj.trace.file";

    /**
     * Default Trace file size. Default value: 10 K.
     */
    public final static long DEFAULT_FILE_SIZE = 10240;

    /**
     * Trace list files size
     */
    public long trace_file_size = DEFAULT_FILE_SIZE;

    /**
     * Trace list files size property name
     */
    public final static String trace_file_size_name = 
        "es.tid.TIDorbj.trace.file_size";

    /**
     * Default Trace files list length. Default value: 1 file.
     */
    public final static int DEFAULT_NUM_FILES = 1;

    /**
     * Trace files list length
     */
    public int trace_num_files = DEFAULT_NUM_FILES;

    /**
     * Trace files list length property name
     */
    public final static String trace_num_files_name = 
        "es.tid.TIDorbj.trace.num_files";


    /**
     * Default qos.
     */
    public final static boolean DEFAULT_QOS_ENABLED = true;

    /**
     * Enable QoS policies for client and server side.
     * <ul>
     * <li>If <code>true</code> enabled QoS policies 
     * <li>If <code>false</code> disabled QoS policies.
     * </ul>
     * Values must be "true" or "false".
     */

    public boolean qos_enabled = DEFAULT_QOS_ENABLED;

    /**
     * Exhaustive equal Property name.
     */

    public final static String qos_enabled_name =
        "es.tid.TIDorbj.qos_enabled";




    /**
     * Default ORB's ZIOP internal chunk size.
     * <p>
     * Default value: <code>32768</code>.
     */

    public final static int DEFAULT_ZIOP_CHUNK_SIZE = 32768;

    /**
     * ORB's ZIOP internal chunk size.
     * <p>
     * Values must be greater or equal than 0.
     */

    public int ziop_chunk_size = DEFAULT_ZIOP_CHUNK_SIZE;

    /**
     * ziop_chunk_size Property name.
     */

    public final static String ziop_chunk_size_name =
        "es.tid.TIDorbj.ziop_chunk_size";


    /**
     * Default assume ziop at server side.
     */
    public final static boolean DEFAULT_ASSUME_ZIOP_SERVER = false;

    /**
     * Assume ZIOP policies enabled at server side. Useful to connect through
     * corbaloc or Unicast/Multicast
     * <ul>
     * <li>If <code>true</code> server has ZIOP policies defined and known by client
     * <li>If <code>false</code> server has not ZIOP policies.
     * </ul>
     * Values must be "true" or "false".
     */

    public boolean assume_ziop_server = DEFAULT_ASSUME_ZIOP_SERVER;

    /**
     * Exhaustive equal Property name.
     */

    public final static String assume_ziop_server_name =
        "es.tid.TIDorbj.assume_ziop_server";



    private Applet      parameters;
    private Properties  properties;
    private Vector      arguments;
    
    
    protected static PolicyContext createDefaultPolicies()
    {
        PolicyContext context = new PolicyContext(null);

        try {
            context.setPolicy(
                new RelativeRoundtripTimeoutPolicyImpl(
                      UTC.toTimeT(DEFAULT_MAX_BLOCKED_TIME)));
            context.setPolicy(
                new BidirectionalPolicyImpl(DEFAULT_BIDIRECTIONAL));
        }
        catch (PolicyError pe) {}

        return context;
    }

    public ConfORB()
    {
        policy_context = new PolicyContext(st_default_policy_context);
    }

    public PolicyContext getPolicyContext()
    {
        return policy_context;
    }

    /**
     * Initialize the ORB properties.
     * 
     * @param args
     *            the arguments vector.
     * @param props
     *            the properties.
     */
    public void init(String[] args, java.util.Properties props)
    {
        parse_system_properties();

        if (props != null)
            init(props);

        if (args != null)
            init(args);
    }

    /**
     * Initialize the ORB properties with the Applet arguments.
     * 
     * @param app
     *            the applet that has created the ORB.
     * @param props
     *            the properties.
     */
    public void init(java.applet.Applet app, java.util.Properties props)
    {
        // parse_system_properties();

        if (props != null)
            init(props);

        if (app != null)
            init(app);

    }

    /**
     * Initialize the ORB properties with the arguments vector.
     * 
     * @param app
     *            the applet that has created the ORB.
     */

    public void init(String[] args)
    {
        if ( this.arguments == null ){
            this.arguments = new Vector( args.length );
        }
        this.arguments.addAll( Arrays.asList( args ) );
        
        
        int i = 0;
        String name;

        while (i < args.length - 1) {
            if ((args[i] == null) || (args[i].length() == 0)) {
                i++;
            } else {
                if ( args[i].equals(comm_layers_name) )
                    parse_comm_layers( args[++i] );
                if (args[i].equals(def_init_ref_name))
                    default_initial_reference = args[++i];
                if (args[i].equals(orb_id_name))
                    orb_id = args[++i];
                else if (args[i].equals(init_refs_name))
                    parse_init_ref(args[++i]);
                else if (args[i].equals(naming_service_name))
                    parse_naming_service(args[++i]);
                else if (args[i].equals(block_size_name))
                    parse_block_size(args[++i]);
                else if (args[i].equals(exhaustive_equal_name))
                    parse_exhaustive_equal(args[++i]);
                else if (args[i].equals(typecode_cache_size_name))
                    parse_typecode_cache_size(args[++i]);
                else if (args[i].equals(bidirectional_name))
                    parse_bidirectional(args[++i]);
                else if (args[i].equals(max_blocked_time_name))
                    parse_max_blocked_time(args[++i]);
                else if (args[i].equals(poa_max_threads_name))
                    parse_poa_max_threads(args[++i]);
                else if (args[i].equals(poa_min_threads_name))
                    parse_poa_min_threads(args[++i]);
                else if (args[i].equals(poa_max_queued_requests_name))
                    parse_poa_max_queued_requests(args[++i]);
                else if (args[i].equals(poa_starving_time_name))
                    parse_poa_starving_time(args[++i]);
                else if (args[i].equals(max_time_in_shutdown_name))
                    parse_max_time_in_shutdown(args[++i]);
                else if (args[i].equals(ami_max_threads_name))
                    parse_ami_max_threads(args[++i]);
                else if (args[i].equals(ami_min_threads_name))
                    parse_ami_min_threads(args[++i]);
                else if (args[i].equals(ami_max_queued_handled_requests_name))
                    parse_ami_max_queued_handled_requests(args[++i]);
                else if (args[i].equals(ami_starving_time_name))
                    parse_ami_starving_time(args[++i]);
                else if (args[i].equals(trace_file_name))
                    trace_file = args[++i];
                else if (args[i].equals(trace_level_name))
                    parse_trace_level(args[++i]);
                else if (args[i].equals(trace_file_size_name))
                    parse_trace_file_size(args[++i]);
                else if (args[i].equals(trace_num_files_name))
                    parse_trace_num_files(args[++i]);
                else if (args[i].equals(qos_enabled_name))
                    parse_qos_enabled(args[++i]);
                else if (args[i].equals(ziop_chunk_size_name))
                    parse_ziop_chunk_size(args[++i]);
                else if (args[i].equals(assume_ziop_server_name))
                    parse_assume_ziop_server(args[++i]);
                else
                    i++;
            }
        }

    }


    
    /**
     * Initialize the ORB properties with the Applet arguments.
     * 
     * @param app
     *            the applet that has created the ORB.
     */
    
    public void init(Applet app)
    {
        this.parameters = app;
        
        parse_init_ref(app.getParameter(init_refs_name));

        String aux_initial = app.getParameter(def_init_ref_name);

        if (aux_initial != null)
            default_initial_reference = aux_initial;

        String id = app.getParameter(orb_id_name);
        if (id != null)
            orb_id = id;
        
        parse_comm_layers( app.getParameter(comm_layers_name));
        parse_naming_service(app.getParameter(naming_service_name));
        parse_block_size(app.getParameter(block_size_name));
        parse_exhaustive_equal(app.getParameter(exhaustive_equal_name));
        parse_typecode_cache_size(app.getParameter(typecode_cache_size_name));
        parse_bidirectional(app.getParameter(bidirectional_name));
        parse_max_blocked_time(app.getParameter(max_blocked_time_name));
        parse_poa_max_threads(app.getParameter(poa_max_threads_name));
        parse_poa_min_threads(app.getParameter(poa_min_threads_name));
        parse_poa_max_queued_requests(
            app.getParameter(poa_max_queued_requests_name));
        parse_poa_starving_time(app.getParameter(poa_starving_time_name));
        parse_max_time_in_shutdown(app.getParameter(max_time_in_shutdown_name));

        parse_ami_max_threads(app.getParameter(ami_max_threads_name));
        parse_ami_min_threads(app.getParameter(ami_min_threads_name));
        parse_ami_max_queued_handled_requests(
            app.getParameter(ami_max_queued_handled_requests_name));
        parse_ami_starving_time(app.getParameter(poa_starving_time_name));

        String aux_trace = app.getParameter(trace_file_name);
        if (aux_trace != null)
            trace_file = aux_trace;

        parse_trace_level(app.getParameter(trace_level_name));
        parse_trace_file_size(app.getParameter(trace_file_size_name));
        parse_trace_num_files(app.getParameter(trace_num_files_name));

        parse_qos_enabled(app.getParameter(qos_enabled_name));
        parse_ziop_chunk_size(app.getParameter(ziop_chunk_size_name));
        parse_assume_ziop_server(app.getParameter(assume_ziop_server_name));
    }

    /**
     * Initialize the ORB properties with the Properties object.
     * 
     * @param props
     *            the properties.
     */

    public void init(java.util.Properties props)
    {
        if ( this.properties == null ){
            this.properties = new Properties();
        }
        this.properties.putAll( props );
        
        parse_comm_layers(props.getProperty(comm_layers_name));
        parse_naming_service(props.getProperty(naming_service_name));
        parse_block_size(props.getProperty(block_size_name));
        parse_exhaustive_equal(props.getProperty(exhaustive_equal_name));
        parse_typecode_cache_size(props.getProperty(typecode_cache_size_name));
        parse_bidirectional(props.getProperty(bidirectional_name));
        parse_max_blocked_time(props.getProperty(max_blocked_time_name));
        parse_poa_max_threads(props.getProperty(poa_max_threads_name));
        parse_poa_min_threads(props.getProperty(poa_min_threads_name));
        parse_poa_max_queued_requests(
        props.getProperty(poa_max_queued_requests_name));
        parse_poa_starving_time(props.getProperty(poa_starving_time_name));
        parse_max_time_in_shutdown(props.getProperty(max_time_in_shutdown_name));

        parse_ami_max_threads(props.getProperty(ami_max_threads_name));
        parse_ami_min_threads(props.getProperty(ami_min_threads_name));
        parse_ami_max_queued_handled_requests(
                props.getProperty(ami_max_queued_handled_requests_name));
        parse_ami_starving_time(props.getProperty(ami_starving_time_name));

        String aux_trace = props.getProperty(trace_file_name);
        if (aux_trace != null)
            trace_file = aux_trace;

        parse_trace_level(props.getProperty(trace_level_name));
        parse_trace_file_size(props.getProperty(trace_file_size_name));
        parse_trace_num_files(props.getProperty(trace_num_files_name));

        parse_qos_enabled(props.getProperty(qos_enabled_name));
        parse_ziop_chunk_size(props.getProperty(ziop_chunk_size_name));
        parse_assume_ziop_server(props.getProperty(assume_ziop_server_name));
    }

    /**
     * Search the ORB properties in the SystemProperties.
     */

    public void parse_system_properties()
    {
        parse_comm_layers( SystemProperties.findProperty(comm_layers_name) );
        parse_naming_service(
            SystemProperties.findProperty(naming_service_name));
        parse_block_size(SystemProperties.findProperty(block_size_name));
        parse_exhaustive_equal(
            SystemProperties.findProperty(exhaustive_equal_name));
        parse_max_blocked_time(
            SystemProperties.findProperty(max_blocked_time_name));
        parse_poa_max_threads(
            SystemProperties.findProperty(poa_max_threads_name));
        parse_poa_min_threads(
            SystemProperties.findProperty(poa_min_threads_name));
        parse_poa_max_queued_requests(
            SystemProperties.findProperty(poa_max_queued_requests_name));
        parse_poa_starving_time(
            SystemProperties.findProperty(poa_starving_time_name));
        parse_max_time_in_shutdown(
            SystemProperties.findProperty(max_time_in_shutdown_name));

        parse_ami_max_threads(
            SystemProperties.findProperty(ami_max_threads_name));
        parse_ami_min_threads(
            SystemProperties.findProperty(ami_min_threads_name));
        parse_ami_max_queued_handled_requests(
            SystemProperties.findProperty(ami_max_queued_handled_requests_name));
        parse_ami_starving_time(
            SystemProperties.findProperty(ami_starving_time_name));

        String aux_trace = SystemProperties.findProperty(trace_file_name);
        if (aux_trace != null)
            trace_file = aux_trace;

        parse_trace_level(SystemProperties.findProperty(trace_level_name));
        parse_trace_file_size(
            SystemProperties.findProperty(trace_file_size_name));
        parse_trace_num_files(
            SystemProperties.findProperty(trace_num_files_name));

        parse_qos_enabled(
            SystemProperties.findProperty(qos_enabled_name));
        parse_ziop_chunk_size(
            SystemProperties.findProperty(ziop_chunk_size_name));
        parse_assume_ziop_server(
            SystemProperties.findProperty(assume_ziop_server_name));
    }

    /********************************************************************/
    /*              COMPATIBILIDAD CON VERSIONES ANTIGUAS               */
    /********************************************************************/
    public void fillPropertyInfoOldValues( PropertyInfo pi ){
    	if ( pi != null ){
    		
    		boolean old_property_name_founded = false;
    		String old_property_name = "";
    		String new_property_name = pi.getName();
    		
    		if (new_property_name.equals(IIOPCommunicationLayerPropertiesInfo.PORT)){
    			old_property_name = "es.tid.TIDorbj.iiop.orb_port";
    			old_property_name_founded = true;
    		} else if (new_property_name.equals(IIOPCommunicationLayerPropertiesInfo.HOST_ADDRESS)){
    			old_property_name = "es.tid.TIDorbj.iiop.ip_address";
    			old_property_name_founded = true;
    		} else if (new_property_name.equals(IIOPCommunicationLayerPropertiesInfo.RELIABLE_ONEWAY)){
    			old_property_name = "es.tid.TIDorbj.reliable_oneway";
    			old_property_name_founded = true;
    		}
    		
    		if (old_property_name_founded){		
    			boolean found = false;
    			if ( !found && this.parameters != null ){
    				String value;
    				value = this.parameters.getParameter(old_property_name );
    				found = value != null; 
    				if ( found ) {
    					pi.setValue( value );
    				}
    			}
    			if ( !found && this.arguments != null ){
    				int index;
    				index = this.arguments.indexOf( old_property_name );
    				found = index > -1;
    				if ( found ){
    					pi.setValue( (String)this.arguments.elementAt( ++index ) );
    				}
    			}
    			if ( !found && this.properties != null ){
    				String value;
    				value = this.properties.getProperty( old_property_name );
    				found = value != null;
    				if ( found ){
    					pi.setValue( value );
    				}
    			}
    			if ( !found ){
    				String value; 
    				value = SystemProperties.findProperty( old_property_name );
    				found = value != null;
    				if ( found ){
    					pi.setValue( value );
    				}
    			}
    		}
    	} 
    }//fillPropertyInfoOldValues
    
    /********************************************************************/
    
    
    public void fillPropertyInfo( PropertyInfo pi ){
        if ( pi != null ){
        	fillPropertyInfoOldValues(pi);
            boolean found = false;
            if ( !found && this.parameters != null ){
                String value;
                value = this.parameters.getParameter( pi.getName() );
                found = value != null; 
                if ( found ) {
                    pi.setValue( value );
                }
            }
            if ( !found && this.arguments != null ){
                int index;
                index = this.arguments.indexOf( pi.getName() );
                found = index > -1;
                if ( found ){
                    pi.setValue( (String)this.arguments.elementAt( ++index ) );
                }
            }
            if ( !found && this.properties != null ){
                String value;
                value = this.properties.getProperty( pi.getName() );
                found = value != null;
                if ( found ){
                    pi.setValue( value );
                }
            }
            if ( !found ){
                String value; 
                value = SystemProperties.findProperty( pi.getName() );
                found = value != null;
                if ( found ){
                    pi.setValue( value );
                }
            }
        } 
    }
    
    public void fillPropertyInfo( PropertyInfo[] pis ){
        if ( pis != null ){
            PropertyInfo pi;
            for ( int i=0; i < pis.length; i++ ) {
                this.fillPropertyInfo( pis[ i ] );
            }
        }
    }
    
    public void parse_init_ref(String str)
    {
        if (str == null)
            return;

        InitialReference init_ref = new InitialReference();

        init_ref.parse(str);

        initial_references.addElement(init_ref);
    }

    public void parse_naming_service(String str)
    {
        if (str == null)
            return;
        InitialReference init_ref = new InitialReference("NameService", str);

        initial_references.addElement(init_ref);
    }

    /**
     * Parse the communications layer instances that should be passed to the
     * CommunicationManager to be initialized.
     * This method will check for the existence of the default communication 
     * layer (DEFAULT_COMM_LAYER)... which will represent both the IIOP and the
     * local comm layers...
     * @param str
     */
    public void parse_comm_layers( String str ){
        if ( str != null ){
            if ( (str.indexOf( DEFAULT_COMM_LAYER ) == -1 && 
                  str.indexOf( "es.tid.TIDorbj.core.comm.ssliop.SSLIOPCommunicationLayer" ) == -1 )&& 
                 str.trim().length() > 0 ) {
                //must include default comm layer
                comm_layers = DEFAULT_COMM_LAYER + ":" + str;
            } else {
                //default comm layer already present, 
                comm_layers = str;
            }
        }
    }
    
    
    /**
     * Parse the block_size value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be > 256 and multiple of 8.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_block_size(String str)
    {
        if (str == null)
            return;

        int value = parse_int(block_size_name, str);

        if ((value < 256) || (value % CDR.LONG_SIZE != 0))
            throw new INITIALIZE(block_size_name + ":invalid value " + value
                                 + " (minimum fragment size: 256)");

        block_size = value;
    }

    /**
     * Parse the exhaustive_equal boolean value.
     * 
     * @param str
     *            the value representation.
     * @pre Values must be "true" or "false".
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_exhaustive_equal(String str)
    {
        if (str == null)
            return;

        exhaustive_equal = parse_boolean(exhaustive_equal_name, str);
    }

    /**
     * Parse the typecode_cache_size value.
     * 
     * @param str
     *            the value digits.
     * @pre Value must be > 1024 and multiple of 8.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_typecode_cache_size(String str)
    {
        if (str == null)
            return;

        int value = parse_int(typecode_cache_size_name, str);

        if (value < 0)
            throw new INITIALIZE(typecode_cache_size_name + ": invalid value "
                                 + value + " (fragment size must be > 0)");

        typecode_cache_size = value;

    }

    /**
     * Parse the ORB nominal Bidirectional Policy.
     * 
     * @param str
     *            the policy value.
     * @pre Values must be "NORMAL", "BOTH".
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    //TODO: remove iiop, this is por ORB's policy manager...
    public void parse_bidirectional(String str)
    {
        if (str == null)
            return;

        short value = 0;

        if (str.equals("NORMAL")) {
            return;
        } else if (str.equals("BOTH")) {
            try {
                policy_context.setPolicy(
                   new BidirectionalPolicyImpl(org.omg.BiDirPolicy.BOTH.value));
            }
            catch (PolicyError pe) {}
        } else {
            throw new org.omg.CORBA.INITIALIZE("Invalid value"
                                               + bidirectional_name 
                                               + "=" + str);
        }
    }

    /**
     * Parse the max_blocked_time value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be >= 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_max_blocked_time(String str)
    {
        if (str == null)
            return;

        long value = parse_long(max_blocked_time_name, str);

        if (value < 0)
            throw new INITIALIZE(max_blocked_time_name + ":invalid value "
                                 + value + " (minimum blocked 0)");

        if (value != DEFAULT_MAX_BLOCKED_TIME) {
            // converts to TimeT (from ms to units of 100 ns)
            value = UTC.toTimeT(value);
            try {
                policy_context.setPolicy(
                    new RelativeRoundtripTimeoutPolicyImpl(value));
            }
            catch (PolicyError pe) {}
            max_blocked_time = value;
        }

    }

    /**
     * Parse the poa_max_threads value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be > 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_poa_max_threads(String str)
    {
        if (str == null)
            return;

        int value = parse_int(poa_max_threads_name, str);

        if (value < 1)
            throw new INITIALIZE(poa_max_threads_name + ":invalid value "
                                 + value + " (minimum 1)");

        poa_max_threads = value;

    }

    /**
     * Parse the poa_min_threads value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be >= 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_poa_min_threads(String str)
    {
        if (str == null)
            return;

        int value = parse_int(poa_min_threads_name, str);

        if (value < 1)
            throw new INITIALIZE(poa_min_threads_name + ":invalid value "
                                 + value + " (minimum 1)");

        poa_min_threads = value;

    }

    /**
     * Parse the poa_max_queued_requests value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be > 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_poa_max_queued_requests(String str)
    {
        if (str == null)
            return;

        int value = parse_int(poa_max_queued_requests_name, str);

        if (value < 1)
            throw new INITIALIZE(poa_max_queued_requests_name
                                 + ":invalid value " + value + " (minimum 1)");

        poa_max_queued_requests = value;

    }

    /**
     * Parse the poa_starving_time value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be >= 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_poa_starving_time(String str)
    {
        if (str == null)
            return;

        int value = parse_int(poa_starving_time_name, str);

        if (value < 0)
            throw new INITIALIZE(poa_starving_time_name + ":invalid value "
                                 + value + " (minimum 0)");

        poa_starving_time = value;

    }

    /**
     * Parse the ami_max_threads value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be > 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_ami_max_threads(String str)
    {
        if (str == null)
            return;

        int value = parse_int(ami_max_threads_name, str);

        if (value < 1)
            throw new INITIALIZE(ami_max_threads_name + ":invalid value "
                                 + value + " (minimum 1)");

        ami_max_threads = value;

    }

    /**
     * Parse the ami_min_threads value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be >= 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_ami_min_threads(String str)
    {
        if (str == null)
            return;

        int value = parse_int(ami_min_threads_name, str);

        if (value < 1)
            throw new INITIALIZE(ami_min_threads_name + ":invalid value "
                                 + value + " (minimum 1)");

        ami_min_threads = value;

    }

    /**
     * Parse the ami_max_queued_requests value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be > 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_ami_max_queued_handled_requests(String str)
    {
        if (str == null)
            return;

        int value = parse_int(ami_max_queued_handled_requests_name, str);

        if (value < 1)
            throw new INITIALIZE(ami_max_queued_handled_requests_name
                                 + ":invalid value " + value + " (minimum 1)");

        ami_max_queued_handled_requests = value;

    }

    /**
     * Parse the ami_starving_time value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be >= 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_ami_starving_time(String str)
    {
        if (str == null)
            return;

        int value = parse_int(ami_starving_time_name, str);

        if (value < 0)
            throw new INITIALIZE(ami_starving_time_name + ":invalid value "
                                 + value + " (minimum 0)");

        ami_starving_time = value;

    }




    /**
     * Parse the max_time_in_shutdown value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be >= 0.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_max_time_in_shutdown(String str)
    {
        if (str == null)
            return;

        int value = parse_int(max_time_in_shutdown_name, str);

        if (value < 0)
            throw new INITIALIZE(max_time_in_shutdown_name + ":invalid value "
                                 + value + " (minimum blocked 0)");

        max_time_in_shutdown = value;

    }


    /**
     * Parse the trace_level value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be between 0 and 4.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_trace_level(String str)
    {
        if (str == null)
            return;

        int value = parse_int(trace_level_name, str);

        if ((value < Trace.NONE) || (value > Trace.DUMP))
            throw new INITIALIZE(trace_level_name + ":invalid value " + value
                                 + " (valid value between 0 and 5)");

        trace_level = value;

    }

    public void parse_trace_file_size(String size)
    {
        if (size == null)
            return;

        long value = parse_long(trace_file_size_name, size);

        if (value < 1024L)
            throw new INITIALIZE(trace_file_size_name + ": invalid value "
                                 + value + " (valid value greater than 1024)");
        trace_file_size = value;
    }

    public void parse_trace_num_files(String length)
    {
        if (length == null)
            return;

        int value = parse_int(trace_num_files_name, length);

        if ((value < 1) || (value > 10))
            throw new INITIALIZE(trace_num_files_name + ": invalid value "
                                 + value + " (valid value between 1 and 10)");

        trace_num_files = value;
    }




    /**
     * Parse the qos_enabled value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be true or false
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_qos_enabled(String str)
    {
       if (str == null)
            return;

        qos_enabled = parse_boolean(qos_enabled_name, str);

    }

    /**
     * Parse the ziop_chunk_size value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be > 0
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_ziop_chunk_size(String str)
    {
        if (str == null)
            return;

        int value = parse_int(ziop_chunk_size_name, str);

        if (value > 0) 
            throw new INITIALIZE(ziop_chunk_size_name + ":invalid value " + value
                                 + " (value must be > 0)");

        ziop_chunk_size = value;
    }



    /**
     * Parse the assume_ziop_server value.
     * 
     * @param str
     *            the value digits.
     * @pre Values must be true or false
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    public void parse_assume_ziop_server(String str)
    {
       if (str == null)
            return;

        assume_ziop_server = parse_boolean(assume_ziop_server_name, str);

    }



    /**
     * Parse the an int value.
     * 
     * @param str
     *            the value digits.
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid number.
     */
    protected static int parse_int(String prop_name, String str)
    {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException num) {
            throw new INITIALIZE(prop_name + ": int value expected.");
        }
    }

    /**
     * Parse an long value
     * 
     * @param str
     *            the value digits
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid number
     */
    protected static long parse_long(String prop_name, String str)
    {
        try {
            return Long.parseLong(str);
        }
        catch (NumberFormatException num) {
            throw new INITIALIZE(prop_name + ": long value expected");
        }
    }

    /**
     * Parse the a boolean value.
     * 
     * @param str
     *            the value representation: "true" or "false".
     * @exception org.omg.CORBA.INITIALIZE
     *                if the string does not contains a valid value.
     */
    protected static boolean parse_boolean(String prop_name, String str)
    {
        if (str.equals("true"))
            return true;
        else if (str.equals("false"))
            return false;
        else
            throw new INITIALIZE(prop_name + ": boolean value expected.");
    }

    public void dump(java.io.PrintWriter writer)
    {
        //TODO: complete dump with CommLayers stuff
        writer.println("TIDorb properties:");

        writer.print('\t');
        writer.print(block_size_name);
        writer.print('=');
        writer.println(block_size);
        writer.print('\t');
        writer.print(exhaustive_equal_name);
        writer.print('=');
        writer.println(exhaustive_equal);
        writer.print('\t');

        writer.print(typecode_cache_size_name);
        writer.print('=');
        writer.println(typecode_cache_size);

        writer.println();

        writer.print('\t');
        writer.print(poa_max_threads_name);
        writer.print('=');
        writer.println(poa_max_threads);
        writer.print('\t');
        writer.print(poa_min_threads_name);
        writer.print('=');
        writer.println(poa_min_threads);
        writer.print('\t');
        writer.print(poa_max_queued_requests_name);
        writer.print('=');
        writer.println(poa_max_queued_requests);
        writer.print('\t');
        writer.print(poa_starving_time_name);
        writer.print('=');
        writer.println(poa_starving_time);

        writer.print('\t');
        writer.print(ami_max_threads_name);
        writer.print('=');
        writer.println(ami_max_threads);
        writer.print('\t');
        writer.print(ami_min_threads_name);
        writer.print('=');
        writer.println(ami_min_threads);
        writer.print('\t');
        writer.print(ami_max_queued_handled_requests_name);
        writer.print('=');
        writer.println(ami_max_queued_handled_requests);
        writer.print('\t');
        writer.print(ami_starving_time_name);
        writer.print('=');
        writer.println(ami_starving_time);


        writer.print('\t');
        writer.print(max_time_in_shutdown_name);
        writer.print('=');
        writer.println(max_time_in_shutdown);
        writer.println();
        writer.print('\t');
        writer.print(trace_level_name);
        writer.print('=');
        writer.println(trace_level);

        if (trace_file != null) {
            writer.print('\t');
            writer.print(trace_file_name);
            writer.print('=');
            writer.println(trace_file);

            writer.print('\t');
            writer.print(trace_file_size_name);
            writer.print('=');
            writer.println(trace_file_size);

            writer.print('\t');
            writer.print(trace_num_files_name);
            writer.print('=');
            writer.println(trace_num_files);
        }

        writer.print("Default Policies:");
        st_default_policy_context.dump(writer);
        writer.print('\n');
        writer.print("User Policies:");
        policy_context.dump(writer);
        writer.print('\n');

        writer.flush();
    }
}


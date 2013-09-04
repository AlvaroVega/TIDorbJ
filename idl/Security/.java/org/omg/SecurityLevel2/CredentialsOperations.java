//
// Credentials.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public interface CredentialsOperations {

  org.omg.SecurityLevel2.Credentials copy();

  void destroy();

  org.omg.Security.InvocationCredentialsType credentials_type();

  org.omg.Security.AuthenticationStatus authentication_state();

  java.lang.String mechanism();

  short accepting_options_supported();
  void accepting_options_supported(short value);


  short accepting_options_required();
  void accepting_options_required(short value);


  short invocation_options_supported();
  void invocation_options_supported(short value);


  short invocation_options_required();
  void invocation_options_required(short value);


  boolean get_security_feature(org.omg.Security.CommunicationDirection direction, org.omg.Security.SecurityFeature feature);

  boolean set_attributes(org.omg.Security.SecAttribute[] requested_attributes, org.omg.Security.AttributeListHolder actual_attributes);

  org.omg.Security.SecAttribute[] get_attributes(org.omg.Security.AttributeType[] attributes);

  boolean is_valid(org.omg.TimeBase.UtcTHolder expiry_time);

  boolean refresh(org.omg.CORBA.Any refresh_data);


}

//
// CredentialsLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class CredentialsLocalTie
 extends CredentialsLocalBase
 {

  private CredentialsOperations _delegate;
  public CredentialsLocalTie(CredentialsOperations delegate) {
    this._delegate = delegate;
  };

  public CredentialsOperations _delegate() {
    return this._delegate;
  };

  public org.omg.SecurityLevel2.Credentials copy() {
    return this._delegate.copy(
    );
  };

  public void destroy() {
    this._delegate.destroy(
    );
  };

  public org.omg.Security.InvocationCredentialsType credentials_type() {
    return this._delegate.credentials_type();
  }

  public org.omg.Security.AuthenticationStatus authentication_state() {
    return this._delegate.authentication_state();
  }

  public java.lang.String mechanism() {
    return this._delegate.mechanism();
  }

  public short accepting_options_supported() {
    return this._delegate.accepting_options_supported();
  }

  public void accepting_options_supported(short value) {
    this._delegate.accepting_options_supported(value);
  }

  public short accepting_options_required() {
    return this._delegate.accepting_options_required();
  }

  public void accepting_options_required(short value) {
    this._delegate.accepting_options_required(value);
  }

  public short invocation_options_supported() {
    return this._delegate.invocation_options_supported();
  }

  public void invocation_options_supported(short value) {
    this._delegate.invocation_options_supported(value);
  }

  public short invocation_options_required() {
    return this._delegate.invocation_options_required();
  }

  public void invocation_options_required(short value) {
    this._delegate.invocation_options_required(value);
  }

  public boolean get_security_feature(org.omg.Security.CommunicationDirection direction, org.omg.Security.SecurityFeature feature) {
    return this._delegate.get_security_feature(
    direction, 
    feature
    );
  };

  public boolean set_attributes(org.omg.Security.SecAttribute[] requested_attributes, org.omg.Security.AttributeListHolder actual_attributes) {
    return this._delegate.set_attributes(
    requested_attributes, 
    actual_attributes
    );
  };

  public org.omg.Security.SecAttribute[] get_attributes(org.omg.Security.AttributeType[] attributes) {
    return this._delegate.get_attributes(
    attributes
    );
  };

  public boolean is_valid(org.omg.TimeBase.UtcTHolder expiry_time) {
    return this._delegate.is_valid(
    expiry_time
    );
  };

  public boolean refresh(org.omg.CORBA.Any refresh_data) {
    return this._delegate.refresh(
    refresh_data
    );
  };


}

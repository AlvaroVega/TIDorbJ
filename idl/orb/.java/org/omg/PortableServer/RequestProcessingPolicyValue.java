//
// RequestProcessingPolicyValue.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class RequestProcessingPolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _USE_ACTIVE_OBJECT_MAP_ONLY = 0;
  public static final RequestProcessingPolicyValue USE_ACTIVE_OBJECT_MAP_ONLY = new RequestProcessingPolicyValue(_USE_ACTIVE_OBJECT_MAP_ONLY);
  public static final int _USE_DEFAULT_SERVANT = 1;
  public static final RequestProcessingPolicyValue USE_DEFAULT_SERVANT = new RequestProcessingPolicyValue(_USE_DEFAULT_SERVANT);
  public static final int _USE_SERVANT_MANAGER = 2;
  public static final RequestProcessingPolicyValue USE_SERVANT_MANAGER = new RequestProcessingPolicyValue(_USE_SERVANT_MANAGER);

  public int value() { return _value; }
  public static RequestProcessingPolicyValue from_int(int value) {
    switch (value) {
      case 0: return USE_ACTIVE_OBJECT_MAP_ONLY;
      case 1: return USE_DEFAULT_SERVANT;
      case 2: return USE_SERVANT_MANAGER;
    };
    return null;
  };
  protected RequestProcessingPolicyValue (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}

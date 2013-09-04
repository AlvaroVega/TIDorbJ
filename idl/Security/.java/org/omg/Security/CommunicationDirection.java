//
// CommunicationDirection.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class CommunicationDirection
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecDirectionBoth = 0;
  public static final CommunicationDirection SecDirectionBoth = new CommunicationDirection(_SecDirectionBoth);
  public static final int _SecDirectionRequest = 1;
  public static final CommunicationDirection SecDirectionRequest = new CommunicationDirection(_SecDirectionRequest);
  public static final int _SecDirectionReply = 2;
  public static final CommunicationDirection SecDirectionReply = new CommunicationDirection(_SecDirectionReply);

  public int value() { return _value; }
  public static CommunicationDirection from_int(int value) {
    switch (value) {
      case 0: return SecDirectionBoth;
      case 1: return SecDirectionRequest;
      case 2: return SecDirectionReply;
    };
    return null;
  };
  protected CommunicationDirection (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}

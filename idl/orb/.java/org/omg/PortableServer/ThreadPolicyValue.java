//
// ThreadPolicyValue.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class ThreadPolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _ORB_CTRL_MODEL = 0;
  public static final ThreadPolicyValue ORB_CTRL_MODEL = new ThreadPolicyValue(_ORB_CTRL_MODEL);
  public static final int _SINGLE_THREAD_MODEL = 1;
  public static final ThreadPolicyValue SINGLE_THREAD_MODEL = new ThreadPolicyValue(_SINGLE_THREAD_MODEL);
  public static final int _MAIN_THREAD_MODEL = 2;
  public static final ThreadPolicyValue MAIN_THREAD_MODEL = new ThreadPolicyValue(_MAIN_THREAD_MODEL);

  public int value() { return _value; }
  public static ThreadPolicyValue from_int(int value) {
    switch (value) {
      case 0: return ORB_CTRL_MODEL;
      case 1: return SINGLE_THREAD_MODEL;
      case 2: return MAIN_THREAD_MODEL;
    };
    return null;
  };
  protected ThreadPolicyValue (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}

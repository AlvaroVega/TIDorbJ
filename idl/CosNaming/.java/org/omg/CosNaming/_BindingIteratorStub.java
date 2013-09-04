//
// _BindingIteratorStub.java (stub)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public class _BindingIteratorStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements BindingIterator {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CosNaming/BindingIterator:1.0"  };

  public boolean next_one(org.omg.CosNaming.BindingHolder b) {
    org.omg.CORBA.Request _request = this._request("next_one");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
    org.omg.CORBA.Any $b = _request.add_named_out_arg("b");
    $b.type(org.omg.CosNaming.BindingHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    boolean _result;
    _result = _request.return_value().extract_boolean();
    b.value = org.omg.CosNaming.BindingHelper.extract($b);
    return _result;
  }

  public boolean next_n(int how_many, org.omg.CosNaming.BindingListHolder bl) {
    org.omg.CORBA.Request _request = this._request("next_n");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
    org.omg.CORBA.Any $how_many = _request.add_named_in_arg("how_many");
    $how_many.insert_ulong(how_many);
    org.omg.CORBA.Any $bl = _request.add_named_out_arg("bl");
    $bl.type(org.omg.CosNaming.BindingListHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    boolean _result;
    _result = _request.return_value().extract_boolean();
    bl.value = org.omg.CosNaming.BindingListHelper.extract($bl);
    return _result;
  }

  public void destroy() {
    org.omg.CORBA.Request _request = this._request("destroy");
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }


}

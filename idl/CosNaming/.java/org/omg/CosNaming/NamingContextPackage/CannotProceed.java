//
// CannotProceed.java (exception)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

final public class CannotProceed
   extends org.omg.CORBA.UserException {

  public org.omg.CosNaming.NamingContext cxt;
  public org.omg.CosNaming.NameComponent[] rest_of_name;

  public CannotProceed() {
    super(CannotProceedHelper.id());
  }

  public CannotProceed(org.omg.CosNaming.NamingContext _cxt, org.omg.CosNaming.NameComponent[] _rest_of_name) {
    super(CannotProceedHelper.id());

    this.cxt = _cxt;
    this.rest_of_name = _rest_of_name;
  }

  public CannotProceed(String reason, org.omg.CosNaming.NamingContext _cxt, org.omg.CosNaming.NameComponent[] _rest_of_name) {
    super(CannotProceedHelper.id()+" "+reason);

    this.cxt = _cxt;
    this.rest_of_name = _rest_of_name;
  }

}

//
// NotFound.java (exception)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

final public class NotFound
   extends org.omg.CORBA.UserException {

  public org.omg.CosNaming.NamingContextPackage.NotFoundReason why;
  public org.omg.CosNaming.NameComponent[] rest_of_name;

  public NotFound() {
    super(NotFoundHelper.id());
  }

  public NotFound(org.omg.CosNaming.NamingContextPackage.NotFoundReason _why, org.omg.CosNaming.NameComponent[] _rest_of_name) {
    super(NotFoundHelper.id());

    this.why = _why;
    this.rest_of_name = _rest_of_name;
  }

  public NotFound(String reason, org.omg.CosNaming.NamingContextPackage.NotFoundReason _why, org.omg.CosNaming.NameComponent[] _rest_of_name) {
    super(NotFoundHelper.id()+" "+reason);

    this.why = _why;
    this.rest_of_name = _rest_of_name;
  }

}

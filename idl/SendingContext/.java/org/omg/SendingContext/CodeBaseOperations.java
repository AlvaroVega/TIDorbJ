//
// CodeBase.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

public interface CodeBaseOperations
   extends org.omg.SendingContext.RunTimeOperations {

  org.omg.CORBA.Repository get_ir();

  java.lang.String implementation(java.lang.String x);

  java.lang.String[] implementations(java.lang.String[] x);

  org.omg.CORBA.ValueDefPackage.FullValueDescription meta(java.lang.String x);

  org.omg.CORBA.ValueDefPackage.FullValueDescription[] metas(java.lang.String[] x);

  java.lang.String[] bases(java.lang.String x);


}

//
// BindingIterator.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public interface BindingIteratorOperations {

  boolean next_one(org.omg.CosNaming.BindingHolder b);

  boolean next_n(int how_many, org.omg.CosNaming.BindingListHolder bl);

  void destroy();


}

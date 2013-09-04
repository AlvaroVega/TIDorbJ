//
// SASContextBody.java (union)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class SASContextBody
   implements org.omg.CORBA.portable.IDLEntity {

  private short _discriminator;
  private java.lang.Object _union_value;
  protected boolean _isDefault = true;

  public org.omg.CSI.EstablishContext establish_msg(){
    if (_discriminator == 0) {
       return (org.omg.CSI.EstablishContext)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("establish_msg");
  }

  public void establish_msg(org.omg.CSI.EstablishContext value){
    _discriminator = 0;
    _union_value = (value);
    _isDefault = false;
  }


  public org.omg.CSI.CompleteEstablishContext complete_msg(){
    if (_discriminator == 1) {
       return (org.omg.CSI.CompleteEstablishContext)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("complete_msg");
  }

  public void complete_msg(org.omg.CSI.CompleteEstablishContext value){
    _discriminator = 1;
    _union_value = (value);
    _isDefault = false;
  }


  public org.omg.CSI.ContextError error_msg(){
    if (_discriminator == 4) {
       return (org.omg.CSI.ContextError)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("error_msg");
  }

  public void error_msg(org.omg.CSI.ContextError value){
    _discriminator = 4;
    _union_value = (value);
    _isDefault = false;
  }


  public org.omg.CSI.MessageInContext in_context_msg(){
    if (_discriminator == 5) {
       return (org.omg.CSI.MessageInContext)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("in_context_msg");
  }

  public void in_context_msg(org.omg.CSI.MessageInContext value){
    _discriminator = 5;
    _union_value = (value);
    _isDefault = false;
  }


  public void __default(){
    _discriminator = -32768;
    _union_value = null;
    _isDefault = true;
  }

  public void __default(short discriminator){
    if((discriminator == 0)||
   (discriminator == 1)||
   (discriminator == 4)||
   (discriminator == 5))
	 {
      throw new org.omg.CORBA.BAD_OPERATION("__default");
    }
    _discriminator = discriminator;
    _union_value = null;
    _isDefault = true;
  }

  public short discriminator(){
     return _discriminator;
  }

}

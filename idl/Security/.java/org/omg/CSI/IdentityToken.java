//
// IdentityToken.java (union)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class IdentityToken
   implements org.omg.CORBA.portable.IDLEntity {

  private int _discriminator;
  private java.lang.Object _union_value;
  protected boolean _isDefault = true;

  public boolean absent(){
    if (_discriminator == 0) {
       return ((java.lang.Boolean)_union_value).booleanValue();
    }
    throw new org.omg.CORBA.BAD_OPERATION("absent");
  }

  public void absent(boolean value){
    _discriminator = 0;
    _union_value = new java.lang.Boolean(value);
    _isDefault = false;
  }


  public boolean anonymous(){
    if (_discriminator == 1) {
       return ((java.lang.Boolean)_union_value).booleanValue();
    }
    throw new org.omg.CORBA.BAD_OPERATION("anonymous");
  }

  public void anonymous(boolean value){
    _discriminator = 1;
    _union_value = new java.lang.Boolean(value);
    _isDefault = false;
  }


  public byte[] principal_name(){
    if (_discriminator == 2) {
       return (byte[])_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("principal_name");
  }

  public void principal_name(byte[] value){
    _discriminator = 2;
    _union_value = (value);
    _isDefault = false;
  }


  public byte[] certificate_chain(){
    if (_discriminator == 4) {
       return (byte[])_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("certificate_chain");
  }

  public void certificate_chain(byte[] value){
    _discriminator = 4;
    _union_value = (value);
    _isDefault = false;
  }


  public byte[] dn(){
    if (_discriminator == 8) {
       return (byte[])_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("dn");
  }

  public void dn(byte[] value){
    _discriminator = 8;
    _union_value = (value);
    _isDefault = false;
  }


  public byte[] id(){
    if (_isDefault) {
       return (byte[])_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("id");
  }

  public void id(byte[] value){
    _discriminator = 3;
    _union_value = (value);
    _isDefault = true;
  }


  public int discriminator(){
     return _discriminator;
  }

}

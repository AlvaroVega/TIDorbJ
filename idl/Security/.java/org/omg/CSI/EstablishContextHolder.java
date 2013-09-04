//
// EstablishContextHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class EstablishContextHolder
   implements org.omg.CORBA.portable.Streamable {

  public EstablishContext value; 
  public EstablishContextHolder() {
  }

  public EstablishContextHolder(EstablishContext initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSI.EstablishContextHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSI.EstablishContextHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSI.EstablishContextHelper.type();
  };

}


package org.estudy.webservice;

import org.estudy.learning.model.ESession;

import java.util.Collection;

public class JSONData {

  private Collection<ESession> list;
  private Collection<String> info ;

  public Collection<String> getInfo() {
    return info;
  }
  
  public void setInfo(Collection<String> info) {
    this.info = info;
  }


  public Collection<ESession> getList() {
    return list;
  }

  public void setList(Collection<ESession> list) {
    this.list = list;
  }
}
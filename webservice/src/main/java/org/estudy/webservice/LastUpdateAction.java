
package org.estudy.webservice;

import org.apache.commons.chain.Context;
import org.exoplatform.services.command.action.Action;
import org.exoplatform.services.jcr.impl.core.NodeImpl;
import org.exoplatform.services.jcr.impl.core.PropertyImpl;

import javax.jcr.Node;
import java.util.GregorianCalendar;


public class LastUpdateAction implements Action {

  public boolean execute(Context context) throws Exception {
    try {
      Node node = (Node)context.get("currentItem");
      if(!node.isNodeType("exo:datetime")){
        if(node.canAddMixin("exo:datetime")) {
          node.addMixin("exo:datetime");            
        }
        node.setProperty("exo:dateCreated",new GregorianCalendar());
        node.setProperty("exo:dateModified",new GregorianCalendar());  
      }
      else
        node.setProperty("exo:dateModified",new GregorianCalendar());  
    } catch (ClassCastException e){
      PropertyImpl property = (PropertyImpl)context.get("currentItem");
      NodeImpl parent = (NodeImpl)property.getParent();
      if(!parent.isNodeType("exo:question"))
        throw new Exception("incoming node is not exo:question");
      parent.setProperty("exo:dateModified",new GregorianCalendar());
    }
    return false;
  }

}

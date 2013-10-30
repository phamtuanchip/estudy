/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.juzu.portlet.example.exampleJuzu;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;

import juzu.Path;
import juzu.Resource;
import juzu.Response;
import juzu.View;
import juzu.impl.common.JSON;
import juzu.request.RenderContext;
import juzu.template.Template;

import org.exoplatform.commons.juzu.ajax.Ajax;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;


public class ExampleControll {
  private static final Log LOG = ExoLogger.getLogger(ExampleControll.class);

  @Inject
  @Path("index.gtmpl") Template index;
  
  @Inject
  ResourceBundle bundle;  
  
  
  private final static String LOCATION_INPUT = "location_input";
  
  private final static String GOOGLE_MAP_URL = "https://maps.google.fr/maps?f=q&source=s_q&hl=en&geocode=&q=QUERY&aq=&t=m&ie=UTF8&hq=&hnear=QUERY&z=12&output=embed";

  private Locale locale = Locale.ENGLISH;
  
  private String location = "Las Vegas";

  @View
  public void index(RenderContext renderContext) {
    if (renderContext != null) {
      locale = renderContext.getUserContext().getLocale();
    }
    if (bundle == null) {
      bundle = renderContext.getApplicationContext().resolveBundle(locale);
    }

    index.render(parameters());
  }
 
  @Ajax
  @Resource
  public Response updateLocation(String location) {
   
    this.location = location;
    return index.ok(parameters()).withMimeType("text/html");
  }

  private Map<String, Object> parameters() {
    
    Map<String, Object> parameters = new HashMap<String, Object>();

    //
    Context context = new Context(bundle);
    parameters.put("_ctx", context);
    parameters.put("input_id", LOCATION_INPUT);
    parameters.put("location", location);
    parameters.put("GMapURL", GOOGLE_MAP_URL.replace("QUERY", location.replaceAll(" ", "%20")));

    //
    return parameters;
  }

  
  @Ajax
  @Resource
  public Response saveSetting(String params) {
    JSON data = new JSON();
    try {
      Map<String, String> datas = parserParams(params);
      
      data.set("ok", "true");
      data.set("value", "");
    } catch (Exception e) {
      data.set("ok", "false");
      data.set("status", e.toString());
    }
   
    return Response.ok(data.toString()).withMimeType("application/json");
  }

  private Map<String, String> parserParams(String params) {
    Map<String, String> datas = new HashMap<String, String>();
    String[] arrays = params.split("&");
    for (int i = 0; i < arrays.length; i++) {
      String[] data = arrays[i].split("=");
      datas.put(data[0], data[1]);
    }
    return datas;
  }

  public class Context {
    ResourceBundle rs;

    public Context(ResourceBundle rs) {
      this.rs = rs;
    }

    public String appRes(String key) {
      try {
        return rs.getString(key).replaceAll("'", "&#39;").replaceAll("\"", "&#34;");
      } catch (java.util.MissingResourceException e) {
        LOG.warn("Can't find resource for bundle key " + key);
      } catch (Exception e) {
        LOG.error("Error when get resource bundle key " + key, e);
      }
      return key;
    }
    
  }
     
}

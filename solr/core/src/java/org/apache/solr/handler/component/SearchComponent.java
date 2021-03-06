/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.solr.handler.component;

import java.io.IOException;
import java.net.URL;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.SolrInfoMBean;
import org.apache.solr.util.plugin.NamedListInitializedPlugin;

/**
 * TODO!
 * 
 *
 * @since solr 1.3
 */
public abstract class SearchComponent implements SolrInfoMBean, NamedListInitializedPlugin
{
  /**
   * The name given to this component in solrconfig.xml file
   */
  private String name = this.getClass().getName();
  /**
   * Prepare the response.  Guaranteed to be called before any SearchComponent {@link #process(org.apache.solr.handler.component.ResponseBuilder)} method.
   * Called for every incoming request.
   *
   * The place to do initialization that is request dependent.
   * @param rb The {@link org.apache.solr.handler.component.ResponseBuilder}
   * @throws IOException If there is a low-level I/O error.
   */
  public abstract void prepare(ResponseBuilder rb) throws IOException;

  /**
   * Process the request for this component 
   * @param rb The {@link ResponseBuilder}
   * @throws IOException If there is a low-level I/O error.
   */
  public abstract void process(ResponseBuilder rb) throws IOException;

  /**
   * Process for a distributed search.
   * @return the next stage for this component
   */
  public int distributedProcess(ResponseBuilder rb) throws IOException {
    return ResponseBuilder.STAGE_DONE;
  }

  /** Called after another component adds a request */
  public void modifyRequest(ResponseBuilder rb, SearchComponent who, ShardRequest sreq) {
  }

  /** Called after all responses for a single request were received */
  public void handleResponses(ResponseBuilder rb, ShardRequest sreq) {
  }

  /** Called after all responses have been received for this stage.
   * Useful when different requests are sent to each shard.
   */
  public void finishStage(ResponseBuilder rb) {
  }
  
  /**
   * Sets the name of the SearchComponent. The name of the component is usually
   * the name defined for it in the configuration.
   */
  public void setName(String name) {
    this.name = name;
  }


  //////////////////////// NamedListInitializedPlugin methods //////////////////////
  @Override
  public void init( NamedList args )
  {
    // By default do nothing
  }
  
  //////////////////////// SolrInfoMBeans methods //////////////////////

  @Override
  public String getName() {
    return name;
  }

  @Override
  public abstract String getDescription();
  @Override
  public abstract String getSource();
  
  @Override
  public String getVersion() {
    return getClass().getPackage().getSpecificationVersion();
  }
  
  @Override
  public Category getCategory() {
    return Category.OTHER;
  }

  @Override
  public URL[] getDocs() {
    return null;  // this can be overridden, but not required
  }

  @Override
  public NamedList getStatistics() {
    return null;
  }
}

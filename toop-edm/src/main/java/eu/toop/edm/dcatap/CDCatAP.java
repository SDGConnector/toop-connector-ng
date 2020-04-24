/**
 * Copyright (C) 2018-2020 toop.eu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.toop.edm.dcatap;

import java.util.List;

import javax.annotation.Nonnull;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.xsds.ccts.cct.schemamodule.CCCTS;

public final class CDCatAP
{
  @Nonnull
  private static final ClassLoader _getCL ()
  {
    return CDCatAP.class.getClassLoader ();
  }

  public static final List <ClassPathResource> XSDS = new CommonsArrayList <> (CCCTS.getXSDResource (),
                                                                               new ClassPathResource ("schemas/skos.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/xml.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/dcterms.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/CV-DataTypes.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/CV-CommonBasicComponents.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/foaf.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/adms.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/odrl.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/rdf.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/spdx.xsd",
                                                                                                      _getCL ()),
                                                                               new ClassPathResource ("schemas/dcat-ap.xsd",
                                                                                                      _getCL ())).getAsUnmodifiable ();

  private CDCatAP ()
  {}
}

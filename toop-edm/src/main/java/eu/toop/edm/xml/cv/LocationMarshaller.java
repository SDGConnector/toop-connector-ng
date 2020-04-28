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
package eu.toop.edm.xml.cv;

import com.helger.jaxb.GenericJAXBMarshaller;

import eu.toop.edm.jaxb.w3.cv.location.CvlocationType;

public class LocationMarshaller extends GenericJAXBMarshaller <CvlocationType>
{
  public LocationMarshaller ()
  {
    super (CvlocationType.class, CCV.XSDS, x -> new ObjectFactory ().createCvlocation (x));
    setNamespaceContext (CCVNamespaceContext.getInstance ());
  }
}

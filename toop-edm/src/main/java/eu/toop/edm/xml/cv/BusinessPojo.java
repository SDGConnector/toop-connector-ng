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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.string.StringHelper;

import eu.toop.edm.jaxb.w3.cv.ac.CoreBusinessType;
import eu.toop.edm.jaxb.w3.cv.bc.LegalEntityIDType;
import eu.toop.edm.jaxb.w3.cv.bc.LegalEntityLegalIDType;
import eu.toop.edm.jaxb.w3.cv.bc.LegalEntityLegalNameType;

public class BusinessPojo
{
  private final String m_sLegalID;
  private final String m_sLegalIDSchemeID;
  private final String m_sID;
  private final String m_sIDSchemeID;
  private final String m_sLegalName;
  private final AddressPojo m_aAddress;

  public BusinessPojo (@Nullable final String sLegalID,
                       @Nullable final String sLegalIDType,
                       @Nullable final String sID,
                       @Nullable final String sIDType,
                       @Nullable final String sLegalName,
                       @Nullable final AddressPojo aAddress)
  {
    m_sLegalID = sLegalID;
    m_sLegalIDSchemeID = sLegalIDType;
    m_sID = sID;
    m_sIDSchemeID = sIDType;
    m_sLegalName = sLegalName;
    m_aAddress = aAddress;
  }

  @Nonnull
  public CoreBusinessType getAsBusiness ()
  {
    final CoreBusinessType ret = new CoreBusinessType ();

    if (StringHelper.hasText (m_sLegalID))
    {
      final LegalEntityLegalIDType aLegalID = new LegalEntityLegalIDType ();
      aLegalID.setValue (m_sLegalID);
      aLegalID.setSchemeID (m_sLegalIDSchemeID);
      ret.addLegalEntityLegalID (aLegalID);
    }
    if (StringHelper.hasText (m_sID))
    {
      final LegalEntityIDType aLegalID = new LegalEntityIDType ();
      aLegalID.setValue (m_sID);
      aLegalID.setSchemeID (m_sIDSchemeID);
      ret.addLegalEntityID (aLegalID);
    }
    if (StringHelper.hasText (m_sLegalName))
    {
      final LegalEntityLegalNameType aLegalName = new LegalEntityLegalNameType ();
      aLegalName.setValue (m_sLegalName);
      ret.addLegalEntityLegalName (aLegalName);
    }
    if (m_aAddress != null)
      ret.setLegalEntityCoreAddress (m_aAddress.getAsAddress ());

    return ret;
  }

  @Nonnull
  public static BusinessPojo createMinimum ()
  {
    return new BusinessPojo (null, null, null, null, null, null);
  }
}
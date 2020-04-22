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
package eu.toop.edm.regrep;

import javax.annotation.Nonnull;

import org.w3.ns.corevocabulary.business.CvbusinessType;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;

import eu.toop.edm.cv.BusinessMarshaller;
import eu.toop.regrep.SlotBuilder;
import eu.toop.regrep.rim.SlotType;

/**
 * DataSubject "LegalPerson" slot
 *
 * @author Philip Helger
 */
public class SlotDataSubjectLegalPerson implements ISlotProvider
{
  public static final String NAME = "LegalPerson";

  private final CvbusinessType m_aLegalPerson;

  public SlotDataSubjectLegalPerson (@Nonnull final CvbusinessType aLegalPerson)
  {
    ValueEnforcer.notNull (aLegalPerson, "LegalPerson");
    m_aLegalPerson = aLegalPerson;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return NAME;
  }

  @Nonnull
  public SlotType createSlot ()
  {
    return new SlotBuilder ().setName (NAME)
                             .setValue (new BusinessMarshaller ().getAsDocument (m_aLegalPerson))
                             .build ();
  }
}

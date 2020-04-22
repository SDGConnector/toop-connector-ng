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

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;

import eu.toop.regrep.SlotBuilder;
import eu.toop.regrep.rim.InternationalStringValueType;
import eu.toop.regrep.rim.SlotType;

/**
 * "Procedure" slot
 *
 * @author Philip Helger
 */
public class SlotProcedure implements ISlotProvider
{
  public static final String NAME = "Procedure";

  private final InternationalStringValueType m_aString;

  public SlotProcedure (@Nonnull final InternationalStringValueType aString)
  {
    ValueEnforcer.notNull (aString, "String");
    m_aString = aString;
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
    return new SlotBuilder ().setName (NAME).setValue (m_aString).build ();
  }
}
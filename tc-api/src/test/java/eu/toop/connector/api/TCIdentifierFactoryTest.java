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
package eu.toop.connector.api;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.helger.peppolid.IParticipantIdentifier;

/**
 * Test class for class {@link TCIdentifierFactory}.
 *
 * @author Philip Helger
 */
public final class TCIdentifierFactoryTest
{
  @Test
  public void testBasic ()
  {
    final TCIdentifierFactory aIF = TCIdentifierFactory.INSTANCE_TC;

    final IParticipantIdentifier aPI1 = aIF.createParticipantIdentifier (null, "iso6523-actorid-upis::9999:elonia");
    final IParticipantIdentifier aPI2 = aIF.createParticipantIdentifier ("iso6523-actorid-upis", "9999:elonia");
    assertNotEquals (aPI1.getURIEncoded (), aPI2.getURIEncoded ());
  }
}
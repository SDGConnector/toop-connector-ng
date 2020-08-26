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
package eu.toop.connector.api.me.incoming;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.annotation.Nonempty;

/**
 * Base interface for incoming EDM objects.
 *
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface IIncomingEDMObject
{
  /**
   * @return The AS4 Content-ID of the AS4 MIME part containing the EDM object.
   */
  @Nonnull
  @Nonempty
  String getTopLevelContentID ();

  /**
   * @return The incoming metadata associated with this request. Never
   *         <code>null</code>.
   */
  @Nonnull
  IMEIncomingTransportMetadata getMetadata ();
}

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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.impl.CommonsLinkedHashMap;
import com.helger.commons.collection.impl.ICommonsOrderedMap;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

import eu.toop.connector.api.me.model.MEPayload;
import eu.toop.edm.EDMResponse;

/**
 * Incoming EDM response. Uses {@link EDMResponse}, optional attachments and
 * {@link IMEIncomingTransportMetadata} for the metadata.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class IncomingEDMResponse implements IIncomingEDMResponse
{
  private final EDMResponse m_aResponse;
  private final ICommonsOrderedMap <String, MEPayload> m_aAttachments = new CommonsLinkedHashMap <> ();
  private final IMEIncomingTransportMetadata m_aMetadata;

  public IncomingEDMResponse (@Nonnull final EDMResponse aResponse,
                              @Nullable final List <MEPayload> aAttachments,
                              @Nonnull final IMEIncomingTransportMetadata aMetadata)
  {
    ValueEnforcer.notNull (aResponse, "Response");
    ValueEnforcer.notNull (aMetadata, "Metadata");

    m_aResponse = aResponse;
    if (aAttachments != null)
      for (final MEPayload aItem : aAttachments)
        m_aAttachments.put (aItem.getContentID (), aItem);
    m_aMetadata = aMetadata;
  }

  @Nonnull
  public EDMResponse getResponse ()
  {
    return m_aResponse;
  }

  @Nonnull
  @ReturnsMutableObject
  public ICommonsOrderedMap <String, MEPayload> attachments ()
  {
    return m_aAttachments;
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsOrderedMap <String, MEPayload> getAllAttachments ()
  {
    return m_aAttachments.getClone ();
  }

  @Nonnull
  public IMEIncomingTransportMetadata getMetadata ()
  {
    return m_aMetadata;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;

    final IncomingEDMResponse rhs = (IncomingEDMResponse) o;
    return m_aResponse.equals (rhs.m_aResponse) && m_aAttachments.equals (rhs.m_aAttachments) && m_aMetadata.equals (rhs.m_aMetadata);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aResponse).append (m_aAttachments).append (m_aMetadata).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Response", m_aResponse)
                                       .append ("Attachments", m_aAttachments)
                                       .append ("Metadata", m_aMetadata)
                                       .getToString ();
  }
}

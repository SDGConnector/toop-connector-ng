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
package eu.toop.connector.api.me.model;

import java.io.Serializable;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;

/**
 * List of {@link MEPayload} objects.
 *
 * @author myildiz at 12.02.2018.
 */
public class MEMessage implements Serializable
{
  private final ICommonsList <MEPayload> m_aPayloads = new CommonsArrayList <> ();

  protected MEMessage (@Nonnull @Nonempty final ICommonsList <MEPayload> aPayloads)
  {
    ValueEnforcer.notEmptyNoNullValue (aPayloads, "Paayloads");
    m_aPayloads.addAll ();
  }

  /**
   * @return A non-<code>null</code>, non-empty list of payloads. The result
   *         object is mutable and can change the content of this object.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableObject
  public ICommonsList <MEPayload> payloads ()
  {
    return m_aPayloads;
  }

  /**
   * @return A non-<code>null</code>, non-empty list of payloads. The result
   *         object is a cloned list.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsList <MEPayload> getAllPayloads ()
  {
    return m_aPayloads.getClone ();
  }

  @Nonnull
  public static Builder builder ()
  {
    return new Builder ();
  }

  /**
   * Builder class for {@link MEMessage}.
   *
   * @author Philip Helger
   */
  public static class Builder
  {
    private final ICommonsList <MEPayload> m_aPayloads = new CommonsArrayList <> ();

    protected Builder ()
    {}

    @Nonnull
    public Builder addPayload (@Nullable final Consumer <? super MEPayload.Builder> a)
    {
      if (a != null)
      {
        final MEPayload.Builder aBuilder = MEPayload.builder ();
        a.accept (aBuilder);
        addPayload (aBuilder);
      }
      return this;
    }

    @Nonnull
    public Builder addPayload (@Nullable final MEPayload.Builder a)
    {
      return addPayload (a == null ? null : a.build ());
    }

    @Nonnull
    public Builder addPayload (@Nullable final MEPayload a)
    {
      if (a != null)
        m_aPayloads.add (a);
      return this;
    }

    @Nonnull
    public Builder payload (@Nullable final Consumer <? super MEPayload.Builder> a)
    {
      if (a != null)
      {
        final MEPayload.Builder aBuilder = MEPayload.builder ();
        a.accept (aBuilder);
        payload (aBuilder);
      }
      return this;
    }

    @Nonnull
    public Builder payload (@Nullable final MEPayload.Builder a)
    {
      return payload (a == null ? null : a.build ());
    }

    @Nonnull
    public Builder payload (@Nullable final MEPayload a)
    {
      if (a != null)
        m_aPayloads.set (a);
      else
        m_aPayloads.clear ();
      return this;
    }

    @Nonnull
    public Builder payloads (@Nullable final MEPayload... a)
    {
      m_aPayloads.setAll (a);
      return this;
    }

    @Nonnull
    public Builder payloads (@Nullable final Iterable <MEPayload> a)
    {
      m_aPayloads.setAll (a);
      return this;
    }

    public void checkConsistency ()
    {
      if (m_aPayloads.isEmpty ())
        throw new IllegalStateException ("At least one payload MUST be present");
    }

    @Nonnull
    public MEMessage build ()
    {
      checkConsistency ();
      return new MEMessage (m_aPayloads);
    }
  }
}
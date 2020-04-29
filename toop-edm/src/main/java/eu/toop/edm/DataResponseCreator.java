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
package eu.toop.edm;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsLinkedHashMap;
import com.helger.commons.collection.impl.CommonsLinkedHashSet;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsOrderedMap;
import com.helger.commons.collection.impl.ICommonsOrderedSet;
import com.helger.commons.datetime.PDTFactory;

import eu.toop.edm.jaxb.cv.agent.AgentType;
import eu.toop.edm.jaxb.w3.cv.ac.CoreBusinessType;
import eu.toop.edm.jaxb.w3.cv.ac.CorePersonType;
import eu.toop.edm.slot.ISlotProvider;
import eu.toop.edm.slot.SlotConsentToken;
import eu.toop.edm.slot.SlotDataConsumer;
import eu.toop.edm.slot.SlotDataProvider;
import eu.toop.edm.slot.SlotDataSubjectLegalPerson;
import eu.toop.edm.slot.SlotDataSubjectNaturalPerson;
import eu.toop.edm.slot.SlotDatasetIdentifier;
import eu.toop.edm.slot.SlotIssueDateTime;
import eu.toop.regrep.ERegRepResponseStatus;
import eu.toop.regrep.RegRepHelper;
import eu.toop.regrep.query.QueryResponse;

public class DataResponseCreator
{
  private static final ICommonsOrderedSet <String> HEADER_SLOTS = new CommonsLinkedHashSet <> (SlotIssueDateTime.NAME,
                                                                                               SlotDataProvider.NAME);

  private final ERegRepResponseStatus m_eResponseStatus;
  private final ICommonsOrderedMap <String, ISlotProvider> m_aProviders = new CommonsLinkedHashMap <> ();

  private DataResponseCreator (@Nonnull final ERegRepResponseStatus eResponseStatus,
                               @Nonnull final ICommonsList <ISlotProvider> aProviders)
  {
    ValueEnforcer.notNull (eResponseStatus, "ResponseStatus");
    ValueEnforcer.noNullValue (aProviders, "Providers");

    m_eResponseStatus = eResponseStatus;
    for (final ISlotProvider aItem : aProviders)
    {
      final String sName = aItem.getName ();
      if (m_aProviders.containsKey (sName))
        throw new IllegalArgumentException ("A slot provider for name '" + sName + "' is already present");
      m_aProviders.put (sName, aItem);
    }
  }

  @Nonnull
  QueryResponse createQueryResponse ()
  {
    final QueryResponse ret = RegRepHelper.createEmptyQueryResponse (m_eResponseStatus);
    // All slots outside of query
    for (final String sHeader : HEADER_SLOTS)
    {
      final ISlotProvider aSP = m_aProviders.get (sHeader);
      if (aSP != null)
        ret.addSlot (aSP.createSlot ());
    }
    // All slots inside of query
    // TODO
    // for (final Map.Entry <String, ISlotProvider> aEntry :
    // m_aProviders.entrySet ())
    // if (!HEADER_SLOTS.contains (aEntry.getKey ()))
    // ret.getRegistryObjectList ().getQuery ().addSlot (aEntry.getValue
    // ().createSlot ());

    return ret;
  }

  @Nonnull
  public static Builder builder ()
  {
    return new Builder ();
  }

  public static class Builder
  {
    private ERegRepResponseStatus m_eResponseStatus;
    private LocalDateTime m_aIssueDateTime;
    private AgentType m_aDataConsumer;
    private AgentType m_aataProvider;
    private String m_sConsentToken;
    private String m_sDataSetIdentifier;
    private CoreBusinessType m_aDSLegalPerson;
    private CorePersonType m_aDSNaturalPerson;

    public Builder ()
    {}

    @Nonnull
    public Builder setResponseStatus (@Nullable final ERegRepResponseStatus ResponseStatus)
    {
      m_eResponseStatus = ResponseStatus;
      return this;
    }

    @Nonnull
    public Builder setIssueDateTime (@Nullable final LocalDateTime aIssueDateTime)
    {
      m_aIssueDateTime = aIssueDateTime;
      return this;
    }

    @Nonnull
    public Builder setIssueDateTimeNow ()
    {

      return setIssueDateTime (PDTFactory.getCurrentLocalDateTime ());
    }

    @Nonnull
    public Builder setDataConsumer (@Nullable final AgentType aAgent)
    {
      m_aDataConsumer = aAgent;
      return this;
    }

    @Nonnull
    public Builder setDataProvider (@Nullable final AgentType aAgent)
    {
      m_aataProvider = aAgent;
      return this;
    }

    @Nonnull
    public Builder setConsentToken (@Nullable final String sConsentToken)
    {
      m_sConsentToken = sConsentToken;
      return this;
    }

    @Nonnull
    public Builder setDataSetIdentifier (@Nullable final String sDataSetIdentifier)
    {
      m_sDataSetIdentifier = sDataSetIdentifier;
      return this;
    }

    @Nonnull
    public Builder setDataSubject (@Nullable final CoreBusinessType aBusiness)
    {
      m_aDSLegalPerson = aBusiness;
      m_aDSNaturalPerson = null;
      return this;
    }

    @Nonnull
    public Builder setDataSubject (@Nullable final CorePersonType aPerson)
    {
      m_aDSLegalPerson = null;
      m_aDSNaturalPerson = aPerson;
      return this;
    }

    public void checkConsistency ()
    {
      if (m_eResponseStatus == null)
        throw new IllegalStateException ("Response Status must be present");
      if (m_aIssueDateTime == null)
        throw new IllegalStateException ("Issue Date Time must be present");
    }

    @Nonnull
    public QueryResponse build ()
    {
      checkConsistency ();

      final ICommonsList <ISlotProvider> x = new CommonsArrayList <> ();
      if (m_aIssueDateTime != null)
        x.add (new SlotIssueDateTime (m_aIssueDateTime));
      if (m_aDataConsumer != null)
        x.add (new SlotDataConsumer (m_aDataConsumer));
      if (m_aataProvider != null)
        x.add (new SlotDataProvider (m_aataProvider));
      if (m_sConsentToken != null)
        x.add (new SlotConsentToken (m_sConsentToken));
      if (m_sDataSetIdentifier != null)
        x.add (new SlotDatasetIdentifier (m_sDataSetIdentifier));
      if (m_aDSLegalPerson != null)
        x.add (new SlotDataSubjectLegalPerson (m_aDSLegalPerson));
      if (m_aDSNaturalPerson != null)
        x.add (new SlotDataSubjectNaturalPerson (m_aDSNaturalPerson));
      return new DataResponseCreator (m_eResponseStatus, x).createQueryResponse ();
    }
  }
}

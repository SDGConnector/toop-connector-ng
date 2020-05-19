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
package eu.toop.connector.app.dsd;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.string.ToStringGenerator;
import com.helger.pd.searchapi.v1.IDType;
import com.helger.pd.searchapi.v1.MatchType;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;

import eu.toop.connector.api.TCConfig;
import eu.toop.connector.api.dsd.IDSDParticipantIDProvider;
import eu.toop.connector.api.http.TCHttpClientSettings;
import eu.toop.connector.api.smp.ISMPErrorHandler;
import eu.toop.dsd.client.DSDClient;
import eu.toop.edm.error.EToopErrorCode;

/**
 * This class implements the {@link IDSDParticipantIDProvider} interface using a
 * remote query to TOOP Directory.
 *
 * @author Philip Helger
 */
public class DSDParticipantIDProviderRemote implements IDSDParticipantIDProvider
{
  private final String m_sBaseURL;

  /**
   * Constructor using the TOOP Directory URL from the configuration file.
   */
  public DSDParticipantIDProviderRemote ()
  {
    this (TCConfig.DSD.getR2D2DirectoryBaseUrl ());
  }

  /**
   * Constructor with an arbitrary TOOP Directory URL.
   *
   * @param sBaseURL
   *        The base URL to be used. May neither be <code>null</code> nor empty.
   */
  public DSDParticipantIDProviderRemote (@Nonnull final String sBaseURL)
  {
    ValueEnforcer.notEmpty (sBaseURL, "BaseURL");
    m_sBaseURL = sBaseURL;
  }

  /**
   * @return The TOOP Directory Base URL as provided in the constructor. Neither
   *         <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public final String getBaseURL ()
  {
    return m_sBaseURL;
  }

  @Nonnull
  public ICommonsSet <IParticipantIdentifier> getAllParticipantIDs (@Nonnull final String sLogPrefix,
                                                                    @Nonnull final String sDatasetType,
                                                                    @Nullable final String sCountryCode,
                                                                    @Nullable final IDocumentTypeIdentifier aDocumentTypeID,
                                                                    @Nonnull final ISMPErrorHandler aErrorHandler)
  {
    final ICommonsSet <IParticipantIdentifier> ret = new CommonsHashSet <> ();

    final DSDClient aDSDClient = new DSDClient (m_sBaseURL);
    aDSDClient.setHttpClientSettings (new TCHttpClientSettings ());

    List <MatchType> aMatches = null;
    try
    {
      aMatches = aDSDClient.queryDataset (sDatasetType, sCountryCode);
    }
    catch (final RuntimeException ex)
    {
      aErrorHandler.onError ("Failed to query the DSD", ex, EToopErrorCode.DD_001);
    }

    if (aMatches != null)
      for (final MatchType aMatch : aMatches)
      {
        boolean bCanUse = false;
        if (aDocumentTypeID == null)
        {
          // No doc type filter
          bCanUse = true;
        }
        else
          for (final IDType aDocTypeID : aMatch.getDocTypeID ())
            if (aDocumentTypeID.hasScheme (aDocTypeID.getScheme ()) && aDocumentTypeID.hasValue (aDocTypeID.getValue ()))
            {
              bCanUse = true;
              break;
            }

        if (bCanUse)
        {
          ret.add (TCConfig.getIdentifierFactory ()
                           .createParticipantIdentifier (aMatch.getParticipantID ().getScheme (), aMatch.getParticipantID ().getValue ()));
        }
      }

    return ret;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("BaseURL", m_sBaseURL).getToString ();
  }
}
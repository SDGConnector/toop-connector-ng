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
package eu.toop.connector.webapi;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.concurrent.SimpleReadWriteLock;

import eu.toop.connector.api.dd.IDDServiceGroupHrefProvider;
import eu.toop.connector.api.dd.IDDServiceMetadataProvider;
import eu.toop.connector.api.dsd.IDSDParticipantIDProvider;
import eu.toop.connector.api.validation.IVSValidator;
import eu.toop.connector.app.dd.DDServiceGroupHrefProviderSMP;
import eu.toop.connector.app.dd.DDServiceMetadataProviderSMP;
import eu.toop.connector.app.dsd.DSDParticipantIDProviderRemote;
import eu.toop.connector.app.validation.TCValidator;

/**
 * Global TOOP Connector NG API configuration.<br>
 * This configuration is e.g. changed by the TOOP Simulator to install "mock"
 * handler.
 *
 * @author Philip Helger
 */
public final class TCAPIConfig
{
  private static final SimpleReadWriteLock s_aRWLock = new SimpleReadWriteLock ();

  @GuardedBy ("s_aRWLock")
  private static IDSDParticipantIDProvider s_aDSDPartyIDProvider = new DSDParticipantIDProviderRemote ();
  @GuardedBy ("s_aRWLock")
  private static IDDServiceGroupHrefProvider s_aDDSGHrefProvider = new DDServiceGroupHrefProviderSMP ();
  @GuardedBy ("s_aRWLock")
  private static IDDServiceMetadataProvider s_aDDSMProvider = new DDServiceMetadataProviderSMP ();
  @GuardedBy ("s_aRWLock")
  private static IVSValidator s_aValidator = new TCValidator ();

  private TCAPIConfig ()
  {}

  @Nonnull
  public static IDSDParticipantIDProvider getDSDPartyIDIdentifier ()
  {
    return s_aRWLock.readLockedGet ( () -> s_aDSDPartyIDProvider);
  }

  public static void setDSDPartyIDIdentifier (@Nonnull final IDSDParticipantIDProvider aIDProvider)
  {
    ValueEnforcer.notNull (aIDProvider, "DSDParticipantIDProvider");
    s_aRWLock.writeLockedGet ( () -> s_aDSDPartyIDProvider = aIDProvider);
  }

  @Nonnull
  public static IDDServiceGroupHrefProvider getDDServiceGroupHrefProvider ()
  {
    return s_aRWLock.readLockedGet ( () -> s_aDDSGHrefProvider);
  }

  public static void setDDServiceGroupHrefProvider (@Nonnull final IDDServiceGroupHrefProvider aDDSGHrefProvider)
  {
    ValueEnforcer.notNull (aDDSGHrefProvider, "DDServiceGroupHrefProvider");
    s_aRWLock.writeLockedGet ( () -> s_aDDSGHrefProvider = aDDSGHrefProvider);
  }

  @Nonnull
  public static IDDServiceMetadataProvider getDDServiceMetadataProvider ()
  {
    return s_aRWLock.readLockedGet ( () -> s_aDDSMProvider);
  }

  public static void setDDServiceMetadataProvider (@Nonnull final IDDServiceMetadataProvider aDDSMProvider)
  {
    ValueEnforcer.notNull (aDDSMProvider, "DDServiceMetadataProvider");
    s_aRWLock.writeLockedGet ( () -> s_aDDSMProvider = aDDSMProvider);
  }

  @Nonnull
  public static IVSValidator getVSValidator ()
  {
    return s_aRWLock.readLockedGet ( () -> s_aValidator);
  }

  public static void setVSValidator (@Nonnull final IVSValidator aValidator)
  {
    ValueEnforcer.notNull (aValidator, "VSValidator");
    s_aRWLock.writeLockedGet ( () -> s_aValidator = aValidator);
  }
}
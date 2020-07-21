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
package eu.toop.connector.servlet;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;

import com.helger.photon.api.IAPIRegistry;
import com.helger.photon.audit.AuditHelper;
import com.helger.photon.audit.DoNothingAuditor;
import com.helger.photon.core.servlet.WebAppListener;
import com.helger.photon.security.login.LoggedInUserManager;

import eu.toop.connector.api.TCConfig;
import eu.toop.connector.app.TCInit;
import eu.toop.connector.webapi.TCAPIInit;

/**
 * Global startup etc. listener.
 *
 * @author Philip Helger
 */
public class TCWebAppListener extends WebAppListener
{
  public TCWebAppListener ()
  {
    setHandleStatisticsOnEnd (false);
  }

  @Override
  protected String getDataPath (@Nonnull final ServletContext aSC)
  {
    String ret = TCConfig.WebApp.getDataPath ();
    if (ret == null)
    {
      // Fall back to servlet context path
      ret = super.getDataPath (aSC);
    }
    return ret;
  }

  @Override
  protected String getServletContextPath (final ServletContext aSC) throws IllegalStateException
  {
    try
    {
      return super.getServletContextPath (aSC);
    }
    catch (final IllegalStateException ex)
    {
      // E.g. "Unpack WAR files" in Tomcat is disabled
      return getDataPath (aSC);
    }
  }

  @Override
  protected void afterContextInitialized (final ServletContext aSC)
  {
    TCInit.initGlobally (aSC);
    // Don't write audit logs
    AuditHelper.setAuditor (new DoNothingAuditor (LoggedInUserManager.getInstance ()));
  }

  @Override
  protected void initAPI (@Nonnull final IAPIRegistry aAPIRegistry)
  {
    TCAPIInit.initAPI (aAPIRegistry);
  }

  @Override
  protected void beforeContextDestroyed (final ServletContext aSC)
  {
    TCInit.shutdownGlobally (aSC);
  }
}

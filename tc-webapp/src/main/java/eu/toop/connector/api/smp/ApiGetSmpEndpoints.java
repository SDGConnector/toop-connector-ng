/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
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
package eu.toop.connector.api.smp;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.CGlobal;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.http.CHttp;
import com.helger.commons.mime.CMimeType;
import com.helger.commons.timing.StopWatch;
import com.helger.json.IJsonObject;
import com.helger.json.serialize.JsonWriter;
import com.helger.json.serialize.JsonWriterSettings;
import com.helger.peppolid.IDocumentTypeIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.photon.api.IAPIDescriptor;
import com.helger.photon.api.IAPIExecutor;
import com.helger.servlet.response.UnifiedResponse;
import com.helger.smpclient.bdxr1.BDXRClientReadOnly;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;
import com.helger.xsds.bdxr.smp1.ServiceMetadataType;
import com.helger.xsds.bdxr.smp1.SignedServiceMetadataType;

import eu.toop.connector.api.APIParamException;
import eu.toop.connector.api.TCConfig;
import eu.toop.connector.app.smp.R2D2EndpointProviderBDXRSMP1;

public final class ApiGetSmpEndpoints implements IAPIExecutor
{
  private static final Logger LOGGER = LoggerFactory.getLogger (ApiGetSmpEndpoints.class);

  public void invokeAPI (@Nonnull final IAPIDescriptor aAPIDescriptor,
                         @Nonnull @Nonempty final String sPath,
                         @Nonnull final Map <String, String> aPathVariables,
                         @Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                         @Nonnull final UnifiedResponse aUnifiedResponse) throws Exception
  {
    final String sParticipantID = aPathVariables.get ("pid");
    final IParticipantIdentifier aParticipantID = TCConfig.getIdentifierFactory ().parseParticipantIdentifier (sParticipantID);
    if (aParticipantID == null)
      throw new APIParamException ("Invalid participant ID '" + sParticipantID + "' provided.");

    final String sDocTypeID = aPathVariables.get ("doctypeid");
    final IDocumentTypeIdentifier aDocTypeID = TCConfig.getIdentifierFactory ().parseDocumentTypeIdentifier (sDocTypeID);
    if (aDocTypeID == null)
      throw new APIParamException ("Invalid document type ID '" + sDocTypeID + "' provided.");

    final boolean bXMLSchemaValidation = aRequestScope.params ().getAsBoolean ("xmlSchemaValidation", true);
    final boolean bVerifySignature = aRequestScope.params ().getAsBoolean ("verifySignature", true);

    final ZonedDateTime aQueryDT = PDTFactory.getCurrentZonedDateTimeUTC ();
    final StopWatch aSW = StopWatch.createdStarted ();

    LOGGER.info ("[API] Participant information of '" +
                 aParticipantID.getURIEncoded () +
                 "' is queried for document type '" +
                 aDocTypeID.getURIEncoded () +
                 "'; XSD validation=" +
                 bXMLSchemaValidation +
                 "; signature verification=" +
                 bVerifySignature);

    final BDXRClientReadOnly aBDXR1Client = R2D2EndpointProviderBDXRSMP1.getSMPClient (aParticipantID);
    aBDXR1Client.setXMLSchemaValidation (bXMLSchemaValidation);
    aBDXR1Client.setVerifySignature (bVerifySignature);

    final SignedServiceMetadataType aSSM = aBDXR1Client.getServiceMetadataOrNull (aParticipantID, aDocTypeID);
    IJsonObject aJson = null;
    if (aSSM != null)
    {
      final ServiceMetadataType aSM = aSSM.getServiceMetadata ();
      aJson = SMPJsonResponse.convert (aParticipantID, aDocTypeID, aSM);
    }

    aSW.stop ();

    if (aJson == null)
    {
      LOGGER.error ("[API] Failed to perform the SMP lookup");
      aUnifiedResponse.setStatus (CHttp.HTTP_NOT_FOUND);
    }
    else
    {
      LOGGER.info ("[API] Succesfully finished lookup lookup after " + aSW.getMillis () + " milliseconds");

      aJson.add ("queryDateTime", DateTimeFormatter.ISO_ZONED_DATE_TIME.format (aQueryDT));
      aJson.add ("queryDurationMillis", aSW.getMillis ());

      final String sRet = new JsonWriter (new JsonWriterSettings ().setIndentEnabled (true)).writeAsString (aJson);
      aUnifiedResponse.setContentAndCharset (sRet, StandardCharsets.UTF_8)
                      .setMimeType (CMimeType.APPLICATION_JSON)
                      .enableCaching (3 * CGlobal.SECONDS_PER_HOUR);
    }
  }
}

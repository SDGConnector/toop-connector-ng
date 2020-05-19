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

/**
 * The callback handler for incoming messages.
 *
 * @author Philip Helger
 */
public interface IMEIncomingHandler
{
  /**
   * Handle an incoming request for step 2/4.
   *
   * @param aRequest
   *        The request to handle. Never <code>null</code>.
   * @throws MEIncomingException
   *         In case of error.
   */
  void handleIncomingRequest (@Nonnull IncomingEDMRequest aRequest) throws MEIncomingException;

  /**
   * Handle an incoming response for step 4/4.
   *
   * @param aResponse
   *        The response to handle. Contains attachments and metadata. Never
   *        <code>null</code>.
   * @throws MEIncomingException
   *         In case of error.
   */
  void handleIncomingResponse (@Nonnull IncomingEDMResponse aResponse) throws MEIncomingException;

  /**
   * Handle an incoming error response for step 4/4.
   *
   * @param aErrorResponse
   *        The response to handle. Never <code>null</code>.
   * @throws MEIncomingException
   *         In case of error.
   */
  void handleIncomingErrorResponse (@Nonnull IncomingEDMErrorResponse aErrorResponse) throws MEIncomingException;
}
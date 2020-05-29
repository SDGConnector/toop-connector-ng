package eu.toop.connector.app.dsd;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helger.commons.collection.impl.ICommonsSet;

import eu.toop.connector.api.dd.LoggingDDErrorHandler;
import eu.toop.connector.api.dsd.DSDDatasetResponse;

/**
 * Test class of class {@link DSDDatasetResponseProviderRemote}.
 * 
 * @author Philip Helger
 */
public final class DSDDatasetResponseProviderRemoteTest
{
  @Test
  public void testSimple ()
  {
    final ICommonsSet <DSDDatasetResponse> aResp = new DSDDatasetResponseProviderRemote ().getAllDatasetResponses ("test",
                                                                                                                   "REGISTERED_ORGANIZATION_TYPE",
                                                                                                                   null,
                                                                                                                   LoggingDDErrorHandler.INSTANCE);
    assertNotNull (aResp);
  }
}

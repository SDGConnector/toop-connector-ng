package eu.toop.edm.regrep;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.datetime.util.PDTXMLConverter;
import com.helger.xml.serialize.read.DOMReader;

import eu.toop.regrep.RegRep4Writer;
import eu.toop.regrep.query.QueryRequest;

/**
 * Test class for class {@link RegRepHelper}
 *
 * @author Philip Helger
 */
public final class RegRepHelperTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (RegRepHelperTest.class);

  private static void _validate (@Nonnull final QueryRequest aQR)
  {
    assertNotNull (aQR);

    if (true)
    {
      LOGGER.info (RegRep4Writer.queryRequest ().setFormattedOutput (true).getAsString (aQR));
    }

    assertNotNull (RegRep4Writer.queryRequest ().getAsDocument (aQR));
  }

  @Test
  public void testBasic ()
  {
    final Document aDoc = DOMReader.readXMLDOM ("<root attr='a' xmlns='urn:anything-weird/bla-foo'><child><child2>value</child2></child></root>");

    final QueryRequest aQR = RegRepHelper.createQueryRequest ("mock-data-request",
                                                              new CommonsArrayList <> (RegRepHelper.createSlot ("IssueDateTime",
                                                                                                                RegRepHelper.createSlotValue (PDTXMLConverter.getXMLCalendarNow ())),
                                                                                       RegRepHelper.createSlot ("DataConsumerRequestPurpose",
                                                                                                                RegRepHelper.createSlotValue (RegRepHelper.createInternationalStringType (RegRepHelper.createLocalizedString (Locale.ENGLISH,
                                                                                                                                                                                                                              "Qualification Procedure in Public Procurement"),
                                                                                                                                                                                          RegRepHelper.createLocalizedString (Locale.GERMAN,
                                                                                                                                                                                                                              "Qualifizierungsverfahren im öffentlichen Beschaffungswesen")))),
                                                                                       RegRepHelper.createSlot ("DummyXML",
                                                                                                                RegRepHelper.createSlotValue (aDoc.getDocumentElement ()))));
    _validate (aQR);
  }
}

package eu.toop.edm.agent;

import javax.annotation.Nonnull;

import org.w3.ns.corevocabulary.aggregatecomponents.CvidentifierType;
import org.w3.ns.corevocabulary.basiccomponents.IdentifierType;
import org.w3.ns.corevocabulary.basiccomponents.IdentifierTypeType;

import com.helger.commons.string.StringHelper;

import eu.toop.edm.jaxb.cpsv.helper.AgentType;

public class DataProviderPojo
{
  private final String m_sID;
  private final String m_sIDType;
  private final String m_sName;

  public DataProviderPojo (final String sID, final String sIDType, final String sName)
  {
    m_sID = sID;
    m_sIDType = sIDType;
    m_sName = sName;
  }

  @Nonnull
  public AgentType getAsAgent ()
  {
    final AgentType ret = new AgentType ();
    if (StringHelper.hasText (m_sID))
    {
      final CvidentifierType aAgentID = new CvidentifierType ();
      final IdentifierType aID = new IdentifierType ();
      aID.setValue (m_sID);
      aAgentID.setIdentifier (aID);
      if (StringHelper.hasText (m_sIDType))
      {
        final IdentifierTypeType aIdentifierType = new IdentifierTypeType ();
        aIdentifierType.setValue (m_sIDType);
        aAgentID.setIdentifierType (aIdentifierType);
      }
      ret.setAgentID (aAgentID);
    }
    ret.setAgentName (m_sName);
    return ret;
  }
}

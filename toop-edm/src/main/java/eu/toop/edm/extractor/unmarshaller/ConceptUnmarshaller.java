package eu.toop.edm.extractor.unmarshaller;

import eu.toop.edm.jaxb.cccev.CCCEVConceptType;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.Objects;

class ConceptUnmarshaller implements SlotUnmarshaller<CCCEVConceptType> {
    public CCCEVConceptType unmarshal(Object object) throws JAXBException {
        if (Objects.isNull(object))
            return null;
        JAXBContext context = JAXBContext.newInstance(CCCEVConceptType.class);
        Unmarshaller um = context.createUnmarshaller();
        JAXBElement<CCCEVConceptType> root = um.unmarshal((Node)object, CCCEVConceptType.class);
        return root.getValue();
    }
}

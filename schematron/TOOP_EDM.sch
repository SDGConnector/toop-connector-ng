<?xml version="1.0" encoding="UTF-8"?>

<schema xmlns="http://purl.oclc.org/dsdl/schematron" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    queryBinding="xslt2" 
    >
    <ns prefix="query"  uri="urn:oasis:names:tc:ebxml-regrep:xsd:query:4.0"/>
    <ns prefix="rim"    uri="urn:oasis:names:tc:ebxml-regrep:xsd:rim:4.0"/>
    <ns prefix="cva"    uri="http://www.w3.org/ns/corevocabulary/AggregateComponents"/>
    <ns prefix="cvb"    uri="http://www.w3.org/ns/corevocabulary/BasicComponents"/>
    <ns prefix="cbd"    uri="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"/>
    <ns prefix="rs"     uri="urn:oasis:names:tc:ebxml-regrep:xsd:rs:4.0"/>
    <ns prefix="cpov"   uri="http://www.w3.org/ns/corevocabulary/po"/>
    <ns prefix="cagv"   uri="https://semic.org/sa/cv/cagv/agent-2.0.0#"/>
    <ns prefix="cbc"    uri="https://semic.org/sa/cv/common/cbc-2.0.0#"/> 
    <ns prefix="locn"   uri="http://www.w3.org/ns/locn#"/>
    <ns prefix="cccev"  uri="https://semic.org/sa/cv/cccev-2.0.0#"/>
    <ns prefix="dcat"   uri="http://data.europa.eu/r5r/"/>
    <ns prefix="dct"    uri="http://purl.org/dc/terms/"/>
    
    <title>TOOP EDM Rules (specs Version 2.0.0)</title>
    
    
    <pattern>
        <rule context="/">
            <assert test="( (exists(query:QueryRequest)) or (exists(query:QueryResponse)) )"  flag='ERROR' id="mandatory_request_or_response">
                The message must contain either a QueryRequest or a QueryResponse. Please check if the namespace is correct.
            </assert>
        </rule>
    </pattern>
    
    

    <!--******************************-->
    <!--CHECK ROOT SLOTS CARDINALITIES-->
    <!--******************************-->
    <pattern>
        <rule context="query:QueryRequest">
            
            <let name="countIssueDateTime" value="count(rim:Slot[@name = 'IssueDateTime'])"/>      
            <assert test="($countIssueDateTime=1)" flag='ERROR' id='req_card_IssueDateTime'>
                The QueryRequest must contain exactly ONE IssueDateTime slot (found: <value-of select="$countIssueDateTime"/>).
            </assert>  
            
            <let name="countProcedure" value="count(rim:Slot[@name = 'Procedure'])"/>  
            <assert test="($countProcedure=0) or ($countProcedure=1)" flag='ERROR' id='req_card_Procedure'>
                The QueryRequest must contain ZERO or ONE Procedure slots (found: <value-of select="$countProcedure"/>).
            </assert>
            
            <let name="countFullfillingRequirement" value="count(rim:Slot[@name = 'FullfillingRequirement'])"/>  
            <assert test="($countFullfillingRequirement=0) or ($countFullfillingRequirement=1)" flag='ERROR' id='req_card_FullfillingRequirement'>
                The QueryRequest must contain ZERO or ONE FullfillingRequirement slots (found: <value-of select="$countFullfillingRequirement"/>).
            </assert>
            
            <let name="countDataConsumer" value="count(rim:Slot[@name = 'DataConsumer'])"/>  
            <assert test="($countDataConsumer=1)" flag='ERROR' id='req_card_DataConsumer'>
                The QueryRequest must contain exactly ONE DataConsumer slot (found: <value-of select="$countDataConsumer"/>).
            </assert>
            
            <let name="countConsentToken" value="count(rim:Slot[@name = 'ConsentToken'])"/>  
            <assert test="($countConsentToken=0) or ($countConsentToken=1)" flag='ERROR' id='req_card_ConsentToken'>
                The QueryRequest must contain ZERO or ONE ConsentToken slots (found: <value-of select="$countConsentToken"/>).
            </assert>
            
            <let name="countDatasetIdentifier" value="count(rim:Slot[@name = 'DatasetIdentifier'])"/>  
            <assert test="($countDatasetIdentifier=1)" flag='ERROR' id='req_card_DatasetIdentifier'>
                The QueryRequest must contain exactly ONE DatasetIdentifier slot (found: <value-of select="$countDatasetIdentifier"/>).
            </assert>
            
            <let name="countQuery" value="count(query:Query)"/>  
            <assert test="($countQuery=1)" flag='ERROR' id='req_card_Query'>
                The QueryRequest must contain exactly ONE Query slot (found: <value-of select="$countQuery"/>).
            </assert>

        </rule>
    </pattern>
        
    <pattern>
        <rule context="query:QueryResponse">
            
            <let name="countIssueDateTime" value="count(rim:Slot[@name = 'IssueDateTime'])"/>      
            <assert test="($countIssueDateTime=1)" flag='ERROR' id='res_card_IssueDateTime'>
                The QueryResponse must contain exactly ONE IssueDateTime slot (found: <value-of select="$countIssueDateTime"/>).
            </assert>  
            
            <let name="countDataProvider" value="count(rim:Slot[@name = 'DataProvider'])"/>  
            <assert test="($countDataProvider=1)" flag='ERROR' id='res_card_DataProvider'>
                The QueryResponse must contain exactly ONE DataProvider slot (found: <value-of select="$countDataProvider"/>).
            </assert>
            
        </rule>
    </pattern>
    
    <!--TODO: check the EXCEPTION structure (not final yet)-->
    
    <!--TODO: check the DATATYPES-->
    
    <!--TODO: how to check the ORDER of slots, if needed?-->
    
    <!--TODO: how to check for UNWANTED elements in the XML?-->
    
    
    
    <!--*************************************-->
    <!--CHECK REQUEST DATA CONSUMER STRUCTURE-->
    <!--*************************************-->
    <pattern>
        <rule context="rim:Slot[@name = 'DataConsumer']">
            
            <let name="countAgent" value="count(./rim:SlotValue/cagv:Agent)"/>      
            <assert test="($countAgent=1)" flag='ERROR' id='req_dc_card_Agent'>
                The DataConsumer slot must contain exactly ONE Agent (found: <value-of select="$countAgent"/>).
            </assert>  
            
        </rule>
    </pattern>
            
    
    
    
    <!--*****************************-->
    <!--CHECK REQUEST AGENT STRUCTURE-->
    <!--*****************************-->
    <pattern>
        <rule context="rim:Slot[@name = 'DataConsumer']/rim:SlotValue/cagv:Agent">
            
            <!--TODO: check if we need to add a cardinality test for ALL elements: not hard to do, but probably very time-consuming to check-->
            
            <assert test="exists(cbc:id)" flag='ERROR' id='req_agent_mandatory_id'>
                The Agent must have an Id.
            </assert>  
            
            <assert test="exists(cbc:name)" flag='ERROR' id='req_agent_mandatory_name'>
                The Agent must have a name.
            </assert>  
            
            <let name="countLocation" value="count(cagv:location)"/>  
            <assert test="($countLocation=0) or ($countLocation=1)" flag='ERROR' id='req_agent_card_location'>
                The Agent must contain ZERO or ONE Location elements (found: <value-of select="$countLocation"/>).
            </assert>
            
            <let name="countAgentAddressFullAddress" value="count(cagv:location/locn:address/locn:fullAddress)"/>  
            <assert test="($countAgentAddressFullAddress &lt; 4)" flag='ERROR' id='req_card_Agent_Address_FullAddress'>
                The Agent Address must contain UP TO THREE AddressFullAddress elements (found: <value-of select="$countAgentAddressFullAddress"/>).
            </assert>
            
            <let name="countAgentAddressThoroughfare" value="count(cagv:location/locn:address/locn:thoroughfare)"/>  
            <assert test="($countAgentAddressThoroughfare=0) or ($countAgentAddressThoroughfare=1)" flag='ERROR' id='req_card_Agent_Address_AddressThoroughfare'>
                The Agent Address must contain ZERO or ONE AddressThoroughfare elements (found: <value-of select="$countAgentAddressThoroughfare"/>).
            </assert>
            
            <let name="countAgentAddressLocatorDesignator" value="count(cagv:location/locn:address/locn:thoroughfare)"/>  
            <assert test="($countAgentAddressLocatorDesignator=0) or ($countAgentAddressLocatorDesignator=1)" flag='ERROR' id='req_card_Agent_Address_AddressLocatorDesignator'>
                The Agent Address must contain ZERO or ONE AddressLocatorDesignator elements (found: <value-of select="$countAgentAddressLocatorDesignator"/>).
            </assert>
            
            <let name="countAgentAddressPostName" value="count(cagv:location/locn:address/locn:thoroughfare)"/>  
            <assert test="($countAgentAddressPostName=0) or ($countAgentAddressPostName=1)" flag='ERROR' id='req_card_Agent_Address_AddressPostName'>
                The Agent Address must contain ZERO or ONE AddressPostName elements (found: <value-of select="$countAgentAddressPostName"/>).
            </assert>
            
            <let name="countAgentAddressAdminUnitLocationOne" value="count(cagv:location/locn:address/locn:thoroughfare)"/>  
            <assert test="($countAgentAddressAdminUnitLocationOne=0) or ($countAgentAddressAdminUnitLocationOne=1)" flag='ERROR' id='req_card_Agent_Address_AddressAdminUnitLocationOne'>
                The Agent Address must contain ZERO or ONE AddressAdminUnitLocationOne elements (found: <value-of select="$countAgentAddressAdminUnitLocationOne"/>).
            </assert>
            
            <let name="countAgentAddressPostCode" value="count(cagv:location/locn:address/locn:thoroughfare)"/>  
            <assert test="($countAgentAddressPostCode=0) or ($countAgentAddressPostCode=1)" flag='ERROR' id='req_card_Agent_Address_AddressPostCode'>
                The Agent Address must contain ZERO or ONE AddressPostCode elements (found: <value-of select="$countAgentAddressPostCode"/>).
            </assert>
            
        </rule>
    </pattern>


    <!--*****************************-->
    <!--CHECK REQUEST QUERY STRUCTURE-->
    <!--*****************************-->
    <pattern>
        <rule context="query:Query">
            
            <report test="( (exists(rim:Slot[@name = 'ConceptRequestList'])) and (exists(rim:Slot[@name = 'DistributionRequestList'])) )"  flag='ERROR' id="mandatory_query_concept_or_distribution_request_list">
                The Query must contain either a ConceptRequestList or a DistributionRequestList.
            </report>
            
            <report test="( (exists(rim:Slot[@name = 'LegalPerson'])) and (exists(rim:Slot[@name = 'NaturalPerson'])) )"  flag='ERROR' id="mandatory_query_legal_or_natural_person">
                The Query must contain either a LegalPerson or a NaturalPerson.
            </report>
            
            <let name="countConceptRequestList" value="count(rim:Slot[@name = 'ConceptRequestList'])"/>      
            <assert test="($countConceptRequestList=0) or ($countConceptRequestList=1)" flag='ERROR' id='req_card_Query_ConceptRequestList'>
                The Query must contain ZERO or ONE ConceptRequestList elements (found: <value-of select="$countConceptRequestList"/>).
            </assert>  
            
            <let name="countDistributionRequestList" value="count(rim:Slot[@name = 'DistributionRequestList'])"/>      
            <assert test="($countDistributionRequestList=0) or ($countDistributionRequestList=1)" flag='ERROR' id='req_card_Query_DistributionRequestList'>
                The Query must contain ZERO or ONE DistributionRequestList elements (found: <value-of select="$countDistributionRequestList"/>).
            </assert>  
            
            <let name="countLegalPerson" value="count(rim:Slot[@name = 'LegalPerson'])"/>      
            <assert test="($countLegalPerson=0) or ($countLegalPerson=1)" flag='ERROR' id='req_card_Query_LegalPerson'>
                The Query must contain ZERO or ONE LegalPerson elements (found: <value-of select="$countLegalPerson"/>).
            </assert>  
            
            <let name="countNaturalPerson" value="count(rim:Slot[@name = 'NaturalPerson'])"/>      
            <assert test="($countNaturalPerson=0) or ($countNaturalPerson=1)" flag='ERROR' id='req_card_Query_NaturalPerson'>
                The Query must contain ZERO or ONE NaturalPerson elements (found: <value-of select="$countNaturalPerson"/>).
            </assert>  
            
            <let name="countAuthorizedRepresentative" value="count(rim:Slot[@name = 'AuthorizedRepresentative'])"/>      
            <assert test="($countAuthorizedRepresentative=0) or ($countAuthorizedRepresentative=1)" flag='ERROR' id='req_card_Query_AuthorizedRepresentative'>
                The Query must contain ZERO or ONE AuthorizedRepresentative elements (found: <value-of select="$countAuthorizedRepresentative"/>).
            </assert>  
            
        </rule>
    </pattern>
    
    
    
    <!--************************************-->
    <!--CHECK CONCEPT REQUEST LIST STRUCTURE-->
    <!--************************************-->
    <pattern>
        <rule context="query:Query/rim:Slot[@name = 'ConceptRequestList']">
            
            <let name="countElement" value="count(rim:SlotValue/rim:Element)"/>      
            <assert test="($countElement &gt; 0)" flag='ERROR' id='req_crlist_element'>
                The ConceptRequestList slot must contain at least ONE Element (found: <value-of select="$countElement"/>).
            </assert>  
            
            <let name="countElementConcept" value="count(rim:SlotValue/rim:Element/cccev:Concept)"/>      
            <assert test="($countElementConcept = 1)" flag='ERROR' id='req_crlist_element_concept'>
                Each ConceptRequestList/Element must contain exactly ONE Concept (found: <value-of select="$countElementConcept"/>).
            </assert>   
            
        </rule>
    </pattern>
    
    
    
    <!--*****************************************-->
    <!--CHECK DISTRIBUTION REQUEST LIST STRUCTURE-->
    <!--*****************************************-->
    <pattern>
        <rule context="query:Query/rim:Slot[@name = 'DistributionRequestList']">
            
            <let name="countElement" value="count(rim:SlotValue/rim:Element)"/>      
            <assert test="($countElement &gt; 0)" flag='ERROR' id='req_distlist_element'>
                The DistributionRequestList slot must contain at least ONE Element (found: <value-of select="$countElement"/>).
            </assert>  
            
            <let name="countElementDistribution" value="count(rim:SlotValue/rim:Element/dcat:Distribution)"/>      
            <assert test="($countElementDistribution = 1)" flag='ERROR' id='req_crlist_element_distribution'>
                Each DistributionRequestList/Element must contain exactly ONE Distribution (found: <value-of select="$countElementDistribution"/>).
            </assert>  
            
        </rule>
    </pattern>


    
    <!--***************************-->
    <!--CHECK CORE PERSON STRUCTURE-->
    <!--***************************-->
    <pattern>
        <rule context="rim:Slot/rim:SlotValue/cva:CorePerson">

            <let name="countPersonId" value="count(cvb:PersonID)"/>      
            <assert test="($countPersonId &gt; 0)" flag='ERROR' id='req_card_CorePerson_PersonId'>
                The CorePerson must have at least ONE PersonId (found: <value-of select="$countPersonId"/>).
            </assert>  
            
            <let name="countPersonFamilyName" value="count(cvb:PersonFamilyName)"/>      
            <assert test="($countPersonFamilyName=1)" flag='ERROR' id='req_card_CorePerson_PersonFamilyName'>
                The CorePerson must have ONE PersonFamilyName (found: <value-of select="$countPersonFamilyName"/>).
            </assert>  
            
            <let name="countPersonGivenName" value="count(cvb:PersonGivenName)"/>      
            <assert test="($countPersonGivenName=1)" flag='ERROR' id='req_card_CorePerson_PersonGivenName'>
                The CorePerson must have ONE PersonGivenName (found: <value-of select="$countPersonGivenName"/>).
            </assert>  
            
            <let name="countPersonGenderCode" value="count(cvb:PersonGenderCode)"/>      
            <assert test="($countPersonGenderCode=0) or ($countPersonGenderCode=1)" flag='ERROR' id='req_card_Person_PersonGenderCode'>
                The CorePerson must have ZERO or ONE PersonGenderCode elements (found: <value-of select="$countPersonGenderCode"/>).
            </assert>  
            
            <let name="countPersonBirthName" value="count(cvb:PersonBirthName)"/>      
            <assert test="($countPersonBirthName=0) or ($countPersonBirthName=1)" flag='ERROR' id='req_card_Person_PersonBirthName'>
                The CorePerson must have ZERO or ONE PersonBirthName elements (found: <value-of select="$countPersonBirthName"/>).
            </assert>  
            
            <let name="countPersonBirthDate" value="count(cvb:PersonBirthDate)"/>      
            <assert test="($countPersonBirthDate=1)" flag='ERROR' id='req_card_CorePerson_PersonBirthDate'>
                The CorePerson must have ONE PersonBirthDate (found: <value-of select="$countPersonBirthDate"/>).
            </assert> 
            
            <let name="countPersonPersonPlaceOfBirthCoreLocation" value="count(cvb:AddressPostName)"/>      
            <assert test="($countPersonPersonPlaceOfBirthCoreLocation=0) or ($countPersonPersonPlaceOfBirthCoreLocation=1)" flag='ERROR' id='req_card_CorePerson_PlaceOfBirthCoreLocation'>
                The CorePerson must have ZERO or ONE  PlaceOfBirthCoreLocation/AddressPostName elements (found: <value-of select="$countPersonPersonPlaceOfBirthCoreLocation"/>).
            </assert> 
            
        </rule>
    </pattern>
    
    
    <!--***********************-->
    <!--CHECK CONCEPT STRUCTURE-->
    <!--***********************-->
    <pattern>
        <rule context="query:Query/rim:Slot[@name = 'ConceptRequestList']/cccev:Concept">
            
            <let name="countConceptId" value="count(cbc:id)"/>      
            <assert test="($countConceptId=1)" flag='ERROR' id='req_card_concept_id'>
                Each Concept must have ONE ConceptId (found: <value-of select="$countConceptId"/>).
            </assert>  
            
            <!--TODO CHECK LegalEntityID-->
            
            <let name="countConceptQName" value="count(cbc:QName)"/>      
            <assert test="($countConceptQName=1)" flag='ERROR' id='req_card_concept_qname'>
                Each Concept must have ONE QName (found: <value-of select="$countConceptQName"/>).
            </assert>   
            
            <let name="countconcepts" value="count(cccev:concept)"/>      
            <assert test="($countconcepts=0) or ($countconcepts=1)" flag='ERROR' id='req_card_Concepts_concepts'>
                The Concept must contain ZERO or ONE concepts elements (found: <value-of select="$countconcepts"/>).
            </assert>  
            
        </rule>
    </pattern>
    
    
    <!--******************************-->
    <!--CHECK NESTED concept STRUCTURE-->
    <!--******************************-->
    <pattern>
        <rule context="query:Query/rim:Slot[@name = 'ConceptRequestList']//cccev:concept">
            
            <let name="countConceptId" value="count(cbc:id)"/>      
            <assert test="($countConceptId=1)" flag='ERROR' id='req_card_nested_concept_id'>
                Each concept must have ONE ConceptId (found: <value-of select="$countConceptId"/>).
            </assert>  
            
            <!--TODO CHECK LegalEntityID-->
            
            <let name="countConceptQName" value="count(cbc:QName)"/>      
            <assert test="($countConceptQName=1)" flag='ERROR' id='req_card_nested_concept_qname'>
                Each concept must have ONE QName (found: <value-of select="$countConceptQName"/>).
            </assert>        
            
        </rule>
    </pattern>
    
    
    <!--****************************-->
    <!--CHECK DISTRIBUTION STRUCTURE-->
    <!--****************************-->
    <pattern>
        <rule context="query:Query/rim:Slot[@name = 'DistributionRequestList']/rim:SlotValue/rim:Element/dcat:Distribution">
            
            <let name="countDistAccessURL" value="count(dcat:accessURL)"/>      
            <assert test="($countDistAccessURL=1)" flag='ERROR' id='req_card_dist_AccessURL'>
                Each Distribution must have ONE accessURL (found: <value-of select="$countDistAccessURL"/>).
            </assert>  
            
            <let name="countDistMediaType" value="count(dcat:mediaType)"/>      
            <assert test="($countDistMediaType=0) or ($countDistMediaType=1)" flag='ERROR' id='req_card_dist_MediaType'>
                Each Distribution must have ZERO or ONE mediaType elements (found: <value-of select="$countDistMediaType"/>).
            </assert>  
            
            <let name="countDistFormat" value="count(dct:format)"/>      
            <assert test="($countDistFormat=0) or ($countDistFormat=1)" flag='ERROR' id='req_card_dist_Format'>
                Each Distribution must have ZERO or ONE format elements (found: <value-of select="$countDistFormat"/>).
            </assert>  
            
        </rule>
    </pattern>
    
    
    <!--*****************************-->
    <!--CHECK CORE BUSINESS STRUCTURE-->
    <!--*****************************-->
    <pattern>
        <rule context="rim:Slot/rim:SlotValue/cva:CoreBusiness">
            
            <let name="countLegalEntityLegalID" value="count(cvb:LegalEntityLegalID)"/>      
            <assert test="($countLegalEntityLegalID=1)" flag='ERROR' id='req_card_CoreBusiness_LegalEntityLegalID'>
                The CoreBusiness must have ONE LegalEntityLegalID (found: <value-of select="$countLegalEntityLegalID"/>).
            </assert>  
            
            <!--TODO CHECK LegalEntityID-->
            
            <let name="countLegalEntityLegalName" value="count(cvb:LegalEntityLegalName)"/>      
            <assert test="($countLegalEntityLegalName=1)" flag='ERROR' id='req_card_CoreBusiness_LegalEntityLegalName'>
                The CorePerson must have ONE LegalEntityLegalName (found: <value-of select="$countLegalEntityLegalName"/>).
            </assert>          
            
        </rule>
    </pattern>
    
    
    
    <!--***********************-->
    <!--CHECK ADDRESS STRUCTURE-->
    <!--***********************-->
    <pattern>
        <!--TODO: avoid double slash in the Context-->
        <rule context="//cva:PersonCoreAddress | //cva:LegalEntityCoreAddress">
            
            <let name="countAddressFullAddress" value="count(cvb:AddressFullAddress)"/>  
            <assert test="($countAddressFullAddress &lt; 4)" flag='ERROR' id='req_card_Address_FullAddress'>
                The Address must contain UP TO THREE AddressFullAddress elements (found: <value-of select="$countAddressFullAddress"/>).
            </assert>
            
            <let name="countAddressThoroughfare" value="count(cvb:AddressThoroughfare)"/>  
            <assert test="($countAddressThoroughfare=0) or ($countAddressThoroughfare=1)" flag='ERROR' id='req_card_Address_AddressThoroughfare'>
                The Address must contain ZERO or ONE AddressThoroughfare elements (found: <value-of select="$countAddressThoroughfare"/>).
            </assert>
            
            <let name="countAddressLocatorDesignator" value="count(cvb:AddressLocatorDesignator)"/>  
            <assert test="($countAddressLocatorDesignator=0) or ($countAddressLocatorDesignator=1)" flag='ERROR' id='req_card_Address_AddressLocatorDesignator'>
                The Address must contain ZERO or ONE AddressLocatorDesignator elements (found: <value-of select="$countAddressLocatorDesignator"/>).
            </assert>
            
            <let name="countAddressPostName" value="count(cvb:AddressPostName)"/>  
            <assert test="($countAddressPostName=0) or ($countAddressPostName=1)" flag='ERROR' id='req_card_Address_AddressPostName'>
                The Address must contain ZERO or ONE AddressPostName elements (found: <value-of select="$countAddressPostName"/>).
            </assert>
            
            <let name="countAddressAdminUnitLocationOne" value="count(cvb:AddressAdminUnitLocationOne)"/>  
            <assert test="($countAddressAdminUnitLocationOne=0) or ($countAddressAdminUnitLocationOne=1)" flag='ERROR' id='req_card_Address_AddressAdminUnitLocationOne'>
                The Address must contain ZERO or ONE AddressAdminUnitLocationOne elements (found: <value-of select="$countAddressAdminUnitLocationOne"/>).
            </assert>
            
            <let name="countAddressPostCode" value="count(cvb:AddressPostCode)"/>  
            <assert test="($countAddressPostCode=0) or ($countAddressPostCode=1)" flag='ERROR' id='req_card_Address_AddressPostCode'>
                The Address must contain ZERO or ONE AddressPostCode elements (found: <value-of select="$countAddressPostCode"/>).
            </assert>
            
        </rule>
    </pattern>

   
</schema>
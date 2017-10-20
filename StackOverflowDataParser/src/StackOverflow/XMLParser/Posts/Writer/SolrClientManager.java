package StackOverflow.XMLParser.Posts.Writer;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.RequestWriter;

public class SolrClientManager {
    private HttpSolrClient solrClient;
    
    public SolrClientManager(String clientUrl) {

        solrClient = new HttpSolrClient.Builder(clientUrl).build();
        solrClient.setParser(new XMLResponseParser());
        solrClient.setRequestWriter(new RequestWriter());
    }
    
    public void SolrServerCleanup() throws SolrServerException, IOException {
    	solrClient.deleteByQuery("*.*");
    	solrClient.commit();
    }
    
    public void addQABean(QABean pBean) throws IOException, SolrServerException {

        solrClient.addBean(pBean);
        solrClient.commit();
    }
    
    private void populateIndexFromXmlFile(String fileName) throws Exception {

	    UpdateRequest update = new UpdateRequest();

	    update.add(getSolrInputDocumentListFromXmlFile(fileName));

	    update.process(server);

	    server.commit();
	}

	private List<SolrInputDocument> getSolrInputDocumentListFromXmlFile(String fileName) throws Exception {

	    ArrayList<SolrInputDocument> solrDocList = new ArrayList<SolrInputDocument>();

	    File fXmlFile = new File(fileName);

	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);

	    NodeList docList = doc.getElementsByTagName("doc");

	    for (int docIdx = 0; docIdx < docList.getLength(); docIdx++) {

	        Node docNode = docList.item(docIdx);

	        if (docNode.getNodeType() == Node.ELEMENT_NODE) {

	            SolrInputDocument solrInputDoc = new SolrInputDocument();

	            Element docElement = (Element) docNode;

	            NodeList fieldsList = docElement.getChildNodes();

	            for (int fieldIdx = 0; fieldIdx < fieldsList.getLength(); fieldIdx++) {

	                Node fieldNode = fieldsList.item(fieldIdx);

	                if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {

	                    Element fieldElement = (Element) fieldNode;

	                    String fieldName = fieldElement.getAttribute("name");
	                    String fieldValue = fieldElement.getTextContent();

	                    solrInputDoc.addField(fieldName, fieldValue);
	                }

	            }

	            solrDocList.add(solrInputDoc);
	        }
	    }

	    return solrDocList;

	}
    
    public void deleteSolrDocumentById(String documentId) throws SolrServerException, IOException {

        solrClient.deleteById(documentId);
        solrClient.commit();
    }
    
    public void deleteSolrDocumentByQuery(String query) throws SolrServerException, IOException {

        solrClient.deleteByQuery(query);
        solrClient.commit();
    }

    protected HttpSolrClient getSolrClient() {
        return solrClient;
    }

    protected void setSolrClient(HttpSolrClient solrClient) {
        this.solrClient = solrClient;
    }    
}

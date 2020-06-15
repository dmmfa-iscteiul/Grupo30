package G30_82664.Covid_Query;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class CovidQuery {

	private static final String URL = "https://github.com/vbasto-iscte/ESII1920.git";
	private static final String FILENAME = "covid19spreading.rdf";
	private static final String LOCALPATH = "fileES";

	private Document doc;
	
	/**
	 * Creates an instance of CovidQuery.
	 */
	public CovidQuery() {
		
	}

	/**
	 * Get the most recent version (last commit) of covid19spreading.rdf file available in GitHub repository.
	 */
	public void getFile() {
		File f = new File(LOCALPATH);
		try {
			System.out.println("Cloning from " + URL + " to " + f);
			Git git = Git.cloneRepository().setURI(URL).setDirectory(f).call();

			Repository repository = git.getRepository();
			ObjectId lastCommitId = repository.resolve(Constants.HEAD);
			String content = null;

			RevWalk revWalk = new RevWalk(repository);
			RevCommit commit = revWalk.parseCommit(lastCommitId);
			RevTree tree = commit.getTree();
			TreeWalk treeWalk = new TreeWalk(repository);
			treeWalk.addTree(tree);
			treeWalk.setRecursive(true);
			treeWalk.setFilter(PathFilter.create(FILENAME));
			if (!treeWalk.next())
				throw new IllegalStateException("Did not find expected file:" + FILENAME);
			

			ObjectId objectId = treeWalk.getObjectId(0);
			ObjectLoader loader = repository.open(objectId);
			content = new String(loader.getBytes());
			System.out.println("FILE UPDATED");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			this.doc = dBuilder.parse(new ByteArrayInputStream(content.getBytes("UTF-8")));
			revWalk.dispose();
			git.close();
			deleteFolder(f);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete the given folder.
	 * @param folder the folder to delete.
	 */
	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if(files!=null) {
			for(File f: files) {
				if(f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	/**
	 * Perform a XML query (XPath query) on the covid19spreading.rdf and get the String of total number of the type given in the given region.
	 * @param region the region (e.g. Algarve).
	 * @param type the type (e.g. Testes).
	 * @return the String of total number of the type given in the given region.
	 */
	public String getNumberOf(String region, String type) {
		this.getFile();
		String result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		javax.xml.xpath.XPath xpath = xpathFactory.newXPath();       
		String query = "//*[contains(@about, '"+ region +"')]/" + type + "/text()";  
		XPathExpression expr;
		try {
			expr = xpath.compile(query);
			result = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		System.out.println("Query: " + query + " -> Result: " + result);
		return result;
	}

	/**
	 * Perform a XML query (XPath query) on the covid19spreading.rdf and get the list of Strings of existing regions.
	 * @return the list of String of existing regions.
	 */
	public List<String> getRegions() {   
		this.getFile();
		List<String> result = new ArrayList<String>();
		String query = "/RDF/NamedIndividual/@*";
		XPathFactory xpathFactory = XPathFactory.newInstance();
		javax.xml.xpath.XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = null;
		NodeList nl = null;
		try {
			expr = xpath.compile(query);
			nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nl.getLength(); i++) {
			result.add(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
		}
		System.out.println("Query: " + query + " -> Result: " + result);
		return result;
	}

	/**
	 * Perform a XML query (XPath query) on the covid19spreading.rdf and get the disjunction of the total number of the two types given.
	 * @param t1 the first type (e.g. Testes).
	 * @param t2 the second type (e.g. Internamentos).
	 * @return the total number of the disjunction of the two types given.
	 */
	public String getTotalOr(String t1, String t2) {
		this.getFile();
		int result = 0;
		String query = "//" + t1 + "/text() | //" + t2 + "/text()";
		XPathFactory xpathFactory = XPathFactory.newInstance();
		javax.xml.xpath.XPath xpath = xpathFactory.newXPath();
		XPathExpression expr;
		NodeList nl = null;
		try {
			expr = xpath.compile(query);     
			nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nl.getLength(); i++) {
			result = result + Integer.parseInt(nl.item(i).getNodeValue());
		}
		System.out.println("Query: " + query + " -> Result: " + result);
		return result + "";
	}

	/**
	 * Perform a XML query (XPath query) on the covid19spreading.rdf and get the list of all regions where condition formed by the type, relational operator and value is verified (e.g. Testes greater than 20).
	 * @param type the type (e.g. Testes).
	 * @param operator the relational operator (greater than, less than).
	 * @param value the number of type given.
	 * @return the list of all regions where condition formed by the type, operator and value is verified.
	 */
	public List<String> getRegionsWhere(String type, String operator, String value) {
		this.getFile();
		List<String> result = new ArrayList<String>();
		String query = "//" + type + "[text()" + operator + value +"]/../@*";
		XPathFactory xpathFactory = XPathFactory.newInstance();
		javax.xml.xpath.XPath xpath = xpathFactory.newXPath();
		XPathExpression expr;
		NodeList nl = null;
		try {
			expr = xpath.compile(query);
			nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nl.getLength(); i++) {
			result.add(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
		}
		System.out.println("Query: " + query + " -> Result: " + result);
		return result;
	}
}

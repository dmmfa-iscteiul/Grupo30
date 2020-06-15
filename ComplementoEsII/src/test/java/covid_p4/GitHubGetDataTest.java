package covid_p4;


import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitHubGetDataTest {
	GitHubGetData gData;
	String REMOTE_URL="https://github.com/vbasto-iscte/ESII1920.git";
	File PATH = new File("C:\\Users\\Utilizador\\eclipse-workspace_3\\ComplementoEsII\\data");
	String REMOTE_URL_GRAPH="http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/master/covid19spreading.rdf ";
	String wordToChangeLink = ("master");
	String fileName = ("covid19spreading.rdf");

	@Before
	public void setUp() throws Exception {
		gData = new GitHubGetData(REMOTE_URL,PATH,REMOTE_URL_GRAPH, wordToChangeLink, fileName);
		gData.clearFiles(PATH);
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testGitHubGetData() throws InvalidRemoteException, TransportException, IOException, GitAPIException {
		gData = new GitHubGetData(REMOTE_URL,PATH,REMOTE_URL_GRAPH, wordToChangeLink, fileName);
		
	}

	@Test
	public void testClearFiles() {
		gData.clearFiles(PATH);
	}

	@Test
	public void testGetTags() throws Exception {
		gData.cloneRepository();
		gData.getTags();
	}

	@Test
	public void testGetTagsDescription() throws Exception {
		gData.cloneRepository();
		gData.getTags();
		gData.getTagsDescription();
	}

	@Test
	public void testGetHyperLink() throws Exception {
		gData.cloneRepository();
		gData.getTags();
		gData.getTagsDescription();
		gData.getHyperLink();

	}

	@Test
	public void testGetArraysInformation() {
		gData.getArraysInformation();
	}

}

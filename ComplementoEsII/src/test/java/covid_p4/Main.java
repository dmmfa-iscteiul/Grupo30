package covid_p4;

import java.io.File;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {

		String REMOTE_URL="https://github.com/vbasto-iscte/ESII1920.git";
		File PATH = new File("C:\\Users\\Utilizador\\eclipse-workspace_3\\ComplementoEsII\\data");
		File PATH_HTML = new File("C:\\Users\\Utilizador\\eclipse-workspace_3\\ComplementoEsII\\covid_p4.html");
		String REMOTE_URL_GRAPH="http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/master/covid19spreading.rdf ";
		String wordToChangeLink = ("master");
		String fileName = ("covid19spreading.rdf");

		ArrayList<String> headers = new ArrayList<String>();
		headers.add("File Timestamp");
		headers.add("File Name");
		headers.add("File Tag");
		headers.add("Description");
		headers.add("Spread Visualization Link");

		GitHubGetData gitHubGetData = new GitHubGetData(REMOTE_URL,PATH,REMOTE_URL_GRAPH, wordToChangeLink, fileName);
		HtmlCovidTable htmlcovid = new HtmlCovidTable(5, PATH_HTML);


		gitHubGetData.cloneRepository();
		gitHubGetData.getTags();
		gitHubGetData.getTagsDescription();
		gitHubGetData.getHyperLink();

		htmlcovid.createFile(headers, gitHubGetData.getArraysInformation());

	}
}

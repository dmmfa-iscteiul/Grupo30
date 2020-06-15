package covid_p4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;

public class GitHubGetData {

	private String REMOTE_URL;
	private File PATH;
	private String REMOTE_URL_GRAPH;
	private String WORD_TO_CHANGE_LINK;
	private String FILE_NAME;
	private int numbertags;

	private ArrayList<String> tags;
	private ArrayList<String> commitsMessage; //Tag Description
	private ArrayList<String> tagsName; //File Tag
	private ArrayList<String> hyperLinks; //Spread Visualization Link
	private ArrayList<String> timestamp; //File Timestamp
 
	private Git git;
	
/**
 * CONSTRUTOR DA CLASSE QUE ATRIBUI AOS SEUS ATRIBUTOS OS PARAMETROS E LIMPA O REPOSIT�RIO (PATH). 
 * @param remote_url - CAMINHO DO REPOSIT�RIO GIT ORIGIN�RIO DE TODA A INFORMA��O.
 * @param path - REPOSIT�RIO ONDE � CLONADA A INFORMA��O DO REPOSIT�RIO GIT.
 * @param remote_url_graph - CAMINHO DO LINK PARA EDI��O, PARA PARTILHAR A VISTA DO SPREADING DO COVID DE ACORDO COM OS DADOS DO FICHEIRO.
 * @param wordToChangeLink - PALAVRA CHAVE PARA MUDAR NO LINK (REMOTE_URL_GRAPH).
 * @param FileName - NOME DO FICHEIRO, AO QUAL SE QUER SABER AS VERS�ES (TAGS).
 */
	public GitHubGetData(String remote_url, File path, String remote_url_graph, String wordToChangeLink, String FileName) {
		this.REMOTE_URL=remote_url;
		this.PATH=path;
		this.REMOTE_URL_GRAPH=remote_url_graph;
		this.WORD_TO_CHANGE_LINK=wordToChangeLink;
		this.FILE_NAME=FileName;
		clearFiles(PATH);
		System.out.println("Cleaning...");
	}

	/**
	 * M�TODO QUE CLONA O REPOSIT�RIO PARA O WORKSPACE.
	 * @throws IOException 
	 * @throws InvalidRemoteException
	 * @throws TransportException
	 * @throws GitAPIException
	 */
	public void cloneRepository() throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		System.out.println("Cloning...");
		git= Git.cloneRepository()
				.setURI(REMOTE_URL)
				.setDirectory(PATH)
				.call();
	}

	/**
	 * M�TODO PARA LIMPAR O REPOSIT�RIO ONDE SE ENCONTRA O CLONE DO REPOSIT�RIO GIT.
	 * @param file - REPOSIT�RIO ONDE � ELIMINADA A INFORMA��O DO REPOSIT�RIO LOCAL.
	 */
	public void clearFiles(File file) {	
		if(file.exists()) {
			for (File subFile : file.listFiles()) {
				if(subFile.isDirectory()) {
					clearFiles(subFile);
				} else {
					subFile.delete();
				}
			}
			file.delete();
		}
	}

	/**
	 * M�TODO QUE GUARDA OS NOMES DAS TAGS EXISTENTES NO REPOSIT�RIO GIT.
	 * @throws Exception
	 */
	public void getTags() throws Exception {
		List<Ref> allTags = git.tagList().call();

		tags = new ArrayList<String>();
		tagsName = new ArrayList<String>();

		allTags.subList(0, allTags.size()).forEach(tag -> {

			int n= tag.toString().indexOf('=');
			int i=n+1;
			int f= tag.toString().indexOf('(') ;

			tags.add(tag.toString().substring(i,f));
			tagsName.add(tag.getName().substring(10));
			
		});
		numbertags=tags.size();
	}

	/**
	 * M�TODO QUE GUARDA A DESCRI��O ASSOCIADA A CADA TAG.
	 * @throws NoHeadException
	 * @throws GitAPIException
	 */
	public void getTagsDescription () throws NoHeadException, GitAPIException {
		commitsMessage = new ArrayList<String>();
		timestamp = new ArrayList<String>();

		/**commits associados �s tags**/
		git.log().call().forEach(commit -> {
			if (tags.contains(commit.name().toString())) {
				commitsMessage.add(commit.getFullMessage());
				System.out.println("MENSSAGE:"+ commit.getFullMessage());
				timestamp.add(commit.getAuthorIdent().getWhen().toString());
				System.out.println(commit.getAuthorIdent().getWhen());
			}
		});	
	} 

	/**
	 * M�TODO QUE REALIZA A MUDAN�A DA PALAVRA CHAVE PARA AS TAGSNAMES DE MODO A TONAR O LINK UTILIZAV�L.
	 */
	public void getHyperLink () {
		hyperLinks = new ArrayList<String>();
		for(String tag :  tagsName) {
			String remote_url_graph = REMOTE_URL_GRAPH.replace(WORD_TO_CHANGE_LINK, tag);
			hyperLinks.add(remote_url_graph);
			System.out.println(remote_url_graph);
		}	
	}
	
	/**
	 * M�TODO QUE RETORNA UM ARRAY DE ARRAYS EM QUE CADA UMA CORRESPONDE A UMA LINHA DA TABELA.
	 * @return 
	 */
	public ArrayList<ArrayList<String>> getArraysInformation() {
		ArrayList<ArrayList<String>> informationsToTable = new ArrayList<>();
		for(int i=0; i < numbertags; i++) {
			ArrayList<String> information= new ArrayList<>();
			information.add(timestamp.get(i));
			information.add(FILE_NAME);
			information.add(tagsName.get(i));
			information.add(commitsMessage.get(i));
			information.add(hyperLinks.get(i));
			informationsToTable.add(information);
		}
		
		return informationsToTable;
	}
}

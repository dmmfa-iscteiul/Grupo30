package g30_82503.comp6;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

public class CovidEvolutionDiff {
	static String REMOTE_URL="https://github.com/vbasto-iscte/ESII1920.git";
	static File path;
	private static Git git;
	
	private static ArrayList<Ref>latestTwoTagsObj;
	private static List<byte[]> lastTwoFiles;
	
	private static ArrayList<String> latestTwoTags;
	private static ArrayList<RevCommit>latestTwoFiles;

	private static List<String> oldFile;
	private static List<String> newFile;

	private static List<String> diffNewFile;
	private static List<String> diffOldFile;
	public 	Repository repository;
	
	public Repository getRepository() {
		return repository;
	}

	/**
	 * Clones the wanted repository to the local environment  given by the path variable
	 *
	 * */
	private void cloneRepository() throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		// then clone
	System.out.println("Cloning from " + REMOTE_URL + " to " + path.toString());				
		git = Git.cloneRepository()
				.setURI(REMOTE_URL).setDirectory( path ).call();
	
		System.out.println("Having repository: " + git.getRepository().getDirectory());
	}
	
	/**
	 * Searches for the last two tags on the repository and saves both name and the tag itself
	 * on a lists
	 * 
	 * */
	private static void getLastestTwoTags() throws Exception {

		/**Vai buscar a lista das tags existentes no repositorio**/
		List<Ref> allTags = git.tagList().call();

		latestTwoTags = new ArrayList<String>();
		latestTwoTagsObj= new ArrayList<Ref>();

		
		allTags.subList(allTags.size() - 2, allTags.size()).forEach(tag -> {

			int n= tag.toString().indexOf('=');
			int i=n+1;
			int f= tag.toString().indexOf('(') ;

			latestTwoTags.add(tag.toString().substring(i,f));
			latestTwoTagsObj.add(tag);
		});
	}

	/**
	 * Obtains the two version files associated with the tags searched before.
	 * Save each version in a list of strings. 
	 *
	 * @param file name of the wanted file   
	 */
	private static void saveLastestTwoFileVersions(String file) throws Exception {
		lastTwoFiles = new ArrayList<byte[]>();
		latestTwoFiles = new ArrayList<RevCommit>() ;
		
		git.log().addPath(file).call().forEach(commit -> {
			
			if (latestTwoTags.contains(commit.name().toString())) {
				try (TreeWalk treeWalk = TreeWalk.forPath(git.getRepository(), file, commit.getTree())) {
					ObjectId blobId = treeWalk.getObjectId(0);

					try (ObjectReader objectReader = git.getRepository().newObjectReader()) {
						ObjectLoader objectLoader = objectReader.open(blobId);
						lastTwoFiles.add(objectLoader.getBytes());
						latestTwoFiles.add(commit);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		newFile = Arrays.asList(new String(lastTwoFiles.get(0), StandardCharsets.UTF_8).split("\\r?\\n"));
		oldFile = Arrays.asList(new String(lastTwoFiles.get(1), StandardCharsets.UTF_8).split("\\r?\\n"));
	}
	
	/** Writes in a given file a given string content.
	 *  
	 * @param file Destination file that will be written
	 *  
	 * @param content Wanted content to be written 
	 * */
	
	public static void writeFile( File file, String content ) throws IOException {
		FileOutputStream outputStream = new FileOutputStream( file, true );
		outputStream.write( content.getBytes( "UTF-8" ) );
		outputStream.close();
		
	}
	/**Creates a file based on a list of strings
	 * 
	 * @param file Name of the file  
	 * 
	 * @param arrData List of the strings that will be writen on the file
	 * */
	public static void createFile(String file, List<String> arrData)
			throws IOException {
		FileWriter writer = new FileWriter(file + ".txt");
		int size = arrData.size();
		for (int i=0;i<size;i++) {
			String str = arrData.get(i).toString();
			writer.write(str);
			if(i < size-1)/**This prevent creating a blank like at the end of the file**/
				writer.write("\n");
		}
		writer.close();
	}
	/**
	 * Generates a File CovidSpreadDiff.html with the
	 * */
	private void generateHTML() throws IOException {
		
		HTMLBuilder html = new HTMLBuilder();
		html.htmlTableBuilder(null, false, 1, 2);	
		html.addTableHeader("Previous Version", "Update Version");
		String table =html.buildTable(newFile, oldFile);
		
		File file = new File("CovidSpreadDiff.html");
		FileUtils.write(file, "");
		writeFile(file, table);	
		
	
		Desktop.getDesktop().browse(file.toURI());
		
	}
	/**
	 *Closes the call connection made on the cloneRepository method
	 * and cleans the cloned repository files.
	 * */
	private void closeRepository() throws IOException {
		git.close();
		FileUtils.cleanDirectory(path);
		System.out.println(path.toString()+ " apagada com sucesso");
	}
	/**Defines the path value
	 *
	 * @param path  path to the directory where the remote repository 
	 * should be cloned to
	 * */
	private void setPath(String path ) {
		this.path= new File(path) ;		
	}
	
    public static void main( String[] args ) throws Exception
    {
    	CovidEvolutionDiff app = new CovidEvolutionDiff();
    	app.setPath("CovidDiff");
    	app.cloneRepository();
    	app.getLastestTwoTags();
		app.saveLastestTwoFileVersions("covid19spreading.rdf");
    	app.generateHTML();
    	app.closeRepository();	
    }
}

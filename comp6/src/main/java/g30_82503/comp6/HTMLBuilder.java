package g30_82503.comp6;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

/**
 * The HTMLBuilder class is used by the CovidEvolutionDiff class to  create HTML structured objects, 
 * allowing to create a table to display a previous and an updated version of the file covid19spreading.rdf
 * in which differences appear highlighted. 
 */

public class HTMLBuilder {

	private int columns;
	private final StringBuilder table = new StringBuilder();
	public static String HTML_START = "<html>";
	public static String HTML_END = "</html>";
	public static String TABLE_START_BORDER = "<table border=\"1\">";
	public static String TABLE_START = "<table>";
	public static String TABLE_END = "</table>";
	public static String HEADER_START = "<th>";
	public static String HEADER_END = "</th>";
	public static String ROW_START = "<tr>";
	public static String ROW_END = "</tr>";
	public static String COLUMN_START = "<td>";
	public static String COLUMN_END = "</td>";
	public static String HTML_HIGHLIGHT_START_N ="<span style=\"background-color: #ffc6a3;\">";
	public static String HTML_HIGHLIGHT_START_O ="<span style=\"background-color: #84cfc6;\">";
	public static String HTML_HIGHLIGHT_END ="</span>";
	public static String HTML_PARAGRAPH_START ="<p>";
	public static String HTML_PARAGRAPH_END ="</p>";
	
	private static List<String> newList;
	private static List<String> oldList;
	private static List<String> diffNewFile;
	private static List<String> diffOldFile;	



	/** builds a stringBuilder table, which content is the structure of a HTML table.
	 * 
	 * @param header Wanted header of the table, if value of the string is "null" then the header is not 
	 * 					defined.
	 * 
	 * @param border true if border is wanted in the table, false if not.
	 * 
	 * @param rows Wanted number of rows to the table. 
	 *
	 * @param columns Wanted number of columns to the table.
	 * */
	public void htmlTableBuilder(String header, boolean border, int rows, int columns) {
		this.columns = columns;
		if (header != null) {
			table.append("<b>");
			table.append(header);
			table.append("</b>");
		}
		table.append(HTML_START);
		table.append(border ? TABLE_START_BORDER : TABLE_START);
		table.append(TABLE_END);
		table.append(HTML_END);
	}

	/** Allows to define an header for every existing column.
	 * 
	 * @param values - one or more strings, that each one of them will correspond
	 * to the header of a column
	 *
	 * */
	public void addTableHeader(String... values) {
		if (values.length != columns) {
			System.out.println("Error column lenth");
		} else {
			int lastIndex = table.lastIndexOf(TABLE_END);
			if (lastIndex > 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(ROW_START);
				for (String value : values) {
					sb.append(HEADER_START);
					sb.append(value);
					sb.append(HEADER_END);
				}
				sb.append(ROW_END);
				table.insert(lastIndex, sb.toString());
			}
		}
	}

	/**Adds the content of a row, given the corresponding content of every cell from that row.
	 * every call of this method adds a row to the table sequentially.
	 * 
	 * @param values - one or more strings, every value correspond to a cell 
	 * */
	public void addRowValues(String... values) {
		if (values.length != columns) {
			System.out.println("Error column lenth");
		} else {
			int lastIndex = table.lastIndexOf(ROW_END);
			if (lastIndex > 0) {
				int index = lastIndex + ROW_END.length();
				StringBuilder sb = new StringBuilder();
				sb.append(ROW_START);
				for (String value : values) {
					sb.append(COLUMN_START);
					sb.append(value);
					sb.append(COLUMN_END);
				}
				sb.append(ROW_END);
				table.insert(index, sb.toString());
			}
		}
	}
	
	/** Process the raw content of a list of strings, deleting characters used 
	 * by html to delimit tags, such as '<' and '>'.
	 * 
	 * @param file list with the content wanted to be added to the html file, 
	 * but possibly has delimiting characters. 
	 * 
	 * @return list with the lines without the characters "<" and ">".
	 * */
	static List<String> preString(List<String> file) {
		List<String> list= new ArrayList<String>();

		for( String item: file ) { 
			String s = item.replaceAll("<", "\0");
			String s1 = s.replaceAll(">", "\0");
			list.add(s1);
		}
		return list;
	}
	/**Creates a string that will be HTML content, by adding the HTML paragraph tags
	 *  to every String from the list, and append all to a stringBuilder.
	 *
	 * @param file List with content from a file.
	 * 
	 * @return String with every paragraph from a file.
	 * 
	 */
	public String stringHtml(List<String> file) {
		  StringBuilder b = new StringBuilder();
		    for( String item: file ) { 
		    	b.append("<p>").append(item ).append("</p>" );
		    }
		    return b.toString();
		    
	}
	/** Highlights the two versions of the same string from the given lists,
	 * if they are different. It is assumed that the lists were created based
	 * on two similar versions of the same file.
	 * 
	 * @param ofile List 1 with file content to be compared.
	 * @param nfile List 2 with file content to be compared.
	 * 
	 */
	public static void diffOcurrences(List<String> ofile,List<String> nfile ) throws IOException {
		
		File fileO = new File("ofile.txt");
		File fileN = new File("nfile.txt");
		
		
		CovidEvolutionDiff.createFile("ofile", ofile);
		CovidEvolutionDiff.createFile("nfile", nfile);
		
		Scanner so=new Scanner(fileO);
		Scanner sn= new Scanner(fileN);
		
		diffNewFile= new ArrayList<String>();
		diffOldFile= new ArrayList<String>();
		
		while (so.hasNextLine() && sn.hasNextLine()) {
			String lineO = so.nextLine();
			String lineN = sn.nextLine();
			
			StringBuilder ob = new StringBuilder();
			StringBuilder nb = new StringBuilder();
			
			if (!lineO.equalsIgnoreCase(lineN)) {
				ob.append("<span style=\"background-color: #ffc6a3;\">").append(lineO).append("</span>");
				nb.append("<span style=\"background-color: #84cfc6;\">").append(lineN).append("</span>");
			}
			else {
				ob.append(lineO);
				nb.append(lineN);
			}
			diffNewFile.add(nb.toString());
			diffOldFile.add(ob.toString());
		}
		
		while (so.hasNextLine() && !sn.hasNextLine()) {
			String line1 = so.nextLine();
			System.out.println("file1: " + line1);
		}
		
		while (!so.hasNextLine() && sn.hasNextLine()) {
			String line2 = sn.nextLine();
			System.out.println("file2: " + line2);
		}
		FileUtils.write(fileO, "");
		FileUtils.write(fileN, "");
		fileO.delete();
		fileN.delete();
	}

	/** Given two lists with content of different versions from the same file,
	 * builds a HTML table that shows simultaneously the previous and updated
	 * version of a file, with the alterations highlighted.
	 * 
	 * @param newFile List with content from updated version of a file
	 * @param oldFile List with content from previous version of file
	 * 
	 * @return string with HTML structure text, ready to be added to .html file. 
	 */
	public String buildTable(List<String> newFile, List<String> oldFile) {
		try {
			newList= preString(newFile);
			oldList = preString(oldFile);
			
			diffOcurrences(newList, oldList);
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		String so =stringHtml(diffOldFile);
		String sn =stringHtml(diffNewFile);


		addRowValues(so,sn);
		return table.toString();
	}
	
}

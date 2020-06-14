import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author Diogo
 * 
 * Esta classe tem como objetivo auxiliar na construção do conteúdo do ficheiro html
 *
 */
public class HtmlBuilder {

 
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

	
	/**
	 * @param title titulo da tabela
	 * @param border rebordo da tabela
	 * @param rows linhas da tabela
	 * @param columns colunas da tabela
	 * 
	 * Construtor da página html
	 */
	
	public HtmlBuilder(String title, boolean border, int rows, int columns) {
		this.columns = columns;
		if (title != null) {
			table.append("<b>");
			table.append(title);
			table.append("</b>");
		}
		table.append(HTML_START);
		table.append(border ? TABLE_START_BORDER : TABLE_START);
		table.append(TABLE_END);
		table.append(HTML_END);
	}

	
	/**
	 * @param values Recebe um conjunto de strings que correspondem aos nomes de cada coluna
	 * 
	 * Serve para criar o cabeçalho da tabela que irá ser gerada
	 */
	
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

	
	/**
	 * @param values  Recebe um conjunto de strings que corresponde ao conteúdo de cada célula de uma linha
	 * 
	 * Adicionar os valores das células de cada fila
	 */

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

	/**
	 * @return string representa numa string todo o conteudo do ficheiro html
	 * 
	 * Transformar a StringBuilder numa string
	 * 
	 */
	
	public String build() {
		return table.toString();
	}
	
	
	/**
	 * @param file ficheiro html
	 * @param text conteudo do ficheiro html
	 * 
	 * @throws IOException
	 * 
	 * Escrever a string dentro de um ficheiro
	 */
	public void writeToFile(File file, String text) throws IOException {

		PrintWriter writer = new PrintWriter(file);
		writer.write(text);
		writer.close();
	}



}
package monitoring_tool;

public class TableBuilder {
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
	 * O htmlTableBuilder classe é usada para construir a estrutura de uma tabela html com titulo, margem, linhas e colunas
	 * @param header cabeçalho da tabela
	 * @param border margem da tabela	
	 * @param rows linhas da tabela
	 * @param clumns colunas da tabela
	 */

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
	
	/**
	 * O addTableHeader classe é usada para definir um titulo para a tabela
	 * @param values onde cada um corresponde a um cabeçalho/titulo de cada coluna
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
	 * O addRowValues classe é usada para definir o valor de cada células desta linha da tabela
	 * @param values onde cada um corresponde ao valor de cada celula da tabela
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
	 * O buildTable classe é usada para definir a tabela
	 */
	public String buildTable() {
		return table.toString();
	}
}

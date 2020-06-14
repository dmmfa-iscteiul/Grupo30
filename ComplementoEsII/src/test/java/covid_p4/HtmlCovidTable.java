package covid_p4;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HtmlCovidTable {

	private final StringBuilder html_table = new StringBuilder();
	private int columns;
	private File path_html;

	public HtmlCovidTable(int columns, File path_html) {
		this.columns=columns;
		this.path_html=path_html;
		html_table.append("<html>");
		html_table.append("<table border=\"1\">");
		html_table.append("</table>");
		html_table.append("</html>");
	}


	public void addTitleInformation(ArrayList<String> titleColumns) {
		String rStart = "<tr>";
		String rFinish = "</tr>";
		String cStart = "<td>";
		String cFinish ="</td>";
		String htmlFinish = "</table>";

		if(titleColumns.size()==columns) {
			int lastTime = html_table.lastIndexOf(htmlFinish);
			System.out.println(lastTime);;
			if (lastTime > 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(rStart);
				for(String title : titleColumns) {
					sb.append(cStart);
					sb.append(title);
					sb.append(cFinish);
				}
				sb.append(rFinish);
				html_table.insert(lastTime, sb.toString());
			}
		}
	}

	public void addSubInformation(ArrayList<String> subInformation) {
		String rStart = "<tr>";
		String rFinish = "</tr>";
		String cStart = "<td>";
		String cFinish ="</td>";


		if(subInformation.size()==columns) {
			int lastTime = html_table.lastIndexOf(rFinish);
			if (lastTime > 0) {
				StringBuilder sb = new StringBuilder();
				int newTime = lastTime + rFinish.length();
				sb.append(rStart);
				for(String information : subInformation) {
					sb.append(cStart);
					sb.append(information);
					sb.append(cFinish);
				}
				sb.append(rFinish);
				html_table.insert(newTime, sb.toString());
			}
		}
	}


	public void createFile(ArrayList<String> titleColumns, ArrayList<ArrayList<String>> allLinesInformation) {
		File f = path_html;
		try {
			addTitleInformation(titleColumns);
			for(ArrayList<String> informationLine : allLinesInformation) {
				addSubInformation(informationLine);
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(html_table.toString());
			bw.close();
			Desktop.getDesktop().browse(f.toURI());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
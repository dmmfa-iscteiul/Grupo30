import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.metadata.model.DocumentMetadata;

public class Main {

//	private static String path = "C:\\Users\\anabe\\wordpress\\ArtigosCovid\\";
	private static String path = "C:\\Users\\Diogo\\Documents\\Informatica e Gestão\\Engenharia Software II\\ArtigosCovid\\";
	
	public static void main(String[] args) {

		HtmlBuilder htmlBuilder = new HtmlBuilder(null, true, 2, 4);
		File[] list_of_files = new File(path).listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				String name = f.getName();
				if (name.endsWith(".pdf"))
					return true;

				return false;
			}
		});

		htmlBuilder.addTableHeader("Article Title", "Journal Name", "Publication Year", "Authors");
		
		try {
			
			for (File f : list_of_files) {

				ContentExtractor extractor = new ContentExtractor();
				InputStream inputStream = new FileInputStream(f);
				extractor.setPDF(inputStream);
				DocumentMetadata metadata = extractor.getMetadata();

				String title = metadata.getTitle();
				String journal = metadata.getJournal();
				String year = metadata.getDate(DateType.PUBLISHED).getYear();
				List<DocumentAuthor> authores = metadata.getAuthors();

				StringBuilder sb = new StringBuilder();
				
				for(DocumentAuthor a : authores) 
					sb.append(a.getName() + ", ");
				
				String finalNames = sb.toString();
								
				StringBuilder link = new StringBuilder();
				link.append("<a href=");
				link.append("\"" + path );
				link.append(f.getName() + "\"" + ">");
				link.append(title);
				link.append("</a>");

				htmlBuilder.addRowValues(link.toString(),journal, year, finalNames);
	
			}

			String table = htmlBuilder.build();
			System.out.println(table);
			File file = new File("Article-Data.htm");
			htmlBuilder.writeToFile(file, table);
			Desktop.getDesktop().browse(file.toURI());
				
		} catch (AnalysisException | IOException e) {}

	}

}

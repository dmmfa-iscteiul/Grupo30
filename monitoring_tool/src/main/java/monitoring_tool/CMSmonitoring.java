package monitoring_tool;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CMSmonitoring {
	
	private String cmsURL="http://192.168.99.100:8000";
	private WebDriver driver;
	private int uptime_login;
	private int uptime_pages;
	private int uptime_rep;
	private int downtime_login;
	private int downtime_pages;
	private int downtime_rep;
	static TableBuilder tb = new TableBuilder();


	public CMSmonitoring() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
	}

	/**
	 * classe testAvailabilitypage é usada para navegar até ao URL da pagina dado como parametro de forma a testar
	 * se este está a abrir ou não
	 * @param pageURL fornece o URL do site pretendido
	 */
	public void testAvailabilitypage(String pageURL) {
		driver.navigate().to(pageURL);
	}

	/**
	 * classe testLogin é usada para navegar até certa página de login de determinado URL inserindo automaticamente
	 * as credencias de determinado utilizador de forma a testar a entrada no site.
	 * Caso entre no site, isto é, caso encontre a palavra DashBoard, então a metrica de 'uptime' é incrementada 
	 * servindo de contador para as vezes que a entrada foi bem sucedida
	 * Caso contrario é incrementada contrariamente a metrica de 'downtime' contabilizando as vezes em que o login
	 * nao foi bem sucedido e é enviado um email ao administrador a informar do problema
	 * Entra no else quando se tenta fazer um login com uma sessão já iniciada.
	 */
	public void testLogin() {
		driver.navigate().to("http://192.168.99.100:8000/wp-login.php");
		driver.findElement(By.id("user_login")).sendKeys("admin");
		driver.findElement(By.id("user_pass")).sendKeys("admin");
		driver.findElement(By.id("wp-submit")).click();
		if (driver.findElement(By.tagName("h1")).getText().equals("Dashboard")) {
			uptime_login++;
			System.out.println("Login com sucesso! \n" + "metrica uptime: " +uptime_login);
		} else {
			downtime_login++;
			System.out.println("Falha no Login! \n"+ "metrica downtime: " + downtime_login);
			sendEmail("Ora bolas! Falha no Login...");
		}
	}
	
	/**
	 * classe testWebPages é usada para certificar a abertura de certas páginas no site
	 * Caso entre na pagina então a metrica de 'uptime' é incrementada 	 * servindo de contador para as vezes 
	 * que a entrada foi bem sucedida
	 * Caso contrario é incrementada contrariamente a metrica de 'downtime' contabilizando as vezes em que a entrada
	 * nao foi bem sucedida e é enviado um email ao administrador a informar do problema
	 */

	//	public void testWebPages() {
	//		try {	
	//			driver.findElement(By.linkText("Home"));
	//			driver.findElement(By.linkText("About us"));
	//			driver.findElement(By.linkText("FAQ"));
	//			driver.findElement(By.linkText("Repository"));
	//			driver.findElement(By.linkText("Covid Applications"));
	//			driver.findElement(By.linkText("Web Site Analytics"));
	//			driver.findElement(By.linkText("User"));
	//			uptime_pages++;
	//		}catch (Exception e) {
	//			downtime_pages++;
	//			System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//			sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//		}
	//	}
	
	/**
	 * classe testWebRep é usada para testar a abertura do Respositório
	 *  Em caso de sucesso a metrica de 'uptime' é incrementada 	 
	 * Caso contrario é incrementada contrariamente a metrica de 'downtime' e enviado um email sobre onde está a falha
	 */
	//
	//	public void testWebRep() {
	//		try {	
	//			driver.findElement(By.linkText("Repository"));
	//			uptime_ref++;
	//		}catch (Exception e) {
	//			downtime_rep++;
	//			System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_rep);
	//			sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_rep);
	//		}
	//	}
	
	/**
	 * classe sendMail é usada para enviar emails de desilusão ao administrados caso a abertura de uma pagina 
	 * falhe no site
	 * @param email  conteúdo do email
	 */
	
	public void sendEmail(String email) {
		final String username = "es2iscteiultest@gmail.com";
		final String password = "iscteiultest";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); //TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("es2iscteiultest@gmail.com"));
			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse("es2iscteiultest@gmail.com")
					);
			message.setSubject("Problema here!");
			message.setText("Dear Friend, " + email);
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * classe runTests é usada para testar a viabilidade das páginas, atribuir o cabeçalho à tabela e atribuir
	 *  as linhas e o conteudo de cada célula nessa linha
	 */

	public void runTests() throws InterruptedException {
		testLogin();
		//		testWebPages();
		//		testWebRep();
		testAvailabilitypage(cmsURL);
		tb.addTableHeader("metric", "value");
		tb.addRowValues("uptime_login", Integer.toString(uptime_login) );
		tb.addRowValues("uptime_page ", Integer.toString(uptime_pages));
		tb.addRowValues("uptime_rep", Integer.toString(uptime_rep));
		tb.addRowValues("downtime_login", Integer.toString(downtime_login));
		tb.addRowValues("downtime_page", Integer.toString(downtime_pages));
		tb.addRowValues("downtime_page", Integer.toString(downtime_rep));		
	}
	
	/**
	 * classe writeFile é usada para, uma vez atribuido o nome de um ficheiro e um conteudo, cria esse ficheiro 
	 * com intuito html de forma onde o conteudo deste será a tabela contruida com as respetivas linhas e colunas 
	 * @param file nome a dar ao ficheiro html 
	 * @param content conteudo a escrever no ficheiro file html
	 * 
	 */

	public static void writeFile( File file, String content ) throws IOException {
		FileOutputStream outputStream = new FileOutputStream( file, true );
		outputStream.write( content.getBytes( "UTF-8" ) );
		outputStream.close();
	}

	public static void main(String args[]) throws InterruptedException, IOException {
		CMSmonitoring tool = new CMSmonitoring();

		while(true) {
			tb.htmlTableBuilder(null, true, 6, 2);	
			File f= new File("Metricas.html");
			tool.runTests();
			String s = tb.buildTable();
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			writer.write(s);
			writer.close();
			Desktop.getDesktop().browse(f.toURI());
			tb= new TableBuilder();
			Thread.sleep(7200000);
		}
	}

}




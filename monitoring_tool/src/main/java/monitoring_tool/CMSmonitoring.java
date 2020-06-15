package monitoring_tool;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.module.FindException;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.ClickAction;

public class CMSmonitoring {

	private String cmsURL="https://www.zara.com/pt/";
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
	 * Método usado para navegar até ao URL da pagina dado como parametro de forma a testar
	 * se este está a abrir ou não
	 * @param pageURL fornece o URL do site pretendido
	 */
	public void testAvailabilitypage(String pageURL) {
		driver.navigate().to(pageURL);
	}


	/**
	 * Método usado para navegar até certa página de login de determinado URL inserindo automaticamente
	 * as credencias de determinado utilizador de forma a testar a entrada no site.
	 * Caso entre no site,isto é, caso o URL inicial seja diferente do Atual então a metrica de 'uptime' é incrementada 
	 * servindo de contador para as vezes que a entrada foi bem sucedida
	 * Caso contrario é incrementada contrariamente a metrica de 'downtime' contabilizando as vezes em que o login
	 * nao foi bem sucedido e é enviado um email ao administrador a informar do problema
	 * Entra no else quando se tenta fazer um login com uma sessão já iniciada.
	 * @throws InterruptedException 
	 */

	public void testLogin() throws InterruptedException {
		driver.navigate().to("https://www.zara.com/pt/");
		driver.findElement(By.xpath("//*[@id='header-actions']/li[1]/a")).click();
		WebElement e = driver.findElement(By.xpath("//*[@id=\"main\"]/article/div/div/section[1]/form/div[1]/div[1]/div/div/div[1]/input"));
		e.sendKeys("es2iscteiultest@gmail.com");
		WebElement p = driver.findElement(By.xpath("//*[@id=\"main\"]/article/div/div/section[1]/form/div[1]/div[2]/div/div/div[1]/input"));
		p.sendKeys("IscteIulTest123");
		String s = driver.getCurrentUrl();
		driver.findElement(By.xpath("//*[@id=\"main\"]/article/div/div/section[1]/form/div[2]/button")).click();
		Thread.sleep(700);
		String s2= driver.getCurrentUrl();

		if(!s.equals(s2)) {			
			uptime_login++;
			System.out.println("Login com sucesso! \n" + "metrica uptime_logIn: " +uptime_login);
		} else {
			downtime_login++;
			System.out.println("Falha no Login! \n"+ "metrica downtime_logIn: " + downtime_login);
			sendEmail("Ora bolas! Falha no Login...");
		}
	}

	/**
	 * Método usado para certificar a abertura de certas páginas no site
	 * Caso entre na pagina então a metrica de 'uptime' é incrementada 	 
	 * servindo de contador para as vezes 
	 * que a entrada foi bem sucedida
	 * Caso contrario é incrementada contrariamente a metrica de 'downtime' contabilizando as vezes em que a entrada
	 * nao foi bem sucedida e é enviado um email ao administrador a informar do problema
	 */

	public void testWebPages() {
		try {	
			driver.navigate().to("https://www.zara.com/pt/pt/mulher-blazers-l1055.html?v1=1445747");
			driver.findElement(By.xpath("//*[@id=\"product-56262085\"]/div/div[2]/a")).getText();
			System.out.println(driver.findElement(By.xpath("//*[@id=\"product-56262085\"]/div/div[2]/a")).getText());
			uptime_pages++;
			driver.findElement(By.linkText("Moman")).click();
		}catch (Exception e) {
			downtime_pages++;
			System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
			sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
		}
	}

	public void testWebRep() {
		try {	
			driver.navigate().to("https://www.zara.com/pt/pt/mulher-blazers-l1055.html?v1=1445747");
			driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[1]/a/span")).getText();
			uptime_rep++;

		}catch (Exception e) {
			downtime_pages++;
			System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_rep);
			sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_rep);
		}
	}


	//	/**
	//	 * Métodos destinadas ao site original para certificar a abertura de certas páginas no site
	//	 * Caso entre na pagina então a metrica de 'uptime' é incrementada 	 
	//	 * servindo de contador para as vezes que a entrada foi bem sucedida
	//	 * Caso contrario é incrementada contrariamente a metrica de 'downtime' contabilizando as vezes em que a entrada
	//	 * nao foi bem sucedida e é enviado um email ao administrador a informar do problema
	//	 */
	//	
	//public void testHomePage() {
	//try {	
	//	driver.navigate().to("http://192.168.99.100:8000/");
	//	driver.findElement(By.xpath("//*[@id=\"menu-item-126\"]/a\r\n")).getText();
	//	uptime_pages++;
	//}catch (Exception e) {
	//	downtime_pages++;
	//	System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//	sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//}
	//}
	//
	//public void testAboutUsPage() {
	//try {	
	//	driver.navigate().to("http://192.168.99.100:8000/\r\n");
	//	driver.findElement(By.xpath("//*[@id=\"menu-item-125\"]/a\r\n")).getText();
	//	uptime_pages++;
	//}catch (Exception e) {
	//	downtime_pages++;
	//	System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//	sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//}
	//}
	//
	//public void testCovidApplicationsPage() {
	//try {	
	//	driver.navigate().to("http://192.168.99.100:8000/\r\n");
	//	driver.findElement(By.xpath("//*[@id=\"menu-item-119\"]/a\r\n")).getText();
	//	uptime_pages++;
	//}catch (Exception e) {
	//	downtime_pages++;
	//	System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//	sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//}
	//}
	//
	//public void testFAQPage() {
	//try {	
	//	driver.navigate().to("http://192.168.99.100:8000/\r\n");
	//	driver.findElement(By.xpath("//*[@id=\"menu-item-144\"]/a\r\n")).getText();
	//	uptime_pages++;
	//}catch (Exception e) {
	//	downtime_pages++;
	//	System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//	sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//}
	//}
	//
	//public void testContactUsPage() {
	//try {	
	//	driver.navigate().to("http://192.168.99.100:8000/\r\n");
	//	driver.findElement(By.xpath("//*[@id=\"menu-item-145\"]/a\r\n")).getText();
	//	uptime_pages++;
	//}catch (Exception e) {
	//	downtime_pages++;
	//	System.out.println("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//	sendEmail("Falha a entrar nas páginas! \n" + "metrica downtime: " + downtime_pages);
	//}
	//}

	/**
	 * Método usado para enviar emails de desilusão ao administrados caso a abertura de uma pagina 
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
	 * Método usado para testar a viabilidade das páginas, atribuir o cabeçalho à tabela e atribuir
	 *  as linhas e o conteudo de cada célula nessa linha
	 */

	public void runTests() throws InterruptedException {
		testAvailabilitypage(cmsURL);
		testLogin();
		testWebPages();
		testWebRep();
		//		testHomePage();
		//		testAboutUsPage();
		//		testCovidApplicationsPage();
		//		testFAQPage();
		//		testContactUsPage();
		tb.addTableHeader("metric", "value");
		tb.addRowValues("uptime_login", Integer.toString(uptime_login) );
		tb.addRowValues("uptime_page ", Integer.toString(uptime_pages));
		tb.addRowValues("uptime_rep", Integer.toString(uptime_rep));
		tb.addRowValues("downtime_login", Integer.toString(downtime_login));
		tb.addRowValues("downtime_page", Integer.toString(downtime_pages));
		tb.addRowValues("downtime_rep", Integer.toString(downtime_rep));		
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



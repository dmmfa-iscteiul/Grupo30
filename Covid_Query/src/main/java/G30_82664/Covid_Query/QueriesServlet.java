package G30_82664.Covid_Query;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/queries.do")
public class QueriesServlet extends HttpServlet {

	private CovidQuery covidQuery = new CovidQuery();
	private RegionService regionsList = new RegionService();
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/queries.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String button = request.getParameter("button");

        if ("button1".equals(button)) {
        	regionsList.deleteAll();
        	for(String s : covidQuery.getRegions())
        		regionsList.addRegion(new Region(s));
			request.getSession().setAttribute("query", "What are the regions?");
			response.sendRedirect("/list-regions.do");
			
		} else if ("button2".equals(button)) {
			String region = request.getParameter("region");
			String type = request.getParameter("type");
			request.getSession().setAttribute("val", covidQuery.getNumberOf(region, type));
			if(type.equals("Testes")) type = "tests";
			if(type.equals("Infecoes")) type = "infections";
			if(type.equals("Internamentos")) type = "hospitalizations";
			request.getSession().setAttribute("query", "What is the total number of " + type + " in the region of " + region + "?");
			response.sendRedirect("/get-value.do");
			
		} else if ("button3".equals(button)) {
			String type1 = request.getParameter("type1");
			String type2 = request.getParameter("type2");
			System.out.println(" A escolha é: " + type1 + " " + type2);
			request.getSession().setAttribute("val", covidQuery.getTotalOr(type1, type2));
			if(type1.equals("Testes")) type1 = "tests";
			if(type1.equals("Infecoes")) type1 = "infections";
			if(type1.equals("Internamentos")) type1 = "hospitalizations";
			if(type2.equals("Testes")) type2 = "tests";
			if(type2.equals("Infecoes")) type2 = "infections";
			if(type2.equals("Internamentos")) type2 = "hospitalizations";
			request.getSession().setAttribute("query", "What is the total number of " + type1 + " or " + type2 + "?");
			response.sendRedirect("/get-value.do");
		
		} else if ("button4".equals(button)) {
			String type = request.getParameter("type0");
			String op = request.getParameter("op");
			String number = request.getParameter("number");
			System.out.println(" A escolha é: " + type + " " + op + " " + number);
			regionsList.deleteAll();
        	for(String s : covidQuery.getRegionsWhere(type, op, number))
        		regionsList.addRegion(new Region(s));
        	if(type.equals("Testes")) type = "tests";
			else if(type.equals("Infecoes")) type = "infections";
			else if(type.equals("Internamentos")) type = "hospitalizations";
        	if(op.equals(">")) op = "greater than";
        	else if(op.equals("<")) op = "less than";
        	else if(op.equals("<=")) op = "less than or equals to";
        	else if(op.equals(">=")) op = "greater than or equals to";
			request.getSession().setAttribute("query", "Which regions where " + type + " " + op + " " + number + "?");
			response.sendRedirect("/list-regions.do");
		}
	}
}
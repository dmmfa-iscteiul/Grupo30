package G30_82664.Covid_Query;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/list-regions.do")
public class ListRegionsServlet extends HttpServlet {

	private RegionService regionService = new RegionService();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("todos", regionService.getRegions());
		request.getRequestDispatcher("/WEB-INF/views/list-regions.jsp").forward(request, response);
	}
}
package travel;

import java.io.IOException;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchFlights
 */
@WebServlet(name = "filter", urlPatterns = { "/filter" })
public class FilterFlights extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String dburl,dbuname,dbpass;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterFlights() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init()
    {
    	dburl=getServletContext().getInitParameter("dburl");
    	dbuname=getServletContext().getInitParameter("dbuname");
    	dbpass=getServletContext().getInitParameter("dbpass");
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Get the parameters
		String src=request.getParameter("src");
		String dest=request.getParameter("dest");
		String day=request.getParameter("day");
		String company=request.getParameter("company");
		String stops=request.getParameter("stops");
		
		DAO dao=new DAO(dburl,dbuname,dbpass);
		ResultSet rs=null;
		
		System.out.println(day);

		try 
		{
			rs=dao.filterFlights(src,dest,company,stops,day);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("flights",rs);
		request.setAttribute("day",day);
		request.setAttribute("src",src);
		request.setAttribute("dest",dest);
		
		//Then redirect to dashboard
		request.getRequestDispatcher("flights.jsp").forward(request, response);;  
		
		
	}

}

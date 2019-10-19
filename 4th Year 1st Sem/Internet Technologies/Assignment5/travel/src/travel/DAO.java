package travel;

import java.sql.*;

public class DAO {
	
	private Connection con;
	private Statement stmt;
	private String url,uname,password;
	
	public DAO(String url, String uname, String password)
	{
		this.url=url;
		this.uname=uname;
		this.password=password;
	}
	
	//Function to establish the connection
	private void setConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(url,uname,password);
		stmt=con.createStatement(); 
	}
	
	//Function to close the connection
	private void closeConn() throws SQLException
	{
		con.close();
	}
	
	//Function for getting cities
	public ResultSet getCities() throws ClassNotFoundException, SQLException
	{
		setConnection();
		String query="select * from cities";
		ResultSet rs=stmt.executeQuery(query);
		
		if(rs.next())
		{
			return rs;
		}
		closeConn();
		return null;
	}
	
	//Function for getting flights 
	public ResultSet getFlights(String src, String dest, String day) throws ClassNotFoundException, SQLException
	{
		setConnection();
		String query1="(select * from flights where src_city=\'"+src+"\' and dest_city=\'"+dest+"\' and day=\'"+day+"\')";
		
//		Timestamp time = new Timestamp(System.currentTimeMillis());
//        System.out.println(time);
        
		String query2="(select * from offers where CURRENT_TIMESTAMP between start_time and end_time)";
		
		String query="select * from "+query1+" f left join "+query2+" o on f.id=o.flight_id";
		
		System.out.println(query);
		
		ResultSet rs=stmt.executeQuery(query);
		
		if(rs.next())
		{
			return rs;
		}
		closeConn();
		return null;
	}
	
	//Function for getting flights 
	public ResultSet getOffers() throws ClassNotFoundException, SQLException
	{
		setConnection();
		
//		Timestamp time = new Timestamp(System.currentTimeMillis());
//        System.out.println(time);
        
		String query="select * from offers, flights where CURRENT_TIMESTAMP between offers.start_time and offers.end_time and offers.flight_id=flights.id";
		
		System.out.println(query);
		
		ResultSet rs=stmt.executeQuery(query);
		
		
		if(rs.next())
		{
			return rs;
		}
		closeConn();
		return null;
	}
	
}
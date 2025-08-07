package sql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion 
{
	static String host_Server="localhost";
    static String user_Server="root";
    static String pass_Server="km2805";
    static String base_datos="famicaja";
    static boolean entro=false;
    static boolean se_conecto=false;
    
    public static Statement  st=null;
    public static Connection con=null;
    public ResultSet rs;
    
    public static Connection getConnection() {
    	Connection con = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		String url =  "jdbc:mysql://" + host_Server + "/" + base_datos + "?useSSL=false&serverTimezone=UTC";
    		con = DriverManager.getConnection(url, user_Server, pass_Server);
    	} catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return con;
    }

	
	public Conexion() 
	{
		cone1();
		desconectar();
	}
	
	public static void cone1()
    {
		boolean mostrar=true;
	    try
    	{
            String driver = "com.mysql.cj.jdbc.Driver";
            try
            {
                if(mostrar)                    
                {
                    System.out.println( "=> Cargando el Driver 1:" );
                }               
                Class.forName(driver);
            } catch (ClassNotFoundException ex) 
            {
               
            }
            if(mostrar)                    
            {
                System.out.println("OK");
            }
           
            String url  = "jdbc:mysql://"+host_Server+"/"+base_datos ;
            String user = user_Server;
            String pass = pass_Server;
           
            if(mostrar)                    
            {
                System.out.println( "=> conectando: 1" );
            }            
            con = DriverManager.getConnection(url,user,pass);
            if(mostrar)                    
            {
                System.out.println("OK");
            }            
            
            st= con.createStatement();
    	}catch( SQLException x )
    	{
           
            System.out.println("ERROR 5"+x);
    	}
    }
	
	public void desconectar() 
	{

		try {
			con.close();
			System.out.println("Desconectado");

		} catch (Exception e) {

			Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,e);
		}
	}
	
	public static void main(String args[])
	{
		new Conexion();
	}
}


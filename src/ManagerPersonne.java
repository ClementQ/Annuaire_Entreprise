
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ManagerPersonne 
{
	
	static final String DB_URL = "jdbc:mysql://";
	
    static String USER = "root";                //Définition du nom d'user
    static String PASS = "";                //Définition du mdp
    
    static String ip = "127.0.0.1";
    
    
    static Connection conn = null;
    String requete = "";
    ResultSet rs = null;
    
    
    public ManagerPersonne() 
    {
    	
    }
    
    public void connecter() throws ClassNotFoundException, SQLException {
    	
    	
            try {                                                //Chargement du driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                 System.out.println("Impossible de charger le pilote jdbc:odbc");
           }
            
            System.out.println("Connection à la base de données...");    //Connection à la base
            try {
            conn = (Connection) DriverManager.getConnection(DB_URL+ip+":3306/annuaire",USER,PASS);
            System.out.println("Connection réussie");
            	
            } catch (SQLException e) {
                System.out.println("Connection à la base de données impossible");
           }
    }
    
    public ResultSet lecturePersonnes() throws SQLException
    {
    	
    	requete = "SELECT nom, prenom, telephone, date, services.service FROM personnes, services WHERE service_id = services.id_service";
            try {
                Statement stmt = (Statement) conn.createStatement();
                rs = stmt.executeQuery(requete);
            } catch (SQLException e) { 
            System.out.println("Anomalie lors de l'execution de la requête");
            System.out.println(e.getErrorCode());
            }
        
        return rs;
    }
    
    public void insertPersonnes(String lName, String fName, String phone, int idService, String date)
    {
    	requete = "INSERT INTO `personnes` (`id_personne`, `nom`, `prenom`, `telephone`, `service_id`, `date`) VALUES (NULL, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement pstmt = conn.prepareStatement(requete);
			pstmt.setString(1, lName);
			pstmt.setString(2, fName);
			pstmt.setString(3, phone);
			pstmt.setInt(4, idService);
			pstmt.setString(5, date);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Anomalie lors de l'execution de la requête");
			e.printStackTrace();
		}
        
    }
    
    public ResultSet searchNom(String nom) 
    {
    	requete = "SELECT nom, prenom, telephone, date, services.service FROM personnes, services WHERE service_id = services.id_service AND nom = ?";
        try {
        	PreparedStatement pstmt = conn.prepareStatement(requete);
        	pstmt.setString(1, nom);
        	System.out.println(pstmt);
            rs = pstmt.executeQuery();
        } catch (SQLException e) { 
        System.out.println("Anomalie lors de l'execution de la requête");
        System.out.println(e.getErrorCode());
        }
        return rs;
    }
    
    public ResultSet searchPrenom(String prenom) 
    {
    	requete = "SELECT nom, prenom, telephone, date, services.service FROM personnes, services WHERE service_id = services.id_service AND prenom = ?";
        try {
        	PreparedStatement pstmt = conn.prepareStatement(requete);
        	pstmt.setString(1, prenom);
        	System.out.println(pstmt);
            rs = pstmt.executeQuery();
        } catch (SQLException e) { 
        System.out.println("Anomalie lors de l'execution de la requête");
        System.out.println(e.getErrorCode());
        }
    	return rs;
    }
    
    public ResultSet searchService(String service) 
    {
    	
    	requete = "SELECT nom, prenom, telephone, date, services.service FROM personnes, services WHERE service_id = services.id_service AND services.service = ?";
        try {
        	PreparedStatement pstmt = conn.prepareStatement(requete);
        	pstmt.setString(1, service);
        	System.out.println(pstmt);
            rs = pstmt.executeQuery();
        } catch (SQLException e) { 
        System.out.println("Anomalie lors de l'execution de la requête");
        System.out.println(e.getErrorCode());
        }
    	
    	return rs;
    }
    
    public ResultSet searchDate(String date) 
    {
    	requete = "SELECT nom, prenom, telephone, date, services.service FROM personnes, services WHERE service_id = services.id_service AND date = ?";
        try {
        	PreparedStatement pstmt = conn.prepareStatement(requete);
        	pstmt.setString(1, date);
        	System.out.println(pstmt);
            rs = pstmt.executeQuery();
        } catch (SQLException e) { 
        System.out.println("Anomalie lors de l'execution de la requête");
        System.out.println(e.getErrorCode());
        }
        
        ///permet d"afficher en console
      
//      try {
//          ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
//          int nbCols = rsmd.getColumnCount();
//          boolean encore = rs.next();
//          System.out.println("oui");
//          while (encore) {
//        	 System.out.println("oui 2");
//             for (int i = 1; i <= nbCols; i++) {
//                System.out.print(rs.getString(i) + " ");
//             }
//             
//             
//             System.out.println();
//             encore = rs.next();
//          }
//          System.out.println("oui 3");
//       	} catch (SQLException e) {
//          System.out.println(e.getMessage());
//       	}
//      System.out.println("oui 4");
    	return rs;
    }
}

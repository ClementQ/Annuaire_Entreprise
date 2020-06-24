import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIReader 
{
	private static HttpURLConnection con;
	String buff;
	
	public void readAPI()
	{
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		
		//initialize request
		URL url;
		try {
			url = new URL("https://randomapi.com/api/ll9zqofj?key=RD5M-G4HB-987L-Y16P");
			con = (HttpURLConnection) url.openConnection();
			
			//creation de Requete
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			int status = con.getResponseCode();
			//System.out.println(status);
			
			if (status > 299)
			{
				reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while((line = reader.readLine()) != null)
				{
					responseContent.append(line);
				}
				reader.close();
			} 
			else 
			{
				reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while((line = reader.readLine()) != null)
				{
					responseContent.append(line);
				}
				reader.close();
			}
			System.out.println(responseContent.toString());
			buff = responseContent.toString();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
	}
	
	public String getService() 
	{
		//valeur de départ de la valeur
		int v = buff.indexOf("service") + 10;
		//val de fin de la valeur
		int valFinal = buff.indexOf('"', v);
		
		//coupe la chaine
		String service = buff.substring(v,valFinal);
		return service;
	}
	
	public int getIdService() 
	{
		//valeur de départ de la valeur
		int v = buff.indexOf("idService") + 12;
		//val de fin de la valeur
		int valFinal = buff.indexOf('"', v);
		
		//coupe la chaine
		String idServiceString = buff.substring(v,valFinal);
		int idService = Integer.parseInt(idServiceString);
		return idService;
	}
	
	public String getLName() 
	{
		
		//valeur de départ de la valeur
		int v = buff.indexOf("lastname") + 11;
		//val de fin de la valeur
		int valFinal = buff.indexOf('"', v);
				
		//coupe la chaine
		String lName = buff.substring(v,valFinal);
		
		return lName;
		
	}
	
	public String getFName() 
	{
		//valeur de départ de la valeur
		int v = buff.indexOf("firstname") + 12;
		//val de fin de la valeur
		int valFinal = buff.indexOf('"', v);
				
		//coupe la chaine
		String fName = buff.substring(v,valFinal);
		return fName;
		
	}
	
	public String getPhone() 
	{
		//valeur de départ de la valeur
		int v = buff.indexOf("phone") + 8;
		//val de fin de la valeur
		int valFinal = buff.indexOf('"', v);
				
		//coupe la chaine
		String phone = buff.substring(v,valFinal);
		return phone;
		
	}
	
	public String getDate() 
	{
		//valeur de départ de la valeur
		int v = buff.indexOf("date") + 7;
		//val de fin de la valeur
		int valFinal = buff.indexOf('"', v);
				
		//coupe la chaine
		String date = buff.substring(v,valFinal);
		return date;
		
	}
}



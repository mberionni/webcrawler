import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class readerWWW
{
	public static String read(String url) throws MalformedURLException, IOException
	{
		URLConnection conn = new URL(url).openConnection();
		conn.connect();

		StringBuilder sbIn = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
		{
			sbIn.append(inputLine);
			// System.out.println(inputLine);
		}
		in.close();

		return sbIn.toString();
	}
}

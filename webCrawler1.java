import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class webCrawler1
{
	private HashSet<String> map1 = new HashSet<String>();
	private Queue<String> q1 = new Queue<String>();
	private HashMap<String, urlAnalysis> sitemap = new HashMap<String, urlAnalysis>();
	private int num = 0;
	private int maxq = 0;

	public webCrawler1()
	{
		System.out.print("web Crawler constructor");
		this.num = 0;
		this.maxq = 0;
	}

	public int getMaxQueueSize()
	{
		return maxq;
	}

	private boolean isStatic(String res)
	{
		if (res.matches(".*\\.css$") || res.matches(".*\\.js$")
				|| res.matches(".*\\.txt$") || res.matches(".*\\.pdf$")
				|| res.matches(".*\\.png$") || res.matches(".*\\.jpg$")
				|| res.matches(".*\\.gif$") || res.matches(".*\\.ico$")) return true;

		return false;
	}

	private void addURLs(urlAnalysis urlsList)
	{
		int qs;
		Bag<String> l1 = urlsList.getLinks();

		for (String url : l1)
		{
			System.out.print("addURLs - current link " + url);
			if (!map1.contains(url))
			{
				// url1 still to be visited
				System.out.println(" ... NEW... adding to queue");
				q1.enqueue(url);
				map1.add(url);
			}
			else
			{
				System.out.println(" ... already in queue.");
			}
		}

		qs = q1.size();
		if (qs > maxq) maxq = qs;
	}

	public int mainCrawl(String url, String d1)
	{
		boolean domainVisited;
		String nextURL;
		String dom1;
		int qs;
		urlAnalysis f2;

		domainVisited = false;
		System.out.println("Starting URL=" + url + " Initial queue size="
				+ q1.size());

		dom1 = "";
		Pattern domainPattern = Pattern
				.compile("^http[s]?://([\\-a-zA-Z.0-9]+)/");
		Matcher domainMatcher = domainPattern.matcher(url);
		if (domainMatcher.find() && domainMatcher.groupCount() == 1)
		{
			dom1 = domainMatcher.group(1);
		}

		System.out.println("mMain Url=" + url + " found domain =" + dom1);
		if (dom1 == "")
		{
			if (d1 == null) dom1 = "gocardless.com";
			else dom1 = d1;
		}

		q1.enqueue(url);

		// main crawler loop
		while (!domainVisited)
		{
			nextURL = q1.dequeue();

			f2 = crawl1(nextURL, dom1);

			System.out.println("-------- url crawling completed -------");
			System.out.println("---------------------------------------");
			System.out.println();

			System.out.println("---------------------------------------");
			System.out.println("-------- adding urls (" + f2.getLinks().size()
					+ ") to queue ----------");
			System.out.println("--------- Current queue size = " + q1.size());

			addURLs(f2);
			sitemap.put(nextURL, f2);
			qs = q1.size();

			System.out.println("-------- url adding  completed ------=");
			System.out.println("-------- Current queue size = " + qs);
			System.out.println("---------------------------------------");

			if (qs == 0) domainVisited = true;
		}

		System.out.println();
		System.out.println("------------------------------");
		System.out.println("--------- Job done! ----------");
		System.out.println("------------------------------");
		return num;

	}

	private urlAnalysis crawl1(String url1, String domain)
	{
		System.out.println();
		System.out.println("Visiting url: " + url1);

		Bag<String> linksBag = new Bag<String>();
		Bag<String> srBag = new Bag<String>();

		try
		{
			String pagecode = readerWWW.read(url1);

			Pattern pattern = Pattern.compile("href\\s*=\\s*\"([^\"]*)\"");
			Matcher matcher = pattern.matcher(pagecode);
			// System.out.println("pagecode="+pagecode);

			while (matcher.find() == true)
			{
				if (matcher.groupCount() != 1)
				{
					System.out.print("skipping beacuse of group count");
					continue;
				}
				String link = matcher.group(1);

				// || link.startsWith("./") || link.startsWith("../"))
				System.out.print("considering link " + link);
				if (link.startsWith("/"))
				{
					link = "http://" + domain + link;
					System.out.print(" (" + link + ") ");
				}

				if (isStatic(link))
				{
					System.out.println(" ... static res ok! ");
					srBag.add(link);
					continue;
				}

				if (link.contains(domain) && link.startsWith("http"))
				{
					System.out.println(" ... link ok! ");
					linksBag.add(link);
					continue;
				}
				System.out.println(" ... skipping href! ");
			}

			// search for others static resources
			// (done separately because not expert of Java regular expression
			// libraries)
			Pattern pattern1 = Pattern.compile("src\\s*=\\s*\"([^\"]*)\"");
			Matcher matcher1 = pattern1.matcher(pagecode);
			while (matcher1.find() == true)
			{
				if (matcher1.groupCount() != 1)
				{
					System.out.print("skipping beacuse of group count");
					continue;
				}
				String res = matcher1.group(1);

				System.out.print("considering src= " + res);

				if (isStatic(res))
				{
					System.out.println(" ... static res ok! ");
					srBag.add(res);
					continue;
				}

				System.out.println(" ... skipping static res! ");
			}

		}
		catch (MalformedURLException ex)
		{
			System.out.println("err malformed exception" + ex.toString());
		}
		catch (IOException ex)
		{
			System.out.println("err IOException");
		}

		urlAnalysis ua1 = new urlAnalysis(url1, linksBag, srBag);

		num++;
		return ua1;
	}

	void printStruct()
	{
		System.out.println();
		System.out.println();
		System.out.println("PRINTING SITE MAP");
		Iterator<Entry<String, urlAnalysis>> i2 = sitemap.entrySet().iterator();

		while (i2.hasNext())
		{
			Entry<String, urlAnalysis> pairs = i2.next();

			String key = pairs.getKey();
			urlAnalysis value = pairs.getValue();
			System.out.println();
			System.out.println("----------------------------------------- ");
			System.out.println("PAGE " + key);
			System.out.println("----------------------------------------- ");
			value.stampa();
		}
	}
}

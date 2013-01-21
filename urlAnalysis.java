public class urlAnalysis
{
	private String url;
	private Bag<String> links;
	private Bag<String> staticRes;

	public urlAnalysis(String url, Bag<String> lk, Bag<String> sr)
	{
		this.setUrl(url);
		this.setLinks(lk);
		this.setStaticRes(sr);
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setStaticRes(Bag<String> sr)
	{
		this.staticRes = sr;
	}

	public Bag<String> getLinks()
	{
		return links;
	}

	public void setLinks(Bag<String> links)
	{
		this.links = links;
	}

	public void stampa()
	{
		System.out.println(" LINKS ");
		for (String s : this.links)
			System.out.println("    " + s);

		System.out.println(" STATIC RESOURCES ");
		for (String s : this.staticRes)
			System.out.println("    " + s);
	}

}

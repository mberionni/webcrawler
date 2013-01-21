public class crawlingStart
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		webCrawler1 wc1 = new webCrawler1();
		String a1;

		if (args.length == 0)
		{
			System.out
					.println("Proper Usage is: java program websiteURL [domain]");
			System.exit(0);
		}

		if (args.length == 1) a1 = null;
		else a1 = args[1];

		int n = wc1.mainCrawl(args[0], a1);

		int maxq = wc1.getMaxQueueSize();

		System.out.println();
		System.out.println(" -------------------------------------  ");
		System.out.println(" -------- Analysis completed! --------  ");
		System.out.println(" -------------------------------------  ");
		System.out.println(" ------- Total page visited: " + n + " ------  ");
		System.out
				.println(" ------- Maximun queue size: " + maxq + " ------  ");

		wc1.printStruct();

	}

}

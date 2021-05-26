package com.maximuscooke.lib.common.net;

import com.maximuscooke.lib.common.collections.CHashMap;
import com.maximuscooke.lib.common.exceptions.CNetException;

public class CNetRunner
{

	public static final String SERVER_PARAM = "-sv";
	public static final String CLIENT_PARAM = "-cl";
	public static final String IP_ADDR_PARAM = "-ip";
	public static final String PORT_PARAM = "-pt";
	public static final String POOL_SIZE_PARAM = "-ps";
	public static final String TIMEOUT_PARAM = "-to";
	public static final String LOG_PARAM = "-lg";
	public static final String HELP_PARAM = "-hp";
	public static final String SHUTDOWN_PARAM = "-sd";
	public static final String SERVER_INFO = "-si";

	private static final String PREFIX = "-";

	public CNetRunner()
	{

	}

	public static void main(String[] args)
	{

		try
		{
			CHashMap<String, String> map = parse(args);

			if (map.containsKey(CNetRunner.SERVER_PARAM))
			{
				CServer svr = new CServer(map);

				svr.start();
			} 
			else if (map.containsKey(CNetRunner.CLIENT_PARAM))
			{
				CClient c = new CClient(map);

				if (map.containsKey(CNetRunner.SERVER_INFO))
				{
					System.out.println("[SERVER INFO=" + c.getServerInfo() + "]");
				}
				if (map.containsKey(CNetRunner.SHUTDOWN_PARAM))
				{
					c.shutdownServer();
				}

			} 
			else
			{
				throw new CNetException(String.format("must specify either %s SERVER or %s CLIENT", CNetRunner.SERVER_PARAM, CNetRunner.CLIENT_PARAM));
			}

			System.out.println("Exiting App...");

		} 
		catch (Exception e)
		{
			e.printStackTrace();

			CNetRunner.printUsage();
		}
	}

	private static CHashMap<String, String> parse(String[] args) throws CNetException
	{

		CHashMap<String, String> map = new CHashMap<String, String>();

		for (int i = 0; i < args.length; i++)
		{

			if (args[i].startsWith(PREFIX))
			{

				if (args[i].equalsIgnoreCase(CNetRunner.CLIENT_PARAM))
				{
					map.add(CNetRunner.CLIENT_PARAM, CNetRunner.CLIENT_PARAM);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.SERVER_PARAM))
				{
					map.add(CNetRunner.SERVER_PARAM, CNetRunner.SERVER_PARAM);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.IP_ADDR_PARAM))
				{
					map.add(CNetRunner.IP_ADDR_PARAM, args[i + 1]);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.PORT_PARAM))
				{
					map.add(CNetRunner.PORT_PARAM, args[i + 1]);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.POOL_SIZE_PARAM))
				{
					map.add(CNetRunner.POOL_SIZE_PARAM, args[i + 1]);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.TIMEOUT_PARAM))
				{
					map.add(CNetRunner.TIMEOUT_PARAM, args[i + 1]);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.LOG_PARAM))
				{
					map.add(CNetRunner.LOG_PARAM, CNetRunner.LOG_PARAM);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.SHUTDOWN_PARAM))
				{
					map.add(CNetRunner.SHUTDOWN_PARAM, CNetRunner.SHUTDOWN_PARAM);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.SERVER_INFO))
				{
					map.add(CNetRunner.SERVER_INFO, CNetRunner.SERVER_INFO);
				} 
				else if (args[i].equalsIgnoreCase(CNetRunner.HELP_PARAM))
				{
					printUsage();
					System.exit(0);
				} 
				else
				{
					throw new CNetException("Option: <" + args[i] + "> not recognized.");
				}

			}
		}

		return map;
	}

	private static void printUsage()
	{
		System.out
				.println(String.format("Usage: %s<=RUN-SERVER> or %s=<RUN-CLIENT> %s=<IP-ADDRESS> %s=<PORT-NUMBER> %s=<POOL-SIZE> %s=<TIMEOUT-TIME> %s=<LOG-ENABLED> %s=<HELP>", CNetRunner.SERVER_PARAM,
						CNetRunner.CLIENT_PARAM, CNetRunner.IP_ADDR_PARAM, CNetRunner.PORT_PARAM, CNetRunner.POOL_SIZE_PARAM, CNetRunner.TIMEOUT_PARAM, CNetRunner.LOG_PARAM, CNetRunner.HELP_PARAM));
	}

}

package com.maximuscooke.lib.common.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.maximuscooke.lib.common.IVersion;
import com.maximuscooke.lib.common.collections.CHashMap;
import com.maximuscooke.lib.common.exceptions.CNetException;

public class CClient implements IVersion
{

	private int mPort = CServer.DEFAULT_SERVER_PORT;

	private final int mReleaseVer = 1;
	private final int mBuildVer = 0;
	private final int mRevisionVer = 0;

	private String mNameOrIpAddress = CServer.DEFAULT_SERVER_NAME;

	public CClient(String nameOrIpAddress, int port)
	{
		this.mPort = port;

		mNameOrIpAddress = nameOrIpAddress;
	}

	public CClient(CHashMap<String, String> map) throws CNetException
	{
		if (map.containsKey(CNetRunner.PORT_PARAM))
		{
			this.mPort = Integer.parseInt(map.get(CNetRunner.PORT_PARAM));
		}

		if (map.containsKey(CNetRunner.IP_ADDR_PARAM))
		{
			this.mNameOrIpAddress = map.get(CNetRunner.POOL_SIZE_PARAM);
		}
	}

	public void shutdownServer()
	{
		this.runOnServer(new CPacketServerRequest(CServer.REQUEST_SERVER_SHUTDOWN));

	}

	public String getServerInfo()
	{

		CPacket p = this.runOnServer(new CPacketServerRequest(CServer.REQUEST_SERVER_INFO));

		return (String) p.getReturnValue();
	}

	public CPacket runOnServer(CPacket p)
	{

		CPacket result = null;

		try
		{
			Socket s = new Socket(mNameOrIpAddress, mPort);

			ObjectOutputStream outStream = new ObjectOutputStream(s.getOutputStream());

			ObjectInputStream inStream = new ObjectInputStream(s.getInputStream());

			outStream.writeObject(p);

			outStream.flush();

			result = (CPacket) inStream.readObject();

			outStream.close();

			inStream.close();

			s.close();

		} 
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();

			result = null;
		}

		return result;
	}

	@Override
	public int getReleaseVersion()
	{
		return this.mReleaseVer;
	}

	@Override
	public int getBuildVersion()
	{
		return this.mBuildVer;
	}

	@Override
	public int getRevisionVersion()
	{
		return this.mRevisionVer;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("[CLIENT=Version (");

		sb.append(this.getReleaseVersion());

		sb.append(".");

		sb.append(this.getBuildVersion());

		sb.append(".");

		sb.append(this.getRevisionVersion());

		sb.append(")]\n");

		sb.append("[CONNECTION SERVER=");

		sb.append(this.mNameOrIpAddress);

		sb.append("]\n");

		sb.append("[PORT=");

		sb.append(this.mPort);

		sb.append("]");

		return sb.toString();
	}

}

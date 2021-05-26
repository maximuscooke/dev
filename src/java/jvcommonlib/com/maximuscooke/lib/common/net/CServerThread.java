package com.maximuscooke.lib.common.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CServerThread extends Thread
{

	private Socket mSocket;

	private static final String mShutdownMsg = "[Server] Shutting Down";

	public CServerThread(Socket socket)
	{
		mSocket = socket;
	}

	@Override
	public void run()
	{

		try
		{

			ObjectInputStream inStream = new ObjectInputStream(mSocket.getInputStream());

			ObjectOutputStream outStream = new ObjectOutputStream(mSocket.getOutputStream());

			CPacket p = (CPacket) inStream.readObject();

			p.setErrorMessage(null);

			p.setErrorCode(CServer.REQUEST_CODE_SUCCESSFUL);

			if (p.getRequestID() == CServer.REQUEST_SERVER_SHUTDOWN)
			{

				CServer.shutdowServer();

				p.setReturnValue(mShutdownMsg);

			} 
			else if (p.getRequestID() == CServer.REQUEST_SERVER_INFO)
			{

				p.setReturnValue(mSocket.toString());

			} 
			else
			{
				try
				{

					System.out.println("Running on server");
					p.setReturnValue(p.runOnServer());

				} 
				catch (Exception e)
				{

					System.out.println(e.getMessage());

					p.setErrorMessage(e.getMessage());

					p.setErrorCode(CServer.REQUEST_CODE_FAILED);
				}
			}

			outStream.writeObject(p);

			outStream.flush();

			outStream.close();

			inStream.close();

			mSocket.close();

		} 
		catch (ClassNotFoundException | IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
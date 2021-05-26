package com.maximuscooke.lib.common.net;

public class CPacketServerRequest extends CPacket
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5027634995227703724L;

	public CPacketServerRequest()
	{
		this(CServer.REQUEST_SERVER_INFO);
	}

	public CPacketServerRequest(int requestID)
	{
		super(requestID);
	}

	@Override
	public Object runOnServer()
	{
		return null;
	}
}

package com.maximuscooke.lib.common.net;

public abstract class CPacketContext extends CPacket
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6208613982180882111L;

	public CPacketContext()
	{
		super(CServer.REQUEST_SERVER_CONTEXT);
	}

}

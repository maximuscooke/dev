package com.maximuscooke.lib.common;

public interface INotify
{
	void register(INotifyListener listener);

	void unregister(INotifyListener listener);

	void notify(int action, INotifyMessage msg);
}

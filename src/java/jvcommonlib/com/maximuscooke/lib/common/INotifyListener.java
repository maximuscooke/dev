package com.maximuscooke.lib.common;

public interface INotifyListener
{
	void notifyListeners(Object sender, int action, INotifyMessage msg);
}

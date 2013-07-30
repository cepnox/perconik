package sk.stuba.fiit.perconik.core.listeners;

import java.util.Map;

public interface CountingListener extends Listener
{
	public Map<Key, Long> getCounts();

	public long getCount(Key key);
	
	public static interface Key
	{
	}
}

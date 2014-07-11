package com.gratex.perconik.activity.ide.listeners;

import static com.google.common.base.Preconditions.checkArgument;
import static com.gratex.perconik.activity.ide.IdeData.setApplicationData;
import static com.gratex.perconik.activity.ide.IdeData.setEventData;
import static com.gratex.perconik.activity.ide.listeners.Utilities.currentTime;
import java.io.File;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import sk.stuba.fiit.perconik.core.listeners.GitReferenceListener;
import sk.stuba.fiit.perconik.eclipse.jgit.lib.GitRepositories;
import com.google.common.collect.Maps;
import com.gratex.perconik.activity.ide.IdeData;
import com.gratex.perconik.activity.ide.UacaProxy;
import com.gratex.perconik.services.uaca.ide.IdeCheckinEventRequest;

/**
 * A listener of IDE commit events. This listener handles desired
 * events and eventually builds corresponding data transfer objects
 * of type {@link IdeCheckinEventRequest} and passes them to the
 * {@link UacaProxy} to be transferred into the <i>User Activity Central
 * Application</i> for further processing.
 * 
 * <p>Commit listener listens to Git commit events only.
 * 
 * <p>Data available in an {@code IdeCheckinEventRequest}:
 * 
 * <ul>
 *   <li>{@code changesetIdInRcs} - current Git commit
 *   identifier (40 hexadecimal characters),
 *   for example {@code "ffba951d35f710abee873db3f5547043aeb3fde9"}.
 *   <li>{@code rcsServer} - see {@code RcsServerDto} below.
 *   <li>See {@link IdeListener} for documentation of inherited data.
 * </ul>
 * 
 * <p>Data available in an {@code RcsServerDto}:
 * 
 * <ul>
 *   <li>{@code url} - remote origin URL from the nearest Git repository,
 *   for example {@code https://github.com/perconik/perconik.git}. The nearest
 *   Git repository is the first one found on path starting in project root,
 *   going through workspace root down to the file system root.
 *   <li>{@code typeUri} - always {@code "git"}.
 * </ul>
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public final class IdeCommitListener extends IdeListener implements GitReferenceListener
{
	private final Object lock = new Object();
	
	@GuardedBy("lock")
	private final Map<File, Map<String, String>> cache;
	
	public IdeCommitListener()
	{
		this.cache = Maps.newHashMap();
	}
	
	private final boolean updateLastCommit(final File directory, final String branch, final String id)
	{
		Map<String, String> cache;
		
		synchronized (this.lock)
		{
			cache = this.cache.get(directory);
			
			if (cache == null)
			{
				this.cache.put(directory, cache = Maps.newHashMap());
			}
		}
		
		synchronized (cache)
		{
			String last = cache.get(branch);
			
			if (!id.equals(last))
			{
				cache.put(branch, id);
				
				return last != null;
			}
		}
		
		return false;
	}

	static final IdeCheckinEventRequest build(final long time, final String url, final String id)
	{
		final IdeCheckinEventRequest data = new IdeCheckinEventRequest();

		data.setChangesetIdInRcs(id);
		data.setRcsServer(IdeData.newGitServerData(url));

		setApplicationData(data);
		setEventData(data, time);
		
		if (Log.enabled()) Log.message().appendln("commit: " + id + " url: " + url).appendTo(console);
		
		return data;
	}
	
	final void process(final long time, final RefsChangedEvent event)
	{
		Repository repository = event.getRepository();
		File       directory  = repository.getDirectory();
		String     url        = GitRepositories.getRemoteOriginUrl(repository);
		
		checkArgument(url != null, "Unable to get remote origin url from %s", directory);
		
		String    branch = GitRepositories.getBranch(repository);
		RevCommit commit = GitRepositories.getMostRecentCommit(repository);
		
		String id = commit.getName();
		
		if (this.updateLastCommit(directory, branch, id))
		{
			UacaProxy.sendCheckinEvent(build(time, url, id));
		}
	}

	public final void onRefsChanged(final RefsChangedEvent event)
	{
		final long time = currentTime();
		
		execute(new Runnable()
		{
			public final void run()
			{
				process(time, event);
			}
		});
	}
}

package sk.stuba.fiit.perconik.environment;

import javax.annotation.Nullable;
import org.osgi.framework.Version;

public class JavaVerificationException extends Exception
{
	private static final long serialVersionUID = 0;

	private final String required;

	private final String detected;
	
	public JavaVerificationException(Version required, Version detected)
	{
		this(required, detected, null);
	}
	
	public JavaVerificationException(Version required, Version detected, @Nullable Throwable cause)
	{
		super("Java " + required + " required but Java " + detected + " detected", cause);
		
		this.detected = detected.toString();
		this.required = required.toString();
	}
	
	public final Version getDetectedJavaVersion()
	{
		return Version.parseVersion(this.detected);
	}

	public final Version getRequiredJavaVersion()
	{
		return Version.parseVersion(this.required);
	} 
}

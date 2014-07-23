package sk.stuba.fiit.perconik.core;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import sk.stuba.fiit.perconik.core.annotations.Experimental;
import sk.stuba.fiit.perconik.core.annotations.Internal;
import sk.stuba.fiit.perconik.core.annotations.Persistent;
import sk.stuba.fiit.perconik.core.annotations.Unsafe;
import sk.stuba.fiit.perconik.core.annotations.Unsupported;
import sk.stuba.fiit.perconik.core.annotations.Version;
import sk.stuba.fiit.perconik.osgi.framework.Bundles;
import sk.stuba.fiit.perconik.utilities.reflect.Reflections;
import sk.stuba.fiit.perconik.utilities.reflect.annotation.Annotable;
import sk.stuba.fiit.perconik.utilities.reflect.annotation.Annotations;

/**
 * Static helper methods pertaining to the core registrables. 
 * 
 * @author Pavol Zbell
 * @since 1.0
 */
public final class Registrables
{
	private Registrables()
	{
		throw new AssertionError();
	}
	
	@Persistent
	private static enum PersistentMark
	{
	}
	
	private static final class VersionHandler implements InvocationHandler, Serializable
	{
		private static final long serialVersionUID = 0L;

		private final String value;
		
		VersionHandler(final String value)
		{
			this.value = value;
		}

		public final Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
		{
			String member = method.getName();
			
			Class<?>[] parameters = method.getParameterTypes();
			
			if (member.equals("equals") && parameters.length == 1 && parameters[0] == Object.class)
			{
				Object o = args[0];
				
				return (o == this) || (o instanceof Version && Objects.equals(this.value, ((Version) o).value())); 
			}
			
	        switch (member)
	        {
	        	case "hashCode":
	        		return (127 * "value".hashCode()) ^ Objects.hashCode(this.value);
	        		
	        	case "toString":
	        		return "@" + Version.class.getName() +"(" + String.valueOf(this.value) + ")";
	        		
	        	case "annotationType":
	        		return Version.class;
	        		
	        	case "value":
	        		return this.value;
	        		
	        	default:
	        		throw new IncompleteAnnotationException(Version.class, member);
	        }
		}
	}
	
	private static final Persistent persistent = PersistentMark.class.getAnnotation(Persistent.class);
	
	private static final <R extends Registrable> Version version(final Class<R> type)
	{
		String value = Bundles.forClass(type).getVersion().toString();
		
		if (Strings.isNullOrEmpty(value))
		{
			throw new IllegalArgumentException();
		}
		
		return (Version) Proxy.newProxyInstance(Version.class.getClassLoader(), new Class[] { Version.class }, new VersionHandler(value));
	}
	
	public static final <R extends Registrable> Annotable toAnnotable(final Class<R> type)
	{
		LinkedList<Class<? super R>> types = Reflections.collectSuperclasses(type);

		types.addFirst(type);
		
		List<Annotation> annotations = Lists.newArrayList(Annotations.ofClasses(types));
		
		if (Serializable.class.isAssignableFrom(type))
		{
			annotations.add(persistent);
		}
				
		Annotable annotable = Annotations.asAnnotable(annotations);
		
		if (annotable.hasAnnotation(Version.class))
		{
			return annotable;
		}
		
		annotations.add(version(type));
		
		return Annotations.asAnnotable(annotations);
	}
	
	public static final boolean isExperimental(final Class<? extends Registrable> type)
	{
		return type.isAnnotationPresent(Experimental.class);
	}

	public static final boolean isInternal(final Class<? extends Registrable> type)
	{
		return type.isAnnotationPresent(Internal.class);
	}

	public static final boolean isPersistent(final Class<? extends Registrable> type)
	{
		return type.isAnnotationPresent(Persistent.class);
	}
	
	public static final boolean isUnsafe(final Class<? extends Registrable> type)
	{
		return type.isAnnotationPresent(Unsafe.class);
	}

	public static final boolean isUnsupported(final Class<? extends Registrable> type)
	{
		return type.isAnnotationPresent(Unsupported.class);
	}
}

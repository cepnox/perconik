package sk.stuba.fiit.perconik.utilities.reflect.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import sk.stuba.fiit.perconik.utilities.MoreLists;
import sk.stuba.fiit.perconik.utilities.reflect.accessor.Accessor;
import sk.stuba.fiit.perconik.utilities.reflect.accessor.Accessors;

public final class Annotations
{
	private Annotations()
	{
		throw new AssertionError();
	}
	
	public static final List<Annotation> ofClass(Class<?> type)
	{
		return ofElement(type);
	}

	public static final List<Annotation> ofClasses(Iterable<? extends Class<?>> types)
	{
		return ofElements(types);
	}

	public static final List<Annotation> ofElement(AnnotatedElement element)
	{
		return ImmutableList.copyOf(element.getAnnotations());
	}
	
	public static final List<Annotation> ofElements(Iterable<? extends AnnotatedElement> elements)
	{
		ImmutableList.Builder<Annotation> annotations = ImmutableList.builder();
		
		for (AnnotatedElement element: elements)
		{
			annotations.addAll(ofElement(element));
		}
		
		return annotations.build();
	}

	public static final Annotable asAnnotable(Collection<Annotation> annotations)
	{
		return new EnumeratedAnnotable(annotations.iterator());
	}
	
	public static final Annotable asAnnotable(Iterable<Annotation> annotations)
	{
		return new EnumeratedAnnotable(annotations.iterator());
	}
	
	public static final Annotable asAnnotable(Iterator<Annotation> annotations)
	{
		return new EnumeratedAnnotable(annotations);
	}

	public static final Map<String, String> toData(Annotation annotation)
	{
		 Map<String, String> data = Maps.newLinkedHashMap();
		
		for (Entry<String, Object> entry: toElements(annotation).entrySet())
		{
			String key   = keyToString(entry.getKey());
			String value = valueToString(entry.getValue());
					
			data.put(key, value == null || value.isEmpty() ? null : value);
		}
		
		return data;
	}

	public static final Map<String, Object> toElements(Annotation annotation)
	{
		 Map<String, Object> elements = Maps.newLinkedHashMap();
		
		for (Method method: annotation.annotationType().getDeclaredMethods())
		{
			String name = method.getName();
				
			Accessor<Object> accessor = Accessors.ofInstanceMethod(annotation, Object.class, name).get();
			
			elements.put(name, accessor.get());
		}
		
		return elements;
	}

	public static final String toString(Annotation annotation)
	{
		Class<? extends Annotation> type = annotation.annotationType();

		Map<String, String> data = Maps.filterValues(toData(annotation), Predicates.notNull());
		
		StringBuilder builder = new StringBuilder(keyToString(type.getSimpleName()));
		
		if (data.size() == 1 && data.containsKey("value"))
		{
			builder.append(" (").append(data.get("value")).append(")");
		}
		else if (data.size() != 0)
		{
			builder.append(" (");
			
			Joiner.on(", ").withKeyValueSeparator(": ").appendTo(builder, data);
			
			builder.append(")");
		}
		
		return builder.toString();
	}
	
	public static final String toString(Iterable<Annotation> annotations)
	{
		Set<String> values = Sets.newTreeSet();
		
		for (Annotation annotation: annotations)
		{
			values.add(toString(annotation));
		}
		
		return Joiner.on(", ").join(values);
	}
	
	private static final String keyToString(Object object)
	{
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, object.toString());
	}
	
	private static final String valueToString(Object object)
	{
		Set<String> elements = Sets.newTreeSet();
		
		for (Object element: MoreLists.wrap(object))
		{
			if (element instanceof Class)
			{
				elements.add(((Class<?>) element).getCanonicalName());
				
				continue;
			}

			String value = Objects.toString(element, "");
			
			if (value.isEmpty())
			{
				continue;
			}
			
			elements.add(value);
		}
		
		return Joiner.on(", ").join(elements);
	}
}

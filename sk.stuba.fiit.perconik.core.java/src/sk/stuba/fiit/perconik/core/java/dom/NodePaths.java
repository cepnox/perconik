package sk.stuba.fiit.perconik.core.java.dom;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Function;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;

import sk.stuba.fiit.perconik.eclipse.jdt.core.dom.NodeType;

public class NodePaths
{
	static final String unknownPathName = "_";
	
	static final String variableSeparator = ",";

	static final Path singleUnknownPath = Paths.get(unknownPathName);

	private static final NodePathExtractor<?> namePathExtractor = NodePathExtractor.using(PathNameStrategy.NAME);
	
	private static final NodePathExtractor<?> typePathExtractor = NodePathExtractor.using(PathNameStrategy.TYPE);

	private NodePaths()
	{
		throw new AssertionError();
	}
	
	public static final String unknownPathName()
	{
		return unknownPathName;
	}
	
	public static final String variableSeparator()
	{
		return variableSeparator;
	}
	
	private static enum PathNameStrategy implements Function<ASTNode, String>
	{
		NAME
		{
			public final String apply(final ASTNode node)
			{
				if (node == null)
				{
					return unknownPathName;
				}
				
				for (StructuralPropertyDescriptor descriptor: Nodes.structuralProperties(node))
				{
					if (descriptor.getId().equals("name"))
					{
						return node.getStructuralProperty(descriptor).toString();
					}
				}
				
				return unknownPathName;
			}
			
			@Override
			public final String toString()
			{
				return "name";
			}
		},
		
		TYPE
		{
			public final String apply(final ASTNode node)
			{
				return node != null ? NodeType.valueOf(node).getName() : unknownPathName;
			}
			
			@Override
			public final String toString()
			{
				return "type";
			}
		};
	}
	
	private static final <N extends ASTNode> NodePathExtractor<N> cast(final NodePathExtractor<?> extractor)
	{
		// only for stateless internal singletons shared across all types
		@SuppressWarnings("unchecked")
		NodePathExtractor<N> result = (NodePathExtractor<N>) extractor;
		
		return result;
	}
	
	public static final <N extends ASTNode> NodePathExtractor<N> namePathExtractor()
	{
		return cast(namePathExtractor);
	}

	public static final <N extends ASTNode> NodePathExtractor<N> typePathExtractor()
	{
		return cast(typePathExtractor);
	}
}

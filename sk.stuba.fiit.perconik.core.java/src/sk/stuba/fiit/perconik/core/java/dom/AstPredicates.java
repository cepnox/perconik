package sk.stuba.fiit.perconik.core.java.dom;

import java.util.Arrays;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import sk.stuba.fiit.perconik.eclipse.jdt.core.dom.AstNodeType;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

public final class AstPredicates
{
	private AstPredicates()
	{
		throw new AssertionError();
	}

	private static abstract class SingleType<N extends ASTNode> implements Predicate<N>
	{
		final AstNodeType type;
		
		SingleType(final AstNodeType type)
		{
			this.type = Preconditions.checkNotNull(type);
		}
	}
	
	private static final class SingleIsInstance<N extends ASTNode> extends SingleType<N>
	{
		SingleIsInstance(final AstNodeType type)
		{
			super(type);
		}

		public final boolean apply(final N node)
		{
			return this.type.isInstance(node);
		}
	}
	
	private static final class SingleIsMatching<N extends ASTNode> extends SingleType<N>
	{
		SingleIsMatching(final AstNodeType type)
		{
			super(type);
		}

		public final boolean apply(final N node)
		{
			return this.type.isMatching(node);
		}
	}

	private static abstract class MultiType<N extends ASTNode> implements Predicate<N>
	{
		final Set<AstNodeType> types;
		
		MultiType(final Iterable<AstNodeType> types)
		{
			this.types = ImmutableSet.copyOf(types);
		}
	}
	
	private static final class MultiIsInstance<N extends ASTNode> extends MultiType<N>
	{
		MultiIsInstance(final Iterable<AstNodeType> types)
		{
			super(types);
		}

		public final boolean apply(final N node)
		{
			for (AstNodeType type: this.types)
			{
				if (type.isInstance(node))
				{
					return true;
				}
			}
			
			return false;
		}
	}
	
	private static final class MultiIsMatching<N extends ASTNode> extends MultiType<N>
	{
		MultiIsMatching(final Iterable<AstNodeType> types)
		{
			super(types);
		}

		public final boolean apply(final N node)
		{
			for (AstNodeType type: this.types)
			{
				if (type.isMatching(node))
				{
					return true;
				}
			}
			
			return false;
		}
	}

	public static final <N extends ASTNode> Predicate<N> isInstance(final AstNodeType type)
	{
		return new SingleIsInstance<>(type);
	}

	public static final <N extends ASTNode> Predicate<N> isInstance(final AstNodeType ... types)
	{
		return isInstance(Arrays.asList(types));
	}

	public static final <N extends ASTNode> Predicate<N> isInstance(final Iterable<AstNodeType> types)
	{
		return new MultiIsInstance<>(types);
	}

	public static final <N extends ASTNode> Predicate<N> isMatching(final AstNodeType type)
	{
		return new SingleIsMatching<>(type);
	}

	public static final <N extends ASTNode> Predicate<N> isMatching(final AstNodeType ... types)
	{
		return isMatching(Arrays.asList(types));
	}

	public static final <N extends ASTNode> Predicate<N> isMatching(final Iterable<AstNodeType> types)
	{
		return new MultiIsMatching<>(types);
	}
}
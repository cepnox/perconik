package sk.stuba.fiit.perconik.core.java.dom.difference;

import java.util.Objects;

import javax.annotation.Nullable;

import org.eclipse.jdt.core.dom.ASTNode;

import sk.stuba.fiit.perconik.core.java.dom.Nodes;
import sk.stuba.fiit.perconik.eclipse.jdt.core.dom.NodeType;
import sk.stuba.fiit.perconik.utilities.SmartStringBuilder;

public abstract class NodeDelta<N extends ASTNode>
{
	NodeDelta()
	{
	}
	
	@Override
	public final boolean equals(@Nullable Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (!(o instanceof NodeDelta))
		{
			return false;
		}
		
		NodeDelta<?> other = (NodeDelta<?>) o;
		
		return Objects.equals(this.getOriginalNode(), other.getOriginalNode())
		    && Objects.equals(this.getRevisedNode(),  other.getRevisedNode());
	}
	
	@Override
	public final int hashCode()
	{
		return Objects.hashCode(this.getOriginalNode()) ^ Objects.hashCode(this.getRevisedNode());
	}

	@Override
	public final String toString()
	{
		return this.toString(0);
	}

	public abstract String toString(int indent);
	
	final SmartStringBuilder toStringBuilder(final int indent)
	{
		String type = this.getType().toString().toLowerCase();
		
		return new SmartStringBuilder().indent(indent).append(type).appendln(':').tab();
	}
	
	public final boolean hasOriginalNode()
	{
		return this.getOriginalNode() != null;
	}

	public abstract N getOriginalNode();
	
	public final ASTNode getOriginalNodeRoot()
	{
		return Nodes.root(this.getOriginalNode());
	}

	public final ASTNode getOriginalNodeParent()
	{
		return Nodes.parent(this.getOriginalNode());
	}

	public final NodeType getOriginalNodeType()
	{
		return Nodes.toType(this.getOriginalNode());
	}

	public final boolean hasRevisedNode()
	{
		return this.getRevisedNode() != null;
	}

	public abstract N getRevisedNode();
	
	public final ASTNode getRevisedNodeRoot()
	{
		return Nodes.root(this.getRevisedNode());
	}

	public final ASTNode getRevisedNodeParent()
	{
		return Nodes.parent(this.getRevisedNode());
	}

	public final NodeType getRevisedNodeType()
	{
		return Nodes.toType(this.getRevisedNode());
	}
	
	public abstract NodeDeltaType getType();
}

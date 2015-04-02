package net.trackmate.graph.algorithm;

import java.util.Collections;
import java.util.Comparator;

import net.trackmate.graph.Edge;
import net.trackmate.graph.Graph;
import net.trackmate.graph.Vertex;
import net.trackmate.graph.collection.RefList;

public abstract class AbstractGraphSortedIteratorAlgorithm< V extends Vertex< E >, E extends Edge< V > > extends AbstractGraphIteratorAlgorithm< V, E >
{
	private final Comparator< V > comparator;

	private final RefList< V > list;

	public AbstractGraphSortedIteratorAlgorithm( final Graph< V, E > graph, final Comparator< V > comparator )
	{
		super( graph );
		this.comparator = comparator;
		this.list = createVertexList();
	}

	@Override
	protected void fetchNext()
	{
		while ( canFetch() )
		{
			fetched = fetch( fetched );
			list.clear();
			for ( final E e : neighbors( fetched ) )
			{
				final V target = e.getTarget( tmpRef );
				if ( !visited.contains( target ) )
				{
					visited.add( target );
					list.add( target );
				}
			}

			Collections.sort( list, comparator );
			// To have right order when pop from stack:
			for ( int i = 0; i < list.size(); i++ )
			{
				final V target = list.get( i );
				toss( target );
			}
			return;
		}
		releaseRef( tmpRef );
		releaseRef( fetched );
		fetched = null;
	}
}

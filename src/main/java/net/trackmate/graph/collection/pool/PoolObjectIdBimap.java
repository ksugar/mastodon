package net.trackmate.graph.collection.pool;

import net.trackmate.graph.collection.IdBimap;
import net.trackmate.graph.zzrefcollections.PoolObject;
import net.trackmate.graph.zzrefcollections.Ref;
import net.trackmate.graph.zzrefcollections.RefPool;

/**
 * Bidirectional mapping between {@link PoolObject}s and their internal pool
 * indices.
 *
 * @param <O>
 *            the {@link PoolObject} type.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
// TODO rename RefIdBimap
public class PoolObjectIdBimap< O extends Ref< O > > implements IdBimap< O >
{
	private final RefPool< O > pool;

	public PoolObjectIdBimap( final RefPool< O > pool )
	{
		this.pool = pool;
	}

	@Override
	public int getId( final O o )
	{
		return o.getInternalPoolIndex();
	}

	@Override
	public O getObject( final int id, final O ref )
	{
		pool.getByInternalPoolIndex( id, ref );
		return ref;
	}

	@Override
	public O createRef()
	{
		return pool.createRef();
	}

	@Override
	public void releaseRef( final O ref )
	{
		pool.releaseRef( ref );
	}
}

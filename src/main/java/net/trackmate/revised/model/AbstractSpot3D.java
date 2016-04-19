package net.trackmate.revised.model;

import static net.trackmate.graph.mempool.ByteUtils.DOUBLE_SIZE;
import static net.trackmate.graph.mempool.ByteUtils.INT_SIZE;

import java.util.Map;

import net.imglib2.RealLocalizable;
import net.trackmate.graph.AbstractEdge;
import net.trackmate.graph.AbstractVertex;
import net.trackmate.graph.AbstractVertexPool;
import net.trackmate.graph.mempool.ByteMappedElement;
import net.trackmate.graph.mempool.MappedElement;
import net.trackmate.spatial.HasTimepoint;

/**
 * Base class for specialized vertices that are part of a graph, and are used to
 * store spatial and temporal location.
 * <p>
 * The class ships the minimal required feature, that is X, Y, Z, time-point,
 * and radius.
 *
 * @author Jean-Yves Tinevez
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 *
 * @param <V>
 *            the recursive type of the concrete implementation.
 * @param <E>
 *            associated edge type
 * @param <T>
 *            the MappedElement type, for example {@link ByteMappedElement}.
 */
public class AbstractSpot3D<
		V extends AbstractSpot3D< V, E, T >,
		E extends AbstractEdge< E, ?, ? >,
		T extends MappedElement >
	extends AbstractVertex< V, E, T >
	implements RealLocalizable, HasTimepoint
{
	protected static final int X_OFFSET = AbstractVertex.SIZE_IN_BYTES;
	protected static final int Y_OFFSET = X_OFFSET + DOUBLE_SIZE;
	protected static final int Z_OFFSET = Y_OFFSET + DOUBLE_SIZE;
	protected static final int TP_OFFSET = Z_OFFSET + DOUBLE_SIZE;
	protected static final int SIZE_IN_BYTES = TP_OFFSET + INT_SIZE;

	@Override
	protected void setToUninitializedState()
	{
		super.setToUninitializedState();
	}

	public double getX()
	{
		return access.getDouble( X_OFFSET );
	}

	protected void setX( final double x )
	{
		access.putDouble( x, X_OFFSET );
	}

	public double getY()
	{
		return access.getDouble( Y_OFFSET );
	}

	protected void setY( final double y )
	{
		access.putDouble( y, Y_OFFSET );
	}

	public double getZ()
	{
		return access.getDouble( Z_OFFSET );
	}

	protected void setZ( final double z )
	{
		access.putDouble( z, Z_OFFSET );
	}

	protected int getTimepointId()
	{
		return access.getInt( TP_OFFSET );
	}

	protected void setTimepointId( final int tp )
	{
		access.putInt( tp, TP_OFFSET );
	}

	@Override
	public int getTimepoint()
	{
		return getTimepointId();
	}

	private final AbstractModel< ?, V, ? > model;

	private final Map< VertexFeature< ?, V, ? >, FeatureValue< ? > > featureValues;

	@SuppressWarnings( "unchecked" )
	public < F extends FeatureValue< ? >, M > F feature( final VertexFeature< M, V, F > feature )
	{
		F fv = ( F ) featureValues.get( feature );
		if ( fv == null )
		{
			fv = feature.createFeatureValue( model.getVertexFeature( feature ), ( V ) this );
			featureValues.put( feature, fv );
		}
		return fv;
	}

	protected AbstractSpot3D( final AbstractVertexPool< V, E, T > pool, final AbstractModel< ?, V, ? > model )
	{
		super( pool );
		this.model = model;
		featureValues = new UniqueHashcodeArrayMap<>();
	}

	// === RealLocalizable ===

	@Override
	public int numDimensions()
	{
		return 3;
	}

	@Override
	public void localize( final float[] position )
	{
		position[ 0 ] = ( float ) getX();
		position[ 1 ] = ( float ) getY();
		position[ 2 ] = ( float ) getZ();
	}

	@Override
	public void localize( final double[] position )
	{
		position[ 0 ] = getX();
		position[ 1 ] = getY();
		position[ 2 ] = getZ();
	}

	@Override
	public float getFloatPosition( final int d )
	{
		return ( float ) getDoublePosition( d );
	}

	@Override
	public double getDoublePosition( final int d )
	{
		return ( d == 0 ) ? getX() : ( ( d == 1 ) ? getY() : getZ() );
	}
}

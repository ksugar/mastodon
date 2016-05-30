package net.trackmate.collection.util;

import static net.trackmate.collection.RefCollections.wrap;
import static net.trackmate.collection.RefCollections.wrapAsStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import net.trackmate.collection.IntRefMap;
import net.trackmate.collection.RefCollection;
import net.trackmate.collection.RefDeque;
import net.trackmate.collection.RefDoubleMap;
import net.trackmate.collection.RefIntMap;
import net.trackmate.collection.RefList;
import net.trackmate.collection.RefObjectMap;
import net.trackmate.collection.RefRefMap;
import net.trackmate.collection.RefSet;
import net.trackmate.collection.RefStack;
import net.trackmate.collection.wrap.IntRefMapWrapper;
import net.trackmate.collection.wrap.RefDoubleMapWrapper;
import net.trackmate.collection.wrap.RefIntMapWrapper;
import net.trackmate.graph.ReadOnlyGraph;

/**
 * Static utility methods to create {@link RefCollection}s of vertices and edges
 * of a {@link ReadOnlyGraph}.
 * <p>
 * If the graph implements interfaces for providing specific
 * {@link RefCollection} implementations (e.g., {@link ListCreator}) these
 * specific implementations are used. Otherwise, standard {@code java.util}
 * {@link Collections} are created and wrapped as {@link RefCollection}.
 *
 * @author Tobias Pietzsch &lt;tobias.pietzsch@gmail.com&gt;
 */
public class CollectionUtils
{
	public static interface SetCreator< O > extends RefCollection< O >
	{
		public RefSet< O > createRefSet();

		public RefSet< O > createRefSet( final int initialCapacity );
	}

	public static interface ListCreator< O > extends RefCollection< O >
	{
		public RefList< O > createRefList();

		public RefList< O > createRefList( final int initialCapacity );
	}

	public static interface DequeCreator< O > extends RefCollection< O >
	{
		public RefDeque< O > createRefDeque();

		public RefDeque< O > createRefDeque( final int initialCapacity );
	}

	public static interface StackCreator< O > extends RefCollection< O >
	{
		public RefStack< O > createRefStack();

		public RefStack< O > createRefStack( final int initialCapacity );
	}

	public static interface MapCreator< O > extends RefCollection< O >
	{
		public < T > RefObjectMap< O, T > createRefObjectMap();

		public < T > RefObjectMap< O, T > createRefObjectMap( final int initialCapacity );

		public RefRefMap< O, O > createRefRefMap();

		public RefRefMap< O, O > createRefRefMap( final int initialCapacity );

		public RefIntMap< O > createRefIntMap( final int noEntryValue );

		public RefIntMap< O > createRefIntMap( final int noEntryValue, final int initialCapacity );

		public IntRefMap< O > createIntRefMap( final int noEntryKey );

		public IntRefMap< O > createIntRefMap( final int noEntryKey, final int initialCapacity );

		public RefDoubleMap< O > createRefDoubleMap( final double noEntryValue );

		public RefDoubleMap< O > createRefDoubleMap( final double noEntryValue, final int initialCapacity );
	}

	public static interface CollectionCreator< O > extends
			SetCreator< O >,
			ListCreator< O >,
			DequeCreator< O >,
			StackCreator< O >,
			MapCreator< O >
	{}

	public static < O > RefSet< O > createRefSet( final RefCollection< O > collection )
	{
		if ( collection instanceof SetCreator )
			return ( ( SetCreator< O > ) collection ).createRefSet();
		else
			return wrap( new HashSet< O >() );
	}

	public static < O > RefSet< O > createRefSet( final RefCollection< O > collection, final int initialCapacity )
	{
		if ( collection instanceof SetCreator )
			return ( ( SetCreator< O > ) collection ).createRefSet( initialCapacity );
		else
			return wrap( new HashSet< O >( initialCapacity ) );
	}

	public static < O > RefList< O > createRefList( final RefCollection< O > collection )
	{
		if ( collection instanceof ListCreator )
			return ( ( ListCreator< O > ) collection ).createRefList();
		else
			return wrap( new ArrayList< O >() );
	}

	public static < O > RefList< O > createRefList( final RefCollection< O > collection, final int initialCapacity )
	{
		if ( collection instanceof ListCreator )
			return ( ( ListCreator< O > ) collection ).createRefList( initialCapacity );
		else
			return wrap( new ArrayList< O >( initialCapacity ) );
	}

	public static < O > RefDeque< O > createRefDeque( final RefCollection< O > collection )
	{
		if ( collection instanceof DequeCreator )
			return ( ( DequeCreator< O > ) collection ).createRefDeque();
		else
			return wrap( new ArrayDeque< O >() );
	}

	public static < O > RefDeque< O > createRefDeque( final RefCollection< O > collection, final int initialCapacity )
	{
		if ( collection instanceof DequeCreator )
			return ( ( DequeCreator< O > ) collection ).createRefDeque( initialCapacity );
		else
			return wrap( new ArrayDeque< O >( initialCapacity ) );
	}

	public static < O > RefStack< O > createRefStack( final RefCollection< O > collection )
	{
		if ( collection instanceof StackCreator )
			return ( ( StackCreator< O > ) collection ).createRefStack();
		else
			return wrapAsStack( new ArrayDeque< O >() );
	}

	public static < O > RefStack< O > createRefStack( final RefCollection< O > collection, final int initialCapacity )
	{
		if ( collection instanceof StackCreator )
			return ( ( StackCreator< O > ) collection ).createRefStack( initialCapacity );
		else
			return wrapAsStack( new ArrayDeque< O >( initialCapacity ) );
	}

	public static < O, T > RefObjectMap< O, T > createRefObjectMap( final RefCollection< O > collection )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefObjectMap();
		else
			return wrap( new HashMap< O, T >() );
	}

	public static < O, T > RefObjectMap< O, T > createRefObjectMap( final RefCollection< O > collection, final int initialCapacity )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefObjectMap( initialCapacity );
		else
			return wrap( new HashMap< O, T >( initialCapacity ) );
	}

	public static < O > RefRefMap< O, O > createRefRefMap( final RefCollection< O > collection )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefRefMap();
		else
			return wrap( new HashMap< O, O >() );
	}

	public static < O > RefRefMap< O, O > createRefRefMap( final RefCollection< O > collection, final int initialCapacity )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefRefMap( initialCapacity );
		else
			return wrap( new HashMap< O, O >( initialCapacity ) );
	}

	public static < O > RefIntMap< O > createRefIntMap( final RefCollection< O > collection, final int noEntryValue )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefIntMap( noEntryValue );
		else
			return new RefIntMapWrapper< O >( noEntryValue );
	}

	public static < O > RefIntMap< O > createRefIntMap( final RefCollection< O > collection, final int noEntryValue, final int initialCapacity )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefIntMap( noEntryValue, initialCapacity );
		else
			return new RefIntMapWrapper< O >( noEntryValue, initialCapacity );
	}

	public static < O > RefDoubleMap< O > createRefDoubleMap( final RefCollection< O > collection, final double noEntryValue )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefDoubleMap( noEntryValue );
		else
			return new RefDoubleMapWrapper< O >( noEntryValue );
	}

	public static < O > RefDoubleMap< O > createRefDoubleMap( final RefCollection< O > collection, final double noEntryValue, final int initialCapacity )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createRefDoubleMap( noEntryValue, initialCapacity );
		else
			return new RefDoubleMapWrapper< O >( noEntryValue, initialCapacity );
	}
	public static < O > IntRefMap< O > createIntRefMap( final RefCollection< O > collection, final int noEntryKey )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createIntRefMap( noEntryKey );
		else
			return new IntRefMapWrapper< O >( noEntryKey );
	}

	public static < O > IntRefMap< O > createIntRefMap( final RefCollection< O > collection, final int noEntryKey, final int initialCapacity )
	{
		if ( collection instanceof MapCreator )
			return ( ( MapCreator< O > ) collection ).createIntRefMap( noEntryKey, initialCapacity );
		else
			return new IntRefMapWrapper< O >( noEntryKey, initialCapacity );
	}
}

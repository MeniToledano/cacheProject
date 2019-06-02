package com.hit.memory;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;

public class CacheUnitTest {

	@Test
	public void testCacheUnit() {
		IAlgoCache<java.lang.Long, DataModel<Integer>> cache = new LRUAlgoCacheImpl<>(3);

		java.lang.Long[] ids = { (long) 1, (long) 2, (long) 3 };

		CacheUnit<Integer> unit = new CacheUnit<>(cache);

		//@SuppressWarnings("unchecked")
		DataModel<Integer> a[] = new DataModel[3];
		@SuppressWarnings("unchecked")
		DataModel<Integer> b[] = new DataModel[3];
		a[0] = new DataModel<Integer>(ids[0], 1);
		a[1] = new DataModel<Integer>(ids[1], 2);
		a[2] = new DataModel<Integer>(ids[2], 3);
		b[0] = null;
		b[1] = null;
		b[2] = null;
		Assert.assertArrayEquals(unit.putDataModels(a), b);

		b[0] = a[0];
		b[1] = a[1];
		b[2] = a[2];
		Assert.assertArrayEquals(unit.getDataModels(ids), b);

		java.lang.Long[] ids2 = { (long) 2 };
		unit.removeDataModels(ids2);
		b[1] = null;
		Assert.assertArrayEquals(unit.getDataModels(ids), b);
	}

	@Test
	public void testCacheUnit2() {
		IAlgoCache<java.lang.Long, DataModel<Integer>> cache = new LRUAlgoCacheImpl<>(2);

		java.lang.Long[] ids = { (long) 1, (long) 2, (long) 3 };

		CacheUnit<Integer> unit = new CacheUnit<>(cache);

		@SuppressWarnings("unchecked")
		DataModel<Integer> a[] = new DataModel[3];
		@SuppressWarnings("unchecked")
		DataModel<Integer> b[] = new DataModel[3];
		a[0] = new DataModel<Integer>(ids[0], 1);
		a[1] = new DataModel<Integer>(ids[1], 2);
		a[2] = new DataModel<Integer>(ids[2], 3);
		b[0] = null;
		b[1] = null;
		b[2] = a[0];
		Assert.assertArrayEquals(unit.putDataModels(a), b);

		b[0] = null;
		b[1] = a[1];
		b[2] = a[2];
		Assert.assertArrayEquals(unit.getDataModels(ids), b);

	}

	
	@Test
	public void testDaoFileImpl() throws FileNotFoundException, IOException, ClassNotFoundException  {
		
		
	    Path filePath=Paths.get(System.getProperty("user.dir"),("src/main/resources/DataSource.txt"));

		DaoFileImpl<String> file = new DaoFileImpl<String>(filePath.toString(), 1);
		DataModel<String> DM1=new DataModel<>((long) 1, "Hi");
		DataModel<String> DM2=new DataModel<>((long) 2, "Bye");
		
		file.read();        //empty
		file.save(DM1); 
		file.read();
		file.save(DM2);
		file.read();
		
		Assert.assertEquals(file.find((long) 3), null);
		Assert.assertEquals(file.find((long) 2), DM2 );
		file.delete(DM2);
		Assert.assertEquals(file.find((long) 2), null );
		file.delete(DM1);

	}
	

	

}

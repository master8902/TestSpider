package com;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.Environment;

public abstract class AbstractFrontier {
	
	private Environment env;
	private static final String CLASS_CATLOG = "java_class_catalog";
	private StoredClassCatalog javaCatalog;
	protected Database catalogdatabase;
	protected Database databse;
	

}

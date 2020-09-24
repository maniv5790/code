package c1x;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.storage.StorageLevel;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.*;



public class spark_read_json {
	
	public static String file_date;
	  public static void main(String[] args) {

		  file_date = args[0];
	SparkSession spark = SparkSession.builder()
	        .appName("JSON")
	        .master("local")
	        .getOrCreate();
	    
		System.out.println(spark);
		String jsonPath = "D:\\WorkSpace\\c1x\\"+file_date+"\\";
		
		
        // read JSON file to Dataset
		Dataset<Row> git_data = spark.read().json(jsonPath);	
		//git_data.printSchema();
		//git_data.createOrReplaceTempView("data");
	
		//Dataset<Row> namesDF = spark.sql("SELECT * FROM data limit 10");
		//namesDF.show();
		
		//Dataset<Row> appName = git_data.select("id","type","actor.login","public");
		Dataset<Row> appName = git_data
				.withColumn("login", col("actor.login"))
				.withColumn("datetime", regexp_replace(col("created_at"), "[TZ]", " "))
				.withColumn("performed_via_github_app", col("payload.comment.performed_via_github_app.id"))
				.withColumn("public",col("public").cast(DataTypes.StringType)).persist(StorageLevel.MEMORY_AND_DISK());
        appName.printSchema();
        
        //to get the event counts per day to see which events is performed more on daily basis 
        appName.select("type").groupBy("type").agg( count(col("type")).alias("count")).orderBy(desc("count")).coalesce(1).write().option("header","true").mode("overwrite").csv("D:\\WorkSpace\\c1x\\"+file_date+"\\grp1\\output\\");
        //to get the event counts per hour to see which events is performed more on hour basis 
        appName.select("type","datetime").groupBy("type","datetime").agg( count(col("datetime")).alias("count")).orderBy(desc("type")).coalesce(1).write().option("header","true").mode("overwrite").csv("D:\\WorkSpace\\c1x\\"+file_date+"\\grp2\\output\\");

	  }
}

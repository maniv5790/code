package c1x;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.storage.StorageLevel;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.*;
import org.apache.spark.sql.functions.*;



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
				.withColumn("date", split(col("created_at"), "T").getItem(0).cast(DataTypes.StringType))
				.withColumn("time_hr", substring(split(col("created_at"), "T").getItem(1),0,2))
				.withColumn("performed_via_github_app", col("payload.comment.performed_via_github_app.id"))
				.withColumn("public",col("public").cast(DataTypes.StringType))
				.withColumn("actor_id", col("actor.id"))
				.withColumn("event",col("payload.action"))
				.withColumn("organization",split(col("payload.member.organizations_url"),"/").getItem(4))
				.persist();
        //appName.printSchema(); StorageLevel.MEMORY_AND_DISK()
        
        //to get the event counts per day to see which events is performed more on daily basis 
        appName.select("type").groupBy("type").agg( count(col("type")).alias("count")).orderBy(desc("count")).coalesce(1).write().option("header","true").mode("overwrite").csv("D:\\WorkSpace\\c1x\\"+file_date+"\\grp1\\output\\");
        //to get the event counts per hour to see which events is performed more on hour basis 
        appName.select("type","date","time_hr").groupBy("type","date","time_hr").agg( count(col("time_hr")).alias("count")).orderBy(asc("type"),asc("date"),asc("time_hr")).coalesce(1).write().option("header","true").mode("overwrite").csv("D:\\WorkSpace\\c1x\\"+file_date+"\\grp2\\output\\");
        //Issues - Opened, Closed, Reopened
       appName.select("event").filter(col("type").contains("IssuesEvent")).groupBy("event").agg( count(col("event")).alias("count")).orderBy(desc("event")).coalesce(1).write().option("header","true").mode("overwrite").csv("D:\\WorkSpace\\c1x\\"+file_date+"\\grp3\\output\\");
	   //Organization Name from URL - Top 20 Organization
       appName.select(col("organization")).filter(col("organization").isNotNull()).groupBy(col("organization")).agg( count(col("organization")).alias("count")).orderBy(desc("count")).coalesce(1).limit(20).write().option("header","true").mode("overwrite").csv("D:\\WorkSpace\\c1x\\"+file_date+"\\grp4\\output\\");

        
	  }
}

package com.jacobs.spark

import org.apache.spark.ml.feature.{Interaction, OneHotEncoderEstimator, StringIndexer, VectorAssembler}
import org.apache.spark.sql.SparkSession

/**
  * @author lichao
  *         Created on 2019-05-27
  */
object InteractionExamples {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("InteractionExample")
      .getOrCreate()

    //    interactionDemo1(spark)
    interactionDemo2(spark)

    spark.stop()
  }

  /**
    * +---+--------------+--------------+------------------------------------------------------+
    * |id1|vec1          |vec2          |interactedCol                                         |
    * +---+--------------+--------------+------------------------------------------------------+
    * |1  |[1.0,2.0,3.0] |[8.0,4.0,5.0] |[8.0,4.0,5.0,16.0,8.0,10.0,24.0,12.0,15.0]            |
    * |2  |[4.0,3.0,8.0] |[7.0,9.0,8.0] |[56.0,72.0,64.0,42.0,54.0,48.0,112.0,144.0,128.0]     |
    * |3  |[6.0,1.0,9.0] |[2.0,3.0,6.0] |[36.0,54.0,108.0,6.0,9.0,18.0,54.0,81.0,162.0]        |
    * |4  |[10.0,8.0,6.0]|[9.0,4.0,5.0] |[360.0,160.0,200.0,288.0,128.0,160.0,216.0,96.0,120.0]|
    * |5  |[9.0,2.0,7.0] |[10.0,7.0,3.0]|[450.0,315.0,135.0,100.0,70.0,30.0,350.0,245.0,105.0] |
    * |6  |[1.0,1.0,4.0] |[2.0,8.0,4.0] |[12.0,48.0,24.0,12.0,48.0,24.0,48.0,192.0,96.0]       |
    * +---+--------------+--------------+------------------------------------------------------+
    *
    * @param spark
    */
  private def interactionDemo1(spark: SparkSession) = {
    // $example on$
    val df = spark.createDataFrame(Seq(
      (1, 1, 2, 3, 8, 4, 5),
      (2, 4, 3, 8, 7, 9, 8),
      (3, 6, 1, 9, 2, 3, 6),
      (4, 10, 8, 6, 9, 4, 5),
      (5, 9, 2, 7, 10, 7, 3),
      (6, 1, 1, 4, 2, 8, 4)
    )).toDF("id1", "id2", "id3", "id4", "id5", "id6", "id7")

    val assembler1 = new VectorAssembler().
      setInputCols(Array("id2", "id3", "id4")).
      setOutputCol("vec1")

    val assembled1 = assembler1.transform(df)

    val assembler2 = new VectorAssembler().
      setInputCols(Array("id5", "id6", "id7")).
      setOutputCol("vec2")

    val assembled2 = assembler2.transform(assembled1).select("id1", "vec1", "vec2")

    val interaction = new Interaction()
      .setInputCols(Array("id1", "vec1", "vec2"))
      .setOutputCol("interactedCol")

    val interacted = interaction.transform(assembled2)

    interacted.show(truncate = false)
  }

  /**
    * +-------+------+
    * |name   |length|
    * +-------+------+
    * |Alberto|2     |
    * |Dakota |2     |
    * |test1  |1     |
    * |test2  |5     |
    *
    * +-------+------+----------+-------------+
    * |name   |length|name_index|encoded_name |
    * +-------+------+----------+-------------+
    * |Alberto|2     |2.0       |(3,[2],[1.0])|
    * |Dakota |2     |3.0       |(3,[],[])    |
    * |test1  |1     |1.0       |(3,[1],[1.0])|
    * |test2  |5     |0.0       |(3,[0],[1.0])|
    * +-------+------+----------+-------------+
    *
    * +-------+------+----------+-------------+-------------+
    * |name   |length|name_index|encoded_name |comb_age_city|
    * +-------+------+----------+-------------+-------------+
    * |Alberto|2     |2.0       |(3,[2],[1.0])|[0.0,0.0,2.0]|
    * |Dakota |2     |3.0       |(3,[],[])    |(3,[],[])    |
    * |test1  |1     |1.0       |(3,[1],[1.0])|[0.0,1.0,0.0]|
    * |test2  |5     |0.0       |(3,[0],[1.0])|[5.0,0.0,0.0]|
    * +-------+------+----------+-------------+-------------+
    *
    * @param spark
    */
  private def interactionDemo2(spark: SparkSession): Unit = {
    val df = spark.createDataFrame(Seq(
      ("Alberto", 0),
      ("Dakota", 2),
      ("test1", 1),
      ("test2", 5)
    )).toDF("name", "length")

    df.show(truncate = false)

    //    indexer =new StringIndexer(inputCol="name", outputCol="name_index",
    //      handleInvalid="skip", stringOrderType="alphabetAsc")
    //    df1 = indexer.fit(df1).transform(df1)
    //    df1.show()
    //    encoder = OneHotEncoderEstimator(inputCols=["name_index"], handleInvalid="error", outputCols=["encoded_name"],
    //    dropLast=True)

    val stringIndexer = new StringIndexer()
      .setInputCol("name")
      .setOutputCol("name_index")
      .setStringOrderType("alphabetAsc")

    val stringEncodedDf = stringIndexer.fit(df).transform(df)
    val oneHotEncoderEstimator = new OneHotEncoderEstimator().setInputCols(Array("name_index"))
      .setOutputCols(Array("encoded_name"))

    val oneHotEncodedDf = oneHotEncoderEstimator.fit(stringEncodedDf).transform(stringEncodedDf)
    oneHotEncodedDf.show(truncate = false)
    val interaction = new Interaction().setInputCols(Array("length", "encoded_name")).setOutputCol("comb_age_city")
    val interacted = interaction.transform(oneHotEncodedDf)

    interacted.show(truncate = false)
  }
}
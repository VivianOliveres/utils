# Logs configuration

## Table of Contents

1. Log4J2 Alone
2. Log4J2 over Slf4J
3. Logback over Slf4J
4. Spark

## 1. Log4J2 Alone

The file named `log4j2.xml` will automatically be loaded if it is in the classpath.
Else it can be forced with the VM parameter: `-Dlog4j2.configurationFile=path/to/log4j2.xml`.
Alternatively, the parameter `-Dlog4j2.configuration=path/to/log4j2.xml` can also be used but is deprecated.

Finally, all this dependencies are needed
```sbt
  libraryDependencies ++= Seq(
      "org.apache.logging.log4j" % "log4j-api" % LOG4J_VERSION,
      "org.apache.logging.log4j" % "log4j-core" % LOG4J_VERSION
    )
```

## 2. Log4J2 over Slf4J

It works the same way as Log4J2 alone for the configuration file (ie directly included for `log4j2.xml` or use `-Dlog4j2.configurationFile=`

But dependencies are different
```sbt
      libraryDependencies ++= Seq(
          "org.apache.logging.log4j" % "log4j-api" % LOG4J_VERSION,
          "org.apache.logging.log4j" % "log4j-core" % LOG4J_VERSION,
          "org.apache.logging.log4j" % "log4j-slf4j-impl" % LOG4J_VERSION
        )
```

And the Scala code:
```scala
val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)
```

## 3. Logback over Slf4J

This is very similar than log4j2.
If the file `logback.xml` is in the classpath, it will be automatically loaded.
Else, it can be forced with the VM parameter: `-Dlogback.configurationFile=path/to/logback.xml` or alternatively with `-Dlogback.configuration=path/to/logback.xml`

Dependencies will be (it natively implements Slf4J):
```sbt
      libraryDependencies ++= Seq(
        "ch.qos.logback" % "logback-classic" % LOGBACK_VERSION,
        "ch.qos.logback" % "logback-core" % LOGBACK_VERSION
    )
```

Also notes than `log4j-slf4j-impl` should not be present in the classpath as it will collide with logback.

## 4. Spark

Same than before. But the configuration change with the spark-submit process:
```bash
spark-submit \
    --master yarn \
    --deploy-mode cluster \
    --conf "spark.driver.extraJavaOptions=-Dlog4j2.configuration=file:log4j2.xml" \
    --conf "spark.executor.extraJavaOptions=-Dlog4j2.configuration=file:log4j2.xml" \
    --files "/absolute/path/to/your/log4j2.xml" \
    --class com.github.atais.Main \
    "SparkApp.jar"
```

Note the `file:` prefix in the `--conf` parameter.
Also, the `--files` parameter is needed to send the configuration file to the cluster.

See this [link](https://stackoverflow.com/questions/27781187/how-to-stop-info-messages-displaying-on-spark-console/55596389#55596389) for more information.

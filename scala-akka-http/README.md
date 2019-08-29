# Akka HTTP
Useful informations about Akka-Http

**Doc**: https://doc.akka.io/docs/akka-http/current/introduction.html

# Download with Circe
**Circe version**: https://mvnrepository.com/artifact/io.circe/circe-core
**Akka Http version**: https://mvnrepository.com/artifact/com.typesafe.akka/akka-http
```scala
val circeVersion = "0.11.1"
val akkaHttpVersion = "10.1.9"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser "% circeVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
)
```

# Test with HTTPie
```bash
http :8888/version

http :8888/users

http :8888/users age==21

http :8888/users/toto
http :8888/users/titi

http post :8888/users name="tutu" age:=33

http put :8888/users name="tutu" age:=34

http delete :8888/users/toto
```
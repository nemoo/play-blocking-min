Minimal Repo to enable blocking-slick with Scala 3
==================

1. Install [Java JDK 11](https://adoptopenjdk.net/).
2. Install [SBT](http://www.scala-sbt.org/download.html)
3. Start a local postgres db via `docker run -it -p 5432:5432 -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=playslickexample postgres`
3. Run `sbt ~run` for continuous recompilation of the server app.

Done: [http://localhost:9000/](http://localhost:9000/)

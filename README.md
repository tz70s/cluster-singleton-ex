# cluster-singleton-ex
Akka cluster singleton experiment.

## Execution

```bash
# For each terminal
env JAVA_OPTS="-Dakka.remote.netty.tcp.port=2551" sbt run
env JAVA_OPTS="-Dakka.remote.netty.tcp.port=2552" sbt run
env JAVA_OPTS="-Dakka.remote.netty.tcp.port=2553" sbt run
# ... spawn any number as you wish; and close each one for observing singleton migration!
```

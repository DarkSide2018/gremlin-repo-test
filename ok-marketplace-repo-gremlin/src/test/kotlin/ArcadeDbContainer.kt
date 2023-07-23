package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import java.time.Duration

object ArcadeDbContainer {
    val username: String = "root"
    val password: String = "root_root"
    val container: GenericContainer<*> = createContainer()
    init {
        container.waitingFor(Wait.forLogMessage(".*ArcadeDB Server started.*\\n", 1))
        container.withStartupTimeout(Duration.ofMinutes(5))
        container.withAccessToHost(true)
        container.isHostAccessible = true
        container.withReuse(true)
        container.start()
        println("ARCADE: http://${container.host}:${container.getMappedPort(2480)}")
        println("ARCADE: http://${container.host}:${container.getMappedPort(2424)}")
        println(container.logs)
        println("RUNNING?: ${container.isRunning}")
    }
    fun createContainer(): GenericContainer<*> {
       return GenericContainer(DockerImageName.parse("arcadedata/arcadedb:latest"))
            .withExposedPorts(2480, 2424, 8182)
            .withEnv("arcadedb.server.plugins", "GremlinServer:com.arcadedb.server.gremlin.GremlinServerPlugin")
            .withEnv(
                "JAVA_OPTS", "-Darcadedb.server.rootPassword=$password " +
                        "-Darcadedb.server.plugins=GremlinServer:com.arcadedb.server.gremlin.GremlinServerPlugin"
            )
    }
}


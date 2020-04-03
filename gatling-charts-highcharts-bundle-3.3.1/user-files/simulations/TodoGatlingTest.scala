import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

/**
 * Performance test for the Todo entity.
 */
class TodoGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val scn = scenario("Test the Todo entity")
        .repeat(100) {
            exec(http("Create new todo")
                .post("/todo")
                .body(StringBody(
                    """{
                "id":null
                , "description":"SAMPLE_TEXT"
                , "details":"SAMPLE_TEXT"
                , "done":null
                }""")).asJson
                .check(status.is(201)))
                .exec(http("Get all todos")
                    .get("/todo")
                    .check(status.is(200)))
                .pause(1 seconds, 2 seconds)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 10000)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}

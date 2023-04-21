import cats.effect.IOApp
import cats.effect.{IO, Resource}
import skunk.Session
import skunk.implicits.*
import skunk.codec.all.*
import skunk.*
import com.comcast.ip4s.Literals.host
import natchez.Trace.Implicits.noop
import org.typelevel.log4cats.Logger
import cats.syntax.all.*
import org.typelevel.log4cats.slf4j.Slf4jLogger

object SkunkDemo extends IOApp.Simple:

  type Pool = Resource[IO, Session[IO]]
  type SessionPool = Resource[IO, Pool]

  given Logger[IO] = Slf4jLogger.getLogger

  private def checkPostgresConnection(pool: Pool): IO[Unit] =
    pool.use { session =>
      session
        .unique(sql"select version();".query(text))
        .flatMap { v =>
          Logger[IO].info(s"Connected to Postgres $v")
        }
    }

  def createSessionPool() =
    Session
      .pooled[IO](
        host = "localhost",
        port = 5432,
        user = "postgres",
        password = Some("pass1234"),
        database = "demodb",
        max = 10
      )
      .evalTap(checkPostgresConnection)

  val countryQuery: Query[Void, String] =
    sql"SELECT name FROM country".query(varchar)

  def getCountries(session: Session[IO]): IO[List[String]] =
    session.execute(countryQuery)

  def run: IO[Unit] =
    val pool = createSessionPool()
    IO.pure(())

package part2effects

import cats.effect.IO
import scala.io.StdIn
import cats.effect.IOApp
import cats.effect.ExitCode

object IOApps:
    val program = for
        line <- IO(StdIn.readLine())
        _ <- IO(println(s"You are written: $line"))
    yield ()

object TestApp:
    import IOApps.*

    def main(args: Array[String]): Unit =
        import cats.effect.unsafe.implicits.global
        program.unsafeRunSync()

object FirstCEApp extends IOApp:
    import IOApps.*
    
    override def run(args: List[String]): IO[ExitCode] =
        program.as(ExitCode.Success)

object MySimpleApp extends IOApp.Simple:
    import IOApps.*

    override def run: IO[Unit] = program
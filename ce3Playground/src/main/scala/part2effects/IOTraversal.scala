package part2effects

import cats.effect.IOApp
import scala.util.Random
import scala.concurrent.Future
import cats.effect.IO
import cats.instances.future


object IOTraversal extends IOApp.Simple:

    import scala.concurrent.ExecutionContext.Implicits.global

    def heavyComputation(string: String): Future[Int] =
        Future {
            Thread.sleep(Random.nextInt(1000))
            string.length()
        }

    val workload: List[String] = List("I quite like CE", "Scala is great", "Some other irrelevant sentence")

    def clunkyFutures(): Unit =
        val futures: List[Future[Int]] = workload.map(heavyComputation)
        // Future[List[Int]] would be hard to obtain
        futures.foreach(_.foreach(println))

    // traverse
    import cats.Traverse
    import cats.instances.list._
    val listTraverse = Traverse[List]

    def traverseFutures(): Unit =
        val consolidatedResult: Future[List[Int]] = listTraverse.traverse(workload)(heavyComputation)
        //  ^^ this stores all the result
        consolidatedResult.foreach(println)

    import utils.DebugUtils.*

    // traverse for IO
    def computeAsIO(string: String): IO[Int] = IO {
        Thread.sleep(1000)
        string.split(" ").length
    }.debugLog

    val ios: List[IO[Int]] = workload.map(computeAsIO)
    val singleIO: IO[List[Int]] = listTraverse.traverse(workload)(computeAsIO)

    // parallel traversal
    import cats.syntax.parallel._ // parTraverse extension method
    val parallelSingleIO: IO[List[Int]] = workload.parTraverse(computeAsIO)

    /**
      * Exercises
      */
      def sequence[A](listOfIOs: List[IO[A]]): IO[List[A]] = ???

      // hard version
      def sequence_v2[F[_] : Traverse, A](listOfIOs: F[IO[A]]): IO[F[A]] = ???

      // parallel version
      def parSequence[A](listOfIOs: List[IO[A]]): IO[List[A]] = ???

      // hard
      def parSequence_v2[F[_]: Traverse, A](listOfIOs: F[IO[A]]): IO[F[A]] = ???

    override def run: IO[Unit] =
        parallelSingleIO.map(_.sum).debugLog.void
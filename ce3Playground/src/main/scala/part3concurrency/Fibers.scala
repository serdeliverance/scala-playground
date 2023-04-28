package part3concurrency

import cats.effect.IOApp
import cats.effect.{IO, Fiber}

import utils.DebugUtils.*

object Fibers extends IOApp.Simple:

    val meaningOfLife = IO.pure(42)
    val favLang = IO.pure("Scala")

    // this computation runs in the same thread
    def sameThreadIOs() =
        for
            mol <- meaningOfLife.debugLog
            lang <- favLang.debugLog
        yield (mol, lang)

    // introduce the Fiber
    def createFiber: Fiber[IO, Throwable, String] = ???

    // the fiber is not actually started, but the fiber allocation is wrapped in another effect
    val aFiber: IO[Fiber[IO, Throwable, Int]] = meaningOfLife.debugLog.start

    def differentThreadIOs() =
        for
            _ <- aFiber
            _ <- favLang.debugLog
        yield ()

    override def run: IO[Unit] = differentThreadIOs().void
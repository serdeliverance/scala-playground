package part2effects

import cats.effect.IO

object IOIntro:
    def sequenceTakeLast[A, B](ioa: IO[B], iob: IO[B]): IO[B] =
        ioa.flatMap(_ => iob)

    def sequenceTakeFirst[A, B](ioa: IO[A], iob: IO[B]): IO[A] =
        ioa.flatMap(a => iob.map(_ => a))

    def sequenceTakeFirst_v2[A, B](ioa: IO[A], iob: IO[B]): IO[A] =
        for
            a <- ioa
            _ <- iob
        yield a

    def forever[A](ioa: IO[A]): IO[A] =
        ioa.flatMap(_ => forever(ioa))

    def convert[A, B](ioa: IO[A], value: B): IO[B] =
        ioa.map(_ => value)

    def asUnit[A](ioa: IO[A]): IO[Unit] =
        ioa.map(_ => ())
package part2effects

import cats.effect.IO

object IOIntro:
    def sequenceTakeLast[A, B](ioa: IO[B], iob: IO[B]): IO[B] =
        ioa.flatMap(_ => iob)
    
    def sequenceTakeLast_v2[A, B](ioa: IO[B], iob: IO[B]): IO[B] =
        ioa *> iob // "andThen"

    def sequenceTakeLast_v3[A, B](ioa: IO[B], iob: IO[B]): IO[B] =
        ioa >> iob // "andThen"

    def sequenceTakeFirst[A, B](ioa: IO[A], iob: IO[B]): IO[A] =
        ioa.flatMap(a => iob.map(_ => a))

    def sequenceTakeFirs_v2[A, B](ioa: IO[A], iob: IO[B]): IO[A] =
        ioa <* iob

    def sequenceTakeFirst_v2[A, B](ioa: IO[A], iob: IO[B]): IO[A] =
        for
            a <- ioa
            _ <- iob
        yield a

    def forever[A](ioa: IO[A]): IO[A] =
        ioa.flatMap(_ => forever(ioa))
    
    def forever_v2[A](ioa: IO[A]): IO[A] =
        ioa >> forever_v2(ioa)
    
    def forever_v3[A](ioa: IO[A]): IO[A] =
        ioa *> forever_v3(ioa)

    def forever_v4[A](ioa: IO[A]): IO[A] =
        ioa.foreverM

    def convert[A, B](ioa: IO[A], value: B): IO[B] =
        ioa.map(_ => value)

    def convert_v2[A, B](ioa: IO[A], value: B): IO[B] =
        ioa.as(value)

    def asUnit[A](ioa: IO[A]): IO[Unit] =
        ioa.as(())

    @main def run: Unit =
        import cats.effect.unsafe.implicits.global
        forever_v3(IO {
            println("forever!")
            Thread.sleep(100)
        }).unsafeRunSync()
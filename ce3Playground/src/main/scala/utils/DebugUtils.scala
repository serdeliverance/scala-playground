package utils

import cats.effect.IO

object DebugUtils:
  extension [A](io: IO[A])
    def debugLog: IO[A] =
      for
        a <- io
        tn = Thread.currentThread.getName
        _ = println(s"[${tn}] $a")
      yield a
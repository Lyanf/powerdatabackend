package dclab.powerdatabackend.util

final class Constants {
    //    final val SQL_HOST = "localhost"
    companion object {
        final val ALGORITHM_URL = System.getenv("ALGORITHM_URL")
        final val SHARED_ROOT = System.getenv("SHARED_ROOT")
    }
}
import java.util.Date


object Main extends Loggable {

  def main(args: Array[String]) {

    logger.info("First log message - info level")
    logger.debug("First log message - debug level")
    logger.trace("First log message - trace level")

    println("Started at " + new Date)

  }

}

package org.folio.rest.tools.utils;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class LogUtil {

  private static final Logger log = LoggerFactory.getLogger(LogUtil.class);

  public static void formatStatsLogMessage(String clientIP, String httpMethod, String httpVersion, int ResponseCode, long responseTime,
      long responseSize, String url, String queryParams, String message) {

    String message1 = new StringBuilder(clientIP).append(" ").append(httpMethod).append(" ").append(url).append(" ").append(queryParams)
        .append(" ").append(httpVersion).append(" ").append(ResponseCode).append(" ").append(responseSize).append(" ").append(responseTime)
        .append(" ").append(message).toString();

    log.info(message1);
  }

  public static void formatStatsLogMessage(String clientIP, String httpMethod, String httpVersion, int ResponseCode, long responseTime,
      long responseSize, String url, String queryParams, String message, String tenantId, String body) {

    String message1 = new StringBuilder(clientIP).append(" ").append(httpMethod).append(" ").append(url).append(" ").append(queryParams)
        .append(" ").append(httpVersion).append(" ").append(ResponseCode).append(" ").append(responseSize).append(" ").append(responseTime)
        .append(" tid=").append(tenantId).append(" ").append(message).append(" ").append(body).toString();

    log.info(message1);
  }

  public static void formatLogMessage(String clazz, String funtion, String message) {
    log.info(new StringBuilder(clazz).append(" ").append(funtion).append(" ").append(message));
  }
  public static void formatErrorLogMessage(String clazz, String funtion, String message) {
    log.error(new StringBuilder(clazz).append(" ").append(funtion).append(" ").append(message));
  }

  public static void closeLogger() {
    LoggerFactory.removeLogger("LogUtil");
  }

  /**
   * Update the log level for all packages / a specific package / a specific class
   * @param packageName - pass "*" for all packages
   * @param level - see {@link Level}
   * @return - JsonObject with a list of updated loggers and their levels
   */
  public static JsonObject updateLogConfiguration(String packageName, Level level){

    JsonObject updatedLoggers = new JsonObject();

    LogManager manager = LogManager.getLogManager();
    Enumeration<String> loggers = manager.getLoggerNames();
    while (loggers.hasMoreElements()) {
      String log = loggers.nextElement();
      if(log != null && packageName != null && (log.startsWith(packageName.replace("*", "")) || "*".equals(packageName)) ){
        java.util.logging.Logger logger = manager.getLogger(log);
        if(logger != null){
          logger.setLevel(level);
          updatedLoggers.put(logger.getName(), logger.getLevel().getName());
        }
      }
    }
    return updatedLoggers;
  }

  /**
   * Iterate over all loggers and return a json object with them and their log level
   * @return JsonObject
   */
  public static JsonObject getLogConfiguration(){

    JsonObject loggers = new JsonObject();

    LogManager manager = LogManager.getLogManager();
    Enumeration<String> loggerNames = manager.getLoggerNames();
    while (loggerNames.hasMoreElements()) {
      String log = loggerNames.nextElement();
      if(log != null){
        java.util.logging.Logger logger = manager.getLogger(log);
        if(logger != null && logger.getLevel() != null && logger.getName() != null){
          loggers.put(logger.getName(), logger.getLevel().getName());
        }
      }
    }
    return loggers;
  }

}

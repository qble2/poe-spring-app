package qble2.poe.exception;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

  @Override
  public void handleUncaughtException(Throwable ex, Method method, Object... params) {
    log.error("async uncaught exception", ex);
    for (Object param : params) {
      log.error("Parameter value - {}", param);
    }
  }

}

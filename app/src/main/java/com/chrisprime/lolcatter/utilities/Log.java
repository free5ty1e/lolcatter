package com.chrisprime.lolcatter.utilities;

/**
 * Custom logging class mainly to overcome the puny 4000 character limit of the wily logcat entry
 * But also so we can fully control how logging works whenever necessary
 * This is a handy logging class I typically will throw into my projects
 *
 * Created by cpaian on 2/7/15.
 */
public class Log
{
  public enum LogLevel
  {
    NONE,
    WTF,
    ERROR,
    WARN,
    INFO,
    DEBUG,
    VERBOSE,
    SUPER_ULTRA_UBER_VERBOSE
  }

  private static final int LOGCAT_CHARACTER_LIMIT = 4000;
  private static LogLevel logLevel = LogLevel.SUPER_ULTRA_UBER_VERBOSE;

  protected static LogLevel getLogLevel()
  {
    return logLevel;
  }

  public static boolean isLogLevelAtLeast(LogLevel logLevel)
  {
    boolean ret = false;
    if (getLogLevel().ordinal() >= logLevel.ordinal())
    {
      ret = true;
    }
    return ret;
  }

  /**
   *WTF, mate?
   */
  public static void wtf(String tag, String string)
  {
    wtf(tag, string, null);
  }

  /**
   *WTF, mate?
   */
  public static void wtf(String tag, String string, Throwable throwable)
  {
    if (getLogLevel().ordinal() >= LogLevel.WTF.ordinal())
    {
      android.util.Log.wtf(tag, string, throwable);
    }
  }

  /**
   *Error
   */
  public static void e(String tag, String string)
  {
    e(tag, string, null);
  }

  /**
   *Error
   */
  public static void e(String tag, String string, Throwable throwable)
  {
    if (getLogLevel().ordinal() >= LogLevel.WARN.ordinal())
    {
      android.util.Log.e(tag, string, throwable);
    }
  }

  /**
   * Warn
   */
  public static void w(String tag, String string)
  {
    w(tag, string, null);
  }

  /**
   * Warn
   */
  public static void w(String tag, String string, Throwable throwable)
  {
    if (getLogLevel().ordinal() >= LogLevel.WARN.ordinal())
    {
      android.util.Log.w(tag, string, throwable);
    }
  }

  /**
   * Info
   */
  public static void i(String tag, String string)
  {
    i(tag, string, null);
  }

  /**
   * Info
   */
  public static void i(String tag, String string, Throwable throwable)
  {
    if (getLogLevel().ordinal() >= LogLevel.INFO.ordinal())
    {
      android.util.Log.i(tag, string, throwable);
    }
  }

  /**
   * Debug
   */
  public static void d(String tag, String string)
  {
    d(tag, string, null);
  }

  /**
   * Debug
   */
  public static void d(String tag, String string, Throwable throwable)
  {
    if (getLogLevel().ordinal() >= LogLevel.DEBUG.ordinal())
    {
      android.util.Log.d(tag, string, throwable);
    }
  }

  /**
   * Verbose
   */
  public static void v(String tag, String string)
  {
    v(tag, string, null);
  }

  /**
   * Verbose
   */
  public static void v(String tag, String string, Throwable throwable)
  {
    if (getLogLevel().ordinal() >= LogLevel.VERBOSE.ordinal())
    {
      android.util.Log.v(tag, string, throwable);
    }
  }

  /**
   * Super ultra uber verbose (why not?)
   * Top tip: This log level also includes the capability to split a huge string into log-sized chunks so we can read the whole thing!
   */
  public static void suuv(String tag, String string)
  {
    suuv(tag, string, null);
  }

  /**
   * Super ultra uber verbose (why not?)
   * Top tip: This log level also includes the capability to split a huge string into log-sized chunks so we can read the whole thing!
   */
  public static void suuv(String tag, String string, Throwable throwable)
  {
    if (getLogLevel().ordinal() >= LogLevel.SUPER_ULTRA_UBER_VERBOSE.ordinal())
    {
      //These log entries will all start on a new line for easier visual log scanning, and to keep copy / paste operations simpler
      //Also, the first character in a log entry cannot be a newline, so the first colon : is just a dummy char to make this happen
      string = ":\n" + string;
      if (string.length() > LOGCAT_CHARACTER_LIMIT) //This string is longer than the logcat limit!  Split it up so we can see the entire thing!
      {
        tag = tag + " split";
        android.util.Log.v(tag, string.substring(0, LOGCAT_CHARACTER_LIMIT), throwable);
        suuv(tag, string.substring(LOGCAT_CHARACTER_LIMIT), throwable);
      }
      else
      {
        android.util.Log.v(tag, string, throwable);
      }
    }
  }
}

package com.onehilltech.httpres.retrofit.gson;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @class GsonResourceManager
 *
 * Resource manager for the Gson serialization library.
 */
public class GsonResourceManager
{
  private static GsonResourceManager instance_;

  private final HashMap<String, Type> types_ = new HashMap<> ();

  /**
   * Get the singleton instance.
   *
   * @return
   */
  public static GsonResourceManager getInstance ()
  {
    if (instance_ != null)
      return instance_;

    instance_ = new GsonResourceManager ();
    return instance_;
  }

  /**
   * Register a resource type.
   *
   * @param name
   * @param type
   */
  public void registerType (String name, Type type)
  {
    types_.put (name, type);
  }

  /**
   * Get a registered resource type.
   *
   * @param name
   * @return
   */
  public Type getType (String name)
  {
    return this.types_.get (name);
  }
}

package com.onehilltech.httpres.retrofit;

import android.os.Looper;

import com.onehilltech.httpres.CompletionCallback;
import com.onehilltech.httpres.Concurrent;
import com.onehilltech.httpres.Task;

import org.junit.Assert;
import org.junit.Test;

public class AndroidCompletionCallbackTest
{
  private final Object lock_ = new Object ();

  @Test
  public void testOnComplete () throws Exception
  {
    final int answer = 1;

    final CompletionCallback callback = new AndroidCompletionCallback ()
    {
      @Override
      public void onPostComplete (Object result)
      {
        synchronized (lock_)
        {
          Assert.assertEquals (answer, result);
          Assert.assertSame (Looper.getMainLooper (), Looper.myLooper ());

          lock_.notify ();
        }
      }

      @Override
      public void onPostCancel ()
      {
        Assert.fail ();
      }

      @Override
      public void onPostFail (Throwable reason)
      {
        Assert.fail ();
      }
    };

    synchronized (this.lock_)
    {
      Concurrent.getDefault ().waterfall (new Task ()
      {
        @Override
        public void run (Object item, CompletionCallback callback)
        {
          callback.done (answer);
        }
      }).execute (1, callback);

      // Wait for the test to complete.
      lock_.wait ();
    }
  }
}

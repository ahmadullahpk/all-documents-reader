/*
 * 文件名称:          SafeAsyncTask.java
 *  
 * 编译器:            android2.2
 * 时间:              下午9:36:57
 */
package com.ahmadullahpk.alldocumentreader.xs.simpletext.control;

import android.os.AsyncTask;
import java.util.concurrent.RejectedExecutionException;

public abstract class SafeAsyncTask<Params, Progress, Result> extends
    AsyncTask<Params, Progress, Result>
{
    /**
     * 
     * @param params
     */
    public void safeExecute(Params...params)
    {
        try
        {
            execute(params);
        }
        catch(RejectedExecutionException e)
        {
            //MainControl.sysKit.getErrorKit().writerLog(e);
            onPreExecute();
            if (isCancelled())
            {
                onCancelled();
            }
            else
            {
                onPostExecute(doInBackground(params));
            }
        }
    }
}

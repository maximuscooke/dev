#include "CThread.h"

namespace  _COMMON_NS
{
    void CThread::doWork() 
    { 
        preDoWork(); 
        
        runAsync(); 
        
        postDoWork(); 
    }

    void CThread::preDoWork() 
    { 
        setOperation(_THREAD_OP_RUNNING);

        if (mCallBack != nullptr)
        {
            mCallBack->threadStarting(mID);
        }
    }

    void CThread::postDoWork() 
    { 
        setOperation(_THREAD_OP_FINISHED);

         if (mCallBack != nullptr)
        {
            mCallBack->threadFinished(mID);
        }
      }

     std::thread CThread::start(CThread& thread, Int id, CThreadCallBack* callback)
     {
       _LOGF("Starting Thread %2d", id);

        thread.setUserID(id); 
        
        thread.setOperation(_THREAD_OP_RUNNING);

        thread.setThreadCallBack(callback);
        
        std::thread t( runThread, &thread ); 
         
        return t; 
     }

    std::thread CThread::start(CThread& thread, Int id) 
    {
        return start(thread, id, nullptr);
    }

    void CThread::stop() 
    { 
        setOperation(_THREAD_OP_STOP);

        if (mCallBack != nullptr)
        {
            mCallBack->threadStopped(mID);
        }
    }

    void CThread::runThread(CThread* t) 
    { 
        t->doWork(); 
    }
}
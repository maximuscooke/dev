#ifndef _CTHREAD_H_
#define _CTHREAD_H_

#include "CObject.h"
#include <thread>
#include <atomic>
#include <chrono>
#include <mutex>

#define _THREAD_OP_STOP         (1<<0)
#define _THREAD_OP_RUNNING      (1<<1)
#define _THREAD_OP_FINISHED     (1<<2)

namespace _COMMON_NS
{
    class CThreadCallBack
    {
        public:
            _VIRTUAL void threadStarting(int id) _ABSTRACT;
            _VIRTUAL void threadFinished(int id) _ABSTRACT;
            _VIRTUAL void threadStopped(int id) _ABSTRACT;
    };

    class CThread : public CObject
    {
        private:
            std::thread mThread;
            std::atomic_int mID;
            std::atomic_int mThreadOperation;
            CThreadCallBack* mCallBack=nullptr;
  
        public:
            CThread()
            {
                mCallBack=nullptr;
            }

            CThread(CThreadCallBack* cb)
            {
                mCallBack = cb;
            }

            _STATIC void runThread(CThread* t);
     
            _STATIC std::thread start(CThread& t, Int id);
            _STATIC std::thread start(CThread& t, Int id, CThreadCallBack* cb);

            _OVERRIDE   std::string getClassName() const;

            _VIRTUAL    void runAsync() _ABSTRACT;

            void preDoWork();
            void postDoWork();
            void doWork();

            _GET_PROPERTY(CThreadCallBack*, ThreadCallBack, mCallBack)
            _SET_PROPERTY(CThreadCallBack*, ThreadCallBack, mCallBack)

            _INLINE     void sleepFor(std::chrono::seconds t) { std::this_thread::sleep_for( std::chrono::seconds(t) ); }
            _INLINE     void sleepFor(std::chrono::milliseconds t) { std::this_thread::sleep_for( std::chrono::milliseconds(t) ); }
            _INLINE     void sleepFor(std::chrono::microseconds t) { std::this_thread::sleep_for( std::chrono::microseconds(t) ); }
            _INLINE     void sleepFor(std::chrono::minutes t) { std::this_thread::sleep_for( std::chrono::minutes(t) ); }

            _INLINE     std::thread::id getID() { return std::this_thread::get_id(); }

            _INLINE void start(Int id) { start(id, nullptr); };
            _INLINE void start(Int id, CThreadCallBack* cb) { mThread = CThread::start(*this, id, cb); };

            void stop();

            _INLINE void wait() { join(); }
            _INLINE void join() { mThread.join(); }
            _INLINE Bool isJoinable() { return mThread.joinable(); }
            _INLINE void detach()  { mThread.detach(); }

            _INLINE Int  getOperation() { return mThreadOperation; }
            _INLINE void setOperation(Int op) { mThreadOperation = op; }
 
            _INLINE void setUserID(int id) { mID = id; }
            _INLINE Int  getUserID() { return mID; }
     };
}
#endif
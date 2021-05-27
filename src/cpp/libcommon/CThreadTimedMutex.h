#ifndef _CTHREADTIMEDMUTEX_H_
#define _CTHREADTIMEDMUTEX_H_

#include "CThread.h"

namespace  _COMMON_NS
{
    class CThreadTimedMutex : public CThread
    {
        private:
            std::timed_mutex mMutex;
        
            _OVERRIDE std::string getClassName() const;

      protected:
             Bool tryLockForTimedMutex(std::chrono::seconds t);
             Bool tryLockForTimedMutex(std::chrono::milliseconds t);
             Bool tryLockForTimedMutex(std::chrono::microseconds t);
             Bool tryLockForTimedMutex(std::chrono::minutes t);
             void unlockTimedMutext();
    };
}
#endif
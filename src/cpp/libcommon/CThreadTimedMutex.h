#ifndef _CTHREADTIMEDMUTEX_H_
#define _CTHREADTIMEDMUTEX_H_

#include "CThread.h"

namespace  _COMMON_NS
{
    class CThreadTimedMutex : public CThread
    {
        private:
            std::timed_mutex mMutex;
        
            std::string getClassName() const _OVERRIDE;

      protected:
             Bool tryLockForTimedMutex(std::chrono::seconds t);
             Bool tryLockForTimedMutex(std::chrono::milliseconds t);
             Bool tryLockForTimedMutex(std::chrono::microseconds t);
             Bool tryLockForTimedMutex(std::chrono::minutes t);
             void unlockTimedMutext();
    };
}
#endif
#ifndef _CTHREADMUTEX_H_
#define _CTHREADMUTEX_H_

#include "CThread.h"

namespace  _COMMON_NS
{
    class CThreadMutex : public CThread
    {
        private:
            std::mutex mMutex;
        
            std::string getClassName() const _OVERRIDE;

        protected:
            void lockMutex();
            Bool tryLockMutex();
            void unlockMutext();
    };
}
#endif
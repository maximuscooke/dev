#ifndef _CTHREADMUTEX_H_
#define _CTHREADMUTEX_H_

#include "CThread.h"

namespace  _COMMON_NS
{
    class CThreadMutex : public CThread
    {
        private:
            std::mutex mMutex;
        
            _OVERRIDE std::string getClassName() const { return std::string( (const char*)_QUOTE(CThreadMutex) ); }

        protected:
            void lockMutex();
            Bool tryLockMutex();
            void unlockMutext();
    };
}
#endif
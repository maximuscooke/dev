#include "CThreadMutex.h"

namespace  _COMMON_NS
{
    std::string CThreadMutex::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CThreadMutex) ); 
    }

    void CThreadMutex::lockMutex() 
    { 
        mMutex.lock(); 
    }

    Bool CThreadMutex::tryLockMutex() 
    { 
        return mMutex.try_lock(); 
    }

    void CThreadMutex::unlockMutext() 
    { 
        mMutex.unlock(); 
    }
}

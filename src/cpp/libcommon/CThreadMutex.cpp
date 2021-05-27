#include "CThreadMutex.h"

namespace  _COMMON_NS
{
    
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

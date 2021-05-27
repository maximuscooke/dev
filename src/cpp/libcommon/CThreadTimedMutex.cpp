#include "CThreadTimedMutex.h"

namespace  _COMMON_NS
{
    std::string CThreadTimedMutex::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CThreadTimedMutex) );
    }

    Bool CThreadTimedMutex::tryLockForTimedMutex(std::chrono::seconds t) 
    { 
        return mMutex.try_lock_for(t); 
    }

    Bool CThreadTimedMutex::tryLockForTimedMutex(std::chrono::milliseconds t) 
    { 
        return mMutex.try_lock_for(t); 
    }

    Bool CThreadTimedMutex::tryLockForTimedMutex(std::chrono::microseconds t) 
    { 
        return mMutex.try_lock_for(t); 
    }

    Bool CThreadTimedMutex::tryLockForTimedMutex(std::chrono::minutes t) 
    { 
        return mMutex.try_lock_for(t); 
    }

    void CThreadTimedMutex::unlockTimedMutext() 
    { 
        mMutex.unlock(); 
    }
}
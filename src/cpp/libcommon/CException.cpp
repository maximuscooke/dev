#include "CException.h"

namespace  _COMMON_NS
{
    std::string CException::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CException) );
    }

    void CException::log() const
    {
        std::cout << this->mMsg << std::endl;
    }

    std::string CException::toString() const
    {
        return this->mMsg;
    }
}
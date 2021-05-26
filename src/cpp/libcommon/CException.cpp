#include "CException.h"

namespace  _COMMON_NS
{
    void CException::log() const
    {
        std::cout << this->mMsg << std::endl;
    }

    std::string CException::toString() const
    {
        return this->mMsg;
    }
}
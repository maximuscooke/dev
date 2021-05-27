#include <limits.h>
#include "CLong.h"

namespace  _COMMON_NS
{
    std::string CLong::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CLong) );
    }

    void CLong::log() const
    {
        std::cout << this->getValue();
    }

    std::string CLong::toString() const
    {
        return std::to_string( this->getValue() );
    }

    Integer CLong::compare(CObject* pObj)
    {
        _TYPE_CHECK_OBJECT(CLong*, pObj);

        return _COMPARE_ORDER(mValue, ((CLong*)pObj)->mValue);
    }

    Long CLong::getMax() 
    { 
        return LONG_MAX; 
    }

    Long CLong::getMin() 
    { 
        return LONG_MIN; 
    }
}
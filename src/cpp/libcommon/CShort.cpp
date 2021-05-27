#include <limits.h>
#include "CShort.h"

namespace  _COMMON_NS
{
    std::string CShort::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CShort) );
    }

    void CShort::log() const
    {
        std::cout << this->getValue();
    }

    std::string CShort::toString() const
    {
        return std::to_string( this->getValue() );
    }

    Integer CShort::compare(CObject* pObj)
    {
        _TYPE_CHECK_OBJECT(CShort*, pObj);

         return _COMPARE_ORDER(mValue, ((CShort*)pObj)->mValue);
    }

    Short CShort::getMax() 
    { 
        return SHRT_MAX; 
    }

    Short CShort::getMin() 
    { 
        return SHRT_MIN; 
    }
}
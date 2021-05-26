#include <float.h>
#include "CFloat.h"

namespace  _COMMON_NS
{
    void CFloat::log() const
    {
        std::cout << this->getValue();
    }

    std::string CFloat::toString() const
    {
        return std::to_string( this->getValue() );
    }

    Integer CFloat::compare(CObject* pObj)
    {
        _TYPE_CHECK_OBJECT(CFloat*, pObj);

        return _COMPARE_ORDER(mValue, ((CFloat*)pObj)->mValue);
    }

    Float CFloat::getMax() 
    { 
        return FLT_MAX; 
    }

    Float CFloat::getMin() 
    { 
        return FLT_MIN; 
    }

}
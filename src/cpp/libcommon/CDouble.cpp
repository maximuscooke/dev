#include <float.h>
#include "CDouble.h"

namespace  _COMMON_NS
{
    std::string CDouble::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CDouble) );
    }

    void CDouble::log() const
    {
        std::cout << this->getValue();
    }

    std::string CDouble::toString() const
    {
        return std::to_string( this->getValue() );
    }

    Integer CDouble::compare(CObject* pObj)
    {
        _TYPE_CHECK_OBJECT(CDouble*, pObj);

        return _COMPARE_ORDER(mValue, ((CDouble*)pObj)->mValue);
    }

    Double CDouble::getMax() 
    { 
        return DBL_MAX; 
    }
    Double CDouble::getMin() 
    { 
        return DBL_MIN; 
    }

}
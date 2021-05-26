#include <limits.h>
#include "CInteger.h"

namespace  _COMMON_NS
{
    void CInteger::log() const
    {
        std::cout << this->getValue();
    }

    std::string CInteger::toString() const
    {
        return std::to_string( this->getValue() );
    }

    Integer CInteger::compare(CObject* pObj)
    {   
        _TYPE_CHECK_OBJECT(CInteger*, pObj);
        
        return _COMPARE_ORDER(mValue, ((CInteger*)pObj)->mValue);
    }

    Integer CInteger::getMax() 
    { 
        return INT_MAX; 
    }
             
    Integer CInteger::getMin() 
    { 
        return INT_MIN; 
    }
}
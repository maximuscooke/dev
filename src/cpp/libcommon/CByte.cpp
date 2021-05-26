#include <limits.h>
#include "CByte.h"

namespace  _COMMON_NS
{
    void CByte::log() const
    {
        std::cout << this->getValue();
    }

    std::string CByte::toString() const
    {
        return std::to_string( this->getValue() );
    }

    Integer CByte::compare(CObject* pObj)
    {  
        _TYPE_CHECK_OBJECT(CByte*, pObj);
        
        return _COMPARE_ORDER(mValue, ((CByte*)pObj)->mValue);
    }

    Short CByte::getMax() 
    { 
        return CHAR_MAX; 
    }
    Short CByte::getMin() 
    { 
        return CHAR_MIN; 
    }

}
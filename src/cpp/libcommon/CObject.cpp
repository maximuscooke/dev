#include "CObject.h"

namespace  _COMMON_NS
{
    std::string CObject::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(_className) ); 
    }

    void CObject::log() const
    {
       std::cout << _OVERRIDE_MSG;
    }

    Integer CObject::compare(CObject* pObj)
    {
        _TYPE_CHECK_OBJECT(CObject*, pObj);

        return (pObj != NULL && pObj == this) ? _EQUAL : _NOT_EQUAL;
    }
    
    std::string CObject::toString(Integer format) const
    {
         return toString();
    }

    std::string CObject::toString() const
    {
        return std::string(_OVERRIDE_MSG);
    }

    Long CObject::id() const
    {
        return 0;
    }

    CObject* CObject::clone() const
    {
        return NULL;
    }

    std::string CObject::platform()
    {
        return std::string(_PLATFORM);
    }
}
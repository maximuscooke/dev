#ifndef _CCOLLECTION_H_
#define _CCOLLECTION_H_

#include "CObject.h"

#define _DEFAULT_COLLECTION_SIZE    16

namespace  _COMMON_NS
{
    class CCollection : public CObject
    {
        _VIRTUAL Boolean isEmpty() const _ABSTRACT;
        _VIRTUAL Count getElementCount() const _ABSTRACT;
        _VIRTUAL void clear() _ABSTRACT;
    };
}
#endif
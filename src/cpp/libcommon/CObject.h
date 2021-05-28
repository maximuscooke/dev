#ifndef _COBJECT_H_
#define _COBJECT_H_

#include "libcommon.h"

namespace  _COMMON_NS
{
    class CObject
    {
        public:
            _INLINE_STATIC std::string platform();           
            _VIRTUAL void log() const;
            _VIRTUAL Integer compare(CObject* pObj);
            _VIRTUAL std::string toString() const;
            _VIRTUAL std::string toString(Integer format) const;
            _VIRTUAL CObject* clone() const;
            _VIRTUAL Long id() const;
            _VIRTUAL std::string getClassName() const;
            _VIRTUAL ~CObject() { }
     };
}
#endif
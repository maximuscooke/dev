#ifndef _CMATHS_H_
#define _CMATHS_H_

#include "CObject.h"


namespace  _COMMON_NS
{
    class CMaths : public CObject
    {
        public:
            _OVERRIDE std::string getClassName() const { return std::string( (const char*)_QUOTE(CMaths) ); }

            void seed();
            Integer genRandomNumber(Integer max);
    };
}
#endif
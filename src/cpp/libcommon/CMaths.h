#ifndef _CMATHS_H_
#define _CMATHS_H_

#include "CObject.h"


namespace  _COMMON_NS
{
    class CMaths : public CObject
    {
        public:
            std::string getClassName() const _OVERRIDE;

            _STATIC void seedRandomNumber();
            _STATIC Integer genRandomNumber(Integer max);
    };
}
#endif
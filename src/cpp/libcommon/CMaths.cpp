#include "CMaths.h"
#include <time.h>

namespace _COMMON_NS
{
    std::string CMaths::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CMaths) );
    }

    void CMaths::seedRandomNumber() 
    { 
        srand (time(NULL)); 
    }

    Integer CMaths::genRandomNumber(Integer max)
    {
        return rand() % max;
    }
}
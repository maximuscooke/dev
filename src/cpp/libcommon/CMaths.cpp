#include "CMaths.h"
#include <time.h>

namespace _COMMON_NS
{
    void CMaths::seed() 
    { 
        srand (time(NULL)); 
    }

    Integer CMaths::genRandomNumber(Integer max)
    {
        return rand() % max;
    }
}
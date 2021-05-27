#include "CLinePosition.h"

namespace  _COMMON_NS
{
    std::string CLinePosition::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CLinePosition) );
    }

    void CLinePosition::log() const
    {
        std::cout << toString() << std::endl;
    }

    std::string CLinePosition::toString() const
    {
        Char buffer[256];

        sprintf(buffer, "Ln = %i, Col = %i", getLineNumber(), getColumn() );

        return std::string(buffer);
    }

}

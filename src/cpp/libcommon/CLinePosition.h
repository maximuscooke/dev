#ifndef _CLINEPOSITION_H_
#define _CLINEPOSITION_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CLinePosition : public CObject
    {
        private:
            Index mLineNumber;
            Index mColumn;

        public:
            CLinePosition(Index ln, Index col) 
            { 
                mLineNumber = ln, 
                mColumn = col; 
            };

            _GET_PROPERTY(Index, LineNumber, mLineNumber)
            _GET_PROPERTY(Index, Column, mColumn)

            void log() const _OVERRIDE;
            std::string toString() const _OVERRIDE;

             std::string getClassName() const _OVERRIDE;
    };
}
#endif
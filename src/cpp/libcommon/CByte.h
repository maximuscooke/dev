#ifndef _CBYTE_H_
#define _CBYTE_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CByte : public CObject
    {
        private:
            Byte mValue;

        public:
            CByte()
            {
                this->mValue = 0;
            }

            CByte(Byte value)
            {
                this->mValue = value;
            }
 
            _STATIC Short getMax();
            _STATIC Short getMin();

            _OVERRIDE std::string getClassName() const;
            _OVERRIDE void log() const;
            _OVERRIDE std::string toString() const;
            _OVERRIDE Integer compare(CObject* pObj);

            _OP_NUMERIC(CByte, Byte, mValue)
            _OP_NUMERIC_MOD(CByte, Byte, mValue)

            _PROPERTY(Byte, Value, mValue)
     };
}
#endif
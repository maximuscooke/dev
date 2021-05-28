#ifndef _CSHORT_H_
#define _CSHORT_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CShort : public CObject
    {
        private:
            Short mValue;

        public:
            CShort()
            {
                this->mValue = 0;
            }

            CShort(Short value)
            {
                this->mValue = value;
            }

            _STATIC Short getMax();
            _STATIC Short getMin();
            
            std::string getClassName() const _OVERRIDE;
            void log() const _OVERRIDE;
            std::string toString() const _OVERRIDE;
            Integer compare(CObject* pObj) _OVERRIDE;

            _OP_NUMERIC(CShort, Short, mValue)
            _OP_NUMERIC_MOD(CShort, Short, mValue)

            _PROPERTY(Short, Value, mValue)
     };
}
#endif